package com.game.bmanager.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.ApkEntry;
import com.game.bmanager.entity.JxShare;
import com.game.bmanager.service.IJxShareService;
import com.game.bmanager.util.ApkUtil;
import com.game.bmanager.util.Constants;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;
import com.game.smvc.util.UserUtil;
import com.game.util.Md5Encoder;

@Namespace("/bmanager/share")
@Results({ @Result(name = "reload", location = "share.action?authId=${authId}", type = "redirect") })
public class ShareAction extends CrudActionSupport<JxShare>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8681638601684553205L;
	
	@Autowired
	private IJxShareService shareService;
	
	private JxShare jxShare;
	
	private Long id;
    private Long oldId;
    private List<Long> ids;
    
    private Page<JxShare> page = new Page<JxShare>(15);
    
    
    //接收APK文件
  	private File apk;
  	private String apkFileName;
  	private String apkDigest;
  	
  	//接受图片
  	private File iconfile;
    private String iconfileFileName;

	@Override
	public JxShare getModel() {
		if (this.id != null) {
            this.jxShare = ((JxShare) shareService.get(this.id));
        } else {
            this.jxShare = new JxShare();
        }
     return jxShare;
	}

	@Override
	public String delete() throws Exception {
		if(id != null){
			shareService.remove(id);
		}
		return RELOAD;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String list() throws Exception {
		List<PropertyFilter> filters = HibernateUtils
                .buildPropertyFilters(Struts2Utils.getRequest());
        if (!this.page.isOrderBySetted()) {
            this.page.setOrderBy("id");
            this.page.setOrder("desc");
        }
        page = shareService.searchPage(page, filters);
        return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public String save() throws Exception {
		String url = null;
		if (jxShare.getId() == null) {
			jxShare.setShare_addtime(new Date());
		}
		jxShare.setShare_modifytime(new Date());

		if (iconfile != null) {
			String iconPath = Constants.CONS_PROPERTIES.getValue("PIC_PATH");
			String iconUrl = Constants.CONS_PROPERTIES.getValue("PIC_URL");
			String iconMd5 = Md5Encoder.md5(iconfile);
			String newFileName = iconMd5
					+ iconfileFileName.substring(iconfileFileName
							.lastIndexOf("."));

			// 保存路径
			File targeta = new File(iconPath + File.separator, newFileName);
			FileUtils.copyFile(iconfile, targeta);

			url = iconUrl + "/" + newFileName;// 下载路径
		} else {
			System.out.println("pic is null");
		}
		
		if(url != null){
			jxShare.setShare_imgurl(url);
		}
		if(jxShare.getShare_status()==1){
		    shareService.updateOtherInvalid();
		}
		shareService.save(jxShare);
		return RELOAD;
	}

	public JxShare getJxShare() {
		return jxShare;
	}

	public void setJxShare(JxShare jxShare) {
		this.jxShare = jxShare;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOldId() {
		return oldId;
	}

	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Page<JxShare> getPage() {
		return page;
	}

	public void setPage(Page<JxShare> page) {
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

	public File getIconfile() {
		return iconfile;
	}

	public void setIconfile(File iconfile) {
		this.iconfile = iconfile;
	}

	public String getIconfileFileName() {
		return iconfileFileName;
	}

	public void setIconfileFileName(String iconfileFileName) {
		this.iconfileFileName = iconfileFileName;
	}
	
}
