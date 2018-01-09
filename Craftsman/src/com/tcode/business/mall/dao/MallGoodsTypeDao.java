package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallGoodsType;


public interface MallGoodsTypeDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MallGoodsType> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return MallGoodsType
	 * @throws Exception
	 */
	public MallGoodsType loadById(Integer id) throws Exception;
	
	/**
	 * 根据appId查找
	 * @param id
	 * @return List<MallGoodsType>
	 * @throws Exception
	 */
	public List<MallGoodsType> loadByAppId(String appId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallGoodsType goodsType) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallGoodsType goodsType) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallGoodsType goodsType) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param MallGoodsType, start,limit
	 * @return List<MallGoodsType>
	 * @throws Exception
	 */
	public List<MallGoodsType> loadListPage(MallGoodsType goodsType, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param MallGoodsType
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(MallGoodsType goodsType) throws Exception;
}
