package com.tcode.business.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsHourRemindDao;
import com.tcode.business.goods.model.GoodsHourRemind;
import com.tcode.business.goods.service.GoodsHourRemindService;

@Component("goodsHourRemindService")
public class GoodsHourRemindServiceImpl implements GoodsHourRemindService {

	private GoodsHourRemindDao goodsHourRemindDao;
	
	@Override
	public List<GoodsHourRemind> getHourRemindByDeptCode(String deptCode) throws Exception {
		return goodsHourRemindDao.loadAll(deptCode);
	}

	@Override
	public GoodsHourRemind getHourRemindById(Integer id) throws Exception {
		return goodsHourRemindDao.loadById(id);
	}

	@Override
	public void insert(GoodsHourRemind hourRemind) throws Exception {
		goodsHourRemindDao.save(hourRemind);
	}

	@Override
	public void update(GoodsHourRemind hourRemind) throws Exception {
		goodsHourRemindDao.edit(hourRemind);
	}

	@Override
	public void delete(GoodsHourRemind hourRemind) throws Exception {
		goodsHourRemindDao.remove(hourRemind);
	}

	public GoodsHourRemindDao getGoodsHourRemindDao() {
		return goodsHourRemindDao;
	}
	@Resource
	public void setGoodsHourRemindDao(GoodsHourRemindDao goodsHourRemindDao) {
		this.goodsHourRemindDao = goodsHourRemindDao;
	}

	
	
}
