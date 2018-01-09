package com.tcode.business.wechat.act.dao;

import java.util.List;

import com.tcode.business.wechat.act.model.WechatActivityLottery;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryVo;

public interface WechatActivityLotteryDao {
	
	/**
	 * 添加
	 * @param wechatActivityLottery
	 * @throws Exception
	 */
	public void save(WechatActivityLottery wechatActivityLottery) throws Exception;
	
	/**
	 * 修改
	 * @param wechatActivityLottery
	 * @throws Exception
	 */
	public void edit(WechatActivityLottery wechatActivityLottery) throws Exception;
	
	/**
	 * 删除
	 * @param wechatActivityLottery
	 * @throws Exception
	 */
	public void remove(WechatActivityLottery wechatActivityLottery) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return wechatActivityLottery
	 * @throws Exception
	 */
	public WechatActivityLottery loadById(Integer id) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLottery> loadAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatActivityLotteryVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLottery> loadListPage(WechatActivityLotteryVo wechatActivityLotteryVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatActivityLotteryVo
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(WechatActivityLotteryVo wechatActivityLotteryVo) throws Exception;
	
	/**
	 * 根据活动编码查询
	 * @param activityCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLottery> loadByActivityCode(String activityCode) throws Exception;
	
	/**
	 * 根据公司ID查询（有效的活动信息）
	 * @param activityCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLottery> loadByCompanyId(String companyId) throws Exception;

}
