package com.tcode.business.msg.util;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.msg.model.MsgCharging;
import com.tcode.business.msg.service.MsgChargingService;

@Component
public class MsgChargingUtil {
	
	private static MsgChargingService msgChargingService;
	
	/**
	 * 短信计数计费
	 * @param deptCode
	 * @throws Exception 
	 */
	public static void chargingMsg(String deptCode) throws Exception {
		
		//根据店柜查询是否存在相应数据
		List<MsgCharging> msgChargingList = msgChargingService.getByDeptCode(deptCode);
		MsgCharging msgCharging = null;
		if(msgChargingList.size() == 1) {//如存在则进行计数计费，已发送加一，剩余条数减一
			msgCharging = msgChargingList.get(0);
			int remainingNum = msgCharging.getRemainingNum() -1;//剩余条数
			int sendNum = msgCharging.getSendNum() + 1;//已发送条数
			msgCharging.setSendNum(sendNum);
			msgCharging.setRemainingNum(remainingNum);
			msgCharging.setUpdateBy("sys");
			msgChargingService.update(msgCharging);
			
		}else {//不存在则初始化一条计数计费信息
			msgCharging = new MsgCharging();
			msgCharging.setDeptCode(deptCode);
			msgCharging.setSendNum(1);
			msgCharging.setRemainingNum(10);//初始可发送条数为10
			msgCharging.setCreateBy("sys");
			msgCharging.setUpdateBy("sys");
			
			msgChargingService.insert(msgCharging);
		}
		
	}
	
	/**
	 * 根据店柜编码检测计数计费状态，剩余条数为0，则允许发送短信
	 * @param deptCode
	 * @return true 可以发送， false 条数已用完，不能发送
	 * @throws Exception 
	 */
	public static boolean checkChargingStatus(String deptCode) throws Exception {
		boolean flag = false;
		//根据店柜查询是否存在相应数据
		List<MsgCharging> msgChargingList = msgChargingService.getByDeptCode(deptCode);
		MsgCharging msgCharging = null;
		if(msgChargingList.size() == 1) {//如存在则检查其状态
			msgCharging = msgChargingList.get(0);
			int remainingNum = msgCharging.getRemainingNum();//剩余条数
			if(remainingNum > 0) //如果剩余条数为0，则不允许发送短信
				flag = true;
		}else {//不存在则初始化一条计数计费信息
			msgCharging = new MsgCharging();
			msgCharging.setDeptCode(deptCode);
			msgCharging.setSendNum(1);
			msgCharging.setRemainingNum(10);//初始可发送条数为10
			msgCharging.setCreateBy("sys");
			msgCharging.setUpdateBy("sys");
			
			msgChargingService.insert(msgCharging);
		}
		return flag;
	}

	
	public static MsgChargingService getMsgChargingService() {
		return msgChargingService;
	}

	@Resource
	public void setMsgChargingService(MsgChargingService msgChargingService) {
		MsgChargingUtil.msgChargingService = msgChargingService;
	}

}
