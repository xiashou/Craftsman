package com.tcode.business.goods.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.goods.model.GoodsMaterialType;
import com.tcode.business.goods.service.GoodsMaterialService;
import com.tcode.business.goods.service.GoodsMaterialTypeService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("goodsMaterialTypeAction")
public class GoodsMaterialTypeAcion extends BaseAction {

	private static final long serialVersionUID = 6582854975822171645L;
	private static Logger log = Logger.getLogger("SLog");
	
	private GoodsMaterialTypeService goodsMaterialTypeService;
	private GoodsMaterialService goodsMaterialService;
	
	private List<GoodsMaterialType> materialTypeList;
	private GoodsMaterialType materialType;
	
	/**
	 * 根据门店取商品类型
	 * @return
	 */
	public String queryMaterialTypeByDeptCode() {
		try {
			materialTypeList = goodsMaterialTypeService.getTypeByDeptCode(this.getDept().getCompanyId());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有实物类商品类型
	 * @return
	 */
	public String queryAllMaterialType() {
		try {
			this.setTotalCount(goodsMaterialTypeService.getListCount(materialType));
			materialTypeList = goodsMaterialTypeService.getTypeByDeptCode(this.getDept().getCompanyId());
		} catch(Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertMaterialType() {
		try {
			if(!Utils.isEmpty(materialType)){
				GoodsMaterialType search = new GoodsMaterialType();
				search.setDeptCode(this.getDept().getCompanyId());
				search.setTypeName(materialType.getTypeName());
				int count = goodsMaterialTypeService.getListCount(search);
				if(count == 0){
					materialType.setDeptCode(this.getDept().getCompanyId());
					goodsMaterialTypeService.insert(materialType);
					this.setResult(true, "添加成功！");
				}else{
					this.setResult(false, "类型名称已存在！");
				}
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateMaterialType() {
		try {
			if(!Utils.isEmpty(materialType) && !Utils.isEmpty(materialType.getId())){
				materialType.setDeptCode(this.getDept().getCompanyId());
				goodsMaterialTypeService.update(materialType);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteMaterialType() {
		try {
			if(!Utils.isEmpty(materialType) && !Utils.isEmpty(materialType.getId())){
				goodsMaterialTypeService.delete(materialType);
				GoodsMaterial goodsMaterial = new GoodsMaterial();
				goodsMaterial.setTypeId(materialType.getId());
				goodsMaterialService.deleteByType(goodsMaterial);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public GoodsMaterialTypeService getGoodsMaterialTypeService() {
		return goodsMaterialTypeService;
	}
	@Resource
	public void setGoodsMaterialTypeService(GoodsMaterialTypeService goodsMaterialTypeService) {
		this.goodsMaterialTypeService = goodsMaterialTypeService;
	}
	public GoodsMaterialService getGoodsMaterialService() {
		return goodsMaterialService;
	}
	@Resource
	public void setGoodsMaterialService(GoodsMaterialService goodsMaterialService) {
		this.goodsMaterialService = goodsMaterialService;
	}

	public List<GoodsMaterialType> getMaterialTypeList() {
		return materialTypeList;
	}
	public void setMaterialTypeList(List<GoodsMaterialType> materialTypeList) {
		this.materialTypeList = materialTypeList;
	}
	public GoodsMaterialType getMaterialType() {
		return materialType;
	}

	public void setMaterialType(GoodsMaterialType materialType) {
		this.materialType = materialType;
	}
	
}
