package com.game.smvc.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_news")
public class JxNews {

	private int news_id;//新闻id
	private String news_title;//新闻标题
	private String news_content;//新闻内容
	private int news_type;//新闻类型
	private int news_stick;//新闻是否置顶0置顶1不置顶
	private int news_num;//新闻点击次数
	private String news_url;
	private java.util.Date news_addtime;
	private java.util.Date news_modtime;
	//private List<JxNewsItem> jx_news_itme;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getNews_id() {
		return news_id;
	}
	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}
	public String getNews_title() {
		return news_title;
	}
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	public String getNews_content() {
		return news_content;
	}
	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}
	public int getNews_type() {
		return news_type;
	}
	public void setNews_type(int news_type) {
		this.news_type = news_type;
	}
	public int getNews_stick() {
		return news_stick;
	}
	public void setNews_stick(int news_stick) {
		this.news_stick = news_stick;
	}
	public int getNews_num() {
		return news_num;
	}
	public void setNews_num(int news_num) {
		this.news_num = news_num;
	}
	public java.util.Date getNews_addtime() {
		return news_addtime;
	}
	public void setNews_addtime(java.util.Date news_addtime) {
		this.news_addtime = news_addtime;
	}
	public java.util.Date getNews_modtime() {
		return news_modtime;
	}
	public void setNews_modtime(java.util.Date news_modtime) {
		this.news_modtime = news_modtime;
	}
	public String getNews_url() {
		return news_url;
	}
	public void setNews_url(String news_url) {
		this.news_url = news_url;
	}
	
	
	
	
}
