package com.tcode.business.shop.dao;

import java.util.List;

import com.tcode.business.shop.model.TypeCommission;

public interface TypeCommissionDao {

	/**
	 * 根据id查找
	 * @param typeId, deptCode
	 * @return TypeCommission
	 * @throws Exception
	 */
	public TypeCommission loadById(Integer typeId, String deptCode) throws Exception;
	
	/**
	 * 根据门店查找
	 * @param typeId, deptCode
	 * @return List<TypeCommission>
	 * @throws Exception
	 */
	public List<TypeCommission> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 根据门店删除提成信息
	 * @param deptCode
	 * @param type
	 * @throws Exception
	 */
	public void removeByDept(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(TypeCommission commission) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(TypeCommission commission) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(TypeCommission commission) throws Exception;
}
