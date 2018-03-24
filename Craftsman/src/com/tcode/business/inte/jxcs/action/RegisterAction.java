package com.tcode.business.inte.jxcs.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.tcode.business.inte.jxcs.model.Register;
import com.tcode.business.inte.jxcs.service.RegisterService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("registerAction")
public class RegisterAction extends BaseAction {

	private static final long serialVersionUID = 335619606425463046L;
	private static Logger log = Logger.getLogger("SLog");
	
	private RegisterService jx_registerService;
	
	private Register register;
	
	/**
	 * 根据门店编码获取注册信息
	 * @return
	 */
	public String queryRegisterByDept(){
		try {
			register = jx_registerService.getByDept(this.getDept().getDeptCode());
			if(Utils.isEmpty(register))
				register = new Register();
			register.setDeptCode(this.getDept().getDeptCode());
		} catch (Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String saveRegister(){
		try {
			if(!Utils.isEmpty(register)){
				Register exist = jx_registerService.getByDept(register.getDeptCode());
				if(Utils.isEmpty(exist))
					jx_registerService.insert(register);
				else
					jx_registerService.update(register);
				this.setResult(true, "保存成功！");
			}
		} catch (Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String syncRegister(){
		try {
			if(!Utils.isEmpty(register)){
				JSONObject result = jx_registerService.syncRegister(register);
				if(!Utils.isEmpty(result) && !Utils.isEmpty(result.get("code")) && result.getInteger("code") == 1)
					this.setResult(true, "注册成功！");
				else
					this.setResult(false, result.getString("status"));
			}
		} catch (Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public RegisterService getJx_registerService() {
		return jx_registerService;
	}
	@Resource
	public void setJx_registerService(RegisterService jx_registerService) {
		this.jx_registerService = jx_registerService;
	}
	public Register getRegister() {
		return register;
	}
	public void setRegister(Register register) {
		this.register = register;
	}
	
	
}
