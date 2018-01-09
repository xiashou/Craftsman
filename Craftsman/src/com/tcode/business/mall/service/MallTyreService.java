package com.tcode.business.mall.service;

import java.util.List;

import com.tcode.business.mall.model.MallTyre;


public interface MallTyreService {

	/**
	 * 根据车型查询匹配商品
	 * @return
	 * @throws Exception
	 */
	public List<MallTyre> getListByModel(String appId, Integer modelId) throws Exception;
	
	/**
	 * 根据系列查询所有型号关联
	 * @param appId
	 * @param seriesId
	 * @return
	 * @throws Exception
	 */
	public List<MallTyre> getListBySeries(String appId, Integer seriesId) throws Exception;
	
	/**
	 * 保存多个轮胎商品关系
	 * @return
	 * @throws Exception
	 */
	public Integer saveMoreMallTyre(String appId, Integer[] modelIds, String[] goodsIds) throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return MallTyre
	 * @throws Exception
	 */
	public MallTyre getById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @param MallTyre
	 * @throws Exception
	 */
	public void insert(MallTyre mallTyre) throws Exception;
	
	/**
	 * 修改
	 * @param MallTyre
	 * @throws Exception
	 */
	public void update(MallTyre mallTyre) throws Exception;
	
	/**
	 * 删除
	 * @param MallTyre
	 * @throws Exception
	 */
	public void delete(MallTyre mallTyre) throws Exception;
	
}
