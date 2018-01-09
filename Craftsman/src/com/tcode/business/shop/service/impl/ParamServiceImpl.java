package com.tcode.business.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.ParamDao;
import com.tcode.business.shop.model.Param;
import com.tcode.business.shop.service.ParamService;
import com.tcode.core.util.Utils;

@Component("paramService")
public class ParamServiceImpl implements ParamService {
	
	private ParamDao paramDao;

	@Override
	public Param getById(String id) throws Exception {
		Param param = paramDao.loadById(id);
		if(Utils.isEmpty(param))
			param = paramDao.loadById("XXXX");
		return param;
	}

	@Override
	public void insert(Param param) throws Exception {
		paramDao.save(param);
	}

	@Override
	public void update(Param param) throws Exception {
		paramDao.edit(param);
	}

	@Override
	public void delete(Param param) throws Exception {
		paramDao.remove(param);
	}

	public ParamDao getParamDao() {
		return paramDao;
	}
	@Resource
	public void setParamDao(ParamDao paramDao) {
		this.paramDao = paramDao;
	}

	
}
