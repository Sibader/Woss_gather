package com.briup.main;

import java.util.List;

import com.briup.common.Configuration;
import com.briup.util.BIDR;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;

/**
 * 服务器端
 */
public class ServerMain {
	public static void main(String[] args) {
		System.out.println("这是服务器：  ");
		try {			
			new Configuration().getLogger().warn("等待客户端连接");
			while (true) {
				// 创建配置对象
				Configuration con = new Configuration();
				Server server = con.getServer();
				long start = System.currentTimeMillis();// 初始时间
				DBStore dbStore = con.getDBStore();
				// 接收BIDR对象
				List<BIDR> revicer = (List<BIDR>) server.revicer();
				// 入库
				dbStore.saveToDB(revicer);
				long end = System.currentTimeMillis();// 初始时间
				System.out.println();
				System.out.println("读取时间：" + (end - start));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
