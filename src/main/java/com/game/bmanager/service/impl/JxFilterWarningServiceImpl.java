package com.game.bmanager.service.impl;


import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxFilterWarning;
import com.game.bmanager.service.IJxFilterWarningService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.Page;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;

@Service("filterWarningService")
public class JxFilterWarningServiceImpl extends GenericManagerImpl<JxFilterWarning, Integer> implements IJxFilterWarningService {
	private GenericDao<JxFilterWarning,Integer> warningDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxFilterWarningServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.warningDao = new GenericDaoHibernate<JxFilterWarning,Integer>(JxFilterWarning.class,sessionFactory);
        this.dao = this.warningDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	@Override
	public Page<JxFilterWarning> query(Page<JxFilterWarning> page,
			String orderNo, String phone,String managerNo) {
		String sql = "select * from jx_filter_warning where 1=1 ";
		if(!StringUtils.isBlank(phone)){
			sql = sql + " and user_phone="+phone ;
		}
		if(!StringUtils.isBlank(orderNo)){
			sql = sql +" and order_no="+ orderNo ;
		}
		if(!StringUtils.isBlank(managerNo)){
			sql = sql +" and manager_no="+ managerNo ;
		}
		if(StringUtils.isBlank(phone) && StringUtils.isBlank(orderNo) && StringUtils.isBlank(managerNo)){
			sql = sql+" and status =" + 0; 
		}
		page = warningDao.findPageOnSql(page,sql);
		return page;
	}
	@Override
	public Long queryCount(String temp) {
		Long value = null;
		if(temp.equals("admin")){
			value = jdbcTemplate.queryForLong("select count(*) from jx_filter_warning where status = 0");
		}else{
			value = jdbcTemplate.queryForLong("select count(*) from jx_filter_warning a WHERE a.manager_no in (select t.id from jx_partner t where find_in_set(id, findselltotalnum("+temp+"))) and status = 0");
		}
		return value;
	}
	
	
	@Override
	public String queryLever(String userid) {
		String sql ="select par_level from jx_partner where id ="+userid;
		return jdbcTemplate.queryForObject(sql, String.class);
	}
	@Override
	public Page<JxFilterWarning> queryBypartner(Page<JxFilterWarning> page,
			String orderNo, String phone, String id,String managerNo) {
		String sql = "select * from jx_filter_warning a WHERE a.manager_no in (select id from jx_partner where find_in_set(id, findselltotalnum("+id+")))";
		if(!StringUtils.isBlank(phone)){
			sql = sql + " and user_phone="+phone ;
		}
		if(!StringUtils.isBlank(orderNo)){
			sql = sql +" and order_no="+ orderNo ;
		}
		if(!StringUtils.isBlank(managerNo)){
			sql = sql +" and manager_no="+ managerNo ;
		}
		if(StringUtils.isBlank(phone) && StringUtils.isBlank(orderNo) && StringUtils.isBlank(managerNo)){
			sql = sql+" and status =" + 0; 
		}
		page = warningDao.findPageOnSql(page,sql);
		return page;
	}

	@Override
	public JxFilterWarning findByProNoAndFilter(String pro_no, String filterName) {
	    String sql = "from JxFilterWarning where pro_no='"+pro_no+"' and filter_name='"+filterName+"' and status=0";
	    return warningDao.findOne(sql);
	}
	@Override
	public JxFilterWarning getWarning(String orderno) {
		String sql = "from JxFilterWarning where order_no = '"+orderno+"' and status=0";
		return warningDao.findOne(sql);
	}
	@Override
	public JxFilterWarning getWarning(String orderno, String filterno) {
		String sql = "from JxFilterWarning where order_no = '"+orderno+"' and filter_name='"+filterno+"' and status=0";
		return warningDao.findOne(sql);
	}
	
}
