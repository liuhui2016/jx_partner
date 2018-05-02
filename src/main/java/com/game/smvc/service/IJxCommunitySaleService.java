package com.game.smvc.service;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxCommunitySale;

public interface IJxCommunitySaleService extends GenericManager<JxCommunitySale,Long>{

	JxCommunitySale findpubCommunity(String uid, String pubid, String cause);

	
}
