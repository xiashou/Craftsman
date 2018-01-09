package com.tcode.business.wechat.act.service;

import java.util.List;

import com.tcode.business.wechat.act.model.WechatActivityNum;
import com.tcode.business.wechat.act.vo.WechatActivityNumVo;

public interface WechatActivityNumService {

	/**
	 * 添加
	 * @param wechatActivityNum
	 * @throws Exception
	 */
	public void insert(WechatActivityNum wechatActivityNum) throws Exception;
	
	/**
	 * 修改
	 * @param wechatActivityNum
	 * @throws Exception
	 */
	public void update(WechatActivityNum wechatActivityNum) throws Exception;
	
	/**
	 * 删除
	 * @param wechatActivityNum
	 * @throws Exception
	 */
	public void delete(WechatActivityNum wechatActivityNum) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return wechatActivityNum
	 * @throws Exception
	 */
	public WechatActivityNum getById(Integer id) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityNum> getAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatActivityNumVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityNum> getListPage(WechatActivityNumVo wechatActivityNumVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatActivityNumVo
	 * @return
	 * @throws Exception
	 */
	public Integer getListCount(WechatActivityNumVo wechatActivityNumVo) throws Exception;
	
	/**
	 * 根据活动编码查询
	 * @param activityCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityNum> getByActivityCode(String activityCode) throws Exception;
	
	/**
	 * 根据appId、openId、活动编码查询
	 * @param activityCode
	 * @param openId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityNum> getByActivityCode(String activityCode, String openId, String appId) throws Exception;

	
}
