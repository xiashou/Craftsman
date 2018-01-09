package org.weixin4j.message.template.OPENTM206161737;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class OPENTM206161737 extends BaseTemplate {
	
	/**
	 * 车辆年检到期提醒
	 * @param first
	 * @param carNo 车牌
 	 * @param nextDate 保险到期时间
	 * @param remark
	 * @param color
	 */
	public OPENTM206161737(String first, String carNo, String nextDate, String remark,
			String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keyword1\":{\"value\":\"CARNO\",\"color\":\"COLOR\"},"
				+ "\"keyword2\":{\"value\":\"NEXTDATE\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("CARNO", carNo).replace("NEXTDATE", nextDate)
				.replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}

}
