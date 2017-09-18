package com.briup.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.briup.common.Configuration;
import com.briup.common.LoggerImpl;
import com.briup.util.BIDR;
import com.briup.utils.Dom4jReader;
import com.briup.woss.server.Server;

/**
 * 中央服务器
 */
public class ServerImpl implements Server {

	private ServerSocket ss = null;
	private Socket s = null;
	private List<BIDR> list = null;
	private Integer port = null;

	public ServerImpl() {
		init(new Properties());
	}
	@Override
	public void init(Properties pro) {
		try {			
			String[] serverInfo = Dom4jReader.serverInfo();
			pro.setProperty("port", serverInfo[1]);
			Class<?> clazz = Class.forName(serverInfo[0]);
			Field portField = clazz.getDeclaredField("port");
			portField.setAccessible(true);
			portField.set(this, Integer.parseInt(pro.getProperty("port")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public Collection<BIDR> revicer() throws Exception {
		try {
			// 1.创建服务器端的ServerSocket
			ss = new ServerSocket(port);
			// 2.调用accept方法，等待客户端发送过来的连接(Socket);			
			s = ss.accept();
			MyThread thread = new MyThread(s);
			thread.start();
			thread.join();
			list = thread.bidrs;//传值返回
			new Configuration().getLogger().warn("服务器--接收文件");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			shutdown();
		}
		return list;
	}

	@Override
	public void shutdown() {
		try {
			if (ss != null) ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MyThread extends Thread {

	private Socket s = null;
	private InputStream is = null;
	private ObjectInputStream ois = null;
	public List<BIDR> bidrs = null;

	public MyThread(Socket s) {
		this.s = s;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			new Configuration().getLogger().warn("连接客户端成功");
			is = s.getInputStream();
			ois = new ObjectInputStream(is);
			bidrs = (List<BIDR>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (ois != null)
					ois.close();
				if (s != null)
					s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
