package com.tcode.business.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsHourTypeDao;
import com.tcode.business.goods.model.GoodsHourType;
import com.tcode.business.goods.service.GoodsHourTypeService;

@Component("goodsHourTypeService")
public class GoodsHourTypeServiceImpl implements GoodsHourTypeService {

	private GoodsHourTypeDao goodsHourTypeDao;

	@Override
	public List<GoodsHourType> getTypeByDeptCode(String deptCode) throws Exception {
		return goodsHourTypeDao.loadTypeByDeptCode(deptCode);
	}
	
	@Override
	public GoodsHourType getTypeByName(String deptCode, String typeName) throws Exception {
		return goodsHourTypeDao.loadTypeByName(deptCode, typeName);
	}

	@Override
	public Integer getListCount(GoodsHourType hourType) throws Exception {
		return goodsHourTypeDao.loadListCount(hourType);
	}
	
	@Override
	public List<GoodsHourType> getAll() throws Exception {
		return goodsHourTypeDao.loadAll();
	}

	
	@Override
	public void insert(GoodsHourType hourType) throws Exception {
		goodsHourTypeDao.save(hourType);
	}

	@Override
	public void update(GoodsHourType hourType) throws Exception {
		goodsHourTypeDao.edit(hourType);
	}

	@Override
	public void delete(GoodsHourType hourType) throws Exception {
		goodsHourTypeDao.remove(hourType);
	}
	
	public GoodsHourTypeDao getGoodsHourTypeDao() {
		return goodsHourTypeDao;
	}
	@Resource
	public void setGoodsHourTypeDao(GoodsHourTypeDao goodsHourTypeDao) {
		this.goodsHourTypeDao = goodsHourTypeDao;
	}

	
}
