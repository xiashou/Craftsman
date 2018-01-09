package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsHourType;

public interface GoodsHourTypeService {
	
	/**
	 * 根据部门查分类
	 * @param deptCode
	 * @return List<GodHourType>
	 * @throws Exception
	 */
	public List<GoodsHourType> getTypeByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据名称查类型
	 */
	public GoodsHourType getTypeByName(String deptCode, String typeName) throws Exception;

	/**
	 * 根据条件查找工时类商品类型数量
	 * @param hourType
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getListCount(GoodsHourType hourType) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<GoodsHourType> getAll() throws Exception;
	
	/**
	 * 添加
	 * @param hourType
	 * @throws Exception
	 */
	public void insert(GoodsHourType hourType) throws Exception;
	
	/**
	 * 修改
	 * @param hourType
	 * @throws Exception
	 */
	public void update(GoodsHourType hourType) throws Exception;
	
	/**
	 * 删除
	 * @param hourType
	 * @throws Exception
	 */
	public void delete(GoodsHourType hourType) throws Exception;
}
