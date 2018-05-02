package com.game.smvc.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

import org.opensaml.xml.signature.J;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.bmanager.entity.JxPartner;
import com.game.bmanager.service.IJxPartnerService;
import com.game.entity.account.User;
import com.game.services.account.AccountManager;
import com.game.smvc.core.config.CustomConfig;
import com.game.smvc.core.http.HttpSender;
import com.game.smvc.dao.WebDao;
import com.game.smvc.entity.JxAlipayAccount;
import com.game.smvc.entity.JxDrawPeople;
import com.game.smvc.entity.JxInformationSafety;
import com.game.smvc.entity.JxPartnerRebate;
import com.game.smvc.entity.JxPhCode;
import com.game.smvc.entity.JxRebateProportion;
import com.game.smvc.entity.JxRebates;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxAlipayAccountService;
import com.game.smvc.service.IJxDrawPeopleService;
import com.game.smvc.service.IJxInformationSafetyService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPartnerRebateService;
import com.game.smvc.service.IJxPhCodeService;
import com.game.smvc.service.IJxRebateProportionService;
import com.game.smvc.service.IJxRebatesService;
import com.game.smvc.service.IJxUserService;
import com.game.smvc.util.HttpUtil;
import com.game.smvc.util.IdentifyingUtil;
import com.game.smvc.util.RandomUtil;
import com.game.smvc.util.Sha1Util;
import com.game.smvc.util.UserUtil;
import com.game.util.Des;

/**
 * 订单管理
 * 
 * @author lh 2017/08/14
 */
@Controller
@RequestMapping({ "/smvc" })
public class JxOrderController {

	@Autowired
	private WebDao webDao;
	@Autowired
	private IJxOrderService jxOrderService;
	@Autowired
	private IJxUserService accUserService;
	@Autowired
	private IJxPartnerService partnerService;
	@Autowired
	private IJxPhCodeService jxPhCodeService;
	@Autowired
	private IJxAlipayAccountService jxAlipayAccountService;
	@Autowired
	private IJxInformationSafetyService jxInformationSafetyService;
	@Autowired
	private IJxRebatesService jxRebatesService;
	@Autowired
	private IJxPartnerRebateService jxPartnerRebateService;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private IJxRebateProportionService jxRebateProportionService;
	@Autowired
	private IJxDrawPeopleService jxDrawPeopleService;

	/**
	 * 点击'订单管理'查询这个产品经理下面所有的订单
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/myOrders")
	public Result myOrders(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			// 产品经理编号
			String safetyMark = jsonObject.getString("safetyMark");

			JxInformationSafety safety = null;
			String ord_managerno = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					ord_managerno = safety.getUser_name();
				} else {
					return new Result(Errors.SECURITY_VERIFICATION_FAILED);
				}
			} else {
				return new Result(Errors.SECURITY_VERIFICATION_FAILED);
			}
			int page = Integer.parseInt(jsonObject.getString("page"));
			String state = jsonObject.getString("state");

			if ((ord_managerno == null) || ("".equals(ord_managerno.trim()))) {
				return new Result(Errors.PARTNER_ERROR_NOTFOUND);
			}

			// 全部订单
			if ("".equals(state) || state == null) {
				List<Map<String, Object>> orders = jxOrderService
						.queryOrdersByuid(ord_managerno, (page - 1) * 10);

				return new SecretResult(Errors.OK, orders);
			} else if ("0".equals(state)) {
				// 代付款订单
				List<Map<String, Object>> orders = jxOrderService
						.findGenerationOfPayment(ord_managerno, state,
								(page - 1) * 10);
				return new SecretResult(Errors.OK, orders);
			} else if ("1".equals(state)) {
				// 已付款订单
				List<Map<String, Object>> orders = jxOrderService
						.findPaymentHasBenn(ord_managerno, state,
								(page - 1) * 10);
				return new SecretResult(Errors.OK, orders);
			} else if ("3".equals(state)) {
				// 已绑定订单
				List<Map<String, Object>> orders = jxOrderService
						.findIsBinding(ord_managerno, state, (page - 1) * 10);
				return new SecretResult(Errors.OK, orders);
			} else if ("4,5".equals(state)) {
				// 续费订单
				List<Map<String, Object>> orders = jxOrderService.findRenewal(
						ord_managerno, state, (page - 1) * 10);
				return new SecretResult(Errors.OK, orders);
			} else {

				return new Result(Errors.USER_ERROR_NOT_ORDER);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 查看订单详情
	 * 
	 * @param request
	 * @param orderNum
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "/partner/orderuDetail")
	public Result orderDetail(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String ono = jsonObject.getString("ord_no");
			// 根据订单号查看订单详情
			List<Map<String, Object>> list = this.jxOrderService
					.findOrderDetailByOno(ono);
			return new SecretResult(Errors.OK, list);

		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 提现优化 2017/09/04
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/yhwithdrawalamount")
	public Result withdrawalAmount(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			// String safetyMark = jsonObject.getString("safetyMark");
			String safetyMark = "QfJ5h4TF82MzSp66lpi4radPaRfHY1wJZTYIP19U";
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

							// 上级是否分配比例
							JxRebateProportion proportion = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ username + "'");
							// 得到上级
							JxPartner partner = partnerService
									.findUnique("from JxPartner where id = '"
											+ username + "'");
							System.out.println("---逻辑部分---");
							if (proportion == null) {
								Maintenance = jxRebates.getRwl_install();// 维护费
								fee = jxRebates.getService_fee();// 服务费
								install = jxRebates.getF_install();
							} else {
								fee = proportion.getRp_rebates();
								install = proportion.getRp_installed();
								Maintenance = jxRebates.getRwl_install();// 维护费
							}
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
								fee = IdentifyingUtil.proportion(level);
								Maintenance = jxRebates.getRwl_install();// 维护费
								if (partner.getParParentid() != null
										|| partner.getParParentid().length() != 0) {// 有上级
									if (proportion == null) {// 没有被修改过比例
										fee = jxRebates.getF_install()
												+ jxRebates.getService_fee();
										Maintenance = jxRebates
												.getRwl_install();// 维护费
									} else {
										fee = proportion.getRp_total();
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
								jxPartnerRebateService.save(jxPartnerRebate);
							} else {
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
			last_add_time = "0000-00-00 00:00:00";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, Object> ma = mm.get(0);
			Date s = (Date) ma.get("add_time");
			last_add_time = sdf.format(s);
		}

		// 服务费返利
		Float money1 = jxOrderService.findTotalMoneyOrBg(username, time);
		if (money1 == null) {
			money1 = 0f;
		}

		// 服务费押金
		Float Service_charge = jxOrderService.findServiceChargeYJ(username,
				time);
		if (Service_charge == null) {
			Service_charge = 0f;
		}
		Float fwf = money1 - Service_charge;// 总服务费金额

		// 续费返利
		Float renewal1 = jxOrderService.findRenewalBg(username, time);
		if (renewal1 == null) {
			renewal1 = 0f;
		}

		// 续费押金
		Float Renew_the_deposit = jxOrderService.findRenewalYJ(username, time);
		if (Renew_the_deposit == null) {
			Renew_the_deposit = 0f;
		}
		Float renewal = renewal1 - Renew_the_deposit;// 续费总金额
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
		System.out.println("总安装费:" + installation_whf);
		System.out.println("---服务费---");

		Float f = (fwf + renewal) * fee;// 总服务费
		Float tatol_fwf = f + installation_whf;// 总费用

		Float fw = fwf * fee;
		Float xf = renewal * fee;
		System.out.println("服务费:" + fw);
		System.out.println("服务续费:" + xf);
		System.out.println("fwf:" + fwf);
		System.out.println("renewal:" + renewal);

		// String par_parentid = partnerService.findParentid(username);//得到上级
		// 下级返利
		Float fl1 = 0f;
		Float fl2 = 0f;
		Float fl3 = 0f;
		fee = jxRebates2.getService_fee();
		if (partner.getParParentid() == null
				|| partner.getParParentid().length() == 0) {// 最高级用户或没有被分配比例
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
											fee = jxRebates2.getService_fee();
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
						 * .findLevelOfUsername(m.get("id"));// 区县级、产品经理
						 * 
						 * 
						 * System.out.println("----断开处----"); if (tierqx.size()
						 * > 0) { for (int j = 0; j < tierqx.size(); j++) {
						 * Map<String, Object> m1 = tierqx.get(j); if
						 * (m1.get("par_level").equals("3")) {// 省---市---区县
						 * JxDrawPeople people3 = new JxDrawPeople();
						 * people3.setAdd_time(new Date());
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
										.getService_fee());
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
									if (total_pledge4 == 0) {
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
										.getService_fee());
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
			// 得到区县级
			List<Map<String, Object>> tier = partnerService
					.findLevelOfUsername(username);// 区县级
			// 判断区县级是否为空
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
					JxRebateProportion jxProportion3 = jxRebateProportionService
							.findUnique("from jx_rebate_proportion where user_number = '"
									+ m.get("id")
									+ "' and most_superior_id = '"
									+ username
									+ "'");
					if (jxProportion3 != null) {
						fee = jxProportion3.getSuper_totall();
					}
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
							JxRebateProportion jxProportion4 = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ m1.get("id")
											+ "' and most_superior_id = '"
											+ username + "'");
							if (jxProportion4 != null) {
								fee = jxProportion4.getSuper_totall();
							}
							if (money4 == null) {
								money4 = 0f;
							}
							if (total_pledge4 == 0) {
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

		tatol_fwf = tatol_fwf + fl1 + fl2 + fl3;
		System.out.println("总服务费:" + tatol_fwf);
		Float xj = fl1 + fl2 + fl3;
		System.out.println("下级返利:" + xj);
		DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(tatol_fwf);
		Float sum = Float.valueOf(p);

		int wall = jxOrderService.findWallNumber(username, time, last_add_time);// 壁挂式台数
		int desktop = jxOrderService.findVerticalNumber(username, time,
				last_add_time);// 台式
		int vertical = jxOrderService.findDesktopNumber(username, time,
				last_add_time);// 立式
		// 续费台数
		int wall_renew = jxOrderService.findWallRenewNumber(username, time,
				last_add_time);
		int desktop_renew = jxOrderService.findVerticalRenewNumber(username,
				time, last_add_time);// desktop_renew
		int vertical_renew = jxOrderService.findDesktopRenewNumber(username,
				time, last_add_time);// vertical_renew

		if (partnerRebate == null) {
			System.out.println("1");
			jxPartnerRebate.setUser_name(username);
			jxPartnerRebate.setTotal_amount(sum);
			jxPartnerRebate.setWithdrawal_order(withdrawalOrderNo);// 提现单号
			jxPartnerRebate.setService_fee(fw);// 服务费返利
			jxPartnerRebate.setF_renewal(xf);// 续费返利
			jxPartnerRebate.setLower_rebate(xj);// 返利补贴
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
			partnerRebate.setLower_rebate(xj);// 返利补贴
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
	}
	
}
