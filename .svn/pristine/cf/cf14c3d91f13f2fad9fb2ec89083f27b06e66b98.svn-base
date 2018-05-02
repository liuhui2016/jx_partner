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
import com.game.smvc.entity.JxPartnerRebate;
import com.game.smvc.service.IJxPartnerRebateService;

@Service("jxPartnerRebateService")
public class JxPartnerRebateServiceImpl extends
GenericManagerImpl<JxPartnerRebate, Long> implements IJxPartnerRebateService{

	private GenericDao<JxPartnerRebate, Long> jxPartnerRebateDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxPartnerRebateServiceImpl(SessionFactory sessionFactory,
			DataSource dataSource) {
		this.jxPartnerRebateDao = new GenericDaoHibernate<JxPartnerRebate, Long>(
				JxPartnerRebate.class, sessionFactory);
		this.dao = this.jxPartnerRebateDao;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Map<String,Object>> findLastAddtime(String username) {
		String sql = "SELECT * from jx_partner_rebate where user_name = '"+username+"' and w_state = 3 ORDER BY add_time DESC LIMIT 1";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findLastAddtimes(String user_name) {
		String sql = "SELECT * from jx_partner_rebate where user_name = '"+user_name+"' and w_state = 3 and withdrawal_state = 3 ORDER BY add_time DESC LIMIT 1";
		return jdbcTemplate.queryForList(sql);
	}
}
