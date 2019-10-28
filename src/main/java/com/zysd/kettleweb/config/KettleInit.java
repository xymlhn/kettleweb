package com.zysd.kettleweb.config;

import lombok.extern.java.Log;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.i18n.BaseMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 初始化环境
 */
@Log
public class KettleInit implements InitializingBean {
	@Value(value = "${spring.pluginPath}")
	private String pluginPath;

	@Value(value = "${spring.jdbcPath}")
	private String jdbcPath;

	@Override
	public void afterPropertiesSet() {
		try {
			//插件配置
			System.setProperty("KETTLE_PLUGIN_BASE_FOLDERS", pluginPath);
			//jndi配置
			Const.JNDI_DIRECTORY = jdbcPath;
			System.setProperty( "java.naming.factory.initial", "org.osjava.sj.SimpleContextFactory" );
			System.setProperty( "org.osjava.sj.root", jdbcPath);
			System.setProperty( "org.osjava.sj.delimiter", "/" );

			System.setProperty( "MAIL_PASSWORD", "ucg_123456" );
			System.setProperty( "MAIL_PORT", "25" );
			System.setProperty( "MAIL_SENDER", "system@join-share.com" );
			System.setProperty( "MAIL_HOST", "smtp.chengmail.cn" );
			System.setProperty( "MAIL_ADDRESSEE", "luoxianze@join-share.com" );
			System.setProperty( "MAIL_USERNAME", "system@join-share.com" );



			KettleEnvironment.init(true);
			log.info("Kettle环境初始化成功");
		}catch (Exception e){
			log.info("Kettle环境初始化失败:"+e.getMessage());
		}
	}

}
