package com.tcode.business.inte.jxcs.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.tcode.business.inte.jxcs.model.Register;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;

public interface RegisterService {

	public Register getByDept(String deptCode) throws Exception;
	
	public Register getByCompanycode(String code) throws Exception;
	
	public JSONObject syncRegister(Register register) throws Exception;
	
	public void sendRepairInfo(OrderHead orderHead, List<OrderItem> itemList) throws Exception;
	
	/**
	 * 添加
	 * @param Register
	 * @throws Exception
	 */
	public void insert(Register register) throws Exception;
	
	/**
	 * 添加或修改
	 * @param Register
	 * @throws Exception
	 */
	public void insertOrUpdate(Register register) throws Exception;
	
	/**
	 * 修改
	 * @param Register
	 * @throws Exception
	 */
	public void update(Register register) throws Exception;
	
	/**
	 * 删除
	 * @param Register
	 * @throws Exception
	 */
	public void delete(Register register) throws Exception;
}
