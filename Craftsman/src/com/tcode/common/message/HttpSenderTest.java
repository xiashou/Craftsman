package com.tcode.common.message;


public class HttpSenderTest {
	public static void main(String[] args) throws Exception {
		
		String mobile = "18503087176";// 手机号码，多个号码使用","分割
		String msg = "【君威汽修厂】尊敬的林先生您好！您本次进行【蜡水洗车】等消费共30元，获得3积分，当前余额：150元。"
				+ "详情关注本店微信公众号：君威汽修厂，感谢您对本店的支持，服务电话0769-88867655。";// 短信内容

		try {
			int returnString = HttpSender.batchSend(mobile, msg);
			System.out.println(returnString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
