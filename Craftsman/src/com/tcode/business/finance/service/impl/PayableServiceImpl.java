package com.tcode.business.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.finance.dao.PayableDao;
import com.tcode.business.finance.model.Payable;
import com.tcode.business.finance.service.PayableService;
import com.tcode.business.shop.dao.SupplierDao;
import com.tcode.business.shop.model.Supplier;
import com.tcode.core.util.Utils;

@Component("payableService")
public class PayableServiceImpl implements PayableService {

	private PayableDao payableDao;
	private SupplierDao supplierDao;

	@Override
	public Payable getById(String deptCode, Integer supplierId) throws Exception {
		return payableDao.loadById(deptCode, supplierId);
	}

	@Override
	public List<Payable> getListByDept(String deptCode) throws Exception {
		return payableDao.loadByDept(deptCode);
	}

	@Override
	public List<Payable> getListByDeptPage(Payable payable, Integer start, Integer limit) throws Exception {
		List<Payable> list = payableDao.loadByDeptPage(payable, start, limit);
		for(Payable pay : list) {
			Supplier supplier = supplierDao.loadById(pay.getSupplierId());
			if(!Utils.isEmpty(supplier)){
				if(!Utils.isEmpty(supplier.getMobile()))
					pay.setMobile(supplier.getMobile());
				if(!Utils.isEmpty(supplier.getContact()))
					pay.setContact(supplier.getContact());
				if(!Utils.isEmpty(supplier.getName()))
					pay.setName(supplier.getName());
			}
			if(Utils.isEmpty(pay.getRepayment()))
				pay.setRepayment(0.0);
			pay.setResidual(pay.getPayable() - pay.getRepayment());
		}
		return list;
	}
	
	@Override
	public List<Double> getSumByBoss(String companyId, String date, String deptCode) throws Exception {
		return payableDao.loadSumByBoss(companyId, date, deptCode);
	}

	@Override
	public List<Payable> getListByBossPage(String companyId, String deptCode, Integer start, Integer limit) throws Exception {
		List<Payable> list = payableDao.loadByBossPage(companyId, deptCode, start, limit);
		for(Payable pay : list) {
			Supplier supplier = supplierDao.loadById(pay.getSupplierId());
			if(!Utils.isEmpty(supplier)){
				if(!Utils.isEmpty(supplier.getName()))
					pay.setName(supplier.getName());
			}
			if(Utils.isEmpty(pay.getRepayment()))
				pay.setRepayment(0.0);
			pay.setResidual(pay.getPayable() - pay.getRepayment());
		}
		return list;
	}

	@Override
	public Integer getListByDeptCount(Payable payable) throws Exception {
		return payableDao.loadByDeptCount(payable);
	}

	@Override
	public void insert(Payable payable) throws Exception {
		payableDao.save(payable);
	}

	@Override
	public void update(Payable payable) throws Exception {
		payableDao.edit(payable);
	}

	@Override
	public void delete(Payable payable) throws Exception {
		payableDao.remove(payable);
	}

	public PayableDao getPayableDao() {
		return payableDao;
	}
	@Resource
	public void setPayableDao(PayableDao payableDao) {
		this.payableDao = payableDao;
	}

	public SupplierDao getSupplierDao() {
		return supplierDao;
	}
	@Resource
	public void setSupplierDao(SupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	
}
