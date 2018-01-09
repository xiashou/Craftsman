package com.tcode.business.finance.service;

import java.util.List;

import com.tcode.business.finance.model.Exptype;

public interface ExptypeService {

	/**
	 * 根据id查找
	 * @param id
	 * @return Exptype
	 * @throws Exception
	 */
	public Exptype getById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param id
	 * @return Exptype
	 * @throws Exception
	 */
	public List<Exptype> getListByDept(String deptCode) throws Exception ;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insert(Exptype exptype) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void update(Exptype exptype) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void delete(Exptype exptype) throws Exception;
}
