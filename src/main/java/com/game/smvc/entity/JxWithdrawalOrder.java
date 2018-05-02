package com.game.smvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_withdrawal_order")
public class JxWithdrawalOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int w_id;
	private String user_number;//合伙人编号
	private String real_name;//合伙人姓名
	private String withdrawal_order;//提现单号
	private Float withdrawal_amount;//提现金额
	private int withdrawal_way;//提现方式
	private int withdrawal_state;//提现状态
	private String withdrawal_reason;//失败原因
	private String pay_name;//支付宝账户名
	private String pay_account;//支付宝账户
	private Date add_time;//添加时间
	private Date audit_time;//审核时间
	private Date arrive_time;//到账时间
	private Date last_modtime;//最后更新时间
	private String audit_person;//审核人
	private Date last_time;//上次提现时间
	//保留字段
	private Float total_amount;//总金额
	private Float service_fee;//服务费
	private Float f_renewal;//续费返利
	private Float build_store;//建店返利
	private Float f_installation;//安装费返利
	private Float lower_rebate;//下级返利
	private int sell_wall;//壁挂式
	private int sell_vertical;//壁挂式续费
	private int sell_desktop;//台式
	private int desktop_renew;//台式续费
	private int wall_renew;//立式
	private int vertical_renew;//立式续费
	private Float buy_combined;//购买合计(总金额)
	private Float renewal_combined;//续费合计(总金额)
	private Float rwl_install;//安装费比例
	private Float wdl_fee;//返利比例
	private int par_pact;//是否按照合同
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getW_id() {
		return w_id;
	}
	public void setW_id(int w_id) {
		this.w_id = w_id;
	}
	public String getUser_number() {
		return user_number;
	}
	public void setUser_number(String user_number) {
		this.user_number = user_number;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getWithdrawal_order() {
		return withdrawal_order;
	}
	public void setWithdrawal_order(String withdrawal_order) {
		this.withdrawal_order = withdrawal_order;
	}
	public Float getWithdrawal_amount() {
		return withdrawal_amount;
	}
	public void setWithdrawal_amount(Float withdrawal_amount) {
		this.withdrawal_amount = withdrawal_amount;
	}
	public int getWithdrawal_way() {
		return withdrawal_way;
	}
	public void setWithdrawal_way(int withdrawal_way) {
		this.withdrawal_way = withdrawal_way;
	}
	public int getWithdrawal_state() {
		return withdrawal_state;
	}
	public void setWithdrawal_state(int withdrawal_state) {
		this.withdrawal_state = withdrawal_state;
	}
	public String getWithdrawal_reason() {
		return withdrawal_reason;
	}
	public void setWithdrawal_reason(String withdrawal_reason) {
		this.withdrawal_reason = withdrawal_reason;
	}
	public Date getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}
	public Date getAudit_time() {
		return audit_time;
	}
	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}
	public Date getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(Date arrive_time) {
		this.arrive_time = arrive_time;
	}
	public Date getLast_modtime() {
		return last_modtime;
	}
	public void setLast_modtime(Date last_modtime) {
		this.last_modtime = last_modtime;
	}
	public String getPay_name() {
		return pay_name;
	}
	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
	public String getPay_account() {
		return pay_account;
	}
	public void setPay_account(String pay_account) {
		this.pay_account = pay_account;
	}
	
	public String getAudit_person() {
		return audit_person;
	}
	public void setAudit_person(String audit_person) {
		this.audit_person = audit_person;
	}
	public Date getLast_time() {
		return last_time;
	}
	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}
	public Float getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(Float total_amount) {
		this.total_amount = total_amount;
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
	public Float getLower_rebate() {
		return lower_rebate;
	}
	public void setLower_rebate(Float lower_rebate) {
		this.lower_rebate = lower_rebate;
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
	public int getDesktop_renew() {
		return desktop_renew;
	}
	public void setDesktop_renew(int desktop_renew) {
		this.desktop_renew = desktop_renew;
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
