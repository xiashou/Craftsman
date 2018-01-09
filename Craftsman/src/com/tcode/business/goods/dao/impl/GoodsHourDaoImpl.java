package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsHourDao;
import com.tcode.business.goods.model.GoodsHour;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("goodsHourDao")
public class GoodsHourDaoImpl extends BaseDao<GoodsHour, Serializable> implements GoodsHourDao {

	@Override
	public List<GoodsHour> loadAll(String deptCode) throws Exception {
		return super.loadList("from GoodsHour g where g.deptCode = ?", deptCode);
	}
	
	@Override
	public GoodsHour loadById(String id) throws Exception {
		return super.loadById(GoodsHour.class, id);
	}

	@Override
	public List<GoodsHour> loadByType(Integer typeId) throws Exception {
		return super.loadList("from GoodsHour g where g.typeId = ?", typeId);
	}

	@Override
	public List<GoodsHour> loadListPage(GoodsHour goodsHour, int start, int limit) throws Exception {
		List<GoodsHour> list = null;
		DetachedCriteria criteria = connectionCriteria(goodsHour);
		criteria.addOrder(Order.asc("id"));
		list = (List<GoodsHour>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(GoodsHour goodsHour) throws Exception {
		int totalCount = 0;
		DetachedCriteria criteria = connectionCriteria(goodsHour);
		totalCount = loadListCount(criteria);
		return totalCount;
	}

	public DetachedCriteria connectionCriteria(GoodsHour goodsHour) {
		DetachedCriteria criteria = DetachedCriteria.forClass(GoodsHour.class);
		if (goodsHour != null) {
			if (!Utils.isEmpty(goodsHour.getId()))
				criteria.add(Restrictions.eq("id", goodsHour.getId()));
			if (!Utils.isEmpty(goodsHour.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", goodsHour.getDeptCode()));
			if (goodsHour.getTypeId() > 0)
				criteria.add(Restrictions.eq("typeId", goodsHour.getTypeId()));
			if (!Utils.isEmpty(goodsHour.getName()))
				criteria.add(Restrictions.eq("name", goodsHour.getName()));
		}
		return criteria;
	}

	@Override
	public void removeByType(GoodsHour goodHour) throws Exception {
		super.executeSql("delete from god_hour where type_id = ?", goodHour.getTypeId());
	}

	@Override
	public List<GoodsHour> loadByKeyword(String deptCode, String keyword) throws Exception {
		return super.loadList("from GoodsHour g where g.deptCode = ? and (g.name like ? or g.shorthand like ?) ", 
				deptCode, '%' + keyword + '%', '%' + keyword + '%');
	}

}
