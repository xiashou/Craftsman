package com.tcode.business.mall.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.model.MallTyre;
import com.tcode.business.mall.service.MallTyreService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("mallTyreAction")
public class MallTyreAction extends BaseAction {
	
	private static final long serialVersionUID = 335619606425463046L;
	private static Logger log = Logger.getLogger("SLog");

	private MallTyreService mallTyreService;
	
	private List<MallTyre> tyreList;
	private String[] goodsIds;
	private Integer[] modelIds;
	private Integer modelId;
	
	
	/**
	 * 保存轮胎商品关联关系
	 * @return
	 */
	public String saveMallTyreRelation() {
		try {
			if(!Utils.isEmpty(modelIds) && !Utils.isEmpty(goodsIds)) {
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					int count = mallTyreService.saveMoreMallTyre(wechatApp.getAuthorizerAppId(), modelIds, goodsIds);
					this.setResult(true, "成功保存" + count + "个关联！");
				} else
					this.setResult(false, "参数缺失！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询系列下面所有型号的关联
	 * @return
	 */
	public String queryMallTyreByModel(){
		try {
			if(!Utils.isEmpty(modelId)) {
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp))
					tyreList = mallTyreService.getListByModel(wechatApp.getAuthorizerAppId(), modelId);
				else
					this.setResult(false, "参数缺失！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public MallTyreService getMallTyreService() {
		return mallTyreService;
	}
	@Resource
	public void setMallTyreService(MallTyreService mallTyreService) {
		this.mallTyreService = mallTyreService;
	}
	public String[] getGoodsIds() {
		return goodsIds;
	}
	public void setGoodsIds(String[] goodsIds) {
		this.goodsIds = goodsIds;
	}
	public List<MallTyre> getTyreList() {
		return tyreList;
	}
	public void setTyreList(List<MallTyre> tyreList) {
		this.tyreList = tyreList;
	}
	public Integer[] getModelIds() {
		return modelIds;
	}
	public void setModelIds(Integer[] modelIds) {
		this.modelIds = modelIds;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	
	
}
