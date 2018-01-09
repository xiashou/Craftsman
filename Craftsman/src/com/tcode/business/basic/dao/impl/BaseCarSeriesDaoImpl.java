package com.tcode.business.basic.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseCarSeriesDao;
import com.tcode.business.basic.model.BaseCarSeries;
import com.tcode.common.dao.BaseDao;

@Component("carSeriesDao")
public class BaseCarSeriesDaoImpl extends BaseDao<BaseCarSeries, Serializable> implements BaseCarSeriesDao {

	@Override
	public BaseCarSeries loadById(Integer id) throws Exception {
		return super.loadById(BaseCarSeries.class, id);
	}
	
	public BaseCarSeries loadByCarId(Integer carId) throws Exception {
		return (BaseCarSeries) super.loadEntity("from BaseCarSeries s where s.id = (select c.carSeries from MemberCar c where id = ?)", carId);
	}

	@Override
	public List<BaseCarSeries> loadByBrandId(Integer brandId) throws Exception {
		return super.loadList("from BaseCarSeries s where s.brandId = ?", brandId);
	}

	@Override
	public List<BaseCarSeries> loadListPage(BaseCarSeries carSeries, int start, int limit) throws Exception {
		List<BaseCarSeries> carSeriesList = null;
		DetachedCriteria criteria = connectionCriteria(carSeries);
		criteria.addOrder(Order.asc("id"));
		carSeriesList = (List<BaseCarSeries>) super.loadListForPage(criteria, start, limit);
		return carSeriesList;
	}

	@Override
	public Integer loadListCount(BaseCarSeries carSeries) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(carSeries);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(BaseCarSeries carSeries){
		DetachedCriteria criteria = DetachedCriteria.forClass(BaseCarSeries.class);  
		if(carSeries != null){
			if(carSeries.getId() != null)
				criteria.add(Restrictions.eq("id", carSeries.getId()));
		}
		return criteria;
	}

}
