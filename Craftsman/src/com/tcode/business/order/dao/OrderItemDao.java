package com.tcode.business.order.dao;

import java.util.List;

import com.tcode.business.order.model.OrderItem;

public interface OrderItemDao {
	
	/**
	 * 分页查询会员历史消费记录
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadListByMemIdPage(Integer memId, int start, int limit) throws Exception;
	
	/**
	 * 查询会员历史消费记录总数
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCountByMemIdPage(Integer memId) throws Exception;
	
	/**
	 * 分页查询会商品销售记录
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadListByGoodsIdPage(OrderItem item, int start, int limit) throws Exception;
	
	/**
	 * 分页查询会商品销售记录总数
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCountByGoodsIdPage(OrderItem item) throws Exception;
	
	/**
	 * 查询店铺最后几条营业数据
	 * @param deptCode
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadLastListByDept(String deptCode, Integer limit) throws Exception;
	
	/**
	 * 查询店铺未完成订单
	 * @param deptCode
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadIncompleteListByDept(String deptCode) throws Exception;
	
	/**
	 * 分页查询店铺销售数据
	 * @param item
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadListByDeptPage(OrderItem item, String deptCode, Integer start, Integer limit) throws Exception;
	
	/**
	 * 分页查询店铺销售数据总数
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCountByDept(OrderItem item, String deptCode) throws Exception;
	
	/**
	 * 查询会员消费记录
	 * @param deptCode
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadOrderByKeyword(String deptCode, Integer memId, String keyword) throws Exception;
	
	/**
	 * 查询会员消费记录
	 * @param deptCode
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadOrderByKeywords(String deptCode, Integer memId, String keyword) throws Exception;
	
	/**
	 * 根据订单号查询订单项目
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadItemByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 根据订单号查订单项目列表
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadProjectListByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 根据订单号查询订单商品列表
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadProductListByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 根据条件查询施工提成订单
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadCommissionList(OrderItem orderItem) throws Exception;
	
	/**
	 * 保存一条订单项目
	 * @param orderItem
	 * @throws Exception
	 */
	public void save(OrderItem orderItem) throws Exception;
	
	/**
	 * 删除
	 * @param orderItem
	 * @throws Exception
	 */
	public void remove(OrderItem orderItem) throws Exception;
	
	/**
	 * 根据订单号删除
	 * @param orderItem
	 * @throws Exception
	 */
	public void removeByOrderNo(String orderNo) throws Exception;
	
	/**
	 * 出库记录表
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadOutGoodsListPage(OrderItem orderItem, Integer start, Integer limit) throws Exception;
	
	/**
	 * 出库记录表记录数
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public Integer loadOutGoodsListCount(OrderItem orderItem) throws Exception;
	
	/**
	 * 统计项目大类销售数据
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadListGroupProject(OrderItem orderItem) throws Exception;
	
	/**
	 * 统计订单类型销售数据
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public List<OrderItem> loadListGroupOrder(OrderItem orderItem) throws Exception;
	
	/**
	 * 更新提成相关人员
	 * @param orderId
	 * @param itemNo
	 * @param performer
	 * @param seller
	 * @param middleman
	 * @throws Exception
	 */
	public void editCommissioner(String orderId, String itemNo, String performer, String seller, String middleman) throws Exception;
}
