package com.game.smvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name = "jx_draw_people")
public class JxDrawPeople implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String tkr_id;//提款日编号
	private String tkr_name;//提款日姓名
	private String by_tkr_id;//被提款人编号
	private String by_tkr_name;//被提款人姓名
	private String by_super_tkr_id;//被提款人上级编号
	private String by_super_name;//被提款人上级姓名
	private String withdrawal_order;//提款订单
	private Float by_tkr_rebates;
	private Float by_tkr_install;
	private Float by_tkr_total_money;
	private int tkr_state;//状态
	private int sell_wall;
	private int sell_vertical;
	private int sell_desktop;
	private int wall_renew;
	private int vertical_renew;
	private int desktop_renew;
	private Float service_fee;//服务费
	private Float f_renewal;//服务续费
	private Float f_installation;//维护费
	private Float lower_rebate;//下级返利
	private Date add_time;
	private Date mod_time;
	private Float total_money;
	private int withdrawal_state;//提现状态
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTkr_id() {
		return tkr_id;
	}
	public void setTkr_id(String tkr_id) {
		this.tkr_id = tkr_id;
	}
	public String getTkr_name() {
		return tkr_name;
	}
	public void setTkr_name(String tkr_name) {
		this.tkr_name = tkr_name;
	}
	public String getBy_tkr_id() {
		return by_tkr_id;
	}
	public void setBy_tkr_id(String by_tkr_id) {
		this.by_tkr_id = by_tkr_id;
	}
	public String getBy_tkr_name() {
		return by_tkr_name;
	}
	public void setBy_tkr_name(String by_tkr_name) {
		this.by_tkr_name = by_tkr_name;
	}
	public String getBy_super_tkr_id() {
		return by_super_tkr_id;
	}
	public void setBy_super_tkr_id(String by_super_tkr_id) {
		this.by_super_tkr_id = by_super_tkr_id;
	}
	public String getBy_super_name() {
		return by_super_name;
	}
	public void setBy_super_name(String by_super_name) {
		this.by_super_name = by_super_name;
	}
	public String getWithdrawal_order() {
		return withdrawal_order;
	}
	public void setWithdrawal_order(String withdrawal_order) {
		this.withdrawal_order = withdrawal_order;
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
	public int getTkr_state() {
		return tkr_state;
	}
	public void setTkr_state(int tkr_state) {
		this.tkr_state = tkr_state;
	}
	public Float getBy_tkr_rebates() {
		return by_tkr_rebates;
	}
	public void setBy_tkr_rebates(Float by_tkr_rebates) {
		this.by_tkr_rebates = by_tkr_rebates;
	}
	public Float getBy_tkr_install() {
		return by_tkr_install;
	}
	public void setBy_tkr_install(Float by_tkr_install) {
		this.by_tkr_install = by_tkr_install;
	}
	public Float getBy_tkr_total_money() {
		return by_tkr_total_money;
	}
	public void setBy_tkr_total_money(Float by_tkr_total_money) {
		this.by_tkr_total_money = by_tkr_total_money;
	}
	public int getSell_wall() {
		return sell_wall;
	}
	public void setSell_wall(int sell_wall) {
		this.sell_wall = sell_wall;
	}
	public int getSell_vertical() {
		return sell_vertical;
	}
	public void setSell_vertical(int sell_vertical) {
		this.sell_vertical = sell_vertical;
	}
	public int getSell_desktop() {
		return sell_desktop;
	}
	public void setSell_desktop(int sell_desktop) {
		this.sell_desktop = sell_desktop;
	}
	public int getWall_renew() {
		return wall_renew;
	}
	public void setWall_renew(int wall_renew) {
		this.wall_renew = wall_renew;
	}
	public int getVertical_renew() {
		return vertical_renew;
	}
	public void setVertical_renew(int vertical_renew) {
		this.vertical_renew = vertical_renew;
	}
	public int getDesktop_renew() {
		return desktop_renew;
	}
	public void setDesktop_renew(int desktop_renew) {
		this.desktop_renew = desktop_renew;
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
	public Float getF_installation() {
		return f_installation;
	}
	public void setF_installation(Float f_installation) {
		this.f_installation = f_installation;
	}
	public Float getLower_rebate() {
		return lower_rebate;
	}
	public void setLower_rebate(Float lower_rebate) {
		this.lower_rebate = lower_rebate;
	}
	public Float getTotal_money() {
		return total_money;
	}
	public void setTotal_money(Float total_money) {
		this.total_money = total_money;
	}
	public int getWithdrawal_state() {
		return withdrawal_state;
	}
	public void setWithdrawal_state(int withdrawal_state) {
		this.withdrawal_state = withdrawal_state;
	}

	
	

}
