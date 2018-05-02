package com.game.smvc.service.impl;

/**
 * 子订单的实现
 */
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxOrderItem;
import com.game.smvc.entity.JxShoppingCart;
import com.game.smvc.service.IJxOrderItemService;

@Service("jxOrderItemService")
public class JxOrderItemServiceImpl extends GenericManagerImpl<JxOrderItem, Long> implements IJxOrderItemService{
	
	private GenericDao<JxOrderItem, Long> codeDao;
	private JdbcTemplate jdbcTemplate;
	@SuppressWarnings("unused")
	private Object jxOrderItemService;

	@Autowired
	public JxOrderItemServiceImpl(SessionFactory sessionFactory,
			DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<JxOrderItem, Long>(JxOrderItem.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}

	//得到所有订单的价格
	@Override
	public JxOrderItem findPrice(String orderno) {
		String sql = "select sum(order_price) from jx_order_item where orditem_no = "+orderno;
		return codeDao.findOne(sql);
	}

	@Override
	public int findPrices(String orderno) {
		String sql = "select sum(ord_price) from jx_order where fim_ord_no = "+orderno;
		return jdbcTemplate.queryForInt(sql);
	}

	//修改子订单的状态 (银联)
	@Override
	public int upadteStatus(String subject) {
		//String sql = "update jx_order_item set orditem_status = 1 where order_no = "+subject;
		String sql = "update jx_order set ord_status = 1,ord_way = 2 where fim_ord_no = "+subject;
		return jdbcTemplate.update(sql);
	}
	
	//微信
	@Override
	public int upadtexwStatus(String out_trade_no) {
		String sql = "update jx_order set ord_status = 1,ord_way = 1 where fim_ord_no = "+out_trade_no;
		return jdbcTemplate.update(sql);
	}
	
	//支付宝
	@Override
	public int upadtezfbStatus(String out_trade_no) {
		String sql = "update jx_order set ord_status = 1,ord_way = 0 where fim_ord_no = "+out_trade_no;
		return jdbcTemplate.update(sql);
	}

	
	

	//根据父订单号得到所有子订单
	@Override
	public List<Map<String, Object>> findorderno(String s) {
		String sql = "select ord_no from jx_order where fim_ord_no = "+s;
		return jdbcTemplate.queryForList(sql);
	}

	//总押金
	@Override
	public int findpledges(String s) {
		String sql = "select sum(ord_pledge) from jx_order where fim+ord_no = "+s;
		return jdbcTemplate.queryForInt(sql);
	}

	//修改子订单发修改时间
	@Override
	public int updatetime(String subject) {
		String sql = "update jx_order  set ord_modtime = (SELECT orditem_modtime FROM jx_order_item where orditem_no = '"+subject+"') where fim_ord_no ="+subject;
		return jdbcTemplate.update(sql);
	}

	//未改完
	@Override
	public JxOrderItem findAllOrder(String subject) {
		//String sql = "select * from jx_order_item A,JxOrder O where A.orditem_no = '"+subject+"' OR O.ord_no = '"+subject+"' LIMIT 1";
		//JxOrderItem jxOrderItem = codeDao.findOne("from JxOrderItem A,JxOrder B where A.orditem_no = '"+subject+"' or B.ord_no = '"+subject+"' limit 0,1 ");
		String sql = "from jx_order where ord_no ='"+subject+"' or fim_ord_no = '"+subject+"'";
		return codeDao.findOne(sql);
	}

	

	

}
