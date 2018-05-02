package com.game.bmanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="jx_share")
public class JxShare implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1837947705338607200L;
    private Long id;
    private String share_title;
    private String share_content;
    private String share_apkurl;
    private String share_imgurl;
    private Integer share_status;
    private Date share_addtime;
    private Date share_modifytime;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getShare_title() {
        return share_title;
    }
    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }
    public String getShare_content() {
        return share_content;
    }
    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }
    public String getShare_apkurl() {
        return share_apkurl;
    }
    public void setShare_apkurl(String share_apkurl) {
        this.share_apkurl = share_apkurl;
    }
    public String getShare_imgurl() {
        return share_imgurl;
    }
    public void setShare_imgurl(String share_imgurl) {
        this.share_imgurl = share_imgurl;
    }
    public Integer getShare_status() {
        return share_status;
    }
    public void setShare_status(Integer share_status) {
        this.share_status = share_status;
    }
    public Date getShare_addtime() {
        return share_addtime;
    }
    public void setShare_addtime(Date share_addtime) {
        this.share_addtime = share_addtime;
    }
    public Date getShare_modifytime() {
        return share_modifytime;
    }
    public void setShare_modifytime(Date share_modifytime) {
        this.share_modifytime = share_modifytime;
    }

}
