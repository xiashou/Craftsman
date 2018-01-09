package org.weixin4j.message.template.OPENTM202535907;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class OPENTM202535907 extends BaseTemplate {
	
	/**
	 * 会员充值通知 消费品 - 消费品
	 * @param first 抬头
	 * @param time 充值时间
	 * @param money 充值金额
	 * @param type 充值方式
	 * @param balance 当前余额
	 * @param remark 备注
	 * @param color 数据颜色 默认为#173177
	 */
	public OPENTM202535907(String first, String time, String money, String type, String balance, String remark,
			String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keyword1\":{\"value\":\"TIME\",\"color\":\"COLOR\"},"
				+ "\"keyword2\":{\"value\":\"MONEY\",\"color\":\"COLOR\"},"
				+ "\"keyword3\":{\"value\":\"TYEP\",\"color\":\"COLOR\"},"
				+ "\"keyword4\":{\"value\":\"BANLANCE\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("TIME", time).replace("MONEY", money)
				.replace("TYEP", type).replace("BANLANCE", balance)
				.replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}

}
