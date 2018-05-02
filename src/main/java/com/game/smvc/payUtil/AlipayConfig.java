package com.game.smvc.payUtil;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：1.0
 *日期：2016-06-06
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	//合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String partner = "2088521298136605";
	
	public static String app_id= "2016112203103615";

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN5OVh1VucWVzbGS"
            +"1LH0ytH6h9dlN/wvX4GZaMeIMkgZsZgBfF4V0eTUwhBfkSikhe1nV+4fR1Ss6Fo/"
            +"9KA166ilNoE0F1EUwiXeVegGHNY7KYVTDJVT1iM6gqMPva7f/EWzpIW4G7/1NgsN"
            +"Ev/kN1CU5emS0OQDtc8er6xuCU7xAgMBAAECgYBnKNr1Se6nLdkB6i0hV4M25Zdb"
            +"8PCF6kXbkiD5Vs5efu3Wp/nafy2jjsdvaammvpIXlLlNGt6zAHniR4NxRRRRlLr0"
            +"1Hhsn7FET6JFU6p/KM1/CxmoMbWbF+4obVVkmjIqLLHDTCjMraH/tlSrHT7xgvji"
            +"Ew9Zk4yNzDBhyRUOsQJBAPVSJ4fDtDm1cZWFFjTBWQJB07R5ws6MoLOOv9nc7FQF"
            +"7/O7rlm4cF4tOoXWSXClA02pHrMQZoOraeUZa2dBkucCQQDn+7RrzXVkcdoFntgH"
            +"1qIy5/bovqJXyFdqWwTAQAhIXIXonkKgyUJnd/NxNpKKyX+8H2wxH16ZelcCcwSB"
            +"7KxnAkB2fgsX+YBIy4okZVcXfjhm7bK7HoDo0WYhtJaYPaxs3T1MZd/N+FdWNdRp"
            +"ptpsLVgOH9zzMr3BZX9NqFyHUFYLAkAOXiWHg7sglHiXXoYsvhtfocRGGACABVV8"
            +"rdR2f8DDko9sn9iqkqx9Mg2u1l1vIRm7MgsGY9X9FXmsGimOTnVDAkEAjON4g3oU"
            +"F4hWAN5866iZ0OTnnDbZe7bS+3YOz7flggETyXlcom8/HUoyHhCQZ91EsFdgBwK6"
            +"tQo2kSJ5LQHZTg==";
	
	//支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	
	//转账公钥
	public static String alipay_public_zh_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path ="D:/log";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	//支付宝网关
	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	
	public static String format = "json";
	// 接收通知的接口名
	public static String service = "http://192.168.1.37:13310/jx_smart/smvc/pay/alipayResult.v";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}

