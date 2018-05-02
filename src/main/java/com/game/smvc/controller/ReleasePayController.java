package com.game.smvc.controller;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Map.Entry;

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

import com.game.push.quickin.PushBase;
import com.game.smvc.entity.JxMessages;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxOrderItem;
import com.game.smvc.entity.JxPay;
import com.game.smvc.entity.JxReleaseOrder;
import com.game.smvc.entity.Jxpublish;
import com.game.smvc.entity.result.DataResult;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
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
import com.game.util.pay.Unionpay.AcpService;
import com.game.util.pay.Unionpay.SDKConfig;
import com.game.util.pay.Unionpay.SDKConstants;
import com.game.util.pay.Unionpay.demo.DemoBase;
import com.game.util.pay.Unionpay.util.LogUtil;
import com.game.util.pay.WXpay.ClientRequestHandler;
import com.game.util.pay.WXpay.PrepayIdRequestHandler;
import com.game.util.pay.WXpay.util.WXUtil;
import com.game.util.pay.WXpay.util.WxPayParamsUtil;
import com.game.util.pay.WXpay.util.XMLUtil;
import com.game.smvc.entity.result.Result;
import com.game.smvc.service.IJxMerchantPublishService;
import com.game.smvc.service.IJxMessageService;
import com.game.smvc.service.IJxPayWayService;
import com.game.smvc.service.JxReleaseOrderService;
import com.gexin.rp.sdk.http.IGtPush;

//发布支付
@SuppressWarnings("unused")
@Controller
@RequestMapping({ "/smvc" })
public class ReleasePayController extends PushBase{

	
	@Autowired
	private IJxPayWayService payWayService;
	@Autowired
	private IJxMessageService messageService;
	@Autowired
	private IJxMerchantPublishService merchantPublish;
	@Autowired
	private JxReleaseOrderService jxReleaseOrderService;
	/**
	 * 支付宝支付
	 * @param lh
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/releasepay/alipay", method = RequestMethod.POST)
	public Result ReleasePay(HttpServletRequest request) {
		try {
			
			System.out.println("---支付宝支付开始---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String subject = jsonObject.getString("ord_no");
			String context = jsonObject.getString("seller");
			String price = jsonObject.getString("price");
			String uid = jsonObject.getString("userid");
			
			float  price1 =  (float)(Math.round(Float.valueOf(price)*100))/100;
			price=price1+"";
			
			if("121".equals(uid)){
				System.out.println("---测试账号---");
				JxReleaseOrder releaseOrder = jxReleaseOrderService.findUnique("from jx_release_order where ord_no=?", subject);
				releaseOrder.setOrd_way(0);
				releaseOrder.setFb_modtime(new Date());
				releaseOrder.setFb_state(1);
				Jxpublish publish = merchantPublish.findUnique("from Jxpublish where ord_no = ?", subject);
				publish.setPub_modtime(new Date());
				publish.setFb_state(0);
				jxReleaseOrderService.save(releaseOrder);
				merchantPublish.save(publish);
				String str="亲~您发布的"+releaseOrder.getFb_type()+"已发布完成";
				str=str+" "+"支付金额为:"+releaseOrder.getFb_price()+"元";
				String title = "交易消息";
				JxMessages mess = PushController.Mssages(subject, title, str, Long.parseLong(releaseOrder.getU_id()+""));
				messageService.save(mess);
				
				String alias = releaseOrder.getU_id()+"";
				PushController.YHIOSMssage(alias,title,str);
				
				return new Result(Errors.ERROR_CODE);
			}else{
				System.out.println("---真实支付---");
				JxReleaseOrder releaseOrder = jxReleaseOrderService.findUnique("from jx_release_order where ord_no=?", subject);
				releaseOrder.setOrd_way(0);
				jxReleaseOrderService.save(releaseOrder);
				
				String notifyUrl = null;
				
				notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/releasepay/alipayResult.v";
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
				System.out.println("---所有支付流程结束---");
				System.out.println("payInfo:"+payInfo);
				return new SingleDataResult(Errors.OK, payInfo);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	
	@SuppressWarnings({"rawtypes" })
	@RequestMapping(value = "/releasepay/alipayResult")
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
							//修改前
						
							JxReleaseOrder releaseOrder = jxReleaseOrderService.findUnique("from jx_release_order where ord_no = ?", out_trade_no);
							releaseOrder.setFb_modtime(new Date());
							releaseOrder.setFb_state(1);;
							Jxpublish publish = merchantPublish.findUnique("from Jxpublish where ord_no = ?", out_trade_no);
							publish.setFb_state(0);
							jxReleaseOrderService.save(releaseOrder);
							merchantPublish.save(publish);
							
							String str="亲~您发布的"+releaseOrder.getFb_type()+"已发布完成";
							str=str+" "+"支付金额为:"+releaseOrder.getFb_price()+"元";
							String title = "交易消息";
							JxMessages mess = PushController.Mssages(out_trade_no, title, str, Long.parseLong(releaseOrder.getU_id()+""));
							messageService.save(mess);
							String alias = releaseOrder.getU_id()+"";
							PushController.YHIOSMssage(alias,title,str);
							
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
	
	
	/**
	 * 微信支付
	 * @param lh
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/releasepay/wxpay", method = RequestMethod.POST)
	public Result ReleasepayWXAliSign(HttpServletRequest request,HttpServletResponse response) {
		try {
			System.out.println("---微信支付开始---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String out_trade_no  = jsonObject.getString("ord_no");
			String seller = jsonObject.getString("seller");
			String context = "社区发布";
			String price = jsonObject.getString("price");
			String uid = jsonObject.getString("userid");
			
			float  price1 =  (float)(Math.round(Float.valueOf(price)*10000))/100;
			int priceceten=(int)price1;
			
			if("121".equals(uid)){
				System.out.println("---测试账号---");
				JxReleaseOrder releaseOrder = jxReleaseOrderService.findUnique("from jx_release_order where ord_no=?", out_trade_no );
				releaseOrder.setOrd_way(1);
				releaseOrder.setFb_modtime(new Date());
				releaseOrder.setFb_state(1);
				Jxpublish publish = merchantPublish.findUnique("from Jxpublish where ord_no = ?", out_trade_no);
				publish.setPub_modtime(new Date());
				publish.setFb_state(0);
				merchantPublish.save(publish);
				jxReleaseOrderService.save(releaseOrder);
				String str="亲~您发布的"+releaseOrder.getFb_type()+"已发布完成";
				str=str+" "+"支付金额为:"+releaseOrder.getFb_price()+"元";
				String title = "交易消息";
				JxMessages mess = PushController.Mssages(out_trade_no, title, str, Long.parseLong(releaseOrder.getU_id()+""));
				messageService.save(mess);
				String alias = releaseOrder.getU_id()+"";
				PushController.YHIOSMssage(alias,title,str);
				return new Result(Errors.ERROR_CODE);
			}else{
				
				System.out.println("---真实支付---");
				JxReleaseOrder releaseOrder = jxReleaseOrderService.findUnique("from jx_release_order where ord_no=?", out_trade_no );
				releaseOrder.setOrd_way(1);
				jxReleaseOrderService.save(releaseOrder);
				
				String notifyUrl = null;
				notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/releasepay/wxpayResult.v";
				
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
	@RequestMapping(value = "/releasepay/wxpayResult")
	public void wxpayResult(HttpServletRequest request, HttpServletResponse res) {
		PrintWriter out = null;
		try{
			out = res.getWriter();
		String xmlstr = HttpUtil.getRquestParamsByIOs(request);
		Map map = XMLUtil.doXMLParse(xmlstr);
		if(WxPayParamsUtil.APP_ID.equals(map.get("appid"))&&WxPayParamsUtil.PARTNER.equals(map.get("mch_id"))&&"SUCCESS".equals(map.get("result_code"))){
			String out_trade_no=(String)map.get("out_trade_no");
			out_trade_no=out_trade_no.substring(0, 15);
			JxReleaseOrder releaseOrder = jxReleaseOrderService.findUnique("from jx_release_order where ord_no=?", out_trade_no );
			releaseOrder.setFb_modtime(new Date());
			releaseOrder.setFb_state(1);;
			Jxpublish publish = merchantPublish.findUnique("from Jxpublish where ord_no = ?", out_trade_no);
			publish.setFb_state(0);
			jxReleaseOrderService.save(releaseOrder);
			merchantPublish.save(publish);
			String str="亲~您发布的"+releaseOrder.getFb_type()+"已发布完成";
			str=str+" "+"支付金额为:"+releaseOrder.getFb_price()+"元";
			String title = "交易消息";
			JxMessages mess = PushController.Mssages(out_trade_no, title, str, Long.parseLong(releaseOrder.getU_id()+""));
			messageService.save(mess);
			String alias = releaseOrder.getU_id()+"";
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
	
	
	
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/releasepay/unionpay", method = RequestMethod.POST)
	public Result releaseYLPayUnionpay(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			System.out.println("---银联支付开始---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String orderId = jsonObject.getString("ord_no");//订单号
			String context = jsonObject.getString("seller");
			String price = jsonObject.getString("price");
			String uid = jsonObject.getString("userid");
			
			int price1 = (Math.round(Float.valueOf(price)*10000))/100;
			price=price1+"";
			
			if("121".equals(uid)){
				System.out.println("---测试账号---");
				JxReleaseOrder releaseOrder = jxReleaseOrderService.findUnique("from jx_release_order where ord_no=?", orderId );
				releaseOrder.setOrd_way(2);
				releaseOrder.setFb_modtime(new Date());
				releaseOrder.setFb_state(1);
				Jxpublish publish = merchantPublish.findUnique("from Jxpublish where ord_no = ?", orderId);
				publish.setPub_modtime(new Date());
				publish.setFb_state(0);
				merchantPublish.save(publish);
				jxReleaseOrderService.save(releaseOrder);
				String str="亲~您发布的"+releaseOrder.getFb_type()+"已发布完成";
				str=str+" "+"支付金额为:"+releaseOrder.getFb_price()+"元";
				String title = "交易消息";
				JxMessages mess = PushController.Mssages(orderId, title, str, Long.parseLong(releaseOrder.getU_id()+""));
				messageService.save(mess);
				String alias = releaseOrder.getU_id()+"";
				PushController.YHIOSMssage(alias,title,str);
				return new Result(Errors.ERROR_CODE);
			}else{
				
				System.out.println("---真实支付---");
				//正则匹配特殊字符
				String str="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
				if(orderId.contains(str)){
					return new Result(Errors.USER_ERROR_NOT_CORRECT);
				}else if(orderId.contains("-")){
					return new Result(Errors.USER_ERROR_NOT_CORRECT);
				}else{
				
				JxReleaseOrder releaseOrder = jxReleaseOrderService.findUnique("from jx_release_order where ord_no=?", orderId );
				releaseOrder.setOrd_way(2);
				jxReleaseOrderService.save(releaseOrder);
				
				String notifyUrl = null;
				notifyUrl = "http://www.szjxzn.tech:8080/jx_smart/smvc/releasepay/UnionpayResult.v";
				
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
				System.out.println("2222---》"+rspData);
				Map<String,String> map = new HashMap<String, String>();
				if(!rspData.isEmpty()){
					if(AcpService.validate(rspData, DemoBase.encoding)){
						LogUtil.writeLog("验证签名成功");
						String respCode = rspData.get("respCode") ;
						//业务逻辑
						if(("00").equals(respCode)){
							//成功,获取tn号
							String tn = rspData.get("tn");
							map.put("tn", tn);
						}else if(("12").equals(respCode)){
							//其他应答码为失败请排查原因或做失败处理
							return new Result(Errors.USER_ERROR_REPEAT);
						}else if(("01").equals(respCode)){
							return new Result(Errors.TRANSACTION_FAILURE);
						}else if(("02").equals(respCode)){
							return new Result(Errors.SYSTEM_IS_NOT_OPEN);
						}else if(("64").equals(respCode)){
							return new Result(Errors.LACK_OF_BALANCE);
							
						}
					}else{
						LogUtil.writeErrorLog("验证签名失败");
						//TODO 检查验证签名失败的原因
					}
				}else{
					//未返回正确的http状态
					LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
				}
				String reqMessage = DemoBase.genHtmlResult(reqData);
				String rspMessage = DemoBase.genHtmlResult(rspData);
				
				//response.getWriter().write(reqMessage+"<br/>" +rspMessage+"");
				
				//out.write("请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
				//list.add(contentData);
				//list.add(rspData);
				list.add(map);
				System.out.println("---->集合"+list);
				return new SecretResult(Errors.OK, list);
				}
			}
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
	@RequestMapping(value = "/releasepay/UnionpayResult")
	@ResponseBody
	public void ReleaseYLPayBackRcvResponse(HttpServletRequest request, HttpServletResponse response) {
		
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
	   			System.out.println("3333");
	   		} else {
	   			LogUtil.writeLog("验证签名结果[成功].");
	   			//获取后台通知的数据，其他字段也可用类似方式获取
	   			String orderId =valideData.get("orderId"); 
	   			//String orderId = "277925655945040";
	   			//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
				String respCode = valideData.get("respCode");
	   			//String respCode = "00";
				//判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
	   			//【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态
				if(("00").equals(respCode)){
				JxReleaseOrder releaseOrder = jxReleaseOrderService.findUnique("from jx_release_order where ord_no=?", orderId );
				releaseOrder.setFb_modtime(new Date());
				releaseOrder.setFb_state(1);;
				Jxpublish publish = merchantPublish.findUnique("from Jxpublish where ord_no = ?", orderId);
				publish.setFb_state(0);
				jxReleaseOrderService.save(releaseOrder);
				merchantPublish.save(publish);
				String str="亲~您发布的"+releaseOrder.getFb_type()+"已发布完成";
				str=str+" "+"支付金额为:"+releaseOrder.getFb_price()+"元";
				String title = "交易消息";
				JxMessages mess = PushController.Mssages(orderId, title, str, Long.parseLong(releaseOrder.getU_id()+""));
				messageService.save(mess);
				String alias = releaseOrder.getU_id()+"";
				PushController.YHIOSMssage(alias,title,str);
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
