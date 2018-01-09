package com.tcode.business.goods.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.goods.dao.GoodsInStockDao;
import com.tcode.business.goods.dao.GoodsMaterialDao;
import com.tcode.business.goods.dao.GoodsMaterialTypeDao;
import com.tcode.business.goods.dao.GoodsStockDao;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.goods.model.GoodsStock;
import com.tcode.business.goods.service.GoodsMaterialService;
import com.tcode.core.util.Utils;

@Component("goodsMaterialService")
public class GoodsMaterialServiceImpl implements GoodsMaterialService {

	private GoodsMaterialDao goodsMaterialDao;
	private GoodsMaterialTypeDao goodsMaterialTypeDao;
	private GoodsStockDao goodsStockDao;
	private GoodsInStockDao goodsInStockDao;
	
	//带库存
	@Override
	public GoodsMaterial getGoodsMaterialById(String deptCode, String id) throws Exception {
		GoodsMaterial goods = goodsMaterialDao.loadById(id);
		GoodsStock stock = goodsStockDao.loadById(id, deptCode);
		if(!Utils.isEmpty(stock))
			goods.setNumber(stock.getNumber());
		else
			goods.setNumber(0.0);
		return goods;
	}
	@Override
	public GoodsMaterial getById(String id) throws Exception {
		return goodsMaterialDao.loadById(id);
	}
	@Override
	public List<GoodsMaterial> getGoodsMaterialByDept(String deptCode) throws Exception {
		return goodsMaterialDao.loadByDept(deptCode);
	}
	@Override
	public List<GoodsMaterial> getGoodsMaterialByType(Integer typeId) throws Exception {
		return goodsMaterialDao.loadByType(typeId);
	}
	@Override
	public GoodsMaterial getGoodsMaterialByName(String deptCode, String name) throws Exception {
		return goodsMaterialDao.loadByName(deptCode, name);
	}
	@Override
	public GoodsMaterial getGoodsMaterialByCode(String deptCode, String code) throws Exception {
		return goodsMaterialDao.loadByCode(deptCode, code);
	}
	@Override
	public GoodsMaterial getGoodsMaterialByIdCode(String deptCode, String idOrCode) throws Exception {
		return goodsMaterialDao.loadByIdOrCode(deptCode, idOrCode);
	}
	@Override
	public List<GoodsMaterial> getGoodsMaterialByKeyword(String deptCode, String keyword) throws Exception {
		List<GoodsMaterial> list = goodsMaterialDao.loadByKeyword(deptCode, keyword);
		for(GoodsMaterial goods : list){
			goods.setTypeName(goodsMaterialTypeDao.loadById(goods.getTypeId()).getTypeName());
		}
		return list;
	}
	@Override
	public List<GoodsMaterial> getGoodsMaterialByTypeKeyword(String companyId, Integer typeId, String keyword) throws Exception {
		List<GoodsMaterial> list = goodsMaterialDao.loadByTypeAndKeyword(companyId, typeId, keyword);
		for(GoodsMaterial goods : list){
			goods.setTypeName(goodsMaterialTypeDao.loadById(goods.getTypeId()).getTypeName());
		}
		return list;
	}
	@Override
	public List<GoodsMaterial> getGoodsMaterialStockByTypeKeyword(String companyId, String deptCode, Integer typeId, String keyword) throws Exception {
		List<GoodsMaterial> list = goodsMaterialDao.loadByTypeAndKeyword(companyId, typeId, keyword);
		//如果没有任何库存记录的则过滤掉，这里涉及删除所以用迭代
		for(Iterator<GoodsMaterial> it = list.iterator(); it.hasNext();){
	         GoodsMaterial goods = it.next();
	         GoodsStock stock = goodsStockDao.loadById(goods.getId(), deptCode);
	         if(!Utils.isEmpty(stock))
					goods.setNumber(stock.getNumber());
				else
					it.remove();
	         goods.setTypeName(goodsMaterialTypeDao.loadById(goods.getTypeId()).getTypeName());
	    }
		return list;
	}
	@Override
	public void insert(GoodsMaterial goodsMaterial) throws Exception {
		goodsMaterialDao.save(goodsMaterial);
	}
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public Integer insertMoreGoodsMaterial(List<GoodsMaterial> list) throws Exception {
		int count = 0;
		for(GoodsMaterial goods : list){
			goodsMaterialDao.save(goods);
			count++;
		}
		return count;
	}
	@Override
	public void update(GoodsMaterial goodsMaterial) throws Exception {
		goodsMaterialDao.edit(goodsMaterial);
	}
	@Override
	public void delete(GoodsMaterial goodsMaterial) throws Exception {
		goodsMaterialDao.remove(goodsMaterial);
	}
	@Override
	public void deleteByType(GoodsMaterial goodsMaterial) throws Exception {
		goodsMaterialDao.removeByType(goodsMaterial);
	}
	@Override
	public List<GoodsMaterial> getListPage(GoodsMaterial goodsMaterial, int start, int limit) throws Exception {
		return goodsMaterialDao.loadListPage(goodsMaterial, start, limit);
	}
	@Override
	public Integer getListCount(GoodsMaterial goodsMaterial) throws Exception {
		return goodsMaterialDao.loadListCount(goodsMaterial);
	}
	public GoodsMaterialDao getGoodsMaterialDao() {
		return goodsMaterialDao;
	}
	@Resource
	public void setGoodsMaterialDao(GoodsMaterialDao goodsMaterialDao) {
		this.goodsMaterialDao = goodsMaterialDao;
	}
	public GoodsMaterialTypeDao getGoodsMaterialTypeDao() {
		return goodsMaterialTypeDao;
	}
	@Resource
	public void setGoodsMaterialTypeDao(GoodsMaterialTypeDao goodsMaterialTypeDao) {
		this.goodsMaterialTypeDao = goodsMaterialTypeDao;
	}
	public GoodsStockDao getGoodsStockDao() {
		return goodsStockDao;
	}
	@Resource
	public void setGoodsStockDao(GoodsStockDao goodsStockDao) {
		this.goodsStockDao = goodsStockDao;
	}
	public GoodsInStockDao getGoodsInStockDao() {
		return goodsInStockDao;
	}
	@Resource
	public void setGoodsInStockDao(GoodsInStockDao goodsInStockDao) {
		this.goodsInStockDao = goodsInStockDao;
	}
	
}
