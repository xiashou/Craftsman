package com.tcode.business.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseCarBrandDao;
import com.tcode.business.basic.dao.BaseCarModelDao;
import com.tcode.business.basic.dao.BaseCarSeriesDao;
import com.tcode.business.basic.model.BaseCarBrand;
import com.tcode.business.member.dao.MemberCarDao;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.core.util.Utils;

@Component("memberCarService")
public class MemberCarServiceImpl implements MemberCarService {
	
	private MemberCarDao memberCarDao;
	private BaseCarBrandDao carBrandDao;
	private BaseCarSeriesDao carSeriesDao;
	private BaseCarModelDao carModelDao;
	
	@Override
	public MemberCar getMemberCarById(Integer id) throws Exception {
		MemberCar car = memberCarDao.loadById(id);
		if(!Utils.isEmpty(car) && !Utils.isEmpty(car.getCarBrand()))
			car.setBrandName(carBrandDao.loadById(car.getCarBrand()).getBrandName());
		return car;
	}
	
	@Override
	public List<MemberCar> getMemberCarByMemberId(Integer memberId) throws Exception {
		List<MemberCar> carList = memberCarDao.loadByMemberId(memberId);
		for(MemberCar car : carList){
			if(!Utils.isEmpty(car.getCarBrand())){
				BaseCarBrand brand = carBrandDao.loadById(car.getCarBrand());
				car.setShowName(car.getCarShort() + car.getCarCode() + car.getCarNumber() + "/" + brand.getBrandName() + "/" + car.getCarSeries());
				car.setBrandName(brand.getBrandName());
			} else
				car.setShowName(car.getCarShort() + car.getCarCode() + car.getCarNumber());
//			if(!Utils.isEmpty(car.getCarSeries()))
//				car.setSeriesName(carSeriesDao.loadById(car.getCarSeries()).getSeriesName());
//			if(!Utils.isEmpty(car.getCarModel()))
//				car.setModelName(carModelDao.loadById(car.getCarModel()).getModelName());
		}
		return carList; 
	}
	
	@Override
	public Integer getCarCountByDept(String deptCode) throws Exception {
		Integer count = memberCarDao.loadCarCountByDept(deptCode);
		return Utils.isEmpty(count) ? 0 : count;
	}
	
	@Override
	public MemberCar getCarByNumber(String companyId, String carShort, String code, String number) throws Exception {
		return memberCarDao.loadByCarNumber(companyId, carShort, code, number);
	}
	@Override
	public void insert(MemberCar car) throws Exception {
		if(!Utils.isEmpty(car) && !Utils.isEmpty(car.getCarNumber()))
			car.setCarNumber(car.getCarNumber().toUpperCase());
		memberCarDao.save(car);
	}
	@Override
	public void update(MemberCar car) throws Exception {
		if(!Utils.isEmpty(car) && !Utils.isEmpty(car.getCarNumber()))
			car.setCarNumber(car.getCarNumber().toUpperCase());
		memberCarDao.edit(car);
	}
	@Override
	public void delete(MemberCar car) throws Exception {
		memberCarDao.remove(car);
	}
	@Override
	public List<MemberCar> getListPage(MemberCar member, int start, int limit) throws Exception {
		return memberCarDao.loadListPage(member, start, limit);
	}
	@Override
	public Integer getListCount(MemberCar member) throws Exception {
		return memberCarDao.loadListCount(member);
	}
	
	@Override
	public List<MemberCar> getListByApartNextTime(int apartTime, int type) throws Exception {
		return memberCarDao.loadListByApartNextTime(apartTime, type);
	}
	
	
	
	
	
	
	
	public MemberCarDao getMemberCarDao() {
		return memberCarDao;
	}
	@Resource
	public void setMemberCarDao(MemberCarDao memberCarDao) {
		this.memberCarDao = memberCarDao;
	}
	public BaseCarBrandDao getCarBrandDao() {
		return carBrandDao;
	}
	@Resource
	public void setCarBrandDao(BaseCarBrandDao carBrandDao) {
		this.carBrandDao = carBrandDao;
	}

	public BaseCarSeriesDao getCarSeriesDao() {
		return carSeriesDao;
	}
	@Resource
	public void setCarSeriesDao(BaseCarSeriesDao carSeriesDao) {
		this.carSeriesDao = carSeriesDao;
	}

	public BaseCarModelDao getCarModelDao() {
		return carModelDao;
	}
	@Resource
	public void setCarModelDao(BaseCarModelDao carModelDao) {
		this.carModelDao = carModelDao;
	}
	
	

}
