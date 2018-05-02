package com.game.smvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name = "Jx_filter_after_sales")
public class JxFilterAfterSales implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private int pro_id;//机器类型
	private int u_id;//用户ID
	private String pro_no;//机器码
	private String ord_color;//净水机颜色
	private String proflt_life;//滤芯
	private Date make_time;//预约时间
	private String contact_person;//联系人
	private String contact_way;//联系方式
	private String user_address;//地址
	private String address_details;//详细地址
	private String fault_cause;//故障原因
	private String specific_reason;//详细原因
	private String fautl_url;//图片Url
	private String ord_managerno;
	private int fas_state;//售后状态
	private int fas_type;//售后类型
	private Date fas_addtime;//添加时间
	private Date fas_modtime;//更新时间
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPro_id() {
		return pro_id;
	}
	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}
	public String getPro_no() {
		return pro_no;
	}
	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}
	public String getOrd_color() {
		return ord_color;
	}
	public void setOrd_color(String ord_color) {
		this.ord_color = ord_color;
	}
	public Date getMake_time() {
		return make_time;
	}
	public void setMake_time(Date make_time) {
		this.make_time = make_time;
	}
	public String getContact_person() {
		return contact_person;
	}
	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}
	public String getContact_way() {
		return contact_way;
	}
	public void setContact_way(String contact_way) {
		this.contact_way = contact_way;
	}
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	public String getAddress_details() {
		return address_details;
	}
	public void setAddress_details(String address_details) {
		this.address_details = address_details;
	}
	public int getFas_state() {
		return fas_state;
	}
	public void setFas_state(int fas_state) {
		this.fas_state = fas_state;
	}
	public int getFas_type() {
		return fas_type;
	}
	public void setFas_type(int fas_type) {
		this.fas_type = fas_type;
	}
	public Date getFas_addtime() {
		return fas_addtime;
	}
	public void setFas_addtime(Date fas_addtime) {
		this.fas_addtime = fas_addtime;
	}
	public String getProflt_life() {
		return proflt_life;
	}
	public void setProflt_life(String proflt_life) {
		this.proflt_life = proflt_life;
	}
	public Date getFas_modtime() {
		return fas_modtime;
	}
	public void setFas_modtime(Date fas_modtime) {
		this.fas_modtime = fas_modtime;
	}
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int u_id) {
		this.u_id = u_id;
	}
	public String getFault_cause() {
		return fault_cause;
	}
	public void setFault_cause(String fault_cause) {
		this.fault_cause = fault_cause;
	}
	public String getSpecific_reason() {
		return specific_reason;
	}
	public void setSpecific_reason(String specific_reason) {
		this.specific_reason = specific_reason;
	}
	public String getFautl_url() {
		return fautl_url;
	}
	public void setFautl_url(String fautl_url) {
		this.fautl_url = fautl_url;
	}
	public String getOrd_managerno() {
		return ord_managerno;
	}
	public void setOrd_managerno(String ord_managerno) {
		this.ord_managerno = ord_managerno;
	}
	
}
