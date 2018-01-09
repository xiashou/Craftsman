package com.tcode.business.wechat.act.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.WechatActivityLotteryDao;
import com.tcode.business.wechat.act.model.WechatActivityLottery;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryVo;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("wechatActivityLotteryDao")
public class WechatActivityLotteryDaoImpl extends BaseDao<WechatActivityLottery, Serializable> implements WechatActivityLotteryDao {


	@Override
	public WechatActivityLottery loadById(Integer id) throws Exception {
		return super.loadById(WechatActivityLottery.class, id);
	}

	@Override
	public List<WechatActivityLottery> loadListPage(WechatActivityLotteryVo wechatActivityLotteryVo, int start,
			int limit) throws Exception {
		List<WechatActivityLottery> list = null;
		DetachedCriteria criteria = connectionCriteria(wechatActivityLotteryVo);
		criteria.addOrder(Order.desc("createTime"));
		list = (List<WechatActivityLottery>) super.loadListForPage(criteria, start, limit);
		return list;
	}
	
	@Override
	public Integer loadListCount(WechatActivityLotteryVo wechatActivityLotteryVo) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(wechatActivityLotteryVo);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(WechatActivityLotteryVo wechatActivityLotteryVo){
		DetachedCriteria criteria = DetachedCriteria.forClass(WechatActivityLottery.class);  
		if(!Utils.isEmpty(wechatActivityLotteryVo)){
			if(!Utils.isEmpty(wechatActivityLotteryVo.getId()))
				criteria.add(Restrictions.eq("id", wechatActivityLotteryVo.getId()));
			if(!Utils.isEmpty(wechatActivityLotteryVo.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", wechatActivityLotteryVo.getDeptCode()));
		}
		return criteria;
	}

	@Override
	public List<WechatActivityLottery> loadByActivityCode(String activityCode) throws Exception {
		return super.loadList("from WechatActivityLottery w where w.activityCode = ?", activityCode);
	}

	@Override
	public List<WechatActivityLottery> loadByCompanyId(String companyId) throws Exception {
		return super.loadList("from WechatActivityLottery w where w.companyId = ? "
				+ "and w.activityStatus = 1 and convert(datetime,w.bDate,111) <= getdate() "
				+ "and convert(datetime,w.eDate,111) >= getdate()", companyId);
//		return super.loadList("from WechatActivityLottery w where w.companyId = ? "
//				+ "and w.activityStatus = 1 and convert(datetime,w.bDate,111) <= getdate() "
//				+ "and convert(datetime,w.eDate,111) >= getdate()", companyId);
	}
}
