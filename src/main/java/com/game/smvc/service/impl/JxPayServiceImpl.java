package com.game.smvc.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxPay;
import com.game.smvc.entity.JxPhCode;
import com.game.smvc.service.IJxPayWayService;
@Service("payWayService")
public class JxPayServiceImpl extends GenericManagerImpl<JxPay, Integer> implements IJxPayWayService {

    private GenericDao<JxPay, Integer>codeDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxPayServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.codeDao = new GenericDaoHibernate<JxPay, Integer>(JxPay.class,
                sessionFactory);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dao = this.codeDao;
    }
    
    //得到净水机的价格
	@Override
	public int selectPrice(String name, String type) {
		String sql = "select CAST(pay_totalmoney AS CHAR(20)) price from jx_pay where pay_typeid= '"+name+"' and pay_typename = '"+type+"'";
		return jdbcTemplate.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> findPrice(String proid, String type) {
		String sql = "select CAST(pay_totalmoney AS CHAR(20)) price from jx_pay where pay_typeid= '"+proid+"' and pay_typename = '"+type+"'";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public Map<String, Object> findflow() {
		String sql = "select pay_flow from jx_pay where pay_id = 1";
		return jdbcTemplate.queryForMap(sql);
	}

	
	
}
