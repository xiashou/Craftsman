package com.tcode.business.shop.dao;

import java.util.List;

import com.tcode.business.shop.model.Recharge;

public interface RechargeDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return Recharge
	 * @throws Exception
	 */
	public Recharge loadById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param id
	 * @return Recharge
	 * @throws Exception
	 */
	public List<Recharge> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(Recharge recharge) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(Recharge recharge) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(Recharge recharge) throws Exception;
}
