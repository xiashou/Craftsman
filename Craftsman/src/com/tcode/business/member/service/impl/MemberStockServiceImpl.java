package com.tcode.business.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.goods.dao.GoodsHourDao;
import com.tcode.business.goods.dao.GoodsMaterialDao;
import com.tcode.business.goods.dao.GoodsPackageDao;
import com.tcode.business.goods.dao.GoodsStockDao;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.goods.model.GoodsPackage;
import com.tcode.business.member.dao.MemberStockDao;
import com.tcode.business.member.model.MemberStock;
import com.tcode.business.member.service.MemberStockService;
import com.tcode.business.report.dao.ReptMemberStockDao;
import com.tcode.core.util.Constant;
import com.tcode.core.util.Utils;

@Component("memberStockService")
public class MemberStockServiceImpl implements MemberStockService {
	
	private MemberStockDao memberStockDao;
	private GoodsStockDao goodsStockDao;
	private GoodsPackageDao goodsPackageDao;
	private GoodsHourDao goodsHourDao;
	private GoodsMaterialDao goodsMaterialDao;
	private ReptMemberStockDao reptMemberStockDao;

	@Override
	public MemberStock getById(Integer memId, String goodsId, String endDate, String source) throws Exception {
		return memberStockDao.loadById(memId, goodsId, endDate, source);
	}

	/**
	 * 查询会员库存并循环查询出类别名称与来源名称
	 */
	@Override
	public List<MemberStock> getListByMemId(Integer memId, String deptCode) throws Exception {
		List<MemberStock> list = memberStockDao.loadByMemId(memId);
		if(!Utils.isEmpty(list)){
			for(MemberStock stock : list) {
				if(stock.getGoodsId().substring(0, 1).equals("3")){
					GoodsMaterial goods = goodsMaterialDao.loadById(stock.getGoodsId());
					if(!Utils.isEmpty(goods))
						stock.setUnit(Utils.isEmpty(goods.getUnit()) ? "个" : goods.getUnit());
				} else
					stock.setUnit("次");
			}
		}
		return list;
	}
	
	@Override
	public List<MemberStock> getListByDept(String deptCode, Integer memId) throws Exception {
		return memberStockDao.loadListByDept(deptCode, memId);
	}
	
	@Override
	public Integer getListCountByDept(String deptCode, Integer memId) throws Exception {
		return memberStockDao.loadListCountByDept(deptCode, memId);
	}
	
	/**
	 * 查询会员库存
	 */
	@Override
	public List<MemberStock> getListDetailByMemId(Integer memId) throws Exception {
		List<MemberStock> list = memberStockDao.loadDetailByMemId(memId);
		for(MemberStock stock : list) {
			if(!Utils.isEmpty(stock.getSource())){
				if("2".equals(stock.getSource()))
					stock.setSourceName("补发");
				else {
					GoodsPackage pack = goodsPackageDao.loadById(stock.getSource());
					if(!Utils.isEmpty(pack))
						stock.setSourceName(pack.getName());
				}
			} else
				stock.setSourceName("赠送");
		}
		return list;
	}

	/**
	 * 更新会员库存，先检查是否存在，存在则修改不存在则添加，修改可正数加负数减
	 */
	@Override
	public void updateStockNumber(MemberStock memberStock) throws Exception {
		MemberStock exist = memberStockDao.loadById(memberStock.getMemId(), memberStock.getGoodsId(), 
				(Utils.isEmpty(memberStock.getEndDate()) ? Constant.END_DATE : memberStock.getEndDate()), memberStock.getSource());
		if(!Utils.isEmpty(exist)){
			memberStock.setEndDate(Utils.isEmpty(memberStock.getEndDate()) ? Constant.END_DATE : memberStock.getEndDate());
			memberStockDao.editStockNumber(memberStock.getMemId(), memberStock.getGoodsId(), memberStock.getEndDate(), memberStock.getNumber(), memberStock.getSource());
		} else 
			this.insert(memberStock);
		//记录一笔会员库存流水
		reptMemberStockDao.addRecord(memberStock.getDeptCode(), memberStock.getMemId(), memberStock.getSource(), memberStock.getGoodsId(), memberStock.getGoodsName(), memberStock.getNumber());
	}
	
	@Override
	public void updateStockByProc(MemberStock memberStock) throws Exception {
		memberStockDao.editStockByProc(memberStock);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void updateMoreStockByProc(List<MemberStock> msList) throws Exception {
		if(!Utils.isEmpty(msList)){
			for(MemberStock memberStock : msList){
				memberStockDao.editStockByProc(memberStock);
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void updateMoreStockNumber(List<MemberStock> msList) throws Exception {
		if(!Utils.isEmpty(msList)){
			for(MemberStock memberStock : msList){
				//实物类商品扣减库存，销售时不减库存，会员使用时再减
//				if(memberStock.getGoodsType() == 2 || memberStock.getGoodsType() == 4)
//					goodsStockDao.editByGoodsId(memberStock.getDeptCode(), memberStock.getGoodsId(), memberStock.getNumber());
				//会员增加库存
				this.updateStockNumber(memberStock);
			}
		}
	}

	@Override
	public void insert(MemberStock memberStock) throws Exception {
		if(Utils.isEmpty(memberStock.getEndDate()))
			memberStock.setEndDate(Constant.END_DATE);
		memberStockDao.save(memberStock);
	}
	@Override
	public void update(MemberStock memberStock) throws Exception {
		memberStockDao.edit(memberStock);
	}
	@Override
	public void delete(MemberStock memberStock) throws Exception {
		memberStockDao.remove(memberStock);
	}
	
	@Override
	public double getGoodsNum(Integer memId, String goodsId) throws Exception {
		return memberStockDao.loadGoodsNum(memId, goodsId);
	}
	
	public MemberStockDao getMemberStockDao() {
		return memberStockDao;
	}
	@Resource
	public void setMemberStockDao(MemberStockDao memberStockDao) {
		this.memberStockDao = memberStockDao;
	}
	public GoodsStockDao getGoodsStockDao() {
		return goodsStockDao;
	}
	@Resource
	public void setGoodsStockDao(GoodsStockDao goodsStockDao) {
		this.goodsStockDao = goodsStockDao;
	}

	public GoodsPackageDao getGoodsPackageDao() {
		return goodsPackageDao;
	}
	@Resource
	public void setGoodsPackageDao(GoodsPackageDao goodsPackageDao) {
		this.goodsPackageDao = goodsPackageDao;
	}

	public GoodsHourDao getGoodsHourDao() {
		return goodsHourDao;
	}
	@Resource
	public void setGoodsHourDao(GoodsHourDao goodsHourDao) {
		this.goodsHourDao = goodsHourDao;
	}

	public GoodsMaterialDao getGoodsMaterialDao() {
		return goodsMaterialDao;
	}
	@Resource
	public void setGoodsMaterialDao(GoodsMaterialDao goodsMaterialDao) {
		this.goodsMaterialDao = goodsMaterialDao;
	}

	public ReptMemberStockDao getReptMemberStockDao() {
		return reptMemberStockDao;
	}
	@Resource
	public void setReptMemberStockDao(ReptMemberStockDao reptMemberStockDao) {
		this.reptMemberStockDao = reptMemberStockDao;
	}

}
