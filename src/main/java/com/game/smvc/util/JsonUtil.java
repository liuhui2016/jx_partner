package com.game.smvc.util;

import java.net.URLDecoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	public static String toJson(Object obj) {
		JSONObject json = JSONObject.fromObject(obj);
		return json.toString();
	}
	public static String toJsonArray(Object obj) {
        JSONArray json = JSONArray.fromObject(obj);
        return json.toString();
    }
	public static void main(String[] args) {
	}
}
