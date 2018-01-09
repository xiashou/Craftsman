package com.tcode.business.mall.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.model.MallAddress;
import com.tcode.business.mall.service.MallAddressService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("mallAddressAction")
public class MallAddressAction extends BaseAction {

	private static final long serialVersionUID = -829180056778613815L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MallAddressService mallAddressService;
	
	private List<MallAddress> addressList;
	private MallAddress address;
	
	
	public String insertMallAddress() {
		try {
			if(!Utils.isEmpty(address) && !Utils.isEmpty(address.getMemId())){
				address.setIsDefault(false);
				address.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				mallAddressService.insert(address);
				this.setResult(true, "添加成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String initUpdateMallAddress() {
		try {
			if(!Utils.isEmpty(address) && !Utils.isEmpty(address.getId())){
				address = mallAddressService.getById(address.getId());
				this.setResult(true, "添加成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String updateMallAddress() {
		try {
			if(!Utils.isEmpty(address) && !Utils.isEmpty(address.getId())){
				mallAddressService.update(address);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String deleteMallAddress() {
		try {
			if(!Utils.isEmpty(address) && !Utils.isEmpty(address.getId())){
				mallAddressService.delete(address);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String updateMallAddressDefault() {
		try {
			if(!Utils.isEmpty(address) && !Utils.isEmpty(address.getId())){
				address = mallAddressService.getById(address.getId());
				mallAddressService.updateAddressDefault(address.getMemId(), address.getId());
				this.setResult(true, "设置成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "设置失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public MallAddressService getMallAddressService() {
		return mallAddressService;
	}
	@Resource
	public void setMallAddressService(MallAddressService mallAddressService) {
		this.mallAddressService = mallAddressService;
	}
	public List<MallAddress> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<MallAddress> addressList) {
		this.addressList = addressList;
	}
	public MallAddress getAddress() {
		return address;
	}
	public void setAddress(MallAddress address) {
		this.address = address;
	}

}
