package com.game.smvc.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.game.push.quickin.PushPartnerBase;
import com.game.smvc.entity.JxPartnerMessages;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

/**
 * 合伙人消息推送
 * 
 * @author Administrator
 *
 */

@Controller
@RequestMapping({ "/smvc" })
public class PushPartnerController extends PushPartnerBase {

	public static TransmissionTemplate PartnerMssage(String alias,
			String title, String content) {

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

		// 简单模式APNPayload.SimpleMsg
		payload.setAlertMsg(new APNPayload.SimpleAlertMsg(title));
		// 字典模式使用下者

		// payload.setAlertMsg(getDictionaryAlertMsg());
		template.setAPNInfo(payload);
		message.setData(template);
		message.setOffline(true); // 用户当前不在线时，是否离线存储,可选
		message.setOfflineExpireTime(72 * 3600 * 1000); // 离线有效时间，单位为毫秒，可选
		Target target1 = new Target();
		target1.setAppId(appId);
		target1.setAlias(alias);
		// target1.setClientId(alias);
		IPushResult ret = push.pushMessageToSingle(message, target1);
		System.out.println(ret.getResponse().toString());
		return template;

	}

	public static JxPartnerMessages partnerMessage(String orderno,
			String content, String ord_managerno, String p_title,int p_type) {
		JxPartnerMessages messages = new JxPartnerMessages();
		messages.setMessage_time(new Date());
		messages.setNextparams(orderno);
		messages.setP_content(content);
		messages.setP_isread(0);
		messages.setP_type(p_type);
		messages.setP_name(ord_managerno);
		messages.setP_title(p_title);
		return messages;

	}
	
	public static String lower_title(int p_type) {
		String title = null; 
		if(p_type == 12){
			title = "下级提现消息";
		}else if(p_type == 7){
			title = "提现消息";
		}else if(p_type == 8){
			title = "提现消息";
		}else if(p_type == 9){
			title = "提现消息";
		}else if(p_type == 10){
			title = "提现消息";
		}else if(p_type == 11){
			title = "提现消息";
		}
		return title;
		
	}
	
	public static String lower_content(String username,String withdrawal_order_no,int p_type) {
		String content = null;
		if(p_type == 12){
			content = "您的下级" + username
					+ "发起了一次提现,提现单号为:" + withdrawal_order_no
					+ ",请尽快审批";
		}else if(p_type == 7){
			content = "您刚发起的提现单(" + withdrawal_order_no
					+ "),待人工审核，预计3个工作日。";
		}else if(p_type == 8){
			content = "您刚发起的提现单(" + withdrawal_order_no
					+ "),已经审核成功,预计5分钟内到账，具体到账时间以支付宝为准。";
		}else if(p_type == 9){
			content = "您刚发起的提现单(" + withdrawal_order_no
					+ "),审核失败,原因可查看提现记录。";
		}else if(p_type == 10){
			content = "您刚发起的提现单(" + withdrawal_order_no + "),已成功到账。";
		}else if(p_type == 11){
			content = "您刚发起的提现单(" + withdrawal_order_no + "),处理失败。具体原因可查看提现详情";
		}

		return content;
		
	}

}
