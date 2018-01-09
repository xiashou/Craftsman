package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallOrderItemDao;
import com.tcode.business.mall.model.MallOrderItem;
import com.tcode.common.dao.BaseDao;

@Component("mallOrderItemDao")
public class MallOrderItemDaoImpl extends BaseDao<MallOrderItem, Serializable> implements MallOrderItemDao {
	
	@Override
	public List<MallOrderItem> loadListByOrderId(String orderId) throws Exception {
		return super.loadList("select new MallOrderItem(oi.itemId,oi.orderId,oi.goodsId,oi.goodsName,oi.oprice,oi.aprice,oi.number,oi.sendMode,g.pictures) "
				+ "from MallOrderItem oi, MallGoods g where oi.goodsId = g.goodsId and oi.orderId = ?", orderId);
	}
	
	@Override
	public void removeByOrderId(String orderId) throws Exception {
		super.executeHql("delete from MallOrderItem i where i.orderId = ?", orderId);
	}

}
