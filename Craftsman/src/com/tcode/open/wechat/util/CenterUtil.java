package com.tcode.open.wechat.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.basic.model.BaseArea;
import com.tcode.business.basic.service.BaseAreaService;
import com.tcode.business.basic.service.BaseCarSeriesService;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.shop.service.SettingService;
import com.tcode.core.util.Utils;
import com.tcode.open.wechat.vo.CenterVo;

@Component
public class CenterUtil {
	
	private static BaseAreaService baseAreaService;
	private static MemberCarService memberCarService;
	private static BaseCarSeriesService baseCarSeriesService;
	private static SettingService settingService;
	
	/**
	 * 初始化个人中心信息
	 * @param member
	 * @return
	 * @throws Exception 
	 */
	public static CenterVo initCenterInfo(Member member, Integer carId) throws Exception {
		int memId = member.getMemId();
		MemberCar memberCar = null;
		List<MemberCar> memberCarList = new ArrayList<MemberCar>();
		//获取区域信息
		BaseArea baseArea = baseAreaService.getAreaByMemId(memId);
		
		//获取会员对应的车辆信息
		if(!Utils.isEmpty(carId))
			memberCar = memberCarService.getMemberCarById(carId);
		else
			memberCarList = memberCarService.getMemberCarByMemberId(memId);
		
		//封装个人中心信息
		CenterVo centerVo = new CenterVo();
		if(Utils.isEmpty(memberCar) && memberCarList.size() > 0){
			memberCar = memberCarList.get(0);
		}
		if(!Utils.isEmpty(memberCar)) {//存在车辆信息则进行封装显示
			
			String carNumber = memberCar.getCarShort() + memberCar.getCarCode() + memberCar.getCarNumber();
			String nextCarInsection = memberCar.getCarInspection();//得到下次年检时间
			if(!Utils.isEmpty(nextCarInsection)) {
				//得到当前时间与下次年检时间相差天数
				String days = Utils.daysBetween(nextCarInsection, Utils.getSysTime(), "yyyy/MM/dd") + "";
				centerVo.setInspectionRemainingTime(days);//年检剩余时间
			} else centerVo.setInspectionRemainingTime("--");//年检剩余时间
			
			String nextCarInsurance = memberCar.getCarInsurance();//下次保险时间
			if(!Utils.isEmpty(nextCarInsurance)) {
				//得到当前时间与下次保险时间相差天数
				String days = Utils.daysBetween(nextCarInsurance, Utils.getSysTime(), "yyyy/MM/dd") + "";
				centerVo.setInsuranceRemainingTime(days);//保险剩余时间
			}else centerVo.setInsuranceRemainingTime("--");//保险剩余时间
			
			String nextCarMaintain = memberCar.getCarMaintain();//下次保养时间
			if(!Utils.isEmpty(nextCarMaintain)) {
				//得到当前时间与下次保养时间相差天数
				String days = Utils.daysBetween(nextCarMaintain, Utils.getSysTime(), "yyyy/MM/dd") + "";
				centerVo.setMaintainRemainingTime(days);//保养剩余时间
			}else centerVo.setMaintainRemainingTime("--");//保养剩余时间
			
			//获得车型信息
			centerVo.setCarId(memberCar.getId());//车辆ID
			centerVo.setCarNumber(carNumber);//车牌
			centerVo.setSeriesName(memberCar.getCarSeries());
//			Integer carSeries = memberCar.getCarSeries();//车型ID
//			if(carSeries != null) {
//				BaseCarSeries baseCarSeries = baseCarSeriesService.getById(carSeries);
//				
//				if(baseCarSeries != null)
//					centerVo.setSeriesName(baseCarSeries.getSeriesName());//车型号
//			} 
			
		}else {//不存在则设置 特殊数值
			centerVo.setInspectionRemainingTime("--");//年检剩余时间
			centerVo.setInsuranceRemainingTime("--");//保险剩余时间
			centerVo.setMaintainRemainingTime("--");//保养剩余时间
		}
		
		if(baseArea != null) {
			centerVo.setProvince(baseArea.getAreaName());
			centerVo.setCity(baseArea.getAreaName());
		}
		centerVo.setSex(member.getSex());//1男 2女 0未知
		centerVo.setWechatNick(member.getWechatNick());
		centerVo.setWehcatHead(member.getWehcatHead());
		centerVo.setBalance(member.getBalance());
		centerVo.setPoint(member.getPoint());
		
		//获取店铺信息
		String deptCode = member.getDeptCode();//门店编码
		centerVo.setSettingInfo(settingService.getById(deptCode));
		
		return centerVo;
	}

	
	
	
	
	
	
	public static BaseAreaService getBaseAreaService() {
		return baseAreaService;
	}

	@Resource
	public void setBaseAreaService(BaseAreaService baseAreaService) {
		CenterUtil.baseAreaService = baseAreaService;
	}
	
	public static MemberCarService getMemberCarService() {
		return memberCarService;
	}

	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		CenterUtil.memberCarService = memberCarService;
	}

	public BaseCarSeriesService getBaseCarSeriesService() {
		return baseCarSeriesService;
	}

	@Resource
	public void setBaseCarSeriesService(BaseCarSeriesService baseCarSeriesService) {
		CenterUtil.baseCarSeriesService = baseCarSeriesService;
	}

	public SettingService getSettingService() {
		return settingService;
	}

	@Resource
	public void setSettingService(SettingService settingService) {
		CenterUtil.settingService = settingService;
	}
	
	
}
