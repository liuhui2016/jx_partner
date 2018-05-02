package com.game.util;


import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Des {


	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	
    public static String initkey(){
		String[] keys = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
				"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
				"y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
    	StringBuffer kstr = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			int d = new Random().nextInt(62);
			kstr.append(keys[d]);
		}
    	return kstr.toString();
    }
	public static String encryptDES(String encryptString, String encryptKey)
			throws Exception {
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes("utf-8"));

		return Base64.encode(encryptedData);
	}
	
	public static String encryptDES(String encryptString) throws Exception {
		String encryptKey = initkey();
		return encryptDES(encryptString, encryptKey).concat(encryptKey);
	}

	public static String decryptDES(String decryptString, String decryptKey)
			throws Exception {
		byte[] byteMi = Base64.decode(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);

		return new String(decryptedData,"utf-8");
	}
	
	public static String decryptDES (String src) throws Exception {
		//String urlKey = src.substring(src.length() - 8, src.length());
		//String readlyContent = src.substring(0, src.length() - 8);
		String urlKey = src.substring(src.length() - 8, src.length());
		String readlyContent = src.substring(0, src.length() - 8);
		return decryptDES(readlyContent, urlKey);
	}
    public static void main(String[] args) {
        try {
            //System.out.println(Des.encryptDES("root"));//vX+cf2Zor7g=7SpDp7yt
            //System.out.println(Des.encryptDES("uhjg!$2Ale0j#*R9"));//vNs8hjgDSrv4kvFhFi9KbfRPu9M6A7ix6AeEtDeY
            //System.out.println(Des.encryptDES("jdbc:mysql://113.106.93.195:3366/jxdb?useUnicode=true&characterEncoding=utf-8"));//K923PyP4UjW9XqDbvxu/u0TCU51ZFWxeSAsGUfryw01+3yJmxSuRAsk5JJu0 8eq220WZOEAZyfKZfgocgudbTuXimTA4afiikVJ2AVOwQls=9SZFAW7l
            //System.out.println(Des.decryptDES("vX+cf2Zor7g=7SpDp7yt"));
            //System.out.println(Des.decryptDES("vNs8hjgDSrv4kvFhFi9KbfRPu9M6A7ix6AeEtDeY"));
            //System.out.println(Des.decryptDES("K923PyP4UjW9XqDbvxu/u0TCU51ZFWxeSAsGUfryw01+3yJmxSuRAsk5JJu0 8eq220WZOEAZyfKZfgocgudbTuXimTA4afiikVJ2AVOwQls=9SZFAW7l"));
        /*	System.out.println(Des.encryptDES("root"));
            System.out.println(Des.encryptDES("123456"));
        	System.out.println(Des.encryptDES("jdbc:mysql://192.168.1.45:3306/jxdb?useUnicode=true&characterEncoding=utf-8"));*/
        	//Des.decryptDES(src);
        	System.out.println(Des.decryptDES("hv0wpU8vaBUioRDHxTrNTzOqpY7wAozqTEOxa14Ht2c=kUEr3b6O"));//解密
        	System.out.println(Des.encryptDES("jxuser"));
            System.out.println(Des.encryptDES("fDCGDFGfgZ!2^dgf*fGH#(RH"));
        	System.out.println(Des.encryptDES("jdbc:mysql://119.23.11.236:3306/jxdb?useUnicode=true&characterEncoding=utf-8"));
        	
        	System.out.println("------------------------------------------------");
        	
        	System.out.println(Des.encryptDES("root"));
            System.out.println(Des.encryptDES("123456"));
        	System.out.println(Des.encryptDES("jdbc:mysql://192.168.1.45:3306/jxcs1?useUnicode=true&characterEncoding=utf-8"));
        	System.out.println("-----------");
        	System.out.println(Des.decryptDES("4+Jja7wGN9IxaPwmK0/ZQ8oyfc4d4d4K8JsKx1t8wywF33a8nEaTqShTaf5q 1aCDDgLePK6LkOnRiefKRRbWO/SzEDXM9kEdpyV/oflUYZY=Bre2Lqjg"));
        	System.out.println(Des.decryptDES("EHfO3UlawFs=3O5SMGUq"));
        	System.out.println(Des.decryptDES("nE+hrJIjZ+Uux4CZ5VNOLe8PG0zl6QShs2j6UfxD7es=yGyMfxUD"));
        	
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
