package com.game.bmanager.service.impl;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxUpflt;
import com.game.bmanager.service.IJxUpfltService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.Page;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;

@Service("upfltService")
public class JxUpfltServiceImpl extends GenericManagerImpl<JxUpflt, Long> implements IJxUpfltService{
	private GenericDao<JxUpflt, Long> partnerDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxUpfltServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.partnerDao = new GenericDaoHibernate<JxUpflt,Long>(JxUpflt.class,sessionFactory);
        this.dao = this.partnerDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	@Override
	public Page<JxUpflt> queryUserUpdate(Page<JxUpflt> page) {
		page  = partnerDao.findPageOnSql(page, "select a.*,b.u_name from jx_upflt a INNER JOIN jx_user b on b.u_id=a.JX__U_ID ");
		return page;
	}
	@Override
	public void saveUpflt(JxUpflt jxUpflt) {
		jdbcTemplate.update("insert into jx_upflt (JX__U_ID,pro_id,manager_no,flt_addtime,flt_othertime) values(?,?,?,?,?)",  new Object[] {jxUpflt.getJX__U_ID(),jxUpflt.getPro_id(),jxUpflt.getManager_no(),jxUpflt.getFlt_addtime(),jxUpflt.getFlt_othertime()});
	}
	@Override
	public Long queryByUserId(Long id) {
		Long value = jdbcTemplate.queryForLong("select count(*) from jx_user where u_id ="+id);
		return value;
	}
	@Override
	public Long queryByprofltId(Long id) {
		Long value = jdbcTemplate.queryForLong("select count(*) from jx_proflt where prf_id ="+id);
		return value;
	}
	@Override
	public Long queryByparnerId(Long id) {
		Long value = jdbcTemplate.queryForLong("select count(*) from jx_partner where id ="+id);
		return value;
	}
}
