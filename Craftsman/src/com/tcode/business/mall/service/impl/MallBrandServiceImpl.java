package com.tcode.business.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallBrandDao;
import com.tcode.business.mall.model.MallBrand;
import com.tcode.business.mall.service.MallBrandService;

@Component("mallBrandService")
public class MallBrandServiceImpl implements MallBrandService {
	
	private MallBrandDao mallBrandDao;

	@Override
	public List<MallBrand> getAll() throws Exception {
		return mallBrandDao.loadAll();
	}

	@Override
	public MallBrand getById(Integer id) throws Exception {
		return mallBrandDao.loadById(id);
	}

	@Override
	public void insert(MallBrand mallBrand) throws Exception {
		mallBrandDao.save(mallBrand);
	}

	@Override
	public void update(MallBrand mallBrand) throws Exception {
		mallBrandDao.edit(mallBrand);
	}

	@Override
	public void delete(MallBrand mallBrand) throws Exception {
		mallBrandDao.remove(mallBrand);
	}

	@Override
	public List<MallBrand> getListPage(MallBrand mallBrand, int start, int limit) throws Exception {
		return mallBrandDao.loadListPage(mallBrand, start, limit);
	}

	@Override
	public Integer getListCount(MallBrand mallBrand) throws Exception {
		return mallBrandDao.loadListCount(mallBrand);
	}

	public MallBrandDao getMallBrandDao() {
		return mallBrandDao;
	}
	@Resource
	public void setMallBrandDao(MallBrandDao mallBrandDao) {
		this.mallBrandDao = mallBrandDao;
	}

}
