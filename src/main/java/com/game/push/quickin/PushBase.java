package com.game.push.quickin;

import java.util.Date;

public class PushBase {

/*	public static final String appId = "yJvONMFSWP9XhqBHSm2at8";
	public static final String appKey = "x2bHOsewQ38dpOCqZSUXs";
	public static final String masterSecret = "w7H5yVlWVuAzphjYcLcfW3";*/
	public static final String appId = "MQIp3PrBgD7DMFvxVdfHiA";
	public static final String appKey = "oNZHjZ5YWhAItAtS44LHD1";
	public static final String masterSecret = "YGd1ARSiRS6IXPnijPl1w1";
	public static final String CID = "99e81f7aa3d0f0274da81726f046ee53";
	//别名推送方式
	public static final String Alias = "11258a";
	public static final String Alias1 = "123456";
	public static final String Alias2 = "121";
	//public static final String url = "http://sdk.open.api.igexin.com/apiex.htm";//http://sdk.open.api.igexin.com/apiex.htm
	public static final String url = "https://api.getui.com/apiex.htm";
	
	@SuppressWarnings("deprecation")
	protected static String getDate(){  
		Date date = new Date();  
		return date.toLocaleString();  
	}  
}
