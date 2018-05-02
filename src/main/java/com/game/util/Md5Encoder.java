package com.game.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;


public class Md5Encoder {
	
	
	public static String md5(File f){
		
			
			try {
				if(f.exists()){
					
					FileInputStream fileInputStream = new FileInputStream(f);
					byte[] buffer = new byte[8192];
					DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, MessageDigest.getInstance("md5"));
					MessageDigest messageDigest = digestInputStream.getMessageDigest();
					
					int i = -2;
					while(i!=-1){
						i = digestInputStream.read(buffer);
					}
					
					byte[] aftercode = messageDigest.digest();
					String md5code = new String(Hex.encodeHex(aftercode));		
					digestInputStream.close();
					
					return md5code;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} 

		return null;
	}
	public static String md5(String f){
		try {
			if(f!=null&&f.length()>0){				
				   MessageDigest md = MessageDigest.getInstance("MD5");   
				   md.update(f.getBytes());  
				   String pwd = new BigInteger(1, md.digest()).toString(16);   
				    //System.err.println(pwd);   
				   return pwd;   
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 

	return null;
}

	public static void main(String[] args) {
		/*String filepath="D:\\apk\\a.apk";
		File a=new File(filepath);
		String res=md5(a);
		System.out.println(res);*/
		
	}
	
}