package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallGoodsTypeDao;
import com.tcode.business.mall.model.MallGoodsType;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("mallGoodsTypeDao")
public class MallGoodsTypeDaoImpl extends BaseDao<MallGoodsType, Serializable> implements MallGoodsTypeDao {

	@Override
	public MallGoodsType loadById(Integer id) throws Exception {
		return super.loadById(MallGoodsType.class, id);
	}
	
	@Override
	public List<MallGoodsType> loadByAppId(String appId) throws Exception {
		return super.loadList("from MallGoodsType t where t.appId = ? order by t.sortNo", appId);
	}

	@Override
	public List<MallGoodsType> loadListPage(MallGoodsType goodsType, int start, int limit) throws Exception {
		List<MallGoodsType> brandList = null;
		DetachedCriteria criteria = connectionCriteria(goodsType);
		criteria.addOrder(Order.asc("id"));
		brandList = (List<MallGoodsType>) super.loadListForPage(criteria, start, limit);
		return brandList;
	}

	@Override
	public Integer loadListCount(MallGoodsType goodsType) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(goodsType);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(MallGoodsType goodsType){
		DetachedCriteria criteria = DetachedCriteria.forClass(MallGoodsType.class);  
		if(goodsType != null){
			if(!Utils.isEmpty(goodsType.getId()))
				criteria.add(Restrictions.eq("id", goodsType.getId()));
			if(!Utils.isEmpty(goodsType.getAppId()))
				criteria.add(Restrictions.eq("appId", goodsType.getAppId()));
		}
		return criteria;
	}

}
