package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsInStockDao;
import com.tcode.business.goods.model.GoodsInStock;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("goodsInStockDao")
public class GoodsInStockDaoImpl extends BaseDao<GoodsInStock, Serializable> implements GoodsInStockDao {

	@Override
	public GoodsInStock loadById(Integer id) throws Exception {
		return super.loadById(GoodsInStock.class, id);
	}

	@Override
	public List<GoodsInStock> loadByDept(String deptCode) throws Exception {
		return super.loadList("from GoodsInStock i where i.deptCode = ?", deptCode);
	}
	
	@Override
	public List<GoodsInStock> loadByInNumber(String inNumber) throws Exception {
		return super.loadList("from GoodsInStock i where i.inNumber = ?", inNumber);
	}

	@Override
	public List<GoodsInStock> loadByGoodsId(String deptCode, String goodsId) throws Exception {
		return super.loadList("from GoodsInStock i where i.deptCode = ? and i.goodsId = ? order by createTime desc", deptCode, goodsId);
	}
	
	@Override
	public List<GoodsInStock> loadListPage(GoodsInStock inStock, int start, int limit) throws Exception {
		List<GoodsInStock> list = null;
		DetachedCriteria criteria = connectionCriteria(inStock);
		criteria.addOrder(Order.desc("id"));
		list = (List<GoodsInStock>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(GoodsInStock goodsMaterial) throws Exception {
		int totalCount = 0;
		DetachedCriteria criteria = connectionCriteria(goodsMaterial);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(GoodsInStock inStock) {
		DetachedCriteria criteria = DetachedCriteria.forClass(GoodsInStock.class);
		if (inStock != null) {
			if (!Utils.isEmpty(inStock.getId()))
				criteria.add(Restrictions.eq("id", inStock.getId()));
			if (!Utils.isEmpty(inStock.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", inStock.getDeptCode()));
			if(!Utils.isEmpty(inStock.getGoodsId()))
				criteria.add(Restrictions.like("goodsId", inStock.getGoodsId(), MatchMode.ANYWHERE));
			if(!Utils.isEmpty(inStock.getGoodsName()))
				criteria.add(Restrictions.or(Restrictions.like("goodsName", inStock.getGoodsName(), MatchMode.ANYWHERE), 
						Restrictions.sqlRestriction("this_.goods_id in (select id from god_material where code like '%"+inStock.getGoodsName()+"%')")));
			if(!Utils.isEmpty(inStock.getInNumber()))
				criteria.add(Restrictions.like("inNumber", inStock.getInNumber(), MatchMode.ANYWHERE));
			if(!Utils.isEmpty(inStock.getNumber()))
				if(inStock.getNumber() > 0)
					criteria.add(Restrictions.gt("number", 0.0));
				else
					criteria.add(Restrictions.le("number", 0.0));
			if(!Utils.isEmpty(inStock.getSupplier()))
				criteria.add(Restrictions.eq("supplier", inStock.getSupplier()));
			if(!Utils.isEmpty(inStock.getSettlement()))
				criteria.add(Restrictions.eq("settlement", inStock.getSettlement()));
			if(!Utils.isEmpty(inStock.getStartDate()))
				criteria.add(Restrictions.ge("createTime", inStock.getStartDate()));
			if(!Utils.isEmpty(inStock.getEndDate()))
				criteria.add(Restrictions.le("createTime", inStock.getEndDate()));
		}
		return criteria;
	}

}
