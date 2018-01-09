package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsPackage;

public interface GoodsPackageService {

	/**
	 * 查询门店所有工时商品
	 * @return
	 * @throws Exception
	 */
	public List<GoodsPackage> getGoodsPackageByDeptCode(String deptCode, String companyId) throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return goodsPackage
	 * @throws Exception
	 */
	public GoodsPackage getGoodsPackageById(String id) throws Exception;
	
	/**
	 * 添加
	 * @param GoodsPackage
	 * @throws Exception
	 */
	public void insert(GoodsPackage goodsPackage) throws Exception;
	
	/**
	 * 保存商品套餐信息
	 * @param goodsPackage
	 * @throws Exception
	 */
	public int saveGoodsPackage(GoodsPackage goodsPackage) throws Exception;
	
	/**
	 * 修改
	 * @param GoodsPackage
	 * @throws Exception
	 */
	public void update(GoodsPackage goodsPackage) throws Exception;
	
	/**
	 * 删除
	 * @param GoodsPackage
	 * @throws Exception
	 */
	public void delete(GoodsPackage goodsPackage) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start, limit
	 * @return List<goodsPackage>
	 * @throws Exception
	 */
	public List<GoodsPackage> getListPage(GoodsPackage goodsPackage, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<GoodHour>
	 * @throws Exception
	 */
	public Integer getListCount(GoodsPackage goodsPackage) throws Exception;
}
