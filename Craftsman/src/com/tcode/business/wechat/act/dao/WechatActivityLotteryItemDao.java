package com.tcode.business.wechat.act.dao;

import java.util.List;

import com.tcode.business.wechat.act.model.WechatActivityLotteryItem;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryItemVo;


public interface WechatActivityLotteryItemDao {
	
	/**
	 * 添加
	 * @param wechatActivityLotteryItem
	 * @throws Exception
	 */
	public void save(WechatActivityLotteryItem wechatActivityLotteryItem) throws Exception;
	
	/**
	 * 修改
	 * @param wechatActivityLotteryItem
	 * @throws Exception
	 */
	public void edit(WechatActivityLotteryItem wechatActivityLotteryItem) throws Exception;
	
	/**
	 * 删除
	 * @param wechatActivityLotteryItem
	 * @throws Exception
	 */
	public void remove(WechatActivityLotteryItem wechatActivityLotteryItem) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return wechatActivityLotteryItem
	 * @throws Exception
	 */
	public WechatActivityLotteryItem loadById(Integer id) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryItem> loadAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatActivityLotteryItemVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryItem> loadListPage(WechatActivityLotteryItemVo wechatActivityLotteryItemVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatActivityLotteryItemVo
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(WechatActivityLotteryItemVo wechatActivityLotteryItemVo) throws Exception;
	
	/**
	 * 根据活动编码查询
	 * @param activityCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryItem> loadByActivityCode(String activityCode) throws Exception;
	
	/**
	 * 根据活动编码、奖品等级查询
	 * @param activityCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryItem> loadByActivityCode(String activityCode, int prizeLevel) throws Exception;


}
