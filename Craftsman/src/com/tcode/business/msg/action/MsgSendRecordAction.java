package com.tcode.business.msg.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.msg.service.MsgSendRecordService;
import com.tcode.business.msg.vo.MsgSendRecordVo;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

/**
 * 短信发送记录
 * @author supeng
 *
 */
@Scope("prototype")
@Component("MsgSendRecordAction")
public class MsgSendRecordAction extends BaseAction {
	
	private static Logger log = Logger.getLogger("SLog");
	
	private MsgSendRecordService msgSendRecordService;
	private MsgSendRecordVo msgSendRecordVo;
	private List<MsgSendRecord> msgSendRecordList;
	
	/**
	 * 分页查询当前部门短信发送记录
	 * @return
	 */
	public String queryMsgSendRecordPage() {
		try {
			if(msgSendRecordVo == null) msgSendRecordVo = new MsgSendRecordVo();
			msgSendRecordVo.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(msgSendRecordService.getListCount(msgSendRecordVo));
			msgSendRecordList = msgSendRecordService.getListPage(msgSendRecordVo, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public MsgSendRecordService getMsgSendRecordService() {
		return msgSendRecordService;
	}

	@Resource
	public void setMsgSendRecordService(MsgSendRecordService msgSendRecordService) {
		this.msgSendRecordService = msgSendRecordService;
	}

	public MsgSendRecordVo getMsgSendRecordVo() {
		return msgSendRecordVo;
	}

	public void setMsgSendRecordVo(MsgSendRecordVo msgSendRecordVo) {
		this.msgSendRecordVo = msgSendRecordVo;
	}

	public List<MsgSendRecord> getMsgSendRecordList() {
		return msgSendRecordList;
	}

	public void setMsgSendRecordList(List<MsgSendRecord> msgSendRecordList) {
		this.msgSendRecordList = msgSendRecordList;
	}
	
}
