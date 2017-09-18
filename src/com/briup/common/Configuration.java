package com.briup.common;


import com.briup.util.BackUP;
import com.briup.util.Logger;
import com.briup.utils.Dom4jReader;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;
/**
 * 配置模块
 */
public class Configuration implements com.briup.util.Configuration{

	@Override
	public BackUP getBackup() throws Exception {
		String[] backupInfo = Dom4jReader.backupInfo();		
		return (BackUP) Class.forName(backupInfo[0]).newInstance();
	}

	@Override
	public Client getClient() throws Exception {
		String[] clientInfo = Dom4jReader.clientInfo();		 
		return (Client) Class.forName(clientInfo[0]).newInstance();		
	}

	@Override
	public DBStore getDBStore() throws Exception {
		String[] dbStore = Dom4jReader.dbStore();
		return (DBStore) Class.forName(dbStore[0]).newInstance();
	}

	@Override
	public Gather getGather() throws Exception {
		String[] gatherInfo = Dom4jReader.gatherInfo();
		return (Gather) Class.forName(gatherInfo[0]).newInstance();
		
	}

	@Override
	public Logger getLogger() throws Exception {
		String[] loggerInfo = Dom4jReader.loggerInfo();
		return (Logger) Class.forName(loggerInfo[0]).newInstance();
	}

	@Override
	public Server getServer() throws Exception {
		String[] serverInfo = Dom4jReader.serverInfo();
		return (Server) Class.forName(serverInfo[0]).newInstance();
	}
}
