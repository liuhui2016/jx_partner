package com.game.bmanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_opinon")
public class JxOpinon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4635731529564963174L;

	private Long id;

	private String op_user;
	private String op_phone;
	private String op_titel;
	private String op_detail;
	private Date op_addtime;
	private Date op_modtime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOp_user() {
		return op_user;
	}

	public void setOp_user(String op_user) {
		this.op_user = op_user;
	}

	public String getOp_phone() {
		return op_phone;
	}

	public void setOp_phone(String op_phone) {
		this.op_phone = op_phone;
	}

	public String getOp_titel() {
		return op_titel;
	}

	public void setOp_titel(String op_titel) {
		this.op_titel = op_titel;
	}

	public String getOp_detail() {
		return op_detail;
	}

	public void setOp_detail(String op_detail) {
		this.op_detail = op_detail;
	}

	public Date getOp_addtime() {
		return op_addtime;
	}

	public void setOp_addtime(Date op_addtime) {
		this.op_addtime = op_addtime;
	}

	public Date getOp_modtime() {
		return op_modtime;
	}

	public void setOp_modtime(Date op_modtime) {
		this.op_modtime = op_modtime;
	}
	
	public JxOpinon(){
		super();
	}

	public JxOpinon(String op_user, String op_phone, String op_titel,
			String op_detail, Date op_addtime, Date op_modtime) {
		super();
		this.op_user = op_user;
		this.op_phone = op_phone;
		this.op_titel = op_titel;
		this.op_detail = op_detail;
		this.op_addtime = op_addtime;
		this.op_modtime = op_modtime;
	}

	@Override
	public String toString() {
		return "Opinon [id=" + id + ", op_user=" + op_user + ", op_phone="
				+ op_phone + ", op_titel=" + op_titel + ", op_detail="
				+ op_detail + ", op_addtime=" + op_addtime + ", op_modtime="
				+ op_modtime + "]";
	}

}
