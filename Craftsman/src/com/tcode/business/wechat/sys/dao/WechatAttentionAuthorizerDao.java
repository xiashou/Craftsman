package com.tcode.business.wechat.sys.dao;

import java.util.List;

import com.tcode.business.wechat.sys.model.WechatAttentionAuthorizer;

public interface WechatAttentionAuthorizerDao {
	
	/**
	 * 添加
	 * @param wechatAttentionAuthorizer
	 * @throws Exception
	 */
	public void save(WechatAttentionAuthorizer wechatAttentionAuthorizer) throws Exception;
	
	/**
	 * 修改
	 * @param wechatAttentionAuthorizer
	 * @throws Exception
	 */
	public void edit(WechatAttentionAuthorizer wechatAttentionAuthorizer) throws Exception;
	
	/**
	 * 删除
	 * @param wechatAttentionAuthorizer
	 * @throws Exception
	 */
	public void remove(WechatAttentionAuthorizer wechatAttentionAuthorizer) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatAttentionAuthorizer> loadAll() throws Exception;
	
	/**
	 * 根据授权方appId和用户openId获取关注信息
	 * @param authorizerAppId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public List<WechatAttentionAuthorizer> loadByappIdAndopenId(String authorizerAppId, String openId) throws Exception;

}
