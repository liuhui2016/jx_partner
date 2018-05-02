package com.game.util;


public class CustomIdCache {
	
	private static CustomIdCache uniqueInstance = null;
	
	public static String CUSTOMID = "";

    private CustomIdCache() {

    }

    public static CustomIdCache getInstance() {
       if (uniqueInstance == null) {
           uniqueInstance = new CustomIdCache();
       }
       return uniqueInstance;
    }

	public static String getCUSTOMID() {
		return CUSTOMID;
	}

	public static void setCUSTOMID(String cUSTOMID) {
		CUSTOMID = cUSTOMID;
	}
}
