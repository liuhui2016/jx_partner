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
import com.game.smvc.entity.Jxpublish;
import com.game.smvc.service.IJxMerchantPublishService;

@Service("merchantPublish")
public class JxMerchantPublishServiceImpl  extends GenericManagerImpl<Jxpublish, Long> implements IJxMerchantPublishService {
	private GenericDao<Jxpublish, Long> codeDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxMerchantPublishServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<Jxpublish,Long>(Jxpublish.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}
	/*
	 * 查看服务列表根据地理位置
	 * 
	 * @see com.game.smvc.service.IMerchantPublish#findPublishbycategoryId(java.lang.Integer, java.lang.Integer)
	 */
	public List<Map<String, Object>> findPublishbycategoryId(Integer id,Integer page,String address) {
		//pub_addtime
		String sql = "select pub_id pubId,p.pub_content content,p.pub_seller seller,p.pub_address address,DATE_FORMAT(p.pub_addtime,'%Y-%m-%d %H:%i') pub_addtime,"
				+ "p.pub_vaildtime vaildtime,p.pub_invildtime invildtime,p.pub_url url,p.ph_no phoneNum "
				+ "from jx_publish p ";
		sql=sql+"where p.pub_categoryid=? and p.fb_state = 0 and pub_address like ? limit ?,10";
		System.out.println(jdbcTemplate.queryForList(sql,new Object[]{id,"%"+address+"%",page}));
		return jdbcTemplate.queryForList(sql,new Object[]{id,"%"+address+"%",page});
	}
	 
	/*
	 * 根据id查询服务的内容
	 * 
	 */
	
	public List<Map<String, Object>> findPublishdetailbyId(int id) {
		String sql = "select b.pub_url url,b.pub_seller name,b.pub_address address,"
				+ "b.pub_content content,b.pub_invildtime invildtime,b.pub_vaildtime vaildtime,b.ph_no phoneNum,b.pub_traffic traffic,b.pub_inquiries inquiries,b.pub_name pubName from jx_publish b"
				+ " where b.pub_id=?";
		//System.out.println(jdbcTemplate.queryForList(sql,id));
		return jdbcTemplate.queryForList(sql,id);
	}
	
	@Override
	public int deletePublishById(int parseInt) {
		
		return jdbcTemplate.update("delete from jx_publish where pub_id=?",parseInt);
	}
	
	//咨询量
	@Override
	public int findInquiries(String pubId) {
		String sql = "select pub_inquiries from jx_publish where pub_id = '"+pubId+"'";
		return jdbcTemplate.queryForInt(sql);
	}
	//访问量
	@Override
	public int findTraffic(String pubId) {
		String sql = "select pub_traffic from jx_publish where pub_id = '"+pubId+"'";
		return jdbcTemplate.queryForInt(sql);
	}
	@Override
	public List<Map<String, Object>> findInquiries(int pubId) {
		String sql = "select pub_inquiries from jx_publish where pub_id = '"+pubId+"'";
		return jdbcTemplate.queryForList(sql);
	}
	@Override
	public Map<String, Object> findpubId(String ordno) {
		String sql = "select pub_id from jx_publish where ord_no = '"+ordno+"'";
		return jdbcTemplate.queryForMap(sql);
	}
	@Override
	public Jxpublish findPublish(String pubid) {
		String sql = "from Jxpublish where pub_id = '"+pubid+"'";
		return codeDao.findOne(sql);
	}
	@Override
	public List<Map<String, Object>> findRanking() {
		String sql = "select pub_id,pub_seller,pub_traffic from jx_publish ORDER BY pub_traffic DESC";
		return jdbcTemplate.queryForList(sql);
	}
	


}
