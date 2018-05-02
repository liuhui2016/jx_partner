package com.game.bmanager.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.entity.JxFilterLife;
import com.game.bmanager.service.IJxFilterLifeService;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;

@Namespace("/bmanager/filterlife")
@Results({ @Result(name = "reload", location = "filter-life.action?authId=${authId}", type = "redirect") })
public class FilterLifeAction extends CrudActionSupport<JxFilterLife>{

    private static final long serialVersionUID = -1228774549738703755L;
    @Autowired
    private IJxFilterLifeService jxFilterLifeService;
    
    private JxFilterLife filterFife;
    
    private Long id;
    private Long oldId;
    private List<Long> ids;
    
    private Page<JxFilterLife> page = new Page<JxFilterLife>(15);

    @Override
    public JxFilterLife getModel() {
    	if (this.id != null) {
            this.filterFife = ((JxFilterLife) jxFilterLifeService.get(this.id));
        } else {
            this.filterFife = new JxFilterLife();
        }
		return filterFife;
    }

    @Override
    public String list() throws Exception {
    	List<PropertyFilter> filters = HibernateUtils
                .buildPropertyFilters(Struts2Utils.getRequest());
        if (!this.page.isOrderBySetted()) {
            this.page.setOrderBy("id");
            this.page.setOrder("desc");
        }
        page = jxFilterLifeService.searchPage(page, filters);
        return SUCCESS;
    }

    @Override
    public String input() throws Exception {
        return INPUT;
    }

    @Override
    public String save() throws Exception {
    	jxFilterLifeService.save(filterFife);
        return RELOAD;
    }

    @Override
    public String delete() throws Exception {
    	if(id != null){
    		jxFilterLifeService.remove(id);
    	}
        return RELOAD;
    }

    @Override
    protected void prepareModel() throws Exception {
        
    }

	public JxFilterLife getFilterFife() {
		return filterFife;
	}

	public void setFilterFife(JxFilterLife filterFife) {
		this.filterFife = filterFife;
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

	public Page<JxFilterLife> getPage() {
		return page;
	}

	public void setPage(Page<JxFilterLife> page) {
		this.page = page;
	}
    
}
