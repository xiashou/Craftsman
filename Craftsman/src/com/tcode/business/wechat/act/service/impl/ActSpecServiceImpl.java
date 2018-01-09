package com.tcode.business.wechat.act.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.ActSpecDao;
import com.tcode.business.wechat.act.model.ActSpec;
import com.tcode.business.wechat.act.service.ActSpecService;

@Component("actSpecService")
public class ActSpecServiceImpl implements ActSpecService {

	private ActSpecDao actSpecDao;

	@Override
	public ActSpec getById(Integer id) throws Exception {
		return actSpecDao.loadById(id);
	}

	@Override
	public List<ActSpec> getListByActId(Integer actId) throws Exception {
		return actSpecDao.loadByActId(actId);
	}

	@Override
	public void insert(ActSpec spec) throws Exception {
		actSpecDao.save(spec);
	}

	@Override
	public void update(ActSpec spec) throws Exception {
		actSpecDao.edit(spec);
	}

	@Override
	public void delete(ActSpec spec) throws Exception {
		actSpecDao.remove(spec);
	}

	public ActSpecDao getActSpecDao() {
		return actSpecDao;
	}
	@Resource
	public void setActSpecDao(ActSpecDao actSpecDao) {
		this.actSpecDao = actSpecDao;
	}
}
