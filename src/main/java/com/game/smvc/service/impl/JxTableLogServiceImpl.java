package com.game.smvc.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxTableLog;
import com.game.smvc.service.IJxTableLogService;

@Service("jxTableLogService")
public class JxTableLogServiceImpl extends GenericManagerImpl<JxTableLog,Long>implements IJxTableLogService{

	private GenericDao<JxTableLog, Long> jxTableLogDao;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JxTableLogServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.jxTableLogDao = new GenericDaoHibernate<JxTableLog,Long>(JxTableLog.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.jxTableLogDao;
	}

	
}
