package org.weixin4j.message.template.TM00619;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class TM00619 extends BaseTemplate {
	
	/**
	 * 汽车保险到期提醒
	 * @param first
	 * @param upDate 上次投保时间
 	 * @param nextDate 保险到期时间
	 * @param remark
	 * @param color
	 */
	public TM00619(String first, String upDate, String nextDate, String remark,
			String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keynote1\":{\"value\":\"UPDATE\",\"color\":\"COLOR\"},"
				+ "\"keynote2\":{\"value\":\"NEXTDATE\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("UPDATE", upDate).replace("NEXTDATE", nextDate)
				.replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}

}
