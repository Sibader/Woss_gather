package com.briup.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.briup.common.BackUpImpl;
import com.briup.common.Configuration;
import com.briup.common.LoggerImpl;
import com.briup.util.BIDR;
import com.briup.utils.Dom4jReader;
import com.briup.woss.client.Gather;

/**
 * 完成数据采集，封装成一个装有BIDR对象的集合
 */
public class GatherImpl implements Gather {

	private FileReader reader = null;
	private BufferedReader br = null;
	private Map<String, BIDR> maps = null;
	private String pathName = null;

	public GatherImpl() {
		init(new Properties());
	}
	@Override
	public void init(Properties pro) {
		try {
			String[] gatherInfo = Dom4jReader.gatherInfo();
			pro.setProperty("src_file", gatherInfo[2]);	
			Class<?> clazz =  Class.forName(gatherInfo[0]);
			Field field = clazz.getDeclaredField("pathName");
			field.setAccessible(true);
			field.set(this, pro.getProperty("src_file"));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	/*
	 * 采集BIDR对象的集合
	 */
	@Override
	public Collection<BIDR> gather() throws Exception {
		reader = new FileReader(pathName);
		br = new BufferedReader(reader);
		maps = new HashMap<>();// 临时储存登录的对象
		List<BIDR> list = new ArrayList<>();// 储存完整对象
		List<BIDR> busy = new ArrayList<>();// 占线对象
		String str = "";
		while ((str = br.readLine()) != null) {
			String substring = str.substring(1, str.length());
			String[] strings = substring.split("\\|");

			if (strings[2].equals("7")) {
				BIDR bidr = new BIDR();
				bidr.setAAA_login_name(strings[0]);
				bidr.setNAS_ip(strings[1]);
				Timestamp timesIn = new Timestamp(Long.parseLong(strings[3]) * 1000);
				bidr.setLogin_date(timesIn);
				bidr.setLogin_ip(strings[4]);
				if (!maps.containsKey(bidr.getLogin_ip())) {
					maps.put(bidr.getLogin_ip(), bidr);
				} else {
					busy.add(bidr);// 占用他人网的集合
				}
			} else {
				BIDR bidr2 = maps.get(strings[4]);
				Timestamp timesOut = new Timestamp(Long.parseLong(strings[3]) * 1000);
				bidr2.setLogout_date(timesOut);
				Timestamp timeIn = bidr2.getLogin_date();
				Integer time = (int) (timesOut.getTime() - timeIn.getTime());
				bidr2.setTime_deration(time);
				list.add(bidr2);
				maps.remove(bidr2.getLogin_ip());
			}
		}
		new BackUpImpl().store("Emmmmmmmmm", maps, true);
		new Configuration().getLogger().warn("客户端  -- 备份完成");
		return list;
	}
}
