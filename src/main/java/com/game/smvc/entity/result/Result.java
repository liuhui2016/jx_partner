package com.game.smvc.entity.result;

import com.game.smvc.util.JsonUtil;

public class Result implements Errcode {
	protected int result;
	protected int errcode;
	protected String msg;

	public Result() {
	}

	public Result(int result) {
		this.result = result;
	}

	public Result(int result, int errcode) {
		this(result);
		this.errcode = errcode;
	}

	public Result(int result, String msg) {
		this(result);
		this.errcode = 0;
		this.msg = msg;
	}

	public Result(int result, int errcode, String msg) {
		this(result, errcode);
		this.msg = msg;
	}

	public Result(Errcode errcode) {
		this(errcode, errcode.getMsg());
	}

	public Result(Errcode errcode, String msg) {
		setErrcode(errcode, msg);
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

	public void setErrcode(Errcode errcode) {
		this.result = errcode.getResult();
		this.errcode = errcode.getErrcode();
		this.msg = errcode.getMsg();
	}

	public void setErrcode(Errcode errcode, String msg) {
		this.result = errcode.getResult();
		this.errcode = errcode.getErrcode();
		this.msg = msg;
	}

	public String toString() {
		return JsonUtil.toJson(this);
	}
}
