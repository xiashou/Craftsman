package com.tcode.business.finance.dao;

import java.util.List;

import com.tcode.business.finance.model.Baddebt;

public interface BaddebtDao {
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Baddebt loadById(Integer id) throws Exception;

	/**
	 * 根据部门查找
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Baddebt> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 分页查找
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Baddebt> loadByDeptPage(Baddebt debt, Integer start, Integer limit) throws Exception;
	
	/**
	 * 根据分页查询总数
	 * @param debt
	 * @return
	 * @throws Exception
	 */
	public Integer loadByDeptCount(Baddebt debt) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(Baddebt debt) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(Baddebt debt) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(Baddebt debt) throws Exception;
}
