package org.weixin4j.message.template.OPENTM203462646;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class OPENTM203462646 extends BaseTemplate {
	
	/**
	 * 套餐开通通知 交通工具 - 汽车相关
	 * @param first 抬头
	 * @param carNum 车牌号
	 * @param name 套餐名称
	 * @param bDate 生效时间
	 * @param eDate 失效时间
	 * @param remark 备注
	 * @param color 数据颜色 默认为#173177
	 */
	public OPENTM203462646(String first, String carNum, String name, String bDate, String eDate, String remark,
			String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keyword1\":{\"value\":\"CARNUM\",\"color\":\"COLOR\"},"
				+ "\"keyword2\":{\"value\":\"NAME\",\"color\":\"COLOR\"},"
				+ "\"keyword3\":{\"value\":\"BDATE\",\"color\":\"COLOR\"},"
				+ "\"keyword4\":{\"value\":\"EDATE\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("CARNUM", carNum).replace("NAME", name)
				.replace("BDATE", bDate).replace("EDATE", eDate)
				.replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}

}
