package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsHourRemind;

public interface GoodsHourRemindDao {
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<GoodsHourRemind> loadAll(String deptCode) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return GoodsHourRemind
	 * @throws Exception
	 */
	public GoodsHourRemind loadById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsHourRemind hourRemind) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsHourRemind hourRemind) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsHourRemind hourRemind) throws Exception;

}
