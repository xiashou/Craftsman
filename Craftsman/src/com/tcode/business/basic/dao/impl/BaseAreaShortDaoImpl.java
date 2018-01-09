package com.tcode.business.basic.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseAreaShortDao;
import com.tcode.business.basic.model.BaseAreaShort;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("areaShortDao")
public class BaseAreaShortDaoImpl extends BaseDao<BaseAreaShort, Serializable> implements BaseAreaShortDao {

	@Override
	public BaseAreaShort loadById(Integer id) throws Exception {
		return super.loadById(BaseAreaShort.class, id);
	}

	@Override
	public List<BaseAreaShort> loadListPage(BaseAreaShort areaShort, int start, int limit) throws Exception {
		List<BaseAreaShort> areaShortList = null;
		DetachedCriteria criteria = connectionCriteria(areaShort);
		criteria.addOrder(Order.asc("id"));
		areaShortList = (List<BaseAreaShort>) super.loadListForPage(criteria, start, limit);
		return areaShortList;
	}

	@Override
	public Integer loadListCount(BaseAreaShort areaShort) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(areaShort);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(BaseAreaShort areaShort){
		DetachedCriteria criteria = DetachedCriteria.forClass(BaseAreaShort.class);  
		if(!Utils.isEmpty(areaShort)){
			if(!Utils.isEmpty(areaShort.getId()))
				criteria.add(Restrictions.eq("id", areaShort.getId()));
		}
		return criteria;
	}

}
