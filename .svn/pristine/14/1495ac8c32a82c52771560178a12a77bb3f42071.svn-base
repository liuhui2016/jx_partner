package com.game.bmanager.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.ApkEntry;
import com.game.bmanager.entity.JxApkVersions;
import com.game.bmanager.service.IJxApkVersionService;
import com.game.bmanager.util.ApkUtil;
import com.game.bmanager.util.Constants;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;
import com.game.smvc.util.UserUtil;

@Namespace("/bmanager/apk")
@Results({ @Result(name = "reload", location = "apk.action?authId=${authId}", type = "redirect") })
public class ApkAction extends CrudActionSupport<JxApkVersions>{
	/**
	 */
	private static final long serialVersionUID = 5149651676353661633L;
	@Autowired
	private IJxApkVersionService apkService;
	
	private Integer id;
	private Integer oldId;
	private List<Integer> ids;
	private JxApkVersions entity;
	private Page<JxApkVersions> page = new Page<JxApkVersions>(15);
	private Integer type;
	private Integer mustupgrade;
	//接收APK文件
	private File apk;
	private String apkFileName;
	private String apkDigest;
	
	private String apkVersion;
	
	private String systemVersion;
	
	@Override
	public JxApkVersions getModel() {
		return this.entity;
	}
	@Override
	protected void prepareModel() throws Exception {
		if (this.id != null) {
			this.entity = ((JxApkVersions) apkService.get(this.id));
		} else {
			this.entity = new JxApkVersions();
		}
	}
	
	@Override
	public String list() throws Exception {
		List<PropertyFilter> filters = HibernateUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		if (!this.page.isOrderBySetted()) {
			this.page.setOrderBy("id");
			this.page.setOrder("desc");
		}
		page = apkService.searchPage(page, filters);
		return SUCCESS;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String save() throws Exception {
    	String apkPath = Constants.CONS_PROPERTIES.getValue("apk.path");
		//String apkPath = "E:\test";
		String prefix = apkFileName.substring(apkFileName.lastIndexOf(".")+1);
		//重命名图片
		String newApkName = System.currentTimeMillis() + UserUtil.generateNumString(5) + "." + prefix;
		if(type==1||type==0){
    		ApkEntry entry = ApkUtil.getApkEntry(apk, apkPath, newApkName);
    		entity = entry.getApkVersions();
    		entity.setCreateTime(new Date());
    		entity.setApkDigest(apkDigest);
    		entity.setType(type);
    		entity.setMustupgrade(mustupgrade);
		}else{
		    ApkEntry entry = ApkUtil.saveIOSApk(apk, apkPath, newApkName);
		    entity = entry.getApkVersions();
		    entity.setCreateTime(new Date());
		    entity.setApkDigest(apkDigest);
		    entity.setApkName(apkFileName);
		    entity.setApkVersion(apkVersion);
		    entity.setSystemVersion(systemVersion);
		    entity.setType(type);
		    entity.setMustupgrade(mustupgrade);
		}
		apkService.save(entity);
		//更改分享链接
		return RELOAD;
	}
	
	public String delAll() throws Exception {
        return RELOAD;
	}
	@Override
	public String delete() throws Exception {
		return null;
	}
	
	public String changeStatus(){
		
		return null;
	}
	
	/*-------------------------getter  and   setter--------------------------*/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOldId() {
		return oldId;
	}
	public void setOldId(Integer oldId) {
		this.oldId = oldId;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public JxApkVersions getEntity() {
		return entity;
	}
	public void setEntity(JxApkVersions entity) {
		this.entity = entity;
	}
	public Page<JxApkVersions> getPage() {
		return page;
	}
	public void setPage(Page<JxApkVersions> page) {
		this.page = page;
	}
	public File getApk() {
		return apk;
	}
	public void setApk(File apk) {
		this.apk = apk;
	}
	public String getApkFileName() {
		return apkFileName;
	}
	public void setApkFileName(String apkFileName) {
		this.apkFileName = apkFileName;
	}
	public String getApkDigest() {
		return apkDigest;
	}
	public void setApkDigest(String apkDigest) {
		this.apkDigest = apkDigest;
	}
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
	public String getApkVersion() {
		return apkVersion;
	}
	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
    public Integer getMustupgrade() {
        return mustupgrade;
    }
    public void setMustupgrade(Integer mustupgrade) {
        this.mustupgrade = mustupgrade;
    }
    
}
