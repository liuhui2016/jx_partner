package com.game.smvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.views.util.ContextUtil;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.game.modules.orm.GenericDao;
import com.game.modules.orm.hibernate.GenericDaoHibernate;
import com.game.modules.service.impl.GenericManagerImpl;
import com.game.smvc.entity.JxShoppingCart;
import com.game.smvc.service.IJxShoppingCartService;

@Service("jxShoppingCartService")
public class JxShoppingCartServiceImpl extends GenericManagerImpl<JxShoppingCart, Long> implements IJxShoppingCartService{

	private GenericDao<JxShoppingCart, Long> shoppingDao;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JxShoppingCartServiceImpl(SessionFactory sessionFactory,DataSource dataSource) {
		this.shoppingDao = new GenericDaoHibernate<JxShoppingCart,Long>(JxShoppingCart.class,
				sessionFactory);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.dao = this.shoppingDao;
	}

	@Override
	public Map<String, Object> findShoppingCart(String uid) {
		String sql = "select u_id uid,sc_price price,sc_name name,sc_model model,sc_color color,sc_param param,sc_number number from jx_shopping_cart where u_id ="+uid;
		return jdbcTemplate.queryForMap(sql);
	}

	//查询购物车
	@Override
	public JxShoppingCart findShoppingCarts(String uid) {
		JxShoppingCart shoppingCart = shoppingDao.findOne("from jx_shopping_cart where u_id ="+uid);
		//String sql = "select u_id uid,sc_price price,sc_name name,sc_model model,sc_color color,sc_param param,sc_number number from jx_shopping_cart where u_id ="+uid;
		if(shoppingCart == null){
			shoppingCart = new JxShoppingCart();
		}
		return shoppingCart;
	}

	//更新数量
	@Override
	public void updateNumber(String uid,String color,String name,String number,String type,String ppdnum) {
		String sql = "update jx_shopping_cart set sc_number = sc_number+'"+number+"' where u_id= '"+uid+"' and sc_color = '"+color+"' and sc_name = '"+name+"' and sc_type ='"+type+"' and pro_multiple ='"+ppdnum+"' ";
		jdbcTemplate.update(sql);
	}

	/*@Override
	public JxShoppingCart findShoppingCartOnProduct(String uid, int proid) {
		JxShoppingCart shoppingCart = shoppingDao.findOne("from jx_shopping_cart where u_id ='"+uid+"' and pro_id ='"+proid+"'");
		if(shoppingCart == null){
			shoppingCart = new JxShoppingCart();
		}
		return shoppingCart;
	}
*/
	@Override
	public JxShoppingCart findShoppingCartOnProduct(String uid, String proid,
			String color, String type,String ppdnum) {
		JxShoppingCart shoppingCart = shoppingDao.findOne("from jx_shopping_cart where u_id ='"+uid+"' and pro_id ='"+proid+"' and sc_color ='"+color+"' and sc_type ='"+type+"' and pro_multiple ='"+ppdnum+"'  and sc_state = 1 ");
		if(shoppingCart == null){
			shoppingCart = new JxShoppingCart();
		}
		return shoppingCart;
	}

	//查询所有商品，以条目的方式
	@Override
	public List<Map<String, List<Map<String, Object>>>> findAllProduct(
			String uid) {
		
		String sql0 = "select pay_pledge pledge,pay_unitprice unitprice from jx_pay where pay_id = 1 ";
		List<Map<String, Object>> list0 = this.jdbcTemplate.queryForList(sql0);
		Map<String, Object> map0 = list0.get(0);
		int pledge =  (Integer) map0.get("pledge");
		Float unitprice = (Float) map0.get("unitprice");
		String sql = "select sc_id,u_id userid,pro_id proid,sc_model model,sc_weight weight,sc_price price,sc_color color,sc_name name,sc_number number,pro_multiple ppdnum,sc_imgurl url,sc_type type from jx_shopping_cart where u_id ='"+uid+"' and pro_id = 1 and sc_state = 1 ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		for(int i = 0; i<list.size();i++){
			Map<String,Object> j =list.get(i);
			int type1 = (Integer) j.get("type");
			int number1 = (Integer) j.get("number");
			int ppdnum1 = (Integer) j.get("ppdnum");
			Float price1 = (Float) j.get("price");
			Float totalPrice1 = (number1*ppdnum1*price1)+(pledge * number1);
			if(type1 == 0){
				if(ppdnum1 == 3){
					pledge = 0;
				}else{
					pledge = (Integer) map0.get("pledge");
				}
				totalPrice1 = (number1*ppdnum1*price1)+(pledge * number1);
				String years = "包年购买:"+" "+j.get("ppdnum")+"年";
				j.put("yearsorflow", years);
				j.put("pledge", (pledge *number1));
				j.put("totalPrice", totalPrice1);
			}else{
				if(ppdnum1 == 3){
					pledge = 0;
				}else{
					pledge = (Integer) map0.get("pledge");
				}
				totalPrice1 = (number1*ppdnum1*price1)+(pledge * number1);
				int s1 = (Integer) j.get("number");
				int prepaidTraffic = (int) ((price1 * ppdnum1)/unitprice);
				String years = "流量预付:"+" "+prepaidTraffic+"升"+"("+price1+"*"+ppdnum1+"/"+unitprice+"="+prepaidTraffic+"升"+")";
				j.put("yearsorflow", years);
				j.put("pledge", (pledge *number1));
				j.put("totalPrice", totalPrice1);
			}
		}

		
		String sql1 = "select sc_id,u_id userid,pro_id proid,sc_model model,sc_weight weight,sc_price price,sc_color color,sc_name name,sc_number number,pro_multiple ppdnum,sc_imgurl url,sc_type type from jx_shopping_cart where u_id ='"+uid+"' and pro_id = 2 and sc_state = 1 ";
		List<Map<String, Object>> list1 = this.jdbcTemplate.queryForList(sql1);
		for(int i = 0; i<list1.size();i++){
			Map<String,Object> j =list1.get(i);
			int type1 = (Integer) j.get("type");
			int number1 = (Integer) j.get("number");
			int ppdnum1 = (Integer) j.get("ppdnum");
			Float price1 = (Float) j.get("price");
			Float totalPrice1 = (number1*ppdnum1*price1)+(pledge * number1);
			if(type1 == 0){
				if(ppdnum1 == 3){
					pledge = 0;
				}else{
					pledge = (Integer) map0.get("pledge");
				}
				totalPrice1 = (number1*ppdnum1*price1)+(pledge * number1);
				String years = "包年购买:"+" "+j.get("ppdnum")+"年";
				j.put("yearsorflow", years);
				j.put("pledge", (pledge *number1));
				j.put("totalPrice", totalPrice1);
			}else{
				if(ppdnum1 == 3){
					pledge = 0;
				}else{
					pledge = (Integer) map0.get("pledge");
				}
				totalPrice1 = (number1*ppdnum1*price1)+(pledge * number1);
				int s1 = (Integer) j.get("number");
				int prepaidTraffic = (int) ((price1 * ppdnum1)/unitprice);
				String years = "流量预付:"+" "+prepaidTraffic+"升"+"("+price1+"*"+ppdnum1+"/"+unitprice+"="+prepaidTraffic+"升"+")";
				j.put("yearsorflow", years);
				j.put("pledge", (pledge *number1));
				j.put("totalPrice", totalPrice1);
			}
		}
		
		String sql2 = "select sc_id,u_id userid,pro_id proid,sc_model model,sc_weight weight,sc_price price,sc_color color,sc_name name,sc_number number,pro_multiple ppdnum,sc_imgurl url,sc_type type from jx_shopping_cart where u_id ='"+uid+"' and pro_id = 3 and sc_state = 1 ";
		List<Map<String, Object>> list2 = this.jdbcTemplate.queryForList(sql2);
		for(int i = 0; i<list2.size();i++){
			Map<String,Object> j =list2.get(i);
			int type1 = (Integer) j.get("type");
			int number1 = (Integer) j.get("number");
			int ppdnum1 = (Integer) j.get("ppdnum");
			Float price1 = (Float) j.get("price");
			Float totalPrice1 = (number1*ppdnum1*price1)+(pledge * number1);
			if(type1 == 0){
				if(ppdnum1 == 3){
					pledge = 0;
				}else{
					pledge = (Integer) map0.get("pledge");
				}
				totalPrice1 = (number1*ppdnum1*price1)+(pledge * number1);
				String years = "包年购买:"+" "+j.get("ppdnum")+"年";
				j.put("yearsorflow", years);
				j.put("pledge", (pledge *number1));
				j.put("totalPrice", totalPrice1);
			}else{
				if(ppdnum1 == 3){
					pledge = 0;
				}else{
					pledge = (Integer) map0.get("pledge");
				}
				totalPrice1 = (number1*ppdnum1*price1)+(pledge * number1);
				int s1 = (Integer) j.get("number");
				int prepaidTraffic = (int) ((price1 * ppdnum1)/unitprice);
				String years = "流量预付:"+" "+prepaidTraffic+"升"+"("+price1+"*"+ppdnum1+"/"+unitprice+"="+prepaidTraffic+"升"+")";
				j.put("yearsorflow", years);
				j.put("pledge", (pledge *number1));
				j.put("totalPrice", totalPrice1);
			}
		}
		
		String sql3 = "select sc_name name from jx_shopping_cart where u_id ='"+uid+"' and pro_id = 1 and sc_state = 1  LIMIT 1 ";
		List<Map<String, Object>> list3 = this.jdbcTemplate.queryForList(sql3);
		
		List<Map<String, List<Map<String, Object>>>> l = new ArrayList<Map<String, List<Map<String, Object>>>>();
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		Map<String, List<Map<String, Object>>> map1 = new HashMap<String, List<Map<String, Object>>>();
		Map<String, List<Map<String, Object>>> map2 = new HashMap<String, List<Map<String, Object>>>();
		
		Map<String, Object> m = new HashMap<String, Object>();
		List<Map<String, Object>> l1 = new ArrayList<Map<String,Object>>();
		l1.add(m);
		//m.put("name", "壁挂式净水机");
		m.put("proid", "1");
		
		Map<String, Object> mm = new HashMap<String, Object>();
		List<Map<String, Object>> l2 = new ArrayList<Map<String,Object>>();
		l2.add(mm);
		//mm.put("name", "台式净水机");
		mm.put("proid", "2");
		
		Map<String, Object> mmm = new HashMap<String, Object>();
		List<Map<String, Object>> l3 = new ArrayList<Map<String,Object>>();
		l3.add(mmm);
		//mmm.put("name", "立式净水机");
		mmm.put("proid", "3");
		
		//净水机名字
		Map<String, Object> n = new HashMap<String, Object>();
		List<Map<String, Object>> n1 = new ArrayList<Map<String,Object>>();
		n1.add(n);
		n.put("name", "壁挂式净水机");
		
		Map<String, Object> nn = new HashMap<String, Object>();
		List<Map<String, Object>> n2 = new ArrayList<Map<String,Object>>();
		n2.add(nn);
		nn.put("name", "台式净水机");
		
		Map<String, Object> nnn = new HashMap<String, Object>();
		List<Map<String, Object>> n3 = new ArrayList<Map<String,Object>>();
		n3.add(nnn);
		nnn.put("name", "立式净水机");
		
		if(list.size()>0){
			map.put("list", list);
			map.put("proid", l1);
			//map.put("name", n1);
			map.put("name", list3);
			l.add(map);
		}
		if(list1.size()>0){
			map1.put("list", list1);
			map1.put("proid", l2);
			map1.put("name", n2);
			l.add(map1);
		}
		if(list2.size()>0){
			map2.put("list", list2);
			map2.put("proid", l3);
			map2.put("name", n3);
			l.add(map2);
		}
		
		return l;
	}

	//删除商品的方法
	@Override
	public int delCartProduct(Long... ids) {
		int s = 1;
		StringBuffer sb = new StringBuffer();
		List<String> l = new ArrayList<String>();
		l.add(ids.toString());
		for (String String : l) {
			System.out.println("删除id"+l);
			sb.append("update jx_shopping_cart set sc_state = 0 where sc_state = 1 and sc_id = ").append(String);
		}
		return s;
	}

	//修改购物车商品
	@Override
	public int updateCartProduct(String id, String color,
			int number, int ppdnum) {
		String sql = "update jx_shopping_cart set sc_color = '"+color+"',sc_number = '"+number+"',pro_multiple = '"+ppdnum+"' where sc_id = '"+id+"' and sc_state= 1";
		return jdbcTemplate.update(sql);
	}

	//计算购物车数量
	@Override
	public int selectnum(String uid) {
		String sql = "select sum(sc_state) from jx_shopping_cart where u_id = '"+uid+"' and sc_state= 1";
		return jdbcTemplate.queryForInt(sql);
	}

	//删除用户商品子项
	@Override
	public int delCartProducts(String proid) {
		String sql = "update jx_shopping_cart set sc_state = 0 where sc_state = 1 and pro_id ="+proid;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateCartProductnum(String id, String number) {
		String sql = "update jx_shopping_cart set sc_number = '"+number+"' where sc_id = '"+id+"' and sc_state= 1 ";
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateCartProductsnum(String proid, int number) {
		String sql = "update jx_shopping_cart set sc_number = '"+number+"' where sc_id = '"+proid+"' and sc_state= 1 ";
		return jdbcTemplate.update(sql);
	}

	@Override
	public List<Map<String, Object>> findAllProducts(String uid) {
		String sql = "select * from jx_shopping_cart where u_id ='"+uid+"' and sc_state = 1 ";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<List<Map<String, Object>>> findAllProductToList(
			String uid) {
		
		String sql = "select * from jx_shopping_cart where u_id ='"+uid+"' and pro_id = 1 and sc_state = 1 ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		String sql1 = "select * from jx_shopping_cart where u_id ='"+uid+"' and pro_id = 2 and sc_state = 1 ";
		List<Map<String, Object>> list1 = jdbcTemplate.queryForList(sql1);
		
		String sql2 = "select * from jx_shopping_cart where u_id ='"+uid+"' and pro_id = 3 and sc_state = 1 ";
		List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql2);
		
		
		
		List<List<Map<String, Object>>> l1 = new ArrayList<List<Map<String,Object>>>();
		if(list.size()<=0){
			
		}else{
			
			l1.add(list);
		}
		if(list1.size()<=0){
			
		}else{
			
			l1.add(list1);
		}
		if(list2.size()<=0){
			
		}else{
			l1.add(list2);
		}
		
		
		System.out.println("集合:"+l1);
		return l1;
	}

	@Override
	public List<Map<String, Object>> findAllProductToLists(String uid) {
		String sql = "select * from jx_shopping_cart where u_id ='"+uid+"' and pro_id = 1 and sc_state = 1 ";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findAllProductToListss(String uid) {
		String sql = "select * from jx_shopping_cart where u_id ='"+uid+"' and pro_id = 2 and sc_state = 1 ";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> findAllProductToListsss(String uid) {
		String sql = "select * from jx_shopping_cart where u_id ='"+uid+"' and pro_id = 3 and sc_state = 1 ";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public int delCartProduct(String[] ids) {
		int s = 1;
		StringBuffer sb = new StringBuffer();
		List<String> l = new ArrayList<String>();
		l.add(ids.toString());
		for (String String : l) {
			System.out.println("删除id"+l);
			sb.append("update jx_shopping_cart set sc_state = 0 where sc_state = 1 and sc_id = ").append(String);
		}
		return s;
	}

	//批量删除购物车
	@Override
	public int delCartProduct(String id) {
		String sql = "update jx_shopping_cart set sc_state = 0 where sc_state = 1 and sc_id in ("+id+")";
		return jdbcTemplate.update(sql);
	}

	//用户下商品的数量
	@Override
	public int findProductNumber(String uid) {
		String sql = "select sum(sc_number) from jx_shopping_cart where u_id = '"+uid+"' and sc_state= 1";
		return jdbcTemplate.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> selectAllProduct(String id) {
		//String sql = "select * from jx_shopping_cart where sc_state = 1 and sc_id in ("+id+")";
		//String sql = "select u_id userid,pro_id proid,sc_model model,sc_weight weight,sc_price price,sc_color color,sc_name name,sc_number number,pro_multiple ppdnum,sc_imgurl url,sc_type,sc_tag tag,sc_state state from jx_shopping_cart where sc_state = 1 and sc_id in ("+id+")";
		String sql = "select u_id userid,pro_id proid,sc_model model,sc_weight weight,sc_price price,sc_color color,sc_name name,sc_number number,pro_multiple ppdnum,sc_imgurl url,sc_type type,sc_tag tag,sc_state state from jx_shopping_cart where sc_id in ("+id+")";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> selectProduct(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> findId(String s1) {
		String sql = "select sc_id from jx_shopping_cart where sc_tag = '"+s1+"'";
		return jdbcTemplate.queryForMap(sql);
	}

	@Override
	public int delProduct(String id) {
		String sql = "DELETE FROM jx_shopping_cart where  sc_id in ("+id+")";
		return jdbcTemplate.update(sql);
	}

	@Override
	public int findState(String color, String name,String uid) {
		String sql = "select sc_state from jx_shopping_cart where sc_color = '"+color+"' and sc_name = '"+name+"' and u_id = '"+uid+"' and sc_state = 1";
		return jdbcTemplate.queryForInt(sql);
		
	}

	//得到所有的数量
	@Override
	public int findNumbers(String id) {
		String sql = "select sum(sc_number) from jx_shopping_cart where sc_id in ("+id+")";
		return jdbcTemplate.queryForInt(sql);
	}

	
}
