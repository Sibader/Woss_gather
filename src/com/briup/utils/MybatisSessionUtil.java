package com.briup.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisSessionUtil {
	private static SqlSessionFactory sqlSessionFactory;
	private static InputStream is;

	public static SqlSessionFactory getSqlSessionFactory() {
		if (sqlSessionFactory == null) {
			try {
				is = Resources.getResourceAsStream("mybatis-config.xml");
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sqlSessionFactory;
	}

	// 不自动提交事务
	public static SqlSession openSession() {
		return openSession(false);
	}

	// 自动提交事务
	public static SqlSession openSession(boolean autoCommit) {
		return getSqlSessionFactory().openSession(autoCommit);
	}
}
