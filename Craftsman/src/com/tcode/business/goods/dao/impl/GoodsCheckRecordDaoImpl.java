package com.tcode.business.goods.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsCheckRecordDao;
import com.tcode.business.goods.model.GoodsCheckRecord;
import com.tcode.common.dao.BaseDao;

@Component("goodsCheckRecordDao")
public class GoodsCheckRecordDaoImpl extends BaseDao<GoodsCheckRecord, Serializable> implements GoodsCheckRecordDao {


}
