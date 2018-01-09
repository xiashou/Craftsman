package com.tcode.business.order.service;

import java.util.List;

import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;


public interface OrderService {
	
	/**
	 * 创建订单
	 * @param orderHead
	 * @param itemList
	 * @return
	 * @throws Exception
	 */
	public String insertOrder(OrderHead orderHead, List<OrderItem> itemList) throws Exception;
	
	/**
	 * 创建退货订单
	 * @param orderHead
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public String insertReturnOrder(OrderHead orderHead, List<OrderItem> itemList) throws Exception;
	
	/**
	 * 创建充值订单
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public String insertRechargeOrder(OrderHead orderHead) throws Exception;
	
	/**
	 * 快捷创建订单
	 * @param orderHead
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public String insertQuickOrder(OrderHead orderHead, OrderItem orderItem) throws Exception;
	
	/**
	 * 删除订单（逻辑删）
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public void deleteOrderByNo(String orderNo) throws Exception;
	
	/**
	 * 删除订单（物理删,慎用）
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public void deletePhysicsOrderByNo(String orderNo) throws Exception;
	
	/**
	 * 分页查询会员历史消费记录
	 * @param memId
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getListByMemIdPage(Integer memId, Integer start, Integer limit) throws Exception;
	
	/**
	 * 分页查询商品销售记录
	 * @param memId
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getListByGoodsIdPage(OrderItem item, Integer start, Integer limit) throws Exception;
	
	/**
	 * 查询会员历史消费记录总数
	 * @param memId
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Integer getListCountByMemIdPage(Integer memId) throws Exception;
	
	/**
	 * 分页查询商品销售记录总数
	 * @param memId
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Integer getListCountByGoodsIdPage(OrderItem item) throws Exception;
	
	/**
	 * 查询店铺最后几条营业数据
	 * @param deptCode
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getLastListByDept(String deptCode, Integer limit) throws Exception;
	
	/**
	 * 查询店铺未完成订单
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getIncompleteListByDept(String deptCode) throws Exception;
	
	/**
	 * 关键字查询会员购买记录
	 * @param deptCode
	 * @param memId
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getListByKeyword(String deptCode, Integer memId, String keyword) throws Exception;
	
	/**
	 * 关键字查询会员购买记录
	 * @param deptCode
	 * @param memId
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getListByKeywords(String deptCode, Integer memId, String keyword) throws Exception;
	
	/**
	 * 分页查询店铺营销数据
	 * @param deptCode
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getListByDeptPage(OrderItem item, String deptCode, Integer start, Integer limit) throws Exception;
	
	/**
	 * 查询充值订单
	 * @param item
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getRechargeListByDeptPage(OrderItem item, String deptCode, Integer start, Integer limit) throws Exception;
	
	/**
	 * 分页查询店铺销售数据总数
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Integer getListCountByDept(OrderItem item, String deptCode) throws Exception;
	
	/**
	 * 分页查询店铺充值订单数据总数
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Integer getRechargeListCountByDept(OrderItem item, String deptCode) throws Exception;

	/**
	 * 根据订单号查询订单表头
	 * @param orderNo
	 * @return OrderHead
	 * @throws Exception
	 */
	public OrderHead getHeadByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 根据订单号查询订单项目
	 * @param orderNo
	 * @return List<OrderItem>
	 * @throws Exception
	 */
	public List<OrderItem> getItemByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 添加一条订单表头信息
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Boolean insertOrderHead(OrderHead orderHead) throws Exception;
	
	/**
	 * 添加一行订单项目
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public Boolean insertOrderItem(OrderItem orderItem) throws Exception;
	
	/**
	 * 修改订单抬头
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Boolean updateOrderHead(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询店铺未完成订单
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public Integer getUnfinishedOrderCount(String deptCode) throws Exception;
	
	/**
	 * 根据订单号查询项目或商品列表(type=1项目 type=2商品)
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getTypeListByOrderNo(String orderNo, Integer type) throws Exception;
	
	/**
	 * 出库记录表
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getOutGoodsListPage(OrderItem orderItem, Integer start, Integer limit) throws Exception;
	
	/**
	 * 出库记录表记录数
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public Integer getOutGoodsListCount(OrderItem orderItem) throws Exception;
	
	/**
	 * 根据条件查询施工提成订单
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getCommissionList(OrderItem orderItem) throws Exception;
	
	/**
	 * 根据条件查询接单提成订单
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> getCommissionList(OrderHead orderHead) throws Exception;
	
	/**
	 * 销售顾问提成
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> getCommissionSalesList(OrderHead orderHead) throws Exception;
	
	/**
	 * 更改订单状态
	 * @param orderNo
	 * @param status
	 * @throws Exception
	 */
	public void updateOrderStatus(String orderNo, Integer status) throws Exception;
	
	/**
	 * 查询会员历史挂账记录
	 * @param memId
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> getBillListByMemIdPage(Integer memId, String deptCode, Integer start, Integer limit) throws Exception;
	
	/**
	 * 查询会员历史挂账记录总数
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Integer getBillListCountByMemId(Integer memId, String deptCode) throws Exception;
	
	/**
	 * 分页查询营业流水
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> getWaterListPage(OrderHead orderHead, Integer start, Integer limit) throws Exception;
	
	/**
	 * 分页查询营业流水总数
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Integer getWaterListCount(OrderHead orderHead) throws Exception;
	
	/**
	 * 分页查询已删除订单
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> getDeleteListPage(OrderHead orderHead, Integer start, Integer limit) throws Exception;
	
	/**
	 * 分页查询已删除订单总数
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Integer getDeleteListCount(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询公司日营业额
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	public Double getDayTurnover(String companyId, String date, String deptCode) throws Exception;
	
	/**
	 * 查询各订单类型的销售额
	 * @param companyId
	 * @param date
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> getSalesGroupType(String companyId, String date, String deptCode) throws Exception;
	
	/**
	 * 统计项目大类销售数据
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getListGroupProject(OrderItem orderItem) throws Exception;
	
	/**
	 * 统计订单类型销售数据
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> getListGroupOrder(OrderItem orderItem) throws Exception;
	
	/**
	 * 根据支付方式统计金额
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public List<OrderHead> getListGroupPay(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询当前库存的总零售价
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Double getStoreSumOprice(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询当前库存的总成本价
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Double getStoreSumAprice(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询库存品类总数
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Integer getStoreTypeCount(OrderHead orderHead) throws Exception;
	
	/**
	 * 查询商品总数
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Integer getGoodsCount(OrderHead orderHead) throws Exception;
	
	/**
	 * 车辆进店台数
	 * @param orderHead
	 * @return
	 * @throws Exception
	 */
	public Integer getCarsCount(OrderHead orderHead) throws Exception;
	
	/**
	 * 更新提成相关人员
	 * @param orderId
	 * @param itemNo
	 * @param performer
	 * @param seller
	 * @param middleman
	 * @throws Exception
	 */
	public void updateCommissioner(String orderId, String itemNo, String performer, String seller, String middleman) throws Exception;
	
}
