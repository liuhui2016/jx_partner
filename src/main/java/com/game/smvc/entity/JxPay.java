package com.game.smvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_pay")
public class JxPay implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7190176772907734872L;
	
	private Long id;
	private Long pay_typeid;
	private Long pay_typename;
	private Float pay_totalmoney;
	private Float pay_flow;
	private Float pay_unitprice;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pay_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPay_typeid() {
		return pay_typeid;
	}

	public void setPay_typeid(Long pay_typeid) {
		this.pay_typeid = pay_typeid;
	}

	public Long getPay_typename() {
		return pay_typename;
	}

	public void setPay_typename(Long pay_typename) {
		this.pay_typename = pay_typename;
	}

	public Float getPay_totalmoney() {
		return pay_totalmoney;
	}

	public void setPay_totalmoney(Float pay_totalmoney) {
		this.pay_totalmoney = pay_totalmoney;
	}

	public Float getPay_flow() {
		return pay_flow;
	}

	public void setPay_flow(Float pay_flow) {
		this.pay_flow = pay_flow;
	}

	public Float getPay_unitprice() {
		return pay_unitprice;
	}

	public void setPay_unitprice(Float pay_unitprice) {
		this.pay_unitprice = pay_unitprice;
	}

	public JxPay() {
		super();
	}

	public JxPay(Long pay_typeid, Long pay_typename, Float pay_totalmoney,
			Float pay_flow, Float pay_unitprice) {
		super();
		this.pay_typeid = pay_typeid;
		this.pay_typename = pay_typename;
		this.pay_totalmoney = pay_totalmoney;
		this.pay_flow = pay_flow;
		this.pay_unitprice = pay_unitprice;
	}
	
}
