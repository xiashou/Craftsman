package com.tcode.business.basic.service;

import java.util.List;

import com.tcode.business.basic.model.BaseArea;

public interface BaseAreaService {
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseArea> getAll() throws Exception;
	
	/**
	 * 查询所有省份信息
	 * @return
	 * @throws Exception
	 */
	public List<BaseArea> getAllProvince() throws Exception;
	
	/**
	 * 查询区域树
	 * @return
	 * @throws Exception
	 */
	public List<BaseArea> getBaseAreaTree() throws Exception;
	
	/**
	 * 查询代理区域树
	 * @return
	 * @throws Exception
	 */
	public List<BaseArea> getBaseAreaTreeByAgent(String areaId) throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return BaseArea
	 * @throws Exception
	 */
	public BaseArea getAreaById(String areaId) throws Exception;
	
	/**
	 * 根据会员ID取区域信息
	 * @param memId
	 * @return
	 * @throws Exception
	 */
	public BaseArea getAreaByMemId(Integer memId) throws Exception;
	
	/**
	 * 添加
	 * @param BaseArea
	 * @throws Exception
	 */
	public void insert(BaseArea area) throws Exception;
	
	/**
	 * 修改
	 * @param BaseArea
	 * @throws Exception
	 */
	public void update(BaseArea area) throws Exception;
	
	/**
	 * 删除
	 * @param BaseArea
	 * @throws Exception
	 */
	public void delete(BaseArea area) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start,limit
	 * @return List<BaseArea>
	 * @throws Exception
	 */
	public List<BaseArea> getListPage(BaseArea area, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<BaseArea>
	 * @throws Exception
	 */
	public Integer getListCount(BaseArea area) throws Exception;
}
