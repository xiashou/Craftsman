package com.tcode.business.finance.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.ExptypeDao;
import com.tcode.business.finance.model.Exptype;
import com.tcode.common.dao.BaseDao;

@Component("exptypeDao")
public class ExptypeDaoImpl extends BaseDao<Exptype, Serializable> implements ExptypeDao {

	@Override
	public Exptype loadById(Integer id) throws Exception {
		return super.loadById(Exptype.class, id);
	}

	@Override
	public List<Exptype> loadByDept(String deptCode) throws Exception {
		return super.loadList("from Exptype t where t.deptCode = ?", deptCode);
	}

}
