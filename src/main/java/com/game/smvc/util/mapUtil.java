package com.game.smvc.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 计算地图距离 的方法
 * @author Administrator
 *
 */
public class mapUtil {

/*	static double DEF_PI = 3.14159265359; // PI
    static double DEF_2PI= 6.28318530712; // 2*PI
    static double DEF_PI180= 0.01745329252; // PI/180.0
    static double DEF_R =6370693.5; // radius of earth
            //适用于近距离
    public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2)
    {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
        dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
        dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }
            //适用于远距离
    public static double GetLongDistance(double lon1, double lat1, double lon2, double lat2)
    {
        double ew1, ns1, ew2, ns2;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 求大圆劣弧与球心所夹的角(弧度)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
        // 调整到[-1..1]范围内，避免溢出
        if (distance > 1.0)
             distance = 1.0;
        else if (distance < -1.0)
              distance = -1.0;
        // 求大圆劣弧长度
        distance = DEF_R * Math.acos(distance);
        return distance;
    }*/
    
    
  
    
    
 /*   
    public static double getDistance(double lng1,double lat1,double lng2,double lat2){
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lng1) - Math.toRadians(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        return s;
    }*/
    /**
     * 计算TP值
     * @param curPoint      当前点
     * @param relatedPoint  偏移点
     * @param isGeography   是否是地理坐标 false为2d坐标
     * @return              tp值
     */
   /* public static double getDirAngle(Point curPoint,Point relatedPoint,boolean isGeography){
        double result = 0;
        if(isGeography){
            double y2 = Math.toRadians(relatedPoint.getLat());
            double y1 = Math.toRadians(curPoint.getLat());
            double alpha = Math.atan2(relatedPoint.getLat() - curPoint.getLat(), (relatedPoint.getLng() - curPoint.getLng()) * Math.cos((y2 - y1) / 2));//纬度方向乘以cos(y2-y1/2)
            double delta =alpha<0?(2*Math.PI+alpha):alpha;
            result = Math.toDegrees(delta);
        }else {
            double alpha = Math.atan2(relatedPoint.getLat() - curPoint.getLat(), relatedPoint.getLng() - curPoint.getLng());
            double delta=alpha<0?(2*Math.PI+alpha):alpha;
            result = Math.toDegrees(delta);
        }
        return result;
    }
   */
    
	  //单位m(比较精确)
	  public static String Distance(double long1, double lat1, double long2,  
	            double lat2) {  
	        double a, b, R;  
	        R = 6378.137; // 地球半径  
	        lat1 = lat1 * Math.PI / 180.0;  
	        lat2 = lat2 * Math.PI / 180.0;  
	        a = lat1 - lat2;  
	        b = (long1 - long2) * Math.PI / 180.0;  
	        double d;  
	        double sa2, sb2;  
	        sa2 = Math.sin(a / 2.0);  
	        sb2 = Math.sin(b / 2.0);  
	        d = 2  
	                * R  
	                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
	                        * Math.cos(lat2) * sb2 * sb2));
	        
	        d = d*1000;
	        String distanceStr = d+"";
	        distanceStr = distanceStr.
		            substring(0, distanceStr.indexOf("."));
	        return distanceStr;  
	    }  
    
	  
	  private static double EARTH_RADIUS = 6378.137; 
	   
	    private static double rad(double d) { 
	        return d * Math.PI / 180.0; 
	    }
	     
	    /**
	     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
	     * 参数为String类型
	     * @param lat1 用户经度
	     * @param lng1 用户纬度
	     * @param lat2 商家经度
	     * @param lng2 商家纬度
	     * @return
	     */
	    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
	        Double lat1 = Double.parseDouble(lat1Str);
	        Double lng1 = Double.parseDouble(lng1Str);
	        Double lat2 = Double.parseDouble(lat2Str);
	        Double lng2 = Double.parseDouble(lng2Str);
	         
	        double radLat1 = rad(lat1);
	        double radLat2 = rad(lat2);
	        double difference = radLat1 - radLat2;
	        double mdifference = rad(lng1) - rad(lng2);
	        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
	                + Math.cos(radLat1) * Math.cos(radLat2)
	                * Math.pow(Math.sin(mdifference / 2), 2)));
	        distance = distance * EARTH_RADIUS;
	        distance = Math.round(distance * 10000) / 10000;
	        String distanceStr = distance+"";
	        distanceStr = distanceStr.
	            substring(0, distanceStr.indexOf("."));
	         
	        return distanceStr;
	    }
	     
	    /**
	     * 获取当前用户一定距离以内的经纬度值
	     * 单位米 return minLat
	     * 最小经度 minLng
	     * 最小纬度 maxLat
	     * 最大经度 maxLng
	     * 最大纬度 minLat
	     */
	    public static Map getAround(String latStr, String lngStr, String raidus) {
	        Map map = new HashMap();
	         
	        Double latitude = Double.parseDouble(latStr);// 传值给经度
	        Double longitude = Double.parseDouble(lngStr);// 传值给纬度
	 
	        Double degree = (24901 * 1609) / 360.0; // 获取每度
	        double raidusMile = Double.parseDouble(raidus);
	         
	        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180))+"").replace("-", ""));
	        Double dpmLng = 1 / mpdLng;
	        Double radiusLng = dpmLng * raidusMile;
	        //获取最小经度
	        Double minLat = longitude - radiusLng;
	        // 获取最大经度
	        Double maxLat = longitude + radiusLng;
	         
	        Double dpmLat = 1 / degree;
	        Double radiusLat = dpmLat * raidusMile;
	        // 获取最小纬度
	        Double minLng = latitude - radiusLat;
	        // 获取最大纬度
	        Double maxLng = latitude + radiusLat;
	         
	        map.put("minLat", minLat+"");
	        map.put("maxLat", maxLat+"");
	        map.put("minLng", minLng+"");
	        map.put("maxLng", maxLng+"");
	         
	        return map;
	    }
	     
	   
	  
    public static void main(String[] args) {
    	System.out.println(mapUtil.getAround("117.11811", "36.68484", "10400"));
       /* double mLat1 = 39.90923; // point1纬度
        double mLon1 = 116.357428; // point1经度
        double mLat2 = 39.90923;// point2纬度
        double mLon2 = 116.397428;// point2经度
        double distance = mapUtil.GetShortDistance(mLon1, mLat1, mLon2, mLat2);
        System.out.println(distance);*/
    	System.out.println(mapUtil.getDistance("117.11811","36.68484","117.00999000000002","36.66123"));
       // System.out.println("地图距离："+mapUtil.Distance(long1, lat1, long2, lat2));
        System.out.println("11:"+mapUtil.Distance(117.11811,36.68484,117.00999000000002,36.66123));
    }
    
    
    
}
