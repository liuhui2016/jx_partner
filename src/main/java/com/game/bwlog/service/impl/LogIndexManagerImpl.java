package com.game.bwlog.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.bwlog.entity.LogIndex;
import com.game.bwlog.service.LogIndexManager;
import com.game.jdbcdao.ICommonDao;
import com.game.jdbcdao.impl.BaseGeneralManagerImpl;
import com.game.jdbcdao.impl.CommonDaoImpl;
import com.game.modules.orm.Page;

@Service(value = "logIndexManager")
public class LogIndexManagerImpl extends BaseGeneralManagerImpl<LogIndex>
		implements LogIndexManager {

	private ICommonDao<LogIndex> logIndexDao;

	@Autowired
	public LogIndexManagerImpl(DataSource dataSource) {
		logIndexDao = new CommonDaoImpl<LogIndex>(dataSource, LogIndex.class);
		this.dao = logIndexDao;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Page<LogIndex> getByTime(Page<LogIndex> page, String begintime,
			String endtime) {
		List list = new ArrayList();
		StringBuilder whereSb = new StringBuilder();
		if (StringUtils.isNotBlank(begintime)) {
			list.add(begintime);
			whereSb.append(" and accesstime>=? ");
		}
		if (StringUtils.isNotBlank(endtime)) {
			list.add(endtime);
			whereSb.append(" and accesstime<? ");
		}
		return dao.findPage(page, "select * from browser_log_index where 1=1 "
				+ whereSb.toString(), list.toArray());
	}

}
