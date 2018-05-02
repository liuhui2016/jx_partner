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
import com.game.smvc.entity.JxInformationSafety;
import com.game.smvc.service.IJxInformationSafetyService;

@Service("jxInformationSafetyService")
public class JxInformationSafetyServiceImpl extends GenericManagerImpl<JxInformationSafety, Long>
implements IJxInformationSafetyService{
	private GenericDao<JxInformationSafety, Long> jxInformationSafetyDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxInformationSafetyServiceImpl(SessionFactory sessionFactory,
			DataSource dataSource) {
		this.jxInformationSafetyDao = new GenericDaoHibernate<JxInformationSafety, Long>(
				JxInformationSafety.class, sessionFactory);
		this.dao = this.jxInformationSafetyDao;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Map<String, Object>> findAccount(String username) {
		String sql = "select pay_name,pay_account from jx_alipay_account where p_number = '"+username+"'";
		return jdbcTemplate.queryForList(sql);
	}


}
