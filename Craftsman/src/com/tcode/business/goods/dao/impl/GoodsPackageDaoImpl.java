package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsPackageDao;
import com.tcode.business.goods.model.GoodsPackage;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("goodsPackageDao")
public class GoodsPackageDaoImpl extends BaseDao<GoodsPackage, Serializable> implements GoodsPackageDao {

	@Override
	public List<GoodsPackage> loadAll(String deptCode, String companyId) throws Exception {
		return super.loadList("from GoodsPackage g where g.deptCode = ? or (g.deptCode in(select d.deptCode from SysDept d where d.companyId = ?) and g.range = 2)", deptCode, companyId);
	}

	@Override
	public GoodsPackage loadById(String id) throws Exception {
		return super.loadById(GoodsPackage.class, id);
	}

	@Override
	public List<GoodsPackage> loadListPage(GoodsPackage goodsPackage, int start, int limit) throws Exception {
		List<GoodsPackage> list = null;
		DetachedCriteria criteria = connectionCriteria(goodsPackage);
		criteria.addOrder(Order.asc("id"));
		list = (List<GoodsPackage>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(GoodsPackage goodsPackage) throws Exception {
		int totalCount = 0;
		DetachedCriteria criteria = connectionCriteria(goodsPackage);
		totalCount = loadListCount(criteria);
		return totalCount;
	}

	public DetachedCriteria connectionCriteria(GoodsPackage goodsPackage) {
		DetachedCriteria criteria = DetachedCriteria.forClass(GoodsPackage.class);
		if (goodsPackage != null) {
			if (!Utils.isEmpty(goodsPackage.getId()))
				criteria.add(Restrictions.eq("id", goodsPackage.getId()));
			if (!Utils.isEmpty(goodsPackage.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", goodsPackage.getDeptCode()));
			if (!Utils.isEmpty(goodsPackage.getName()))
				criteria.add(Restrictions.eq("name", goodsPackage.getName()));
		}
		return criteria;
	}
}
