package com.game.bmanager.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxAdvpic;
import com.game.bmanager.service.IJxPayService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxPay;

@Service("payService")
public class IJxPayServiceImpl extends GenericManagerImpl<JxPay,Long> implements IJxPayService{
	private GenericDao<JxPay,Long> partnerDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public IJxPayServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.partnerDao = new GenericDaoHibernate<JxPay,Long>(JxPay.class,sessionFactory);
        this.dao = this.partnerDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
