package com.tcode.business.wechat.act.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.ActJoinerDao;
import com.tcode.business.wechat.act.model.ActJoiner;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("actJoinerDao")
public class ActJoinerDaoImpl extends BaseDao<ActJoiner, Serializable> implements ActJoinerDao {

	@Override
	public ActJoiner loadById(Integer id) throws Exception {
		return super.loadById(ActJoiner.class, id);
	}
	
	@Override
	public ActJoiner loadByOrderNo(String orderNo) throws Exception {
		List<ActJoiner> joinerList = super.loadList("from ActJoiner j where j.orderNo = ?", orderNo);
		return Utils.isEmpty(joinerList) ? null : joinerList.get(0);
	}
	
	@Override
	public ActJoiner loadByJoin(String openId, Integer actId) throws Exception {
		List<ActJoiner> joinerList = super.loadList("from ActJoiner j where j.actId = ? and j.openId = ? and j.status = 1", actId, openId);
		return Utils.isEmpty(joinerList) ? null : joinerList.get(0);
	}

	@Override
	public List<ActJoiner> loadListPage(ActJoiner joiner, int start, int limit) throws Exception {
		List<ActJoiner> brandList = null;
		DetachedCriteria criteria = connectionCriteria(joiner);
		criteria.addOrder(Order.asc("id"));
		brandList = (List<ActJoiner>) super.loadListForPage(criteria, start, limit);
		return brandList;
	}

	@Override
	public Integer loadListCount(ActJoiner joiner) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(joiner);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(ActJoiner joiner){
		DetachedCriteria criteria = DetachedCriteria.forClass(ActJoiner.class);  
		if(joiner != null){
			if(!Utils.isEmpty(joiner.getId()))
				criteria.add(Restrictions.eq("id", joiner.getId()));
			if(!Utils.isEmpty(joiner.getActId()))
				criteria.add(Restrictions.eq("actId", joiner.getActId()));
			if(!Utils.isEmpty(joiner.getMobile()))
				criteria.add(Restrictions.like("mobile", joiner.getMobile(), MatchMode.ANYWHERE));
		}
		return criteria;
	}

}
