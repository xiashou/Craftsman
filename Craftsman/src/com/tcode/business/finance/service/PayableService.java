package com.tcode.business.finance.service;

import java.util.List;

import com.tcode.business.finance.model.Payable;

public interface PayableService {

	/**
	 * 根据ID查找
	 * @param deptCode
	 * @param supplierId
	 * @return
	 * @throws Exception
	 */
	public Payable getById(String deptCode, Integer supplierId) throws Exception;
	
	/**
	 * 查询门店所有应付
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Payable> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 分页查询门店所有应付
	 * @param payable
	 * @return
	 * @throws Exception
	 */
	public List<Payable> getListByDeptPage(Payable payable, Integer start, Integer limit) throws Exception;
	
	/**
	 * 查询门店所有应付总数
	 * @param payable
	 * @return
	 * @throws Exception
	 */
	public Integer getListByDeptCount(Payable payable) throws Exception;
	
	/**
	 * 保存
	 * @param payable
	 * @throws Exception
	 */
	public void insert(Payable payable) throws Exception;
	
	/**
	 * 修改
	 * @param payable
	 * @throws Exception
	 */
	public void update(Payable payable) throws Exception;
	
	/**
	 * 删除
	 * @param payable
	 * @throws Exception
	 */
	public void delete(Payable payable) throws Exception;
	
	/**
	 * 查询管家端挂账总数
	 * @param companyId
	 * @param date
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Double> getSumByBoss(String companyId, String date, String deptCode) throws Exception;
	
	/**
	 * 查询管家端挂账列表
	 * @param companyId
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<Payable> getListByBossPage(String companyId, String deptCode, Integer start, Integer limit) throws Exception;
}
