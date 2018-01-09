package org.weixin4j.message.template.OPENTM410192957;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class OPENTM410192957 extends BaseTemplate {

	/**
	 * 预约成功通知
	 * @param first
	 * @param actName
	 * @param time
	 * @param address
	 * @param name
	 * @param remark
	 * @param color
	 */
	public OPENTM410192957(String first, String bookTime, String store, String service, String remark, String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keyword1\":{\"value\":\"BOOKTIME\",\"color\":\"COLOR\"},"
				+ "\"keyword2\":{\"value\":\"STORE\",\"color\":\"COLOR\"},"
				+ "\"keyword3\":{\"value\":\"SERVICE\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("BOOKTIME", bookTime).replace("STORE", store)
				.replace("SERVICE", service).replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}
}
