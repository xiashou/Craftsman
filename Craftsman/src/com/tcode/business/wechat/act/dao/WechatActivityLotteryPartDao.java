package com.tcode.business.wechat.act.dao;

import java.util.List;

import com.tcode.business.wechat.act.model.WechatActivityLotteryPart;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryPartVo;


public interface WechatActivityLotteryPartDao {
	
	/**
	 * 添加
	 * @param wechatActivityLotteryPart
	 * @throws Exception
	 */
	public void save(WechatActivityLotteryPart wechatActivityLotteryPart) throws Exception;
	
	/**
	 * 修改
	 * @param wechatActivityLotteryPart
	 * @throws Exception
	 */
	public void edit(WechatActivityLotteryPart wechatActivityLotteryPart) throws Exception;
	
	/**
	 * 删除
	 * @param wechatActivityLotteryPart
	 * @throws Exception
	 */
	public void remove(WechatActivityLotteryPart wechatActivityLotteryPart) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return wechatActivityLotteryPart
	 * @throws Exception
	 */
	public WechatActivityLotteryPart loadById(Integer id) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryPart> loadAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatActivityLotteryPartVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryPart> loadListPage(WechatActivityLotteryPartVo wechatActivityLotteryPartVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatActivityLotteryPartVo
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(WechatActivityLotteryPartVo wechatActivityLotteryPartVo) throws Exception;
	
	/**
	 * 根据活动编码查询
	 * @param activityCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryPart> loadByActivityCode(String activityCode) throws Exception;
	
	/**
	 * 根据appId、openId、活动编码查询
	 * @param activityCode
	 * @param openId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryPart> loadByActivityCode(String activityCode, String openId, String appId) throws Exception;


}
