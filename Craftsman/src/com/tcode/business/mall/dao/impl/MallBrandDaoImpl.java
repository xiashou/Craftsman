package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallBrandDao;
import com.tcode.business.mall.model.MallBrand;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("mallBrandDao")
public class MallBrandDaoImpl extends BaseDao<MallBrand, Serializable> implements MallBrandDao {

	@Override
	public MallBrand loadById(Integer id) throws Exception {
		return super.loadById(MallBrand.class, id);
	}

	@Override
	public List<MallBrand> loadListPage(MallBrand mallBrand, int start, int limit) throws Exception {
		List<MallBrand> brandList = null;
		DetachedCriteria criteria = connectionCriteria(mallBrand);
		criteria.addOrder(Order.asc("id"));
		brandList = (List<MallBrand>) super.loadListForPage(criteria, start, limit);
		return brandList;
	}

	@Override
	public Integer loadListCount(MallBrand mallBrand) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(mallBrand);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(MallBrand mallBrand){
		DetachedCriteria criteria = DetachedCriteria.forClass(MallBrand.class);  
		if(mallBrand != null){
			if(!Utils.isEmpty(mallBrand.getId()))
				criteria.add(Restrictions.eq("id", mallBrand.getId()));
			if(!Utils.isEmpty(mallBrand.getAppId()))
				criteria.add(Restrictions.eq("appId", mallBrand.getAppId()));
		}
		return criteria;
	}

}
