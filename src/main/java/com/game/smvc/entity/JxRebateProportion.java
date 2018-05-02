package com.game.smvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name = "jx_rebate_proportion")
public class JxRebateProportion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String real_name;
	private String user_number;
	private int par_level;
	private String most_superior_id;//最上级id
	private Float rp_rebates;//返利比例
	private Float rp_installed;//装机比例
	private Float rp_total;//总计
	private int super_level;
	private Float super_rebates;//上级返利比例
	private Float super_installed;//上级装机比例
	private Float super_totall;//上级总计比例
	private Date add_time;
	private Date mod_time;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getUser_number() {
		return user_number;
	}
	public void setUser_number(String user_number) {
		this.user_number = user_number;
	}
	public int getPar_level() {
		return par_level;
	}
	public void setPar_level(int par_level) {
		this.par_level = par_level;
	}
	public Float getRp_rebates() {
		return rp_rebates;
	}
	public void setRp_rebates(Float rp_rebates) {
		this.rp_rebates = rp_rebates;
	}
	public Float getRp_installed() {
		return rp_installed;
	}
	public void setRp_installed(Float rp_installed) {
		this.rp_installed = rp_installed;
	}
	public Float getRp_total() {
		return rp_total;
	}
	public void setRp_total(Float rp_total) {
		this.rp_total = rp_total;
	}
	public Date getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	public Date getMod_time() {
		return mod_time;
	}
	public void setMod_time(Date mod_time) {
		this.mod_time = mod_time;
	}
	public String getMost_superior_id() {
		return most_superior_id;
	}
	public void setMost_superior_id(String most_superior_id) {
		this.most_superior_id = most_superior_id;
	}
	public Float getSuper_rebates() {
		return super_rebates;
	}
	public void setSuper_rebates(Float super_rebates) {
		this.super_rebates = super_rebates;
	}
	public Float getSuper_installed() {
		return super_installed;
	}
	public void setSuper_installed(Float super_installed) {
		this.super_installed = super_installed;
	}
	public Float getSuper_totall() {
		return super_totall;
	}
	public void setSuper_totall(Float super_totall) {
		this.super_totall = super_totall;
	}
	public int getSuper_level() {
		return super_level;
	}
	public void setSuper_level(int super_level) {
		this.super_level = super_level;
	}
	
}
