package com.game.smvc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 发布订单类
 * @author lh
 *
 */

@Table
@Entity(name = "jx_release_order")
public class JxReleaseOrder {

	public int fb_id;
	public String ord_no;
	public int u_id;
	public int fb_state;
	public Float fb_price;
	public int ord_way;
	public String fb_type;//发布类型
	public String fb_phone;
	private Date fb_addtime;
	private Date fb_modtime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getFb_id() {
		return fb_id;
	}
	public void setFb_id(int fb_id) {
		this.fb_id = fb_id;
	}
	public String getOrd_no() {
		return ord_no;
	}
	public void setOrd_no(String ord_no) {
		this.ord_no = ord_no;
	}
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public int getFb_state() {
		return fb_state;
	}
	public void setFb_state(int fb_state) {
		this.fb_state = fb_state;
	}
	public Float getFb_price() {
		return fb_price;
	}
	public void setFb_price(Float fb_price) {
		this.fb_price = fb_price;
	}
	public int getOrd_way() {
		return ord_way;
	}
	public void setOrd_way(int ord_way) {
		this.ord_way = ord_way;
	}
	public String getFb_type() {
		return fb_type;
	}
	public void setFb_type(String fb_type) {
		this.fb_type = fb_type;
	}
	public String getFb_phone() {
		return fb_phone;
	}
	public void setFb_phone(String fb_phone) {
		this.fb_phone = fb_phone;
	}
	@Override
	public String toString() {
		return "JxReleaseOrder [fb_id=" + fb_id + ", ord_no=" + ord_no
				+ ", u_id=" + u_id + ", fb_state=" + fb_state + ", fb_price="
				+ fb_price + ", ord_way=" + ord_way + ", fb_type=" + fb_type
				+ ", fb_phone=" + fb_phone + "]";
	}
	public Date getFb_addtime() {
		return fb_addtime;
	}
	public void setFb_addtime(Date fb_addtime) {
		this.fb_addtime = fb_addtime;
	}
	public Date getFb_modtime() {
		return fb_modtime;
	}
	public void setFb_modtime(Date fb_modtime) {
		this.fb_modtime = fb_modtime;
	}
	
	
}
