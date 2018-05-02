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
import com.game.smvc.entity.JxReleaseOrder;
import com.game.smvc.service.JxReleaseOrderService;

@Service("jxReleaseOrderService")
public class JxReleaseOrderServiceImpl extends GenericManagerImpl<JxReleaseOrder, Long> implements JxReleaseOrderService{

	  private GenericDao<JxReleaseOrder, Long> jxReleaseOrderDao;
	    private JdbcTemplate jdbcTemplate;
	    @Autowired
	    public JxReleaseOrderServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
	        this.jxReleaseOrderDao = new GenericDaoHibernate<JxReleaseOrder,Long>(JxReleaseOrder.class,
	                sessionFactory);
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
	        this.dao = this.jxReleaseOrderDao;
	    }
	    
	    //删除发布订单
		@Override
		public int deleteReleaseOrderByordNo(String ordno) {
			String sql = "delete from jx_release_order where ord_no = '"+ordno+"'";
			return jdbcTemplate.update(sql);
		}

		@Override
		public List<Map<String, Object>> queryReleaseOrdersByuid(String uid,
				int page) {
			String sql = "select A.ord_no ordno,B.pub_seller,A.fb_state state,A.fb_price price,A.fb_addtime addtime from jx_release_order A,jx_publish B where A.ord_no=B.ord_no and A.u_id = '"+uid+"'";
			return jdbcTemplate.queryForList(sql);
		}

		@Override
		public List<Map<String, Object>> AllPromoter(String uid, int page) {
			//String sql = "select B.pub_other promoterid,B.pub_id pubid,B.pub_seller seller,B.pub_content content,B.pub_address address,B.pub_url url,B.pub_addtime addtime from jx_release_order A,jx_publish B where B.fb_state = 0 and A.ord_no=B.ord_no and B.u_id = '"+uid+"' LIMIT "+page+",10 ";
			String sql = "select B.u_id pubuserid,B.pub_id pubid,B.pub_seller seller,B.pub_content content,B.pub_address address,B.pub_url url,B.pub_addtime from jx_release_order A,jx_publish B where B.fb_state = 0 and A.ord_no=B.ord_no and B.pub_other = '"+uid+"' LIMIT "+page+",10 ";
			return jdbcTemplate.queryForList(sql);
		}

		@Override
		public List<Map<String, Object>> AllPromoters(String uid, int page) {
			String sql = "select B.u_id pubuserid,B.pub_id pubid,B.pub_seller seller,B.pub_content content,B.pub_address address,B.pub_url url,B.pub_addtime addtime from jx_release_order A,jx_publish B where B.fb_state = 0 and A.ord_no=B.ord_no and B.pub_other = '"+uid+"' LIMIT "+page+",10 ";
			return jdbcTemplate.queryForList(sql);
		}
}
