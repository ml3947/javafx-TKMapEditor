package com.wing.game.mapeditor.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
/**
 * 配置文件
 * @author Wing Mei
 *
 */
public class Config {
	public static final String ELEMENT_CONFIG = "Config";
	public static final String ELEMENT_FILEPATH = "FilePath";
	public static final String ATTRIBUTE_PATH = "Path";
	private static final String ConfigName = "Config.xml";
	private SAXReader saxReader = new SAXReader();
	private ArrayList<String> filePaths = new ArrayList<>();
    private static Config config;
    
    public static Config getInstance(){
    	if(config == null){
    		config = new Config();
    	}
    	return config;
    }
    
	/*
	 * 保存配置文件
	 */
	public void saveConfig() {
		File file = new File(ConfigName);
		if (file.exists()) {
           file.delete();
		}
		Document document = DocumentHelper.createDocument();
		Element config = document.addElement(ELEMENT_CONFIG);
		for (String str : filePaths) {
			Element pathElement = config.addElement(ELEMENT_FILEPATH);
			pathElement.addAttribute(ATTRIBUTE_PATH, str);
		}
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(file));
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 读取配置文件
	 */
	public void readConfig(){
		File file = new File(ConfigName);
		if (file.exists()) {
			try {
				Document document = saxReader.read(file);
				Element rootElement = document.getRootElement();
				System.out.println(rootElement.getName());
				for (Iterator<Element> i = rootElement.elementIterator(); i.hasNext();) {
					Element e = i.next();
					String pathStr = e.attributeValue(ATTRIBUTE_PATH);
					filePaths.add(pathStr);
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<String> getFilePaths() {
		return filePaths;
	}
}
