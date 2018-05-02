package com.game.bmanager.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxOpinon;
import com.game.bmanager.service.IJxOpinonService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;

@Service("opinonService")
public class JxOpinonServiceImpl extends GenericManagerImpl<JxOpinon, Long> implements IJxOpinonService{
	private GenericDao<JxOpinon, Long> partnerDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxOpinonServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.partnerDao = new GenericDaoHibernate<JxOpinon,Long>(JxOpinon.class,sessionFactory);
        this.dao = this.partnerDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	@Override
	public String findUsernameByPhone(String phoneNum) {
		List<String> name = jdbcTemplate.queryForList("select u_snname from jx_user where u_phone=?", String.class, phoneNum); 
		return name.get(0);
	}

	

}
