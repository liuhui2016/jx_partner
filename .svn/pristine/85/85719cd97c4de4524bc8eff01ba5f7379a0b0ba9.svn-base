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
import com.game.smvc.entity.JxWater;
import com.game.smvc.service.IJxWaterService;

@Service("jxWaterService")
public class JxWaterServiceImpl extends GenericManagerImpl <JxWater,Long> implements IJxWaterService{

	private GenericDao<JxWater, Long> codeDao;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JxWaterServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<JxWater,Long>(JxWater.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}

	@Override
	public Map<String, Object> findCodeTds(String cityCode) {
		String sql = "select water_tds from jx_water where city_code like '%"+cityCode+"%' ";
		return jdbcTemplate.queryForMap(sql);
	}

	
}
