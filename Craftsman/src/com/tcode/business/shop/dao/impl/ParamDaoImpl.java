package com.tcode.business.shop.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.ParamDao;
import com.tcode.business.shop.model.Param;
import com.tcode.common.dao.BaseDao;

@Component("paramDao")
public class ParamDaoImpl extends BaseDao<Param, Serializable> implements ParamDao {

	@Override
	public Param loadById(String id) throws Exception {
		return super.loadById(Param.class, id);
	}
}
