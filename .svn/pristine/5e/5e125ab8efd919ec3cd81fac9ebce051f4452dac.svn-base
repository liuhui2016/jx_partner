package com.game.bmanager.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.JxOpinon;
import com.game.bmanager.service.IJxOpinonService;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;

@Namespace("/bmanager/opinon")
@Results({ @Result(name = "reload", location = "opinon.action?authId=${authId}", type = "redirect") })
public class OpinonAction extends CrudActionSupport<JxOpinon> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1525079400360631151L;

	@Autowired
	private IJxOpinonService service;

	private JxOpinon opinon;

	private Long id;
	private Long oldId;
	private List<Long> ids;
	private Page<JxOpinon> page = new Page<JxOpinon>(15);

	@Override
	public JxOpinon getModel() {
		if (this.id != null) {
			this.opinon = ((JxOpinon) service.get(this.id));
		} else {
			this.opinon = new JxOpinon();
		}
		return opinon;
	}

	@Override
	public String delete() throws Exception {
		if(id!=null){
    		service.remove(id);
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
		page = service.searchPage(page, filters);
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {

	}

	@Override
	public String save() throws Exception {
		if (opinon.getId() == null) {
			opinon.setOp_addtime(new Date());
		}
		opinon.setOp_modtime(new Date());
		service.save(opinon);
		return RELOAD;
	}

	public JxOpinon getOpinon() {
		return opinon;
	}

	public void setOpinon(JxOpinon opinon) {
		this.opinon = opinon;
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

	public Page<JxOpinon> getPage() {
		return page;
	}

	public void setPage(Page<JxOpinon> page) {
		this.page = page;
	}

}
