package com.tcode.business.mall.service;

import java.util.List;

import com.tcode.business.mall.model.MallGoods;


public interface MallGoodsService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MallGoods> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param goodsId
	 * @return MallGoods
	 * @throws Exception
	 */
	public MallGoods getById(String goodsId) throws Exception;
	
	/**
	 * 所有商品根据类型排序字段排序
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<MallGoods> getAllListOrderType(String appId) throws Exception;
	
	/**
	 * 查询轮胎产品
	 * @param appId
	 * @param modelId
	 * @return
	 * @throws Exception
	 */
	public List<MallGoods> getListByTyre(String appId, Integer modelId) throws Exception;
	
	/**
	 * 查询保养产品
	 * @param appId
	 * @param modelId
	 * @return
	 * @throws Exception
	 */
	public List<MallGoods> getListByMaintain(String appId, Integer modelId) throws Exception;
	
	/**
	 * 添加多条
	 * @param MallGoods
	 * @throws Exception
	 */
	public Integer insertMore(List<MallGoods> goodsList) throws Exception;
	
	/**
	 * 添加
	 * @param MallGoods
	 * @throws Exception
	 */
	public void insert(MallGoods goods) throws Exception;
	
	/**
	 * 修改
	 * @param MallGoods
	 * @throws Exception
	 */
	public void update(MallGoods goods) throws Exception;
	
	/**
	 * 删除
	 * @param MallGoods
	 * @throws Exception
	 */
	public void delete(MallGoods goods) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param goods, start, limit
	 * @return List<MallGoods>
	 * @throws Exception
	 */
	public List<MallGoods> getListPage(MallGoods goods, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param goods
	 * @return List<MallGoods>
	 * @throws Exception
	 */
	public Integer getListCount(MallGoods goods) throws Exception;
	
	/**
	 * 查询热销商品
	 * @param goods
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public List<MallGoods> getHotList(String appId, int limit) throws Exception;
	
	/**
	 * 根据类型查询商品
	 * @param appId
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public List<MallGoods> getListByType(String appId, Integer typeId) throws Exception;
}
