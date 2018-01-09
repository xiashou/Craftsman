package com.tcode.business.wechat.act.service;

import java.util.List;

import org.weixin4j.OAuth2User;

import com.tcode.business.member.model.Member;
import com.tcode.business.wechat.act.model.WechatActivityLottery;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryVo;

public interface WechatActivityLotteryService {
	
	/**
	 * 添加
	 * @param wechatActivityLottery
	 * @throws Exception
	 */
	public void insert(WechatActivityLottery wechatActivityLottery) throws Exception;
	
	/**
	 * 修改
	 * @param wechatActivityLottery
	 * @throws Exception
	 */
	public void update(WechatActivityLottery wechatActivityLottery) throws Exception;
	
	/**
	 * 删除
	 * @param wechatActivityLottery
	 * @throws Exception
	 */
	public void delete(WechatActivityLottery wechatActivityLottery) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return wechatActivityLottery
	 * @throws Exception
	 */
	public WechatActivityLottery getById(Integer id) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLottery> getAll() throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param wechatActivityLotteryVo
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLottery> getListPage(WechatActivityLotteryVo wechatActivityLotteryVo, int start, int limit) throws Exception;
	
	/**
	 * 
	 * 根据条件查找条目数
	 * @param wechatActivityLotteryVo
	 * @return
	 * @throws Exception
	 */
	public Integer getListCount(WechatActivityLotteryVo wechatActivityLotteryVo) throws Exception;
	
	/**
	 * 根据活动编码查询
	 * @param activityCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLottery> getByActivityCode(String activityCode) throws Exception;
	
	/**
	 * 根据公司ID查询（有效的活动信息）
	 * @param activityCode
	 * @return
	 * @throws Exception
	 */
	public List<WechatActivityLottery> getByCompanyId(String companyId) throws Exception;

	
	public int executeWechatActivityLottery(Member member, OAuth2User oAuth2User, WechatActivityLottery wechatActivityLottery) throws Exception;
}
