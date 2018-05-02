package com.game.smvc.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxNewsItem;
import com.game.smvc.service.IJxNewsItemService;

@Service("jxNewsItemService")
public class JxNewsItemServiceImpl extends GenericManagerImpl<JxNewsItem,Long> implements IJxNewsItemService{

	private GenericDao<JxNewsItem, Long> codeDao;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JxNewsItemServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<JxNewsItem,Long>(JxNewsItem.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}
}
