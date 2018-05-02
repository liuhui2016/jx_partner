package com.game.smvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.protocol.HttpService;
import org.apache.poi.hssf.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.bmanager.service.IJxPictureService;
import com.game.bmanager.service.IJxPrototalService;
import com.game.smvc.entity.JxOrder;
import com.game.smvc.entity.JxShoppingCart;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxOrderItemService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxPayWayService;
import com.game.smvc.service.IJxProductService;
import com.game.smvc.service.IJxShoppingCartService;
import com.game.smvc.util.HttpUtil;

/**
 * 购物车信息
 * 主要涉及购物车的增删改查
 * 这里购物车主要用cookie和数据表
 * @author lh
 *
 */
@Controller
@RequestMapping({ "/smvc" })
public class ShoppingCartController {

	@Autowired
	IJxProductService productService;
	@Autowired
	IJxOrderService jxOrderService;
	@Autowired
	private IJxOrderItemService jxOrderItemServic;
	@Autowired
	private IJxShoppingCartService jxShoppingCartService;
	@Autowired
	private IJxPictureService pictureService;
	@Autowired
	private IJxPrototalService prototalService;
	@Autowired
	private IJxPayWayService payWayService;
	
	//添加商品(加入购物车)
	@ResponseBody
	@RequestMapping(value = "/shoppingcart/addshoppingcart")
	public Result addToShoppingCart(HttpServletRequest request) {
		try {
			System.out.println("---开始加入商品到购物车---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String price = jsonObject.getString("price");//单价
			String name = jsonObject.getString("name");//净水机名字
			String url = jsonObject.getString("url");//图片地址
			String color = jsonObject.getString("color");//净水机颜色
			String ppdnum = jsonObject.getString("ppdnum");//倍数
			String number = jsonObject.getString("number");//数量
			String uid = jsonObject.getString("userid");//用户id
			String proid = jsonObject.getString("proid");//商品id
			String type = jsonObject.getString("type");//包年还是包流量/交易类型
			
			//得到单价
			/*List<Map<String, Object>> pe =  payWayService.findPrice(proid,type);
			Map<String, Object> m1 = pe.get(0);
			String price = (String) m1.get("price");
			*/
			//得到净水机url
		/*	List<Map<String, Object>> Url = pictureService.findNmaeAndUrl(proid,color);
			Map<String, Object> nau = Url.get(0);
			String url = (String) nau.get("url");
			*/
			//得到净水机名字 、型号、重量、押金
			List<Map<String, Object>> jxPrototal = prototalService.findparam(proid);
			Map<String, Object> m = jxPrototal.get(0);
			//String name = (String) m.get("name");
			String model = (String) m.get("typename");
			String wx = (String) m.get("wx");
			Integer pledge = (Integer) m.get("pledge");
			
			//根据uid去查找商品
			JxShoppingCart map1 = jxShoppingCartService.findShoppingCarts(uid);
			int state = jxShoppingCartService.selectnum(uid);//判断购物车是否有商品
			//如果map1为空，则是第一次购买			
			if(map1 == null || map1.getU_id() == null){
				System.out.println("第一次");
				map1.setSc_name(name);
				map1.setSc_state(1);
				map1.setSc_model(model);
				map1.setSc_weight(wx);
				map1.setSc_type(Integer.parseInt(type));
				map1.setPro_multiple(Integer.parseInt(ppdnum));
				map1.setSc_number(Integer.parseInt(number));
				map1.setSc_color(color);
				map1.setSc_imgurl(url);
				map1.setPro_id(Integer.parseInt(proid));
				map1.setU_id(Long.parseLong(uid));
				map1.setSc_price(Float.parseFloat(price));
				jxShoppingCartService.save(map1);
				
			}else{
				//得到商品 (确定商品的唯一性)
				JxShoppingCart cart = jxShoppingCartService.findShoppingCartOnProduct(uid,proid,color,type,ppdnum);
				
				//此用户下此商品为空
				if(cart == null || cart.getU_id() == null || cart.getSc_number() == 0){
					System.out.println("用户没有此商品");
					cart.setSc_name(name);
					cart.setSc_state(1);
					cart.setSc_model(model);
					cart.setSc_weight(wx);
					cart.setSc_type(Integer.parseInt(type));
					cart.setPro_multiple(Integer.parseInt(ppdnum));
					cart.setSc_number(Integer.parseInt(number));
					cart.setSc_color(color);
					cart.setSc_imgurl(url);
					cart.setPro_id(Integer.parseInt(proid));
					cart.setU_id(Long.parseLong(uid));
					cart.setSc_price(Float.parseFloat(price));
					jxShoppingCartService.save(cart);
					
				}else{
				//购物车有商品（判断颜色和名字）
				System.out.println("颜色:"+cart.getSc_color());
				System.out.println("名字:"+cart.getSc_name());
				cart.getSc_type();
				if(cart.getSc_color().equals(color) && cart.getSc_name().equals(name)){
					System.out.println("有商品");
					//相同则数量加一 
		            jxShoppingCartService.updateNumber(uid,color,name,number,type,ppdnum);
		            
					}else{
						System.out.println("有商品，不相同");
						//把商品加入购物车
						JxShoppingCart map = new JxShoppingCart();
						map.setSc_name(name);
						map.setSc_state(1);
						map.setSc_model(model);
						map.setSc_weight(wx);
						map.setSc_type(Integer.parseInt(type));
						map.setPro_multiple(Integer.parseInt(ppdnum));
						map.setSc_number(Integer.parseInt(number));
						map.setSc_color(color);
						map.setSc_imgurl(url);
						map.setPro_id(Integer.parseInt(proid));
						map.setU_id(Long.parseLong(uid));
						map.setSc_price(Float.parseFloat(price));
						jxShoppingCartService.save(map);
						
					}
				}
			}
			
			/*int states = jxShoppingCartService.findState(color,name,uid);
			
			if(state == 0){
				System.out.println("购物车被清空");
				JxShoppingCart map = new JxShoppingCart();
				map.setSc_name(name);
				map.setSc_state(1);
				map.setSc_model(model);
				map.setSc_weight(wx);
				map.setSc_type(Integer.parseInt(type));
				map.setPro_multiple(Integer.parseInt(ppdnum));
				map.setSc_number(Integer.parseInt(number));
				map.setSc_color(color);
				map.setSc_imgurl(url);
				map.setPro_id(Integer.parseInt(proid));
				map.setU_id(Long.parseLong(uid));
				map.setSc_price(Float.parseFloat(price));
				jxShoppingCartService.save(map);
			}*/
			
			System.out.println("---开始计算数量---");
			Map<String,Object> map0 = new HashMap<String, Object>();
			int sum = jxShoppingCartService.selectnum(uid);//商品子项数量
			//int productNumber = jxShoppingCartService.findProductNumber(uid);//商品数量
			map0.put("sum", sum);
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			list.add(map0);
			System.out.println("用户购物车数量:"+list);
			
			return new SecretResult(Errors.OK,list);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}

	}
	
	
	//查询所有商品(可以根据u_id来遍历所以商品)
	@ResponseBody
	@RequestMapping(value = "/shoppingcart/showcat")
	public Result showCart(HttpServletRequest request){
		try{
			System.out.println("---开始查询商品---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String uid = jsonObject.getString("userid");
			List<Map<String,List<Map<String, Object>>>> list = jxShoppingCartService.findAllProduct(uid);
			
			return new SecretResult(Errors.OK,list);
		}catch(Exception e){
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	//删除商品
	@ResponseBody
	@RequestMapping(value = "/shoppingcart/delcat")
	public Result deleteToShoppingCart(HttpServletRequest request){
		try{
			System.out.println("---开始删除商品---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String id= jsonObject.getString("id");//购物车的id
			//int del = jxShoppingCartService.delCartProduct(id);
			int del = jxShoppingCartService.delProduct(id);
			String deletes = String.valueOf(del);
			if(del == 0){
				System.out.println("删除失败");
				return new Result(Errors.DELETE_FAILED);
			}else{
				System.out.println("删除成功");
				return new Result(Errors.SUCCESSFULLY_DELETE);
			}
			//return new Result(Errors.OK);
			//return new SecretResult(Errors.OK,deletes);
		}catch(Exception e){
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	//修改商品(修改商品数量、颜色、倍数)
	@ResponseBody
	@RequestMapping(value = "/shoppingcart/updatecat")
	public Result amendToShoppingCart(HttpServletRequest request){
		try{
			System.out.println("---开始修改商品属性---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String id = jsonObject.getString("id");
			String number = jsonObject.getString("number");//商品数量
			int updateCart = jxShoppingCartService.updateCartProductnum(id,number);
			String updateCarts = String.valueOf(updateCart);
			if(updateCart == 0){
				System.out.println("修改失败");
				return new Result(Errors.DELETE_FAILED);
			}else{
				System.out.println("修改成功");
				return new Result(Errors.SUCCESSFULLY_DELETE);
			}
		}catch(Exception e){
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	
	//购物车商品数量
	@ResponseBody
	@RequestMapping(value = "/shoppingcart/selectnum")
	public Result selectNumber(HttpServletRequest request){
		try{
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String uid = jsonObject.getString("userid");
			int sum = jxShoppingCartService.selectnum(uid);
			//int sum = jxShoppingCartService.findProductNumber(uid);//商品数量
			Map<String,Object> map = new HashMap<String, Object>();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			map.put("sum", sum);
			list.add(map);
			return new SecretResult(Errors.OK,list);
		}catch(Exception e){
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
}
