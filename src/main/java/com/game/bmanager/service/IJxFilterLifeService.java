package com.game.bmanager.service;

import java.util.List;
import java.util.Map;

import com.game.bmanager.entity.JxFilterLife;
import com.game.modules.service.GenericManager;

public interface IJxFilterLifeService extends GenericManager<JxFilterLife, Long>{

    abstract List<Map<String, Object>> queryFilterLifeByProvince(String code);

	abstract Long queryCode(String temp);
	
	//旧绑定的方法
	List<Map<String, Object>> queryFilterLifeByProOn(String proNo);

	
	List<Map<String, List<Map<String, Object>>>> queryFilterLifeByProOns(String proNo,
			String code) ;

	
	
	

}
