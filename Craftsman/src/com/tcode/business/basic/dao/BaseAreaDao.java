package com.tcode.business.basic.dao;

import java.util.List;

import com.tcode.business.basic.model.BaseArea;

public interface BaseAreaDao {
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseArea> loadAll() throws Exception;

	/**
	 * 查询所有省份
	 * @return
	 * @throws Exception
	 */
	public List<BaseArea> loadAllProvince() throws Exception;
	
	/**
	 * 根据ID查询城市
	 * @return
	 * @throws Exception
	 */
	public List<BaseArea> loadCityById(String areaId) throws Exception;
	
	/**
	 * 根据id查找
	 * @param areaId
	 * @return BaseArea
	 * @throws Exception
	 */
	public BaseArea loadById(String areaId) throws Exception;
	
	/**
	 * 根据id查找
	 * @param memId
	 * @return BaseArea
	 * @throws Exception
	 */
	public BaseArea loadByMemId(Integer memId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(BaseArea area) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(BaseArea area) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(BaseArea area) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<BaseArea>
	 * @throws Exception
	 */
	public List<BaseArea> loadListPage(BaseArea area, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param param
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(BaseArea area) throws Exception;
}
