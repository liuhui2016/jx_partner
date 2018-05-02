package com.game.smvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.smvc.entity.Jxpublish;
import com.game.smvc.entity.result.DataResult;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxMerchantPublishService;
import com.game.smvc.util.HttpUtil;
import com.game.smvc.util.RandomUtil;
import com.game.smvc.util.mapUtil;
import com.game.spider.bean.LinkTypeData;
import com.game.spider.core.ExtractService;
import com.game.spider.rule.Rule;

@Controller
@RequestMapping({ "/smvc" })
public class PublishServiceController {
	@Autowired
	private IJxMerchantPublishService merchantPublish;

	/**
	 * 用户添加发布
	 * 
	 * @param request
	 * @param goodsId
	 * @return
	 */

	
	@ResponseBody
	@RequestMapping(value = "/user/addPublish")
	public Result addPublish(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String phoneNum = jsonObject.getString("phoneNum");
			if ((phoneNum == null) || ("".equals(phoneNum.trim()))) {
				return new Result(Errors.USER_ERROR_PHONE_FORMAT);
			}
			Jxpublish publish = new Jxpublish();
			//publish.setPub_no(RandomUtil.getRandom());
			publish.setPub_address(jsonObject.getString("address"));
			publish.setPub_categoryid(Long.parseLong(jsonObject
					.getString("categoryid")));
			publish.setPub_content(jsonObject.getString("content"));
			publish.setPub_seller(jsonObject.getString("sellername"));
			publish.setPub_vaildtime(jsonObject.getString("begintime"));
			publish.setPub_invildtime(jsonObject.getString("endtime"));
			publish.setPh_no(jsonObject.getString("phoneNum"));
			publish.setPub_url(jsonObject.getString("imgUrl"));
			publish.setPub_name(jsonObject.getString("userPhone"));
			publish.setPub_addtime(new Date());
			merchantPublish.save(publish);
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("ord_no", "ordno");
			map1.put("price", "price");
			map1.put("seller", "seller");
			return new SecretResult(Errors.OK, list);

		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}


	/*
	 * 根据分类id查询服务列表
	 */
	@ResponseBody
	@RequestMapping(value = "/user/publishList")
	public Result publishList(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String id = jsonObject.getString("id");
			String address=jsonObject.getString("address");
			int page = Integer.parseInt(jsonObject.getString("page"))-1;
			if ((id == null) || ("".equals(id.trim()))||page<0) {
				return new Result(Errors.JSON_ERROR_NOTJSON);
			}
			page=page*10;
			
			List<Map<String, Object>> list = merchantPublish.findPublishbycategoryId(
					Integer.parseInt(id),page,address);

			return new SecretResult(Errors.OK,list);

		} catch (JSONException e) {
			e.printStackTrace();
			return new Result(Errors.JSON_ERROR_NOTJSON);
		} catch (Exception e) {
 			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	

	/**
	 * 地图距离计算
	 * 需要传经纬度
	 * @param  long1用户经度
	 * @param  lat1用户纬度
	 * @param  long2商家经度
	 * @param  lat2商家纬度
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping({ "/userwappush/mapdistance" })
	public Result mapdistance(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();
			String lg1 = jsonObject.getString("userlong");//用户经度
			String la1 = jsonObject.getString("userlat");//用户纬度
			String lg2 = jsonObject.getString("merchantslong");//商家经度
			String la2 = jsonObject.getString("merchantslat");//商家纬度
			Double long1 = Double.parseDouble(lg1);
			Double lat1 = Double.parseDouble(la1);
			Double long2 = Double.parseDouble(lg2);
			Double lat2 = Double.parseDouble(la2);
			String distance = mapUtil.Distance(long1, lat1, long2, lat2);
			map.put("distance", distance);
			list.add(map);
			return new SecretResult(Errors.OK, list);
		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	/**
	 * 商家详情
	 * 综合版
	 * @param lh
	 * @return
	 * traffic 访问量
	 */
	@ResponseBody
	@RequestMapping({ "/userwappush/doultondetails" })
	public Result mydoultonDetails(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String pubId = jsonObject.getString("pubId");
			
			String lg1 = null;
			String la1 = null;
			if(jsonObject.containsKey("userlong")){
				lg1 = jsonObject.getString("userlong");//用户经度
			}else{
				lg1 = "0";
			}
			
			if(jsonObject.containsKey("userlat")){
				la1 = jsonObject.getString("userlat");//用户纬度
			}else{
				la1 = "0";
			}
			
			
			//拿到访问量
			int sum = merchantPublish.findTraffic(pubId);
			int traffic = sum + 1;
			System.out.println("访问量:"+traffic);
			Jxpublish publish = merchantPublish.findUnique("from Jxpublish where pub_id = ?", Long.parseLong(pubId));
			publish.setPub_traffic(traffic);
			Jxpublish jxPublish = this.merchantPublish.save(publish);
			String lg2 = jxPublish.getPub_longitude();//商家经度
			String la2 = jxPublish.getPub_latitude();//商家纬度
			System.out.println("lg2:"+lg2);
			System.out.println("la2:"+la2);
			if("".equals(lg2) || lg2 == null){
				System.out.println("经度为空");
				lg2 = "0";
			}
			
			if("".equals(la2) || la2 == null){
				System.out.println("纬度为空");
				la2 = "0";
			}
			
			Double long1 = Double.parseDouble(lg1);
			Double lat1 = Double.parseDouble(la1);
			Double long2 = Double.parseDouble(lg2);
			Double lat2 = Double.parseDouble(la2);
			String distance = mapUtil.Distance(long1, lat1, long2, lat2);
			List<Map<String,Object>> list0 = merchantPublish.findPublishdetailbyId(Integer.parseInt(pubId));
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> m = new HashMap<String, Object>();
			Map<String,Object> map = list0.get(0);
			map.put("distance", distance);
			list0.add(map);
			m.put("url", map.get("url"));//图片地址
			m.put("name", map.get("name"));//净水机名字
			m.put("pubName", map.get("pubName"));//发布人
			m.put("address", map.get("address"));//地址
			m.put("content", map.get("content"));//内容
			m.put("invildtime", map.get("invildtime"));//开始服务时间
			m.put("vaildtime", map.get("vaildtime"));//服务结束时间
			m.put("phoneNum", map.get("phoneNum"));//电话号码
			m.put("traffic", map.get("traffic"));//访问量
			m.put("inquiries", map.get("inquiries"));//咨询量
			m.put("distance", map.get("distance"));//距离
			m.put("merchantlong", jxPublish.getPub_longitude());//商家经度
			m.put("merchantlat", jxPublish.getPub_latitude());//商家纬度
			list.add(m);
			
			return new SecretResult(Errors.OK, list);
		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	/**
	 * 获得咨询量
	 * @param lh
	 * @return
	 */
	@ResponseBody
	@RequestMapping({ "/userwappush/inquiries" })
	public Result Inquiries(HttpServletRequest request) {
		try {
			System.out.println("---咨询量开始---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String pubId = jsonObject.getString("pubid");
			/*String answer = jsonObject.getString("answer");
			if(answer == null || answer == ""){
				System.out.println("响应不存在");
			}else{
				System.out.println("存在响应");*/
				//拿到访问量
				int sum = merchantPublish.findInquiries(pubId);
				System.out.println("sum:"+sum);
				int inquiries = sum + 1;
				System.out.println("咨询量:"+inquiries);
				Jxpublish publish = merchantPublish.findUnique("from Jxpublish where pub_id = ?", Long.parseLong(pubId));
				publish.setPub_inquiries(inquiries);
				merchantPublish.save(publish);
			//}
			List<Map<String,Object>> list=this.merchantPublish.findInquiries(Integer.parseInt(pubId));
			
			return new SecretResult(Errors.OK, list);
		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	

	/*
	 * 删除服务
	 * 
	 */
	@ResponseBody
	@RequestMapping({ "/userwappush/deletePublish" })
	public Result deletePublish(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
		
			String pubId = jsonObject.getString("pubId");
			int a=merchantPublish.deletePublishById(Integer.parseInt(pubId));
			if(a>0)
			return new Result(Errors.OK);
			else{
				return new Result(Errors.PARAM_ERROR);
			}
		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	/**
	 * 社区排行榜
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping({ "/userwappush/publishRanking" })
	public Result PublishRanking(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
		
			String id = jsonObject.getString("categoryid");
			
			List<Map<String,Object>> list = merchantPublish.findRanking();
			
			return new Result(Errors.PARAM_ERROR);
			
		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
}
