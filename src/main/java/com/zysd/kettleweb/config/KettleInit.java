package com.zysd.kettleweb.config;

import org.pentaho.di.core.KettleEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * 初始化环境
 */
public class KettleInit implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(KettleInit.class);

	@Value(value = "${spring.pluginPath}")
	private String pluginPath;

	@Value(value = "${spring.home}")
	private String home;

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			System.setProperty("KETTLE_PLUGIN_BASE_FOLDERS", pluginPath);
			System.setProperty("KETTLE_HOME",home);
			KettleEnvironment.init();
			logger.info("Kettle环境初始化成功");
		}catch (Exception e){
			e.printStackTrace();
			logger.info("Kettle环境初始化失败");
		}
	}

}
