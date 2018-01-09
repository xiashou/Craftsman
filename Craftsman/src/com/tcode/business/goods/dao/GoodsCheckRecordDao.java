package com.tcode.business.goods.dao;

import com.tcode.business.goods.model.GoodsCheckRecord;

public interface GoodsCheckRecordDao {

	
	/**
	 * 添加
	 * @throws Exception
	 */
	public void save(GoodsCheckRecord checkRecord) throws Exception;
	
	/**
	 * 更新
	 * @throws Exception
	 */
	public void edit(GoodsCheckRecord checkRecord) throws Exception;
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void remove(GoodsCheckRecord checkRecord) throws Exception;
	
}
