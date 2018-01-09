package com.tcode.business.order.dao;

import java.util.List;

import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;

public interface OrderHeadDao {
	

	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(OrderHead orderHead) throws Exception;
	
	/**
	 * 修改
	 * @throws Exception
	 */
	public void edit(OrderHead orderHead) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(OrderHead orderHead) throws Exception;
	
	/**
	 * 根据订单号查询订单表头
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public OrderHead loadHeadByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 统计店铺未完成订单数
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public Integer loadUnfinishedOrderCount(String deptCode) throws Exception;
	
	/**
	 * 查询充值订单（没有项目数据）
	 * @param item
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadRechargeListByDeptPage(OrderItem item, String deptCode, Integer start, Integer limit) throws Exception;
	
	/**
	 * 分页查询店铺充值订单数据总数
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Integer loadRechargeListCountByDept(OrderItem item, String deptCode) throws Exception;
	
	/**
	 * 更改订单状态
	 * @param orderId
	 * @param status
	 * @throws Exception
	 */
	public void editOrderStatus(String orderId, Integer status) throws Exception;
	
	/**
	 * 查询会员历史挂账记录
	 * @param memId
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> loadBillListByMemIdPage(Integer memId, String deptCode, Integer start, Integer limit) throws Exception;
	
	/**
	 * 查询会员历史挂账记录总数
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Integer loadBillListCountByMemId(Integer memId, String deptCode) throws Exception;
	
	/**
	 * 根据条件查询施工提成订单
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> loadCommissionList(OrderHead orderHead) throws Exception;
	
	/**
	 * 分页查询营业流水
	 * @param memId
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> loadWaterListPage(OrderHead orderHead, Integer start, Integer limit) throws Exception;
	
	/**
	 * 分页查询营业流水总数
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Integer loadWaterListCount(OrderHead orderHead) throws Exception;
	
	/**
	 * 分页查询被删除订单
	 * @param orderHead
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> loadDeleteListPage(OrderHead orderHead, Integer start, Integer limit) throws Exception;
	
	/**
	 * 分页查询被删除订单总数
	 * @param orderHead
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Integer loadDeleteListCount(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询公司日营业额
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	public Double loadDayTurnover(String companyId, String date, String deptCode) throws Exception;
	
	/**
	 * 查询各订单类型的销售额
	 * @param companyId
	 * @param date
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> loadSalesGroupType(String companyId, String date, String deptCode) throws Exception;
	
	/**
	 * 根据会员ID和本次保养公里数取上一次保养时间
	 * @param memId
	 * @param kilo
	 * @return
	 * @throws Exception
	 */
	public OrderHead loadPreOrderByMemIdAndKilo(Integer memId, Integer kilo) throws Exception;
	
	/**
	 * 根据支付方式统计金额
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> loadListGroupPay(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询当前库存的总零售价
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Double loadStoreSumOprice(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询当前库存的总成本价
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Double loadStoreSumAprice(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询库存品类总数
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Integer loadStoreTypeCount(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询商品总数
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Integer loadGoodsCount(OrderHead orderHead) throws Exception;
	
	/**
	 * 进店车辆台数
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Integer loadCarsCount(OrderHead orderHead) throws Exception;
	
}
