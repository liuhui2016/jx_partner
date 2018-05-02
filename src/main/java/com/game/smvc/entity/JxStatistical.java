package com.game.smvc.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_statistical")
public class JxStatistical {

	private int sta_id;
	private int u_id;
	private String ord_no;
	private String pro_no;
	private int sta_tds;
	private int sta_temperature;
	private String pro_restflow;//饮水量
	private String output_water;//制水量
	private java.util.Date sta_addtime;
	private java.util.Date sta_modtime;
	
	public JxStatistical() {
		super();
	}
	
	public JxStatistical(List<Map<String, Object>> JxStatistical) {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getSta_id() {
		return sta_id;
	}
	public void setSta_id(int sta_id) {
		this.sta_id = sta_id;
	}
	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getOrd_no() {
		return ord_no;
	}

	public void setOrd_no(String ord_no) {
		this.ord_no = ord_no;
	}

	public String getPro_no() {
		return pro_no;
	}

	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}
	public int getSta_tds() {
		return sta_tds;
	}
	public void setSta_tds(int sta_tds) {
		this.sta_tds = sta_tds;
	}
	public int getSta_temperature() {
		return sta_temperature;
	}
	public void setSta_temperature(int sta_temperature) {
		this.sta_temperature = sta_temperature;
	}
	public String getPro_restflow() {
		return pro_restflow;
	}
	public void setPro_restflow(String pro_restflow) {
		this.pro_restflow = pro_restflow;
	}
	public java.util.Date getSta_addtime() {
		return sta_addtime;
	}
	public void setSta_addtime(java.util.Date sta_addtime) {
		this.sta_addtime = sta_addtime;
	}

	public java.util.Date getSta_modtime() {
		return sta_modtime;
	}

	public void setSta_modtime(java.util.Date sta_modtime) {
		this.sta_modtime = sta_modtime;
	}

	public String getOutput_water() {
		return output_water;
	}

	public void setOutput_water(String output_water) {
		this.output_water = output_water;
	}
	
	
}
