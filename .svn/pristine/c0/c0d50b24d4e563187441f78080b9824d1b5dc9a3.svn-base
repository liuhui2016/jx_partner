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
import com.game.bmanager.service.IJxPictureService;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;
import com.game.util.Constants;
import com.game.util.Md5Encoder;

@Namespace("/bmanager/picture")
@Results({ @Result(name = "reload", location = "picture.action?authId=${authId}", type = "redirect") })
public class PictureAction extends CrudActionSupport<JxPicture>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6769469922322210087L;

	@Autowired
	private IJxPictureService pictrueService;
	
	private JxPicture pictrue;
	
	private Long id;
    private Long oldId;
    private List<Long> ids;
    private Page<JxPicture> page = new Page<JxPicture>(15);
    
    private File iconfile;
    
    private String iconfileFileName;
    
    private Long prod_picid;

	@Override
	public JxPicture getModel() {
		 if (this.id != null) {
	            this.pictrue = ((JxPicture) pictrueService.get(this.id));
	        } else {
	            this.pictrue = new JxPicture();
	        }
	     return pictrue;
	}

	@Override
	public String delete() throws Exception {
		if (id != null) {
			pictrueService.remove(id);
		}
		prod_picid = pictrue.getId();
		return picture();
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
        page = pictrueService.searchPage(page, filters);
        return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public String save() throws Exception {
		boolean flag = false;
		if (pictrue.getId() == null) {
			flag = true;
			pictrue.setPic_addtime(new Date());
		}
		pictrue.setPic_modtime(new Date());

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

			pictrue.setPic_url(iconUrl + "/" + newFileName);// 下载路径
		} else {
			System.out.println("pic is null");
		}
		if (flag) {
			this.logToDB(106, "新增图片ID：" + pictrue.getId());
		} else {
			this.logToDB(106, "修改图片ID：" + pictrue.getId());
		}
//		pictrue.setPic_default(0);
		pictrueService.save(pictrue);
		prod_picid = pictrue.getId();
		return picture();
	}
	
	public String picture() throws Exception{
		page = pictrueService.queryByPicId(page,prod_picid);
		return SUCCESS;
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

	public Page<JxPicture> getPage() {
		return page;
	}

	public void setPage(Page<JxPicture> page) {
		this.page = page;
	}

	public JxPicture getPictrue() {
		return pictrue;
	}

	public void setPictrue(JxPicture pictrue) {
		this.pictrue = pictrue;
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

	public Long getProd_picid() {
		return prod_picid;
	}

	public void setProd_picid(Long prod_picid) {
		this.prod_picid = prod_picid;
	}
	
}
