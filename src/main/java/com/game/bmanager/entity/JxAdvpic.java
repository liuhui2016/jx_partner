package com.game.bmanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "jx_advpic")
public class JxAdvpic implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1238463561134183392L;
	
	private Long id;
	
	private String adv_phone;
	
	private String adv_imgurl;
	
	private String adv_dir;
	
	private String adv_name;
	
	private Long adv_type;
	
	private Date adv_vaildtime;
	
	private Date adv_invildtime;
	
	private Date adv_addtime;
	
	private Date adv_modtime;
	
	private String adv_url;
	
	private String menu_name;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Formula("(select t.menu_name from  jx_menu t where t.id = adv_type)")
	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	public String getAdv_phone() {
		return adv_phone;
	}

	public void setAdv_phone(String adv_phone) {
		this.adv_phone = adv_phone;
	}

	public String getAdv_imgurl() {
		return adv_imgurl;
	}

	public void setAdv_imgurl(String adv_imgurl) {
		this.adv_imgurl = adv_imgurl;
	}

	public String getAdv_dir() {
		return adv_dir;
	}

	public void setAdv_dir(String adv_dir) {
		this.adv_dir = adv_dir;
	}

	public String getAdv_name() {
		return adv_name;
	}

	public void setAdv_name(String adv_name) {
		this.adv_name = adv_name;
	}

	public Long getAdv_type() {
		return adv_type;
	}

	public void setAdv_type(Long adv_type) {
		this.adv_type = adv_type;
	}

	public Date getAdv_vaildtime() {
		return adv_vaildtime;
	}

	public void setAdv_vaildtime(Date adv_vaildtime) {
		this.adv_vaildtime = adv_vaildtime;
	}

	public Date getAdv_invildtime() {
		return adv_invildtime;
	}

	public void setAdv_invildtime(Date adv_invildtime) {
		this.adv_invildtime = adv_invildtime;
	}

	public Date getAdv_addtime() {
		return adv_addtime;
	}

	public void setAdv_addtime(Date adv_addtime) {
		this.adv_addtime = adv_addtime;
	}

	public Date getAdv_modtime() {
		return adv_modtime;
	}

	public void setAdv_modtime(Date adv_modtime) {
		this.adv_modtime = adv_modtime;
	}

	public String getAdv_url() {
		return adv_url;
	}

	public void setAdv_url(String adv_url) {
		this.adv_url = adv_url;
	}

	public JxAdvpic(String adv_phone, String adv_imgurl, String adv_dir,
			String adv_name, Long adv_type, Date adv_vaildtime,
			Date adv_invildtime, Date adv_addtime, Date adv_modtime,
			String adv_url) {
		super();
		this.adv_phone = adv_phone;
		this.adv_imgurl = adv_imgurl;
		this.adv_dir = adv_dir;
		this.adv_name = adv_name;
		this.adv_type = adv_type;
		this.adv_vaildtime = adv_vaildtime;
		this.adv_invildtime = adv_invildtime;
		this.adv_addtime = adv_addtime;
		this.adv_modtime = adv_modtime;
		this.adv_url = adv_url;
	}

	public JxAdvpic() {
		
	}
	
}
