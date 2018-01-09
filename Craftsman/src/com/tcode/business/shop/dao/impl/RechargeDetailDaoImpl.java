package com.tcode.business.shop.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.RechargeDetailDao;
import com.tcode.business.shop.model.RechargeDetail;
import com.tcode.common.dao.BaseDao;

@Component("rechargeDetailDao")
public class RechargeDetailDaoImpl extends BaseDao<RechargeDetail, Serializable> implements RechargeDetailDao {

	@Override
	public List<RechargeDetail> loadByRechargeId(Integer rechargeId) throws Exception {
		return super.loadList("from RechargeDetail rd where rd.rechargeId = ?", rechargeId);
	}
	
	@Override
	public void removeByRechargeId(Integer rechargeId) throws Exception {
		super.executeHql("delete from RechargeDetail rd where rd.rechargeId = ?", rechargeId);
	}
}
