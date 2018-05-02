package com.game.smvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.smvc.entity.JxMessages;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxOrderItem;
import com.game.smvc.entity.JxPay;
import com.game.smvc.entity.result.DataResult;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SingleDataResult;
import com.game.smvc.payUtil.AlipayNotify;
import com.game.smvc.service.IJxMessageService;
import com.game.smvc.service.IJxOrderItemService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPayWayService;
import com.game.smvc.service.IJxProductService;
import com.game.smvc.util.HttpUtil;
import com.game.util.pay.WXpay.ClientRequestHandler;
import com.game.util.pay.WXpay.PrepayIdRequestHandler;
import com.game.util.pay.WXpay.util.TenpayUtil;
import com.game.util.pay.WXpay.util.WXUtil;
import com.game.util.pay.WXpay.util.WxPayParamsUtil;
import com.game.util.pay.WXpay.util.XMLUtil;
import com.mysql.jdbc.log.Log;
import com.mysql.jdbc.log.LogFactory;

@SuppressWarnings("unused")
@Controller
@RequestMapping({ "/smvc" })
public class WxPayController {
	@Autowired
	IJxOrderService jxOrderService;
	@Autowired
	IJxProductService productService;
	@Autowired
	private IJxPayWayService payWayService;
	@Autowired
	private IJxMessageService messageService;
	@Autowired
	private IJxOrderItemService jxOrderItemServic;

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/pay/wxpaysign", method = RequestMethod.POST)
	public Result wxpaysign(HttpServletRequest request,HttpServletResponse response) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String out_trade_no = jsonObject.getString("ord_no");
			String context = jsonObject.getString("context");
			String price = jsonObject.getString("price");
			
			
			float  price1 =  (float)(Math.round(Float.valueOf(price)*10000))/100;
			int priceceten=(int)price1;
			
			if(priceceten==0){
				System.out.println("---进入测试的支付流程---");
				int isAgain = Integer.parseInt(jsonObject.getString("isAgain"));
				System.out.println("isAgain----->"+isAgain);
				if(isAgain == 0){
					System.out.println("---测试账号支付开始---");
					String tag = jsonObject.getString("tag");
					int tags = Integer.parseInt(tag);
					if(tags == 0){
						System.out.println("---父订单支付---");
						JxOrderItem jxOrderItem = jxOrderItemServic.findUnique("from JxOrderItem where orditem_no = ?", out_trade_no);
						System.out.println("---子订单1---");
						int item = jxOrderItemServic.upadtexwStatus(out_trade_no);
						System.out.println("---子订单2---");
						jxOrderItem.setOrditem_modtime(new Date());
						System.out.println("1");
						jxOrderItem.setOrditem_status(1);
						jxOrderItem.setOrditem_way(1);
						jxOrderItemServic.save(jxOrderItem);
						int time = jxOrderItemServic.updatetime(out_trade_no);
						String str="亲~您购买的"+jxOrderItem.getOrd_proname();
						str=str+" "+jxOrderItem.getOrder_price()+"元已经支付完成了";
						
						String title = "交易消息";
						JxMessages mess = PushController.Mssages(out_trade_no, title, str, jxOrderItem.getU_id());
						messageService.save(mess);
						String alias = String.valueOf(jxOrderItem.getU_id());
						PushController.YHIOSMssage(alias,title,str);
						return new Result(Errors.ERROR_CODE);
					}else{
						System.out.println("---子订单支付---");
						
						JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", out_trade_no);
						jxOrder.setOrd_modtime(new Date());
						jxOrder.setOrd_status(1);
						jxOrder.setOrd_way(1);
						jxOrderService.save(jxOrder);
						String str="亲~您购买的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
						String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
						str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
						
						System.out.println("---保存消息列表---");
						String title = "交易消息";
						JxMessages mess = PushController.Mssages(out_trade_no, title, str, jxOrder.getU_id());
						messageService.save(mess);
						String alias = String.valueOf(jxOrder.getU_id());
						PushController.YHIOSMssage(alias,title,str);
						return new Result(Errors.ERROR_CODE);
					}
				}else{
					JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", out_trade_no);
					jxOrder.setOrd_modtime(new Date());
					jxOrder.setOrd_status(4);
					jxOrder.setOrd_way(1);
					jxOrderService.save(jxOrder);
					String str="亲~您续费的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
					String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
					str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
					System.out.println("---保存消息列表---");
					String title = "交易消息";
					JxMessages mess = PushController.Mssages(out_trade_no, title, str, jxOrder.getU_id());
					messageService.save(mess);
					String alias = String.valueOf(jxOrder.getU_id());
					PushController.YHIOSMssage(alias,title,str);
					return new Result(Errors.ERROR_CODE);
				}
			}else{
				String tag = null;
				if(jsonObject.containsKey("tag")){
					tag = jsonObject.getString("tag");
					if(tag.equals("")){
						System.out.println("---续费---");
						JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=?", out_trade_no);
						out_trade_no =out_trade_no+(int)(Math.random()*9000+1000);
						jxOrder.setOrd_way(1);
						jxOrderService.save(jxOrder);
					}else{
						int tags = Integer.parseInt(tag);
						if(tags == 0){
							System.out.println("---进入真正父订单的支付流程---");
							JxOrderItem jxOrderItem = jxOrderItemServic.findUnique("from JxOrderItem where orditem_no = ?", out_trade_no);
							out_trade_no =out_trade_no+(int)(Math.random()*9000+1000);
							jxOrderItem.setOrditem_way(1);
							jxOrderItemServic.save(jxOrderItem);
						}else{
							System.out.println("---进入真正子订单的支付流程---");
							JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=?", out_trade_no);
							out_trade_no =out_trade_no+(int)(Math.random()*9000+1000);
							jxOrder.setOrd_way(1);
							jxOrderService.save(jxOrder);
						}
					}
				}else{
					System.out.println("---续费---");
					JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=?", out_trade_no);
					out_trade_no =out_trade_no+(int)(Math.random()*9000+1000);
					jxOrder.setOrd_way(1);
					jxOrderService.save(jxOrder);
				}
				
				String notifyUrl = null;
				int isAgain = Integer.parseInt(jsonObject.getString("isAgain"));
				// 回调接口url
				if (isAgain == 0) {
					if(Integer.parseInt(tag) == 0){
						notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/pay/wxpayResult.v";
					}else{
						notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/pays/wxpayResult.v";
					}
					
				} else {
					notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/pay/wxpayAgainResult.v";
				}
				PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);//锟斤拷取prepayid锟斤拷锟斤拷锟斤拷锟斤拷
				ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);//锟斤拷锟截客伙拷锟斤拷支锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
				
				String uuid = UUID.randomUUID().toString().replaceAll("\\-", "");
				String ip = request.getRemoteAddr();
				System.out.println(ip);
				prepayReqHandler.setParameter("appid", WxPayParamsUtil.APP_ID);
				prepayReqHandler.setParameter("mch_id", WxPayParamsUtil.PARTNER); 
				prepayReqHandler.setParameter("nonce_str", uuid); 
				prepayReqHandler.setParameter("body", context); 
				prepayReqHandler.setParameter("out_trade_no", out_trade_no); 
				prepayReqHandler.setParameter("total_fee",priceceten+"");   
				prepayReqHandler.setParameter("spbill_create_ip",request.getRemoteAddr()); //WXUtil.findRealIp(request)); 
				prepayReqHandler.setParameter("notify_url", notifyUrl);
				prepayReqHandler.setParameter("trade_type", "APP");
				String sign = prepayReqHandler.createSign();
	
					Map m=null;
					String prepayid=null;
					try {
						m = prepayReqHandler.sendPrepay();
						prepayid=(String)m.get("prepay_id");
					} catch (Exception e) {
					 e.printStackTrace();
					}
					List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
					if (null != prepayid && !"".equals(prepayid)) {
						
						Map<String,Object> map=new TreeMap<String, Object>();
						map.put("appid", WxPayParamsUtil.APP_ID);
						map.put("partnerid", WxPayParamsUtil.PARTNER);
						map.put("prepayid", prepayid);
						map.put("package", "Sign=WXPay");
						map.put("noncestr", uuid);
						map.put("timestamp", WXUtil.getTimeStamp());
			
						String sign2 = prepayReqHandler.createclientSign(map);
						map.put("sign", sign2);
						
						list.add(map);
					}
				System.out.println("---所有支付流程结束---");
				return new DataResult(Errors.OK, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/pay/wxpayResult")
	public void wxpayResult(HttpServletRequest request, HttpServletResponse res) {
		PrintWriter out = null;
		try{
			out = res.getWriter();
		String xmlstr = HttpUtil.getRquestParamsByIOs(request);
		Map map = XMLUtil.doXMLParse(xmlstr);
		if(WxPayParamsUtil.APP_ID.equals(map.get("appid"))&&WxPayParamsUtil.PARTNER.equals(map.get("mch_id"))&&"SUCCESS".equals(map.get("result_code"))){
			String out_trade_no=(String)map.get("out_trade_no");
			out_trade_no=out_trade_no.substring(0, 15);
			JxOrderItem jxOrderItem = jxOrderItemServic.findUnique("from JxOrderItem where orditem_no = ?", out_trade_no);
			System.out.println("---子订单1---");
			int item = jxOrderItemServic.upadtexwStatus(out_trade_no);
			System.out.println("---子订单2---");
			jxOrderItem.setOrditem_modtime(new Date());
			jxOrderItem.setOrditem_status(1);
			jxOrderItemServic.save(jxOrderItem);
			int time = jxOrderItemServic.updatetime(out_trade_no);
			String str="亲~您购买的"+jxOrderItem.getOrd_proname();
			str=str+" "+jxOrderItem.getOrder_price()+"元已经支付完成了";
			
			System.out.println("---保存消息列表---");
			String title = "交易消息";
			JxMessages mess = PushController.Mssages(out_trade_no, title, str, jxOrderItem.getU_id());
			messageService.save(mess);
			String alias = String.valueOf(jxOrderItem.getU_id());
			PushController.YHIOSMssage(alias,title,str);
			out.print("<xml>\r\n <return_code><![CDATA[SUCCESS]]></return_code> \r\n<return_msg><![CDATA[OK]]></return_msg>\r\n</xml>");
			
		}else{
			out.print("<xml>\r\n <return_code><![CDATA[FAIL]]></return_code> \r\n<return_msg><![CDATA[]]></return_msg>\r\n</xml>");
		}
		
		}catch(Exception e){
			out.print("<xml>\r\n <return_code><![CDATA[FAIL]]></return_code> \r\n<return_msg><![CDATA[]]></return_msg>\r\n</xml>");
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/pays/wxpayResult")
	public void wxpayResults(HttpServletRequest request, HttpServletResponse res) {
		PrintWriter out = null;
		try{
			
			System.out.println("01");
			out = res.getWriter();
		String xmlstr = HttpUtil.getRquestParamsByIOs(request);
		Map map = XMLUtil.doXMLParse(xmlstr);
		if(WxPayParamsUtil.APP_ID.equals(map.get("appid"))&&WxPayParamsUtil.PARTNER.equals(map.get("mch_id"))&&"SUCCESS".equals(map.get("result_code"))){
			String out_trade_no=(String)map.get("out_trade_no");
			System.out.println("02");
			out_trade_no=out_trade_no.substring(0, 15);
			System.out.println("03");
			JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", out_trade_no);
			jxOrder.setOrd_modtime(new Date());
			jxOrder.setOrd_status(1);
			jxOrderService.save(jxOrder);
			String str="亲~您购买的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
			String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
			str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
			System.out.println("04");
			
			System.out.println("---保存消息列表---");
			String title = "交易消息";
			JxMessages mess = PushController.Mssages(out_trade_no, title, str, jxOrder.getU_id());
			messageService.save(mess);
			String alias = String.valueOf(jxOrder.getU_id());
			PushController.YHIOSMssage(alias,title,str);
			
			out.print("<xml>\r\n <return_code><![CDATA[SUCCESS]]></return_code> \r\n<return_msg><![CDATA[OK]]></return_msg>\r\n</xml>");
			
		}else{
			out.print("<xml>\r\n <return_code><![CDATA[FAIL]]></return_code> \r\n<return_msg><![CDATA[]]></return_msg>\r\n</xml>");
		}
		
		}catch(Exception e){
			out.print("<xml>\r\n <return_code><![CDATA[FAIL]]></return_code> \r\n<return_msg><![CDATA[]]></return_msg>\r\n</xml>");
			e.printStackTrace();
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/pay/wxpayAgainResult")
	public void wxpayAgainResult(HttpServletRequest request, HttpServletResponse res) {
		System.out.println("---微信续费回调开始---");
		PrintWriter out = null;
		try{
			out = res.getWriter();
		String xmlstr = HttpUtil.getRquestParamsByIOs(request);
		Map map = XMLUtil.doXMLParse(xmlstr);
		if(WxPayParamsUtil.APP_ID.equals(map.get("appid"))&&WxPayParamsUtil.PARTNER.equals(map.get("mch_id"))&&"SUCCESS".equals(map.get("result_code"))){
			String out_trade_no=(String)map.get("out_trade_no");
			out_trade_no=out_trade_no.substring(0, 15);
			JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", out_trade_no);
			String proNo=jxOrder.getPro_no();
			if(jxOrder.getOrd_protypeid()==0){
				this.productService.addDayByproNo(proNo);
			}else{
				JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?", Long.valueOf(jxOrder.getPro_id()),Long.valueOf(jxOrder.getOrd_protypeid()));
				this.productService.addFlowByproNo(proNo,jxPay.getPay_flow());
			}
			jxOrder.setOrd_status(4);
			jxOrderService.save(jxOrder);
			//添加续费消息
			String str="亲~您续费的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
			String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
			str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
			
			System.out.println("---保存消息列表---");
			String title = "交易消息";
			JxMessages mess = PushController.Mssages(out_trade_no, title, str, jxOrder.getU_id());
			messageService.save(mess);
			String alias = String.valueOf(jxOrder.getU_id());
			PushController.YHIOSMssage(alias,title,str);
			out.print("<xml>\r\n <return_code><![CDATA[SUCCESS]]></return_code> \r\n<return_msg><![CDATA[OK]]></return_msg>\r\n</xml>");
			System.out.println("---微信续费回调结束");
		}else{
			out.print("<xml>\r\n <return_code><![CDATA[FAIL]]></return_code> \r\n<return_msg><![CDATA[]]></return_msg>\r\n</xml>");
		}
		
		}catch(Exception e){
			out.print("<xml>\r\n <return_code><![CDATA[FAIL]]></return_code> \r\n<return_msg><![CDATA[]]></return_msg>\r\n</xml>");
			e.printStackTrace();
		}

	}
	

}
