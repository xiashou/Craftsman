package com.tcode.business.wechat.act.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.ActStatementDao;
import com.tcode.business.wechat.act.model.ActStatement;
import com.tcode.business.wechat.act.service.ActStatementService;

@Component("actStatementService")
public class ActStatementServiceImpl implements ActStatementService {

	private ActStatementDao actStatementDao;

	@Override
	public ActStatement getById(String appId) throws Exception {
		return actStatementDao.loadById(appId);
	}

	@Override
	public void insert(ActStatement statement) throws Exception {
		actStatementDao.save(statement);
	}

	@Override
	public void update(ActStatement statement) throws Exception {
		actStatementDao.edit(statement);
	}

	@Override
	public void delete(ActStatement statement) throws Exception {
		actStatementDao.remove(statement);
	}

	public ActStatementDao getActStatementDao() {
		return actStatementDao;
	}
	@Resource
	public void setActStatementDao(ActStatementDao actStatementDao) {
		this.actStatementDao = actStatementDao;
	}
}
