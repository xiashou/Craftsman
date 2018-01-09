package com.tcode.business.shop.service;

import java.util.List;

import com.tcode.business.shop.model.Commission;
import com.tcode.business.shop.model.TypeCommission;

public interface CommissionService {

	/**
	 * 根据id查找
	 * @param goodsId, deptCode
	 * @return Commission
	 * @throws Exception
	 */
	public Commission getCommissionById(String goodsId, String deptCode) throws Exception;
	
	/**
	 * 根据类型查询门店所有工时提成设置
	 * @param deptCode
	 * @return Commission
	 * @throws Exception
	 */
	public List<Commission> getListHourByDept(String deptCode) throws Exception;
	
	/**
	 * 根据类型查询门店所有实物提成设置
	 * @param deptCode
	 * @return Commission
	 * @throws Exception
	 */
	public List<Commission> getListMateByDept(String deptCode, Integer typeId) throws Exception;
	
	/**
	 * 根据门店和等级删除等级价格信息
	 * @param deptCode
	 * @param type
	 * @throws Exception
	 */
	public void deleteByType(String deptCode, String type) throws Exception;
	
	/**
	 * 根据ID查询大类提成设置
	 * @param typeId
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public TypeCommission getTypeCommById(Integer typeId, String deptCode) throws Exception;
	
	/**
	 * 根据类型查询门店所有大类提成设置
	 * @param deptCode
	 * @return Commission
	 * @throws Exception
	 */
	public List<TypeCommission> getTypeCommByDept(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insert(Commission commission) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void update(Commission commission) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void delete(Commission commission) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insertType(TypeCommission typeComm) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void updateType(TypeCommission typeComm) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void deleteType(TypeCommission typeComm) throws Exception;
}
