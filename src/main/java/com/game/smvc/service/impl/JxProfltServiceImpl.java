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
import com.game.smvc.entity.JxProflt;
import com.game.smvc.service.IJxProfltService;
@Service("profltService")
public class JxProfltServiceImpl extends GenericManagerImpl<JxProflt, Long> implements
		IJxProfltService {
	private GenericDao<JxProflt, Long> codeDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxProfltServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<JxProflt,Long>(JxProflt.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}

    @Override
    public void deleteProfltByProno(String prono) {
        String sql="delete FROM jx_proflt where pro_no=?";
        jdbcTemplate.update(sql,prono);
    }
    
    @Override
    public JxProflt findJxProfltByProNO(String pro_no) {
        JxProflt jxProflt =  codeDao.findOne("from JxProflt where pro_no=?",pro_no);
        //JxProflt jxProflt =  codeDao.findOne("from jx_proflt where pro_no=?",pro_no);
        if(jxProflt==null){
            jxProflt = new JxProflt();
        }
        return jxProflt;
    }

    //修改滤芯
	/*@Override
	public int update(String pro_no) {
		String sql = "update jx_proflt set prf_pp=prf_pp,prf_cto=prf_cto,prf_ro=prf_ro,prf_t33=prf_t33,prf_wfr=prf_wfr where pro_no like '"+pro_no+"%'";
//		String sql1 = "inser into (prf_pp,prf_cto,prf_ro,prf_t33,prf_wfr) values(?,?,?,?,?)where pro_no like '"+pro_no+"%'";
		return jdbcTemplate.update(sql);
	}
*/
	
	//旧回调
	@Override
	public List<Map<String, Object>> codeByFilterState(String code) {
		String sql = "select pp,cto,ro,t33,wfr from jx_proflt_life where code like '"+code+"%'";
		return jdbcTemplate.queryForList(sql);
	}

	//更新滤芯寿命
	@Override
	public int update(String pro_no,Integer prfpp, Integer prfcto, Integer prfro,
			Integer prft33, Integer prfwfr) {
		System.out.println("3333");
		String sql = "update jx_proflt set prf_pp="+prfpp+",prf_cto="+prfcto+",prf_ro="+prfro+",prf_t33="+prft33+",prf_wfr="+prfwfr+" where pro_no like '"+pro_no+"%'";
		System.out.println("4444");
		return jdbcTemplate.update(sql);
	}

	@Override
	public void save(List<Map<String, Object>> jxProflt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map<String, Object>> selectJxProfltLxsm(String prono) {
		String sql = "SELECT * FROM jx_proflt where pro_no like '"+prono+"%'";
		return jdbcTemplate.queryForList(sql);
	}

	
    
}
