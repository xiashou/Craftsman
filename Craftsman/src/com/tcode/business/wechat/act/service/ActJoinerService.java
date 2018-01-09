package com.tcode.business.wechat.act.service;

import java.util.List;

import com.tcode.business.wechat.act.model.ActJoiner;

public interface ActJoinerService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<ActJoiner> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return ActJoiner
	 * @throws Exception
	 */
	public ActJoiner getById(Integer id) throws Exception;
	
	/**
	 * 根据openId和活动查询
	 * @param openId
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	public ActJoiner getByJoin(String openId, Integer actId) throws Exception;
	
	/**
	 * 根据订单号查询
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public ActJoiner getByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 添加
	 * @param ActJoiner
	 * @throws Exception
	 */
	public void insert(ActJoiner joiner) throws Exception;
	
	/**
	 * 修改
	 * @param ActJoiner
	 * @throws Exception
	 */
	public void update(ActJoiner joiner) throws Exception;
	
	/**
	 * 删除
	 * @param ActJoiner
	 * @throws Exception
	 */
	public void delete(ActJoiner joiner) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param ActJoiner, start, limit
	 * @return List<ActJoiner>
	 * @throws Exception
	 */
	public List<ActJoiner> getListPage(ActJoiner joiner, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param ActJoiner
	 * @return List<ActJoiner>
	 * @throws Exception
	 */
	public Integer getListCount(ActJoiner joiner) throws Exception;
}
