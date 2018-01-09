package com.tcode.business.mall.service;

import java.util.List;

import com.tcode.business.mall.model.MallGoodsType;


public interface MallGoodsTypeService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MallGoodsType> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return MallGoodsType
	 * @throws Exception
	 */
	public MallGoodsType getById(Integer id) throws Exception;
	
	/**
	 * 根据appId查询
	 * @return
	 * @throws Exception
	 */
	public List<MallGoodsType> getByAppId(String appId) throws Exception;
	
	/**
	 * 添加
	 * @param MallGoodsType
	 * @throws Exception
	 */
	public void insert(MallGoodsType goodsType) throws Exception;
	
	/**
	 * 修改
	 * @param MallGoodsType
	 * @throws Exception
	 */
	public void update(MallGoodsType goodsType) throws Exception;
	
	/**
	 * 删除
	 * @param MallGoodsType
	 * @throws Exception
	 */
	public void delete(MallGoodsType goodsType) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param mallBrand, start, limit
	 * @return List<MallGoodsType>
	 * @throws Exception
	 */
	public List<MallGoodsType> getListPage(MallGoodsType goodsType, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param mallBrand
	 * @return List<MallGoodsType>
	 * @throws Exception
	 */
	public Integer getListCount(MallGoodsType goodsType) throws Exception;
}
