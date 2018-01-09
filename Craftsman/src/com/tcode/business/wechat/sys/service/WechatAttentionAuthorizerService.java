package com.tcode.business.wechat.sys.service;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatAttentionAuthorizer;

public interface WechatAttentionAuthorizerService {
	
	/**
	 * 添加
	 * @param wechatAttentionAuthorizer
	 * @throws Exception
	 */
	public void insert(WechatAttentionAuthorizer wechatAttentionAuthorizer) throws Exception;
	
	/**
	 * 修改
	 * @param wechatAttentionAuthorizer
	 * @throws Exception
	 */
	public void update(WechatAttentionAuthorizer wechatAttentionAuthorizer) throws Exception;
	
	/**
	 * 删除
	 * @param wechatAttentionAuthorizer
	 * @throws Exception
	 */
	public void delete(WechatAttentionAuthorizer wechatAttentionAuthorizer) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatAttentionAuthorizer> getAll() throws Exception;
	
	/**
	 * 根据授权方appId和用户openId获取关注信息
	 * @param authorizerAppId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public List<WechatAttentionAuthorizer> getByappIdAndopenId(String authorizerAppId, String openId) throws Exception;

}
