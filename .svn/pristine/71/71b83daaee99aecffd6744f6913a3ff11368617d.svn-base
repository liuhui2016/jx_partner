package com.game.smvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.payUtil.AlipayNotify;
import com.game.smvc.service.IJxMessageService;
import com.game.smvc.service.IJxOrderItemService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPayWayService;
import com.game.smvc.service.IJxProductService;
import com.game.smvc.util.HttpUtil;
import com.game.util.pay.Unionpay.AcpService;
import com.game.util.pay.Unionpay.SDKConfig;
import com.game.util.pay.Unionpay.SDKConstants;
import com.game.util.pay.Unionpay.demo.DemoBase;
import com.game.util.pay.Unionpay.util.LogUtil;


@Controller
@RequestMapping({ "/smvc" })
public class UnionPayController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static Log logger = LogFactory.getLog(PayController.class);
	
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
	
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/pay/unionpay", method = RequestMethod.POST)
	public Result Unionpay(HttpServletRequest request,HttpServletResponse response) {
		try {
			System.out.println("---银联支付开始---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String orderId = jsonObject.getString("ord_no");//订单号
			String context = jsonObject.getString("context");
			String price = jsonObject.getString("price");
			
			int price1 = (Math.round(Float.valueOf(price)*10000))/100;
			price=price1+"";
			//测试账号专用
			if(price1==0){
				System.out.println("---进入测试的支付流程---");
				int isAgain = Integer.parseInt(jsonObject.getString("isAgain"));
				if(isAgain == 0){
					System.out.println("---测试账号支付开始---");
					String tag = jsonObject.getString("tag");
					int tags = Integer.parseInt(tag);
					if(tags == 0){
						System.out.println("---父订单支付---");
						JxOrderItem jxOrderItem = jxOrderItemServic.findUnique("from JxOrderItem where orditem_no = ?", orderId);
						System.out.println("---子订单1---");
						int item = jxOrderItemServic.upadteStatus(orderId);
						System.out.println("---子订单2---");
						jxOrderItem.setOrditem_modtime(new Date());
						System.out.println("1");
						jxOrderItem.setOrditem_status(1);
						jxOrderItem.setOrditem_way(2);
						jxOrderItemServic.save(jxOrderItem);
						int time = jxOrderItemServic.updatetime(orderId);
						String str="亲~您购买的"+jxOrderItem.getOrd_proname();
						str=str+" "+jxOrderItem.getOrder_price()+"元已经支付完成了";
						
						System.out.println("---保存消息列表---");
						String title = "交易消息";
						JxMessages mess = PushController.Mssages(orderId, title, str, jxOrderItem.getU_id());
						messageService.save(mess);
						System.out.println("保存消息结束");
						//消息推送
						String alias = String.valueOf(jxOrderItem.getU_id());
						System.out.println("开始消息推送");
						PushController.YHIOSMssage(alias,title,str);
						System.out.println("消息推送结束");
				        //消息推送
						return new Result(Errors.ERROR_CODE);
					}else{
						System.out.println("---子订单支付---");
						JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", orderId);
						jxOrder.setOrd_modtime(new Date());
						jxOrder.setOrd_status(1);
						jxOrder.setOrd_way(2);
						jxOrderService.save(jxOrder);
						String str="亲~您购买的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
						String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
						str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
						
						System.out.println("---保存消息列表---");
						String title = "交易消息";
						JxMessages mess = PushController.Mssages(orderId, title, str, jxOrder.getU_id());
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
					JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", orderId);
					jxOrder.setOrd_modtime(new Date());
					jxOrder.setOrd_status(4);
					jxOrder.setOrd_way(2);
					jxOrderService.save(jxOrder);
					String str="亲~您续费的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
					String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
					str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
					
					System.out.println("---保存消息列表---");
					String title = "交易消息";
					JxMessages mess = PushController.Mssages(orderId, title, str, jxOrder.getU_id());
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
				String tag = null;
				String str="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
				if(jsonObject.containsKey("tag")){
					tag = jsonObject.getString("tag");
					if(tag.equals("")){
						System.out.println("---续费---");
						//正则匹配特殊字符
						if(orderId.contains(str)){
							return new Result(Errors.USER_ERROR_NOT_CORRECT);
						}else if(orderId.contains("-")){
							return new Result(Errors.USER_ERROR_NOT_CORRECT);
						}else{
						JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=?", orderId);
						
						jxOrder.setOrd_way(2);
						jxOrderService.save(jxOrder);
						}
					}else{
						int tags = Integer.parseInt(tag);
						if(tags == 0){
							System.out.println("---进入银联真正父订单的支付流程---");
							//正则匹配特殊字符
							if(orderId.contains(str)){
								return new Result(Errors.USER_ERROR_NOT_CORRECT);
							}else if(orderId.contains("-")){
								return new Result(Errors.USER_ERROR_NOT_CORRECT);
							}else{
								JxOrderItem jxOrderItem = jxOrderItemServic.findUnique("from JxOrderItem where orditem_no = ?", orderId);
								jxOrderItem.setOrditem_way(2);
								jxOrderItemServic.save(jxOrderItem);
							}
						}else{
							System.out.println("---进入银联真正子订单的支付流程---");
							//正则匹配特殊字符
							if(orderId.contains(str)){
								return new Result(Errors.USER_ERROR_NOT_CORRECT);
							}else if(orderId.contains("-")){
								return new Result(Errors.USER_ERROR_NOT_CORRECT);
							}else{
								JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=?", orderId);
								
								jxOrder.setOrd_way(2);
								jxOrderService.save(jxOrder);
							}
						}
					}
					
				}else{
					System.out.println("---续费---");
					//正则匹配特殊字符
					if(orderId.contains(str)){
						return new Result(Errors.USER_ERROR_NOT_CORRECT);
					}else if(orderId.contains("-")){
						return new Result(Errors.USER_ERROR_NOT_CORRECT);
					}else{
					JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=?", orderId);
					
					jxOrder.setOrd_way(2);
					jxOrderService.save(jxOrder);
					}
				}
			
				String notifyUrl = null;
				int isAgain = Integer.parseInt(jsonObject.getString("isAgain"));
				// 回调接口url
				if (isAgain == 0) {
					if(Integer.parseInt(tag) == 0){
						notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/pay/UnionpayResult.v";
					}else{
						notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/pays/UnionpayResult.v";
					}
					
				} else {
					notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/pay/UnionpayRenewalResult.v";
				}
				
				List<Map<String,String>> list=new ArrayList<Map<String,String>>();
				
				Map<String, String> contentData = new HashMap<String, String>();
	
				/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
				contentData.put("version", DemoBase.version);            //版本号 全渠道默认值
				contentData.put("encoding", DemoBase.encoding);     //字符集编码 可以使用UTF-8,GBK两种方式
				contentData.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
				contentData.put("txnType", "01");              		 	//交易类型 01:消费
				contentData.put("txnSubType", "01");           		 	//交易子类 01：消费
				contentData.put("bizType", "000201");          		 	//填写000201
				contentData.put("channelType", "08");          		 	//渠道类型 08手机
	
				/***商户接入参数***/
				contentData.put("merId", "310420157320006");   		 	//商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
				//contentData.put("merId", "777290058145907");
				contentData.put("accessType", "0");            		 	//接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
				contentData.put("orderId", orderId);        	 	    	//商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
				contentData.put("txnTime",  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));		 		    //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
				contentData.put("accType", "01");					 	//账号类型 01：银行卡02：存折03：IC卡帐号类型(卡介质)
				contentData.put("txnAmt", price);						//交易金额 单位为分，不能带小数点
				contentData.put("currencyCode", "156");                 //境内商户固定 156 人民币
				//contentData.put("backUrl", DemoBase.backUrl);
				contentData.put("backUrl", notifyUrl);
				
				Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
				String requestAppUrl = SDKConfig.getConfig().getAppRequestUrl();								 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
				Map<String, String> rspData = AcpService.post(reqData,requestAppUrl,DemoBase.encoding);  //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
				Map<String,String> map = new HashMap<String, String>();
				if(!rspData.isEmpty()){
					if(AcpService.validate(rspData, DemoBase.encoding)){
						LogUtil.writeLog("验证签名成功");
						//得到应答码
						String respCode = rspData.get("respCode");
						System.out.println("应答码:"+respCode);
						//业务逻辑
						if(("00").equals(respCode)){
							//成功,获取tn号
							String tn = rspData.get("tn");
							map.put("tn", tn);
						//其他应答码为失败请排查原因或做失败处理
						}else if(("12").equals(respCode)){
							return new Result(Errors.USER_ERROR_REPEAT);
						}else if(("01").equals(respCode)){
							return new Result(Errors.TRANSACTION_FAILURE);
						}else if(("02").equals(respCode)){
							return new Result(Errors.SYSTEM_IS_NOT_OPEN);
						}else if(("64").equals(respCode)){
							return new Result(Errors.LACK_OF_BALANCE);
						}else if(("62").equals(respCode)){
							return new Result(Errors.REPLACE_THE_BANK_CARD);
						}else if(("61").equals(respCode)){
							return new Result(Errors.CARD_NUMBER_IS_INVALID);
						}else if(("68").equals(respCode)){
							return new Result(Errors.DOSE_NOT_SUPPORT_THE_BUSINESS);
						}else if(("33").equals(respCode)){
							return new Result(Errors.TRANSACTION_VALUE_OF_TRANSFINITE);
						}else if(("34").equals(respCode)){
							return new Result(Errors.THIS_TRANSCATON_CANNONT_BE_FOUNT);
						}
					}else{
						LogUtil.writeErrorLog("验证签名失败");
					}
				}else{
					//未返回正确的http状态
					LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
				}
				String reqMessage = DemoBase.genHtmlResult(reqData);
				String rspMessage = DemoBase.genHtmlResult(rspData);
				list.add(map);
				return new SecretResult(Errors.OK, list);
				}
			
			//测试结束符
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.USER_ERROR_NOT_ORDER);
		}
	}
	
	
	
	/**
	* 后台通知处理
	* @param sign
	* @param request
	* @param response
	*/
	@RequestMapping(value = "/pay/UnionpayResult")
	@ResponseBody
	public void backRcvResponse(HttpServletRequest request, HttpServletResponse response) {
		
		LogUtil.writeLog("BackRcvResponse接收后台通知开始");

		String encoding = request.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(request);

		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
	       try
	       {
	    	   if (null != reqParam && !reqParam.isEmpty()) {
	   			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
	   			valideData = new HashMap<String, String>(reqParam.size());
	   			while (it.hasNext()) {
	   				Entry<String, String> e = it.next();
	   				String key = (String) e.getKey();
	   				String value = (String) e.getValue();
	   				
	   				valideData.put(key, value);
	   			}
	   		}
	   		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
	   		if (!AcpService.validate(valideData, encoding)) {
	   			LogUtil.writeLog("验证签名结果[失败].");
	   		} else {
	   			LogUtil.writeLog("验证签名结果[成功].");
	   			//获取后台通知的数据，其他字段也可用类似方式获取
	   			String orderId =valideData.get("orderId"); 
	   			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
				String respCode = valideData.get("respCode");
	   			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
				if(("00").equals(respCode)){
					JxOrderItem jxOrderItem = jxOrderItemServic.findUnique("from JxOrderItem where orditem_no = ?", orderId);
					System.out.println("---子订单1---");
					int item = jxOrderItemServic.upadteStatus(orderId);
					System.out.println("---子订单2---");
					jxOrderItem.setOrditem_modtime(new Date());
					jxOrderItem.setOrditem_status(1);
					jxOrderItem.setOrditem_way(2);
					jxOrderItemServic.save(jxOrderItem);
					int time = jxOrderItemServic.updatetime(orderId);
					String str="亲~您购买的"+jxOrderItem.getOrd_proname();
					str=str+" "+jxOrderItem.getOrder_price()+"元已经支付完成了";
					System.out.println("---保存消息列表---");
					String title = "交易消息";
					JxMessages mess = PushController.Mssages(orderId, title, str, jxOrderItem.getU_id());
					messageService.save(mess);
					System.out.println("保存消息结束");
					//消息推送
					String alias = String.valueOf(jxOrderItem.getU_id());
					System.out.println("开始消息推送");
					PushController.YHIOSMssage(alias,title,str);
					System.out.println("消息推送结束");
					//返回给银联服务器http 200  状态码
					response.getWriter().print("ok");	
				}else if(("30").equals(respCode)){
					//其他应答码为失败请排查原因或做失败处理
					LogUtil.writeErrorLog("交易未通过");
				}else if(("01").equals(respCode)){
					LogUtil.writeErrorLog("交易失败");
				}else if(("33").equals(respCode)){
					LogUtil.writeErrorLog("交易金额超限");
				}else if(("32").equals(respCode)){
					LogUtil.writeErrorLog("无此交易权限");
				}else if(("64").equals(respCode)){
					LogUtil.writeErrorLog("余额不足");
				}else{
					LogUtil.writeErrorLog("其他情况");
				}
				
	   		}
	   		
	   		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
	       }
	       catch(Exception e){
	    	   e.printStackTrace();
	       }
	}
	
	
	/**
	* 后台通知处理
	* @param sign
	* @param request
	* @param response
	*/
	@RequestMapping(value = "/pays/UnionpayResult")
	@ResponseBody
	public void UnionpayResults(HttpServletRequest request, HttpServletResponse response) {
		
		LogUtil.writeLog("BackRcvResponse接收后台通知开始");

		String encoding = request.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(request);

		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
	       try
	       {
	    	   if (null != reqParam && !reqParam.isEmpty()) {
	   			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
	   			valideData = new HashMap<String, String>(reqParam.size());
	   			while (it.hasNext()) {
	   				Entry<String, String> e = it.next();
	   				String key = (String) e.getKey();
	   				String value = (String) e.getValue();
	   				
	   				valideData.put(key, value);
	   			}
	   		}
	   		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
	   		if (!AcpService.validate(valideData, encoding)) {
	   			LogUtil.writeLog("验证签名结果[失败].");
	   		} else {
	   			LogUtil.writeLog("验证签名结果[成功].");
	   			//获取后台通知的数据，其他字段也可用类似方式获取
	   			String orderId =valideData.get("orderId"); 
	   			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
				String respCode = valideData.get("respCode");
	   			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
				if(("00").equals(respCode)){
	   			JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", orderId);
				jxOrder.setOrd_modtime(new Date());
				jxOrder.setOrd_status(1);
				jxOrderService.save(jxOrder);
				String str="亲~您购买的"+jxOrder.getOrd_proname()+"("+jxOrder.getOrd_color()+")";
				String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
				str=str+str1+" "+jxOrder.getOrd_price()+"元已经支付完成了";
				String title = "交易消息";
				JxMessages mess = PushController.Mssages(orderId, title, str, jxOrder.getU_id());
				messageService.save(mess);
				String alias = String.valueOf(jxOrder.getU_id());
				PushController.IOSMssage(orderId,alias,str);
				
				//返回给银联服务器http 200  状态码
				response.getWriter().print("ok");	
				}else if(("30").equals(respCode)){
					//其他应答码为失败请排查原因或做失败处理
					LogUtil.writeErrorLog("交易未通过");
				}else if(("01").equals(respCode)){
					LogUtil.writeErrorLog("交易失败");
				}else if(("33").equals(respCode)){
					LogUtil.writeErrorLog("交易金额超限");
				}else if(("32").equals(respCode)){
					LogUtil.writeErrorLog("无此交易权限");
				}else if(("64").equals(respCode)){
					LogUtil.writeErrorLog("余额不足");
				}else{
					LogUtil.writeErrorLog("其他情况");
				}
				
	   		}
	   		
	   		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
	       }
	       catch(Exception e){
	    	   e.printStackTrace();
	       }
	}
	
	
	
	@RequestMapping(value = "/pay/UnionpayRenewalResult")
	@ResponseBody
	public void backRcvResponses(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("-----开始续费-----");
		LogUtil.writeLog("BackRcvResponse接收后台通知开始");

		String encoding = request.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(request);

		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
	       try
	       {
	    	   if (null != reqParam && !reqParam.isEmpty()) {
	   			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
	   			valideData = new HashMap<String, String>(reqParam.size());
	   			while (it.hasNext()) {
	   				Entry<String, String> e = it.next();
	   				String key = (String) e.getKey();
	   				String value = (String) e.getValue();
	   				
	   				valideData.put(key, value);
	   			}
	   		}
	    	  
	   		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
	   		if (!AcpService.validate(valideData, encoding)) {
	   			LogUtil.writeLog("验证签名结果[失败].");
	   			//验签失败，需解决验签问题
	   		} else {
	   			LogUtil.writeLog("验证签名结果[成功].");
	   			//获取后台通知的数据，其他字段也可用类似方式获取
	   			String orderId =valideData.get("orderId"); 
	   			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
				String respCode = valideData.get("respCode");
				//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
	   			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
				if(("00").equals(respCode)){
					//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
		   			System.out.println("----------------续费---------------------");
					//删除旧的订单
					JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no=? ", orderId);
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
					JxMessages mess = PushController.Mssages(orderId, title, str, jxOrder.getU_id());
					messageService.save(mess);
					System.out.println("保存消息结束");
					//消息推送
					String alias = String.valueOf(jxOrder.getU_id());
					System.out.println("开始消息推送");
					PushController.YHIOSMssage(alias,title,str);
					System.out.println("消息推送结束");
			        //消息推送
					//返回给银联服务器http 200  状态码
					response.getWriter().print("ok");	
				}else if(("30").equals(respCode)){
					//其他应答码为失败请排查原因或做失败处理
					LogUtil.writeErrorLog("交易未通过");
				}else if(("01").equals(respCode)){
					LogUtil.writeErrorLog("交易失败");
				}else if(("33").equals(respCode)){
					LogUtil.writeErrorLog("交易金额超限");
				}else if(("32").equals(respCode)){
					LogUtil.writeErrorLog("无此交易权限");
				}else if(("64").equals(respCode)){
					LogUtil.writeErrorLog("余额不足");
				}else{
					LogUtil.writeErrorLog("其他情况");
				}
				
	   		}
	   		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
	   		//返回给银联服务器http 200  状态码
	       }
	       catch(Exception e){
	    	   e.printStackTrace();
	       }
	}
	
	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> getAllRequestParam(HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
	
}

	

