package com.game.util.pay.WXpay;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.game.util.pay.WXpay.util.MD5Util;
import com.game.util.pay.WXpay.util.TenpayUtil;



/**
 * ��������
 * ��������̳д��࣬��дcreateSign�������ɡ�
 * @author miklchen
 *
 */
public class RequestHandler {
	
	/** ���url��ַ */
	private String gateUrl;
	
	/** ��Կ */
	private String key;
	
	/** ����Ĳ��� */
	private SortedMap parameters;
	
	/** debug��Ϣ */
	private String debugInfo;
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	/**
	 * ���캯��
	 * @param request
	 * @param response
	 */
	public RequestHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		this.gateUrl = "https://gw.tenpay.com/gateway/pay.htm";
		this.key = "bacd37dab2cd450ba346487c204a119e";
		this.parameters = new TreeMap();
		this.debugInfo = "";
	}
	
	/**
	*��ʼ������
	*/
	public void init() {
		//nothing to do
	}

	/**
	*��ȡ��ڵ�ַ,�������ֵ
	*/
	public String getGateUrl() {
		return gateUrl;
	}

	/**
	*������ڵ�ַ,�������ֵ
	*/
	public void setGateUrl(String gateUrl) {
		this.gateUrl = gateUrl;
	}

	/**
	*��ȡ��Կ
	*/
	public String getKey() {
		return key;
	}

	/**
	*������Կ
	*/
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * ��ȡ����ֵ
	 * @param parameter �������
	 * @return String 
	 */
	public String getParameter(String parameter) {
		String s = (String)this.parameters.get(parameter); 
		return (null == s) ? "" : s;
	}
	
	/**
	 * ���ò���ֵ
	 * @param parameter �������
	 * @param parameterValue ����ֵ
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if(null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}
	
	/**
	 * �������еĲ���
	 * @return SortedMap
	 */
	public SortedMap getAllParameters() {		
		return this.parameters;
	}

	/**
	*��ȡdebug��Ϣ
	*/
	public String getDebugInfo() {
		return debugInfo;
	}
	
	/**
	 * ��ȡ����������URL
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public String getRequestURL() throws UnsupportedEncodingException {
		
		this.createSign();
		
		StringBuffer sb = new StringBuffer();
		String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			
			if(!"spbill_create_ip".equals(k)) {
				sb.append(k + "=" + URLEncoder.encode(v, enc) + "&");
			} else {
				sb.append(k + "=" + v.replace("\\.", "%2E") + "&");
			}
		}
		
		//ȥ�����һ��&
		String reqPars = sb.substring(0, sb.lastIndexOf("&"));
		
		return this.getGateUrl() + "?" + reqPars;
		
	}
	
	public void doSend() throws UnsupportedEncodingException, IOException {
		this.response.sendRedirect(this.getRequestURL());
	}
	
	/**
	 * ����md5ժҪ,������:���������a-z����,������ֵ�Ĳ���μ�ǩ��
	 */
	public String createSign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		
		String enc = "utf-8";
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toUpperCase();
		
		this.setParameter("sign", sign);
		
		return sign;
		
	}
	
	
	
	
	public String createclientSign(Map m) {
		StringBuffer sb = new StringBuffer();
		Set es = m.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			sb.append(k + "=" + v + "&");
		}
		sb.append("key=" + this.getKey());
		String enc = "utf-8";
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toUpperCase();
		return sign;
		
	}
	
	
	
	/**
	*����debug��Ϣ
	*/
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}
	
	protected HttpServletRequest getHttpServletRequest() {
		return this.request;
	}
	
	protected HttpServletResponse getHttpServletResponse() {
		return this.response;
	}
	 
}
