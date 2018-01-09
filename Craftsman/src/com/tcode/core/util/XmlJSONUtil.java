package com.tcode.core.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class XmlJSONUtil {

	public static String xmlToJSON(String xml) {
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			Map map = iterateElement(root);
			obj.put(root.getName(), map);
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map iterateElement(Element root) {
		List childrenList = root.getChildren();
		Element element = null;
		Map map = new HashMap();
		List list = null;
		for (int i = 0; i < childrenList.size(); i++) {
			list = new ArrayList();
			element = (Element) childrenList.get(i);
			if (element.getChildren().size() > 0) {
				if (root.getChildren(element.getName()).size() > 1) {
					if (map.containsKey(element.getName())) {
						list = (List) map.get(element.getName());
					}
					list.add(iterateElement(element));
					map.put(element.getName(), list);
				} else {
					map.put(element.getName(), iterateElement(element));
				}
			} else {
				if (root.getChildren(element.getName()).size() > 1) {
					if (map.containsKey(element.getName())) {
						list = (List) map.get(element.getName());
					}
					list.add(element.getTextTrim());
					map.put(element.getName(), list);
				} else {
					map.put(element.getName(), element.getTextTrim());
				}
			}
		}

		return map;
	}

	public static void main(String[] args) {

		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx8208c12eb8f85d78]]></appid><mch_id><![CDATA[1395510602]]></mch_id><nonce_str><![CDATA[N6um9bGnlR71Zy0p]]></nonce_str><sign><![CDATA[75D84239FFCF983FA2A7A980600A58D2]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx20170905185253e55812acad0631189682]]></prepay_id><trade_type><![CDATA[MWEB]]></trade_type><mweb_url><![CDATA[https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx20170905185253e55812acad0631189682&package=212456865]]></mweb_url></xml>";
		String str = XmlJSONUtil.xmlToJSON(xml);
		System.out.println(str);
	}

}
