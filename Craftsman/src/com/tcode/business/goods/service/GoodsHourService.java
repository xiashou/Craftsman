package com.tcode.business.goods.service;

import java.util.List;

import com.tcode.business.goods.model.GoodsHour;

public interface GoodsHourService {

	/**
	 * 查询门店所有工时商品
	 * @return
	 * @throws Exception
	 */
	public List<GoodsHour> getGoodsHourByDeptCode(String deptCode) throws Exception;
	
	/**
	 * 根据ID查找
	 * @param id
	 * @return GoodHour
	 * @throws Exception
	 */
	public GoodsHour getGoodsHourById(String id) throws Exception;
	
	/**
	 * 根据会员卡号查找
	 * @param vipNo
	 * @return Member
	 * @throws Exception
	 */
	public List<GoodsHour> getGoodsHourByType(Integer typeId) throws Exception;
	
	/**
	 * 添加
	 * @param GoodsHour
	 * @throws Exception
	 */
	public void insert(GoodsHour goodHour) throws Exception;
	
	/**
	 * 修改
	 * @param GoodsHour
	 * @throws Exception
	 */
	public void update(GoodsHour goodHour) throws Exception;
	
	/**
	 * 删除
	 * @param GoodsHour
	 * @throws Exception
	 */
	public void delete(GoodsHour goodHour) throws Exception;
	
	/**
	 * 根据类型删除
	 * @param GoodsHour
	 * @throws Exception
	 */
	public void deleteByType(GoodsHour goodHour) throws Exception;
	
	/**
	 * 根据条件分页查找参数
	 * @param button, start, limit
	 * @return List<GoodHour>
	 * @throws Exception
	 */
	public List<GoodsHour> getListPage(GoodsHour goodHour, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找参数条目数
	 * @param button
	 * @return List<GoodHour>
	 * @throws Exception
	 */
	public Integer getListCount(GoodsHour goodHour) throws Exception;
}
