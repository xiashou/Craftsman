package com.tcode.common.jcrontab.job.wechat;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.msg.service.MsgSendRecordService;
import com.tcode.business.wechat.sys.model.WechatSendRecord;
import com.tcode.common.message.HttpSender;
import com.tcode.common.message.MsgUtil;
import com.tcode.core.util.DataManageUtil;
import com.tcode.core.util.Utils;

/**
 * 送通知类微信模板消息JOB
 * @author supeng
 *
 */
@Component
public class WechatNotifySendJob {
	
	private static Logger msgLog = Logger.getLogger("msgLog");
	private static Logger SLog = Logger.getLogger("SLog");
	
	private static MsgSendRecordService msgSendRecordService;
	
	public static void main(String[] args) {
		System.out.println("我在发送通知类微信模板消息...");
		//扫描缓存数据，发送微信模板消息
		sendWechatNotify();
	}
	
	/**
	 * 发送通知类微信模板消息
	 */
	public static void sendWechatNotify() {
		Map map = DataManageUtil.getMap();
		List<WechatSendRecord> wechatSendRecordList = (List<WechatSendRecord>) map.get("wechatNotifyList");
		
		if(wechatSendRecordList != null) {
			for(WechatSendRecord wechatSendRecord : wechatSendRecordList) {
				try {
					//发送短信
					MsgUtil.sendWechatTemplateMsg(wechatSendRecord);
					wechatSendRecordList.remove(wechatSendRecord);
				} catch (Exception e) {
					e.printStackTrace();
					SLog.error(Utils.getErrorMessage(e));
				}
			}
		}
	}

	public MsgSendRecordService getMsgSendRecordService() {
		return msgSendRecordService;
	}

	@Resource
	public void setMsgSendRecordService(MsgSendRecordService msgSendRecordService) {
		WechatNotifySendJob.msgSendRecordService = msgSendRecordService;
	}
	
}
