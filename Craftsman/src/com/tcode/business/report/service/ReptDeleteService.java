package com.tcode.business.report.service;

import java.util.List;

import com.tcode.business.report.model.ReptDelete;

public interface ReptDeleteService {

	/**
	 * 根据id查找
	 * @param id
	 * @return ReptDelete
	 * @throws Exception
	 */
	public ReptDelete getReptDeleteById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param deptCode
	 * @return List<ReptDelete>
	 * @throws Exception
	 */
	public List<ReptDelete> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 根据会员ID查找
	 * @param memId
	 * @return List<ReptDelete>
	 * @throws Exception
	 */
	public List<ReptDelete> getListByMemId(Integer memId) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param recharge, start, limit
	 * @return List<ReptDelete>
	 * @throws Exception
	 */
	public List<ReptDelete> getListPage(ReptDelete reptDelete, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param ReptDelete
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getListCount(ReptDelete reptDelete) throws Exception;
	
	/**
	 * 添加一条记录
	 * @param reptDelete
	 * @throws Exception
	 */
	public void insertRecord(String deptCode, String orderNo, String userId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insert(ReptDelete reptDelete) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void update(ReptDelete reptDelete) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void delete(ReptDelete reptDelete) throws Exception;

}
