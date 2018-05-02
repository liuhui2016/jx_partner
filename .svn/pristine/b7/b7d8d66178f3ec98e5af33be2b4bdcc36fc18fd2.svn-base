package com.game.smvc.core.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpStrOperate {
	public static boolean hasValue(String str) {
		return (str != null) && (!"".equals(str));
	}

	public static String paramEncode(String paramDecString) {
		if (!hasValue(paramDecString)) {
			return "";
		}
		try {
			return

			URLEncoder.encode(paramDecString, "UTF-8").replace("+", "%20")
					.replace("*", "%2A").replace("%7E", "~")
					.replace("#", "%23");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String ParamDecode(String paramEncString) {
		int nCount = 0;
		for (int i = 0; i < paramEncString.length(); i++) {
			if (paramEncString.charAt(i) == '%') {
				i += 2;
			}
			nCount++;
		}
		byte[] sb = new byte[nCount];

		int i = 0;
		for (int index = 0; i < paramEncString.length(); i++) {
			if (paramEncString.charAt(i) != '%') {
				sb[(index++)] = ((byte) paramEncString.charAt(i));
			} else {
				StringBuilder sChar = new StringBuilder();
				sChar.append(paramEncString.charAt(i + 1));
				sChar.append(paramEncString.charAt(i + 2));
				sb[(index++)] = Integer.valueOf(sChar.toString(), 16)
						.byteValue();
				i += 2;
			}
		}
		String decode = "";
		try {
			decode = new String(sb, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decode;
	}

	public static List<NameValuePair> getQueryParamsList(String queryString) {
		if (queryString.startsWith("?")) {
			queryString = queryString.substring(1);
		}
		List<NameValuePair> result = new ArrayList();
		if ((queryString != null) && (!queryString.equals(""))) {
			String[] p = queryString.split("&");
			for (String s : p) {
				if ((s != null) && (!s.equals("")) && (s.indexOf('=') > -1)) {
					String[] temp = s.split("=");
					if (temp.length > 1) {
						result.add(new BasicNameValuePair(temp[0],
								ParamDecode(temp[1])));
					}
				}
			}
		}
		return result;
	}

	public static String getQueryString(List<NameValuePair> QueryParamsList) {
		StringBuilder queryString = new StringBuilder();
		for (NameValuePair param : QueryParamsList) {
			queryString.append('&');
			queryString.append(param.getName());
			queryString.append('=');
			queryString.append(paramEncode(param.getValue()));
		}
		return queryString.toString().substring(1);
	}

	public static String getTimeState(String timestamp) {
		if ((timestamp == null) || ("".equals(timestamp))) {
			return "";
		}
		try {
			long _timestamp = Long.parseLong(timestamp) * 1000L;
			if (System.currentTimeMillis() - _timestamp < 60000L) {
				return "刚刚";
			}
			if (System.currentTimeMillis() - _timestamp < 1800000L) {
				return (System.currentTimeMillis() - _timestamp) / 1000L / 60L
						+ "分钟前";
			}
			Calendar now = Calendar.getInstance();
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(_timestamp);
			if ((c.get(1) == now.get(1)) && (c.get(2) == now.get(2))
					&& (c.get(5) == now.get(5))) {
				SimpleDateFormat sdf = new SimpleDateFormat("今天 HH:mm");
				return sdf.format(c.getTime());
			}
			if ((c.get(1) == now.get(1)) && (c.get(2) == now.get(2))
					&& (c.get(5) == now.get(5) - 1)) {
				SimpleDateFormat sdf = new SimpleDateFormat("昨天 HH:mm");
				return sdf.format(c.getTime());
			}
			if (c.get(1) == now.get(1)) {
				SimpleDateFormat sdf = new SimpleDateFormat("M月d日 HH:mm:ss");
				return sdf.format(c.getTime());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss");
			return sdf.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
