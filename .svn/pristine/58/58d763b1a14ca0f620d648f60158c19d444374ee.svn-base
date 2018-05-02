package com.game.push.template;

import com.gexin.rp.sdk.template.LinkTemplate;

public class LinkTemplateDemo {
	private static String appId = "yJvONMFSWP9XhqBHSm2at8";
    private static String appKey = "x2bHOsewQ38dpOCqZSUXs";
    private static String masterSecret = "w7H5yVlWVuAzphjYcLcfW3";   
	public static final String CID = "abcfeefdab7b4aee5a75d4aa0a359df3";
	public static LinkTemplate linkTemplateDemo(String appId, String appKey) {
	    LinkTemplate template = new LinkTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    // 设置通知栏标题与内容
	    template.setTitle("请输入通知栏标题");
	    template.setText("请输入通知栏内容");
	    // 配置通知栏图标
	    template.setLogo("icon.png");
	    // 配置通知栏网络图标
	    template.setLogoUrl("");
	    // 设置通知是否响铃，震动，或者可清除
	    template.setIsRing(true);
	    template.setIsVibrate(true);
	    template.setIsClearable(true);
	    // 设置打开的网址地址
	    template.setUrl("http://www.getui.com");
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
	}
	
	public static void main(String[] args) {
		System.out.println("1");
		linkTemplateDemo(appId,appKey);
		System.out.println("2");
	}
}
