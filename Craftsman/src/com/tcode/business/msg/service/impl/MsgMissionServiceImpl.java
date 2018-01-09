package com.tcode.business.msg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.msg.dao.MsgMissionDao;
import com.tcode.business.msg.model.MsgMission;
import com.tcode.business.msg.service.MsgMissionService;
import com.tcode.core.util.Utils;

@Component("msgMissionService")
public class MsgMissionServiceImpl implements MsgMissionService {
	
	private MsgMissionDao msgMissionDao;

	@Override
	public void insert(MsgMission msgMission) throws Exception {
		msgMission.setCreateTime(Utils.getSysTime());
		msgMission.setUpdateTime(Utils.getSysTime());
		msgMissionDao.save(msgMission);
	}

	@Override
	public void update(MsgMission msgMission) throws Exception {
		msgMission.setUpdateTime(Utils.getSysTime());
		msgMissionDao.edit(msgMission);
	}
	
	@Override
	public void delete(MsgMission msgMission) throws Exception {
		msgMissionDao.remove(msgMission);
	}
	
	@Override
	public List<MsgMission> getAll() throws Exception {
		return msgMissionDao.loadAll();
	}
	

	public MsgMissionDao getMsgMissionDao() {
		return msgMissionDao;
	}

	@Resource
	public void setMsgMissionDao(MsgMissionDao msgMissionDao) {
		this.msgMissionDao = msgMissionDao;
	}
	
}
