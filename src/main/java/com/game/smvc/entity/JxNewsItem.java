package com.game.smvc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jx_news_item")
public class JxNewsItem {

	private int news_item_id;//新闻子id
	private int news_id;//新闻id
	private String news_title;//新闻标题
	private String news_content;//新闻内容
	private int news_item_type;//新闻子类型
	private java.util.Date news_addtime;
	private java.util.Date news_modtime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getNews_item_id() {
		return news_item_id;
	}
	public void setNews_item_id(int news_item_id) {
		this.news_item_id = news_item_id;
	}
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
	public int getNews_item_type() {
		return news_item_type;
	}
	public void setNews_item_type(int news_item_type) {
		this.news_item_type = news_item_type;
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
	
	
}
