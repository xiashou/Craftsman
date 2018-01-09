package com.tcode.business.wechat.act.dao;

import java.util.List;

import com.tcode.business.wechat.act.model.ActJoiner;

public interface ActJoinerDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<ActJoiner> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return ActJoiner
	 * @throws Exception
	 */
	public ActJoiner loadById(Integer id) throws Exception;
	
	/**
	 * 根据openId和活动查询
	 * @param openId
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	public ActJoiner loadByJoin(String openId, Integer actId) throws Exception;
	
	/**
	 * 根据订单号查
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public ActJoiner loadByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(ActJoiner joiner) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(ActJoiner joiner) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(ActJoiner joiner) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param ActJoiner, start,limit
	 * @return List<ActJoiner>
	 * @throws Exception
	 */
	public List<ActJoiner> loadListPage(ActJoiner joiner, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param ActJoiner
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(ActJoiner joiner) throws Exception;
}
