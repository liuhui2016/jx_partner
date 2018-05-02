package com.game.comm.service.impl;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.game.comm.entity.City;
import com.game.comm.entity.Province;
import com.game.comm.service.CityManager;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.services.ServiceException;

/**
 * 代理人设置
 * 
 */
@Service("cityManager")
public class CityManagerImpl extends GenericManagerImpl<City, Long> implements CityManager {

    private GenericDao<Province, Long> provinceDao;
    private GenericDao<City, Long> cityDao;
    private static Logger logger = LoggerFactory.getLogger(CityManagerImpl.class);
    private static final Long WHOLE_CODE = 10000L;

    /**
     * 注入hibernate的sessionFactory构建dao
     * 
     * @param sessionFactory
     */
    @Autowired
    public CityManagerImpl(SessionFactory sessionFactory) {
        this.cityDao = new GenericDaoHibernate<City, Long>(City.class, sessionFactory);
        this.provinceDao = new GenericDaoHibernate<Province, Long>(Province.class, sessionFactory);
        this.dao = this.cityDao;
    }

    /**
     * 检查资源字符串是否唯一.
     * 
     * @return resString在数据库中唯一或等于oldResString时返回true.
     */
    @Transactional(readOnly = true)
    public boolean isResStringUnique(String newResString, String oldResString) {
        return cityDao.isPropertyUnique("resString", newResString, oldResString);
    }

    /**
     * 使用属性过滤条件查询city.
     */
    @Transactional(readOnly = true)
    public Page<City> searchCity(final Page<City> page, final List<PropertyFilter> filters) {
        return cityDao.findPage(page, filters);
    }

    @Transactional(readOnly = true)
    public City getCity(Long id) {
        return cityDao.get(id);
    }

    public City saveCity(City object) {
        object = cityDao.save(object);
        return object;
    }

    public void deleteCity(Long id) {
        cityDao.remove(id);
    }

    public boolean delAll(List<Long> ids) {
        try {
            for (Long id : ids) {
                this.cityDao.remove(id);
            }
            return true;
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public String findTreeXml() {
        List<Province> plist = provinceDao.getAll();
        List<City> clist = this.getAll();

        Document doc = DocumentHelper.createDocument();
        Element tree = doc.addElement("tree");
        tree.setAttributeValue("id", "0");

        Element root = tree.addElement("item");
        root.setAttributeValue("text", "全部");
        root.setAttributeValue("id", "1000");
        root.setAttributeValue("im0", "tombs.gif");
        root.setAttributeValue("im1", "tombs.gif");
        root.setAttributeValue("im2", "iconSafe.gif");

        root.setAttributeValue("open", "0");
        root.setAttributeValue("call", "1");
        root.setAttributeValue("select", "1");

        for (Province p : plist) {

            Element n1 = root.addElement("item");
            n1.setAttributeValue("text", p.getName());
            n1.setAttributeValue("id", String.valueOf(p.getId()));
            n1.setAttributeValue("im0", "folderClosed.gif");
            n1.setAttributeValue("im1", "folderOpen.gif");
            n1.setAttributeValue("im2", "folderClosed.gif");

            for (City c : clist) {

                if (c.getPid().equals(p.getId())) {
                    Element n11 = n1.addElement("item");
                    n11.setAttributeValue("text", c.getName());
                    n11.setAttributeValue("id", String.valueOf(c.getId()));
                    n11.setAttributeValue("im0", "book_titel.gif");
                    n11.setAttributeValue("im1", "book_titel.gif");
                    n11.setAttributeValue("im2", "book_titel.gif");
                }
            }

        }

        String ss = doc.asXML().replaceAll("\n", "").replace("\"", "'");

        return ss;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String findTreeXmlByType(int type) {
        // List<Province> plist = provinceDao.getAll();
        List<City> clist = this.getAllByPinyin("asc");

        Document doc = DocumentHelper.createDocument();
        Element tree = doc.addElement("tree");
        tree.setAttributeValue("id", "0");

        Element root = tree.addElement("item");
        root.setAttributeValue("text", "全部");
        root.setAttributeValue("id", "10000");
        root.setAttributeValue("im0", "tombs.gif");
        root.setAttributeValue("im1", "tombs.gif");
        root.setAttributeValue("im2", "iconSafe.gif");

        root.setAttributeValue("open", "0");
        root.setAttributeValue("call", "1");
        root.setAttributeValue("select", "1");

        for (City c : clist) {

            if (WHOLE_CODE.equals(c.getPid())) {
                Element n11 = root.addElement("item");
                n11.setAttributeValue("text", c.getName());
                n11.setAttributeValue("id", String.valueOf(c.getId()));
                n11.setAttributeValue("im0", "book_titel.gif");
                n11.setAttributeValue("im1", "book_titel.gif");
                n11.setAttributeValue("im2", "book_titel.gif");
            }
        }

        String ss = doc.asXML().replaceAll("\n", "").replace("\"", "'");

        return ss;
    }

    @Override
    public List<City> find(List<PropertyFilter> filters) {
        return dao.find(filters);
    }

    @Override
    public List<City> getAllByPinyin(String order) {
        StringBuilder sb = new StringBuilder();
        sb.append(" from City order by pinyin ").append(order);
        return dao.find(sb.toString());
    }

    @Override
    public City getByCode(String code) {
        List<City> citys = dao.find(" from City where code=?", code);
        if (!CollectionUtils.isEmpty(citys)) {
            return citys.get(0);
        }
        return null;
    }

    @Override
    public City getByName(String name) {
        List<City> citys = dao.find(" from City where name=?", name);
        if (!CollectionUtils.isEmpty(citys)) {
            return citys.get(0);
        }
        return null;
    }

}
