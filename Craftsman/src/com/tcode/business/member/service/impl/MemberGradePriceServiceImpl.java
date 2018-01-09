package com.tcode.business.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.goods.dao.GoodsHourDao;
import com.tcode.business.goods.dao.GoodsHourTypeDao;
import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.member.dao.MemberGradeDao;
import com.tcode.business.member.dao.MemberGradePriceDao;
import com.tcode.business.member.model.MemberGrade;
import com.tcode.business.member.model.MemberGradePrice;
import com.tcode.business.member.service.MemberGradePriceService;
import com.tcode.core.util.Utils;

@Component("gradePriceService")
public class MemberGradePriceServiceImpl implements MemberGradePriceService {
	
	private MemberGradePriceDao gradePriceDao;
	private MemberGradeDao memberGradeDao;
	private GoodsHourDao goodsHourDao;
	private GoodsHourTypeDao goodsHourTypeDao;
	
	
	@Override
	public MemberGradePrice getGradePriceById(String goodsId, Integer gradeId, String deptCode) throws Exception {
		return gradePriceDao.loadById(goodsId, gradeId, deptCode);
	}

	@Override
	public List<MemberGradePrice> getListByDeptCode(String companyId, String deptCode, Integer gradeId) throws Exception {
		List<GoodsHour> gList = goodsHourDao.loadAll(deptCode);
		MemberGrade grade = memberGradeDao.loadByGrade(companyId, gradeId);
		List<MemberGradePrice> list = new ArrayList<MemberGradePrice>();
		MemberGradePrice gradePrice = null;
		if(!Utils.isEmpty(gList)){
			for(GoodsHour goodsHour : gList){
				gradePrice = gradePriceDao.loadById(goodsHour.getId(), gradeId, companyId);
				if(Utils.isEmpty(gradePrice)){
					gradePrice = new MemberGradePrice();
					gradePrice.setDeptCode(companyId);
					gradePrice.setGoodsId(goodsHour.getId());
					gradePrice.setGradeId(gradeId);
					gradePrice.setPrice(goodsHour.getPrice());
					gradePrice.setMprice((grade.getDiscount() == 0 ? 10 : grade.getDiscount()) * (Utils.isEmpty(goodsHour.getPrice()) ? 0 : goodsHour.getPrice()) / 10);
					gradePrice.setOprice((grade.getOrderDiscount() == 0 ? 10 : grade.getOrderDiscount()) * (Utils.isEmpty(goodsHour.getPrice()) ? 0 : goodsHour.getPrice()) / 10);
				}
				gradePrice.setGoodsName(goodsHour.getName());
				gradePrice.setGoodsType(goodsHourTypeDao.loadById(goodsHour.getTypeId()).getTypeName());
				gradePrice.setPrice(goodsHour.getPrice());
				
				list.add(gradePrice);
			}
		}
		return list;
	}
	
	public void deleteByDeptGrade(String deptCode, Integer grade) throws Exception {
		gradePriceDao.removeByDeptGrade(deptCode, grade);
	}

	@Override
	public void insert(MemberGradePrice gradePrice) throws Exception {
		gradePriceDao.save(gradePrice);
	}

	@Override
	public void update(MemberGradePrice gradePrice) throws Exception {
		gradePriceDao.edit(gradePrice);
	}

	@Override
	public void delete(MemberGradePrice gradePrice) throws Exception {
		gradePriceDao.remove(gradePrice);
	}
	
	
	

	public MemberGradePriceDao getGradePriceDao() {
		return gradePriceDao;
	}
	@Resource
	public void setGradePriceDao(MemberGradePriceDao gradePriceDao) {
		this.gradePriceDao = gradePriceDao;
	}
	public GoodsHourDao getGoodsHourDao() {
		return goodsHourDao;
	}
	@Resource
	public void setGoodsHourDao(GoodsHourDao goodsHourDao) {
		this.goodsHourDao = goodsHourDao;
	}
	public GoodsHourTypeDao getGoodsHourTypeDao() {
		return goodsHourTypeDao;
	}
	@Resource
	public void setGoodsHourTypeDao(GoodsHourTypeDao goodsHourTypeDao) {
		this.goodsHourTypeDao = goodsHourTypeDao;
	}
	public MemberGradeDao getMemberGradeDao() {
		return memberGradeDao;
	}
	@Resource
	public void setMemberGradeDao(MemberGradeDao memberGradeDao) {
		this.memberGradeDao = memberGradeDao;
	}

}
