package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallGoods;


public interface MallGoodsDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MallGoods> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return MallGoods
	 * @throws Exception
	 */
	public MallGoods loadById(String goodsId) throws Exception;
	
	/**
	 * 所有商品根据类型排序字段排序
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<MallGoods> loadAllListOrderType(String appId) throws Exception;
	
	/**
	 * 根据类型删除
	 * @param typeId
	 * @throws Exception
	 */
	public void deleteByType(Integer typeId) throws Exception;
	
	/**
	 * 查询轮胎产品
	 * @param appId
	 * @param modelId
	 * @return
	 * @throws Exception
	 */
	public List<MallGoods> loadListByTyre(String appId, Integer modelId) throws Exception;
	
	/**
	 * 查询保养产品
	 * @param appId
	 * @param modelId
	 * @return
	 * @throws Exception
	 */
	public List<MallGoods> loadListByMaintain(String appId, Integer modelId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallGoods goods) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallGoods goods) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallGoods goods) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param MallGoods, start, limit
	 * @return List<MallGoods>
	 * @throws Exception
	 */
	public List<MallGoods> loadListPage(MallGoods goods, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param MallGoods
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(MallGoods goods) throws Exception;
}
