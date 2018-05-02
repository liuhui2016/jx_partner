package com.game.bmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_partner")
public class JxPartner implements java.io.Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String PAR_NAME;
	private String PAR_LEVEL;
	@Column(name = "PAR_PARENTID")
	private String parParentid;
	private String PAR_PARENT;
	private String PAR_AREA;
	private String PAR_ADDRESS;
	private String PAR_PHONE;
	private String PAR_OTHER;
	private Long par_sellernum;
	private int par_shop;
	private int par_pact;
	private int ispermissions;
	private int total_number;
	private int three_years_numbers;
	private String card_number;
	private String order_no;

	public Long getPar_sellernum() {
		return par_sellernum;
	}

	public void setPar_sellernum(Long par_sellernum) {
		this.par_sellernum = par_sellernum;
	}

	public JxPartner() {
		super();
	}

	public JxPartner(String pAR_NAME, String pAR_LEVEL, String pAR_PARENTID,
			String pAR_PARENT, String pAR_AREA, String pAR_ADDRESS,
			String pAR_PHONE, String pAR_OTHER) {
		super();
		PAR_NAME = pAR_NAME;
		PAR_LEVEL = pAR_LEVEL;
		parParentid = pAR_PARENTID;
		PAR_PARENT = pAR_PARENT;
		PAR_AREA = pAR_AREA;
		PAR_ADDRESS = pAR_ADDRESS;
		PAR_PHONE = pAR_PHONE;
		PAR_OTHER = pAR_OTHER;
	}

	public String getParParentid() {
		return parParentid;
	}

	public void setParParentid(String pAR_PARENTID) {
		parParentid = pAR_PARENTID;
	}

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPAR_NAME() {
		return PAR_NAME;
	}

	public void setPAR_NAME(String pAR_NAME) {
		this.PAR_NAME = pAR_NAME;
	}

	public String getPAR_LEVEL() {
		return PAR_LEVEL;
	}

	public void setPAR_LEVEL(String pAR_LEVEL) {
		this.PAR_LEVEL = pAR_LEVEL;
	}

	public String getPAR_PARENT() {
		return PAR_PARENT;
	}

	public void setPAR_PARENT(String pAR_PARENT) {
		this.PAR_PARENT = pAR_PARENT;
	}

	public String getPAR_AREA() {
		return PAR_AREA;
	}

	public void setPAR_AREA(String pAR_AREA) {
		this.PAR_AREA = pAR_AREA;
	}

	public String getPAR_ADDRESS() {
		return PAR_ADDRESS;
	}

	public void setPAR_ADDRESS(String pAR_ADDRESS) {
		this.PAR_ADDRESS = pAR_ADDRESS;
	}

	public String getPAR_PHONE() {
		return PAR_PHONE;
	}

	public void setPAR_PHONE(String pAR_PHONE) {
		this.PAR_PHONE = pAR_PHONE;
	}

	public String getPAR_OTHER() {
		return PAR_OTHER;
	}

	public void setPAR_OTHER(String pAR_OTHER) {
		this.PAR_OTHER = pAR_OTHER;
	}

	@Override
	public String toString() {
		return "Partner [id=" + id + ", PAR_NAME=" + PAR_NAME + ", PAR_LEVEL="
				+ PAR_LEVEL + ", parParentid=" + parParentid + ", PAR_PARENT="
				+ PAR_PARENT + ", PAR_AREA=" + PAR_AREA + ", PAR_ADDRESS="
				+ PAR_ADDRESS + ", PAR_PHONE=" + PAR_PHONE + ", PAR_OTHER="
				+ PAR_OTHER + "]";
	}

	public int getPar_shop() {
		return par_shop;
	}

	public void setPar_shop(int par_shop) {
		this.par_shop = par_shop;
	}

	public int getPar_pact() {
		return par_pact;
	}

	public void setPar_pact(int par_pact) {
		this.par_pact = par_pact;
	}

	public int getIspermissions() {
		return ispermissions;
	}

	public void setIspermissions(int ispermissions) {
		this.ispermissions = ispermissions;
	}

	public int getTotal_number() {
		return total_number;
	}

	public void setTotal_number(int total_number) {
		this.total_number = total_number;
	}

	public int getThree_years_numbers() {
		return three_years_numbers;
	}

	public void setThree_years_numbers(int three_years_numbers) {
		this.three_years_numbers = three_years_numbers;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
}
