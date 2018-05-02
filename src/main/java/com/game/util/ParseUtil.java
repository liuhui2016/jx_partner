package com.game.util;

import java.util.ArrayList;

public class ParseUtil {

	 /**
	  * 将字符串转换成ArrayList
	  * @param str
	  * @return
	  */
	 public static ArrayList<String> asArrayList(String str)
	 {
	    return  parseString(new ArrayList<String>() , str, null);
	 }

	 /**
	  * 将字符串转换成ArrayList
	  * @param str
	  * @param separator
	  * @return
	  */
	 public static ArrayList<String> asArrayList(String str, char separator)
	 {
	    return  parseString(new ArrayList<String> (), str, separator + "");
	 }
	 
	 /**
	  * 将字符串转换成ArrayList
	  * @param list
	  * @param str
	  * @param delimiter
	  * @return
	  */
	 private static ArrayList<String> parseString(ArrayList<String> list, String str, String delimiter)
	  {
	    if (delimiter == null) {
	       delimiter = "," + "";
	    }

	    int i = 0;

	    while ((i = str.indexOf(delimiter)) != -1) {
	      String s = str.substring(0, i);

	      if (s != null)
	        s = s.trim();
	      else {
	        s = "";
	      }

	      list.add(s);
	      str = str.substring(i + 1);
	    }

	    list.add(str);

	    return list;
	  }


}
