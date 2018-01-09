package com.tcode.business.goods.service;

import com.tcode.business.goods.model.GoodsCheckRecord;

public interface GoodsCheckRecordService {

	/**
	 * 添加
	 * @param GoodsCheckRecord
	 * @throws Exception
	 */
	public void insert(GoodsCheckRecord checkRecord) throws Exception;
	
	/**
	 * 修改
	 * @param GoodsCheckRecord
	 * @throws Exception
	 */
	public void update(GoodsCheckRecord checkRecord) throws Exception;
	
	/**
	 * 删除
	 * @param GoodsCheckRecord
	 * @throws Exception
	 */
	public void delete(GoodsCheckRecord checkRecord) throws Exception;
	
}
