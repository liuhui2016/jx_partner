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
import com.game.smvc.entity.JxDrawPeople;
import com.game.smvc.service.IJxDrawPeopleService;

@Service("jxDrawPeopleService")
public class JxDrawPeopleServiceImpl extends GenericManagerImpl<JxDrawPeople, Long>
implements IJxDrawPeopleService{
	
	private GenericDao<JxDrawPeople, Long> jxDrawPeopleDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxDrawPeopleServiceImpl(SessionFactory sessionFactory,
			DataSource dataSource) {
		this.jxDrawPeopleDao = new GenericDaoHibernate<JxDrawPeople, Long>(
				JxDrawPeople.class, sessionFactory);
		this.dao = this.jxDrawPeopleDao;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Map<String, Object>> findDrts(String withdrawalOrderNo) {
		String sql = "select id,by_tkr_id number,by_tkr_name name,by_tkr_total_money money,by_tkr_rebates rebates from jx_draw_people where withdrawal_order = '"+withdrawalOrderNo+"'";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public String findUsernameById(String id) {
		String sql = "select by_tkr_id from jx_draw_people where id = "+id+"";
		return jdbcTemplate.queryForObject(sql, String.class);
	}

	@Override
	public int findupdate_state(String withdrawal_order_no) {
		String sql = "update jx_draw_people set withdrawal_state = 0 where withdrawal_order = '"+withdrawal_order_no+"'";
		return jdbcTemplate.update(sql);
	}

	@Override
	public int findupdate_states(String withdrawal_order) {
		String sql = "update jx_draw_people set withdrawal_state = 1 where withdrawal_order = '"+withdrawal_order+"'";
		return jdbcTemplate.update(sql);
	}

}
