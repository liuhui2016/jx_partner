package com.game.bmanager.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxDetailPicture;
import com.game.bmanager.entity.JxProdetail;
import com.game.bmanager.entity.JxView;
import com.game.bmanager.service.IJxProdetailService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.Page;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;

@Service("prodetailService")
public class JxProdetailServiceImpl extends GenericManagerImpl<JxProdetail, Long> implements IJxProdetailService{
	private GenericDao<JxProdetail, Long> partnerDao;
	private GenericDao<JxView, Long> detailDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxProdetailServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
    	this.partnerDao = new GenericDaoHibernate<JxProdetail,Long>(JxProdetail.class,sessionFactory);
        this.detailDao = new GenericDaoHibernate<JxView,Long>(JxView.class,sessionFactory);
    	this.dao = this.partnerDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	@Override
	public Page<JxView> queryByProtId(Page<JxView> page, Long protId) {
		page  = detailDao.findPageOnSql(page, "select * from jx_prodetail a INNER JOIN jx_picture b on a.prod_picid = b.id and a.prot_id = "+ protId);
		return page;
	}
	@Override
	public void update(JxView jxView) {
		jdbcTemplate.update("update jx_prodetail a set a.prod_name =? ,a.prod_modtime =? where a.id = ?", new Object[]{jxView.getProd_name(),jxView.getProd_modtime(),jxView.getId()});
	}
	
}
