package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsMaterialType;

public interface GoodsMaterialTypeDao {

	/**
	 * 根据ID查分类
	 * @param id
	 * @throws Exception
	 */
	public GoodsMaterialType loadById(Integer id) throws Exception;
	
	/**
	 * 根据部门ID查分类
	 * @param deptCode
	 * @return List<GodMaterialType>
	 * @throws Exception
	 */
	public List<GoodsMaterialType> loadTypeByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据条件查找实物类商品类型数量
	 * @param MaterialType
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(GoodsMaterialType MaterialType) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMaterialType> loadAll() throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsMaterialType materialType) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsMaterialType materialType) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsMaterialType materialType) throws Exception;
}
