package com.tcode.business.finance.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.finance.model.Assets;
import com.tcode.business.finance.service.AssetsService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("assetsAction")
public class AssetsAction extends BaseAction {

	private static final long serialVersionUID = 5737430633447310051L;
	private static Logger log = Logger.getLogger("SLog");
	
	private AssetsService assetsService;
	
	private List<Assets> assetsList;
	
	private Assets assets;
	
	/**
	 * 查询门店所有资产信息
	 * @return
	 */
	public String queryAssetsByDept() {
		try {
			assetsList = assetsService.getListByDept(this.getDept().getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertAssets() {
		try {
			if(!Utils.isEmpty(assets)) {
				assets.setDeptCode(this.getDept().getDeptCode());
				assetsService.insert(assets);
			}
			this.setResult(true, "添加成功！");
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
	public String updateAssets() {
		try {
			if(!Utils.isEmpty(assets) && !Utils.isEmpty(assets.getId())) {
				assets.setDeptCode(this.getDept().getDeptCode());
				assetsService.update(assets);
			}
			this.setResult(true, "修改成功！");
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
	public String deleteAssets() {
		try {
			if(!Utils.isEmpty(assets) && !Utils.isEmpty(assets.getId()))
				assetsService.delete(assets);
			this.setResult(true, "删除成功！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public AssetsService getAssetsService() {
		return assetsService;
	}
	@Resource
	public void setAssetsService(AssetsService assetsService) {
		this.assetsService = assetsService;
	}
	public List<Assets> getAssetsList() {
		return assetsList;
	}
	public void setAssetsList(List<Assets> assetsList) {
		this.assetsList = assetsList;
	}
	public Assets getAssets() {
		return assets;
	}
	public void setAssets(Assets assets) {
		this.assets = assets;
	}
	
}
