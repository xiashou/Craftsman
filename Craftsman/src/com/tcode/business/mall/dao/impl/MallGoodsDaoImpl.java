package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallGoodsDao;
import com.tcode.business.mall.model.MallGoods;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("mallGoodsDao")
public class MallGoodsDaoImpl extends BaseDao<MallGoods, Serializable> implements MallGoodsDao {

	@Override
	public MallGoods loadById(String goodsId) throws Exception {
		return super.loadById(MallGoods.class, goodsId);
	}
	
	@Override
	public List<MallGoods> loadAllListOrderType(String appId) throws Exception {
		return super.loadList("select g from MallGoods g, MallGoodsType t where g.typeId = t.id and t.appId = ? and g.status = 1 order by t.sortNo", appId);
	}
	
	@Override
	public void deleteByType(Integer typeId) throws Exception {
		super.executeHql("delete from MallGoods g where g.typeId = ?", typeId);
	}

	@Override
	public List<MallGoods> loadListPage(MallGoods goods, int start, int limit) throws Exception {
		List<MallGoods> goodsList = null;
		DetachedCriteria criteria = connectionCriteria(goods);
		criteria.addOrder(Order.asc("id"));
		goodsList = (List<MallGoods>) super.loadListForPage(criteria, start, limit);
		return goodsList;
	}

	@Override
	public Integer loadListCount(MallGoods goods) throws Exception {
		int totalCount = 0; 
		DetachedCriteria criteria = connectionCriteria(goods);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(MallGoods goods){
		DetachedCriteria criteria = DetachedCriteria.forClass(MallGoods.class);  
		if(goods != null){
			if(!Utils.isEmpty(goods.getGoodsId()))
				criteria.add(Restrictions.eq("goodsId", goods.getGoodsId()));
			if(!Utils.isEmpty(goods.getAppId()))
				criteria.add(Restrictions.eq("appId", goods.getAppId()));
			if(!Utils.isEmpty(goods.getTypeId()))
				criteria.add(Restrictions.eq("typeId", goods.getTypeId()));
			if(!Utils.isEmpty(goods.getIsHot()))
				criteria.add(Restrictions.eq("isHot", goods.getIsHot()));
			if(!Utils.isEmpty(goods.getStatus()))
				criteria.add(Restrictions.eq("status", goods.getStatus()));
		}
		return criteria;
	}

	@Override
	public List<MallGoods> loadListByTyre(String appId, Integer modelId) throws Exception {
		return super.loadList("from MallGoods g where g.goodsId in (select t.goodsId from MallTyre t where t.appId = ? and t.modelId = ?) and g.status = 1", appId, modelId);
	}

	@Override
	public List<MallGoods> loadListByMaintain(String appId, Integer modelId) throws Exception {
		return super.loadList("from MallGoods g where g.goodsId in (select t.goodsId from MallMaintain t where t.appId = ? and t.modelId = ?) and g.status = 1", appId, modelId);
	}

}
