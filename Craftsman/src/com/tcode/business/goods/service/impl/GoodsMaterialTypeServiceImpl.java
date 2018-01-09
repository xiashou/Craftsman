package com.tcode.business.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsMaterialTypeDao;
import com.tcode.business.goods.model.GoodsMaterialType;
import com.tcode.business.goods.service.GoodsMaterialTypeService;

@Component("GoodsMaterialTypeService")
public class GoodsMaterialTypeServiceImpl implements GoodsMaterialTypeService {

	private GoodsMaterialTypeDao goodsMaterialTypeDao;

	@Override
	public List<GoodsMaterialType> getTypeByDeptCode(String deptCode) throws Exception {
		return goodsMaterialTypeDao.loadTypeByDeptCode(deptCode);
	}

	@Override
	public Integer getListCount(GoodsMaterialType materialType) throws Exception {
		return goodsMaterialTypeDao.loadListCount(materialType);
	}
	
	@Override
	public List<GoodsMaterialType> getAll() throws Exception {
		return goodsMaterialTypeDao.loadAll();
	}

	
	@Override
	public void insert(GoodsMaterialType materialType) throws Exception {
		goodsMaterialTypeDao.save(materialType);
	}

	@Override
	public void update(GoodsMaterialType materialType) throws Exception {
		goodsMaterialTypeDao.edit(materialType);
	}

	@Override
	public void delete(GoodsMaterialType materialType) throws Exception {
		goodsMaterialTypeDao.remove(materialType);
	}
	
	public GoodsMaterialTypeDao getGoodsMaterialTypeDao() {
		return goodsMaterialTypeDao;
	}
	@Resource
	public void setGoodsMaterialTypeDao(GoodsMaterialTypeDao goodsMaterialTypeDao) {
		this.goodsMaterialTypeDao = goodsMaterialTypeDao;
	}

	
}
