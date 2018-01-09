package com.tcode.business.finance.dao;

import java.util.List;

import com.tcode.business.finance.model.Exptype;

public interface ExptypeDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<Exptype> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return Exptype
	 * @throws Exception
	 */
	public Exptype loadById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param id
	 * @return Exptype
	 * @throws Exception
	 */
	public List<Exptype> loadByDept(String deptCode) throws Exception ;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(Exptype exptype) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(Exptype exptype) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(Exptype exptype) throws Exception;
	
}
