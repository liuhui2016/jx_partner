package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
// TODO 未从数据库中查表
public interface IJXAccessService extends GenericManager<String,Integer> {
	/**
	 * 查看用户的足迹列表
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> queryAccessList(Integer userId,Integer pageNum,Integer pageSize);
	
	boolean removeAccess(Integer userId,String goodsIds);
	
	//添加访问记录
	void addAccess(Integer userId,Integer goodsId);
	
	//用户清空足迹
	void delAllAccess(Integer userId);
}
