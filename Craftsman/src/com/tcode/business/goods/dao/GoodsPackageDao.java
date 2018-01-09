package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsPackage;

public interface GoodsPackageDao {
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<GoodsPackage> loadAll(String deptCode, String companyId) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return goodsPackage
	 * @throws Exception
	 */
	public GoodsPackage loadById(String id) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsPackage goodsPackage) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsPackage goodsPackage) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsPackage goodsPackage) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<goodsPackage>
	 * @throws Exception
	 */
	public List<GoodsPackage> loadListPage(GoodsPackage goodsPackage, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param GoodsPackage
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(GoodsPackage goodsPackage) throws Exception;

}
