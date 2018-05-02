package com.game.bmanager.entity;

import java.io.File;

import org.codehaus.jackson.annotate.JsonIgnore;

public class ApkEntry {
	
	private String apkSize;
	
	private String apkVersion;
	
	private String apkPackage;
	
	@JsonIgnore
	private File apkIcon;
	
	private String apkIconPath;
	
	private String apkIconUrl;
	
	private String apkIconMd5;
	
	private String apkName;
	
	private String apkUrl;
	
	private String apkMd5;
	
	private String apkPath;
	
	private String versionCode;
	
	public String getApkSize() {
		return apkSize;
	}

	public void setApkSize(String apkSize) {
		this.apkSize = apkSize;
	}

	public String getApkVersion() {
		return apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	public String getApkPackage() {
		return apkPackage;
	}

	public void setApkPackage(String apkPackage) {
		this.apkPackage = apkPackage;
	}

	public String getApkIconUrl() {
		return apkIconUrl;
	}

	public void setApkIconUrl(String apkIconUrl) {
		this.apkIconUrl = apkIconUrl;
	}

	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getApkIconMd5() {
		return apkIconMd5;
	}

	public void setApkIconMd5(String apkIconMd5) {
		this.apkIconMd5 = apkIconMd5;
	}

	public String getApkMd5() {
		return apkMd5;
	}

	public void setApkMd5(String apkMd5) {
		this.apkMd5 = apkMd5;
	}

	public String getApkPath() {
		return apkPath;
	}

	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}
	
	public String getApkIconPath() {
		return apkIconPath;
	}

	public void setApkIconPath(String apkIconPath) {
		this.apkIconPath = apkIconPath;
	}

	public File getApkIcon() {
		return apkIcon;
	}

	public void setApkIcon(File apkIcon) {
		this.apkIcon = apkIcon;
	}
	
	public JxApkVersions getApkVersions(){
		JxApkVersions apk = new JxApkVersions();
		apk.setApkName(apkName);
		apk.setApkVersion(versionCode);
		apk.setSystemVersion(apkVersion);
		apk.setApkUrl(apkUrl);
		apk.setApkPath(apkPath);
		//apk.setApkPath("E:\test");
		apk.setApkSize(apkSize);
		apk.setApkIcon(apkIconUrl);
		apk.setIconMd5(apkIconMd5);
		apk.setApkMd5(apkMd5);
		return apk;
	}
	
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	@Override
	public String toString() {
		
		return "apkVersion="+this.apkVersion+
				",apkPackage="+this.apkPackage+
				",apkPath="+this.apkPath+
				",apkIconUrl="+this.apkIconUrl+
				",apkIconMD5="+this.apkIconMd5+
				",apkName="+this.apkName+
				",apkUrl="+this.apkUrl+
				",apkMD5="+this.apkMd5+
				",apkPath="+this.apkPath;
	}
}
