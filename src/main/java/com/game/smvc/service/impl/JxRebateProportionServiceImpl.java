package com.game.smvc.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxRebateProportion;
import com.game.smvc.service.IJxRebateProportionService;

@Service("jxRebateProportionService")
public class JxRebateProportionServiceImpl extends
GenericManagerImpl<JxRebateProportion, Long> implements IJxRebateProportionService{

	private GenericDao<JxRebateProportion, Long> jxRebateProportionDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxRebateProportionServiceImpl(SessionFactory sessionFactory,
			DataSource dataSource) {
		this.jxRebateProportionDao = new GenericDaoHibernate<JxRebateProportion, Long>(
				JxRebateProportion.class, sessionFactory);
		this.dao = this.jxRebateProportionDao;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
