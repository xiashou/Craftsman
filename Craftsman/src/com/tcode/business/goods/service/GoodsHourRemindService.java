package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsHourRemind;

public interface GoodsHourRemindService {

	/**
	 * 查询门店所有工时提醒
	 * @return
	 * @throws Exception
	 */
	public List<GoodsHourRemind> getHourRemindByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return GoodsHourRemind
	 * @throws Exception
	 */
	public GoodsHourRemind getHourRemindById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @param GoodsHourRemind
	 * @throws Exception
	 */
	public void insert(GoodsHourRemind hourRemind) throws Exception;
	
	/**
	 * 修改
	 * @param GoodsHourRemind
	 * @throws Exception
	 */
	public void update(GoodsHourRemind hourRemind) throws Exception;
	
	/**
	 * 删除
	 * @param GoodsHourRemind
	 * @throws Exception
	 */
	public void delete(GoodsHourRemind hourRemind) throws Exception;
	
}
