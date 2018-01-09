package com.tcode.business.shop.dao;

import java.util.List;

import com.tcode.business.shop.model.Employee;

public interface EmployeeDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Employee> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return Employee
	 * @throws Exception
	 */
	public Employee loadById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param id
	 * @return Employee
	 * @throws Exception
	 */
	public List<Employee> loadByDept(String deptCode) throws Exception ;
	
	/**
	 * 根据是否为组查找
	 * @param id
	 * @return Employee
	 * @throws Exception
	 */
	public List<Employee> loadByGroup(String deptCode, Boolean group) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(Employee employee) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(Employee employee) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(Employee employee) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param employee, start,limit
	 * @return List<Employee>
	 * @throws Exception
	 */
	public List<Employee> loadListPage(Employee employee, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param employee
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(Employee employee) throws Exception;
}
