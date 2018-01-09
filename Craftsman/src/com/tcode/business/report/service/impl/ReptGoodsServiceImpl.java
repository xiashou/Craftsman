package com.tcode.business.report.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.report.dao.ReptGoodsDao;
import com.tcode.business.report.model.ReptGoods;
import com.tcode.business.report.service.ReptGoodsService;

@Component("reptGoodsService")
public class ReptGoodsServiceImpl implements ReptGoodsService {
	
	private ReptGoodsDao reptGoodsDao;

	
	@Override
	public ReptGoods getReptGoodsById(Integer id) throws Exception {
		return reptGoodsDao.loadById(id);
	}

	@Override
	public List<ReptGoods> getListByDept(String deptCode) throws Exception {
		return reptGoodsDao.loadByDept(deptCode);
	}

	@Override
	public List<ReptGoods> getListByGoodsId(String goodsId) throws Exception {
		return reptGoodsDao.loadByGoodsId(goodsId);
	}

	@Override
	public List<ReptGoods> getListPage(ReptGoods recharge, int start, int limit) throws Exception {
		return reptGoodsDao.loadListPage(recharge, start, limit);
	}

	@Override
	public Integer getListCount(ReptGoods memberStock) throws Exception {
		return reptGoodsDao.loadListCount(memberStock);
	}
	
	@Override
	public void insertRecord(String deptCode, String orderNo, String type, String goodsId, String goodsName, Double number) throws Exception {
		reptGoodsDao.addRecord(deptCode, orderNo, type, goodsId, goodsName, number);
	}

	@Override
	public void insert(ReptGoods memberStock) throws Exception {
		reptGoodsDao.save(memberStock);
	}

	@Override
	public void update(ReptGoods memberStock) throws Exception {
		reptGoodsDao.edit(memberStock);
	}

	@Override
	public void delete(ReptGoods memberStock) throws Exception {
		reptGoodsDao.remove(memberStock);
	}

	
	
	public ReptGoodsDao getReptGoodsDao() {
		return reptGoodsDao;
	}
	@Resource
	public void setReptGoodsDao(ReptGoodsDao reptGoodsDao) {
		this.reptGoodsDao = reptGoodsDao;
	}


}
