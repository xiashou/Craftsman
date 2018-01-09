package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsMaterial;

public interface GoodsMaterialDao {
	
	/**
	 * 根据id查找
	 * @param id
	 * @return GoodMaterial
	 * @throws Exception
	 */
	public GoodsMaterial loadById(String id) throws Exception;
	
	/**
	 * 根据门店查找
	 * @param deptCode
	 * @return List<GoodsMaterial>
	 * @throws Exception
	 */
	public List<GoodsMaterial> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 根据名称查找
	 * @param name
	 * @throws Exception
	 */
	public GoodsMaterial loadByName(String deptCode, String name) throws Exception;
	
	/**
	 * 根据编码查找
	 * @param code
	 * @throws Exception
	 */
	public GoodsMaterial loadByCode(String deptCode, String code) throws Exception;
	
	/**
	 * 根据编码或者ID查找
	 * @param deptCode
	 * @param idOrCode
	 * @return
	 * @throws Exception
	 */
	public GoodsMaterial loadByIdOrCode(String deptCode, String idOrCode) throws Exception;
	
	/**
	 * 根据关键字查询商品
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMaterial> loadByKeyword(String deptCode, String keyword) throws Exception;
	
	/**
	 * 根据关键字和类型查询商品
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMaterial> loadByTypeAndKeyword(String deptCode, Integer typeId, String keyword) throws Exception;
	
	/**
	 * 根据类型查找
	 * @param typeId
	 * @return List<GoodMaterial>
	 * @throws Exception
	 */
	public List<GoodsMaterial> loadByType(Integer typeId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsMaterial goodsMaterial) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsMaterial goodsMaterial) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsMaterial goodsMaterial) throws Exception;
	
	/**
	 * 根据类型删除
	 * @param goodsMaterial
	 * @throws Exception
	 */
	public void removeByType(GoodsMaterial goodsMaterial) throws Exception;
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<GoodMaterial>
	 * @throws Exception
	 */
	public List<GoodsMaterial> loadListPage(GoodsMaterial goodsMaterial, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param GoodsMaterial
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(GoodsMaterial goodsMaterial) throws Exception;

}
