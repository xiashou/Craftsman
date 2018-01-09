package com.tcode.business.basic.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseAreaCodeDao;
import com.tcode.business.basic.model.BaseAreaCode;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("areaCodeDao")
public class BaseAreaCodeDaoImpl extends BaseDao<BaseAreaCode, Serializable> implements BaseAreaCodeDao {

	@Override
	public BaseAreaCode loadById(Integer id) throws Exception {
		return super.loadById(BaseAreaCode.class, id);
	}

	@Override
	public List<BaseAreaCode> loadListPage(BaseAreaCode areaCode, int start, int limit) throws Exception {
		List<BaseAreaCode> areaCodeList = null;
		DetachedCriteria criteria = connectionCriteria(areaCode);
		criteria.addOrder(Order.asc("id"));
		areaCodeList = (List<BaseAreaCode>) super.loadListForPage(criteria, start, limit);
		return areaCodeList;
	}

	@Override
	public Integer loadListCount(BaseAreaCode areaCode) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(areaCode);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(BaseAreaCode areaCode){
		DetachedCriteria criteria = DetachedCriteria.forClass(BaseAreaCode.class);  
		if(areaCode != null){
			if(!Utils.isEmpty(areaCode.getId()))
				criteria.add(Restrictions.eq("id", areaCode.getId()));
		}
		return criteria;
	}

}
