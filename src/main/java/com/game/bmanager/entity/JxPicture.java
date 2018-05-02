package com.game.bmanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_picture")
public class JxPicture implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3941723724885646940L;
	
	private Long id;
	
	private Long protype_id;
	
	private String pic_color;
	
	private String pic_name;
	
	private String pic_url;
	
	private Date pic_addtime;
	
	private Date pic_modtime;
	
	private Integer pic_default;
	
	private String pic_tone;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProtype_id() {
		return protype_id;
	}

	public void setProtype_id(Long protype_id) {
		this.protype_id = protype_id;
	}

	public String getPic_color() {
		return pic_color;
	}

	public void setPic_color(String pic_color) {
		this.pic_color = pic_color;
	}

	public String getPic_name() {
		return pic_name;
	}

	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public Date getPic_addtime() {
		return pic_addtime;
	}

	public void setPic_addtime(Date pic_addtime) {
		this.pic_addtime = pic_addtime;
	}

	public Date getPic_modtime() {
		return pic_modtime;
	}

	public void setPic_modtime(Date pic_modtime) {
		this.pic_modtime = pic_modtime;
	}
	

	public String getPic_tone() {
		return pic_tone;
	}

	public void setPic_tone(String pic_tone) {
		this.pic_tone = pic_tone;
	}

	public JxPicture() {
		super();
	}
	

	public Integer getPic_default() {
		return pic_default;
	}

	public void setPic_default(Integer pic_default) {
		this.pic_default = pic_default;
	}

	public JxPicture(Long protype_id, String pic_color, String pic_name,
			String pic_url, Date pic_addtime, Date pic_modtime,
			Integer pic_default, String pic_tone) {
		super();
		this.protype_id = protype_id;
		this.pic_color = pic_color;
		this.pic_name = pic_name;
		this.pic_url = pic_url;
		this.pic_addtime = pic_addtime;
		this.pic_modtime = pic_modtime;
		this.pic_default = pic_default;
		this.pic_tone = pic_tone;
	}
	
}
