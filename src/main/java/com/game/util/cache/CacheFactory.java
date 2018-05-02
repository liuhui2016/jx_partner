package com.game.util.cache;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheFactory {

	private static Logger logger = LoggerFactory.getLogger(CacheFactory.class);

	/**
	 * 缓存工厂实例
	 */
	private static CacheFactory factory = null;

	/**
	 * 用于保存缓存实现类对象的MAP
	 */
	private Map<String, BaseCache> map = null;

	/**
	 * 私有的构造方法
	 */
	private CacheFactory() {
		map = new HashMap<String, BaseCache>();
	}

	/**
	 * 取工厂实例
	 * 
	 * @return 实例
	 */
	public static CacheFactory newInstance() {
		if (factory == null) {
			synchronized (CacheFactory.class) {
				if (factory == null) {
					factory = new CacheFactory();
				}
			}
		}
		return factory;
	}

	/**
	 * 取缓存实现类对象 默认使用 OsCacheImpl和CacheConstant.OS_CACHE的值
	 */
	public BaseCache getCache() {
		return getCache(OsCacheImpl.class, CacheConstant.OS_CACHE);
	}

	/**
	 * 取缓存实现对象（默认使用OsCacheImpl实现）
	 * 
	 * @param osCachePropName
	 *            缓存配置文件
	 * @return
	 */
	public BaseCache getCache(String osCachePropName) {
		return getCache(OsCacheImpl.class, osCachePropName);
	}

	/**
	 * 获取Memcache
	 * 
	 * @return
	 */
	public BaseCache getMemCache() {
		return getCache(MemCacheImpl.class, CacheConstant.MEM_CACHE);
	}

	/**
	 * 取缓存实现类对象
	 * 
	 * @param cls
	 *            baseCache实现类 如果是NULL 默认使用 OsCacheImpl
	 * @param osCachePropName
	 *            cache 属性文件名称,使用CacheConstant接口的常量 如果是NULL
	 *            默认使用CacheConstant.OS_CACHE的值
	 */
	public BaseCache getCache(Class<? extends BaseCache> cls,
			String osCachePropName) {
		String key = cls.getName() + osCachePropName;
		// 从MAP中取出实现类实例
		BaseCache cache = (BaseCache) map.get(key);
		if (cache == null) {// 如果没有则实例化
			synchronized (map) {
				if(cache == null){
					try {
						osCachePropName += ".properties";
						InputStream in = Thread.currentThread().getContextClassLoader()
								.getResourceAsStream(osCachePropName);
						Properties prop = new Properties();
						prop.load(in);// 加载属性文件
						cache = (BaseCache) cls.newInstance();
						cache.setPropFile(prop);// 设置属性文件
						map.put(key, cache);// 保存实例到MAP中
					} catch (Exception e) {
						logger.error("获取缓存对象发生错误", e);
					}
				}
			}
		}
		return cache;

	}

}
