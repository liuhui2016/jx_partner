package com.game.smvc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.game.entity.account.User;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxUser;
import com.game.smvc.service.IJxUserService;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service("jxUserService")
public class JxUserServiceImpl extends
		GenericManagerImpl<JxUser, Long> implements IJxUserService {
	private GenericDao<JxUser, Long> codeDao;
	private JdbcTemplate jdbcTemplate;

	
	@Autowired
	public JxUserServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.codeDao = new GenericDaoHibernate<JxUser,Long>(JxUser.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.codeDao;
	}

	public JxUser save(JxUser object) {
		return (JxUser) super.save(object);
	}

	public Integer findByPhone(String phoneNum) {
	    JxUser user = (JxUser) this.dao.findOne(
				"  FROM JxUser WHERE u_phone=? ", phoneNum);
		return Integer.valueOf(user == null ? 0 : 1);
	}

	public JxUser findUserByPhoneNum(String phoneNum) {
	    JxUser user = (JxUser) this.dao.findOne(
				"  FROM JxUser WHERE u_phone=? ", phoneNum);
		return user;
	}
	
	/**
	 * 统计这个用户购物车里面商品的件数
	 */
	@Override
	public Integer countUserCart(Integer userId) {
		String sql = "SELECT SUM(goods_count) FROM fk_cart WHERE user_id=?";
		return jdbcTemplate.queryForInt(sql, userId);
	}
	
	@Override
	public Integer countUserCollect(Integer userId) {
		String sql = "SELECT COUNT(user_id) FROM fk_collection  WHERE user_id=?";
		return jdbcTemplate.queryForInt(sql, userId);
	}
	
	@Override
	public void createFamily(String id,String userId) {
	    String sql = "insert IGNORE into jx_family values (" +id+","+userId +")";
	    jdbcTemplate.execute(sql);
	}
	@Override
    public void addFamilyMembersByFamilyID(String familyID,String userId) {
        String sql = "insert IGNORE into jx_famuser values ("+familyID+","+userId+")";
        jdbcTemplate.execute(sql);
    }
	@Override
	public void addFamilyMembersByOwnerId(String ownerid, String targetid) {
	    String sql = "insert IGNORE into jx_famuser VALUES((select fam_id from jx_family t where t.fam_owner = "+ownerid+"),"+targetid+")";
	    jdbcTemplate.execute(sql);
	}
	
	@Override
	public void addFamilyProductByFamilyID(String familyId, String proid) {
	    String sql = "insert IGNORE into jx_fampro VALUES("+familyId+","+proid+")";
        jdbcTemplate.execute(sql);
	}
	@Override
	public String findFamilyIdByUserId(String userid) {
		Long id=Long.parseLong(userid);
	    String sql = "select * from jx_family where fam_owner=?";
	    Map<String, Object> map = jdbcTemplate.queryForMap(sql,id);

	    	return map.get("fam_id")+"";

	}
	@Override
	public void deleteFamilyProductByProno(String prono) {
	    String sql = "delete FROM jx_fampro where pro_id = (select pro_id from jx_product where pro_no = ?)";
	    jdbcTemplate.update(sql,prono);
	}
	@Override
	public List<Map<String,Object>> getAddressByUserID(String userid,String isdefault){
	    
	    String sql = "select adr_id id,adr_name name,adr_phone phone,adr_area area,adr_detail detail,adr_code code,adr_first isdefault from jx_address t where t.u_id = "+ userid;
	    if(isdefault!=null){
	        sql = sql + " and t.adr_first = 0";
	    }
	    return jdbcTemplate.queryForList(sql);
	}
	@Override
	public void saveAddress(String userid, String name, String phone, String area, String detail, String isdefault) {
	    if("0".equals(isdefault)){
	        updateDefault(userid);
	    }
	    String sql = "insert into jx_address(u_id,adr_name,adr_phone,adr_area,adr_detail,adr_first,adr_addtime) values(?,?,?,?,?,?,?)";
	    jdbcTemplate.update(sql, new Object[]{userid,name,phone,area,detail,isdefault,new Date()});
	}
	@Override
    public void updateAddress(String id, String userid, String name, String phone, String area, String detail, String isdefault) {
	    if("0".equals(isdefault)){
            updateDefault(userid);
        }
        String sql = "update jx_address set u_id=?,adr_name=?,adr_phone=?,adr_area=?,adr_detail=?,adr_first=?,adr_addtime=? where adr_id = ?";
        jdbcTemplate.update(sql, new Object[]{userid,name,phone,area,detail,isdefault,new Date(),id});
    }
	private void updateDefault(String userid){
	    String sql = "update jx_address set adr_first=1 where u_id = ?";
	    jdbcTemplate.update(sql, new Object[]{userid});
	}
	@Override
	public void deleteAddress(String id) {
	    String sql = "delete from jx_address where adr_id = ?";
	    jdbcTemplate.update(sql, new Object[]{id});
	    
	}

	@Override
	public List<Map<String, Object>> findIsShareByuid(long parseLong, Long u_id) {
		String sql="SELECT * FROM jx_famuser u WHERE u.fam_id IN (SELECT f.fam_id FROM jx_family f WHERE f.fam_owner=?) and u.u_id=?";
		return this.jdbcTemplate.queryForList(sql,parseLong,u_id);
	}

	@Override
	public int findtotalUserByphone(String phone) {
		String sql="select count(*) from jx_user where u_phone=?";
		return jdbcTemplate.queryForInt(sql,phone);
	}

	/**
	 * 新版
	 */
	
	@Override
	public User findUserByUserName(String username) {
		String sql = "select * from acc_user where username = '"+username+"'";
		return jdbcTemplate.queryForObject(sql, User.class);
	}

	@Override
	public List<Map<String, Object>> findUserName(String username) {
		String sql = "select * from acc_user where username = '"+username+"'";
		return jdbcTemplate.queryForList(sql);
	}

	
	
	 

	
}
