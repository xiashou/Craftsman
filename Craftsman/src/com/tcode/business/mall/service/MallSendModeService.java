package com.tcode.business.mall.service;

import java.util.List;

import com.tcode.business.mall.model.MallSendMode;


public interface MallSendModeService {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MallSendMode> getAll() throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return MallSendMode
	 * @throws Exception
	 */
	public MallSendMode getById(Integer id) throws Exception;
	
	/**
	 * 查询公众号所有配送方式
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<MallSendMode> getListByAppId(String appId) throws Exception;
	
	/**
	 * 添加
	 * @param MallSendMode
	 * @throws Exception
	 */
	public void insert(MallSendMode sendMode) throws Exception;
	
	/**
	 * 修改
	 * @param MallSendMode
	 * @throws Exception
	 */
	public void update(MallSendMode sendMode) throws Exception;
	
	/**
	 * 删除
	 * @param MallSendMode
	 * @throws Exception
	 */
	public void delete(MallSendMode sendMode) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param sendMode, start, limit
	 * @return List<MallSendMode>
	 * @throws Exception
	 */
	public List<MallSendMode> getListPage(MallSendMode sendMode, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param sendMode
	 * @return List<MallSendMode>
	 * @throws Exception
	 */
	public Integer getListCount(MallSendMode sendMode) throws Exception;
}
