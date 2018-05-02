package com.game.smvc.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxCommunitySale;
import com.game.smvc.entity.JxShoppingCart;
import com.game.smvc.service.IJxCommunitySaleService;

@Service("jxCommunitySaleService")
public class JxCommunitySaleServiceImpl extends GenericManagerImpl <JxCommunitySale,Long> implements IJxCommunitySaleService{

	private GenericDao<JxCommunitySale, Long> jxCommunitySaleDao;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JxCommunitySaleServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.jxCommunitySaleDao = new GenericDaoHibernate<JxCommunitySale,Long>(JxCommunitySale.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.jxCommunitySaleDao;
	}

	@Override
	public JxCommunitySale findpubCommunity(String uid, String pubid,
			String cause) {
		JxCommunitySale sale = jxCommunitySaleDao.findOne("from JxCommunitySale where u_id = '"+uid+"' and pub_id = '"+pubid+"' and rpt_cause = '"+cause+"'");
		if(sale == null){
			sale = new JxCommunitySale();
		}
		return sale;
	}
}
