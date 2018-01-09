package com.tcode.business.shop.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.shop.dao.CommParamDao;
import com.tcode.business.shop.model.CommParam;
import com.tcode.business.shop.service.CommParamService;
import com.tcode.core.util.Utils;

@Component("commParamService")
public class CommParamServiceImpl implements CommParamService {
	
	private CommParamDao commParamDao;

	@Override
	public CommParam getById(String id) throws Exception {
		CommParam param = commParamDao.loadById(id);
		if(Utils.isEmpty(param))
			param = commParamDao.loadById("XXXX");
		return param;
	}

	@Override
	public void insert(CommParam param) throws Exception {
		commParamDao.save(param);
	}

	@Override
	public void update(CommParam param) throws Exception {
		commParamDao.edit(param);
	}

	@Override
	public void delete(CommParam param) throws Exception {
		commParamDao.remove(param);
	}

	public CommParamDao getCommParamDao() {
		return commParamDao;
	}
	@Resource
	public void setCommParamDao(CommParamDao commParamDao) {
		this.commParamDao = commParamDao;
	}

	

	
}
