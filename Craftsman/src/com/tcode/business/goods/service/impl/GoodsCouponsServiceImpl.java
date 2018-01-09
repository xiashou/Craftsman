package com.tcode.business.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsCouponsDao;
import com.tcode.business.goods.dao.GoodsHourDao;
import com.tcode.business.goods.model.GoodsCoupons;
import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.service.GoodsCouponsService;
import com.tcode.core.util.Utils;

@Component("goodsCouponsService")
public class GoodsCouponsServiceImpl implements GoodsCouponsService {
	
	private GoodsCouponsDao goodsCouponsDao;
	private GoodsHourDao goodsHourDao;

	@Override
	public GoodsCoupons getCouponsById(String id) throws Exception {
		return goodsCouponsDao.loadById(id);
	}

	@Override
	public List<GoodsCoupons> getListByDept(String deptCode) throws Exception {
		List<GoodsCoupons> list = goodsCouponsDao.loadByDept(deptCode);
		for(GoodsCoupons coupons : list) {
			String goodsName = "";
			if(!Utils.isEmpty(coupons.getApplyId())) {
				if(coupons.getApplyId().indexOf(",") > 0){
					String[] ids = coupons.getApplyId().split(",");
					for(int i = 0; i < ids.length; i++){
						if(!Utils.isEmpty(ids[i])){
							GoodsHour goodsHour = goodsHourDao.loadById(ids[i].trim());
							if(!Utils.isEmpty(goodsHour))
								goodsName += goodsHour.getName() + ",";
							else
								goodsName += ",";
						}
					}
				} else {
					GoodsHour goodsHour = goodsHourDao.loadById(coupons.getApplyId());
					if(!Utils.isEmpty(goodsHour))
						goodsName = goodsHour.getName();
					else
						goodsName = coupons.getApplyId();
				}
			}
			coupons.setGoodsName(goodsName);
		}
		return list;
	}

	@Override
	public void insert(GoodsCoupons coupons) throws Exception {
		goodsCouponsDao.save(coupons);
	}

	@Override
	public void update(GoodsCoupons coupons) throws Exception {
		goodsCouponsDao.edit(coupons);
	}

	@Override
	public void delete(GoodsCoupons coupons) throws Exception {
		goodsCouponsDao.remove(coupons);
	}

	public GoodsCouponsDao getGoodsCouponsDao() {
		return goodsCouponsDao;
	}
	@Resource
	public void setGoodsCouponsDao(GoodsCouponsDao goodsCouponsDao) {
		this.goodsCouponsDao = goodsCouponsDao;
	}

	public GoodsHourDao getGoodsHourDao() {
		return goodsHourDao;
	}
	@Resource
	public void setGoodsHourDao(GoodsHourDao goodsHourDao) {
		this.goodsHourDao = goodsHourDao;
	}

}
