package com.game.smvc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 当前城市水质报告信息
 * @author Administrator
 *
 */
@Entity
@Table(name = "jx_water")
public class JxWater {

	private int water_id;
	private String city_code;
	private int water_tds;
	private java.util.Date water_addtime;
	private java.util.Date water_modtime;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getWater_id() {
		return water_id;
	}
	public void setWater_id(int water_id) {
		this.water_id = water_id;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public int getWater_tds() {
		return water_tds;
	}
	public void setWater_tds(int water_tds) {
		this.water_tds = water_tds;
	}
	public java.util.Date getWater_addtime() {
		return water_addtime;
	}
	public void setWater_addtime(java.util.Date water_addtime) {
		this.water_addtime = water_addtime;
	}
	public java.util.Date getWater_modtime() {
		return water_modtime;
	}
	public void setWater_modtime(java.util.Date water_modtime) {
		this.water_modtime = water_modtime;
	}
	
	
}
