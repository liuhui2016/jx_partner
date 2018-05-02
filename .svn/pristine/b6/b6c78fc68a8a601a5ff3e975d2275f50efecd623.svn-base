package com.game.smvc.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxRebates;
import com.game.smvc.service.IJxRebatesService;

@Service("jxRebatesService")
public class JxRebatesServiceImpl extends GenericManagerImpl<JxRebates, Long>
implements IJxRebatesService{
	
	private GenericDao<JxRebates, Long> jxRebatesDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxRebatesServiceImpl(SessionFactory sessionFactory,
			DataSource dataSource) {
		this.jxRebatesDao = new GenericDaoHibernate<JxRebates, Long>(
				JxRebates.class, sessionFactory);
		this.dao = this.jxRebatesDao;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
