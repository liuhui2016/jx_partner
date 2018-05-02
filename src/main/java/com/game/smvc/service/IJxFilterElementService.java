package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxFilterElement;

public interface IJxFilterElementService extends GenericManager<JxFilterElement,Long>{

	List<Map<String, Object>> getFilterElementNo(String pro_no);

}
