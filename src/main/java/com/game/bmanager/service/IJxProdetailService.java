package com.game.bmanager.service;

import com.game.bmanager.entity.JxProdetail;
import com.game.bmanager.entity.JxView;
import com.game.modules.orm.Page;
import com.game.modules.service.GenericManager;

public interface IJxProdetailService extends GenericManager<JxProdetail, Long>{

	Page<JxView> queryByProtId(Page<JxView> page, Long protId);

	void update(JxView jxView);

}
