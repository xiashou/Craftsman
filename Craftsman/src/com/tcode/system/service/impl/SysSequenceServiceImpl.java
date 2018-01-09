package com.tcode.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.system.dao.SysSequenceDao;
import com.tcode.system.model.SysSequence;
import com.tcode.system.service.SysSequenceService;

@Component("sysSequenceService")
public class SysSequenceServiceImpl implements SysSequenceService {

	private SysSequenceDao sysSequenceDao;
	
	@Override
	public List<SysSequence> getAll() throws Exception {
		return sysSequenceDao.loadAll();
	}
	
	@Override
	public SysSequence getSequenceById(Integer id) throws Exception {
		return sysSequenceDao.loadById(id);
	}
	
	@Override
	public SysSequence getSequenceByName(String fieldName) throws Exception {
		return sysSequenceDao.loadByName(fieldName);
	}

	@Override
	public void insert(SysSequence param) throws Exception {
		sysSequenceDao.save(param);
	}

	@Override
	public void update(SysSequence param) throws Exception {
		sysSequenceDao.edit(param);
	}

	@Override
	public void delete(SysSequence param) throws Exception {
		sysSequenceDao.remove(param);
	}

	@Override
	public List<SysSequence> getListPage(SysSequence param, int start, int limit) throws Exception {
		return sysSequenceDao.loadListPage(param, start, limit);
	}

	@Override
	public Integer getListCount(SysSequence param) throws Exception {
		return sysSequenceDao.loadListCount(param);
	}

	public SysSequenceDao getSysSequenceDao() {
		return sysSequenceDao;
	}
	@Resource
	public void setSysSequenceDao(SysSequenceDao sysSequenceDao) {
		this.sysSequenceDao = sysSequenceDao;
	}
	
	
}
