package com.game.bmanager.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxPrototal;
import com.game.bmanager.service.IJxPrototalService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.Page;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;


@Service("prototalService")
public class JxPrototalServiceImpl extends GenericManagerImpl<JxPrototal, Long> implements IJxPrototalService{
	private GenericDao<JxPrototal, Long> partnerDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxPrototalServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.partnerDao = new GenericDaoHibernate<JxPrototal,Long>(JxPrototal.class,sessionFactory);
        this.dao = this.partnerDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	@Override
	public void updateStaus(Long id) {
		jdbcTemplate.update("update jx_prototal a set a.prot_status =? where a.id = ?",new Object[]{1,id});
	}
	
	//得到商品参数
	@Override
	public List<Map<String, Object>> findparam(String proid) {
		String sql = "select P.prot_name name,P.prod_typename typename,P.prod_wx wx,A.pay_pledge pledge,A.pay_unitprice unitprice from jx_prototal P,jx_pay A where prot_type ='"+proid+"' LIMIT 0,1";
		//String sql = "select prod_typename from jx_prototal where prot_type ='"+name+"' LIMIT 0,1";
		return jdbcTemplate.queryForList(sql);
	}
	
}
