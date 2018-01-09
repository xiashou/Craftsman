package com.tcode.business.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.goods.dao.GoodsStockDao;
import com.tcode.business.goods.model.GoodsStock;
import com.tcode.business.goods.service.GoodsStockService;
import com.tcode.business.report.dao.ReptGoodsDao;
import com.tcode.core.util.Utils;

@Component("goodsStockService")
public class GoodsStockServiceImpl implements GoodsStockService {

	private GoodsStockDao goodsStockDao;
	private ReptGoodsDao reptGoodsDao;

	@Override
	public GoodsStock getGoodsStockById(String goodsId, String deptCode) throws Exception {
		return goodsStockDao.loadById(goodsId, deptCode);
	}
	@Override
	public List<GoodsStock> getGoodsStockByGoodsId(String goodsId) throws Exception {
		return goodsStockDao.loadByGoodsId(goodsId);
	}
	@Override
	public List<GoodsStock> getListByDept(String deptCode) throws Exception {
		GoodsStock stock = new GoodsStock();
		stock.setDeptCode(deptCode);
		return goodsStockDao.loadByDeptPage(stock, 0, 100000);
	}
	@Override
	public List<GoodsStock> getListByKeyword(String deptCode, String keyword) throws Exception {
		return goodsStockDao.loadByKeyword(deptCode, keyword);
	}
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void insertMoreGoodsStock(List<GoodsStock> stockList) throws Exception {
		if(!Utils.isEmpty(stockList)){
			GoodsStock exist = new GoodsStock();
			for(GoodsStock stock : stockList){
				exist = goodsStockDao.loadById(stock.getGoodsId(), stock.getDeptCode());
				if(!Utils.isEmpty(exist)){
					exist.setNumber(exist.getNumber() + stock.getNumber());
					goodsStockDao.edit(exist);
				} else 
					goodsStockDao.save(stock);
				//数量大于0时入库，小于0时退货。此方法只能用与采购入库和供应商退货
				reptGoodsDao.addRecord(stock.getDeptCode(), "", stock.getNumber() > 0 ? "I" : "Q", stock.getGoodsId(), stock.getName(), stock.getNumber());
			}
		}
	}
	
	@Override
	public List<GoodsStock> getListByDeptPage(GoodsStock stock, int start, int limit) throws Exception {
		return goodsStockDao.loadByDeptPage(stock, start, limit);
	}
	@Override
	public Integer getCountByDeptPage(GoodsStock stock) throws Exception {
		return goodsStockDao.loadByDeptPageCount(stock);
	}
	
	@Override
	public Integer getByBossCount(String companyId, String deptCode) throws Exception {
		return goodsStockDao.loadByBossCount(companyId, deptCode);
	}
	
	@Override
	public Integer getByBossCost(String companyId, String deptCode) throws Exception {
		return goodsStockDao.loadByBossCost(companyId, deptCode);
	}
	
	@Override
	public List<GoodsStock> getByBossType(String companyId, String deptCode) throws Exception {
		return goodsStockDao.loadByBossType(companyId, deptCode);
	}
	
	@Override
	public void insert(GoodsStock stock) throws Exception {
		goodsStockDao.save(stock);
	}
	@Override
	public void update(GoodsStock stock) throws Exception {
		goodsStockDao.edit(stock);
	}
	@Override
	public void delete(GoodsStock stock) throws Exception {
		goodsStockDao.remove(stock);
	}
	

	public GoodsStockDao getGoodsStockDao() {
		return goodsStockDao;
	}
	@Resource
	public void setGoodsStockDao(GoodsStockDao goodsStockDao) {
		this.goodsStockDao = goodsStockDao;
	}
	public ReptGoodsDao getReptGoodsDao() {
		return reptGoodsDao;
	}
	@Resource
	public void setReptGoodsDao(ReptGoodsDao reptGoodsDao) {
		this.reptGoodsDao = reptGoodsDao;
	}
}
