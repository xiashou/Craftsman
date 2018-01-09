package com.tcode.business.wechat.sys.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.dao.WechatAttentionAuthorizerDao;
import com.tcode.business.wechat.sys.model.WechatAttentionAuthorizer;
import com.tcode.common.dao.BaseDao;

@Component("wechatAttentionAuthorizerDao")
public class WechatAttentionAuthorizerDaoImpl extends BaseDao<WechatAttentionAuthorizer, Serializable> implements WechatAttentionAuthorizerDao {

	@Override
	public List<WechatAttentionAuthorizer> loadByappIdAndopenId(String authorizerAppId, String openId)
			throws Exception {
		return super.loadList("from WechatAttentionAuthorizer w where w.authorizerAppId = ? and w.openId = ?", authorizerAppId, openId);
	}
	
}
