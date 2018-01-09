package org.weixin4j.message.template.OPENTM411517389;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class OPENTM411517389 extends BaseTemplate {

	/**
	 * 开单成功提醒
	 * @param first 抬头
	 * @param orderId 订单号
	 * @param status 订单状态
	 * @param detail 订单明细
	 * @param money 费用
	 * @param remark 备注
	 * @param color
	 */
	public OPENTM411517389(String first, String orderId, String status, String detail, String money, String remark,
			String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keyword1\":{\"value\":\"ORDERID\",\"color\":\"COLOR\"},"
				+ "\"keyword2\":{\"value\":\"STATUS\",\"color\":\"COLOR\"},"
				+ "\"keyword3\":{\"value\":\"DETAIL\",\"color\":\"COLOR\"},"
				+ "\"keyword4\":{\"value\":\"MONEY\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("ORDERID", orderId).replace("STATUS", status)
				.replace("DETAIL", detail).replace("MONEY", money).replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}
	
}
