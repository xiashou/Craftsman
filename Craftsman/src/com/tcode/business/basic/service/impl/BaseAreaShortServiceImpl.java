package com.tcode.business.basic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseAreaShortDao;
import com.tcode.business.basic.model.BaseAreaShort;
import com.tcode.business.basic.service.BaseAreaShortService;

@Component("areaShortService")
public class BaseAreaShortServiceImpl implements BaseAreaShortService {
	
	private BaseAreaShortDao areaShortDao;

	@Override
	public List<BaseAreaShort> getAll() throws Exception {
		return areaShortDao.loadAll();
	}

	@Override
	public BaseAreaShort getById(Integer id) throws Exception {
		return areaShortDao.loadById(id);
	}

	@Override
	public void insert(BaseAreaShort areaShort) throws Exception {
		areaShortDao.save(areaShort);
	}

	@Override
	public void update(BaseAreaShort areaShort) throws Exception {
		areaShortDao.edit(areaShort);
	}

	@Override
	public void delete(BaseAreaShort areaShort) throws Exception {
		areaShortDao.remove(areaShort);
	}

	@Override
	public List<BaseAreaShort> getListPage(BaseAreaShort areaShort, int start, int limit) throws Exception {
		return areaShortDao.loadListPage(areaShort, start, limit);
	}

	@Override
	public Integer getListCount(BaseAreaShort areaShort) throws Exception {
		return areaShortDao.loadListCount(areaShort);
	}

	public BaseAreaShortDao getAreaShotDao() {
		return areaShortDao;
	}
	@Resource
	public void setAreaShotDao(BaseAreaShortDao areaShortDao) {
		this.areaShortDao = areaShortDao;
	}

}
