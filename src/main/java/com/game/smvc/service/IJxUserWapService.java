package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.Jxpublish;

public interface IJxUserWapService extends GenericManager<Jxpublish, String>{
	//查询我发布的服务
	List<Map<String,Object>> wapServicecategory();
	//查询我发布的服务
	List<Map<String, Object>> findwapServiceBypho(String uid,String page);
	
	//根据电话号码查询此电话下的所有订单
	List<Map<String, Object>> queryOrdersByuid(String uid,Integer page);
	
	List<Map<String, Object>> findMyProductByUid(String uid,Integer page);
	List<Map<String, Object>> findOrderDetailByOno(String ono);

	List<Map<String, Object>> findStatusByproId(String pro_id);
	List<Map<String, Object>> queryOrdFilterLifeByProvince(String pro_no);
	List<Map<String, Object>> findOrderDetailByFimOno(String ono);
	List<Map<String, Object>> findGenerationOfPayment(String uid, String state,
			int page);
	List<Map<String, Object>> findPaymentHasBenn(String uid, String state, int page);
	List<Map<String, Object>> findIsBinding(String uid, String state, int page);
	List<Map<String, Object>> findRenewal(String uid, String state, int page);
	Map<String, Object> findFimordno(String uid, int page);
}
