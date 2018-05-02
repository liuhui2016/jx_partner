package com.game.bmanager.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxAdvpic;
import com.game.bmanager.service.IJxAdvpicService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;

@Service("advpicService")
public class JxAdvpicServiceImpl extends GenericManagerImpl<JxAdvpic,Long>implements IJxAdvpicService{
	private GenericDao<JxAdvpic, Long> partnerDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxAdvpicServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.partnerDao = new GenericDaoHibernate<JxAdvpic,Long>(JxAdvpic.class,sessionFactory);
        this.dao = this.partnerDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<Map<String, Object>> queryAdverByType(String type) {
        
        String sql = "select * from jx_advpic where adv_invildtime >= NOW() and adv_type = " + type;
        return jdbcTemplate.queryForList(sql);
    }
}
