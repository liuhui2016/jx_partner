package com.game.spider.core;

import java.io.File;
import java.io.IOException;
 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.game.smvc.entity.JxSpider;
import com.game.smvc.service.IJxSpiderService;
import com.game.smvc.service.impl.JxSpiderServiceImpl;

public class ParseDocument {
	@Autowired
	private IJxSpiderService jxSpiderService;

	/**
    * 将String转换成Document
    * @return org.jsoup.nodes.Document
    */
   public static Document parseHtmlFromString(){
       String html = "<html><head><title>标题</title></head>"
               + "<body><p>段落</p></body></html>";
       Document doc = Jsoup.parse(html);
       return doc;
   }
    
   /**
    * 注意：这是一个不安全的方法
    * 将String转换成Html片段,注意防止跨站脚本攻击
    * @return Element
    */
   public static Element parseHtmlFragmentFromStringNotSafe(){
       String html = "<div><p>Lorem ipsum.</p>";
       Document doc = Jsoup.parseBodyFragment(html);
       Element body = doc.body();
       return body;
   }
    
   /**
    * 这是一个安全的方法
    * 将String转换成Html片段,注意防止跨站脚本攻击
    * @return Element
    */
   public static Element parseHtmlFragmentFromStringSafe(){
       String html = "<div><p>Lorem ipsum.</p>";
       //白名单列表定义了哪些元素和属性可以通过清洁器，其他的元素和属性一律移除
       Whitelist wl=new Whitelist();
       //比较松散的过滤，包括
       //"a", "b", "blockquote", "br", "caption", "cite", "code", "col",
       //"colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
       //"i", "img", "li", "ol", "p", "pre", "q", "small", "strike", "strong",
       //"sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
       //"ul"
       Whitelist.relaxed();
       //没有任何标签，只有文本
       Whitelist.none();
       //常规的过滤器
       //"a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em",
       //"i", "li", "ol", "p", "pre", "q", "small", "strike", "strong", "sub",
       //"sup", "u", "ul"
       Whitelist.basic();
       //常规的过滤器，多了一个img标签
       Whitelist.basicWithImages();
       //文本类型的标签
       //"b", "em", "i", "strong", "u"
       Whitelist.simpleText();
       //另外还可以自定义过滤规则,例如
       wl.addTags("a");
       //执行过滤
       Jsoup.clean(html, wl);
       Document doc = Jsoup.parseBodyFragment(html);
       Element body = doc.body();
       return body;
   }
    
   /**
    * 从URL加载
    * @return Document
    */
   public static Document parseDocumentFromUrl(){
       Document doc = null;
       try {
           doc = Jsoup.connect("http://video.eastday.com/a/170609174407244904661.html").get();
           Elements es = doc.getElementsByAttributeValue("class","video0").first().getElementsByTag("source");
           //Elements es = doc.getElementsByAttributeValue("class","video0").after().
           Element content = doc.getElementById("example0");
           System.out.println(content);
           //String author = ele.text();
          // sb.append(author + " ");
           
          /* StringBuilder sb = new StringBuilder();
           for (Element element : es) {
               Element ele  = element.getElementsByAttributeValue("id","example0_html5_api").first();
               //String author = ele.text();
               Element link = element.getElementsByTag("video").first();
               String href = link.attr("src");
               String name = link.text();
              // sb.append(author + " ");
               sb.append(name+" "+href).append("\n");
               System.out.println("href:"+href);
           }*/
   		  /* String r=" href='(.mp4)' ";
   		   Elements content1 = doc.select(r);*/
           //var mp4 = "//mv.eastday.com/vzixun/20170609/20170609174407244904661_1_06400360.mp4";
           //获取标题
           String title = doc.title();
          // String url = doc.absUrl(r);
           System.out.println(title);//输出：Google
          // System.out.println(url);
           //Elements newsHeadlines = doc.select("#mp-itn b a");//http.*\.mp4
           Elements newsHeadlines = doc.select("#mp-itn b a");
           System.out.println("视频地址:"+es);
           //data(key,value)是该URL要求的参数
           //userAgent制定用户使用的代理类型
           //cookie带上cookie，如cookie("JSESSIONID","FDE234242342342423432432")
           //连接超时时间
           //post或者get方法
        /*   doc = Jsoup.connect("http://www.hao123.com/")
                     .data("query", "mp4")
                     .userAgent("Mozilla")
                     .cookie("auth", "token")
                     .timeout(3000)
                     .post();*/
            
       } catch (IOException e) {
           e.printStackTrace();
       }
       return doc;
   }
   /**
    * 从文件加载
    * @return Document
    */
   public static Document parseDocumentFromFile(){
       File input = new File("/tmp/input.html");
       Document doc=null;
       try {
           //从文件加载Document文档
           doc = Jsoup.parse(input, "UTF-8");
           System.out.println(doc.title());
       } catch (IOException e) {
           e.printStackTrace();
       }
       return doc;
   }
   
   private static int COUNT = 0;
   private static int DOWN_COUNT = 0;
   public static void jsoupHTML(String urlPath) throws Exception{
       Document doc = Jsoup.connect(urlPath).timeout(1000000).get();
       //:当前页中的视频
       Elements srcLinks = doc.select("video[src$=.mp4]");
       for (Element link : srcLinks) {
           //:剔除标签，只剩链接路径
           String imagesPath = link.attr("src");
           System.out.println("当前访问路径:"+imagesPath);
           //getImages(imagesPath, "d://images//0000"+ ++COUNT +".jpg");
       }
        
       //:提取网站中所有的href连接
       Elements linehrefs = doc.select("a[href]");
        
       for (Element linehref : linehrefs) {
           String lihr = linehref.attr("href");
           if(lihr.length()>4){
               String ht = lihr.substring(0, 4);
               String htt = lihr.substring(0, 1);
               if(!ht.equals("http") && htt.equals("/")){
                   lihr = urlPath + lihr;
               }
               if(lihr.substring(0, 4).equals("http")){
                   Document docs = Jsoup.connect(lihr).timeout(1000000).get();
                   Elements links = docs.select("video[src$=.mp4]");
                   for (Element link : links) {
                       //:剔除标签，只剩链接路径
                       String imagesPath = link.attr("src");
                       System.out.println("当前访问路径:"+imagesPath);
                       //getImages(imagesPath, "d://images//0000"+ COUNT++ +".jpg");
                   }
               }
           }
       }
   }
   
   /** 
    * 根据jsoup方法获取htmlContent 
           * 加入简单的时间记录 
    * @throws IOException  
    */  
   public static String getContentByJsoup(String url){  
       String content="";  
       try {  
           System.out.println("time=====start");  
           Date startdate=new Date();  
           Document doc=Jsoup.connect(url)  
           .data("jquery", "java")  
           .userAgent("Mozilla")  
           .cookie("auth", "token")  
           .timeout(50000)  
           .get();  
           Date enddate=new Date();  
           Long time=enddate.getTime()-startdate.getTime();  
           System.out.println("使用Jsoup耗时=="+time);  
           System.out.println("time=====end");  
           content=doc.toString();//获取iteye网站的源码html内容  
           System.out.println(doc.title());//获取iteye网站的标题  
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
       System.out.println(content);   
       return content;  
   }  
   
   /** 
* 使用jsoup来对文档分析 
    * 获取目标内容所在的目标层 
    * 这个目标层可以是div，table，tr等等 
*/  
public static String getDivContentByJsoup(String content){  
String divContent="";  
Document doc=Jsoup.parse(content);  
Elements divs=doc.getElementsByClass("main_left");  
divContent=divs.toString();  
  //System.out.println("div==="+divContent);  
return divContent;  
} 
   
   /** 
    * 使用jsoup分析divContent 
    * 1.获取链接 2.获取url地址（绝对路径） 
    */  
   public static void getLinksByJsoup(String divContent){  
       String abs="http://video.eastday.com/a/170620201908876523367.html?indextt";  
       Document doc=Jsoup.parse(divContent,abs);  
       Elements linkStrs=doc.select("source[src$=.mp4]");  
       System.out.println("链接==="+linkStrs.size());  
       for(Element linkStr:linkStrs){  
           String url=linkStr.getElementsByTag("a").attr("abs:href");  
           String title=linkStr.getElementsByTag("a").text();  
           System.out.println("标题:"+title+" url:"+url);  
       }  
   }  


   public static void main(String[] args) throws Exception {
	  /* String url="http://www.hao123.com/";  
//	   String HtmlContent=getContentByJsoup(url);  
//       String divContent=getDivContentByJsoup(HtmlContent);  
//       getLinksByJsoup(divContent);  
	  // ParseDocument.parseDocumentFromUrl();
	   //String url = "http://video.eastday.com";
	  
	   System.out.println( ParseDocument.parseDocumentFromUrl());
	   System.out.println("1");*/
	   
	   try {
           //解析Url获取Document对象
		   //http://video.eastday.com/a/170626141723987665199.html?indexzx
		   //http://video.eastday.com/a/170622023156792550384.html?qid=firefoxshipin
           Document document = Jsoup.connect("http://video.eastday.com").get();
           System.out.println(document.toString());
           Elements liElements1 = document.getElementsByClass("oUlPlay").get(0).getElementsByTag("a");
           Elements liElements2 = document.getElementsByClass("oUlPlay").get(0).getElementsByTag("li");
           System.out.println("liElements2:"+liElements2);
           for (Element element : liElements2) {
        	   String img = element.attr("img");
        	   System.out.println("img:"+img);
		}
          /* Elements srcLinks = document.select("img[src$=.jpg]");//获取图片
           Elements videoLinks = document.select("a");//获取视频
           System.out.println("视频链接:"+videoLinks);
           for (Element element : videoLinks) {
        	   String imagesPath1 = element.attr("abs:href");
        	   System.out.println("视频链接:"+imagesPath1);
           }
           */
           //获取图片链接
         /*  for (Element element : srcLinks) {
        	   String imagesPath = element.attr("src");
        	   System.out.println("图片链接:"+imagesPath);
           }*/
           String s1 = liElements1.select("img[src$=.jpg]").get(0).attr("abs:src");
           System.out.println("tup:"+s1);
           
           for(int a =0; a<liElements2.size();a++){
        	   
        	   System.out.println("图片1:"+liElements1.get(1).attr("src"));
           }
           //System.out.println("图片:"+liElements1.get(0).absUrl("src"));
           System.out.println("图片:"+liElements2);
           for (int j =0; j<liElements1.size(); j++) {
        	   System.out.println("内容："+j + ". " + liElements1.get(j).text());//内容
        	   //System.out.println("地址:"+liElements1.get(1).absUrl("href"));//地址
        	  System.out.println("地址:"+liElements1.get(j).attr("abs:href"));//地址
        	  System.out.println("图片:"+liElements1.get(j).attr("src"));//图片
        	  String s2 = liElements1.select("img[src$=.jpg]").get(j).attr("abs:src");
        	  String s = liElements1.get(j).attr("abs:href");
        	  System.out.println("s:"+s);
        	  System.out.println("s2:"+s2);
        	  Document document1 = Jsoup.connect(s).get();
        	  Elements liElements = document1.getElementsByClass("video0").get(0).getElementsByTag("source");//得到地址
        	  System.out.println("liElements:"+liElements.get(1).absUrl("src"));
		}
           
           //获取网页源码文本内容
           //System.out.println(document.toString());
           //获取指定class的内容指定tag的元素
           Elements liElements = document.getElementsByClass("video0").get(0).getElementsByTag("source");//得到地址
           System.out.println("1");
           System.out.println("liElements:"+liElements.get(1).absUrl("src"));
           String s = liElements.get(1).absUrl("src");
           
           
           JxSpider jxSpider = new JxSpider();
           jxSpider.setJx_linkhref(s);
           
           
           System.out.println("2");
           for (int i = 0; i < liElements.size(); i++) {
               System.out.println("地址："+i + ". " + liElements.get(1).text());
               System.out.println(liElements.get(1).absUrl("src"));
               
           }
       } catch (IOException e) {
           System.out.println("解析出错！");
           e.printStackTrace();
       }
   }
	    
   
    
}
