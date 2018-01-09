package org.weixin4j.message.template.OPENTM406019708;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class OPENTM406019708 extends BaseTemplate {

	/**
	 * 开单成功提醒
	 * @param first 抬头
	 * @param orderId 订单号
	 * @param time 订单时间
	 * @param detail 订单明细
	 * @param status 状态
	 * @param remark 备注
	 * @param color
	 */
	public OPENTM406019708(String first, String orderId, String time, String detail, String status, String remark,
			String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keyword1\":{\"value\":\"ORDERID\",\"color\":\"COLOR\"},"
				+ "\"keyword2\":{\"value\":\"TIME\",\"color\":\"COLOR\"},"
				+ "\"keyword3\":{\"value\":\"DETAIL\",\"color\":\"COLOR\"},"
				+ "\"keyword4\":{\"value\":\"STATUS\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("ORDERID", orderId).replace("TIME", time)
				.replace("DETAIL", detail).replace("STATUS", status).replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}
}
