package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxProduct;


public interface IJxProductService extends GenericManager<JxProduct, Long>{

	List<Map<String, Object>> findallPrototal(int parseInt);
	
	List<Map<String,List<Map<String,Object>>>> findProductById(Integer id);

	void addDayByproNo(String proNo);

	void addFlowByproNo(String proNo,Float flow);

    void deleteProductByProno(String prono);

	List<Map<String, Object>> findShareProduct(String uid, int page);

	int findtotalproductByuid(String userid);

	int selectSfNull(String prono);

	//查看产品参数信息
	List<Map<String, Object>> selectParametersById(Integer id);
	//商品信息
	List<Map<String, List<Map<String, Object>>>> findCommodityById(Integer id);

	List<Map<String, Object>> findhomepage(String type);

	List<Map<String, Object>> findphbimg();


	//List<Map<String, List<Map<String, Object>>>> findcommunity(String type);

	
	

	

	

	

	
	
}
