package com.game.smvc.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxPhCode;
import com.game.smvc.service.IJxPhCodeService;
@Service("jxPhCodeService")
public class JxPhCodeServiceImpl extends GenericManagerImpl<JxPhCode, Long> implements IJxPhCodeService {
    
    private GenericDao<JxPhCode, Long> codeDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxPhCodeServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.codeDao = new GenericDaoHibernate<JxPhCode,Long>(JxPhCode.class,
                sessionFactory);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dao = this.codeDao;
    }
}
