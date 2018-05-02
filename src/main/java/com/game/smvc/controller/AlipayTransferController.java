package com.game.smvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.game.bmanager.service.IJxPartnerService;
import com.game.smvc.dao.WebDao;
import com.game.smvc.entity.JxInformationSafety;
import com.game.smvc.entity.JxMessages;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxPartnerMessages;
import com.game.smvc.entity.JxPartnerRebate;
import com.game.smvc.entity.JxWithdrawalOrder;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SingleDataResult;
import com.game.smvc.payUtil.AlipayConfig;
import com.game.smvc.payUtil.AlipayNotify;
import com.game.smvc.service.IJxAlipayAccountService;
import com.game.smvc.service.IJxInformationSafetyService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPartnerMessagesService;
import com.game.smvc.service.IJxPartnerRebateService;
import com.game.smvc.service.IJxRebatesService;
import com.game.smvc.service.IJxUserService;
import com.game.smvc.service.IJxWithdrawalOrderService;
import com.game.smvc.util.HttpUtil;
import com.game.util.Des;
import com.game.util.pay.AliSignUtils;

@Controller
@RequestMapping({ "/smvc" })
public class AlipayTransferController {

	@Autowired
	private WebDao webDao;
	@Autowired
	private IJxUserService accUserService;
	@Autowired
	private IJxPartnerService partnerService;
	@Autowired
	private IJxOrderService jxOrderService;
	@Autowired
	private IJxInformationSafetyService jxInformationSafetyService;
	@Autowired
	private IJxRebatesService jxRebatesService;
	@Autowired
	private IJxPartnerRebateService jxPartnerRebateService;
	@Autowired
	private IJxWithdrawalOrderService jxWithdrawalOrderService;
	@Autowired
	private IJxAlipayAccountService jxAlipayAccountService;
	@Autowired
	private IJxPartnerMessagesService JxPartnerMessagesService;
	private JxWithdrawalOrder jxWithdrawalOrder;
	private JxPartnerRebate jxPartnerRebate;

	@ResponseBody
	@RequestMapping(value = "/partner/alipaytransfer")
	public Result alipayTransfer(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			/*
			 * String safetyMark = jsonObject.getString("safetyMark"); String
			 * out_biz_no = jsonObject.getString("withdrawal_order_no");
			 */
			String safetyMark = "1XwzH/J4kswq6IwT3ZSHMdkS4spxX2bVqDxq3u85";
			String out_biz_no = "TX934560823183037";
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains("jxsmart")) {
					username = safety.getUser_name();
					JxWithdrawalOrder withdrawalOrder = jxWithdrawalOrderService
							.findUnique("from JxWithdrawalOrder where withdrawal_order = '"
									+ out_biz_no
									+ "' and withdrawal_state = 3 ");
					if (withdrawalOrder == null) {
						return new Result(Errors.NO_REVIEW_OF_SUCCESSFUL_ORDERS);
					}

					String payee_type = "ALIPAY_LOGONID";// 收款方账户类型
					String gatewayUrl = AlipayConfig.gatewayUrl;
					String app_id = AlipayConfig.app_id;
					String payee_account = "15129086145";
					String amount = withdrawalOrder.getWithdrawal_amount() + "";// 转账金额
					float price1 = (float) (Math
							.round(Float.valueOf(amount) * 100)) / 100;
					amount = price1 + "";
					String rsa_private = AlipayConfig.private_key;// 商户私钥，pkcs8格式
					String rsa_public = AlipayConfig.alipay_public_zh_key;// 支付宝公钥
					String remark = "转账备注信息";
					AlipayClient alipayClient = new DefaultAlipayClient(
							gatewayUrl, app_id, rsa_private,
							AlipayConfig.format, AlipayConfig.input_charset,
							rsa_public, AlipayConfig.sign_type);
					AlipayFundTransToaccountTransferRequest requests = new AlipayFundTransToaccountTransferRequest();
					AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
					// 商户转账唯一订单号
					model.setOutBizNo(out_biz_no);
					// 收款方账户类型。
					// 1、ALIPAY_USERID：pid ,以2088开头的16位纯数字组成。
					// 2、ALIPAY_LOGONID：支付宝登录号(邮箱或手机号)
					model.setPayeeType(payee_type);
					// 收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
					model.setPayeeAccount(payee_account);
					// 测试金额必须大于等于0.1，只支持2位小数，小数点前最大支持13位
					model.setAmount("0.1");
					// 当付款方为企业账户且转账金额达到（大于等于）50000元，remark不能为空。
					model.setRemark(remark);
					requests.setBizModel(model);
					AlipayFundTransToaccountTransferResponse response = alipayClient
							.execute(requests);
					System.out.println(response.getBody());
					System.out.println(response.getMsg());

					if (response.isSuccess()) {
						System.out.println("调用成功");
						// 成功之后的逻辑
						// 设置订单状态
						withdrawalOrder.setWithdrawal_state(200);
						withdrawalOrder.setLast_modtime(new Date());
						withdrawalOrder.setArrive_time(new Date());// 到账时间
						JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
								.findUnique("from jx_partner_rebate where withdrawal_order = '"
										+ out_biz_no + "' and w_state = 1");
						jxPartnerRebate.setW_state(200);
						Date at = jxPartnerRebate.getAdd_time();
						Date mt = jxPartnerRebate.getMod_time();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String addtime = sdf.format(at);
						String modtime = sdf.format(mt);
						String user_name = jxPartnerRebate.getUser_name();
						// 设置订单状态
						int jxOrder = jxOrderService.updateTradeStateToSuccess(
								addtime, modtime, user_name);
						jxPartnerRebateService.save(jxPartnerRebate);
						jxWithdrawalOrderService.save(withdrawalOrder);
						// 消息推送
						String alias = withdrawalOrder.getUser_number();
						String title = "提现消息";
						String content = "您刚发起的提现单(" + out_biz_no + "),已成功到账。";
						PushPartnerController.PartnerMssage(alias, title,
								content);
						int p_type = 10;
						JxPartnerMessages mess = PushPartnerController
								.partnerMessage(out_biz_no, content, alias,
										title, p_type);
						JxPartnerMessagesService.save(mess);
						return new Result(Errors.OK);
					} else {
						System.out.println("调用失败");
						// 失败的逻辑
						// 消息推送
						String alias = username;
						String title = "提现消息";
						String content = "您刚发起的提现单(" + out_biz_no
								+ "),处理失败。具体原因可查看提现详情";
						PushPartnerController.PartnerMssage(alias, title,
								content);
						int p_type = 11;
						JxPartnerMessages mess = PushPartnerController
								.partnerMessage(out_biz_no, content, alias,
										title, p_type);
						JxPartnerMessagesService.save(mess);
						return new SingleDataResult(Errors.OK,
								response.getSubMsg());
					}

				} else {
					return new Result(Errors.SECURITY_VERIFICATION_FAILED);
				}
			} else {
				return new Result(Errors.SECURITY_VERIFICATION_FAILED);
			}

		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}

	// 查询转账是否到账
	@ResponseBody
	@RequestMapping(value = "/partner/queryalipaytransfer")
	public Result queryAlipayTransfer(HttpServletRequest request,HttpServletResponse res) {
		try {
			
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String out_biz_no = jsonObject.getString("withdrawal_order");
			String rsa_private = AlipayConfig.private_key;
			JxWithdrawalOrder withdrawalOrder = jxWithdrawalOrderService
					.findUnique("from JxWithdrawalOrder where withdrawal_order = '"
							+ out_biz_no + "' and withdrawal_state = 3");
			AlipayClient alipayClient = new DefaultAlipayClient(
					AlipayConfig.gatewayUrl, AlipayConfig.app_id, rsa_private,
					AlipayConfig.format, AlipayConfig.input_charset,
					AlipayConfig.alipay_public_zh_key, AlipayConfig.sign_type);
			AlipayFundTransOrderQueryRequest request1 = new AlipayFundTransOrderQueryRequest();
			AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();
			// 商户转账唯一订单号
			model.setOutBizNo(out_biz_no);
			// 支付宝转账单据号：和商户转账唯一订单号不能同时为空。二选一传入
			request1.setBizModel(model);
			AlipayFundTransOrderQueryResponse response = alipayClient
					.execute(request1);
			if (response.isSuccess()) {
				System.out.println("调用成功");
				// 成功之后的逻辑
				// 设置订单状态
				withdrawalOrder.setWithdrawal_state(200);
				withdrawalOrder.setLast_modtime(new Date());
				withdrawalOrder.setArrive_time(new Date());// 到账时间
				JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
						.findUnique("from jx_partner_rebate where withdrawal_order = '"
								+ out_biz_no + "' and w_state = 1");
				jxPartnerRebate.setW_state(3);
				Date at = jxPartnerRebate.getAdd_time();
				Date mt = jxPartnerRebate.getMod_time();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String addtime = sdf.format(at);
				String modtime = sdf.format(mt);
				String user_name = jxPartnerRebate.getUser_name();
				// 设置订单状态
				int jxOrder = jxOrderService.updateTradeStateToSuccess(addtime,
						modtime, user_name);
				jxPartnerRebateService.save(jxPartnerRebate);
				jxWithdrawalOrderService.save(withdrawalOrder);
				// 消息推送
				String alias = withdrawalOrder.getUser_number();
				String title = "提现消息";
				String content = "您刚发起的提现单(" + out_biz_no + "),已成功到账。";
				PushPartnerController.PartnerMssage(alias, title, content);
				int p_type = 10;
				JxPartnerMessages mess = PushPartnerController.partnerMessage(
						out_biz_no, content, alias, title, p_type);
				JxPartnerMessagesService.save(mess);
				return new Result(Errors.OK);

			} else {
				System.out.println("调用失败");
				// 失败的逻辑
				return new SingleDataResult(Errors.OK, response.getSubMsg());
			}

		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}
	
	
	@SuppressWarnings("unused")
	public Result transfer(String out_biz_no) throws AlipayApiException{
		
		JxWithdrawalOrder withdrawalOrder = jxWithdrawalOrderService
				.findUnique("from JxWithdrawalOrder where withdrawal_order = '"
						+ out_biz_no
						+ "' and withdrawal_state = 3 ");
		if (withdrawalOrder == null) {
			return new Result(Errors.NO_REVIEW_OF_SUCCESSFUL_ORDERS);
		}

		String payee_type = "ALIPAY_LOGONID";// 收款方账户类型
		String gatewayUrl = AlipayConfig.gatewayUrl;
		String app_id = AlipayConfig.app_id;
		String payee_account = withdrawalOrder.getPay_account();
		String amount = withdrawalOrder.getWithdrawal_amount() + "";// 转账金额
		float price1 = (float) (Math
				.round(Float.valueOf(amount) * 100)) / 100;
		amount = price1 + "";
		String rsa_private = AlipayConfig.private_key;// 商户私钥，pkcs8格式
		String rsa_public = AlipayConfig.alipay_public_zh_key;// 支付宝公钥
		String remark = "转账备注信息";
		AlipayClient alipayClient = new DefaultAlipayClient(
				gatewayUrl, app_id, rsa_private,
				AlipayConfig.format, AlipayConfig.input_charset,
				rsa_public, AlipayConfig.sign_type);
		AlipayFundTransToaccountTransferRequest requests = new AlipayFundTransToaccountTransferRequest();
		AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
		// 商户转账唯一订单号
		model.setOutBizNo(out_biz_no);
		// 收款方账户类型。
		// 1、ALIPAY_USERID：pid ,以2088开头的16位纯数字组成。
		// 2、ALIPAY_LOGONID：支付宝登录号(邮箱或手机号)
		model.setPayeeType(payee_type);
		// 收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
		model.setPayeeAccount(payee_account);
		// 测试金额必须大于等于0.1，只支持2位小数，小数点前最大支持13位
		model.setAmount("0.1");
		// 当付款方为企业账户且转账金额达到（大于等于）50000元，remark不能为空。
		model.setRemark(remark);
		requests.setBizModel(model);
		AlipayFundTransToaccountTransferResponse response = alipayClient
				.execute(requests);
		System.out.println(response.getBody());
		System.out.println(response.getMsg());

		if (response.isSuccess()) {
			System.out.println("调用成功");
			// 成功之后的逻辑
			// 设置订单状态
			withdrawalOrder.setWithdrawal_state(200);
			withdrawalOrder.setLast_modtime(new Date());
			withdrawalOrder.setArrive_time(new Date());// 到账时间
			JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
					.findUnique("from jx_partner_rebate where withdrawal_order = '"
							+ out_biz_no + "' and w_state = 1");
			jxPartnerRebate.setW_state(200);
			Date at = jxPartnerRebate.getAdd_time();
			Date mt = jxPartnerRebate.getMod_time();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String addtime = sdf.format(at);
			String modtime = sdf.format(mt);
			String user_name = jxPartnerRebate.getUser_name();
			// 设置订单状态
			int jxOrder = jxOrderService.updateTradeStateToSuccess(
					addtime, modtime, user_name);
			jxPartnerRebateService.save(jxPartnerRebate);
			jxWithdrawalOrderService.save(withdrawalOrder);
			// 消息推送
			String alias = withdrawalOrder.getUser_number();
			String title = "提现消息";
			String content = "您刚发起的提现单(" + out_biz_no + "),已成功到账。";
			PushPartnerController.PartnerMssage(alias, title,
					content);
			int p_type = 10;
			JxPartnerMessages mess = PushPartnerController
					.partnerMessage(out_biz_no, content, alias,
							title, p_type);
			JxPartnerMessagesService.save(mess);
			return new Result(Errors.OK);
		} else {
			System.out.println("调用失败");
			// 失败的逻辑
			// 消息推送
			String alias = withdrawalOrder.getUser_number();
			String title = "提现消息";
			String content = "您刚发起的提现单(" + out_biz_no
					+ "),处理失败。具体原因可查看提现详情";
			PushPartnerController.PartnerMssage(alias, title,
					content);
			int p_type = 11;
			JxPartnerMessages mess = PushPartnerController
					.partnerMessage(out_biz_no, content, alias,
							title, p_type);
			JxPartnerMessagesService.save(mess);
			return new SingleDataResult(Errors.OK,
					response.getSubMsg());
		}

	
		
	}

	/*
	 * public static void main(String[] args) throws AlipayApiException {
	 * AlipayClient alipayClient = new
	 * DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.partner,
	 * AlipayConfig.private_key, AlipayConfig.format,
	 * AlipayConfig.input_charset, AlipayConfig.alipay_public_key,
	 * AlipayConfig.sign_type); AlipayFundTransToaccountTransferRequest request
	 * = new AlipayFundTransToaccountTransferRequest();
	 * AlipayFundTransToaccountTransferModel model = new
	 * AlipayFundTransToaccountTransferModel(); //商户转账唯一订单号
	 * model.setOutBizNo("TX883650823143354"); //收款方账户类型。 //1、ALIPAY_USERID：pid
	 * ,以2088开头的16位纯数字组成。 //2、ALIPAY_LOGONID：支付宝登录号(邮箱或手机号)
	 * model.setPayeeType("ALIPAY_LOGONID");
	 * //收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
	 * model.setPayeeAccount("17665288801"); //测试金额必须大于等于0.1，只支持2位小数，小数点前最大支持13位
	 * model.setAmount("0.2"); //当付款方为企业账户且转账金额达到（大于等于）50000元，remark不能为空。
	 * model.setRemark("转账备注"); request.setBizModel(model);
	 * AlipayFundTransToaccountTransferResponse response =
	 * alipayClient.execute(request); System.out.println(response.getBody());
	 * if(response.isSuccess()){ System.out.println("调用成功"); } else {
	 * System.out.println("调用失败"); } }
	 */

	/**
	 * 
	 * @param request
	 * @param res
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/alipaytransfer/alipayResult")
	public void AliPayResults(HttpServletRequest request,
			HttpServletResponse res) {
		System.out.println("2");
		PrintWriter out;
		try {
			System.out.println("3");
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
			String out_trade_no = new String(request.getParameter("out_biz_no")
					.getBytes("ISO-8859-1"), "UTF-8");

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
			// 批量付款数据中转账成功的详细信息
			String success_details = request.getParameter("success_details") != null ? request
					.getParameter("success_details") : "";

			// 批量付款数据中转账失败的详细信息
			String fail_details = request.getParameter("fail_details") != null ? request
					.getParameter("fail_details") : "";

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			System.out.println("4");
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
							System.out.println("5");
							JxWithdrawalOrder withdrawalOrder = jxWithdrawalOrderService
									.findUnique("from JxWithdrawalOrder where withdrawal_order = '"
											+ out_trade_no + "'");
							withdrawalOrder.setWithdrawal_state(1);
							withdrawalOrder.setLast_modtime(new Date());
							JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
									.findUnique("from jx_partner_rebate where withdrawal_order = '"
											+ out_trade_no + "'");
							jxPartnerRebate.setW_state(1);
							Date at = jxPartnerRebate.getAdd_time();
							Date mt = jxPartnerRebate.getMod_time();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							String addtime = sdf.format(at);
							String modtime = sdf.format(mt);
							String user_name = jxPartnerRebate.getUser_name();
							int jxOrder = jxOrderService.updateTradeState(
									addtime, modtime, user_name);
							jxPartnerRebateService.save(jxPartnerRebate);
							jxWithdrawalOrderService.save(withdrawalOrder);
							System.out.println("6");
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

	public JxPartnerRebate getJxPartnerRebate() {
		return jxPartnerRebate;
	}

	public void setJxPartnerRebate(JxPartnerRebate jxPartnerRebate) {
		this.jxPartnerRebate = jxPartnerRebate;
	}

	public JxWithdrawalOrder getJxWithdrawalOrder() {
		return jxWithdrawalOrder;
	}

	public void setJxWithdrawalOrder(JxWithdrawalOrder jxWithdrawalOrder) {
		this.jxWithdrawalOrder = jxWithdrawalOrder;
	}

}
