package com.tcode.business.shop.dao;

import java.util.List;

import com.tcode.business.shop.model.Commission;

public interface CommissionDao {

	/**
	 * 根据id查找
	 * @param goodsId, deptCode
	 * @return Commission
	 * @throws Exception
	 */
	public Commission loadById(String goodsId, String deptCode) throws Exception;
	
	/**
	 * 根据类型查询门店所有提成设置
	 * @param deptCode
	 * @return Commission
	 * @throws Exception
	 */
	public List<Commission> loadByDeptType(String deptCode, String type) throws Exception;
	
	/**
	 * 根据门店和等级删除等级价格信息
	 * @param deptCode
	 * @param type
	 * @throws Exception
	 */
	public void removeByType(String deptCode, String type) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(Commission commission) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(Commission commission) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(Commission commission) throws Exception;
}
