package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallCartDao;
import com.tcode.business.mall.model.MallCart;
import com.tcode.common.dao.BaseDao;

@Component("mallCartDao")
public class MallCartDaoImpl extends BaseDao<MallCart, Serializable> implements MallCartDao {

	@Override
	public MallCart loadById(String goodsId, Integer memId) throws Exception {
		return (MallCart) super.loadUniqueResult("from MallCart c where c.goodsId = '" + goodsId + "' and c.memId = " + memId);
	}

	@Override
	public List<MallCart> loadByMemId(Integer memId) throws Exception {
		return super.loadList("from MallCart c where c.memId = ?", memId);
	}

}
