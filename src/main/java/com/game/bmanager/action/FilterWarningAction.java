package com.game.bmanager.action;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.JxFilterWarning;
import com.game.bmanager.service.IJxFilterWarningService;
import com.game.entity.account.User;
import com.game.modules.orm.Page;
import com.game.modules.web.CrudActionSupport;

@Namespace("/bmanager/filterwarning")
@Results({ @Result(name = "reload", location = "filter-warning.action?authId=${authId}", type = "redirect") })
public class FilterWarningAction extends CrudActionSupport<JxFilterWarning>{

    private static final long serialVersionUID = 1L;
    
    @Autowired
    private IJxFilterWarningService filterWarningService;

    private JxFilterWarning entity;
    
    private Integer id;
    private Integer oldId;
    private List<Integer> ids;
    private Page<JxFilterWarning> page = new Page<JxFilterWarning>(15);
    
    private User user;
    
    private String orderNo;
    
    private String phone;
    
    private String managerNo;
    
    @Override
    public JxFilterWarning getModel() {
        if (this.id != null) {
            this.entity = ((JxFilterWarning) filterWarningService.get(this.id));
        } else {
            this.entity = new JxFilterWarning();
        }
        if(user==null){
            user=super.getUser(); 
        }
        return entity;
    }

    @Override
    public String delete() throws Exception {
        if (id != null) {
            filterWarningService.remove(id);
        }
        return RELOAD;
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    @Override
    public String list() throws Exception {
    	if(user.getUsername().equals("admin")){
    		page = filterWarningService.query(page,orderNo,phone,managerNo);
    	}else{
    		page = filterWarningService.queryBypartner(page,orderNo,phone,user.getUsername(),managerNo);
    	}
        return SUCCESS;
    }

    @Override
    protected void prepareModel() throws Exception {
        
    }

    @Override
    public String save() throws Exception {
    	entity.setStatus(1);
    	entity.setModify_time(new Date());
    	filterWarningService.save(entity);
        return RELOAD;
    }
    
	public JxFilterWarning getEntity() {
		return entity;
	}

	public void setEntity(JxFilterWarning entity) {
		this.entity = entity;
	}

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

	public Page<JxFilterWarning> getPage() {
		return page;
	}

	public void setPage(Page<JxFilterWarning> page) {
		this.page = page;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getManagerNo() {
		return managerNo;
	}

	public void setManagerNo(String managerNo) {
		this.managerNo = managerNo;
	}
	
}
