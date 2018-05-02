package com.game.bmanager.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.JxUpflt;
import com.game.bmanager.service.IJxUpfltService;
import com.game.modules.orm.Page;
import com.game.modules.web.CrudActionSupport;

@Namespace("/bmanager/upflt")
@Results({ @Result(name = "reload", location = "upflt.action?authId=${authId}", type = "redirect") })
public class UpfltAction extends CrudActionSupport<JxUpflt>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2306497802123641926L;
	
	@Autowired
	private IJxUpfltService upfltService;
	
	private JxUpflt jxUpflt;
	private Long id;
    private Long oldId;
    private List<Long> ids;
    
    private Page<JxUpflt> page = new Page<JxUpflt>(15);
    
    private String u_name;

	@Override
	public JxUpflt getModel() {
		if (this.id != null) {
            this.jxUpflt = ((JxUpflt) upfltService.get(this.id));
        } else {
            this.jxUpflt = new JxUpflt();
        }
		return jxUpflt;
	}

	@Override
	public String delete() throws Exception {
		if (id != null) {
			upfltService.remove(id);
		}
		return RELOAD;
	}

	@Override
	public String input() throws Exception {
		return INPUT;
	}

	@Override
	public String list() throws Exception {
        page = upfltService.queryUserUpdate(page);
        return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public String save() throws Exception {
		if(this.id == null){
			jxUpflt.setFlt_addtime(new Date());
		}
		jxUpflt.setFlt_othertime(new Date());
//		upfltService.save(jxUpflt);
		upfltService.saveUpflt(jxUpflt);
		return RELOAD;
	}

	public JxUpflt getJxUpflt() {
		return jxUpflt;
	}

	public void setJxUpflt(JxUpflt jxUpflt) {
		this.jxUpflt = jxUpflt;
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

	public Page<JxUpflt> getPage() {
		return page;
	}

	public void setPage(Page<JxUpflt> page) {
		this.page = page;
	}

	public String getU_name() {
		return u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	
}
