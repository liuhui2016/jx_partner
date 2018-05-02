package com.game.comm.service.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.comm.entity.Picr;
import com.game.comm.service.IPicrManager;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.services.ServiceException;

@Service("picrManager")
public class PicrManagerImpl extends GenericManagerImpl<Picr, Long> implements IPicrManager {
    private GenericDao<Picr, Long> picrDao;
    private static Logger logger = LoggerFactory.getLogger(PicrManagerImpl.class);

    /**
     * 注入hibernate的sessionFactory构建dao
     * 
     * @param sessionFactory
     */
    @Autowired
    public PicrManagerImpl(SessionFactory sessionFactoryFolder) {
        this.picrDao = new GenericDaoHibernate<Picr, Long>(Picr.class, sessionFactoryFolder);
        this.dao = this.picrDao;
    }

    public boolean delAll(List<Long> ids) {
        try {
            for (Long id : ids) {
                // 逻辑删除
                dao.remove(id);
            }
            return true;
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
