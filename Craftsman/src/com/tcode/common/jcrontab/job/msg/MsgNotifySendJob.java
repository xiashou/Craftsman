package com.tcode.common.jcrontab.job.msg;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.msg.service.MsgSendRecordService;
import com.tcode.common.message.HttpSender;
import com.tcode.common.message.MsgUtil;
import com.tcode.core.util.DataManageUtil;
import com.tcode.core.util.Utils;

/**
 * 发送通知类短信JOB
 * @author supeng
 *
 */
@Component
public class MsgNotifySendJob {
	
	private static Logger msgLog = Logger.getLogger("msgLog");
	private static Logger SLog = Logger.getLogger("SLog");
	
	private static MsgSendRecordService msgSendRecordService;
	
	public static void main(String[] args) {
		System.out.println("我在发送通知类短信...");
		//扫描缓存数据，发送短信
		sendMsgNotify();
	}
	
	/**
	 * 发送通知类短信
	 */
	public static void sendMsgNotify() {
		Map map = DataManageUtil.getMap();
		List<MsgSendRecord> msgSendRecordList = (List<MsgSendRecord>) map.get("msgNotifyList");
		
		if(msgSendRecordList != null) {
			for(MsgSendRecord msgSendRecord : msgSendRecordList) {
				try {
					//发送短信
					MsgUtil.sendMsg(msgSendRecord);
				} catch (Exception e) {
					e.printStackTrace();
					SLog.error(Utils.getErrorMessage(e));
				}
				msgSendRecordList.remove(msgSendRecord);
			}
		}
	}

	public MsgSendRecordService getMsgSendRecordService() {
		return msgSendRecordService;
	}

	@Resource
	public void setMsgSendRecordService(MsgSendRecordService msgSendRecordService) {
		MsgNotifySendJob.msgSendRecordService = msgSendRecordService;
	}
	
}
