package com.tcode.business.report.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.report.dao.ReptDeleteDao;
import com.tcode.business.report.model.ReptDelete;
import com.tcode.business.report.service.ReptDeleteService;

@Component("reptDeleteService")
public class ReptDeleteServiceImpl implements ReptDeleteService {
	
	private ReptDeleteDao reptDeleteDao;

	@Override
	public ReptDelete getReptDeleteById(Integer id) throws Exception {
		return reptDeleteDao.loadById(id);
	}

	@Override
	public List<ReptDelete> getListByDept(String deptCode) throws Exception {
		return reptDeleteDao.loadByDept(deptCode);
	}

	@Override
	public List<ReptDelete> getListByMemId(Integer memId) throws Exception {
		return reptDeleteDao.loadByMemId(memId);
	}
	
	@Override
	public void insertRecord(String deptCode, String orderNo, String userId) throws Exception {
		reptDeleteDao.addRecord(deptCode, orderNo, userId);
	}

	@Override
	public List<ReptDelete> getListPage(ReptDelete recharge, int start, int limit) throws Exception {
		return reptDeleteDao.loadListPage(recharge, start, limit);
	}

	@Override
	public Integer getListCount(ReptDelete recharge) throws Exception {
		return reptDeleteDao.loadListCount(recharge);
	}

	@Override
	public void insert(ReptDelete recharge) throws Exception {
		reptDeleteDao.save(recharge);
	}

	@Override
	public void update(ReptDelete recharge) throws Exception {
		reptDeleteDao.edit(recharge);
	}

	@Override
	public void delete(ReptDelete recharge) throws Exception {
		reptDeleteDao.remove(recharge);
	}

	
	public ReptDeleteDao getReptDeleteDao() {
		return reptDeleteDao;
	}
	@Resource
	public void setReptDeleteDao(ReptDeleteDao reptDeleteDao) {
		this.reptDeleteDao = reptDeleteDao;
	}

	

}
