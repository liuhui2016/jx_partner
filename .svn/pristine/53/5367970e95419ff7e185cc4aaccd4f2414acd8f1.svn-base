package com.game.smvc.service;

import java.util.List;
import java.util.Map;

import com.game.modules.service.GenericManager;
import com.game.smvc.entity.JxShoppingCart;

/**
 * 购物车的service
 * @author lh
 *
 */
public interface IJxShoppingCartService extends GenericManager<JxShoppingCart,Long> {

	Map<String, Object> findShoppingCart(String uid);

	JxShoppingCart findShoppingCarts(String uid);

	void updateNumber(String uid,String color,String name,String number,String type,String ppdnum);

	//JxShoppingCart findShoppingCartOnProduct(String uid, int proid);

	JxShoppingCart findShoppingCartOnProduct(String uid, String proid,
			String color, String type,String ppdnum);

	List<Map<String, List<Map<String, Object>>>> findAllProduct(String uid);

	int delCartProduct(Long... ids);

	int updateCartProduct(String id, String color, int number,
			int ppdnum);

	int selectnum(String uid);

	int delCartProducts(String proid);

	int updateCartProductnum(String id, String number);

	int updateCartProductsnum(String proid, int number);

	List<Map<String, Object>> findAllProducts(String uid);

	List<List<Map<String, Object>>>  findAllProductToList(String uid);

	List<Map<String, Object>> findAllProductToLists(String uid);

	List<Map<String, Object>> findAllProductToListss(String uid);

	List<Map<String, Object>> findAllProductToListsss(String uid);

	int delCartProduct(String[] ids);

	int delCartProduct(String id);

	int findProductNumber(String uid);

	List<Map<String, Object>> selectAllProduct(String id);

	List<Map<String, Object>> selectProduct(String tag);

	Map<String, Object> findId(String s1);

	int delProduct(String id);

	int findState(String color, String name,String uid);

	int findNumbers(String id);

	


	

	

	
}
