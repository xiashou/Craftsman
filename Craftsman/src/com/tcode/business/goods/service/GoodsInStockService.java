package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsInStock;

public interface GoodsInStockService {

	/**
	 * 根据id查找
	 * @param id
	 * @return GoodsInStock
	 * @throws Exception
	 */
	public GoodsInStock getGoodsInStockById(Integer id) throws Exception;
	
	/**
	 * 根据门店查询所有
	 * @param deptCode
	 * @return List<GoodsInStock>
	 * @throws Exception
	 */
	public List<GoodsInStock> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 根据入库单号查询
	 * @param inNumber
	 * @return List<GoodsInStock>
	 * @throws Exception
	 */
	public List<GoodsInStock> getListByInNumber(String inNumber) throws Exception;
	
	/**
	 * 根据商品ID查询入库记录
	 * @param deptCode, goodsId
	 * @return List<GoodsInStock>
	 * @throws Exception
	 */
	public List<GoodsInStock> getListByGoodsId(String deptCode, String goodsId) throws Exception;
	
	/**
	 * 根据条件查询入库记录
	 * @param deptCode, goodsId
	 * @return List<GoodsInStock>
	 * @throws Exception
	 */
	public List<GoodsInStock> getListPage(GoodsInStock inStock, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查询入库记录总数
	 * @param Integer
	 * @throws Exception
	 */
	public Integer getListCount(GoodsInStock inStock) throws Exception;
	
	/**
	 * 添加多条记录
	 * 带事务
	 * @param List<GoodsInStock>
	 * @throws Exception
	 */
	public Integer insertMoreGoodsInStock(List<GoodsInStock> inStockList) throws Exception;
	
	/**
	 * 商品退货供应商
	 * @param inStockList
	 * @return
	 * @throws Exception
	 */
	public Integer insertReturnGoodsInStock(List<GoodsInStock> inStockList) throws Exception;
	
	/**
	 * 添加
	 * @param GoodsInStock
	 * @throws Exception
	 */
	public void insert(GoodsInStock inStock) throws Exception;
	
	/**
	 * 修改
	 * @param GoodsInStock
	 * @throws Exception
	 */
	public void update(GoodsInStock inStock) throws Exception;
	
	/**
	 * 删除
	 * @param GoodsInStock
	 * @throws Exception
	 */
	public void delete(GoodsInStock inStock) throws Exception;
}
