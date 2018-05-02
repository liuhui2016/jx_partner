package com.game.smvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_rebates")
public class JxRebates implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String pro_name;
	private String par_level;
	private int pro_id;
	private Float service_fee;//服务费
	private Float f_renewal;//服务续费
	private Float f_shop;//建店补贴
	private Float f_cost;//安装费
	private Float lower_rebate;//下级返利
	private Float f_install;//装机费
	private Float rwl_install;//维护费
	private Float special_service_charge;//未按合同的服务费
	private Float special_service_renewal;//未按合同的服务费续费
	private Date f_addtime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPar_level() {
		return par_level;
	}
	public void setPar_level(String par_level) {
		this.par_level = par_level;
	}
	public int getPro_id() {
		return pro_id;
	}
	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}
	public Float getService_fee() {
		return service_fee;
	}
	public void setService_fee(Float service_fee) {
		this.service_fee = service_fee;
	}
	public Float getF_renewal() {
		return f_renewal;
	}
	public void setF_renewal(Float f_renewal) {
		this.f_renewal = f_renewal;
	}
	public Float getF_shop() {
		return f_shop;
	}
	public void setF_shop(Float f_shop) {
		this.f_shop = f_shop;
	}
	public Float getF_cost() {
		return f_cost;
	}
	public void setF_cost(Float f_cost) {
		this.f_cost = f_cost;
	}
	public Date getF_addtime() {
		return f_addtime;
	}
	public void setF_addtime(Date f_addtime) {
		this.f_addtime = f_addtime;
	}
	public Float getLower_rebate() {
		return lower_rebate;
	}
	public void setLower_rebate(Float lower_rebate) {
		this.lower_rebate = lower_rebate;
	}
	public Float getSpecial_service_charge() {
		return special_service_charge;
	}
	public void setSpecial_service_charge(Float special_service_charge) {
		this.special_service_charge = special_service_charge;
	}
	public Float getSpecial_service_renewal() {
		return special_service_renewal;
	}
	public void setSpecial_service_renewal(Float special_service_renewal) {
		this.special_service_renewal = special_service_renewal;
	}
	public Float getF_install() {
		return f_install;
	}
	public void setF_install(Float f_install) {
		this.f_install = f_install;
	}
	public Float getRwl_install() {
		return rwl_install;
	}
	public void setRwl_install(Float rwl_install) {
		this.rwl_install = rwl_install;
	}
	

}
