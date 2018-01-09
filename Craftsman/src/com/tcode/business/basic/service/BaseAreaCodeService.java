package com.tcode.business.basic.service;

import java.util.List;

import com.tcode.business.basic.model.BaseAreaCode;

public interface BaseAreaCodeService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<BaseAreaCode> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return BaseAreaCode
	 * @throws Exception
	 */
	public BaseAreaCode getById(Integer id) throws Exception;
	
	/**
	 * 添加
	 * @param BaseAreaCode
	 * @throws Exception
	 */
	public void insert(BaseAreaCode areaCode) throws Exception;
	
	/**
	 * 修改
	 * @param BaseAreaCode
	 * @throws Exception
	 */
	public void update(BaseAreaCode areaCode) throws Exception;
	
	/**
	 * 删除
	 * @param BaseAreaCode
	 * @throws Exception
	 */
	public void delete(BaseAreaCode areaCode) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start,limit
	 * @return List<BaseAreaCode>
	 * @throws Exception
	 */
	public List<BaseAreaCode> getListPage(BaseAreaCode areaCode, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<BaseAreaCode>
	 * @throws Exception
	 */
	public Integer getListCount(BaseAreaCode areaCode) throws Exception;
}
