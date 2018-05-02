package com.game.bmanager.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxMenu;
import com.game.bmanager.service.IJxMenuService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;

@Service("menuService")
public class JxMenuServiceImpl extends GenericManagerImpl<JxMenu,Long> implements IJxMenuService{
	private GenericDao<JxMenu, Long> partnerDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxMenuServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.partnerDao = new GenericDaoHibernate<JxMenu,Long>(JxMenu.class,sessionFactory);
        this.dao = this.partnerDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	@Override
	public Map<String, Object> findTypeNmae(String typeName) {
		String sql = "select menu_name from jx_menu where id = '"+typeName+"'";
		return jdbcTemplate.queryForMap(sql);
	}
}
