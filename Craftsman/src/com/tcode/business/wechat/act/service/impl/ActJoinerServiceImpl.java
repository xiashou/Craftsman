package com.tcode.business.wechat.act.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.ActJoinerDao;
import com.tcode.business.wechat.act.model.ActJoiner;
import com.tcode.business.wechat.act.service.ActJoinerService;

@Component("actJoinerService")
public class ActJoinerServiceImpl implements ActJoinerService {
	
	private ActJoinerDao actJoinerDao;

	@Override
	public List<ActJoiner> getAll() throws Exception {
		return actJoinerDao.loadAll();
	}

	@Override
	public ActJoiner getById(Integer id) throws Exception {
		return actJoinerDao.loadById(id);
	}
	
	@Override
	public ActJoiner getByJoin(String openId, Integer actId) throws Exception {
		return actJoinerDao.loadByJoin(openId, actId);
	}
	
	@Override
	public ActJoiner getByOrderNo(String orderNo) throws Exception {
		return actJoinerDao.loadByOrderNo(orderNo);
	}

	@Override
	public void insert(ActJoiner joiner) throws Exception {
		actJoinerDao.save(joiner);
	}

	@Override
	public void update(ActJoiner joiner) throws Exception {
		actJoinerDao.edit(joiner);
	}

	@Override
	public void delete(ActJoiner joiner) throws Exception {
		actJoinerDao.remove(joiner);
	}

	@Override
	public List<ActJoiner> getListPage(ActJoiner joiner, int start, int limit)
			throws Exception {
		return actJoinerDao.loadListPage(joiner, start, limit);
	}

	@Override
	public Integer getListCount(ActJoiner joiner) throws Exception {
		return actJoinerDao.loadListCount(joiner);
	}

	public ActJoinerDao getActJoinerDao() {
		return actJoinerDao;
	}
	@Resource
	public void setActJoinerDao(ActJoinerDao actJoinerDao) {
		this.actJoinerDao = actJoinerDao;
	}

}
