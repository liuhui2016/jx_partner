package com.game.bmanager.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxApkVersions;
import com.game.bmanager.service.IJxApkVersionService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;

@Service("apkVersionService")
public class JxApkVersionServiceImpl extends GenericManagerImpl<JxApkVersions, Integer> implements
		IJxApkVersionService {
	//private static final String BEANNAME = "FkApk";
	private GenericDao<JxApkVersions, Integer> tmpDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxApkVersionServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.tmpDao = new GenericDaoHibernate<JxApkVersions,Integer>(JxApkVersions.class,sessionFactory);
		this.dao = this.tmpDao;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/*@Override
	public Map<String, Object> queryLastApk(String type) {
		String sql = "SELECT apk_name apkName,apk_version apkVersion,apk_url apkUrl,apk_size apkSize, mustupgrade ";
		sql += " FROM jx_version  WHERE apk_version=(SELECT MAX(apk_version) FROM jx_version where type = "+type+") and type = "+type;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list.size()==0?null:list.get(0);
	}
	*/
	
	@Override
	public Map<String, Object> queryLastApk(String type) {
		String sql = "SELECT apk_name apkName,apk_version apkVersion,apk_url apkUrl,apk_size apkSize, mustupgrade ";
		sql += " FROM jx_version  WHERE apk_version=(SELECT MAX(apk_version) FROM jx_version where type = "+type+") and type = "+type;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list.size()==0?null:list.get(0);
	}
}
