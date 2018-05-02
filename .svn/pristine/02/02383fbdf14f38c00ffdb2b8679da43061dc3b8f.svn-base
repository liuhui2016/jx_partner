package com.game.smvc.controller;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.bmanager.entity.JxMenu;
import com.game.bmanager.service.IJxFilterLifeService;
import com.game.bmanager.service.IJxFilterWarningService;
import com.game.bmanager.service.IJxMenuService;
import com.game.bmanager.service.IJxPictureService;
import com.game.bmanager.service.IJxUpfltService;
import com.game.smvc.util.HttpUtil;

@Controller
@RequestMapping({ "/smvc" })
public class PrototalController {
	@Autowired
	private IJxPictureService pictrueService;
	
	@Autowired
	private IJxMenuService menuService;
	
	@Autowired
	private IJxUpfltService upfltSrevice;
	
	@Autowired
	private IJxFilterWarningService filterWarningService;
	
	@Autowired
	private IJxFilterLifeService jxFilterLifeService;

	@ResponseBody
	@RequestMapping(value="prot/prototal")
	public String proAjax(HttpServletRequest request) throws Exception {
		String authCode = HttpUtil.getRquestParamsByIOs(request);
		JSONObject jsonObject = JSONObject.fromObject(authCode);
		String srcpath = jsonObject.getString("srcpath");
		Long id = Long.valueOf(srcpath);
		String url = pictrueService.queryByDefault(id);
		return url;
	}
	
	@ResponseBody
	@RequestMapping(value="adv/advpic")
	public String advAjax(HttpServletRequest request) throws Exception {
	    String id = request.getParameter("id");
		List <JxMenu> list = menuService.getAll(); 
		
		StringBuffer sb = new StringBuffer();
		for(JxMenu menu : list){
			sb.append("<option value='").append(menu.getId()).append("'");
			if(id.equals(menu.getId().toString())){
			    sb.append("selected=true");
			}
			sb.append(">").append(menu.getMenu_name()).append("</option>");
		}
		return sb.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="upflt/jxUpflt")
	public Long upfltAjax(HttpServletRequest request) throws Exception {
		String authCode = HttpUtil.getRquestParamsByIOs(request);
		JSONObject jsonObject = JSONObject.fromObject(authCode);
		String srcpath = jsonObject.getString("srcpath");
		Long id = Long.valueOf(srcpath);
		Long value = upfltSrevice.queryByUserId(id);
		return value;
	}
	
	@ResponseBody
	@RequestMapping(value="pro/proflt")
	public Long profltAjax(HttpServletRequest request) throws Exception {
		String authCode = HttpUtil.getRquestParamsByIOs(request);
		JSONObject jsonObject = JSONObject.fromObject(authCode);
		String srcpath = jsonObject.getString("srcpath");
		Long id = Long.valueOf(srcpath);
		Long value = upfltSrevice.queryByprofltId(id);
		return value;
	}
	
	@ResponseBody
	@RequestMapping(value="par/partner")
	public Long partnerAjax(HttpServletRequest request) throws Exception {
		String authCode = HttpUtil.getRquestParamsByIOs(request);
		JSONObject jsonObject = JSONObject.fromObject(authCode);
		String srcpath = jsonObject.getString("srcpath");
		Long id = Long.valueOf(srcpath);
		Long value = upfltSrevice.queryByparnerId(id);
		return value;
	}
	
	@ResponseBody
	@RequestMapping(value="filter/warning")
	public Long filterAjax(HttpServletRequest request) throws Exception {
		String authCode = HttpUtil.getRquestParamsByIOs(request);
		JSONObject jsonObject = JSONObject.fromObject(authCode);
		String temp = jsonObject.getString("Json");
		Long value = filterWarningService.queryCount(temp);
		return value;
	}
	
	@ResponseBody
	@RequestMapping(value="codes/codeA")
	public Long codeAjax(HttpServletRequest request) throws Exception {
		String authCode = HttpUtil.getRquestParamsByIOs(request);
		JSONObject jsonObject = JSONObject.fromObject(authCode);
		String temp = jsonObject.getString("codeValue");
		Long value = jxFilterLifeService.queryCode(temp);
		return value;
	}
}
