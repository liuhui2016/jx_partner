package com.game.smvc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "jx_user")
public class JxUser implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 6696451346033578731L;
	private Long u_id;
	private String u_phone;
	private String u_name;
	private String u_pwd;
	private Integer u_sex;
	private Integer u_status;
	private String u_snname;//用户昵称
	private String u_txttail;//个性签名
	private String u_picurl;//头像url
	private Date u_addtime;
	private Date u_pwdmodtime;//密码重置时间
	private Date u_modtime;
	private String u_other;
	
	private Set<JxAddress> jxAddress;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getU_id() {
        return u_id;
    }
    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }
    public String getU_phone() {
        return u_phone;
    }
    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }
    public String getU_name() {
        return u_name;
    }
    public void setU_name(String u_name) {
        this.u_name = u_name;
    }
    public String getU_pwd() {
        return u_pwd;
    }
    public void setU_pwd(String u_pwd) {
        this.u_pwd = u_pwd;
    }
    public Integer getU_sex() {
        return u_sex;
    }
    public void setU_sex(Integer u_sex) {
        this.u_sex = u_sex;
    }
    public Integer getU_status() {
        return u_status;
    }
    public void setU_status(Integer u_status) {
        this.u_status = u_status;
    }
    public String getU_snname() {
        return u_snname;
    }
    public void setU_snname(String u_snname) {
        this.u_snname = u_snname;
    }
    public String getU_txttail() {
        return u_txttail;
    }
    public void setU_txttail(String u_txttail) {
        this.u_txttail = u_txttail;
    }
    public String getU_picurl() {
        return u_picurl;
    }
    public void setU_picurl(String u_picurl) {
        this.u_picurl = u_picurl;
    }
    public Date getU_addtime() {
        return u_addtime;
    }
    public void setU_addtime(Date u_addtime) {
        this.u_addtime = u_addtime;
    }
    public Date getU_pwdmodtime() {
        return u_pwdmodtime;
    }
    public void setU_pwdmodtime(Date u_pwdmodtime) {
        this.u_pwdmodtime = u_pwdmodtime;
    }
    public Date getU_modtime() {
        return u_modtime;
    }
    public void setU_modtime(Date u_modtime) {
        this.u_modtime = u_modtime;
    }
    public String getU_other() {
        return u_other;
    }
    public void setU_other(String u_other) {
        this.u_other = u_other;
    }
    @OneToMany(mappedBy="jxUser")
    public Set<JxAddress> getJxAddress() {
        return jxAddress;
    }
    public void setJxAddress(Set<JxAddress> jxAddress) {
        this.jxAddress = jxAddress;
    }

}
