package com.tcode.business.finance.dao;

import java.util.List;

import com.tcode.business.finance.model.Expenditure;

public interface ExpenditureDao {

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Expenditure loadById(Integer id) throws Exception;
	
	/**
	 * 查询店铺所有支出记录
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Expenditure> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 分页查询店铺所有支出记录
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Expenditure> loadByDeptPage(Expenditure expend, Integer start, Integer limit) throws Exception;
	
	/**
	 * 查询店铺所有支出记录总数
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public Integer loadByDeptCount(Expenditure expend) throws Exception;
	
	/**
	 * 添加
	 * @param expend
	 * @throws Exception
	 */
	public void save(Expenditure expend) throws Exception;
	
	/**
	 * 修改
	 * @param expend
	 * @throws Exception
	 */
	public void edit(Expenditure expend) throws Exception;
	
	/**
	 * 删除
	 * @param expend
	 * @throws Exception
	 */
	public void remove(Expenditure expend) throws Exception;
}
