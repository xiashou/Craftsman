package com.tcode.business.report.dao.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.report.dao.ReptRechargeDao;
import com.tcode.business.report.model.ReptRecharge;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("reptRechargeDao")
public class ReptRechargeDaoImpl extends BaseDao<ReptRecharge, Serializable> implements ReptRechargeDao {

	@Override
	public ReptRecharge loadById(Integer id) throws Exception {
		return super.loadById(ReptRecharge.class, id);
	}

	@Override
	public List<ReptRecharge> loadByDept(String deptCode) throws Exception {
		return super.loadList("from ReptRecharge rr where rr.deptCode = ?", deptCode);
	}

	@Override
	public List<ReptRecharge> loadByMemId(Integer memId) throws Exception {
		return super.loadList("from ReptRecharge rr where rr.memId = ?", memId);
	}
	
	@Override
	public List<ReptRecharge> loadByOrder(String orderId, String goodsId) throws Exception {
		return super.loadList("from ReptRecharge rr where rr.orderNo = ? and goodsId = ?", orderId, goodsId);
	}
	
	@Override
	public void removeByOrder(String orderNo) throws Exception {
		super.executeHql("delete from ReptRecharge rr where rr.orderNo = ?", orderNo);
	}

	@Override
	public void addRecord(String deptCode, Integer memId, String orderNo, String goodsId, String goodsName, Double number) throws Exception {
		ReptRecharge recharge = new ReptRecharge(deptCode, memId, orderNo, goodsId, goodsName, number);
		recharge.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		super.save(recharge);
	}

	@Override
	public List<ReptRecharge> loadListPage(ReptRecharge recharge, int start, int limit) throws Exception {
		List<ReptRecharge> list = null;
		DetachedCriteria criteria = connectionCriteria(recharge);
		criteria.addOrder(Order.desc("id"));
		list = (List<ReptRecharge>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(ReptRecharge recharge) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(recharge);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(ReptRecharge recharge){
		DetachedCriteria criteria = DetachedCriteria.forClass(ReptRecharge.class);  
		if(!Utils.isEmpty(recharge)){
			if(!Utils.isEmpty(recharge.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", recharge.getDeptCode()));
		}
		return criteria;
	}

}
