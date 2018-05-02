package com.game.smvc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jx_product")
public class JxProduct implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 8261443966190396983L;
    private Long pro_id;
    private String pro_code;
    private String pro_no;
    private String fam_id;
    private String ph_no;
    private String pro_image;
    private String pro_name;
    private Integer pro_category;
    private String pro_color;
    private Date pro_addtime;
    private Date pro_modtime;

    private Date pro_invalidtime;
    private Float pro_hasflow;
    private Float pro_restflow;
    private String pro_alias;
    //商品押金
    //private Integer pro_pledge;
    private Long u_id;
	private int Pro_restflow;
	
	private String pro_restflow2;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getPro_id() {
        return pro_id;
    }
    public void setPro_id(Long pro_id) {
        this.pro_id = pro_id;
    }
    public String getPro_code() {
        return pro_code;
    }
    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }
    public String getPro_no() {
        return pro_no;
    }
    public void setPro_no(String pro_no) {
        this.pro_no = pro_no;
    }
    public String getFam_id() {
        return fam_id;
    }
    public void setFam_id(String fam_id) {
        this.fam_id = fam_id;
    }
    public String getPh_no() {
        return ph_no;
    }
    public void setPh_no(String ph_no) {
        this.ph_no = ph_no;
    }

    public String getPro_image() {
		return pro_image;
	}
	public void setPro_image(String pro_image) {
		this.pro_image = pro_image;
	}
	public String getPro_name() {
        return pro_name;
    }
    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }
    public Integer getPro_category() {
        return pro_category;
    }
    public void setPro_category(Integer pro_category) {
        this.pro_category = pro_category;
    }
    public String getPro_color() {
        return pro_color;
    }
    public void setPro_color(String pro_color) {
        this.pro_color = pro_color;
    }
    public Date getPro_addtime() {
        return pro_addtime;
    }
    public void setPro_addtime(Date pro_addtime) {
        this.pro_addtime = pro_addtime;
    }
    public Date getPro_modtime() {
        return pro_modtime;
    }
    public void setPro_modtime(Date pro_modtime) {
        this.pro_modtime = pro_modtime;
    }

	public Date getPro_invalidtime() {
		return pro_invalidtime;
	}
	public void setPro_invalidtime(Date pro_invalidtime) {
		this.pro_invalidtime = pro_invalidtime;
	}
	public Float getPro_hasflow() {
		return pro_hasflow;
	}
	public void setPro_hasflow(Float pro_hasflow) {
		this.pro_hasflow = pro_hasflow;
	}
	public Float getPro_restflow() {
		return pro_restflow;
	}
	public void setPro_restflow(Float pro_restflow) {
		this.pro_restflow = pro_restflow;
	}
	public Long Pro_restflow() {
		return u_id;
	}
	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}
	public Float getPro_hasflow(String hasflow) {
		return pro_hasflow;
		
	}
	public Long getU_id() {
		return u_id;
	}
	public Float getPro_restflow(Float restflow) {
		return pro_hasflow;
		
	}
	public Float getPro_restflow(int restflow) {
		return pro_restflow;
		
	}
	public void setPro_restflow(int restflow, Float pro_restflow) {
		this.pro_restflow = pro_restflow;
		
	}
	public void setPro_restflow(int restflow) {
		this.Pro_restflow = restflow;
		
	}
	public void setPro_restflow(String pro_restflow2) {
		this.pro_restflow2 = pro_restflow2;
		
	}
	public String getPro_alias() {
		return pro_alias;
	}
	public void setPro_alias(String pro_alias) {
		this.pro_alias = pro_alias;
	}
	
	
	

	
	
	

}
