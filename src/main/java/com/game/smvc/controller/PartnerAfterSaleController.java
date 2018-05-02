package com.game.smvc.controller;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.bmanager.service.IJxFilterLifeService;
import com.game.bmanager.service.IJxFilterWarningService;
import com.game.smvc.dao.WebDao;
import com.game.smvc.entity.JxInformationSafety;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxFilterAfterSalesService;
import com.game.smvc.service.IJxInformationSafetyService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxUserWapService;
import com.game.smvc.util.HttpUtil;
import com.game.smvc.util.IdentifyingUtil;
import com.game.util.Des;

/**
 * 合伙人售后
 * 1.套餐滤芯状况的统计
 * 2.当前售后任务
 * 3.滤芯寿命警报
 * @author Administrator
 *
 */
@Controller
@RequestMapping({ "/smvc" })
public class PartnerAfterSaleController {

	@Autowired
	private WebDao webDao;
	@Autowired
	private IJxInformationSafetyService jxInformationSafetyService;
	@Autowired
    private IJxFilterLifeService jxFilterLifeService;
	@Autowired
	private IJxFilterWarningService filterWarningService;
	@Autowired
	private IJxOrderService jxOrderService;
	@Autowired
	private IJxFilterAfterSalesService jxFilterAfterSalesService;
	@Autowired
	private IJxUserWapService userWapService;
	
	/**
	 * 套餐滤芯状况的统计
	 * @param request
	 * @return
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/filterofsetmeal")
	public Result filterOfSetMeal(HttpServletRequest request) {
		try {
			System.out.println("---套餐滤芯状况的统计---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			int page = Integer.parseInt(jsonObject.getString("page"));
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					//业务逻辑部分
					//根据产品经理编号，查找名下所以已绑定订单
					//List<Map<String,Object>> list = jxFilterAfterSalesService.findFilterOfUserName(username,(page - 1)*10,name,ord_no,adr_id);
					List<Map<String,Object>> list = jxFilterAfterSalesService.findFilterToUserName(username,(page - 1)*10);
					for(int i = 0;i<list.size();i++){
						Map<String,Object> map = list.get(i);
						//判断最后更新时间
						
						String pro_no = (String) map.get("pro_no");
						List<Map<String, Object>> lx = this.userWapService
								.findStatusByproId(pro_no);
						if(lx.size()<=0){
							return new Result(Errors.NO_FILTER_MESSAGES);
						}
						//得到现在的滤芯
						Map<String, Object> lxmap = lx.get(0);
						Object pp = lxmap.get("pp");
						Object cto = lxmap.get("cto");
						Object ro = lxmap.get("ro");
						Object t33 = lxmap.get("t33");
						Object wfr = lxmap.get("wfr");
						
						double pps=Double.parseDouble(pp.toString());
						double ctos=Double.parseDouble(cto.toString());
						double ros=Double.parseDouble(ro.toString());
						double t33s=Double.parseDouble(t33.toString());
						double wfrs=Double.parseDouble(wfr.toString());
						String code = (String) lxmap.get("code");
						//获得原始滤芯
						List<Map<String, Object>> list1 = jxFilterLifeService.queryFilterLifeByProvince(code);
						Map<String, Object> map1 = list1.get(0);
						Object ppss = map1.get("pp");
						Object ctoss = map1.get("cto");
						Object ross = map1.get("ro");
						Object t33ss = map1.get("t33");
						Object wfrss = map1.get("wfr");
						double yspp=Double.parseDouble(ppss.toString());
						double yscto=Double.parseDouble(ctoss.toString());
						double ysro=Double.parseDouble(ross.toString());
						double yst33=Double.parseDouble(t33ss.toString());
						double yswfr=Double.parseDouble(wfrss.toString());
						//计算滤芯百分比
						double p = (pps/yspp)*100;
						double c = (ctos/yscto)*100;
						double r = (ros/ysro)*100;
						double t = (t33s/yst33)*100;
						double w = (wfrs/yswfr)*100;
						//判断滤芯值
						if(p > 100){
							p = 100;
						}
						
						if(c > 100){
							c = 100;
						}
						
						if(r > 100){
							r =100;
						}
						if(t > 100){
							t = 100;
						}
						if(w > 100){
							w = 100;
						}
						List<Map<String, Object>> l = new ArrayList<Map<String,Object>>(); 
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("proportion", (int)p);
						m.put("name", "pp滤芯");
						m.put("proflt_life", "pp");
						
						Map<String, Object> m1 = new HashMap<String, Object>();
						m1.put("proportion", (int)c);
						m1.put("name", "cto块状活性炭滤芯");
						m1.put("proflt_life", "cto");
						
						Map<String, Object> m2 = new HashMap<String, Object>();
						m2.put("proportion", (int)r);
						m2.put("name", "ro膜滤芯");
						m2.put("proflt_life", "ro");
						
						Map<String, Object> m3 = new HashMap<String, Object>();
						m3.put("proportion", (int)t);
						m3.put("name", "复合能量矿化滤芯");
						m3.put("proflt_life", "t33+wfr");
						l.add(m);
						l.add(m1);
						l.add(m2);
						l.add(m3);
						map.put("Filter_state", l);
					}
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
	 * 搜索
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/search")
	public Result search(HttpServletRequest request) {
		try {
			System.out.println("---搜索---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			int page = Integer.parseInt(jsonObject.getString("page"));
			String search = jsonObject.getString("search");
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					//业务逻辑部分
					//根据售后状态查看所有售后信息
					List<Map<String,Object>> list = jxFilterAfterSalesService.findSearch(search,username,(page-1) *10);
					for(int i = 0;i<list.size();i++){
						Map<String,Object> map = list.get(i);
						String pro_no = (String) map.get("pro_no");
						List<Map<String, Object>> lx = this.userWapService
								.findStatusByproId(pro_no);
						if(lx.size()<=0){
							return new Result(Errors.NO_FILTER_MESSAGES);
						}
						//得到现在的滤芯
						Map<String, Object> lxmap = lx.get(0);
						Object pp = lxmap.get("pp");
						Object cto = lxmap.get("cto");
						Object ro = lxmap.get("ro");
						Object t33 = lxmap.get("t33");
						Object wfr = lxmap.get("wfr");
						
						double pps=Double.parseDouble(pp.toString());
						double ctos=Double.parseDouble(cto.toString());
						double ros=Double.parseDouble(ro.toString());
						double t33s=Double.parseDouble(t33.toString());
						double wfrs=Double.parseDouble(wfr.toString());
						String code = (String) lxmap.get("code");
						//获得原始滤芯
						List<Map<String, Object>> list1 = jxFilterLifeService.queryFilterLifeByProvince(code);
						Map<String, Object> map1 = list1.get(0);
						Object ppss = map1.get("pp");
						Object ctoss = map1.get("cto");
						Object ross = map1.get("ro");
						Object t33ss = map1.get("t33");
						Object wfrss = map1.get("wfr");
						double yspp=Double.parseDouble(ppss.toString());
						double yscto=Double.parseDouble(ctoss.toString());
						double ysro=Double.parseDouble(ross.toString());
						double yst33=Double.parseDouble(t33ss.toString());
						double yswfr=Double.parseDouble(wfrss.toString());
						//计算滤芯百分比
						double p = (pps/yspp)*100;
						double c = (ctos/yscto)*100;
						double r = (ros/ysro)*100;
						double t = (t33s/yst33)*100;
						double w = (wfrs/yswfr)*100;
						//判断滤芯值
						if(p > 100){
							p = 100;
						}
						
						if(c > 100){
							c = 100;
						}
						
						if(r > 100){
							r =100;
						}
						if(t > 100){
							t = 100;
						}
						if(w > 100){
							w = 100;
						}
						List<Map<String, Object>> l = new ArrayList<Map<String,Object>>(); 
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("proportion", (int)p);
						m.put("name", "pp滤芯");
						m.put("proflt_life", "pp");
						
						Map<String, Object> m1 = new HashMap<String, Object>();
						m1.put("proportion", (int)c);
						m1.put("name", "cto块状活性炭滤芯");
						m1.put("proflt_life", "cto");
						
						Map<String, Object> m2 = new HashMap<String, Object>();
						m2.put("proportion", (int)r);
						m2.put("name", "ro膜滤芯");
						m2.put("proflt_life", "ro");
						
						Map<String, Object> m3 = new HashMap<String, Object>();
						m3.put("proportion", (int)t);
						m3.put("name", "复合能量矿化滤芯");
						m3.put("proflt_life", "t33+wfr");
						l.add(m);
						l.add(m1);
						l.add(m2);
						l.add(m3);
						map.put("Filter_state", l);
					}
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
	 * 当前售后任务
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/afterthetask")
	public Result AfterTheTask(HttpServletRequest request) {
		try {
			System.out.println("---当前售后任务---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			int page = Integer.parseInt(jsonObject.getString("page"));
			String fas_state = jsonObject.getString("fas_state");
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					//业务逻辑部分
					//根据售后状态查看所有售后信息
					List<Map<String,Object>> list = jxFilterAfterSalesService.findAfterInformationOfState(fas_state,username,(page-1) *10);
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
	 * 查看当前任务详情
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/afterthetaskparticulars")
	public Result AfterTheTaskParticulars(HttpServletRequest request) {
		try {
			System.out.println("---当前售后任务详情---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String id = jsonObject.getString("id");
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					//业务逻辑部分
					//根据售后状态查看所有售后信息
					List<Map<String,Object>> list = jxFilterAfterSalesService.findAfterTheTaskParticularsOfId(id,username);
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
	 * 合伙人查看评价
	 * 根据售后id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/partnerviewappraise")
	public Result partnerViewAppraise(HttpServletRequest request) {
		try {
			System.out.println("---合伙人查看评价---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			String id = jsonObject.getString("id");
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					//业务逻辑部分
					//根据售后id查看评价信息
					List<Map<String,Object>> list = jxFilterAfterSalesService.findPartnerViewAppraiseOfId(id,username);
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
	 * 维修记录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/maintenancerecord")
	public Result MaintenanceRecord(HttpServletRequest request) {
		try {
			System.out.println("---维修记录---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			int page = Integer.parseInt(jsonObject.getString("page"));
			String pro_no = jsonObject.getString("pro_no");
			String ord_no = jsonObject.getString("ord_no");
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					//业务逻辑部分
					//根据产品经理编号，查找名下所有已维护售后信息
					List<Map<String,Object>> list = jxFilterAfterSalesService.findMaintenanceRecord(pro_no,ord_no,username,(page-1) *10);
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
	 * 滤芯寿命警报
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/filterwarnings")
	public Result FilterWarning(HttpServletRequest request) {
		try {
			System.out.println("---滤芯警告---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			int page = Integer.parseInt(jsonObject.getString("page"));
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					//业务逻辑部分
					//根据产品经理编号，查找名下所以已绑定订单
					/*List<Map<String,Object>> list = jxFilterAfterSalesService.findFilterWarningOfUserName(username,page);
					for(int i = 0;i<list.size();i++){
						Map<String,Object> map = list.get(i);
						String pro_no = (String) map.get("pro_no");
						//查询滤芯警告表未处理的滤芯消息
						List<Map<String,Object>> l = jxFilterAfterSalesService.findFilterWarning(pro_no);
						map.put("filter_warning", l);
					}*/
					
					List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
					Map<String,Object> maps = new HashMap<String, Object>();
					List<Map<String,Object>> list = jxFilterAfterSalesService.findLxOfUserName(username,(page-1)*10);
					for(int i = 0;i<list.size();i++){
						Map<String,Object> map = list.get(i);
						String pro_no = (String) map.get("pro_no");
						String filter_name = (String) map.get("filter_name");
						List<Map<String,Object>> l = jxFilterAfterSalesService.findYHToProNo(pro_no);
						maps.put("filter_name", filter_name);
						if(l.size() > 0){
							maps.put("yh", l);
							lists.add(maps);
						}else{
							map.put("yh", "");
						}
						
					}
					return new SecretResult(Errors.OK, lists);

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
	 * 滤芯寿命警报
	 *方案二
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/filterwarningss")
	public Result FilterWarnings(HttpServletRequest request) {
		try {
			System.out.println("---滤芯警告---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			int page = Integer.parseInt(jsonObject.getString("page"));
			
			/*String safetyMark = "uJrvE5+koAIuCUTOZgDeA9QU3k8rjb1oiL54FqfD";
			int page = 1 ;*/
		
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					//业务逻辑部分
					List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
					//根据产品经理编号，查找名下所以已绑定订单
					List<Map<String,Object>> list = jxFilterAfterSalesService.findFilterWarningOfUserName(username,(page - 1)*10);
					for(int i = 0;i<list.size();i++){
						Map<String,Object> map = list.get(i);
						String pro_no = (String) map.get("pro_no");
						//查询滤芯警告表未处理的滤芯消息
						List<Map<String,Object>> l = jxFilterAfterSalesService.findFilterWarning(pro_no);
						for(int j = 0;j<l.size();j++){
							Map<String,Object> lx = l.get(j);
							//根据机器码查找对应的用户信息
							List<Map<String,Object>> yh = jxFilterAfterSalesService.findYHToProNo(pro_no);
							Map<String,Object> maps = new HashMap<String, Object>();
							maps.put("filter_name", lx.get("filter_name"));
							maps.put("time_left", lx.get("time_left"));
							maps.put("yh", yh);
							lists.add(maps);
						}
					}
					return new SecretResult(Errors.OK, lists);

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
	 * 方案三
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/partner/filterwarning")
	public Result FilterWarningss(HttpServletRequest request) {
		try {
			System.out.println("---滤芯警告---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String safetyMark = jsonObject.getString("safetyMark");
			int page = Integer.parseInt(jsonObject.getString("page"));
			
			/*String safetyMark = "uJrvE5+koAIuCUTOZgDeA9QU3k8rjb1oiL54FqfD";
			int page = 1 ;*/
		
			JxInformationSafety safety = jxInformationSafetyService
					.findUnique("from JxInformationSafety where safety_mark = '"
							+ safetyMark + "'");
			if (safety != null) {
				if (Des.decryptDES(safetyMark).contains(IdentifyingUtil.Identifying())) {
					String username = safety.getUser_name();
					//业务逻辑部分
					List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
					//根据产品经理编号，查找名下所以已绑定订单
					List<Map<String,Object>> list = jxFilterAfterSalesService.findFilterWarningOfUserName(username,(page - 1)*10);
					for(int i = 0;i<list.size();i++){
						Map<String,Object> map = list.get(i);
						String pro_no = (String) map.get("pro_no");
						List<Map<String, Object>> lx = this.userWapService
								.findStatusByproId(pro_no);
						if(lx.size()<=0){
							return new Result(Errors.NO_FILTER_MESSAGES);
						}
						//得到现在的滤芯
						Map<String, Object> lxmap = lx.get(0);
						Object p = lxmap.get("pp");
						Object c = lxmap.get("cto");
						Object r = lxmap.get("ro");
						Object t = lxmap.get("t33");
						Object w = lxmap.get("wfr");
						Float p1 = Float.valueOf(String.valueOf(p));
						Float c1 = Float.valueOf(String.valueOf(c));
						Float r1 = Float.valueOf(String.valueOf(r));
						Float t1 = Float.valueOf(String.valueOf(t));
						Float w1 = Float.valueOf(String.valueOf(w));
						int pp = Math.round(p1);
						int cto = Math.round(c1);
						int ro = Math.round(r1);
						int t33 = Math.round(t1);
						int wfr = Math.round(w1);
						String code = (String) lxmap.get("code");
						//获得原始滤芯
						List<Map<String, Object>> list1 = jxFilterLifeService.queryFilterLifeByProvince(code);
						Map<String, Object> map1 = list1.get(0);
						Integer ppss = (Integer) map1.get("pp");
						Integer ctoss = (Integer) map1.get("cto");
						Integer ross = (Integer) map1.get("ro");
						Integer t33ss = (Integer) map1.get("t33");
						Integer wfrss = (Integer) map1.get("wfr");
						if(pp<=(ppss * 0.05) || cto<=(ctoss*0.05)||ro<=(ross*0.05)||t33<=(t33ss*0.05)||wfr<=(wfrss*0.05)){
							double yspp=Double.parseDouble(ppss.toString());
							double yscto=Double.parseDouble(ctoss.toString());
							double ysro=Double.parseDouble(ross.toString());
							double yst33=Double.parseDouble(t33ss.toString());
							double yswfr=Double.parseDouble(wfrss.toString());
							//计算滤芯百分比
							double p2 = (p1/yspp)*100;
							double c2 = (c1/yscto)*100;
							double r2 = (r1/ysro)*100;
							double t2 = (t1/yst33)*100;
							double w2 = (w1/yswfr)*100;
							//判断滤芯值
							if(p2 > 100){
								p2 = 100;
							}
							
							if(c2 > 100){
								c2 = 100;
							}
							
							if(r2 > 100){
								r2 =100;
							}
							if(t2 > 100){
								t2 = 100;
							}
							if(w2 > 100){
								w2 = 100;
							}
							List<Map<String, Object>> l = new ArrayList<Map<String,Object>>(); 
							Map<String, Object> m = new HashMap<String, Object>();
							m.put("proportion", (int)p2);
							m.put("name", "pp滤芯");
							m.put("proflt_life", "pp");
							
							Map<String, Object> m1 = new HashMap<String, Object>();
							m1.put("proportion", (int)c2);
							m1.put("name", "cto块状活性炭滤芯");
							m1.put("proflt_life", "cto");
							
							Map<String, Object> m2 = new HashMap<String, Object>();
							m2.put("proportion", (int)r2);
							m2.put("name", "ro膜滤芯");
							m2.put("proflt_life", "ro");
							
							Map<String, Object> m3 = new HashMap<String, Object>();
							m3.put("proportion", (int)t2);
							m3.put("name", "复合能量矿化滤芯");
							m3.put("proflt_life", "t33+wfr");
							l.add(m);
							l.add(m1);
							l.add(m2);
							l.add(m3);
							//根据机器码查找对应的用户信息
							String pro_no2 = (String) lxmap.get("pro_no");
							System.out.println(pro_no2);
							Map<String,Object> maps = new HashMap<String, Object>();
							List<Map<String,Object>> yh = jxFilterAfterSalesService.findYHToProNo(pro_no2);
							Map<String,Object> y = yh.get(0);
							//maps.put("yh", yh);
							maps.put("ord_receivename", y.get("ord_receivename"));
							maps.put("ord_phone", y.get("ord_phone"));
							maps.put("adr_id", y.get("adr_id"));
							maps.put("pro_no", y.get("pro_no"));
							maps.put("color", y.get("color"));
							maps.put("pro_id", y.get("pro_id"));
							maps.put("ord_managerno", y.get("ord_managerno"));
							maps.put("ord_no", y.get("ord_no"));
							maps.put("name", y.get("name"));
							maps.put("url", y.get("url"));
							maps.put("pro_alias", y.get("pro_alias"));
							maps.put("Filter_state", l);
							lists.add(maps);	
							
						}
					}
					return new SecretResult(Errors.OK, lists);

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
	
	public static void main(String[] args) throws InterruptedException {
	     TimerTask task = new TimerTask() {  
	            @Override  
	            public void run() {
	            	System.out.println("---开始---");  
	            	long timed = new Date().getTime();
	            	Random rand = new Random();
	            	while (true) {
	            		 try {// 休眠300毫秒，模拟处理业务等
							Thread.sleep(300);
							int i = rand.nextInt(100); // 产生一个0-100之间的随机数
		            		 if (i > 20 && i < 56) { // 如果随机数在20-56之间就视为有效数据，模拟数据发生变化
		            			 long responseTime = System.currentTimeMillis();
		            			 System.out.println("result: " + i + ", response time: " + responseTime + ", request time: " + timed + ", use time: " + (responseTime - timed));
		            			 break; // 跳出循环，返回数据
		            		 }else{// 模拟没有数据变化，将休眠 hold住连接
		            			 Thread.sleep(1300);
		            		 }
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
	            		 
	            	}
	                System.out.println("---结束---");  
	            }  
	        };  
	        Timer timer = new Timer();  
	        long delay = 0;  
	        long intevalPeriod = 1000;  
	        timer.scheduleAtFixedRate(task, delay, intevalPeriod);  
	    }  

}
