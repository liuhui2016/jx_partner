package com.game.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {	
	private Properties prop = new Properties();	
	public PropertiesUtil(){
		
	}	
	public PropertiesUtil(String propertiesFileName){
		initProperties(propertiesFileName);
	}
	
	public PropertiesUtil(InputStream inputStream){
		initProperties(inputStream);
	}
	
	private void initProperties(String propertiesFileName){
		try{
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName);
	        prop.load(inputStream);
	        if(inputStream != null){
	        	inputStream.close();
	        }
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void initProperties(InputStream inputStream){
		try{
	        prop.load(inputStream);
	        if(inputStream != null){
	        	inputStream.close();
	        }
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public String getValue(String keyName){
		try{
			String ret = prop.getProperty(keyName);
			if(ret == null) return "";
			return ret.trim();
		}catch(Exception ex){
			return "";
		}
	}
	
	public Properties getProp() {
		return prop;
	}
	
	public void setProp(Properties prop) {
		this.prop = prop;
	}
	
	


	
}
