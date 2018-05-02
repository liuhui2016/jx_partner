package com.game.smvc.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

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
import com.game.smvc.entity.JxInformationSafety;
import com.game.smvc.entity.JxPhCode;
import com.game.smvc.entity.JxRebateProportion;
import com.game.smvc.entity.JxRebates;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxAlipayAccountService;
import com.game.smvc.service.IJxInformationSafetyService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPartnerMessagesService;
import com.game.smvc.service.IJxPhCodeService;
import com.game.smvc.service.IJxRebateProportionService;
import com.game.smvc.service.IJxRebatesService;
import com.game.smvc.service.IJxUserService;
import com.game.smvc.util.HttpUtil;
import com.game.smvc.util.IdentifyingUtil;
import com.game.smvc.util.Sha1Util;
import com.game.smvc.util.UserUtil;
import com.game.util.Des;

/**
 * 我的
 * 
 * @author lh 2017/08/14
 */
@Controller
@RequestMapping({ "/smvc" })
public class MineController {

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
	private IJxPartnerMessagesService jxPartnerMessagesService;
	@Autowired
	private IJxRebateProportionService jxRebateProportionService;
	@Autowired
	private IJxRebatesService jxRebatesService;

	/**
	 * 我的下属
	 * 
	 * @param request
	 * @return 2017/08/14
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/partner/mystaff") public Result
	 * myStaff(HttpServletRequest request) { try { String params =
	 * HttpUtil.getRquestParamsByIO(request); JSONObject jobj =
	 * JSONObject.fromObject(params); String safetyMark =
	 * jobj.getString("safetyMark"); int page =
	 * Integer.parseInt(jobj.getString("page"));
	 * 
	 * String safetyMark = "YbG5CcotjKG9GKTkAx4vYJzS6/A9oBS3uBHDqapV"; int page
	 * = 1;
	 * 
	 * JxInformationSafety safety = null; String username = null; safety =
	 * jxInformationSafetyService
	 * .findUnique("from JxInformationSafety where safety_mark = '" + safetyMark
	 * + "'"); if (safety != null) { if
	 * (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
	 * username = safety.getUser_name(); } else { return new
	 * Result(Errors.SECURITY_VERIFICATION_FAILED); } } else { return new
	 * Result(Errors.SECURITY_VERIFICATION_FAILED); }
	 * 
	 * // 名称 编号 级别 销售总台数 // 根据产品经理编号查询下级 List<Map<String, Object>> list = null;
	 * List<Map<String, Object>> date = new ArrayList<Map<String, Object>>(); if
	 * (username.contains("A") || username.contains("B") ||
	 * username.contains("C")) { list =
	 * partnerService.findMineUnderlingForSpecial(username, (page - 1) * 10); if
	 * (list.size() > 0) { Map<String, Object> m = new HashMap<String,
	 * Object>(); for (int i = 0; i < list.size(); i++) { Map<String, Object>
	 * map = list.get(i); Long par_sellernum = (Long) map.get("par_sellernum");
	 * if (par_sellernum == null) { par_sellernum = 0l; } m.put("par_other",
	 * map.get("par_other")); m.put("par_name", map.get("par_name"));
	 * m.put("par_level", map.get("par_level")); m.put("par_sellernum",
	 * par_sellernum); date.add(m); } } } else { list =
	 * partnerService.findMineUnderling(username, (page - 1) * 10); if
	 * (list.size() > 0) { Map<String, Object> m = new HashMap<String,
	 * Object>(); for (int i = 0; i < list.size(); i++) { Map<String, Object>
	 * map = list.get(i); Long par_sellernum = (Long) map.get("par_sellernum");
	 * if (par_sellernum == null) { par_sellernum = 0l; } m.put("par_other",
	 * map.get("id")); m.put("par_name", map.get("par_name"));
	 * m.put("par_level", map.get("par_level")); m.put("par_sellernum",
	 * par_sellernum); date.add(m); } }
	 * 
	 * } return new SecretResult(Errors.OK, date); } catch (Exception e) {
	 * e.printStackTrace(); return new Result(Errors.SERVER_DATA_SAVE_FAIL); } }
	 */

	/**
	 * 我的下属
	 * 
	 * @param request
	 * @return 2017/08/29
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/partner/mystaff") public Result
	 * myStaff(HttpServletRequest request) { try { String params =
	 * HttpUtil.getRquestParamsByIO(request); JSONObject jobj =
	 * JSONObject.fromObject(params);
	 * 
	 * String safetyMark = "xZpT2HL/TvcUSFPYGjfBM/Xn1UbR5L2cfK9eV7xa"; int page
	 * = 1;
	 * 
	 * String safetyMark = jobj.getString("safetyMark"); int page =
	 * Integer.parseInt(jobj.getString("page")); List<Map<String,
	 * List<Map<String, Object>>>> lists = new ArrayList<Map<String,
	 * List<Map<String, Object>>>>(); Map<String, List<Map<String, Object>>> l =
	 * new HashMap<String, List<Map<String, Object>>>(); Map<String,
	 * List<Map<String, Object>>> l1 = new HashMap<String, List<Map<String,
	 * Object>>>(); Map<String, List<Map<String, Object>>> l2 = new
	 * HashMap<String, List<Map<String, Object>>>(); JxInformationSafety safety
	 * = null; String username = null; safety = jxInformationSafetyService
	 * .findUnique("from JxInformationSafety where safety_mark = '" + safetyMark
	 * + "'"); if (safety != null) { if (Des.decryptDES(safetyMark).contains(
	 * IdentifyingUtil.Identifying())) { username = safety.getUser_name(); }
	 * else { return new Result(Errors.SECURITY_VERIFICATION_FAILED); } } else {
	 * return new Result(Errors.SECURITY_VERIFICATION_FAILED); }
	 * 
	 * // 名称 编号 级别 销售总台数 // 根据产品经理编号查询下级 List<Map<String, Object>> list = null;
	 * List<Map<String, Object>> date = new ArrayList<Map<String, Object>>();
	 * String par_parentid = partnerService.findParentid(username); if
	 * (par_parentid == null || par_parentid.equals("")) {
	 * 
	 * } if (username.contains("A") || username.contains("B") ||
	 * username.contains("C")) { list =
	 * partnerService.findMineUnderlingForSpecial(username, (page - 1) * 10); if
	 * (list.size() > 0) { Map<String, Object> m = new HashMap<String,
	 * Object>(); for (int i = 0; i < list.size(); i++) { Map<String, Object>
	 * map = list.get(i); Long par_sellernum = (Long) map.get("par_sellernum");
	 * if (par_sellernum == null) { par_sellernum = 0l; } m.put("par_other",
	 * map.get("par_other")); m.put("par_name", map.get("par_name"));
	 * m.put("par_level", map.get("par_level")); m.put("par_sellernum",
	 * par_sellernum); date.add(m);
	 * 
	 * } } } else { list = partnerService.findMineUnderling(username, (page - 1)
	 * * 10);
	 * 
	 * l.put("direct", list);
	 * 
	 * // 区县间接下属 List<Map<String, Object>> list1 = null; List<Map<String,
	 * Object>> list0 = new ArrayList<Map<String, Object>>(); if (list.size() >
	 * 0) { for (int i = 0; i < list.size(); i++) { Map<String, Object> map =
	 * list.get(i);
	 * 
	 * list1 = partnerService.findMineUnderling(map.get("id"), (page - 1) * 10);
	 * for (int j = 0; j < list1.size(); j++) { Map<String, Object> maps =
	 * list1.get(j); maps.put("super_id", map.get("id"));
	 * maps.put("super_par_name", map.get("par_name")); maps.put("par_other",
	 * map.get("par_other")); maps.put("par_name", map.get("par_name"));
	 * maps.put("par_level", map.get("par_level")); list0.add(maps); }
	 * System.out.println("list1:" + list1); } l1.put("indirection", list0); }
	 * 
	 * // 产品经理间接下属 List<Map<String, Object>> list2 = null; List<Map<String,
	 * Object>> list01 = new ArrayList<Map<String, Object>>(); if (list1.size()
	 * > 0) { for (int i = 0; i < list1.size(); i++) { Map<String, Object> map =
	 * list1.get(i); list2 = partnerService.findMineUnderling(map.get("id"),
	 * (page - 1) * 10); for (int j = 0; j < list2.size(); j++) { Map<String,
	 * Object> maps = list2.get(j); maps.put("super_id", map.get("id"));
	 * maps.put("super_par_name", map.get("par_name")); maps.put("par_other",
	 * map.get("par_other")); maps.put("par_name", map.get("par_name"));
	 * maps.put("par_level", map.get("par_level")); list01.add(maps); } } if
	 * (list2 != null) { l2.put("indirection", list01); lists.add(l2); } } }
	 * lists.add(l); lists.add(l1);
	 * 
	 * return new SecretResult(Errors.OK, lists); } catch (Exception e) {
	 * e.printStackTrace(); return new Result(Errors.SERVER_DATA_SAVE_FAIL); } }
	 */

	/**
	 * 我的下属 2017/08/30
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/mystaff")
	public Result myStaff(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject jobj = JSONObject.fromObject(params);
			String safetyMark = jobj.getString("safetyMark");
			int page = Integer.parseInt(jobj.getString("page"));
			int tag = Integer.parseInt(jobj.getString("tag"));
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
				} else {
					return new Result(Errors.SECURITY_VERIFICATION_FAILED);
				}
			} else {
				return new Result(Errors.SECURITY_VERIFICATION_FAILED);
			}

			JxPartner jxPartner = null;
			if (username.contains("A") || username.contains("B")
					|| username.contains("C")) {
				jxPartner = partnerService
						.findUnique("from JxPartner where par_other = '"
								+ username + "'");
			} else {
				jxPartner = partnerService
						.findUnique("from JxPartner where id = '" + username
								+ "'");
			}

			// 名称 编号 级别 销售总台数
			// 根据产品经理编号查询下级
			List<Map<String, Object>> list = null;
			List<Map<String, Object>> date = new ArrayList<Map<String, Object>>();
			String par_parentid = partnerService.findParentid(username);
			Map<String, Object> l = new HashMap<String, Object>();

			int par_level = Integer.parseInt(jxPartner.getPAR_LEVEL());
			if ((par_parentid == null || par_parentid.equals(""))
					&& (par_level < 4 || par_level != -3)) {
				String permissions = "0";
				l.put("permissions", permissions);
			} else {
				String permissions = "1";
				l.put("permissions", permissions);
			}

			if (username.contains("A") || username.contains("B")
					|| username.contains("C")) {

				if (tag == 0) {
					date = partnerService.findMineUnderlingForSpecial(username,
							(page - 1) * 10);
					l.put("date", date);
					return new SecretResult(Errors.OK, l);
				} else {
					list = partnerService.findMineUnderlingForSpecial(username,
							(page - 1) * 10);
					List<Map<String, Object>> list5 = null;
					if (list.size() > 0) {
						Map<String, Object> m1 = new HashMap<String, Object>();
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> map = list.get(i);
							list5 = partnerService.findMineUnderlingToSpecial(
									map.get("id"), (page - 1) * 10);
							for (int j = 0; j < list5.size(); j++) {
								Map<String, Object> maps = list5.get(j);
								m1.put("super_id", map.get("id"));
								m1.put("super_par_name", map.get("par_name"));
								m1.put("par_other", maps.get("id"));
								m1.put("par_name", maps.get("par_name"));
								m1.put("par_level", maps.get("par_level"));
								date.add(m1);
							}
						}
					}
					List<Map<String, Object>> list6 = null;
					if (list5.size() > 0) {
						Map<String, Object> m2 = new HashMap<String, Object>();
						for (int i = 0; i < list5.size(); i++) {
							Map<String, Object> map = list5.get(i);
							list6 = partnerService.findMineUnderlingToSpecial(
									map.get("id"), (page - 1) * 10);
							for (int j = 0; j < list6.size(); j++) {
								Map<String, Object> maps = list6.get(j);
								m2.put("super_id", map.get("id"));
								m2.put("super_par_name", map.get("par_name"));
								m2.put("par_other", maps.get("id"));
								m2.put("par_name", maps.get("par_name"));
								m2.put("par_level", maps.get("par_level"));
								date.add(m2);
							}
						}
					}
					l.put("date", date);
					return new SecretResult(Errors.OK, l);
				}

			} else {
				if (tag == 0) {
					date = partnerService.findMineUnderling(username,
							(page - 1) * 10);
					l.put("date", date);
					return new SecretResult(Errors.OK, l);
				} else {
					list = partnerService.findMineUnderling(username,
							(page - 1) * 10);
					// 区县间接下属
					List<Map<String, Object>> list1 = null;
					if (list.size() > 0) {
						Map<String, Object> m1 = new HashMap<String, Object>();
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> map = list.get(i);
							list1 = partnerService.findMineUnderling(
									map.get("id"), (page - 1) * 10);
							for (int j = 0; j < list1.size(); j++) {
								System.out.println(list1.size());
								Map<String, Object> maps = list1.get(j);
								maps.put("super_id", map.get("id"));
								maps.put("super_par_name", map.get("par_name"));
								m1.put("par_other", maps.get("id"));
								m1.put("par_name", maps.get("par_name"));
								m1.put("par_level", maps.get("par_level"));
								date.add(maps);
							}

							// 产品经理间接下属
							List<Map<String, Object>> list2 = null;

							if (list1.size() > 0) {
								Map<String, Object> m2 = new HashMap<String, Object>();
								for (int z = 0; z < list1.size(); z++) {
									Map<String, Object> map1 = list1.get(z);
									list2 = partnerService.findMineUnderling(
											map1.get("id"), (page - 1) * 10);
									for (int j = 0; j < list2.size(); j++) {
										Map<String, Object> maps = list2.get(j);
										maps.put("super_id", map1.get("id"));
										maps.put("super_par_name",
												map1.get("par_name"));
										m2.put("par_other", maps.get("id"));
										m2.put("par_name", maps.get("par_name"));
										m2.put("par_level",
												maps.get("par_level"));
										date.add(maps);
									}
								}
							}
						}
					}
					l.put("date", date);
					return new SecretResult(Errors.OK, l);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.SERVER_DATA_SAVE_FAIL);
		}
	}

	/**
	 * 下属订单列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/mystaffdetails")
	public Result myStaffDetails(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			int page = Integer.parseInt(jsonObject.getString("page"));
			String underling = jsonObject.getString("username");
			String state = jsonObject.getString("state");
			JxInformationSafety safety = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					if ("1".equals(state)) {
						// 已付款订单
						List<Map<String, Object>> orders = jxOrderService
								.findPaymentHasBenn(underling, state,
										(page - 1) * 10);
						return new SecretResult(Errors.OK, orders);
					} else if ("3".equals(state)) {
						// 已绑定订单
						List<Map<String, Object>> orders = jxOrderService
								.findIsBinding(underling, state,
										(page - 1) * 10);
						return new SecretResult(Errors.OK, orders);
					} else if ("4,5".equals(state)) {
						// 续费订单
						List<Map<String, Object>> orders = jxOrderService
								.findRenewal(underling, state, (page - 1) * 10);
						return new SecretResult(Errors.OK, orders);
					} else {

						return new Result(Errors.USER_ERROR_NOT_ORDER);
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

	/**
	 * 比例分配
	 * 根据
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/permissions")
	public Result permissions(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String underling = jsonObject.getString("username");// 下属编号
			String par_level = jsonObject.getString("par_level");// 级别
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			JxPartner partner = partnerService
					.findUnique("from JxPartner where id = '" + underling + "'");
			if (partner == null) {
				return new Result(Errors.NO_PARTNER);
			}
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
					JxPartner jxPartner = null;
					if (username.contains("A") || username.contains("B")
							|| username.contains("C")) {
						jxPartner = partnerService
								.findUnique("from JxPartner where par_other = '"
										+ underling + "'");
					} else {
						jxPartner = partnerService
								.findUnique("from JxPartner where id = '"
										+ underling + "'");
					}
					String par_parentid = partnerService.findParentid(username);
					if (par_parentid == null || par_parentid.length() == 0) {
						// 判断级别是否一致
						if (jxPartner.getPAR_LEVEL().equals(par_level)) {
							List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
							Map<String, Object> map = new HashMap<String, Object>();
							// 查询下属返利表
							JxRebateProportion jxRebateProportion = jxRebateProportionService
									.findUnique("from jx_rebate_proportion where user_number = '"
											+ underling + "'");
							if (jxRebateProportion == null) {
								// 查询返利表
								JxRebates jxRebates = jxRebatesService
										.findUnique("from JxRebates where par_level = '"
												+ par_level + "'");
								Float total_rebate = jxRebates.getService_fee()
										+ jxRebates.getF_install();
								map.put("service_fee",
										jxRebates.getService_fee());// 返利比例
								map.put("install", jxRebates.getF_install());// 装机比例
								map.put("total_rebate", total_rebate);// 总比例
								map.put("username", underling);
								list.add(map);
								return new SecretResult(Errors.OK, list);
							} else {
								// 返利比例表
								Float total_rebate = jxRebateProportion
										.getRp_rebates()
										+ jxRebateProportion.getRp_installed();
								map.put("service_fee",
										jxRebateProportion.getRp_rebates());// 返利比例
								map.put("install",
										jxRebateProportion.getRp_installed());// 装机比例
								map.put("total_rebate", total_rebate);
								map.put("username", underling);
								list.add(map);
								return new SecretResult(Errors.OK, list);
							}
						} else {
							// 级别不一致
							return new Result(
									Errors.THE_PARTNERSHIP_LEVEL_IS_NOT_CORRECT);
						}

					} else {
						return new Result(Errors.INSUFFICIENT_PERMISSIONS);
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

	/**
	 * 修改下级比例并保存到下级比例表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/updatepermission")
	public Result updatePermission(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String underling = jsonObject.getString("username");// 下属编号
			String rebates = jsonObject.getString("rebates");// 返利比例
			String installed = jsonObject.getString("installed");// 装机比例
			String total = jsonObject.getString("total");// 总计

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					// 逻辑代码块
					username = safety.getUser_name();
					String par_parentid = partnerService.findParentid(username);
					if (par_parentid != null && par_parentid.length() != 0) {
						return new Result(Errors.INSUFFICIENT_PERMISSIONS);
					}
					JxPartner jxPartner = null;
					JxPartner partner = null;
					if (username.contains("A") || username.contains("B")
							|| username.contains("C")) {
						jxPartner = partnerService
								.findUnique("from JxPartner where par_other = '"
										+ username + "'");
						partner = partnerService
								.findUnique("from JxPartner where par_other = '"
										+ underling + "'");
					} else {
						jxPartner = partnerService
								.findUnique("from JxPartner where id = '"
										+ username + "'");
						partner = partnerService
								.findUnique("from JxPartner where id = '"
										+ underling + "'");
					}

					if (jxPartner == null && partner == null) {
						return new Result(Errors.NO_PARTNER);
					}
					// 查询比例表
					JxRebateProportion jxRebateProportion = jxRebateProportionService
							.findUnique("from jx_rebate_proportion where user_number = '"
									+ underling + "'");
					// 根据级别得出返利比例
					// 上级比例
					JxRebates jxRebates = jxRebatesService
							.findUnique("from JxRebates where par_level = '"
									+ jxPartner.getPAR_LEVEL() + "'");
					String level = jxPartner.getPAR_LEVEL();
					Float p = updatePermissionsss(username, underling, level,
							jxRebates);
					DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
					String sum = decimalFormat.format(p);
					Float f = Float.valueOf(sum);
					System.out.println("浮动范围:" + f);
					// 得出下级比例
					JxRebates jxRebates2 = jxRebatesService
							.findUnique("from JxRebates where par_level = '"
									+ partner.getPAR_LEVEL() + "'");
					// 下级比例
					Float rp_rebates = Float.parseFloat(rebates);

					// 上级所拿服务费比例
					/*
					 * Float super_rebates = jxRebates.getService_fee() +
					 * (jxRebates2.getService_fee() - rp_rebates);
					 */
					System.out.println("rp_rebates:"+rp_rebates);
					System.out.println("jxRebates2.getService_fee():"+jxRebates2.getService_fee());
					Float super_rebates = f
							+ (jxRebates2.getService_fee() - rp_rebates);

					/*Float a = jxRebates.getService_fee()
							+ jxRebates2.getService_fee();*/
					// 装机比例
					Float rp_install = Float.parseFloat(installed);

					// 上级所拿安装费
					Float super_install = (jxRebates2.getF_install() - rp_install);

					/*Float b = jxRebates.getF_install()
							+ jxRebates2.getF_install();*/

					// 总返利
					Float c = jxRebates.getService_fee()
							+ jxRebates2.getService_fee()
							+ jxRebates.getF_install();
					System.out.println("c:" + c);
					// 总修改比例
					Float xg = super_install + super_rebates;
					System.out.println("xg1:" + xg);
					String sin = decimalFormat.format(xg);
					xg = Float.valueOf(sin);
					System.out.println("xg:" + xg);
					if (c > xg && xg > 0.009) {
						/*
						 * if ((a > super_rebates && super_rebates > 0) && (b >
						 * super_install && super_install > 0)) {// 大于最大，小于最小
						 */if (jxRebateProportion == null) {
							JxRebateProportion proportion = new JxRebateProportion();
							proportion.setAdd_time(new Date());
							proportion.setPar_level(Integer.parseInt(partner
									.getPAR_LEVEL()));
							proportion.setReal_name(partner.getPAR_NAME());
							proportion.setUser_number(underling);
							proportion.setRp_installed(Float
									.parseFloat(installed));
							proportion.setRp_rebates(Float.parseFloat(rebates));
							proportion.setRp_total(Float.parseFloat(total));
							proportion.setMost_superior_id(username);
							proportion.setSuper_level(Integer
									.parseInt(jxPartner.getPAR_LEVEL()));
							proportion.setSuper_installed(super_install);
							proportion.setSuper_rebates(super_rebates);
							proportion
									.setSuper_totall((super_install + super_rebates));
							jxRebateProportionService.save(proportion);
							map.put("service_fee", rebates);// 返利比例
							map.put("install", installed);// 装机比例
							map.put("total_rebate", total);
							map.put("username", underling);
							list.add(map);
							return new SecretResult(Errors.OK, list);

						 																											} else {
							// 更新数据
							jxRebateProportion.setAdd_time(new Date());
							jxRebateProportion.setPar_level(Integer
									.parseInt(partner.getPAR_LEVEL()));
							jxRebateProportion.setReal_name(partner
									.getPAR_NAME());
							jxRebateProportion.setUser_number(underling);
							jxRebateProportion.setRp_installed(Float
									.parseFloat(installed));
							jxRebateProportion.setRp_rebates(Float
									.parseFloat(rebates));
							jxRebateProportion.setRp_total(Float
									.parseFloat(total));
							jxRebateProportion.setMost_superior_id(username);
							jxRebateProportion.setSuper_level(Integer
									.parseInt(jxPartner.getPAR_LEVEL()));
							jxRebateProportion
									.setSuper_installed(super_install);
							jxRebateProportion.setSuper_rebates(super_rebates);
							jxRebateProportion
									.setSuper_totall((super_install + super_rebates));
							jxRebateProportion.setMod_time(new Date());
							jxRebateProportionService.save(jxRebateProportion);
							map.put("service_fee", rebates);// 返利比例
							map.put("install", installed);// 装机比例
							map.put("total_rebate", total);
							map.put("username", underling);
							list.add(map);
							return new SecretResult(Errors.OK, list);
						}
					} else {
						// 比例不正确
						return new Result(
								Errors.THE_REBATE_RATIO_IS_NOT_CORRECT);
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

	/**
	 * 修改比例方法 2017/09/20
	 * 
	 * @param username
	 * @param underling
	 * @param level
	 * @return
	 */
	public Float updatePermissions(String username, String underling,
			String level, JxRebates jxRebates) {
		Float fee = null;
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
						System.out.println("下级:" + m.get("id"));
						System.out.println("大小:" + tierqx.size());
						Long s = (Long) m.get("id");
						String a = String.valueOf(s);
						
							if (tierqx.size() <= 0) {// 没有下级
								System.out.println("---省市---");
								fee = IdentifyingUtil.ssx();
								return fee;
							} else {
								System.out.println("yxj001");
								// 市有下级并且下级是区县
								for (int j = 0; j < tierqx.size();j++) {
									Map<String, Object> m1 = tierqx.get(j);
									if (m1.get("par_level").equals("3")) {// 省---市---区县
										// 市
										List<Map<String, Object>> tiercp = partnerService
												.findLevelOfUsername(m1
														.get("id"));// 产品经理
										fee = IdentifyingUtil.zcfw();
										if (tiercp.size() > 0) {
											fee = IdentifyingUtil.zcfw();
										}
										return fee;

									} else {
										// 下级是产品经理
										fee = IdentifyingUtil.ssx();
										return fee;
									}
								}
							}
						}
					

					if (m.get("par_level").equals("3")) {// 省---区县
						List<Map<String, Object>> tiercp = partnerService
								.findLevelOfUsername(m.get("id"));// 产品经理
						fee = IdentifyingUtil.sqc();
						if (tiercp.size() > 0) {// 有下级产品经理
							fee = IdentifyingUtil.sqc();
						}
						return fee;
					}
					if (m.get("par_level").equals("4")) {// 省---产品经理
						fee = (IdentifyingUtil.proportion(level) - jxRebates
								.getF_install());
						return fee;
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
						fee = jxRebates.getService_fee();
						List<Map<String, Object>> tiercp = partnerService
								.findLevelOfUsername(m.get("id"));// 产品经理
						if (tiercp.size() > 0) {// 产品经理
							fee = jxRebates.getService_fee();
						}
						return fee;
					}
					if (m.get("par_level").equals("4")) {// 市---产品经理
						fee = (IdentifyingUtil.proportion(level) - jxRebates
								.getF_install());
						return fee;
					}
				}
			}

		}
		if (level.equals("3")) {// 最上级为区县
			List<Map<String, Object>> tier = partnerService
					.findLevelOfUsername(username);// 产品经理
			if (tier.size() > 0) {// 区县---产品经理
				fee = jxRebates.getService_fee();
				return fee;
			}
		}
		return fee;
	}

	/**
	 * 优化修改比例的方法
	 * 	2017/09/25
	 * @param username 上级编号
	 * @param underling 下级编号
	 * @param level 上级级别
	 * @param jxRebates 初始返利
	 * @return
	 */
	public Float updatePermissionss(String username, String underling,
			String level, JxRebates jxRebates) {
		JxPartner jxPartner = partnerService
				.findUnique("from JxPartner where id = '" + underling + "'");
		String pid = jxPartner.getParParentid();
		String levels = jxPartner.getPAR_LEVEL();// 拿到下级级别
		Float fee = null;
		if (level.equals("1")) {
			if (levels.equals("2")) {// 下级为市
				List<Map<String, Object>> tier = partnerService
						.findLevelOfUsername(underling);// 市、区县级、产品经理
				if (tier.size() <= 0) {
					// 没有下级
					System.out.println("---省市---");
					fee = IdentifyingUtil.ssx();
					return fee;
				} else {
					// 有下级---区县/产品经理
					for (int i = 0; i < tier.size(); i++) {
						Map<String, Object> m = tier.get(i);
						if (m.get("par_level").equals("3")) {// 省---市---区县
							// 判断是否拥有产品经理
							List<Map<String, Object>> tierqx = partnerService
									.findLevelOfUsername(m.get("id"));// 产品经理
							fee = IdentifyingUtil.zcfw();
							System.out.println("---省市区县---");
							if (tierqx.size() > 0) {
								System.out.println("---省市区县产品经理---");
								fee = IdentifyingUtil.zcfw();
							}
							return fee;
						} else {// 省---市---产品经理
							System.out.println("---省市产品经理---");
							fee = IdentifyingUtil.zcfw();
							return fee;
						}
					}
				}

				/*if (tier.size() > 0) {// 有 市、区县级、产品经理
					for (int i = 0; i < tier.size(); i++) {
						Map<String, Object> m = tier.get(i);
						if (m.get("par_level").equals("2")) {// 省----市
							// 判断是否拥有下级
							List<Map<String, Object>> tierqx = partnerService
									.findLevelOfUsername(m.get("id"));// 区县级、产品经理
							System.out.println("下级:" + m.get("id"));
							System.out.println("大小:" + tierqx.size());
							Long s = (Long) m.get("id");
							String a = String.valueOf(s);
							if (a.equals(underling)) {
								if (tierqx.size() <= 0) {// 没有下级
									System.out.println("---省市---");
									fee = IdentifyingUtil.ssx();
									return fee;
								} else {
									System.out.println("yxj001");
									// 市有下级并且下级是区县
									for (int j = 0; j < tierqx.size(); j++) {
										Map<String, Object> m1 = tierqx.get(j);
										if (m1.get("par_level").equals("3")) {// 省---市---区县
											// 市
											List<Map<String, Object>> tiercp = partnerService
													.findLevelOfUsername(m1
															.get("id"));// 产品经理
											fee = IdentifyingUtil.zcfw();
											if (tiercp.size() > 0) {
												fee = IdentifyingUtil.zcfw();
											}
											return fee;

										} else {
											// 下级是产品经理
											fee = IdentifyingUtil.ssx();
											return fee;
										}
									}
								}
							}
						}

						if (m.get("par_level").equals("3")) {// 省---区县
							List<Map<String, Object>> tiercp = partnerService
									.findLevelOfUsername(m.get("id"));// 产品经理
							fee = IdentifyingUtil.sqc();
							if (tiercp.size() > 0) {// 有下级产品经理
								fee = IdentifyingUtil.sqc();
							}
							return fee;
						}
						if (m.get("par_level").equals("4")) {// 省---产品经理
							fee = (IdentifyingUtil.proportion(level) - jxRebates
									.getF_install());
							return fee;
						}
					}
				}*/
			} else if (levels.equals("3")) {// 省---区县
				// 判断有无上级---市
				//pid  根据下级去到的上级id
				if (pid.equals(username)) {//上级=最高级
					// 没有上级
					List<Map<String, Object>> tier = partnerService
							.findLevelOfUsername(underling);
					System.out.println("---省区县---");
					fee = IdentifyingUtil.sqc();
					if (tier.size() > 0) {
						System.out.println("---省区县产品经理---");
						fee = IdentifyingUtil.sqc();
					}
					return fee;
				} else {
					// 省---市---区县
					List<Map<String, Object>> tier = partnerService
							.findLevelOfUsername(underling);
					System.out.println("---省市区县---");
					fee = IdentifyingUtil.zcfw();
					if (tier.size() > 0) {
						System.out.println("---省市区县产品经理---");
						fee = IdentifyingUtil.zcfw();
					}
					return fee;
				}
			} else if (levels.equals("4")) {
				if (pid.equals(username)) {//上级=最高级
					// 省---产品经理
					System.out.println("---省产品经理---");
					fee = (IdentifyingUtil.proportion(level) - jxRebates
							.getF_install());
					return fee;
				} else {
					// 有上级 根据上级id 查询级别
					jxPartner = partnerService
							.findUnique("from JxPartner where id = '"
									+ jxPartner.getParParentid() + "'");
					if (jxPartner.getParParentid() == null) {//上级的上级为空
						if (jxPartner.getPAR_LEVEL().equals("2")) {
							System.out.println("---省市产品经理---");
							fee = IdentifyingUtil.ssx();
						} else {
							System.out.println("---省区县产品经理---");
							fee = IdentifyingUtil.sqc();
						}
						return fee;
					} else {
						System.out.println("---省市区县产品经理---");
						fee = IdentifyingUtil.zcfw();
						return fee;
					}
				}
			}
		}
		
		if (level.equals("2")) {// 最上级为市
			System.out.println("最上级市");
			// 得到区县级
			JxPartner partner = partnerService.findUnique("from JxPartner where id = '"+underling+"'");
			if(partner.getPAR_LEVEL().equals("3")){
				System.out.println("---市区县---");
				fee = jxRebates.getService_fee();
				List<Map<String, Object>> tier = partnerService
						.findLevelOfUsername(underling);//产品经理
				if(tier.size() > 0){
					System.out.println("---市区县产品经理---");
					fee = jxRebates.getService_fee();
				}
				return fee;
			}else{
				//产品经理
				if(partner.getParParentid().equals(username)){//父id = 最高级
					System.out.println("---市产品经理---");
					fee = (IdentifyingUtil.proportion(level) - jxRebates
							.getF_install());
					return fee;
				}else{//有区县
					System.out.println("---市区县产品经理---");
					fee = jxRebates.getService_fee();
					return fee;
				}
			}
			/*List<Map<String, Object>> tier = partnerService
					.findLevelOfUsername(underling);//区县级、产品经理
			if(tier.size() <= 0){
				System.out.println("区县级、产品经理");
				fee = jxRebates.getService_fee();
				System.out.println("fee"+fee);
				return fee;
			}else{
				//有产品经理
				System.out.println("产品经理");
				fee = (IdentifyingUtil.proportion(level) - jxRebates
						.getF_install());
				System.out.println("fee"+fee);
				return fee;
			}*/
		}
		if (level.equals("3")) {// 最上级为区县
			List<Map<String, Object>> tier = partnerService
					.findLevelOfUsername(underling);// 产品经理
			if (tier.size() > 0) {// 区县---产品经理
				System.out.println("---区县产品经理---");
				fee = jxRebates.getService_fee();
				return fee;
			}
		}
		return fee;
	}
	
	/**
	 * 标准版修改比例
	 * 2017/09/25
	 * @param username
	 * @param underling
	 * @param level
	 * @param jxRebates
	 * @return
	 */
	public Float updatePermissionsss(String username, String underling,
			String level, JxRebates jxRebates) {
		Float fee = null;
		if(level.equals("1")){
			fee = jxRebates.getService_fee();
			return fee;
		}else if(level.equals("2")){
			fee = jxRebates.getService_fee();
			return fee;
		}else if(level.equals("3")){
			fee = jxRebates.getService_fee();
			return fee;
		}
		return fee;
	}
	
	/**
	 * 我的消息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/mymessage")
	public Result myMessage(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			int page = Integer.parseInt(jsonObject.getString("page"));
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					List<Map<String, Object>> date = new ArrayList<Map<String, Object>>();

					username = safety.getUser_name();
					List<Map<String, Object>> list = jxPartnerMessagesService
							.findAllPartnerMessages(username, (page - 1) * 10);
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> m = new HashMap<String, Object>();
						Map<String, Object> map = list.get(i);
						Date o = (Date) map.get("message_time");
						if (o == null) {
							o = new Date();
						}
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String message_time = dateFormat.format(o);
						m.put("p_id", map.get("p_id"));
						m.put("p_name", map.get("p_name"));
						m.put("p_title", map.get("p_title"));
						m.put("p_content", map.get("p_content"));
						m.put("nextparams", map.get("nextparams"));
						m.put("p_isread", map.get("p_isread"));
						m.put("message_time", message_time);
						m.put("p_type", map.get("p_type"));
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
	 * 删除消息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/delmessage")
	public Result delMessage(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String id = jsonObject.getString("id");
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
					int mess = jxPartnerMessagesService
							.findDelPartnerMessages(id);
					if (mess > 0) {
						return new Result(Errors.OK);
					} else {
						return new Result(Errors.JSON_ERROR_NOTJSON);
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

	/**
	 * 修改消息状态
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/updatemessage")
	public Result updateMessage(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String id = jsonObject.getString("id");
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
					int mess = jxPartnerMessagesService
							.findUpdatePartnerMessages(id);
					if (mess > 0) {
						return new Result(Errors.OK);
					} else {
						return new Result(Errors.JSON_ERROR_NOTJSON);
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

	/**
	 * 统计消息条数
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/numberofmessage")
	public Result numberOfMessage(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String username = null;
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(
						IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
					int mess = jxPartnerMessagesService
							.findMessagestotal(username);
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("number", mess);
					list.add(map);
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
	
	public static void main(String[] args) {
		TimerTask task = new TimerTask() {  
            @Override  
            public void run() {  
                System.out.println("Hello !!!");  
            }  
        };  
        Timer timer = new Timer();  
        long delay = 0;  
        long intevalPeriod = 10 * 60 * 1000;  
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);  
    }  
	
}
