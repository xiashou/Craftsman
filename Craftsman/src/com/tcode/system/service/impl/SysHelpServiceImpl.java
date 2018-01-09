package com.tcode.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.system.dao.SysHelpDao;
import com.tcode.system.model.SysHelp;
import com.tcode.system.service.SysHelpService;

@Component("sysHelpService")
public class SysHelpServiceImpl implements SysHelpService {
	
	private SysHelpDao sysHelpDao;

	@Override
	public SysHelp getSysHelpById(String id) throws Exception {
		if(id.indexOf("_") > 0)
			id = id.substring(0, id.indexOf("_"));
		return sysHelpDao.loadById(id);
	}
	
	@Override
	public void insert(SysHelp help) throws Exception {
		sysHelpDao.save(help);
	}
	
	@Override
	public void update(SysHelp help) throws Exception {
		sysHelpDao.edit(help);
	}

	public SysHelpDao getSysHelpDao() {
		return sysHelpDao;
	}
	@Resource
	public void setSysHelpDao(SysHelpDao sysHelpDao) {
		this.sysHelpDao = sysHelpDao;
	}

}
