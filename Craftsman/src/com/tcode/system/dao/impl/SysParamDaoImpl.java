package com.tcode.system.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.common.dao.BaseDao;
import com.tcode.system.dao.SysParamDao;
import com.tcode.system.model.SysParam;

@Component("sysParamDao")
public class SysParamDaoImpl extends BaseDao<SysParam, Serializable> implements SysParamDao {

	@Override
	public List<SysParam> loadAll() throws Exception {
		return super.loadAll();
	}

	@Override
	public SysParam loadById(Integer id) throws Exception {
		return super.loadById(SysParam.class, id);
	}

	@Override
	public List<SysParam> loadListPage(SysParam param, int start, int limit) throws Exception {
		List<SysParam> roleList = null;
		DetachedCriteria criteria = connectionCriteria(param);
		criteria.addOrder(Order.asc("id"));
		roleList = (List<SysParam>) super.loadListForPage(criteria, start, limit);
		return roleList;
	}

	@Override
	public Integer loadListCount(SysParam param) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(param);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(SysParam param){
		DetachedCriteria criteria = DetachedCriteria.forClass(SysParam.class);  
		if(param != null){
			if(param.getId() != null)
				criteria.add(Restrictions.eq("id", param.getId()));
		}
		return criteria;
	}

	@Override
	public SysParam loadByKey(String key) throws Exception {
		List<SysParam> paramList = super.loadList("from SysParam p where p.paramKey = ?", key);
		return paramList.size() > 0 ? paramList.get(0) : null;
	}
}
