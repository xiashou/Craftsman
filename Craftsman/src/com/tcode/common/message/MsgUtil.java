package com.tcode.common.message;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.weixin4j.Weixin;
import org.weixin4j.message.template.BaseTemplate;
import org.weixin4j.message.template.OPENTM202665053.OPENTM202665053;
import org.weixin4j.message.template.OPENTM203424414.OPENTM203424414;
import org.weixin4j.message.template.OPENTM203462646.OPENTM203462646;
import org.weixin4j.message.template.OPENTM401447462.OPENTM401447462;
import org.weixin4j.message.template.OPENTM405637284.OPENTM405637284;
import org.weixin4j.message.template.OPENTM406019708.OPENTM406019708;
import org.weixin4j.message.template.OPENTM410192957.OPENTM410192957;
import org.weixin4j.message.template.TM00504.TM00504;

import com.tcode.business.goods.model.GoodsPackage;
import com.tcode.business.goods.service.GoodsPackageService;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberBook;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.member.service.MemberService;
import com.tcode.business.member.service.MemberStockService;
import com.tcode.business.msg.model.MsgMission;
import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.msg.model.MsgTemplate;
import com.tcode.business.msg.service.MsgMissionService;
import com.tcode.business.msg.service.MsgSendRecordService;
import com.tcode.business.msg.service.MsgTemplateService;
import com.tcode.business.msg.util.MsgChargingUtil;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;
import com.tcode.business.order.service.OrderService;
import com.tcode.business.order.util.OrderUtil;
import com.tcode.business.report.model.ReptRecharge;
import com.tcode.business.report.service.ReptRechargeService;
import com.tcode.business.shop.model.Setting;
import com.tcode.business.shop.service.SettingService;
import com.tcode.business.wechat.act.model.ActJoiner;
import com.tcode.business.wechat.act.model.ActSignup;
import com.tcode.business.wechat.act.service.ActSignupService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.model.WechatSendRecord;
import com.tcode.business.wechat.sys.service.WechatAuthorizerParamsService;
import com.tcode.business.wechat.sys.service.WechatSendRecordService;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysDept;
import com.tcode.system.service.SysDeptService;
import com.tcode.system.util.SysDeptUtil;

/**
 * 短信发送接口
 * @author supeng
 *
 */
@Component
public class MsgUtil {
	
	private static Logger msgLog = Logger.getLogger("msgLog");
	private static Logger SLog = Logger.getLogger("SLog");
	private static OrderService orderService;
	private static SysDeptService sysDeptService;
	private static ActSignupService actSignupService;
	private static MemberService memberService;
	private static SettingService settingService;
	private static MsgTemplateService msgTemplateService;
	private static MsgSendRecordService msgSendRecordService;
	private static MsgMissionService msgMissionService;
	private static GoodsPackageService goodsPackageService;
	private static MemberCarService memberCarService;
	private static MemberStockService memberStockService;
	private static ReptRechargeService reptRechargeService;
	private static WechatSendRecordService wechatSendRecordService;
	private static WechatAuthorizerParamsService wechatAuthorizerParamsService;
	
	/**
	 * 消费类短信发送（项目消费、套餐购买、客户充值、下单）
	 * @param templateTypeNo 模板类型编码
	 * @param ordeID 订单ID
	 * @return
	 */
	public static String sendConsumerMsg(int templateTypeNo, String orderID) {
		
		try {
			//获取订单信息
			OrderHead orderHead = orderService.getHeadByOrderNo(orderID);
			
			//获取会员信息
			int memID = orderHead.getMemId();//会员ID
			String vipNO = orderHead.getVipNo();//会员号
			Member member = memberService.getMemberById(memID);
			String mobile = member.getMobile();//手机号
			
			//获取短信模板信息
			String deptCode = orderHead.getDeptCode();//门店编码
			List<MsgTemplate> msgTemplateList = msgTemplateService.getByTemplateTypeNo(deptCode, templateTypeNo);
			MsgTemplate msgTemplate = null;
			
			if(msgTemplateList.size() == 1 && !Utils.isEmpty(mobile)) {//获取到有效的模板信息、且手机号有效才推送短信
				msgTemplate = msgTemplateList.get(0);
				//初始化短信内容
				String content = initConsumerContent(orderHead, member, msgTemplate);//短信内容
			
				if(!Utils.isEmpty(content)) {
					//记录发送信息
					MsgSendRecord msgSendRecord = new MsgSendRecord();
					msgSendRecord.setContent(content);
					msgSendRecord.setVipNo(vipNO);
					msgSendRecord.setDeptCode(deptCode);
					msgSendRecord.setMobile(mobile);
					msgSendRecord.setOrderId(orderID);
					
					//短息发送
					sendMsg(msgSendRecord);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			SLog.error(Utils.getErrorMessage(e));
		}
		return null;
	}
	
	/**
	 * 消费类短信和微信消息发送（项目消费、套餐购买、客户充值、下单）
	 * @param request
	 * @param templateTypeNo
	 * @param orderID
	 * @return
	 * @throws Exception
	 */
	public static String sendConsumerMsg(HttpServletRequest request, int templateTypeNo, String orderID) throws Exception {
		sendConsumerMsg(templateTypeNo, orderID);
		if(templateTypeNo == 4) templateTypeNo = 12;
		sendConsumerWechatTemplateMsg(templateTypeNo, request, orderID);
		
		return null;
	}
	
	/**
	 * 活动报名成功消息发送
	 * @param request
	 * @param templateNo
	 * @param joiner
	 * @throws Exception
	 */
	public static void sendActivityMsg(HttpServletRequest request, String templateNo, ActJoiner joiner) throws Exception {
		MsgSendRecord msgSendRecord = null;
		String first = "尊敬的" + joiner.getNickName() + "，您已经成功报名，请按时参与这一期的活动！";
		String remark = "活动咨询电话： ";
		String actName = "", time = "", address = "", name = "";
		ActSignup signup = actSignupService.getById(joiner.getActId());
		if(!Utils.isEmpty(signup)){
			remark += signup.getContact();
			actName = signup.getName();
			time = signup.getDateStart() + "-" + signup.getDateEnd();
			address = signup.getAddress();
			name = joiner.getRealName();
			BaseTemplate baseTemplate = new OPENTM202665053(first, actName, time, address, name, remark, null);
			new Weixin().sendTemplateMsgByAuthorizerAppId(signup.getAppId(), joiner.getOpenId(), templateNo, baseTemplate);
			
			//短信发送
			List<WechatAuthorizerParams> authorParams = wechatAuthorizerParamsService.getByAuthorizerAppId(signup.getAppId());
			if(!Utils.isEmpty(authorParams) && authorParams.size() > 0){
				msgSendRecord = new MsgSendRecord();
				
				msgSendRecord.setDeptCode(authorParams.get(0).getDeptCode());
				msgSendRecord.setVipNo("ACT" + signup.getId());
				msgSendRecord.setMobile(joiner.getMobile());
				msgSendRecord.setContent(first + "编号：" + joiner.getId() + "。活动名称：" + actName + "。 活动时间：" + time + "。 活动地址：" + address + "。 " + remark + "【" + signup.getOrganization() + "】" );
				sendMsg(msgSendRecord);
			}
			
		}
	}
	
	/**
	 * 预约成功提醒
	 * @param request
	 * @param templateNo
	 * @param deptName
	 * @param member
	 * @param book
	 * @throws Exception
	 */
	public static void sendBookMsg(HttpServletRequest request, String templateNo, Member member, MemberBook book) throws Exception {
		String first = "尊敬的车主，您已成功预约！";
		String remark = "如果您不能按时到店，请联系我们，感谢您的支持。";
		if(!Utils.isEmpty(member)){
			SysDept dept = sysDeptService.getByDeptCode(member.getDeptCode());
			BaseTemplate baseTemplate = new OPENTM410192957(first, book.getBookTime(), dept.getDeptName(), book.getService(), remark, null);
			new Weixin().sendTemplateMsgByAuthorizerAppId(member.getAppId(), member.getWechatNo(), templateNo, baseTemplate);
		}
	}
	
	/**
	 * 发送微信消费类模板信息
	 * @param request
	 * @param orderID
	 * @throws Exception
	 */
	public static void sendConsumerWechatTemplateMsg(int templateTypeNo, HttpServletRequest request, String orderID) throws Exception {
		
		//获取订单信息
		OrderHead orderHead = orderService.getHeadByOrderNo(orderID);
		//消费类型1, '项目消费' ], [ 2, '套餐购买' ], [ 3, '客户退货' ], [ 4, '会员充值' ], [ 5, '保险理赔' ], [ 6, '散客开单' ], [7, '套餐扣次']
		String type = orderHead.getOrderType();
		//特殊模板处理（其他消息类型无法完全通过订单类型体现，此处通过"短信"模板编号进行区分）
		if(templateTypeNo == 11) type = templateTypeNo + ""; 
		if(templateTypeNo == 12) type = templateTypeNo + ""; 
		String templateNo = "TM00504";
		switch (type) {
			case "2"://套餐购买
				templateNo = "OPENTM203462646";
				break;
			case "4"://会员充值
				templateNo = "OPENTM203424414";
				break;
			case "7"://套餐扣次
				templateNo = "OPENTM405637284";
				break;
			case "11"://提车通知
				templateNo = "OPENTM401447462";
				break;
			case "12"://开单成功通知
				templateNo = "OPENTM406019708";
				break;
			default:
				break;
		}
		
		//获取会员信息
		int memID = orderHead.getMemId();//会员ID
		Member member = memberService.getMemberById(memID);
		String deptCode = orderHead.getDeptCode();//门店编码
		
		//初始化微信模板消息内容,并发送模板消息
		String openId = member.getWechatNo();
		if(!Utils.isEmpty(openId)) {
			BaseTemplate baseTemplate = initConsumerWechatTemplate(templateTypeNo, request, orderHead, member);
			new Weixin().sendTemplateMsgByDeptCode(deptCode, openId, templateNo, baseTemplate);
		}
	}

	/**
	 * 初始化微信消费类模板内容
	 * @param orderHead
	 * @param member
	 * @return
	 * @throws Exception 
	 */
	private static BaseTemplate initConsumerWechatTemplate(int templateTypeNo, HttpServletRequest request, OrderHead orderHead, Member member) throws Exception {
		
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + Utils.convertServerName(request.getServerName()) + path + "/";
		BaseTemplate baseTemplate = null;
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		//消费类型1, '项目消费' ], [ 2, '套餐购买' ], [ 3, '客户退货' ], [ 4, '会员充值' ], [ 5, '保险理赔' ], [ 6, '散客开单' ], [7, '套餐扣次']
		String orderId = orderHead.getOrderId();
		OrderItem orderItem = null;
		String typeValue = "";
		List<OrderItem> orderItemList = orderService.getItemByOrderNo(orderId);
		if(orderItemList.size() > 0) orderItem = orderItemList.get(0);
		typeValue = OrderUtil.initConsumerItem(orderItemList);
		String name = member.getName();//称呼
		String sex = member.getSex();//性别 1男 2女 0未知
		String nowBalance = member.getBalance() + "";//当前余额
		String nowPoint = member.getPoint() + "";//当前积分
		Integer memId = member.getMemId();
		if("1".equals(sex)) name = name + "先生";
		else if("2".equals(sex)) name = name + "女士";
		String carNum = orderHead.getCarNumber();
		String first = "尊敬的" + name + "，您的爱车" + carNum + "消费信息如下";
		String saleTime = dateFormat2.format(dateFormat1.parse(orderHead.getSaleDate()));//消费时间
		String nowTime = dateFormat2.format(dateFormat1.parse(Utils.getSysTime()));//当前时间
		String deptCode = orderHead.getDeptCode();//门店
		String type = orderHead.getOrderType();
		Setting setting = settingService.getById(deptCode);
		
		
		//特殊模板处理（其他消息类型无法完全通过订单类型体现，此处通过模板编号进行区分）
		if(templateTypeNo == 11) type = templateTypeNo + ""; 
		if(templateTypeNo == 12) type = templateTypeNo + ""; 
		
		String money = OrderUtil.initPayType(orderHead) + " " + orderHead.getAprice() + "元";
		String point = orderHead.getPoint() + "";
		String remark = "谢谢您的使用，点击查看详情。";
		
		
		WechatAuthorizerParams wechatAuthorizerParams =  WechatAuthorizerParamsUtil.getWechatAuthorizerParamsByDeptCode(deptCode);
		SysDept sysDept = SysDeptUtil.getSysDeptByDeptCode(deptCode);
		//如果根据门店编码没有取到授权信息，则根据公司ID获取（一公司下多门店公用公众号）
    	if(wechatAuthorizerParams == null || Utils.isEmpty(wechatAuthorizerParams.getAuthorizerAccessToken())) {
    		wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsByCompanyId(sysDept.getCompanyId());
    	}
		String sid = wechatAuthorizerParams.getSid();
		String org = sysDept.getDeptName();
		if("1".equals(type)) {//项目消费
			remark = "获得" + point + "积分，" + OrderUtil.initPointInfo(orderHead) + "当前积分：" + nowPoint + "，当前余额：" + nowBalance + "元。";
			baseTemplate = new TM00504(first, saleTime, org, typeValue, money, "见下方", remark, null);
			baseTemplate.setUrl(basePath + "/wct/center/orderRecordDetail.jsp?sid=" + sid + "&orderId=" + orderId);
		}else if("2".equals(type)) {//套餐购买
			first = "尊敬的" + name + "，您已开通以下套餐：";
			String goodsID = orderItem.getGoodsId();//产品ID
			GoodsPackage goodsPackage = goodsPackageService.getGoodsPackageById(goodsID);
			String packageName = goodsPackage.getName();
//			String bDate = Utils.changeDateFormat(goodsPackage.getStartDate(), "yyyy/MM/dd", "yyyy年MM月dd日");
//			String eDate = Utils.changeDateFormat(goodsPackage.getEndDate(), "yyyy/MM/dd", "yyyy年MM月dd日");
			String bDate = Utils.changeDateFormat(new SimpleDateFormat("yyyy/MM/dd").format(new Date()), "yyyy/MM/dd", "yyyy年MM月dd日");
			String eDate = Utils.changeDateFormat(Utils.dateApart(Utils.isEmpty(goodsPackage.getExpire()) ? 0 : goodsPackage.getExpire()), "yyyy/MM/dd", "yyyy年MM月dd日");
			baseTemplate = new OPENTM203462646(first, carNum, packageName, bDate, eDate, remark, null);
			baseTemplate.setUrl(basePath + "/wct/center/memberStock.jsp?sid=" + sid );
		}else if("3".equals(type)) {//客户退货
			
		}else if("4".equals(type)) {//会员充值
			remark = "谢谢您的使用！";
			first = "尊敬的" + name + "，您的会员卡充值操作成功：";
			List<ReptRecharge> reptRechargeList = reptRechargeService.getByOrder(orderId, "M");
			String give = "---";
			if(reptRechargeList.size() > 0) give = reptRechargeList.get(0).getNumber() + "";
			String mode = "门店充值-" + org;//充值方式
			baseTemplate = new OPENTM203424414(first, nowTime, money, mode, give, nowBalance, remark, null);
			//baseTemplate.setUrl(basePath + "/wct/center/memberStock.jsp?sid=" + sid );
		}else if("5".equals(type)) {//保险理赔
			
		}else if("6".equals(type)) {//散客开单
			
		}else if("7".equals(type)) {//套餐扣次
			DecimalFormat decimalFormat = new DecimalFormat("###0");//格式化设置 
			first = "尊敬的" + name + "，您的卡片在" + org + "进行了扣次：";
			Integer carId = orderHead.getCarId();
			String goodsId = orderItem.getGoodsId();
			String number = decimalFormat.format(orderItem.getNumber()) + "次";//消费数量，即套餐扣除次数
			String goodsNum = decimalFormat.format(Utils.isEmpty(goodsId)?0.0:memberStockService.getGoodsNum(memId, goodsId)) + "次";//库存数
			MemberCar memberCar = memberCarService.getMemberCarById(carId);
			String carKilometers = memberCar.getCarKilometers() + "公里";
			typeValue = typeValue + ",扣除" + number;
			baseTemplate = new OPENTM405637284(first, typeValue, nowTime, carKilometers, "车辆状态良好", goodsNum, remark, null);
			baseTemplate.setUrl(basePath + "/wct/center/memberStock.jsp?sid=" + sid );
		}else if("11".equals(type)) {//提车通知
			first = "尊敬的" + name + "，您的车辆服务已完工！";
			String finishTime = dateFormat2.format(dateFormat1.parse(orderHead.getCreateTime()));//创建时间（执行未完成订单时，先删除原有订单再创建新订单，所以创建即完成时间或修改时间）
			int takeTimeHours = Utils.hoursBetween(saleTime, finishTime, "yyyy年MM月dd日 HH:mm");
			int takeDays = takeTimeHours / 24;
			String takeHours = takeTimeHours % 24 + "";
			String takeTime = takeDays + "天" + takeHours + "小时";
			remark = "感谢您的耐心等待！";
			baseTemplate = new OPENTM401447462(first, carNum, saleTime, finishTime, takeTime, money, remark, null);
		}else if("12".equals(type)) {//下单成功通知
			first = "尊敬的" + name + "，您已成功下单，我们正在为您指派技师！";
			StringBuffer detail = new StringBuffer();
			if(orderItemList.size()>0) detail.append(orderItemList.get(0).getGoodsName() + ",");
			if(orderItemList.size()>1) detail.append(orderItemList.get(1).getGoodsName() + ",");
			if(orderItemList.size()>2) detail.append(orderItemList.get(2).getGoodsName() + ",");
			remark = "客服电话:" + (!Utils.isEmpty(setting) && !Utils.isEmpty(setting.getPhone()) ? setting.getPhone() : "");
			baseTemplate = new OPENTM406019708(first, orderId, saleTime, detail.toString(), "下单成功,订单分配中", remark, null);
		}
		
		return baseTemplate;
	}

	/**
	 * 通知类短信发送
	 * @param templateTypeNo 模板类型编码
	 * @param ordeID 订单ID
	 * @return
	 */
	public static String sendNotifyMsg(int templateTypeNo, String orderID) {
		
		try {
			//获取订单信息
			OrderHead orderHead = orderService.getHeadByOrderNo(orderID);
		
			String deptCode = orderHead.getDeptCode();
			String vipNo = orderHead.getVipNo();
			
			//插入任务信息
			MsgMission msgMission = new MsgMission();
			msgMission.setTemplateTypeNo(templateTypeNo);//模板类型编码
			msgMission.setDeptCode(deptCode);//门店代码
			msgMission.setVipNo(vipNo);//会员卡号
			msgMission.setOrderID(orderID);//订单ID
			
			msgMissionService.insert(msgMission);
		
		} catch (Exception e) {
			e.printStackTrace();
			SLog.error(Utils.getErrorMessage(e));
		}
		return null;
	}
	
	
	/**
	 * 初始化消费类短信内容
	 * @param orderHead 订单主表
	 * @param orderItemList 订单行项目表
	 * @param member 会员信息
	 * @param msgTemplate 模板信息
	 * @return
	 */
	private static String initConsumerContent(OrderHead orderHead, Member member, MsgTemplate msgTemplate) {
		String orderType = orderHead.getOrderType();//订单类型 1-项目消费，2-套餐购买，3-客户退货，4-会员充值，5-保险理赔
		String ordeID = orderHead.getOrderId();
		List<OrderItem> orderItemList = null;
		String content = "";
		try {
			orderItemList = orderService.getItemByOrderNo(ordeID);
			
			//获得动态内容
			//{1}:称呼、{2}:项目名、{3}:消费金额、{4}:所得积分、{5}:当前余额、{6}:车牌、{7}:剩余天数、{8}:消费时间、{9}:公里数、{10}:有效期
			//{11}:支付方式
			String name = member.getName();//称呼
			String sex = member.getSex();//性别 1男 2女 0未知
			String projectName = "";//项目名
			String goodsID = "";//产品ID
			String endDate = "";//套餐结束日期（有效期）
			String carNum = orderHead.getCarNumber();
			String saleTime = Utils.changeDateFormat(orderHead.getSaleDate(), "yyyy/MM/dd HH:mm", "yyyy年MM月dd日 HH:mm");
			String payType = OrderUtil.initPayType(orderHead);
			
			if("1".equals(sex)) name = name + "先生";
			else if("2".equals(sex)) name = name + "女士";
			
			if(!"4".equals(orderType)) {//订单类型不为充值类型时 组装消费具体项目名
				OrderItem orderItem = orderItemList.get(0);
				projectName = orderItem.getGoodsName();//项目名
				
				if("2".equals(orderType)) {//如果订单类型为套餐购买，获取套餐信息
					goodsID = orderItem.getGoodsId();//产品ID
					GoodsPackage goodsPackage = goodsPackageService.getGoodsPackageById(goodsID);
//					endDate = goodsPackage.getEndDate();
					endDate = Utils.dateApart(Utils.isEmpty(goodsPackage.getExpire()) ? 0 : goodsPackage.getExpire());
				}
			}
			double price = orderHead.getAprice();//消费金额
			int point = orderHead.getPoint();//所得积分
			double balance = member.getBalance();//当前余额
			
			//初始化信息
			content = msgTemplate.getContent();
			content = content.replace("{1}", name).replace("{2}", projectName).replace("{3}", price + "")
			.replace("{4}", point + "").replace("{5}", balance + "").replace("{6}", carNum)
			.replace("{7}", "").replace("{8}", saleTime).replace("{9}", "")
			.replace("{10}", endDate).replace("{11}", payType);
			
		} catch (Exception e) {
			content = null;
			e.printStackTrace();
			SLog.error(Utils.getErrorMessage(e));
		}
		
		return content;
	}

	/**
	 * 短信发送
	 * @param msgSendRecord
	 * @throws Exception
	 */
	public static void sendMsg(MsgSendRecord msgSendRecord) throws Exception {
		
		String vipNO = msgSendRecord.getVipNo();//会员号
		String mobile = msgSendRecord.getMobile();//手机号
		String content = msgSendRecord.getContent();//短信内容
		String deptCode = msgSendRecord.getDeptCode();//店柜编码
		String orderID = msgSendRecord.getOrderId();//订单ID
		int missionID = msgSendRecord.getMissionID();//任务ID
		
		//根据部门ID检查计数计费状态
		boolean sendFlag = MsgChargingUtil.checkChargingStatus(deptCode);
		int sendStatus = 1;
		String remark = "";
		if(sendFlag) {
			//短信发送
			sendStatus = HttpSender.batchSend2(mobile, content);
			if(sendStatus == 0) //发送成功则进行计数计费
				MsgChargingUtil.chargingMsg(deptCode);
			else remark = "发送异常";
		} else 
			remark = "余额不足";
		
		//日志记录
		StringBuffer logBuffer = new StringBuffer();
		logBuffer.append("门店：").append(deptCode).append("-").append("会员：").append(vipNO)
		.append("-").append("手机：").append(mobile).append("-").append("订单：").append(orderID)
		.append("-").append("任务ID：").append(missionID)
		.append("-").append("内容：").append(content).append("-").append("状态：").append(sendStatus);
		msgLog.warn(logBuffer);
		
		//记录发送信息
		msgSendRecord.setStatus(sendStatus);//发送状态 0-成功，1-失败
		msgSendRecord.setRemark(remark);
		msgSendRecordService.insert(msgSendRecord);
	}
	
	/**
	 * 发送微信模板消息，并记录
	 * @param wechatSendRecord
	 * @throws Exception
	 */
	public static void sendWechatTemplateMsg(WechatSendRecord wechatSendRecord) throws Exception {
		BaseTemplate baseTemplate = new BaseTemplate();
		baseTemplate.setData(wechatSendRecord.getContent());
		String openId = wechatSendRecord.getOpenId();
		String deptCode = wechatSendRecord.getDeptCode();
		String templateNo = wechatSendRecord.getTemplateCode();
		if(!Utils.isEmpty(openId)) {
			SLog.warn("WARNNNN:" + openId + "|" + deptCode);
			new Weixin().sendTemplateMsgByDeptCode(deptCode, openId, templateNo, baseTemplate);
		}
		wechatSendRecord.setStatus(0);//发送状态 0-成功，1-失败
		wechatSendRecordService.insert(wechatSendRecord);
	}

	
	public OrderService getOrderService() {
		return orderService;
	}

	@Resource
	public void setOrderService(OrderService orderService) {
		MsgUtil.orderService = orderService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	@Resource
	public void setMemberService(MemberService memberService) {
		MsgUtil.memberService = memberService;
	}

	public MsgTemplateService getMsgTemplateService() {
		return msgTemplateService;
	}

	@Resource
	public void setMsgTemplateService(MsgTemplateService msgTemplateService) {
		MsgUtil.msgTemplateService = msgTemplateService;
	}

	public MsgSendRecordService getMsgSendRecordService() {
		return msgSendRecordService;
	}

	@Resource
	public void setMsgSendRecordService(MsgSendRecordService msgSendRecordService) {
		MsgUtil.msgSendRecordService = msgSendRecordService;
	}

	public MsgMissionService getMsgMissionService() {
		return msgMissionService;
	}

	@Resource
	public void setMsgMissionService(MsgMissionService msgMissionService) {
		MsgUtil.msgMissionService = msgMissionService;
	}

	public GoodsPackageService getGoodsPackageService() {
		return goodsPackageService;
	}

	@Resource
	public void setGoodsPackageService(GoodsPackageService goodsPackageService) {
		MsgUtil.goodsPackageService = goodsPackageService;
	}

	public MemberCarService getMemberCarService() {
		return memberCarService;
	}

	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		MsgUtil.memberCarService = memberCarService;
	}

	public MemberStockService getMemberStockService() {
		return memberStockService;
	}

	@Resource
	public void setMemberStockService(MemberStockService memberStockService) {
		MsgUtil.memberStockService = memberStockService;
	}

	public ReptRechargeService getReptRechargeService() {
		return reptRechargeService;
	}

	@Resource
	public void setReptRechargeService(ReptRechargeService reptRechargeService) {
		MsgUtil.reptRechargeService = reptRechargeService;
	}

	public WechatSendRecordService getWechatSendRecordService() {
		return wechatSendRecordService;
	}

	@Resource
	public void setWechatSendRecordService(WechatSendRecordService wechatSendRecordService) {
		MsgUtil.wechatSendRecordService = wechatSendRecordService;
	}

	public SettingService getSettingService() {
		return settingService;
	}
	
	@Resource
	public void setSettingService(SettingService settingService) {
		MsgUtil.settingService = settingService;
	}

	public ActSignupService getActSignupService() {
		return actSignupService;
	}
	@Resource
	public void setActSignupService(ActSignupService actSignupService) {
		MsgUtil.actSignupService = actSignupService;
	}

	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}
	@Resource
	public void setSysDeptService(SysDeptService sysDeptService) {
		MsgUtil.sysDeptService = sysDeptService;
	}

	public WechatAuthorizerParamsService getWechatAuthorizerParamsService() {
		return wechatAuthorizerParamsService;
	}
	@Resource
	public void setWechatAuthorizerParamsService(
			WechatAuthorizerParamsService wechatAuthorizerParamsService) {
		MsgUtil.wechatAuthorizerParamsService = wechatAuthorizerParamsService;
	}
	
}
