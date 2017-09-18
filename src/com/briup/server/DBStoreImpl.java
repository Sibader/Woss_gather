package com.briup.server;

import java.util.Collection;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;

import com.briup.common.Configuration;
import com.briup.common.LoggerImpl;
import com.briup.mapper.BIDRMapper;
import com.briup.util.BIDR;
import com.briup.utils.Dom4jReader;
import com.briup.utils.MybatisSessionUtil;
import com.briup.woss.server.DBStore;
/**
 * 执行入库操作
 */
public class DBStoreImpl implements DBStore{

	@Override
	public void init(Properties pro) {		
	}
	/*
	 *入库 
	 */
	@Override
	public void saveToDB(Collection<BIDR> arg0) throws Exception {		
		SqlSession sqlSession = MybatisSessionUtil.openSession(true);
		BIDRMapper bidrMapper = sqlSession.getMapper(BIDRMapper.class);
		for(BIDR bidr: arg0){
			Integer day =bidr.getLogin_date().getDate();
			bidrMapper.addBIDR(day,bidr);
		}
		new Configuration().getLogger().warn("入库完成");
	}
	public void deleteByDay(int day) throws Exception{
		SqlSession sqlSession = MybatisSessionUtil.openSession(true);
		BIDRMapper bidrMapper = sqlSession.getMapper(BIDRMapper.class);
		bidrMapper.deleteByDay(day,0);
		new Configuration().getLogger().warn("删除成功！");
	}
}
