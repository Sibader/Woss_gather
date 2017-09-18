package com.briup.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.briup.client.GatherImpl;
import com.briup.util.BIDR;
import com.briup.util.BackUP;
import com.briup.utils.Dom4jReader;
/**
 * 备份模块
 */
public class BackUpImpl implements BackUP{

	private static  Map<String,Object> bidrs = new HashMap<>();
	private String path=null;
	
	public BackUpImpl() {
		init(new Properties());
	}
	@Override
	public void init(Properties pro) {
		try {
			String[] dbStore = Dom4jReader.backupInfo();
			pro.setProperty("path",dbStore[1]);
			Class<?> clazz = Class.forName(dbStore[0]);
			Field field = clazz.getDeclaredField("path");
			field.setAccessible(true);
			field.set(this, pro.getProperty("path"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//读取
	@SuppressWarnings("unchecked")
	@Override
	public Object load(String arg0, boolean bool) throws Exception {
		Map<String,BIDR> bidrs = new HashMap<>();
		if(bool == true){			
			File file = new File(path);
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			bidrs =   (Map<String, BIDR>) ois.readObject();
			BIDR bidr=null;
		    for(String key:bidrs.keySet()){
		    	if(key.equals(arg0)){
		    		bidr = bidrs.get(key);
		    	}
		    }
		    ois.close();
			return bidr;
		}else{
			new Configuration().getLogger().warn(("没有权限读取备份文件"));;
			return null;
		}				
	}	
	//存储
	@Override
	public void store(String key, Object obj, boolean bool) throws Exception {		
			File file = new File(path);	
			ObjectOutputStream oos = null;
			if(bool){				
				oos=new ObjectOutputStream(new FileOutputStream(file,true));			
			}else{
				oos=new ObjectOutputStream(new FileOutputStream(file,false));
			}
			oos.writeObject(obj);
			oos.flush();
			oos.close();	
	}
}
