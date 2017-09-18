package com.briup.client;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.common.LoggerImpl;
import com.briup.util.BIDR;
import com.briup.utils.Dom4jReader;
import com.briup.woss.client.Client;

/**
 * AAA服务器客户端
 */
public class ClientImpl implements Client {

	private ObjectOutputStream oos = null;
	private OutputStream os = null;
	private Socket socket = null;
	private String ip =null;
	private Integer port = null;

	public ClientImpl() {
		init(new Properties());
	}
	@Override
	public void init(Properties pro) {
		try {
			String[] clientInfo = Dom4jReader.clientInfo();
			pro.setProperty("ip", clientInfo[1]);
			pro.setProperty("port", clientInfo[2]);
			Class<?> clazz = Class.forName(clientInfo[0]);
			Field ipField = clazz.getDeclaredField("ip");
			Field portField = clazz.getDeclaredField("port");
			ipField.setAccessible(true);
			portField.setAccessible(true);
			ipField.set(this, pro.getProperty("ip"));
			portField.set(this, Integer.parseInt(pro.getProperty("port")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 向服务器发送采集好的数据
	 */
	@Override
	public void send(Collection<BIDR> arg0) {
		try {
			socket = new Socket(ip, port);
			os = socket.getOutputStream();
			oos = new ObjectOutputStream(os);
			oos.writeObject(arg0);
			oos.flush();
			new LoggerImpl().warn("客户端  -- 发送完成");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (os != null)
					oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
