package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsMaterial;

public interface GoodsMaterialService {

	/**
	 * 根据ID查找(带库存)
	 * @param id
	 * @return GoodMaterial
	 * @throws Exception
	 */
	public GoodsMaterial getGoodsMaterialById(String deptCode, String id) throws Exception;
	
	/**
	 * 根据ID查找(不带库存)
	 * @param deptCode
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GoodsMaterial getById(String id) throws Exception;
	
	/**
	 * 根据门店查找所有商品
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMaterial> getGoodsMaterialByDept(String deptCode) throws Exception;
	
	/**
	 * 根据类型查找
	 * @param typeId
	 * @return Member
	 * @throws Exception
	 */
	public List<GoodsMaterial> getGoodsMaterialByType(Integer typeId) throws Exception;
	
	/**
	 * 根据名称查找
	 * @param name
	 * @throws Exception
	 */
	public GoodsMaterial getGoodsMaterialByName(String deptCode, String name) throws Exception;
	
	/**
	 * 根据编码查找
	 * @param code
	 * @throws Exception
	 */
	public GoodsMaterial getGoodsMaterialByCode(String deptCode, String code) throws Exception;
	
	/**
	 * 根据ID或者编码查找
	 * @param code
	 * @throws Exception
	 */
	public GoodsMaterial getGoodsMaterialByIdCode(String deptCode, String idOrCode) throws Exception;
	
	/**
	 * 根据关键字 查找(带类型名)
	 * @param deptCode
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMaterial> getGoodsMaterialByKeyword(String deptCode, String keyword) throws Exception;
	
	/**
	 * 根据类型和关键字查找
	 * @param companyId
	 * @param deptCode
	 * @param typeId
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMaterial> getGoodsMaterialByTypeKeyword(String companyId, Integer typeId, String keyword) throws Exception;
	
	/**
	 * 根据关键字 查找(带库存)
	 * @param deptCode
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMaterial> getGoodsMaterialStockByTypeKeyword(String companyId, String deptCode, Integer typeId, String keyword) throws Exception;
	
	/**
	 * 批量添加商品
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Integer insertMoreGoodsMaterial(List<GoodsMaterial> list) throws Exception;
	/**
	 * 添加
	 * @param GoodsMaterial
	 * @throws Exception
	 */
	public void insert(GoodsMaterial goodsMaterial) throws Exception;
	
	/**
	 * 修改
	 * @param GoodsMaterial
	 * @throws Exception
	 */
	public void update(GoodsMaterial goodsMaterial) throws Exception;
	
	/**
	 * 删除
	 * @param GoodsMaterial
	 * @throws Exception
	 */
	public void delete(GoodsMaterial goodsMaterial) throws Exception;
	
	/**
	 * 根据类型删除
	 * @param GoodsMaterial
	 * @throws Exception
	 */
	public void deleteByType(GoodsMaterial goodsMaterial) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start, limit
	 * @return List<GoodMaterial>
	 * @throws Exception
	 */
	public List<GoodsMaterial> getListPage(GoodsMaterial goodsMaterial, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<GoodMaterial>
	 * @throws Exception
	 */
	public Integer getListCount(GoodsMaterial goodsMaterial) throws Exception;
}
