package com.tcode.business.finance.dao;

import java.util.List;

import com.tcode.business.finance.model.Payable;

public interface PayableDao {

	/**
	 * 根据ID查找
	 * @param deptCode
	 * @param supplierId
	 * @return
	 * @throws Exception
	 */
	public Payable loadById(String deptCode, Integer supplierId) throws Exception;
	
	/**
	 * 查询门店所有应付
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Payable> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 分页查询门店所有应付
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Payable> loadByDeptPage(Payable payable, Integer start, Integer limit) throws Exception;
	
	/**
	 * 分页查询门店所有应付总数
	 * @param payable
	 * @return
	 * @throws Exception
	 */
	public Integer loadByDeptCount(Payable payable) throws Exception;
	
	/**
	 * 保存
	 * @param payable
	 * @throws Exception
	 */
	public void save(Payable payable) throws Exception;
	
	/**
	 * 修改
	 * @param payable
	 * @throws Exception
	 */
	public void edit(Payable payable) throws Exception;
	
	/**
	 * 删除
	 * @param payable
	 * @throws Exception
	 */
	public void remove(Payable payable) throws Exception;
	
	/**
	 * 查询管家端应付总数
	 * @param companyId
	 * @param date
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Double> loadSumByBoss(String companyId, String date, String deptCode) throws Exception;
	
	/**
	 * 查询管家端应付列表
	 * @param companyId
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<Payable> loadByBossPage(String companyId, String deptCode, Integer start, Integer limit) throws Exception;
}
