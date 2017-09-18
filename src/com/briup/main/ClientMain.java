package com.briup.main;

import java.util.List;

import com.briup.common.Configuration;
import com.briup.util.BIDR;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;

/**
 * 客户端
 */
public class ClientMain {
	public static void main(String[] args) throws Exception {
		System.out.println("这是客户端。。。");
		Configuration con = new Configuration();
		//获取客户端
		Client client = con.getClient();
		//获取收集信息对象
		Gather gather = con.getGather();
		//调用收集信息方法
		List<BIDR> list = (List<BIDR>) gather.gather();
		//发送给服务器
		client.send(list);
	}
}
