package com.game.smvc.util;



public class IdentifyingUtil {

	public static String Identifying(){
		String str = "jxsmart";
		return str;
	}
	
	public static Float leastMoney(){
		Float str = 0.1f;
		return str;
	}
	public static Float maxMoney(){
		Float str = 50000f;
		return str;
	}
	
	public static Float sqc(){
		Float str = 0.12f;
		return str;
	}
	
	public static Float ssx(){
		Float str = 0.18f;
		return str;
	}
	
	public static Float zcfw(){
		Float str = 0.05f;
		return str;
	}
	
	public static Float town(){
		Float str = 0.07f;
		return str;
	}
	
	public static Float qx(){
		Float str = 0.13f;
		return str;
	}
	
	public static int isPay(){
		int str = 1;
		return str;
	}
	
	public static Float proportion(String level){
		Float str = 0f;
		if(level.equals("1")){
			str = 0.65f;
		}else if(level.equals("2")){
			str = 0.60f;
		}else if(level.equals("3")){
			str = 0.53f;
		}else if(level.equals("4")){
			str = 0.40f;
		}else{
			str = 0f;
		}
		return str;
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(IdentifyingUtil.proportion("3"));
	}
	   
}
