package com.tcode.business.goods.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.finance.dao.PayableDao;
import com.tcode.business.finance.model.Payable;
import com.tcode.business.goods.dao.GoodsInStockDao;
import com.tcode.business.goods.dao.GoodsMaterialDao;
import com.tcode.business.goods.dao.GoodsStockDao;
import com.tcode.business.goods.model.GoodsInStock;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.goods.model.GoodsStock;
import com.tcode.business.goods.service.GoodsInStockService;
import com.tcode.business.shop.dao.SupplierDao;
import com.tcode.business.shop.model.Supplier;
import com.tcode.core.util.Utils;

@Component("goodsInStockService")
public class GoodsInStockServiceImpl implements GoodsInStockService {
	
	private GoodsInStockDao goodsInStockDao;
	private GoodsMaterialDao goodsMaterialDao;
	private GoodsStockDao goodsStockDao;
	private PayableDao payableDao;
	private SupplierDao supplierDao;

	@Override
	public GoodsInStock getGoodsInStockById(Integer id) throws Exception {
		return goodsInStockDao.loadById(id);
	}
	@Override
	public List<GoodsInStock> getListByDept(String deptCode) throws Exception {
		return goodsInStockDao.loadByDept(deptCode);
	}
	@Override
	public List<GoodsInStock> getListByInNumber(String inNumber) throws Exception {
		List<GoodsInStock> inList = goodsInStockDao.loadByInNumber(inNumber);
		if(!Utils.isEmpty(inList)){
			for(GoodsInStock inStock : inList){
				if(!Utils.isEmpty(inStock.getSupplier())){
					Supplier supplier = supplierDao.loadById(inStock.getSupplier());
					if(!Utils.isEmpty(supplier))
						inStock.setSupplierName(supplier.getName());
				}
			}
		}
		return inList;
	}
	@Override
	public List<GoodsInStock> getListByGoodsId(String deptCode, String goodsId) throws Exception {
		return goodsInStockDao.loadByGoodsId(deptCode, goodsId);
	}
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public Integer insertMoreGoodsInStock(List<GoodsInStock> inStockList) throws Exception {
		int count = 0;
		for(GoodsInStock inStock : inStockList) {
			//将最后一次入库价格写入商品属性
			if(!Utils.isEmpty(inStock.getInPrice())){
				GoodsMaterial goods = goodsMaterialDao.loadById(inStock.getGoodsId());
				if(!Utils.isEmpty(goods)){
					goods.setInPrice(inStock.getInPrice());
					goodsMaterialDao.edit(goods);
				}
			}
			//未结算时，增加一笔应付
//			if(!Utils.isEmpty(inStock.getSettlement()) && inStock.getSettlement() == 0){
				Payable payable = payableDao.loadById(inStock.getDeptCode(), inStock.getSupplier());
				if(!Utils.isEmpty(payable)){
					payable.setPayable(payable.getPayable() + (inStock.getInPrice() * inStock.getNumber()));
					payable.setStatus(0);
					payable.setLastUpdate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				} else {
					payable = new Payable(inStock.getDeptCode(), inStock.getSupplier(), (inStock.getInPrice() * inStock.getNumber()));
					payableDao.save(payable);
				}
//			}
			goodsInStockDao.save(inStock); count++;
		}
		return count;
	}
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public Integer insertReturnGoodsInStock(List<GoodsInStock> inStockList) throws Exception {
		int count = 0;
		for(GoodsInStock inStock : inStockList) {
			//退货时不管是否结算，都产生一笔负的未结算应付记录
			if(!Utils.isEmpty(inStock)){
				Payable payable = payableDao.loadById(inStock.getDeptCode(), Utils.isEmpty(inStock.getSupplier()) ? 0 : inStock.getSupplier());
				if(!Utils.isEmpty(payable)){
					payable.setPayable(payable.getPayable() + (inStock.getInPrice() * inStock.getNumber()));
					payable.setLastUpdate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				} else {
					payable = new Payable(inStock.getDeptCode(), inStock.getSupplier(), (inStock.getInPrice() * inStock.getNumber()));
					payableDao.save(payable);
				}
			}
			inStock.setSettlement(0);
			inStock.setSettdate("");
			goodsInStockDao.save(inStock); count++;
		}
		return count;
	}
	@Override
	public List<GoodsInStock> getListPage(GoodsInStock inStock, int start, int limit) throws Exception {
		List<GoodsInStock> inStockList = goodsInStockDao.loadListPage(inStock, start, limit);
		if(!Utils.isEmpty(inStock.getStockNum()) && inStock.getStockNum() > 0){
			for(GoodsInStock stock : inStockList) {
				if(!Utils.isEmpty(stock.getGoodsId()) && !Utils.isEmpty(stock.getDeptCode())){
					GoodsStock gStock = goodsStockDao.loadById(stock.getGoodsId(), stock.getDeptCode());
					if(!Utils.isEmpty(gStock))
						stock.setStockNum(gStock.getNumber());
				}
			}
		}
		return inStockList;
	}
	@Override
	public Integer getListCount(GoodsInStock inStock) throws Exception {
		return goodsInStockDao.loadListCount(inStock);
	}
	@Override
	public void insert(GoodsInStock inStock) throws Exception {
		goodsInStockDao.save(inStock);
	}
	@Override
	public void update(GoodsInStock inStock) throws Exception {
		goodsInStockDao.edit(inStock);
	}
	@Override
	public void delete(GoodsInStock inStock) throws Exception {
		goodsInStockDao.remove(inStock);
	}
	
	public GoodsInStockDao getGoodsInStockDao() {
		return goodsInStockDao;
	}
	@Resource
	public void setGoodsInStockDao(GoodsInStockDao goodsInStockDao) {
		this.goodsInStockDao = goodsInStockDao;
	}
	public GoodsMaterialDao getGoodsMaterialDao() {
		return goodsMaterialDao;
	}
	@Resource
	public void setGoodsMaterialDao(GoodsMaterialDao goodsMaterialDao) {
		this.goodsMaterialDao = goodsMaterialDao;
	}
	public PayableDao getPayableDao() {
		return payableDao;
	}
	@Resource
	public void setPayableDao(PayableDao payableDao) {
		this.payableDao = payableDao;
	}
	public GoodsStockDao getGoodsStockDao() {
		return goodsStockDao;
	}
	@Resource
	public void setGoodsStockDao(GoodsStockDao goodsStockDao) {
		this.goodsStockDao = goodsStockDao;
	}
	public SupplierDao getSupplierDao() {
		return supplierDao;
	}
	@Resource
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

}
