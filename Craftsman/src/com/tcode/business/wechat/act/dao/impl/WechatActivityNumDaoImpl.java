package com.tcode.business.wechat.act.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.WechatActivityNumDao;
import com.tcode.business.wechat.act.model.WechatActivityNum;
import com.tcode.business.wechat.act.vo.WechatActivityNumVo;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("wechatActivityNumDao")
public class WechatActivityNumDaoImpl extends BaseDao<WechatActivityNum, Serializable> implements WechatActivityNumDao {


	@Override
	public WechatActivityNum loadById(Integer id) throws Exception {
		return super.loadById(WechatActivityNum.class, id);
	}

	@Override
	public List<WechatActivityNum> loadListPage(WechatActivityNumVo wechatActivityNumVo, int start,
			int limit) throws Exception {
		List<WechatActivityNum> list = null;
		DetachedCriteria criteria = connectionCriteria(wechatActivityNumVo);
		criteria.addOrder(Order.desc("createTime"));
		list = (List<WechatActivityNum>) super.loadListForPage(criteria, start, limit);
		return list;
	}
	
	@Override
	public Integer loadListCount(WechatActivityNumVo wechatActivityNumVo) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(wechatActivityNumVo);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(WechatActivityNumVo wechatActivityNumVo){
		DetachedCriteria criteria = DetachedCriteria.forClass(WechatActivityNum.class);  
		if(!Utils.isEmpty(wechatActivityNumVo)){
			if(!Utils.isEmpty(wechatActivityNumVo.getId()))
				criteria.add(Restrictions.eq("id", wechatActivityNumVo.getId()));
			if(!Utils.isEmpty(wechatActivityNumVo.getVipNo()))
				criteria.add(Restrictions.eq("vipNo", wechatActivityNumVo.getVipNo()));
			if(!Utils.isEmpty(wechatActivityNumVo.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", wechatActivityNumVo.getDeptCode()));
		}
		return criteria;
	}

	@Override
	public List<WechatActivityNum> loadByActivityCode(String activityCode) throws Exception {
		return super.loadList("from WechatActivityNum w where w.activityCode = ?", activityCode);
	}

	@Override
	public List<WechatActivityNum> loadByActivityCode(String activityCode, String openId, String appId)
			throws Exception {
		return super.loadList("from WechatActivityNum w where w.activityCode = ? and w.openId = ? and w.appId = ?", activityCode, openId, appId);
	}
}
