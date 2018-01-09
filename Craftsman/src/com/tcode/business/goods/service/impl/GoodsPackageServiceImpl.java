package com.tcode.business.goods.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.goods.dao.GoodsPackageDao;
import com.tcode.business.goods.dao.GoodsPackageDetailDao;
import com.tcode.business.goods.model.GoodsPackage;
import com.tcode.business.goods.model.GoodsPackageDetail;
import com.tcode.business.goods.service.GoodsPackageService;
import com.tcode.common.idgenerator.IDHelper;
import com.tcode.core.util.Utils;

@Component("goodsPackageService")
public class GoodsPackageServiceImpl implements GoodsPackageService {

	private GoodsPackageDao goodsPackageDao;
	private GoodsPackageDetailDao goodsPackageDetailDao;

	@Override
	public List<GoodsPackage> getGoodsPackageByDeptCode(String deptCode, String companyId) throws Exception {
		return goodsPackageDao.loadAll(deptCode, companyId);
	}

	@Override
	public GoodsPackage getGoodsPackageById(String id) throws Exception {
		return goodsPackageDao.loadById(id);
	}

	@Override
	public void insert(GoodsPackage goodsPackage) throws Exception {
		goodsPackageDao.save(goodsPackage);
	}

	@Override
	public void update(GoodsPackage goodsPackage) throws Exception {
		goodsPackageDao.edit(goodsPackage);
	}

	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void delete(GoodsPackage goodsPackage) throws Exception {
		//先删除套餐明细，在删套餐信息
		GoodsPackageDetail packageDetail = new GoodsPackageDetail();
		packageDetail.setGpId(goodsPackage.getId());
		goodsPackageDetailDao.removeById(packageDetail);
		goodsPackageDao.remove(goodsPackage);
	}

	@Override
	public List<GoodsPackage> getListPage(GoodsPackage goodsPackage, int start, int limit) throws Exception {
		return goodsPackageDao.loadListPage(goodsPackage, start, limit);
	}

	@Override
	public Integer getListCount(GoodsPackage goodsPackage) throws Exception {
		return goodsPackageDao.loadListCount(goodsPackage);
	}

	@Override
	public int saveGoodsPackage(GoodsPackage goodsPackage) throws Exception {
		//自定义结果（1：成功，2：重复，3：失败）
		int result;
		try {
			// 判断ID是否为空，为空则新增，不为空则修改
			if (Utils.isEmpty(goodsPackage.getId())) {
				//先根据套餐名称判断是否已存在
				GoodsPackage search = new GoodsPackage();
				search.setName(goodsPackage.getName());
				search.setDeptCode(goodsPackage.getDeptCode());
				int count = goodsPackageDao.loadListCount(search);
				if (count == 0) {
					goodsPackage.setId(IDHelper.getPackageId());
					goodsPackage.setStartDate(goodsPackage.getStartDate().substring(0, 10));
					goodsPackage.setEndDate(goodsPackage.getEndDate().substring(0, 10));
					goodsPackage.setCreateTime(Utils.getSysTime());
					goodsPackageDao.save(goodsPackage);
					result = 1;
				}else{
					result = 2;
				}
			} else {
				goodsPackage.setStartDate(goodsPackage.getStartDate().substring(0, 10));
				goodsPackage.setEndDate(goodsPackage.getEndDate().substring(0, 10));
				goodsPackageDao.edit(goodsPackage);
				result = 1;
			}
		} catch (Exception e) {
			result = 3;
			throw e;
		}
		return result;
	}
	
	
	public GoodsPackageDao getGoodsPackageDao() {
		return goodsPackageDao;
	}
	@Resource
	public void setGoodsPackageDao(GoodsPackageDao goodsPackageDao) {
		this.goodsPackageDao = goodsPackageDao;
	}
	
	public GoodsPackageDetailDao getGoodsPackageDetailDao() {
		return goodsPackageDetailDao;
	}
	@Resource
	public void setGoodsPackageDetailDao(GoodsPackageDetailDao goodsPackageDetailDao) {
		this.goodsPackageDetailDao = goodsPackageDetailDao;
	}
}
