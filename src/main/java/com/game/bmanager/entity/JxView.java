package com.game.bmanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_tmpprodetail")
public class JxView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6889186269840031788L;

	private Long id;

	private Long prot_id;
	
	private String prod_name;

	private String pic_color;

	private String pic_tone;

	private Long prod_picid;

	private Date prod_addtime;

	private Date prod_modtime;

	private String pic_url;

	@Id
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

	public String getPic_color() {
		return pic_color;
	}

	public void setPic_color(String pic_color) {
		this.pic_color = pic_color;
	}

	public String getPic_tone() {
		return pic_tone;
	}

	public void setPic_tone(String pic_tone) {
		this.pic_tone = pic_tone;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public Long getProd_picid() {
		return prod_picid;
	}

	public void setProd_picid(Long prod_picid) {
		this.prod_picid = prod_picid;
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
	
	public JxView() {
		super();
	}

	public JxView(Long prot_id, String prod_name,
			String pic_color, String pic_tone, Long prod_picid,
			Date prod_addtime, Date prod_modtime, String pic_url) {
		super();
		this.prot_id = prot_id;
		this.prod_name = prod_name;
		this.pic_color = pic_color;
		this.pic_tone = pic_tone;
		this.prod_picid = prod_picid;
		this.prod_addtime = prod_addtime;
		this.prod_modtime = prod_modtime;
		this.pic_url = pic_url;
	}
	
}
