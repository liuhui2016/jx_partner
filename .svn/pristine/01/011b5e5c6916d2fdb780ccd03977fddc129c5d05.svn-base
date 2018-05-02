package com.game.comm.service.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.game.comm.entity.Province;
import com.game.comm.service.ProvinceManager;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.services.ServiceException;

/**
 *  代理人设置
 * 
 */
@Service("provinceManager")
public class ProvinceManagerImpl extends GenericManagerImpl<Province, Long> implements ProvinceManager {

    private GenericDao<Province, Long> provinceDao;
    private static Logger logger = LoggerFactory.getLogger(ProvinceManagerImpl.class);

    /**
     * 注入hibernate的sessionFactory构建dao
     * 
     * @param sessionFactory
     */
    @Autowired
    public ProvinceManagerImpl(SessionFactory sessionFactory) {
        this.provinceDao = new GenericDaoHibernate<Province, Long>(Province.class, sessionFactory);
        this.dao = this.provinceDao;
    }

    /**
     * 获取代理人设置
     * 
     * @param id
     * @return
     * @throws ACException
     */
//    public String getAgent(String username) {
//        Agents a = dao.findUniqueBy("login", username);
//        if (a != null) {
//            return a.getAgent();
//        }
//        return null;
//    }

//    @Transactional(readOnly = true)
//    public List<Province> getUrlResourceWithAuthorities() {
//        return provinceDao.getUrlResourceWithAuthorities();
//    }

    /**
     * 检查资源字符串是否唯一.
     * 
     * @return resString在数据库中唯一或等于oldResString时返回true.
     */
    @Transactional(readOnly = true)
    public boolean isResStringUnique(String newResString, String oldResString) {
        return provinceDao.isPropertyUnique("resString", newResString, oldResString);
    }

    /**
     * 使用属性过滤条件查询province.
     */
    @Transactional(readOnly = true)
    public Page<Province> searchProvince(final Page<Province> page, final List<PropertyFilter> filters) {
        return provinceDao.findPage(page, filters);
    }

    @Transactional(readOnly = true)
    public Province getProvince(Long id) {
        return provinceDao.get(id);
    }

    public Province saveProvince(Province object) {
        object = provinceDao.save(object);
        return object;
    }

    public void deleteProvince(Long id) {
        provinceDao.remove(id);
    }

    public boolean delAll(List<Long> ids) {
        try {
            for (Long id : ids) {
                this.provinceDao.remove(id);
            }
            return true;
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
