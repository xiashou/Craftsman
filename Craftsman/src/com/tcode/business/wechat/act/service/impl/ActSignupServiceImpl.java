package com.tcode.business.wechat.act.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.ActSignupDao;
import com.tcode.business.wechat.act.model.ActSignup;
import com.tcode.business.wechat.act.service.ActSignupService;

@Component("actSignupService")
public class ActSignupServiceImpl implements ActSignupService {
	
	private ActSignupDao actSignupDao;

	@Override
	public ActSignup getById(Integer id) throws Exception {
		return actSignupDao.loadById(id);
	}
	
	@Override
	public List<ActSignup> getListByAppId(String appId) throws Exception {
		return actSignupDao.loadByAppId(appId);
	}
	
	@Override
	public List<ActSignup> getListByOpenId(String openId) throws Exception {
		return actSignupDao.loadByOpenId(openId);
	}
	
	@Override
	public void updateSignNumber(Integer actId, Integer number) throws Exception {
		actSignupDao.editSignNumber(actId, number);
	}
	
	@Override
	public void updateReadNumber(Integer actId, Integer number) throws Exception {
		actSignupDao.editReadNumber(actId, number);
	}

	@Override
	public void insert(ActSignup signup) throws Exception {
		actSignupDao.save(signup);
	}

	@Override
	public void update(ActSignup signup) throws Exception {
		actSignupDao.edit(signup);
	}

	@Override
	public void delete(ActSignup signup) throws Exception {
		actSignupDao.remove(signup);
	}

	@Override
	public List<ActSignup> getListPage(ActSignup signup, int start, int limit) throws Exception {
		return actSignupDao.loadListPage(signup, start, limit);
	}

	@Override
	public Integer getListCount(ActSignup signup) throws Exception {
		return actSignupDao.loadListCount(signup);
	}

	public ActSignupDao getActSignupDao() {
		return actSignupDao;
	}
	@Resource
	public void setActSignupDao(ActSignupDao actSignupDao) {
		this.actSignupDao = actSignupDao;
	}

}
