package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallMaintain;


public interface MallMaintainDao {

	/**
	 * 根据车型查询
	 * @return
	 * @throws Exception
	 */
	public List<MallMaintain> loadListByModel(String appId, Integer modelId) throws Exception;
	
	/**
	 * 查询唯一值
	 * @param appId
	 * @param modelId
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<MallMaintain> loadByOnly(String appId, Integer modelId, String goodsId) throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return MallMaintain
	 * @throws Exception
	 */
	public MallMaintain loadById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallMaintain maintain) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallMaintain maintain) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallMaintain maintain) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void removeByOnly(String appId, Integer[] modelIds) throws Exception;
}
