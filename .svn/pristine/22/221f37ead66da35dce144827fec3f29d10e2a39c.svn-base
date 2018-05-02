package com.game.filter;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class I18NInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String lang = request.getParameter("lang");
		if(lang == null){
			lang = (String) request.getSession().getAttribute("lang");
		}else{
			request.getSession().setAttribute("lang", lang);
		}
		
		if (lang == null || "".equals(lang) || "zh".equals(lang)) {
			ActionContext.getContext().setLocale(Locale.CHINA);
		} else if ("en".equals(lang)) {
			ActionContext.getContext().setLocale(Locale.US);
		}
		return invocation.invoke();
	}
}
