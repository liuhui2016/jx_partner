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
import com.game.smvc.entity.JxFilterElement;
import com.game.smvc.service.IJxFilterElementService;


@Service("filterElementService")
public class JxFilterElementServiceImpl extends GenericManagerImpl<JxFilterElement,Long>implements IJxFilterElementService{

	private GenericDao<JxFilterElement, Long> codeDao;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JxFilterElementServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<JxFilterElement,Long>(JxFilterElement.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}

	@Override
	public List<Map<String, Object>> getFilterElementNo(String pro_no) {
		String sql = "select fet_pp pp,fet_cto cto,fet_ro ro,fet_t33 t33,fet_wfr wfr from jx_filter_element where pro_no like '"+pro_no+"%'";
		return jdbcTemplate.queryForList(sql);
	}
	
	

}
