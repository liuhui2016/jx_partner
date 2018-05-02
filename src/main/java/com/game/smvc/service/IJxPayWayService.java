package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxPay;

public interface IJxPayWayService extends GenericManager<JxPay, Integer>{

	int selectPrice(String name, String type);

	List<Map<String, Object>> findPrice(String proid, String type);

	Map<String, Object> findflow();

	

}
