package com.game.servlet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.game.comm.entity.City;
import com.game.comm.service.CityManager;
import com.game.modules.orm.PropertyFilter;
import com.game.modules.utils.SpringContextHolder;
import com.game.util.cache.BaseCache;
import com.game.util.cache.CacheConstant;
import com.game.util.cache.CacheFactory;

public class InitializeServlet extends HttpServlet {

	private static final long serialVersionUID = 8975819588647676970L;

	public void init() throws ServletException {

		System.out.println(new Date() + "*****缓存省份开始*****");
		CityManager cityManager = (CityManager) SpringContextHolder.getBean("cityManager"); // 初始化ip
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQL_pid", "-1"));
		List<City> cityList = cityManager.find(filters);

		BaseCache bc = CacheFactory.newInstance().getCache(CacheConstant.OS_CACHE);

		Map<String, Long> province2IdMap = new HashMap<String, Long>();
		Map<Long, Map<String, Long>> id2ProvinceMap = new HashMap<Long, Map<String, Long>>();
		ss(cityList, province2IdMap, id2ProvinceMap, cityManager);
		bc.putIntoCache("province2IdMap", province2IdMap);
		bc.putIntoCache("id2ProvinceMap", id2ProvinceMap);

		System.out.println("省份总数：" + province2IdMap.size());
		System.out.println("城市总数：" + id2ProvinceMap.size());
		System.out.println(new Date() + "*****缓存省份结束*****");

	}

	private void ss(List<City> cityList, Map<String, Long> province2IdMap, Map<Long, Map<String, Long>> id2ProvinceMap, CityManager cityManager) {
		for (City city : cityList) {
			if (1000 == city.getId() || city.getId() == 10000) { // 国内，国外
				List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
				filters.add(new PropertyFilter("EQL_pid", city.getId() + ""));
				List<City> _cityList = cityManager.find(filters);
				ss(_cityList, province2IdMap, id2ProvinceMap, cityManager);
			} else { // 省份(海外国家)
				Map<String, Long> map = new HashMap<String, Long>();
				if (1000 < city.getId() && city.getId() < 10000) { // 城市
					List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
					filters.add(new PropertyFilter("EQL_pid", city.getId() + ""));
					List<City> _cityList = cityManager.find(filters);
					for (City city2 : _cityList) {
						map.put(city2.getName(), city2.getId());
					}
					id2ProvinceMap.put(city.getId(), map);
				}
				province2IdMap.put(city.getName(), city.getId());
			}
		}
	}
}
