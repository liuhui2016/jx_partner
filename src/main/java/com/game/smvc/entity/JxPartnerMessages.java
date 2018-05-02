package com.game.smvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name = "jx_partner_messages")
public class JxPartnerMessages implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int p_id;
	private String p_name;
	private String p_title;
	private String p_content;
	private String nextparams;
	private Integer p_isread;
	private Integer p_type;
	private Date message_time;
	
	
	
	public JxPartnerMessages() {
		super();
	}
	
	public JxPartnerMessages(int p_id, String p_name, String p_title,
			String p_content, String nextparams, Integer p_isread,
			Integer p_type, Date message_time) {
		super();
		this.p_id = p_id;
		this.p_name = p_name;
		this.p_title = p_title;
		this.p_content = p_content;
		this.nextparams = nextparams;
		this.p_isread = p_isread;
		this.p_type = p_type;
		this.message_time = message_time;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_title() {
		return p_title;
	}
	public void setP_title(String p_title) {
		this.p_title = p_title;
	}
	public String getP_content() {
		return p_content;
	}
	public void setP_content(String p_content) {
		this.p_content = p_content;
	}
	public String getNextparams() {
		return nextparams;
	}
	public void setNextparams(String nextparams) {
		this.nextparams = nextparams;
	}
	public Integer getP_isread() {
		return p_isread;
	}
	public void setP_isread(Integer p_isread) {
		this.p_isread = p_isread;
	}
	public Integer getP_type() {
		return p_type;
	}
	public void setP_type(Integer p_type) {
		this.p_type = p_type;
	}
	public Date getMessage_time() {
		return message_time;
	}
	public void setMessage_time(Date message_time) {
		this.message_time = message_time;
	}
	
	
}
