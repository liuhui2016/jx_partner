package com.game.smvc.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxPwd;
import com.game.smvc.service.IJxPwdService;

@Service("jxPwdService")
public class JxPwdServiceImpl extends GenericManagerImpl<JxPwd, Long>
implements IJxPwdService{

	private GenericDao<JxPwd, Long> jxPwdDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxPwdServiceImpl(SessionFactory sessionFactory,
			DataSource dataSource) {
		this.jxPwdDao = new GenericDaoHibernate<JxPwd, Long>(
				JxPwd.class, sessionFactory);
		this.dao = this.jxPwdDao;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

}
