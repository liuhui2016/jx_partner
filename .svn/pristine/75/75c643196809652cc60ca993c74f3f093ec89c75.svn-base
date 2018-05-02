package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.Jxpublish;

//商家发布
public interface IJxMerchantPublishService extends GenericManager<Jxpublish, Long>{

	List<Map<String,Object>> findPublishbycategoryId(Integer id,Integer page,String address);
//	//用户发布服务
//	Jxpublish addPublish(Jxpublish phoneNum);

	List<Map<String, Object>> findPublishdetailbyId(int parseInt);

	int deletePublishById(int parseInt);

	int findInquiries(String pubId);

	int findTraffic(String pubId);

	List<Map<String, Object>> findInquiries(int pubId);

	Map<String, Object> findpubId(String ordno);

	Jxpublish findPublish(String pubid);

	List<Map<String, Object>> findRanking();
}
