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
import com.game.smvc.entity.JxSpider;
import com.game.smvc.service.IJxSpiderService;

@Service("jxSpiderService")
public class JxSpiderServiceImpl extends GenericManagerImpl<JxSpider,Long>implements IJxSpiderService{

	private GenericDao<JxSpider, Long> jxSpiderDao;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JxSpiderServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.jxSpiderDao = new GenericDaoHibernate<JxSpider,Long>(JxSpider.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.jxSpiderDao;
	}

	@Override
	public List<Map<String, Object>> findurl(int id) {
		String sql = "SELECT jx_linkhref FROM jx_spider where id = "+id;
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findRecreation(String id, int page) {
		String sql = "select jx_content title,jx_linktext img,jx_linkhref video from jx_spider where id = '"+id+"' ORDER BY addtime DESC LIMIT "+page+",10 ";
		return jdbcTemplate.queryForList(sql);
	}
}
