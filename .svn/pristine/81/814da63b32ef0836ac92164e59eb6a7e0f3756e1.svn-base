package com.game.smvc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.game.smvc.entity.JxTableLog;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.service.IJxTableLogService;
import com.game.smvc.util.HttpUtil;
import com.mysql.fabric.xmlrpc.base.Data;

/**
 * 平板日志信息
 * @author lh
 *
 */
@Controller
@RequestMapping({ "/smvc" })
public class TableLogController {

	@Autowired
	private IJxTableLogService jxTableLogService;
	
	@ResponseBody
	@RequestMapping(value = "/table/tablelog")
	public Result tableLog(HttpServletRequest request) {
		try {
			System.out.println("---开始保存平板日志信息---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String pro_no = jsonObject.getString("pro_no");//机器号
			String tl_param = jsonObject.getString("tl_param");//参数
			String tl_option = jsonObject.getString("tl_option");//操作名字
			String tl_netdate = jsonObject.getString("tl_netdate");//网络回来的数据
			String tl_localdate = jsonObject.getString("tl_localdate");//本地的数据
			String apk_version = jsonObject.getString("apk_version");//app版本号
			String pb_version = jsonObject.getString("slab_version");//平板版本号
			JxTableLog jxTableLog = new JxTableLog();
			jxTableLog.setPro_no(pro_no);
			jxTableLog.setTl_param(tl_param);
			jxTableLog.setTl_localdate(tl_localdate);
			jxTableLog.setTl_netdate(tl_netdate);
			jxTableLog.setTl_option(tl_option);
			jxTableLog.setApk_version(apk_version);
			jxTableLog.setPb_version(pb_version);
			jxTableLog.setTl_addtime(new Date());
			jxTableLogService.save(jxTableLog);
			return new Result(Errors.OK);

		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	
	public static void main(String[] args) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		Map<String, Object> map = new HashMap<String, Object>();
		int ppdnum = 2;
		Calendar calendar = Calendar.getInstance();
		map.put("quantity", (calendar.getActualMaximum(Calendar.DAY_OF_YEAR)*ppdnum));
		calendar.add(Calendar.YEAR,(1 * ppdnum));
		// 默认包一年,如果有多的,从订单获取
		map.put("now", sdf.format(new Date()));
		Date date = calendar.getTime();
		map.put("end", sdf.format(date));
		list.add(map);
		System.out.println(list);
	}
}
