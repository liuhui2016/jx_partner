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
import com.game.smvc.entity.JxFilterAfterSales;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.service.IJxFilterAfterSalesService;

@Service("jxFilterAfterSalesService")
public class JxFilterAfterSalesServiceImpl extends
		GenericManagerImpl<JxFilterAfterSales, Long> implements
		IJxFilterAfterSalesService {

	private GenericDao<JxFilterAfterSales, Long> jxFilterAfterSalesDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxFilterAfterSalesServiceImpl(SessionFactory sessionFactory,
			DataSource dataSource) {
		this.jxFilterAfterSalesDao = new GenericDaoHibernate<JxFilterAfterSales, Long>(
				JxFilterAfterSales.class, sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.jxFilterAfterSalesDao;
	}

	// 根据用户Id查看所有设备信息
	@Override
	public List<Map<String, Object>> findFilterOfUserId(String userid) {
		String sql = "select * from jx_order where ord_status = 3 and u_id = '"
				+ userid + "'";
		return jdbcTemplate.queryForList(sql);
	}

	// 查看当然任务
	@Override
	public List<Map<String, Object>> findAfterInformationOfState(
			String fas_state, String username, int page) {
		String sql = "select id,DATE_FORMAT(make_time,'%Y-%m-%d %H:%i:%s') make_time,contact_person,contact_way,user_address,address_details,fas_state,fas_type,DATE_FORMAT(fas_addtime,'%Y-%m-%d %H:%i:%s') fas_addtime from jx_filter_after_sales where ord_managerno = '"
				+ username
				+ "' and fas_state = '"
				+ fas_state
				+ "' order by fas_addtime desc LIMIT " + page + ",10";
		return jdbcTemplate.queryForList(sql);
	}

	// 查看当然任务详情
	@Override
	public List<Map<String, Object>> findAfterTheTaskParticularsOfId(String id,
			String username) {
		String sql = "select id,pro_id,u_id,pro_name,ord_color,pro_no,ord_no,proflt_life,filter_name,DATE_FORMAT(make_time,'%Y-%m-%d %H:%i:%s') make_time,contact_person,contact_way,user_address,address_details,fault_cause,specific_reason,fautl_url,fas_state,fas_type,DATE_FORMAT(fas_addtime,'%Y-%m-%d %H:%i:%s') fas_addtime,DATE_FORMAT(fas_modtime,'%Y-%m-%d %H:%i:%s') fas_modtime from jx_filter_after_sales where id = '"
				+ id
				+ "' and ord_managerno = '"
				+ username
				+ "' order by fas_addtime desc";
		return jdbcTemplate.queryForList(sql);
	}

	// 根据Id查看售后详情
	@Override
	public List<Map<String, Object>> findAfterthedetailsToId(String id) {
		String sql = "select id,pro_id,u_id,ord_color,pro_no,pro_name,proflt_life,filte_name,DATE_FORMAT(make_time,'%Y-%m-%d %H:%i:%s') make_time,contact_person,contact_way,user_address,address_details,fault_cause,specific_reason,fautl_url,ord_managerno,fas_state,fas_type,DATE_FORMAT(fas_addtime,'%Y-%m-%d %H:%i:%s') fas_addtime,DATE_FORMAT(fas_modtime,'%Y-%m-%d %H:%i:%s') fas_modtime from jx_filter_after_sales where id = '"
				+ id + "'";
		return jdbcTemplate.queryForList(sql);
	}

	// 查询所有上架的设备故障
	@Override
	public List<Map<String, Object>> findFault(String is_shelves) {
		String sql = "select * from jx_fault where is_shelves = '" + is_shelves
				+ "'";
		return jdbcTemplate.queryForList(sql);
	}

	// 查询所有滤芯
	@Override
	public List<Map<String, Object>> findFilter(String pro_no) {
		String sql = "select prf_pp pp,prf_cto cto,prf_ro ro,prf_t33 t33,prf_wfr wfr from jx_proflt where pro_no = '"
				+ pro_no + "'";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findFilterOfUserName(String username,
			int page, String name, String ord_no, String adr_id) {
		String sql = "select A.ord_receivename,A.ord_phone,A.adr_id,A.ord_protypeid,A.pro_restflow,A.pro_day,p.pro_no,pro_color color,A.pro_id,A.ord_managerno,A.ord_no,pro_name name,A.ord_imgurl url,p.pro_alias pro_alias "
				+ "from jx_product p,jx_order A "
				+ "where A.pro_no=p.pro_no and A.ord_status=3 ";
		// String sql = "select * from jx_order where ord_status = 3 ";
		if (name != null && name != "") {
			sql += "and A.ord_receivename = '" + name
					+ "' and A.ord_managerno = '" + username + "'";
		} else if (ord_no != null && ord_no != "") {
			sql += "and A.ord_no = '" + ord_no + "' and A.ord_managerno = '"
					+ username + "'";
		} else if (adr_id != null && adr_id != "") {
			sql += "and A.adr_id like '%" + adr_id
					+ "%' and A.ord_managerno = '" + username + "'";
		} else {
			sql += "and A.ord_managerno = '" + username + "'";
		}
		sql += " order by A.ord_addtime desc LIMIT " + page + ",10";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findFilterToUserName(String username,
			int page) {
		String sql = "select A.ord_receivename,A.ord_phone,A.adr_id,A.ord_protypeid,A.pro_restflow,A.pro_day,p.pro_no,pro_color color,A.pro_id,A.ord_managerno,A.ord_no,pro_name name,A.ord_imgurl url,p.pro_alias pro_alias "
				+ "from jx_product p,jx_order A "
				+ "where A.pro_no=p.pro_no and A.ord_status=3 and A.ord_managerno = '"
				+ username
				+ "' order by A.ord_addtime desc LIMIT "
				+ page
				+ ",10";
		return jdbcTemplate.queryForList(sql);
	}

	// 搜索
	@Override
	public List<Map<String, Object>> findSearch(String search, String username,
			int page) {
		List<Map<String, Object>> list = null;
		String sql = "select A.ord_receivename,A.ord_phone,A.adr_id,A.ord_protypeid,A.pro_restflow,A.pro_day,p.pro_no,pro_color color,A.pro_id,A.ord_managerno,A.ord_no,pro_name name,A.ord_imgurl url,p.pro_alias pro_alias "
				+ "from jx_product p,jx_order A "
				+ "where A.pro_no=p.pro_no and A.ord_status=3 and A.ord_managerno = '"
				+ username
				+ "' and A.ord_receivename like '%"
				+ search
				+ "%' order by A.ord_addtime desc LIMIT " + page + ",10";
		list = jdbcTemplate.queryForList(sql);
		if (jdbcTemplate.queryForList(sql).size() <= 0) {
			String sql1 = "select A.ord_receivename,A.ord_phone,A.adr_id,A.ord_protypeid,A.pro_restflow,A.pro_day,p.pro_no,pro_color color,A.pro_id,A.ord_managerno,A.ord_no,pro_name name,A.ord_imgurl url,p.pro_alias pro_alias "
					+ "from jx_product p,jx_order A "
					+ "where A.pro_no=p.pro_no and A.ord_status=3 and A.ord_managerno = '"
					+ username
					+ "' and A.ord_no like '%"
					+ search
					+ "%' order by A.ord_addtime desc LIMIT " + page + ",10";
			list = jdbcTemplate.queryForList(sql1);
			if (jdbcTemplate.queryForList(sql1).size() <= 0) {
				String sql2 = "select A.ord_receivename,A.ord_phone,A.adr_id,A.ord_protypeid,A.pro_restflow,A.pro_day,p.pro_no,pro_color color,A.pro_id,A.ord_managerno,A.ord_no,pro_name name,A.ord_imgurl url,p.pro_alias pro_alias "
						+ "from jx_product p,jx_order A "
						+ "where A.pro_no=p.pro_no and A.ord_status=3 and A.ord_managerno = '"
						+ username
						+ "' and A.adr_id like '%"
						+ search
						+ "%' order by A.ord_addtime desc LIMIT "
						+ page
						+ ",10";
				list = jdbcTemplate.queryForList(sql2);
			}
		}
		return list;
	}

	// 维修记录
	@Override
	public List<Map<String, Object>> findMaintenanceRecord(String pro_no,
			String ord_no, String username, int page) {
		String sql = "select id,pro_id,u_id,ord_color,pro_no,proflt_life,DATE_FORMAT(make_time,'%Y-%m-%d %H:%i:%s') make_time,contact_person,contact_way,user_address,address_details,fault_cause,specific_reason,fautl_url,ord_managerno,fas_state,fas_type,DATE_FORMAT(fas_addtime,'%Y-%m-%d %H:%i:%s') fas_addtime,DATE_FORMAT(fas_modtime,'%Y-%m-%d %H:%i:%s') fas_modtime from jx_filter_after_sales where ord_managerno = '"
				+ username
				+ "' and fas_state = 200 and pro_no = '"
				+ pro_no
				+ "' and ord_no = '"
				+ ord_no
				+ "' order by fas_addtime desc LIMIT " + page + ",10";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findFilterWarningOfUserName(
			String username, int page) {
		String sql = "select A.ord_receivename,A.ord_phone,A.adr_id,p.pro_no,pro_color color,A.pro_id,A.ord_managerno,A.ord_no,pro_name name,A.ord_imgurl url,p.pro_alias pro_alias "
				+ "from jx_product p,jx_order A "
				+ "where A.pro_no=p.pro_no and A.ord_status=3 and A.ord_managerno = '"
				+ username + "' LIMIT " + page + ",10";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findFilterWarning(String pro_no) {
		String sql = "select filter_name,time_left,pro_no from jx_filter_warning where status = 0 and pro_no = '"
				+ pro_no + "'";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findLxOfUserName(String username, int page) {
		String sql = "select filter_name,pro_no from jx_filter_warning where status = 0 and manager_no = '"
				+ username + "' limit " + page + ",10";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findYHToProNo(String object) {
		String sql = "select A.ord_receivename,A.ord_phone,A.adr_id,p.pro_no,pro_color color,A.pro_id,A.ord_managerno,A.ord_no,pro_name name,A.ord_imgurl url,p.pro_alias pro_alias "
				+ "from jx_product p,jx_order A "
				+ "where A.pro_no = p.pro_no and A.pro_no = '"
				+ object
				+ "' and A.ord_status=3";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findPartnerViewAppraiseOfId(String id,
			String username) {
		String sql = "select id,pro_no,ord_no,service_master,service_master_phone,evaluation_people,evaluation_people_phone,u_id,ae_content,appraise_url,is_badge,is_overalls,is_anonymous,service_attitude,DATE_FORMAT(ae_addtime,'%Y-%m-%d %H:%i:%s') ae_addtime from jx_appraise where after_id = "
				+ id + " and ord_managerno = '" + username + "'";
		return jdbcTemplate.queryForList(sql);
	}

}
