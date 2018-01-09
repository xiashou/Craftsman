package com.tcode.business.finance.service;

import java.util.List;

import com.tcode.business.finance.model.Baddebt;

public interface BaddebtService {

	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Baddebt getById(Integer id) throws Exception;

	/**
	 * 根据部门查找
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Baddebt> getByDept(String deptCode) throws Exception;
	
	/**
	 * 分页查找
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Baddebt> getByDeptPage(Baddebt debt, Integer start, Integer limit) throws Exception;
	
	/**
	 * 根据分页查询总数
	 * @param debt
	 * @return
	 * @throws Exception
	 */
	public Integer getByDeptCount(Baddebt debt) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insert(Baddebt debt) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void update(Baddebt debt) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void delete(Baddebt debt) throws Exception;
}
