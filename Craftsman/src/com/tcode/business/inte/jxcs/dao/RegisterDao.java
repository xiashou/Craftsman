package com.tcode.business.inte.jxcs.dao;

import com.tcode.business.inte.jxcs.model.Register;

public interface RegisterDao {

	public Register loadByDept(String deptCode) throws Exception;
	
	public Register loadByCompanycode(String code) throws Exception;
	
	public void save(Register register) throws Exception;
	
	public void saveOrUpdate(Register register) throws Exception;
	
	public void edit(Register register) throws Exception;
	
	public void remove(Register register) throws Exception;
}
