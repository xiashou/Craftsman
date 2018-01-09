package com.tcode.business.report.dao;

import java.util.List;

import com.tcode.business.report.model.ReptMemberStock;

public interface ReptMemberStockDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return ReptMemberStock
	 * @throws Exception
	 */
	public ReptMemberStock loadById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param deptCode
	 * @return List<ReptMemberStock>
	 * @throws Exception
	 */
	public List<ReptMemberStock> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 根据会员ID查找
	 * @param memId
	 * @return List<ReptMemberStock>
	 * @throws Exception
	 */
	public List<ReptMemberStock> loadByMemId(Integer memId) throws Exception;
	
	/**
	 * 添加一条充值记录
	 * @throws Exception
	 */
	public void addRecord(String deptCode, Integer memId, String orderNo, String goodsId, String goodsName, Double number) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(ReptMemberStock memberStock) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(ReptMemberStock memberStock) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(ReptMemberStock memberStock) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param memberStock, start, limit
	 * @return List<ReptMemberStock>
	 * @throws Exception
	 */
	public List<ReptMemberStock> loadListPage(ReptMemberStock memberStock, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param ReptMemberStock
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(ReptMemberStock memberStock) throws Exception;
	
	
}
