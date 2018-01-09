package com.tcode.business.msg.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.msg.dao.MsgChargingDao;
import com.tcode.business.msg.model.MsgCharging;
import com.tcode.common.dao.BaseDao;

@Component("msgChargingDao")
public class MsgChargingDaoImpl extends BaseDao<MsgCharging, Serializable>  implements MsgChargingDao {

	@Override
	public List<MsgCharging> loadByDeptCode(String deptCode) throws Exception {
		return super.loadList("from MsgCharging m where m.deptCode = ?", deptCode);
	}
	
}
