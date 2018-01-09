package com.tcode.business.mall.dao;

import java.util.List;

import com.tcode.business.mall.model.MallSendMode;


public interface MallSendModeDao {

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<MallSendMode> loadAll() throws Exception;

	/**
	 * 根据id查找
	 * @param id
	 * @return MallSendMode
	 * @throws Exception
	 */
	public MallSendMode loadById(Integer id) throws Exception;

	/**
	 * 查询公众号所有配送方式
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<MallSendMode> loadByAppId(String appId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(MallSendMode sendMode) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(MallSendMode sendMode) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(MallSendMode sendMode) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param sendMode, start,limit
	 * @return List<MallSendMode>
	 * @throws Exception
	 */
	public List<MallSendMode> loadListPage(MallSendMode sendMode, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param sendMode
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(MallSendMode sendMode) throws Exception;
}
