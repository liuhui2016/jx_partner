package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxProflt;

public interface IJxProfltService extends GenericManager<JxProflt,Long> {

    void deleteProfltByProno(String prono);

    JxProflt findJxProfltByProNO(String pro_no);

	//int update(String pro_no);
    
	//旧回调
	public List<Map<String, Object>> codeByFilterState(String code);

	int update(String pro_no,Integer prfpp, Integer prfcto, Integer prfro, Integer prft33,
			Integer prfwfr);

	void save(List<Map<String, Object>> jxProflt);

	List<Map<String, Object>> selectJxProfltLxsm(String prono);

}
