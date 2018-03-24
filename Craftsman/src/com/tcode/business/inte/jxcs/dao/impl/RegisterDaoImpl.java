package com.tcode.business.inte.jxcs.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tcode.business.inte.jxcs.dao.RegisterDao;
import com.tcode.business.inte.jxcs.model.Register;
import com.tcode.common.dao.BaseDao;

@Component("jx_registerDao")
public class RegisterDaoImpl extends BaseDao<Register, Serializable> implements RegisterDao {

	@Override
	public Register loadByDept(String deptCode) throws Exception {
		return (Register) super.loadEntity("from Register r where r.deptCode = ?", deptCode);
	}

	@Override
	public Register loadByCompanycode(String code) throws Exception {
		return (Register) super.loadEntity("from Register r where r.cmpyCompanycode = ?", code);
	}

}
