package com.tcode.business.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallBannerDao;
import com.tcode.business.mall.model.MallBanner;
import com.tcode.business.mall.service.MallBannerService;

@Component("mallBannerService")
public class MallBannerServiceImpl implements MallBannerService {
	
	private MallBannerDao mallBannerDao;


	@Override
	public MallBanner getById(Integer id) throws Exception {
		return mallBannerDao.loadById(id);
	}
	@Override
	public List<MallBanner> getListByAppId(String appid) throws Exception {
		return mallBannerDao.loadByAppId(appid);
	}
	@Override
	public void insert(MallBanner mallBrand) throws Exception {
		mallBannerDao.save(mallBrand);
	}
	@Override
	public void update(MallBanner mallBrand) throws Exception {
		mallBannerDao.edit(mallBrand);
	}
	@Override
	public void delete(MallBanner mallBrand) throws Exception {
		mallBannerDao.remove(mallBrand);
	}
	
	public MallBannerDao getMallBannerDao() {
		return mallBannerDao;
	}
	@Resource
	public void setMallBannerDao(MallBannerDao mallBannerDao) {
		this.mallBannerDao = mallBannerDao;
	}

}
