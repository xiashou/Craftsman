package com.tcode.business.report.dao;

import java.util.List;

import com.tcode.business.report.model.ReptGoods;

public interface ReptGoodsDao {

	/**
	 * 根据id查找
	 * @param id
	 * @return ReptGoods
	 * @throws Exception
	 */
	public ReptGoods loadById(Integer id) throws Exception;
	
	/**
	 * 根据部门查询所有
	 * @param deptCode
	 * @return List<ReptGoods>
	 * @throws Exception
	 */
	public List<ReptGoods> loadByDept(String deptCode) throws Exception;
	
	/**
	 * 根据商品ID查找
	 * @param goodsId
	 * @return List<ReptGoods>
	 * @throws Exception
	 */
	public List<ReptGoods> loadByGoodsId(String goodsId) throws Exception;
	
	/**
	 * 添加一条记录
	 * @throws Exception
	 */
	public void addRecord(String deptCode, String orderNo, String type, String goodsId, String goodsName, Double number) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(ReptGoods reptGoods) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(ReptGoods reptGoods) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(ReptGoods reptGoods) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param ReptGoods, start, limit
	 * @return List<ReptGoods>
	 * @throws Exception
	 */
	public List<ReptGoods> loadListPage(ReptGoods reptGoods, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param ReptGoods
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(ReptGoods reptGoods) throws Exception;
	
	
}
