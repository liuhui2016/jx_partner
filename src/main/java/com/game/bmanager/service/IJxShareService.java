package com.game.bmanager.service;

import com.game.bmanager.entity.JxShare;
import com.game.modules.service.GenericManager;

public interface IJxShareService extends GenericManager<JxShare, Long>{

    public abstract void updateOtherInvalid();

}
