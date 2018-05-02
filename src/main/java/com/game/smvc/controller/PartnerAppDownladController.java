package com.game.smvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.bmanager.service.IJxApkVersionService;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.util.HttpUtil;

@Controller
@RequestMapping({ "/smvc" })
public class PartnerAppDownladController {

	@Autowired
    private IJxApkVersionService apkVersionService;
	
	/**
     * 合伙人apk下载接口
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/partner/apk/download")
    public Result downloadApk(HttpServletRequest request,HttpServletResponse response){
        try{
            String type = request.getParameter("type");
            if(type==null){
                String userAgent = request.getHeader("user-agent");
                System.out.println(userAgent);
                if(userAgent.indexOf("iPhone")>-1){
                    type = "4";
                }else if(userAgent.indexOf("Android")>-1){
                    type = "3";
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
    
    /**
     * 根据版本号和type值得到版本信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/partner/launch/test/visit")
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
    
    
	
}
