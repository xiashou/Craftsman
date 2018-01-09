package com.tcode.business.basic.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseCarModelDao;
import com.tcode.business.basic.model.BaseCarModel;
import com.tcode.common.dao.BaseDao;

@Component("carModelDao")
public class BaseCarModelDaoImpl extends BaseDao<BaseCarModel, Serializable> implements BaseCarModelDao {

	@Override
	public BaseCarModel loadById(Integer id) throws Exception {
		return super.loadById(BaseCarModel.class, id);
	}

	@Override
	public List<BaseCarModel> loadBySeriesId(Integer seriesId) throws Exception {
		return super.loadList("from BaseCarModel m where m.seriesId = ?", seriesId);
	}

	@Override
	public List<BaseCarModel> loadListPage(BaseCarModel carModel, int start, int limit) throws Exception {
		List<BaseCarModel> carModelList = null;
		DetachedCriteria criteria = connectionCriteria(carModel);
		criteria.addOrder(Order.asc("id"));
		carModelList = (List<BaseCarModel>) super.loadListForPage(criteria, start, limit);
		return carModelList;
	}

	@Override
	public Integer loadListCount(BaseCarModel carModel) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(carModel);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(BaseCarModel carModel){
		DetachedCriteria criteria = DetachedCriteria.forClass(BaseCarModel.class);  
		if(carModel != null){
			if(carModel.getId() != null)
				criteria.add(Restrictions.eq("id", carModel.getId()));
		}
		return criteria;
	}

}
