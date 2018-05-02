package com.game.bmanager.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxShare;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.bmanager.service.IJxShareService;
@Service("shareService")
public class JxShareServiceImpl extends GenericManagerImpl<JxShare, Long> implements IJxShareService{
    private GenericDao<JxShare, Long> jxSharedao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxShareServiceImpl(SessionFactory sessionFactory,DataSource dataSource){
        this.jxSharedao = new GenericDaoHibernate<JxShare,Long>(JxShare.class,sessionFactory);
        this.dao = this.jxSharedao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void updateOtherInvalid(){
        String sql = "update jx_share set share_status = 0";
        jdbcTemplate.execute(sql);
    }
}
