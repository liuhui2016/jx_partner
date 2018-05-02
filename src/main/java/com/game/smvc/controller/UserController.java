package com.game.smvc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.bmanager.entity.JxFilterWarning;
import com.game.bmanager.entity.JxShare;
import com.game.bmanager.service.IJxFilterLifeService;
import com.game.bmanager.service.IJxFilterWarningService;
import com.game.bmanager.service.IJxShareService;
import com.game.smvc.core.config.CustomConfig;
import com.game.smvc.core.http.HttpSender;
import com.game.smvc.dao.WebDao;
import com.game.smvc.entity.JxAfterSales;
import com.game.smvc.entity.JxMessages;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxPay;
import com.game.smvc.entity.JxPhCode;
import com.game.smvc.entity.JxProduct;
import com.game.smvc.entity.JxProflt;
import com.game.smvc.entity.JxStatistical;
import com.game.smvc.entity.JxUser;
import com.game.smvc.entity.result.DataResult;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxAfterSalesService;
import com.game.smvc.service.IJxMessageService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPayWayService;
import com.game.smvc.service.IJxPhCodeService;
import com.game.smvc.service.IJxProductService;
import com.game.smvc.service.IJxProfltService;
import com.game.smvc.service.IJxStatisticalService;
import com.game.smvc.service.IJxUserService;
import com.game.smvc.util.HttpUtil;
import com.game.smvc.util.UserUtil;

@Controller
@RequestMapping({ "/smvc" })
public class UserController {
	@Autowired
	private WebDao webDao;
	@Autowired
	private IJxUserService jxUserService;
	@Autowired
	private IJxPhCodeService jxPhCodeService;
	@Autowired
	private IJxOrderService jxOrderService;
	@Autowired
	private IJxShareService jxShareService;
	@Autowired
	private IJxProductService jxProductService;
	@Autowired
	private IJxPayWayService payWayService;
	@Autowired
    private IJxFilterLifeService jxFilterLifeService;
	@Autowired
	private IJxFilterWarningService filterWarningService;
	@Autowired
	private IJxProfltService profltService;
	@Autowired
	private IJxMessageService messageService;
	@Autowired
	private IJxStatisticalService jxStatisticalService;
	@Autowired
	private IJxAfterSalesService jxAfterSalesService;
	

	private CustomConfig config = CustomConfig.getInstance();
	
	/**
	 * 获取验证码
	 * 
	 * @param authCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/registerCode")
	public Result registerCode(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String phoneNum = jsonObject.getString("phoneNum");
			String type = jsonObject.getString("type");
			boolean findPwd = "1".equals(type);
			if ((phoneNum == null) || ("".equals(phoneNum.trim()))
					|| (phoneNum.length() < 6) || (phoneNum.length() > 11)) {
				return new Result(Errors.USER_ERROR_PHONE_FORMAT);
			}
			Integer count = this.jxUserService.findByPhone(phoneNum);
			if (count.intValue() > 0 && !findPwd) {
				return new Result(Errors.USER_ERROR_PHONE_EXIST);
			} else if (count.intValue() == 0 && findPwd) {
				return new Result(Errors.USER_ERROR_NOT_EXIST);
			}
			String codevalue = String.valueOf(Math.random()).substring(2, 8);
			List<Map<String, Object>> code = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", codevalue);
			code.add(map);
		
			String content = (findPwd ? this.config.getSysProp("sms.content2")
					: this.config.getSysProp("sms.content")).replace("{code}",
					codevalue);
			String url = this.config.getSysProp("sms.url");
			String user = this.config.getSysProp("sms.user");
			String pwd = this.config.getSysProp("sms.pwd");
			String result = HttpSender.batchSend(url, user, pwd, phoneNum,
					content, true, null);
			System.out.println("错误码:"+result);
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
	@RequestMapping(value = "/user/test/checkCode", method = RequestMethod.POST)
	public Result checkCode(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject jobj = JSONObject.fromObject(params);
			String phoneNum = jobj.getString("phoneNum");
			String code = jobj.getString("code");
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
	 * 用户注册
	 * 
	 * @param register
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/register", method = RequestMethod.POST)
	public Result register(HttpServletRequest request) {
		try {
			String register = HttpUtil.getRquestParamsByIO(request);
			if (StringUtils.isBlank(register)) {
				return new Result(Errors.USER_ERROR_REGISTER);
			}
			JSONObject json = JSONObject.fromObject(register);
			String phoneNum = json.getString("phoneNum");
			String password = json.getString("password");
			JxUser user = new JxUser();
			user.setU_status(0);
			user.setU_phone(phoneNum);
			user.setU_pwd(UserUtil.md5(password));
			user.setU_snname("");
			user.setU_picurl("");
			user.setU_sex(Integer.valueOf(0));
			user.setU_addtime(new Date());
			user = jxUserService.save(user);
			// 创建新用户时，需要加入一个家庭组
			// 为家庭组创建id
			String id = new Date().getTime()
					+ String.valueOf(Math.random()).substring(2, 8);
			jxUserService.createFamily(id, user.getU_id().toString());
			jxUserService.addFamilyMembersByFamilyID(id, user.getU_id()
					.toString());
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.SERVER_DATA_SAVE_FAIL);
		}
	}

	/**
	 * 用户登陆
	 * 
	 * @param login
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/login", method = RequestMethod.POST)
	public Result login(HttpServletRequest request) {
		try {

			String encData = HttpUtil.getRquestParamsByIO(request);
			JSONObject json = JSONObject.fromObject(encData);
			String phoneNum = json.getString("phoneNum");
			String password = json.getString("password");

			JxUser user = this.jxUserService.findUserByPhoneNum(phoneNum);
			if (user == null) {
				return new Result(Errors.MOBILE_ERROR_NOT_EXIST);
			}
			if (!UserUtil.md5(password).equals(user.getU_pwd())) {
				return new Result(Errors.USER_ERROR_PASSWORD);
			}
			if (user.getU_status() == 1) {
				return new Result(Errors.USER_ERROR_FREEZE);
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", user.getU_id());
			map.put("nickname", user.getU_snname());// 昵称
			map.put("userImg", user.getU_picurl());// 头像
			map.put("sex", user.getU_sex());// 性别
			map.put("sign", user.getU_txttail());// 签名
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			data.add(map);
			return new SecretResult(Errors.OK, data);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 刷新个人信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/refreshInfo", method = RequestMethod.POST)
	public Result refreshInfo(HttpServletRequest request) {
		try {
			String encData = HttpUtil.getRquestParamsByIO(request);
			JSONObject json = JSONObject.fromObject(encData);
			String userid = json.getString("userid");
			JxUser user = this.jxUserService.get(Long.valueOf(userid));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userid", user.getU_id());
			map.put("nickname", user.getU_snname());// 昵称
			map.put("userImg", user.getU_picurl());// 头像
			map.put("sex", user.getU_sex());// 性别
			map.put("sign", user.getU_txttail());// 签名
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			data.add(map);
			return new SecretResult(Errors.OK, data);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 用户退出
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/loginOut", method = RequestMethod.POST)
	public Result loginOut(HttpServletRequest request) {
		try {
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/modifyPwd")
	public Result modifyPwd(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject param = JSONObject.fromObject(params);
			String phoneNum = param.getString("phoneNum");
			String oldPwd = param.getString("oldPwd");
			String newPwd = param.getString("newPwd");
			JxUser user = jxUserService.findUserByPhoneNum(phoneNum);
			if (null == user)
				return new Result(Errors.USER_NOT＿LOGIN);
			// 旧密码输入错误
			if (!"qqqqxxxxpppp".equals(oldPwd)
					&& !user.getU_pwd().equals(UserUtil.md5(oldPwd)))
				return new Result(Errors.USER_ERROR_OLD_PASSWORD);
			if (StringUtils.isBlank(newPwd))
				return new Result(Errors.EXCEPTION_UNKNOW);
			user.setU_pwd(UserUtil.md5(newPwd));
			jxUserService.save(user);
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 修改昵称
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/modifyNickName")
	public Result modifyInfo(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject param = JSONObject.fromObject(params);
			String userid = param.getString("userid");
			String value = param.getString("value");
			JxUser user = jxUserService.get(Long.valueOf(userid));
			if (null == user)
				return new Result(Errors.USER_NOT＿LOGIN);
			// 就密码输入错误
			user.setU_snname(value);
			jxUserService.save(user);
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 修改头像
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/modifyHead")
	public Result modifyHead(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject param = JSONObject.fromObject(params);
			String userid = param.getString("userid");
			String value = param.getString("value");
			JxUser user = jxUserService.get(Long.valueOf(userid));
			if (null == user)
				return new Result(Errors.USER_NOT＿LOGIN);
			// 就密码输入错误
			user.setU_picurl(value);
			jxUserService.save(user);
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 修改性别
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/modifySex")
	public Result modifySex(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject param = JSONObject.fromObject(params);
			String userid = param.getString("userid");
			String value = param.getString("value");
			JxUser user = jxUserService.get(Long.valueOf(userid));
			if (null == user)
				return new Result(Errors.USER_NOT＿LOGIN);
			// 就密码输入错误
			user.setU_sex(Integer.valueOf(value));
			jxUserService.save(user);
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 修改昵称
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/modifySign")
	public Result modifySign(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject param = JSONObject.fromObject(params);
			String userid = param.getString("userid");
			String value = param.getString("value");
			JxUser user = jxUserService.get(Long.valueOf(userid));
			if (null == user)
				return new Result(Errors.USER_NOT＿LOGIN);
			// 就密码输入错误
			user.setU_txttail(value);
			jxUserService.save(user);
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 平板绑定
	 * 
	 * @param params
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/user/test/tabletbinding")
	public Result tabletBinding(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject j = JSONObject.fromObject(params);
			String orderno = j.getString("orderno");
			JxOrder jxOrder = jxOrderService.findUnique(
					"from jx_order where ord_no=? and ord_status=1", orderno);
			// from Jx_Order where ord_no = ? and ord_status=2
			if (jxOrder == null || jxOrder.getOrd_id() == null) {
				return new Result(Errors.USER_ERROR_WRONGORDERNO);
			}
			//得到倍数
			int ppdnum = jxOrder.getOrd_multiple();
			System.out.println("倍数:"+ppdnum);
			String unique = UUID.randomUUID().toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			int paytypeid = jxOrder.getOrd_protypeid();// 产品商品名称，0=包年；1=按流量付费
			map.put("pro_no", unique);
			if (paytypeid == 0) {
			    map.put("pay_proid", 1);//单片机设置与数据库相反,1表示包年
				Calendar calendar = Calendar.getInstance();
				//map.put("quantity", calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
				map.put("quantity", ((calendar.getActualMaximum(Calendar.DAY_OF_YEAR))*ppdnum));
				//calendar.add(Calendar.YEAR,1);
				calendar.add(Calendar.YEAR,(1*ppdnum));
				// 默认包一年,如果有多的,从订单获取
				map.put("now", sdf.format(new Date()));
				Date date = calendar.getTime();
				map.put("end", sdf.format(date));
				
			} else {
				// 如果是包流量,查询初始流量值
			    map.put("pay_proid", 0);//单片机设置与数据库相反,0表示购买流量
				JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?",Long.valueOf(jxOrder.getPro_id()), Long.valueOf(jxOrder.getOrd_protypeid()));
				
				Float flow = jxPay.getPay_flow();
				Float flows = flow * ppdnum;
				//map.put("quantity", jxPay.getPay_flow());
				map.put("quantity", flows);
			}
			
			list.add(map);
			return new SecretResult(Errors.OK, list);
		} catch (EmptyResultDataAccessException e) {
			return new Result(Errors.USER_ERROR_NOT_EXIST);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	/**
     * 平板绑定 回调
     * 
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/test/tabletbindingback")
    public Result tabletBindingBack(HttpServletRequest request) {
        try {
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject j = JSONObject.fromObject(params);
            String orderno = j.getString("orderno");
            String prono = j.getString("prono");
            String status = j.getString("status");
            if(status.equals("0")){
                //平板写入单片机成功
                JxOrder jxOrder = jxOrderService.findUnique(
                        "from jx_order where ord_no=? and ord_status=1", orderno);
                // from Jx_Order where ord_no = ? and ord_status=2
                if (jxOrder == null || jxOrder.getOrd_id() == null) {
                    return new Result(Errors.USER_ERROR_WRONGORDERNO);
                }
                //得到倍数
    			int ppdnum = jxOrder.getOrd_multiple();
                JxProduct jxproduct = new JxProduct();
                String userid = jxOrder.getU_id().toString();
                String familyId = jxUserService.findFamilyIdByUserId(userid);
                jxproduct.setPro_color(jxOrder.getOrd_color());
                //jxproduct.setPro_name(this.jxOrderService.findProNameById(jxOrder.getPro_id()));
                jxproduct.setPro_name(this.jxOrderService.findProNameByIds(jxOrder.getPro_id()));
                jxproduct.setPro_no(prono);
                jxproduct.setFam_id(familyId);
                jxproduct.setPh_no(jxOrder.getOrd_phone());
                jxproduct.setPro_addtime(new Date());
                jxproduct.setPro_image(jxOrder.getOrd_imgurl());
                jxproduct.setU_id(jxOrder.getU_id());
        		float a=0;
                jxproduct.setPro_hasflow(a);
                //Long pro_id = jxOrder.getPro_id();
                int pro_id = jxOrder.getPro_id();
                jxproduct.setPro_category(Integer.parseInt(String.valueOf(pro_id)));
                jxOrder.setPro_no(prono);
                jxOrder.setOrd_status(3);
                jxOrder.setOrd_modtime(new Date());
                int paytypeid = jxOrder.getOrd_protypeid();// 产品商品名称，0=包年；1=按流量付费
                if (paytypeid == 0) {
                    Calendar calendar = Calendar.getInstance();
                    //calendar.add(Calendar.YEAR, 1);
                    calendar.add(Calendar.YEAR, (1*ppdnum));
                    // 默认包一年,如果有多的,从订单获取
                    Date date = calendar.getTime();
                    jxproduct.setPro_invalidtime(date);
                    
                } else {
                    // 如果是包流量,查询初始流量值
                    JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?",Long.valueOf(jxOrder.getPro_id()), Long.valueOf(jxOrder.getOrd_protypeid()));
                    Float flow = jxPay.getPay_flow();
                    Float flows = flow *ppdnum;
                    //jxproduct.setPro_restflow(jxPay.getPay_flow());
                    jxproduct.setPro_restflow(flows);
                }
                jxproduct = jxProductService.save(jxproduct);
                
                //添加滤芯状态
                JxProflt jp=new JxProflt(500,800,2000,2000,2000);
                
                jp.setPrf_addtime(new Date());
                jp.setPro_no(prono);
                profltService.save(jp);
                //产品绑定时,需要在家庭组里添加记录
                jxUserService.addFamilyProductByFamilyID(familyId, jxproduct.getPro_id().toString());
                
                jxOrderService.save(jxOrder);
                //平板绑定完向消息中心中插入消息
                String str="亲~您购买的"+jxproduct.getPro_name()+"("+jxOrder.getOrd_color()+")";
                String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
                str=str+str1+" "+jxOrder.getOrd_price()+"元已经绑定完成了";
                //平板绑定完向消息中心中插入消息
                String title = "净水机绑定消息";
                JxMessages mess = PushController.BDMssages(jxOrder.getOrd_no(),title, str,jxproduct.getU_id());
                messageService.save(mess);
                //消息推送
                String alias = String.valueOf(jxproduct.getU_id());
                PushController.YHIOSMssage(alias, title, str);
                
            }else{
                //平板写入单片机失败,平板不会回调
            }
            return new Result(Errors.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
	

    
    /**
     * 平板获取续费订单信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/test/tabletquerystatus")
    public Result tabletQueryStatusa(HttpServletRequest request) {
        try {
        	System.out.println("---平板续费开始---");
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject j = JSONObject.fromObject(params);
            String prono = j.getString("prono");
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String, Object>> list1 = jxOrderService.selectOrdernoByPronos(prono);
            if(list1.size()<=0){
            	return new Result(Errors.NOT_RENEW_THE_ORDER);
            }
            Map<String, Object> m = list1.get(0);
            Long ord_protypeid = (Long) m.get("ord_protypeid");
            Integer ordprotypeid=Integer.parseInt(ord_protypeid.toString());
            String order_no = (String) m.get("ord_no");
            Long proid = (Long) m.get("pro_id");
            int ppdnum = (Integer) m.get("ord_multiple");
            // 产品商品名称，0=包年包流量；1=按流量付费
            int paytypeid =ordprotypeid;
            map.put("pro_no", prono);
            map.put("order_no", order_no);
            if (paytypeid == 0) {
                map.put("pay_proid", 1);//单片机设置与数据库相反,1表示包年
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
                Calendar calendar = Calendar.getInstance();
				map.put("day", calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
				calendar.add(Calendar.YEAR,(1 * ppdnum));
				// 默认包一年,如果有多的,从订单获取
				map.put("now", sdf.format(new Date()));
				Date date = calendar.getTime();
				map.put("end", sdf.format(date));
            } else {
                // 如果是包流量,查询初始流量值
                map.put("pay_proid", 0);//单片机设置与数据库相反,0表示购买流量
                JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?", proid,Long.valueOf(ord_protypeid));
                Float flow = jxPay.getPay_flow();
                Float flows = flow * ppdnum;
                map.put("quantity", flows);
            }
            list.add(map);
            System.out.println("----"+list);
            return new SecretResult(Errors.OK, list);
        } catch (EmptyResultDataAccessException e) {
            return new Result(Errors.USER_ERROR_NOT_EXIST);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    
    /**
     * 平板续费回调
     */
    @ResponseBody
    @RequestMapping(value = "/user/test/tabletquerystatusback")
    public Result tabletQueryStatusBack(HttpServletRequest request) {
        try {
        	System.out.println("---平板续费回调开始---");
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject j = JSONObject.fromObject(params);
            String prono = j.getString("prono");
            String orderno = j.getString("orderno");
            String newOrderno = j.getString("newOrderno");
            JxOrder jxOrder = jxOrderService.findUnique(
                    "from jx_order where ord_no=? and pro_no = ?", orderno,prono);
            //判断是否有续费信息
            if (jxOrder == null || jxOrder.getOrd_id() == null) {
				return new Result(Errors.USER_ERROR_NOT_ORDER);
			}
            JxOrder jxOrder1 = jxOrderService.findUnique("from jx_order where ord_no=? and pro_no = ?", newOrderno,prono);
            int ppdnum = jxOrder1.getOrd_multiple();
            System.out.println("---开始存商品表数据---");
            JxProduct jxProduct = jxProductService.findUnique("from JxProduct where pro_no = ?", prono);
            int paytypeid = jxOrder.getOrd_protypeid();// 产品商品名称，0=包年；1=按流量付费
            if (paytypeid == 0) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, (1*ppdnum));
                // 默认包一年,如果有多的,从订单获取
                Date date = calendar.getTime();
                jxProduct.setPro_invalidtime(date);
                jxOrder1.setPro_day((365 * ppdnum));
                System.out.println("---失效时间---"+date);
            } else {
            	System.out.println("初始化流量");
                // 如果是包流量,查询初始流量值
                JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?",Long.valueOf(jxOrder.getPro_id()), Long.valueOf(jxOrder.getOrd_protypeid()));
                Float flow = jxPay.getPay_flow();
                Float flows = flow * ppdnum;
                jxProduct.setPro_restflow(flows);
              
                String restflow = flows+"";
                jxOrder1.setPro_restflow(restflow);
                System.out.println("初始化流量结束");
            }
          /*  Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 1);
            // 默认包一年,如果有多的,从订单获取
            Date date = calendar.getTime();
            jxProduct.setPro_invalidtime(date);*/
            jxProductService.save(jxProduct);
            System.out.println("---商品表数据保存结束---");
            jxOrder.setOrd_status(5);
            jxOrderService.save(jxOrder);
            
            jxOrder1.setOrd_status(3);
            jxOrderService.save(jxOrder1);
            return new Result(Errors.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    
    
    /**
     * 平板获取滤芯使用寿命,单位小时
     * 
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/test/tabletquerylife")
    public Result tabletQueryLife(HttpServletRequest request) {
        try {
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject j = JSONObject.fromObject(params);
            String code = j.getString("code");
            List<Map<String, Object>> list = jxFilterLifeService.queryFilterLifeByProvince(code);
             return new SecretResult(Errors.OK, list);
        } catch (EmptyResultDataAccessException e) {
            return new Result(Errors.USER_ERROR_NOT_EXIST);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    
    /**
     * 净水器 更换滤芯
     * 
     * @param params
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/user/test/filterchange")
    public Result filterChange(HttpServletRequest request) {
        try {
        	System.out.println("------开始更换滤芯------");
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject j = JSONObject.fromObject(params);
            String filterno = j.getString("filterno");//cto
            String orderno = j.getString("orderno");
            String code = j.getString("code");
            System.out.println("---222---");
            JxFilterWarning fw = filterWarningService.getWarning(orderno,filterno);
            System.out.println("10101");
            System.out.println(filterno);
            System.out.println("---333----");
            
            if(fw == null){
            	 return new Result(Errors.FILTER_TEMPORARILY_WITHOUT_REPLACEMENT);
            }else{
            	if(fw.getFilter_name().contains(filterno)){
            		System.out.println("---444----");
            		fw.setStatus(1);
            		System.out.println("---555---");
            		filterWarningService.save(fw);
            		System.out.println("---666---");
            		List<Map<String, Object>> list = jxFilterLifeService.queryFilterLifeByProvince(code);
            		System.out.println("-----"+list);
            		return new SecretResult(Errors.OK, list);
            	}else{
            		System.out.println("----00---");
            		return new Result(Errors.PRODUCT_ERROR_NO);
            	}
            }
            
        } catch (Exception e) {
        	System.out.println("---11---");
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    
	/**
	 * 获取地址列表
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/getAddress")
	public Result getAddress(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject j = JSONObject.fromObject(params);
			String userid = j.getString("userid");
			String isdefault = j.get("isdefault") == null ? null : j
					.getString("isdefault");
			List<Map<String, Object>> addrList = jxUserService
					.getAddressByUserID(userid, isdefault);
			return new SecretResult(Errors.OK, addrList);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 修改地址
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/modifyAddress")
	public Result modifyAddress(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject j = JSONObject.fromObject(params);
			String id = j.get("id") == null ? null : j.getString("id");
			String userid = j.getString("userid") == null ? "" : j
					.getString("userid");
			String name = j.getString("name") == null ? "" : j
					.getString("name");
			String phone = j.getString("phone") == null ? "" : j
					.getString("phone");
			String area = j.getString("area") == null ? "" : j
					.getString("area");
			String detail = j.getString("detail") == null ? "" : j
					.getString("detail");
			// String code = j.getString("code")==null?"":j.getString("code");
			String isdefault = j.getString("isdefault") == null ? "" : j
					.getString("isdefault");
			if (StringUtils.isBlank(id) || "null".equals(id)) {
				// 新怎
				jxUserService.saveAddress(userid, name, phone, area, detail,
						isdefault);
			} else {
				jxUserService.updateAddress(id, userid, name, phone, area,
						detail, isdefault);
			}
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 修改地址
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/deleteAddress")
	public Result deleteAddress(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject j = JSONObject.fromObject(params);
			String id = j.getString("id");
			jxUserService.deleteAddress(id);
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	
	
	/**
	 * 上传滤芯状态
	 * 2017/07/12
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/lxstatusupload", method = RequestMethod.POST)
	public Result lxStatusUploads(HttpServletRequest request) {
		try {
			System.out.println("------开始上传滤芯状态------");
			String param = HttpUtil.getRquestParamsByIO(request);
			JSONObject jObj = JSONObject.fromObject(param);
			System.out.println("----开始上传滤芯寿命----");
			String pro_no = jObj.getString("pro_id");
			String ordno = jObj.getString("ordno");
			String code = jObj.getString("code");
			Integer pp = jObj.getInt("pp");
			Integer cto = jObj.getInt("cto");
			Integer ro = jObj.getInt("ro");
			Integer t33 = jObj.getInt("t33");
			Integer wfr = jObj.getInt("wfr");
			int day = 0;//剩余天数
			String output_water = null;//制水量
			if(jObj.containsKey("surplus_day")){
				System.out.println("有剩余天数");
				day = jObj.getInt("surplus_day");
			}
			
			if(jObj.containsKey("output_water")){
				System.out.println("有制水量");
				output_water = jObj.getString("output_water");//制水量
			}
			//JSONObject jo = JSONObject.fromObject("{surplus_day:'',output_water:\"''\"}");
		
			
			//上传出水量(得到出水量)剩余水量
			String restflow = jObj.getString("restflow");
			int tds = jObj.getInt("tds");//tds信息
			int temperature = jObj.getInt("temperature");
			System.out.println("tds--->"+tds);
			System.out.println("temperature--->"+temperature);
			System.out.println("000");
			JxProflt filter = profltService.findJxProfltByProNO(pro_no);
			System.out.println("111");
			
			if(filter.getPrf_id()==null){
				System.out.println("444");
			    filter.setPrf_addtime(new Date());
			}
			System.out.println("555");
			filter.setPro_no(pro_no);
			filter.setPrf_modtime(new Date());
			
			filter.setPrf_code(code);
			filter.setPrf_pp(pp);
			filter.setPrf_cto(cto);
			filter.setPrf_ro(ro);
			filter.setPrf_t33(t33);
			filter.setPrf_wfr(wfr);
			System.out.println("666");
			
			//获取系统当前时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		    String time = df.format(new Date());
		    System.out.println("当前时间:"+time);
		    
			//设置出水量
			JxOrder jxOrder = jxOrderService.findUnique("from jx_order where pro_no = ? and ord_no = ?", pro_no,ordno);
			jxOrder.setPro_restflow(restflow);
			jxOrder.setPro_day(day);//设置天数
			jxOrder.setOrd_modtime(new Date());
			int paytypeid = jxOrder.getOrd_protypeid();// 产品商品名称，0=包年；1=按流量付费
			if(paytypeid == 0){
				
			}
			System.out.println("----->新表开始");
			JxStatistical jxStatistical = jxStatisticalService.selectJxStatisticalByOrderNo(ordno);
			
			if(jxStatistical.getSta_id()==0){
				System.out.println("---没有信息，直接插入新的数据---");
				jxStatistical.setU_id(Integer.parseInt(jxOrder.getU_id().toString()));
				jxStatistical.setOrd_no(ordno);
				jxStatistical.setPro_no(pro_no);
				jxStatistical.setSta_tds(tds);
				jxStatistical.setSta_temperature(temperature);
				jxStatistical.setPro_restflow(restflow);
				jxStatistical.setOutput_water(output_water);
				jxStatistical.setSta_addtime(new Date());
				jxStatisticalService.save(jxStatistical);
				System.out.println("---->保存新表结束");
			}else{
				
				//得到更新和添加时间
				String modtime = jxStatistical.getSta_modtime()+"";
				String addtime = jxStatistical.getSta_addtime()+"";
				System.out.println("---开始更新数据---");
				if(addtime.contains(time)){
					//同一天的数据更新
					System.out.println("---开始更新表数据(添加时间)---");
					jxStatistical.setSta_modtime(new Date());
					jxStatistical.setSta_tds(tds);
					jxStatistical.setSta_temperature(temperature);
					jxStatistical.setPro_restflow(restflow);
					jxStatistical.setOutput_water(output_water);
					//jxStatisticalService.update(tds, temperature, restflow, ordno);
					jxStatisticalService.save(jxStatistical);
					System.out.println("---结束更新数据---");
				}else if(modtime.contains(time)){
					//同一天的数据更新
					System.out.println("---开始更新表数据---");
					jxStatistical.setSta_modtime(new Date());
					jxStatistical.setSta_tds(tds);
					jxStatistical.setSta_temperature(temperature);
					jxStatistical.setPro_restflow(restflow);
					jxStatistical.setOutput_water(output_water);
					//jxStatisticalService.update(tds, temperature, restflow, ordno);
					jxStatisticalService.save(jxStatistical);
					System.out.println("---结束更新数据---");
				}else{
					//插入新的数据
					System.out.println("---开始更新(插入)表数据---");
					JxStatistical statistical = new JxStatistical();
					statistical.setOrd_no(jxStatistical.getOrd_no());
					statistical.setPro_no(jxStatistical.getPro_no());
					statistical.setSta_addtime(jxStatistical.getSta_addtime());
					statistical.setU_id(jxStatistical.getU_id());
					statistical.setSta_modtime(new Date());
					statistical.setSta_tds(tds);
					statistical.setSta_temperature(temperature);
					statistical.setPro_restflow(restflow);
					statistical.setOutput_water(output_water);
					jxStatisticalService.save(statistical);
					System.out.println("---更新(插入)表数据结束---");
				}

			}
			System.out.println("---->保存新表结束2");
			
			System.out.println("777");
			//判断滤芯寿命
			if(pp<=5){
				System.out.println("888");
			    dealFilterWaring("pp",pp,pro_no);
			}
			if(cto<=5){
				System.out.println(999);
			    dealFilterWaring("cto",cto,pro_no);
            }
			if(ro<=5){
				System.out.println("101");
			    dealFilterWaring("ro",ro,pro_no);
            }
			if(t33<=5){
				System.out.println("102");
			    dealFilterWaring("t33",t33,pro_no);
            }
			if(wfr<=5){
				System.out.println("103");
			    dealFilterWaring("wfr",wfr,pro_no);
            }
			System.out.println("104");
			profltService.save(filter);
			jxOrderService.save(jxOrder);
			System.out.println("105");
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	private void dealFilterWaring(String name,Integer timeleft,String pro_no){
		System.out.println("106");
        //小于5表示警报,使用寿命快到期,往jx_filter_warning插入记录
        JxFilterWarning fw = filterWarningService.findByProNoAndFilter(pro_no,name+"滤芯");
        System.out.println("107");
        if(fw==null||fw.getId()==null){
        	System.out.println("108");
            fw = new JxFilterWarning();
            fw.setFilter_name(name+"滤芯");
            fw.setTime_left(timeleft);
            //JxOrder jo = jxOrderService.queryOrderByProno(pro_no);
            System.out.println("109");
            JxOrder jo = jxOrderService.findUnique("from jx_order where pro_no = ? and ord_status=3", pro_no);
            System.out.println("110");
            fw.setManager_no(Long.valueOf(jo.getOrd_managerno()));
            fw.setOrder_id(jo.getOrd_id());
            fw.setOrder_no(jo.getOrd_no());
            fw.setPro_no(pro_no);
            fw.setStatus(0);
            fw.setUser_id(jo.getU_id());
            fw.setUser_phone(jo.getOrd_phone());
            fw.setCreate_time(new Date());
            System.out.println("111");
        }else{
        	System.out.println("112");
            fw.setTime_left(timeleft);
        }
        System.out.println("113");
        filterWarningService.save(fw);
	}
	
	
	/**
	 * 修改手机号
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/modifyPhoneNum", method = RequestMethod.POST)
	public Result modifyPhoneNum(HttpServletRequest request) {
		try {
			String param = HttpUtil.getRquestParamsByIO(request);
			JSONObject jObj = JSONObject.fromObject(param);
			String userid = jObj.getString("userid");
			String newNum = jObj.getString("newNum");
			
				int a=jxUserService.findtotalUserByphone(newNum);
				if(a>0){
					return new Result(Errors.USER_ERROR_PHONE_EXIST);
				}
			JxUser ju = jxUserService.get(Long.valueOf(userid));
			ju.setU_phone(newNum);
			jxUserService.save(ju);
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 分享设备
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/shareDevice", method = RequestMethod.POST)
	public Result shareDevice(HttpServletRequest request) {
		try {
			String param = HttpUtil.getRquestParamsByIO(request);
			JSONObject jObj = JSONObject.fromObject(param);
			String userid = jObj.getString("userid");
			String targetNum = jObj.getString("targetNum");
			JxUser ju = jxUserService.findUserByPhoneNum(targetNum);
			if (ju == null || ju.getU_id() == null) {
				return new Result(Errors.USER_ERROR_NOT_EXIST);
			}
			//校验用户是否有净水器
			int a=jxProductService.findtotalproductByuid(userid);
			if(a==0)
				{return new Result(Errors.USER_ERROR_NOT_PRODUCT);}
			//校验是否已经分享过设备
			List<Map<String,Object>> list=this.jxUserService.findIsShareByuid(Long.parseLong(userid),ju.getU_id());
			if(Long.parseLong(userid)==ju.getU_id()||list.size()>0){
				return new Result(Errors.USER_ERROR_SHARE1);
			}
			JxUser user = jxUserService.findUnique("from JxUser where u_id=?", Long.parseLong(userid));

			jxUserService.addFamilyMembersByOwnerId(userid, ju.getU_id().toString());
			
			String str="亲~您名下的净水器已经分享到"+targetNum+"用户";
			String str1="亲~您已经收到用户"+user.getU_phone()+"的净水器";
			String title = "净水器分享";
			JxMessages mess = PushController.FXMssages(targetNum,title, str1,Long.parseLong(userid));
			JxMessages mess1 = PushController.FXMssages(targetNum,title, str1,ju.getU_id());
			messageService.save(mess);
			messageService.save(mess1);
			//消息推送
			String alias = String.valueOf(ju.getU_id());
			String alias1 = userid;
			PushController.YHIOSMssage(alias, title, str1);
			PushController.YHIOSMssage(alias1, title, str);
			
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

	/**
	 * 社交分享
	 */
	@ResponseBody
	@RequestMapping(value = "/user/test/shareContent", method = RequestMethod.POST)
	public Result shareContent(HttpServletRequest request) {
		try {
			JxShare js = jxShareService.findUnique(
					"from JxShare where share_status = ?", 1);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", js.getShare_title());
			map.put("content", js.getShare_content());
			map.put("apkurl", js.getShare_apkurl());
			map.put("imgurl", js.getShare_imgurl());
			list.add(map);
			return new SecretResult(Errors.OK, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	
	
	/**
	 * 平板解绑state==3
	 * pro_hasflow	已使用流量
	   pro_restflow	剩余流量	
	   u_name	用户名称
	  */
	@ResponseBody
	@RequestMapping(value = "/user/test/untabletbinding")
	public Result unTabletBinding(HttpServletRequest request) {
		try {
			System.out.println("------开始解绑------");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String orderno = jsonObject.getString("orderno");
			JxOrder jxOrder = jxOrderService.findUnique(
					"from jx_order where ord_status=3 and ord_no=?", orderno);
			if (jxOrder == null || jxOrder.getOrd_id() == null) {
				return new Result(Errors.USER_ERROR_WRONGORDERNO);
			}
			return new Result(Errors.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	
    /**
     * 平板解绑 回调
     * 
     * @param params
     * @return
     */
    @SuppressWarnings("unused")
	@ResponseBody
    @RequestMapping(value = "/user/test/untabletbindingback")
    public Result unTabletBindingBack(HttpServletRequest request) {
        try {
        	System.out.println("------开始解绑 回调------");
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject j = JSONObject.fromObject(params);
            String orderno = j.getString("orderno");
            String pro_no = j.getString("pro_no");
            Integer prfpp = j.getInt("prfpp");
            Integer prfcto = j.getInt("prfcto");
            Integer prfro = j.getInt("prfro");
            Integer prft33	 = j.getInt("prft33");
            Integer prfwfr	 = j.getInt("prfwfr");
            String restflow = j.getString("restflow");
            int day = j.getInt("day");
                //平板开始解绑
                JxOrder jxOrder = jxOrderService.findUnique(
                        "from jx_order where ord_no=? and ord_status=3", orderno);
                if (jxOrder == null || jxOrder.getOrd_id() == null) {
                    return new Result(Errors.USER_ERROR_WRONGORDERNO);
                }
                //JxProduct jxproduct = new JxProduct();
                System.out.println("---开始查商品表信息---");
                JxProduct jxproduct = jxProductService.findUnique("from JxProduct where pro_no = ?", pro_no);
                System.out.println("---查商品表信息结束---");
                String userid = jxOrder.getU_id().toString();
                //修改状态的方法
				//int jxOrder1 = jxOrderService.updateStatusAndProNo(pro_no);
				jxOrder.setOrd_status(1);
				jxOrder.setPro_no(null);
				jxOrder.setPro_restflow(restflow);
                jxOrder.setPro_day(day);
                jxOrder.setOrd_modtime(new Date());
                jxOrderService.save(jxOrder);
                //上传滤芯状态
                JxProflt jxProflt = profltService.findJxProfltByProNO(pro_no);
    			if(jxOrder == null || jxProflt.getPrf_id()==null){
    				jxProflt.setPrf_pp(prfpp);
    				jxProflt.setPrf_cto(prfcto);
    				jxProflt.setPrf_ro(prfro);
    				jxProflt.setPrf_t33(prft33);
    				jxProflt.setPrf_wfr(prfwfr);
    				jxProflt.setPro_no(pro_no);
    				jxProflt.setPrf_addtime(new Date());
    				profltService.save(jxProflt);
    			}else{
    				jxProflt.setPrf_modtime(new Date());
    				profltService.update(pro_no,prfpp,prfcto,prfro,prft33,prfwfr);
    				
    			}
    			
    			//保存数据到售后表
    			JxAfterSales afterSales = jxAfterSalesService.findUnique("from JxAfterSales where ord_no = ?", orderno);
    			if(afterSales == null || afterSales.getAs_id() == 0){
    				
    				System.out.println("---开始保存数据到售后表---");
    				JxAfterSales jxAfterSales = new JxAfterSales();
    				jxAfterSales.setAs_addtime(new Date());	
    				jxAfterSales.setAs_color(jxOrder.getOrd_color());
    				jxAfterSales.setAdr_id(jxOrder.getAdr_id());
    				jxAfterSales.setAs_managerno(jxOrder.getOrd_managerno());
    				jxAfterSales.setAs_phone(jxOrder.getOrd_phone());
    				jxAfterSales.setAs_proname(jxOrder.getOrd_proname());
    				jxAfterSales.setAs_status(jxOrder.getOrd_status());
    				jxAfterSales.setAs_visit(0);
    				jxAfterSales.setOrd_no(orderno);
    				jxAfterSales.setOrd_price(jxOrder.getOrd_price());
    				jxAfterSales.setOrd_receivename(jxOrder.getOrd_receivename());
    				jxAfterSales.setPro_no(pro_no);
    				jxAfterSales.setPro_id(jxOrder.getPro_id());
    				jxAfterSales.setPro_restflow(restflow);
    				jxAfterSales.setPro_day(day);
    				jxAfterSales.setU_id(Integer.valueOf(jxOrder.getU_id().toString()));
    				jxAfterSalesService.save(jxAfterSales);
    				System.out.println("---保存售后表结束---");
    			}else{
    				System.out.println("---更新数据---");
    				afterSales.setAs_modtime(new Date());	
    				afterSales.setAs_color(jxOrder.getOrd_color());
    				afterSales.setAdr_id(jxOrder.getAdr_id());
    				afterSales.setAs_managerno(jxOrder.getOrd_managerno());
    				afterSales.setAs_phone(jxOrder.getOrd_phone());
    				afterSales.setAs_proname(jxOrder.getOrd_proname());
    				afterSales.setAs_status(jxOrder.getOrd_status());
    				afterSales.setAs_visit(0);
    				afterSales.setOrd_price(jxOrder.getOrd_price());
    				afterSales.setOrd_receivename(jxOrder.getOrd_receivename());
    				afterSales.setPro_id(jxOrder.getPro_id());
    				afterSales.setPro_restflow(restflow);
    				afterSales.setPro_day(day);
    				afterSales.setU_id(Integer.valueOf(jxOrder.getU_id().toString()));
    				jxAfterSalesService.save(afterSales);
    				System.out.println("---更新数据结束---");
    			}
    			
                //平板解绑完向消息中心中插入消息
    			String str="亲~您解绑的"+jxproduct.getPro_name()+"("+jxOrder.getOrd_color()+")";
    			int paytypeid = jxOrder.getOrd_protypeid();// 产品商品名称，0=包年；1=按流量付费
    			if(paytypeid == 0){
    				String str1 = jxOrder.getPro_day()+"";
    				str=str+"已解绑完成!剩余天数为"+" "+str1+"天";
    			}else{
    				
    				String str1 = jxOrder.getPro_restflow();
    				str=str+"已解绑完成!流量剩余"+" "+str1+"升";
    			}
                String title = "净水机解绑消息";
                JxMessages mess = PushController.JBMssages(jxOrder.getOrd_no(),title, str,jxproduct.getU_id());
                messageService.save(mess);
                //消息推送
                String alias = String.valueOf(jxproduct.getU_id());
                PushController.YHIOSMssage(alias, title, str);
                //清空商品表数据
    			System.out.println("---开始清空商品表数据---");
    			jxProductService.deleteProductByProno(pro_no);
    			System.out.println("---清空完成---");
            /*}else{
                //平板写入单片机失败,平板不会回调
            }*/
            return new Result(Errors.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
 
 
    /**
     * 绑定旧订单
     * 2017/07/12  修改
     * @param request
     * @return
     */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/user/test/tabletbindings")
	public Result tabletBindingolds(HttpServletRequest request) {
		try {
			System.out.println("------开始绑定订单------");
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject j = JSONObject.fromObject(params);
			String orderno = j.getString("orderno");
			String proNo = j.getString("pro_no");
			System.out.println("jqm--->"+proNo);
			JxOrder jxOrder = jxOrderService.findUnique(
					"from jx_order where ord_no=? and ord_status=1", orderno);
			if (jxOrder == null || jxOrder.getOrd_id() == null) {
				return new Result(Errors.USER_ERROR_WRONGORDERNO);
			}
			
			int ppdnum = jxOrder.getOrd_multiple();
			System.out.println("倍数:"+ppdnum);
			String jqm = jxOrder.getPro_no();
			String restflow = jxOrder.getPro_restflow();
			int day = jxOrder.getPro_day();
			System.out.println("机器码"+jqm);
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			//判断新旧订单
			if((restflow == ""||restflow==null)&&day==0){
				System.out.println("---是否传机器码---");
				if(proNo.equals("")){
					System.out.println("---无，生成新机器码---");
					String unique = UUID.randomUUID().toString();
					map.put("pro_no", unique);
				}else{
					System.out.println("---有，直接添加---");
					map.put("pro_no", proNo);
					System.out.println("---添加成功---");
				}
				
				
				System.out.println("1");
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
				int paytypeid = jxOrder.getOrd_protypeid();// 产品商品名称，0=包年；1=按流量付费
				if (paytypeid == 0) {
					System.out.println("---首次包年---");
				    map.put("pay_proid", 1);//单片机设置与数据库相反,1表示包年
					Calendar calendar = Calendar.getInstance();
					map.put("quantity", (calendar.getActualMaximum(Calendar.DAY_OF_YEAR)*ppdnum));
					//calendar.add(Calendar.YEAR,1);
					calendar.add(Calendar.YEAR,(1 * ppdnum));
					// 默认包一年,如果有多的,从订单获取
					map.put("now", sdf.format(new Date()));
					Date date = calendar.getTime();
					map.put("end", sdf.format(date));
					//初始化天数
					jxOrder.setPro_day((365 * ppdnum));
					System.out.println("2");
				} else {
					// 如果是包流量,查询初始流量值
					System.out.println("---首次包流量---");
				    map.put("pay_proid", 0);//单片机设置与数据库相反,0表示购买流量
				    System.out.println("3");
					JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?",Long.valueOf(jxOrder.getPro_id()), Long.valueOf(jxOrder.getOrd_protypeid()));
					System.out.println("4");
					Float flow = jxPay.getPay_flow();
					Float flows = flow * ppdnum;
					map.put("quantity", flows);
					//初始化流量
                    String restflow2 = flows+"";
					jxOrder.setPro_restflow(restflow2);
					System.out.println("---第一次绑定流量---"+flows);
				}
				
			}else{
				
				System.out.println("---订单重复，判断机器码是否为空---");
				if(proNo.equals("")||(jqm=="")){
					System.out.println("---机器码不存在，开始生成新的机器码---");
					String unique = UUID.randomUUID().toString();
					map.put("pro_no", unique);
				}else{
					
					System.out.println("---重复，直接添加机器码---");
					map.put("pro_no", proNo);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
				int paytypeid = jxOrder.getOrd_protypeid();// 产品商品名称，0=包年；1=按流量付费
				if (paytypeid == 0) {
					System.out.println("---包年---");
				    map.put("pay_proid", 1);//单片机设置与数据库相反,1表示包年
					map.put("quantity", jxOrder.getPro_day());
					//得到旧的天数
					int d = jxOrder.getPro_day();
					System.out.println("天数:"+d);
					jxOrder.setPro_day(d);
				} else {
					System.out.println("---包流量---");
					// 如果是包流量,查询初始流量值
				    map.put("pay_proid", 0);//单片机设置与数据库相反,0表示购买流量
					JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?",Long.valueOf(jxOrder.getPro_id()), Long.valueOf(jxOrder.getOrd_protypeid()));
					map.put("quantity", jxOrder.getPro_restflow());
					String restflow1 = jxOrder.getPro_restflow();
					System.out.println("流量："+restflow1);
					jxOrder.setPro_restflow(restflow1);
					System.out.println("---旧流量---"+jxOrder.getPro_restflow());
				}
			}
			
			//标记信息
			if(jxOrder.getU_id() == 121){
				map.put("tag", 1);
			}else{
				map.put("tag", 2);
			}
			
			//添加机器类型
			int type = jxOrderService.selectType(orderno);
			System.out.println("类型---》"+type);
			map.put("proname", type);
			list.add(map);
			//保存天数或者流量到订单号表
			jxOrder.setOrd_modtime(new Date());
			jxOrderService.save(jxOrder);
			System.out.println("集合---->"+list);
			return new SecretResult(Errors.OK, list);
		} catch (EmptyResultDataAccessException e) {
			return new Result(Errors.USER_ERROR_NOT_EXIST);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	

    /**
     * 绑定获取新滤芯寿命
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/test/tabletQueryOldLife")
    public Result tabletQueryOldLifes(HttpServletRequest request) {
        try {
        	System.out.println("------开始绑定获取新滤芯寿命------");
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject j = JSONObject.fromObject(params);
            String proNo = j.getString("pro_no");
            String code = j.getString("code");
            List<Map<String, List<Map<String, Object>>>> list = jxFilterLifeService.queryFilterLifeByProOns(proNo,code);
            return new SecretResult(Errors.OK, list);
        } catch (EmptyResultDataAccessException e) {
            return new Result(Errors.USER_ERROR_NOT_EXIST);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    


    
    
	/**
     * 平板新绑定 回调
     * 2017/07/12  修改
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/test/tabletbindingbackas")
    public Result tabletBindingBackx(HttpServletRequest request) {
        try {
        	System.out.println("---平板绑定 回调开始---1");
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject j = JSONObject.fromObject(params);
            String orderno = j.getString("orderno");
            String prono = j.getString("prono");
            String status = j.getString("status");
            String code = j.getString("code");
            String pro_alias = null;
            j.containsKey("pro_alias");
            if(j.containsKey("pro_alias")){
            	System.out.println("别名不为空");
            	pro_alias = j.getString("pro_alias");//净水机别名
            }
          
           
            if(status.equals("0")){
                //平板写入单片机成功
                JxOrder jxOrder = jxOrderService.findUnique(
                        "from jx_order where ord_no=? and ord_status=1", orderno);
                if (jxOrder == null || jxOrder.getOrd_id() == null) {
                    return new Result(Errors.USER_ERROR_WRONGORDERNO);
                }
                
                //得到倍数
            	int ppdnum = jxOrder.getOrd_multiple();
    			System.out.println("倍数:"+ppdnum);
                
                String userid = jxOrder.getU_id().toString();
                String familyId = jxUserService.findFamilyIdByUserId(userid);
                
                System.out.println("---判断product表是否为空---2");
                
                //判断product表是否为空
                JxProduct jxproduct1 = jxProductService.findUnique("from JxProduct where pro_no = ?", prono);
                System.out.println("---进入判断---3");
                if(jxproduct1==null){
                	System.out.println("---空，开始创建新的数据---4");
                
                JxProduct jxproduct = new JxProduct();
                jxproduct.setPro_color(jxOrder.getOrd_color());
                System.out.println("净水机颜色---》"+jxOrder.getOrd_color());
                jxproduct.setPro_name(this.jxOrderService.findProNameByIds(jxOrder.getPro_id()));
                jxproduct.setPro_no(prono);
                jxproduct.setPro_code(code);
                jxproduct.setFam_id(familyId);
                jxproduct.setPro_alias(pro_alias);
                jxproduct.setPh_no(jxOrder.getOrd_phone());
                jxproduct.setPro_addtime(new Date());
                jxproduct.setPro_image(jxOrder.getOrd_imgurl());
                jxproduct.setU_id(jxOrder.getU_id());
        		float a=0;
                jxproduct.setPro_hasflow(a);
                //Long pro_id = jxOrder.getPro_id();
                int pro_id = jxOrder.getPro_id();
                //jxproduct.setPro_category(Integer.parseInt(String.valueOf(pro_id)));
                jxproduct.setPro_category(pro_id);
                int paytypeid = jxOrder.getOrd_protypeid();// 产品商品名称，0=包年；1=按流量付费
              
                
                if (paytypeid == 0) {
                    Calendar calendar = Calendar.getInstance();
                    //calendar.add(Calendar.YEAR, 1);
                    calendar.add(Calendar.YEAR, 1*ppdnum);
                    // 默认包一年,如果有多的,从订单获取
                    Date date = calendar.getTime();
                    jxproduct.setPro_invalidtime(date);
                    //jxOrder.setPro_day(365);
                    System.out.println("---失效时间---"+date);
                } else {
                	System.out.println("初始化流量");
                    // 如果是包流量,查询初始流量值
                    JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?",Long.valueOf(jxOrder.getPro_id()), Long.valueOf(jxOrder.getOrd_protypeid()));
                    Float flow = jxPay.getPay_flow();
                    Float flows = flow * ppdnum;
                    jxproduct.setPro_restflow(flows);
                    System.out.println("流量:"+flows);
                    //Float s = jxPay.getPay_flow();
                    //String restflow = s+"";
                    //jxOrder.setPro_restflow(restflow);
                    System.out.println("初始化流量结束");
                }
                System.out.println("---添加数据---5");
                jxproduct = jxProductService.save(jxproduct);
                System.out.println("---数据添加完成，开始往家庭组添加记录---6");
                //产品绑定时,需要在家庭组里添加记录
                jxUserService.addFamilyProductByFamilyID(familyId, jxproduct.getPro_id().toString());
                
                System.out.println("---开始添加滤芯寿命---7");
                //根据code添加滤芯寿命
                List<Map<String,Object>> map = profltService.selectJxProfltLxsm(prono);
                if(map.size()==0){
                	System.out.println("---滤芯表为空---8");
                	List<Map<String, Object>> jxProflt = profltService.codeByFilterState(code);
                	if(jxProflt.equals("")){
                		return new Result(Errors.NO_FILTER_MESSAGES_IN_THE_PROVINCES);
                		
                	}else{
                		System.out.println("---初始化滤芯寿命---9");
                		JxProflt jp=new JxProflt(500,800,2000,2000,2000);
                		jp.setPrf_code(code);
                		jp.setPrf_addtime(new Date());
                		jp.setPro_no(prono);
                		profltService.save(jp);
                	}
                	
                }else{
                	System.out.println("---更新滤芯数据---10");
                	profltService.save(map);
                }
                
                System.out.println("---插入消息---11");
                //平板绑定完向消息中心中插入消息
                String str="亲~您购买的"+jxproduct.getPro_name()+"("+jxOrder.getOrd_color()+")";
                String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
                str=str+str1+" "+jxOrder.getOrd_price()+"元已经绑定完成了";
                
                String title = "净水机绑定消息";
                JxMessages mess = PushController.BDMssages(jxOrder.getOrd_no(),title, str,jxproduct.getU_id());
                messageService.save(mess);
                //推送消息
                String alias = String.valueOf(jxproduct.getU_id());
                PushController.YHIOSMssage(alias, title, str);
                System.out.println(".10101");
                }else{
                	System.out.println("---更新product表数据---12");
                	
                	 //Long pro_id = jxOrder.getPro_id();
                	 int pro_id = jxOrder.getPro_id();
                	 jxproduct1.setPro_category(pro_id);
                	 jxproduct1.setPro_color(jxOrder.getOrd_color());
                     System.out.println("净水机颜色---》"+jxOrder.getOrd_color());
                	 jxproduct1.setPro_image(jxOrder.getOrd_imgurl());
                	 jxproduct1.setPro_alias(pro_alias);
                     //jxproduct1.setPro_category(Integer.parseInt(String.valueOf(pro_id)));
                     int paytypeid = jxOrder.getOrd_protypeid();// 产品商品名称，0=包年；1=按流量付费
                     if (paytypeid == 0) {
                         Calendar calendar = Calendar.getInstance();
                         //calendar.add(Calendar.YEAR, 1);
                         calendar.add(Calendar.YEAR, 1 * ppdnum);
                         // 默认包一年,如果有多的,从订单获取
                         Date date = calendar.getTime();
                         jxproduct1.setPro_invalidtime(date);
                         //jxOrder.setPro_day(365);
                         System.out.println("---更新数据时失效时间---"+date);
                     } else {
                         // 如果是包流量,查询初始流量值
                         JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?",Long.valueOf(jxOrder.getPro_id()), Long.valueOf(jxOrder.getOrd_protypeid()));
                         Float flow = jxPay.getPay_flow();
                         Float flows = flow * ppdnum;
                         //String restflow = s+"";
                         //jxOrder.setPro_restflow(restflow);
                         jxproduct1.setPro_restflow(flows);
                     }
                	jxproduct1.setPro_modtime(new Date());
                	jxProductService.save(jxproduct1);
                	
                	System.out.println("---开始获取滤芯寿命---13");
                	//根据code添加滤芯寿命
                    List<Map<String,Object>> map = profltService.selectJxProfltLxsm(prono);
                    if(map==null){
                    	System.out.println("---滤芯表为空---14");
                    	List<Map<String, Object>> jxProflt = profltService.codeByFilterState(code);
                    	if(jxProflt.equals("")){
                    		return new Result(Errors.NO_FILTER_MESSAGES_IN_THE_PROVINCES);
                    		
                    	}else{
                    		System.out.println("---初始化滤芯寿命---15");
                    		JxProflt jp=new JxProflt(500,800,2000,2000,2000);
                    		jp.setPrf_code(code);
                    		jp.setPrf_addtime(new Date());
                    		jp.setPro_no(prono);
                    		profltService.save(jp);
                    	}
                    	
                    }else{
                    	System.out.println("---更新滤芯寿命---16");
                    	profltService.save(map);
                    }
                    
                   
                    //平板绑定完向消息中心中插入消息
                    String str="亲~您购买的"+jxproduct1.getPro_name()+"("+jxOrder.getOrd_color()+")";
                    String str1=jxOrder.getOrd_protypeid()==0?"包年费用":"包流量费用";
                    str=str+str1+" "+jxOrder.getOrd_price()+"元已经绑定完成了";
                   
                    String title = "净水机绑定消息";
                    JxMessages mess = PushController.BDMssages(jxOrder.getOrd_no(),title, str,jxproduct1.getU_id());
                    messageService.save(mess);
                    //推送消息
                    String alias = String.valueOf(jxproduct1.getU_id());
                    PushController.YHIOSMssage(alias, title, str);
                    System.out.println(".10101");
                }
                
                System.out.println("---order---");
                jxOrder.setPro_no(prono);
                jxOrder.setOrd_status(3);
                jxOrder.setOrd_modtime(new Date());
                System.out.println("0000");
                //产品绑定时,需要在家庭组里添加记录
                //jxUserService.addFamilyProductByFamilyID(familyId, jxproduct1.getPro_id().toString());
                System.out.println("00001");
                jxOrderService.save(jxOrder);
                System.out.println("1111");
                
                System.out.println("---添加水质信息---");
                JxStatistical jxStatistical = new JxStatistical();
                jxStatistical.setU_id(Integer.parseInt(jxOrder.getU_id().toString()));
				jxStatistical.setOrd_no(orderno);
				jxStatistical.setPro_no(prono);
				jxStatistical.setSta_tds(0);
				jxStatistical.setSta_temperature(0);
				jxStatistical.setPro_restflow("0");
				jxStatistical.setOutput_water("0");
				jxStatistical.setSta_addtime(new Date());
				jxStatisticalService.save(jxStatistical);
                
            }else{
                //平板写入单片机失败,平板不会回调
            }
            System.out.println("666");
            return new Result(Errors.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    
    
    
    //找回功能
    @ResponseBody
	@RequestMapping(value = "/user/test/back")
	public Result back(HttpServletRequest request) {
		try {
			String params = HttpUtil.getRquestParamsByIO(request);
			JSONObject j = JSONObject.fromObject(params);
			String orderno = j.getString("orderno");
			String proNo = j.getString("pro_no");
			
			JxOrder jxOrder = jxOrderService.findUnique(
					"from jx_order where ord_no=? and ord_status=3", orderno);
			if (jxOrder == null || jxOrder.getOrd_id() == null) {
				return new Result(Errors.UNABLE_TO_RETRIEVE);
			}
			String s = jxOrder.getPro_no();
			System.out.println("--->"+s);
			if(!proNo.equals(jxOrder.getPro_no())){
				return new Result(Errors.MACHINE_CODE_ERROR);
			}else{
			Map<String, Object> map = new HashMap<String, Object>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			map.put("pro_no", proNo);
			//SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			int paytypeid = jxOrder.getOrd_protypeid();// 产品商品名称，0=包年；1=按流量付费
			if (paytypeid == 0) {
			    map.put("pay_proid", 1);//单片机设置与数据库相反,1表示包年
				/*Calendar calendar = Calendar.getInstance();
				map.put("quantity", calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
				calendar.add(Calendar.YEAR,1);
				// 默认包一年,如果有多的,从订单获取
				map.put("now", sdf.format(new Date()));
				Date date = calendar.getTime();
				map.put("end", sdf.format(date));*/
			    map.put("quantity", jxOrder.getPro_day());
				
			} else {
				// 如果是包流量,查询初始流量值
			    map.put("pay_proid", 0);//单片机设置与数据库相反,0表示购买流量
				//JxPay jxPay = payWayService.findUnique("from JxPay where pay_typeid=? and pay_typename=?",Long.valueOf(jxOrder.getPro_id()), Long.valueOf(jxOrder.getOrd_protypeid()));
				//map.put("quantity", jxPay.getPay_flow());
				map.put("quantity", jxOrder.getPro_restflow());
			}
			
			//标记信息
			if(jxOrder.getU_id() == 121){
				map.put("tag", 1);
			}else{
				map.put("tag", 2);
			}
			
			//添加机器类型
			int type = jxOrderService.selectType(orderno);
			map.put("proname", type);
			list.add(map);
			return new SecretResult(Errors.OK, list);
			
			}
		} catch (EmptyResultDataAccessException e) {
			return new Result(Errors.USER_ERROR_NOT_EXIST);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
    
    
    
    public static void main(String[] args) {
    	/*Map<String,Object> map = new HashMap<String, Object>();
    	Calendar calendar = Calendar.getInstance();
		map.put("quantity", calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		calendar.add(Calendar.YEAR,1);
		System.out.println();
		System.out.println(map);*/
    	Calendar calendar = Calendar.getInstance();
		 calendar.add(Calendar.YEAR, 1);
         // 默认包一年,如果有多的,从订单获取
         Date date = calendar.getTime();
         System.out.println(date);
    }
    
    /**
     * 1.新增功能 
		要求客户端在社区首页 城市按钮 添加 ‘’水机用户 **人 “    
		限制： 只在合肥市开放
     */
    //查看合肥市的用户人数
    @ResponseBody
   	@RequestMapping(value = "/user/test/citybutton")
   	public Result cityButton(HttpServletRequest request) {
   		try {
   			String params = HttpUtil.getRquestParamsByIO(request);
   			JSONObject j = JSONObject.fromObject(params);
   			String city= j.getString("city");
   			Map<String, Object> map = new HashMap<String, Object>();
   			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
   			if(city.contains("合肥市")){
   				System.out.println("111");
   				int l = jxOrderService.selectCity(city);
   				int number1 = l +20000;
   				System.out.println("number1:"+number1);
   				String number = "用户数量:"+number1;
   				System.out.println("---->"+number);
   				if(l == 0){
   		           return new Result(Errors.THE_CITY_NO_INFORMATION);
   		         }
   				map.put("user_number",number);
			    list.add(map);
   			}else{
   				//
   				map.put("user_number",null);
			    list.add(map);
   			}
   			
   			return new SecretResult(Errors.OK, list);
   		} catch (EmptyResultDataAccessException e) {
   			return new Result(Errors.USER_ERROR_NOT_EXIST);
   		} catch (Exception e) {
   			e.printStackTrace();
   			return new Result(Errors.EXCEPTION_UNKNOW);
   		}
   		
   	}
    
}
