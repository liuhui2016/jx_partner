package com.game.bmanager.action;

import java.util.List;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.bmanager.service.IJxPayService;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.HibernateUtils;
import com.game.modules.web.CrudActionSupport;
import com.game.modules.web.struts2.Struts2Utils;
import com.game.smvc.entity.JxPay;

@Namespace("/bmanager/pay")
@Results({ @Result(name = "reload", location = "pay.action?authId=${authId}", type = "redirect") })
public class PayAction extends CrudActionSupport<JxPay> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1805414673400396856L;
	
	@Autowired
	private IJxPayService payService;
	
	private JxPay jxpay;
	private Long id;
    private Long oldId;
    private List<Long> ids;
    private Page<JxPay> page = new Page<JxPay>(15);

	@Override
	public JxPay getModel() {
		if (this.id != null) {
            this.jxpay = ((JxPay) payService.get(this.id));
        } else {
            this.jxpay = new JxPay();
        }
     return jxpay;
	}


	@Override
	public String delete() throws Exception {
		if(id != null){
			payService.remove(this.id);
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
        page = payService.searchPage(page, filters);
        return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		
	}

	@Override
	public String save() throws Exception {
		if(jxpay.getPay_typename() == 0){
			jxpay.setPay_unitprice(null);
			jxpay.setPay_flow(null);
		}else if(jxpay.getPay_typename() == 1){
			jxpay.setPay_flow(jxpay.getPay_totalmoney()/jxpay.getPay_unitprice());
		}
		payService.save(jxpay);
		return RELOAD;
	}

	public JxPay getJxpay() {
		return jxpay;
	}

	public void setJxpay(JxPay jxpay) {
		this.jxpay = jxpay;
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


	public Page<JxPay> getPage() {
		return page;
	}

	public void setPage(Page<JxPay> page) {
		this.page = page;
	}
	
}
