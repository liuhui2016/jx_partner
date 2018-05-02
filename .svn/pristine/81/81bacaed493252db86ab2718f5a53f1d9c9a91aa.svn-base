package com.game.comm.service;

import java.util.List;

import com.game.comm.entity.City;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.service.GenericManager;

public interface CityManager extends GenericManager<City, Long> {

    /**
     * 获取代理人设置
     * 
     * @param id
     * @return
     */
    // public String getAgent(String username);
    public Page<City> searchCity(final Page<City> page, final List<PropertyFilter> filters);

    public City getCity(Long id);

    public City saveCity(City object);

    public void deleteCity(Long id);

    public boolean delAll(List<Long> ids);

    /**
     * 构造省市树
     * 
     * @return
     */
    public String findTreeXml();

    public List<City> find(List<PropertyFilter> filters);

    /**
     * 根据类型构建省市树 type=1 取所有的，type=2 只取国家
     */
    public String findTreeXmlByType(int type);

    /**
     * 根据拼音排序
     * 
     * @param order
     *            desc asc
     */
    public List<City> getAllByPinyin(String order);

    /**
     * 根据编码获取信息
     */
    public City getByCode(String code);

    /**
     * 根据名称获取信息
     */
    public City getByName(String name);

}
