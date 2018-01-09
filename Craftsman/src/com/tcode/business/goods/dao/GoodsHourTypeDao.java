package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsHourType;

public interface GoodsHourTypeDao {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public GoodsHourType loadById(Integer id) throws Exception;
	
	/**
	 * 根据部门ID查分类
	 * @param deptCode
	 * @return List<GodHourType>
	 * @throws Exception
	 */
	public List<GoodsHourType> loadTypeByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据名称查分类
	 * @param deptCode, typeName
	 * @return GodHourType
	 * @throws Exception
	 */
	public GoodsHourType loadTypeByName(String deptCode, String typeName) throws Exception;
	
	/**
	 * 根据条件查找工时类商品类型数量
	 * @param hourType
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(GoodsHourType hourType) throws Exception;
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<GoodsHourType> loadAll() throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsHourType hourType) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsHourType hourType) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsHourType hourType) throws Exception;
}
