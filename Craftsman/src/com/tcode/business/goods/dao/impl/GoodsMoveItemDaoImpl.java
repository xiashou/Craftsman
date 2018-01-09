package com.tcode.business.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsMoveItemDao;
import com.tcode.business.goods.model.GoodsMoveItem;
import com.tcode.common.dao.BaseDao;

@Component("goodsMoveItemDao")
public class GoodsMoveItemDaoImpl extends BaseDao<GoodsMoveItem, Serializable> implements GoodsMoveItemDao {

	@Override
	public GoodsMoveItem loadById(Integer moveId, Integer itemNo) throws Exception {
		return (GoodsMoveItem) super.loadUniqueResult("from GoodsMoveItem i where i.moveId = " + moveId + " and itemNo = " + itemNo);
	}

	@Override
	public List<GoodsMoveItem> loadByMoveId(Integer moveId) throws Exception {
		return super.loadList("from GoodsMoveItem i where i.moveId = ?", moveId);
	}


}
