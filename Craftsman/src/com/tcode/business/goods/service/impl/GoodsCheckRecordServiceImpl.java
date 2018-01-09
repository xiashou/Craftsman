package com.tcode.business.goods.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsCheckRecordDao;
import com.tcode.business.goods.model.GoodsCheckRecord;
import com.tcode.business.goods.service.GoodsCheckRecordService;

@Component("goodsCheckRecordService")
public class GoodsCheckRecordServiceImpl implements GoodsCheckRecordService {

	private GoodsCheckRecordDao goodsCheckRecordDao;
	
	@Override
	public void insert(GoodsCheckRecord checkRecord) throws Exception {
		goodsCheckRecordDao.save(checkRecord);
	}

	@Override
	public void update(GoodsCheckRecord checkRecord) throws Exception {
		goodsCheckRecordDao.edit(checkRecord);
	}

	@Override
	public void delete(GoodsCheckRecord checkRecord) throws Exception {
		goodsCheckRecordDao.remove(checkRecord);
	}

	public GoodsCheckRecordDao getGoodsCheckRecordDao() {
		return goodsCheckRecordDao;
	}
	@Resource
	public void setGoodsCheckRecordDao(GoodsCheckRecordDao goodsCheckRecordDao) {
		this.goodsCheckRecordDao = goodsCheckRecordDao;
	}

	

	
	
}
