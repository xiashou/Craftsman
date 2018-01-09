package com.tcode.business.finance.dao;

import java.util.List;

import com.tcode.business.finance.model.Repayment;
import com.tcode.business.order.model.OrderHead;

public interface RepaymentDao {

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Repayment loadById(Integer id) throws Exception;
	
	/**
	 * 根据应收ID查询
	 * @param deptCode
	 * @param memId
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public List<Repayment> loadByReceId(String deptCode, Integer memId, Integer carId) throws Exception;
	
	/**
	 * 根据供应商ID查询应付
	 * @param deptCode
	 * @param memId
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public List<Repayment> loadBySupplierId(String deptCode, Integer supplierId) throws Exception;
	
	/**
	 * 添加
	 * @param repayment
	 * @throws Exception
	 */
	public void save(Repayment repayment) throws Exception;
	
	/**
	 * 修改
	 * @param repayment
	 * @throws Exception
	 */
	public void edit(Repayment repayment) throws Exception;
	
	/**
	 * 删除
	 * @param repayment
	 * @throws Exception
	 */
	public void remove(Repayment repayment) throws Exception;
	
	/**
	 * 统计各支付方式金额
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Double loadSumByPay(OrderHead orderHead) throws Exception;
}
