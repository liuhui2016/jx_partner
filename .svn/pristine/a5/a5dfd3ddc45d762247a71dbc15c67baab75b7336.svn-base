package com.game.bmanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_menu")
public class JxMenu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8417567963134592716L;
	
	private Long id;
	private String menu_name;
	private Long menu_parentid;
	private String menu_icourl;
	private Date menu_addtime;
	private Date menu_modtime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public Long getMenu_parentid() {
		return menu_parentid;
	}
	public void setMenu_parentid(Long menu_parentid) {
		this.menu_parentid = menu_parentid;
	}
	public String getMenu_icourl() {
		return menu_icourl;
	}
	public void setMenu_icourl(String menu_icourl) {
		this.menu_icourl = menu_icourl;
	}
	public Date getMenu_addtime() {
		return menu_addtime;
	}
	public void setMenu_addtime(Date menu_addtime) {
		this.menu_addtime = menu_addtime;
	}
	public Date getMenu_modtime() {
		return menu_modtime;
	}
	public void setMenu_modtime(Date menu_modtime) {
		this.menu_modtime = menu_modtime;
	}
	
	public JxMenu (){
		super();
	}
	public JxMenu(String menu_name, Long menu_parentid, String menu_icourl,
			Date menu_addtime, Date menu_modtime) {
		super();
		this.menu_name = menu_name;
		this.menu_parentid = menu_parentid;
		this.menu_icourl = menu_icourl;
		this.menu_addtime = menu_addtime;
		this.menu_modtime = menu_modtime;
	}
	@Override
	public String toString() {
		return "Menu [id=" + id + ", menu_name=" + menu_name
				+ ", menu_parentid=" + menu_parentid + ", menu_icourl="
				+ menu_icourl + ", menu_addtime=" + menu_addtime
				+ ", menu_modtime=" + menu_modtime + "]";
	}
}
