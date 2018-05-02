package com.game.smvc.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxAfterSales;
import com.game.smvc.service.IJxAfterSalesService;

@Service("jxAfterSalesService")
public class JxAfterSalesServiceImpl extends GenericManagerImpl <JxAfterSales,Long> implements IJxAfterSalesService{

	private GenericDao<JxAfterSales, Long> codeDao;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JxAfterSalesServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<JxAfterSales,Long>(JxAfterSales.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}
	
}

