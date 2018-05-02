package com.game.smvc.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.game.bmanager.entity.JxPartner;
import com.game.bmanager.service.IJxPartnerService;
import com.game.smvc.dao.WebDao;
import com.game.smvc.entity.JxAlipayAccount;
import com.game.smvc.entity.JxDrawPeople;
import com.game.smvc.entity.JxInformationSafety;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxPartnerMessages;
import com.game.smvc.entity.JxPartnerRebate;
import com.game.smvc.entity.JxRebateProportion;
import com.game.smvc.entity.JxRebates;
import com.game.smvc.entity.JxWithdrawalOrder;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.entity.result.SingleDataResult;
import com.game.smvc.payUtil.AlipayConfig;
import com.game.smvc.service.IJxAlipayAccountService;
import com.game.smvc.service.IJxDrawPeopleService;
import com.game.smvc.service.IJxInformationSafetyService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPartnerMessagesService;
import com.game.smvc.service.IJxPartnerRebateService;
import com.game.smvc.service.IJxRebateProportionService;
import com.game.smvc.service.IJxRebatesService;
import com.game.smvc.service.IJxUserService;
import com.game.smvc.service.IJxWithdrawalOrderService;
import com.game.smvc.util.HttpUtil;
import com.game.smvc.util.IdentifyingUtil;
import com.game.smvc.util.RandomUtil;
import com.game.util.Des;

/**
 * 提现管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping({ "/smvc" })
public class TradeController {

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
	private IJxRebateProportionService jxRebateProportionService;
	@Autowired
	private IJxDrawPeopleService jxDrawPeopleService;
	@Autowired
	private IJxPartnerMessagesService JxPartnerMessagesService;

	/**
	 * 统计合伙人销售台数
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/salesamount")
	public Result salesAmount(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String withdrawalOrderNo = jsonObject
					.getString("withdrawal_order_no");
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
					// 根据产品经理编号得出销售台数
					JxPartner jxPartner = null;
					if (username.contains("A") || username.contains("B")
							|| username.contains("C")) {
						jxPartner = partnerService
								.findUnique("from JxPartner where par_other = '"
										+ username + "'");
					} else {
						jxPartner = partnerService
								.findUnique("from JxPartner where id = '"
										+ username + "'");
					}

					// 返利
					JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
							.findUnique("from jx_partner_rebate where withdrawal_order = '"
									+ withdrawalOrderNo + "'");
					if (jxPartnerRebate == null) {
						return new Result(
								Errors.THE_WITHDRAWAL_ORDER_IS_incorrect);
					}
					// 得到下级
					List<Map<String, Object>> direct_subordinates = jxDrawPeopleService
							.findDrts(withdrawalOrderNo);

					// List<Map<String, Object>> list = new
					// ArrayList<Map<String, Object>>();
					Map<String, Object> date = new HashMap<String, Object>();

					// 查询订单
					JxWithdrawalOrder withdrawalOrder = jxWithdrawalOrderService
							.findUnique("from JxWithdrawalOrder where withdrawal_order = '"
									+ withdrawalOrderNo + "'");
					
					if (withdrawalOrder == null) {
						
						date.put("state", -1);
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date s = jxPartnerRebate.getAdd_time();
						String sales_time = sdf.format(s);
						// 产品明细
						date.put("wall", jxPartnerRebate.getSell_wall());// 壁挂式
						date.put("vertical", jxPartnerRebate.getSell_vertical());// 立式
						date.put("desktop", jxPartnerRebate.getSell_desktop());// 台式
						date.put("wall_renew", jxPartnerRebate.getWall_renew());// 壁挂式续费
						date.put("vertical_renew",
								jxPartnerRebate.getVertical_renew());// 立式续费
						date.put("desktop_renew",
								jxPartnerRebate.getDesktop_renew());// 台式续费
						// map.put("isshop", jxPartner.getPar_shop());// 是否建店 1为已建店
						// 0为未建店
						date.put("ispact", jxPartnerRebate.getPar_pact());// 是否按照合同
						date.put("withdrawal_total_amount",
								jxPartnerRebate.getTotal_amount());// 提现总金额
						date.put("service_fee", jxPartnerRebate.getService_fee());// 服务费返利
						date.put("renewal", jxPartnerRebate.getF_renewal());// 续费返利
						// map.put("build_store",
						// jxPartnerRebate.getBuild_store());// 建店续费返利
						date.put("installation",
								jxPartnerRebate.getF_installation());// 安装费返利
						date.put("lower_rebate", jxPartnerRebate.getLower_rebate());// 下级返利
						date.put("withdrawal_order_no", withdrawalOrderNo);// 提现单号
						date.put("sales_time", sales_time);// 销售时间
						date.put("direct_subordinates", direct_subordinates);//下级
						date.put("user_number", jxPartnerRebate.getUser_name());//提现人编号
						
						date.put("buy_combined", jxPartnerRebate.getBuy_combined());//购买型合计(去押金)
						date.put("renewal_combined", jxPartnerRebate.getRenewal_combined());//续费型合计(去押金)
						date.put("wdl_fee", jxPartnerRebate.getWdl_fee());//返利比例
						date.put("rwl_install", jxPartnerRebate.getRwl_install());//安装费比例
						
					} else {
						date.put("state", withdrawalOrder.getWithdrawal_state());
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date s = jxPartnerRebate.getAdd_time();
						String sales_time = sdf.format(s);
						// 产品明细
						date.put("wall", withdrawalOrder.getSell_wall());// 壁挂式
						date.put("vertical", withdrawalOrder.getSell_vertical());// 立式
						date.put("desktop", withdrawalOrder.getSell_desktop());// 台式
						date.put("wall_renew", withdrawalOrder.getWall_renew());// 壁挂式续费
						date.put("vertical_renew",
								withdrawalOrder.getVertical_renew());// 立式续费
						date.put("desktop_renew",
								withdrawalOrder.getDesktop_renew());// 台式续费
						// map.put("isshop", jxPartner.getPar_shop());// 是否建店 1为已建店
						// 0为未建店
						date.put("ispact", withdrawalOrder.getPar_pact());// 是否按照合同
						date.put("withdrawal_total_amount",
								withdrawalOrder.getTotal_amount());// 提现总金额
						date.put("service_fee", withdrawalOrder.getService_fee());// 服务费返利
						date.put("renewal", withdrawalOrder.getF_renewal());// 续费返利
						// map.put("build_store",
						// jxPartnerRebate.getBuild_store());// 建店续费返利
						date.put("installation",
								withdrawalOrder.getF_installation());// 安装费返利
						date.put("lower_rebate", withdrawalOrder.getLower_rebate());// 下级返利
						date.put("withdrawal_order_no", withdrawalOrderNo);// 提现单号
						date.put("sales_time", sales_time);// 销售时间
						date.put("direct_subordinates", direct_subordinates);//下级
						date.put("user_number", withdrawalOrder.getUser_number());//提现人编号
						/*date.put("pay_name", withdrawalOrder.getPay_name());//支付宝名称
						date.put("pay_account", withdrawalOrder.getPay_account());//支付宝账号
*/						date.put("buy_combined", withdrawalOrder.getBuy_combined());//购买型合计(去押金)
						date.put("renewal_combined", withdrawalOrder.getRenewal_combined());//续费型合计(去押金)
						date.put("wdl_fee", withdrawalOrder.getWdl_fee());//返利比例
						date.put("rwl_install", withdrawalOrder.getRwl_install());//安装费比例
					}
					
					// list.add(date);
					return new SecretResult(Errors.OK, date);
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

	/**
	 * 提现下级详情 207/09/05
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/lowerdetails")
	public Result lowerDetails(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String id = jsonObject.getString("id");
			String withdrawalOrderNo = jsonObject
					.getString("withdrawal_order_no");
			JxInformationSafety safety = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					// 根据产品经理编号得出销售台数
					String username = jxDrawPeopleService.findUsernameById(id);

					JxPartner jxPartner = null;
					if (username.contains("A") || username.contains("B")
							|| username.contains("C")) {
						jxPartner = partnerService
								.findUnique("from JxPartner where par_other = '"
										+ username + "'");
					} else {
						jxPartner = partnerService
								.findUnique("from JxPartner where id = '"
										+ username + "'");
					}

					// 返利
					JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
							.findUnique("from jx_partner_rebate where withdrawal_order = '"
									+ withdrawalOrderNo + "'");
					if (jxPartnerRebate == null) {
						return new Result(
								Errors.THE_WITHDRAWAL_NUMBER_IS_INCORRECT);
					}

					JxDrawPeople drawPeople = jxDrawPeopleService
							.findUnique("from jx_draw_people where withdrawal_order = '"
									+ withdrawalOrderNo
									+ "' and id = "
									+ id
									+ "");
					if (drawPeople == null) {
						// 订单号不存在
						return new Result(
								Errors.THE_WITHDRAWAL_NUMBER_IS_INCORRECT);
					}

					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Map<String, Object> date = new HashMap<String, Object>();

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String sales_time = sdf.format(jxPartnerRebate
							.getAdd_time());

					// 产品明细
					date.put("wall", drawPeople.getSell_wall());// 壁挂式
					date.put("vertical", drawPeople.getSell_vertical());// 立式
					date.put("desktop", drawPeople.getSell_desktop());// 台式
					date.put("wall_renew", drawPeople.getWall_renew());// 壁挂式续费
					date.put("vertical_renew", drawPeople.getVertical_renew());// 立式续费
					date.put("desktop_renew", drawPeople.getDesktop_renew());// 台式续费
					date.put("ispact", jxPartner.getPar_pact());// 是否按照合同 1按照合同
					date.put("withdrawal_total_amount",
							drawPeople.getBy_tkr_total_money());// 提现总金额
					date.put("service_fee", drawPeople.getService_fee());// 服务费返利
					date.put("renewal", drawPeople.getF_renewal());// 续费返利
					date.put("withdrawal_order_no", withdrawalOrderNo);// 提现单号
					date.put("sales_time", sales_time);// 销售时间
					date.put("by_tkr_rebates", drawPeople.getBy_tkr_rebates());//下级被提现比例
					date.put("total_money", drawPeople.getTotal_money());//去押金下级总金额
					list.add(date);
					return new SecretResult(Errors.OK, list);
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

	/**
	 * 提现订单 -->提现发起
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/withdrawalorder")
	public Result withdrawalOrder(HttpServletRequest request) {
		try {
			System.out.println("---提现发起---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			// 订单号
			String withdrawal_order_no = jsonObject
					.getString("withdrawal_order_no");

			String username = null;
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Map<String, Object> m = new HashMap<String, Object>();
					JxPartnerRebate rebate = jxPartnerRebateService
							.findUnique("from jx_partner_rebate where withdrawal_order = '"
									+ withdrawal_order_no + "' ");
					if (rebate == null) {
						return new Result(
								Errors.THE_WITHDRAWAL_NUMBER_IS_INCORRECT);
					}
					String s1 = rebate.getUser_name();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					// Date d = jxOrderService.finTime(s1);
					Map<String, Object> date = jxOrderService.finTime(s1);
					Date d = (Date) date.get("ord_addtime");
					if (d == null) {
						d = new Date();
					}
					// 取出最后一次提现成功的时间
					List<Map<String, Object>> mm = jxPartnerRebateService
							.findLastAddtime(rebate.getUser_name());
					String last_add_time = null;
					Date last_time = null;
					if (mm.size() <= 0) {
						last_add_time = "1970-01-01 00:00:00";
						//时间取购买订单的最初时间
						last_time = sdf.parse(last_add_time);
					} else {
						Map<String, Object> ma = mm.get(0);
						Date s = (Date) ma.get("add_time");
						last_add_time = sdf.format(s);
						last_time = (Date) ma.get("add_time");
					}
					// 金额低于0.1元不能提现
					if(rebate.getTotal_amount() < IdentifyingUtil.leastMoney()){
						return new
							 Result(Errors.THE_AMOUNT_IS_TOO_LOE_TO_WITHDRAW_CASH); 
					}
					  
					if(rebate.getTotal_amount() > IdentifyingUtil.maxMoney()){
						 return new
								  Result(Errors.THE_AMOUNT_IS_TOO_LOE_TO_WITHDRAW_MAX_CASH);
					} 
					username = safety.getUser_name();
					JxAlipayAccount account = jxAlipayAccountService
							.findUnique("from JxAlipayAccount where p_number = '"
									+ username + "'");
					if (account == null) {
						return new Result(Errors.THERE_IS_NO_BINDING_ALIPAY);
					}
					List<Map<String, Object>> realNmae = accUserService
							.findUserName(username);
					if (realNmae == null || realNmae.size() <= 0) {
						return new Result(Errors.NO_PARTNER);
					}
					Map<String, Object> map = realNmae.get(0);
					String real_name = (String) map.get("real_name");// 合伙人姓名
					Date time = new Date();
					time = rebate.getAdd_time();
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(time);
					gc.add(5, 3);
					Date t = gc.getTime();
					JxWithdrawalOrder jxWithdrawalOrder = new JxWithdrawalOrder();
					jxWithdrawalOrder.setWithdrawal_order(withdrawal_order_no);
					jxWithdrawalOrder.setUser_number(username);
					jxWithdrawalOrder.setReal_name(real_name);
					jxWithdrawalOrder.setPay_name(account.getPay_name());
					jxWithdrawalOrder.setPay_account(account.getPay_account());
					jxWithdrawalOrder.setWithdrawal_way(0);
					jxWithdrawalOrder.setWithdrawal_state(0);
					jxWithdrawalOrder.setWithdrawal_amount(rebate
							.getTotal_amount());
					jxWithdrawalOrder.setAdd_time(time);
					jxWithdrawalOrder.setAudit_time(t);
					jxWithdrawalOrder.setAudit_person("admin");
					jxWithdrawalOrder.setLast_time(last_time);// 上次提现时间
					jxWithdrawalOrder.setTotal_amount(rebate.getTotal_amount());
					jxWithdrawalOrder.setService_fee(rebate.getService_fee());
					jxWithdrawalOrder.setF_installation(rebate.getF_installation());
					jxWithdrawalOrder.setF_renewal(rebate.getF_renewal());
					jxWithdrawalOrder.setLower_rebate(rebate.getLower_rebate());
					jxWithdrawalOrder.setSell_wall(rebate.getSell_wall());
					jxWithdrawalOrder.setSell_desktop(rebate.getSell_desktop());
					jxWithdrawalOrder.setSell_vertical(rebate.getSell_vertical());
					jxWithdrawalOrder.setWall_renew(rebate.getWall_renew());
					jxWithdrawalOrder.setDesktop_renew(rebate.getDesktop_renew());
					jxWithdrawalOrder.setVertical_renew(rebate.getVertical_renew());
					jxWithdrawalOrder.setBuy_combined(rebate.getBuy_combined());
					jxWithdrawalOrder.setRenewal_combined(rebate.getRenewal_combined());
					jxWithdrawalOrder.setPar_pact(rebate.getPar_pact());//是否按照合同
					jxWithdrawalOrder.setWdl_fee(rebate.getWdl_fee());//返利比例
					jxWithdrawalOrder.setRwl_install(rebate.getRwl_install());//安装费比例
					jxWithdrawalOrderService.save(jxWithdrawalOrder);
					m.put("withdrawal_amount", rebate.getTotal_amount());
					m.put("withdrawal_order_no", withdrawal_order_no);
					m.put("user_name", real_name);
					m.put("user_number", username);
					list.add(m);
					
					/*JxDrawPeople jxDrawPeople = jxDrawPeopleService.
							findUnique("from jx_draw_people where withdrawal_order = '"+withdrawal_order_no+"'");
					jxDrawPeople.setWithdrawal_state(0);
					jxDrawPeopleService.save(jxDrawPeople);*/
					
					int update_state = jxDrawPeopleService.findupdate_state(withdrawal_order_no);
					
					// 冻结资金
					String addtime = sdf.format(rebate.getAdd_time());
					String modtime = sdf.format(jxWithdrawalOrder
							.getAudit_time());

					System.out.println("addtime:" + addtime);
					System.out.println("last_add_time:" + last_add_time);
					// 资金冻结
					int jxOrder = jxOrderService.updateTradeState(addtime,
							last_add_time, username);
					rebate.setW_state(3);
					rebate.setWithdrawal_state(0);
					rebate.setAudit_person("admin");
					jxPartnerRebateService.save(rebate);
					// 判断是否有最高级 有最高级则往最高级发送一条消息
					// 如果当前级别为最高级则没有此消息
					String parentid = partnerService.findParentid(username);
					if (parentid != null && parentid.length() != 0) {
						Map<String, Object> highest = partnerService
								.findMostPartner(username);
						String par_parentid = (String) highest
								.get("par_parentid");
						if (par_parentid == null || par_parentid.length() == 0) {
							System.out.println("最高级:" + highest.get("id"));
							String title = "下级提现消息";
							String alias = highest.get("id") + "";
							String content = "您的下级" + username
									+ "发起了一次提现,提现单号为:" + withdrawal_order_no
									+ ",请尽快审批";
							int p_type = 12;
							title = PushPartnerController.lower_title(p_type);
							content = PushPartnerController.lower_content(username, withdrawal_order_no,p_type);
							PushPartnerController.PartnerMssage(alias, title,
									content);
							JxPartnerMessages mess = PushPartnerController
									.partnerMessage(withdrawal_order_no,
											content, alias, title, p_type);
							JxPartnerMessagesService.save(mess);
							// 保存审核人
							jxWithdrawalOrder.setAudit_person(highest
									.get("par_name") + "");
							rebate.setAudit_person(highest.get("par_name") + "");
							jxPartnerRebateService.save(rebate);
							jxWithdrawalOrderService.save(jxWithdrawalOrder);
						}
					}

					// 提现消息推送
					String alias = username;
					String title = "提现消息";
					String content = "您刚发起的提现单(" + withdrawal_order_no
							+ "),待人工审核，预计3个工作日。";
					int p_type = 7;
					title = PushPartnerController.lower_title(p_type);
					content = PushPartnerController.lower_content(username, withdrawal_order_no,p_type);
					PushPartnerController.PartnerMssage(alias, title, content);
					JxPartnerMessages mess = PushPartnerController
							.partnerMessage(withdrawal_order_no, content,
									alias, title, p_type);
					JxPartnerMessagesService.save(mess);

					return new SecretResult(Errors.OK, list);
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

	/**
	 * 提现记录
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/withdrawalrecord")
	public Result withdrawalRecord(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			int page = Integer.parseInt(jsonObject.getString("page"));
			String username = null;
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");

			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
					List<Map<String, Object>> list = jxWithdrawalOrderService
							.findAllWithdrawalOrder(username, (page - 1) * 10);

					List<Map<String, Object>> date = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> m = new HashMap<String, Object>();
						
						Map<String, Object> map = list.get(i);
						m.put("w_id", map.get("w_id"));// 订单id
						m.put("user_number", map.get("user_number"));// 产品经理编号
						m.put("real_name", map.get("real_name"));// 姓名
						m.put("withdrawal_order", map.get("withdrawal_order"));// 提现订单号
						m.put("withdrawal_amount", map.get("withdrawal_amount"));// 总金额
						m.put("withdrawal_way", map.get("withdrawal_way"));// 支付方式
						m.put("withdrawal_state", map.get("withdrawal_state"));// 状态
						m.put("withdrawal_reason", map.get("withdrawal_reason"));// 失败原因
						m.put("pay_name", map.get("pay_name"));// 支付宝账户名
						m.put("pay_account", map.get("pay_account"));// 支付宝账户
						Object ad = map.get("add_time");// 提现发起时间
						Object au = map.get("audit_time");// 审核时间
						Object ar = map.get("arrive_time");// 到账时间
						Object la = map.get("last_modtime");// 最后更新时间

						String add_time = String.valueOf(ad);
						String audit_time = String.valueOf(au);
						String arrive_time = String.valueOf(ar);
						String last_modtime = String.valueOf(la);
						m.put("add_time", add_time);
						m.put("audit_time", audit_time);
						m.put("arrive_time", arrive_time);
						m.put("last_modtime", last_modtime);
						date.add(m);
					}

					return new SecretResult(Errors.OK, date);
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

	/**
	 * 提现上级审核
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/withdrawalaudit")
	public Result withdrawalAudit(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			// 提现单号
			String withdrawal_order = jsonObject.getString("withdrawal_order");
			String state = jsonObject.getString("state");
			String reason = jsonObject.getString("reason");// 原因
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			String username = null;
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
					List<Map<String, Object>> realNmae = accUserService
							.findUserName(username);
					if (realNmae == null || realNmae.size() <= 0) {
						return new Result(Errors.NO_PARTNER);
					}
					JxPartner partner = partnerService
							.findUnique("from JxPartner where id = '"
									+ username + "'");
					String parentid = partner.getParParentid();
					if (parentid != null && parentid.length() != 0) {
						return new Result(Errors.INSUFFICIENT_PERMISSIONS);
					}
					JxWithdrawalOrder jxWithdrawalOrder = jxWithdrawalOrderService
							.findUnique("from JxWithdrawalOrder where withdrawal_order = '"
									+ withdrawal_order
									+ "' and withdrawal_state = 0");
					if (jxWithdrawalOrder == null) {
						return new Result(
								Errors.THE_WITHDRAWAL_ORDER_IS_incorrect);
					}
					if (state.equals("3")) {//审核通过
						Date time = new Date();
						GregorianCalendar gc = new GregorianCalendar();
						gc.setTime(time);
						gc.add(GregorianCalendar.MINUTE, 5);
						Date t = gc.getTime();
						jxWithdrawalOrder.setWithdrawal_state(3);
						jxWithdrawalOrder.setAudit_time(time);
						jxWithdrawalOrder.setArrive_time(t);
						jxWithdrawalOrderService.save(jxWithdrawalOrder);

						int update_state = jxDrawPeopleService.findupdate_states(withdrawal_order);
						
						// 提现消息推送
						String alias = jxWithdrawalOrder.getUser_number();
						String title = "提现消息";
						String content = "您刚发起的提现单(" + withdrawal_order
								+ "),已经审核成功,预计5分钟内到账，具体到账时间以支付宝为准。";
						int p_type = 8;
						title = PushPartnerController.lower_title(p_type);
						content = PushPartnerController.lower_content(username,withdrawal_order,p_type);
						PushPartnerController.PartnerMssage(alias, title,
								content);
						JxPartnerMessages mess = PushPartnerController
								.partnerMessage(withdrawal_order, content,
										alias, title, p_type);
						JxPartnerMessagesService.save(mess);
						// 开启转账功能
						if (IdentifyingUtil.isPay() == 0) {
							String date = transfer(withdrawal_order);
							System.out.println("1111:"+date);
							return new Result(Errors.OK, date);
						} else {
							// 不调用支付宝成功的逻辑
							
							JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
									.findUnique("from jx_partner_rebate where withdrawal_order = '"
											+ withdrawal_order
											+ "' and w_state = 3");
							if(jxPartnerRebate == null){
								return new Result(
										Errors.THE_WITHDRAWAL_ORDER_IS_incorrect);
							}
							jxWithdrawalOrder.setWithdrawal_state(200);
							jxWithdrawalOrder.setLast_modtime(new Date());
							jxWithdrawalOrder.setArrive_time(new Date());// 到账时间
							jxPartnerRebate.setWithdrawal_state(200);
							Date at = jxPartnerRebate.getAdd_time();
							//Date mt = jxPartnerRebate.getMod_time();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							String addtime = sdf.format(at);
							//String modtime = sdf.format(mt);
							String user_name = jxPartnerRebate.getUser_name();
							// 取出最后一次提现成功的时间
							/*List<Map<String, Object>> mm =jxPartnerRebateService
							 .findLastAddtime(jxPartnerRebate.getUser_name());*/
							List<Map<String, Object>> mm = jxPartnerRebateService
									.findLastAddtimes(jxPartnerRebate
											.getUser_name());
							String last_add_time = null;
							if (mm.size() <= 0) {
								last_add_time = "0000-00-00 00:00:00";
							} else {
								Map<String, Object> ma = mm.get(0);
								Date s = (Date) ma.get("add_time");
								last_add_time = sdf.format(s);
								last_add_time = sdf.format(jxWithdrawalOrder
										.getLast_time());
							}
							// 设置订单状态
							int jxOrder = jxOrderService
									.updateTradeStateToSuccess(addtime,
											last_add_time, user_name);
							jxPartnerRebateService.save(jxPartnerRebate);
							jxWithdrawalOrderService.save(jxWithdrawalOrder);
							return new Result(Errors.OK);
						}

					} else {
						
						// 审核失败，事件回滚
						JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
								.findUnique("from jx_partner_rebate where withdrawal_order = '"
										+ withdrawal_order + "'");
						if(jxPartnerRebate == null){
							return new Result(
									Errors.THE_WITHDRAWAL_ORDER_IS_incorrect);
						}
						// 取出最后一次提现的时间
						List<Map<String, Object>> mm = jxPartnerRebateService
								.findLastAddtime(jxPartnerRebate.getUser_name());
						String last_add_time = null;
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						if (mm.size() <= 0) {
							last_add_time = "1970-01-01 00:00:00";
						} else {
							Map<String, Object> ma = mm.get(0);
							Date s = (Date) ma.get("add_time");
							last_add_time = sdf.format(s);
							last_add_time = sdf.format(jxWithdrawalOrder
									.getLast_time());
						}
						Date t = jxPartnerRebate.getAdd_time();
						String time = sdf.format(t);
						jxPartnerRebate.setW_state(1);// 审核失败
						jxPartnerRebate.setWithdrawal_state(1);
						String name = jxPartnerRebate.getUser_name();
						jxWithdrawalOrder.setWithdrawal_state(1);// //审核失败
						jxWithdrawalOrder.setWithdrawal_reason(reason);
						jxWithdrawalOrder.setAudit_time(new Date());
						jxPartnerRebateService.save(jxPartnerRebate);
						jxWithdrawalOrderService.save(jxWithdrawalOrder);
						System.out.println("time:" + time);
						System.out.println("last_add_time:" + last_add_time);
						System.out.println("name:" + name);
						int jxOrder = jxOrderService.updateTradeStateToFail(
								time, last_add_time, name);
						System.out.println("0101");
						int update_state = jxDrawPeopleService.findupdate_states(withdrawal_order);

						// 提现消息推送
						String alias = jxWithdrawalOrder.getUser_number();
						String title = "提现消息";
						String content = "您刚发起的提现单(" + withdrawal_order
								+ "),审核失败,原因可查看提现记录。";
						int p_type = 9;
						title = PushPartnerController.lower_title(p_type);
						content = PushPartnerController.lower_content(username,withdrawal_order,p_type);
						PushPartnerController.PartnerMssage(alias, title,
								content);
						JxPartnerMessages mess = PushPartnerController
								.partnerMessage(withdrawal_order, content,
										alias, title, p_type);
						JxPartnerMessagesService.save(mess);
						return new Result(Errors.OK);
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

	private String transfer(String out_biz_no) throws AlipayApiException, InterruptedException {
		System.out.println("---转账开始---");
		JxWithdrawalOrder withdrawalOrder = jxWithdrawalOrderService
				.findUnique("from JxWithdrawalOrder where withdrawal_order = '"
						+ out_biz_no + "' and withdrawal_state = 3 ");
		String reason = null;
		if(withdrawalOrder == null){
			reason = "提款单不正确";
			return reason;
		}
		String payee_type = "ALIPAY_LOGONID";// 收款方账户类型
		String gatewayUrl = AlipayConfig.gatewayUrl;
		String app_id = AlipayConfig.app_id;
		String payee_account = withdrawalOrder.getPay_account();
		String amount = withdrawalOrder.getWithdrawal_amount() + "";// 转账金额
		float price1 = (float) (Math.round(Float.valueOf(amount) * 100)) / 100;
		amount = price1 + "";
		String rsa_private = AlipayConfig.private_key;// 商户私钥，pkcs8格式
		String rsa_public = AlipayConfig.alipay_public_zh_key;// 支付宝公钥
		String remark = "净喜转账";
		AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id,
				rsa_private, AlipayConfig.format, AlipayConfig.input_charset,
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
		model.setAmount(amount);
		// 当付款方为企业账户且转账金额达到（大于等于）50000元，remark不能为空。
		model.setRemark(remark);
		requests.setBizModel(model);
		AlipayFundTransToaccountTransferResponse response = alipayClient
				.execute(requests);
		System.out.println("001:"+response.getBody());
		System.out.println("002:"+response.getMsg());
		reason = response.getSubMsg();
		System.out.println("003:"+reason);
		
		
		if (response.isSuccess()) {
			System.out.println("调用成功");
			// 成功之后的逻辑
			// 设置订单状态
			

			JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
					.findUnique("from jx_partner_rebate where withdrawal_order = '"
							+ out_biz_no + "' and w_state = 3");
			withdrawalOrder.setWithdrawal_state(200);
			withdrawalOrder.setLast_modtime(new Date());
			withdrawalOrder.setArrive_time(new Date());// 到账时间
			jxPartnerRebate.setWithdrawal_state(200);
			Date at = jxPartnerRebate.getAdd_time();
			//Date mt = jxPartnerRebate.getMod_time();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addtime = sdf.format(at);
			//String modtime = sdf.format(mt);
			String user_name = jxPartnerRebate.getUser_name();
			// 取出最后一次提现成功的时间
			List<Map<String, Object>> mm = jxPartnerRebateService
					.findLastAddtime(jxPartnerRebate.getUser_name());
			String last_add_time = null;
			if (mm.size() <= 0) {
				last_add_time = "0000-00-00 00:00:00";
			} else {
				Map<String, Object> ma = mm.get(0);
				Date s = (Date) ma.get("add_time");
				last_add_time = sdf.format(s);
				last_add_time = sdf.format(withdrawalOrder.getLast_time());
			}
			// 设置订单状态
			int jxOrder = jxOrderService.updateTradeStateToSuccess(addtime,
					last_add_time, user_name);
			jxPartnerRebateService.save(jxPartnerRebate);
			jxWithdrawalOrderService.save(withdrawalOrder);
			// 消息推送
			String alias = withdrawalOrder.getUser_number();
			String title = "提现消息";
			String content = "您刚发起的提现单(" + out_biz_no + "),已成功到账。";
			int p_type = 10;
			title = PushPartnerController.lower_title(p_type);
			content = PushPartnerController.lower_content(alias,out_biz_no,p_type);
			PushPartnerController.PartnerMssage(alias, title, content);
			JxPartnerMessages mess = PushPartnerController.partnerMessage(
					out_biz_no, content, alias, title, p_type);
			JxPartnerMessagesService.save(mess);
			return reason;
		} else {
			System.out.println("调用失败");
			// 失败的逻辑
			// 查询账单是否到账
			System.out.println("00001");
			Thread.sleep(5000);
			enquiries(out_biz_no, reason);
			System.out.println("00002");
			return reason;
		}
	}

	// 到账查询
	@SuppressWarnings("unused")
	private String enquiries(String out_biz_no, String reason)
			throws AlipayApiException {
		System.out.println("---开始查询是否到账---");
		System.out.println("订单号:" + out_biz_no);
		String rsa_private = AlipayConfig.private_key;
		JxWithdrawalOrder withdrawalOrder = jxWithdrawalOrderService
				.findUnique("from JxWithdrawalOrder where withdrawal_order = '"
						+ out_biz_no + "' and withdrawal_state = 3");
		if(withdrawalOrder == null){
			reason = "提款单不正确";
			return reason;
		}
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println("到账时间:"+response.getArrivalTimeEnd());
		if (response.isSuccess()) {
			System.out.println("调用成功");
			// 成功之后的逻辑
			// 设置订单状态
			JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
					.findUnique("from jx_partner_rebate where withdrawal_order = '"
							+ out_biz_no + "' and w_state = 3");
			withdrawalOrder.setWithdrawal_state(200);
			withdrawalOrder.setLast_modtime(new Date());
			withdrawalOrder.setArrive_time(new Date());// 到账时间
			jxPartnerRebate.setWithdrawal_state(200);
			System.out.println("withdrawalOrder:"+withdrawalOrder);
			System.out.println("jxPartnerRebate:"+jxPartnerRebate);
			Date at = jxPartnerRebate.getAdd_time();
			//Date mt = jxPartnerRebate.getMod_time();
			String addtime = sdf.format(at);
			//String modtime = sdf.format(mt);
			String user_name = jxPartnerRebate.getUser_name();

			// 取出最后一次提现成功的时间
			List<Map<String, Object>> mm = jxPartnerRebateService
					.findLastAddtime(jxPartnerRebate.getUser_name());
			String last_add_time = null;
			if (mm.size() <= 0) {
				last_add_time = "0000-00-00 00:00:00";
			} else {
				Map<String, Object> ma = mm.get(0);
				Date s = (Date) ma.get("add_time");
				last_add_time = sdf.format(s);
				last_add_time = sdf.format(withdrawalOrder.getLast_time());
			}

			// 设置订单状态
			int jxOrder = jxOrderService.updateTradeStateToSuccess(addtime,
					last_add_time, user_name);
			jxPartnerRebateService.save(jxPartnerRebate);
			jxWithdrawalOrderService.save(withdrawalOrder);
			// 消息推送
			String alias = withdrawalOrder.getUser_number();
			String title = "提现消息";
			String content = "您刚发起的提现单(" + out_biz_no + "),已成功到账。";
			int p_type = 10;
			title = PushPartnerController.lower_title(p_type);
			content = PushPartnerController.lower_content(alias,out_biz_no,p_type);
			PushPartnerController.PartnerMssage(alias, title, content);
			JxPartnerMessages mess = PushPartnerController.partnerMessage(
					out_biz_no, content, alias, title, p_type);
			JxPartnerMessagesService.save(mess);
			return reason;
		} else {
			System.out.println("---调用失败---");
			System.out.println("---000001---");
			withdrawalOrder.setWithdrawal_state(4);
			withdrawalOrder.setLast_modtime(new Date());
			withdrawalOrder.setWithdrawal_reason(reason);
			JxPartnerRebate jxPartnerRebate = jxPartnerRebateService
					.findUnique("from jx_partner_rebate where withdrawal_order = '"
							+ out_biz_no + "' and w_state = 3");
			if(jxPartnerRebate == null){
				reason = "提款单不正确";
				return reason;
			}
			System.out.println("000002");
			jxPartnerRebate.setW_state(1);
			jxPartnerRebate.setWithdrawal_state(4);
			Date at = jxPartnerRebate.getAdd_time();
			//Date mt = jxPartnerRebate.getMod_time();

			String addtime = sdf.format(at);
			//String modtime = sdf.format(mt);
			String user_name = jxPartnerRebate.getUser_name();
			// 取出最后一次提现成功的时间
			List<Map<String, Object>> mm = jxPartnerRebateService
					.findLastAddtime(jxPartnerRebate.getUser_name());
			String last_add_time = null;
			if (mm.size() <= 0) {
				last_add_time = "0000-00-00 00:00:00";
			} else {
				Map<String, Object> ma = mm.get(0);
				Date s = (Date) ma.get("add_time");
				last_add_time = sdf.format(s);
				last_add_time = sdf.format(withdrawalOrder.getLast_time());
			}
			// 解除冻结
			int jxOrder = jxOrderService.updateTradeStateToFail(addtime,
					last_add_time, user_name);
			jxPartnerRebateService.save(jxPartnerRebate);
			jxWithdrawalOrderService.save(withdrawalOrder);
			// 消息推送
			String alias = withdrawalOrder.getUser_number();
			String title = "提现消息";
			String content = "您刚发起的提现单(" + out_biz_no + "),处理失败。具体原因可查看提现详情";
			int p_type = 11;
			title = PushPartnerController.lower_title(p_type);
			content = PushPartnerController.lower_content(alias,out_biz_no,p_type);
			
			PushPartnerController.PartnerMssage(alias, title, content);
			JxPartnerMessages mess = PushPartnerController.partnerMessage(
					out_biz_no, content, alias, title, p_type);
			JxPartnerMessagesService.save(mess);
			return reason;
			// 失败的逻辑
		}

	}

	/**
	 * 提现优化 2017/09/04
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/withdrawalamount")
	public Result withdrawalAmount(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String withdrawalOrderNo = "TX" + RandomUtil.getRandom();
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
					JxPartner jxPartner = null;
					if (username.contains("A") || username.contains("B")
							|| username.contains("C")) {
						jxPartner = partnerService
								.findUnique("from JxPartner where par_other = '"
										+ username + "'");
					} else {
						jxPartner = partnerService
								.findUnique("from JxPartner where id = '"
										+ username + "'");
					}

					// 判断是否拥有提现权限
					if (jxPartner.getIspermissions() == 1) {
						// 判断是否绑定了支付宝账号
						JxAlipayAccount alipayAccount = jxAlipayAccountService
								.findUnique("from JxAlipayAccount where p_number = '"
										+ username + "'");
						if (alipayAccount != null) {
							List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
							Map<String, Object> map1 = new HashMap<String, Object>();
							Date t = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							String time = sdf.format(t);
							jxPartner.getPar_pact();
							jxPartner.getPar_shop();
							String level = jxPartner.getPAR_LEVEL();
							if (username.equals("34152300015")
									|| username.equals("45000000009")) {
								level = "4";
							}
							Float Maintenance = 0f;
							Float fee = 0f;
							Float install = 0f;
							// 根据级别查出对应的返利参数
							JxRebates jxRebates = jxRebatesService
									.findUnique("from JxRebates where par_level = '"
											+ level + "'");
							if (jxRebates == null) {
								return new Result(Errors.NO_PARTNER);
							}
							// 上级是否分配比例
							JxRebateProportion proportion = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ username + "'");
							System.out.println("---逻辑部分---");
							// 得到上级
							JxPartner partner = partnerService
									.findUnique("from JxPartner where id = '"
											+ username + "'");
							// 判断是否按照合同
							System.out.println("---逻辑代码块---");

							JxPartnerRebate partnerRebate = jxPartnerRebateService
									.findUnique("from jx_partner_rebate where user_name = '"
											+ username + "' and w_state = 0");
							JxPartnerRebate jxPartnerRebate = null;
							if (partnerRebate == null) {
								jxPartnerRebate = new JxPartnerRebate();
							}

							if (jxPartner.getPar_pact() == 1) {// 按照合同
								System.out.println("级别:" + level);
								fee = IdentifyingUtil.proportion(level);
								System.out.println("自身返利费:" + fee);
								Maintenance = jxRebates.getRwl_install();// 维护费
								String parent_id = partner.getParParentid();
								if (parent_id != null
										&& parent_id.length() != 0) {// 有上级
									if (proportion == null) {// 没有被修改过比例
										fee = jxRebates.getF_install()
												+ jxRebates.getService_fee();
										Maintenance = jxRebates
												.getRwl_install();// 维护费
									} else {
										fee = proportion.getRp_rebates()
												+ proportion.getRp_installed();
										System.out.println("修改的服务费:" + fee);
										Maintenance = jxRebates
												.getRwl_install();// 维护费
									}
								}
								Float tatol_rebate = rebate(fee, Maintenance,
										install, username, time,
										withdrawalOrderNo, jxPartnerRebate,
										partnerRebate, level, proportion,
										jxRebates, partner);
								map1.put("withdrawal_total_amount",
										tatol_rebate);
								map1.put("withdrawalOrderNo", withdrawalOrderNo);
								list.add(map1);
							} else {// 没有按照合同
								fee = jxRebates.getSpecial_service_charge();
								Maintenance = jxRebates.getRwl_install();// 维护费
								if (partner.getParParentid() != null
										|| partner.getParParentid().length() != 0) {// 有上级
									if (proportion == null) {// 没有被修改过比例
										fee = jxRebates
												.getSpecial_service_charge();
										Maintenance = jxRebates
												.getRwl_install();// 维护费
									} else {
										fee = jxRebates
												.getSpecial_service_charge();
										if (level.equals("4")) {
											fee = proportion.getRp_installed();
										}
										Maintenance = jxRebates
												.getRwl_install();// 维护费
									}
								}
								Float tatol_rebate = rebate(fee, Maintenance,
										install, username, time,
										withdrawalOrderNo, jxPartnerRebate,
										partnerRebate, level, proportion,
										jxRebates, partner);
								map1.put("withdrawal_total_amount",
										tatol_rebate);
								map1.put("withdrawalOrderNo", withdrawalOrderNo);
								list.add(map1);
							}
							if (partnerRebate == null) {
								jxPartnerRebate.setWdl_fee(fee);
								jxPartnerRebate.setRwl_install(Maintenance);
								jxPartnerRebate.setPar_pact(jxPartner.getPar_pact());
								jxPartnerRebateService.save(jxPartnerRebate);
							} else {
								partnerRebate.setWdl_fee(fee);
								partnerRebate.setRwl_install(Maintenance);
								partnerRebate.setPar_pact(jxPartner.getPar_pact());
								jxPartnerRebateService.save(partnerRebate);
							}

							System.out.println("---逻辑部分---");
							return new SecretResult(Errors.OK, list);
						} else {
							return new Result(Errors.THERE_IS_NO_BINDING_ALIPAY);
						}
					} else {
						return new Result(Errors.NO_PERMISSIONS);
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

	private Float rebate(Float fee, Float maintenance, Float install,
			String username, String time, String withdrawalOrderNo,
			JxPartnerRebate jxPartnerRebate, JxPartnerRebate partnerRebate,
			String level, JxRebateProportion proportion, JxRebates jxRebates2,
			JxPartner partner) {

		// 取出最后一次提现的时间
		List<Map<String, Object>> mm = jxPartnerRebateService
				.findLastAddtime(username);
		String last_add_time = null;
		if (mm.size() <= 0) {
			last_add_time = "1970-01-01 00:00:00";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> ma = mm.get(0);
			Date s = (Date) ma.get("add_time");
			last_add_time = sdf.format(s);
		}
		DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		// 服务费返利
		Float money1 = jxOrderService.findAllMoneyOfTimeF(username, time,
				last_add_time);
		if (money1 == null) {
			money1 = 0f;
		}
		System.out.println("money1:" + money1);
		// 服务费押金
		Float Service_charge = jxOrderService
				.findAllTotalPledgeOfLowerToLastTimeY(username, time,
						last_add_time);
		if (Service_charge == null) {
			Service_charge = 0f;
		}
		System.out.println("Service_charge:" + Service_charge);
		Float fwf = money1 - Service_charge;// 总服务费金额
		System.out.println("fwf:" + fwf);
		// 续费返利
		Float renewal1 = jxOrderService.findAllXfMoneyOfTimeX(username, time,
				last_add_time);
		if (renewal1 == null) {
			renewal1 = 0f;
		}
		// 续费押金
		Float Renew_the_deposit = jxOrderService.findAllXfPledgeMoneyOfTimeXY(
				username, time, last_add_time);
		if (Renew_the_deposit == null) {
			Renew_the_deposit = 0f;
		}
		Float renewal = renewal1 - Renew_the_deposit;// 续费总金额
		System.out.println("renewal:" + renewal);
		// 安装费返利
		Float installation1 = jxOrderService.findCostMoneyOrBg(username, time,
				last_add_time);
		if (installation1 == null) {
			installation1 = 0f;
		}
		// 安装费押金
		Float Security_deposit = jxOrderService.findCostYJ(username, time,
				last_add_time);
		if (Security_deposit == null) {
			Security_deposit = 0f;
		}
		Float installation_whf = (installation1 - Security_deposit)
				* maintenance;// 总安装费金额(维护费)
		String installation_whf1 = decimalFormat.format(installation_whf);
		installation_whf = Float.valueOf(installation_whf1);
		System.out.println("总安装费:" + installation_whf);
		System.out.println("---服务费---");

		Float f = (fwf + renewal) * fee;// 总服务费
		Float tatol_fwf = f + installation_whf;// 总费用

		Float fw = fwf * fee;
		Float xf = renewal * fee;
		String fw1 = decimalFormat.format(fw);
		fw = Float.valueOf(fw1);
		
		String xf1 = decimalFormat.format(xf);
		xf = Float.valueOf(xf1);
		System.out.println("服务费:" + fw);
		System.out.println("服务续费:" + xf);
		System.out.println("fwf:" + fwf);
		System.out.println("renewal:" + renewal);

		String par_parentid = partnerService.findParentid(username);//得到上级
		// 下级返利
		Float fl1 = 0f;
		Float fl2 = 0f;
		Float fl3 = 0f;
		fee = jxRebates2.getService_fee();
		if(par_parentid == null || par_parentid.length() == 0) {// 最高级用户或没有被分配比例
			if (level.equals("1")) {// 最上级为省
				List<Map<String, Object>> tier = partnerService
						.findLevelOfUsername(username);// 市、区县级、产品经理
				if (tier.size() > 0) {// 有 市、区县级、产品经理
					for (int i = 0; i < tier.size(); i++) {
						Map<String, Object> m = tier.get(i);

						/*
						 * if (m.get("par_level").equals("2")) {// 省----市
						 * JxDrawPeople people2 = new JxDrawPeople();
						 * people2.setAdd_time(new Date());
						 * people2.setTkr_id(username);
						 * people2.setBy_tkr_name(m.get("par_name")+"");
						 * people2.setBy_tkr_id(m.get("id")+"");
						 * people2.setTkr_state(0);
						 * people2.setWithdrawal_order(withdrawalOrderNo); int
						 * wall2 =
						 * jxOrderService.findWallNumber(m.get("id")+"",time,
						 * last_add_time);// 壁挂式台数 int desktop2 =
						 * jxOrderService.
						 * findVerticalNumber(m.get("id")+"",time,
						 * last_add_time);// 台式 int vertical2 =
						 * jxOrderService.findDesktopNumber(m.get("id")+"",time,
						 * last_add_time);// 立式 // 续费台数 int wall_renew2 =
						 * jxOrderService
						 * .findWallRenewNumber(m.get("id")+"",time,
						 * last_add_time); int desktop_renew2 = jxOrderService
						 * .findVerticalRenewNumber(m.get("id")+"",time,
						 * last_add_time);// desktop_renew int vertical_renew2 =
						 * jxOrderService
						 * .findDesktopRenewNumber(m.get("id")+"",time,
						 * last_add_time);// vertical_renew
						 * people2.setSell_wall(wall2);
						 * people2.setSell_vertical(vertical2);
						 * people2.setSell_desktop(desktop2);
						 * people2.setWall_renew(wall_renew2);
						 * people2.setVertical_renew(vertical_renew2);
						 * people2.setDesktop_renew(desktop_renew2);
						 * 
						 * JxRebateProportion jxProportion2 =
						 * jxRebateProportionService .findUnique(
						 * "from jx_rebate_proportion where user_number = '" +
						 * m.get("id") + "' and most_superior_id = '" + username
						 * + "'"); if (jxProportion2 != null) { fee =
						 * jxProportion2.getSuper_totall(); } else { fee =
						 * IdentifyingUtil.ssx(); } Float money2 =
						 * jxOrderService.findAllMoneyOfTime( m.get("id"), time,
						 * last_add_time); Float total_pledge2 = jxOrderService
						 * .findAllTotalPledgeOfLowerToLastTime( m.get("id"),
						 * time, last_add_time); if (money2 == null) { money2 =
						 * 0f; } if (total_pledge2 == null) { total_pledge2 =
						 * 0f; } Float xf2 =
						 * jxOrderService.findAllXfMoneyOfTime( m.get("id"),
						 * time, last_add_time);
						 * 
						 * Float xf_pledge2 =
						 * jxOrderService.findAllXfPledgeMoneyOfTime(
						 * m.get("id"), time, last_add_time); if(xf2 == null){
						 * xf2 = 0f; } if(xf_pledge2 == null){ xf_pledge2 = 0f;
						 * } fl1 += ((money2 - total_pledge2) + (xf2 -
						 * xf_pledge2)) * fee;// 返利
						 * people2.setBy_tkr_rebates(fee);
						 * people2.setBy_tkr_total_money(((money2 -
						 * total_pledge2) + (xf2 - xf_pledge2)) * fee);
						 * people2.setService_fee((money2 - total_pledge2) *
						 * fee);//服务费 people2.setF_renewal((xf2 - xf_pledge2) *
						 * fee);//服务续费 jxDrawPeopleService.save(people2);
						 * List<Map<String, Object>> tierqx = partnerService
						 * .findLevelOfUsername(m.get("id"));// 区县级、产品经理 if
						 * (tierqx.size() > 0) { for (int j = 0; j <
						 * tierqx.size(); j++) { Map<String, Object> m1 =
						 * tierqx.get(j); if (m1.get("par_level").equals("3"))
						 * {// 省---市---区县 JxDrawPeople people3 = new
						 * JxDrawPeople(); people3.setAdd_time(new Date());
						 * people3.setTkr_id(username);
						 * people3.setBy_tkr_name(m1.get("par_name")+"");
						 * people3.setBy_tkr_id(m1.get("id")+"");
						 * people3.setTkr_state(1);
						 * people3.setWithdrawal_order(withdrawalOrderNo);
						 * people3.setBy_super_name(m.get("par_name")+"");
						 * people3.setBy_super_tkr_id(m.get("id")+""); int wall3
						 * = jxOrderService.findWallNumber(m1.get("id")+"",time,
						 * last_add_time);// 壁挂式台数 int desktop3 =
						 * jxOrderService.
						 * findVerticalNumber(m1.get("id")+"",time,
						 * last_add_time);// 台式 int vertical3 =
						 * jxOrderService.findDesktopNumber
						 * (m1.get("id")+"",time, last_add_time);// 立式 // 续费台数
						 * int wall_renew3 = jxOrderService
						 * .findWallRenewNumber(m1.get("id")+"",time,
						 * last_add_time); int desktop_renew3 = jxOrderService
						 * .findVerticalRenewNumber(m1.get("id")+"",time,
						 * last_add_time);// desktop_renew int vertical_renew3 =
						 * jxOrderService
						 * .findDesktopRenewNumber(m1.get("id")+"",time,
						 * last_add_time);// vertical_renew
						 * people3.setSell_wall(wall3);
						 * people3.setSell_vertical(vertical3);
						 * people3.setSell_desktop(desktop3);
						 * people3.setWall_renew(wall_renew3);
						 * people3.setVertical_renew(vertical_renew3);
						 * people3.setDesktop_renew(desktop_renew3);
						 * 
						 * JxRebateProportion jxProportion3 =
						 * jxRebateProportionService .findUnique(
						 * "from jx_rebate_proportion where user_number = '" +
						 * m1.get("id") + "' and most_superior_id = '" +
						 * username + "'"); if (jxProportion3 != null) { fee =
						 * jxProportion3 .getSuper_totall(); } else { fee =
						 * IdentifyingUtil.sqc(); } Float money3 =
						 * jxOrderService .findAllMoneyOfTime( m1.get("id"),
						 * time, last_add_time); Float total_pledge3 =
						 * jxOrderService .findAllTotalPledgeOfLowerToLastTime(
						 * m1.get("id"), time, last_add_time); if (money3 ==
						 * null) { money3 = 0f; } if (total_pledge3 == null) {
						 * total_pledge3 = 0f; } Float xf3 =
						 * jxOrderService.findAllXfMoneyOfTime( m1.get("id"),
						 * time, last_add_time);
						 * 
						 * Float xf_pledge3 =
						 * jxOrderService.findAllXfPledgeMoneyOfTime(
						 * m1.get("id"), time, last_add_time); if(xf3 == null){
						 * xf3 = 0f; } if(xf_pledge3 == null){ xf_pledge3 = 0f;
						 * } fl2 += ((money3 - total_pledge3) + (xf3 -
						 * xf_pledge3)) * fee;// 返利
						 * people3.setBy_tkr_rebates(fee);
						 * people3.setBy_tkr_total_money(((money3 -
						 * total_pledge3) + (xf3 - xf_pledge3)) * fee);
						 * people3.setService_fee((money3 - total_pledge3) *
						 * fee);//服务费 people3.setF_renewal((xf3 - xf_pledge3) *
						 * fee);//服务续费 jxDrawPeopleService.save(people3);
						 * List<Map<String, Object>> tiercp = partnerService
						 * .findLevelOfUsername(m1 .get("id"));// 产品经理 if
						 * (tiercp.size() > 0) { for (int z = 0; z <
						 * tiercp.size(); z++) { Map<String, Object> m2 = tiercp
						 * .get(z); JxDrawPeople people4 = new JxDrawPeople();
						 * people4.setAdd_time(new Date());
						 * people4.setTkr_id(username);
						 * people4.setBy_tkr_name(m2.get("par_name")+"");
						 * people4.setBy_tkr_id(m2.get("id")+"");
						 * people4.setTkr_state(1);
						 * people4.setWithdrawal_order(withdrawalOrderNo);
						 * people4.setBy_super_name(m1.get("par_name")+"");
						 * people4.setBy_super_tkr_id(m1.get("id")+""); int
						 * wall4 =
						 * jxOrderService.findWallNumber(m2.get("id")+"",time,
						 * last_add_time);// 壁挂式台数 int desktop4 =
						 * jxOrderService.
						 * findVerticalNumber(m2.get("id")+"",time,
						 * last_add_time);// 台式 int vertical4 =
						 * jxOrderService.findDesktopNumber
						 * (m2.get("id")+"",time, last_add_time);// 立式 // 续费台数
						 * int wall_renew4 = jxOrderService
						 * .findWallRenewNumber(m2.get("id")+"",time,
						 * last_add_time); int desktop_renew4 = jxOrderService
						 * .findVerticalRenewNumber(m2.get("id")+"",time,
						 * last_add_time);// desktop_renew int vertical_renew4 =
						 * jxOrderService
						 * .findDesktopRenewNumber(m2.get("id")+"",time,
						 * last_add_time);// vertical_renew
						 * people4.setSell_wall(wall4);
						 * people4.setSell_vertical(vertical4);
						 * people4.setSell_desktop(desktop4);
						 * people4.setWall_renew(wall_renew4);
						 * people4.setVertical_renew(vertical_renew4);
						 * people4.setDesktop_renew(desktop_renew4);
						 * 
						 * JxRebateProportion jxProportion4 =
						 * jxRebateProportionService .findUnique(
						 * "from jx_rebate_proportion where user_number = '" +
						 * m2.get("id") + "' and most_superior_id = '" +
						 * username + "'"); if (jxProportion4 != null) { fee =
						 * jxProportion4 .getSuper_totall(); } Float money4 =
						 * jxOrderService .findAllMoneyOfTime( m2.get("id"),
						 * time, last_add_time); Float total_pledge4 =
						 * jxOrderService .findAllTotalPledgeOfLowerToLastTime(
						 * m2.get("id"), time, last_add_time); if (money4 ==
						 * null) { money4 = 0f; } if (total_pledge4 == null) {
						 * total_pledge4 = 0f; } Float xf4 =
						 * jxOrderService.findAllXfMoneyOfTime( m2.get("id"),
						 * time, last_add_time);
						 * 
						 * Float xf_pledge4 =
						 * jxOrderService.findAllXfPledgeMoneyOfTime(
						 * m2.get("id"), time, last_add_time); if(xf4 == null){
						 * xf4 = 0f; } if(xf_pledge4 == null){ xf_pledge4 = 0f;
						 * } fl3 += ((money4 - total_pledge4) + (xf4 -
						 * xf_pledge4)) * fee;// 返利
						 * people4.setBy_tkr_rebates(fee);
						 * people4.setBy_tkr_total_money(((money4 -
						 * total_pledge4) + (xf4 - xf_pledge4)) * fee);
						 * people4.setService_fee((money4 - total_pledge4) *
						 * fee);//服务费 people4.setF_renewal((xf4 - xf_pledge4) *
						 * fee);//服务续费 jxDrawPeopleService.save(people4); } }
						 * 
						 * } if (m1.get("par_level").equals("4")) {//
						 * 省---市---产品经理 JxDrawPeople people4 = new
						 * JxDrawPeople(); people4.setAdd_time(new Date());
						 * people4.setTkr_id(username);
						 * people4.setBy_tkr_name(m1.get("par_name")+"");
						 * people4.setBy_tkr_id(m1.get("id")+"");
						 * people4.setTkr_state(1);
						 * people4.setWithdrawal_order(withdrawalOrderNo);
						 * people4.setBy_super_name(m.get("par_name")+"");
						 * people4.setBy_super_tkr_id(m.get("id")+""); int wall4
						 * = jxOrderService.findWallNumber(m1.get("id")+"",time,
						 * last_add_time);// 壁挂式台数 int desktop4 =
						 * jxOrderService.
						 * findVerticalNumber(m1.get("id")+"",time,
						 * last_add_time);// 台式 int vertical4 =
						 * jxOrderService.findDesktopNumber
						 * (m1.get("id")+"",time, last_add_time);// 立式 // 续费台数
						 * int wall_renew4 = jxOrderService
						 * .findWallRenewNumber(m1.get("id")+"",time,
						 * last_add_time); int desktop_renew4 = jxOrderService
						 * .findVerticalRenewNumber(m1.get("id")+"",time,
						 * last_add_time);// desktop_renew int vertical_renew4 =
						 * jxOrderService
						 * .findDesktopRenewNumber(m1.get("id")+"",time,
						 * last_add_time);// vertical_renew
						 * people4.setSell_wall(wall4);
						 * people4.setSell_vertical(vertical4);
						 * people4.setSell_desktop(desktop4);
						 * people4.setWall_renew(wall_renew4);
						 * people4.setVertical_renew(vertical_renew4);
						 * people4.setDesktop_renew(desktop_renew4);
						 * 
						 * JxRebateProportion jxProportion4 =
						 * jxRebateProportionService .findUnique(
						 * "from jx_rebate_proportion where user_number = '" +
						 * m1.get("id") + "' and most_superior_id = '" +
						 * username + "'"); if (jxProportion4 != null) { fee =
						 * jxProportion4 .getSuper_totall(); } else { fee =
						 * IdentifyingUtil.ssx(); } Float money4 =
						 * jxOrderService .findAllMoneyOfTime( m1.get("id"),
						 * time, last_add_time); Float total_pledge4 =
						 * jxOrderService .findAllTotalPledgeOfLowerToLastTime(
						 * m1.get("id"), time, last_add_time); if (money4 ==
						 * null) { money4 = 0f; } if (total_pledge4 == null) {
						 * total_pledge4 = 0f; } Float xf4 =
						 * jxOrderService.findAllXfMoneyOfTime( m1.get("id"),
						 * time, last_add_time);
						 * 
						 * Float xf_pledge4 =
						 * jxOrderService.findAllXfPledgeMoneyOfTime(
						 * m1.get("id"), time, last_add_time); if(xf4 == null){
						 * xf4 = 0f; } if(xf_pledge4 == null){ xf_pledge4 = 0f;
						 * } fl3 += ((money4 - total_pledge4)+(xf4 -
						 * xf_pledge4)) * fee;// 返利
						 * people4.setBy_tkr_rebates(fee);
						 * people4.setBy_tkr_total_money(((money4 -
						 * total_pledge4)+(xf4 - xf_pledge4)) * fee);
						 * people4.setService_fee((money4 - total_pledge4) *
						 * fee);//服务费 people4.setF_renewal((xf4 - xf_pledge4) *
						 * fee);//服务续费 jxDrawPeopleService.save(people4); } } }
						 * 
						 * }
						 */

						if (m.get("par_level").equals("2")) {// 省----市
							// 判断是否拥有下级
							List<Map<String, Object>> tierqx = partnerService
									.findLevelOfUsername(m.get("id"));// 区县级、产品经理
							if (tierqx.size() <= 0) {// 没有下级
								JxDrawPeople people2 = new JxDrawPeople();
								people2.setAdd_time(new Date());
								people2.setTkr_id(username);
								people2.setBy_tkr_name(m.get("par_name") + "");
								people2.setBy_tkr_id(m.get("id") + "");
								people2.setTkr_state(0);
								people2.setWithdrawal_order(withdrawalOrderNo);
								int wall2 = jxOrderService.findWallNumber(
										m.get("id") + "", time, last_add_time);// 壁挂式台数
								int desktop2 = jxOrderService
										.findVerticalNumber(m.get("id") + "",
												time, last_add_time);// 台式
								int vertical2 = jxOrderService
										.findDesktopNumber(m.get("id") + "",
												time, last_add_time);// 立式
								// 续费台数
								int wall_renew2 = jxOrderService
										.findWallRenewNumber(m.get("id") + "",
												time, last_add_time);
								int desktop_renew2 = jxOrderService
										.findVerticalRenewNumber(m.get("id")
												+ "", time, last_add_time);// desktop_renew
								int vertical_renew2 = jxOrderService
										.findDesktopRenewNumber(m.get("id")
												+ "", time, last_add_time);// vertical_renew
								people2.setSell_wall(wall2);
								people2.setSell_vertical(vertical2);
								people2.setSell_desktop(desktop2);
								people2.setWall_renew(wall_renew2);
								people2.setVertical_renew(vertical_renew2);
								people2.setDesktop_renew(desktop_renew2);

								JxRebateProportion jxProportion2 = jxRebateProportionService
										.findUnique("from jx_rebate_proportion where user_number = '"
												+ m.get("id")
												+ "' and most_superior_id = '"
												+ username + "'");
								if (jxProportion2 != null) {
									fee = jxProportion2.getSuper_totall();
									fee = IdentifyingUtil.proportion(level) - jxProportion2.getSuper_totall();
									System.out.println("010011fee:"+fee);
								} else {
									fee = IdentifyingUtil.ssx();
								}
								Float money2 = jxOrderService
										.findAllMoneyOfTime(m.get("id"), time,
												last_add_time);
								Float total_pledge2 = jxOrderService
										.findAllTotalPledgeOfLowerToLastTime(
												m.get("id"), time,
												last_add_time);
								if (money2 == null) {
									money2 = 0f;
								}
								if (total_pledge2 == null) {
									total_pledge2 = 0f;
								}
								Float xf2 = jxOrderService
										.findAllXfMoneyOfTime(m.get("id"),
												time, last_add_time);

								Float xf_pledge2 = jxOrderService
										.findAllXfPledgeMoneyOfTime(
												m.get("id"), time,
												last_add_time);
								if (xf2 == null) {
									xf2 = 0f;
								}
								if (xf_pledge2 == null) {
									xf_pledge2 = 0f;
								}
								people2.setTotal_money((money2 - total_pledge2) + (xf2 - xf_pledge2));
								fl1 += ((money2 - total_pledge2) + (xf2 - xf_pledge2))
										* fee;// 返利
								people2.setBy_tkr_rebates(fee);
								people2.setBy_tkr_total_money(((money2 - total_pledge2) + (xf2 - xf_pledge2))
										* fee);
								people2.setService_fee((money2 - total_pledge2)
										* fee);// 服务费
								people2.setF_renewal((xf2 - xf_pledge2) * fee);// 服务续费
								jxDrawPeopleService.save(people2);
							} else {
								// 市有下级并且下级是区县
								JxDrawPeople people2 = new JxDrawPeople();
								people2.setAdd_time(new Date());
								people2.setTkr_id(username);
								people2.setBy_tkr_name(m.get("par_name") + "");
								people2.setBy_tkr_id(m.get("id") + "");
								people2.setTkr_state(0);
								people2.setWithdrawal_order(withdrawalOrderNo);
								int wall2 = jxOrderService.findWallNumber(
										m.get("id") + "", time, last_add_time);// 壁挂式台数
								int desktop2 = jxOrderService
										.findVerticalNumber(m.get("id") + "",
												time, last_add_time);// 台式
								int vertical2 = jxOrderService
										.findDesktopNumber(m.get("id") + "",
												time, last_add_time);// 立式
								// 续费台数
								int wall_renew2 = jxOrderService
										.findWallRenewNumber(m.get("id") + "",
												time, last_add_time);
								int desktop_renew2 = jxOrderService
										.findVerticalRenewNumber(m.get("id")
												+ "", time, last_add_time);// desktop_renew
								int vertical_renew2 = jxOrderService
										.findDesktopRenewNumber(m.get("id")
												+ "", time, last_add_time);// vertical_renew
								people2.setSell_wall(wall2);
								people2.setSell_vertical(vertical2);
								people2.setSell_desktop(desktop2);
								people2.setWall_renew(wall_renew2);
								people2.setVertical_renew(vertical_renew2);
								people2.setDesktop_renew(desktop_renew2);
								JxRebateProportion jxProportion2 = jxRebateProportionService
										.findUnique("from jx_rebate_proportion where user_number = '"
												+ m.get("id")
												+ "' and most_superior_id = '"
												+ username + "'");
								if (jxProportion2 != null) {
									fee = jxProportion2
											.getSuper_totall();
									fee = IdentifyingUtil.proportion(level) - jxProportion2.getRp_total();
									
								} else {
									fee = jxRebates2.getService_fee();
									fee = IdentifyingUtil.ssx();
								}
								Float money2 = jxOrderService
										.findAllMoneyOfTime(
												m.get("id"), time,
												last_add_time);
								Float total_pledge2 = jxOrderService
										.findAllTotalPledgeOfLowerToLastTime(
												m.get("id"), time,
												last_add_time);
								if (money2 == null) {
									money2 = 0f;
								}
								if (total_pledge2 == null) {
									total_pledge2 = 0f;
								}
								Float xf2 = jxOrderService
										.findAllXfMoneyOfTime(
												m.get("id"), time,
												last_add_time);

								Float xf_pledge2 = jxOrderService
										.findAllXfPledgeMoneyOfTime(
												m.get("id"), time,
												last_add_time);
								if (xf2 == null) {
									xf2 = 0f;
								}
								if (xf_pledge2 == null) {
									xf_pledge2 = 0f;
								}
								people2.setTotal_money((money2 - total_pledge2) + (xf2 - xf_pledge2));
								fl1 += ((money2 - total_pledge2) + (xf2 - xf_pledge2))
										* fee;// 返利
								people2.setBy_tkr_rebates(fee);
								people2.setBy_tkr_total_money(((money2 - total_pledge2) + (xf2 - xf_pledge2))
										* fee);
								people2.setService_fee((money2 - total_pledge2)
										* fee);// 服务费
								people2.setF_renewal((xf2 - xf_pledge2)
										* fee);// 服务续费
								jxDrawPeopleService.save(people2);
								
								for (int j = 0; j < tierqx.size(); j++) {
									Map<String, Object> m1 = tierqx.get(j);
									if (m1.get("par_level").equals("3")) {// 省---市---区县
										// 市
										JxDrawPeople people3 = new JxDrawPeople();
										people3.setAdd_time(new Date());
										people3.setTkr_id(username);
										people3.setBy_tkr_name(m1
												.get("par_name") + "");
										people3.setBy_tkr_id(m1.get("id") + "");
										people3.setTkr_state(1);
										people3.setWithdrawal_order(withdrawalOrderNo);
										people3.setBy_super_name(m
												.get("par_name") + "");
										people3.setBy_super_tkr_id(m.get("id")
												+ "");
										int wall3 = jxOrderService
												.findWallNumber(m1.get("id")
														+ "", time,
														last_add_time);// 壁挂式台数
										int desktop3 = jxOrderService
												.findVerticalNumber(
														m1.get("id") + "",
														time, last_add_time);// 台式
										int vertical3 = jxOrderService
												.findDesktopNumber(m1.get("id")
														+ "", time,
														last_add_time);// 立式
										// 续费台数
										int wall_renew3 = jxOrderService
												.findWallRenewNumber(
														m1.get("id") + "",
														time, last_add_time);
										int desktop_renew3 = jxOrderService
												.findVerticalRenewNumber(
														m1.get("id") + "",
														time, last_add_time);// desktop_renew
										int vertical_renew3 = jxOrderService
												.findDesktopRenewNumber(
														m1.get("id") + "",
														time, last_add_time);// vertical_renew
										people3.setSell_wall(wall3);
										people3.setSell_vertical(vertical3);
										people3.setSell_desktop(desktop3);
										people3.setWall_renew(wall_renew3);
										people3.setVertical_renew(vertical_renew3);
										people3.setDesktop_renew(desktop_renew3);

										JxRebateProportion jxProportion3 = jxRebateProportionService
												.findUnique("from jx_rebate_proportion where user_number = '"
														+ m1.get("id")
														+ "' and most_superior_id = '"
														+ username + "'");
										if (jxProportion3 != null) {
											fee = jxProportion3
													.getSuper_totall();
										} else {
											fee = jxRebates2.getService_fee();
										}
										Float money3 = jxOrderService
												.findAllMoneyOfTime(
														m1.get("id"), time,
														last_add_time);
										Float total_pledge3 = jxOrderService
												.findAllTotalPledgeOfLowerToLastTime(
														m1.get("id"), time,
														last_add_time);
										if (money3 == null) {
											money3 = 0f;
										}
										if (total_pledge3 == null) {
											total_pledge3 = 0f;
										}
										Float xf3 = jxOrderService
												.findAllXfMoneyOfTime(
														m1.get("id"), time,
														last_add_time);

										Float xf_pledge3 = jxOrderService
												.findAllXfPledgeMoneyOfTime(
														m1.get("id"), time,
														last_add_time);
										if (xf3 == null) {
											xf3 = 0f;
										}
										if (xf_pledge3 == null) {
											xf_pledge3 = 0f;
										}
										people3.setTotal_money((money3 - total_pledge3) + (xf3 - xf_pledge3));
										fl2 += ((money3 - total_pledge3) + (xf3 - xf_pledge3))
												* fee;// 返利
										people3.setBy_tkr_rebates(fee);
										people3.setBy_tkr_total_money(((money3 - total_pledge3) + (xf3 - xf_pledge3))
												* fee);
										people3.setService_fee((money3 - total_pledge3)
												* fee);// 服务费
										people3.setF_renewal((xf3 - xf_pledge3)
												* fee);// 服务续费
										jxDrawPeopleService.save(people3);
										List<Map<String, Object>> tiercp = partnerService
												.findLevelOfUsername(m1
														.get("id"));// 产品经理
										if (tiercp.size() > 0) {
											for (int z = 0; z < tiercp.size(); z++) {
												Map<String, Object> m2 = tiercp
														.get(z);
												JxDrawPeople people4 = new JxDrawPeople();
												people4.setAdd_time(new Date());
												people4.setTkr_id(username);
												people4.setBy_tkr_name(m2
														.get("par_name") + "");
												people4.setBy_tkr_id(m2
														.get("id") + "");
												people4.setTkr_state(1);
												people4.setWithdrawal_order(withdrawalOrderNo);
												people4.setBy_super_name(m1
														.get("par_name") + "");
												people4.setBy_super_tkr_id(m1
														.get("id") + "");
												int wall4 = jxOrderService
														.findWallNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);// 壁挂式台数
												int desktop4 = jxOrderService
														.findVerticalNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);// 台式
												int vertical4 = jxOrderService
														.findDesktopNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);// 立式
												// 续费台数
												int wall_renew4 = jxOrderService
														.findWallRenewNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);
												int desktop_renew4 = jxOrderService
														.findVerticalRenewNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);// desktop_renew
												int vertical_renew4 = jxOrderService
														.findDesktopRenewNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);// vertical_renew
												people4.setSell_wall(wall4);
												people4.setSell_vertical(vertical4);
												people4.setSell_desktop(desktop4);
												people4.setWall_renew(wall_renew4);
												people4.setVertical_renew(vertical_renew4);
												people4.setDesktop_renew(desktop_renew4);

												JxRebateProportion jxProportion4 = jxRebateProportionService
														.findUnique("from jx_rebate_proportion where user_number = '"
																+ m2.get("id")
																+ "' and most_superior_id = '"
																+ username
																+ "'");
												if (jxProportion4 != null) {
													fee = jxProportion4
															.getSuper_totall();
													Float s = IdentifyingUtil.town();
													Float q = IdentifyingUtil.qx();
													fee = IdentifyingUtil.proportion(level) - (jxProportion4.getRp_total() + s + q);
												} else {
													fee = jxRebates2
															.getService_fee();
												}
												Float money4 = jxOrderService
														.findAllMoneyOfTime(
																m2.get("id"),
																time,
																last_add_time);
												Float total_pledge4 = jxOrderService
														.findAllTotalPledgeOfLowerToLastTime(
																m2.get("id"),
																time,
																last_add_time);
												if (money4 == null) {
													money4 = 0f;
												}
												if (total_pledge4 == null) {
													total_pledge4 = 0f;
												}
												Float xf4 = jxOrderService
														.findAllXfMoneyOfTime(
																m2.get("id"),
																time,
																last_add_time);

												Float xf_pledge4 = jxOrderService
														.findAllXfPledgeMoneyOfTime(
																m2.get("id"),
																time,
																last_add_time);
												if (xf4 == null) {
													xf4 = 0f;
												}
												if (xf_pledge4 == null) {
													xf_pledge4 = 0f;
												}
												people4.setTotal_money((money4 - total_pledge4) + (xf4 - xf_pledge4));
												fl3 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
														* fee;// 返利
												people4.setBy_tkr_rebates(fee);
												people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
														* fee);
												people4.setService_fee((money4 - total_pledge4)
														* fee);// 服务费
												people4.setF_renewal((xf4 - xf_pledge4)
														* fee);// 服务续费
												jxDrawPeopleService
														.save(people4);
											}
										}
									} else{
										// 下级是产品经理
										JxDrawPeople people4 = new JxDrawPeople();
										people4.setAdd_time(new Date());
										people4.setTkr_id(username);
										people4.setBy_tkr_name(m1
												.get("par_name") + "");
										people4.setBy_tkr_id(m1.get("id") + "");
										people4.setTkr_state(1);
										people4.setWithdrawal_order(withdrawalOrderNo);
										people4.setBy_super_name(m
												.get("par_name") + "");
										people4.setBy_super_tkr_id(m.get("id")
												+ "");
										int wall4 = jxOrderService
												.findWallNumber(m1.get("id")
														+ "", time,
														last_add_time);// 壁挂式台数
										int desktop4 = jxOrderService
												.findVerticalNumber(
														m1.get("id") + "",
														time, last_add_time);// 台式
										int vertical4 = jxOrderService
												.findDesktopNumber(m1.get("id")
														+ "", time,
														last_add_time);// 立式
										// 续费台数
										int wall_renew4 = jxOrderService
												.findWallRenewNumber(
														m1.get("id") + "",
														time, last_add_time);
										int desktop_renew4 = jxOrderService
												.findVerticalRenewNumber(
														m1.get("id") + "",
														time, last_add_time);// desktop_renew
										int vertical_renew4 = jxOrderService
												.findDesktopRenewNumber(
														m1.get("id") + "",
														time, last_add_time);// vertical_renew
										people4.setSell_wall(wall4);
										people4.setSell_vertical(vertical4);
										people4.setSell_desktop(desktop4);
										people4.setWall_renew(wall_renew4);
										people4.setVertical_renew(vertical_renew4);
										people4.setDesktop_renew(desktop_renew4);

										JxRebateProportion jxProportion4 = jxRebateProportionService
												.findUnique("from jx_rebate_proportion where user_number = '"
														+ m1.get("id")
														+ "' and most_superior_id = '"
														+ username + "'");
										if (jxProportion4 != null) {
											fee = jxProportion4
													.getSuper_totall();
											fee = IdentifyingUtil.proportion(level) - (IdentifyingUtil.town()+jxProportion4.getRp_total());
											
										} else {
											fee = IdentifyingUtil.ssx();
										}
										Float money4 = jxOrderService
												.findAllMoneyOfTime(
														m1.get("id"), time,
														last_add_time);
										Float total_pledge4 = jxOrderService
												.findAllTotalPledgeOfLowerToLastTime(
														m1.get("id"), time,
														last_add_time);
										if (money4 == null) {
											money4 = 0f;
										}
										if (total_pledge4 == null) {
											total_pledge4 = 0f;
										}
										Float xf4 = jxOrderService
												.findAllXfMoneyOfTime(
														m1.get("id"), time,
														last_add_time);

										Float xf_pledge4 = jxOrderService
												.findAllXfPledgeMoneyOfTime(
														m1.get("id"), time,
														last_add_time);
										if (xf4 == null) {
											xf4 = 0f;
										}
										if (xf_pledge4 == null) {
											xf_pledge4 = 0f;
										}
										people4.setTotal_money((money4 - total_pledge4) + (xf4 - xf_pledge4));
										fl2 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
												* fee;// 返利
										people4.setBy_tkr_rebates(fee);
										people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
												* fee);
										people4.setService_fee((money4 - total_pledge4)
												* fee);// 服务费
										people4.setF_renewal((xf4 - xf_pledge4)
												* fee);// 服务续费
										jxDrawPeopleService.save(people4);
									}
								}
							}
						}

						if (m.get("par_level").equals("3")) {// 省---区县
							JxDrawPeople people3 = new JxDrawPeople();
							people3.setAdd_time(new Date());
							people3.setTkr_id(username);
							people3.setBy_tkr_name(m.get("par_name") + "");
							people3.setBy_tkr_id(m.get("id") + "");
							people3.setTkr_state(0);
							people3.setWithdrawal_order(withdrawalOrderNo);
							int wall3 = jxOrderService.findWallNumber(
									m.get("id") + "", time, last_add_time);// 壁挂式台数
							int desktop3 = jxOrderService.findVerticalNumber(
									m.get("id") + "", time, last_add_time);// 台式
							int vertical3 = jxOrderService.findDesktopNumber(
									m.get("id") + "", time, last_add_time);// 立式
							// 续费台数
							int wall_renew3 = jxOrderService
									.findWallRenewNumber(m.get("id") + "",
											time, last_add_time);
							int desktop_renew3 = jxOrderService
									.findVerticalRenewNumber(m.get("id") + "",
											time, last_add_time);// desktop_renew
							int vertical_renew3 = jxOrderService
									.findDesktopRenewNumber(m.get("id") + "",
											time, last_add_time);// vertical_renew
							people3.setSell_wall(wall3);
							people3.setSell_vertical(vertical3);
							people3.setSell_desktop(desktop3);
							people3.setWall_renew(wall_renew3);
							people3.setVertical_renew(vertical_renew3);
							people3.setDesktop_renew(desktop_renew3);

							JxRebateProportion jxProportion3 = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ m.get("id")
											+ "' and most_superior_id = '"
											+ username + "'");
							if (jxProportion3 != null) {
								fee = jxProportion3.getSuper_totall();
							} else {
								fee = IdentifyingUtil.sqc();
							}
							Float money3 = jxOrderService.findAllMoneyOfTime(
									m.get("id"), time, last_add_time);
							Float total_pledge3 = jxOrderService
									.findAllTotalPledgeOfLowerToLastTime(
											m.get("id"), time, last_add_time);
							if (money3 == null) {
								money3 = 0f;
							}
							if (total_pledge3 == null) {
								total_pledge3 = 0f;
							}
							Float xf3 = jxOrderService.findAllXfMoneyOfTime(
									m.get("id"), time, last_add_time);

							Float xf_pledge3 = jxOrderService
									.findAllXfPledgeMoneyOfTime(m.get("id"),
											time, last_add_time);
							if (xf3 == null) {
								xf3 = 0f;
							}
							if (xf_pledge3 == null) {
								xf_pledge3 = 0f;
							}
							people3.setTotal_money((money3 - total_pledge3) + (xf3 - xf_pledge3));
							fl1 += ((money3 - total_pledge3) + (xf3 - xf_pledge3))
									* fee;// 返利
							people3.setBy_tkr_rebates(fee);
							people3.setBy_tkr_total_money(((money3 - total_pledge3) + (xf3 - xf_pledge3))
									* fee);
							people3.setService_fee((money3 - total_pledge3)
									* fee);// 服务费
							people3.setF_renewal((xf3 - xf_pledge3) * fee);// 服务续费
							jxDrawPeopleService.save(people3);
							List<Map<String, Object>> tiercp = partnerService
									.findLevelOfUsername(m.get("id"));// 产品经理
							if (tiercp.size() > 0) {// 有下级产品经理
								for (int j = 0; j < tiercp.size(); j++) {
									Map<String, Object> m1 = tiercp.get(j);
									JxDrawPeople people4 = new JxDrawPeople();
									people4.setAdd_time(new Date());
									people4.setTkr_id(username);
									people4.setBy_tkr_name(m1.get("par_name")
											+ "");
									people4.setBy_tkr_id(m1.get("id") + "");
									people4.setTkr_state(1);
									people4.setWithdrawal_order(withdrawalOrderNo);
									people4.setBy_super_name(m.get("par_name")
											+ "");
									people4.setBy_super_tkr_id(m.get("id") + "");
									int wall4 = jxOrderService.findWallNumber(
											m1.get("id") + "", time,
											last_add_time);// 壁挂式台数
									int desktop4 = jxOrderService
											.findVerticalNumber(m1.get("id")
													+ "", time, last_add_time);// 台式
									int vertical4 = jxOrderService
											.findDesktopNumber(m1.get("id")
													+ "", time, last_add_time);// 立式
									// 续费台数
									int wall_renew4 = jxOrderService
											.findWallRenewNumber(m1.get("id")
													+ "", time, last_add_time);
									int desktop_renew4 = jxOrderService
											.findVerticalRenewNumber(
													m1.get("id") + "", time,
													last_add_time);// desktop_renew
									int vertical_renew4 = jxOrderService
											.findDesktopRenewNumber(
													m1.get("id") + "", time,
													last_add_time);// vertical_renew
									people4.setSell_wall(wall4);
									people4.setSell_vertical(vertical4);
									people4.setSell_desktop(desktop4);
									people4.setWall_renew(wall_renew4);
									people4.setVertical_renew(vertical_renew4);
									people4.setDesktop_renew(desktop_renew4);

									JxRebateProportion jxProportion4 = jxRebateProportionService
											.findUnique("from jx_rebate_proportion where user_number = '"
													+ m1.get("id")
													+ "' and most_superior_id = '"
													+ username + "'");
									if (jxProportion4 != null) {
										fee = jxProportion4.getSuper_totall();
										fee = IdentifyingUtil.proportion(level) - (jxProportion4.getRp_total() + IdentifyingUtil.qx());
									} else {
										fee = IdentifyingUtil.sqc();
									}
									Float money4 = jxOrderService
											.findAllMoneyOfTime(m1.get("id"),
													time, last_add_time);
									Float total_pledge4 = jxOrderService
											.findAllTotalPledgeOfLowerToLastTime(
													m1.get("id"), time,
													last_add_time);
									if (money4 == null) {
										money4 = 0f;
									}
									if (total_pledge4 == null) {
										total_pledge4 = 0f;
									}
									Float xf4 = jxOrderService
											.findAllXfMoneyOfTime(m1.get("id"),
													time, last_add_time);

									Float xf_pledge4 = jxOrderService
											.findAllXfPledgeMoneyOfTime(
													m1.get("id"), time,
													last_add_time);
									if (xf4 == null) {
										xf4 = 0f;
									}
									if (xf_pledge4 == null) {
										xf_pledge4 = 0f;
									}
									people4.setTotal_money((money4 - total_pledge4) + (xf4 - xf_pledge4));
									fl2 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
											* fee;// 返利
									people4.setBy_tkr_rebates(fee);
									people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
											* fee);
									people4.setService_fee((money4 - total_pledge4)
											* fee);// 服务费
									people4.setF_renewal((xf4 - xf_pledge4)
											* fee);// 服务续费
									jxDrawPeopleService.save(people4);
								}
							}

						}
						if (m.get("par_level").equals("4")) {// 省---产品经理
							JxDrawPeople people4 = new JxDrawPeople();
							people4.setAdd_time(new Date());
							people4.setTkr_id(username);
							people4.setBy_tkr_name(m.get("par_name") + "");
							people4.setBy_tkr_id(m.get("id") + "");
							people4.setTkr_state(0);
							people4.setWithdrawal_order(withdrawalOrderNo);
							int wall4 = jxOrderService.findWallNumber(
									m.get("id") + "", time, last_add_time);// 壁挂式台数
							int desktop4 = jxOrderService.findVerticalNumber(
									m.get("id") + "", time, last_add_time);// 台式
							int vertical4 = jxOrderService.findDesktopNumber(
									m.get("id") + "", time, last_add_time);// 立式
							// 续费台数
							int wall_renew4 = jxOrderService
									.findWallRenewNumber(m.get("id") + "",
											time, last_add_time);
							int desktop_renew4 = jxOrderService
									.findVerticalRenewNumber(m.get("id") + "",
											time, last_add_time);// desktop_renew
							int vertical_renew4 = jxOrderService
									.findDesktopRenewNumber(m.get("id") + "",
											time, last_add_time);// vertical_renew
							people4.setSell_wall(wall4);
							people4.setSell_vertical(vertical4);
							people4.setSell_desktop(desktop4);
							people4.setWall_renew(wall_renew4);
							people4.setVertical_renew(vertical_renew4);
							people4.setDesktop_renew(desktop_renew4);

							JxRebates jxRebates = jxRebatesService
									.findUnique("from JxRebates where par_level = '"
											+ m.get("par_level") + "'");
							// 判断是否被分配下级比例
							JxRebateProportion jxProportion4 = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ m.get("id")
											+ "' and most_superior_id = '"
											+ username + "'");
							if (jxProportion4 != null) {
								fee = jxProportion4.getSuper_totall();
							} else {
								fee = (IdentifyingUtil.proportion(level) - jxRebates
										.getF_install());
							}
							Float money4 = jxOrderService.findAllMoneyOfTime(
									m.get("id"), time, last_add_time);
							if (money4 == null) {
								money4 = 0f;
							}
							Float total_pledge4 = jxOrderService
									.findAllTotalPledgeOfLowerToLastTime(
											m.get("id"), time, last_add_time);
							if (total_pledge4 == null) {
								total_pledge4 = 0f;
							}
							Float xf4 = jxOrderService.findAllXfMoneyOfTime(
									m.get("id"), time, last_add_time);

							Float xf_pledge4 = jxOrderService
									.findAllXfPledgeMoneyOfTime(m.get("id"),
											time, last_add_time);
							if (xf4 == null) {
								xf4 = 0f;
							}
							if (xf_pledge4 == null) {
								xf_pledge4 = 0f;
							}
							people4.setTotal_money((money4 - total_pledge4) + (xf4 - xf_pledge4));
							fl1 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee;// 返利
							people4.setBy_tkr_rebates(fee);
							people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee);
							people4.setService_fee((money4 - total_pledge4)
									* fee);// 服务费
							people4.setF_renewal((xf4 - xf_pledge4) * fee);// 服务续费
							jxDrawPeopleService.save(people4);

						}
					}
				}
			}
			if (level.equals("2")) {// 最上级为市
				System.out.println("---最上级为市---");
				// 得到区县级
				List<Map<String, Object>> tier = partnerService
						.findLevelOfUsername(username);// 区县级
				// 判断区县级是否为空
				if (tier.size() > 0) {// 区县级不为空 市----区县/产品经理
					for (int i = 0; i < tier.size(); i++) {
						Map<String, Object> m = tier.get(i);
						if (m.get("par_level").equals("3")) {// 市---区县/市---区县---产品经理
							JxDrawPeople people3 = new JxDrawPeople();
							people3.setAdd_time(new Date());
							people3.setTkr_id(username);
							people3.setBy_tkr_name(m.get("par_name") + "");
							people3.setBy_tkr_id(m.get("id") + "");
							people3.setTkr_state(0);
							people3.setWithdrawal_order(withdrawalOrderNo);
							int wall3 = jxOrderService.findWallNumber(
									m.get("id") + "", time, last_add_time);// 壁挂式台数
							int desktop3 = jxOrderService.findVerticalNumber(
									m.get("id") + "", time, last_add_time);// 台式
							int vertical3 = jxOrderService.findDesktopNumber(
									m.get("id") + "", time, last_add_time);// 立式
							// 续费台数
							int wall_renew3 = jxOrderService
									.findWallRenewNumber(m.get("id") + "",
											time, last_add_time);
							int desktop_renew3 = jxOrderService
									.findVerticalRenewNumber(m.get("id") + "",
											time, last_add_time);// desktop_renew
							int vertical_renew3 = jxOrderService
									.findDesktopRenewNumber(m.get("id") + "",
											time, last_add_time);// vertical_renew
							people3.setSell_wall(wall3);
							people3.setSell_vertical(vertical3);
							people3.setSell_desktop(desktop3);
							people3.setWall_renew(wall_renew3);
							people3.setVertical_renew(vertical_renew3);
							people3.setDesktop_renew(desktop_renew3);

							// 判断是否被分配下级
							JxRebateProportion jxProportion3 = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ m.get("id")
											+ "' and most_superior_id = '"
											+ username + "'");
							if (jxProportion3 != null) {
								fee = jxProportion3.getSuper_totall();
							}else{
								fee = jxRebates2.getService_fee();
							}
							Float money3 = jxOrderService.findAllMoneyOfTime(
									m.get("id"), time, last_add_time);
							Float total_pledge3 = jxOrderService
									.findAllTotalPledgeOfLowerToLastTime(
											m.get("id"), time, last_add_time);
							if (money3 == null) {
								money3 = 0f;
							}
							if (total_pledge3 == null) {
								total_pledge3 = 0f;
							}

							Float xf3 = jxOrderService.findAllXfMoneyOfTime(
									m.get("id"), time, last_add_time);

							Float xf_pledge3 = jxOrderService
									.findAllXfPledgeMoneyOfTime(m.get("id"),
											time, last_add_time);
							if (xf3 == null) {
								xf3 = 0f;
							}
							if (xf_pledge3 == null) {
								xf_pledge3 = 0f;
							}
							people3.setTotal_money((money3 - total_pledge3) + (xf3 - xf_pledge3));
							fl1 += ((money3 - total_pledge3) + (xf3 - xf_pledge3))
									* fee;// 返利
							people3.setBy_tkr_rebates(fee);
							people3.setBy_tkr_total_money(((money3 - total_pledge3) + (xf3 - xf_pledge3))
									* fee);
							people3.setService_fee((money3 - total_pledge3)
									* fee);// 服务费
							people3.setF_renewal((xf3 - xf_pledge3) * fee);// 服务续费
							jxDrawPeopleService.save(people3);
							List<Map<String, Object>> tiercp = partnerService
									.findLevelOfUsername(m.get("id"));// 产品经理
							if (tiercp.size() > 0) {// 产品经理
								for (int j = 0; j < tiercp.size(); j++) {
									Map<String, Object> m1 = tiercp.get(j);
									JxDrawPeople people4 = new JxDrawPeople();
									people4.setAdd_time(new Date());
									people4.setTkr_id(username);
									people4.setBy_tkr_name(m1.get("par_name")
											+ "");
									people4.setBy_tkr_id(m1.get("id") + "");
									people4.setTkr_state(1);
									people4.setWithdrawal_order(withdrawalOrderNo);
									people4.setBy_super_name(m.get("par_name")
											+ "");
									people4.setBy_super_tkr_id(m.get("id") + "");
									int wall4 = jxOrderService.findWallNumber(
											m1.get("id") + "", time,
											last_add_time);// 壁挂式台数
									int desktop4 = jxOrderService
											.findVerticalNumber(m1.get("id")
													+ "", time, last_add_time);// 台式
									int vertical4 = jxOrderService
											.findDesktopNumber(m1.get("id")
													+ "", time, last_add_time);// 立式
									// 续费台数
									int wall_renew4 = jxOrderService
											.findWallRenewNumber(m1.get("id")
													+ "", time, last_add_time);
									int desktop_renew4 = jxOrderService
											.findVerticalRenewNumber(
													m1.get("id") + "", time,
													last_add_time);// desktop_renew
									int vertical_renew4 = jxOrderService
											.findDesktopRenewNumber(
													m1.get("id") + "", time,
													last_add_time);// vertical_renew
									people4.setSell_wall(wall4);
									people4.setSell_vertical(vertical4);
									people4.setSell_desktop(desktop4);
									people4.setWall_renew(wall_renew4);
									people4.setVertical_renew(vertical_renew4);
									people4.setDesktop_renew(desktop_renew4);

									JxRebateProportion jxProportion4 = jxRebateProportionService
											.findUnique("from jx_rebate_proportion where user_number = '"
													+ m1.get("id")
													+ "' and most_superior_id = '"
													+ username + "'");
									if (jxProportion4 != null) {
										fee = jxProportion4.getSuper_totall();
										fee = IdentifyingUtil.proportion(level) - (jxProportion4.getRp_total() + IdentifyingUtil.qx());
									}else{
										fee = jxRebates2.getService_fee();
									}
									Float money4 = jxOrderService
											.findAllMoneyOfTime(m1.get("id"),
													time, last_add_time);
									Float total_pledge4 = jxOrderService
											.findAllTotalPledgeOfLowerToLastTime(
													m1.get("id"), time,
													last_add_time);
									if (money4 == null) {
										money4 = 0f;
									}
									if (total_pledge4 == null) {
										total_pledge4 = 0f;
									}

									Float xf4 = jxOrderService
											.findAllXfMoneyOfTime(m1.get("id"),
													time, last_add_time);

									Float xf_pledge4 = jxOrderService
											.findAllXfPledgeMoneyOfTime(
													m1.get("id"), time,
													last_add_time);
									if (xf4 == null) {
										xf4 = 0f;
									}
									if (xf_pledge4 == null) {
										xf_pledge4 = 0f;
									}
									people4.setTotal_money((money4 - total_pledge4) + (xf4 - xf_pledge4));
									fl2 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
											* fee;// 返利
									people4.setBy_tkr_rebates(fee);
									people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
											* fee);
									people4.setService_fee((money4 - total_pledge4)
											* fee);// 服务费
									people4.setF_renewal((xf4 - xf_pledge4)
											* fee);// 服务续费
									jxDrawPeopleService.save(people4);
								}
							}

						}
						if (m.get("par_level").equals("4")) {// 市---产品经理
							JxDrawPeople people4 = new JxDrawPeople();
							people4.setAdd_time(new Date());
							people4.setTkr_id(username);
							people4.setBy_tkr_name(m.get("par_name") + "");
							people4.setBy_tkr_id(m.get("id") + "");
							people4.setTkr_state(0);
							people4.setWithdrawal_order(withdrawalOrderNo);
							int wall = jxOrderService.findWallNumber(
									m.get("id") + "", time, last_add_time);// 壁挂式台数
							int desktop = jxOrderService.findVerticalNumber(
									m.get("id") + "", time, last_add_time);// 台式
							int vertical = jxOrderService.findDesktopNumber(
									m.get("id") + "", time, last_add_time);// 立式
							// 续费台数
							int wall_renew = jxOrderService
									.findWallRenewNumber(m.get("id") + "",
											time, last_add_time);
							int desktop_renew = jxOrderService
									.findVerticalRenewNumber(m.get("id") + "",
											time, last_add_time);// desktop_renew
							int vertical_renew = jxOrderService
									.findDesktopRenewNumber(m.get("id") + "",
											time, last_add_time);// vertical_renew
							people4.setSell_wall(wall);
							people4.setSell_vertical(vertical);
							people4.setSell_desktop(desktop);
							people4.setWall_renew(wall_renew);
							people4.setVertical_renew(vertical_renew);
							people4.setDesktop_renew(desktop_renew);

							JxRebates jxRebates = jxRebatesService
									.findUnique("from JxRebates where par_level = '"
											+ m.get("par_level") + "'");
							Float money4 = jxOrderService.findAllMoneyOfTime(
									m.get("id"), time, last_add_time);
							JxRebateProportion jxProportion4 = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ m.get("id")
											+ "' and most_superior_id = '"
											+ username + "'");
							if (jxProportion4 != null) {
								fee = jxProportion4.getSuper_totall();
								fee = IdentifyingUtil.proportion(level) - jxProportion4.getRp_total();
							} else {
								fee = (IdentifyingUtil.proportion(level) - jxRebates
										.getF_install());
							}
							if (money4 == null) {
								money4 = 0f;
							}
							Float total_pledge4 = jxOrderService
									.findAllTotalPledgeOfLowerToLastTime(
											m.get("id"), time, last_add_time);
							if (total_pledge4 == null) {
								total_pledge4 = 0f;
							}

							Float xf4 = jxOrderService.findAllXfMoneyOfTime(
									m.get("id"), time, last_add_time);

							Float xf_pledge4 = jxOrderService
									.findAllXfPledgeMoneyOfTime(m.get("id"),
											time, last_add_time);
							if (xf4 == null) {
								xf4 = 0f;
							}
							if (xf_pledge4 == null) {
								xf_pledge4 = 0f;
							}
							people4.setTotal_money((money4 - total_pledge4) + (xf4 - xf_pledge4));
							fl1 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee;// 返利
							people4.setBy_tkr_rebates(fee);
							people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee);
							people4.setService_fee((money4 - total_pledge4)
									* fee);// 服务费
							people4.setF_renewal((xf4 - xf_pledge4) * fee);// 服务续费
							jxDrawPeopleService.save(people4);
						}
					}
				}

			}
			if (level.equals("3")) {// 最上级为区县
				List<Map<String, Object>> tier = partnerService
						.findLevelOfUsername(username);// 产品经理
				if (tier.size() > 0) {// 区县---产品经理
					for (int i = 0; i < tier.size(); i++) {
						Map<String, Object> m = tier.get(i);
						JxDrawPeople people4 = new JxDrawPeople();
						people4.setAdd_time(new Date());
						people4.setTkr_id(username);
						people4.setBy_tkr_name(m.get("par_name") + "");
						people4.setBy_tkr_id(m.get("id") + "");
						people4.setTkr_state(0);
						people4.setWithdrawal_order(withdrawalOrderNo);
						int wall = jxOrderService.findWallNumber(m.get("id")
								+ "", time, last_add_time);// 壁挂式台数
						int desktop = jxOrderService.findVerticalNumber(
								m.get("id") + "", time, last_add_time);// 台式
						int vertical = jxOrderService.findDesktopNumber(
								m.get("id") + "", time, last_add_time);// 立式
						// 续费台数
						int wall_renew = jxOrderService.findWallRenewNumber(
								m.get("id") + "", time, last_add_time);
						int desktop_renew = jxOrderService
								.findVerticalRenewNumber(m.get("id") + "",
										time, last_add_time);// desktop_renew
						int vertical_renew = jxOrderService
								.findDesktopRenewNumber(m.get("id") + "", time,
										last_add_time);// vertical_renew
						people4.setSell_wall(wall);
						people4.setSell_vertical(vertical);
						people4.setSell_desktop(desktop);
						people4.setWall_renew(wall_renew);
						people4.setVertical_renew(vertical_renew);
						people4.setDesktop_renew(desktop_renew);

						// 得到下级返利
						JxRebateProportion jxProportion4 = jxRebateProportionService
								.findUnique("from jx_rebate_proportion where user_number = '"
										+ m.get("id")
										+ "' and most_superior_id = '"
										+ username + "'");
						if (jxProportion4 != null) {
							fee = jxProportion4.getSuper_totall();
							fee = IdentifyingUtil.proportion(level) - jxProportion4.getRp_total();
						}else{
							fee = jxRebates2.getService_fee();
						}
						Float money4 = jxOrderService.findAllMoneyOfTime(
								m.get("id"), time, last_add_time);
						if (money4 == null) {
							money4 = 0f;
						}
						Float total_pledge4 = jxOrderService
								.findAllTotalPledgeOfLowerToLastTime(
										m.get("id"), time, last_add_time);
						if (total_pledge4 == null) {
							total_pledge4 = 0f;
						}
						Float xf4 = jxOrderService.findAllXfMoneyOfTime(
								m.get("id"), time, last_add_time);

						Float xf_pledge4 = jxOrderService
								.findAllXfPledgeMoneyOfTime(m.get("id"), time,
										last_add_time);
						if (xf4 == null) {
							xf4 = 0f;
						}
						if (xf_pledge4 == null) {
							xf_pledge4 = 0f;
						}
						people4.setTotal_money((money4 - total_pledge4) + (xf4 - xf_pledge4));
						fl1 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
								* fee;// 返利
						people4.setBy_tkr_rebates(fee);
						people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
								* fee);
						people4.setService_fee((money4 - total_pledge4) * fee);// 服务费
						people4.setF_renewal((xf4 - xf_pledge4) * fee);// 服务续费
						jxDrawPeopleService.save(people4);
					}
				}
			}
		} else {
			// 有上级
			System.out.println("---有上级---");
			//根据上级id 得出上级级别
			System.out.println("-------------------------");
			
			List<Map<String, Object>> tier = partnerService
					.findLevelOfUsername(username);// 区县级、市
			if (tier.size() > 0) {// 区县级不为空 市----区县/产品经理
				for (int i = 0; i < tier.size(); i++) {
					Map<String, Object> m = tier.get(i);
					JxDrawPeople people3 = new JxDrawPeople();
					people3.setAdd_time(new Date());
					people3.setTkr_id(username);
					people3.setBy_tkr_name(m.get("par_name") + "");
					people3.setBy_tkr_id(m.get("id") + "");
					people3.setTkr_state(0);
					people3.setWithdrawal_order(withdrawalOrderNo);
					int wall3 = jxOrderService.findWallNumber(m.get("id") + "",
							time, last_add_time);// 壁挂式台数
					int desktop3 = jxOrderService.findVerticalNumber(
							m.get("id") + "", time, last_add_time);// 台式
					int vertical3 = jxOrderService.findDesktopNumber(
							m.get("id") + "", time, last_add_time);// 立式
					// 续费台数
					int wall_renew3 = jxOrderService.findWallRenewNumber(
							m.get("id") + "", time, last_add_time);
					int desktop_renew3 = jxOrderService
							.findVerticalRenewNumber(m.get("id") + "", time,
									last_add_time);// desktop_renew
					int vertical_renew3 = jxOrderService
							.findDesktopRenewNumber(m.get("id") + "", time,
									last_add_time);// vertical_renew
					people3.setSell_wall(wall3);
					people3.setSell_vertical(vertical3);
					people3.setSell_desktop(desktop3);
					people3.setWall_renew(wall_renew3);
					people3.setVertical_renew(vertical_renew3);
					people3.setDesktop_renew(desktop_renew3);

					Float money3 = jxOrderService.findAllMoneyOfTime(
							m.get("id"), time, last_add_time);
					Float total_pledge3 = jxOrderService
							.findAllTotalPledgeOfLowerToLastTime(m.get("id"),
									time, last_add_time);

					if (money3 == null) {
						money3 = 0f;
					}
					if (total_pledge3 == null) {
						total_pledge3 = 0f;
					}
					Float xf3 = jxOrderService.findAllXfMoneyOfTime(
							m.get("id"), time, last_add_time);

					Float xf_pledge3 = jxOrderService
							.findAllXfPledgeMoneyOfTime(m.get("id"), time,
									last_add_time);
					if (xf3 == null) {
						xf3 = 0f;
					}
					if (xf_pledge3 == null) {
						xf_pledge3 = 0f;
					}
					people3.setTotal_money((money3 - total_pledge3) + (xf3 - xf_pledge3));
					fl1 += ((money3 - total_pledge3) + (xf3 - xf_pledge3))
							* fee;// 返利
					people3.setBy_tkr_rebates(fee);
					people3.setBy_tkr_total_money(((money3 - total_pledge3) + (xf3 - xf_pledge3))
							* fee);
					people3.setService_fee((money3 - total_pledge3) * fee);// 服务费
					people3.setF_renewal((xf3 - xf_pledge3) * fee);// 服务续费
					jxDrawPeopleService.save(people3);
					List<Map<String, Object>> tiercp = partnerService
							.findLevelOfUsername(m.get("id"));// 产品经理
					if (tiercp.size() > 0) {// 产品经理
						for (int j = 0; j < tiercp.size(); j++) {
							Map<String, Object> m1 = tiercp.get(j);
							JxDrawPeople people4 = new JxDrawPeople();
							people4.setAdd_time(new Date());
							people4.setTkr_id(username);
							people4.setBy_tkr_name(m1.get("par_name") + "");
							people4.setBy_tkr_id(m1.get("id") + "");
							people4.setBy_super_tkr_id(m.get("id") + "");
							people4.setBy_super_name(m.get("par_name") + "");
							people4.setTkr_state(1);
							people4.setWithdrawal_order(withdrawalOrderNo);
							int wall4 = jxOrderService.findWallNumber(
									m1.get("id") + "", time, last_add_time);// 壁挂式台数
							int desktop4 = jxOrderService.findVerticalNumber(
									m1.get("id") + "", time, last_add_time);// 台式
							int vertical4 = jxOrderService.findDesktopNumber(
									m1.get("id") + "", time, last_add_time);// 立式
							// 续费台数
							int wall_renew4 = jxOrderService
									.findWallRenewNumber(m1.get("id") + "",
											time, last_add_time);
							int desktop_renew4 = jxOrderService
									.findVerticalRenewNumber(m1.get("id") + "",
											time, last_add_time);// desktop_renew
							int vertical_renew4 = jxOrderService
									.findDesktopRenewNumber(m1.get("id") + "",
											time, last_add_time);// vertical_renew
							people4.setSell_wall(wall4);
							people4.setSell_vertical(vertical4);
							people4.setSell_desktop(desktop4);
							people4.setWall_renew(wall_renew4);
							people4.setVertical_renew(vertical_renew4);
							people4.setDesktop_renew(desktop_renew4);

							Float money4 = jxOrderService.findAllMoneyOfTime(
									m1.get("id"), time, last_add_time);
							Float total_pledge4 = jxOrderService
									.findAllTotalPledgeOfLowerToLastTime(
											m1.get("id"), time, last_add_time);

							if (money4 == null) {
								money4 = 0f;
							}
							if (total_pledge4 == null) {
								total_pledge4 = 0f;
							}
							Float xf4 = jxOrderService.findAllXfMoneyOfTime(
									m1.get("id"), time, last_add_time);

							Float xf_pledge4 = jxOrderService
									.findAllXfPledgeMoneyOfTime(m1.get("id"),
											time, last_add_time);
							if (xf4 == null) {
								xf4 = 0f;
							}
							if (xf_pledge4 == null) {
								xf_pledge4 = 0f;
							}
							people4.setTotal_money((money4 - total_pledge4) + (xf4 - xf_pledge4));
							fl2 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee;// 返利
							people4.setBy_tkr_rebates(fee);
							people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee);
							people4.setService_fee((money4 - total_pledge4)
									* fee);// 服务费
							people4.setF_renewal((xf4 - xf_pledge4) * fee);// 服务续费
							jxDrawPeopleService.save(people4);
						}
					}
				}
			}
		}

		
		Float zxj = fl1 + fl2 + fl3;
		String xj = decimalFormat.format(zxj);
		Float lower = Float.valueOf(xj);
		System.out.println("下级返利:" + xj);
		tatol_fwf = tatol_fwf + lower;
		System.out.println("总服务费:" + tatol_fwf);
		String p = decimalFormat.format(tatol_fwf);
		Float sum = Float.valueOf(p);

		System.out.println("time:"+time);
		System.out.println("last_add_time:"+last_add_time);
		int wall = jxOrderService
				.findWallNumbers(username, time, last_add_time);// 壁挂式台数
		int desktop = jxOrderService.findVerticalNumbers(username, time,
				last_add_time);// 台式
		int vertical = jxOrderService.findDesktopNumbers(username, time,
				last_add_time);// 立式
		// 续费台数
		int wall_renew = jxOrderService.findWallRenewNumbers(username, time,
				last_add_time);
		int desktop_renew = jxOrderService.findVerticalRenewNumbers(username,
				time, last_add_time);// desktop_renew
		int vertical_renew = jxOrderService.findDesktopRenewNumbers(username,
				time, last_add_time);// vertical_renew

		System.out.println("壁挂台数:"+wall);
		System.out.println("立式台数:"+vertical);
		System.out.println("台数台数:"+desktop);
		System.out.println("壁挂续费台数:"+wall_renew);
		System.out.println("立式续费台数:"+vertical_renew);
		System.out.println("台数续费台数:"+desktop_renew);
		
		if (partnerRebate == null) {
			System.out.println("1");
			jxPartnerRebate.setUser_name(username);
			jxPartnerRebate.setTotal_amount(sum);
			jxPartnerRebate.setWithdrawal_order(withdrawalOrderNo);// 提现单号
			jxPartnerRebate.setService_fee(fw);// 服务费返利
			jxPartnerRebate.setF_renewal(xf);// 续费返利
			jxPartnerRebate.setLower_rebate(lower);// 返利补贴
			jxPartnerRebate.setF_installation(installation_whf);// 安装费补贴
			jxPartnerRebate.setSell_wall(wall);
			jxPartnerRebate.setSell_vertical(vertical);
			jxPartnerRebate.setSell_desktop(desktop);
			jxPartnerRebate.setWall_renew(wall_renew);
			jxPartnerRebate.setVertical_renew(vertical_renew);
			jxPartnerRebate.setDesktop_renew(desktop_renew);
			jxPartnerRebate.setBuy_combined(fwf);
			jxPartnerRebate.setRenewal_combined(renewal);
			jxPartnerRebate.setAdd_time(new Date());
		} else {
			System.out.println("2");
			partnerRebate.setUser_name(username);
			partnerRebate.setTotal_amount(sum);
			partnerRebate.setWithdrawal_order(withdrawalOrderNo);// 提现单号
			partnerRebate.setService_fee(fw);// 服务费返利
			partnerRebate.setF_renewal(xf);// 续费返利
			partnerRebate.setLower_rebate(lower);// 返利补贴
			partnerRebate.setF_installation(installation_whf);// 安装费补贴
			partnerRebate.setSell_wall(wall);
			partnerRebate.setSell_vertical(vertical);
			partnerRebate.setSell_desktop(desktop);
			partnerRebate.setWall_renew(wall_renew);
			partnerRebate.setVertical_renew(vertical_renew);
			partnerRebate.setDesktop_renew(desktop_renew);
			partnerRebate.setBuy_combined(fwf);
			partnerRebate.setRenewal_combined(renewal);
			partnerRebate.setAdd_time(new Date());
			partnerRebate.setMod_time(new Date());
		}
		return sum;
	}
	
	
	/*private Float rebates(Float fee, Float maintenance, Float install,
			String username, String time, String withdrawalOrderNo,
			JxPartnerRebate jxPartnerRebate, JxPartnerRebate partnerRebate,
			String level, JxRebateProportion proportion, JxRebates jxRebates2,
			JxPartner partner) {
		// 取出最后一次提现的时间
		List<Map<String, Object>> mm = jxPartnerRebateService
				.findLastAddtime(username);
		String last_add_time = null;
		if (mm.size() <= 0) {
			last_add_time = "0000-00-00 00:00:00";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> ma = mm.get(0);
			Date s = (Date) ma.get("add_time");
			last_add_time = sdf.format(s);
		}
		DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		// 服务费返利
		Float money1 = jxOrderService.findAllMoneyOfTime(username, time,
				last_add_time);
		if (money1 == null) {
			money1 = 0f;
		}
		System.out.println("money1:" + money1);
		// 服务费押金
		Float Service_charge = jxOrderService
				.findAllTotalPledgeOfLowerToLastTime(username, time,
						last_add_time);
		if (Service_charge == null) {
			Service_charge = 0f;
		}
		System.out.println("Service_charge:" + Service_charge);
		Float fwf = money1 - Service_charge;// 总服务费金额
		System.out.println("fwf:" + fwf);
		// 续费返利
		Float renewal1 = jxOrderService.findAllXfMoneyOfTime(username, time,
				last_add_time);
		if (renewal1 == null) {
			renewal1 = 0f;
		}
		// 续费押金
		Float Renew_the_deposit = jxOrderService.findAllXfPledgeMoneyOfTime(
				username, time, last_add_time);
		if (Renew_the_deposit == null) {
			Renew_the_deposit = 0f;
		}
		Float renewal = renewal1 - Renew_the_deposit;// 续费总金额
		System.out.println("renewal:" + renewal);
		// 安装费返利
		Float installation1 = jxOrderService.findCostMoneyOrBg(username, time,
				last_add_time);
		if (installation1 == null) {
			installation1 = 0f;
		}
		// 安装费押金
		Float Security_deposit = jxOrderService.findCostYJ(username, time,
				last_add_time);
		if (Security_deposit == null) {
			Security_deposit = 0f;
		}
		Float installation_whf = (installation1 - Security_deposit)
				* maintenance;// 总安装费金额(维护费)
		String installation_whf1 = decimalFormat.format(installation_whf);
		installation_whf = Float.valueOf(installation_whf1);
		System.out.println("总安装费:" + installation_whf);
		System.out.println("---服务费---");

		Float f = (fwf + renewal) * fee;// 总服务费
		Float tatol_fwf = f + installation_whf;// 总费用

		Float fw = fwf * fee;
		Float xf = renewal * fee;
		String fw1 = decimalFormat.format(fw);
		fw = Float.valueOf(fw1);
		
		String xf1 = decimalFormat.format(xf);
		xf = Float.valueOf(xf1);
		System.out.println("服务费:" + fw);
		System.out.println("服务续费:" + xf);
		System.out.println("fwf:" + fwf);
		System.out.println("renewal:" + renewal);

		String par_parentid = partnerService.findParentid(username);//得到上级
		// 下级返利
		Float fl1 = 0f;
		Float fl2 = 0f;
		Float fl3 = 0f;
		fee = jxRebates2.getService_fee();
		if (par_parentid == null) {// 最高级用户或没有被分配比例
			if (level.equals("1")) {// 最上级为省
				List<Map<String, Object>> tier = partnerService
						.findLevelOfUsername(username);// 市、区县级、产品经理
				if (tier.size() > 0) {// 有 市、区县级、产品经理
					for (int i = 0; i < tier.size(); i++) {
						Map<String, Object> m = tier.get(i);
						if (m.get("par_level").equals("2")) {// 省----市
							// 判断是否拥有下级
							List<Map<String, Object>> tierqx = partnerService
									.findLevelOfUsername(m.get("id"));// 区县级、产品经理
							if (tierqx.size() <= 0) {// 没有下级
								JxDrawPeople people2 = new JxDrawPeople();
								people2.setAdd_time(new Date());
								people2.setTkr_id(username);
								people2.setBy_tkr_name(m.get("par_name") + "");
								people2.setBy_tkr_id(m.get("id") + "");
								people2.setTkr_state(0);
								people2.setWithdrawal_order(withdrawalOrderNo);
								int wall2 = jxOrderService.findWallNumber(
										m.get("id") + "", time, last_add_time);// 壁挂式台数
								int desktop2 = jxOrderService
										.findVerticalNumber(m.get("id") + "",
												time, last_add_time);// 台式
								int vertical2 = jxOrderService
										.findDesktopNumber(m.get("id") + "",
												time, last_add_time);// 立式
								// 续费台数
								int wall_renew2 = jxOrderService
										.findWallRenewNumber(m.get("id") + "",
												time, last_add_time);
								int desktop_renew2 = jxOrderService
										.findVerticalRenewNumber(m.get("id")
												+ "", time, last_add_time);// desktop_renew
								int vertical_renew2 = jxOrderService
										.findDesktopRenewNumber(m.get("id")
												+ "", time, last_add_time);// vertical_renew
								people2.setSell_wall(wall2);
								people2.setSell_vertical(vertical2);
								people2.setSell_desktop(desktop2);
								people2.setWall_renew(wall_renew2);
								people2.setVertical_renew(vertical_renew2);
								people2.setDesktop_renew(desktop_renew2);

								JxRebateProportion jxProportion2 = jxRebateProportionService
										.findUnique("from jx_rebate_proportion where user_number = '"
												+ m.get("id")
												+ "' and most_superior_id = '"
												+ username + "'");
								if (jxProportion2 != null) {
									fee = jxProportion2.getSuper_totall();
								} else {
									fee = IdentifyingUtil.ssx();
								}
								Float money2 = jxOrderService
										.findAllMoneyOfTime(m.get("id"), time,
												last_add_time);
								Float total_pledge2 = jxOrderService
										.findAllTotalPledgeOfLowerToLastTime(
												m.get("id"), time,
												last_add_time);
								if (money2 == null) {
									money2 = 0f;
								}
								if (total_pledge2 == null) {
									total_pledge2 = 0f;
								}
								Float xf2 = jxOrderService
										.findAllXfMoneyOfTime(m.get("id"),
												time, last_add_time);

								Float xf_pledge2 = jxOrderService
										.findAllXfPledgeMoneyOfTime(
												m.get("id"), time,
												last_add_time);
								if (xf2 == null) {
									xf2 = 0f;
								}
								if (xf_pledge2 == null) {
									xf_pledge2 = 0f;
								}
								fl1 += ((money2 - total_pledge2) + (xf2 - xf_pledge2))
										* fee;// 返利
								people2.setBy_tkr_rebates(fee);
								people2.setBy_tkr_total_money(((money2 - total_pledge2) + (xf2 - xf_pledge2))
										* fee);
								people2.setService_fee((money2 - total_pledge2)
										* fee);// 服务费
								people2.setF_renewal((xf2 - xf_pledge2) * fee);// 服务续费
								jxDrawPeopleService.save(people2);
							} else {
								// 市有下级并且下级是区县
								JxDrawPeople people2 = new JxDrawPeople();
								people2.setAdd_time(new Date());
								people2.setTkr_id(username);
								people2.setBy_tkr_name(m.get("par_name") + "");
								people2.setBy_tkr_id(m.get("id") + "");
								people2.setTkr_state(0);
								people2.setWithdrawal_order(withdrawalOrderNo);
								int wall2 = jxOrderService.findWallNumber(
										m.get("id") + "", time, last_add_time);// 壁挂式台数
								int desktop2 = jxOrderService
										.findVerticalNumber(m.get("id") + "",
												time, last_add_time);// 台式
								int vertical2 = jxOrderService
										.findDesktopNumber(m.get("id") + "",
												time, last_add_time);// 立式
								// 续费台数
								int wall_renew2 = jxOrderService
										.findWallRenewNumber(m.get("id") + "",
												time, last_add_time);
								int desktop_renew2 = jxOrderService
										.findVerticalRenewNumber(m.get("id")
												+ "", time, last_add_time);// desktop_renew
								int vertical_renew2 = jxOrderService
										.findDesktopRenewNumber(m.get("id")
												+ "", time, last_add_time);// vertical_renew
								people2.setSell_wall(wall2);
								people2.setSell_vertical(vertical2);
								people2.setSell_desktop(desktop2);
								people2.setWall_renew(wall_renew2);
								people2.setVertical_renew(vertical_renew2);
								people2.setDesktop_renew(desktop_renew2);
								for (int j = 0; j < tierqx.size(); j++) {
									Map<String, Object> m1 = tierqx.get(j);
									if (m1.get("par_level").equals("3")) {// 省---市---区县
										// 市
										JxRebateProportion jxProportion2 = jxRebateProportionService
												.findUnique("from jx_rebate_proportion where user_number = '"
														+ m.get("id")
														+ "' and most_superior_id = '"
														+ username + "'");
										if (jxProportion2 != null) {
											fee = jxProportion2
													.getSuper_totall();
										} else {
											fee = jxRebates2.getService_fee();
										}
										Float money2 = jxOrderService
												.findAllMoneyOfTime(
														m.get("id"), time,
														last_add_time);
										Float total_pledge2 = jxOrderService
												.findAllTotalPledgeOfLowerToLastTime(
														m.get("id"), time,
														last_add_time);
										if (money2 == null) {
											money2 = 0f;
										}
										if (total_pledge2 == null) {
											total_pledge2 = 0f;
										}
										Float xf2 = jxOrderService
												.findAllXfMoneyOfTime(
														m.get("id"), time,
														last_add_time);

										Float xf_pledge2 = jxOrderService
												.findAllXfPledgeMoneyOfTime(
														m.get("id"), time,
														last_add_time);
										if (xf2 == null) {
											xf2 = 0f;
										}
										if (xf_pledge2 == null) {
											xf_pledge2 = 0f;
										}
										fl1 += ((money2 - total_pledge2) + (xf2 - xf_pledge2))
												* fee;// 返利
										people2.setBy_tkr_rebates(fee);
										people2.setBy_tkr_total_money(((money2 - total_pledge2) + (xf2 - xf_pledge2))
												* fee);
										people2.setService_fee((money2 - total_pledge2)
												* fee);// 服务费
										people2.setF_renewal((xf2 - xf_pledge2)
												* fee);// 服务续费
										jxDrawPeopleService.save(people2);

										JxDrawPeople people3 = new JxDrawPeople();
										people3.setAdd_time(new Date());
										people3.setTkr_id(username);
										people3.setBy_tkr_name(m1
												.get("par_name") + "");
										people3.setBy_tkr_id(m1.get("id") + "");
										people3.setTkr_state(1);
										people3.setWithdrawal_order(withdrawalOrderNo);
										people3.setBy_super_name(m
												.get("par_name") + "");
										people3.setBy_super_tkr_id(m.get("id")
												+ "");
										int wall3 = jxOrderService
												.findWallNumber(m1.get("id")
														+ "", time,
														last_add_time);// 壁挂式台数
										int desktop3 = jxOrderService
												.findVerticalNumber(
														m1.get("id") + "",
														time, last_add_time);// 台式
										int vertical3 = jxOrderService
												.findDesktopNumber(m1.get("id")
														+ "", time,
														last_add_time);// 立式
										// 续费台数
										int wall_renew3 = jxOrderService
												.findWallRenewNumber(
														m1.get("id") + "",
														time, last_add_time);
										int desktop_renew3 = jxOrderService
												.findVerticalRenewNumber(
														m1.get("id") + "",
														time, last_add_time);// desktop_renew
										int vertical_renew3 = jxOrderService
												.findDesktopRenewNumber(
														m1.get("id") + "",
														time, last_add_time);// vertical_renew
										people3.setSell_wall(wall3);
										people3.setSell_vertical(vertical3);
										people3.setSell_desktop(desktop3);
										people3.setWall_renew(wall_renew3);
										people3.setVertical_renew(vertical_renew3);
										people3.setDesktop_renew(desktop_renew3);

										JxRebateProportion jxProportion3 = jxRebateProportionService
												.findUnique("from jx_rebate_proportion where user_number = '"
														+ m1.get("id")
														+ "' and most_superior_id = '"
														+ username + "'");
										if (jxProportion3 != null) {
											fee = jxProportion3
													.getSuper_totall();
										} else {
											fee = jxRebates2.getService_fee();
										}
										Float money3 = jxOrderService
												.findAllMoneyOfTime(
														m1.get("id"), time,
														last_add_time);
										Float total_pledge3 = jxOrderService
												.findAllTotalPledgeOfLowerToLastTime(
														m1.get("id"), time,
														last_add_time);
										if (money3 == null) {
											money3 = 0f;
										}
										if (total_pledge3 == null) {
											total_pledge3 = 0f;
										}
										Float xf3 = jxOrderService
												.findAllXfMoneyOfTime(
														m1.get("id"), time,
														last_add_time);

										Float xf_pledge3 = jxOrderService
												.findAllXfPledgeMoneyOfTime(
														m1.get("id"), time,
														last_add_time);
										if (xf3 == null) {
											xf3 = 0f;
										}
										if (xf_pledge3 == null) {
											xf_pledge3 = 0f;
										}
										fl2 += ((money3 - total_pledge3) + (xf3 - xf_pledge3))
												* fee;// 返利
										people3.setBy_tkr_rebates(fee);
										people3.setBy_tkr_total_money(((money3 - total_pledge3) + (xf3 - xf_pledge3))
												* fee);
										people3.setService_fee((money3 - total_pledge3)
												* fee);// 服务费
										people3.setF_renewal((xf3 - xf_pledge3)
												* fee);// 服务续费
										jxDrawPeopleService.save(people3);
										List<Map<String, Object>> tiercp = partnerService
												.findLevelOfUsername(m1
														.get("id"));// 产品经理
										if (tiercp.size() > 0) {
											for (int z = 0; z < tiercp.size(); z++) {
												Map<String, Object> m2 = tiercp
														.get(z);
												JxDrawPeople people4 = new JxDrawPeople();
												people4.setAdd_time(new Date());
												people4.setTkr_id(username);
												people4.setBy_tkr_name(m2
														.get("par_name") + "");
												people4.setBy_tkr_id(m2
														.get("id") + "");
												people4.setTkr_state(1);
												people4.setWithdrawal_order(withdrawalOrderNo);
												people4.setBy_super_name(m1
														.get("par_name") + "");
												people4.setBy_super_tkr_id(m1
														.get("id") + "");
												int wall4 = jxOrderService
														.findWallNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);// 壁挂式台数
												int desktop4 = jxOrderService
														.findVerticalNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);// 台式
												int vertical4 = jxOrderService
														.findDesktopNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);// 立式
												// 续费台数
												int wall_renew4 = jxOrderService
														.findWallRenewNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);
												int desktop_renew4 = jxOrderService
														.findVerticalRenewNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);// desktop_renew
												int vertical_renew4 = jxOrderService
														.findDesktopRenewNumber(
																m2.get("id")
																		+ "",
																time,
																last_add_time);// vertical_renew
												people4.setSell_wall(wall4);
												people4.setSell_vertical(vertical4);
												people4.setSell_desktop(desktop4);
												people4.setWall_renew(wall_renew4);
												people4.setVertical_renew(vertical_renew4);
												people4.setDesktop_renew(desktop_renew4);

												JxRebateProportion jxProportion4 = jxRebateProportionService
														.findUnique("from jx_rebate_proportion where user_number = '"
																+ m2.get("id")
																+ "' and most_superior_id = '"
																+ username
																+ "'");
												if (jxProportion4 != null) {
													fee = jxProportion4
															.getSuper_totall();
												} else {
													fee = jxRebates2
															.getService_fee();
												}
												Float money4 = jxOrderService
														.findAllMoneyOfTime(
																m2.get("id"),
																time,
																last_add_time);
												Float total_pledge4 = jxOrderService
														.findAllTotalPledgeOfLowerToLastTime(
																m2.get("id"),
																time,
																last_add_time);
												if (money4 == null) {
													money4 = 0f;
												}
												if (total_pledge4 == null) {
													total_pledge4 = 0f;
												}
												Float xf4 = jxOrderService
														.findAllXfMoneyOfTime(
																m2.get("id"),
																time,
																last_add_time);

												Float xf_pledge4 = jxOrderService
														.findAllXfPledgeMoneyOfTime(
																m2.get("id"),
																time,
																last_add_time);
												if (xf4 == null) {
													xf4 = 0f;
												}
												if (xf_pledge4 == null) {
													xf_pledge4 = 0f;
												}
												fl3 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
														* fee;// 返利
												people4.setBy_tkr_rebates(fee);
												people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
														* fee);
												people4.setService_fee((money4 - total_pledge4)
														* fee);// 服务费
												people4.setF_renewal((xf4 - xf_pledge4)
														* fee);// 服务续费
												jxDrawPeopleService
														.save(people4);
											}
										}
									} else {
										// 下级是产品经理
										JxRebateProportion jxProportion2 = jxRebateProportionService
												.findUnique("from jx_rebate_proportion where user_number = '"
														+ m.get("id")
														+ "' and most_superior_id = '"
														+ username + "'");
										if (jxProportion2 != null) {
											fee = jxProportion2
													.getSuper_totall();
										} else {
											fee = IdentifyingUtil.ssx();
										}
										Float money2 = jxOrderService
												.findAllMoneyOfTime(
														m.get("id"), time,
														last_add_time);
										Float total_pledge2 = jxOrderService
												.findAllTotalPledgeOfLowerToLastTime(
														m.get("id"), time,
														last_add_time);
										if (money2 == null) {
											money2 = 0f;
										}
										if (total_pledge2 == null) {
											total_pledge2 = 0f;
										}
										Float xf2 = jxOrderService
												.findAllXfMoneyOfTime(
														m.get("id"), time,
														last_add_time);

										Float xf_pledge2 = jxOrderService
												.findAllXfPledgeMoneyOfTime(
														m.get("id"), time,
														last_add_time);
										if (xf2 == null) {
											xf2 = 0f;
										}
										if (xf_pledge2 == null) {
											xf_pledge2 = 0f;
										}
										fl1 += ((money2 - total_pledge2) + (xf2 - xf_pledge2))
												* fee;// 返利
										people2.setBy_tkr_rebates(fee);
										people2.setBy_tkr_total_money(((money2 - total_pledge2) + (xf2 - xf_pledge2))
												* fee);
										people2.setService_fee((money2 - total_pledge2)
												* fee);// 服务费
										people2.setF_renewal((xf2 - xf_pledge2)
												* fee);// 服务续费
										jxDrawPeopleService.save(people2);

										JxDrawPeople people4 = new JxDrawPeople();
										people4.setAdd_time(new Date());
										people4.setTkr_id(username);
										people4.setBy_tkr_name(m1
												.get("par_name") + "");
										people4.setBy_tkr_id(m1.get("id") + "");
										people4.setTkr_state(1);
										people4.setWithdrawal_order(withdrawalOrderNo);
										people4.setBy_super_name(m
												.get("par_name") + "");
										people4.setBy_super_tkr_id(m.get("id")
												+ "");
										int wall4 = jxOrderService
												.findWallNumber(m1.get("id")
														+ "", time,
														last_add_time);// 壁挂式台数
										int desktop4 = jxOrderService
												.findVerticalNumber(
														m1.get("id") + "",
														time, last_add_time);// 台式
										int vertical4 = jxOrderService
												.findDesktopNumber(m1.get("id")
														+ "", time,
														last_add_time);// 立式
										// 续费台数
										int wall_renew4 = jxOrderService
												.findWallRenewNumber(
														m1.get("id") + "",
														time, last_add_time);
										int desktop_renew4 = jxOrderService
												.findVerticalRenewNumber(
														m1.get("id") + "",
														time, last_add_time);// desktop_renew
										int vertical_renew4 = jxOrderService
												.findDesktopRenewNumber(
														m1.get("id") + "",
														time, last_add_time);// vertical_renew
										people4.setSell_wall(wall4);
										people4.setSell_vertical(vertical4);
										people4.setSell_desktop(desktop4);
										people4.setWall_renew(wall_renew4);
										people4.setVertical_renew(vertical_renew4);
										people4.setDesktop_renew(desktop_renew4);

										JxRebateProportion jxProportion4 = jxRebateProportionService
												.findUnique("from jx_rebate_proportion where user_number = '"
														+ m1.get("id")
														+ "' and most_superior_id = '"
														+ username + "'");
										if (jxProportion4 != null) {
											fee = jxProportion4
													.getSuper_totall();
										} else {
											fee = IdentifyingUtil.ssx();
										}
										Float money4 = jxOrderService
												.findAllMoneyOfTime(
														m1.get("id"), time,
														last_add_time);
										Float total_pledge4 = jxOrderService
												.findAllTotalPledgeOfLowerToLastTime(
														m1.get("id"), time,
														last_add_time);
										if (money4 == null) {
											money4 = 0f;
										}
										if (total_pledge4 == null) {
											total_pledge4 = 0f;
										}
										Float xf4 = jxOrderService
												.findAllXfMoneyOfTime(
														m1.get("id"), time,
														last_add_time);

										Float xf_pledge4 = jxOrderService
												.findAllXfPledgeMoneyOfTime(
														m1.get("id"), time,
														last_add_time);
										if (xf4 == null) {
											xf4 = 0f;
										}
										if (xf_pledge4 == null) {
											xf_pledge4 = 0f;
										}
										fl2 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
												* fee;// 返利
										people4.setBy_tkr_rebates(fee);
										people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
												* fee);
										people4.setService_fee((money4 - total_pledge4)
												* fee);// 服务费
										people4.setF_renewal((xf4 - xf_pledge4)
												* fee);// 服务续费
										jxDrawPeopleService.save(people4);
									}
								}
							}
						}

						if (m.get("par_level").equals("3")) {// 省---区县
							JxDrawPeople people3 = new JxDrawPeople();
							people3.setAdd_time(new Date());
							people3.setTkr_id(username);
							people3.setBy_tkr_name(m.get("par_name") + "");
							people3.setBy_tkr_id(m.get("id") + "");
							people3.setTkr_state(0);
							people3.setWithdrawal_order(withdrawalOrderNo);
							int wall3 = jxOrderService.findWallNumber(
									m.get("id") + "", time, last_add_time);// 壁挂式台数
							int desktop3 = jxOrderService.findVerticalNumber(
									m.get("id") + "", time, last_add_time);// 台式
							int vertical3 = jxOrderService.findDesktopNumber(
									m.get("id") + "", time, last_add_time);// 立式
							// 续费台数
							int wall_renew3 = jxOrderService
									.findWallRenewNumber(m.get("id") + "",
											time, last_add_time);
							int desktop_renew3 = jxOrderService
									.findVerticalRenewNumber(m.get("id") + "",
											time, last_add_time);// desktop_renew
							int vertical_renew3 = jxOrderService
									.findDesktopRenewNumber(m.get("id") + "",
											time, last_add_time);// vertical_renew
							people3.setSell_wall(wall3);
							people3.setSell_vertical(vertical3);
							people3.setSell_desktop(desktop3);
							people3.setWall_renew(wall_renew3);
							people3.setVertical_renew(vertical_renew3);
							people3.setDesktop_renew(desktop_renew3);

							JxRebateProportion jxProportion3 = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ m.get("id")
											+ "' and most_superior_id = '"
											+ username + "'");
							if (jxProportion3 != null) {
								fee = jxProportion3.getSuper_totall();
							} else {
								fee = IdentifyingUtil.sqc();
							}
							Float money3 = jxOrderService.findAllMoneyOfTime(
									m.get("id"), time, last_add_time);
							Float total_pledge3 = jxOrderService
									.findAllTotalPledgeOfLowerToLastTime(
											m.get("id"), time, last_add_time);
							if (money3 == null) {
								money3 = 0f;
							}
							if (total_pledge3 == null) {
								total_pledge3 = 0f;
							}
							Float xf3 = jxOrderService.findAllXfMoneyOfTime(
									m.get("id"), time, last_add_time);

							Float xf_pledge3 = jxOrderService
									.findAllXfPledgeMoneyOfTime(m.get("id"),
											time, last_add_time);
							if (xf3 == null) {
								xf3 = 0f;
							}
							if (xf_pledge3 == null) {
								xf_pledge3 = 0f;
							}
							fl1 += ((money3 - total_pledge3) + (xf3 - xf_pledge3))
									* fee;// 返利
							people3.setBy_tkr_rebates(fee);
							people3.setBy_tkr_total_money(((money3 - total_pledge3) + (xf3 - xf_pledge3))
									* fee);
							people3.setService_fee((money3 - total_pledge3)
									* fee);// 服务费
							people3.setF_renewal((xf3 - xf_pledge3) * fee);// 服务续费
							jxDrawPeopleService.save(people3);
							List<Map<String, Object>> tiercp = partnerService
									.findLevelOfUsername(m.get("id"));// 产品经理
							if (tiercp.size() > 0) {// 有下级产品经理
								for (int j = 0; j < tiercp.size(); j++) {
									Map<String, Object> m1 = tiercp.get(j);
									JxDrawPeople people4 = new JxDrawPeople();
									people4.setAdd_time(new Date());
									people4.setTkr_id(username);
									people4.setBy_tkr_name(m1.get("par_name")
											+ "");
									people4.setBy_tkr_id(m1.get("id") + "");
									people4.setTkr_state(1);
									people4.setWithdrawal_order(withdrawalOrderNo);
									people4.setBy_super_name(m.get("par_name")
											+ "");
									people4.setBy_super_tkr_id(m.get("id") + "");
									int wall4 = jxOrderService.findWallNumber(
											m1.get("id") + "", time,
											last_add_time);// 壁挂式台数
									int desktop4 = jxOrderService
											.findVerticalNumber(m1.get("id")
													+ "", time, last_add_time);// 台式
									int vertical4 = jxOrderService
											.findDesktopNumber(m1.get("id")
													+ "", time, last_add_time);// 立式
									// 续费台数
									int wall_renew4 = jxOrderService
											.findWallRenewNumber(m1.get("id")
													+ "", time, last_add_time);
									int desktop_renew4 = jxOrderService
											.findVerticalRenewNumber(
													m1.get("id") + "", time,
													last_add_time);// desktop_renew
									int vertical_renew4 = jxOrderService
											.findDesktopRenewNumber(
													m1.get("id") + "", time,
													last_add_time);// vertical_renew
									people4.setSell_wall(wall4);
									people4.setSell_vertical(vertical4);
									people4.setSell_desktop(desktop4);
									people4.setWall_renew(wall_renew4);
									people4.setVertical_renew(vertical_renew4);
									people4.setDesktop_renew(desktop_renew4);

									JxRebateProportion jxProportion4 = jxRebateProportionService
											.findUnique("from jx_rebate_proportion where user_number = '"
													+ m1.get("id")
													+ "' and most_superior_id = '"
													+ username + "'");
									if (jxProportion4 != null) {
										fee = jxProportion4.getSuper_totall();
									} else {
										fee = IdentifyingUtil.sqc();
									}
									Float money4 = jxOrderService
											.findAllMoneyOfTime(m1.get("id"),
													time, last_add_time);
									Float total_pledge4 = jxOrderService
											.findAllTotalPledgeOfLowerToLastTime(
													m1.get("id"), time,
													last_add_time);
									if (money4 == null) {
										money4 = 0f;
									}
									if (total_pledge4 == null) {
										total_pledge4 = 0f;
									}
									Float xf4 = jxOrderService
											.findAllXfMoneyOfTime(m1.get("id"),
													time, last_add_time);

									Float xf_pledge4 = jxOrderService
											.findAllXfPledgeMoneyOfTime(
													m1.get("id"), time,
													last_add_time);
									if (xf4 == null) {
										xf4 = 0f;
									}
									if (xf_pledge4 == null) {
										xf_pledge4 = 0f;
									}
									fl2 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
											* fee;// 返利
									people4.setBy_tkr_rebates(fee);
									people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
											* fee);
									people4.setService_fee((money4 - total_pledge4)
											* fee);// 服务费
									people4.setF_renewal((xf4 - xf_pledge4)
											* fee);// 服务续费
									jxDrawPeopleService.save(people4);
								}
							}

						}
						if (m.get("par_level").equals("4")) {// 省---产品经理
							JxDrawPeople people4 = new JxDrawPeople();
							people4.setAdd_time(new Date());
							people4.setTkr_id(username);
							people4.setBy_tkr_name(m.get("par_name") + "");
							people4.setBy_tkr_id(m.get("id") + "");
							people4.setTkr_state(0);
							people4.setWithdrawal_order(withdrawalOrderNo);
							int wall4 = jxOrderService.findWallNumber(
									m.get("id") + "", time, last_add_time);// 壁挂式台数
							int desktop4 = jxOrderService.findVerticalNumber(
									m.get("id") + "", time, last_add_time);// 台式
							int vertical4 = jxOrderService.findDesktopNumber(
									m.get("id") + "", time, last_add_time);// 立式
							// 续费台数
							int wall_renew4 = jxOrderService
									.findWallRenewNumber(m.get("id") + "",
											time, last_add_time);
							int desktop_renew4 = jxOrderService
									.findVerticalRenewNumber(m.get("id") + "",
											time, last_add_time);// desktop_renew
							int vertical_renew4 = jxOrderService
									.findDesktopRenewNumber(m.get("id") + "",
											time, last_add_time);// vertical_renew
							people4.setSell_wall(wall4);
							people4.setSell_vertical(vertical4);
							people4.setSell_desktop(desktop4);
							people4.setWall_renew(wall_renew4);
							people4.setVertical_renew(vertical_renew4);
							people4.setDesktop_renew(desktop_renew4);

							JxRebates jxRebates = jxRebatesService
									.findUnique("from JxRebates where par_level = '"
											+ m.get("par_level") + "'");
							// 判断是否被分配下级比例
							JxRebateProportion jxProportion4 = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ m.get("id")
											+ "' and most_superior_id = '"
											+ username + "'");
							if (jxProportion4 != null) {
								fee = jxProportion4.getSuper_totall();
							} else {
								fee = (IdentifyingUtil.proportion(level) - jxRebates
										.getF_install());
							}
							Float money4 = jxOrderService.findAllMoneyOfTime(
									m.get("id"), time, last_add_time);
							if (money4 == null) {
								money4 = 0f;
							}
							Float total_pledge4 = jxOrderService
									.findAllTotalPledgeOfLowerToLastTime(
											m.get("id"), time, last_add_time);
							if (total_pledge4 == null) {
								total_pledge4 = 0f;
							}
							Float xf4 = jxOrderService.findAllXfMoneyOfTime(
									m.get("id"), time, last_add_time);

							Float xf_pledge4 = jxOrderService
									.findAllXfPledgeMoneyOfTime(m.get("id"),
											time, last_add_time);
							if (xf4 == null) {
								xf4 = 0f;
							}
							if (xf_pledge4 == null) {
								xf_pledge4 = 0f;
							}
							fl1 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee;// 返利
							people4.setBy_tkr_rebates(fee);
							people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee);
							people4.setService_fee((money4 - total_pledge4)
									* fee);// 服务费
							people4.setF_renewal((xf4 - xf_pledge4) * fee);// 服务续费
							jxDrawPeopleService.save(people4);

						}
					}
				}
			}
			if (level.equals("2")) {// 最上级为市
				System.out.println("---最上级为市---");
				// 得到区县级
				List<Map<String, Object>> tier = partnerService
						.findLevelOfUsername(username);// 区县级
				// 判断区县级是否为空
				if (tier.size() > 0) {// 区县级不为空 市----区县/产品经理
					for (int i = 0; i < tier.size(); i++) {
						Map<String, Object> m = tier.get(i);
						if (m.get("par_level").equals("3")) {// 市---区县/市---区县---产品经理
							JxDrawPeople people3 = new JxDrawPeople();
							people3.setAdd_time(new Date());
							people3.setTkr_id(username);
							people3.setBy_tkr_name(m.get("par_name") + "");
							people3.setBy_tkr_id(m.get("id") + "");
							people3.setTkr_state(0);
							people3.setWithdrawal_order(withdrawalOrderNo);
							int wall3 = jxOrderService.findWallNumber(
									m.get("id") + "", time, last_add_time);// 壁挂式台数
							int desktop3 = jxOrderService.findVerticalNumber(
									m.get("id") + "", time, last_add_time);// 台式
							int vertical3 = jxOrderService.findDesktopNumber(
									m.get("id") + "", time, last_add_time);// 立式
							// 续费台数
							int wall_renew3 = jxOrderService
									.findWallRenewNumber(m.get("id") + "",
											time, last_add_time);
							int desktop_renew3 = jxOrderService
									.findVerticalRenewNumber(m.get("id") + "",
											time, last_add_time);// desktop_renew
							int vertical_renew3 = jxOrderService
									.findDesktopRenewNumber(m.get("id") + "",
											time, last_add_time);// vertical_renew
							people3.setSell_wall(wall3);
							people3.setSell_vertical(vertical3);
							people3.setSell_desktop(desktop3);
							people3.setWall_renew(wall_renew3);
							people3.setVertical_renew(vertical_renew3);
							people3.setDesktop_renew(desktop_renew3);

							// 判断是否被分配下级
							JxRebateProportion jxProportion3 = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ m.get("id")
											+ "' and most_superior_id = '"
											+ username + "'");
							if (jxProportion3 != null) {
								fee = jxProportion3.getSuper_totall();
							}
							Float money3 = jxOrderService.findAllMoneyOfTime(
									m.get("id"), time, last_add_time);
							Float total_pledge3 = jxOrderService
									.findAllTotalPledgeOfLowerToLastTime(
											m.get("id"), time, last_add_time);
							if (money3 == null) {
								money3 = 0f;
							}
							if (total_pledge3 == null) {
								total_pledge3 = 0f;
							}

							Float xf3 = jxOrderService.findAllXfMoneyOfTime(
									m.get("id"), time, last_add_time);

							Float xf_pledge3 = jxOrderService
									.findAllXfPledgeMoneyOfTime(m.get("id"),
											time, last_add_time);
							if (xf3 == null) {
								xf3 = 0f;
							}
							if (xf_pledge3 == null) {
								xf_pledge3 = 0f;
							}

							fl1 += ((money3 - total_pledge3) + (xf3 - xf_pledge3))
									* fee;// 返利
							people3.setBy_tkr_rebates(fee);
							people3.setBy_tkr_total_money(((money3 - total_pledge3) + (xf3 - xf_pledge3))
									* fee);
							people3.setService_fee((money3 - total_pledge3)
									* fee);// 服务费
							people3.setF_renewal((xf3 - xf_pledge3) * fee);// 服务续费
							jxDrawPeopleService.save(people3);
							List<Map<String, Object>> tiercp = partnerService
									.findLevelOfUsername(m.get("id"));// 产品经理
							if (tiercp.size() > 0) {// 产品经理
								for (int j = 0; j < tiercp.size(); j++) {
									Map<String, Object> m1 = tiercp.get(j);
									JxDrawPeople people4 = new JxDrawPeople();
									people4.setAdd_time(new Date());
									people4.setTkr_id(username);
									people4.setBy_tkr_name(m1.get("par_name")
											+ "");
									people4.setBy_tkr_id(m1.get("id") + "");
									people4.setTkr_state(1);
									people4.setWithdrawal_order(withdrawalOrderNo);
									people4.setBy_super_name(m.get("par_name")
											+ "");
									people4.setBy_super_tkr_id(m.get("id") + "");
									int wall4 = jxOrderService.findWallNumber(
											m1.get("id") + "", time,
											last_add_time);// 壁挂式台数
									int desktop4 = jxOrderService
											.findVerticalNumber(m1.get("id")
													+ "", time, last_add_time);// 台式
									int vertical4 = jxOrderService
											.findDesktopNumber(m1.get("id")
													+ "", time, last_add_time);// 立式
									// 续费台数
									int wall_renew4 = jxOrderService
											.findWallRenewNumber(m1.get("id")
													+ "", time, last_add_time);
									int desktop_renew4 = jxOrderService
											.findVerticalRenewNumber(
													m1.get("id") + "", time,
													last_add_time);// desktop_renew
									int vertical_renew4 = jxOrderService
											.findDesktopRenewNumber(
													m1.get("id") + "", time,
													last_add_time);// vertical_renew
									people4.setSell_wall(wall4);
									people4.setSell_vertical(vertical4);
									people4.setSell_desktop(desktop4);
									people4.setWall_renew(wall_renew4);
									people4.setVertical_renew(vertical_renew4);
									people4.setDesktop_renew(desktop_renew4);

									JxRebateProportion jxProportion4 = jxRebateProportionService
											.findUnique("from jx_rebate_proportion where user_number = '"
													+ m1.get("id")
													+ "' and most_superior_id = '"
													+ username + "'");
									if (jxProportion4 != null) {
										fee = jxProportion4.getSuper_totall();
									}
									Float money4 = jxOrderService
											.findAllMoneyOfTime(m1.get("id"),
													time, last_add_time);
									Float total_pledge4 = jxOrderService
											.findAllTotalPledgeOfLowerToLastTime(
													m1.get("id"), time,
													last_add_time);
									if (money4 == null) {
										money4 = 0f;
									}
									if (total_pledge4 == null) {
										total_pledge4 = 0f;
									}

									Float xf4 = jxOrderService
											.findAllXfMoneyOfTime(m1.get("id"),
													time, last_add_time);

									Float xf_pledge4 = jxOrderService
											.findAllXfPledgeMoneyOfTime(
													m1.get("id"), time,
													last_add_time);
									if (xf4 == null) {
										xf4 = 0f;
									}
									if (xf_pledge4 == null) {
										xf_pledge4 = 0f;
									}

									fl2 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
											* fee;// 返利
									people4.setBy_tkr_rebates(fee);
									people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
											* fee);
									people4.setService_fee((money4 - total_pledge4)
											* fee);// 服务费
									people4.setF_renewal((xf4 - xf_pledge4)
											* fee);// 服务续费
									jxDrawPeopleService.save(people4);
								}
							}

						}
						if (m.get("par_level").equals("4")) {// 市---产品经理
							JxDrawPeople people4 = new JxDrawPeople();
							people4.setAdd_time(new Date());
							people4.setTkr_id(username);
							people4.setBy_tkr_name(m.get("par_name") + "");
							people4.setBy_tkr_id(m.get("id") + "");
							people4.setTkr_state(0);
							people4.setWithdrawal_order(withdrawalOrderNo);
							int wall = jxOrderService.findWallNumber(
									m.get("id") + "", time, last_add_time);// 壁挂式台数
							int desktop = jxOrderService.findVerticalNumber(
									m.get("id") + "", time, last_add_time);// 台式
							int vertical = jxOrderService.findDesktopNumber(
									m.get("id") + "", time, last_add_time);// 立式
							// 续费台数
							int wall_renew = jxOrderService
									.findWallRenewNumber(m.get("id") + "",
											time, last_add_time);
							int desktop_renew = jxOrderService
									.findVerticalRenewNumber(m.get("id") + "",
											time, last_add_time);// desktop_renew
							int vertical_renew = jxOrderService
									.findDesktopRenewNumber(m.get("id") + "",
											time, last_add_time);// vertical_renew
							people4.setSell_wall(wall);
							people4.setSell_vertical(vertical);
							people4.setSell_desktop(desktop);
							people4.setWall_renew(wall_renew);
							people4.setVertical_renew(vertical_renew);
							people4.setDesktop_renew(desktop_renew);

							JxRebates jxRebates = jxRebatesService
									.findUnique("from JxRebates where par_level = '"
											+ m.get("par_level") + "'");
							Float money4 = jxOrderService.findAllMoneyOfTime(
									m.get("id"), time, last_add_time);
							JxRebateProportion jxProportion4 = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ m.get("id")
											+ "' and most_superior_id = '"
											+ username + "'");
							if (jxProportion4 != null) {
								fee = jxProportion4.getSuper_totall();
							} else {
								fee = (IdentifyingUtil.proportion(level) - jxRebates
										.getF_install());
							}
							if (money4 == null) {
								money4 = 0f;
							}
							Float total_pledge4 = jxOrderService
									.findAllTotalPledgeOfLowerToLastTime(
											m.get("id"), time, last_add_time);
							if (total_pledge4 == null) {
								total_pledge4 = 0f;
							}

							Float xf4 = jxOrderService.findAllXfMoneyOfTime(
									m.get("id"), time, last_add_time);

							Float xf_pledge4 = jxOrderService
									.findAllXfPledgeMoneyOfTime(m.get("id"),
											time, last_add_time);
							if (xf4 == null) {
								xf4 = 0f;
							}
							if (xf_pledge4 == null) {
								xf_pledge4 = 0f;
							}

							fl1 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee;// 返利
							people4.setBy_tkr_rebates(fee);
							people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee);
							people4.setService_fee((money4 - total_pledge4)
									* fee);// 服务费
							people4.setF_renewal((xf4 - xf_pledge4) * fee);// 服务续费
							jxDrawPeopleService.save(people4);
						}
					}
				}

			}
			if (level.equals("3")) {// 最上级为区县
				List<Map<String, Object>> tier = partnerService
						.findLevelOfUsername(username);// 产品经理
				if (tier.size() > 0) {// 区县---产品经理
					for (int i = 0; i < tier.size(); i++) {
						Map<String, Object> m = tier.get(i);
						JxDrawPeople people4 = new JxDrawPeople();
						people4.setAdd_time(new Date());
						people4.setTkr_id(username);
						people4.setBy_tkr_name(m.get("par_name") + "");
						people4.setBy_tkr_id(m.get("id") + "");
						people4.setTkr_state(0);
						people4.setWithdrawal_order(withdrawalOrderNo);
						int wall = jxOrderService.findWallNumber(m.get("id")
								+ "", time, last_add_time);// 壁挂式台数
						int desktop = jxOrderService.findVerticalNumber(
								m.get("id") + "", time, last_add_time);// 台式
						int vertical = jxOrderService.findDesktopNumber(
								m.get("id") + "", time, last_add_time);// 立式
						// 续费台数
						int wall_renew = jxOrderService.findWallRenewNumber(
								m.get("id") + "", time, last_add_time);
						int desktop_renew = jxOrderService
								.findVerticalRenewNumber(m.get("id") + "",
										time, last_add_time);// desktop_renew
						int vertical_renew = jxOrderService
								.findDesktopRenewNumber(m.get("id") + "", time,
										last_add_time);// vertical_renew
						people4.setSell_wall(wall);
						people4.setSell_vertical(vertical);
						people4.setSell_desktop(desktop);
						people4.setWall_renew(wall_renew);
						people4.setVertical_renew(vertical_renew);
						people4.setDesktop_renew(desktop_renew);

						// 得到下级返利
						JxRebateProportion jxProportion4 = jxRebateProportionService
								.findUnique("from jx_rebate_proportion where user_number = '"
										+ m.get("id")
										+ "' and most_superior_id = '"
										+ username + "'");
						if (jxProportion4 != null) {
							fee = jxProportion4.getSuper_totall();
						}
						Float money4 = jxOrderService.findAllMoneyOfTime(
								m.get("id"), time, last_add_time);
						if (money4 == null) {
							money4 = 0f;
						}
						Float total_pledge4 = jxOrderService
								.findAllTotalPledgeOfLowerToLastTime(
										m.get("id"), time, last_add_time);
						if (total_pledge4 == null) {
							total_pledge4 = 0f;
						}
						Float xf4 = jxOrderService.findAllXfMoneyOfTime(
								m.get("id"), time, last_add_time);

						Float xf_pledge4 = jxOrderService
								.findAllXfPledgeMoneyOfTime(m.get("id"), time,
										last_add_time);
						if (xf4 == null) {
							xf4 = 0f;
						}
						if (xf_pledge4 == null) {
							xf_pledge4 = 0f;
						}

						fl1 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
								* fee;// 返利
						people4.setBy_tkr_rebates(fee);
						people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
								* fee);
						people4.setService_fee((money4 - total_pledge4) * fee);// 服务费
						people4.setF_renewal((xf4 - xf_pledge4) * fee);// 服务续费
						jxDrawPeopleService.save(people4);
					}
				}
			}
		} else {
			// 有上级
			System.out.println("---有上级---");
			List<Map<String, Object>> tier = partnerService
					.findLevelOfUsername(username);// 区县级、市
			if (tier.size() > 0) {// 区县级不为空 市----区县/产品经理
				for (int i = 0; i < tier.size(); i++) {
					Map<String, Object> m = tier.get(i);
					JxDrawPeople people3 = new JxDrawPeople();
					people3.setAdd_time(new Date());
					people3.setTkr_id(username);
					people3.setBy_tkr_name(m.get("par_name") + "");
					people3.setBy_tkr_id(m.get("id") + "");
					people3.setTkr_state(0);
					people3.setWithdrawal_order(withdrawalOrderNo);
					int wall3 = jxOrderService.findWallNumber(m.get("id") + "",
							time, last_add_time);// 壁挂式台数
					int desktop3 = jxOrderService.findVerticalNumber(
							m.get("id") + "", time, last_add_time);// 台式
					int vertical3 = jxOrderService.findDesktopNumber(
							m.get("id") + "", time, last_add_time);// 立式
					// 续费台数
					int wall_renew3 = jxOrderService.findWallRenewNumber(
							m.get("id") + "", time, last_add_time);
					int desktop_renew3 = jxOrderService
							.findVerticalRenewNumber(m.get("id") + "", time,
									last_add_time);// desktop_renew
					int vertical_renew3 = jxOrderService
							.findDesktopRenewNumber(m.get("id") + "", time,
									last_add_time);// vertical_renew
					people3.setSell_wall(wall3);
					people3.setSell_vertical(vertical3);
					people3.setSell_desktop(desktop3);
					people3.setWall_renew(wall_renew3);
					people3.setVertical_renew(vertical_renew3);
					people3.setDesktop_renew(desktop_renew3);
					
					JxRebateProportion jxProportion3 = jxRebateProportionService
							.findUnique("from jx_rebate_proportion where user_number = '"
									+ m.get("id")
									+ "' and most_superior_id = '"
									+ username + "'");
					if (jxProportion3 != null) {
						fee = jxProportion3
								.getSuper_totall();
					} else {
						fee = jxRebates2.getService_fee();
					}

					Float money3 = jxOrderService.findAllMoneyOfTime(
							m.get("id"), time, last_add_time);
					Float total_pledge3 = jxOrderService
							.findAllTotalPledgeOfLowerToLastTime(m.get("id"),
									time, last_add_time);

					if (money3 == null) {
						money3 = 0f;
					}
					if (total_pledge3 == null) {
						total_pledge3 = 0f;
					}
					Float xf3 = jxOrderService.findAllXfMoneyOfTime(
							m.get("id"), time, last_add_time);

					Float xf_pledge3 = jxOrderService
							.findAllXfPledgeMoneyOfTime(m.get("id"), time,
									last_add_time);
					if (xf3 == null) {
						xf3 = 0f;
					}
					if (xf_pledge3 == null) {
						xf_pledge3 = 0f;
					}

					fl1 += ((money3 - total_pledge3) + (xf3 - xf_pledge3))
							* fee;// 返利
					people3.setBy_tkr_rebates(fee);
					people3.setBy_tkr_total_money(((money3 - total_pledge3) + (xf3 - xf_pledge3))
							* fee);
					people3.setService_fee((money3 - total_pledge3) * fee);// 服务费
					people3.setF_renewal((xf3 - xf_pledge3) * fee);// 服务续费
					jxDrawPeopleService.save(people3);
					List<Map<String, Object>> tiercp = partnerService
							.findLevelOfUsername(m.get("id"));// 产品经理
					if (tiercp.size() > 0) {// 产品经理
						for (int j = 0; j < tiercp.size(); j++) {
							Map<String, Object> m1 = tiercp.get(j);
							JxDrawPeople people4 = new JxDrawPeople();
							people4.setAdd_time(new Date());
							people4.setTkr_id(username);
							people4.setBy_tkr_name(m1.get("par_name") + "");
							people4.setBy_tkr_id(m1.get("id") + "");
							people4.setBy_super_tkr_id(m.get("id") + "");
							people4.setBy_super_name(m.get("par_name") + "");
							people4.setTkr_state(1);
							people4.setWithdrawal_order(withdrawalOrderNo);
							int wall4 = jxOrderService.findWallNumber(
									m1.get("id") + "", time, last_add_time);// 壁挂式台数
							int desktop4 = jxOrderService.findVerticalNumber(
									m1.get("id") + "", time, last_add_time);// 台式
							int vertical4 = jxOrderService.findDesktopNumber(
									m1.get("id") + "", time, last_add_time);// 立式
							// 续费台数
							int wall_renew4 = jxOrderService
									.findWallRenewNumber(m1.get("id") + "",
											time, last_add_time);
							int desktop_renew4 = jxOrderService
									.findVerticalRenewNumber(m1.get("id") + "",
											time, last_add_time);// desktop_renew
							int vertical_renew4 = jxOrderService
									.findDesktopRenewNumber(m1.get("id") + "",
											time, last_add_time);// vertical_renew
							people4.setSell_wall(wall4);
							people4.setSell_vertical(vertical4);
							people4.setSell_desktop(desktop4);
							people4.setWall_renew(wall_renew4);
							people4.setVertical_renew(vertical_renew4);
							people4.setDesktop_renew(desktop_renew4);

							Float money4 = jxOrderService.findAllMoneyOfTime(
									m1.get("id"), time, last_add_time);
							Float total_pledge4 = jxOrderService
									.findAllTotalPledgeOfLowerToLastTime(
											m1.get("id"), time, last_add_time);

							if (money4 == null) {
								money4 = 0f;
							}
							if (total_pledge4 == null) {
								total_pledge4 = 0f;
							}
							Float xf4 = jxOrderService.findAllXfMoneyOfTime(
									m1.get("id"), time, last_add_time);

							Float xf_pledge4 = jxOrderService
									.findAllXfPledgeMoneyOfTime(m1.get("id"),
											time, last_add_time);
							if (xf4 == null) {
								xf4 = 0f;
							}
							if (xf_pledge4 == null) {
								xf_pledge4 = 0f;
							}

							fl2 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee;// 返利
							people4.setBy_tkr_rebates(fee);
							people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
									* fee);
							people4.setService_fee((money4 - total_pledge4)
									* fee);// 服务费
							people4.setF_renewal((xf4 - xf_pledge4) * fee);// 服务续费
							jxDrawPeopleService.save(people4);
						}
					}
				}
			}
		}

		
		Float zxj = fl1 + fl2 + fl3;
		String xj = decimalFormat.format(zxj);
		Float lower = Float.valueOf(xj);
		System.out.println("下级返利:" + xj);
		tatol_fwf = tatol_fwf + lower;
		System.out.println("总服务费:" + tatol_fwf);
		String p = decimalFormat.format(tatol_fwf);
		Float sum = Float.valueOf(p);

		int wall = jxOrderService
				.findWallNumbers(username, time, last_add_time);// 壁挂式台数
		int desktop = jxOrderService.findVerticalNumbers(username, time,
				last_add_time);// 台式
		int vertical = jxOrderService.findDesktopNumbers(username, time,
				last_add_time);// 立式
		// 续费台数
		int wall_renew = jxOrderService.findWallRenewNumbers(username, time,
				last_add_time);
		int desktop_renew = jxOrderService.findVerticalRenewNumbers(username,
				time, last_add_time);// desktop_renew
		int vertical_renew = jxOrderService.findDesktopRenewNumbers(username,
				time, last_add_time);// vertical_renew

		if (partnerRebate == null) {
			System.out.println("1");
			jxPartnerRebate.setUser_name(username);
			jxPartnerRebate.setTotal_amount(sum);
			jxPartnerRebate.setWithdrawal_order(withdrawalOrderNo);// 提现单号
			jxPartnerRebate.setService_fee(fw);// 服务费返利
			jxPartnerRebate.setF_renewal(xf);// 续费返利
			jxPartnerRebate.setLower_rebate(lower);// 返利补贴
			jxPartnerRebate.setF_installation(installation_whf);// 安装费补贴
			jxPartnerRebate.setSell_wall(wall);
			jxPartnerRebate.setSell_vertical(vertical);
			jxPartnerRebate.setSell_desktop(desktop);
			jxPartnerRebate.setWall_renew(wall_renew);
			jxPartnerRebate.setVertical_renew(vertical_renew);
			jxPartnerRebate.setDesktop_renew(desktop_renew);
			jxPartnerRebate.setAdd_time(new Date());
		} else {
			System.out.println("2");
			partnerRebate.setUser_name(username);
			partnerRebate.setTotal_amount(sum);
			partnerRebate.setWithdrawal_order(withdrawalOrderNo);// 提现单号
			partnerRebate.setService_fee(fw);// 服务费返利
			partnerRebate.setF_renewal(xf);// 续费返利
			partnerRebate.setLower_rebate(lower);// 返利补贴
			partnerRebate.setF_installation(installation_whf);// 安装费补贴
			partnerRebate.setSell_wall(wall);
			partnerRebate.setSell_vertical(vertical);
			partnerRebate.setSell_desktop(desktop);
			partnerRebate.setWall_renew(wall_renew);
			partnerRebate.setVertical_renew(vertical_renew);
			partnerRebate.setDesktop_renew(desktop_renew);
			partnerRebate.setAdd_time(new Date());
			partnerRebate.setMod_time(new Date());
		}
		return sum;
	}*/
	
	
	/**
	 * 提现优化 最终版2018/04/16
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/withdrawalamounts")
	public Result withdrawalAmounts(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String withdrawalOrderNo = "TX" + RandomUtil.getRandom();
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
					JxPartner jxPartner = null;
					if (username.contains("A") || username.contains("B")
							|| username.contains("C")) {
						jxPartner = partnerService
								.findUnique("from JxPartner where par_other = '"
										+ username + "'");
					} else {
						jxPartner = partnerService
								.findUnique("from JxPartner where id = '"
										+ username + "'");
					}

					// 判断是否拥有提现权限
					if (jxPartner.getIspermissions() == 1) {
						// 判断是否绑定了支付宝账号
						JxAlipayAccount alipayAccount = jxAlipayAccountService
								.findUnique("from JxAlipayAccount where p_number = '"
										+ username + "'");
						if (alipayAccount != null) {
							List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
							Map<String, Object> map1 = new HashMap<String, Object>();
							Date t = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							String time = sdf.format(t);
							jxPartner.getPar_pact();
							jxPartner.getPar_shop();
							String level = jxPartner.getPAR_LEVEL();
							if (username.equals("34152300015")
									|| username.equals("45000000009")) {
								level = "4";
							}
							Float Maintenance = 0f;
							Float fee = 0f;
							Float install = 0f;
							// 根据级别查出对应的返利参数
							level = "1";
							JxRebates jxRebates = jxRebatesService
									.findUnique("from JxRebates where par_level = '"
											+ level + "'");
							if (jxRebates == null) {
								return new Result(Errors.NO_PARTNER);
							}
							// 上级是否分配比例
							JxRebateProportion proportion = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ username + "'");
							System.out.println("---逻辑部分---");
							// 得到上级
							JxPartner partner = partnerService
									.findUnique("from JxPartner where id = '"
											+ username + "'");
							// 判断是否按照合同
							System.out.println("---逻辑代码块---");

							JxPartnerRebate partnerRebate = jxPartnerRebateService
									.findUnique("from jx_partner_rebate where user_name = '"
											+ username + "' and w_state = 0");
							JxPartnerRebate jxPartnerRebate = null;
							if (partnerRebate == null) {
								jxPartnerRebate = new JxPartnerRebate();
							}

							if (jxPartner.getPar_pact() == 1) {// 按照合同
								System.out.println("级别:" + level);
								fee = IdentifyingUtil.proportion(level);
								System.out.println("自身返利费:" + fee);
								Maintenance = jxRebates.getRwl_install();// 维护费
								String parent_id = partner.getParParentid();
								if (parent_id != null
										&& parent_id.length() != 0) {// 有上级
									if (proportion == null) {// 没有被修改过比例
										fee = jxRebates.getF_install()
												+ jxRebates.getService_fee();
										Maintenance = jxRebates
												.getRwl_install();// 维护费
										fee = jxRebates.getF_install();
									} else {
										fee = proportion.getRp_rebates()
												+ proportion.getRp_installed();
										System.out.println("修改的服务费:" + fee);
										Maintenance = jxRebates
												.getRwl_install();// 维护费
									}
								}else{
									fee = jxRebates.getF_install();
								}
								Float tatol_rebate = rebates(fee, Maintenance,
										install, username, time,
										withdrawalOrderNo, jxPartnerRebate,
										partnerRebate, level, proportion,
										jxRebates, partner);
								map1.put("withdrawal_total_amount",
										tatol_rebate);
								map1.put("withdrawalOrderNo", withdrawalOrderNo);
								list.add(map1);
							} 
							if (partnerRebate == null) {
								jxPartnerRebate.setWdl_fee(fee);
								jxPartnerRebate.setRwl_install(Maintenance);
								jxPartnerRebate.setPar_pact(jxPartner.getPar_pact());
								jxPartnerRebateService.save(jxPartnerRebate);
							} else {
								partnerRebate.setWdl_fee(fee);
								partnerRebate.setRwl_install(Maintenance);
								partnerRebate.setPar_pact(jxPartner.getPar_pact());
								jxPartnerRebateService.save(partnerRebate);
							}

							System.out.println("---逻辑部分---");
							return new SecretResult(Errors.OK, list);
						} else {
							return new Result(Errors.THERE_IS_NO_BINDING_ALIPAY);
						}
					} else {
						return new Result(Errors.NO_PERMISSIONS);
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

	//我的余额
	private Float rebates(Float fee, Float maintenance, Float install,
			String username, String time, String withdrawalOrderNo,
			JxPartnerRebate jxPartnerRebate, JxPartnerRebate partnerRebate,
			String level, JxRebateProportion proportion, JxRebates jxRebates2,
			JxPartner partner) {
			fee = fee + maintenance;
		// 取出最后一次提现的时间
				List<Map<String, Object>> mm = jxPartnerRebateService
						.findLastAddtime(username);
				String last_add_time = null;
				if (mm.size() <= 0) {
					last_add_time = "1970-01-01 00:00:00";
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Map<String, Object> ma = mm.get(0);
					Date s = (Date) ma.get("add_time");
					last_add_time = sdf.format(s);
				}
				DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
				// 安装费
				Float money1 = jxOrderService.findAllMoneyOfTimeF(username, time,
						last_add_time);
				if (money1 == null) {
					money1 = 0f;
				}
				System.out.println("money1:" + money1);
				// 安装费押金
				Float Service_charge = jxOrderService
						.findAllTotalPledgeOfLowerToLastTimeY(username, time,
								last_add_time);
				if (Service_charge == null) {
					Service_charge = 0f;
				}
				System.out.println("Service_charge:" + Service_charge);
				//安装费总额去押金
				Float fwf = money1 - Service_charge;// 总服务费金额(安装费总额去押金)
				System.out.println("fwf:" + fwf);
				// 续费返利
				Float renewal1 = jxOrderService.findAllXfMoneyOfTimeX(username, time,
						last_add_time);
				if (renewal1 == null) {
					renewal1 = 0f;
				}
				// 续费押金
				Float Renew_the_deposit = jxOrderService.findAllXfPledgeMoneyOfTimeXY(
						username, time, last_add_time);
				if (Renew_the_deposit == null) {
					Renew_the_deposit = 0f;
				}
				//续费总金额去押金
				Float renewal = renewal1 - Renew_the_deposit;// 续费总金额
				System.out.println("renewal:" + renewal);
				
				Float f = (fwf + renewal) * fee;// 总服务费
				Float tatol_fwf = 0f;// 总费用

				Float fw = fwf * fee;
				Float xf = renewal * fee;
				tatol_fwf = fw + xf;
				String fw1 = decimalFormat.format(fw);
				fw = Float.valueOf(fw1);
				
				String xf1 = decimalFormat.format(xf);
				xf = Float.valueOf(xf1);
				System.out.println("服务费:" + fw);
				System.out.println("服务续费:" + xf);
				System.out.println("fwf:" + fwf);
				System.out.println("renewal:" + renewal);
				
				// 下级返利
				Float fl1 = 0f;
				Float fl2 = 0f;
				Float fl3 = 0f;
				fee = jxRebates2.getService_fee();
				
				//查询用户是否拥有下级
				List<Map<String, Object>> tier = partnerService.findLevelOfUsername(username);
				if(tier.size() > 0){
					for (int i = 0; i < tier.size(); i++) {
						Map<String, Object> m = tier.get(i);
						JxDrawPeople people4 = new JxDrawPeople();
						people4.setAdd_time(new Date());
						people4.setTkr_id(username);
						people4.setBy_tkr_name(m.get("par_name") + "");
						people4.setBy_tkr_id(m.get("id") + "");
						people4.setTkr_state(0);
						people4.setWithdrawal_order(withdrawalOrderNo);
						int wall = jxOrderService.findWallNumber(m.get("id")
								+ "", time, last_add_time);// 壁挂式台数
						int desktop = jxOrderService.findVerticalNumber(
								m.get("id") + "", time, last_add_time);// 台式
						int vertical = jxOrderService.findDesktopNumber(
								m.get("id") + "", time, last_add_time);// 立式
						// 续费台数
						int wall_renew = jxOrderService.findWallRenewNumber(
								m.get("id") + "", time, last_add_time);
						int desktop_renew = jxOrderService
								.findVerticalRenewNumber(m.get("id") + "",
										time, last_add_time);// desktop_renew
						int vertical_renew = jxOrderService
								.findDesktopRenewNumber(m.get("id") + "", time,
										last_add_time);// vertical_renew
						people4.setSell_wall(wall);
						people4.setSell_vertical(vertical);
						people4.setSell_desktop(desktop);
						people4.setWall_renew(wall_renew);
						people4.setVertical_renew(vertical_renew);
						people4.setDesktop_renew(desktop_renew);

						// 得到下级返利
						JxRebateProportion jxProportion4 = jxRebateProportionService
								.findUnique("from jx_rebate_proportion where user_number = '"
										+ m.get("id")
										+ "' and most_superior_id = '"
										+ username + "'");
						if (jxProportion4 != null) {
							fee = jxProportion4.getSuper_totall();
							fee = IdentifyingUtil.proportion(level) - jxProportion4.getRp_total();
							fee = jxRebates2.getLower_rebate();
						}else{
							fee = jxRebates2.getLower_rebate();
							//fee = 0.05f;
						}
						//总安装费用
						Float money4 = jxOrderService.findAllMoneyOfTime(
								m.get("id"), time, last_add_time);
						if (money4 == null) {
							money4 = 0f;
						}
						//总安装押金费用
						Float total_pledge4 = jxOrderService
								.findAllTotalPledgeOfLowerToLastTime(
										m.get("id"), time, last_add_time);
						if (total_pledge4 == null) {
							total_pledge4 = 0f;
						}
						//总续费费用
						Float xf4 = jxOrderService.findAllXfMoneyOfTime(
								m.get("id"), time, last_add_time);
						
						//总续费押金费用
						Float xf_pledge4 = jxOrderService
								.findAllXfPledgeMoneyOfTime(m.get("id"), time,
										last_add_time);
						if (xf4 == null) {
							xf4 = 0f;
						}
						if (xf_pledge4 == null) {
							xf_pledge4 = 0f;
						}
						people4.setTotal_money((money4 - total_pledge4) + (xf4 - xf_pledge4));
						fl1 += ((money4 - total_pledge4) + (xf4 - xf_pledge4))
								* fee;// 返利
						people4.setBy_tkr_rebates(fee);
						people4.setBy_tkr_total_money(((money4 - total_pledge4) + (xf4 - xf_pledge4))
								* fee);
						people4.setService_fee((money4 - total_pledge4) * fee);// 服务费
						people4.setF_renewal((xf4 - xf_pledge4) * fee);// 服务续费
						//jxDrawPeopleService.save(people4);
					}
				}

				
				Float zxj = fl1 + fl2 + fl3;
				String xj = decimalFormat.format(zxj);
				Float lower = Float.valueOf(xj);
				System.out.println("下级返利:" + xj);
				tatol_fwf = tatol_fwf + lower;
				System.out.println("总服务费:" + tatol_fwf);
				String p = decimalFormat.format(tatol_fwf);
				Float sum = Float.valueOf(p);
				return sum;
	}
	
	
	/**
	 * 提现优化 最终版2018/04/22
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/withdrawalamountnew")
	public Result withdrawalAmountNew(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			//String safetyMark = "KdlSDvrNOVrgPGnQ5VXDJy96u8CXRr9uqesRwb8W";
			String withdrawalOrderNo = "TX" + RandomUtil.getRandom();
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
					JxPartner jxPartner = null;
					if (username.contains("A") || username.contains("B")
							|| username.contains("C")) {
						jxPartner = partnerService
								.findUnique("from JxPartner where par_other = '"
										+ username + "'");
					} else {
						jxPartner = partnerService
								.findUnique("from JxPartner where id = '"
										+ username + "'");
					}

					// 判断是否拥有提现权限
					if (jxPartner.getIspermissions() == 1) {
						// 判断是否绑定了支付宝账号
						JxAlipayAccount alipayAccount = jxAlipayAccountService
								.findUnique("from JxAlipayAccount where p_number = '"
										+ username + "'");
						if (alipayAccount != null) {
							List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
							Map<String, Object> map1 = new HashMap<String, Object>();
							Date t = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							String time = sdf.format(t);
							jxPartner.getPar_pact();
							jxPartner.getPar_shop();
							String level = jxPartner.getPAR_LEVEL();
							if (username.equals("34152300015")
									|| username.equals("45000000009")) {
								level = "4";
							}
							Float Maintenance = 0f;
							Float fee = 0f;
							Float install = 0f;
							// 根据级别查出对应的返利参数
							level = "1";
							JxRebates jxRebates = jxRebatesService
									.findUnique("from JxRebates where par_level = '"
											+ level + "'");
							if (jxRebates == null) {
								return new Result(Errors.NO_PARTNER);
							}
							// 上级是否分配比例
							JxRebateProportion proportion = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ username + "'");
							System.out.println("---逻辑部分---");
							// 得到上级
							JxPartner partner = partnerService
									.findUnique("from JxPartner where id = '"
											+ username + "'");
							// 判断是否按照合同
							System.out.println("---逻辑代码块---");
							//是否存在没有体现的订单
							JxPartnerRebate partnerRebate = jxPartnerRebateService
									.findUnique("from jx_partner_rebate where user_name = '"
											+ username + "' and w_state = 0");
							JxPartnerRebate jxPartnerRebate = null;
							if (partnerRebate == null) {
								jxPartnerRebate = new JxPartnerRebate();
							}

							if (jxPartner.getPar_pact() == 1) {// 按照合同
								//System.out.println("级别:" + level);
								fee = IdentifyingUtil.proportion(level);
								//System.out.println("自身返利费:" + fee);
								Maintenance = jxRebates.getService_fee();// 推广费
								String parent_id = partner.getParParentid();
								if (parent_id != null
										&& parent_id.length() != 0) {// 有上级
									if (proportion == null) {// 没有被修改过比例
										fee = jxRebates.getF_install();//直接赠机费
									} else {
										fee = proportion.getRp_rebates()
												+ proportion.getRp_installed();
										System.out.println("修改的服务费:" + fee);
										Maintenance = jxRebates
												.getRwl_install();// 维护费
									}
								}else{
									fee = jxRebates.getF_install();
								}
								Maintenance = jxRebates.getService_fee();// 推广费
								fee = jxRebates.getF_install();
								//总是收入
								Float indirectBenefits = jxRebates.getLower_rebate();//间接收益百分比
								Float totalRevenue = totalRevenues(username,fee,indirectBenefits,Maintenance);//我的总收入
								System.out.println("我的总收入:"+totalRevenue);
								//可提现额度
								Float myWithdrawalLimit = withdrawalLimits(username,fee,indirectBenefits,partnerRebate,jxPartnerRebate,withdrawalOrderNo,Maintenance);
								//我的余额
								Float my_balance = rebates(fee, Maintenance,
										install, username, time,
										withdrawalOrderNo, jxPartnerRebate,
										partnerRebate, level, proportion,
										jxRebates, partner);
								map1.put("my_balance",my_balance);//我的余额
								map1.put("withdrawalOrderNo", withdrawalOrderNo);//提现单号
								map1.put("myTotalIncome", totalRevenue);//我的总收入
								map1.put("myWithdrawalLimit", myWithdrawalLimit);//可提现额度
								list.add(map1);
							}
							if (partnerRebate == null) {
								jxPartnerRebate.setWdl_fee(fee);
								jxPartnerRebate.setRwl_install(Maintenance);
								jxPartnerRebate.setPar_pact(jxPartner.getPar_pact());
								jxPartnerRebateService.save(jxPartnerRebate);
							} else {
								partnerRebate.setWdl_fee(fee);
								partnerRebate.setRwl_install(Maintenance);
								partnerRebate.setPar_pact(jxPartner.getPar_pact());
								jxPartnerRebateService.save(partnerRebate);
							}

							System.out.println("---逻辑部分---");
							return new SecretResult(Errors.OK, list);
						} else {
							return new Result(Errors.THERE_IS_NO_BINDING_ALIPAY);
						}
					} else {
						return new Result(Errors.NO_PERMISSIONS);
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

	//可提现额度（上月及以前未提取的部分）
	private Float withdrawalLimits(String username, Float fee, Float indirectBenefits,JxPartnerRebate partnerRebate, JxPartnerRebate jxPartnerRebate, String withdrawalOrderNo, Float maintenance) {
		fee = fee + maintenance;
		System.out.println("直接赠机和推广费:"+fee);
		System.out.println("下级返利:"+indirectBenefits);
		// 取出最后一次提现的时间
		List<Map<String, Object>> mm = jxPartnerRebateService.findLastAddtime(username);
		String last_add_time = null;
		if (mm.size() <= 0) {
			last_add_time = "1970-01-01 00:00:00";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> ma = mm.get(0);
			Date s = (Date) ma.get("add_time");
			last_add_time = sdf.format(s);
		}
		DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		Float f1 = 0f;
		//得到日期
		String month = sdf1.format(date);
		month = month.substring(5);
		System.out.println(month);
		int yue = Integer.parseInt(month);
		//得到天数
		String d = sdf.format(date);
		d = d.substring(8);
		int s = Integer.parseInt(d);
		String  time = null;
		//时间大于15号
		if(s > 15){
			//时间大于15号上个月及以前未提现的部分
			c.setTime(new Date());
			if(yue == 3){
				c.set(Calendar.DAY_OF_MONTH, 28);
			}else{
				c.set(Calendar.DAY_OF_MONTH, 30);
			}
			c.add(Calendar.MONTH, -1);
			Date m = c.getTime();
			time = format.format(m);
			
		}else{
			//小于15号，上上个月以前
			c.setTime(new Date());
			if(yue == 4){
				c.set(Calendar.DAY_OF_MONTH, 28);
			}else{
				c.set(Calendar.DAY_OF_MONTH, 30);
			}
			c.add(Calendar.MONTH, -2);
			Date m = c.getTime();
			time = format.format(m);
			System.out.println(time);
		}
		//服务费总额
		Float txfwf = jxOrderService.selectTxFwf(username,time,last_add_time);
		if(txfwf == null){
			txfwf = 0f;
		}
		//服务费押金
		Float txfwfyajin = jxOrderService.selectTxFwfYaJin(username,time,last_add_time);
		if(txfwfyajin == null){
			txfwfyajin = 0f;
		}
		//提现续费
		Float txxufei = jxOrderService.selectTxXuFei(username,time,last_add_time);
		if(txxufei == null){
			txxufei = 0f;
		}
		//提现续费押金
		Float txxufeiyajin = jxOrderService.selectTxXuFeiYaJin(username,time,last_add_time);
		if(txxufeiyajin == null){
			txxufeiyajin = 0f;
		}
	/*	//提现总额
		Float txzong = jxOrderService.selectTxZong(username,time);
		//提现押金
		Float txYaJin = jxOrderService.selecttxYaJin(username,time);
		if(txzong == null){
			txzong =0f;
		}
		if(txYaJin == null ){
			txYaJin =0f;
		}*/
		String s1 = decimalFormat.format(fee);
		fee = Float.valueOf(s1);
		
		/*String xf1 = decimalFormat.format(xf);
		xf = Float.valueOf(xf1);*/
		System.out.println("fee:"+fee);
		Float txjine = ((txfwf - txfwfyajin)+(txxufei - txxufeiyajin)) * fee;
		System.out.println("提现服务费:"+txfwf);
		System.out.println("提现服务费押金:"+txfwfyajin);
		System.out.println("提现续费金额:"+txxufei);
		System.out.println("提现续费押金:"+txxufeiyajin);
		System.out.println("提现手续费:"+fee);
		System.out.println("提现总金额:"+txjine);
		//间接收益额度
		List<Map<String,Object>> list = partnerService.findLevelOfUsername(username);
		for(int i = 0;i<list.size();i++){
			System.out.println("---拥有下级---");
			Map<String, Object> m2 = list.get(i);
			Long low_name = (Long) m2.get("id");
			JxDrawPeople people4 = new JxDrawPeople();
			people4.setAdd_time(new Date());
			people4.setTkr_id(username);
			people4.setBy_tkr_name(m2.get("par_name") + "");
			people4.setBy_tkr_id(m2.get("id") + "");
			people4.setTkr_state(0);
			people4.setWithdrawal_order(withdrawalOrderNo);
			int wall = jxOrderService.findWallNumberNew(low_name, time,last_add_time);// 壁挂式台数
			int desktop = jxOrderService.findVerticalNumberNew(low_name, time,last_add_time);// 台式
			int vertical = jxOrderService.findDesktopNumberNew(low_name, time,last_add_time);// 立式
			// 续费台数
			int wall_renew = jxOrderService.findWallRenewNumberNew(low_name, time,last_add_time);
			int desktop_renew = jxOrderService.findVerticalRenewNumberNew(low_name,time,last_add_time);// desktop_renew
			int vertical_renew = jxOrderService.findDesktopRenewNumberNew(low_name, time,last_add_time);// vertical_renew
			people4.setSell_wall(wall);
			people4.setSell_vertical(vertical);
			people4.setSell_desktop(desktop);
			people4.setWall_renew(wall_renew);
			people4.setVertical_renew(vertical_renew);
			people4.setDesktop_renew(desktop_renew);
			// 得到下级返利
			JxRebateProportion jxProportion4 = jxRebateProportionService
					.findUnique("from jx_rebate_proportion where user_number = '"
							+ m2.get("id")
							+ "' and most_superior_id = '"
							+ username + "'");
			/*if (jxProportion4 != null) {
				fee = jxProportion4.getSuper_totall();
				fee = IdentifyingUtil.proportion(level) - jxProportion4.getRp_total();
				indirectBenefits = indirectBenefits;
			}else{
				fee = jxRebates2.getService_fee();
				fee = 0.05f;
			}*/
			/*Float txjianjiezong = jxOrderService.selectTxZong(low_name,time);
			if(txjianjiezong == null){
				txjianjiezong = 0f;
			}
			Float txjianjieyajin = jxOrderService.selecttxYaJin(low_name,time);
			if(txjianjieyajin == null){
				txjianjieyajin = 0f;
			}*/
			//服务费总额
			Float xjtxfwf = jxOrderService.selectTxFwf(low_name,time,last_add_time);
			if(xjtxfwf == null){
				xjtxfwf = 0f;
			}
			//服务费押金
			Float xjtxfwfyajin = jxOrderService.selectTxFwfYaJin(low_name,time,last_add_time);
			if(xjtxfwfyajin == null){
				xjtxfwfyajin = 0f;
			}
			//提现续费
			Float xjtxxufei = jxOrderService.selectTxXuFei(low_name,time,last_add_time);
			if(xjtxxufei == null){
				xjtxxufei = 0f;
			}
			//提现续费押金
			Float xjtxxufeiyajin = jxOrderService.selectTxXuFeiYaJin(low_name,time,last_add_time);
			if(xjtxxufeiyajin == null){
				xjtxxufeiyajin = 0f;
			}
			people4.setTotal_money((xjtxfwf - xjtxfwfyajin )+ (xjtxxufei - xjtxxufeiyajin));
			f1 += ((xjtxfwf - xjtxfwfyajin )+ (xjtxxufei - xjtxxufeiyajin)) * indirectBenefits;
			people4.setBy_tkr_rebates(indirectBenefits);
			people4.setBy_tkr_total_money((xjtxfwf - xjtxfwfyajin)+ (xjtxxufei - xjtxxufeiyajin) * indirectBenefits);
			people4.setService_fee((xjtxfwf - xjtxfwfyajin) * indirectBenefits);// 服务费
			people4.setF_renewal((xjtxxufei - xjtxxufeiyajin) * indirectBenefits);// 服务续费
			System.out.println("下级服务费:"+xjtxfwf);
			System.out.println("下级服务费押金:"+xjtxfwfyajin);
			System.out.println("下级续费:"+xjtxxufei);
			System.out.println("下级续费押金:"+xjtxxufeiyajin);
			System.out.println("下级手续费:"+indirectBenefits);
			System.out.println("下级总金额:"+f1);
			jxDrawPeopleService.save(people4);
		}
		System.out.println("sum:"+txjine);
		System.out.println("f1:"+f1);
		Float sum = txjine + f1;
		System.out.println("可提现额度:"+sum);
		int wall = jxOrderService.findWallNumberNew(username, time,last_add_time);// 壁挂式台数
		int desktop = jxOrderService.findVerticalNumberNew(username, time,last_add_time);// 台式
		int vertical = jxOrderService.findDesktopNumberNew(username, time,last_add_time);// 立式
		// 续费台数
		int wall_renew = jxOrderService.findWallRenewNumberNew(username, time,last_add_time);
		int desktop_renew = jxOrderService.findVerticalRenewNumberNew(username,time,last_add_time);// desktop_renew
		int vertical_renew = jxOrderService.findDesktopRenewNumberNew(username, time,last_add_time);// vertical_renew
		if (partnerRebate == null) {
			System.out.println("1");
			jxPartnerRebate.setUser_name(username);
			jxPartnerRebate.setTotal_amount(sum);
			jxPartnerRebate.setWithdrawal_order(withdrawalOrderNo);// 提现单号
			jxPartnerRebate.setService_fee((txfwf - txfwfyajin)*fee);// 服务费返利
			jxPartnerRebate.setF_renewal((txxufei - txxufeiyajin)*fee);// 续费返利
			jxPartnerRebate.setLower_rebate(f1);// 间接收益
			jxPartnerRebate.setSell_wall(wall);
			jxPartnerRebate.setSell_vertical(vertical);
			jxPartnerRebate.setSell_desktop(desktop);
			jxPartnerRebate.setWall_renew(wall_renew);
			jxPartnerRebate.setVertical_renew(vertical_renew);
			jxPartnerRebate.setDesktop_renew(desktop_renew);
			jxPartnerRebate.setBuy_combined(txfwf - txfwfyajin);
			jxPartnerRebate.setRenewal_combined(txxufei - txxufeiyajin);
			jxPartnerRebate.setAdd_time(new Date());
		} else {
			System.out.println("2");
			partnerRebate.setUser_name(username);
			partnerRebate.setTotal_amount(sum);
			partnerRebate.setWithdrawal_order(withdrawalOrderNo);// 提现单号
			partnerRebate.setService_fee((txfwf - txfwfyajin)*fee);// 服务费返利
			partnerRebate.setF_renewal((txxufei - txxufeiyajin)*fee);// 续费返利
			partnerRebate.setLower_rebate(f1);// 间接收益
			partnerRebate.setSell_wall(wall);
			partnerRebate.setSell_vertical(vertical);
			partnerRebate.setSell_desktop(desktop);
			partnerRebate.setWall_renew(wall_renew);
			partnerRebate.setVertical_renew(vertical_renew);
			partnerRebate.setDesktop_renew(desktop_renew);
			partnerRebate.setBuy_combined(txfwf - txfwfyajin);
			partnerRebate.setRenewal_combined(txxufei - txxufeiyajin);
			partnerRebate.setAdd_time(new Date());
			partnerRebate.setMod_time(new Date());
		}
		return sum;
	}

	//我的总收入方法
	private Float totalRevenues(String username, Float fee,
			Float indirectBenefits, Float maintenance) {
		fee = fee + maintenance;
		Float f1 = 0f;
		//用户总收入(带押金)
		Float totalUserRevenue = jxOrderService.selectTotalUserRevenue(username);
		if(totalUserRevenue == null){
			totalUserRevenue = 0f;
		}
		//用户总押金
		Float totalDeposit = jxOrderService.totalDeposit(username);
		if(totalDeposit == null){
			totalDeposit = 0f;
		}
		Float zong = (totalUserRevenue - totalDeposit) * fee;
		//查询用户是否拥有下级
		List<Map<String,Object>> list = partnerService.findLevelOfUsername(username);
		for(int i = 0;i<list.size();i++){
			Map<String, Object> m = list.get(i);
			Long low_name = (Long) m.get("id");
			Float jianjiezong = jxOrderService.selectTotalUserRevenue(low_name);
			if(jianjiezong == null){
				jianjiezong = 0f;
			}
			Float jianjieyajin = jxOrderService.totalDeposit(low_name);
			if(jianjieyajin == null){
				jianjieyajin = 0f;
			}
			f1 += (jianjiezong - jianjieyajin) * indirectBenefits;
		}
		zong = zong +f1;
		return zong;
	}
	
	
	
	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		//得到日期
		String month = sdf1.format(date);
		month = month.substring(5);
		System.out.println(month);
		int yue = Integer.parseInt(month);
		//得到天数
		String d = sdf.format(date);
		d = d.substring(8);
		int s = Integer.parseInt(d);
		if(s < 15){
			c.setTime(new Date());
			if(yue == 3){
				c.set(Calendar.DAY_OF_MONTH, 28);
			}else{
				c.set(Calendar.DAY_OF_MONTH, 30);
			}
			c.add(Calendar.MONTH, -1);
			Date m = c.getTime();
			String mon = sdf.format(m);
			System.out.println(mon);
			System.out.println("sss");
		}else{
			c.setTime(new Date());
			if(yue == 4){
				c.set(Calendar.DAY_OF_MONTH, 28);
			}else{
				c.set(Calendar.DAY_OF_MONTH, 30);
			}
			c.add(Calendar.MONTH, -2);
			Date m = c.getTime();
			String mon = sdf.format(m);
			System.out.println(mon);
			System.out.println("ddd");
		}
		
	}
}
