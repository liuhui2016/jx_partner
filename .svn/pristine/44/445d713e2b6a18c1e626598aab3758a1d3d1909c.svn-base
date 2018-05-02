package com.game.bmanager.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.JxMenu;
import com.game.bmanager.service.IJxMenuService;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;
import com.game.util.Constants;
import com.game.util.Md5Encoder;

@Namespace("/bmanager/menu")
@Results({ @Result(name = "reload", location = "menu.action?authId=${authId}", type = "redirect") })
public class MenuAction extends CrudActionSupport<JxMenu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4806161354167051269L;

	@Autowired
	private IJxMenuService menuService;

	private JxMenu menu;

	private String iconfileFileName;

	public String getIconfileFileName() {
		return iconfileFileName;
	}

	public void setIconfileFileName(String iconfileFileName) {
		this.iconfileFileName = iconfileFileName;
	}

	private File iconfile;

	public File getIconfile() {
		return iconfile;
	}

	public void setIconfile(File iconfile) {
		this.iconfile = iconfile;
	}

	private Long id;
	private Long oldId;
	private List<Long> ids;
	private Page<JxMenu> page = new Page<JxMenu>(15);

	@Override
	public JxMenu getModel() {
		if (this.id != null) {
			this.menu = ((JxMenu) menuService.get(this.id));
		} else {
			this.menu = new JxMenu();
		}
		return menu;
	}

	@Override
	public String delete() throws Exception {
		if (id != null) {
			menuService.remove(id);
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
		page = menuService.searchPage(page, filters);
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	@Override
	public String save() throws Exception {
		boolean flag = false;
		if (menu.getId() == null) {
			flag = true;
			menu.setMenu_addtime(new Date());
		}
		menu.setMenu_modtime(new Date());

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

			menu.setMenu_icourl(iconUrl + "/" + newFileName);// 下载路径
		} else {
			System.out.println("pic is null");
		}
		if (flag) {
			this.logToDB(106, "新增图片ID：" + menu.getId());
		} else {
			this.logToDB(106, "修改图片ID：" + menu.getId());
		}
		menuService.save(menu);
		return RELOAD;
	}

	public IJxMenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(IJxMenuService menuService) {
		this.menuService = menuService;
	}

	public JxMenu getMenu() {
		return menu;
	}

	public void setMenu(JxMenu menu) {
		this.menu = menu;
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

	public Page<JxMenu> getPage() {
		return page;
	}

	public void setPage(Page<JxMenu> page) {
		this.page = page;
	}

}
