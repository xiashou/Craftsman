package com.tcode.business.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.BaddebtDao;
import com.tcode.business.finance.model.Baddebt;
import com.tcode.business.finance.service.BaddebtService;

@Component("baddebtService")
public class BaddebtServiceImpl implements BaddebtService {
	
	private BaddebtDao baddebtDao;

	@Override
	public Baddebt getById(Integer id) throws Exception {
		return baddebtDao.loadById(id);
	}

	@Override
	public List<Baddebt> getByDept(String deptCode) throws Exception {
		return baddebtDao.loadByDept(deptCode);
	}

	@Override
	public List<Baddebt> getByDeptPage(Baddebt debt, Integer start, Integer limit) throws Exception {
		return baddebtDao.loadByDeptPage(debt, start, limit);
	}

	@Override
	public Integer getByDeptCount(Baddebt debt) throws Exception {
		return baddebtDao.loadByDeptCount(debt);
	}

	@Override
	public void insert(Baddebt debt) throws Exception {
		baddebtDao.save(debt);
	}

	@Override
	public void update(Baddebt debt) throws Exception {
		baddebtDao.edit(debt);
	}

	@Override
	public void delete(Baddebt debt) throws Exception {
		baddebtDao.remove(debt);
	}

	public BaddebtDao getBaddebtDao() {
		return baddebtDao;
	}
	@Resource
	public void setBaddebtDao(BaddebtDao baddebtDao) {
		this.baddebtDao = baddebtDao;
	}

}
