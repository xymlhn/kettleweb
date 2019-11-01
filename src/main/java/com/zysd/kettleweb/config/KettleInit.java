package com.zysd.kettleweb.config;

import com.zysd.kettleweb.beans.BaseEnum;
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
	@Value(value = "${kettle.pluginPath}")
	private String pluginPath;

	@Value(value = "${kettle.jdbcPath}")
	private String jdbcPath;

	@Value(value = "${mail.password}")
	private String mailPassword;
	@Value(value = "${mail.port}")
	private String mailPort;
	@Value(value = "${mail.sender}")
	private String mailSender;
	@Value(value = "${mail.host}")
	private String mailHost;
	@Value(value = "${mail.address}")
	private String mailAddress;
	@Value(value = "${mail.username}")
	private String mailUsername;

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

			//邮件配置
			System.setProperty( "MAIL_USERNAME", mailUsername );
			System.setProperty( "MAIL_PASSWORD", mailPassword );
			System.setProperty( "MAIL_ADDRESSEE", mailAddress );
			System.setProperty( "MAIL_PORT", mailPort );
			System.setProperty( "MAIL_SENDER", mailSender );
			System.setProperty( "MAIL_HOST", mailHost );

			System.setProperty( "filter", BaseEnum.NULL_STRING);
			System.setProperty( "order", BaseEnum.NULL_STRING);

			KettleEnvironment.init(true);
			log.info("Kettle环境初始化成功");
		}catch (Exception e){
			log.info("Kettle环境初始化失败:"+e.getMessage());
		}
	}

}
