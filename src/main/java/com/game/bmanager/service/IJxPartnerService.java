package com.game.bmanager.service;

import java.util.List;
import java.util.Map;

import com.game.bmanager.entity.JxPartner;
import com.game.modules.orm.Page;
import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxOrder;

public interface IJxPartnerService extends GenericManager<JxPartner, Long> {
	Page<JxPartner> querySubordinate(Page<JxPartner> page,Long parentid);

	Page<JxPartner> querySelectResourcer(Page<JxPartner> pageResourcer, String par_level);
	
	Page<JxPartner> dimQuery(Page<JxPartner> pageResourcer, String par_level,String par_name,Long parId);

	Page<JxPartner> queryProvince(Page<JxPartner> page,String userid);

	Page<JxPartner> dimQueryProvince(Page<JxPartner> page, String parName,Long id,String userid,String lever);

	String findCityCodeByCity(String par_level, String s_province, String s_city, String s_county);

	Integer countNum();

	String queryLever(String userid);
	//根据产品经理编号查询订单
	//public String querySubordinateNo(Long id);

	List<Map<String, List<Map<String, Object>>>> querySubordinateNo(Long parentid);

	List<Map<String, Object>> findselectNo(Long id);

	String findParParentName(String parParentid);
	
	//我的下属
	List<Map<String, Object>> findMineUnderling(Object object,int page);

	List<Map<String, Object>> findMineUnderlingForSpecial(String username,int page);

	List<Map<String,Object>> findMineUnderlingForSpecials(String par_other,int page);

	List<Map<String,Object>> findMineUnderlings(Long id,int page);

	List<Map<String, Object>> findAllNumber(Object username);

	List<Map<String, Object>> findAllNumberOfOther(String username);

	String findParentid(String username);

	List<Map<String, Object>> findMineUnderlingToSpecial(Object object, int page);

	List<Map<String, Object>> findLevelOfUsername(Object object);

	Map<String,Object> findMostPartner(String username);

	List<Map<String, Object>> findLevelOfUsernameToSuper(String username);

	int updtaeNumber(int numberOfInstalledIsers,
			int numberOfInstalledIsersThreeYears, String username);

	List<Map<String, Object>> findSFZ();

}	
