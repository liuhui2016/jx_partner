package com.game.smvc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.game.util.Des;

public class HttpUtil {
	public static String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getRemoteAddr();

		if (ip != null && ip.trim().length() > 0) {
			if (ip.indexOf(",") > 0) {
				String ips[] = ip.split(",");
				return ips[ips.length - 1];
			} else {
				return ip;
			}
		} else {
			return "0.0.0.0";
		}
	}

	public static String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

	public static String getRquestParamsByIO(HttpServletRequest request)
			throws Exception {
		String type = request.getHeader("Content-Type");
		
		StringBuffer sb = new StringBuffer();
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String s = "";
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}

		String params = sb.toString();

		if(StringUtils.isBlank(params)){
		    return null;
		}
		if (type.equals("application/x-www-form-urlencoded")) {
			params = URLDecoder.decode(params, "utf-8");
			params = params.substring(1, params.length());
		}

		String hehe = Des.decryptDES(params);
		System.out.println(hehe);
		return hehe;

	}
	
	
	public static String getRquestParamsByIOs(HttpServletRequest request) throws Exception{
	    StringBuffer sb = new StringBuffer() ; 
        InputStream is = request.getInputStream(); 
        InputStreamReader isr = new InputStreamReader(is);   
        BufferedReader br = new BufferedReader(isr); 
        String s = "" ; 
        while((s=br.readLine())!=null){ 
        	sb.append(s) ; 
        } 
        return sb.toString();
	}
}
