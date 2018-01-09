package org.weixin4j.message.template.OPENTM401447462;

import org.weixin4j.message.template.BaseTemplate;

import com.tcode.core.util.Utils;

public class OPENTM401447462 extends BaseTemplate {
	
	/**
	 * 车辆维修完工提醒
	 * @param first 抬头
	 * @param carNum 车牌号
	 * @param saleTime 进厂时间
	 * @param finishTime 完工时间
	 * @param takeTime 本次耗时
	 * @param money 费用合计
	 * @param remark 备注
	 * @param color
	 */
	public OPENTM401447462(String first, String carNum, String saleTime, String finishTime, String takeTime, String money, String remark,
			String color) {
		if (Utils.isEmpty(color))
			color = "#173177";
		String value = "{\"first\": {\"value\":\"FIRST\",\"color\":\"COLOR\"},"
				+ "\"keyword1\":{\"value\":\"CARNUM\",\"color\":\"COLOR\"},"
				+ "\"keyword2\":{\"value\":\"SALEDATE\",\"color\":\"COLOR\"},"
				+ "\"keyword3\":{\"value\":\"FINISH\",\"color\":\"COLOR\"},"
				+ "\"keyword4\":{\"value\":\"TAKETIME\",\"color\":\"COLOR\"},"
				+ "\"keyword5\":{\"value\":\"MONEY\",\"color\":\"COLOR\"},"
				+ "\"remark\":{\"value\":\"REMARK\",\"color\":\"COLOR\"}}";
		value = value.replace("FIRST", first).replace("CARNUM", carNum).replace("SALEDATE", saleTime)
				.replace("FINISH", finishTime).replace("TAKETIME", takeTime)
				.replace("MONEY", money).replace("REMARK", remark).replace("COLOR", color);
		
		this.setData(value);
	}

}
