package com.game.smvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.push.messages.MessageTemplate;
import com.game.push.quickin.PushAllToApp;
import com.game.push.quickin.PushBase;
import com.game.smvc.entity.JxMessages;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxOrderItem;
import com.game.smvc.entity.JxPay;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SingleDataResult;
import com.game.smvc.payUtil.AlipayConfig;
import com.game.smvc.payUtil.AlipayNotify;
import com.game.smvc.service.IJxMessageService;
import com.game.smvc.service.IJxOrderItemService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPayWayService;
import com.game.smvc.service.IJxProductService;
import com.game.smvc.util.HttpUtil;
import com.game.util.pay.AliSignUtils;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.template.style.AbstractNotifyStyle;
import com.gexin.rp.sdk.template.style.Style1;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.template.style.Style0;


@SuppressWarnings("unused")
@Controller
@RequestMapping({ "/smvc" })
public class PayController extends PushBase{
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
	
	/**
	 * 支付宝支付(修改版2)
	 * @param lh(2017/06/22)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pay/alipay", method = RequestMethod.POST)
	public Result AliSign(HttpServletRequest request) {
		try {
			System.out.println("---支付开始---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String subject = jsonObject.getString("ord_no");
			String context = jsonObject.getString("context");
			String price = jsonObject.getString("price");
			
			
			float  price1 =  (float)(Math.round(Float.valueOf(price)*100))/100;
			price=price1+"";
			System.out.println("价格---->"+price1);
			//先判断isAgain在判断价格
			if(price1==0){
				System.out.println("---进入测试的支付流程---");
				int isAgain = Integer.parseInt(jsonObject.getString("isAgain"));
				System.out.println("isAgain----->"+isAgain);
				if(isAgain == 0){
					System.out.println("---测试账号支付开始---");
					String tag = jsonObject.getString("tag");
					int tags = Integer.parseInt(tag);
					if(tags == 0){
						System.out.println("---父订单支付---");
						JxOrderItem jxOrderItem = jxOrderItemServic.findUnique("from JxOrderItem where orditem_no = ?", subject);
						System.out.println("---子订单1---");
						int item = jxOrderItemServic.upadtezfbStatus(subject);
						System.out.println("---子订单2---");
						jxOrderItem.setOrditem_modtime(new Date());
						System.out.println("1");
						jxOrderItem.setOrditem_status(1);
						jxOrderItem.setOrditem_way(0);
						jxOrderItemServic.save(jxOrderItem);
						int time = jxOrderItemServic.updatetime(subject);
						String str="亲~您购买的"+jxOrderItem.getOrd_proname();
						str=str+" "+jxOrderItem.getOrder_price()+"元已经支付完成了";
						
						System.out.println("---保存消息列表---");
						String title = "交易消息";
						JxMessages mess = PushController.Mssages(subject, title, str, jxOrderItem.getU_id());
						messageService.save(mess);
						//消息推送
						String alias = String.valueOf(jxOrderItem.getU_id());
						PushController.YHIOSMssage(alias,title,str);
				        //消息推送
						return new Result(Errors.ERROR_CODE);
					}else{
						System.out.println("---子订单支付---");
						JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", subject);
						jxOrder.setOrd_modtime(new Date());
						jxOrder.setOrd_status(1);
						jxOrder.setOrd_way(0);
						jxOrderService.save(jxOrder);
						String str="亲~您购买的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
						String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
						str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
						
						System.out.println("---保存消息列表---");
						String title = "交易消息";
						JxMessages mess = PushController.Mssages(subject, title, str, jxOrder.getU_id());
						messageService.save(mess);
						System.out.println("保存消息结束");
						//消息推送
						String alias = String.valueOf(jxOrder.getU_id());
						System.out.println("开始消息推送");
						PushController.YHIOSMssage(alias,title,str);
						System.out.println("消息推送结束");
				        //消息推送
						return new Result(Errors.ERROR_CODE);
					}
				}else{
					System.out.println("---测试账号续费开始---");
					JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", subject);
					jxOrder.setOrd_modtime(new Date());
					jxOrder.setOrd_status(4);
					jxOrder.setOrd_way(0);
					jxOrderService.save(jxOrder);
					String str="亲~您续费的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
					String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
					str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
					System.out.println("---保存消息列表---");
					String title = "交易消息";
					JxMessages mess = PushController.Mssages(subject, title, str, jxOrder.getU_id());
					messageService.save(mess);
					String alias = String.valueOf(jxOrder.getU_id());
					PushController.YHIOSMssage(alias,title,str);
					
					return new Result(Errors.ERROR_CODE);
				}
			}else{
				String tag = null;	
				if(jsonObject.containsKey("tag")){
					tag = jsonObject.getString("tag");
					if("".equals(tag)){
						System.out.println("---续费---");
						JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", subject);
						jxOrder.setOrd_modtime(new Date());
						jxOrder.setOrd_way(0);
						jxOrderService.save(jxOrder);
					}else{
						int tags = Integer.parseInt(tag);
						if(tags == 0){
							System.out.println("---进入真正父订单的支付流程---");
							JxOrderItem jxOrderItem = jxOrderItemServic.findUnique("from JxOrderItem where orditem_no = ?", subject);
							jxOrderItem.setOrditem_modtime(new Date());
							jxOrderItem.setOrditem_way(0);
							jxOrderItemServic.save(jxOrderItem);
						}else{
							System.out.println("---进入真正子订单的支付流程---");
							JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", subject);
							jxOrder.setOrd_modtime(new Date());
							jxOrder.setOrd_way(0);
							jxOrderService.save(jxOrder);
						}	
					}
				}else{
					System.out.println("---续费---");
					JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", subject);
					jxOrder.setOrd_modtime(new Date());
					jxOrder.setOrd_way(0);
					jxOrderService.save(jxOrder);
				}
				
				String notifyUrl = null;
				int isAgain = Integer.parseInt(jsonObject.getString("isAgain"));
				// 回调接口url
				if (isAgain == 0) {
					
					if(Integer.parseInt(tag) == 0){
						
						notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/pay/alipayResult.v";
					}else{
						notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/pays/alipayResult.v";
					}
				} else {
					notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/pay/alipayAgainResult.v";
				}
				// 商户PID
				String partner = AlipayConfig.partner;
				// 商户收款账号
				String seller = "szsjxzn@163.com";
				// 商户私钥，pkcs8格式
				String rsa_private = AlipayConfig.private_key;
				// 支付宝公钥
				String rsa_public = AlipayConfig.alipay_public_key;
	
				String orderInfo = AliSignUtils.getOrderInfo(partner, seller,
						subject, context, price, notifyUrl);
				String sign = AliSignUtils.sign(orderInfo, rsa_private);
				sign = URLEncoder.encode(sign, "utf-8");
				String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
						+ AliSignUtils.getSignType();// 完整的符合支付宝参数规范的订单信息
				return new SingleDataResult(Errors.OK, payInfo);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	
	@SuppressWarnings({"rawtypes" })
	@RequestMapping(value = "/pay/alipayResult")
	public void AliPayResult(HttpServletRequest request, HttpServletResponse res) {

		PrintWriter out;
		try {
			out = res.getWriter();

			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();

			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				params.put(name, valueStr);
			}

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			// 商户订单号
			String out_trade_no = new String(request.getParameter(
					"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no")
					.getBytes("ISO-8859-1"), "UTF-8");

			// 交易状态
			String trade_status = new String(request.getParameter(
					"trade_status").getBytes("ISO-8859-1"), "UTF-8");

			// 异步通知ID
			String notify_id = request.getParameter("notify_id");

			// sign
			String sign = request.getParameter("sign");
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			if (notify_id != "" && notify_id != null) {// //判断接受的post通知中有无notify_id，如果有则是异步通知。
				if (AlipayNotify.verifyResponse(notify_id).equals("true"))// 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
				{
					if (AlipayNotify.getSignVeryfy(params, sign))// 使用支付宝公钥验签
					{
						// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
						if (trade_status.equals("TRADE_FINISHED")) {
				
						} else if (trade_status.equals("TRADE_SUCCESS")) {
							// 判断该笔订单是否在商户网站中已经做过处理
							// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							// 如果有做过处理，不执行商户的业务程序
							
							//修改后
							JxOrderItem jxOrderItem = jxOrderItemServic.findUnique("from JxOrderItem where orditem_no = ?", out_trade_no);
							System.out.println("---子订单1---");
							int item = jxOrderItemServic.upadtezfbStatus(out_trade_no);
							System.out.println("---子订单2---");
							jxOrderItem.setOrditem_modtime(new Date());
							jxOrderItem.setOrditem_status(1);
							jxOrderItem.setOrditem_way(0);
							jxOrderItemServic.save(jxOrderItem);
							int time = jxOrderItemServic.updatetime(out_trade_no);
							String str="亲~您购买的"+jxOrderItem.getOrd_proname();
							str=str+" "+jxOrderItem.getOrder_price()+"元已经支付完成了";
						
							String title = "交易消息";
							JxMessages mess = PushController.Mssages(out_trade_no, title, str, jxOrderItem.getU_id());
							messageService.save(mess);
							//消息推送
							String alias = String.valueOf(jxOrderItem.getU_id());
							PushController.YHIOSMssage(alias,title,str);
					        //消息推送
							
							// 注意：
							// 付款完成后，支付宝系统发送该交易状态通知
							// 请务必判断请求时的out_trade_no、total_fee、seller_id与通知时获取的out_trade_no、total_fee、seller_id为一致的
						}
						// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
						out.print("success");// 请不要修改或删除

						// 调试打印log
						// AlipayCore.logResult("notify_url success!","notify_url");
					} else// 验证签名失败
					{
						out.print("sign fail");
					}
				} else// 验证是否来自支付宝的通知失败
				{
					out.print("response fail");
				}
			} else {
				out.print("no notify message");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
	//子订单支付
	@SuppressWarnings({"rawtypes" })
	@RequestMapping(value = "/pays/alipayResult")
	public void AliPayResults(HttpServletRequest request, HttpServletResponse res) {
		System.out.println("2");
		PrintWriter out;
		try {
			out = res.getWriter();

			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();

			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				params.put(name, valueStr);
			}

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			// 商户订单号
			String out_trade_no = new String(request.getParameter(
					"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no")
					.getBytes("ISO-8859-1"), "UTF-8");

			// 交易状态
			String trade_status = new String(request.getParameter(
					"trade_status").getBytes("ISO-8859-1"), "UTF-8");

			// 异步通知ID
			String notify_id = request.getParameter("notify_id");

			// sign
			String sign = request.getParameter("sign");
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			if (notify_id != "" && notify_id != null) {// //判断接受的post通知中有无notify_id，如果有则是异步通知。
				if (AlipayNotify.verifyResponse(notify_id).equals("true"))// 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
				{
					if (AlipayNotify.getSignVeryfy(params, sign))// 使用支付宝公钥验签
					{
						// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
						if (trade_status.equals("TRADE_FINISHED")) {
				
						} else if (trade_status.equals("TRADE_SUCCESS")) {
							// 判断该笔订单是否在商户网站中已经做过处理
							// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							// 如果有做过处理，不执行商户的业务程序
							//修改前
							System.out.println("3");
							JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", out_trade_no);
							jxOrder.setOrd_modtime(new Date());
							jxOrder.setOrd_status(1);
							jxOrderService.save(jxOrder);
							String str="亲~您购买的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
							String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
							str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
							
							System.out.println("---保存消息列表---");
							String title = "交易消息";
							JxMessages mess = PushController.Mssages(out_trade_no, title, str, jxOrder.getU_id());
							messageService.save(mess);
							//消息推送
							String alias = String.valueOf(jxOrder.getU_id());
							PushController.YHIOSMssage(alias,title,str);
							//消息推送
							
							// 注意：
							// 付款完成后，支付宝系统发送该交易状态通知
							// 请务必判断请求时的out_trade_no、total_fee、seller_id与通知时获取的out_trade_no、total_fee、seller_id为一致的
						}
						// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
						out.print("success");// 请不要修改或删除

						// 调试打印log
						// AlipayCore.logResult("notify_url success!","notify_url");
					} else// 验证签名失败
					{
						out.print("sign fail");
					}
				} else// 验证是否来自支付宝的通知失败
				{
					out.print("response fail");
				}
			} else {
				out.print("no notify message");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	
	//TODO 已完成待调试
	@SuppressWarnings({"rawtypes" })
	@RequestMapping(value = "/pay/alipayAgainResult")
	public void alipayAgainResult(HttpServletRequest request, HttpServletResponse res) {
		System.out.println("---1开始续费1---");
		PrintWriter out;
		try {
			out = res.getWriter();

			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();

			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "gbk");
				params.put(name, valueStr);
			}

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			// 商户订单号
			String out_trade_no = new String(request.getParameter(
					"out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 支付宝交易号
			String trade_no = new String(request.getParameter("trade_no")
					.getBytes("ISO-8859-1"), "UTF-8");

			// 交易状态
			String trade_status = new String(request.getParameter(
					"trade_status").getBytes("ISO-8859-1"), "UTF-8");

			// 异步通知ID
			String notify_id = request.getParameter("notify_id");

			// sign
			String sign = request.getParameter("sign");
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			if (notify_id != "" && notify_id != null) {// //判断接受的post通知中有无notify_id，如果有则是异步通知。
				if (AlipayNotify.verifyResponse(notify_id).equals("true"))// 判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
				{
					if (AlipayNotify.getSignVeryfy(params, sign))// 使用支付宝公钥验签
					{
						//TODO 未测试
						if (trade_status.equals("TRADE_FINISHED")) {
							// 交易完成修改支付状态
							System.out.println("交易完成------------------------------------------");

						} else if (trade_status.equals("TRADE_SUCCESS")) {
							System.out.println("支付成功-------------------------------------");
							//删除旧的订单
							JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", out_trade_no);
							System.out.println("1");
							String proNo=jxOrder.getPro_no();
							System.out.println("2");
							if(jxOrder.getOrd_protypeid()==0){
								System.out.println("3");
								this.productService.addDayByproNo(proNo);
							}else{
								System.out.println("4");
								JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?", Long.valueOf(jxOrder.getPro_id()),Long.valueOf(jxOrder.getOrd_protypeid()));
								this.productService.addFlowByproNo(proNo,jxPay.getPay_flow());
							}
							System.out.println("5");
							jxOrder.setOrd_status(4);
							jxOrderService.save(jxOrder);
							System.out.println("6");
							//添加续费消息
							String str="亲~您续费的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
							String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
							str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
							System.out.println("7");
							System.out.println("---保存消息列表---");
							String title = "交易消息";
							System.out.println("8");
							JxMessages mess = PushController.Mssages(out_trade_no, title, str, jxOrder.getU_id());
							messageService.save(mess);
							System.out.println("---续费成功---");
							String alias = String.valueOf(jxOrder.getU_id());
							PushController.YHIOSMssage(alias,title,str);
							System.out.println("---消息推送成功---");
							// 注意：
							// 付款完成后，支付宝系统发送该交易状态通知
							// 请务必判断请求时的out_trade_no、total_fee、seller_id与通知时获取的out_trade_no、total_fee、seller_id为一致的
						}
						// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
						out.print("success");// 请不要修改或删除

						// 调试打印log
						// AlipayCore.logResult("notify_url success!","notify_url");
					} else// 验证签名失败
					{
						out.print("sign fail");
					}
				} else// 验证是否来自支付宝的通知失败
				{
					out.print("response fail");
				}
			} else {
				out.print("no notify message");
			}

		}catch (IOException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}