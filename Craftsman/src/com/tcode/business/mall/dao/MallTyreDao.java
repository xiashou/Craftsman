package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallTyre;


public interface MallTyreDao {

	/**
	 * 根据车型查询
	 * @return
	 * @throws Exception
	 */
	public List<MallTyre> loadListByModel(String appId, Integer modelId) throws Exception;
	
	/**
	 * 查询唯一值
	 * @param appId
	 * @param modelId
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<MallTyre> loadByOnly(String appId, Integer modelId, String goodsId) throws Exception;
	
	/**
	 * 根据系列查询其下所有型号关系
	 * @param appId
	 * @param seriesId
	 * @return
	 * @throws Exception
	 */
	public List<MallTyre> loadListBySeries(String appId, Integer seriesId) throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return MallTyre
	 * @throws Exception
	 */
	public MallTyre loadById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallTyre mallTyre) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallTyre mallTyre) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallTyre mallTyre) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void removeByOnly(String appId, Integer[] modelIds) throws Exception;
	
}
