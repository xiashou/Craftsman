package com.tcode.business.finance.service;

import java.util.List;

import com.tcode.business.finance.model.Expenditure;

public interface ExpenditureService {

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Expenditure getById(Integer id) throws Exception;
	
	/**
	 * 查询店铺所有支出记录
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Expenditure> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 分页查询店铺所有支出记录
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public List<Expenditure> getListByDeptPage(Expenditure expend, Integer start, Integer limit) throws Exception;
	
	/**
	 * 查询店铺所有支出记录总数
	 * @param deptCode
	 * @return
	 * @throws Exception
	 */
	public Integer getCountByDept(Expenditure expend) throws Exception;
	
	/**
	 * 添加
	 * @param expend
	 * @throws Exception
	 */
	public void insert(Expenditure expend) throws Exception;
	
	/**
	 * 修改
	 * @param expend
	 * @throws Exception
	 */
	public void update(Expenditure expend) throws Exception;
	
	/**
	 * 删除
	 * @param expend
	 * @throws Exception
	 */
	public void delete(Expenditure expend) throws Exception;
}
