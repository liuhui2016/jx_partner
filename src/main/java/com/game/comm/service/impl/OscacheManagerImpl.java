package com.game.comm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.game.comm.service.OscacheManager;
import com.game.util.cache.BaseCache;
import com.game.util.cache.CacheConstant;
import com.game.util.cache.CacheFactory;
import com.game.util.ip.IPDataHandler;

public class OscacheManagerImpl implements OscacheManager {

   

    static IPDataHandler ipDataHandler = IPDataHandler.getInstance();
    static List<String> provinceList = new ArrayList<String>();
    static {
        provinceList.add("广西");
        provinceList.add("内蒙古");
        provinceList.add("宁夏");
        provinceList.add("新疆");
        provinceList.add("西藏");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findCityCodeByName(String name) {
        Map<String, Object> map = new HashMap<String, Object>();
        BaseCache bc = CacheFactory.newInstance().getCache(CacheConstant.OS_CACHE);

        Map<String, Long> province2IdMap = (Map<String, Long>) bc.getFromCache("province2IdMap");
        Map<Long, Map<String, Long>> id2ProvinceMap = (Map<Long, Map<String, Long>>) bc.getFromCache("id2ProvinceMap");
        name = name.split(" ")[0];
        if (name.equals("中国")) {
            map.put("cityCode", 1000L);
            return map;
        }

        int provinceIndex = name.indexOf("省");
        Long cityCode = 0L;
        boolean isSave = false;
        map.put("isSave", isSave);
        if (provinceIndex > 0) {
            String key = name.substring(0, provinceIndex);
            cityCode = (Long) province2IdMap.get(key);
            if (cityCode != null && cityCode != 0) {
                int cityIndex = name.indexOf("市");
                if (cityIndex > 0) {
                    key = name.substring(provinceIndex + 1, cityIndex);
                    Map<String, Long> cityMap = (Map<String, Long>) id2ProvinceMap.get(cityCode);
                    Long _cityCode = (Long) cityMap.get(key);
                    if (_cityCode != null)
                        cityCode = _cityCode;
                    else {
                        isSave = true;
                        map.put("isSave", isSave);
                    }
                }
                map.put("cityCode", cityCode);
                return map;
            }
        } else {
            provinceIndex = name.indexOf("市");
            if (provinceIndex > 0) {
                String key = name.substring(0, provinceIndex);
                cityCode = (Long) province2IdMap.get(key);
                if (cityCode != null && cityCode != 0) { // 四大直辖市: 北京 天津 上海 重庆
                    Map<String, Long> cityMap = (Map<String, Long>) id2ProvinceMap.get(cityCode);
                    cityCode = (Long) cityMap.get(key);
                    map.put("cityCode", cityCode);
                    return map;
                }
            } else if (name.equals("香港") || name.equals("澳门")) { // 两大特别行政区
                cityCode = (Long) province2IdMap.get(name);
                if (cityCode != null && cityCode != 0) {
                    Map<String, Long> cityMap = (Map<String, Long>) id2ProvinceMap.get(cityCode);
                    cityCode = (Long) cityMap.get(name);
                    map.put("cityCode", cityCode);
                    return map;
                }
            } else { // 五大自治区: 广西 内蒙古 宁夏 新疆 西藏
                String key = name;
                for (String province : provinceList) {
                    if (key.contains(province)) {
                        cityCode = (Long) province2IdMap.get(province);
                        key = province;
                    }
                }
                if (cityCode != null && cityCode != 0) {
                    key = name.substring(key.length(), name.length());
                    key.replace("市", "");
                    if (key.length() > 0) {
                        Map<String, Long> cityMap = (Map<String, Long>) id2ProvinceMap.get(cityCode);
                        Long _cityCode = (Long) cityMap.get(key);
                        if (_cityCode != null)
                            cityCode = _cityCode;
                        else {
                            isSave = true;
                            map.put("isSave", isSave);
                        }
                    }
                }
                if (cityCode != null && cityCode != 0) {
                    map.put("cityCode", cityCode);
                    return map;
                }
            }
            for (Entry<String, Long> entry : province2IdMap.entrySet()) {
                if (name.contains(entry.getKey())) {
                    cityCode = entry.getValue();
                    map.put("cityCode", cityCode);
                    return map;
                }
            }
        }

        map.put("cityCode", cityCode);
        return map;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findCityByCDN(String ip) {
        String name = ipDataHandler.findGeography(ip);
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(name)) {
            return map;
        }
        BaseCache bc = CacheFactory.newInstance().getCache(CacheConstant.OS_CACHE);
        Map<String, Long> province2IdMap = (Map<String, Long>) bc.getFromCache("province2IdMap");
        Map<Long, Map<String, Long>> id2ProvinceMap = (Map<Long, Map<String, Long>>) bc.getFromCache("id2ProvinceMap");
        Long rCityCode = 0L;
        String rCityName = "";

        String[] ipData = StringUtils.splitPreserveAllTokens(name, "\t");
        if (ipData.length < 5) {
            return map;
        }
        String country = ipData[0];
        if ("中国".equals(country)) {
            String provincename = ipData[1];
            String cityname = ipData[2];
            if (StringUtils.isNotBlank(cityname) && StringUtils.isNotBlank(provincename)) { // 市
                rCityCode = (Long) province2IdMap.get(provincename);
                if (rCityCode != null && rCityCode != 0) {
                    Map<String, Long> cityMap = (Map<String, Long>) id2ProvinceMap.get(rCityCode);
                    rCityCode = (Long) cityMap.get(cityname);
                    if (rCityCode != null && rCityCode != 0) {
                        rCityName = cityname;
                    } else { // 市在city表中取不到，取省
                        rCityCode = (Long) province2IdMap.get(provincename);
                        rCityName = provincename;
                    }
                }
            } else { // 省
                if (StringUtils.isNotBlank(provincename)) {
                    rCityCode = (Long) province2IdMap.get(provincename);
                    rCityName = provincename;
                }
            }
        } else {
            rCityCode = (Long) province2IdMap.get(country);
            rCityName = country;
        }
        if (rCityCode == null) {
            rCityCode = 0L;
            rCityName = name;
        }
        map.put("cityCode", rCityCode);
        map.put("cityName", rCityName);
        return map;
    }
}
