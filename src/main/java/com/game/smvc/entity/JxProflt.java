package com.game.smvc.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_proflt")
public class JxProflt {
	private Long prf_id;
	private java.lang.String prf_code;
	private String pro_no;
	private Long prf_category;
	private Integer prf_pp;
	private Integer prf_cto;
	private Integer prf_ro ;
	private Integer prf_t33;
	private Integer prf_wfr ;
	private java.util.Date prf_addtime;
	private java.util.Date prf_modtime;
	private String prf_other;
	
	
	
	
	
	
	public JxProflt() {
		super();
	}
	

	public JxProflt(List<Map<String, Object>> jxProflt) {
		super();
	}
	
	public JxProflt(Integer prf_pp, Integer prf_cto, Integer prf_ro, Integer prf_t33,
	        Integer prf_wfr) {
		super();
		this.prf_pp = prf_pp;
		this.prf_cto = prf_cto;
		this.prf_ro = prf_ro;
		this.prf_t33 = prf_t33;
		this.prf_wfr = prf_wfr;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getPrf_id() {
		return prf_id;
	}

	public void setPrf_id(Long prf_id) {
		this.prf_id = prf_id;
	}

	public java.lang.String getPrf_code() {
		return prf_code;
	}

	public void setPrf_code(java.lang.String prf_code) {
		this.prf_code = prf_code;
	}

	public String getPro_no() {
		return pro_no;
	}

	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}

	public Long getPrf_category() {
		return prf_category;
	}

	public void setPrf_category(Long prf_category) {
		this.prf_category = prf_category;
	}

	public Integer getPrf_pp() {
		return prf_pp;
	}

	public void setPrf_pp(Integer prf_pp) {
		this.prf_pp = prf_pp;
	}

	public Integer getPrf_cto() {
		return prf_cto;
	}

	public void setPrf_cto(Integer prf_cto) {
		this.prf_cto = prf_cto;
	}

	public Integer getPrf_ro() {
		return prf_ro;
	}

	public void setPrf_ro(Integer prf_ro) {
		this.prf_ro = prf_ro;
	}

	public Integer getPrf_t33() {
		return prf_t33;
	}

	public void setPrf_t33(Integer prf_t33) {
		this.prf_t33 = prf_t33;
	}

	public Integer getPrf_wfr() {
		return prf_wfr;
	}

	public void setPrf_wfr(Integer prf_wfr) {
		this.prf_wfr = prf_wfr;
	}

	public java.util.Date getPrf_addtime() {
		return prf_addtime;
	}

	public void setPrf_addtime(java.util.Date prf_addtime) {
		this.prf_addtime = prf_addtime;
	}

	public java.util.Date getPrf_modtime() {
		return prf_modtime;
	}

	public void setPrf_modtime(java.util.Date prf_modtime) {
		this.prf_modtime = prf_modtime;
	}

    public String getPrf_other() {
        return prf_other;
    }

    public void setPrf_other(String prf_other) {
        this.prf_other = prf_other;
    }


	public void setPrf_pp(double p) {
		this.prf_pp = (int) p;
		
	}


	public void setPrf_ro(double r) {
		this.prf_ro =(int) r;
		
	}


	public void setPrf_cto(double c) {
		this.prf_cto = (int) c;
		
	}


	public void setPrf_t33(double t) {
		this.prf_t33 = (int) t;
		
	}


	public void setPrf_wfr(double w) {
		this.prf_wfr = (int) w;
		
	}


	

	
}