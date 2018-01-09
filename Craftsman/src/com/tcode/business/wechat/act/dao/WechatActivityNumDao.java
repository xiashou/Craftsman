package com.tcode.business.wechat.act.dao;

import java.util.List;

import com.tcode.business.wechat.act.model.WechatActivityNum;
import com.tcode.business.wechat.act.vo.WechatActivityNumVo;

public interface WechatActivityNumDao {
	
	/**
	 * 添加
	 * @param wechatActivityNum
	 * @throws Exception
	 */
	public void save(WechatActivityNum wechatActivityNum) throws Exception;
	
	/**
	 * 修改
	 * @param wechatActivityNum
	 * @throws Exception
	 */
	public void edit(WechatActivityNum wechatActivityNum) throws Exception;
	
	/**
	 * 删除
	 * @param wechatActivityNum
	 * @throws Exception
	 */
	public void remove(WechatActivityNum wechatActivityNum) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return wechatActivityNum
	 * @throws Exception
	 */
	public WechatActivityNum loadById(Integer id) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityNum> loadAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatActivityNumVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityNum> loadListPage(WechatActivityNumVo wechatActivityNumVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatActivityNumVo
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(WechatActivityNumVo wechatActivityNumVo) throws Exception;
	
	/**
	 * 根据活动编码查询
	 * @param activityCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityNum> loadByActivityCode(String activityCode) throws Exception;
	
	/**
	 * 根据appId、openId、活动编码查询
	 * @param activityCode
	 * @param openId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityNum> loadByActivityCode(String activityCode, String openId, String appId) throws Exception;


}
