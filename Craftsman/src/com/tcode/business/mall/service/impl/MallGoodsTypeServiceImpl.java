package com.tcode.business.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.mall.dao.MallGoodsDao;
import com.tcode.business.mall.dao.MallGoodsTypeDao;
import com.tcode.business.mall.model.MallGoodsType;
import com.tcode.business.mall.service.MallGoodsTypeService;

@Component("mallGoodsTypeService")
public class MallGoodsTypeServiceImpl implements MallGoodsTypeService {
	
	private MallGoodsTypeDao mallGoodsTypeDao;
	private MallGoodsDao mallGoodsDao;

	@Override
	public List<MallGoodsType> getAll() throws Exception {
		return mallGoodsTypeDao.loadAll();
	}

	@Override
	public MallGoodsType getById(Integer id) throws Exception {
		return mallGoodsTypeDao.loadById(id);
	}
	
	@Override
	public List<MallGoodsType> getByAppId(String appId) throws Exception {
		return mallGoodsTypeDao.loadByAppId(appId);
	}

	@Override
	public void insert(MallGoodsType goodsType) throws Exception {
		mallGoodsTypeDao.save(goodsType);
	}

	@Override
	public void update(MallGoodsType goodsType) throws Exception {
		mallGoodsTypeDao.edit(goodsType);
	}

	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void delete(MallGoodsType goodsType) throws Exception {
		mallGoodsDao.deleteByType(goodsType.getId());
		mallGoodsTypeDao.remove(goodsType);
	}

	@Override
	public List<MallGoodsType> getListPage(MallGoodsType goodsType, int start, int limit) throws Exception {
		return mallGoodsTypeDao.loadListPage(goodsType, start, limit);
	}

	@Override
	public Integer getListCount(MallGoodsType goodsType) throws Exception {
		return mallGoodsTypeDao.loadListCount(goodsType);
	}

	public MallGoodsTypeDao getMallGoodsTypeDao() {
		return mallGoodsTypeDao;
	}
	@Resource
	public void setMallGoodsTypeDao(MallGoodsTypeDao mallGoodsTypeDao) {
		this.mallGoodsTypeDao = mallGoodsTypeDao;
	}

	public MallGoodsDao getMallGoodsDao() {
		return mallGoodsDao;
	}
	@Resource
	public void setMallGoodsDao(MallGoodsDao mallGoodsDao) {
		this.mallGoodsDao = mallGoodsDao;
	}

}
