package com.tcode.business.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsHourDao;
import com.tcode.business.goods.dao.GoodsHourTypeDao;
import com.tcode.business.goods.dao.GoodsMaterialDao;
import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.model.GoodsHourType;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.shop.dao.CommissionDao;
import com.tcode.business.shop.dao.TypeCommissionDao;
import com.tcode.business.shop.model.Commission;
import com.tcode.business.shop.model.TypeCommission;
import com.tcode.business.shop.service.CommissionService;
import com.tcode.core.util.Utils;

@Component("commissionService")
public class CommissionServiceImpl implements CommissionService {

	private CommissionDao commissionDao;
	private TypeCommissionDao typeCommissionDao;
	private GoodsHourDao goodsHourDao;
	private GoodsHourTypeDao goodsHourTypeDao;
	private GoodsMaterialDao goodsMaterialDao;

	@Override
	public Commission getCommissionById(String goodsId, String deptCode) throws Exception {
		return commissionDao.loadById(goodsId, deptCode);
	}

	@Override
	public List<Commission> getListHourByDept(String deptCode) throws Exception {
		List<GoodsHour> gList = goodsHourDao.loadAll(deptCode);
		List<Commission> list = new ArrayList<Commission>();
		Commission commission = null;
		
		//添加一条默认记录，对应所有手动录入的项目
//		GoodsHour goodsHour = new GoodsHour();
//		goodsHour.setId("00000000");
//		goodsHour.setName("默认项目");
//		goodsHour.setPrice(0.0);
//		gList.add(0, goodsHour);
		
		for(GoodsHour hour : gList) {
			commission = commissionDao.loadById(hour.getId(), deptCode);
			if(Utils.isEmpty(commission)){
				commission = new Commission();
				commission.setGoodsId(hour.getId());
				commission.setCommission("0");
			}
			commission.setType(Utils.isEmpty(hour.getTypeId()) ? "默认" : hour.getTypeId() + "");
			commission.setGoodsName(hour.getName());
			commission.setPrice(hour.getPrice());
			list.add(commission);
		}
		return list;
	}
	
	@Override
	public List<Commission> getListMateByDept(String deptCode, Integer typeId) throws Exception {
//		List<GoodsMaterial> mList = goodsMaterialDao.loadByDept(companyId);
		List<GoodsMaterial> mList = goodsMaterialDao.loadByType(typeId);
		List<Commission> list = new ArrayList<Commission>();
		Commission commission = null;
		
		for(GoodsMaterial material : mList) {
			commission = commissionDao.loadById(material.getId(), deptCode);
			if(Utils.isEmpty(commission)){
				commission = new Commission();
				commission.setGoodsId(material.getId());
				commission.setCommission("0");
			}  
			commission.setType(material.getTypeId() + "");
			commission.setGoodsName(material.getName());
			commission.setPrice(material.getPrice());
			list.add(commission);
		}
		return list;
	}
	
	@Override
	public TypeCommission getTypeCommById(Integer typeId, String deptCode) throws Exception {
		return typeCommissionDao.loadById(typeId, deptCode);
	}
	
	@Override
	public List<TypeCommission> getTypeCommByDept(String deptCode) throws Exception {
		List<GoodsHourType> typeList = goodsHourTypeDao.loadTypeByDeptCode(deptCode);
		List<TypeCommission> list = new ArrayList<TypeCommission>();
		TypeCommission typeComm = null;
		for(GoodsHourType type : typeList) {
			typeComm = typeCommissionDao.loadById(type.getId(), deptCode);
			if(Utils.isEmpty(typeComm)){
				typeComm = new TypeCommission();
				typeComm.setTypeId(type.getId());
				typeComm.setCommission("0");
			}
			list.add(typeComm);
		}
		return list;
	}

	@Override
	public void deleteByType(String deptCode, String type) throws Exception {
		commissionDao.removeByType(deptCode, type);
	}

	@Override
	public void insert(Commission commission) throws Exception {
		commissionDao.save(commission);
	}

	@Override
	public void update(Commission commission) throws Exception {
		commissionDao.edit(commission);
	}

	@Override
	public void delete(Commission commission) throws Exception {
		commissionDao.remove(commission);
	}
	
	@Override
	public void insertType(TypeCommission typeComm) throws Exception {
		typeCommissionDao.save(typeComm);
	}
	
	@Override
	public void updateType(TypeCommission typeComm) throws Exception {
		typeCommissionDao.edit(typeComm);
	}

	@Override
	public void deleteType(TypeCommission typeComm) throws Exception {
		typeCommissionDao.remove(typeComm);
	}
	
	
	
	
	

	public CommissionDao getCommissionDao() {
		return commissionDao;
	}
	@Resource
	public void setCommissionDao(CommissionDao commissionDao) {
		this.commissionDao = commissionDao;
	}
	public GoodsHourDao getGoodsHourDao() {
		return goodsHourDao;
	}
	@Resource
	public void setGoodsHourDao(GoodsHourDao goodsHourDao) {
		this.goodsHourDao = goodsHourDao;
	}
	public GoodsHourTypeDao getGoodsHourTypeDao() {
		return goodsHourTypeDao;
	}
	@Resource
	public void setGoodsHourTypeDao(GoodsHourTypeDao goodsHourTypeDao) {
		this.goodsHourTypeDao = goodsHourTypeDao;
	}
	public GoodsMaterialDao getGoodsMaterialDao() {
		return goodsMaterialDao;
	}
	@Resource
	public void setGoodsMaterialDao(GoodsMaterialDao goodsMaterialDao) {
		this.goodsMaterialDao = goodsMaterialDao;
	}
	public TypeCommissionDao getTypeCommissionDao() {
		return typeCommissionDao;
	}
	@Resource
	public void setTypeCommissionDao(TypeCommissionDao typeCommissionDao) {
		this.typeCommissionDao = typeCommissionDao;
	}
	
	
}
