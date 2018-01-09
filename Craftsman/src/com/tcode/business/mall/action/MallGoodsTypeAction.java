package com.tcode.business.mall.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.model.MallGoodsType;
import com.tcode.business.mall.service.MallGoodsTypeService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("mallGoodsTypeAction")
public class MallGoodsTypeAction extends BaseAction {
	
	private static final long serialVersionUID = 335619606425463046L;
	private static Logger log = Logger.getLogger("SLog");

	private MallGoodsTypeService mallGoodsTypeService;
	
	private List<MallGoodsType> goodsTypeList;
	private MallGoodsType goodsType;
	
	
	/**
	 * 分页查找商城品牌信息
	 * @return
	 */
	public String queryMallGoodsTypeByPage() {
		try {
			if(Utils.isEmpty(goodsType))
				goodsType = new MallGoodsType();
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(!Utils.isEmpty(wechatApp)) {
				goodsType.setAppId(wechatApp.getAuthorizerAppId());
				this.setTotalCount(mallGoodsTypeService.getListCount(goodsType));
				goodsTypeList = mallGoodsTypeService.getListPage(goodsType, this.getStart(), this.getLimit());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询公众号所有商品类型
	 * @return
	 */
	public String queryMallGoodsTypeByAppId() {
		try {
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(!Utils.isEmpty(wechatApp)) 
				goodsTypeList = mallGoodsTypeService.getByAppId(wechatApp.getAuthorizerAppId());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertMallGoodsType() {
		try {
			if(!Utils.isEmpty(goodsType)){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					goodsType.setAppId(wechatApp.getAuthorizerAppId());
					mallGoodsTypeService.insert(goodsType);
					this.setResult(true, "添加成功！");
				} else
					this.setResult(true, "请先联系管理员配置公众号！");
			}
		} catch(Exception e) {
			this.setResult(true, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateMallGoodsType() {
		try {
			if(!Utils.isEmpty(goodsType) && !Utils.isEmpty(goodsType.getId())){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					goodsType.setAppId(wechatApp.getAuthorizerAppId());
					mallGoodsTypeService.update(goodsType);
					this.setResult(true, "修改成功！");
				} else
					this.setResult(true, "请先联系管理员配置公众号！");
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
	public String deleteMallGoodsType() {
		try {
			if(!Utils.isEmpty(goodsType) && !Utils.isEmpty(goodsType.getId())){
				mallGoodsTypeService.delete(goodsType);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e){
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public MallGoodsTypeService getMallGoodsTypeService() {
		return mallGoodsTypeService;
	}
	@Resource
	public void setMallGoodsTypeService(MallGoodsTypeService mallGoodsTypeService) {
		this.mallGoodsTypeService = mallGoodsTypeService;
	}
	public List<MallGoodsType> getGoodsTypeList() {
		return goodsTypeList;
	}
	public void setGoodsTypeList(List<MallGoodsType> goodsTypeList) {
		this.goodsTypeList = goodsTypeList;
	}
	public MallGoodsType getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(MallGoodsType goodsType) {
		this.goodsType = goodsType;
	}
	
	
	
}
