package com.game.smvc.entity.result;


public enum Errors implements Errcode {
	/*****    常见操作          ****/
	OK(0, "ok"), 
	EXCEPTION_UNKNOW(2,"未知异常"), 
	SERVER_DATA_SAVE_FAIL(1, "操作失败"), 
	PARAM_ERROR(1,"参数异常"),
	
	/*********   用户         *********/
	USER_NOT＿LOGIN(3,"用户未登录，请先登录"),
	USER_ERROR_PHONE_FORMAT(3, 100, "手机格式不正确"),
	USER_ERROR_PHONE_EXIST(3, "该手机号码已经注册，请直接登陆"), 
    USER_ERROR_CODE_WRONG(3,"验证码错误"),
	USER_ERROR_NOT_EXIST(3, "用户不存在"), 
	USER_ERROR_NOT_PRODUCT(3, "您名下没有商品"),
	NO_ORDERS(3,"无多余订单信息"),
	MOBILE_ERROR_NOT_EXIST(3, "该手机号未注册，请先注册账号"), 
	USER_ERROR_PASSWORD(3, "密码错误"), 
	USER_ERROR_FREEZE(3, "账号被冻结"), 
	USER_ERROR_OLD_PASSWORD(3, "原始密码错误"), 
	USER_ERROR_QUESTION_EMPTY(3, "请先设置安全问题"), 
	USER_ERROR_QUESTION(3, "安全问题错误"), 
	USER_ERROR_MAIL(3, "邮件发送失败"), 
	NO_FILTER_MESSAGES_IN_THE_PROVINCES(3,"暂无该省份滤芯消息"),
	NO_FILTER_MESSAGES(3,"暂无该滤芯数据"),
	NO_PRICE(3,"暂无金额信息"),
	NO_MODTIME(3,"暂无更新时间信息"),
	NO_STATE(3,"暂无绑定水机"),
	NO_TDS_INFORMATION(3,"暂无TDS更新数据，请稍后查询！"),
	NO_TDS(3,"暂无TDS数据"),
	NO_TIME_INFORMATION(3,"暂无时间数据"),
	NO_COST_INFORMATION_ON_THE_TRAFFIC(3,"暂无该流量费用信息"),
	USER_ERROR_SETTIME(3, "安装时间不能早于现在"), 
	USER_ERROR_SMS(3,"短信发送失败"), 
	USER_ERROR_SMS_CHECK(3, "短信验证码错误或不存在"), 
	USER_ERROR_SMS_UNCHECK(3, "手机号码未验证"), 
	USER_ERROR_SMS_CHECKED(3, "该手机号码已经验证，请直接登陆"), 
	USER_ERROR_SMS_EXPIRE(3, "短信验证码过期"), 
	USER_ERROR_SMS_LIMIT_REPEAT(3, "操作太频繁请稍后再试"), 
	USER_ERROR_SMS_LIMIT_DAY(3, "该账号已达到今日发送上限"), 
	USER_ERROR_REGISTER(3, "手机号或密码不能为空"),
	USER_ERROR_SHARE1(3, "无法分享给已经分享的人"),
	FILTER_TEMPORARILY_WITHOUT_REPLACEMENT(3,"滤芯暂不用更换"),
	USER_ERROR_UPLOAD(3,"上传的表单格式不为multipart/form-data"),
	USER_ERROR_WRONGORDERNO(3,"订单编号错误,或者订单未支付,或者订单已经绑定!"),
	UNABLE_TO_RETRIEVE(3,"订单编号错误,或者订单未支付,或者订单未绑定,无法找回!"),
	NOT_RENEW_THE_ORDER(3,"该订单暂无续费信息"),
	MACHINE_CODE_ERROR(3,"机器码错误,请输入正确的机器码！"),
	USER_ERROR_NOT_ORDER(3,"该订单不存在"),
	COMMODITY_INFORMATION_ERROR(3,"商品信息错误,请确定！"),
	DELETE_FAILED(1,"失败"),
	SUCCESSFULLY_DELETE(0,"成功"),
	NO_INFORMATION_THE_TDS(3,"暂无该TDS信息"),
	USER_ERROR_REPEAT(3,"交易进行中，请不要重复提交"),
	TRANSACTION_FAILURE(3,"交易失败，详情请咨询95516"),
	LACK_OF_BALANCE(3,"卡上余额不足"),
	REPLACE_THE_BANK_CARD(3,"交易失败，发卡银联不支持该商户，请跟换其他银行卡"),
	CARD_NUMBER_IS_INVALID(3,"输入的卡号不正确，请确认后输入"),
	DOSE_NOT_SUPPORT_THE_BUSINESS(3,"您的银行卡暂不支持该业务，请向您的银行或95516咨询"),
	TRANSACTION_VALUE_OF_TRANSFINITE(3,"交易金额超限"),
	THIS_TRANSCATON_CANNONT_BE_FOUNT(3,"查无此交易"),
	SYSTEM_IS_NOT_OPEN(3,"系统未开发或暂时关闭，请稍后重试"),
	JSON_ERROR_NOTJSON(3,"JSON格式不对"),
	ERROR_CODE(3,"-5"),
	IS_EMPTY(3,"null"),
	WATER_QUANTITY_DATA_NO_UPDATE(3,"饮水量信息暂无更新，请稍后查询！"),
	NOT_A_TEST_ACCOUNT(3,"该账号不是测试账号，暂无解绑功能！"),
	USER_ERROR_NOT_CORRECT(3,"订单格式不正确"),
	PARTNER_ERROR_NOTFOUND(3,"产品经理不存在"),
	THE_CITY_NO_INFORMATION(3,"暂无该城市信息"),
	NO_PARTNER(3,"合伙人不存在"),
	THIS_MACHINE_IS_NOT_A_PARTNER(3,"该机主不是合伙人"),
	SECURITY_VERIFICATION_FAILED(4,"安全验证失败"),//唯一
	NO_PERMISSIONS(5,"您当前没有权限提现"),
	THERE_IS_NO_BINDING_ALIPAY(3,"暂无绑定支付宝"),
	THE_WITHDRAWAL_FAILED(3,"提现发起失败"),
	THE_WITHDRAWAL_NUMBER_IS_INCORRECT(3,"提现编号不正确"),
	THE_AMOUNT_IS_TOO_LOE_TO_WITHDRAW_MAX_CASH(3,"提现金额不能超过5万元"),
	THE_AMOUNT_IS_TOO_LOE_TO_WITHDRAW_CASH(3,"提现金额不低于0.1元"),
	UNBUNDLING_FAILURE(3,"支付宝账号解绑失败"),
	INSUFFICIENT_PERMISSIONS(3,"权限不足"),
	THE_WITHDRAWAL_ORDER_IS_incorrect(3,"该提现订单号不正确"),
	THE_REBATE_RATIO_IS_NOT_CORRECT(3,"返利比例不能小于1%"),
	NO_REVIEW_OF_SUCCESSFUL_ORDERS(3,"提现单号不正确,或未审核通过"),
	THE_PARTNERSHIP_LEVEL_IS_NOT_CORRECT(3,"合伙人级别不正确"),
	THE_ARE_NOT_SATISFIED_WITH_THE_REGISTRATION_CONDITION(3,"您暂不满足注册条件"),
	THE_USER_IS_REGISTERED_PLEASE_LOGIN_DIRECTLY(3,"用户已注册，请直接登入"),
	THE_RECOMMENDER_CODE_DOES_NOT_EXIST(3,"推荐人编码不存在"),
	REFERENCES_DO_NOT_EXIST(3,"推荐人不存在"),
	USER_ID_CARD_EXISTS(3,"用户身份证以存在"),
	THE_ORDER_NUMBER_IS_REGISTERED(3,"订单编号已注册"),
    PRODUCT_ERROR_NO(3,"任务编号错误")
	;
	
	
	
	private int result;
	private int errcode;
	private String msg;

	private Errors(int result) {
		this.result = result;
	}

	private Errors(int result, int errcode) {
		this(result);
		this.errcode = errcode;
	}

	private Errors(int result, String msg) {
		this(result);
		this.errcode = 0;
		this.msg = msg;
	}

	private Errors(int result, int errcode, String msg) {
		this(result, errcode);
		this.msg = msg;
	}

	public int getResult() {
		return this.result;
	}

	public int getErrcode() {
		return this.errcode;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}