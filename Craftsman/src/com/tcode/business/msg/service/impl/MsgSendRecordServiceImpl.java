package com.tcode.business.msg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.msg.dao.MsgSendRecordDao;
import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.msg.service.MsgSendRecordService;
import com.tcode.business.msg.vo.MsgSendRecordVo;
import com.tcode.core.util.Utils;

@Component("msgSendRecordService")
public class MsgSendRecordServiceImpl implements MsgSendRecordService {
	
	private MsgSendRecordDao msgSendRecordDao;

	@Override
	public void insert(MsgSendRecord msgSendRecord) throws Exception {
		msgSendRecord.setCreateTime(Utils.getSysTime());
		msgSendRecord.setUpdateTime(Utils.getSysTime());
		msgSendRecordDao.save(msgSendRecord);
	}

	@Override
	public void update(MsgSendRecord msgSendRecord) throws Exception {
		msgSendRecord.setUpdateTime(Utils.getSysTime());
		msgSendRecordDao.edit(msgSendRecord);
	}
	
	@Override
	public List<MsgSendRecord> getByMissionID(int missionID) throws Exception {
		return msgSendRecordDao.loadByMissionID(missionID);
	}
	
	@Override
	public List<MsgSendRecord> getListPage(MsgSendRecordVo msgSendRecordVo, int start, int limit) throws Exception {
		return msgSendRecordDao.loadListPage(msgSendRecordVo, start, limit);
	}
	
	@Override
	public Integer getListCount(MsgSendRecordVo msgSendRecordVo) throws Exception {
		return msgSendRecordDao.loadListCount(msgSendRecordVo);
	}

	public MsgSendRecordDao getMsgSendRecordDao() {
		return msgSendRecordDao;
	}

	@Resource
	public void setMsgSendRecordDao(MsgSendRecordDao msgSendRecordDao) {
		this.msgSendRecordDao = msgSendRecordDao;
	}
	
}
