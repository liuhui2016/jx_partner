package com.game.smvc.controller;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.smvc.entity.JxSpider;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.smvc.entity.result.SecretResult;
import com.game.smvc.service.IJxMerchantPublishService;
import com.game.smvc.service.IJxSpiderService;
import com.game.smvc.util.HttpUtil;
import com.game.spider.bean.LinkTypeData;
import com.game.spider.core.ExtractService;
import com.game.spider.rule.Rule;

@Controller
@RequestMapping({ "/smvc" })
public class SpiderController {

	@Autowired
	private IJxMerchantPublishService merchantPublish;
	@Autowired
	private IJxSpiderService jxSpiderService;
	
	/**
	 * 网络爬虫
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping({ "/webspider/spider" })
	public Result spider(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			int id = jsonObject.getInt("id");
			//int id = 0;
			Rule rule = new Rule("http://youku.com/dianshi",  
			            new String[] { "word" }, new String[] { "视频" },  
			            null, -1, Rule.GET);  
			List<LinkTypeData> list1 = ExtractService.extract(rule);
			printf(list1);
			List<Map<String, Object>> list = jxSpiderService.findurl(id);
			return new SecretResult(Errors.OK, list);
		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	public void printf(List<LinkTypeData> datas){
		for (LinkTypeData data : datas){
			JxSpider spider = new JxSpider();
			spider.setId(data.getId());
			spider.setJx_content(data.getContent());
			spider.setJx_linkhref(data.getLinkHref());
			spider.setJx_linktext(data.getLinkText());
			spider.setJx_summary(data.getSummary());
			jxSpiderService.save(spider);
		}

	}
	
	
	
	/**
	 * 视频娱乐
	 * @param request
	 * @return  
	 */
	@ResponseBody
	@RequestMapping(value = "/webspider/recreation")
	public Result Recreation(HttpServletRequest request) {
		try {
			System.out.println("---视频娱乐---");
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			String id = jsonObject.getString("id");
			int page = Integer.parseInt(jsonObject.getString("page"));
			
			List<Map<String, Object>> list = jxSpiderService.findRecreation(
					id, (page - 1) * 10);
			
			return new SecretResult(Errors.OK, list);
		} catch (JSONException e) {
			e.printStackTrace();
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}

		
		

	
	
	
	@ResponseBody
	@RequestMapping({ "/webspider/spiders" })
	public Result spiders(HttpServletRequest request) {
		try {
			String authCode = HttpUtil.getRquestParamsByIO(request);
			JSONObject jsonObject = JSONObject.fromObject(authCode);
			Document document = Jsoup.connect("http://video.eastday.com").get();
			Elements liElements1 = document.getElementsByClass("oUlPlay").get(0).getElementsByTag("a");
			 for (int j =0; j<liElements1.size(); j++) {
	        	  //System.out.println("内容："+j + ". " + liElements1.get(j).text());//内容
	        	  //System.out.println("地址1:"+liElements1.get(j).attr("abs:href"));//地址
	        	  String img = liElements1.select("img[src$=.jpg]").get(j).attr("abs:src");//图片地址
	        	  String titles = liElements1.select("img[src]").get(j).attr("alt");//标题
	        	  String s = liElements1.get(j).attr("abs:href");//视频解析前网址
	        	  //String contenr = liElements1.get(j).text(); 得到文本内容
	        	  Document document1 = Jsoup.connect(s).get();
	        	  Elements liElements = document1.getElementsByClass("video0").get(0).getElementsByTag("source");//得到视频地址
	        	  System.out.println("liElements:"+liElements.get(1).absUrl("src"));
	        	  String href = liElements.get(1).absUrl("src");
	        	  JxSpider jxSpider = new JxSpider();
	        	  jxSpider.setJx_content(titles);
	        	  jxSpider.setJx_linkhref(href);
	        	  jxSpider.setJx_linktext(img);
	        	  jxSpider.setId(0);
	        	  jxSpider.setJx_summary("1");
	        	  jxSpider.setAddtime(new Date());
	        	  jxSpiderService.save(jxSpider);
			}
			
			return new Result(Errors.OK);
		} catch (JSONException e) {
			return new Result(Errors.JSON_ERROR_NOTJSON);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(Errors.EXCEPTION_UNKNOW);
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		
		//http://video.eastday.com/vzixun.html
		//http://video.eastday.com/vgaoxiao.html
		/*Document document = Jsoup.connect("http://video.eastday.com/a/170628112752467620281.html").get();
		//ov phplist   no phplist   special_box  piclist  no phplist
		Elements liElements1 = document.getElementsByClass("special_list").get(0).getElementsByTag("a");
		Elements liElements10 = document.getElementsByClass("piclist pt20").get(0).getElementsByTag("a");
		Elements liElements2 = document.getElementsByClass("special_list").get(0).getElementsByTag("a");
		Elements liElements3 = document.getElementsByClass("edit_list").get(0).getElementsByTag("a");//ul1
		Elements liElements4 = document.getElementsByClass("J_video_list").get(0).getElementsByTag("a");
		System.out.println(document.toString());
		 for (int j =0; j<liElements1.size(); j++) {
        	  //System.out.println("内容："+j + ". " + liElements1.get(j).text());//内容
        	  //System.out.println("地址1:"+liElements1.get(j).attr("abs:href"));//地址
        	  String img = liElements1.select("img[src]").get(j).attr("abs:src");//图片地址
        	  String titles = liElements1.select("img[src]").get(j).attr("alt");//标题
        	  String s = liElements1.get(j).attr("abs:href");
        	  //String title = liElements1.get(j).attr("title");
        	  //String contenr = liElements1.get(j).text();
        	  Document document1 = Jsoup.connect(s).get();
        	  Elements liElements = document1.getElementsByClass("video0").get(0).getElementsByTag("source");//得到地址
        	  String href = liElements.get(1).absUrl("src");
        	  System.out.println("标题:"+titles);
        	  System.out.println("视频地址:"+href);
        	  System.out.println("图片地址:"+img);
        	 
		 }
		 */
			Document document = Jsoup.connect("http://video.eastday.com/vgaoxiao.html").get();
			//System.out.println(document.toString());
			Elements liElements1 = document.getElementsByClass("special_list").get(0).getElementsByTag("a");
			 for (int j =0; j<liElements1.size(); j++) {
	        	  //System.out.println("内容："+j + ". " + liElements1.get(j).text());//内容
	        	  //System.out.println("地址1:"+liElements1.get(j).attr("abs:href"));//地址
	        	  String img = liElements1.select("img[src]").get(j).attr("abs:src");//图片地址
	        	  String titles = liElements1.select("img[src]").get(j).attr("alt");//标题
	        	  String s = liElements1.get(j).attr("abs:href");
	        	  //String title = liElements1.get(j).attr("title");
	        	  //String contenr = liElements1.get(j).text();
	        	  Document document1 = Jsoup.connect(s).get();
	        	  Elements liElements = document1.getElementsByClass("video0").get(0).getElementsByTag("source");//得到地址
	        	  String href = liElements.get(1).absUrl("src");
	        	  System.out.println("标题:"+titles);
	        	  System.out.println("视频地址:"+href);
	        	  System.out.println("图片地址:"+img);
	        	 
	        	 
			 }
			 
	
	}
	
}
