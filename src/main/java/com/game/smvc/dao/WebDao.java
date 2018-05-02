package com.game.smvc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("webDao")
public class WebDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public WebDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	/**
	 * 根据用户ID，查询用户的 收藏 和 访问记录数
	 * @param userId
	 * @return
	 */
	public Map<String,Object> queryCollectAccessLog(Integer userId){
		StringBuilder sb = new StringBuilder();
		List<Integer> list = new ArrayList<Integer>();
		sb.append(" SELECT t1.collect,t2.access  ");
		sb.append(" FROM (SELECT COUNT(user_id) collect FROM fk_collection WHERE user_id=? )t1,");
		list.add(userId);
		sb.append(" (SELECT COUNT(user_id) access FROM fk_access WHERE user_id=? )t2");
		list.add(userId);
		return jdbcTemplate.queryForMap(sb.toString(), list.toArray());
	}
	
	
	
	
	
	
	
}
