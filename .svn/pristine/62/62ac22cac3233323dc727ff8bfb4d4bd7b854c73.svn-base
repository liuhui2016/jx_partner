package com.game.bmanager.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.smvc.entity.JxOrder;
import com.game.smvc.service.IJxOrderService;
import com.game.entity.account.User;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;

@Namespace("/bmanager/order")
@Results({ @Result(name = "reload", location = "order.action?authId=${authId}", type = "redirect") })
public class OrderAction extends CrudActionSupport<JxOrder>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7680327260962182295L;
	
	@Autowired
	private IJxOrderService orderService;
	
	private JxOrder order;
	
	
	private Long id;
	private Long oldId;
	private List<Long> ids;
	private Page<JxOrder> page = new Page<JxOrder>(15);
	
	@Override
	public JxOrder getModel() {
		if (this.id != null) {
			this.order = ((JxOrder) orderService.get(this.id));
		} else {
			this.order = new JxOrder();
		}
		
		
		
		return order;
	}

	@Override
	public String delete() throws Exception {
		return null;
	}

	@Override
	public String input() throws Exception {
		return null;
	}

	@Override
	public String list() throws Exception {
		List<PropertyFilter> filters = HibernateUtils
				.buildPropertyFilters(Struts2Utils.getRequest());
		if (!this.page.isOrderBySetted()) {
			this.page.setOrderBy("id");
			this.page.setOrder("desc");
		}
		page = orderService.searchPage(page, filters);
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public String save() throws Exception {
		return null;
	}

	public JxOrder getOrder() {
		return order;
	}

	public void setOrder(JxOrder order) {
		this.order = order;
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

	public Page<JxOrder> getPage() {
		return page;
	}

	public void setPage(Page<JxOrder> page) {
		this.page = page;
	}

	
}
