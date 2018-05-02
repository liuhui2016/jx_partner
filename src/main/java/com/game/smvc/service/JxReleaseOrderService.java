package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxReleaseOrder;

public interface JxReleaseOrderService  extends GenericManager<JxReleaseOrder, Long>{

	int deleteReleaseOrderByordNo(String ordno);

	List<Map<String, Object>> queryReleaseOrdersByuid(String uid, int page);

	List<Map<String, Object>> AllPromoter(String uid, int page);

	List<Map<String, Object>> AllPromoters(String uid, int page);

	
}
