package com.game.comm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Province entity. @author ZhangXin
 */
@Entity
@Table(name = "common_province")
public class Province implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4436492558772678043L;
	private Long id;
	private String name; // 省份名称
	private String pinyin; // 拼音

	// Constructors

	/** default constructor */
	public Province() {
	}

	/** full constructor */
	public Province(String name, String pinyin) {
		this.name = name;
		this.pinyin = pinyin;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PINYIN", length = 50)
	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

}