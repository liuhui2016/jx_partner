package com.game.comm.service;

import java.util.Map;



/**
 * oscache 数据获取类
 * 
 * @author Administrator
 *
 */
public interface OscacheManager {

    public Map<String, Object> findCityCodeByName(String name);

    /**
     * 解析CND的IP地址的数据
     * 
     * @date 2014-8-19
     * @author wst
     */
    public Map<String, Object> findCityByCDN(String ip);
}
