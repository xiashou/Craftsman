package com.tcode.business.msg.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.msg.dao.MsgChargingDao;
import com.tcode.business.msg.model.MsgCharging;
import com.tcode.business.msg.service.MsgChargingService;
import com.tcode.core.util.Utils;

@Component("msgChargingService")
public class MsgChargingServiceImpl implements MsgChargingService {
	
	private MsgChargingDao msgChargingDao;

	@Override
	public void insert(MsgCharging msgCharging) throws Exception {
		msgCharging.setCreateTime(Utils.getSysTime());
		msgCharging.setUpdateTime(Utils.getSysTime());
		msgChargingDao.save(msgCharging);
	}

	@Override
	public void update(MsgCharging msgCharging) throws Exception {
		msgCharging.setUpdateTime(Utils.getSysTime());
		msgChargingDao.edit(msgCharging);
	}

	@Override
	public void delete(MsgCharging msgCharging) throws Exception {
		msgChargingDao.remove(msgCharging);
	}

	@Override
	public List<MsgCharging> getAll() throws Exception {
		return msgChargingDao.loadAll();
	}

	@Override
	public List<MsgCharging> getByDeptCode(String deptCode) throws Exception {
		return msgChargingDao.loadByDeptCode(deptCode);
	}
	
	
	public MsgChargingDao getMsgChargingDao() {
		return msgChargingDao;
	}

	@Resource
	public void setMsgChargingDao(MsgChargingDao msgChargingDao) {
		this.msgChargingDao = msgChargingDao;
	}
	
}
