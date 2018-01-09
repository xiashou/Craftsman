package com.tcode.business.shop.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.RechargeDao;
import com.tcode.business.shop.model.Recharge;
import com.tcode.common.dao.BaseDao;

@Component("rechargeDao")
public class RechargeDaoImpl extends BaseDao<Recharge, Serializable> implements RechargeDao {

	@Override
	public Recharge loadById(Integer id) throws Exception {
		return super.loadById(Recharge.class, id);
	}

	@Override
	public List<Recharge> loadByDept(String deptCode) throws Exception {
		return super.loadList("from Recharge r where r.deptCode = ?", deptCode);
	}

}
