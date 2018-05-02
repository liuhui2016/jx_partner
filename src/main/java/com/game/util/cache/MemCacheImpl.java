package com.game.util.cache;

import java.util.Date;
import java.util.Properties;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemCacheImpl implements BaseCache {

	private static boolean init = false;
	private static final String KEY_PREFIX = "care_cloud";
	
	MemCachedClient client=new MemCachedClient();  
	
	
	@Override
	public Object getFromCache(String key) {
		
		return client.get(KEY_PREFIX+key);
	}

	@Override
	public Object getFromCache(String key, int refreshPeriod) {
		
		return client.get(KEY_PREFIX+key);
		
	}

	@Override
	public void putIntoCache(String key, Object content) {
		
		client.set(KEY_PREFIX+key, content);
		
	}

	@Override
	public void putIntoCache(String key, Object content, int refreshPeriod) {
		
		long now  = System.currentTimeMillis();
        Date date=new Date(now+refreshPeriod);  
		client.set(KEY_PREFIX+key, content, date);
		
	}

	@Override
	public void flushAll() {
		
		client.flushAll();
		
	}

	@Override
	public void flushAll(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flushEntry(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEntry(String key) {
	
		client.delete(KEY_PREFIX+key);
		
	}

	@Override
	public void destory() {
		
		
	}

	@Override
	public void setPropFile(Properties prop) {
		
		if(!init)
		{
			SockIOPool pool = SockIOPool.getInstance();  
			String servers = prop.getProperty("memcache_servers");
			int initConn = Integer.parseInt(prop.getProperty("memcache_initconn"));
			int minConn = Integer.parseInt(prop.getProperty("memcache_minconn"));
			int maxConn = Integer.parseInt(prop.getProperty("memcache_maxconn"));
			int maxIdle = Integer.parseInt(prop.getProperty("memcache_maxidle"));
			int maintSleep = Integer.parseInt(prop.getProperty("memcache_maintsleep"));
			String [] addr ={servers};  
		    Integer [] weights = {3};  
		    pool.setServers(addr);  
		    pool.setWeights(weights);  
		    pool.setInitConn(initConn);  
		    pool.setMinConn(minConn);  
		    pool.setMaxConn(maxConn);  
		    pool.setMaxIdle(maxIdle);  
		    pool.setMaintSleep(maintSleep);  
		    pool.setNagle(false);  
		    pool.setSocketTO(30);  
		    pool.setSocketConnectTO(0);  
			pool.initialize();    
			init = true;
		}
	}

}
