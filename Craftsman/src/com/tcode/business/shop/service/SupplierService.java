package com.tcode.business.shop.service;

import java.util.List;

import com.tcode.business.shop.model.Supplier;

public interface SupplierService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return Supplier
	 * @throws Exception
	 */
	public Supplier getById(Integer id) throws Exception;
	
	/**
	 * 根据名称查找供应商
	 * @param deptCode
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Supplier getByName(String deptCode, String name) throws Exception;
	
	/**
	 * 根据门店查询所有
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Supplier> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 根据关键子查询供应商
	 * @param deptCode
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<Supplier> getListByKeyword(String deptCode, String keyword) throws Exception;
	
	/**
	 * 添加
	 * @param Supplier
	 * @throws Exception
	 */
	public void insert(Supplier supplier) throws Exception;
	
	/**
	 * 修改
	 * @param Supplier
	 * @throws Exception
	 */
	public void update(Supplier supplier) throws Exception;
	
	/**
	 * 删除
	 * @param Supplier
	 * @throws Exception
	 */
	public void delete(Supplier supplier) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param supplier, start, limit
	 * @return List<Supplier>
	 * @throws Exception
	 */
	public List<Supplier> getListPage(Supplier supplier, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param employee
	 * @throws Exception
	 */
	public Integer getListCount(Supplier supplier) throws Exception;
}
