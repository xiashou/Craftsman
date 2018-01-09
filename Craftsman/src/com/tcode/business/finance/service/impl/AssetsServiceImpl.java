package com.tcode.business.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.AssetsDao;
import com.tcode.business.finance.model.Assets;
import com.tcode.business.finance.service.AssetsService;

@Component("assetsService")
public class AssetsServiceImpl implements AssetsService {

	private AssetsDao assetsDao;

	@Override
	public Assets getById(Integer id) throws Exception {
		return assetsDao.loadById(id);
	}
	@Override
	public List<Assets> getListByDept(String deptCode) throws Exception {
		return assetsDao.loadByDept(deptCode);
	}
	@Override
	public void insert(Assets assets) throws Exception {
		assetsDao.save(assets);
	}
	@Override
	public void update(Assets assets) throws Exception {
		assetsDao.edit(assets);
	}
	@Override
	public void delete(Assets assets) throws Exception {
		assetsDao.remove(assets);
	}
	
	
	
	
	
	public AssetsDao getAssetsDao() {
		return assetsDao;
	}
	@Resource
	public void setAssetsDao(AssetsDao assetsDao) {
		this.assetsDao = assetsDao;
	}
	
}
