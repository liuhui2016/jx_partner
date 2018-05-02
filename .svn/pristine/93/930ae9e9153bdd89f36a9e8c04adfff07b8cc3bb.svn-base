package com.game.comm.service.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.comm.entity.LogCityUndefine;
import com.game.comm.service.LogCityUndefineManager;
import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;

@Service("logCityUndefineManager")
public class LogCityUndefineManagerImpl extends GenericManagerImpl<LogCityUndefine, Long> implements LogCityUndefineManager {
    private GenericDao<LogCityUndefine, Long> logCityUndefineDao;

    /**
     * 注入hibernate的sessionFactory构建dao
     * 
     * @param sessionFactory
     */
    @Autowired
    public LogCityUndefineManagerImpl(SessionFactory sessionFactory) {
        this.logCityUndefineDao = new GenericDaoHibernate<LogCityUndefine, Long>(LogCityUndefine.class, sessionFactory);
        this.dao = this.logCityUndefineDao;
    }

}
