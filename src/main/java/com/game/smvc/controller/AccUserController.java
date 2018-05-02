package com.game.smvc.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.bmanager.entity.JxPartner;
import com.game.bmanager.service.IJxPartnerService;
import com.game.entity.account.Domain;
import com.game.entity.account.Role;
import com.game.entity.account.User;
import com.game.services.account.AccountManager;
import com.game.services.account.DomainManager;
import com.game.smvc.core.config.CustomConfig;
import com.game.smvc.core.http.HttpSender;
import com.game.smvc.dao.WebDao;
import com.game.smvc.entity.JxAlipayAccount;
import com.game.smvc.entity.JxInformationSafety;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxPhCode;
import com.game.smvc.entity.JxPwd;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxAlipayAccountService;
import com.game.smvc.service.IJxInformationSafetyService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPhCodeService;
import com.game.smvc.service.IJxPwdService;
import com.game.smvc.service.IJxUserService;
import com.game.smvc.util.HttpUtil;
import com.game.smvc.util.IdentifyingUtil;
import com.game.smvc.util.Sha1Util;
import com.game.smvc.util.UserUtil;
import com.game.util.Des;

import net.sf.json.JSONObject;

@Controller
@RequestMapping({ "/smvc" })
public class AccUserController {

	@Autowired
	private WebDao webDao;
	@Autowired
	private IJxUserService accUserService;
	@Autowired
	private IJxPartnerService partnerService;
	@Autowired
	private IJxPhCodeService jxPhCodeService;
	@Autowired
	private IJxAlipayAccountService jxAlipayAccountService;
	@Autowired
	private IJxPwdService jxPwdService;
	@Autowired
	private AccountManager accountManager;
	@Autowired
	private IJxInformationSafetyService jxInformationSafetyService;
	@Autowired
	private IJxOrderService jxOrderService;
	@Autowired
	private DomainManager domainManager;
	private User user;

	private CustomConfig config = CustomConfig.getInstance();

	/**
	 * 合伙人登陆
	 * 
	 * @param login
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/toLogin")
	public Result login(HttpServletRequest request) {
		try {

			String encData = HttpUtil.getRquestParamsByIO(request);
			JSONObject json = JSONObject.fromObject(encData);
			String username = json.getString("username");
			String password = json.getString("password");

			List<Map<String, Object>> list = accUserService
					.findUserName(username);

			if (list == null || list.size() <= 0) {
				return new Result(Errors.NO_PARTNER);
			}
			Map<String, Object> map1 = list.get(0);
			String pwd = (String) map1.get("password");

			/*
			 * JxPwd jxPwd = jxPwdService
			 * .findUnique("from JxPwd where par_number = '" + username + "'");
			 */
			/*
			 * if (jxPwd == null) { if (!Sha1Util.getSha1(password).equals(pwd))
			 * { return new Result(Errors.USER_ERROR_PASSWORD); } } else {
			 * 
			 * if (!Sha1Util.getSha1(password).equals(jxPwd.getPassword())) {
			 * return new Result(Errors.USER_ERROR_PASSWORD); } }
			 */

			if (!Sha1Util.getSha1(password).equals(pwd)) {
				return new Result(Errors.USER_ERROR_PASSWORD);
			}

			String real_name = (String) map1.get("real_name");
			String phone = (String) map1.get("phone");

			JxPartner jxPartner = null;
			if (username.contains("A") || username.contains("B")
					|| username.contains("C")) {
				jxPartner = partnerService
						.findUnique("from JxPartner where par_other = '"
								+ username + "'");
			} else {
				jxPartner = partnerService
						.findUnique("from JxPartner where id = ?",
								Long.parseLong(username));
			}

			String ParParentid = jxPartner.getParParentid();
			String ParParentName = null;
			if (ParParentid == null || ParParentid.equals("") || ParParentid.length() == 0) {
				System.out.println("2");
				ParParentid = "无";
				ParParentName = "无";
			} else {
				String ParParentids = jxPartner.getParParentid();
				ParParentName = partnerService.findParParentName(ParParentids);
			}

			Map<String, Object> map = new HashMap<String, Object>();

			// 密码和支付宝 1为未绑定 /0 未已绑定 (1未初始密码 0 修改过后的密码)
			if (password.equals("123456")) {
				String passwords = "1";
				map.put("originalpassword", passwords);
			}else{
				String passwords = "0";
				map.put("originalpassword", passwords);
			}

			List<Map<String, Object>> l = jxAlipayAccountService
					.findAccount(username);
			if (l.size() <= 0 || l == null) {
				String AliaCcount = "1";
				map.put("unboundedalipay", AliaCcount);
			}else{
				String AliaCcount = "0";
				map.put("unboundedalipay", AliaCcount);
			}

			String userSafet = username + IdentifyingUtil.Identifying();
			System.out.println(userSafet);
			String safetyMark = Des.encryptDES(userSafet);

			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where user_name = '"
							+ username + "'");
			if (safety == null) {
				JxInformationSafety informationSafety = new JxInformationSafety();
				informationSafety.setUser_name(username);
				informationSafety.setSafety_mark(safetyMark);
				informationSafety.setAdd_time(new Date());
				jxInformationSafetyService.save(informationSafety);
			} else {
				safety.setSafety_mark(safetyMark);
				safety.setMod_time(new Date());
				jxInformationSafetyService.save(safety);
			}
			
			//查询用户直接装机台数three
			int numberOfInstalledIsers = jxOrderService.selectNumberOfInstalledIsers(username);
			
			int numberOfInstalledIsersThreeYears = jxOrderService.selectNumberOfInstalledIsersThreeYears(username);
			if(numberOfInstalledIsers >= 50 && numberOfInstalledIsersThreeYears >= 20){
				String level = jxPartner.getPAR_LEVEL();
				if(level.equals("1")){
					map.put("operator", "-1");// 运营商
				}else{
					map.put("operator", "0");// 运营商 0为达到运营商要求 -1是未达到
				}
			}else{
				map.put("operator", "-1");// 未达到运营商
			}
			int a = partnerService.updtaeNumber(numberOfInstalledIsers,numberOfInstalledIsersThreeYears,username);
			map.put("username", real_name);// 姓名
			map.put("partnerNumber", map1.get("username"));// 编号
			map.put("level", jxPartner.getPAR_LEVEL());// 级别
			map.put("ParParentName", ParParentName);// 上级合伙人姓名
			map.put("ParParentid", ParParentid);// 上级合伙人编号
			map.put("usernum", jxPartner.getPar_sellernum());// 销售台数
			map.put("safetyMark", safetyMark);// 安全标示
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			data.add(map);
			return new SecretResult(Errors.OK, data);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	
	/**
	 * 获取登入信息
	 * 
	 * @param login
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/logininformation")
	public Result loginInformation(HttpServletRequest request) {
		try {

			String encData = HttpUtil.getRquestParamsByIO(request);
			JSONObject json = JSONObject.fromObject(encData);
			String safetyMark = json.getString("safetyMark");
			JxInformationSafety safety = null;
			String username = null;
			safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					username = safety.getUser_name();
				} else {
					return new Result(Errors.SECURITY_VERIFICATION_FAILED);
				}
			} else {
				return new Result(Errors.SECURITY_VERIFICATION_FAILED);
			}

			List<Map<String, Object>> list = accUserService
					.findUserName(username);

			if (list == null || list.size() <= 0) {
				return new Result(Errors.NO_PARTNER);
			}
			Map<String, Object> map1 = list.get(0);
			String password = (String) map1.get("password");

			String real_name = (String) map1.get("real_name");

			JxPartner jxPartner = null;
			if (username.contains("A") || username.contains("B")
					|| username.contains("C")) {
				jxPartner = partnerService
						.findUnique("from JxPartner where par_other = '"
								+ username + "'");
			} else {
				jxPartner = partnerService
						.findUnique("from JxPartner where id = ?",
								Long.parseLong(username));
			}

			String ParParentid = jxPartner.getParParentid();
			String ParParentName = null;
			if (ParParentid == null || ParParentid.equals("") || ParParentid.length() == 0) {
				ParParentid = "无";
				ParParentName = "无";
			} else {
				String ParParentids = jxPartner.getParParentid();
				ParParentName = partnerService.findParParentName(ParParentids);
			}

			Map<String, Object> map = new HashMap<String, Object>();

			// 密码和支付宝 1为未绑定 /0 未已绑定 (1未初始密码 0 修改过后的密码)
			if (password.equals(Sha1Util.getSha1("123456"))) {
				String passwords = "1";
				map.put("originalpassword", passwords);
			}else{
				String passwords = "0";
				map.put("originalpassword", passwords);
			}

			List<Map<String, Object>> l = jxAlipayAccountService
					.findAccount(username);
			if (l.size() <= 0 || l == null) {
				String AliaCcount = "1";
				map.put("unboundedalipay", AliaCcount);
			}else{
				String AliaCcount = "0";
				map.put("unboundedalipay", AliaCcount);
			}
			

			//查询用户直接装机台数three
			int numberOfInstalledIsers = jxOrderService.selectNumberOfInstalledIsers(username);
			
			int numberOfInstalledIsersThreeYears = jxOrderService.selectNumberOfInstalledIsersThreeYears(username);
			if(numberOfInstalledIsers >= 50 && numberOfInstalledIsersThreeYears >= 20){
				String level = jxPartner.getPAR_LEVEL();
				if(level.equals("1")){
					map.put("operator", "-1");// 运营商
				}else{
					map.put("operator", "0");// 运营商 0为达到运营商要求 -1是未达到
				}
			}else{
				map.put("operator", "-1");// 未达到运营商
			}
			map.put("username", real_name);// 姓名
			map.put("partnerNumber", map1.get("username"));// 编号
			map.put("level", jxPartner.getPAR_LEVEL());// 级别
			map.put("ParParentName", ParParentName);// 上级合伙人姓名
			map.put("ParParentid", ParParentid);// 上级合伙人编号
			map.put("usernum", jxPartner.getPar_sellernum());// 销售台数
			map.put("safetyMark", safety.getSafety_mark());
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			data.add(map);
			return new SecretResult(Errors.OK, data);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 获取验证码
	 * 
	 * @param authCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/registerCode")
	public Result registerCode(HttpServletRequest request) {
		try {
			System.out.println("---开始获取验证码---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String username = jsonObject.getString("username");

			List<Map<String, Object>> list = accUserService
					.findUserName(username);
			if (list == null || list.size() <= 0) {
				return new Result(Errors.THIS_MACHINE_IS_NOT_A_PARTNER);
			}
			Map<String, Object> map1 = list.get(0);

			String phoneNum = (String) map1.get("phone");
			String type = jsonObject.getString("type");
			boolean findPwd = "1".equals(type);
			if ((phoneNum == null) || ("".equals(phoneNum.trim()))
					|| (phoneNum.length() < 6) || (phoneNum.length() > 11)) {
				return new Result(Errors.USER_ERROR_PHONE_FORMAT);
			}

			String codevalue = String.valueOf(Math.random()).substring(2, 8);
			List<Map<String, Object>> code = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", codevalue);
			map.put("phone", phoneNum);
			code.add(map);

			String content = (findPwd ? this.config.getSysProp("sms.content2")
					: this.config.getSysProp("sms.content")).replace("{code}",
					codevalue);
			String url = this.config.getSysProp("sms.url");
			String user = this.config.getSysProp("sms.user");
			String pwd = this.config.getSysProp("sms.pwd");
			String result = HttpSender.batchSend(url, user, pwd, phoneNum,
					content, true, null);
			result = StringUtils.isBlank(result) ? "1" : result.split("\n")[0]
					.split(",")[1];
			if (!"0".equals(result)) {
				return new Result(Errors.USER_ERROR_SMS);
			}
			JxPhCode jxcode = jxPhCodeService
					.findUnique(
							"from JxPhCode where phone_no=? and code_other=1",
							phoneNum);
			if (jxcode != null && jxcode.getId() != null) {
				jxcode.setCode_other("0");
				jxPhCodeService.save(jxcode);
			}
			jxcode = new JxPhCode();
			jxcode.setPhone_no(phoneNum);
			jxcode.setCode_no(codevalue);
			jxcode.setCode_addtime(new Date());
			jxcode.setCode_other("1");
			jxPhCodeService.save(jxcode);
			return new SecretResult(Errors.OK, code);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.USER_ERROR_SMS);
		}
	}

	/**
	 * 校验验证码
	 * 
	 * @param register
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/checkCode")
	public Result checkCode(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject jobj = JSONObject.fromObject(params);
			String username = jobj.getString("username");
			String code = jobj.getString("code");

			List<Map<String, Object>> list = accUserService
					.findUserName(username);

			if (list == null || list.size() <= 0) {
				return new Result(Errors.NO_PARTNER);
			}
			Map<String, Object> map1 = list.get(0);
			String phoneNum = (String) map1.get("phone");

			JxPhCode jpc = jxPhCodeService
					.findUnique(
							"from JxPhCode where phone_no=? and code_other=1",
							phoneNum);
			String trueCode = jpc.getCode_no();
			Date d = jpc.getCode_addtime();
			if (d.getTime() < (new Date().getTime() - 30 * 60 * 1000)) {
				return new Result(Errors.USER_ERROR_SMS_EXPIRE);
			} else if (!code.equals(trueCode)) {
				return new Result(Errors.USER_ERROR_CODE_WRONG);
			}
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.SERVER_DATA_SAVE_FAIL);
		}
	}

	/**
	 * 找回密码
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/modifyPwdBack")
	public Result modifyPwdBack(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject param = JSONObject.fromObject(params);
			String username = param.getString("username");// 编号
			String oldPwd = param.getString("oldPwd");
			String newPwd = param.getString("newPwd");
			// 得到合伙人
			User user = accountManager.getUserByName(username);
			if (!"qqqqxxxxpppp".equals(oldPwd)
					&& !user.getPassword().equals(Sha1Util.getSha1(oldPwd)))
				return new Result(Errors.USER_ERROR_OLD_PASSWORD);
			if (StringUtils.isBlank(newPwd))
				return new Result(Errors.EXCEPTION_UNKNOW);
			user.setPassword(Sha1Util.getSha1(newPwd));
			accountManager.save(user);
			return new Result(Errors.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 查看支付宝账号信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/checkalipayinformation")
	public Result checkAlipayInformation(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject param = JSONObject.fromObject(params);
			String safetyMark = param.getString("safetyMark");
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					List<Map<String, Object>> list = jxInformationSafetyService
							.findAccount(username);
					return new SecretResult(Errors.OK, list);

				} else {
					return new Result(Errors.SECURITY_VERIFICATION_FAILED);
				}
			} else {
				return new Result(Errors.SECURITY_VERIFICATION_FAILED);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}

	/**
	 * 绑定支付宝账号
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/bindingalipay")
	public Result bindingAlipay(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject param = JSONObject.fromObject(params);
			String pay_name = param.getString("pay_name");// 支付宝姓名
			String pay_account = param.getString("pay_account");// 支付宝账号
			String safetyMark = param.getString("safetyMark");

			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					List<Map<String, Object>> list = accUserService
							.findUserName(username);
					if (list == null || list.size() <= 0) {
						return new Result(Errors.NO_PARTNER);
					}
					Map<String, Object> map = list.get(0);
					String real_name = (String) map.get("real_name");
					JxAlipayAccount alipayAccount = jxAlipayAccountService
							.findUnique("from JxAlipayAccount where p_number = '"
									+ username + "'");
					if (alipayAccount == null) {
						JxAlipayAccount account = new JxAlipayAccount();
						account.setPay_name(pay_name);
						account.setPay_account(pay_account);
						account.setP_state(0);// 已绑定
						account.setP_number(username);
						account.setReal_name(real_name);
						account.setAdd_time(new Date());
						jxAlipayAccountService.save(account);
					}

					List<Map<String, Object>> date = new ArrayList<Map<String, Object>>();
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("pay_account", pay_account);
					m.put("pay_name", pay_name);
					date.add(m);
					return new SecretResult(Errors.OK, date);

				} else {
					return new Result(Errors.SECURITY_VERIFICATION_FAILED);
				}
			} else {
				return new Result(Errors.SECURITY_VERIFICATION_FAILED);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}

	/**
	 * 解绑支付宝账号
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/unbundlingaccount")
	public Result unbundlingAccount(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject param = JSONObject.fromObject(params);
			String safetyMark = param.getString("safetyMark");

			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					JxAlipayAccount alipayAccount = jxAlipayAccountService
							.findUnique("from JxAlipayAccount where p_number = '"
									+ username + "'");
					if (alipayAccount == null) {
						return new Result(Errors.UNBUNDLING_FAILURE);
					} else {
						int account = jxAlipayAccountService
								.deleteAccount(username);
					}
					return new Result(Errors.OK);

				} else {
					return new Result(Errors.SECURITY_VERIFICATION_FAILED);
				}
			} else {
				return new Result(Errors.SECURITY_VERIFICATION_FAILED);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}

	
	
	/**
	 * 合伙人注册
	 * 2018-04-17
	 * @param login
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/registereds")
	public Result registereds(HttpServletRequest request) {
		try {
			System.out.println("合伙人注册");
			String encData = HttpUtil.getRquestParamsByIO(request);
			System.out.println("1");
			JSONObject json = JSONObject.fromObject(encData);
			System.out.println("2");
			System.out.println("3");
			/*String password = json.getString("password");//密码
			String confirmPassword = json.getString("confirmPassword");//确认密码
*/			String recommenderCode = json.getString("RecommenderCode");//推荐人代码
			String ordNo = json.getString("ord_no");//订单号码
			String nameReferee = json.getString("NameReferee");//推荐人姓名
			String nameApplicant = json.getString("nameApplicant");//申请人姓名
			String cardNumber = json.getString("cardNumber");//身份证号码
			String mobilePhone = json.getString("mobilePhone");//手机号码
			String homeAddress = json.getString("homeAddress");//家庭住址
			String s_province = json.getString("s_province");//省
			String s_city = json.getString("s_city");//市
			String s_county = json.getString("s_county");//区县
			String par_level = null;//级别
			//推荐人姓名是否存在
			JxPartner jxPartner = partnerService.findUnique("from JxPartner where id = '"+recommenderCode+"'");
			if(jxPartner == null){
				return new Result(Errors.THE_RECOMMENDER_CODE_DOES_NOT_EXIST);
			}
			System.out.println(jxPartner.getPAR_NAME());
			System.out.println(nameReferee);
			if(!jxPartner.getPAR_NAME().equals(nameReferee)){
				return new Result(Errors.REFERENCES_DO_NOT_EXIST);
			}
			//判断创客身份证是否存在
			List<Map<String,Object>> list = partnerService.findSFZ();
			for(int i = 0;i<list.size();i++){
				Map<String,Object> map = list.get(i);
				String sfz = (String) map.get("card_number");
				String orderNo = (String) map.get("order_no");
				if(sfz != null){
					//判断身份证是否存在
					if(sfz.equals(cardNumber)){
						return new Result(Errors.USER_ID_CARD_EXISTS);
					}
				}
				//判断订单是否重复
				if(orderNo != null){					
					if(ordNo.equals(orderNo)){
						return new Result(Errors.THE_ORDER_NUMBER_IS_REGISTERED);
					}
				}	
			}
			
			//判断订单是否为3年的订单
			if(recommenderCode == null || "".equals(recommenderCode)){
				par_level = "2";//级别
			}else{
				par_level = "2";//级别
			}
			JxOrder jxOrder = jxOrderService.findUnique("from jx_order where ord_no = '"+ordNo+"'");
			if(jxOrder.getOrd_multiple() < 3){
				return new Result(Errors.THE_ARE_NOT_SATISFIED_WITH_THE_REGISTRATION_CONDITION);
			}
			//判断推荐人编码是否存在
			if(!jxOrder.getOrd_managerno().equals(recommenderCode)){
				return new Result(Errors.THE_RECOMMENDER_CODE_DOES_NOT_EXIST);
			}
			
			//生产创客代码
			String citycode = partnerService.findCityCodeByCity(par_level,s_province,s_city,s_county);
			System.out.println("citycodecitycode:"+citycode);
            DecimalFormat df = new DecimalFormat("00000");
            String num = df.format(partnerService.countNum());
            System.out.println("num:"+num);
            
            //根据手机号码查询用户是否注册
            JxPartner partner = partnerService.findUnique("from JxPartner where PAR_PHONE = '"+mobilePhone+"'");
            if(partner == null){
            	System.out.println(Long.valueOf(citycode+num));
            	JxPartner partner2 = new JxPartner();
            	partner2.setPAR_LEVEL(par_level);
            	partner2.setId(Long.valueOf(citycode+num));
            	partner2.setPAR_NAME(nameApplicant);
            	partner2.setPAR_ADDRESS(homeAddress);
            	partner2.setParParentid(recommenderCode);
            	partner2.setOrder_no(ordNo);
            	partner2.setPAR_PHONE(mobilePhone);
            	partner2.setPAR_AREA(s_province+"-"+s_city+"-"+s_county);
            	partner2.setPAR_PARENT(nameReferee);
            	partner2.setCard_number(cardNumber);
            	User user = new User();
                user.setUsername(citycode+num);
                user.setRealName(nameApplicant);
                user.setPassword("123456");
                user.setPhone(mobilePhone);
                user.setEmail(citycode+num+"@email.com");
                user.setCustomId(cardNumber);
                user.setCreateBy("admin");
                user.setEnabled(true);
                user.setAccountExpired(false);
                user.setAccountLocked(false);
                user.setCredentialsExpired(false);
                user.setCreateTime(new Date());
                Domain domain = domainManager.getDomain(1l);
                user.setDomain(domain); 
                List<Role> rolelist = accountManager.getAllRole();
                List<Role> useRoles = new ArrayList<Role>();
                for(Role role : rolelist){
                    if(role.getName().equals("合伙人")){
                        useRoles.add(role);
                    }
                }
                System.out.println("6666");
                user.setUseRoles(useRoles);
                accountManager.saveAndUpdateChild(user);
                partnerService.save(partner2);
                System.out.println(partner2);
                return new SecretResult(Errors.OK, partner2);
            }else{
            	//用户已注册，请直接登入
            	return new Result(Errors.THE_USER_IS_REGISTERED_PLEASE_LOGIN_DIRECTLY);
            }
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.JSON_ERROR_NOTJSON);
		}
	}
	
}
