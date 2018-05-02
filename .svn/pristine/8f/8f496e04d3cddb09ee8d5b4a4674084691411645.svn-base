package com.game.util.pay.WXpay.util;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class WXUtil {
	
	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	 public static String findRealIp(HttpServletRequest request) {

	        String ip = request.getHeader("x-forwarded-for");
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.startsWith("10.")) {
	            ip = request.getHeader("Proxy-Client-IP");
	        }
	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.startsWith("10.")) {
	            ip = request.getHeader("WL-Proxy-Client-IP");
	        }

	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.startsWith("10.")) {
	            ip = request.getHeader("X-Real-IP");
	        }

	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip) || ip.startsWith("10.")) {
	            ip = request.getRemoteAddr();
	        }

	        if (StringUtils.isNotBlank(ip)) {
	            if (ip.indexOf(",") > 0) {
	                ip = ip.substring(0, ip.indexOf(",")).trim();
	            }
	        }
	        System.out.println(ip);
	        return ip;
	    }
}
