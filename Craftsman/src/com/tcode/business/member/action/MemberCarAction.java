package com.tcode.business.member.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("memberCarAction")
public class MemberCarAction extends BaseAction {

	private static final long serialVersionUID = -779069155194990033L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MemberCarService memberCarService;
	
	private List<MemberCar> carList;
	private MemberCar car;
	
	private Integer memberId;
	
	/**
	 * 根据会员ID查询车辆
	 * @return
	 */
	public String queryCarByMemberId(){
		try {
			if(!Utils.isEmpty(memberId)){
				carList = memberCarService.getMemberCarByMemberId(memberId);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "查询失败");
		}
		return SUCCESS;
	}
	
	/**
	 * 根据车辆ID查询车辆
	 * @return
	 */
	public String queryCarByCarId(){
		try {
			if(!Utils.isEmpty(car)){
				car = memberCarService.getMemberCarById(car.getId());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "查询失败");
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询会员
	 * @return
	 */
	public String queryCarPage() {
		try {
			this.setTotalCount(memberCarService.getListCount(car));
			carList = memberCarService.getListPage(car, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertCar() {
		try {
			if(!Utils.isEmpty(car))
				memberCarService.insert(car);
			this.setResult(true, "添加成功！");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateCar() {
		try {
			if(!Utils.isEmpty(car) && !Utils.isEmpty(car.getId())){
				memberCarService.update(car);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateCarS() {
		try {
			if(!Utils.isEmpty(car) && !Utils.isEmpty(car.getId())){
				MemberCar carDto = memberCarService.getMemberCarById(car.getId());
				if(!Utils.isEmpty(car.getCarBrand())) carDto.setCarBrand(car.getCarBrand());
				if(!Utils.isEmpty(car.getCarModel())) carDto.setCarModel(car.getCarModel());
				if(!Utils.isEmpty(car.getCarSeries())) carDto.setCarSeries(car.getCarSeries());
				if(!Utils.isEmpty(car.getCarShort())) carDto.setCarShort(car.getCarShort());
				if(!Utils.isEmpty(car.getCarCode())) carDto.setCarCode(car.getCarCode());
				if(!Utils.isEmpty(car.getCarNumber())) carDto.setCarNumber(car.getCarNumber());
				if(!Utils.isEmpty(car.getCarInspection())) {
					String inspection = Utils.changeDateFormat(car.getCarInspection(), "yyyy-MM-dd", "yyyy/MM/dd");
					carDto.setCarInspection(inspection);//下次年检时间
				}
				if(!Utils.isEmpty(car.getCarInsurance())) {
					String insurance = Utils.changeDateFormat(car.getCarInsurance(), "yyyy-MM-dd", "yyyy/MM/dd");
					carDto.setCarInsurance(insurance);//下次保险时间
				}
				if(!Utils.isEmpty(car.getCarMaintain())) {
					String maintain = Utils.changeDateFormat(car.getCarMaintain(), "yyyy-MM-dd", "yyyy/MM/dd ");
					carDto.setCarMaintain(maintain);//下次保养时间
				}
				if(!Utils.isEmpty(car.getCarKilometers())) carDto.setCarKilometers(car.getCarKilometers());//当前保养公里数
				if(!Utils.isEmpty(car.getCarNextkilo())) carDto.setCarNextkilo(car.getCarNextkilo());//下次保养公里数
				memberCarService.update(carDto);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteCar() {
		try {
			if(!Utils.isEmpty(car) && !Utils.isEmpty(car.getId())){
				memberCarService.delete(car);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public MemberCarService getMemberCarService() {
		return memberCarService;
	}
	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		this.memberCarService = memberCarService;
	}
	public List<MemberCar> getCarList() {
		return carList;
	}
	public void setCarList(List<MemberCar> carList) {
		this.carList = carList;
	}
	public MemberCar getCar() {
		return car;
	}
	public void setCar(MemberCar car) {
		this.car = car;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	
}
