package com.tcode.common.jcrontab.job.wechat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.weixin4j.message.template.BaseTemplate;
import org.weixin4j.message.template.OPENTM206161737.OPENTM206161737;
import org.weixin4j.message.template.TM00619.TM00619;
import org.weixin4j.message.template.TM00622.TM00622;

import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.member.service.MemberService;
import com.tcode.business.order.dao.OrderHeadDao;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.service.OrderService;
import com.tcode.business.wechat.sys.model.WechatMsgTemplate;
import com.tcode.business.wechat.sys.model.WechatSendRecord;
import com.tcode.business.wechat.sys.service.WechatMsgTemplateService;
import com.tcode.business.wechat.sys.service.WechatSendRecordService;
import com.tcode.core.util.DataManageUtil;
import com.tcode.core.util.Utils;

/**
 * 初始化通知微信模板消息JOB
 * @author supeng
 *
 */
@Component
public class WechatNotifyInitJob {
	
	private static Logger SLog = Logger.getLogger("SLog");
	private static Logger msgNotifyLog = Logger.getLogger("msgNotifyLog");
	private static WechatSendRecordService wechatSendRecordService;
	private static OrderService orderService;
	private static WechatMsgTemplateService wechatMsgTemplateService;
	private static MemberService memberService;
	private static MemberCarService memberCarService;
	private static OrderHeadDao orderHeadDao;
	
	public static void main(String[] args) {
		/**
		 * 1、通过会员车辆信息获取下次保养时间、年检时间、保险时间。
		 * 2、微信发送列表获取上次发送时间。
		 * 3、通过模板ID从微信模板表中获取剩余天数和发送频率，与下次服务时间（保养时间、年检时间、保险时间）、上次发送时间一起运算是否满足发送短信需求。
		 * 4、满足条件则封装成WechatSendRecord对象，放入List,存入全局Map。不满足则不处理。超出剩余天数则将任务列表对应任务删除（逻辑删除或物理删除）
		 * 当前时间-下次服务时间（保养时间、年检时间、保险时间） = 实际剩余天数
		 * (当前时间-发送时间) >= 发送频率 ？ 满足发送条件 ：不满足
		 */
		System.out.println("我在初始化通知微信模板消息...");
		//初始化缓存数据（每次初始化通知类短信之前清空缓存数据，避免重复存储、发送）
		Map map = DataManageUtil.getMap();
		//初始化通知短信
		map.put("wechatNotifyList", null);
		initMsgNotify();
	}
	
	/**
	 * 初始化微信通知信息
	 */
	public static void initMsgNotify() {
		String currentTime = Utils.getSysTime("yyyy/MM/dd");//当前时间
		msgNotifyLog.warn("---------初始化通知微信模板消息开始：" + currentTime + "---------");
		try {
			int apartTime = 120; //获取90天内需要发送的消息
			//获取所有下次服务时间在指定时间内的车辆信息
			//type 类型 7-保险 6-保养 9-年检
			List<MemberCar> carInsuranceList = memberCarService.getListByApartNextTime(apartTime, 7);//保险
			List<MemberCar> carMaintainList = memberCarService.getListByApartNextTime(apartTime, 6);//保养
			List<MemberCar> carInspectionList = memberCarService.getListByApartNextTime(apartTime, 9);//年检
			msgNotifyLog.warn("保险、保养、年检符合条件数量" + carInsuranceList.size() + ":" + carMaintainList.size() + ":" + carInspectionList.size());
			Map<Integer, List<MemberCar>> memberCarMap = new HashMap<Integer, List<MemberCar>>();
			memberCarMap.put(7, carInsuranceList);
			memberCarMap.put(6, carMaintainList);
			memberCarMap.put(9, carInspectionList);
			
			//遍历满足条件的会员车辆信息（以服务方式不同存放于不同list）Map
			List<MemberCar> memberCars = null;
			for(int key : memberCarMap.keySet()) {
				memberCars = memberCarMap.get(key);
				
				//遍历不同服务类型每辆会员车辆信息
				MemberCar memberCar = null;
				String templateCode = "";
				for(int i=0; i<memberCars.size(); i++) {
					memberCar = memberCars.get(i);
					//type 类型 7-保险 6-保养 9-年检
					if(key == 7) {//保险
						templateCode = "TM00619";
					}else if (key == 6) {//保养
						templateCode = "TM00622";
					}else if (key == 9) {//年检
						templateCode = "OPENTM206161737";
					}
					
					int memberId = memberCar.getMemberId();
					Member member = memberService.getMemberById(memberId);
					String deptCode = member.getDeptCode();//门店编码
					String openId = member.getWechatNo();//微信ID
					String appId = member.getAppId();
					List<WechatMsgTemplate> wechatMsgTemplateList = wechatMsgTemplateService.getBydepNoAndTemplateNo(deptCode, templateCode);//获取对应门店模板信息
					
					if(!Utils.isEmpty(wechatMsgTemplateList) && wechatMsgTemplateList.size() > 0) {
						
						msgNotifyLog.warn("符合发送条件的车辆对应的会员信息：memberId:" + memberId + "carNum:" + memberCar.getCarShort() 
								+ memberCar.getCarCode() + memberCar.getCarNumber() + 
								"-templateCode:" + templateCode + "-openId:" + openId + "-appId:" + appId + "-deptCode:" + deptCode);
						
						if(!Utils.isEmpty(templateCode) && !Utils.isEmpty(openId) && !Utils.isEmpty(appId)) {
							//判断下次服务时间，对应门店、模板消息是否进入设置的发送天数
							WechatMsgTemplate wechatMsgTemplate = wechatMsgTemplateList.get(0);//取到对应微信模板
							int sendBDays = wechatMsgTemplate.getSendBDays();
							String nextDay = "";
							//type 类型 7-保险 6-保养 9-年检
							if(key == 7) {//保险
								nextDay = memberCar.getCarInsurance();//下次保险时间
							}else if (key == 6) {//保养
								nextDay = memberCar.getCarMaintain();
							}else if (key == 9) {//年检
								nextDay = memberCar.getCarInspection();
							}
							//距离下次服务时间天数 = 下次服务时间 - 当前时间
							if(Utils.daysBetween(nextDay, currentTime, "yyyy/MM/dd") <= sendBDays) {
							
								List<WechatSendRecord> wechatSendRecordList = wechatSendRecordService.getByTemplateCodeAndOpenIdForApp(templateCode, openId, appId, deptCode);//获取对应会员，对应模板最新发送信息
								if(wechatSendRecordList.size() > 0 && wechatMsgTemplateList.size() == 1) {//存在已发送的信息，则运算是否可以发送
									WechatSendRecord wechatSendRecord = wechatSendRecordList.get(0);
									int sendRate = wechatMsgTemplate.getSendRate();//发送频率
									String sendDate = wechatSendRecord.getUpdateTime();//最新发送时间
									sendDate = Utils.changeDateFormat(sendDate, "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd");
									//(当前时间-发送时间) >= 发送频率 ？ 满足发送条件 ：不满足
									if(Utils.daysBetween(currentTime, sendDate, "yyyy/MM/dd") >= sendRate) {
										//装成WecahtSendRecord对象，放入List,存入全局Map
										initMsgSendRecord(key, null, memberCar, member);
									}
								} else {//从未发送过则直接发送
									//装成WecahtSendRecord对象，放入List,存入全局Map
									initMsgSendRecord(key, null, memberCar, member);
								}
								
							}
						}
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			SLog.error(Utils.getErrorMessage(e));
		}finally {
			msgNotifyLog.warn("---------初始化通知微信模板消息结束：" + currentTime + "---------");
		}
	}
	
	/**
	 * 初始化短信发送记录
	 * @param orderHead
	 * @param missionID 短信任务ID
	 */
	public static void initMsgSendRecord(int type, HttpServletRequest request, MemberCar memberCar, Member member) {
		WechatSendRecord wechatSendRecord = new WechatSendRecord();
		String templateCode = "";
		//type 类型 7-保险 6-保养 9-年检
		if(type == 7) {//保险
			templateCode = "TM00619";
		}else if (type == 6) {//保养
			templateCode = "TM00622";
		}else if (type == 9) {//年检
			templateCode = "OPENTM206161737";
		}
		String companyId = member.getCompanyId();
		String deptCode = member.getDeptCode();//门店代码
		String vipNo = member.getVipNo();//会员号
		int memID = member.getMemId();//会员ID
		String openId = member.getWechatNo();
		String appId = member.getAppId();
		//获取会员信息
		try {
			BaseTemplate template = initConsumerWechatTemplate(type, request, memberCar, member);
			if(template != null) {
				String content = (String) template.getData();
				
				if(content != null) {
					wechatSendRecord.setCompanyId(companyId);
					wechatSendRecord.setDeptCode(deptCode);
					wechatSendRecord.setVipNo(vipNo);
					wechatSendRecord.setTemplateCode(templateCode);//模板编号
					wechatSendRecord.setOpenId(openId);
					wechatSendRecord.setAppId(appId);
					wechatSendRecord.setContent(content);
					wechatSendRecord.setStatus(1);//发送状态 0-成功，1-失败
					
					//通知类微信模板消息初始化信息写入日志
					StringBuffer logBuffer = new StringBuffer();
					logBuffer.append("通知类微信模板消息存入缓存：").append("门店：").append(deptCode).append("-").append("会员：").append(vipNo)
					.append("-").append("openId：").append(openId).append("-").append("模板编号：").append(templateCode)
					.append("-").append("内容：").append(content).append("-").append("状态：").append("1");
					msgNotifyLog.warn(logBuffer);
					
					//存入缓存
					Map map = DataManageUtil.getMap();
					List<WechatSendRecord> wechatSendRecordList = (List<WechatSendRecord>) map.get("wechatNotifyList");
					if(wechatSendRecordList == null) 
						wechatSendRecordList = new CopyOnWriteArrayList<WechatSendRecord>();
					wechatSendRecordList.add(wechatSendRecord);
					map.put("wechatNotifyList", wechatSendRecordList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			SLog.error(Utils.getErrorMessage(e));
		}
	}
	
	/**
	 * 初始化微信消通知类模板内容
	 * @param type
	 * @param request
	 * @param memberCar
	 * @param member
	 * @return
	 * @throws Exception
	 */
	private static BaseTemplate initConsumerWechatTemplate(int type, HttpServletRequest request, MemberCar memberCar, Member member) throws Exception {
		BaseTemplate baseTemplate = null;
		String name = member.getName();//称呼
		String first = "尊敬的" + name + "您好！";
		String carNo = memberCar.getCarShort() + memberCar.getCarCode() + memberCar.getCarNumber();
		String remark = "谢谢您的使用。";
		int memberId = member.getMemId();
		int carKilometers = memberCar.getCarKilometers() == null ? 0 : memberCar.getCarKilometers();
		//type 类型 7-保险 6-保养 9-年检
		if(type == 7) {
			first = "尊敬的" + name + ",您的车牌为" + carNo + "的汽车保险即将到期";
			remark = "请及时续保\\n若保险时间提醒有误，可重新设置保险时间。";
			String upDate = Utils.changeDateFormat(Utils.dateApart(-365), "yyyy/MM/dd", "yyyy年MM月dd日");
			String nextDate = Utils.changeDateFormat(memberCar.getCarInsurance(), "yyyy/MM/dd", "yyyy年MM月dd日");
			baseTemplate = new TM00619(first, upDate, nextDate, remark, null);
		}else if (type == 6) {//保养
			first = "尊敬的" + name + ",您的车牌为" + carNo + "的汽车保养即将到期";
			remark = "若保养时间提醒有误，可重新设置保养时间！\\n温馨提示：保养到期时间是通过分析您以往的保养记录而计算出的结果，保养数据越完整，分析结果越准确。";
			OrderHead orderHead = orderHeadDao.loadPreOrderByMemIdAndKilo(memberId, carKilometers);//根据会员和车辆行驶公里数推算上次保养时间
			String upDate = "";
			if(orderHead != null ) 
				upDate = Utils.changeDateFormat(orderHead.getSaleDate(), "yyyy/MM/dd", "yyyy年MM月dd日");
			String nextDate = Utils.changeDateFormat(memberCar.getCarMaintain(), "yyyy/MM/dd", "yyyy年MM月dd日");
			
			baseTemplate = new TM00622(first, nextDate, upDate, carKilometers + "公里", remark, null);
		}else if (type == 9) {//年检
			first = "尊敬的" + name + ",您的车牌为" + carNo + "的汽车年检即将到期";
			remark = "在办理年检之前，请确认无任何未处理违法记录。";
			String nextDate = Utils.changeDateFormat(memberCar.getCarInspection(), "yyyy/MM/dd", "yyyy年MM月dd日");
			baseTemplate = new OPENTM206161737(first, carNo, nextDate, remark, null);
		}
		
		return baseTemplate;
	}
	

	public OrderService getOrderService() {
		return orderService;
	}

	@Resource
	public void setOrderService(OrderService orderService) {
		WechatNotifyInitJob.orderService = orderService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	@Resource
	public void setMemberService(MemberService memberService) {
		WechatNotifyInitJob.memberService = memberService;
	}

	public MemberCarService getMemberCarService() {
		return memberCarService;
	}

	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		WechatNotifyInitJob.memberCarService = memberCarService;
	}

	public WechatSendRecordService getWechatSendRecordService() {
		return wechatSendRecordService;
	}

	@Resource
	public void setWechatSendRecordService(WechatSendRecordService wechatSendRecordService) {
		WechatNotifyInitJob.wechatSendRecordService = wechatSendRecordService;
	}

	public WechatMsgTemplateService getWechatMsgTemplateService() {
		return wechatMsgTemplateService;
	}

	@Resource
	public void setWechatMsgTemplateService(WechatMsgTemplateService wechatMsgTemplateService) {
		WechatNotifyInitJob.wechatMsgTemplateService = wechatMsgTemplateService;
	}

	public OrderHeadDao getOrderHeadDao() {
		return orderHeadDao;
	}

	@Resource
	public void setOrderHeadDao(OrderHeadDao orderHeadDao) {
		WechatNotifyInitJob.orderHeadDao = orderHeadDao;
	}

	
}
