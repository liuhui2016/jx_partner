package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.entity.account.User;
import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxUser;

public abstract interface IJxUserService extends
		GenericManager<JxUser, Long> {
	public abstract Integer findByPhone(String paramString);

	public abstract JxUser findUserByPhoneNum(String paramString);
	
	//统计这个用户的购物车里面商品的件数
	Integer countUserCart(Integer userId);
	
	//统计用户收藏的总数
	Integer countUserCollect(Integer userId);

    public abstract void createFamily(String phoneNum,String userId);

    public abstract void addFamilyMembersByFamilyID(String familyID,String userId);
    
    public abstract void addFamilyMembersByOwnerId(String ownerid, String targetid);
    
    public abstract void addFamilyProductByFamilyID(String familyId, String proid);
    
    public abstract void deleteFamilyProductByProno(String prono);
    
    public abstract String findFamilyIdByUserId(String userid);

    public abstract List<Map<String,Object>> getAddressByUserID(String userid, String isdefault);

    public abstract void saveAddress(String userid, String name, String phone, String area, String detail, String isdefault);

    public abstract void updateAddress(String id, String userid, String name, String phone, String area, String detail, String isdefault);

    public abstract void deleteAddress(String id);

	public abstract List<Map<String, Object>> findIsShareByuid(long parseLong,
			Long u_id);

	public abstract int findtotalUserByphone(String phone);

	public abstract User findUserByUserName(String username);

	public  List<Map<String, Object>> findUserName(String username);
	
	

	
}
