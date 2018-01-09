package com.tcode.business.report.dao;

import java.util.List;

import com.tcode.business.report.model.ReptDelete;

public interface ReptDeleteDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return ReptDelete
	 * @throws Exception
	 */
	public ReptDelete loadById(Integer id) throws Exception;
	
	/**
	 * 根据订单编号查找
	 * @param id
	 * @return ReptDelete
	 * @throws Exception
	 */
	public ReptDelete loadByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param deptCode
	 * @return List<ReptDelete>
	 * @throws Exception
	 */
	public List<ReptDelete> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 根据会员ID查找
	 * @param memId
	 * @return List<ReptDelete>
	 * @throws Exception
	 */
	public List<ReptDelete> loadByMemId(Integer memId) throws Exception;
	
	/**
	 * 添加一条记录
	 * @throws Exception
	 */
	public void addRecord(String deptCode, String orderNo, String userId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(ReptDelete reptDelete) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(ReptDelete reptDelete) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(ReptDelete reptDelete) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param reptDelete, start, limit
	 * @return List<ReptDelete>
	 * @throws Exception
	 */
	public List<ReptDelete> loadListPage(ReptDelete reptDelete, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param reptDelete
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(ReptDelete reptDelete) throws Exception;
	
	
}
