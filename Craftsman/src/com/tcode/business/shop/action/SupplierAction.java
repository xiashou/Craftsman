package com.tcode.business.shop.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.shop.model.Supplier;
import com.tcode.business.shop.service.SupplierService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("supplierAction")
public class SupplierAction extends BaseAction {

	private static final long serialVersionUID = 8206852408865695361L;
	private static Logger log = Logger.getLogger("SLog");
	
	private SupplierService supplierService;
	
	private List<Supplier> supplierList;
	private Supplier supplier;
	
	private String keyword;
	
	/**
	 * 查询部门所有供应商
	 * @return
	 */
	public String querySupplierByDept() {
		try {
			supplierList = supplierService.getListByDept(this.getDept().getDeptCode());
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据关键字查询供应商
	 * @return
	 */
	public String querySupplierByKeyword() {
		try {
			supplierList = supplierService.getListByKeyword(this.getDept().getDeptCode(), keyword);
		} catch(Exception e) {
			this.setResult(false, "查询失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加区域信息
	 * @return
	 */
	public String insertSupplier() {
		try {
			if(!Utils.isEmpty(supplier)){
				supplier.setDeptCode(this.getDept().getDeptCode());
				supplierService.insert(supplier);
				this.setResult(true, "添加成功！");
			}
		} catch (Exception e){
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改区域信息
	 * @return
	 */
	public String updateSupplier() {
		try {
			if(!Utils.isEmpty(supplier) && !Utils.isEmpty(supplier.getId())){
				supplier.setDeptCode(this.getDept().getDeptCode());
				supplierService.update(supplier);
				this.setResult(true, "修改成功！");
			}
		} catch (Exception e){
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteSupplier() {
		try {
			if(!Utils.isEmpty(supplier) && !Utils.isEmpty(supplier.getId())){
				supplierService.delete(supplier);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}


	public SupplierService getSupplierService() {
		return supplierService;
	}
	@Resource
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public List<Supplier> getSupplierList() {
		return supplierList;
	}
	public void setSupplierList(List<Supplier> supplierList) {
		this.supplierList = supplierList;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
