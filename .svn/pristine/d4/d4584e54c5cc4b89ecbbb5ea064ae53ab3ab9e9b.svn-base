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
import com.game.smvc.entity.JxStatistical;
import com.game.smvc.service.IJxStatisticalService;

@Service("jxStatisticalService")
public class JxStatisticalServiceImpl extends GenericManagerImpl<JxStatistical,Long>implements IJxStatisticalService{


	private GenericDao<JxStatistical, Long> codeDao;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JxStatisticalServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<JxStatistical,Long>(JxStatistical.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}

	
	@Override
	public JxStatistical selectJxStatisticalByOrderNo(String ordno) {
		JxStatistical jxStatistical = codeDao.findOne("from JxStatistical where ord_no = ?",ordno);
		if(jxStatistical == null){
			jxStatistical = new JxStatistical();
		}
		return jxStatistical;
	}


	@Override
	public int update(int tds, int temperature, String restflow,String ordno) {
		String sql = "update jx_statistical set sta_tds = "+tds+",sta_temperature = "+temperature+",pro_restflow = "+restflow+" where ord_no = "+ordno;
		return jdbcTemplate.update(sql);
	}


	@Override
	public int findTds(String uid, String time) {
		String sql = "select sta_tds from jx_statistical where u_id = '"+uid+"' and sta_modtime like '%"+time+"%'";
		return jdbcTemplate.queryForInt(sql);
	}


	@Override
	public List<Map<String, Object>> findModTds(String uid, String time) {
		String sql = "select sta_tds from jx_statistical where u_id = '"+uid+"' and sta_modtime like '%"+time+"%'";
		return jdbcTemplate.queryForList(sql);
	}


	//根据uid获取添加时间
	@Override
	public List<Map<String, Object>> findTime(String uid) {
		String sql = "select sta_addtime from jx_statistical where u_id = '"+uid+"' order by sta_addtime desc LIMIT 1";
		return jdbcTemplate.queryForList(sql);
	}

	//根据uid获取更新时间
	@Override
	public List<Map<String, Object>> findTimes(String uid) {
		String sql = "select sta_modtime from jx_statistical where u_id = '"+uid+"' order by sta_modtime desc LIMIT 1";
		return jdbcTemplate.queryForList(sql);
	}

	//获取TDSx信息
	@Override
	public List<Map<String, Object>> findAddTds(String uid, String time) {
		String sql = "select sta_tds from jx_statistical where u_id = '"+uid+"' and sta_addtime like '%"+time+"%'";
		return jdbcTemplate.queryForList(sql);
	}


	@Override
	public Map<String, Object> findStaAddtime(String uid) {
		String sql = "SELECT S.sta_addtime FROM jx_order O,jx_statistical S where O.ord_no = S.ord_no and O.u_id = S.u_id and O.u_id = '"+uid+"' and O.ord_protypeid = 0 and O.ord_status = 3 ORDER BY S.sta_addtime DESC LIMIT 1";
		return jdbcTemplate.queryForMap(sql);
	}


	@Override
	public Map<String, Object> findStaModtime(String uid) {
		String sql = "SELECT S.sta_modtime FROM jx_order O,jx_statistical S where O.ord_no = S.ord_no and O.u_id = S.u_id and O.u_id = '"+uid+"' and O.ord_protypeid = 0 and O.ord_status = 3 ORDER BY S.sta_modtime DESC LIMIT 1";
		return jdbcTemplate.queryForMap(sql);
	}


	@Override
	public int findAWater(String uid) {
		String sql = "SELECT S.output_water FROM jx_order O,jx_statistical S where O.ord_no = S.ord_no and O.u_id = S.u_id and O.u_id = '"+uid+"' and O.ord_protypeid = 0 and O.ord_status = 3 ORDER BY S.sta_addtime DESC LIMIT 1";
		return jdbcTemplate.queryForInt(sql);
	}


	@Override
	public int findMWater(String uid) {
		String sql = "SELECT S.output_water FROM jx_order O,jx_statistical S where O.ord_no = S.ord_no and O.u_id = S.u_id and O.u_id = '"+uid+"' and O.ord_protypeid = 0 and O.ord_status = 3 ORDER BY S.sta_modtime DESC LIMIT 1";
		return jdbcTemplate.queryForInt(sql);
	}


	@Override
	public List<Map<String, Object>> findNull(String uid) {
		String sql = "select * from jx_statistical where u_id = '"+uid+"' ";
		return jdbcTemplate.queryForList(sql);
	}



	
}
