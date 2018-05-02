package com.game.bmanager.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.JxAdvpic;
import com.game.bmanager.service.IJxAdvpicService;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;
import com.game.util.Constants;
import com.game.util.Md5Encoder;


@Namespace("/bmanager/advpic")
@Results({ @Result(name = "reload", location = "advpic.action?authId=${authId}", type = "redirect") })
public class AdvpicAction extends CrudActionSupport<JxAdvpic>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7851191912689823824L;
	
	@Autowired
	private IJxAdvpicService advpicService;
	
	private JxAdvpic advpic;
	
	private File iconfile;
	    
	private String iconfileFileName;
	
	private Long id;
	private Long oldId;
	private List<Long> ids;
	private Page<JxAdvpic> page = new Page<JxAdvpic>(15);

	@Override
	public JxAdvpic getModel() {
		if (this.id != null) {
			this.advpic = ((JxAdvpic) advpicService.get(this.id));
		} else {
			this.advpic = new JxAdvpic();
		}
		return advpic;
	}

	@Override
	public String delete() throws Exception {
		if(id != null){
			advpicService.remove(id);
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
		page = advpicService.searchPage(page, filters);
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public String save() throws Exception {
		boolean flag = false;
		if (advpic.getId() == null) {
			flag = true;
			advpic.setAdv_addtime(new Date());
		}
		advpic.setAdv_modtime(new Date());
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

			advpic.setAdv_imgurl(iconUrl + "/" + newFileName);// 下载路径
			advpic.setAdv_dir(targeta.getPath());
			
		} else {
			System.out.println("pic is null");
		}
		if (flag) {
			this.logToDB(106, "新增图片ID：" + advpic.getId());
		} else {
			this.logToDB(106, "修改图片ID：" + advpic.getId());
		}
		advpicService.save(advpic);
		return RELOAD;
	}

	public JxAdvpic getAdvpic() {
		return advpic;
	}

	public void setAdvpic(JxAdvpic advpic) {
		this.advpic = advpic;
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

	public Page<JxAdvpic> getPage() {
		return page;
	}

	public void setPage(Page<JxAdvpic> page) {
		this.page = page;
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
