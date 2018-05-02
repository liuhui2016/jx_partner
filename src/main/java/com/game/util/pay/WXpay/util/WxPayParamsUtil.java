package com.game.util.pay.WXpay.util;

public class WxPayParamsUtil {
	/**
	 * 鍟嗗鍙互鑰冭檻璇诲彇閰嶇疆鏂囦欢
	 */

	// 鍒濆鍖�
	public final static String APP_ID = "wx2e4c1225544f04b1";// 寰俊寮�彂骞冲彴搴旂敤id
	public final static String APP_SECRET = "ee6c40aada607f399d09325c065ccaaa";// 搴旂敤瀵瑰簲鐨勫嚟璇�
	// 搴旂敤瀵瑰簲鐨勫瘑閽�
	public final static String URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public final static String APP_KEY = "";
	public final static String PARTNER = "1430232902";// 璐粯閫氬晢鎴峰彿
	public final static String PARTNER_KEY = null;// 鍟嗘埛鍙峰搴旂殑瀵嗛挜
	public static String TOKENURL = null;// 鑾峰彇access_token瀵瑰簲鐨剈rl
	public static String GRANT_TYPE = null;// 甯搁噺鍥哄畾鍊�
	public static String EXPIRE_ERRCODE = null;// access_token澶辨晥鍚庤姹傝繑鍥炵殑errcode
	public static String FAIL_ERRCODE = null;// 閲嶅鑾峰彇瀵艰嚧涓婁竴娆¤幏鍙栫殑access_token澶辨晥,杩斿洖閿欒鐮�
	public static String GATEURL = null;// 鑾峰彇棰勬敮浠榠d鐨勬帴鍙url
	public static String ACCESS_TOKEN = null;// access_token甯搁噺鍊�
	public static String ERRORCODE = null;// 鐢ㄦ潵鍒ゆ柇access_token鏄惁澶辨晥鐨勫�
	public static String SIGN_METHOD = null;// 绛惧悕绠楁硶甯搁噺鍊�
	// package甯搁噺鍊�
	public static String packageValue = null;
	public static String traceid = null;// 娴嬭瘯鐢ㄦ埛id
	
	public static void main(String[] args) {
		String str1 = "BABEBIBOBUBABEBIBOBU";
		String str2="BE";
		int i = str1.indexOf(str2);
			System.out.println(i);
	}
	
}
