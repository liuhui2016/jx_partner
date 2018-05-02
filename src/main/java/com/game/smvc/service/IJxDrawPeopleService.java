package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxDrawPeople;

public interface IJxDrawPeopleService extends GenericManager<JxDrawPeople,Long>{

	List<Map<String, Object>> findDrts(String withdrawalOrderNo);

	String findUsernameById(String id);

	int findupdate_state(String withdrawal_order_no);

	int findupdate_states(String withdrawal_order);

}
