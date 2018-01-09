package com.tcode.system.service;

import com.tcode.system.model.SysHelp;

public interface SysHelpService {

	public SysHelp getSysHelpById(String id) throws Exception;
	
	public void insert(SysHelp help) throws Exception;
	
	public void update(SysHelp help) throws Exception;
}
