package com.tcode.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WeatherUtils {
	
	private static Logger log = Logger.getLogger("SLog");
	
	InputStream inStream;
	Element root;

	public InputStream getInStream() {
		return inStream;
	}

	public void setInStream(InputStream inStream) {
		this.inStream = inStream;
	}

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	public WeatherUtils() {
	}

	/**
	 * 通过输入流来获取新浪接口信息
	 * 
	 * @param inStream
	 */
	public WeatherUtils(InputStream inStream) {
		if (inStream != null) {
			this.inStream = inStream;
			DocumentBuilderFactory domfac = DocumentBuilderFactory
					.newInstance();
			try {
				DocumentBuilder domBuilder = domfac.newDocumentBuilder();
				Document doc = domBuilder.parse(inStream);
				root = doc.getDocumentElement();
			} catch (ParserConfigurationException e) {
				log.error(Utils.getErrorMessage(e));
			} catch (SAXException e) {
				log.error(Utils.getErrorMessage(e));
			} catch (IOException e) {
				log.error(Utils.getErrorMessage(e));
			}
		}
	}

	public WeatherUtils(String path) {
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			log.error(Utils.getErrorMessage(e1));
		}

		if (inStream != null) {
			this.inStream = inStream;
			DocumentBuilderFactory domfac = DocumentBuilderFactory
					.newInstance();
			try {
				DocumentBuilder domBuilder = domfac.newDocumentBuilder();
				Document doc = domBuilder.parse(inStream);
				root = doc.getDocumentElement();
			} catch (ParserConfigurationException e) {
				log.error(Utils.getErrorMessage(e));
			} catch (SAXException e) {
				log.error(Utils.getErrorMessage(e));
			} catch (IOException e) {
				log.error(Utils.getErrorMessage(e));
			}
		}
	}

	public WeatherUtils(URL url) {
		InputStream inStream = null;
		try {
			inStream = url.openStream();
		} catch (IOException e1) {
			log.error("获取天气出错！");
		}

		if (inStream != null) {
			this.inStream = inStream;
			DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder domBuilder = domfac.newDocumentBuilder();
				Document doc = domBuilder.parse(inStream);
				root = doc.getDocumentElement();
			} catch (ParserConfigurationException e) {
				log.error(Utils.getErrorMessage(e));
			} catch (SAXException e) {
				log.error(Utils.getErrorMessage(e));
			} catch (IOException e) {
				log.error(Utils.getErrorMessage(e));
			}
		}
	}

	/**
	 * 
	 * @param nodes
	 * @return 单个节点多个值以分号分隔
	 */
	public Map<String, String> getValue(String[] nodes) {
		if (inStream == null || root == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		// 初始化每个节点的值为null
		for (int i = 0; i < nodes.length; i++) {
			map.put(nodes[i], null);
		}
		// 遍历第一节点
		NodeList topNodes = root.getChildNodes();
		if (topNodes != null) {
			for (int i = 0; i < topNodes.getLength(); i++) {
				Node book = topNodes.item(i);
				if (book.getNodeType() == Node.ELEMENT_NODE) {
					for (int j = 0; j < nodes.length; j++) {
						for (Node node = book.getFirstChild(); node != null; node = node
								.getNextSibling()) {
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								if (node.getNodeName().equals(nodes[j])) {
									String val = node.getTextContent();
									String temp = map.get(nodes[j]);
									if (temp != null && !temp.equals("")) {
										temp = temp + ";" + val;
									} else {
										temp = val;
									}
									map.put(nodes[j], temp);
								}
							}
						}
					}
				}
			}
		}
		return map;
	}
	
	public static String weatherInfo(String city) {
		String weather = "";
		try {
			if(!Utils.isEmpty(city)) {
				if(city.indexOf("市") > 0)
					city = city.replace("市", "");
			}
			String link = "http://php.weather.sina.com.cn/xml.php?city=" + URLEncoder.encode(city, "GBK") + "&password=DJOYnieT8234jlsK&day=0";
			URL url = new URL(link);
			WeatherUtils parser = new WeatherUtils(url);
			String[] nodes = { "city", "status1", "temperature1", "status2", "temperature2" };
			Map<String, String> map = parser.getValue(nodes);
			if(!Utils.isEmpty(map.get(nodes[0])))
				weather = map.get(nodes[0]) + " " + map.get(nodes[1]) + " " + map.get(nodes[4]) + "-" + map.get(nodes[2]) + "℃ ";
		} catch(Exception e) {
			weather = "天气获取失败！";
			log.error("天气获取失败！");
		}
		return weather;
	}

	public static void main(String[] args) {

		String link = "http://php.weather.sina.com.cn/xml.php?city=%C9%EE%DB%DA&password=DJOYnieT8234jlsK&day=0";

		try {
			System.out.println(URLEncoder.encode("深圳", "GBK"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		URL url;
		try {
			url = new URL(link);
			WeatherUtils parser = new WeatherUtils(url);
			System.out.println(parser);
			String[] nodes = { "city", "status1", "temperature1", "status2", "temperature2" };
			Map<String, String> map = parser.getValue(nodes);
			if(!Utils.isEmpty(map.get(nodes[0])))
				System.out.println(Utils.isEmpty(map.get(nodes[0])) ? "" : map.get(nodes[0]) + " 今天白天：" + map.get(nodes[1]) + " 最高温度：" + map.get(nodes[2]) + "℃ 今天夜间："
					+ map.get(nodes[3]) + " 最低温度：" + map.get(nodes[4]) + "℃ ");
			System.out.println(map.get(nodes[0]) + " " + map.get(nodes[1]) + " " + map.get(nodes[4]) + "-" + map.get(nodes[2]) + "℃ ");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		

	}

	
}
