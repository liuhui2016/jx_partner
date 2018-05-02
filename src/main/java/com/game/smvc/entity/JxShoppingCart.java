package com.game.smvc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 购物车类
 * @author lh
 *
 */
@Table
@Entity(name = "jx_shopping_cart")
public class JxShoppingCart {

	private int sc_id;
	private Long u_id;
	private int pro_id;
	private float sc_price;
	private String sc_name;
	private int sc_state;
	private String sc_color;
	private int pro_multiple;
	private int sc_number;
	private String sc_imgurl;
	private int sc_type;
	private String sc_model;
	private String sc_weight;
	private String sc_tag;
	public JxShoppingCart() {// 无参构造  
        super();  
    }  
	
	//有参构造
	public JxShoppingCart(int sc_id, Long u_id,Float sc_price, String sc_name,  
			int sc_state,String sc_color,int sc_number,int pro_multiple,String sc_imgurl,int sc_type,String sc_model,String sc_weight) {  
	        super();  
	        this.sc_id = sc_id;  
	        this.u_id = u_id;  
	        this.sc_price = sc_price;  
	        this.sc_name = sc_name;  
	        this.setSc_state(sc_state);  
	        this.sc_color = sc_color;  
	        this.sc_number = sc_number;
	        this.setPro_multiple(pro_multiple);
	        this.sc_imgurl = sc_imgurl;
	        this.sc_type = sc_type;
	        this.setSc_model(sc_model);
	        this.setSc_weight(sc_weight);
	    }  
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getSc_id() {
		return sc_id;
	}
	
	public void setSc_id(int sc_id) {
		this.sc_id = sc_id;
	}
	public Long getU_id() {
		return u_id;
	}
	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}
	public float getSc_price() {
		return sc_price;
	}
	public void setSc_price(float sc_price) {
		this.sc_price = sc_price;
	}
	public String getSc_name() {
		return sc_name;
	}
	public void setSc_name(String sc_name) {
		this.sc_name = sc_name;
	}
	
	public String getSc_color() {
		return sc_color;
	}
	public void setSc_color(String sc_color) {
		this.sc_color = sc_color;
	}
	
	public int getSc_number() {
		return sc_number;
	}
	public void setSc_number(int sc_number) {
		this.sc_number = sc_number;
	}
	public String getSc_imgurl() {
		return sc_imgurl;
	}
	public void setSc_imgurl(String sc_imgurl) {
		this.sc_imgurl = sc_imgurl;
	}

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public int getSc_state() {
		return sc_state;
	}

	public void setSc_state(int sc_state) {
		this.sc_state = sc_state;
	}

	public int getPro_multiple() {
		return pro_multiple;
	}

	public void setPro_multiple(int pro_multiple) {
		this.pro_multiple = pro_multiple;
	}

	public int getSc_type() {
		return sc_type;
	}

	public void setSc_type(int sc_type) {
		this.sc_type = sc_type;
	}

	public String getSc_model() {
		return sc_model;
	}

	public void setSc_model(String sc_model) {
		this.sc_model = sc_model;
	}

	public String getSc_weight() {
		return sc_weight;
	}

	public void setSc_weight(String sc_weight) {
		this.sc_weight = sc_weight;
	}

	@Override
	public String toString() {
		return "JxShoppingCart [sc_id=" + sc_id + ", u_id=" + u_id
				+ ", pro_id=" + pro_id + ", sc_price=" + sc_price
				+ ", sc_name=" + sc_name + ", sc_state=" + sc_state
				+ ", sc_color=" + sc_color + ", pro_multiple=" + pro_multiple
				+ ", sc_number=" + sc_number + ", sc_imgurl=" + sc_imgurl
				+ ", sc_type=" + sc_type + ", sc_model=" + sc_model
				+ ", sc_weight=" + sc_weight + "]";
	}

	public String getSc_tag() {
		return sc_tag;
	}

	public void setSc_tag(String sc_tag) {
		this.sc_tag = sc_tag;
	}

	
	
	
	
	
}
