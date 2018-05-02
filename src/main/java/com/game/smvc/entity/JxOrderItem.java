package com.game.smvc.entity;

/**
 * 子订单类
 * 保存的是商品
 * 与订单和商品表关联
 * 一个订单对应多个子订单
 * 一个子订单对应一个商品
 */
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jx_order_item")
public class JxOrderItem{

	 /**
	 * 
	 */
	 private Long orditem_id;
	 //private String order_no;
	 private int pro_id;
	 private String orditem_no;
	 private Float order_price;
	 private String ord_proname;
	 private int orditem_way;
	 private String adr_id;
	 private int orditem_status;
	 private int orditem_protypeid;
	 private int orditem_ordertype;
	 private String pro_no;
	 private String odritem_managerno;
	 private String ph_no;
	 private String orditem_imgurl;
	 private String orditem_receivename;
	 private String pro_color;
	 private java.util.Date orditem_addtime;
	 private java.util.Date orditem_modtime;
	 private String orditem_sertime;
	 private int orditem_hasflow;
	 private int orditem_restflow;
	 private Long u_id;
	 private int ord_multiple;
	 private int ord_number;
	// private int orditem_pledge;
	 
	 public JxOrderItem() {
			super();
		}
		

		public JxOrderItem(List<Map<String, Object>> JxOrderItem) {
			super();
		}
		
		public JxOrderItem(Integer orditem_status, Integer orditem_way) {
			super();
			this.orditem_status = orditem_status;
			this.orditem_way = orditem_way;
		}
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getOrditem_id() {
		return orditem_id;
	}
	public void setOrditem_id(Long orditem_id) {
		this.orditem_id = orditem_id;
	}
	
	public String getOrditem_no() {
		return orditem_no;
	}
	public void setOrditem_no(String orditem_no) {
		this.orditem_no = orditem_no;
	}
	public Float getOrder_price() {
		return order_price;
	}
	public void setOrder_price(Float order_price) {
		this.order_price = order_price;
	}
	public String getOrd_proname() {
		return ord_proname;
	}
	public void setOrd_proname(String ord_proname) {
		this.ord_proname = ord_proname;
	}

	public int getOrditem_status() {
		return orditem_status;
	}
	public void setOrditem_status(int orditem_status) {
		this.orditem_status = orditem_status;
	}
	public int getOrditem_protypeid() {
		return orditem_protypeid;
	}
	public void setOrditem_protypeid(int orditem_protypeid) {
		this.orditem_protypeid = orditem_protypeid;
	}
	public int getOrditem_ordertype() {
		return orditem_ordertype;
	}
	public void setOrditem_ordertype(int orditem_ordertype) {
		this.orditem_ordertype = orditem_ordertype;
	}
	public String getPro_no() {
		return pro_no;
	}
	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}
	public String getOdritem_managerno() {
		return odritem_managerno;
	}
	public void setOdritem_managerno(String odritem_managerno) {
		this.odritem_managerno = odritem_managerno;
	}
	public String getPh_no() {
		return ph_no;
	}
	public void setPh_no(String ph_no) {
		this.ph_no = ph_no;
	}
	public String getOrditem_imgurl() {
		return orditem_imgurl;
	}
	public void setOrditem_imgurl(String orditem_imgurl) {
		this.orditem_imgurl = orditem_imgurl;
	}
	public String getOrditem_receivename() {
		return orditem_receivename;
	}
	public void setOrditem_receivename(String orditem_receivename) {
		this.orditem_receivename = orditem_receivename;
	}
	public String getPro_color() {
		return pro_color;
	}
	public void setPro_color(String pro_color) {
		this.pro_color = pro_color;
	}
	public java.util.Date getOrditem_addtime() {
		return orditem_addtime;
	}
	public void setOrditem_addtime(java.util.Date orditem_addtime) {
		this.orditem_addtime = orditem_addtime;
	}
	public java.util.Date getOrditem_modtime() {
		return orditem_modtime;
	}
	public void setOrditem_modtime(java.util.Date orditem_modtime) {
		this.orditem_modtime = orditem_modtime;
	}
	public String getOrditem_sertime() {
		return orditem_sertime;
	}
	public void setOrditem_sertime(String orditem_sertime) {
		this.orditem_sertime = orditem_sertime;
	}
	public int getOrditem_hasflow() {
		return orditem_hasflow;
	}
	public void setOrditem_hasflow(int orditem_hasflow) {
		this.orditem_hasflow = orditem_hasflow;
	}
	public int getOrditem_restflow() {
		return orditem_restflow;
	}
	public void setOrditem_restflow(int orditem_restflow) {
		this.orditem_restflow = orditem_restflow;
	}
	public Long getU_id() {
		return u_id;
	}
	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}
	public int getPro_id() {
		return pro_id;
	}
	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}
	public int getOrditem_way() {
		return orditem_way;
	}
	public void setOrditem_way(int orditem_way) {
		this.orditem_way = orditem_way;
	}


	public String getAdr_id() {
		return adr_id;
	}


	public void setAdr_id(String adr_id) {
		this.adr_id = adr_id;
	}


	public int getOrd_multiple() {
		return ord_multiple;
	}


	public void setOrd_multiple(int ord_multiple) {
		this.ord_multiple = ord_multiple;
	}


	public int getOrd_number() {
		return ord_number;
	}


	public void setOrd_number(int ord_number) {
		this.ord_number = ord_number;
	}


	

	
	
}
