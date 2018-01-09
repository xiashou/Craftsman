package com.tcode.business.shop.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.SupplierDao;
import com.tcode.business.shop.model.Supplier;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("supplierDao")
public class SupplierDaoImpl extends BaseDao<Supplier, Serializable> implements SupplierDao {

	@Override
	public Supplier loadById(Integer id) throws Exception {
		return super.loadById(Supplier.class, id);
	}
	
	@Override
	public List<Supplier> loadByDept(String deptCode) throws Exception {
		return super.loadList("from Supplier s where s.deptCode = ?", deptCode);
	}
	
	@Override
	public List<Supplier> loadByKeyword(String deptCode, String keyword) throws Exception {
		if(!Utils.isEmpty(keyword))
			return super.loadList("from Supplier s where s.deptCode = ? and s.name like '%" + keyword.trim() + "%'", deptCode);
		else
			return super.loadList("from Supplier s where s.deptCode = ?", deptCode);
	}
	
	@Override
	public Supplier loadByName(String deptCode, String name) throws Exception {
		List<Supplier> list = super.loadList("from Supplier s where s.deptCode = ? and s.name = ?", deptCode, name);
		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public List<Supplier> loadListPage(Supplier supplier, int start, int limit) throws Exception {
		List<Supplier> list = null;
		DetachedCriteria criteria = connectionCriteria(supplier);
		criteria.addOrder(Order.asc("id"));
		list = (List<Supplier>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(Supplier supplier) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(supplier);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(Supplier supplier){
		DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);  
		if(!Utils.isEmpty(supplier)){
			if(!Utils.isEmpty(supplier.getId()))
				criteria.add(Restrictions.eq("id", supplier.getId()));
		}
		return criteria;
	}

}
