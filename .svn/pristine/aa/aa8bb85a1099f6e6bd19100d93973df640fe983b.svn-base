package com.game.smvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxMessageService;
import com.game.smvc.service.IJxNewsItemService;
import com.game.smvc.service.IJxNewsService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPayWayService;
import com.game.smvc.service.IJxProductService;
import com.game.smvc.service.IJxStatisticalService;
import com.game.smvc.util.HttpUtil;

/**
 * 新闻信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping({ "/smvc" })
public class NewsController {

	@Autowired
	IJxProductService productService;
	@Autowired
	IJxOrderService jxOrderService;
	@Autowired
	private IJxMessageService messageService;
	@Autowired
	private IJxPayWayService payWayService;
	@Autowired
	private IJxStatisticalService jxStatisticalService;
	@Autowired
	private IJxNewsItemService jxNewsItemService;
	@Autowired
	private IJxNewsService jxNewsService;
	
	/**
	 * 加载新闻信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/news/information")
	public Result information(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String type = jsonObject.getString("type");
			List<Map<String, Object>> list = null;
			if("0".equals(type)){
				list = jxNewsService.findAllInformation(type);
			}else{
				list = jxNewsService.findInformation(type);
			}
			return new SecretResult(Errors.OK, list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}
	
	
}
