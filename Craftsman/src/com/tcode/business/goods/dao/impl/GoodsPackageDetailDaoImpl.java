package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsPackageDetailDao;
import com.tcode.business.goods.model.GoodsPackageDetail;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;


@Component("goodsPackageDetailDao")
public class GoodsPackageDetailDaoImpl extends BaseDao<GoodsPackageDetail, Serializable> implements GoodsPackageDetailDao {

	
	@Override
	public List<GoodsPackageDetail> loadAll(String packageId) throws Exception {
		return super.loadList("from GoodsPackageDetail g where g.gpId = ?", packageId);
	}
	
	@Override
	public List<GoodsPackageDetail> loadListPage(GoodsPackageDetail goodsPackageDetail, int start, int limit) throws Exception {
		List<GoodsPackageDetail> list = null;
		DetachedCriteria criteria = connectionCriteria(goodsPackageDetail);
		criteria.addOrder(Order.asc("itemNo"));
		list = (List<GoodsPackageDetail>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(GoodsPackageDetail goodsPackageDetail) throws Exception {
		int totalCount = 0;
		DetachedCriteria criteria = connectionCriteria(goodsPackageDetail);
		totalCount = loadListCount(criteria);
		return totalCount;
	}

	public DetachedCriteria connectionCriteria(GoodsPackageDetail goodsPackageDetail) {
		DetachedCriteria criteria = DetachedCriteria.forClass(GoodsPackageDetail.class);
		if (goodsPackageDetail != null) {
			if (!Utils.isEmpty(goodsPackageDetail.getGpId()))
				criteria.add(Restrictions.eq("gpId", goodsPackageDetail.getGpId()));
		}
		return criteria;
	}

	@Override
	public void removeById(GoodsPackageDetail goodsPackageDetail) throws Exception {
		super.executeSql("delete from god_package_detail where gp_id = ?", goodsPackageDetail.getGpId());
	}

	@Override
	public GoodsPackageDetail loadById(String id, Integer itemNo) {
		return (GoodsPackageDetail)super.loadEntity("from GoodsPackageDetail gp where gp.gpId = ? and gp.itemNo = ?", id, itemNo);
	}
}
