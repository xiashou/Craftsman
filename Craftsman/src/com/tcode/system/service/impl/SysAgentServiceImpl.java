package com.tcode.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.system.dao.SysAgentDao;
import com.tcode.system.model.SysAgent;
import com.tcode.system.service.SysAgentService;

/**
 * 系统代理service
 * @author Xiashou
 * @since 2017/3/29
 */
@Component("sysAgentService")
public class SysAgentServiceImpl implements SysAgentService {

	private SysAgentDao sysAgentDao;

	@Override
	public List<SysAgent> getAll() throws Exception {
		return sysAgentDao.loadAll();
	}

	@Override
	public SysAgent getAgentById(Integer agentId) throws Exception {
		return sysAgentDao.loadById(agentId);
	}

	@Override
	public SysAgent getAgentByName(String agentName) throws Exception {
		return sysAgentDao.loadByName(agentName);
	}

	@Override
	public List<SysAgent> getAgentByAreaId(String areaId) throws Exception {
		return sysAgentDao.loadByAreaId(areaId);
	}

	@Override
	public void insert(SysAgent agent) throws Exception {
		sysAgentDao.save(agent);
	}

	@Override
	public void update(SysAgent agent) throws Exception {
		sysAgentDao.edit(agent);
	}

	@Override
	public void delete(SysAgent agent) throws Exception {
		sysAgentDao.remove(agent);
	}

	public SysAgentDao getSysAgentDao() {
		return sysAgentDao;
	}
	@Resource
	public void setSysAgentDao(SysAgentDao sysAgentDao) {
		this.sysAgentDao = sysAgentDao;
	}
	
}
