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
import com.game.smvc.entity.JxAlipayAccount;
import com.game.smvc.service.IJxAlipayAccountService;

@Service("jxAlipayAccountService")
public class JxAlipayAccountServiceImpl extends GenericManagerImpl<JxAlipayAccount, Long>
implements IJxAlipayAccountService{
	
	private GenericDao<JxAlipayAccount, Long> jxAlipayAccountDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxAlipayAccountServiceImpl(SessionFactory sessionFactory,
			DataSource dataSource) {
		this.jxAlipayAccountDao = new GenericDaoHibernate<JxAlipayAccount, Long>(
				JxAlipayAccount.class, sessionFactory);
		this.dao = this.jxAlipayAccountDao;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Map<String,Object>> findAccount(String username) {
		String sql = "select * from jx_alipay_account where p_number = '"+username+"'";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public int deleteAccount(String username) {
		String sql = "delete from jx_alipay_account where p_number = '"+username+"'";
		return jdbcTemplate.update(sql);
	}

}
