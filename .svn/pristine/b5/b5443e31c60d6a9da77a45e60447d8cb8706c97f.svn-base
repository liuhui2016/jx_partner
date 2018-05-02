package com.game.util.pay.WXpay;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.game.util.pay.WXpay.client.TenpayHttpClient;
import com.game.util.pay.WXpay.util.WxPayParamsUtil;
import com.game.util.pay.WXpay.util.XMLUtil;


public class PrepayIdRequestHandler extends RequestHandler {

	public PrepayIdRequestHandler(HttpServletRequest request,
			HttpServletResponse response) {
		super(request, response);
	}

	public Map sendPrepay() throws Exception {
		String prepayid = "";
		StringBuffer sb = new StringBuffer("<xml>");
		Set es = super.getAllParameters().entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append("<").append(k).append(">");
			sb.append(v);
			sb.append("</").append(k).append(">").append("\r\n");
		}
		String params = sb.append("</xml>").toString();

		String requestUrl = WxPayParamsUtil.URL;
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setReqContent(requestUrl);
		String resContent = "";
		Map map = null;
		this.setDebugInfo(this.getDebugInfo() + "\r\n" + "post data:" + params);
		if (httpClient.callHttpPost(requestUrl, params)) {
			resContent = httpClient.getResContent();
			map=XMLUtil.doXMLParse(resContent);

		}
		return map;
	}


}
