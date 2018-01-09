package com.tcode.business.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.SupplierDao;
import com.tcode.business.shop.model.Supplier;
import com.tcode.business.shop.service.SupplierService;

@Component("supplierService")
public class SupplierServiceImpl implements SupplierService {

	private SupplierDao supplierDao;

	@Override
	public Supplier getById(Integer id) throws Exception {
		return supplierDao.loadById(id);
	}
	
	@Override
	public Supplier getByName(String deptCode, String name) throws Exception {
		return supplierDao.loadByName(deptCode, name);
	}
	
	@Override
	public List<Supplier> getListByDept(String deptCode) throws Exception {
		return supplierDao.loadByDept(deptCode);
	}
	
	@Override
	public List<Supplier> getListByKeyword(String deptCode, String keyword) throws Exception {
		return supplierDao.loadByKeyword(deptCode, keyword);
	}

	@Override
	public void insert(Supplier supplier) throws Exception {
		supplierDao.save(supplier);
	}

	@Override
	public void update(Supplier supplier) throws Exception {
		supplierDao.edit(supplier);
	}

	@Override
	public void delete(Supplier supplier) throws Exception {
		supplierDao.remove(supplier);
	}

	@Override
	public List<Supplier> getListPage(Supplier supplier, int start, int limit) throws Exception {
		return supplierDao.loadListPage(supplier, start, limit);
	}

	@Override
	public Integer getListCount(Supplier supplier) throws Exception {
		return supplierDao.loadListCount(supplier);
	}

	public SupplierDao getSupplierDao() {
		return supplierDao;
	}
	@Resource
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	
	
}
