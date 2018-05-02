package com.game.util;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	
	
	/**
	 * 过滤特殊字符
	 * @param input
	 * @return
	 */
	public static String safeCheck(String input)
    {
        //if(!hasSpecialChars(input))
        //{  
        //   return input;
        //}
        StringBuffer filtered = new StringBuffer(input.length());
        char   c;
        for(int i=0;i<input.length();i++)
        {
        	 c=input.charAt(i);
             switch(c)
             {
                 case '<':
                	 break;
                 case '>':
                	 break;
                 case '\'':  
                	 break;
                 case '&':
                 	break;
                 default:   
                 	filtered.append(c);
             }
                
        }
        return filtered.toString();
    }
	
	/**
	 * 判断是否有特殊字符
	 * @param input
	 * @return
	 */
	public static boolean hasSpecialChars(String input)
    {
        boolean flag=false;
        if((input!=null)&&(input.length()> 0))
        {
            char c;
            for(int i=0;i<input.length();i++)
            {
                c=input.charAt(i);            
                switch(c)
                {
                    case '>':   
                    	flag=true;   
                    	return flag;
                    case  '<':   
                    	flag=true;   
                    	return flag;
                    case '\'':   
                    	flag=true;   
                    	return flag;
                    case '&':   
                    	flag=true;   
                    	return flag;

                }
            }
        }
        return   flag;
    }
	/**
	 * 获取客户端的IP
	 * @param httpServletRequest
	 * @return
	 */
	public static String getIPAddress(HttpServletRequest httpServletRequest)
	  {
	    String remoteAddr = httpServletRequest.getRemoteAddr();
	    String x = null; 
	    String y = null;
	    try
	    {
	      if (((x = httpServletRequest.getHeader("HTTP_X_FORWARDED_FOR")) != null) || ((y = httpServletRequest.getHeader("X-FORWARDED-FOR")) != null))
	      {
	        if ((x != null) && (x.length() > 0))
	        {
	          remoteAddr = x;
	        }
	        else
	        {
	          remoteAddr = y;
	        }

	        int idx = remoteAddr.indexOf(44);
	        if (idx > -1) {
	          remoteAddr = remoteAddr.substring(0, idx);
	        }
	      }

	    }
	    catch (Exception e)
	    {
	     
	    }

	    return remoteAddr;
	  }
	

	public static String subpicname(String url){
		
		int e = url.lastIndexOf(".");
		int b = url.lastIndexOf("/")+1;
		String n = url.substring(b,e);
		return n;
	}
	
	/**
	 * 把字符串添加单引号
	 * 如：45052,51051,57050 转换为 '45052','51051','57050'
	 * 
	 * @param org
	 * @return
	 */
	public static String catMark(String org) {
		String result = "";
		if (StringUtils.isNotBlank(org)) {
			String[] spilitSourse = org.split(",");
			if (spilitSourse.length > 0) {
				for (String item : spilitSourse) {
					result = result + "'" + item + "',";
				}
				if (StringUtils.isNotBlank(result))
					return result.substring(0, result.lastIndexOf(","));
			}
		}
		return result;
	}
	
	/**
	 * 生成文件名
	 * @param fileName
	 * @return
	 */
	public static String generateFileName(String fileName) {
		
		DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
		String formatDate = format.format(new Date());
		int random = new Random().nextInt(10000);
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);

		return formatDate + random + extension;
	}
	
	public static String updateFileName(String fileName) {
		fileName = fileName.replace(" ", "");
		int random = new Random().nextInt(10000);
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);
		return fileName.substring(0,position) + random + extension;
	}
	
	
	public  static void deleteFile(File file) {
		if (file == null || !file.exists()) {
		
		} else if (file.isDirectory()) {
			File[] subFiles = file.listFiles();
			if (subFiles != null) {
				for (File subFile : subFiles) {
					deleteFile(subFile);					
				}
			}
		}
	    file.delete();
	}
	
	public static String chinaToUnicode(String str){  
		 String result="";   
		 str = str.toLowerCase();
		 for (int i = 0; i < str.length(); i++){   
			int chr1 = (char) str.charAt(i);  
			String unicod = Integer.toHexString(chr1);  
			if(unicod.length() < 4){
				unicod = "Test" + unicod;
			}
			result += unicod;   
			result += ",";
		 }   
		 
		 if(result.endsWith(",")){
			 result = result.substring(0, result.length()-1);
		 }
		 return result;   
	}   
	public static String transformPercent(int a, int b, int length) {
		if (a <= 0 || b<= 0) {
			return "0.00%";
		} 
		
		DecimalFormat df = null;
		if(length == 4){
			df = new DecimalFormat("0.0000");
		}else if(length == 0){
			df = new DecimalFormat("0");
		}
		else
		{
			df = new DecimalFormat("0.00");
		}
		
		String percent = df.format(a*100.0/b);
		percent = percent.replaceAll("0*$", "");
		if(percent.endsWith(".")){
			percent = percent.substring(0, percent.length()-1);
		}
		return percent+"%";
	}
	public static String transformPercent(Long a, Long b, int length) {
		if (a <= 0 || b<= 0) {
			return "0.00%";
		} 
		
		DecimalFormat df = null;
		if(length == 4){
			df = new DecimalFormat("0.0000");
		}else if(length == 0){
			df = new DecimalFormat("0");
		}
		else
		{
			df = new DecimalFormat("0.00");
		}
		
		String percent = df.format(a*100.0/b);
		percent = percent.replaceAll("0*$", "");
		if(percent.endsWith(".")){
			percent = percent.substring(0, percent.length()-1);
		}
		return percent+"%";
	}
	public static Double transformDecimal(int a, int b, int length) {
		return transformDecimal(Long.valueOf(a), b, length);
	}
	
	public static Double transformDecimal(Long a, int b, int length) {
		if (a <= 0 || b<= 0) {
			return 0.0;
		} 
		
		DecimalFormat df = null;
		if(length == 4){
			df = new DecimalFormat("0.0000");
		}else if(length == 1){
			df = new DecimalFormat("0.0");
		}else{
			df = new DecimalFormat("0.00");
		}
		
		String percent = df.format((float)a/b);
		percent = percent.replaceAll("0*$", "");
		if(percent.endsWith(".")){
			percent = percent.substring(0, percent.length()-1);
		}
		return Double.valueOf(percent);
	}
	public static Double transformDecimal(Long a, Long b, int length) {
		if (a <= 0 || b<= 0) {
			return 0.0;
		} 
		
		DecimalFormat df = null;
		if(length == 4){
			df = new DecimalFormat("0.0000");
		}else if(length == 1){
			df = new DecimalFormat("0.0");
		}else{
			df = new DecimalFormat("0.00");
		}
		
		String percent = df.format((float)a/b);
		percent = percent.replaceAll("0*$", "");
		if(percent.endsWith(".")){
			percent = percent.substring(0, percent.length()-1);
		}
		return Double.valueOf(percent);
	}
	
}
