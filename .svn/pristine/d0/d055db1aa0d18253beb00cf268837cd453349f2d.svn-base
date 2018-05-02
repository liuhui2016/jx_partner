package com.game.smvc.core.config;


public final class CustomConfig extends ConfigEngine {
	public static final String CONFIG_FILE_PATH = "/config.properties";

	private CustomConfig() {
		super("/config.properties");
	}

	public static abstract interface Names {
		public static final String KEYWORDS = "keywords";
		public static final String DESCRIPTION = "description";
	}

	private static class SingletonHolder {
		public static final CustomConfig instance = new CustomConfig();
	}

	public static CustomConfig getInstance() {
		return SingletonHolder.instance;
	}

	public String getSysProp(String key) {
		return this.properties.getProperty(key);
	}
}
