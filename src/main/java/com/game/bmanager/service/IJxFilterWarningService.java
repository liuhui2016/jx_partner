package com.game.bmanager.service;

import com.game.bmanager.entity.JxFilterWarning;
import com.game.modules.orm.Page;
import com.game.modules.service.GenericManager;

public interface IJxFilterWarningService extends GenericManager<JxFilterWarning, Integer>{

	Page<JxFilterWarning> query(Page<JxFilterWarning> page, String orderNo,
			String phone,String managerNo);

	Long queryCount(String value);

	Page<JxFilterWarning> queryBypartner(Page<JxFilterWarning> page,
			String orderNo, String phone, String id,String managerNo);
	
	String queryLever(String userid);
	JxFilterWarning findByProNoAndFilter(String pro_no, String filterName);

	JxFilterWarning getWarning(String orderno);

	JxFilterWarning getWarning(String orderno, String filterno);

}
