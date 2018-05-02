package com.game.smvc.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ConfigEngine implements IConfig {
	protected Logger logger = LoggerFactory.getLogger(ConfigEngine.class);
	protected Properties properties = new Properties();
	public static final String FILE_ENCODEING = "utf-8";
	public static final String COPY_RIGHT = "mrcms Power By marker. 2012-2014 @wuweiit";
	protected File cfgFile;

	public ConfigEngine(String cfgFilePath) {
		String file = null;
		try {
			file = ConfigEngine.class.getResource(cfgFilePath).toURI()
					.getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		read(new File(file));
	}

	public String get(String key) {
		return this.properties.getProperty(key);
	}

	public void set(String key, String val) {
		this.properties.put(key, val);
	}

	public Properties getProperties() {
		return this.properties;
	}

	public void read(File _cfgFile) {
		this.cfgFile = _cfgFile;
		FileInputStream in = null;
		InputStreamReader isr = null;
		try {
			in = new FileInputStream(_cfgFile);
			isr = new InputStreamReader(in, "utf-8");
			this.properties.load(isr);
		} catch (FileNotFoundException e) {
			this.logger.error(
					"config file not found " + this.cfgFile.getAbsolutePath(),
					e);
			try {
				if (isr != null) {
					isr.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e1) {
				this.logger.error(
						"close stream IOException "
								+ this.cfgFile.getAbsolutePath(), e1);
			}
		} catch (UnsupportedEncodingException e) {
			this.logger.error(this.cfgFile.getAbsolutePath()
					+ " not supported encoding " + "utf-8", e);
			try {
				if (isr != null) {
					isr.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e1) {
				this.logger.error(
						"close stream IOException "
								+ this.cfgFile.getAbsolutePath(), e1);
			}
		} catch (IOException e) {
			this.logger.error("IOException " + this.cfgFile.getAbsolutePath(),
					e);
			try {
				if (isr != null) {
					isr.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e1) {
				this.logger.error(
						"close stream IOException "
								+ this.cfgFile.getAbsolutePath(), e1);
			}
		} finally {
			try {
				if (isr != null) {
					isr.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				this.logger.error(
						"close stream IOException "
								+ this.cfgFile.getAbsolutePath(), e);
			}
		}
	}

	public void store() {
		OutputStream out = null;
		OutputStreamWriter osw = null;
		try {
			out = new FileOutputStream(this.cfgFile);
			osw = new OutputStreamWriter(out, "utf-8");
			this.properties.store(osw,
					"mrcms Power By marker. 2012-2014 @wuweiit");
		} catch (FileNotFoundException e) {
			this.logger.error(
					"config file not found " + this.cfgFile.getAbsolutePath(),
					e);
			try {
				if (osw != null) {
					osw.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e1) {
				this.logger.error(
						"close stream IOException "
								+ this.cfgFile.getAbsolutePath(), e1);
			}
		} catch (UnsupportedEncodingException e) {
			this.logger.error("not supported encoding utf-8", e);
			try {
				if (osw != null) {
					osw.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e1) {
				this.logger.error(
						"close stream IOException "
								+ this.cfgFile.getAbsolutePath(), e1);
			}
		} catch (IOException e) {
			this.logger.error("IOException " + this.cfgFile.getAbsolutePath(),
					e);
			try {
				if (osw != null) {
					osw.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e1) {
				this.logger.error(
						"close stream IOException "
								+ this.cfgFile.getAbsolutePath(), e1);
			}
		} finally {
			try {
				if (osw != null) {
					osw.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				this.logger.error(
						"close stream IOException "
								+ this.cfgFile.getAbsolutePath(), e);
			}
		}
	}
}
