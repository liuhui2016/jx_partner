package com.game.util.cache;

import java.util.Date;
import java.util.Properties;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.opensymphony.oscache.web.filter.ExpiresRefreshPolicy;


public class OsCacheImpl implements BaseCache {

	/**
	 * 缓存对象
	 */
	private GeneralCacheAdministrator cache = null;

	/**
	 * 销毁缓存对象
	 */
	public void destory() {
		cache.destroy();
	}

	/**
	 * 从缓存中删除所有对象
	 */
	public void flushAll() {
		cache.flushAll();

	}

	/**
	 * 从缓存中删除指定日期内的对象
	 * 
	 * @param date
	 *            日期
	 */
	public void flushAll(Date date) {
		cache.flushAll(date);

	}

	/**
	 * 根据key删除指定的对象
	 * 
	 * @param key
	 *            对象key
	 */
	public void flushEntry(String key) {
		cache.flushEntry(key);

	}
	/**
	 * 删除
	 * @param key
	 */
	public void removeEntry(String key){
		cache.removeEntry(key);
	}

	/**
	 * 根据key从缓存中取出对象
	 * 
	 * @param key
	 *            对象key
	 * @return 对象
	 */
	public Object getFromCache(String key) {
		Object content = null;
		try {
			content = cache.getFromCache(key);
		} catch (NeedsRefreshException e) {
			cache.cancelUpdate(key);
		}
		return content;

	}

	/**
	 * 根据key从缓存中取出对象
	 * 
	 * @param key
	 *            对象key
	 * @param refreshPeriod
	 *            在缓存中存在的时长(秒)
	 * @return 对象
	 */
	public Object getFromCache(String key, int refreshPeriod) {
		Object content = null;
		try {
			content = cache.getFromCache(key, refreshPeriod);
		} catch (NeedsRefreshException e) {
			//cache.putInCache(key, content);// 防止线程阻塞
			cache.cancelUpdate(key);
		}
		return content;
	}

	/**
	 * 把对象放到缓存中
	 * 
	 * @param key
	 *            对象key
	 * @param content
	 *            对象
	 */
	public void putIntoCache(String key, Object content) {
		cache.putInCache(key, content);
	}

	/**
	 * 把对象放到缓存中
	 * 
	 * @param key
	 *            对象KEY
	 * @param content
	 *            对象
	 * @param refreshPeriod
	 *            保存时间 the refresh period in seconds
	 */
	public void putIntoCache(String key, Object content, int refreshPeriod) {
		cache.putInCache(key, content, new ExpiresRefreshPolicy(refreshPeriod));
	}

	/**
	 * 设置属性文件
	 */
	public void setPropFile(Properties prop) {
		if (prop == null) {
			cache = new GeneralCacheAdministrator();
		} else {
			cache = new GeneralCacheAdministrator(prop);
		}

	}
}
