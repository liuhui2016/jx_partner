package com.game.push.messages;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.game.smvc.entity.JxMessages;
import com.game.smvc.service.IJxMerchantPublishService;
import com.game.smvc.service.IJxMessageService;
import com.game.smvc.service.IJxPayWayService;
import com.game.smvc.service.JxReleaseOrderService;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.style.Style0;


/**
 * 消息模板
 * @author Administrator
 *
 */
public class MessageTemplate {
	@Autowired
	private IJxPayWayService payWayService;
	@Autowired
	private static IJxMessageService messageService;
	@Autowired
	private IJxMerchantPublishService merchantPublish;
	@Autowired
	private JxReleaseOrderService jxReleaseOrderService;
	/**
	 * 支付消息
	 * @return
	 */
	
	 //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
     private static String appId = "yJvONMFSWP9XhqBHSm2at8";
     private static String appKey = "x2bHOsewQ38dpOCqZSUXs";
     private static String masterSecret = "w7H5yVlWVuAzphjYcLcfW3";
     private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
     //别名推送方式
	 //static String Alias = "123456";
     
	@SuppressWarnings("deprecation")
	public static String getPaymentMessage() throws IOException {
		IGtPush push = new IGtPush(url, appKey, masterSecret);
	    push.connect();

	    // 定义"点击链接打开通知模板"，并设置标题、内容、链接
	    LinkTemplate template = new LinkTemplate();
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    template.setTitle("交易消息");
	    template.setText("您的订单已支付成功！");
	    template.setUrl("http://baidu.com");

	    List<String> appIds = new ArrayList<String>();
	    appIds.add(appId);
		
	    // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
        
		return null;
	    	
    	 
    }
	 
	
		private static String CID = "abcfeefdab7b4aee5a75d4aa0a359df3";
	    //别名推送方式
	    static String Alias = "123456";
	    //static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	    public static void main(String[] args) throws Exception {
	    	
	    	//MessageTemplate.getPaymentMessage();
	    	
	        IGtPush push = new IGtPush(url, appKey, masterSecret);
	        System.out.println("1");
	        LinkTemplate template = linkTemplateDemo();
	        System.out.println("2");
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
	        target.setAlias(Alias);
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
	    }
	    @SuppressWarnings("deprecation")
		public static LinkTemplate linkTemplateDemo() {
	    	System.out.println("01");
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
	        System.out.println("02");
	        return template;
	    }
	
	  
}
