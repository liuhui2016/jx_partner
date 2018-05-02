package com.game.bmanager.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.JxPicture;
import com.game.bmanager.entity.JxProdetail;
import com.game.bmanager.entity.JxPrototal;
import com.game.bmanager.service.IJxPictureService;
import com.game.bmanager.service.IJxProdetailService;
import com.game.bmanager.service.IJxPrototalService;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;
import com.game.util.Constants;
import com.game.util.Md5Encoder;

@Namespace("/bmanager/prototal")
@Results({ @Result(name = "reload", location = "prototal.action?authId=${authId}", type = "redirect") })
public class PrototalAction extends CrudActionSupport<JxPrototal>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1462819831411739440L;
	
	@Autowired
	private IJxPrototalService prototalService;
	
	@Autowired
	private IJxProdetailService prodetailService;
	
	@Autowired
	private IJxPictureService pictrueService;
	
	private JxPrototal jxPrototal;
	
	private Long id;
    private Long oldId;
    private List<Long> ids;
    
    private Page<JxPrototal> page = new Page<JxPrototal>(15);
    
    private Page<JxProdetail> pages = new Page<JxProdetail>(15);
    
    private Page<JxPicture> pagePic = new Page<JxPicture>(15);
    
    private Long srcpath;
    
    private File iconfile;
    
    private String iconfileFileName;
    
    private JxPicture picture;
    
    private JxPicture pictures;
    
    private String pic_urls;
    
    private String pic_url;
    
    private JxProdetail prodetail;
    
    private Long prot_id;
	
	
	@Override
	public JxPrototal getModel() {
		if (this.id != null) {
            this.jxPrototal = ((JxPrototal) prototalService.get(this.id));
            this.picture = pictrueService.get(Long.valueOf(jxPrototal.getProt_picid()));
        } else {
            this.jxPrototal = new JxPrototal();
            this.picture = new JxPicture();
        }
		return jxPrototal;
	}

	@Override
	public String delete() throws Exception {
		if (id != null) {
			prototalService.updateStaus(id);
		}
		return RELOAD;
	}

	@Override
	public String input() throws Exception {
		if(picture.getPic_url() != null){
			pic_url = picture.getPic_url();
		}
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
        page = prototalService.searchPage(page, filters);
        return SUCCESS;
	}
	
	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public String save() throws Exception {
		if(id == null){
			jxPrototal.setProt_addtime(new Date());
			picture.setPic_addtime(new Date());
		}
		jxPrototal.setProt_modtime(new Date());
		picture.setPic_modtime(new Date());
		
		String url = null;
		
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
			pic_urls = url;
		} else {
			System.out.println("pic is null");
		}
		
		if(url !=null){
			picture.setPic_url(url);
		}
		picture.setProtype_id(jxPrototal.getProt_type());
		picture.setPic_name(jxPrototal.getProt_name());
		picture.setPic_default(1);
		
		picture = pictrueService.save(picture);
		jxPrototal.setProt_picid(picture.getId());
		prototalService.save(jxPrototal);
		return RELOAD;
	}
	
	public String detailInput() throws Exception{
		return "detailInput";
	}
	
	public JxPrototal getJxPrototal() {
		return jxPrototal;
	}

	public void setJxPrototal(JxPrototal jxPrototal) {
		this.jxPrototal = jxPrototal;
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

	public Page<JxPrototal> getPage() {
		return page;
	}

	public void setPage(Page<JxPrototal> page) {
		this.page = page;
	}

	public Page<JxProdetail> getPages() {
		return pages;
	}

	public void setPages(Page<JxProdetail> pages) {
		this.pages = pages;
	}

	public Page<JxPicture> getPagePic() {
		return pagePic;
	}

	public void setPagePic(Page<JxPicture> pagePic) {
		this.pagePic = pagePic;
	}

	public Long getSrcpath() {
		return srcpath;
	}

	public void setSrcpath(Long srcpath) {
		this.srcpath = srcpath;
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

	public JxPicture getPicture() {
		return picture;
	}

	public void setPicture(JxPicture picture) {
		this.picture = picture;
	}

	public String getPic_urls() {
		return pic_urls;
	}

	public void setPic_urls(String pic_urls) {
		this.pic_urls = pic_urls;
	}

	public JxProdetail getProdetail() {
		return prodetail;
	}

	public void setProdetail(JxProdetail prodetail) {
		this.prodetail = prodetail;
	}

	public JxPicture getPictures() {
		return pictures;
	}

	public void setPictures(JxPicture pictures) {
		this.pictures = pictures;
	}

	public Long getProt_id() {
		return prot_id;
	}

	public void setProt_id(Long prot_id) {
		this.prot_id = prot_id;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	
}
