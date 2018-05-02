package com.game.bmanager.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="jx_version")
public class JxApkVersions implements Serializable{
	/**
	 */
	private static final long serialVersionUID = 4672743768994156182L;
	
	//id,apk_digest,apk_name,apk_md5,apk_version,system_version,apk_path,apk_url,apk_icon,
	//icon_md5,apk_size,create_time
	
	private Integer id;
	@Column(name="apk_digest")
	private String apkDigest;
	@Column(name="apk_name")
	private String apkName;
	@Column(name="apk_md5")
	private String apkMd5;
	@Column(name="apk_version")
	private String apkVersion;
	@Column(name="system_version")
	private String systemVersion;
	@Column(name="apk_path")
	private String apkPath;
	@Column(name="apk_url")
	private String apkUrl;
	@Column(name="apk_icon")
	private String apkIcon;
	@Column(name="icon_md5")
	private String iconMd5;
	@Column(name="apk_size")
	private String apkSize;
	@Column(name="create_time")
	private Date createTime;
	@Column(name="type")
	private Integer type;
	@Column(name="mustupgrade")
    private Integer mustupgrade;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getApkName() {
		return apkName;
	}
	public void setApkName(String apkName) {
		this.apkName = apkName;
	}
	public String getApkVersion() {
		return apkVersion;
	}
	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}
	public String getApkPath() {
		return apkPath;
	}
	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	public String getApkSize() {
		return apkSize;
	}
	public void setApkSize(String apkSize) {
		this.apkSize = apkSize;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getApkIcon() {
		return apkIcon;
	}
	public void setApkIcon(String apkIcon) {
		this.apkIcon = apkIcon;
	}
	public String getApkDigest() {
		return apkDigest;
	}
	public void setApkDigest(String apkDigest) {
		this.apkDigest = apkDigest;
	}
	public String getApkMd5() {
		return apkMd5;
	}
	public void setApkMd5(String apkMd5) {
		this.apkMd5 = apkMd5;
	}
	public String getIconMd5() {
		return iconMd5;
	}
	public void setIconMd5(String iconMd5) {
		this.iconMd5 = iconMd5;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
    public Integer getMustupgrade() {
        return mustupgrade;
    }
    public void setMustupgrade(Integer mustupgrade) {
        this.mustupgrade = mustupgrade;
    }
	
}
