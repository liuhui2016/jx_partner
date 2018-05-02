package com.game.smvc.entity.result;

import java.util.List;

/**
 * 此方法以废弃
 * @author Administrator
 *
 */
public class DataResult extends Result {
	protected List data;

	public DataResult(Errcode errcode, List data) {
		super(errcode, errcode.getMsg());
		this.data = data;
	}

	public DataResult(Errcode errcode, String msg, List data) {
		super(errcode, msg);
		this.data = data;
	}
	
	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
	
}
