package com.game.smvc.entity.result;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.game.util.Des;

public class SecretResult extends Result  {
    protected String data;
    protected String datas;
	@SuppressWarnings("rawtypes")
	public SecretResult(Errcode errcode, List data) throws Exception {
        super(errcode, errcode.getMsg());
        System.out.println(data.toString());
        JSONArray array = JSONArray.fromObject(data);

        String string = array.toString();
     
        string = Des.encryptDES(string);

        this.data = string;
//        this.data = Des.encryptDES(JSONArray.fromObject(data).toString());
    }
	
	
	public SecretResult(Errcode errcode, Map data) throws Exception {
        super(errcode, errcode.getMsg());
        System.out.println(data.toString());
        JSONArray array = JSONArray.fromObject(data);

        String string = array.toString();
     
        string = Des.encryptDES(string);

        this.data = string;
//        this.data = Des.encryptDES(JSONArray.fromObject(data).toString());
    }
	
	@SuppressWarnings("rawtypes")
	public SecretResult(Errcode errcode, String datas) throws Exception {
        super(errcode, errcode.getMsg());
        System.out.println(datas);
        //JSONArray array = JSONArray.fromObject(datas);
        JSONObject array = JSONObject.fromObject(datas);

        String string = array.toString();
     
        string = Des.encryptDES(string);

        this.datas = string;
        //this.datas = Integer.parseInt(string);
//        this.data = Des.encryptDES(JSONArray.fromObject(data).toString());
    }
	
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	
    
   
    
}
