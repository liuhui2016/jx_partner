package com.game.push.quickin;

import java.util.Date;

public class PushPartnerBase {

	public static final String appId = "5CgL3tmnQp98B5rbZvc5m4";
	public static final String appKey = "trlZq64MDI6fYa84cxNu6";
	public static final String masterSecret = "hU7Lu9KGZz6bbLpvQJgi13";
	public static final String CID = "99e81f7aa3d0f0274da81726f046ee53";
	/*//别名推送方式
	public static final String Alias = "11258a";
	public static final String Alias1 = "123456";
	public static final String Alias2 = "121";*/
	//public static final String url = "http://sdk.open.api.igexin.com/apiex.htm";//http://sdk.open.api.igexin.com/apiex.htm
	public static final String url = "https://api.getui.com/apiex.htm";
	
	@SuppressWarnings("deprecation")
	protected static String getDate(){  
		Date date = new Date();  
		return date.toLocaleString();  
	}  
}
