package com.tcode.business.basic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseCarBrandDao;
import com.tcode.business.basic.model.BaseCarBrand;
import com.tcode.business.basic.service.BaseCarBrandService;

@Component("carBrandService")
public class BaseCarBrandServiceImpl implements BaseCarBrandService {

	private BaseCarBrandDao carBrandDao;
	
	@Override
	public List<BaseCarBrand> getAll() throws Exception {
		return carBrandDao.loadAll();
	}
	@Override
	public BaseCarBrand getById(Integer id) throws Exception {
		return carBrandDao.loadById(id);
	}
	@Override
	public List<BaseCarBrand> getByCode(String code) throws Exception {
		return carBrandDao.loadByCode(code);
	}
	@Override
	public void insert(BaseCarBrand carBrand) throws Exception {
		carBrandDao.save(carBrand);
	}
	@Override
	public void update(BaseCarBrand carBrand) throws Exception {
		carBrandDao.edit(carBrand);
	}
	@Override
	public void delete(BaseCarBrand carBrand) throws Exception {
		carBrandDao.remove(carBrand);
	}
	@Override
	public List<BaseCarBrand> getListPage(BaseCarBrand carBrand, int start, int limit) throws Exception {
		return carBrandDao.loadListPage(carBrand, start, limit);
	}
	@Override
	public Integer getListCount(BaseCarBrand carBrand) throws Exception {
		return carBrandDao.loadListCount(carBrand);
	}
	
	
	public BaseCarBrandDao getCarBrandDao() {
		return carBrandDao;
	}
	@Resource
	public void setCarBrandDao(BaseCarBrandDao carBrandDao) {
		this.carBrandDao = carBrandDao;
	}
	
}
