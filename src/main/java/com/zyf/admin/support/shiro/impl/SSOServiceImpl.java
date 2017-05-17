package com.zyf.admin.support.shiro.impl;

import com.zyf.admin.support.shiro.SSOService;
import com.zyf.admin.support.shiro.entity.SSOConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class SSOServiceImpl implements SSOService {

	// 待用变量
	private Properties prop;
	private SSOConfig ssoConfig;

	// 注入变量
	private String properties;

	public SSOServiceImpl() {
	}

	@Override
	public void loadProperties() {
		if (properties == null || "".equals(properties)) {
			log.error("sso service impl properties is null");
			return;
		}
		try {
			if (properties.indexOf("classpath") >= 0) {
				String[] file = properties.split(":");
				if (file.length == 2) {
					InputStream in = SSOServiceImpl.class.getClassLoader().getResourceAsStream(file[1]);
					prop = this.getInputStream(in);
				}
			} else {
				InputStream in = SSOServiceImpl.class.getClassLoader().getResourceAsStream(properties);
				prop = this.getInputStream(in);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}

	@Override
	public void init() {
		if (prop == null) {
			log.error("sso service impl prop is null");
			return;
		}

		String key = prop.getProperty("sso.key");
		String cookiesName = prop.getProperty("sso.cookies.name");
		String encrypt = prop.getProperty("sso.encrypt");
		String domain = prop.getProperty("sso.domain");
		String path = prop.getProperty("sso.path");
		int maxAge = Integer.parseInt(prop.get("sso.max.age").toString());

		boolean checkIp = Boolean.parseBoolean(prop.get("sso.check.ip").toString()) ;
		boolean checkBrowser = Boolean.parseBoolean(prop.get("sso.check.browser").toString());

		SSOConfig config = getConfig();
		config.setKey(key);
		config.setCookiesName(cookiesName);
		config.setEncrypt(encrypt);
		config.setDomain(domain);
		config.setPath(path);
		config.setCheckIp(checkIp);
		config.setCheckBrowser(checkBrowser);
		config.setMaxAge(maxAge);
	}

	@Override
	public SSOConfig getConfig() {
		if (ssoConfig == null) {
			SSOConfig config = new SSOConfig();
			config.setDefaultConfig(config);
			return config;
		}
		return ssoConfig;
	}

	private Properties getInputStream(String filePath) throws FileNotFoundException {
		return getInputStream(new FileInputStream(new File(filePath)));
	}

	private Properties getInputStream(InputStream inputStream) {
		Properties p = null;
		try {
			p = new Properties();
			p.load(inputStream);
		} catch (Exception var4) {
			log.error(" Properties read file path error. \n" + var4.toString());
		}
		return p;
	}


	public void setProperties(String properties) {
		this.properties = properties;
	}

}
