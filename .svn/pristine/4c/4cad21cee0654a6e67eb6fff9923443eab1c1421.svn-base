package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.game.smvc.entity.JxProduct;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.SecretResult;

import net.sf.json.JSONObject;

public class TestMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Map<String,String> map = new HashMap<String, String>();
		map.put("img1", "http://www.milaisz.com:10010/mobile_server/img/goods/good_20150530210933227/imgFile1.png");
		
		System.out.println(map.toString());*/
		
		String str = "{\"img2\":\"http://192.168.1.45:8080/jx_smart/58877beabda9710f628b629c6dbcedd5.png\",\"img1\":\"http://192.168.5.88:8082/picture/forklift/goods/201601/145370258225302917.jpg\",\"img5\":null,\"img3\":\"http://192.168.5.88:8082/picture/forklift/goods/201601/145370258278712189.jpg\",\"img4\":null}";
		//String str = "{\"pledge\":\"http://192.168.1.45:8080/jx_smart/smvc/launch/test/visit.v }";
		
		//http://192.168.1.45:8080/jx_smart/58877beabda9710f628b629c6dbcedd5.png
		 
		
		JSONObject json = JSONObject.fromObject(str);
		System.out.println(json);//{"c":"d","a":"b"}
		String img1 = json.get("img1").toString();
		String img2 = json.get("img2").toString();
		String img3 = json.get("img3").toString();
		String img4 = json.get("img4").toString();
		
		Object img5 = json.get("img5");
		
		System.out.println(img1);
		System.out.println(img2);
		System.out.println(img3);
		System.out.println(img4);
		System.out.println(img5==null);
		
        

	}	

}
