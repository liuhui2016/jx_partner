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
import com.game.smvc.entity.Jxpublish;
import com.game.smvc.service.IJxUserWapService;

@Service("userWapService")
public class JxUserWapServiceimpl extends GenericManagerImpl<Jxpublish, String>
		implements IJxUserWapService {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxUserWapServiceimpl(SessionFactory sessionFactory,
			DataSource dataSource) {

		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/*
	 * 查询所有的服务类型（以添加时间升序操作）
	 */
	public List<Map<String, Object>> wapServicecategory() {
		String sql = "select id,menu_name,menu_icourl from jx_menu ORDER BY menu_addtime ASC";
		List<Map<String, Object>> queryForList = this.jdbcTemplate
				.queryForList(sql);
		return queryForList;
	}

	/*
	 * 查看我发布的所有服务
	 */
	@Override
	public List<Map<String, Object>> findwapServiceBypho(String uid,String page) {

		String sql = "select DATE_FORMAT(p.pub_addtime,'%Y-%m-%d %H:%i') pub_addtime,m.menu_name typename,p.pub_id pubId,p.pub_name phone,p.pub_seller seller,p.pub_address address,p.pub_content content,p.pub_vaildtime vaildtime,p.pub_invildtime invildtime,pub_url url from jx_publish p,jx_menu m where ";
		sql=sql+"m.id=p.pub_categoryid and p.u_id=? order by p.pub_id desc limit ?,10";
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql,uid,(Integer.parseInt(page)-1)*10);

		return queryForList;
	}

	/*
	 * 查看我所有的订单
	 */
	@Override
	public List<Map<String, Object>> queryOrdersByuid(String uid,
			Integer page) {
		String sql = "select "
                + "o.ord_no ordno,p.prot_NAME name,DATE_FORMAT(o.ORD_ADDTIME,'%Y-%m-%d %H:%i:%s') addtime,o.ORD_STATUS status,CAST(o.ORD_PRICE AS CHAR(20)) price "
				+ "FROM jx_order o,jx_prototal p,jx_user u WHERE "
				+ "o.PRO_ID=p.id and u.u_id=o.u_id and u.u_id=? and o.ORD_STATUS in(0,1,3,4,5) order by o.ord_id desc LIMIT ?,10";
		
		List<Map<String, Object>> m = jdbcTemplate.queryForList(sql, uid,page);
		List<Map<String, Object>> l = new ArrayList<Map<String,Object>>();
		for(int i = 0;i< m.size();i++){
			Map<String, Object> map = m.get(i);
			map.put("tag", "1");
			l.add(map);
		}
		return l;

	}
	/*
	 * 查看我的净水器
	 * 
	 */
	
	@Override
	public List<Map<String, Object>> findMyProductByUid(String uid,
			Integer page) {
		page=(page-1)*10;
		String sql="select p.pro_no,pro_color color,pro_name name,A.ord_imgurl url,A.ord_protypeid,p.pro_alias pro_alias "
				+"from jx_product p,jx_order A "
				+"where A.pro_no=p.pro_no and p.u_id=? and A.ord_status=3  order by p.pro_id desc limit ?,10";
//		SELECT B.PAY_TYPENAME name FROM "
//		+ "JX_ORDER A INNER JOIN JX_PAY B ON A.ORD_PROTYPEID=B.PAY_ID"
//		+" where A.pro_no=?
		return jdbcTemplate.queryForList(sql,uid,page);
	}
	/*
	 * 查看订单详情
	 * 
	 */
	@Override
	public List<Map<String, Object>> findOrderDetailByOno(String ono) {
		/*String sql="SELECT A.ORD_NO ordNo,D.prod_name name,A.ord_receivename uname,A.adr_id address,A.ord_color color,A.ord_imgurl url,ord_protypeid paytype,A.ord_ordertype isagain,"
				+ "A.ord_phone phone,DATE_FORMAT(A.ord_modtime,'%Y-%m-%d %H:%i:%s') ord_modtime,A.ord_sertime serttime,A.ord_way way,CAST(A.ORD_PRICE AS CHAR(20)) price,A.ORD_STATUS status FROM jx_order A,jx_user B,"
				+ "jx_prodetail D where A.u_id=B.u_id  and A.pro_id=D.prot_id and A.ord_no=? limit 0,1";*/
		
		/*String sql="SELECT A.ORD_NO ordNo,D.prod_name name,A.ord_receivename uname,A.adr_id address,A.ord_color color,A.ord_imgurl url,ord_protypeid paytype,A.ord_ordertype isagain,A.ord_multiple ppdnum,A.ord_pledge pledge,A.ord_priceper amount,"
				+ "A.ord_phone phone,DATE_FORMAT(A.ord_modtime,'%Y-%m-%d %H:%i:%s') ord_modtime,A.ord_sertime serttime,A.ord_way way,CAST(A.ORD_PRICE AS CHAR(20)) price,A.ORD_STATUS status FROM jx_order A,jx_user B,"
				+ "jx_prodetail D where A.u_id=B.u_id  and A.pro_id=D.prot_id and A.fim_ord_no = '"+ono+"' OR A.ord_no='"+ono+"' limit 0,1";*/
		
		/*String sql="SELECT A.ORD_NO ordNo,D.prod_name name,A.ord_receivename uname,A.adr_id address,A.ord_color color,A.ord_imgurl url,ord_protypeid paytype,A.ord_ordertype isagain,"
				+ "A.ord_phone phone,DATE_FORMAT(A.ord_modtime,'%Y-%m-%d %H:%i:%s') ord_modtime,A.ord_sertime serttime,A.ord_way way,CAST(A.ORD_PRICE AS CHAR(20)) price,A.ORD_STATUS status FROM jx_order A,jx_user B,"
				+ "jx_prodetail D where A.u_id=B.u_id  and A.pro_id=D.prot_id and A.ord_no='"+ono+"' limit 0,1";*/
		
		String sql="SELECT A.ORD_NO ordNo,D.prod_name name,A.ord_receivename uname,A.adr_id address,A.ord_color color,A.ord_imgurl url,ord_protypeid paytype,A.ord_ordertype isagain,"
				+ "A.ord_phone phone,DATE_FORMAT(A.ord_modtime,'%Y-%m-%d %H:%i:%s') ord_modtime,A.ord_sertime serttime,A.ord_way way,A.ORD_PRICE price,A.ORD_STATUS status FROM jx_order A,jx_user B,"
				+ "jx_prodetail D where A.u_id=B.u_id  and A.pro_id=D.prot_id and A.ord_no='"+ono+"' limit 0,1";
		
		List<Map<String, Object>> m = jdbcTemplate.queryForList(sql);
		List<Map<String, Object>> l = new ArrayList<Map<String,Object>>();
		for(int i = 0;i< m.size();i++){
			Map<String, Object> map = m.get(i);
			map.put("tag", "1");
			l.add(map);
		}
		return l;
	}
	/*
	 * 查看滤芯状态
	 * 
	 */
	@Override
	public List<Map<String, Object>> findStatusByproId(String pro_no) {
		String sql="select prf_pp pp,prf_cto cto,prf_ro ro,prf_t33 t33,prf_wfr wfr,prf_code code,pro_no from jx_proflt where pro_no=?";
			List<Map<String, Object>> queryForList = this.jdbcTemplate.queryForList(sql,pro_no);
		
		return queryForList;
	}

	public static void main(String[] args) {
		String sql="DATE_FORMAT(A.ord_modtime,'%Y-%m-%d %H:%i:%s') ord_modtime";	
			System.out.println(sql);
	}

	@Override
	public List<Map<String, Object>> queryOrdFilterLifeByProvince(String pro_no) {
		String sql="select prf_pp pp,prf_cto cto,prf_ro ro,prf_t33 t33,prf_wfr wfr,prf_code code from jx_proflt where pro_no= ?";
		 return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findOrderDetailByFimOno(String ono) {
		String sql="SELECT A.fim_ord_no ordNo,C.ord_number,D.prod_name name,A.ord_receivename uname,A.adr_id address,A.ord_color color,A.ord_imgurl url,ord_protypeid paytype,A.ord_ordertype isagain,"
				+ "A.ord_phone phone,DATE_FORMAT(A.ord_modtime,'%Y-%m-%d %H:%i:%s') ord_modtime,A.ord_sertime serttime,A.ord_way way,C.order_price price,A.ORD_STATUS status,C.ord_number number FROM jx_order A,jx_user B,"
				+ "jx_prodetail D,jx_order_item C where A.u_id=B.u_id  and A.pro_id=D.prot_id and A.fim_ord_no = '"+ono+"' and C.orditem_no = '"+ono+"' limit 0,1";
		return this.jdbcTemplate.queryForList(sql);
	}

	//代付款订单信息
	@Override
	public List<Map<String, Object>> findGenerationOfPayment(String uid,
			String state, int page) {
		String sql = "select o.ord_no ordno,p.prot_NAME name,DATE_FORMAT(o.ORD_ADDTIME,'%Y-%m-%d %H:%i:%s') addtime,o.ORD_STATUS status,CAST(o.ORD_PRICE AS CHAR(20)) price FROM jx_order o,jx_prototal p,jx_user u WHERE o.PRO_ID=p.id and u.u_id=o.u_id and u.u_id= '"+uid+"' and o.ORD_STATUS = '"+state+"' order by o.ord_id desc LIMIT "+page+",10";
		return jdbcTemplate.queryForList(sql);

	}

	//已付款订单信息
	@Override
	public List<Map<String, Object>> findPaymentHasBenn(String uid,
			String state, int page) {
		String sql = "select o.ord_no ordno,p.prot_NAME name,DATE_FORMAT(o.ORD_ADDTIME,'%Y-%m-%d %H:%i:%s') addtime,o.ORD_STATUS status,CAST(o.ORD_PRICE AS CHAR(20)) price FROM jx_order o,jx_prototal p,jx_user u WHERE o.PRO_ID=p.id and u.u_id=o.u_id and u.u_id= '"+uid+"' and o.ORD_STATUS = '"+state+"' order by o.ord_id desc LIMIT "+page+",10";
		return jdbcTemplate.queryForList(sql);
	}

	//已绑定订单信息
	@Override
	public List<Map<String, Object>> findIsBinding(String uid, String state,
			int page) {
		String sql = "select o.ord_no ordno,p.prot_NAME name,DATE_FORMAT(o.ORD_ADDTIME,'%Y-%m-%d %H:%i:%s') addtime,o.ORD_STATUS status,CAST(o.ORD_PRICE AS CHAR(20)) price FROM jx_order o,jx_prototal p,jx_user u WHERE o.PRO_ID=p.id and u.u_id=o.u_id and u.u_id= '"+uid+"' and o.ORD_STATUS = '"+state+"' order by o.ord_id desc LIMIT "+page+",10";
		return jdbcTemplate.queryForList(sql);
	}

	//续费订单信息
	@Override
	public List<Map<String, Object>> findRenewal(String uid, String state,
			int page) {
		String sql = "select o.ord_no ordno,p.prot_NAME name,DATE_FORMAT(o.ORD_ADDTIME,'%Y-%m-%d %H:%i:%s') addtime,o.ORD_STATUS status,CAST(o.ORD_PRICE AS CHAR(20)) price FROM jx_order o,jx_prototal p,jx_user u WHERE o.PRO_ID=p.id and u.u_id=o.u_id and u.u_id= '"+uid+"' and o.ORD_STATUS in ("+state+") order by o.ord_id desc LIMIT "+page+",10";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public Map<String, Object> findFimordno(String uid, int page) {
		String sql = "select "
                + "o.fim_ord_no fim_ord_no "
				+ "FROM jx_order o,jx_prototal p,jx_user u WHERE "
				+ "o.PRO_ID=p.id and u.u_id=o.u_id and u.u_id= '"+uid+"' and o.ORD_STATUS in(0,1,3,4,5) order by o.ord_id desc LIMIT "+page+",10";
		return jdbcTemplate.queryForMap(sql);
	}
	

}
