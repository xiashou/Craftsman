package com.tcode.business.wechat.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatAttentionAuthorizerDao;
import com.tcode.business.wechat.sys.model.WechatAttentionAuthorizer;
import com.tcode.business.wechat.sys.service.WechatAttentionAuthorizerService;
import com.tcode.core.util.Utils;

@Component("wechatAttentionAuthorizerService")
public class WechatAttentionAuthorizerServiceImpl implements WechatAttentionAuthorizerService {
	
	private  WechatAttentionAuthorizerDao wechatAttentionAuthorizerDao;

	@Override
	public void insert(WechatAttentionAuthorizer wechatAttentionAuthorizer) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatAttentionAuthorizer.setCreateTime(currentTime);
		wechatAttentionAuthorizer.setUpdateTime(currentTime);
		wechatAttentionAuthorizerDao.save(wechatAttentionAuthorizer);
	}

	@Override
	public void update(WechatAttentionAuthorizer wechatAttentionAuthorizer) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatAttentionAuthorizer.setUpdateTime(currentTime);
		wechatAttentionAuthorizerDao.edit(wechatAttentionAuthorizer);
	}

	@Override
	public void delete(WechatAttentionAuthorizer wechatAttentionAuthorizer) throws Exception {
		wechatAttentionAuthorizerDao.remove(wechatAttentionAuthorizer);
	}

	@Override
	public List<WechatAttentionAuthorizer> getAll() throws Exception {
		return wechatAttentionAuthorizerDao.loadAll();
	}
	
	@Override
	public List<WechatAttentionAuthorizer> getByappIdAndopenId(String authorizerAppId, String openId) throws Exception {
		return wechatAttentionAuthorizerDao.loadByappIdAndopenId(authorizerAppId, openId);
	}

	
	public WechatAttentionAuthorizerDao getWechatAttentionAuthorizerDao() {
		return wechatAttentionAuthorizerDao;
	}

	@Resource
	public void setWechatAttentionAuthorizerDao(WechatAttentionAuthorizerDao wechatAttentionAuthorizerDao) {
		this.wechatAttentionAuthorizerDao = wechatAttentionAuthorizerDao;
	}

}
