package com.game.bmanager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxFilterLife;
import com.game.bmanager.service.IJxFilterLifeService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
@Service("jxFilterLifeService")
public class JxFilterLifeServiceImpl extends GenericManagerImpl<JxFilterLife, Long> implements IJxFilterLifeService {
    private GenericDao<JxFilterLife, Long> tmpDao;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JxFilterLifeServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.tmpDao = new GenericDaoHibernate<JxFilterLife,Long>(JxFilterLife.class,sessionFactory);
        this.dao = this.tmpDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    //滤芯寿命
    @Override
    public List<Map<String, Object>> queryFilterLifeByProvince(String code) {
        String sql = "select pp,cto,ro,t33,wfr from jx_proflt_life where code like '"+code+"%'";
        return jdbcTemplate.queryForList(sql);
    }

	@Override
	public Long queryCode(String temp) {
		String sql ="select count(*) from jx_proflt_life where code like '"+temp+"%'";
		return jdbcTemplate.queryForLong(sql);
	}

	//旧绑定方法
	@Override
	public List<Map<String, Object>> queryFilterLifeByProOn(String proNo) {
		String sql = "select prf_pp,prf_cto,prf_ro,prf_t33,prf_wfr from jx_proflt where pro_no like '"+proNo+"%'";
		return jdbcTemplate.queryForList(sql);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, List<Map<String, Object>>>> queryFilterLifeByProOns(String proNo,
			String code) {
		if(proNo.equals("")||code.equals("")){
			return (List<Map<String, List<Map<String, Object>>>>) new Result(Errors.PARAM_ERROR);
		}
		List<Map<String, List<Map<String, Object>>>> list = new ArrayList<Map<String, List<Map<String, Object>>>>();
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
			String sql1 = "select pp,cto,ro,t33,wfr from jx_proflt_life where code like '"+code+"%'";
			List<Map<String, Object>> codes = this.jdbcTemplate.queryForList(sql1);
			
			String sql = "select prf_pp as pp,prf_cto as cto,prf_ro as ro,prf_t33 as t33,prf_wfr as wfr from jx_proflt where pro_no like '"+proNo+"%'";
			List<Map<String, Object>> prono = this.jdbcTemplate.queryForList(sql);
			map.put("code", codes);
			map.put("pro_no", prono);
		list.add(map);
		return list;
	}
	
	
	
}
