package com.tcode.business.shop.service;

import java.util.List;

import com.tcode.business.order.model.OrderItem;
import com.tcode.business.shop.model.Employee;

public interface EmployeeService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return Employee
	 * @throws Exception
	 */
	public Employee getById(Integer id) throws Exception;
	
	/**
	 * 根据门店查询所有员工和组
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Employee> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 根据是否为组查找
	 * @param deptCode
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public List<Employee> getListByGroup(String deptCode, Boolean group) throws Exception;
	
	/**
	 * 计算员工提成
	 * @param deptCode
	 * @param group
	 * @param orderItem
	 * @return
	 * @throws Exception
	 */
	public List<Employee> getCommissionList(OrderItem orderItem) throws Exception;
	
	/**
	 * 根据传入的id字符串查询出姓名字符串
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public String getNamesByIds(String ids) throws Exception;
	
	/**
	 * 添加
	 * @param Employee
	 * @throws Exception
	 */
	public void insert(Employee employee) throws Exception;
	
	/**
	 * 修改
	 * @param Employee
	 * @throws Exception
	 */
	public void update(Employee employee) throws Exception;
	
	/**
	 * 删除
	 * @param Employee
	 * @throws Exception
	 */
	public void delete(Employee employee) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param employee, start, limit
	 * @return List<Employee>
	 * @throws Exception
	 */
	public List<Employee> getListPage(Employee employee, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param employee
	 * @return List<Employee>
	 * @throws Exception
	 */
	public Integer getListCount(Employee employee) throws Exception;
}
