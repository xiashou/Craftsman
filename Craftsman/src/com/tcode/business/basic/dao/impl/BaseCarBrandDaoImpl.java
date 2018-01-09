package com.tcode.business.basic.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseCarBrandDao;
import com.tcode.business.basic.model.BaseCarBrand;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("carBrandDao")
public class BaseCarBrandDaoImpl extends BaseDao<BaseCarBrand, Serializable> implements BaseCarBrandDao {

	@Override
	public List<BaseCarBrand> loadAll() throws Exception {
		return super.loadList("from BaseCarBrand b order by b.shortCode asc");
	}
	
	@Override
	public BaseCarBrand loadById(Integer id) throws Exception {
		return super.loadById(BaseCarBrand.class, id);
	}

	@Override
	public List<BaseCarBrand> loadByCode(String code) throws Exception {
		return super.loadList("from BaseCarBrand c where c.shortCode like '%" + code + "%'");
	}

	@Override
	public List<BaseCarBrand> loadListPage(BaseCarBrand carBrand, int start, int limit) throws Exception {
		List<BaseCarBrand> carBrandList = null;
		DetachedCriteria criteria = connectionCriteria(carBrand);
		criteria.addOrder(Order.asc("id"));
		carBrandList = (List<BaseCarBrand>) super.loadListForPage(criteria, start, limit);
		return carBrandList;
	}

	@Override
	public Integer loadListCount(BaseCarBrand carBrand) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(carBrand);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(BaseCarBrand carBrand){
		DetachedCriteria criteria = DetachedCriteria.forClass(BaseCarBrand.class);  
		if(carBrand != null){
			if(carBrand.getId() != null)
				criteria.add(Restrictions.eq("id", carBrand.getId()));
			if(!Utils.isEmpty(carBrand.getShortCode()))
				criteria.add(Restrictions.like("shortCode", carBrand.getShortCode(), MatchMode.ANYWHERE));
		}
		return criteria;
	}

}
