package com.tcode.business.wechat.act.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.ActBannerDao;
import com.tcode.business.wechat.act.model.ActBanner;
import com.tcode.business.wechat.act.service.ActBannerService;

@Component("actBannerService")
public class ActBannerServiceImpl implements ActBannerService {
	
	private ActBannerDao actBannerDao;


	@Override
	public ActBanner getById(Integer id) throws Exception {
		return actBannerDao.loadById(id);
	}
	@Override
	public List<ActBanner> getListByAppId(String appid) throws Exception {
		return actBannerDao.loadByAppId(appid);
	}
	@Override
	public void insert(ActBanner banner) throws Exception {
		actBannerDao.save(banner);
	}
	@Override
	public void update(ActBanner banner) throws Exception {
		actBannerDao.edit(banner);
	}
	@Override
	public void delete(ActBanner banner) throws Exception {
		actBannerDao.remove(banner);
	}
	
	
	public ActBannerDao getActBannerDao() {
		return actBannerDao;
	}
	@Resource
	public void setActBannerDao(ActBannerDao actBannerDao) {
		this.actBannerDao = actBannerDao;
	}
	
	
}
