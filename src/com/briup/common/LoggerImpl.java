package com.briup.common;

import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.briup.utils.Dom4jReader;

/**
 * 日志模块
 */
public class LoggerImpl implements com.briup.util.Logger{
	
	private static String path = null;

	public LoggerImpl() {
		init(new Properties());
		PropertyConfigurator.configure(path);				
	}
	@Override
	public void init(Properties pro) {
		try {
			String[] loggerInfo = Dom4jReader.loggerInfo();
			pro.setProperty("path", loggerInfo[1]);
			Class<?> clazz = Class.forName(loggerInfo[0]);
			Field field = clazz.getDeclaredField("path");
			field.setAccessible(true);
			field.set(this, pro.getProperty("path"));			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void debug(String arg0) {
		Logger.getLogger("mylogger").debug(arg0);
		
	}

	@Override
	public void error(String arg0) {
		Logger.getLogger("mylogger").error(arg0);
	}

	@Override
	public void fatal(String arg0) {
		Logger.getLogger("mylogger").fatal(arg0);
	}

	@Override
	public void info(String arg0) {
		Logger.getLogger("mylogger").info(arg0);
	}

	@Override
	public void warn(String arg0) {
		Logger.getLogger("mylogger").warn(arg0);
	}

}
