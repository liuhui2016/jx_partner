package com.game.smvc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_spider")
public class JxSpider {

	private int jx_id;
	private int id;
	private String jx_linkhref;
	private String jx_linktext;
	private String jx_summary;
	private String jx_content;
	private Date addtime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getJx_id() {
		return jx_id;
	}
	public void setJx_id(int jx_id) {
		this.jx_id = jx_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJx_linkhref() {
		return jx_linkhref;
	}
	public void setJx_linkhref(String jx_linkhref) {
		this.jx_linkhref = jx_linkhref;
	}
	public String getJx_linktext() {
		return jx_linktext;
	}
	public void setJx_linktext(String jx_linktext) {
		this.jx_linktext = jx_linktext;
	}
	public String getJx_summary() {
		return jx_summary;
	}
	public void setJx_summary(String jx_summary) {
		this.jx_summary = jx_summary;
	}
	public String getJx_content() {
		return jx_content;
	}
	public void setJx_content(String jx_content) {
		this.jx_content = jx_content;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
}
