package com.game.smvc.entity;

/***********************************************************************
 * Module:  JxAddress.java
 * Author:  Administrator
 * Purpose: Defines the Class JxAddress
 ***********************************************************************/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table
@Entity(name="jx_address")
public class JxAddress implements java.io.Serializable{

   /**
     * 
     */
    private static final Long serialVersionUID = -3275652794975146491L;
    @Column(name="adr_id")
    private Long adrId;
    @Column(name="adr_name")
    private String adrName;
    @Column(name="adr_phone")
    private String adrPhone;
    @Column(name="adr_area")
    private String adrArea;
    @Column(name="adr_detail")
    private String adrDetail;
    @Column(name="adr_code")
    private Long adrCode;
    @Column(name="adr_first")
    private int adrFirst;
    @Column(name="adr_addtime")
    private java.util.Date adrAddtime;
    @Column(name="adr_modtime")
    private java.util.Date adrModtime;
    @Column(name="adr_other")
    private String adrOther;
    
    public JxUser jxUser;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getAdrId() {
        return adrId;
    }
    
    public void setAdrId(Long adrId) {
        this.adrId = adrId;
    }
    
    public String getAdrName() {
        return adrName;
    }
    
    public void setAdrName(String adrName) {
        this.adrName = adrName;
    }
    
    public String getAdrPhone() {
        return adrPhone;
    }
    
    public void setAdrPhone(String adrPhone) {
        this.adrPhone = adrPhone;
    }
    
    public String getAdrArea() {
        return adrArea;
    }
    
    public void setAdrArea(String adrArea) {
        this.adrArea = adrArea;
    }
    
    public String getAdrDetail() {
        return adrDetail;
    }
    
    public void setAdrDetail(String adrDetail) {
        this.adrDetail = adrDetail;
    }
    
    public Long getAdrCode() {
        return adrCode;
    }
    
    public void setAdrCode(Long adrCode) {
        this.adrCode = adrCode;
    }
    
    public int getAdrFirst() {
        return adrFirst;
    }
    
    public void setAdrFirst(int adrFirst) {
        this.adrFirst = adrFirst;
    }
    
    public java.util.Date getAdrAddtime() {
        return adrAddtime;
    }
    
    public void setAdrAddtime(java.util.Date adrAddtime) {
        this.adrAddtime = adrAddtime;
    }
    
    public java.util.Date getAdrModtime() {
        return adrModtime;
    }
    
    public void setAdrModtime(java.util.Date adrModtime) {
        this.adrModtime = adrModtime;
    }
    
    public String getAdrOther() {
        return adrOther;
    }
    
    public void setAdrOther(String adrOther) {
        this.adrOther = adrOther;
    }
    @ManyToOne
    @JoinColumn(name="u_id")
    public JxUser getJxUser() {
        return jxUser;
    }
    
    public void setJxUser(JxUser jxUser) {
        this.jxUser = jxUser;
    }
   

}