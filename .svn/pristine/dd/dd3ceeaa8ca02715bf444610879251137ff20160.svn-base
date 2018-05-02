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

import com.game.bmanager.service.IJxMenuService;
import com.game.smvc.entity.JxCommunitySale;
import com.game.smvc.entity.JxReleaseOrder;
import com.game.smvc.entity.JxUser;
import com.game.smvc.entity.Jxpublish;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxCommunitySaleService;
import com.game.smvc.service.IJxMerchantPublishService;
import com.game.smvc.service.IJxUserService;
import com.game.smvc.service.JxReleaseOrderService;
import com.game.smvc.util.HttpUtil;
import com.game.smvc.util.RandomUtil;

@Controller
@RequestMapping({ "/smvc" })
public class ReleaseOrderController {

	@Autowired
	private IJxMerchantPublishService merchantPublish;
	@Autowired
	private JxReleaseOrderService jxReleaseOrderService;
	@Autowired
	private IJxMenuService menuService;
	@Autowired
	private IJxCommunitySaleService jxCommunitySaleService;
	@Autowired
	private IJxUserService jxUserService;
	
	/**
	 * 发布下单
	 * @param lh
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/release/addreleaseorder")
	public Result addReleaseOrder(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String uid = jsonObject.getString("userid");//发布人id
			String promoterid = jsonObject.getString("promoterid");//推广人id(自己填写的)
			//String price = jsonObject.getString("price");//发布金额
			String TypeName = jsonObject.getString("categoryid");//发布类型
			String phoneNum = jsonObject.getString("phone");//发布人电话
			String longitude = jsonObject.getString("longitude");//经度
			String latitude  = jsonObject.getString("latitude");//纬度
			int isPush = 1;//发布是否收费
			String ReleaseOrderNo = RandomUtil.getRandoms();
			
			if ((phoneNum == null) || ("".equals(phoneNum.trim()))) {
				return new Result(Errors.USER_ERROR_PHONE_FORMAT);
			}
			
			if(isPush == 0){
				System.out.println("---支付版发布---");
				Map<String,Object> map = menuService.findTypeNmae(TypeName);
				String classifyName = (String) map.get("menu_name");
				System.out.println("分类名字:"+classifyName);
				JxReleaseOrder jxReleaseOrder = new JxReleaseOrder();
				jxReleaseOrder.setU_id(Integer.parseInt(uid));
				jxReleaseOrder.setOrd_way(0);
				jxReleaseOrder.setFb_type(classifyName);
				jxReleaseOrder.setFb_phone(phoneNum);
				jxReleaseOrder.setOrd_no(ReleaseOrderNo);
				jxReleaseOrder.setFb_state(0);
				jxReleaseOrder.setFb_price(Float.parseFloat("300"));
				jxReleaseOrder.setFb_addtime(new Date());
				
				Jxpublish publish = new Jxpublish();
				publish.setOrd_no(ReleaseOrderNo);
				publish.setPub_address(jsonObject.getString("address"));
				publish.setPub_categoryid(Long.parseLong(TypeName));
				publish.setPub_content(jsonObject.getString("content"));
				publish.setPub_seller(jsonObject.getString("sellername"));
				publish.setPub_vaildtime(jsonObject.getString("begintime"));
				publish.setPub_invildtime(jsonObject.getString("endtime"));
				publish.setPh_no(phoneNum);
				publish.setU_id(uid);
				publish.setPub_other(promoterid);//推广人id
				publish.setPub_url(jsonObject.getString("imgUrl"));
				publish.setPub_name(jsonObject.getString("userName"));
				publish.setFb_state(1);
				publish.setPub_longitude(longitude);//经度
				publish.setPub_latitude(latitude);//纬度
				publish.setPub_addtime(new Date());
				Jxpublish jxpublish = this.merchantPublish.save(publish);
				JxReleaseOrder releaseOrder = this.jxReleaseOrderService.save(jxReleaseOrder);
				String ordno = jxpublish.getOrd_no();
				System.out.println("订单号:"+ordno);
				
				Map<String,Object> m = merchantPublish.findpubId(ordno);
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("ord_no", releaseOrder.getOrd_no());
				map1.put("price", releaseOrder.getFb_price());
				map1.put("type", releaseOrder.getFb_type());
				map1.put("seller", jxpublish.getPub_seller());
				map1.put("pubid", m.get("pub_id"));
				map1.put("isPush", "0");
				list.add(map1);
				return new SecretResult(Errors.OK, list);
				
			}else{
				System.out.println("---发布---");
				Map<String,Object> map = menuService.findTypeNmae(TypeName);
				String classifyName = (String) map.get("menu_name");
				System.out.println("分类名字:"+classifyName);
				JxReleaseOrder jxReleaseOrder = new JxReleaseOrder();
				jxReleaseOrder.setU_id(Integer.parseInt(uid));
				jxReleaseOrder.setOrd_way(0);
				jxReleaseOrder.setFb_type(classifyName);
				jxReleaseOrder.setFb_phone(phoneNum);
				jxReleaseOrder.setOrd_no(ReleaseOrderNo);
				jxReleaseOrder.setFb_state(1);
				jxReleaseOrder.setFb_price(Float.parseFloat("0"));
				jxReleaseOrder.setFb_addtime(new Date());
				
				Jxpublish publish = new Jxpublish();
				publish.setOrd_no(ReleaseOrderNo);
				publish.setPub_address(jsonObject.getString("address"));
				publish.setPub_categoryid(Long.parseLong(TypeName));
				publish.setPub_content(jsonObject.getString("content"));
				publish.setPub_seller(jsonObject.getString("sellername"));
				publish.setPub_vaildtime(jsonObject.getString("begintime"));
				publish.setPub_invildtime(jsonObject.getString("endtime"));
				publish.setPh_no(phoneNum);
				publish.setU_id(uid);
				publish.setPub_other(promoterid);//推广人id
				publish.setPub_url(jsonObject.getString("imgUrl"));
				publish.setPub_name(jsonObject.getString("userName"));
				publish.setFb_state(0);
				publish.setPub_longitude(longitude);//经度
				publish.setPub_latitude(latitude);//纬度
				publish.setPub_addtime(new Date());
				Jxpublish jxpublish = this.merchantPublish.save(publish);
				JxReleaseOrder releaseOrder = this.jxReleaseOrderService.save(jxReleaseOrder);
				String ordno = jxpublish.getOrd_no();
				System.out.println("订单号:"+ordno);
				
				Map<String,Object> m = merchantPublish.findpubId(ordno);
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("ord_no", releaseOrder.getOrd_no());
				map1.put("price", releaseOrder.getFb_price());
				map1.put("type", releaseOrder.getFb_type());
				map1.put("seller", jxpublish.getPub_seller());
				map1.put("pubid", m.get("pub_id"));
				map1.put("isPush", "1");
				list.add(map1);
				return new SecretResult(Errors.OK, list);
				
			}
			
			

		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	

		
		
		/**
		 * 根据推广人id去查找推广信息
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/release/AllPromoter")
		public Result AllPromoter(HttpServletRequest request) {
			try {
				String authCode = HttpUtil.getRquestParamsByIO(request);
				JSONObject jsonObject = JSONObject.fromObject(authCode);
				String uid = jsonObject.getString("userid");//推广码
				int page = Integer.parseInt(jsonObject.getString("page"));
				if ((uid == null) || ("".equals(uid.trim()))) {
					return new Result(Errors.USER_ERROR_NOT_EXIST);
				}

				//根据推广码，查询推广信息
				List<Map<String, Object>> list0 = jxReleaseOrderService.AllPromoters(
						uid, (page - 1) * 10);
				
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				
				for(int i = 0;i<list0.size();i++){
					Map<String, Object> m = list0.get(i);
					
					Date addtime = (Date) m.get("addtime");
					String time = String.valueOf(addtime);
					m.put("pubuserid", m.get("pubuserid"));
					m.put("seller", m.get("seller"));
					m.put("content", m.get("content"));
					m.put("address", m.get("address"));
					m.put("url", m.get("url"));
					m.put("pubid", m.get("pubid"));
					m.put("pub_addtime", time);
					list.add(m);
				}
				return new SecretResult(Errors.OK, list);
			} catch (JSONException e) {
				e.printStackTrace();
				return new Result(Errors.JSON_ERROR_NOTJSON);

			} catch (Exception e) {
				e.printStackTrace();
				return new Result(Errors.EXCEPTION_UNKNOW);
			}
		}
		
	
	//查询发布订单
	@ResponseBody
	@RequestMapping(value = "/release/myreleaseorders")
	public Result myReleaseOrders(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String uid = jsonObject.getString("uid");
			int page = Integer.parseInt(jsonObject.getString("page"));
			if ((uid == null) || ("".equals(uid.trim()))) {
				return new Result(Errors.USER_ERROR_PHONE_FORMAT);
			}

			List<Map<String, Object>> orders = jxReleaseOrderService.queryReleaseOrdersByuid(
					uid, (page - 1) * 10);
			
			return new SecretResult(Errors.OK, orders);
		} catch (JSONException e) {
			e.printStackTrace();
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	
	//删除发布订单
	@ResponseBody
	@RequestMapping(value = "/release/deletereleaseorder")
	public Result deleteReleaseOrder(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String ordno = jsonObject.getString("ord_no");
			int b = jxReleaseOrderService.deleteReleaseOrderByordNo(ordno);
			if(b == 0){
				System.out.println("删除失败");
				return new Result(Errors.DELETE_FAILED);
			}else{
				System.out.println("删除成功");
				return new Result(Errors.SUCCESSFULLY_DELETE);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	
	/**
	 * 举报商家
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/release/reportbusinessman")
	public Result ReportBusinessman(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String uid = jsonObject.getString("userid");//举报人id
			String pubid = jsonObject.getString("pubid");//被举报id
			String rptname = jsonObject.getString("rptname");//举报人
			String content = jsonObject.getString("content");//举报内容
			String cause = jsonObject.getString("cause");//举报原因
			String phone = jsonObject.getString("phone");//举报人电话
			
			//举报人信息
			JxUser jxUser = jxUserService.findUnique("from JxUser where u_id = '"+uid+"'");
			
			//被举报人信息
			Jxpublish jxpublish = merchantPublish.findPublish(pubid);
			
			JxCommunitySale communitySale = jxCommunitySaleService.findpubCommunity(uid,pubid,cause);
			int number = communitySale.getRpt_number();
			if(communitySale == null || communitySale.getId() == 0){
				System.out.println("---第一次举报、举报不相同---");
				//社区售后信息
				//JxCommunitySale jxCommunitySale = new JxCommunitySale();
				communitySale.setPub_id(Integer.parseInt(pubid));
				communitySale.setU_id(Integer.parseInt(uid));
				communitySale.setRpt_state(0);
				communitySale.setRpt_addtime(new Date());
				communitySale.setRpt_sex(jxUser.getU_sex());//举报人性别
				communitySale.setRpt_phone(phone);
				communitySale.setRpt_name(rptname);//举报人昵称
				communitySale.setRpt_content(content);//举报内容
				communitySale.setRpt_cause(cause);//举报原因
				communitySale.setRpd_company(jxpublish.getPub_content());//被举报商家
				communitySale.setRpd_phone(jxpublish.getPh_no());
				communitySale.setRpd_name(jxpublish.getPub_seller());
				communitySale.setRpt_number(1);
				jxCommunitySaleService.save(communitySale);
			}else{
				System.out.println("---同一人，同一内容，同一商家---");
				
				communitySale.setRpt_number(number+1);
				communitySale.setRpt_modtime(new Date());
				jxCommunitySaleService.save(communitySale);
			}
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
}
