package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsMaterialType;

public interface GoodsMaterialTypeService {
	
	/**
	 * 根据部门查分类
	 * @param deptCode
	 * @return List<GodmaterialType>
	 * @throws Exception
	 */
	public List<GoodsMaterialType> getTypeByDeptCode(String deptCode) throws Exception;

	/**
	 * 根据条件查找工时类商品类型数量
	 * @param materialType
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getListCount(GoodsMaterialType materialType) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<GoodsMaterialType> getAll() throws Exception;
	
	/**
	 * 添加
	 * @param materialType
	 * @throws Exception
	 */
	public void insert(GoodsMaterialType materialType) throws Exception;
	
	/**
	 * 修改
	 * @param materialType
	 * @throws Exception
	 */
	public void update(GoodsMaterialType materialType) throws Exception;
	
	/**
	 * 删除
	 * @param materialType
	 * @throws Exception
	 */
	public void delete(GoodsMaterialType materialType) throws Exception;
}
