package com.tcode.business.report.service;

import java.util.List;

import com.tcode.business.report.model.ReptGoods;

public interface ReptGoodsService {

	/**
	 * 根据id查找
	 * @param id
	 * @return ReptGoods
	 * @throws Exception
	 */
	public ReptGoods getReptGoodsById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param deptCode
	 * @return List<ReptGoods>
	 * @throws Exception
	 */
	public List<ReptGoods> getListByDept(String deptCode) throws Exception;
	
	/**
	 * 根据商品ID查找
	 * @param goodsId
	 * @return List<ReptGoods>
	 * @throws Exception
	 */
	public List<ReptGoods> getListByGoodsId(String goodsId) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param recharge, start, limit
	 * @return List<ReptGoods>
	 * @throws Exception
	 */
	public List<ReptGoods> getListPage(ReptGoods reptGoods, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param ReptGoods
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getListCount(ReptGoods reptGoods) throws Exception;
	
	/**
	 * 添加一条记录
	 * @throws Exception
	 */
	public void insertRecord(String deptCode, String orderNo, String type, String goodsId, String goodsName, Double number) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void insert(ReptGoods reptGoods) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void update(ReptGoods reptGoods) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void delete(ReptGoods reptGoods) throws Exception;

}
