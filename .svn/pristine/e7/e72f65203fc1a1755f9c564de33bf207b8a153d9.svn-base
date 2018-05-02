package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxPartnerMessages;

public interface IJxPartnerMessagesService extends GenericManager<JxPartnerMessages,Long>{

	List<Map<String, Object>> findAllPartnerMessages(String username, int page);

	int findDelPartnerMessages(String id);

	int findUpdatePartnerMessages(String id);

	int findMessagestotal(String username);
	
}
