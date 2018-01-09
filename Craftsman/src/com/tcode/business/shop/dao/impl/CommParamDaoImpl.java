package com.tcode.business.shop.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.CommParamDao;
import com.tcode.business.shop.model.CommParam;
import com.tcode.common.dao.BaseDao;

@Component("commParamDao")
public class CommParamDaoImpl extends BaseDao<CommParam, Serializable> implements CommParamDao {

	@Override
	public CommParam loadById(String id) throws Exception {
		return super.loadById(CommParam.class, id);
	}
}
