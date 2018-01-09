package com.tcode.business.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsHourDao;
import com.tcode.business.goods.dao.GoodsMaterialDao;
import com.tcode.business.goods.dao.GoodsPackageDetailDao;
import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.goods.model.GoodsPackageDetail;
import com.tcode.business.goods.service.GoodsPackageDetailService;

@Component("goodsPackageDetailService")
public class GoodsPackageDetailServiceImpl implements GoodsPackageDetailService{
	
	private GoodsPackageDetailDao goodsPackageDetailDao;
	private GoodsMaterialDao goodsMaterialDao;
	private GoodsHourDao goodsHourDao;

	@Override
	public List<GoodsPackageDetail> getGoodsPackageDetailById(String packageId) throws Exception {
		List<GoodsPackageDetail> list = goodsPackageDetailDao.loadAll(packageId);
//		for(GoodsPackageDetail goodsPackageDetail : list){
//			GoodsMaterial material = goodsMaterialDao.loadById(goodsPackageDetail.getGoodsId());
//			if(material != null){
//				goodsPackageDetail.setName(material.getName());
//			}
//			GoodsHour hour = goodsHourDao.loadById(goodsPackageDetail.getGoodsId());
//			if(hour != null){
//				goodsPackageDetail.setName(hour.getName());
//			}
//		}
		return list;
	}

	
	@Override
	public GoodsPackageDetail getPackageDetailById(String id, Integer itemNo) throws Exception {
		return goodsPackageDetailDao.loadById(id, itemNo);
	}


	@Override
	public void insert(GoodsPackageDetail goodsPackageDetail) throws Exception {
		goodsPackageDetailDao.save(goodsPackageDetail);
	}

	@Override
	public void update(GoodsPackageDetail goodsPackageDetail) throws Exception {
		goodsPackageDetailDao.edit(goodsPackageDetail);
	}

	@Override
	public void delete(GoodsPackageDetail goodsPackageDetail) throws Exception {
		goodsPackageDetailDao.remove(goodsPackageDetail);
	}

	@Override
	public void deleteById(GoodsPackageDetail goodsPackageDetail) throws Exception {
		goodsPackageDetailDao.removeById(goodsPackageDetail);
	}

	@Override
	public List<GoodsPackageDetail> getListPage(GoodsPackageDetail goodsPackageDetail, int start, int limit)
			throws Exception {
		return goodsPackageDetailDao.loadListPage(goodsPackageDetail, start, limit);
	}

	@Override
	public Integer getListCount(GoodsPackageDetail goodsPackageDetail) throws Exception {
		return goodsPackageDetailDao.loadListCount(goodsPackageDetail);
	}

	
	@Override
	public List<GoodsHour> getGoodsByKeyword(String deptCode, String companyId, String keyword) throws Exception {
		List<GoodsHour> list = goodsHourDao.loadByKeyword(deptCode, keyword);
		List<GoodsMaterial> materialList = goodsMaterialDao.loadByKeyword(companyId, keyword);
		for(GoodsMaterial material : materialList){
			GoodsHour goodsHour = new GoodsHour();
			goodsHour.setId(material.getId());
			goodsHour.setTypeId(material.getTypeId());
			goodsHour.setName(material.getName());
			goodsHour.setSuitModel(material.getSuitModel());
			list.add(goodsHour);
		}
		return list;
	}


	public GoodsPackageDetailDao getGoodsPackageDetailDao() {
		return goodsPackageDetailDao;
	}
	@Resource
	public void setGoodsPackageDetailDao(GoodsPackageDetailDao goodsPackageDetailDao) {
		this.goodsPackageDetailDao = goodsPackageDetailDao;
	}
	public GoodsMaterialDao getGoodsMaterialDao() {
		return goodsMaterialDao;
	}
	@Resource
	public void setGoodsMaterialDao(GoodsMaterialDao goodsMaterialDao) {
		this.goodsMaterialDao = goodsMaterialDao;
	}
	public GoodsHourDao getGoodsHourDao() {
		return goodsHourDao;
	}
	@Resource
	public void setGoodsHourDao(GoodsHourDao goodsHourDao) {
		this.goodsHourDao = goodsHourDao;
	}
}

