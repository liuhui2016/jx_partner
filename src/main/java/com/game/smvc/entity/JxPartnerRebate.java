package com.game.smvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 合伙人返利
 * @author Administrator
 *
 */
@Table
@Entity(name = "jx_partner_rebate")
public class JxPartnerRebate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String user_name;//编号
	private String withdrawal_order;//提现单号
	private Float total_amount;//总金额
	private Float service_fee;//服务费
	private Float f_renewal;//续费返利
	private Float build_store;//建店返利
	private Float f_installation;//安装费返利
	private Float lower_rebate;//下级返利
	private int w_state;//状态
	private Float buy_combined;//购买合计(总金额)
	private Float renewal_combined;//续费合计(总金额)
	private int sell_wall;
	private int sell_vertical;
	private int sell_desktop;
	private int wall_renew;
	private int vertical_renew;
	private int desktop_renew;
	private Date add_time;
	private Date mod_time;
	private String audit_person;
	private int withdrawal_state;
	private Float rwl_install;//安装费比例
	private Float wdl_fee;//返利比例
	private int par_pact;//是否按照合同
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
	public Float getBuild_store() {
		return build_store;
	}
	public void setBuild_store(Float build_store) {
		this.build_store = build_store;
	}
	public Float getF_installation() {
		return f_installation;
	}
	public void setF_installation(Float f_installation) {
		this.f_installation = f_installation;
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
	public Float getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(Float total_amount) {
		this.total_amount = total_amount;
	}
	public Float getLower_rebate() {
		return lower_rebate;
	}
	public void setLower_rebate(Float lower_rebate) {
		this.lower_rebate = lower_rebate;
	}
	public int getW_state() {
		return w_state;
	}
	public void setW_state(int w_state) {
		this.w_state = w_state;
	}
	public String getWithdrawal_order() {
		return withdrawal_order;
	}
	public void setWithdrawal_order(String withdrawal_order) {
		this.withdrawal_order = withdrawal_order;
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
	
	public String getAudit_person() {
		return audit_person;
	}
	public void setAudit_person(String audit_person) {
		this.audit_person = audit_person;
	}
	public int getWithdrawal_state() {
		return withdrawal_state;
	}
	public void setWithdrawal_state(int withdrawal_state) {
		this.withdrawal_state = withdrawal_state;
	}
	public Float getBuy_combined() {
		return buy_combined;
	}
	public void setBuy_combined(Float buy_combined) {
		this.buy_combined = buy_combined;
	}
	public Float getRenewal_combined() {
		return renewal_combined;
	}
	public void setRenewal_combined(Float renewal_combined) {
		this.renewal_combined = renewal_combined;
	}
	public Float getRwl_install() {
		return rwl_install;
	}
	public void setRwl_install(Float rwl_install) {
		this.rwl_install = rwl_install;
	}
	public Float getWdl_fee() {
		return wdl_fee;
	}
	public void setWdl_fee(Float wdl_fee) {
		this.wdl_fee = wdl_fee;
	}
	public int getPar_pact() {
		return par_pact;
	}
	public void setPar_pact(int par_pact) {
		this.par_pact = par_pact;
	}
	

}
