package com.game.smvc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;








import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.smvc.entity.result.DataResult;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.util.HttpUtil;

@Controller
@RequestMapping({ "/smvc" })
public class SeverController {
    @ResponseBody
    @RequestMapping(value="/sever/test/querybill")
    public Result querybill(HttpServletRequest request) {
        try{
            String params = HttpUtil.getRquestParamsByIO(request);
            JSONObject json = JSONObject.fromObject(params);
            String phone = json.getString("phoneNum");
            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
            
            return new DataResult(Errors.OK,list);
        }catch(Exception e){
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    @ResponseBody
    @RequestMapping(value="/sever/test/querypurifier")
    public Result querypurifier(HttpServletRequest request) {
        try{
            String params = HttpUtil.getRquestParamsByIO(request);
            List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
            return new DataResult(Errors.OK,null);
        }catch(Exception e){
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    @ResponseBody
    @RequestMapping(value="/sever/test/queryserver")
    public Result queryserver(HttpServletRequest request) {
        try{
            String params = HttpUtil.getRquestParamsByIO(request);
            List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();
            return new DataResult(Errors.OK,null);
        }catch(Exception e){
            e.printStackTrace();
            return new Result(Errors.EXCEPTION_UNKNOW);
        }
    }
    
    
    public static void main(String[] args) {
		String a="10000.1000000014901161";
		 float  b =  (float)(Math.round(Float.valueOf(a)*100))/100;
		 System.out.println(b);
	}
}
