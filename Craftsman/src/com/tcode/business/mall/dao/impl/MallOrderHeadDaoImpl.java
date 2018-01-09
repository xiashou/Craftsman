package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallOrderHeadDao;
import com.tcode.business.mall.model.MallOrderHead;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("mallOrderHeadDao")
public class MallOrderHeadDaoImpl extends BaseDao<MallOrderHead, Serializable> implements MallOrderHeadDao {

	@Override
	public MallOrderHead loadById(String orderId) throws Exception {
		return (MallOrderHead) super.loadEntity("from MallOrderHead oh where oh.orderId = ?", orderId);
	}
	
	@Override
	public List<MallOrderHead> loadListPage(MallOrderHead orderHead, int start, int limit) throws Exception {
		List<MallOrderHead> headList = null;
		DetachedCriteria criteria = connectionCriteria(orderHead);
		criteria.addOrder(Order.desc("saleDate"));
		headList = (List<MallOrderHead>) super.loadListForPage(criteria, start, limit);
		return headList;
	}

	@Override
	public Integer loadListCount(MallOrderHead orderHead) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(orderHead);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(MallOrderHead orderHead){
		DetachedCriteria criteria = DetachedCriteria.forClass(MallOrderHead.class);  
		if(orderHead != null){
			if(!Utils.isEmpty(orderHead.getMemId()))
				criteria.add(Restrictions.eq("memId", orderHead.getMemId()));
			if(!Utils.isEmpty(orderHead.getAppId()))
				criteria.add(Restrictions.eq("appId", orderHead.getAppId()));
			if(!Utils.isEmpty(orderHead.getOrderType()))
				criteria.add(Restrictions.eq("orderType", orderHead.getOrderType()));
			if(!Utils.isEmpty(orderHead.getPayType()))
				criteria.add(Restrictions.eq("payType", orderHead.getPayType()));
			if(!Utils.isEmpty(orderHead.getStatus()))
				criteria.add(Restrictions.eq("status", orderHead.getStatus()));
		}
		return criteria;
	}

}
