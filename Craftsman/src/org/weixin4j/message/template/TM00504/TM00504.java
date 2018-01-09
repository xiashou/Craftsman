package org.weixin4j.message.template.TM00504;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class TM00504 extends BaseTemplate {

	/**
	 * 消费成功通知 消费品 - 消费品
	 * @param first 抬头
	 * @param time 消费时间
	 * @param org 消费门店
	 * @param type 消费类型
	 * @param money 消费金额
	 * @param point 积分增加
	 * @param remark 备注
	 * @param color 数据颜色 默认为#173177
	 */
	public TM00504(String first, String time, String org, String type, String money, String point, String remark,
			String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"time\":{\"value\":\"TIME\",\"color\":\"COLOR\"},"
				+ "\"org\":{\"value\":\"ORG\",\"color\":\"COLOR\"},"
				+ "\"type\":{\"value\":\"TYPE\",\"color\":\"COLOR\"},"
				+ "\"money\":{\"value\":\"MONEY\",\"color\":\"COLOR\"},"
				+ "\"point\":{\"value\":\"POINT\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("TIME", time).replace("ORG", org).replace("TYPE", type)
				.replace("MONEY", money).replace("POINT", point).replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}

}
