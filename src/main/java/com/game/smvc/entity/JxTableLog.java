package com.game.smvc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 平板日志信息
 * @author lh
 *
 */
@Entity
@Table(name = "jx_table_log")
public class JxTableLog {

	private int tl_id;
	private String pro_no;
	private String tl_param;
	private String tl_option;
	private String tl_netdate;
	private String tl_localdate;
	private String apk_version;
	private String pb_version;
	private Date tl_addtime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getTl_id() {
		return tl_id;
	}
	public void setTl_id(int tl_id) {
		this.tl_id = tl_id;
	}
	public String getPro_no() {
		return pro_no;
	}
	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}
	public String getTl_param() {
		return tl_param;
	}
	public void setTl_param(String tl_param) {
		this.tl_param = tl_param;
	}
	public String getTl_option() {
		return tl_option;
	}
	public void setTl_option(String tl_option) {
		this.tl_option = tl_option;
	}
	public String getTl_netdate() {
		return tl_netdate;
	}
	public void setTl_netdate(String tl_netdate) {
		this.tl_netdate = tl_netdate;
	}
	public String getTl_localdate() {
		return tl_localdate;
	}
	public void setTl_localdate(String tl_localdate) {
		this.tl_localdate = tl_localdate;
	}
	public String getApk_version() {
		return apk_version;
	}
	public void setApk_version(String apk_version) {
		this.apk_version = apk_version;
	}
	public String getPb_version() {
		return pb_version;
	}
	public void setPb_version(String pb_version) {
		this.pb_version = pb_version;
	}
	public Date getTl_addtime() {
		return tl_addtime;
	}
	public void setTl_addtime(Date tl_addtime) {
		this.tl_addtime = tl_addtime;
	}
	
	
}
