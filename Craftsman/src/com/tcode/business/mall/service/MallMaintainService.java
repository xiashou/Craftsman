package com.tcode.business.mall.service;

import java.util.List;

import com.tcode.business.mall.model.MallMaintain;


public interface MallMaintainService {

	/**
	 * 根据车型查询匹配商品
	 * @return
	 * @throws Exception
	 */
	public List<MallMaintain> getListByModel(String appId, Integer modelId) throws Exception;
	
	/**
	 * 保存多个保养商品关系
	 * @return
	 * @throws Exception
	 */
	public Integer saveMoreMallMaintain(String appId, Integer[] modelIds, String[] goodsIds) throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return MallMaintain
	 * @throws Exception
	 */
	public MallMaintain getById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @param MallMaintain
	 * @throws Exception
	 */
	public void insert(MallMaintain maintain) throws Exception;
	
	/**
	 * 修改
	 * @param MallMaintain
	 * @throws Exception
	 */
	public void update(MallMaintain maintain) throws Exception;
	
	/**
	 * 删除
	 * @param MallMaintain
	 * @throws Exception
	 */
	public void delete(MallMaintain maintain) throws Exception;
	
}
