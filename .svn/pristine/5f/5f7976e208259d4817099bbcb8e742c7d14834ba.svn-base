package com.game.smvc.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxMessages;
import com.game.smvc.service.IJxMessageService;
@Service("messageService")
public class JxMessageServicImpl extends
		GenericManagerImpl<JxMessages, Long> implements IJxMessageService {

    private GenericDao<JxMessages, Long>codeDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxMessageServicImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.codeDao = new GenericDaoHibernate<JxMessages, Long>(JxMessages.class,
                sessionFactory);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dao = this.codeDao;
    }
	@Override
	public int deleteMessageById(String id) {
		String sql="DELETE from jx_messages WHERE id=?";
		return jdbcTemplate.update(sql,id);
	}
	@Override
	public int queryMessagestotal(String userid) {
		String sql="select count(*) from jx_messages where u_id=? and isread=0";
		return jdbcTemplate.queryForInt(sql, userid);
	}

}
