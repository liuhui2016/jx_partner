package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxStatistical;

public interface IJxStatisticalService extends GenericManager<JxStatistical,Long>{

	JxStatistical selectJxStatisticalByOrderNo(String ordno);

	int update(int tds, int temperature, String restflow,String ordno);
	//根据uid,和时间获取今日的TDS信息
	int findTds(String uid, String time);

	List<Map<String, Object>> findModTds(String uid, String time);

	List<Map<String, Object>> findTime(String uid);

	List<Map<String, Object>> findAddTds(String uid, String time);

	List<Map<String, Object>> findTimes(String uid);

	Map<String, Object> findStaAddtime(String uid);

	Map<String, Object> findStaModtime(String uid);

	int findAWater(String uid);

	int findMWater(String uid);

	List<Map<String, Object>> findNull(String uid);

	
}
