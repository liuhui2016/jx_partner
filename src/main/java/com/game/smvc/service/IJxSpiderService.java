package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxSpider;

public interface IJxSpiderService extends GenericManager<JxSpider,Long>{

	List<Map<String, Object>> findurl(int id);

	List<Map<String, Object>> findRecreation(String id, int page);

	

}
