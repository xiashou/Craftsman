package com.tcode.common.jcrontab.job.msg;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.common.message.MsgUtil;
import com.tcode.core.util.DataManageUtil;
import com.tcode.core.util.Utils;

/**
 * 发送生日提醒短信
 * @author supeng
 *
 */
public class MsgNotifyBirthdaySendJob {
	
	private static Logger msgLog = Logger.getLogger("msgLog");
	private static Logger SLog = Logger.getLogger("SLog");
	
	public static void main(String[] args) {
		System.out.println("我在发送生日提醒短信...");
		//扫描缓存数据，发送短信
		sendMsgNotifyBirthday();
	}

	/**
	 * 发送生日提醒短信
	 */
	public static void sendMsgNotifyBirthday() {
		Map map = DataManageUtil.getMap();
		List<MsgSendRecord> msgSendRecordList = (List<MsgSendRecord>) map.get("msgNotifyBirthdayList");
		
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
	
}
