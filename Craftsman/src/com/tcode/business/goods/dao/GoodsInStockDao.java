package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsInStock;

public interface GoodsInStockDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return GoodsInStock
	 * @throws Exception
	 */
	public GoodsInStock loadById(Integer id) throws Exception;
	
	/**
	 * 根据门店查询所有
	 * @param deptCode
	 * @return List<GoodsInStock>
	 * @throws Exception
	 */
	public List<GoodsInStock> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 根据门入库单号查询
	 * @param deptCode
	 * @return List<GoodsInStock>
	 * @throws Exception
	 */
	public List<GoodsInStock> loadByInNumber(String inNumber) throws Exception;
	
	/**
	 * 根据商品查询入库记录
	 * @param deptCode, goodsId
	 * @return List<GoodsInStock>
	 * @throws Exception
	 */
	public List<GoodsInStock> loadByGoodsId(String deptCode, String goodsId) throws Exception;
	
	/**
	 * 分页查询
	 * @param inStock
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<GoodsInStock> loadListPage(GoodsInStock inStock, int start, int limit) throws Exception;
	
	/**
	 * 分页查询总数
	 * @param inStock
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public Integer loadListCount(GoodsInStock inStock) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsInStock inStock) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsInStock inStock) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsInStock inStock) throws Exception;
}
