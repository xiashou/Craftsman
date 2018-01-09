package com.tcode.business.basic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseCarSeriesDao;
import com.tcode.business.basic.model.BaseCarSeries;
import com.tcode.business.basic.service.BaseCarSeriesService;

@Component("carSeriesService")
public class BaseCarSeriesServiceImpl implements BaseCarSeriesService {
	
	private BaseCarSeriesDao carSeriesDao;
	
	@Override
	public List<BaseCarSeries> getAll() throws Exception {
		return carSeriesDao.loadAll();
	}

	@Override
	public BaseCarSeries getById(Integer id) throws Exception {
		return carSeriesDao.loadById(id);
	}
	
	@Override
	public BaseCarSeries getByCarId(Integer carId) throws Exception {
		return carSeriesDao.loadByCarId(carId);
	}

	@Override
	public List<BaseCarSeries> getByBrandId(Integer brandId) throws Exception {
		return carSeriesDao.loadByBrandId(brandId);
	}

	@Override
	public void insert(BaseCarSeries carSeries) throws Exception {
		carSeriesDao.save(carSeries);
	}

	@Override
	public void update(BaseCarSeries carSeries) throws Exception {
		carSeriesDao.edit(carSeries);
	}

	@Override
	public void delete(BaseCarSeries carSeries) throws Exception {
		carSeriesDao.remove(carSeries);
	}

	@Override
	public List<BaseCarSeries> getListPage(BaseCarSeries carSeries, int start, int limit) throws Exception {
		return carSeriesDao.loadListPage(carSeries, start, limit);
	}

	@Override
	public Integer getListCount(BaseCarSeries carSeries) throws Exception {
		return carSeriesDao.loadListCount(carSeries);
	}

	public BaseCarSeriesDao getCarSeriesDao() {
		return carSeriesDao;
	}
	@Resource
	public void setCarSeriesDao(BaseCarSeriesDao carSeriesDao) {
		this.carSeriesDao = carSeriesDao;
	}

	
}
