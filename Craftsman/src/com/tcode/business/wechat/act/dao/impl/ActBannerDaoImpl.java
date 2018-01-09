package com.tcode.business.wechat.act.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.ActBannerDao;
import com.tcode.business.wechat.act.model.ActBanner;
import com.tcode.common.dao.BaseDao;

@Component("actBannerDao")
public class ActBannerDaoImpl extends BaseDao<ActBanner, Serializable> implements ActBannerDao {

	@Override
	public ActBanner loadById(Integer id) throws Exception {
		return super.loadById(ActBanner.class, id);
	}

	@Override
	public List<ActBanner> loadByAppId(String appId) throws Exception {
		return super.loadList("from ActBanner b where b.appId = ? order by b.sort", appId);
	}

}
