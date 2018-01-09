package com.tcode.business.finance.service;

import java.util.List;

import com.tcode.business.finance.model.Repayment;
import com.tcode.business.order.model.OrderHead;

public interface RepaymentService {

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Repayment getById(Integer id) throws Exception;
	
	/**
	 * 根据应收ID查询
	 * @param deptCode
	 * @param memId
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public List<Repayment> getByReceId(String deptCode, Integer memId, Integer carId) throws Exception;
	
	/**
	 * 根据供应商查询应付
	 * @param deptCode
	 * @param memId
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public List<Repayment> getBySupplierId(String deptCode, Integer supplierId) throws Exception;
	
	/**
	 * 添加
	 * @param repayment
	 * @throws Exception
	 */
	public void insert(Repayment repayment) throws Exception;
	
	/**
	 * 修改
	 * @param repayment
	 * @throws Exception
	 */
	public void update(Repayment repayment) throws Exception;
	
	/**
	 * 删除
	 * @param repayment
	 * @throws Exception
	 */
	public void delete(Repayment repayment) throws Exception;
	
	/**
	 * 统计个支付方式金额
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Double getSumByPay(OrderHead orderHead) throws Exception;
}
