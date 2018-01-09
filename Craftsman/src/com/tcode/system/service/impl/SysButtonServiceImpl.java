package com.tcode.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.system.dao.SysButtonDao;
import com.tcode.system.model.SysButton;
import com.tcode.system.service.SysButtonService;

@Component("sysButtonService")
public class SysButtonServiceImpl implements SysButtonService {

	private SysButtonDao sysButtonDao;
	
	@Override
	public List<SysButton> getAll() throws Exception {
		return sysButtonDao.loadAll();
	}
	
	@Override
	public SysButton getButtonById(Integer id) throws Exception {
		return sysButtonDao.loadById(id);
	}

	@Override
	public void insert(SysButton button) throws Exception {
		sysButtonDao.save(button);
	}

	@Override
	public void update(SysButton button) throws Exception {
		sysButtonDao.edit(button);
	}

	@Override
	public void delete(SysButton button) throws Exception {
		sysButtonDao.remove(button);
	}

	@Override
	public List<SysButton> getListPage(SysButton button, int start, int limit) throws Exception {
		return sysButtonDao.loadListPage(button, start, limit);
	}

	@Override
	public Integer getListCount(SysButton button) throws Exception {
		return sysButtonDao.loadListCount(button);
	}

	public SysButtonDao getSysButtonDao() {
		return sysButtonDao;
	}
	@Resource
	public void setSysButtonDao(SysButtonDao sysButtonDao) {
		this.sysButtonDao = sysButtonDao;
	}
	
}
