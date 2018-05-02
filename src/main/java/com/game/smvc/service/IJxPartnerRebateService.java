package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxPartnerRebate;

public interface IJxPartnerRebateService extends GenericManager<JxPartnerRebate,Long>{

	List<Map<String,Object>> findLastAddtime(String username);

	List<Map<String, Object>> findLastAddtimes(String user_name);

}
