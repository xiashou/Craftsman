package org.weixin4j.message.template.TM00622;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class TM00622 extends BaseTemplate {
	
	/**
	 * 汽车保养到期提醒
	 * @param first
	 * @param nextDate 保养到期时间
	 * @param carKilometers 车上次保养公里数
	 * @param upDate 上次保养时间
	 * @param remark
	 * @param color
	 */
	public TM00622(String first, String nextDate, String upDate, String carKilometers, String remark,
			String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keynote1\":{\"value\":\"NEXTDATE\",\"color\":\"COLOR\"},"
				+ "\"keynote2\":{\"value\":\"UPDATE\",\"color\":\"COLOR\"},"
				+ "\"keynote3\":{\"value\":\"CARKILONETERS\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("NEXTDATE", nextDate).replace("UPDATE", upDate).replace("CARKILONETERS", carKilometers)
				.replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}

}
