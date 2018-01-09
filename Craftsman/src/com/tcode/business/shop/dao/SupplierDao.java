package com.tcode.business.shop.dao;

import java.util.List;

import com.tcode.business.shop.model.Supplier;

public interface SupplierDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return Supplier
	 * @throws Exception
	 */
	public Supplier loadById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param id
	 * @return Supplier
	 * @throws Exception
	 */
	public List<Supplier> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 根据关键字查询供应商
	 * @param deptCode
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<Supplier> loadByKeyword(String deptCode, String keyword) throws Exception;
	
	/**
	 * 根据名称查找供应商
	 * @param deptCode
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Supplier loadByName(String deptCode, String name) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(Supplier supplier) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(Supplier supplier) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(Supplier supplier) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param supplier, start,limit
	 * @return List<Supplier>
	 * @throws Exception
	 */
	public List<Supplier> loadListPage(Supplier supplier, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param supplier
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(Supplier supplier) throws Exception;
}
