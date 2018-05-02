package com.game.push.template;

import com.game.push.quickin.PushBase;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.gexin.rp.sdk.template.style.Style1;
import com.gexin.rp.sdk.template.style.Style4;
import com.gexin.rp.sdk.template.style.Style6;

public class NotificationTemplateDemo extends PushBase{

	/*public static final String appId = "TxzlIyCcfS9KuENjjP4ux1";
	public static final String appKey = "rAnoicfrNX7915IxPocAL2";
	public static final String masterSecret = "KFDNBNKAVj9bgykwvqgeA5";
	public static final String CID = "e605a0db5ce3cca9b76b012978064940";
*/
	public static NotificationTemplate notificationTemplateDemo(String appId, String appkey) {
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		
		/**Style0系统样式*/
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

		
		
		/**Style1个推样式*/
/*		
 		Style1 style = new Style1();
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
*/
		/**Style4背景图样式*/
		
/*		
 		Style4 style = new Style4();
		style.setLogo("icon.png");
		style.setBanner_url("http://www.getui.com/picture/2017/2/9/1b72c364c34544679859eff3bad3bcf9.jpg");
		template.setStyle(style);
*/
		
		/**Style6展开式通知样式,setBigStyle1/setBigStyle2/setBigStyle3 三种方式选一种*/
		
/*		
 		Style6 style = new Style6();
		style.setTitle("title1");
		style.setText("text2");
		switch (1) {
		case 1:
			style.setBigStyle1(
					"http://www.getui.com/picture/2017/2/9/1b72c364c34544679859eff3bad3bcf9.jpg");
			break;
		case 2:
			style.setBigStyle2("BigStyle2");
			break;

		case 3:
			style.setBigStyle3(
					"http://www.getui.com/picture/2017/2/9/1b72c364c34544679859eff3bad3bcf9.jpg",
					"http://www.getui.com/picture/2017/2/9/1b72c364c34544679859eff3bad3bcf9.jpg");
			break;
		}
		template.setStyle(style);
*/
		
		
		
		

		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(2);
		template.setTransmissionContent("请输入您要透传的内容");
		return template;
	}

	public static void main(String[] args) {
		notificationTemplateDemo(appId, appKey);
	}

}
