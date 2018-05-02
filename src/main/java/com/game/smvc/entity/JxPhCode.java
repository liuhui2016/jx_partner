package com.game.smvc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 验证码表
 * @author xion
 *
 */
@Entity
@Table(name = "jx_phcode")
public class JxPhCode implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String phone_no;
    private String code_no;
    private Date code_addtime;
    private Date modtime;
    private String code_other;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPhone_no() {
        return phone_no;
    }
    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
    public String getCode_no() {
        return code_no;
    }
    public void setCode_no(String code_no) {
        this.code_no = code_no;
    }
    public Date getCode_addtime() {
        return code_addtime;
    }
    public void setCode_addtime(Date code_addtime) {
        this.code_addtime = code_addtime;
    }
    public Date getModtime() {
        return modtime;
    }
    public void setModtime(Date modtime) {
        this.modtime = modtime;
    }
    public String getCode_other() {
        return code_other;
    }
    public void setCode_other(String code_other) {
        this.code_other = code_other;
    }


}
