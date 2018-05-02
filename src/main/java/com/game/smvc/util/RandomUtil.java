package com.game.smvc.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RandomUtil {

	public static String getOutNumberNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
	
	
	public static final String numberChar = "0123456789";
	 
    /**
     * 根据系统时间获得指定位数的随机数
     * 
     * @return 获得的随机数
     */
    public static String getRandom() {
    	
    	 SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",Locale.getDefault());
	     Date date = new Date();
	     String key = format.format(date);
 
        Long seed = System.currentTimeMillis();// 获得系统时间，作为生成随机数的种子
 
        StringBuffer sb = new StringBuffer();// 装载生成的随机数
 
        Random random = new Random(seed);// 调用种子生成随机数
 
        for (int i = 0; i < 5; i++) {
 
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        
        return sb.toString()+key;
 
    }
    private final static String NUM_CHAR = "0123456789"; 
    private static int charLen = NUM_CHAR.length(); 
    public static String getRandomNumber() { 
    	 SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",Locale.getDefault());
	     Date date = new Date();
	     String key = format.format(date);
        long seed = System.currentTimeMillis();// 获得系统时间，作为生成随机数的种子 
        StringBuffer sb = new StringBuffer();// 装载生成的随机数 
        Random random = new Random(seed);// 调用种子生成随机数 
        for (int i = 0; i < 5; i++) { 
           sb.append(NUM_CHAR.charAt(random.nextInt(charLen))); 
        } 


        return sb.toString()+key; 
     } 

    
    public static String getRandoms() {
    	
   	 SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss",Locale.getDefault());
	     Date date = new Date();
	     String key = format.format(date);

       Long seed = System.currentTimeMillis();// 获得系统时间，作为生成随机数的种子

       StringBuffer sb = new StringBuffer();// 装载生成的随机数

       Random random = new Random(seed);// 调用种子生成随机数

       for (int i = 0; i < 2; i++) {

           sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
       }
       
       return "C"+key+sb.toString();

   }
    
    public static void main(String[] args) {
		System.out.println(getRandoms());
	}
   
}
