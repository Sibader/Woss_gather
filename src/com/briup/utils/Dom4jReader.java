package com.briup.utils;

import java.io.File;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Dom4jReader {

	public static Element readXML() throws Exception{
		SAXReader reader = new SAXReader();
		File file = new File("src/com/briup/file/conf.xml");
		Document document = reader.read(file);
		//获取根目录
		Element rootElement = document.getRootElement();
		return rootElement;
	}
	
	public static String[] gatherInfo() throws Exception{
		Element rootElement = Dom4jReader.readXML();
		Element gather = rootElement.element("gather");
		Attribute attribute = gather.attribute(0);
		String attrName = attribute.getValue();
		Element nas_ip = gather.element("nas-ip");
		String nasText = nas_ip.getText();
		Element src_file = gather.element("src-file");
		String srcText = src_file.getText();
		String[] str ={attrName,nasText,srcText};
		return str;
	} 
	
	public static String[] clientInfo() throws Exception{
		Element rootElement = Dom4jReader.readXML();
		Element client = rootElement.element("client");
		Attribute attribute = client.attribute(0);
		String attrName = attribute.getValue();
		Element ipElement = client.element("ip");
		String ip = ipElement.getText();
		Element portElement = client.element("port");
		String port = portElement.getText();
		String[] str ={attrName,ip,port};
		return str;
	}
	public static String[] serverInfo() throws Exception{
		Element rootElement = Dom4jReader.readXML();
		Element server = rootElement.element("server");
		Attribute attribute = server.attribute(0);
		String attrName = attribute.getValue();
		Element portElement = server.element("port");
		String port = portElement.getText();
		String[] str = {attrName,port};
		return str;
	}
	public static String[] loggerInfo() throws Exception{
		Element rootElement = Dom4jReader.readXML();
		Element logger = rootElement.element("logger");
		Attribute attribute = logger.attribute(0);
		String attrName = attribute.getValue();
		Element logElement = logger.element("log-properties");
		String log = logElement.getText();		
		String[] str= {attrName,log};		
		return str;
	}
	public static String[] backupInfo() throws Exception{
		Element rootElement = Dom4jReader.readXML();
		Element backup = rootElement.element("backup");
		Attribute attribute = backup.attribute(0);
		String attrName = attribute.getValue();
		Element backElement = backup.element("back-temp");
		String back = backElement.getText();
		String[] str = {attrName,back};
		return str;
	}
	public static String[] dbStore() throws Exception{
		Element rootElement = Dom4jReader.readXML();
		Element dbstore = rootElement.element("dbstore");
		Attribute attribute = dbstore.attribute(0);
		String attrName = attribute.getValue();
		String[] str = {attrName};
		return str;
	}
}
