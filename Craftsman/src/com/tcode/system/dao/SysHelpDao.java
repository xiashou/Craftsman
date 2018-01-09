package com.tcode.system.dao;

import com.tcode.system.model.SysHelp;

public interface SysHelpDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return SysHelp
	 * @throws Exception
	 */
	public SysHelp loadById(String id) throws Exception;
	
	
	public void save(SysHelp help) throws Exception;
	
	public void edit(SysHelp help) throws Exception;
}
