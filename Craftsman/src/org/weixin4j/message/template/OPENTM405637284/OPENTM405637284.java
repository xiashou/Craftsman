package org.weixin4j.message.template.OPENTM405637284;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class OPENTM405637284 extends BaseTemplate{
	/**
	 * 套餐消费提醒 交通工具 - 汽车相关
	 * @param first 抬头
	 * @param content 服务内容
	 * @param time 使用时间
	 * @param kilometers 行驶公里数
	 * @param status 车辆状态
	 * @param num 套餐剩余次数
	 * @param remark 备注
	 * @param color 数据颜色 默认为#173177
	 */
	public OPENTM405637284(String first, String content, String time, String kilometers, String status, String num, String remark,
			String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keyword1\":{\"value\":\"CONTENT\",\"color\":\"COLOR\"},"
				+ "\"keyword2\":{\"value\":\"TIME\",\"color\":\"COLOR\"},"
				+ "\"keyword3\":{\"value\":\"KILOMETERS\",\"color\":\"COLOR\"},"
				+ "\"keyword4\":{\"value\":\"STATUS\",\"color\":\"COLOR\"},"
				+ "\"keyword5\":{\"value\":\"NUM\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("CONTENT", content).replace("TIME", time)
				.replace("KILOMETERS", kilometers).replace("STATUS", status)
				.replace("NUM", num).replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}
}
