package com.tcode.business.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallGoodsDao;
import com.tcode.business.mall.model.MallGoods;
import com.tcode.business.mall.service.MallGoodsService;
import com.tcode.core.util.Utils;

@Component("mallGoodsService")
public class MallGoodsServiceImpl implements MallGoodsService {
	
	private MallGoodsDao mallGoodsDao;

	@Override
	public List<MallGoods> getAll() throws Exception {
		return mallGoodsDao.loadAll();
	}
	
	@Override
	public List<MallGoods> getHotList(String appId, int limit) throws Exception {
		MallGoods goods = new MallGoods();
		goods.setAppId(appId);
		goods.setIsHot(1);
		goods.setStatus(1);
		return mallGoodsDao.loadListPage(goods, 0, limit);
	}
	
	public List<MallGoods> getAllListOrderType(String appId) throws Exception {
		return mallGoodsDao.loadAllListOrderType(appId);
	}
	
	@Override
	public List<MallGoods> getListByType(String appId, Integer typeId) throws Exception {
		MallGoods goods = new MallGoods();
		goods.setAppId(appId);
		goods.setStatus(1);
		goods.setTypeId(typeId);
		return mallGoodsDao.loadListPage(goods, 0, 50);
	}
	
	@Override
	public List<MallGoods> getListByTyre(String appId, Integer modelId) throws Exception {
		return mallGoodsDao.loadListByTyre(appId, modelId);
	}

	@Override
	public List<MallGoods> getListByMaintain(String appId, Integer modelId) throws Exception {
		return mallGoodsDao.loadListByMaintain(appId, modelId);
	}
	

	@Override
	public MallGoods getById(String goodsId) throws Exception {
		return mallGoodsDao.loadById(goodsId);
	}
	
	@Override
	public Integer insertMore(List<MallGoods> goodsList) throws Exception {
		int count = 0;
		if(!Utils.isEmpty(goodsList)){
			for(MallGoods goods : goodsList){
				mallGoodsDao.save(goods);
				count++;
			}
		}
		return count;
	}

	@Override
	public void insert(MallGoods goods) throws Exception {
		mallGoodsDao.save(goods);
	}

	@Override
	public void update(MallGoods goods) throws Exception {
		mallGoodsDao.edit(goods);
	}

	@Override
	public void delete(MallGoods goods) throws Exception {
		mallGoodsDao.remove(goods);
	}

	@Override
	public List<MallGoods> getListPage(MallGoods goods, int start, int limit) throws Exception {
		return mallGoodsDao.loadListPage(goods, start, limit);
	}

	@Override
	public Integer getListCount(MallGoods goods) throws Exception {
		return mallGoodsDao.loadListCount(goods);
	}

	public MallGoodsDao getMallGoodsDao() {
		return mallGoodsDao;
	}
	@Resource
	public void setMallGoodsDao(MallGoodsDao mallGoodsDao) {
		this.mallGoodsDao = mallGoodsDao;
	}

}
