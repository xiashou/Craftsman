package com.tcode.business.inte.jxcs.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.tcode.business.inte.jxcs.dao.RegisterDao;
import com.tcode.business.inte.jxcs.model.Baseinfo;
import com.tcode.business.inte.jxcs.model.Carparts;
import com.tcode.business.inte.jxcs.model.Register;
import com.tcode.business.inte.jxcs.model.Repair;
import com.tcode.business.inte.jxcs.model.Repairhours;
import com.tcode.business.inte.jxcs.service.RegisterService;
import com.tcode.business.member.dao.MemberCarDao;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;
import com.tcode.core.util.HttpUtil;
import com.tcode.core.util.Utils;

@Component("jx_registerService")
public class RegisterServiceImpl implements RegisterService {
	
	private RegisterDao jx_registerDao;
	private MemberCarDao memberCarDao;
	
	private String register_url = "http://47.93.44.48/repair-station/restapi/register_corp";
	private String addrepair_url = "http://47.93.44.48/repair-station/restapi/add_repair_info_all";
//	private String certificate_url = "http://47.93.44.48/repair-station/restapi/save_repair_certificate";
	
	@Override
	public void sendRepairInfo(OrderHead orderHead, List<OrderItem> itemList) throws Exception {
		if(!Utils.isEmpty(orderHead) && orderHead.getStatus() == 1){
			Register register = jx_registerDao.loadByDept(orderHead.getDeptCode());
			if(!Utils.isEmpty(register) && !Utils.isEmpty(register.getDeptCode())){
				Repair repair = new Repair();
				repair.setBaseinfo(this.createBaseinfo(register.getCmpyCompanycode(), orderHead, itemList));
				repair.setCarpartslist(this.createCarparts(register.getCmpyCompanycode(), itemList));
				repair.setRepairhourslist(this.createRepairhours(register.getCmpyCompanycode(), itemList));
				JSONObject result = HttpUtil.sendPost(addrepair_url, repair);
				System.out.println(result);
			}
		}
	}
	@Override
	public Register getByDept(String deptCode) throws Exception {
		return jx_registerDao.loadByDept(deptCode);
	}
	@Override
	public Register getByCompanycode(String code) throws Exception {
		return jx_registerDao.loadByCompanycode(code);
	}
	@Override
	public JSONObject syncRegister(Register register) throws Exception {
		JSONObject result = HttpUtil.sendPost(register_url, register);
		if(!Utils.isEmpty(result) && !Utils.isEmpty(result.get("code")) && result.getInteger("code") == 1){
			jx_registerDao.saveOrUpdate(register);
		}
		return result;
	}
	@Override
	public void insertOrUpdate(Register register) throws Exception {
		jx_registerDao.saveOrUpdate(register);
	}
	@Override
	public void insert(Register register) throws Exception {
		jx_registerDao.save(register);
	}
	@Override
	public void update(Register register) throws Exception {
		jx_registerDao.edit(register);
	}
	@Override
	public void delete(Register register) throws Exception {
		jx_registerDao.remove(register);
	}
	
	public Baseinfo createBaseinfo(String cmpyCode, OrderHead orderHead, List<OrderItem> itemList) throws Exception {
		Baseinfo baseinfo = null;
		if(!Utils.isEmpty(orderHead) && !Utils.isEmpty(orderHead.getCarId())){
			MemberCar car = memberCarDao.loadById(orderHead.getCarId());
			baseinfo = new Baseinfo();
			baseinfo.setCmpyCompanycode(cmpyCode);
			baseinfo.setCarno(orderHead.getCarNumber());
			baseinfo.setCarvin(car.getCarFrame());
			baseinfo.setFaultdescript(itemList.get(0).getGoodsName());
			baseinfo.setRepairdate(orderHead.getSaleDate().substring(0, 10).replaceAll("/", "-"));
			baseinfo.setRepairmile(car.getCarKilometers() + "");
			baseinfo.setSettledate(orderHead.getSaleDate().substring(0, 10).replaceAll("/", "-"));
			baseinfo.setStatementno(orderHead.getOrderId());
		}
		return baseinfo;
	}
	
	public List<Repairhours> createRepairhours(String cmpyCode, List<OrderItem> itemList) throws Exception {
		List<Repairhours> hoursList = null;
		if(!Utils.isEmpty(cmpyCode) && !Utils.isEmpty(itemList)){
			hoursList = new ArrayList<Repairhours>();
			for(OrderItem orderItem : itemList){
				if(orderItem.getGoodsType() == 1){
					Repairhours hours = new Repairhours();
					hours.setRepairname(orderItem.getGoodsName());
					if(!Utils.isEmpty(orderItem.getDiscount()))
						hours.setRepairhours(Integer.parseInt(Math.round(orderItem.getDiscount()!=0?(orderItem.getPrice()/(0.1*orderItem.getDiscount())/orderItem.getUnitPrice()):orderItem.getPrice()/orderItem.getUnitPrice())+""));
					else
						hours.setRepairhours(0);
					hoursList.add(hours);
				}
			}
		}
		return hoursList;
	}
	
	public List<Carparts> createCarparts(String cmpyCode, List<OrderItem> itemList) throws Exception {
		List<Carparts> partsList = null;
		if(!Utils.isEmpty(cmpyCode) && !Utils.isEmpty(itemList)){
			partsList = new ArrayList<Carparts>();
			for(OrderItem orderItem : itemList){
				if(orderItem.getGoodsType() == 2){
					Carparts part = new Carparts();
					part.setPartsname(orderItem.getGoodsName());
					part.setPartsno(orderItem.getGoodsId());
					part.setPartsquantity(orderItem.getNumber().intValue());
					partsList.add(part);
				}
			}
		}
		return partsList;
	}

	public RegisterDao getJx_registerDao() {
		return jx_registerDao;
	}
	@Resource
	public void setJx_registerDao(RegisterDao jx_registerDao) {
		this.jx_registerDao = jx_registerDao;
	}
	public MemberCarDao getMemberCarDao() {
		return memberCarDao;
	}
	@Resource
	public void setMemberCarDao(MemberCarDao memberCarDao) {
		this.memberCarDao = memberCarDao;
	}
	
	
}
