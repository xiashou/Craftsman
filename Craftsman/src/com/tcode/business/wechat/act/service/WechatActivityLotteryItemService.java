package com.tcode.business.wechat.act.service;

import java.util.List;

import com.tcode.business.wechat.act.model.WechatActivityLotteryItem;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryItemVo;

public interface WechatActivityLotteryItemService {
	
	/**
	 * 添加
	 * @param wechatActivityLotteryItem
	 * @throws Exception
	 */
	public void insert(WechatActivityLotteryItem wechatActivityLotteryItem) throws Exception;
	
	/**
	 * 修改
	 * @param wechatActivityLotteryItem
	 * @throws Exception
	 */
	public void update(WechatActivityLotteryItem wechatActivityLotteryItem) throws Exception;
	
	/**
	 * 删除
	 * @param wechatActivityLotteryItem
	 * @throws Exception
	 */
	public void delete(WechatActivityLotteryItem wechatActivityLotteryItem) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return wechatActivityLotteryItem
	 * @throws Exception
	 */
	public WechatActivityLotteryItem getById(Integer id) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryItem> getAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatActivityLotteryItemVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryItem> getListPage(WechatActivityLotteryItemVo wechatActivityLotteryItemVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatActivityLotteryItemVo
	 * @return
	 * @throws Exception
	 */
	public Integer getListCount(WechatActivityLotteryItemVo wechatActivityLotteryItemVo) throws Exception;
	
	/**
	 * 根据活动编码查询
	 * @param activityCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLotteryItem> getByActivityCode(String activityCode) throws Exception;

}
