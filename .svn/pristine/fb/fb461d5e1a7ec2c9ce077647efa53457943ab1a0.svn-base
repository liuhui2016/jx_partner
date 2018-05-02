package com.game.push.quickin;


import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AppPush {

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "ezB29MXJ77AqqwEVb1VGd7";//MQIp3PrBgD7DMFvxVdfHiA
   // private static String appId = "MQIp3PrBgD7DMFvxVdfHiA";
    private static String appKey = "lq4kTlrPyf7cnmswGi9vR6";//
   // private static String appKey = "oNZHjZ5YWhAItAtS44LHD1";
    private static String masterSecret = "";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

        IGtPush push = new IGtPush(url, appKey, masterSecret);
        push.connect();


        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle("推送消息");
        template.setText("欢迎使用净喜智能App！");
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
    }
    
    
}