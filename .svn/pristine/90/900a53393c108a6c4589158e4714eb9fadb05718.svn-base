package com.game.smvc.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxWithdrawalOrder;
import com.game.smvc.service.IJxWithdrawalOrderService;

@Service("jxWithdrawalOrderService")
public class JxWithdrawalOrderServiceImpl extends GenericManagerImpl <JxWithdrawalOrder,Long> implements IJxWithdrawalOrderService{

	private GenericDao<JxWithdrawalOrder, Long> jxWithdrawalOrderDao;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JxWithdrawalOrderServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.jxWithdrawalOrderDao = new GenericDaoHibernate<JxWithdrawalOrder,Long>(JxWithdrawalOrder.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.jxWithdrawalOrderDao;
	}

	@Override
	public List<Map<String, Object>> findAllWithdrawalOrder(String username,int page) {
		String sql = "select * from jx_withdrawal_order where user_number = '"+username+"' limit "+page+",10";
		return jdbcTemplate.queryForList(sql);
	}
}
