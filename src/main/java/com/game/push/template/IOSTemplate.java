package com.game.push.template;

import com.game.push.quickin.PushBase;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class IOSTemplate extends PushBase{
	/*public static final String appId = "MQIp3PrBgD7DMFvxVdfHiA";
	public static final String appKey = "oNZHjZ5YWhAItAtS44LHD1";
	public static final String masterSecret = "YGd1ARSiRS6IXPnijPl1w1";  
	public static final String Alias2 = "121";
	public static final String url = "http://sdk.open.api.igexin.com/apiex.htm";
	public static final String CID = "e605a0db5ce3cca9b76b012978064940";*/

	public static TransmissionTemplate getTemplate() {
		IIGtPush push = new IGtPush(url, appKey, masterSecret);
		SingleMessage message = new SingleMessage();
	
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    template.setTransmissionContent("透传内容");
	    template.setTransmissionType(2);
	    APNPayload payload = new APNPayload();
	    payload.setBadge(1);
	    payload.setContentAvailable(1);
	    payload.setSound("default");
	    payload.setCategory("$由客户端定义");
	    
	    //简单模式APNPayload.SimpleMsg 
	    payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
	    //字典模式使用下者
	  
	    //payload.setAlertMsg(getDictionaryAlertMsg());
	    template.setAPNInfo(payload);
		message.setData(template);  
        message.setOffline(true);                   //用户当前不在线时，是否离线存储,可选  
        message.setOfflineExpireTime(72 * 3600 * 1000); //离线有效时间，单位为毫秒，可选  
        Target target1 = new Target();  
        target1.setAppId(appId);  
        target1.setAlias(Alias1);
		IPushResult ret = push.pushMessageToSingle(message, target1);
        System.out.println(ret.getResponse().toString());
	    return template;
	}
	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(){
	    APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
	    alertMsg.setBody("body");
	    alertMsg.setActionLocKey("ActionLockey");
	    alertMsg.setLocKey("LocKey");
	    alertMsg.addLocArg("loc-args");
	    alertMsg.setLaunchImage("launch-image");
	    // IOS8.2以上版本支持
	    alertMsg.setTitle("Title");
	    alertMsg.setTitleLocKey("TitleLocKey");
	    alertMsg.addTitleLocArg("TitleLocArg");
	    
	    return alertMsg;
	}
	
	public static void main(String[] args) {
		IOSTemplate.getTemplate();
		
		
	}
}
