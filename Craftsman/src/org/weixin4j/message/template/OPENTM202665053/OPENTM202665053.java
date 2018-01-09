package org.weixin4j.message.template.OPENTM202665053;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class OPENTM202665053 extends BaseTemplate {

	/**
	 * 活动报名成功通知
	 * @param first
	 * @param actName
	 * @param time
	 * @param address
	 * @param name
	 * @param remark
	 * @param color
	 */
	public OPENTM202665053(String first, String actName, String time, String address, String name, String remark, String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keyword1\":{\"value\":\"ACTNAME\",\"color\":\"COLOR\"},"
				+ "\"keyword2\":{\"value\":\"TIME\",\"color\":\"COLOR\"},"
				+ "\"keyword3\":{\"value\":\"ADDRESS\",\"color\":\"COLOR\"},"
				+ "\"keyword4\":{\"value\":\"NAME\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("ACTNAME", actName).replace("TIME", time)
				.replace("ADDRESS", address).replace("NAME", name)
				.replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}
}
