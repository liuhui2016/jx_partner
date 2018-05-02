package com.game.bmanager.service;

import java.util.List;
import java.util.Map;

import com.game.bmanager.entity.JxAdvpic;
import com.game.modules.service.GenericManager;

public interface IJxAdvpicService extends GenericManager<JxAdvpic, Long>{

    public abstract List<Map<String, Object>> queryAdverByType(String type);

}
