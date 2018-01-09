package com.tcode.business.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsHourDao;
import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.service.GoodsHourService;

@Component("goodsHourService")
public class GoodsHourServiceImpl implements GoodsHourService {

	private GoodsHourDao goodsHourDao;

	@Override
	public List<GoodsHour> getGoodsHourByDeptCode(String deptCode) throws Exception {
		return goodsHourDao.loadAll(deptCode);
	}
	@Override
	public GoodsHour getGoodsHourById(String id) throws Exception {
		return goodsHourDao.loadById(id);
	}
	@Override
	public List<GoodsHour> getGoodsHourByType(Integer typeId) throws Exception {
		return goodsHourDao.loadByType(typeId);
	}
	@Override
	public void insert(GoodsHour goodHour) throws Exception {
		goodsHourDao.save(goodHour);
	}
	@Override
	public void update(GoodsHour goodHour) throws Exception {
		goodsHourDao.edit(goodHour);
	}
	@Override
	public void delete(GoodsHour goodHour) throws Exception {
		goodsHourDao.remove(goodHour);
	}
	@Override
	public void deleteByType(GoodsHour goodHour) throws Exception {
		goodsHourDao.removeByType(goodHour);
	}
	@Override
	public List<GoodsHour> getListPage(GoodsHour goodHour, int start, int limit) throws Exception {
		return goodsHourDao.loadListPage(goodHour, start, limit);
	}
	@Override
	public Integer getListCount(GoodsHour goodHour) throws Exception {
		return goodsHourDao.loadListCount(goodHour);
	}
	public GoodsHourDao getGoodsHourDao() {
		return goodsHourDao;
	}
	@Resource
	public void setGoodsHourDao(GoodsHourDao goodsHourDao) {
		this.goodsHourDao = goodsHourDao;
	}
	
}
