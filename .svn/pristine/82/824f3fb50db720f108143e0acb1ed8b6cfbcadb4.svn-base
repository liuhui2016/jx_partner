package com.game.bmanager.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxView;
import com.game.bmanager.service.IJxViewService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;

@Service("viewService")
public class JxViewServiceImpl extends GenericManagerImpl<JxView, Long> implements IJxViewService{
	private GenericDao<JxView, Long> partnerDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxViewServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.partnerDao = new GenericDaoHibernate<JxView,Long>(JxView.class,sessionFactory);
        this.dao = this.partnerDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
