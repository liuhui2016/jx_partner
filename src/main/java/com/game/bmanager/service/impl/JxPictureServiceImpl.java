package com.game.bmanager.service.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.bmanager.entity.JxPartner;
import com.game.bmanager.entity.JxPicture;
import com.game.bmanager.entity.JxView;
import com.game.bmanager.service.IJxPictureService;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.Page;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;

@Service("pictureService")
public class JxPictureServiceImpl extends GenericManagerImpl<JxPicture, Long> implements IJxPictureService{
	private GenericDao<JxPicture, Long> partnerDao;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public JxPictureServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
        this.partnerDao = new GenericDaoHibernate<JxPicture,Long>(JxPicture.class,sessionFactory);
        this.dao = this.partnerDao;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	@Override
	public Page<JxPicture> queryByPicId(Page<JxPicture> pagePic, Long prod_picid) {
		pagePic  = partnerDao.findPageOnSql(pagePic, "select id,protype_id,pic_color,pic_name,pic_url,pic_addtime,pic_modtime,pic_default,pic_tone from jx_picture where id = "+ prod_picid);
		return pagePic;
	}
	@Override
	public String queryByDefault(Long srcpath) {
		JxPicture jxPictrue = partnerDao.findOne("from JxPicture where pic_default = 1 and id ="+srcpath);
		String url = jxPictrue.getPic_url();
		return url;
	}
	@Override
	public Long queryIdByUrl(String pic_url) {
		JxPicture jxPictrue = partnerDao.findOne("from JxPicture where pic_url ="+pic_url);
		Long id = jxPictrue.getId();
		return id;
	}
	@Override
	public void updateUrl(Long picId, String url) {
		jdbcTemplate.execute("update jx_picture set pic_url = "+url+" and picId ="+picId);
	}
	@Override
	public void update(JxView jxView) {
		jdbcTemplate.update("update jx_picture b set b.pic_color =?,b.pic_tone=?,b.pic_url=?,b.pic_modtime=? where b.id=?", new Object[]{jxView.getPic_color(),jxView.getPic_tone(),jxView.getPic_url(),jxView.getProd_modtime(),jxView.getProd_picid()});
	}
	
	//得到名字和url
	@Override
	public List<Map<String, Object>> findNmaeAndUrl(String proid, String color) {
		String sql = "SELECT GROUP_CONCAT(pic_url) url FROM jx_picture where protype_id='"+proid+"' and pic_default=0 and pic_color = '"+color+"' ";
		return jdbcTemplate.queryForList(sql);
	}

}
