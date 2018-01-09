package com.tcode.business.basic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseCarModelDao;
import com.tcode.business.basic.model.BaseCarModel;
import com.tcode.business.basic.service.BaseCarModelService;

@Component("carModelService")
public class BaseCarModelServiceImpl implements BaseCarModelService {

	private BaseCarModelDao carModelDao;
	
	@Override
	public List<BaseCarModel> getAll() throws Exception {
		return carModelDao.loadAll();
	}

	@Override
	public BaseCarModel getById(Integer id) throws Exception {
		return carModelDao.loadById(id);
	}

	@Override
	public List<BaseCarModel> getBySeriesId(Integer id) throws Exception {
		return carModelDao.loadBySeriesId(id);
	}

	@Override
	public void insert(BaseCarModel carModel) throws Exception {
		carModelDao.save(carModel);
	}

	@Override
	public void update(BaseCarModel carModel) throws Exception {
		carModelDao.edit(carModel);
	}

	@Override
	public void delete(BaseCarModel carModel) throws Exception {
		carModelDao.remove(carModel);
	}

	@Override
	public List<BaseCarModel> getListPage(BaseCarModel carModel, int start, int limit) throws Exception {
		return carModelDao.loadListPage(carModel, start, limit);
	}

	@Override
	public Integer getListCount(BaseCarModel carModel) throws Exception {
		return carModelDao.loadListCount(carModel);
	}

	public BaseCarModelDao getCarModelDao() {
		return carModelDao;
	}
	@Resource
	public void setCarModelDao(BaseCarModelDao carModelDao) {
		this.carModelDao = carModelDao;
	}

}
