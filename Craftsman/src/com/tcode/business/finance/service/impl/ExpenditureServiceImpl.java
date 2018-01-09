package com.tcode.business.finance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.finance.dao.AssetsDao;
import com.tcode.business.finance.dao.ExpenditureDao;
import com.tcode.business.finance.model.Expenditure;
import com.tcode.business.finance.service.ExpenditureService;
import com.tcode.core.util.Utils;

@Component("expenditureService")
public class ExpenditureServiceImpl implements ExpenditureService {

	private ExpenditureDao expenditureDao;
	private AssetsDao assetsDao;

	@Override
	public Expenditure getById(Integer id) throws Exception {
		return expenditureDao.loadById(id);
	}

	@Override
	public List<Expenditure> getListByDept(String deptCode) throws Exception {
		return expenditureDao.loadByDept(deptCode);
	}

	@Override
	public List<Expenditure> getListByDeptPage(Expenditure expend, Integer start, Integer limit) throws Exception {
		return expenditureDao.loadByDeptPage(expend, start, limit);
	}

	@Override
	public Integer getCountByDept(Expenditure expend) throws Exception {
		return expenditureDao.loadByDeptCount(expend);
	}

	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void insert(Expenditure expend) throws Exception {
		if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getAssetsId()))
			assetsDao.editPriceById(expend.getAssetsId(), expend.getPrice() * -1);
		expenditureDao.save(expend);
	}

	@Override
	public void update(Expenditure expend) throws Exception {
		expenditureDao.edit(expend);
	}

	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void delete(Expenditure expend) throws Exception {
		if(!Utils.isEmpty(expend) && !Utils.isEmpty(expend.getAssetsId()))
			assetsDao.editPriceById(expend.getAssetsId(), expend.getPrice());
		expenditureDao.remove(expend);
	}

	public ExpenditureDao getExpenditureDao() {
		return expenditureDao;
	}
	@Resource
	public void setExpenditureDao(ExpenditureDao expenditureDao) {
		this.expenditureDao = expenditureDao;
	}

	public AssetsDao getAssetsDao() {
		return assetsDao;
	}
	@Resource
	public void setAssetsDao(AssetsDao assetsDao) {
		this.assetsDao = assetsDao;
	}
	
	
}
