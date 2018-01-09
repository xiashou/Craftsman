package com.tcode.business.basic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseAreaCodeDao;
import com.tcode.business.basic.model.BaseAreaCode;
import com.tcode.business.basic.service.BaseAreaCodeService;

@Component("areaCodeService")
public class BaseAreaCodeServiceImpl implements BaseAreaCodeService {
	
	private BaseAreaCodeDao areaCodeDao;

	@Override
	public List<BaseAreaCode> getAll() throws Exception {
		return areaCodeDao.loadAll();
	}

	@Override
	public BaseAreaCode getById(Integer id) throws Exception {
		return areaCodeDao.loadById(id);
	}

	@Override
	public void insert(BaseAreaCode areaCode) throws Exception {
		areaCodeDao.save(areaCode);
	}

	@Override
	public void update(BaseAreaCode areaCode) throws Exception {
		areaCodeDao.edit(areaCode);
	}

	@Override
	public void delete(BaseAreaCode areaCode) throws Exception {
		areaCodeDao.remove(areaCode);
	}

	@Override
	public List<BaseAreaCode> getListPage(BaseAreaCode areaCode, int start, int limit) throws Exception {
		return areaCodeDao.loadListPage(areaCode, start, limit);
	}

	@Override
	public Integer getListCount(BaseAreaCode areaCode) throws Exception {
		return areaCodeDao.loadListCount(areaCode);
	}

	public BaseAreaCodeDao getAreaCodeDao() {
		return areaCodeDao;
	}
	@Resource
	public void setAreaCodeDao(BaseAreaCodeDao areaCodeDao) {
		this.areaCodeDao = areaCodeDao;
	}
	
}
