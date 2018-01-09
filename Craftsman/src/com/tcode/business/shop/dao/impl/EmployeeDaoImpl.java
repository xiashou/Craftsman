package com.tcode.business.shop.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.EmployeeDao;
import com.tcode.business.shop.model.Employee;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("employeeDao")
public class EmployeeDaoImpl extends BaseDao<Employee, Serializable> implements EmployeeDao {

	@Override
	public Employee loadById(Integer id) throws Exception {
		return super.loadById(Employee.class, id);
	}
	
	@Override
	public List<Employee> loadByDept(String deptCode) throws Exception {
		return super.loadList("from Employee e where e.deptCode = ?", deptCode);
	}

	@Override
	public List<Employee> loadByGroup(String deptCode, Boolean group) throws Exception {
		return super.loadList("from Employee e where e.deptCode = ? and e.isGroup = ?", deptCode, group);
	}
	
	@Override
	public List<Employee> loadListPage(Employee employee, int start, int limit) throws Exception {
		List<Employee> list = null;
		DetachedCriteria criteria = connectionCriteria(employee);
		criteria.addOrder(Order.asc("id"));
		list = (List<Employee>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(Employee employee) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(employee);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(Employee employee){
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);  
		if(!Utils.isEmpty(employee)){
			if(!Utils.isEmpty(employee.getId()))
				criteria.add(Restrictions.eq("id", employee.getId()));
		}
		return criteria;
	}

}
