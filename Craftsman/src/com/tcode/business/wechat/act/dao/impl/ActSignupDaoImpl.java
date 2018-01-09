package com.tcode.business.wechat.act.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.ActSignupDao;
import com.tcode.business.wechat.act.model.ActSignup;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("actSignupDao")
public class ActSignupDaoImpl extends BaseDao<ActSignup, Serializable> implements ActSignupDao {

	@Override
	public ActSignup loadById(Integer id) throws Exception {
		return super.loadById(ActSignup.class, id);
	}
	
	@Override
	public List<ActSignup> loadByAppId(String appId) throws Exception {
		return super.loadList("from ActSignup s where s.appId = ? and s.status = 1", appId);
	}
	
	@Override
	public List<ActSignup> loadByOpenId(String openId) throws Exception {
		return super.loadList("from ActSignup s where s.status = 1 and s.id in (select j.actId from ActJoiner j where j.openId = ? and j.status = 1)", openId);
	}
	
	@Override
	public void editSignNumber(Integer actId, Integer number) throws Exception {
		super.executeHql("update ActSignup s set s.signNumber = s.signNumber + ? , s.signFicNumber = s.signFicNumber + ? where s.id = ?", number, number, actId);
	}
	
	@Override
	public void editReadNumber(Integer actId, Integer number) throws Exception {
		super.executeHql("update ActSignup s set s.readNumber = s.readNumber + ? , s.readFicNumber = s.readFicNumber + ? where s.id = ?", number, number, actId);
	}

	@Override
	public List<ActSignup> loadListPage(ActSignup signup, int start, int limit) throws Exception {
		List<ActSignup> goodsList = null;
		DetachedCriteria criteria = connectionCriteria(signup);
		criteria.addOrder(Order.asc("id"));
		goodsList = (List<ActSignup>) super.loadListForPage(criteria, start, limit);
		return goodsList;
	}

	@Override
	public Integer loadListCount(ActSignup signup) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(signup);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(ActSignup signup){
		DetachedCriteria criteria = DetachedCriteria.forClass(ActSignup.class);  
		if(signup != null){
			if(!Utils.isEmpty(signup.getAppId()))
				criteria.add(Restrictions.eq("appId", signup.getAppId()));
			if(!Utils.isEmpty(signup.getId()))
				criteria.add(Restrictions.eq("id", signup.getId()));
			if(!Utils.isEmpty(signup.getStatus()))
				criteria.add(Restrictions.eq("status", signup.getStatus()));
		}
		return criteria;
	}

}
