package com.game.comm.service;

import java.util.List;

import com.game.comm.entity.Province;
import com.game.modules.orm.Page;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.service.GenericManager;

public interface ProvinceManager extends GenericManager<Province, Long> {

    /**
     * 获取代理人设置
     * 
     * @param id
     * @return
     */
//    public String getAgent(String username);
    public Page<Province> searchProvince(final Page<Province> page, final List<PropertyFilter> filters);

    public Province getProvince(Long id);

    public Province saveProvince(Province object);

    public void deleteProvince(Long id);

    public boolean delAll(List<Long> ids);

}
