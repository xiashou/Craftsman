package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsStock;

public interface GoodsStockService {

	/**
	 * 根据id查找
	 * @param id
	 * @return List<GoodsStock>
	 * @throws Exception
	 */
	public GoodsStock getGoodsStockById(String goodsId, String deptCode) throws Exception;
	
	/**
	 * 根据商品ID查找
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<GoodsStock> getGoodsStockByGoodsId(String goodsId) throws Exception;
	
	/**
	 * 根据门店查询所有
	 * @param deptCode
	 * @return List<GoodsStock>
	 * @throws Exception
	 */
	public List<GoodsStock> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 根据关键字查询门店库存
	 * @param deptCode, keyword
	 * @return List<GoodsStock>
	 * @throws Exception
	 */
	public List<GoodsStock> getListByKeyword(String deptCode, String keyword) throws Exception;
	
	/**
	 * 添加多条记录
	 * 带事务
	 * @param List<GoodsStock>
	 * @throws Exception
	 */
	public void insertMoreGoodsStock(List<GoodsStock> stockList) throws Exception;
	
	/**
	 * 添加
	 * @param GoodsStock
	 * @throws Exception
	 */
	public void insert(GoodsStock stock) throws Exception;
	
	/**
	 * 修改
	 * @param GoodsStock
	 * @throws Exception
	 */
	public void update(GoodsStock stock) throws Exception;
	
	/**
	 * 删除
	 * @param GoodsStock
	 * @throws Exception
	 */
	public void delete(GoodsStock stock) throws Exception;
	
	/**
	 * 根据条件分页查询门店库存
	 * @param GoodsStock
	 * @return List<GoodsStock>
	 * @throws Exception
	 */
	public List<GoodsStock> getListByDeptPage(GoodsStock stock, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查询门店库存总数
	 * @param stock
	 * @param start
	 * @param limit
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getCountByDeptPage(GoodsStock stock) throws Exception;
	
	/**
	 * 统计库存总品类数
	 * @param companyId
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Integer getByBossCount(String companyId, String deptCode) throws Exception;
	
	/**
	 * 统计库存总成本
	 * @param companyId
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Integer getByBossCost(String companyId, String deptCode) throws Exception;
	
	/**
	 * 统计库存分类成本
	 * @param companyId
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<GoodsStock> getByBossType(String companyId, String deptCode) throws Exception;
}
