package com.tcode.business.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.ExptypeDao;
import com.tcode.business.finance.model.Exptype;
import com.tcode.business.finance.service.ExptypeService;

@Component("exptypeService")
public class ExptypeServiceImpl implements ExptypeService {

	private ExptypeDao exptypeDao;

	@Override
	public Exptype getById(Integer id) throws Exception {
		return exptypeDao.loadById(id);
	}

	@Override
	public List<Exptype> getListByDept(String deptCode) throws Exception {
		return exptypeDao.loadByDept(deptCode);
	}

	@Override
	public void insert(Exptype exptype) throws Exception {
		exptypeDao.save(exptype);
	}

	@Override
	public void update(Exptype exptype) throws Exception {
		exptypeDao.edit(exptype);
	}

	@Override
	public void delete(Exptype exptype) throws Exception {
		exptypeDao.remove(exptype);
	}

	
	
	
	public ExptypeDao getExptypeDao() {
		return exptypeDao;
	}
	@Resource
	public void setExptypeDao(ExptypeDao exptypeDao) {
		this.exptypeDao = exptypeDao;
	}
}
