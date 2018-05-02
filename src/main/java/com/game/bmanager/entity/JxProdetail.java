package com.game.bmanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_prodetail")
public class JxProdetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2899249489103339527L;
	
	private Long id;
	
	private Long prot_id;
	
	private String prod_name;
	
	private Long prod_picid;
	
	private String prod_spec;
	
	private Date prod_addtime;
	
	private Date prod_modtime;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProt_id() {
		return prot_id;
	}

	public void setProt_id(Long prot_id) {
		this.prot_id = prot_id;
	}


	public String getProd_name() {
		return prod_name;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}

	public Long getProd_picid() {
		return prod_picid;
	}

	public void setProd_picid(Long prod_picid) {
		this.prod_picid = prod_picid;
	}

	public String getProd_spec() {
		return prod_spec;
	}

	public void setProd_spec(String prod_spec) {
		this.prod_spec = prod_spec;
	}

	public Date getProd_addtime() {
		return prod_addtime;
	}

	public void setProd_addtime(Date prod_addtime) {
		this.prod_addtime = prod_addtime;
	}

	public Date getProd_modtime() {
		return prod_modtime;
	}

	public void setProd_modtime(Date prod_modtime) {
		this.prod_modtime = prod_modtime;
	}
	

	public JxProdetail() {
		super();
	}

	public JxProdetail(Long prot_id, String prod_name,
			Long prod_picid, String prod_spec, Date prod_addtime,
			Date prod_modtime) {
		super();
		this.prot_id = prot_id;
		this.prod_name = prod_name;
		this.prod_picid = prod_picid;
		this.prod_spec = prod_spec;
		this.prod_addtime = prod_addtime;
		this.prod_modtime = prod_modtime;
	}
	
}
