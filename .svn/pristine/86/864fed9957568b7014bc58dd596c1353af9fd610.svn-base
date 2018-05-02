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
import com.game.smvc.entity.JxNews;
import com.game.smvc.service.IJxNewsService;

@Service("jxNewsService")
public class JxNewsServiceImpl extends GenericManagerImpl<JxNews,Long> implements IJxNewsService{

	private GenericDao<JxNews, Long> codeDao;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JxNewsServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<JxNews,Long>(JxNews.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}

	@Override
	public List<Map<String, Object>> findInformation(String type) {
		String sql = "select news_type_name,news_content,news_url,news_type from jx_news where news_type = "+type;
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findAllInformation(String type) {
		String sql = "select news_type_name,news_content,news_url,news_type from jx_news where news_title_type = "+type;
		return jdbcTemplate.queryForList(sql);
	}

}
