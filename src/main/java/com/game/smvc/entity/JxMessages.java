package com.game.smvc.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity(name = "jx_messages")
public class JxMessages implements Serializable{
	private Long id;
	private Long u_id;
	private String title;
	private String content;
	private String nextparams;
	private Integer isread;
	private Integer type;
	private String message_time;
	
	
	public JxMessages(Long u_id, String title, String content, Integer isread,
			Integer type, String message_time) {
		super();
		this.u_id = u_id;
		this.title = title;
		this.content = content;
		this.isread = isread;
		this.type = type;
		this.message_time = message_time;
	}
	
	public JxMessages() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getU_id() {
		return u_id;
	}

	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNextparams() {
		return nextparams;
	}

	public void setNextparams(String nextparams) {
		this.nextparams = nextparams;
	}

	public Integer getIsread() {
		return isread;
	}

	public void setIsread(Integer isread) {
		this.isread = isread;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMessage_time() {
		return message_time;
	}

	public void setMessage_time(String message_time) {
		this.message_time = message_time;
	}
	
}
