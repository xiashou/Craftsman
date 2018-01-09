package com.tcode.business.mall.service;

import java.util.List;

import com.tcode.business.mall.model.MallBrand;


public interface MallBrandService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MallBrand> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return MallBrand
	 * @throws Exception
	 */
	public MallBrand getById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @param MallBrand
	 * @throws Exception
	 */
	public void insert(MallBrand mallBrand) throws Exception;
	
	/**
	 * 修改
	 * @param MallBrand
	 * @throws Exception
	 */
	public void update(MallBrand mallBrand) throws Exception;
	
	/**
	 * 删除
	 * @param MallBrand
	 * @throws Exception
	 */
	public void delete(MallBrand mallBrand) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param mallBrand, start, limit
	 * @return List<MallBrand>
	 * @throws Exception
	 */
	public List<MallBrand> getListPage(MallBrand mallBrand, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param mallBrand
	 * @return List<MallBrand>
	 * @throws Exception
	 */
	public Integer getListCount(MallBrand mallBrand) throws Exception;
}
