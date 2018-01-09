package com.tcode.system.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;
import com.tcode.system.dao.SysDeptDao;
import com.tcode.system.model.SysDept;

@Component("sysDeptDao")
public class SysDeptDaoImpl extends BaseDao<SysDept, Serializable> implements SysDeptDao {

	@Override
	public SysDept loadById(String id) throws Exception {
		return super.loadById(SysDept.class, id);
	}
	
	@Override
	public List<SysDept> loadAllSimple() throws Exception {
		return super.loadList("select new SysDept(d.id, d.deptName) from SysDept d");
	}
	
	@Override
	public List<SysDept> loadByParentId(String id) throws Exception {
		return super.loadList("from SysDept d where d.parentId = ?", id);
	}
	
	@Override
	public List<SysDept> loadByParentIdAndArea(String id, String areaId) throws Exception {
		return super.loadList("from SysDept d where d.parentId = ? and d.areaId like '" + areaId + "%'", id);
	}
	
	@Override
	public SysDept loadByDeptCode(String deptCode) throws Exception {
		List<SysDept> list = super.loadList("from SysDept d where d.deptCode = ? and d.deptType = 3 ", deptCode);
		return Utils.isEmpty(list) ? null : list.get(0);
	}
	
	@Override
	public List<SysDept> loadByCompanyId(String companyId) throws Exception {
		return super.loadList("from SysDept d where d.companyId = ? and d.deptType = 3 and d.enable = 1", companyId);
	}
	
	@Override
	public String loadMaxIdByParentId(String parentId) throws Exception {
		String hql = "select max(d.id) from SysDept d where d.parentId = '" + parentId + "'";
		String maxId = (String) super.loadUniqueResult(hql);
		return maxId;
	}

	@Override
	public List<SysDept> loadListPage(SysDept dept, int start, int limit) throws Exception {
		List<SysDept> userList = null;
		DetachedCriteria criteria = connectionCriteria(dept);
		criteria.addOrder(Order.asc("id"));
		userList = (List<SysDept>) super.loadListForPage(criteria, start, limit);
		return userList;
	}

	@Override
	public Integer loadListCount(SysDept dept) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(dept);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(SysDept dept){
		DetachedCriteria criteria = DetachedCriteria.forClass(SysDept.class);  
		if(!Utils.isEmpty(dept)){
			if(!Utils.isEmpty(dept.getParentId()))
				criteria.add(Restrictions.like("id", dept.getParentId(), MatchMode.START));
			if(!Utils.isEmpty(dept.getDeptType()))
				criteria.add(Restrictions.eq("deptType", dept.getDeptType()));
		}
		return criteria;
	}

}
