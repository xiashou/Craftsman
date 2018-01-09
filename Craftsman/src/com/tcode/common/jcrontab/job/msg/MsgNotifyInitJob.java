package com.tcode.common.jcrontab.job.msg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.member.service.MemberService;
import com.tcode.business.msg.model.MsgMission;
import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.msg.model.MsgTemplate;
import com.tcode.business.msg.service.MsgMissionService;
import com.tcode.business.msg.service.MsgSendRecordService;
import com.tcode.business.msg.service.MsgTemplateService;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.service.OrderService;
import com.tcode.core.util.DataManageUtil;
import com.tcode.core.util.Utils;

/**
 * 初始化通知短信JOB
 * @author supeng
 *
 */
@Component
public class MsgNotifyInitJob {
	
	private static Logger SLog = Logger.getLogger("SLog");
	private static Logger msgNotifyLog = Logger.getLogger("msgNotifyLog");
	private static MsgMissionService msgMissionService;
	private static MsgTemplateService msgTemplateService;
	private static OrderService orderService;
	private static MsgSendRecordService msgSendRecordService;
	private static MemberService memberService;
	private static MemberCarService memberCarService;
	
	public static void main(String[] args) {
		//1、后台JOB扫描任务表，通过模板类型编码、门店代码获取短信模板中的内容、剩余天数和发送频率。
		//2、通过订单ID获取订单内容，得到消费时间和其他相关内容。
		//3、通过任务ID从短信发送列表获取上次发送时间。
		//4、通过模板ID获取消费周期，与消费日期、剩余天数、发送频率、上次发送时间一起运算是否满足发送短信需求。
		//5、满足条件则封装成MsgSendRecord对象，放入List,存入全局Map。不满足则不处理。超出剩余天数则将任务列表对应任务删除（逻辑删除或物理删除）
		//消费周期-（当前时间-消费时间） = 实际剩余天数
		//(当前时间-发送时间) >= 发送频率 ？ 满足发送条件 ：不满足
		
		System.out.println("我在初始化通知类短信...");
		//初始化缓存数据（每次初始化通知类短信之前清空缓存数据，避免重复存储、发送）
		Map map = DataManageUtil.getMap();
		//初始化通知短信
		map.put("msgNotifyList", null);
		initMsgNotify();
	}
	
	/**
	 * 初始化通知短信
	 */
	public static void initMsgNotify() {
		String currentTime = Utils.getSysTime();//当前时间
		msgNotifyLog.warn("---------通知短信初始化开始：" + currentTime + "---------");
		try {
			
			//获取所有任务信息
			List<MsgMission> msgMissionList = msgMissionService.getAll();
			
			//获取消费周期
			int saleCycle = 2;
			
			//遍历任务信息列表，将需要发送短信的任务初始化
			for(MsgMission msgMission : msgMissionList) {
				int templateTypeNo = msgMission.getTemplateTypeNo();//模板类型编码
				String deptCode = msgMission.getDeptCode();//门店代码
				String vipNo = msgMission.getVipNo();//会员卡号
				String orderID = msgMission.getOrderID();//订单ID
				int missionID = msgMission.getId();
				
				//获取模板信息
				List<MsgTemplate> msgTemplateList =  msgTemplateService.getByTemplateTypeNo(deptCode, templateTypeNo);
				if(msgTemplateList.size() == 1) {
					MsgTemplate msgTemplate = msgTemplateList.get(0);
					int remainingDays = msgTemplate.getRemainingDays();//剩余天数
					int sendRate = msgTemplate.getSendRate();//发送频率
					
					//获取订单信息
					OrderHead orderHead = orderService.getHeadByOrderNo(orderID);
					String saleDate = orderHead.getSaleDate()+":00";//消费日期
					
					//消费周期-（当前时间-消费时间） = 实际剩余天数
					int realRemainingDays = saleCycle - Utils.daysBetween(currentTime, saleDate, "yyyy/MM/dd hh:mm:ss");
					
					if(realRemainingDays < 0) {//如果当前日期距离消费日期超出消费周期则结束该任务
						msgMissionService.delete(msgMission);
						continue;
					}else if(realRemainingDays <= remainingDays) {//实际剩余天数小于设置的剩余天数时，开始计算频率是否满足
						//根据任务ID获取，已发送短信记录
						List<MsgSendRecord> msgSendRecordList = msgSendRecordService.getByMissionID(missionID);
						if(msgSendRecordList.size() >= 1) {//如有对应任务ID发送记录，则运算相隔时间是否满足设置的发送频率
							MsgSendRecord msgSendRecord = msgSendRecordList.get(0);
							String sendDate = msgSendRecord.getUpdateTime();//发送时间
							//(当前时间-发送时间) >= 发送频率 ？ 满足发送条件 ：不满足
							if(Utils.daysBetween(currentTime, sendDate, "yyyy/MM/dd hh:mm:ss") >= sendRate) {
								//装成MsgSendRecord对象，放入List,存入全局Map
								initMsgSendRecord(orderHead, missionID, msgTemplate, saleCycle);
							}
							
						}else {//无对应任务ID的发送记录，说明此任务下还未发送短信，可以发送
							//装成MsgSendRecord对象，放入List,存入全局Map
							initMsgSendRecord(orderHead, missionID, msgTemplate, saleCycle);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			SLog.error(Utils.getErrorMessage(e));
		}finally {
			msgNotifyLog.warn("---------通知短信初始化结束：" + currentTime + "---------");
		}
	}
	
	/**
	 * 初始化短信发送记录
	 * @param orderHead
	 * @param missionID 短信任务ID
	 */
	public static void initMsgSendRecord(OrderHead orderHead, int missionID, MsgTemplate msgTemplate, int saleCycle) {
		MsgSendRecord msgSendRecord = new MsgSendRecord();
		
		String deptCode = orderHead.getDeptCode();//门店代码
		String vipNo = orderHead.getVipNo();//会员号
		int memID = orderHead.getMemId();//会员ID
		//获取会员信息
		try {
			Member member = memberService.getMemberById(memID);
			String mobile = member.getMobile();//手机号码
			String content = initContent(orderHead, member, msgTemplate, saleCycle);
			
			if(content != null) {
				msgSendRecord.setDeptCode(deptCode);
				msgSendRecord.setVipNo(vipNo);
				msgSendRecord.setMissionID(missionID);//任务ID
				msgSendRecord.setOrderId(orderHead.getOrderId());//订单ID
				msgSendRecord.setMobile(mobile);
				msgSendRecord.setContent(content);
				msgSendRecord.setStatus(1);//发送状态 0-成功，1-失败
				
				//通知类短信初始化信息写入日志
				StringBuffer logBuffer = new StringBuffer();
				logBuffer.append("门店：").append(deptCode).append("-").append("会员：").append(msgSendRecord.getVipNo())
				.append("-").append("手机：").append(mobile).append("-").append("任务ID：").append(missionID)
				.append("-").append("内容：").append(content).append("-").append("状态：").append("1");
				msgNotifyLog.warn(logBuffer);
				
				//存入缓存
				Map map = DataManageUtil.getMap();
				List<MsgSendRecord> msgSendRecordList = (List<MsgSendRecord>) map.get("msgNotifyList");
				if(msgSendRecordList == null) 
					msgSendRecordList = new CopyOnWriteArrayList<MsgSendRecord>();
				msgSendRecordList.add(msgSendRecord);
				map.put("msgNotifyList", msgSendRecordList);
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
	public static String initContent(OrderHead orderHead, Member member, MsgTemplate msgTemplate, int saleCycle) {
		//{1}:称呼、{2}:项目名、{3}:消费金额、{4}:所得积分、{5}:当前余额、{6}:车牌、{7}:剩余天数、{8}:消费时间、{9}:公里数、{10}:有效期
		//{11}:支付方式
		String content = null;
		String name = member.getName();//称呼
		String sex = member.getSex();//性别 1男 2女 0未知
		String projectName = "";
		double price = orderHead.getAprice();//消费金额
		int point = orderHead.getPoint();//所得积分
		double balance = member.getBalance();//当前余额
		String carNumber = orderHead.getCarNumber();//车牌号
		int carID = orderHead.getCarId();//车辆ID
		String saleDate = orderHead.getSaleDate()+":00";//消费日期
		
		if("1".equals(sex)) name = name + "先生";
		else if("2".equals(sex)) name = name + "女士";
		
		//获得车辆信息
		MemberCar memberCar = null;
		try {
			memberCar = memberCarService.getMemberCarById(carID);
		} catch (Exception e) {
			e.printStackTrace();
			SLog.error(Utils.getErrorMessage(e));
		}
		int carKilometers = 0;
		if(memberCar.getCarKilometers() != null) 
			carKilometers = memberCar.getCarKilometers();//车辆行驶公里数
		
		//消费周期-（当前时间-消费时间） = 实际剩余天数
		int realRemainingDays = saleCycle - Utils.daysBetween(Utils.getSysTime(), saleDate, "yyyy/MM/dd hh:mm:ss");
		
		
		//初始化信息
		content = msgTemplate.getContent();
		content = content.replace("{1}", name).replace("{2}", projectName).replace("{3}", price + "")
		.replace("{4}", point + "").replace("{5}", balance + "").replace("{6}", carNumber)
		.replace("{7}", realRemainingDays + "").replace("{8}", saleDate).replace("{9}", carKilometers + "KM")
		.replace("{10}", "");
		
		
		return content;
	}
	

	public MsgMissionService getMsgMissionService() {
		return msgMissionService;
	}

	@Resource
	public void setMsgMissionService(MsgMissionService msgMissionService) {
		MsgNotifyInitJob.msgMissionService = msgMissionService;
	}

	public MsgTemplateService getMsgTemplateService() {
		return msgTemplateService;
	}

	@Resource
	public void setMsgTemplateService(MsgTemplateService msgTemplateService) {
		MsgNotifyInitJob.msgTemplateService = msgTemplateService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	@Resource
	public void setOrderService(OrderService orderService) {
		MsgNotifyInitJob.orderService = orderService;
	}

	public MsgSendRecordService getMsgSendRecordService() {
		return msgSendRecordService;
	}

	@Resource
	public void setMsgSendRecordService(MsgSendRecordService msgSendRecordService) {
		MsgNotifyInitJob.msgSendRecordService = msgSendRecordService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	@Resource
	public void setMemberService(MemberService memberService) {
		MsgNotifyInitJob.memberService = memberService;
	}

	public MemberCarService getMemberCarService() {
		return memberCarService;
	}

	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		MsgNotifyInitJob.memberCarService = memberCarService;
	}
	
}
