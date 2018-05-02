package com.game.smvc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.text.DateFormat;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.bmanager.entity.JxPrototal;
import com.game.bmanager.service.IJxPictureService;
import com.game.bmanager.service.IJxPrototalService;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxOrderItem;
import com.game.smvc.entity.JxPay;
import com.game.smvc.entity.JxProduct;
import com.game.smvc.entity.JxShoppingCart;
import com.game.smvc.entity.JxStatistical;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.entity.result.SingleDataResult;
import com.game.smvc.service.IJxMessageService;
import com.game.smvc.service.IJxOrderItemService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPayWayService;
import com.game.smvc.service.IJxProductService;
import com.game.smvc.service.IJxShoppingCartService;
import com.game.smvc.service.IJxStatisticalService;
import com.game.smvc.service.IJxWaterService;
import com.game.smvc.service.impl.JxProductSeviceImpl;
import com.game.smvc.util.HttpUtil;
import com.game.smvc.util.RandomUtil;
import com.game.util.pay.AliSignUtils;

@Controller
@RequestMapping({ "/smvc" })
public class ProductController {
	@Autowired
	IJxProductService productService;
	@Autowired
	IJxOrderService jxOrderService;
	@Autowired
	private IJxMessageService messageService;
	@Autowired
	private IJxPayWayService payWayService;
	@Autowired
	private IJxStatisticalService jxStatisticalService;
	@Autowired
	private IJxOrderItemService jxOrderItemServic;
	@Autowired
	private IJxPrototalService prototalService;
	@Autowired
	private IJxPictureService pictureService;
	@Autowired
	private IJxShoppingCartService jxShoppingCartService;
	@Autowired
	private IJxWaterService jxWaterService;
	
	/**
	 * 商品详情
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setup/productdetail")
	public Result productdetail(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String id = jsonObject.getString("id");
			List<Map<String, List<Map<String, Object>>>> list = this.productService
					.findProductById(Integer.parseInt(id));
			return new SecretResult(Errors.OK, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}
	
	
	/*
	 * 续费订单
	 * 
	 * @return
	 */
	/**
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/order/renewalsOrder")
	public Result renewalsOrder(HttpServletRequest request) {
		try {
			System.out.println("---开始续费---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String ord_no = jsonObject.getString("ord_no");
			String paytype = jsonObject.getString("paytype");//支付类型
			JxOrder jo = this.jxOrderService.findUnique(
					"from jx_order where ord_no=?", ord_no);
			String sjs = RandomUtil.getRandom();
			jo.setOrd_no(sjs);
			jo.setFim_ord_no(sjs);
			jo.setOrd_addtime(new Date());
			jo.setOrd_protypeid(Integer.valueOf(paytype));
			jo.setOrd_price(Float.parseFloat(jsonObject.getString("price")));
			jo.setOrd_priceper(Float.parseFloat(jsonObject.getString("price")));
			jo.setOrd_ordertype(1);
			jo.setOrd_id(null);
			jo.setOrd_status(0);
			//押金  倍数1，
			jo.setOrd_pledge(0);
			jo.setOrd_multiple(1);
			JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?", Long.valueOf(jo.getPro_id()),Long.valueOf(paytype));
			if(Integer.valueOf(paytype) == 0){
				jo.setPro_day(365);
				jo.setPro_restflow("0");
			}else{
				jo.setPro_restflow(jxPay.getPay_flow()+"");
				jo.setPro_day(0);
			}
			JxOrder josave = this.jxOrderService.save(jo);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("price", josave.getOrd_price().toString());
			map1.put("ord_no", josave.getOrd_no());
			// 插入产品名字
			map1.put("context", jsonObject.getString("proname"));
			map1.put("paytype", josave.getOrd_protypeid());
			list.add(map1);
			System.out.println("---续费订单---"+list);
			return new SecretResult(Errors.OK, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}

	/**
	 * 查看以前续费订单详情
	 * 
	 * @param request
	 * @param orderNum
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/order/ordeAgainDetail")
	public Result orderDetail(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String ono = jsonObject.getString("ord_no");
			String productId=jsonObject.getString("productId");
			List<Map<String, List<Map<String, Object>>>> list=this.jxOrderService.findAgainOrderDetailByOno(ono,productId);
			return new SecretResult(Errors.OK, list);
		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	
	
	/*
	 * 删除订单
	 */
	@ResponseBody
	@RequestMapping(value = "/order/deleteorder")
	public Result deleteOrder(HttpServletRequest request) {

		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String id = jsonObject.getString("ord_no");
			Boolean b = this.jxOrderService.deleteProductByordNo(id);
			if (b)
				return new Result(Errors.OK);
			return new Result(Errors.PARAM_ERROR);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/*
	 * 查看包年还是流量
	 */
	@ResponseBody
	@RequestMapping(value = "/product/payType")
	public Result payType(HttpServletRequest request) {

		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String pro_no = jsonObject.getString("pro_no");
			String name = this.jxOrderService.findpayWayByProNo(pro_no);
			return new SingleDataResult(Errors.OK, name);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	
	
	/*
	 * 查看净水器服务详情
	 */
	@ResponseBody
	@RequestMapping(value = "/product/myproductServiceDetail")
	public Result myproductServiceDetail(HttpServletRequest request) {

		try {
			System.out.println("111");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String pro_no = jsonObject.getString("pro_no");
			Object user = jsonObject.get("userid");
			if(user==null||user==""){
				List<Map<String, Object>> list1 = jxOrderService.findServiceDetailByProNo(pro_no);
				System.out.println(list1.size());
				if(list1.size()<=0){
					return new Result(Errors.NO_COST_INFORMATION_ON_THE_TRAFFIC);
				}
				Map<String,Object> map = list1.get(0);
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>(); 
				Map<String, Object> m = new HashMap<String, Object>();
				Long productId = (Long) map.get("productId");
				System.out.println("productId--->"+productId);
				String ord_no = (String) map.get("ord_no");
				String name = (String) map.get("name");
				String phone = (String) map.get("phone");
				String prono = (String) map.get("pro_no");
				String ord_price = (String) map.get("ord_price");
				String pro_addtime = (String) map.get("pro_addtime");
				String pro_invalidtime = (String) map.get("pro_invalidtime");
				Long type = (Long) map.get("type");
				System.out.println("type--->"+type);
				String sharetype = (String) map.get("sharetype");
				String pro_name = (String) map.get("pro_name");
				String ord_color = (String) map.get("ord_color");
				if(type == 0){
					SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
					Calendar calendar = Calendar.getInstance();
					map.put("quantity", calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
					calendar.add(Calendar.YEAR,1);
					map.put("now", sdf.format(new Date()));
					Date date = calendar.getTime();
					map.put("end", sdf.format(date));
				}else{
					//剩余流量
					Float restflow =(Float) map.get("restflow");
					if(restflow == null){
						System.out.println("---restflow为null的时候---");
						restflow = (float) 0;
					}else if(restflow>20){
						restflow = (float) 20;
					}
					System.out.println("剩余流量---->"+restflow);
					//从pay_flow 取出总流量，pay表
					JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?",productId,type); 
					//总流量
					Float flow = jxPay.getPay_flow();
					System.out.println("总流量--->"+flow);
					//使用流量
					Float pro_hasflow = flow - restflow;
					System.out.println("使用流量---》"+pro_hasflow);
					m.put("restflow", restflow);//剩余流量
					m.put("pro_hasflow", pro_hasflow);//使用流量
					m.put("flow", flow);//总流量
				}
				m.put("productId", productId);
				m.put("ord_no", ord_no);
				m.put("name", name);
				m.put("phone", phone);
				m.put("pro_no", prono);
				m.put("ord_price", ord_price);
				m.put("pro_addtime", pro_addtime);
				m.put("pro_invalidtime", pro_invalidtime);
				m.put("type", type);
				m.put("sharetype", sharetype);
				m.put("pro_name", pro_name);
				m.put("ord_color", ord_color);
				list.add(m);
				System.out.println("list--->"+list);
				return new SecretResult(Errors.OK, list);
			}
			List<Map<String, Object>> list = jxOrderService.findServiceDetailByProNo(pro_no,(String)user);
			return new SecretResult(Errors.OK, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
					
	}


	/*
	 * 查看消息列表
	 */
	@ResponseBody
	@RequestMapping(value = "/product/queryAllMess")
	public Result queryAllMess(HttpServletRequest request) {

		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String uid = jsonObject.getString("userid");
			String page=jsonObject.getString("page");
			List<Map<String, Object>> list = jxOrderService.queryAllMess(uid,page);
			return new SecretResult(Errors.OK, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}

	/*
	 * 修改根据Id修改消息状态
	 */
	@ResponseBody
	@RequestMapping(value = "/product/alterMessStatus")
	public Result alterMessStatus(HttpServletRequest request) {

		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String id = jsonObject.getString("id");

			int a = this.jxOrderService.updateMessageStatusById(id);
			if (a > 0) {
				return new Result(Errors.OK);
			} else {
				return new Result(Errors.JSON_ERROR_NOTJSON);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}
	
	/*
	 * 修改根据Id删除消息
	 */
	@ResponseBody
	@RequestMapping(value = "/message/deleteMessage")
	public Result deleteMessage(HttpServletRequest request) {

		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String id = jsonObject.getString("id");

			int a = this.messageService.deleteMessageById(id);
			if (a > 0) {
				return new Result(Errors.OK);
			} else {
				return new Result(Errors.JSON_ERROR_NOTJSON);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}
	
	
	
	/*
	 * 统计未读条数
	 */
	@ResponseBody
	@RequestMapping(value = "/message/queryMessages")
	public Result queryMessages(HttpServletRequest request) {

		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String userid = jsonObject.getString("userid");

			int a = this.messageService.queryMessagestotal(userid);
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("number", a);
			list.add(map);
			
			return new SecretResult(Errors.OK,list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}
	
	
	/**
	 * 新功能
	 * 
	 */
	//查看产品参数productParameters
	@ResponseBody
	@RequestMapping(value = "/setup/productparameters")
	public Result productdetails(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String id = jsonObject.getString("id");
			List<Map<String, Object>> list = this.productService.selectParametersById(Integer.parseInt(id));
			return new SecretResult(Errors.OK, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	//商品信息(点击商品跳转的功能)commodityInformation
	@ResponseBody
	@RequestMapping(value = "/setup/commodityInformation")
	public Result commodityInformation(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String id = jsonObject.getString("id");
			List<Map<String, List<Map<String, Object>>>> list = this.productService.findCommodityById(Integer.parseInt(id));
			return new SecretResult(Errors.OK, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	//商品属性
	@ResponseBody
	@RequestMapping(value = "/setup/attribute")
	public Result attribute(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String uid = jsonObject.getString("userid");//用户id
			String proid = jsonObject.getString("proid");//商品id
			String type = jsonObject.getString("type");//交易类型
			String name = jsonObject.getString("typename");//产品类型
			String number = jsonObject.getString("number");//数量
			String ppdnum = jsonObject.getString("ppdnum");//倍数
			//流量
			List<Map<String, Object>> pe =  payWayService.findPrice(proid,type);
			if(pe.size() <= 0 ){
				return new Result(Errors.NO_PRICE);
			}
			Map<String, Object> m1 = pe.get(0);
			String pric = (String) m1.get("price");
			Float price = Float.parseFloat(pric);
			Float n = Float.parseFloat(number);
			Float p = Float.parseFloat(ppdnum);
			
			Float prices = (price * n * p);//价格
			Float totalPrice = prices;
			String url = jsonObject.getString("url");//图片地址
			String color = jsonObject.getString("color");//颜色
			List<Map<String, Object>> jxPrototal = prototalService.findparam(proid);
			Map<String, Object> m = jxPrototal.get(0);
			String names = (String) m.get("name");
			String typename = (String) m.get("typename");
			String wx = (String) m.get("wx");
			Integer pledge = (Integer) m.get("pledge");
			if(p == 3){
				System.out.println("包3年");
				pledge = 0;
			}
			Float unitprice = (Float) m.get("unitprice");
			Float s = totalPrice + (pledge * n);
			
			String s1 = System.nanoTime()+"";
			JxShoppingCart cart = new JxShoppingCart();
			cart.setPro_id(Integer.parseInt(proid));
			cart.setPro_multiple(Integer.parseInt(ppdnum));
			cart.setSc_number(Integer.parseInt(number));
			cart.setSc_imgurl(url);
			cart.setSc_model(typename);
			cart.setSc_color(color);
			cart.setSc_name(names);
			cart.setU_id(Long.parseLong(uid));
			cart.setSc_state(0);
			cart.setSc_type(Integer.parseInt(type));
			cart.setSc_weight(wx);
			cart.setSc_price(Float.parseFloat(price.toString()));
			cart.setSc_tag(s1);//标识码
			jxShoppingCartService.save(cart);
			Map<String,Object> m0 = jxShoppingCartService.findId(s1);
			int id = (Integer) m0.get("sc_id");
			
			Map<String,Object> map = new HashMap<String, Object>();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			if("0".equals(type)){
				String years = "包年购买:"+" "+ppdnum+"年";
				map.put("yearsorflow", years);
				map.put("price", price);
			}else{
				int prepaidTraffic = (int) ((price* p)/unitprice);
				System.out.println("流量:"+prepaidTraffic);
				String years = "流量预付:"+" "+prepaidTraffic+"升"+"("+price+"*"+p+"/"+unitprice+"="+prepaidTraffic+"升"+")";
				map.put("yearsorflow", years);
				map.put("price", unitprice);
			}
			map.put("userid",uid);
			map.put("proid",proid);
			map.put("type", type);
			map.put("ppdnum", ppdnum);//倍数
			map.put("number", number);//数量
			map.put("url", url);
			map.put("color", color);
			map.put("name", names);
			map.put("model", typename);//类型
			map.put("weight", wx);
			map.put("pledge", (pledge * n));
			map.put("totalPrice", s);
			map.put("sc_id", id);
			list.add(map);
			return new SecretResult(Errors.OK, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	
	
	//立即购买
	@ResponseBody
	@RequestMapping(value = "/setup/buy")
	public Result buy(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			//String id = jsonObject.getString("id");
			String color = jsonObject.getString("color");//产品颜色
			String type = jsonObject.getString("type");//产品类型
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Map<String, Object> m = new HashMap<String, Object>();
			Map<String, Object> m1 = new HashMap<String, Object>();
			m.put("color", color);
			m1.put("type", type);
			list.add(m);
			list.add(m1);
			//List<Map<String, List<Map<String, Object>>>> list = this.productService.findCommodityById(Integer.parseInt(id));
			return new SecretResult(Errors.OK, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	

	
	/**
	 * 获取今日饮水量接口
	 * 2017/07/13
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setup/waterQuantity")
	public Result waterQuantity(HttpServletRequest request) {
		try {
			System.out.println("---开始获取今日饮水量---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String uid = jsonObject.getString("userid");
			String c = jsonObject.getString("cityCode");
			String cityCode = c.replace("市", "");
			
			//判断用户是否绑定水机、
			List<Map<String,Object>> jsj = jxOrderService.findState(uid);
			if(jsj.size() < 0){
				return new Result(Errors.NO_STATE);
			}else{
			
			List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();//总饮水量
			Map<String,Object> map1 = new HashMap<String, Object>();//tds信息
			Map<String,Object> map2 = new HashMap<String, Object>();//水质指数
			Map<String,Object> map3 = new HashMap<String, Object>();//当前城市
			Map<String,Object> map4 = new HashMap<String, Object>();//当前城市的水质指数
			Map<String,Object> map5 = new HashMap<String, Object>();//当前城市的tds
			
			Map<String,List<Map<String,Object>>> list = new HashMap<String, List<Map<String,Object>>>();
			list.put("water_quality", list1);
			list.put("current_exponent", list2);
			
			if(cityCode == null || cityCode.equals("")){
				System.out.println("无城市信息");
				map3.put("current_exponent", "当前城市:～");
				map4.put("current_exponent", "TDS:"+"～");
				map5.put("current_exponent", "水质指数:～");
				
			}else{
				System.out.println("有城市信息");
				Map<String,Object> codetds = jxWaterService.findCodeTds(cityCode);//当前城市的tds
				int currenttds = (Integer) codetds.get("water_tds");
				map4.put("current_exponent", "TDS:"+currenttds);
				map3.put("current_exponent", "当前城市:"+c);
				
				if(currenttds<=50){
					map5.put("current_exponent", "水质指数:优秀");
				}else if(currenttds>50&&currenttds<101){
					map5.put("current_exponent", "水质指数:良好");
				}else if(currenttds>100&&currenttds<301){
					map5.put("current_exponent", "水质指数:一般");
				}else if(currenttds>300&&currenttds<601){
					map5.put("current_exponent", "水质指数:较差");
				}else if(currenttds>600&&currenttds<1001){
					map5.put("current_exponent", "水质指数:差");
				}else if(currenttds>1000){
					map5.put("current_exponent", "水质指数:不合格");
				}
				
			}
			
			Float sum = 0f;
			for(int i =0; i<jsj.size();i++){
				Map<String,Object> m = jsj.get(i);
				Long proid = (Long) m.get("pro_id");
				Long protypeid = (Long) m.get("ord_protypeid");
				JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?", proid,protypeid);
				Float s = jxPay.getPay_flow();
				if(s == null){
					
				}else{
					sum+=s;
				}
			}
			
			
			//根据uid查到总剩余饮水量
			int num = jxOrderService.findwater(uid);
			//查到所以的条数
			int number = jxOrderService.findnumber(uid);
			//得到总倍数
			int ppdnum = jxOrderService.findppdnum(uid);
			System.out.println("总剩余饮水量:"+num);
			System.out.println("总条数:"+number);
			Float water_quality = sum - num;
			System.out.println("water_quality:"+water_quality);
			
			System.out.println("总流量11："+sum);
			//得到总水量
			System.out.println("111");
			//JxPay jxPay = payWayService.findflow();
			Map<String,Object> jxPay = payWayService.findflow();
			System.out.println("222");
			//获取系统当前时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		    String time = df.format(new Date());
		    System.out.println("当前时间:"+time);
		    //获取更新时间
		    System.out.println("333");
		    List<Map<String,Object>> modtime = jxOrderService.findtime(uid);
			
		    if(modtime.size()<=0){
				return new Result(Errors.NO_MODTIME);
			}
			Map<String, Object> t = modtime.get(0);
			Object mt= t.get("ord_modtime");
			String modtimes = String.valueOf(mt);
			System.out.println(modtimes);
		    System.out.println("444");
		    if(modtimes.contains(time)){
		    	System.out.println("---开始查询总剩余饮水量---");
		    	//根据uid查到总剩余饮水量
				/*int num = jxOrderService.findwater(uid);
				//查到所以的条数
				int number = jxOrderService.findnumber(uid);
				//得到总倍数
				int ppdnum = jxOrderService.findppdnum(uid);
				System.out.println("总剩余饮水量:"+num);
				System.out.println("总条数:"+number);
				Float flow = (Float) jxPay.get("pay_flow");
				Float flows = flow * ppdnum * number;
				System.out.println("总饮水量"+flows);	
				Float water_quality = sum - num;*/
				System.out.println("water_quality:"+water_quality);
				//判断包年还是包流量
				int yearsOrflow = jxOrderService.findYearsOrFlow(uid);
				System.out.println("类型:"+yearsOrflow);
				if(yearsOrflow == 0){
					System.out.println("包年");
					Map<String,Object> sta_addtime = jxStatisticalService.findStaAddtime(uid);
					Map<String,Object> sta_modtime = jxStatisticalService.findStaModtime(uid);
					Object sta_addtime1 = sta_addtime.get("sta_addtime");
					Object sta_modtime1 = sta_modtime.get("sta_modtime");
					String sta_addtime2 = String.valueOf(sta_addtime1);
					String sta_modtime2 = String.valueOf(sta_modtime1);
					if(sta_addtime2.contains(time)){
						int output_water = jxStatisticalService.findAWater(uid);
						map.put("water_quality", "今日饮水量:"+output_water+"ML");
					}
					if(sta_modtime2.contains(time)){
						int output_water = jxStatisticalService.findMWater(uid);
						map.put("water_quality", "今日饮水量:"+output_water+"ML");
					}
				}else{
					System.out.println("包流量");
					map.put("water_quality", "今日饮水量:"+water_quality+"ML");
				}
				System.out.println("饮水量数据结束");
		    }else{
		    	map.put("water_quality", "今日饮水量:"+0+"ML");
		    	//return new Result(Errors.WATER_QUANTITY_DATA_NO_UPDATE);
		    }
			System.out.println("---开始获取tds信息---");
			//判断是否有数据
			List<Map<String,Object>> js = jxStatisticalService.findNull(uid);
			if(js == null || js.size()<= 0){
				map1.put("water_quality","TDS:"+0);
				map2.put("water_quality", "水质指数:～");
			}else{
			
				//根据uid去查询添加时间
				List<Map<String,Object>> times = jxStatisticalService.findTime(uid);
				if(times.size()<=0){
					return new Result(Errors.NO_TIME_INFORMATION);
				}
				//根据uid去查询更新时间
				List<Map<String,Object>> timess = jxStatisticalService.findTimes(uid);
				if(timess.size()<=0){
					return new Result(Errors.NO_TIME_INFORMATION);
				}
				Map<String, Object> atime = times.get(0);
				Map<String, Object> mtime = timess.get(0);
				
				Object sta_addtime = atime.get("sta_addtime");
				Object sta_modtime = mtime .get("sta_modtime");
				String staaddtime = String.valueOf(sta_addtime);
				String stamodtime = String.valueOf(sta_modtime);
				System.out.println("addtimes:"+staaddtime);
				System.out.println("modtimes:"+stamodtime);
				//时间匹配
				if(stamodtime.contains(time)){
					//根据uid和更新时间查到tds信息
					System.out.println("---匹配到更新时间---");
					
					List<Map<String,Object>> modtds = jxStatisticalService.findModTds(uid,time);
					if(modtds.size()<=0){
						return new Result(Errors.NO_TDS_INFORMATION);
					}
					Map<String, Object> mtds = modtds.get(0);
					Integer tds = (Integer) mtds.get("sta_tds");
					System.out.println("modtds:"+tds);
					System.out.println("---获取tds结束---");
					if(tds<=50){
						map2.put("water_quality", "水质指数:优秀");
					}else if(tds>50&&tds<101){
						map2.put("water_quality", "水质指数:良好");
					}else if(tds>100&&tds<301){
						map2.put("water_quality", "水质指数:一般");
					}else if(tds>300&&tds<601){
						map2.put("water_quality", "水质指数:较差");
					}else if(tds>600&&tds<1001){
						map2.put("water_quality", "水质指数:差");
					}else if(tds>1000){
						map2.put("water_quality", "水质指数:不合格");
					}
					System.out.println("999");
					map1.put("water_quality","TDS:"+tds);
					
				}else if(staaddtime.contains(time)){
					//根据uid和更新时间查到tds信息
					System.out.println("---匹配到添加时间---");
					List<Map<String,Object>> modtds = jxStatisticalService.findAddTds(uid,time);
					if(modtds.size()<=0){
						return new Result(Errors.NO_TDS_INFORMATION);
					}
					Map<String, Object> mtds = modtds.get(0);
					Integer tds = (Integer) mtds.get("sta_tds");
					System.out.println("modtds:"+tds);
					System.out.println("---获取tds结束---");
					if(tds<=50){
						map2.put("water_quality", "水质指数:优秀");
					}else if(tds>50&&tds<101){
						map2.put("water_quality", "水质指数:良好");
					}else if(tds>100&&tds<301){
						map2.put("water_quality", "水质指数:一般");
					}else if(tds>300&&tds<601){
						map2.put("water_quality", "水质指数:较差");
					}else if(tds>600&&tds<1001){
						map2.put("water_quality", "水质指数:差");
					}else if(tds>1000){
						map2.put("water_quality", "水质指数:不合格");
					}
					System.out.println("999");
					map1.put("water_quality","TDS:"+tds);
				}else{
					//都没有匹配上
					map1.put("water_quality","TDS:"+0);
					map2.put("water_quality", "水质指数:～");
					//return new Result(Errors.NO_TDS);
				}
			
			}
			
			list1.add(map);
			list1.add(map1);
			list1.add(map2);
			list2.add(map3);
			list2.add(map4);
			list2.add(map5);
			System.out.println("list--->"+list);
			return new SecretResult(Errors.OK, list);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	

		
		
		/**
		 * 下订单--->修改版2017/07/07
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/order/addorder")
		public Result addOrder(HttpServletRequest request) {
			try {
				System.out.println("---开始添加订单---");
				String authCode = HttpUtil.getRquestParamsByIO(request);
				JSONObject jsonObject = JSONObject.fromObject(authCode);
				String id= jsonObject.getString("id");//购物车的id
				String managerNo = jsonObject.getString("managerNo");//产品经理编号
				String fimOrderNo = RandomUtil.getRandom();//生成父订单号
				Boolean b = this.jxOrderService.queryPaternerByManagerNo(managerNo);
				if (!b) {
					return new Result(Errors.PARTNER_ERROR_NOTFOUND);
				}
				//安装时间校验
				String s2 = jsonObject.getString("settime");
				SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy/MM/dd HH:mm");
				  Date date = simpleDateFormat.parse(s2);
				  long l = date.getTime();
				  Date d=new Date();
				  long long1 = d.getTime();
				if(long1>l){
					return new Result(Errors.USER_ERROR_SETTIME);
				}
				
				Map<String, Object> map = this.jxOrderService.findAddressById(Long
						.parseLong(jsonObject.getString("adrid")));//地址id
				
				
				JxOrderItem item = new JxOrderItem();
				//根据购物车id，得到商品项
				int sum = jxShoppingCartService.findNumbers(id);
				List<Map<String,Object>> allPrpduct = jxShoppingCartService.selectAllProduct(id);
				if(allPrpduct.size() <= 0){
					return new Result(Errors.COMMODITY_INFORMATION_ERROR);
				}
				Map<String,Object> ll = allPrpduct.get(0);
				String tag = (String) ll.get("tag");
				int state = (Integer) ll.get("state");
				if(tag == null && state == 0){
					return new Result(Errors.EXCEPTION_UNKNOW);
				}else{
					
					Map<String,Object> m2 = allPrpduct.get(0);
					Long userid1 = (Long) m2.get("userid");//用户id
					int proid1 = (Integer) m2.get("proid");//商品id
					Float dprice1 = (Float) m2.get("price");//商品单价
					String proname1 = (String) m2.get("name");//商品名字
					String color1 = (String) m2.get("color");//商品颜色
					int number1 = (Integer) m2.get("number");//购买数量
					int ppdnum1 = (Integer) m2.get("ppdnum");//商品倍数
					String url1 = (String) m2.get("url");//商品图片地址
					int type1 = (Integer) m2.get("type");//包年/包流量
					List<Map<String, Object>> jxPrototal1 = prototalService.findparam(String.valueOf(proid1));
					Map<String, Object> m0 = jxPrototal1.get(0);
					Integer pledge1 = (Integer) m0.get("pledge");//押金
					if(ppdnum1 == 3){
						System.out.println("包3年");
						pledge1 = 0;
					}
					Float zprice1 = (dprice1*number1*ppdnum1)+(pledge1*number1);//得到总价格
					if(allPrpduct.size() == 1 && number1 == 1){
						/*Float zprice1 = null;
						if(ppdnum1 == 3){
							zprice1 = (dprice1*number1*ppdnum1);
						}else{
							zprice1 = (dprice1*number1*ppdnum1)+(pledge1*number1);
						}*/
						System.out.println("---单个订单---");
						Float unitPrice = zprice1/number1;//单个订单的价格
						System.out.println("单个订单的金额:"+unitPrice);
						System.out.println("multiplePrice:"+dprice1);
						JxOrder jo = new JxOrder();
						jo.setOrd_way(0);
						jo.setOrd_protypeid(type1);
						jo.setU_id(userid1);
						jo.setAdr_id((String) map.get("address"));
						jo.setOrd_phone((String) map.get("phone"));
						jo.setOrd_receivename((String) map.get("name"));
						jo.setPro_id(proid1);
						jo.setOrd_managerno(managerNo);
						jo.setOrd_ordertype(0);
						jo.setOrd_sertime(jsonObject.getString("settime"));
						jo.setOrd_price(unitPrice);//单个订单的总价
						jo.setOrd_pledge(pledge1);//押金
						jo.setOrd_priceper(dprice1);//单价
						jo.setOrd_multiple(ppdnum1);//倍数
						jo.setOrd_no(fimOrderNo);//子订单号
						jo.setFim_ord_no(fimOrderNo);//父订单号
						jo.setOrd_addtime(new Date());
						jo.setOrd_color(color1);
						jo.setOrd_status(0);
						jo.setOrd_imgurl(url1);
						jo.setOrd_proname(proname1);
			  	        jxOrderService.save(jo);
			  	        
			  	  	System.out.println("开始保存父订单号");
					//保存父订单到订单表
					Map<String,Object> m3 = jxOrderService.findTotalPrices(fimOrderNo);
					Double f = (Double) m3.get("price");
					Float totalPrice = Float.parseFloat(f.toString());
					item.setOrditem_no(fimOrderNo);
					item.setOrder_price(totalPrice);
					item.setAdr_id((String) map.get("address"));
					item.setPh_no((String) map.get("phone"));
					item.setOrditem_receivename((String) map.get("name"));
					item.setU_id(userid1);
					item.setOrditem_protypeid(type1);
					System.out.println("设置时间");
					item.setOrd_number(sum);//设置总数量
					item.setOrditem_addtime(new Date());
					item.setOrditem_status(0);
					item.setOrditem_way(0);
					item.setOrditem_ordertype(0);
					item.setOdritem_managerno(managerNo);
					item.setOrditem_sertime(jsonObject.getString("settime"));
					item.setOrd_proname(proname1);
		  	        JxOrderItem josave = jxOrderItemServic.save(item);
		  	        System.out.println("4");
		  	        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("price", josave.getOrder_price().toString());//总价
					map1.put("ord_no", josave.getOrditem_no());//子订单
					map1.put("context", josave.getOrd_proname());
					map1.put("paytype", josave.getOrditem_protypeid());
					map1.put("tag", "1");//标记
					list.add(map1);
		  	        System.out.println("list0:"+list);
		  	        System.out.println("---开始清除购物车订单信息---");
		  	        int del = jxShoppingCartService.delProduct(id);
		  	        return new SecretResult(Errors.OK, list);
					}else{
						
						System.out.println("---多个订单---");
						System.out.println("---有商品---");
						for(int i =0 ;i < allPrpduct.size();i++){
						System.out.println("---开始进入循环---");
						Map<String,Object> p = allPrpduct.get(i);
						Long userid = (Long) p.get("userid");//用户id
						int proid = (Integer) p.get("proid");//商品id
						Float dprice = (Float) p.get("price");//商品单价
						String proname = (String) p.get("name");//商品名字
						String color = (String) p.get("color");//商品颜色
						int number = (Integer) p.get("number");//购买数量
						int ppdnum = (Integer) p.get("ppdnum");//商品倍数
						String url = (String) p.get("url");//商品图片地址
						int type = (Integer) p.get("type");//包年/包流量
						List<Map<String, Object>> jxPrototal = prototalService.findparam(String.valueOf(proid));
						Map<String, Object> m = jxPrototal.get(0);
						Integer pledge = (Integer) m.get("pledge");//押金
					//先保存到父订单，在保存到子订单
					//依据数量生成子订单号
					/**
					 * 多订单情况
					 */
						System.out.println("1");
						if(ppdnum == 3){
							System.out.println("包3年");
							pledge = 0;
						}
						Float zprice = (dprice*number*ppdnum)+(pledge*number);//得到总价格
						if(type == 0 || type == 1){
							System.out.println("2");
							for(int j= 0;j < number; j++){
								System.out.println("---多个订单---");
								Float unitPrice = zprice/number;//单个订单的价格
								System.out.println("单个订单的金额:"+unitPrice);
								System.out.println("multiplePrice:"+zprice);
								JxOrder jo = new JxOrder();
								SimpleDateFormat format1 = new SimpleDateFormat("yy",Locale.getDefault());
					    		Date date1 = new Date();
					  	        String key1 = format1.format(date1);
					  	        String s1 = key1+System.nanoTime();
				    		    s1 = s1.substring(0, 15);
					  	        String ordItemNo = s1;
					  	        System.out.println("子订单:"+ordItemNo);
					  	     
								jo.setOrd_way(0);
								jo.setOrd_protypeid(type);
								jo.setU_id(userid);
								jo.setAdr_id((String) map.get("address"));
								jo.setOrd_phone((String) map.get("phone"));
								jo.setOrd_receivename((String) map.get("name"));
								jo.setPro_id(proid);
								jo.setOrd_managerno(managerNo);
								jo.setOrd_ordertype(0);
								jo.setOrd_sertime(jsonObject.getString("settime"));
								jo.setOrd_price(unitPrice);//单个订单的总价
								jo.setOrd_pledge(pledge);//押金
								jo.setOrd_priceper(dprice);//单价
								jo.setOrd_multiple(ppdnum);//倍数
								jo.setOrd_no(ordItemNo);//子订单号
								jo.setFim_ord_no(fimOrderNo);//父订单号
								jo.setOrd_addtime(new Date());
								jo.setOrd_color(color);
								jo.setOrd_status(0);
								jo.setOrd_imgurl(url);
								jo.setOrd_proname(proname);
					  	        jxOrderService.save(jo);
					  	      System.out.println("3");
							}
						}
						System.out.println("开始保存父订单号");
						//保存父订单到订单表
						//int  totalPrice = jxOrderService.findTotalPrice(fimOrderNo);
						Map<String,Object> m1 = jxOrderService.findTotalPrices(fimOrderNo);
						Double f = (Double) m1.get("price");
						Float totalPrice = Float.parseFloat(f.toString());
						//Float totalPrices = Float.parseFloat(totalPrice);
						item.setOrditem_no(fimOrderNo);
						item.setOrder_price(totalPrice);
						item.setAdr_id((String) map.get("address"));
						item.setPh_no((String) map.get("phone"));
						item.setOrditem_receivename((String) map.get("name"));
						item.setU_id(userid);
						item.setOrditem_protypeid(type);
					}	
					System.out.println("设置时间");
					item.setOrd_number(sum);//设置总数量
					item.setOrditem_addtime(new Date());
					item.setOrditem_status(0);
					item.setOrditem_way(0);
					item.setOrditem_ordertype(0);
					item.setOdritem_managerno(managerNo);
					item.setOrditem_sertime(jsonObject.getString("settime"));
					item.setOrd_proname("组合支付");
		  	        JxOrderItem josave = jxOrderItemServic.save(item);
		  	        System.out.println("4");
		  	        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("price", josave.getOrder_price().toString());//总价
					map1.put("ord_no", josave.getOrditem_no());//子订单
					map1.put("context", josave.getOrd_proname());
					map1.put("paytype", josave.getOrditem_protypeid());
					map1.put("tag", "0");//标记
					list.add(map1);
		  	        System.out.println("list0:"+list);
		  	        System.out.println("---开始清除购物车订单信息---");
		  	        int del = jxShoppingCartService.delProduct(id);
		  	        return new SecretResult(Errors.OK, list);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new Result(Errors.EXCEPTION_UNKNOW);
			}

		}
		
}
