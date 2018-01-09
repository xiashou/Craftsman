package com.tcode.business.wechat.act.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.WechatActivityLotteryPartDao;
import com.tcode.business.wechat.act.model.WechatActivityLotteryPart;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryPartVo;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("wechatActivityLotteryPartDao")
public class WechatActivityLotteryPartDaoImpl extends BaseDao<WechatActivityLotteryPart, Serializable> implements WechatActivityLotteryPartDao {


	@Override
	public WechatActivityLotteryPart loadById(Integer id) throws Exception {
		return super.loadById(WechatActivityLotteryPart.class, id);
	}

	@Override
	public List<WechatActivityLotteryPart> loadListPage(WechatActivityLotteryPartVo wechatActivityLotteryPartVo, int start,
			int limit) throws Exception {
		List<WechatActivityLotteryPart> list = null;
		DetachedCriteria criteria = connectionCriteria(wechatActivityLotteryPartVo);
		criteria.addOrder(Order.desc("createTime"));
		list = (List<WechatActivityLotteryPart>) super.loadListForPage(criteria, start, limit);
		return list;
	}
	
	@Override
	public Integer loadListCount(WechatActivityLotteryPartVo wechatActivityLotteryPartVo) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(wechatActivityLotteryPartVo);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(WechatActivityLotteryPartVo wechatActivityLotteryPartVo){
		DetachedCriteria criteria = DetachedCriteria.forClass(WechatActivityLotteryPart.class);  
		if(!Utils.isEmpty(wechatActivityLotteryPartVo)){
			if(!Utils.isEmpty(wechatActivityLotteryPartVo.getId()))
				criteria.add(Restrictions.eq("id", wechatActivityLotteryPartVo.getId()));
		}
		return criteria;
	}

	@Override
	public List<WechatActivityLotteryPart> loadByActivityCode(String activityCode) throws Exception {
		return super.loadList("from WechatActivityLotteryPart w where w.activityCode = ?", activityCode);
	}

	@Override
	public List<WechatActivityLotteryPart> loadByActivityCode(String activityCode, String openId, String appId)
			throws Exception {
		return super.loadList("from WechatActivityLotteryPart w where w.activityCode = ? and w.openId = ? and w.appId = ? order by w.lotteryTime DESC", activityCode, openId, appId);
	}
}
