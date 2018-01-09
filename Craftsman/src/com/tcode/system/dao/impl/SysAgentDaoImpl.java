package com.tcode.system.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.common.dao.BaseDao;
import com.tcode.system.dao.SysAgentDao;
import com.tcode.system.model.SysAgent;

@Component("sysAgentDao")
public class SysAgentDaoImpl extends BaseDao<SysAgent, Serializable> implements SysAgentDao {

	@Override
	public SysAgent loadById(Integer id) throws Exception {
		return super.loadById(SysAgent.class, id);
	}

	@Override
	public SysAgent loadByName(String agentName) throws Exception {
		List<SysAgent> agentList = super.loadList("from SysAgent a where a.agentName = ?", agentName);
		return agentList.size() > 0 ? agentList.get(0) : null;
	}

	@Override
	public List<SysAgent> loadByAreaId(String areaId) throws Exception {
		return super.loadList("from SysAgent a where a.areaId = ?", areaId);
	}

}
