package com.game.smvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.game.smvc.entity.JxProduct;
import com.game.smvc.service.IJxProductService;

@Service("productService")
public class JxProductSeviceImpl extends GenericManagerImpl<JxProduct, Long>
		implements IJxProductService {

	private GenericDao<JxProduct, Long> codeDao;
	private JdbcTemplate jdbcTemplate;
	@SuppressWarnings("unused")
	private Object productService;

	@Autowired
	public JxProductSeviceImpl(SessionFactory sessionFactory,
			DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<JxProduct, Long>(JxProduct.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}

	//商品首页
	public List<Map<String, Object>> findallPrototal(int page) {
		String sql = "SELECT A.prot_type id,A.prot_name name,B.pic_url pic_url "
				+ "FROM jx_prototal A INNER JOIN jx_picture B "
				+ "ON A.prot_picid=B.id WHERE B.pic_default=1 and A.prot_status=0 limit ?,5";
		List<Map<String, Object>> queryForList = this.jdbcTemplate
				.queryForList(sql, page);

		return queryForList;
	}
	
	/*
	查看商品详情
	*/
	
	
	@Override
	public List<Map<String, List<Map<String, Object>>>> findProductById(
			Integer id) {
		
	String sql = "SELECT  id,name,typename,PROD_HZ,PROD_W,PROD_MPA,PROD_C,PROD_HL,PROD_FL,PROD_WT,PROD_IW,PROD_WX,PROD_WD,PROD_SZ,PROD_SZI,pic_color,GROUP_CONCAT(url)"
				+ " FROM(SELECT A.id id, B.PROD_NAME name, A.PROD_TYPENAME typename, A.PROD_HZ PROD_HZ,A.PROD_W PROD_W, A.PROD_MPA PROD_MPA,"
				+ "A.PROD_C PROD_C,A.PROD_HL PROD_HL,A.PROD_FL PROD_FL,A.PROD_WT PROD_WT,A.PROD_IW PROD_IW,A.PROD_WX PROD_WX,A.PROD_WD PROD_WD,"
				+ "A.PROD_SZ PROD_SZ,A.PROD_SZI PROD_SZI,C.pic_color ,C.pic_url AS url FROM jx_prodetail B "
				+ "INNER JOIN jx_picture C ON B.prod_picid = C.ID INNER JOIN jx_prototal A ON A.id = B.prot_id "
				+ "WHERE A.ID =?) AS a GROUP BY pic_color limit 0,1";

		
		List<Map<String, Object>> list = this.jdbcTemplate
				.queryForList(sql, id);
		String sql1 = "SELECT pic_color,GROUP_CONCAT(pic_url) url,pic_tone tone FROM jx_picture "
				+ "WHERE protype_id=? and pic_default=0 GROUP BY pic_color";

		List<Map<String, Object>> listcolor = this.jdbcTemplate.queryForList(sql1,
				id);
				
		String sql2 = "select pay_typename paytype,CAST(pay_totalmoney AS CHAR(20)) price,pay_pledge from jx_pay where pay_typeid=? order by pay_typename";
		List<Map<String, Object>> listpay = this.jdbcTemplate.queryForList(sql2,id);
		List<Map<String, List<Map<String, Object>>>> l = new ArrayList<Map<String, List<Map<String, Object>>>>();
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		map.put("detail", list);
		map.put("color", listcolor);
		map.put("paytype", listpay);
		l.add(map);
		return l;
	}




	@Override
	public void addDayByproNo(String proNo) {
		String sql="UPDATE jx_product SET pro_invalidtime=DATE_ADD(pro_invalidtime,INTERVAL 1 YEAR) where pro_no=?";
		jdbcTemplate.update(sql,proNo);
	}

	@Override
	public void addFlowByproNo(String proNo,Float flow) {
		String sql="UPDATE jx_product SET pro_restflow=pro_restflow+? where pro_no=?";
		jdbcTemplate.update(sql,flow,proNo);
	}
	@Override
	public void deleteProductByProno(String prono) {
        String sql="delete FROM jx_product where pro_no=?";
        jdbcTemplate.update(sql,prono);
	}

	@Override
	public List<Map<String, Object>> findShareProduct(String uid, int page) {
		page=(page-1)*10;
		String sql="select p.pro_no,pro_color color,pro_name name,A.ord_imgurl url,A.ord_protypeid,p.pro_alias pro_alias "
				+ "from jx_product p,jx_order A where "
				+ "A.pro_no=p.pro_no and p.fam_id IN "
				+ "(select fam_id from jx_famuser f where f.u_id=? ) "
				+ "and A.ord_status=3 and p.u_id!=? order by p.pro_id desc limit ?,10";	
		return jdbcTemplate.queryForList(sql,uid,uid,page);
	}

	@Override
	public int findtotalproductByuid(String userid) {
		String sql="select count(*) from jx_product where u_id=?";
		return jdbcTemplate.queryForInt(sql,userid);
	}

	@Override
	public int selectSfNull(String prono) {
		String sql = "select * from jx_product where pro_no = "+prono;
		return jdbcTemplate.queryForInt(sql);
	}

	//查看产品参数信息
	@Override
	public List<Map<String, Object>> selectParametersById(Integer id) {
		String sql = "select prot_type id,prot_name name,prod_typename typename,PROD_HZ,PROD_W,PROD_MPA,PROD_C,PROD_HL,PROD_FL,PROD_WT,PROD_IW,PROD_WX,PROD_WD,PROD_SZ,PROD_SZI from jx_prototal where prot_type = ?";
		return this.jdbcTemplate.queryForList(sql, id);
	}

	//商品信息
	@Override
	public List<Map<String, List<Map<String, Object>>>> findCommodityById(Integer id) {
		String sql ="SELECT id,name,typename,PROD_FL,pic_color,GROUP_CONCAT(url)"
				+ " FROM(SELECT A.id id, B.PROD_NAME name, A.PROD_TYPENAME typename,A.PROD_FL PROD_FL,C.pic_color ,C.pic_url AS url FROM jx_prodetail B "
				+ "INNER JOIN jx_picture C ON b.prod_picid = C.ID INNER JOIN jx_prototal A ON A.id = B.prot_id "
				+ "WHERE A.ID =?) AS a GROUP BY pic_color limit 0,1";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, id);
		
		String sql1 = "SELECT pic_color,GROUP_CONCAT(pic_url) url,pic_tone tone FROM jx_picture "
				+ "WHERE protype_id=? and pic_default=0 GROUP BY pic_color";
		List<Map<String, Object>> listcolor = this.jdbcTemplate.queryForList(sql1,id);
		
		String sql2 = "select pay_typename paytype,CAST(pay_totalmoney AS CHAR(20)) price,pay_pledge from jx_pay where pay_typeid=? order by pay_typename";
		List<Map<String, Object>> listpay = this.jdbcTemplate.queryForList(sql2,id);
		
		List<Map<String, List<Map<String, Object>>>> l = new ArrayList<Map<String, List<Map<String, Object>>>>();
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		map.put("product_parameters", list);
		map.put(" picture_url", listcolor);
		map.put("commodity_prices", listpay);
		l.add(map);
		return l;
	}
	
	//加载首页图片
	@Override
	public List<Map<String, Object>> findhomepage(String type) {
		String sql = "select adv_imgurl from jx_advpic where adv_type = ?";
		return jdbcTemplate.queryForList(sql, type);
	}

	//加载排行榜图片数据
	@Override
	public List<Map<String, Object>> findphbimg() {
		String sql = "select adv_imgurl from jx_advpic where adv_type = -3";
		return jdbcTemplate.queryForList(sql);
	}

	

	
	
}
