package com.game.push.quickin;

import java.util.ArrayList;  
import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;

import com.game.smvc.entity.JxOrder;
import com.game.smvc.service.IJxOrderService;
import com.gexin.rp.sdk.base.IIGtPush;  
import com.gexin.rp.sdk.base.IPushResult;  
import com.gexin.rp.sdk.base.impl.AppMessage;  
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.payload.MultiMedia;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;  
import com.gexin.rp.sdk.template.LinkTemplate; 
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.gexin.rp.sdk.template.style.Style1;

/**
 * 对多个app进行推送
 * @author Administrator
 *
 */
public class PushAllToApp extends PushBase{
	
	
	
	@SuppressWarnings("deprecation")
	public static String getPaymentMessage() {
		IIGtPush push = new IGtPush(url, appKey, masterSecret);  
		try {  
			  
            AppMessage message = new AppMessage();  
  
            //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以LinkTemplate为例  
            //在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页  
            LinkTemplate template = new LinkTemplate();  
              
            template.setAppId(appId);//应用APPID  
            template.setAppkey(appKey);//应用APPKEY  
              
            //通知属性设置：如通知的标题，内容  
            template.setTitle("填写通知标题");// 通知标题  
            template.setText("填写通知内容");// 通知内容  
            template.setLogo("hello.png");  
//          template.setIsRing(true);// 收到通知是否响铃，可选，默认响铃  
//          template.setIsVibrate(true);// 收到通知是否震动，可选，默认振动  
//          template.setIsClearable(true);// 通知是否可清除，可选，默认可清除  
            template.setUrl("http://baidu.com");//点击通知后打开的网页地址，你可以设定你希望跳转的网页地址如http://www.igetui.com  
  
            message.setData(template);  
//          message.setOffline(true);//用户当前不在线时，是否离线存储，可选，默认不存储  
//          message.setOfflineExpireTime(72 * 3600 * 1000);//离线有效时间，单位为毫秒，可选  
  
            List<String> appIdList = new ArrayList<String>();  
            appIdList.add(appId);  
  
            List<String> phoneTypeList = new ArrayList<String>();//通知接收者的手机操作系统类型，可选  
            phoneTypeList.add("ANDROID");  
  
            List<String> provinceList = new ArrayList<String>();//通知接收者所在省份，可选  
            provinceList.add("浙江");  
            provinceList.add("上海");  
            provinceList.add("北京");  
              
            List<String> tagList = new ArrayList<String>();//通知接收者的标签用户，可选  
            tagList.add("填写tags名称");  
  
            message.setAppIdList(appIdList);                  
            message.setPhoneTypeList(phoneTypeList);  
            message.setProvinceList(null);  
            message.setTagList(null);  
  
            IPushResult ret = push.pushMessageToApp(message);     
  
            System.out.println(ret.getResponse().toString());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return null;  
	}  
	       

	/**
	 * 对单个app的多个用户进行推送
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String PushMessageToListTest() {
		 
			 //显示每个用户的用户状态，false:不显示，true：显示  
	        System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");  
	        // 推送主类  
	        IIGtPush push = new IGtPush(url, appKey, masterSecret);
	        try {  
	            ListMessage message = new ListMessage();  
	  
	            //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以NotificationTemplate为例  
	            //在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用  
	            NotificationTemplate template = new NotificationTemplate();  
	  
	            template.setAppId(appId);                           //应用APPID  
	            template.setAppkey(appKey);                         //应用APPKEY  
	              
	            //通知属性设置：如通知的标题，内容  
	            template.setTitle("此处填写通知标题"+getDate());                    // 通知标题  
	            template.setText("此处填写通知内容"+getDate());                 // 通知内容  
	            template.setLogo("");               // 通知图标，需要客户端开发时嵌入  
	            template.setIsRing(false);                  // 收到通知是否响铃，可选，默认响铃  
	            template.setIsVibrate(true);                    // 收到通知是否震动，可选，默认振动  
	            template.setIsClearable(true);              // 通知是否可清除，可选，默认可清除  
	              
	            template.setTransmissionType(2);                // 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动  
	            template.setTransmissionContent("你需要透传的内容"+getDate());  // 透传内容（点击通知后SDK将透传内容传给你的客户端，需要客户端做相应开发）  
	  
	            message.setData(template);  
//	            message.setOffline(true);       //用户当前不在线时，是否离线存储，可选，默认不存储  
//	            message.setOfflineExpireTime(72 * 3600 * 1000);     //离线有效时间，单位为毫秒，可选  
	  
	            // 接收者  
	            List<Target> targets = new ArrayList<Target>();  
	            Target target1 = new Target();  
//	            Target target2 = new Target();                      //如果需要可设置多个接收者  
	            target1.setAppId(appId);                            //接收者安装的应用的APPID  
	            //target1.setClientId(CID);                      //接收者的ClientID  
	            target1.setAlias(Alias2);//别名
	            //如需，可设置多个接收者  
//	            target2.setAppId(APPID2);                           //接收者2安装应用的APPID  
//	            target2.setClientId(CLIENTID2);                     //接收者2的ClientID  
	  
	            targets.add(target1);  
//	            targets.add(target2);  
	  
	            //推送前通过该接口申请“ContentID”  
	            String contentId = push.getContentId(message);    
	            IPushResult ret = push.pushMessageToList(contentId, targets);  
	   
	            System.out.println(ret.getResponse().toString());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
			return null;
	}  
	       


	/**
	 * 对单个app对单个用户进行推送
	 * 消息的透传
	 * @return
	 */
	public static String PushMessageToSingleTest() {
		 
	        // 推送主类  
	        IIGtPush push = new IGtPush(url, appKey, masterSecret);
	        try {  
	        	System.out.println("1");
	        	//单推消息类型   
	            SingleMessage message = new SingleMessage();
	            NotificationTemplate template = new NotificationTemplate();
	    		template.setAppId(appId);
	    		template.setAppkey(appKey);
	            /*Style1 style = new Style1();
	    		// 设置通知栏标题与内容
	    		style.setTitle("请输入通知栏标题");
	    		style.setText("请输入通知栏内容");
	    		// 配置通知栏图标
	    		style.setLogo("");
	    		// 配置通知栏网络图标
	    		style.setLogoUrl("");
	    		// 设置通知是否响铃，震动，或者可清除
	    		style.setRing(true);
	    		style.setVibrate(true);
	    		style.setClearable(true);
	    		template.setStyle(style);*/
	    		template.setTransmissionType(2);
	    		template.setTransmissionContent("请输入您要透传的内容"+getDate());
	    		message.setData(template);  
		        message.setOffline(true);                   //用户当前不在线时，是否离线存储,可选  
		        message.setOfflineExpireTime(72 * 3600 * 1000); //离线有效时间，单位为毫秒，可选  
	    		
	    		Target target1 = new Target();  
		        target1.setAppId(appId);  
		        target1.setAlias(Alias2);
	    		IPushResult ret = push.pushMessageToSingle(message, target1);
                
	            System.out.println(ret.getResponse().toString());
	  
	            //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以TransmissionTemplate为例  
	            //数据经SDK传给您的客户端，由您写代码决定如何处理展现给用户  
	           /* TransmissionTemplate template = new TransmissionTemplate();//透传方式  
	            template.setAppId(appId);  
	            template.setAppkey(appKey);  
	            template.setTransmissionContent("您需要透传的内容:"+getDate());  
	              
	            //收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动  
	            template.setTransmissionType(2);                      
	  
	            message.setData(template);  
	            message.setOffline(true);                   //用户当前不在线时，是否离线存储,可选  
	            message.setOfflineExpireTime(72 * 3600 * 1000); //离线有效时间，单位为毫秒，可选  
	              
	            Target target1 = new Target();  
	            target1.setAppId(appId);  
	            target1.setClientId(CID);  
	  
	            //单推  
	            IPushResult ret = push.pushMessageToSingle(message, target1);
	                          
	            System.out.println(ret.getResponse().toString());  */
        } catch (Exception e) {  
            e.printStackTrace();  
        }
			return null;
	}
	
	
	/**
	 * 消息模板类型单推
	 * @return
	 */
	public static String MBPushMessageToSingleTest() {
		IGtPush push = new IGtPush(url, appKey, masterSecret);
        LinkTemplate template = linkTemplateDemo();
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0); 
        Target target = new Target();
        target.setAppId(appId);
        //target.setClientId(CID);
        //target.setClientId("7d098d1e6104a7973bee45bf3c7efe4e");
        target.setAlias("3");
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
		return "";
	
	}  
	
	@SuppressWarnings("deprecation")
	public static LinkTemplate linkTemplateDemo() {
		
		 LinkTemplate template = new LinkTemplate();
		    // 设置APPID与APPKEY
		    template.setAppId(appId);
		    template.setAppkey(appKey);
		    // 设置通知栏标题与内容
		    template.setTitle("请输入通知栏标题");
		    template.setText("请输入通知栏内容");
		    // 配置通知栏图标
		    template.setLogo("");
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
       /* LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle("请输入通知栏标题");
        style.setText("请输入通知栏内容");
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        // 设置打开的网址地址
        template.setUrl("http://www.baidu.com");
        return template;*/
		
    }

	
	
	
	
	@SuppressWarnings("deprecation")
	public static String PushMessageToSingleTest1() {
		 
		// 推送主类  
	    IIGtPush push = new IGtPush(url, appKey, masterSecret);
	    try {  
	    	//单推消息类型   
	        SingleMessage message = new SingleMessage();   
  
            //通知栏弹框下载模版  
            //在通知栏显示一条含图标、标题等的通知，用户点击后弹出框，用户可以选择直接下载应用或者取消下载应用。  
            NotyPopLoadTemplate template = new NotyPopLoadTemplate();  
            // 是否激活  
            template.setActived(true);  
            // 安卓标识  
            template.setAndroidMark("android_mark");  
            template.setAppId(appId);  
            template.setAppkey(appKey);  
            // 是否自动安装  
            template.setAutoInstall(true);  
            // 是否响铃  
            template.setBelled(true);  
            // 通知是否可清除  
            template.setCleared(true);  
            // 苹果标识  
            template.setIphoneMark("iphone_mark");  
            // 下载图标  
            template.setLoadIcon("");  
            // 下载标题  
            template.setLoadTitle("LoadTitle");  
            // 下载地址  
            template.setLoadUrl("http://dizhensubao.igexin.com/dl/com.ceic.apk");  
            // 通知栏内容  
            template.setNotyContent("NotyContent");  
            // 通知栏图标  
            template.setNotyIcon("");  
            // 通知栏标题  
            template.setNotyTitle("NotyTitle");  
            // 左侧按钮名称  
            template.setPopButton1("下载");  
            // 右侧按钮名称  
            template.setPopButton2("取消");  
            // 弹框内容  
            template.setPopContent("popContent");  
            // 弹框图标  
            template.setPopImage("");  
            // 弹框标题  
            template.setPopTitle("PopTitle");  
            // 塞班标识  
            template.setSymbianMark("symbian_mark");  
            // 是否震动  
            template.setVibrationed(true);  
            message.setData(template);  
            message.setOffline(true);  
            message.setOfflineExpireTime(72 * 3600 * 1000);  
            // 设置优先级  
            message.setPriority(1);  
  
            Target target1 = new Target();  
            target1.setAppId(appId);  
            target1.setClientId(CID);  
            // 单推  
            IPushResult ret = push.pushMessageToSingle(message, target1);  
            System.out.println(ret.getResponse().toString());    
        } catch (Exception e) {  
            e.printStackTrace();  
        }
			return null;
	}  
	
	
	
	/**
	 * 批量别名推送
	 * @return
	 */
	@SuppressWarnings({"unchecked" })
	public static String PLPushMessageToSingleTest() {
		 
		// 配置返回每个用户返回用户状态，可选
       System.setProperty("gexin_pushList_needDetails", "true");
    // 配置返回每个别名及其对应cid的用户状态，可选
    // System.setProperty("gexin_pushList_needAliasDetails", "true");
       IGtPush push = new IGtPush(url, appKey, masterSecret);
        // 通知透传模板
       NotificationTemplate template = notificationTemplateDemo();
       ListMessage message = new ListMessage();
       message.setData(template);
        // 设置消息离线，并设置离线时间
       message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
       message.setOfflineExpireTime(24 * 1000 * 3600);
        // 配置推送目标
       @SuppressWarnings("rawtypes")
       List targets = new ArrayList();
       Target target1 = new Target();
       Target target2 = new Target();
       target1.setAppId(appId);
       //target1.setClientId(CID);
       target1.setAlias(Alias);
       target2.setAppId(appId);
       //target2.setClientId(CID2);
       target2.setAlias(Alias1);
       targets.add(target1);
       targets.add(target2);
    // taskId用于在推送时去查找对应的message
       String taskId = push.getContentId(message);
       IPushResult ret = push.pushMessageToList(taskId, targets);
       System.out.println(ret.getResponse().toString());
       return taskId;
	}  
	
	public static NotificationTemplate notificationTemplateDemo() {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle("资源下载");
        style.setText("欢迎使用净喜app！");
        // 配置通知栏图标
        style.setLogo("");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);

        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent("请输入您要透传的内容");
        return template;
    }
	
	

	public static void main(String[] args) {
		//PushAllToApp.PushMessageToListTest();
		//PushAllToApp.getPaymentMessage();//推多个app
		//linkTemplateDemo(appId,appKey);
		//PushAllToApp.PushMessageToListTest();//单个app推多个用户
		//PushAllToApp.PLPushMessageToSingleTest();
		//PushAllToApp.PushMessageToSingleTest();//单个app对单个用户
		//PushAllToApp.getTemplate();
		PushAllToApp.MBPushMessageToSingleTest();//模板类型单推
		//PushAllToApp.PushMessageToSingleTest1();//推消息主类
		
		//PushAllToApp.PLPushMessageToSingleTest();//批量推送
		//PushMessageToSingleTest1();
		/*// 推送主类  
        IIGtPush push = new IGtPush(url, appKey, masterSecret);  
  
        try {  
  
            AppMessage message = new AppMessage();  
  
            //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以LinkTemplate为例  
            //在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页  
            LinkTemplate template = new LinkTemplate();  
              
            template.setAppId(appId);//应用APPID  
            template.setAppkey(appKey);//应用APPKEY  
              
            //通知属性设置：如通知的标题，内容  
            template.setTitle("填写通知标题");// 通知标题  
            template.setText("填写通知内容");// 通知内容  
            template.setLogo("hello.png");  
//          template.setIsRing(true);// 收到通知是否响铃，可选，默认响铃  
//          template.setIsVibrate(true);// 收到通知是否震动，可选，默认振动  
//          template.setIsClearable(true);// 通知是否可清除，可选，默认可清除  
            template.setUrl("http://baidu.com");//点击通知后打开的网页地址，你可以设定你希望跳转的网页地址如http://www.igetui.com  
  
            message.setData(template);  
//          message.setOffline(true);//用户当前不在线时，是否离线存储，可选，默认不存储  
//          message.setOfflineExpireTime(72 * 3600 * 1000);//离线有效时间，单位为毫秒，可选  
  
            List<String> appIdList = new ArrayList<String>();  
            appIdList.add(appId);  
  
            List<String> phoneTypeList = new ArrayList<String>();//通知接收者的手机操作系统类型，可选  
            phoneTypeList.add("ANDROID");  
  
            List<String> provinceList = new ArrayList<String>();//通知接收者所在省份，可选  
            provinceList.add("浙江");  
            provinceList.add("上海");  
            provinceList.add("北京");  
              
            List<String> tagList = new ArrayList<String>();//通知接收者的标签用户，可选  
            tagList.add("填写tags名称");  
  
            message.setAppIdList(appIdList);                  
            message.setPhoneTypeList(phoneTypeList);  
            message.setProvinceList(null);  
            message.setTagList(null);  
  
            IPushResult ret = push.pushMessageToApp(message);     
  
            System.out.println(ret.getResponse().toString());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  */
	}


	
	public static TransmissionTemplate getTemplate() {
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    template.setTransmissionContent("透传内容");
	    template.setTransmissionType(2);
	    APNPayload payload = new APNPayload();
	    //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
	    payload.setAutoBadge("+1");
	    payload.setContentAvailable(1);
	    payload.setSound("default");
	    payload.setCategory("$由客户端定义");

	    //简单模式APNPayload.SimpleMsg
	    payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));

	    //字典模式使用APNPayload.DictionaryAlertMsg
	    //payload.setAlertMsg(getDictionaryAlertMsg());

	    // 添加多媒体资源
	    payload.addMultiMedia(new MultiMedia().setResType(MultiMedia.MediaType.video)
	                .setResUrl("http://ol5mrj259.bkt.clouddn.com/test2.mp4")
	                .setOnlyWifi(true));

	    template.setAPNInfo(payload);
	    return template;
	}
	private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(){
	    APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
	    alertMsg.setBody("body");
	    alertMsg.setActionLocKey("ActionLockey");
	    alertMsg.setLocKey("LocKey");
	    alertMsg.addLocArg("loc-args");
	    alertMsg.setLaunchImage("launch-image");
	    // iOS8.2以上版本支持
	    alertMsg.setTitle("Title");
	    alertMsg.setTitleLocKey("TitleLocKey");
	    alertMsg.addTitleLocArg("TitleLocArg");
	    return alertMsg;
	}
	
	@SuppressWarnings("deprecation")
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
	
	
	
	
}
