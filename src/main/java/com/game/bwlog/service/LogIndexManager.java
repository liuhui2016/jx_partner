package com.game.bwlog.service;

import com.game.bwlog.entity.LogIndex;
import com.game.jdbcdao.BaseGeneralManager;
import com.game.modules.orm.Page;

/**
 * 数据分析通用服务接口
 *
 */
public interface LogIndexManager extends BaseGeneralManager<LogIndex> {

	public Page<LogIndex> getByTime(Page<LogIndex> page, String begintime, String endtime);
}
