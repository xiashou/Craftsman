package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsCouponsDao;
import com.tcode.business.goods.model.GoodsCoupons;
import com.tcode.common.dao.BaseDao;

@Component("goodsCouponsDao")
public class GoodsCouponsDaoImpl extends BaseDao<GoodsCoupons, Serializable> implements GoodsCouponsDao {

	@Override
	public GoodsCoupons loadById(String id) throws Exception {
		return super.loadById(GoodsCoupons.class, id);
	}

	@Override
	public List<GoodsCoupons> loadByDept(String deptCode) throws Exception {
		return super.loadList("from GoodsCoupons c where c.deptCode = ?", deptCode);
	}

}
