package com.tcode.business.goods.dao;

import java.util.List;

import com.tcode.business.goods.model.GoodsHour;

public interface GoodsHourDao {
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<GoodsHour> loadAll(String deptCode) throws Exception;
	
	/**
	 * 根据id查找
	 * @param id
	 * @return GoodHour
	 * @throws Exception
	 */
	public GoodsHour loadById(String id) throws Exception;
	
	/**
	 * 根据类型查找
	 * @param typeId
	 * @return List<GoodHour>
	 * @throws Exception
	 */
	public List<GoodsHour> loadByType(Integer typeId) throws Exception;
	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsHour goodHour) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsHour goodHour) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsHour goodHour) throws Exception;
	
	/**
	 * 根据类型删除
	 * @throws Exception
	 */
	public void removeByType(GoodsHour goodHour) throws Exception;
	
	/**
	 * 根据条件分页查找
	 * @param param, start,limit
	 * @return List<GoodHour>
	 * @throws Exception
	 */
	public List<GoodsHour> loadListPage(GoodsHour goodHour, int start, int limit) throws Exception;
	
	/**
	 * 根据条件查找条目数
	 * @param GoodsHour
	 * @return Integer
	 * @throws Exception
	 */
	public Integer loadListCount(GoodsHour goodHour) throws Exception;
	
	/**
	 * 根据关键字查询商品
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<GoodsHour> loadByKeyword(String deptCode, String keyword) throws Exception;

}
