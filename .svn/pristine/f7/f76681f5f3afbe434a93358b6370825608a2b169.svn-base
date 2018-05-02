package com.game.bmanager.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.modules.orm.Page;
import com.game.modules.web.CrudActionSupport;
import com.game.smvc.entity.JxWithdrawalOrder;
import com.game.smvc.service.IJxPartnerRebateService;
import com.game.smvc.service.IJxWithdrawalOrderService;

@Namespace("/bmanager/trade")
@Results({ @Result(name = "reload", location = "trade.action?authId=${authId}", type = "redirect") })
public class TradeAction extends CrudActionSupport<JxWithdrawalOrder>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JxWithdrawalOrder jxWithdrawalOrder;
	@Autowired
	private IJxWithdrawalOrderService jxWithdrawalOrderService;
	@Autowired
	private IJxPartnerRebateService jxPartnerRebateService;
	
	private Long id;
	private Long oldId;
	private List<Long> ids;
	private Page<JxWithdrawalOrder> page = new Page<JxWithdrawalOrder>(15);
	
	@Override
	public JxWithdrawalOrder getModel() {
		if (this.id != null) {
			this.jxWithdrawalOrder = ((JxWithdrawalOrder) jxWithdrawalOrderService.get(this.id));
		} else {
			this.jxWithdrawalOrder = new JxWithdrawalOrder();
		}
		return jxWithdrawalOrder;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String input() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		jxWithdrawalOrder.getUser_number();
		System.out.println("产品经理编号:"+jxWithdrawalOrder.getUser_number());
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public JxWithdrawalOrder getJxWithdrawalOrder() {
		return jxWithdrawalOrder;
	}

	public void setJxWithdrawalOrder(JxWithdrawalOrder jxWithdrawalOrder) {
		this.jxWithdrawalOrder = jxWithdrawalOrder;
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

	public Page<JxWithdrawalOrder> getPage() {
		return page;
	}

	public void setPage(Page<JxWithdrawalOrder> page) {
		this.page = page;
	}

}
