package com.tcode.business.report.service;

import java.util.List;

import com.tcode.business.report.model.ReptMemberStock;

public interface ReptMemberStockService {

	/**
	 * 根据id查找
	 * @param id
	 * @return ReptMemberStock
	 * @throws Exception
	 */
	public ReptMemberStock getReptMemberStockById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param deptCode
	 * @return List<ReptMemberStock>
	 * @throws Exception
	 */
	public List<ReptMemberStock> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 根据会员ID查找
	 * @param memId
	 * @return List<ReptMemberStock>
	 * @throws Exception
	 */
	public List<ReptMemberStock> getListByMemId(Integer memId) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param recharge, start, limit
	 * @return List<ReptMemberStock>
	 * @throws Exception
	 */
	public List<ReptMemberStock> getListPage(ReptMemberStock memberStock, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param ReptMemberStock
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getListCount(ReptMemberStock memberStock) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insert(ReptMemberStock memberStock) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void update(ReptMemberStock memberStock) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void delete(ReptMemberStock memberStock) throws Exception;

}
