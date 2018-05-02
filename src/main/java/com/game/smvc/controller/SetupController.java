package com.game.smvc.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.bmanager.entity.JxOpinon;
import com.game.bmanager.service.IJxAdvpicService;
import com.game.bmanager.service.IJxApkVersionService;
import com.game.bmanager.service.IJxOpinonService;
import com.game.smvc.entity.result.DataResult;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxMerchantPublishService;
import com.game.smvc.service.IJxOrderService;
import com.game.smvc.service.IJxProductService;
import com.game.smvc.service.IJxStatisticalService;
import com.game.smvc.service.IJxUserService;
import com.game.smvc.util.HttpUtil;

@Controller
@RequestMapping({ "/smvc" })
public class SetupController {
	@Autowired
	private IJxUserService userService;
	@Autowired
    private IJxApkVersionService apkVersionService;
    @Autowired
    private IJxProductService productService;
    @Autowired
    private IJxAdvpicService advpicService;
    @Autowired
    private IJxOpinonService opinonService;
    @Autowired
	IJxOrderService jxOrderService;
    @Autowired
	private IJxStatisticalService jxStatisticalService;
    @Autowired
	private IJxMerchantPublishService merchantPublish;
	/**
	 * APP在打开的时候，显示的图片
	 * @return
	 */

    @ResponseBody
	@RequestMapping(value="/setup/mainImg")
	public Result mainImg(HttpServletRequest request){
		try{
			
			
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String page = jsonObject.getString("page");
			List<Map<String,Object>> list = productService.findallPrototal((Integer.parseInt(page)-1)*5);
			return new SecretResult(Errors.OK,list);
		}catch(Exception e){
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
		
	}
    
    //首页图片
    @ResponseBody
	@RequestMapping(value="/setup/homepage")
	public Result homepage(HttpServletRequest request){
		try{
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String type = jsonObject.getString("type");
			List<Map<String, List<Map<String, Object>>>> list = new ArrayList<Map<String,List<Map<String,Object>>>>();
			Map<String, List<Map<String, Object>>> m = new HashMap<String, List<Map<String, Object>>>();
			Map<String, List<Map<String, Object>>> m1 = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> jd = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> l = productService.findhomepage(type);//首页大图
			List<Map<String, Object>> ll = productService.findphbimg();//排行榜图片
			
			Map<String, Object> map1 = ll.get(0);
			Map<String, Object> map2 = ll.get(1);
			Map<String, Object> map3 = ll.get(2);
			
			//社区排行榜
			List<Map<String,Object>> ranking = merchantPublish.findRanking();
			
			Map<String,Object> r = ranking.get(0);
			Long pub_id = (Long) r.get("pub_id");
			String seller = (String) r.get("pub_seller");
			map1.put("adv_name", seller);
			map1.put("pub_id", pub_id);
			
			Map<String,Object> r1 = ranking.get(1);
			Long pub_id1 = (Long) r1.get("pub_id");
			String seller1 = (String) r1.get("pub_seller");
			map2.put("adv_name", seller1);
			map2.put("pub_id", pub_id1);
			
			Map<String,Object> r2 = ranking.get(2);
			Long pub_id2 = (Long) r2.get("pub_id");
			String seller2 = (String) r2.get("pub_seller");
			map3.put("adv_name", seller2);
			map3.put("pub_id", pub_id2);
			
			jd.add(map1);
			jd.add(map2);
			jd.add(map3);
			m.put("home_page", l);
			m1.put("ranking_list", jd);
			list.add(m);
			list.add(m1);
			return new SecretResult(Errors.OK,list);
		}catch(Exception e){
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
		
	}
    


    /**
     * 打开APP的时候访问这个接口，同时检验APP的更新状态
     * @param batchId,mac,imei,imsi,androidId,appVersion,
     * @param systemVersion,brand,model,src,netType
     * @return
    
    @ResponseBody
    @RequestMapping(value="/launch/test/visit",method = RequestMethod.POST)
    public Result visit(HttpServletRequest request){
        try{
           // String body = readRequestBody(request);
//          String json = Des.decryptDES(params.substring(8, params.length()-8));
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject jsonObj = JSONObject.fromObject(params);
//          String mac = jsonObj.getString("mac");
//          String imei = jsonObj.getString("imei");
//          String imsi = jsonObj.getString("imsi");
//          String androidID = jsonObj.getString("androidID");
//          String studioVersion = jsonObj.getString("studioVersion");
//          Integer appVersion = jsonObj.getInt("appVersion");
//          String systemVersion = jsonObj.getString("systemVersion");
//          String brand = jsonObj.getString("brand");
//          String model = jsonObj.getString("model");
            Integer ver = jsonObj.getInt("ver");
            String type = jsonObj.getString("type");
            Map<String,Object> lastApk = apkVersionService.queryLastApk(type);
            System.out.println(lastApk);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if(lastApk==null){
//              data = Des.initkey()+Des.encryptDES(data)+Des.initkey();
                return new SecretResult(Errors.OK,list);
            }
            Integer lastVersion = Integer.valueOf(lastApk.get("apkVersion").toString());
            Map<String, Object> extMap = new HashMap<String, Object>();
            if (lastVersion > ver) {
                extMap.put("versionCode", lastVersion);
                extMap.put("downurl", lastApk.get("apkUrl"));
                extMap.put("length", lastApk.get("apkSize"));
                extMap.put("name", lastApk.get("apkName"));
                extMap.put("mustupgrade", lastApk.get("mustupgrade"));
            }
            list.add(extMap);
            return new SecretResult(Errors.OK,list);
        } catch(Exception e){
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    
     */
    
    
    
    @ResponseBody
    @RequestMapping(value="/launch/test/visit",method = RequestMethod.POST)
    public Result visit(HttpServletRequest request){
        try{
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject jsonObj = JSONObject.fromObject(params);
            Integer ver = jsonObj.getInt("ver");
            String type = jsonObj.getString("type");
            Map<String,Object> lastApk = apkVersionService.queryLastApk(type);
            System.out.println(lastApk);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if(lastApk==null){
                return new SecretResult(Errors.OK,list);
            }
            Integer lastVersion = Integer.valueOf(lastApk.get("apkVersion").toString());
            Map<String, Object> extMap = new HashMap<String, Object>();
            if (lastVersion > ver) {
                extMap.put("versionCode", lastVersion);
                extMap.put("downurl", lastApk.get("apkUrl"));
                extMap.put("length", lastApk.get("apkSize"));
                extMap.put("name", lastApk.get("apkName"));
                extMap.put("mustupgrade", lastApk.get("mustupgrade"));
            }
            list.add(extMap);
            return new SecretResult(Errors.OK,list);
        } catch(Exception e){
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    
    
    
    
    
    /**
     * 打开APP的时候访问这个接口，同时检验APP的广告信息
     * @param 
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/launch/test/getAdver",method = RequestMethod.POST)
    public Result getAdver(HttpServletRequest request){
        try{
            String params = HttpUtil.getRquestParamsByIO(request);
            String type = JSONObject.fromObject(params).getString("type");
            
            List<Map<String,Object>> list = advpicService.queryAdverByType(type);
            return new SecretResult(Errors.OK, list);
        }catch(Exception e){
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    
   
    
    /**
     * 用户意见反馈
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/setup/addOption")
    public Result addFeedback(
                    HttpServletRequest request){
        try{
            String authCode = HttpUtil.getRquestParamsByIO(request);
            JSONObject jsonObject = JSONObject.fromObject(authCode);
            String phoneNum = jsonObject.getString("phoneNum");
            String context=jsonObject.getString("context");
            String name=this.opinonService.findUsernameByPhone(phoneNum);
            JxOpinon option=new JxOpinon();
            option.setOp_detail(context);
            option.setOp_phone(phoneNum);
            option.setOp_addtime(new Date());
            option.setOp_user(name);
            opinonService.save(option);
        }catch(Exception e){
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
        return new Result(Errors.OK);
    }
    
    /**
     * apk下载接口
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/apk/download")
    public Result downloadApk(HttpServletRequest request,HttpServletResponse response){
        try{
            String type = request.getParameter("type");
            if(type==null){
                String userAgent = request.getHeader("user-agent");
                System.out.println(userAgent);
                if(userAgent.indexOf("iPhone")>-1){
                    type = "2";
                }else if(userAgent.indexOf("Android")>-1){
                    type = "1";
                }else{
                    return new Result(Errors.PARAM_ERROR);
                }
            }
            String url = (String) apkVersionService.queryLastApk(type).get("apkUrl");
            response.sendRedirect(url);
        }catch(Exception e){
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
        return new Result(Errors.OK);
    }
    
    
   
}
