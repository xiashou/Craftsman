package com.tcode.common.jcrontab.job.msg;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.service.MemberService;
import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.msg.model.MsgTemplate;
import com.tcode.business.msg.service.MsgTemplateService;
import com.tcode.core.util.DataManageUtil;
import com.tcode.core.util.Utils;

/**
 * 初始化生日提醒短信
 * @author supeng
 *
 */
@Component
public class MsgNotifyBirthdayInitJob {
	
	private static Logger SLog = Logger.getLogger("SLog");
	private static Logger msgNotifyBirthdayLog = Logger.getLogger("msgNotifyBirthdayLog");
	private static MemberService memberService;
	private static MsgTemplateService msgTemplateService;
	
	public static void main(String[] args) {
		
		System.out.println("我在初始化生日提醒短信...");
		//初始化缓存数据（每次初始化通知类短信之前清空缓存数据，避免重复存储、发送）
		Map map = DataManageUtil.getMap();
		//初始化通知短信
		map.put("msgNotifyBirthdayList", null);
		initMsgNotifyBirthday();
	}
	
	/**
	 * 初始化生日提醒短信
	 */
	public static void initMsgNotifyBirthday() {
		String currentTime = Utils.getSysTime();//当前时间
		msgNotifyBirthdayLog.warn("---------生日提醒短信初始化开始：" + currentTime + "---------");
		try {
			
			//获取与当前时间（MM/dd）相同的会员信息，即当天过生日的会员
			String currentMMdd = Utils.getSysTime("MM/dd");
			List<Member> memberList = memberService.getListByMMdd(currentMMdd);
			
			//遍历会员信息列表，将需要发送短信初始化
			for(Member member : memberList) {
				int templateTypeNo = 12;//模板类型编码(生日提醒模板固定为12)
				String deptCode = member.getDeptCode();//门店代码
				String vipNo = member.getVipNo();//会员卡号
				
				//获取模板信息
				List<MsgTemplate> msgTemplateList =  msgTemplateService.getByTemplateTypeNo(deptCode, templateTypeNo);
				if(msgTemplateList.size() == 1) {
					MsgTemplate msgTemplate = msgTemplateList.get(0);
					//装成MsgSendRecord对象，放入List,存入全局Map
					initMsgSendRecord(msgTemplate, member);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			SLog.error(Utils.getErrorMessage(e));
		}finally {
			msgNotifyBirthdayLog.warn("---------生日提醒短信初始化结束：" + currentTime + "---------");
		}
	}
	
	/**
	 * 初始化短信发送记录
	 * @param orderHead
	 * @param missionID 短信任务ID
	 */
	public static void initMsgSendRecord(MsgTemplate msgTemplate, Member member) {
		MsgSendRecord msgSendRecord = new MsgSendRecord();
		
		String deptCode = member.getDeptCode();//门店代码
		String vipNo = member.getVipNo();//会员号
		//获取会员信息
		try {
			String mobile = member.getMobile();//手机号码
			String content = initContent( member, msgTemplate);
			
			if(content != null) {
				msgSendRecord.setOrderId("");//订单ID
				msgSendRecord.setDeptCode(deptCode);
				msgSendRecord.setVipNo(vipNo);
				msgSendRecord.setMobile(mobile);
				msgSendRecord.setContent(content);
				msgSendRecord.setStatus(1);//发送状态 0-成功，1-失败
				
				//通知类短信初始化信息写入日志
				StringBuffer logBuffer = new StringBuffer();
				logBuffer.append("门店：").append(deptCode).append("-").append("会员：").append(msgSendRecord.getVipNo())
				.append("-").append("手机：").append(mobile).append("-").append("任务ID：").append("")
				.append("-").append("内容：").append(content).append("-").append("状态：").append("1");
				msgNotifyBirthdayLog.warn(logBuffer);
				
				//存入缓存
				Map map = DataManageUtil.getMap();
				List<MsgSendRecord> msgSendRecordList = (List<MsgSendRecord>) map.get("msgNotifyBirthdayList");
				if(msgSendRecordList == null) 
					msgSendRecordList = new CopyOnWriteArrayList<MsgSendRecord>();
				msgSendRecordList.add(msgSendRecord);
				map.put("msgNotifyBirthdayList", msgSendRecordList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			SLog.error(Utils.getErrorMessage(e));
		}
	}
	
	/**
	 * 初始化短信内容
	 * @param orderHead
	 * @param member
	 * @return
	 */
	public static String initContent(Member member, MsgTemplate msgTemplate) {
		//{1}:称呼、{2}:项目名、{3}:消费金额、{4}:所得积分、{5}:当前余额、{6}:车牌、{7}:剩余天数、{8}:消费时间、{9}:公里数、{10}:有效期
		String content = null;
		String name = member.getName();//称呼
		String sex = member.getSex();//性别 1男 2女 0未知
		String projectName = "";
		double price = 0.0;//消费金额
		int point = 0;//所得积分
		double balance = member.getBalance();//当前余额
		String carNumber = "";//车牌号
		int carID = 0;//车辆ID
		String saleDate = "";//消费日期
		
		if("1".equals(sex)) name = name + "先生";
		else if("2".equals(sex)) name = name + "女士";
		
		int carKilometers = 0;
		
		//消费周期-（当前时间-消费时间） = 实际剩余天数
		int realRemainingDays = 0;
		
		
		//初始化信息
		content = msgTemplate.getContent();
		content = content.replace("{1}", name).replace("{2}", projectName).replace("{3}", price + "")
		.replace("{4}", point + "").replace("{5}", balance + "").replace("{6}", carNumber)
		.replace("{7}", realRemainingDays + "").replace("{8}", saleDate).replace("{9}", carKilometers + "KM")
		.replace("{10}", "");
		
		
		return content;
	}

	
	
	
	public static MemberService getMemberService() {
		return memberService;
	}

	@Resource
	public void setMemberService(MemberService memberService) {
		MsgNotifyBirthdayInitJob.memberService = memberService;
	}

	public static MsgTemplateService getMsgTemplateService() {
		return msgTemplateService;
	}

	@Resource
	public void setMsgTemplateService(MsgTemplateService msgTemplateService) {
		MsgNotifyBirthdayInitJob.msgTemplateService = msgTemplateService;
	}

}
