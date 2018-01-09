package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallBannerDao;
import com.tcode.business.mall.model.MallBanner;
import com.tcode.common.dao.BaseDao;

@Component("mallBannerDao")
public class MallBannerDaoImpl extends BaseDao<MallBanner, Serializable> implements MallBannerDao {

	@Override
	public MallBanner loadById(Integer id) throws Exception {
		return super.loadById(MallBanner.class, id);
	}

	@Override
	public List<MallBanner> loadByAppId(String appId) throws Exception {
		return super.loadList("from MallBanner b where b.appId = ? order by b.sort", appId);
	}

}
