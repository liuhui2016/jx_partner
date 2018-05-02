package com.game.smvc.service;

/**
 * 子订单接口
 */
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxOrderItem;

public interface IJxOrderItemService extends GenericManager<JxOrderItem, Long>{

	JxOrderItem findPrice(String orderno);

	int findPrices(String orderno);

	int upadteStatus(String subject);

	List<Map<String, Object>> findorderno(String s);

	int findpledges(String s);

	int updatetime(String subject);

	JxOrderItem findAllOrder(String subject);

	int upadtexwStatus(String out_trade_no);

	int upadtezfbStatus(String out_trade_no);

	

	
}
