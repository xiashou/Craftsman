package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallSendModeDao;
import com.tcode.business.mall.model.MallSendMode;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("mallSendModeDao")
public class MallSendModeDaoImpl extends BaseDao<MallSendMode, Serializable> implements MallSendModeDao {

	@Override
	public MallSendMode loadById(Integer id) throws Exception {
		return super.loadById(MallSendMode.class, id);
	}
	
	@Override
	public List<MallSendMode> loadByAppId(String appId) throws Exception {
		return super.loadList("from MallSendMode s where s.appId = ?", appId);
	}

	@Override
	public List<MallSendMode> loadListPage(MallSendMode sendMode, int start, int limit) throws Exception {
		List<MallSendMode> brandList = null;
		DetachedCriteria criteria = connectionCriteria(sendMode);
		criteria.addOrder(Order.asc("id"));
		brandList = (List<MallSendMode>) super.loadListForPage(criteria, start, limit);
		return brandList;
	}

	@Override
	public Integer loadListCount(MallSendMode sendMode) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(sendMode);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(MallSendMode sendMode){
		DetachedCriteria criteria = DetachedCriteria.forClass(MallSendMode.class);  
		if(sendMode != null){
			if(!Utils.isEmpty(sendMode.getId()))
				criteria.add(Restrictions.eq("id", sendMode.getId()));
			if(!Utils.isEmpty(sendMode.getAppId()))
				criteria.add(Restrictions.eq("appId", sendMode.getAppId()));
		}
		return criteria;
	}

}
