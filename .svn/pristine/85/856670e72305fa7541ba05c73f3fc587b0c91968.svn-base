package com.game.smvc.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxPartnerMessages;
import com.game.smvc.service.IJxPartnerMessagesService;

@Service("jxPartnerMessagesService")
public class JxPartnerMessagesServiceImpl extends
GenericManagerImpl<JxPartnerMessages, Long> implements IJxPartnerMessagesService{

	 private GenericDao<JxPartnerMessages, Long>jxPartnerMessagesDao;
	    private JdbcTemplate jdbcTemplate;
	    @Autowired
	    public JxPartnerMessagesServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
	        this.jxPartnerMessagesDao = new GenericDaoHibernate<JxPartnerMessages, Long>(JxPartnerMessages.class,
	                sessionFactory);
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
	        this.dao = this.jxPartnerMessagesDao;
	    }
		@Override
		public List<Map<String, Object>> findAllPartnerMessages(
				String username, int page) {
			String sql = "select * from jx_partner_messages where p_name = '"+username+"' order by p_isread ASC,message_time DESC limit "+page+",10";
			return jdbcTemplate.queryForList(sql);
		}
		
		@Override
		public int findDelPartnerMessages(String id) {
			String sql = "delete from jx_partner_messages where p_id in("+id+")";
			return jdbcTemplate.update(sql);
		}
		@Override
		public int findUpdatePartnerMessages(String id) {
			String sql = "update jx_partner_messages set p_isread = 1 where p_id in("+id+")";
			return jdbcTemplate.update(sql);
		}
		@Override
		public int findMessagestotal(String username) {
			String sql="select count(*) from jx_partner_messages where p_name = '"+username+"' and p_isread = 0";
			return jdbcTemplate.queryForInt(sql);
		}
}
