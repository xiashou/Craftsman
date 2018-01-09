package com.tcode.business.wechat.act.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.WechatActivityLotteryItemDao;
import com.tcode.business.wechat.act.model.WechatActivityLotteryItem;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryItemVo;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("wechatActivityLotteryItemDao")
public class WechatActivityLotteryItemDaoImpl extends BaseDao<WechatActivityLotteryItem, Serializable> implements WechatActivityLotteryItemDao {


	@Override
	public WechatActivityLotteryItem loadById(Integer id) throws Exception {
		return super.loadById(WechatActivityLotteryItem.class, id);
	}

	@Override
	public List<WechatActivityLotteryItem> loadListPage(WechatActivityLotteryItemVo wechatActivityLotteryItemVo, int start,
			int limit) throws Exception {
		List<WechatActivityLotteryItem> list = null;
		DetachedCriteria criteria = connectionCriteria(wechatActivityLotteryItemVo);
		criteria.addOrder(Order.desc("createTime"));
		list = (List<WechatActivityLotteryItem>) super.loadListForPage(criteria, start, limit);
		return list;
	}
	
	@Override
	public Integer loadListCount(WechatActivityLotteryItemVo wechatActivityLotteryItemVo) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(wechatActivityLotteryItemVo);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(WechatActivityLotteryItemVo wechatActivityLotteryItemVo){
		DetachedCriteria criteria = DetachedCriteria.forClass(WechatActivityLotteryItem.class);  
		if(!Utils.isEmpty(wechatActivityLotteryItemVo)){
			if(!Utils.isEmpty(wechatActivityLotteryItemVo.getId()))
				criteria.add(Restrictions.eq("id", wechatActivityLotteryItemVo.getId()));
		}
		return criteria;
	}

	@Override
	public List<WechatActivityLotteryItem> loadByActivityCode(String activityCode) throws Exception {
		return super.loadList("from WechatActivityLotteryItem w where w.activityCode = ? order by w.prizeLevel", activityCode);
	}
	
	@Override
	public List<WechatActivityLotteryItem> loadByActivityCode(String activityCode, int prizeLevel) throws Exception {
		return super.loadList("from WechatActivityLotteryItem w where w.activityCode = ? and w.prizeLevel = ? order by w.prizeLevel", activityCode,prizeLevel);
	}

}
