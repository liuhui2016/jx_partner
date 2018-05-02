package com.game.smvc.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.push.quickin.PushBase;
import com.game.smvc.entity.JxMessages;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxOrderItem;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxMessageService;
import com.game.smvc.service.IJxOrderItemService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPayWayService;
import com.game.smvc.service.IJxProductService;
import com.game.smvc.util.HttpUtil;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.gexin.rp.sdk.template.style.Style1;

@Controller
@RequestMapping({ "/smvc" })
public class PushController extends PushBase{
	
	@Autowired
	IJxOrderService jxOrderService;
	@Autowired
	IJxProductService productService;
	@Autowired
	private IJxPayWayService payWayService;
	@Autowired
	public static  IJxMessageService messageService;
	@Autowired
	private IJxOrderItemService jxOrderItemServic;
	
	
	public static Result Messages() {
		try {
			/*String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);*/
			//String alias = "123456";
			IGtPush push = new IGtPush(url, appKey, masterSecret);
			LinkTemplate template = new LinkTemplate();
			// 设置APPID与APPKEY
	        template.setAppId(appId);
	        template.setAppkey(appKey);
	        Style0 style = new Style0();
	        // 设置通知栏标题与内容
	        style.setTitle("支付消息");
	        style.setText("111");
	        // 配置通知栏图标
	        style.setLogo("");
	        // 配置通知栏网络图标
	        style.setLogoUrl("");
	        // 设置通知是否响铃，震动，或者可清除
	        style.setRing(true);
	        style.setVibrate(true);
	        style.setClearable(true);
	        template.setStyle(style);
	        // 设置打开的网址地址
	        template.setUrl("http://www.baidu.com");
	        SingleMessage message = new SingleMessage();
	        message.setOffline(true);
	        // 离线有效时间，单位为毫秒，可选
	        message.setOfflineExpireTime(24 * 3600 * 1000);
	        message.setData(template);
	        //可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
	        message.setPushNetWorkType(0); 
	        Target target = new Target();
	        target.setAppId(appId);
	        target.setClientId(CID);
	       // target.setAlias(Alias1);
	        IPushResult ret = null;
			//消息推送
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
			
			return new Result(Errors.OK);
		} catch (JSONException e) {
			e.printStackTrace();
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	
	public static void MBPushMessageToSingleTest(){
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
        target.setClientId(CID);
        //target.setAlias(Alias);
        IPushResult ret = null;
        ret = push.pushMessageToSingle(message, target);
        
	
	}  
	
	public static LinkTemplate linkTemplateDemo() {
        LinkTemplate template = new LinkTemplate();
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
        return template;
    }
	
	
	
	
	/**
	 * ios个推消息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static TransmissionTemplate IOSMssage(String ordno,String alias,String content){
	
		IIGtPush push = new IGtPush(url, appKey, masterSecret);
		SingleMessage message = new SingleMessage();
	
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    template.setTransmissionContent(content);
	    template.setTransmissionType(2);
	    APNPayload payload = new APNPayload();
	    payload.setBadge(+1);
	    payload.setContentAvailable(1);
	    payload.setSound("default");
	    payload.setCategory("$由客户端定义");
	    
	    //简单模式APNPayload.SimpleMsg 
	    payload.setAlertMsg(new APNPayload.SimpleAlertMsg("交易消息"));
	    //字典模式使用下者
	  
	    //payload.setAlertMsg(getDictionaryAlertMsg());
	    template.setAPNInfo(payload);
		message.setData(template);  
        message.setOffline(true);                   //用户当前不在线时，是否离线存储,可选  
        message.setOfflineExpireTime(72 * 3600 * 1000); //离线有效时间，单位为毫秒，可选  
        Target target1 = new Target();  
        target1.setAppId(appId);  
        target1.setAlias(alias);
		IPushResult ret = push.pushMessageToSingle(message, target1);
        System.out.println(ret.getResponse().toString());
        return template;

	}

	
	public static TransmissionTemplate YHIOSMssage(String alias,String title,String content){
		
		IIGtPush push = new IGtPush(url, appKey, masterSecret);
		SingleMessage message = new SingleMessage();
	
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    template.setTransmissionContent(content);
	    template.setTransmissionType(2);
	    APNPayload payload = new APNPayload();
	    payload.setBadge(+1);
	    payload.setContentAvailable(1);
	    payload.setSound("default");
	    payload.setCategory("$由客户端定义");
	    
	    //简单模式APNPayload.SimpleMsg 
	    payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));
	    //字典模式使用下者
	  
	    //payload.setAlertMsg(getDictionaryAlertMsg());
	    template.setAPNInfo(payload);
		message.setData(template);  
        message.setOffline(true);                   //用户当前不在线时，是否离线存储,可选  
        message.setOfflineExpireTime(72 * 3600 * 1000); //离线有效时间，单位为毫秒，可选  
        Target target1 = new Target();  
        target1.setAppId(appId);  
        target1.setAlias(alias);
        //target1.setClientId(alias);
		IPushResult ret = push.pushMessageToSingle(message, target1);
        System.out.println(ret.getResponse().toString());
        return template;

	}

	
	
	public static TransmissionTemplate AppMssage(String alias,String content){
		
		IIGtPush push = new IGtPush(url, appKey, masterSecret);
		SingleMessage message = new SingleMessage();
	
	    TransmissionTemplate template = new TransmissionTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    template.setTransmissionContent(content);
	    template.setTransmissionType(2);
	    APNPayload payload = new APNPayload();
	    payload.setBadge(+1);
	    payload.setContentAvailable(1);
	    payload.setSound("default");
	    payload.setCategory("$由客户端定义");
	    
	    //简单模式APNPayload.SimpleMsg 
	    payload.setAlertMsg(new APNPayload.SimpleAlertMsg("中奖消息"));
	    //字典模式使用下者
	  
	    //payload.setAlertMsg(getDictionaryAlertMsg());
	    template.setAPNInfo(payload);
		message.setData(template);  
        message.setOffline(true);                   //用户当前不在线时，是否离线存储,可选  
        message.setOfflineExpireTime(72 * 3600 * 1000); //离线有效时间，单位为毫秒，可选  
        Target target1 = new Target();  
        target1.setAppId(appId);
        //target1.setClientId(alias);
        target1.setAlias(alias);
		IPushResult ret = push.pushMessageToSingle(message, target1);
        System.out.println(ret.getResponse().toString());
        return template;

	}

	/**
	 * 透传 所有的app用户
	 * @param title
	 * @param content
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static  TransmissionTemplate AllAppMssage(String title,String content){
		
		 IIGtPush push = new IGtPush(url, appKey, masterSecret);
		 AppMessage message = new AppMessage();  
		  
         //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以LinkTemplate为例  
         //在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页  
		 TransmissionTemplate template1 = new TransmissionTemplate();
		 template1.setAppId(appId);
		 template1.setAppkey(appKey);
		 template1.setTransmissionContent(content);
		 template1.setTransmissionType(1);
		
		 APNPayload payload = new APNPayload();
		 payload.setBadge(+1);
		 payload.setContentAvailable(1);
		 payload.setSound("default");
		 payload.setCategory("$由客户端定义");
		 payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));
		 template1.setAPNInfo(payload);
		 message.setData(template1);
         //LinkTemplate template = new LinkTemplate();  
           
		 /* template.setAppId(appId);//应用APPID  
         template.setAppkey(appKey);//应用APPKEY  
           
         //通知属性设置：如通知的标题，内容  
         template.setTitle(title);// 通知标题  
         template.setText(content);// 通知内容  
         template.setLogo("");//图标  
         template.setIsRing(true);// 收到通知是否响铃，可选，默认响铃  
         template.setIsVibrate(true);// 收到通知是否震动，可选，默认振动  
         template.setIsClearable(true);// 通知是否可清除，可选，默认可清除  
         template.setUrl("http://baidu.com");//点击通知后打开的网页地址，你可以设定你希望跳转的网页地址如http://www.igetui.com  

         message.setData(template);  */
         message.setOffline(true);//用户当前不在线时，是否离线存储，可选，默认不存储  
       	 message.setOfflineExpireTime(72 * 3600 * 1000);//离线有效时间，单位为毫秒，可选  

         List<String> appIdList = new ArrayList<String>();  
         appIdList.add(appId);  

         List<String> phoneTypeList = new ArrayList<String>();//通知接收者的手机操作系统类型，可选  
         phoneTypeList.add("ANDROID");  
         phoneTypeList.add("IPHONE");

         List<String> provinceList = new ArrayList<String>();//通知接收者所在省份，可选  
         provinceList.add("44030000");  
         /*provinceList.add("上海");  
         provinceList.add("北京");*/  
           
         List<String> tagList = new ArrayList<String>();//通知接收者的标签用户，可选  
         tagList.add("电影");
         tagList.add("音乐");

         message.setAppIdList(appIdList);                  
         message.setPhoneTypeList(phoneTypeList);  
         message.setProvinceList(provinceList);  
         //message.setTagList(tagList);  

         IPushResult ret = push.pushMessageToApp(message);     

        System.out.println(ret.getResponse().toString());
		return template1;

	}
	
	
	
	@SuppressWarnings("deprecation")
	public static  TransmissionTemplate AllAppMssage1(String title,String content){
		
		 IIGtPush push = new IGtPush(url, appKey, masterSecret);
		 AppMessage message = new AppMessage();  
		  
         //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以LinkTemplate为例  
         //在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页  
		 TransmissionTemplate template1 = new TransmissionTemplate();
		// NotificationTemplate template1 = new NotificationTemplate();
		 template1.setAppId(appId);
		 template1.setAppkey(appKey);
		 
		 template1.setTransmissionContent(content);
		 template1.setTransmissionType(1);
		 
		/* template1.setTitle(title);                 // 通知标题  
         template1.setText(content);                 // 通知内容  
         template1.setLogo("");               		// 通知图标，需要客户端开发时嵌入  
         template1.setIsRing(false);                  // 收到通知是否响铃，可选，默认响铃  
         template1.setIsVibrate(true);                    // 收到通知是否震动，可选，默认振动  
         template1.setIsClearable(true);          */    // 通知是否可清除，可选，默认可清除 
		
		 APNPayload payload = new APNPayload();
		 payload.setBadge(+1);
		 payload.setContentAvailable(1);
		 payload.setSound("default");
		 payload.setCategory("$由客户端定义");
		 payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));
		 template1.setAPNInfo(payload);
		 message.setData(template1);
         //LinkTemplate template = new LinkTemplate();  
           
		 /* template.setAppId(appId);//应用APPID  
         template.setAppkey(appKey);//应用APPKEY  
           
         //通知属性设置：如通知的标题，内容  
         template.setTitle(title);// 通知标题  
         template.setText(content);// 通知内容  
         template.setLogo("");//图标  
         template.setIsRing(true);// 收到通知是否响铃，可选，默认响铃  
         template.setIsVibrate(true);// 收到通知是否震动，可选，默认振动  
         template.setIsClearable(true);// 通知是否可清除，可选，默认可清除  
         template.setUrl("http://baidu.com");//点击通知后打开的网页地址，你可以设定你希望跳转的网页地址如http://www.igetui.com  

         message.setData(template);  */
         message.setOffline(true);//用户当前不在线时，是否离线存储，可选，默认不存储  
       	 message.setOfflineExpireTime(72 * 3600 * 1000);//离线有效时间，单位为毫秒，可选  

         List<String> appIdList = new ArrayList<String>();  
         appIdList.add(appId);  

         List<String> phoneTypeList = new ArrayList<String>();//通知接收者的手机操作系统类型，可选  
         phoneTypeList.add("ANDROID");  
         phoneTypeList.add("IPHONE");

         List<String> provinceList = new ArrayList<String>();//通知接收者所在省份，可选  
         provinceList.add("44030000");  
         /*provinceList.add("上海");  
         provinceList.add("北京");*/  
           
         List<String> tagList = new ArrayList<String>();//通知接收者的标签用户，可选  
         tagList.add("电影");
         tagList.add("音乐");

         message.setAppIdList(appIdList);                  
         //message.setPhoneTypeList(phoneTypeList);  
         message.setProvinceList(provinceList);  
         //message.setTagList(tagList);  

         IPushResult ret = push.pushMessageToApp(message);     

        System.out.println(ret.getResponse().toString());
		return template1;

	}
	
	
	/**
	 * 对多个用户进行推送
	 * @param title
	 * @param content
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static  NotificationTemplate AppMssageList(String[] alias,String title,String content){
		
		
		IIGtPush push = new IGtPush(url, appKey, masterSecret);
		  
        //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以LinkTemplate为例  
        //在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页  
		 ListMessage message = new ListMessage();  
		  
         //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以NotificationTemplate为例  
         //在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用  
         NotificationTemplate template = new NotificationTemplate();  

         template.setAppId(appId);                           //应用APPID  
         template.setAppkey(appKey);                         //应用APPKEY  
           
         //通知属性设置：如通知的标题，内容  
         template.setTitle(title);                 // 通知标题  
         template.setText(content);                 // 通知内容  
         template.setLogo("");               // 通知图标，需要客户端开发时嵌入  
         template.setIsRing(true);                  // 收到通知是否响铃，可选，默认响铃  
         template.setIsVibrate(true);                    // 收到通知是否震动，可选，默认振动  
         template.setIsClearable(true);              // 通知是否可清除，可选，默认可清除  
           
         template.setTransmissionType(2);                // 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动  
         template.setTransmissionContent(content);  // 透传内容（点击通知后SDK将透传内容传给你的客户端，需要客户端做相应开发）  

         //ios端代码
         APNPayload payload = new APNPayload();
		 payload.setBadge(+1);
		 payload.setContentAvailable(1);
		 payload.setSound("default");
		 payload.setCategory("$由客户端定义");
		 payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));
		 template.setAPNInfo(payload);
         
         message.setData(template);  
         message.setOffline(true);       //用户当前不在线时，是否离线存储，可选，默认不存储  
         message.setOfflineExpireTime(72 * 3600 * 1000);     //离线有效时间，单位为毫秒，可选  

         // 接收者  
         List<Target> targets = new ArrayList<Target>();  
         //Target target1 = new Target();  
//       Target target2 = new Target();                      //如果需要可设置多个接收者  
         //target1.setAppId(appId);                            //接收者安装的应用的APPID  
         //target1.setClientId(CID);                      //接收者的ClientID  
         for(int i = 0; i<alias.length;i++){
        	 Target target1 = new Target();
        	 target1.setAppId(appId);  
        	 //target1.setAlias(alias[i]);//别名
        	 target1.setClientId(alias[i]);
        	 targets.add(target1);
 			
 		}
         //如需，可设置多个接收者  
//       target2.setAppId(APPID2);                           //接收者2安装应用的APPID  
//       target2.setClientId(CLIENTID2);                     //接收者2的ClientID  

         //targets.add(target1);  
//       targets.add(target2);  

         //推送前通过该接口申请“ContentID”  
         String contentId = push.getContentId(message);    
         IPushResult ret = push.pushMessageToList(contentId, targets);  

         System.out.println(ret.getResponse().toString());
         return template; 

	}
	
	
	
	@SuppressWarnings("deprecation")
	public static  NotificationTemplate  AppOrIOSMssageList(String[] alias,String title,String content){
		
		
		IIGtPush push = new IGtPush(url, appKey, masterSecret);
		  
        //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以LinkTemplate为例  
        //在通知栏显示一条含图标、标题等的通知，用户点击可打开您指定的网页  
		 ListMessage message = new ListMessage();  
		  
         //通知模版：支持TransmissionTemplate、LinkTemplate、NotificationTemplate，此处以NotificationTemplate为例  
         //在通知栏显示一条含图标、标题等的通知，用户点击后激活您的应用  
		 NotificationTemplate  template1 = new NotificationTemplate ();  

		 //LinkTemplate template1 = new LinkTemplate();
		 
         template1.setAppId(appId);                           //应用APPID  
         template1.setAppkey(appKey);                         //应用APPKEY  
           
         //通知属性设置：如通知的标题，内容  
         /*template1.setTitle(title);                 	//通知标题  
         template1.setText(content);                 //通知内容  
         template1.setLogo("");               		//通知图标，需要客户端开发时嵌入  
         template1.setIsRing(true);                  //收到通知是否响铃，可选，默认响铃  
         template1.setIsVibrate(true);               //收到通知是否震动，可选，默认振动  
         template1.setIsClearable(true);       */       //通知是否可清除，可选，默认可清除  
         
         //ios端代码
         /*APNPayload payload = new APNPayload();
		 payload.setBadge(+1);
		 payload.setContentAvailable(1);
		 payload.setSound("default");
		 payload.setCategory("$由客户端定义");
		 payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));
		 template.setAPNInfo(payload);*/
		 
         template1.setTransmissionType(1);                // 收到消息是否立即启动应用，1为立即启动，2则广播等待客户端自启动  
         template1.setTransmissionContent(content);  // 透传内容（点击通知后SDK将透传内容传给你的客户端，需要客户端做相应开发）  

         Style0 style = new Style0();
         // 设置通知栏标题与内容
         style.setTitle(title);
         style.setText(content);
         // 配置通知栏图标
         style.setLogo("E://image/002.png");
         // 配置通知栏网络图标
         style.setLogoUrl("");
         // 设置通知是否响铃，震动，或者可清除
         style.setRing(true);
         style.setVibrate(true);
         style.setClearable(true);
         template1.setStyle(style);
         
         message.setData(template1);
         // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
         message.setPushNetWorkType(0); 
        // message.setData(template);  
         message.setOffline(true);       //用户当前不在线时，是否离线存储，可选，默认不存储  
         message.setOfflineExpireTime(72 * 3600 * 1000);     //离线有效时间，单位为毫秒，可选  

         // 接收者  
         List<Target> targets = new ArrayList<Target>();  
         for(int i = 0; i<alias.length;i++){
        	 Target target1 = new Target();
        	 target1.setAppId(appId);  
        	 target1.setAlias(alias[i]);//别名
        	 targets.add(target1);
 			
 		}

         //推送前通过该接口申请“ContentID”  
         String contentId = push.getContentId(message);    
         IPushResult ret = push.pushMessageToList(contentId, targets);  

         System.out.println(ret.getResponse().toString());
         return template1; 

	}
	
	public static void main(String[] args) throws Exception {
		String a = "你有一条短消息";
		String b = "恭喜你中奖了王云2！";
		//String[] alias = {"99e81f7aa3d0f0274da81726f046ee53"};
		String alias = "121";
		PushController.YHIOSMssage(alias,a,b);
		//PushController.AllAppMssage(a,b);
		//PushController.AllAppMssage1(a,b);
		
		
		
	}
	
	
	/**
	 * 消息列表  --->交易消息
	 * @param alias
	 * @param title
	 * @param content
	 */
	public static JxMessages Mssages(String orderno,String title,String str,Long uid){
		//JxMessages mess = linkMessage(orderno,str,uid);
		JxMessages mess = new JxMessages();
		mess.setNextparams(orderno);
		mess.setContent(str);
		mess.setType(1);
		mess.setU_id(Long.valueOf(uid));
		mess.setTitle(title);
		mess.setIsread(0);
		DateFormat fo= new SimpleDateFormat("MM-dd HH:mm");
		String string = fo.format(new Date());
		mess.setMessage_time(string);
		return mess;

	}
	
	/**
	 * 消息列表  --->绑定消息
	 * @param orderno
	 * @param title
	 * @param str
	 * @param uid
	 * @return
	 */
	public static JxMessages BDMssages(String orderno,String title,String str,Long uid){
		//JxMessages mess = linkMessage(orderno,str,uid);
		JxMessages mess = new JxMessages();
		mess.setNextparams(orderno);
		mess.setContent(str);
		mess.setType(2);
		mess.setU_id(Long.valueOf(uid));
		mess.setTitle(title);
		mess.setIsread(0);
		DateFormat fo= new SimpleDateFormat("MM-dd HH:mm");
		String string = fo.format(new Date());
		mess.setMessage_time(string);
		return mess;

	}
	
	/**
	 * 消息列表  --->分享消息
	 * @param orderno
	 * @param title
	 * @param str
	 * @param uid
	 * @return
	 */
	public static JxMessages FXMssages(String orderno,String title,String str,Long uid){
		//JxMessages mess = linkMessage(orderno,str,uid);
		JxMessages mess = new JxMessages();
		mess.setNextparams(orderno);
		mess.setContent(str);
		mess.setType(3);
		mess.setU_id(Long.valueOf(uid));
		mess.setTitle(title);
		mess.setIsread(0);
		DateFormat fo= new SimpleDateFormat("MM-dd HH:mm");
		String string = fo.format(new Date());
		mess.setMessage_time(string);
		return mess;

	}
	
	
	/**
	 * 消息列表  --->净水机消息
	 * @param orderno
	 * @param title
	 * @param str
	 * @param uid
	 * @return
	 */
	public static JxMessages JBMssages(String orderno,String title,String str,Long uid){
		JxMessages mess = new JxMessages();
		mess.setNextparams(orderno);
		mess.setContent(str);
		mess.setType(4);
		mess.setU_id(Long.valueOf(uid));
		mess.setTitle(title);
		mess.setIsread(0);
		DateFormat fo= new SimpleDateFormat("MM-dd HH:mm");
		String string = fo.format(new Date());
		mess.setMessage_time(string);
		return mess;

	}
	
	
	/**
	 * 消息列表  --->服务到期消息
	 * @param orderno
	 * @param title
	 * @param str
	 * @param uid
	 * @return
	 */
	public static JxMessages FWMssages(String orderno,String title,String str,Long uid){
		//JxMessages mess = linkMessage(orderno,str,uid);
		JxMessages mess = new JxMessages();
		mess.setNextparams(orderno);
		mess.setContent(str);
		mess.setType(0);
		mess.setU_id(Long.valueOf(uid));
		mess.setTitle(title);
		mess.setIsread(0);
		DateFormat fo= new SimpleDateFormat("MM-dd HH:mm");
		String string = fo.format(new Date());
		mess.setMessage_time(string);
		return mess;

	}
	
	
	public static JxMessages linkMessage(String orderno,String str,Long uid) {
		System.out.println("订单号:"+orderno);
		System.out.println("内容:"+str);
		System.out.println("用户id:"+uid);
		System.out.println("01");
		JxMessages mess = new JxMessages();
		mess.setNextparams(orderno);
		mess.setContent(str);
		mess.setType(1);
		mess.setU_id(Long.valueOf(uid));
		mess.setTitle("交易消息");
		mess.setIsread(0);
		DateFormat fo= new SimpleDateFormat("MM-dd HH:mm");
		String string = fo.format(new Date());
		mess.setMessage_time(string);
		return mess;
      
    }
	

}
