package com.game.spider.util;  
/**
 * 爬去youku视频
 */
import java.io.IOException;  
import java.util.HashMap;
      
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;  
      
    public class parsePage {  
        /** 
         * @param args 
         */  
        public static void main(String[] args) {  
      
              
            String url="http://video.eastday.com/a/170627151930921635214.html";  
            //http://weibo.com/
            //http://www.youku.com
            //url="http://movie.youku.com";  
              
            HashMap list=new HashMap();  
              
            Document doc;  
            try {  
                doc = Jsoup.connect(url).get();  
                  
                Elements links = doc.select("a[href]");  
                  
                int s=0;  
                  
                for (Element link : links) {  
      
                    String v_url=link.attr("abs:href");  
                      
                    if  (link.ownText().length()==0)  
                    {  
                        continue;  
                    }  
      
                    if (list.containsKey(v_url))  
                    {  
                        continue;  
                    }  
                      
                    if (v_url.startsWith("http://video.eastday.com/a/170627151930921635214.html"))  
                    {  
                        System.out.println(link.attr("abs:href")+"-"+link.ownText());  
                        list.put(v_url, v_url);  
                        s++;  
                    }  
      
                }  
                System.out.println("total:"+ s);  
                  
                  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  

        }  
      
    }  

    