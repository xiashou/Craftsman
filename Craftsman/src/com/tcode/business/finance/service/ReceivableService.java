package com.tcode.business.finance.service;

import java.util.List;

import com.tcode.business.finance.model.Receivable;
import com.tcode.business.order.model.OrderHead;

public interface ReceivableService {

	/**
	 * 根据ID查找
	 * @param deptCode
	 * @param memId
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public Receivable getById(String deptCode, Integer memId, Integer carId) throws Exception;
	
	/**
	 * 查询门店所有应收
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Receivable> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 分页查询门店所有应收
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Receivable> getListByDeptPage(Receivable receivable, Integer start, Integer limit) throws Exception;
	
	/**
	 * 查询门店所有应收总数
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public Integer getListByDeptCount(Receivable receivable) throws Exception;
	
	/**
	 * 保存
	 * @param receivable
	 * @throws Exception
	 */
	public void insert(Receivable receivable) throws Exception;
	
	/**
	 * 修改
	 * @param receivable
	 * @throws Exception
	 */
	public void update(Receivable receivable) throws Exception;
	
	/**
	 * 删除
	 * @param receivable
	 * @throws Exception
	 */
	public void delete(Receivable receivable) throws Exception;
	
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
	public List<Receivable> getListByBossPage(String companyId, String deptCode, Integer start, Integer limit) throws Exception;
	
	/**
	 * 统计挂账笔数
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Integer getCountByDept(OrderHead orderHead) throws Exception;
	
}
