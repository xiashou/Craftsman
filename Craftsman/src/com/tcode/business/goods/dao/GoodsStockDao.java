package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsStock;

public interface GoodsStockDao {

	
	/**
	 * 根据id查找
	 * @param id
	 * @return List<GoodsStock>
	 * @throws Exception
	 */
	public GoodsStock loadById(String goodsId, String deptCode) throws Exception;
	
	/**
	 * 根据商品id查找所在库存
	 * @param id
	 * @return List<GoodsStock>
	 * @throws Exception
	 */
	public List<GoodsStock> loadByGoodsId(String goodsId) throws Exception;
	
	/**
	 * 根据门店查询所有库存
	 * @param deptCode
	 * @return List<GoodsStock>
	 * @throws Exception
	 */
	public List<GoodsStock> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 根据关键字查询门店库存
	 * @param deptCode, keyword
	 * @return List<GoodsStock>
	 * @throws Exception
	 */
	public List<GoodsStock> loadByKeyword(String deptCode, String keyword) throws Exception;
	
	/**
	 * 扣减库存
	 * @param deptCode
	 * @param goodsId
	 * @param number
	 * @throws Exception
	 */
	public void editByGoodsId(String deptCode, String goodsId, Double number) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsStock stock) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsStock stock) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsStock stock) throws Exception;
	
	/**
	 * 根据条件分页查询门店库存
	 * @param GoodsStock
	 * @return List<GoodsStock>
	 * @throws Exception
	 */
	public List<GoodsStock> loadByDeptPage(GoodsStock stock, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查询门店库存总数
	 * @param GoodsStock
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadByDeptPageCount(GoodsStock stock) throws Exception;
	
	/**
	 * 统计库存总品类数
	 * @param companyId
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Integer loadByBossCount(String companyId, String deptCode) throws Exception;
	
	/**
	 * 统计库存总成本
	 * @param companyId
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Integer loadByBossCost(String companyId, String deptCode) throws Exception;
	
	/**
	 * 统计库存类别成本
	 * @param companyId
	 * @param deptCode
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<GoodsStock> loadByBossType(String companyId, String deptCode) throws Exception;
}
