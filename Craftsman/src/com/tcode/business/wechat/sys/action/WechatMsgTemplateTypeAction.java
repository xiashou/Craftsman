package com.tcode.business.wechat.sys.action;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.model.WechatMsgTemplateType;
import com.tcode.business.wechat.sys.service.WechatMsgTemplateTypeService;
import com.tcode.business.wechat.sys.vo.WechatMsgTemplateTypeVo;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysUser;

/**
 * 消息模板管理
 * @author supeng
 *
 */
@Scope("prototype")
@Component("WechatMsgTemplateTypeAction")
public class WechatMsgTemplateTypeAction extends BaseAction{
	
	private static Logger log = Logger.getLogger("SLog");
	private WechatMsgTemplateTypeService wechatMsgTemplateTypeService;
	private WechatMsgTemplateType wechatMsgTemplateType;
	private WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo;
	private List<WechatMsgTemplateType> wechatMsgTemplateTypeList;
	
	/**
	 * 查询微信模板消息类型
	 * @return
	 */
	public String queryAllWechatMsgTypeTemplate() {
		try {
			this.setTotalCount(wechatMsgTemplateTypeService.getListCount(wechatMsgTemplateTypeVo));
			wechatMsgTemplateTypeList = wechatMsgTemplateTypeService.getListPage(wechatMsgTemplateTypeVo, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 新增微信模板消息类型
	 * @return
	 */
	public String insertWechatMsgTypeTemplate() {
		try {
			if(wechatMsgTemplateType != null) {
				//根据模板编号查询模板类型信息，模板编号唯一
				String templateNo = wechatMsgTemplateType.getTemplateNo();
				List<WechatMsgTemplateType> wechatMsgTemplateTypeList = wechatMsgTemplateTypeService.getByTemplateNo(templateNo);
				
				if(wechatMsgTemplateTypeList.size() < 1) { 
					SysUser user = getUser();
					wechatMsgTemplateType.setCreateBy(user.getUserName());
					wechatMsgTemplateType.setUpdateBy(user.getUserName());
					wechatMsgTemplateTypeService.insert(wechatMsgTemplateType);
					this.setResult(true, "新增成功！");
				} else
					this.setResult(false, "请勿重复添加！");
			} else
				this.setResult(false, "数据错误！");
		} catch(Exception e) {
			this.setResult(false, "新增失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改微信模板消息类型
	 * @return
	 */
	public String updateWechatMsgTypeTemplate() {
		try {
			if(wechatMsgTemplateType != null) {
				SysUser user = getUser();
//				wechatMsgTemplateType.setDeptCode(wechatMsgTemplateType.getDeptCode().toUpperCase());
				wechatMsgTemplateType.setUpdateBy(user.getUserName());
				wechatMsgTemplateTypeService.update(wechatMsgTemplateType);
				this.setResult(true, "修改成功！");
			} else
				this.setResult(false, "数据错误！");
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除微信模板消息类型
	 * @return
	 */
	public String deleteWechatMsgTypeTemplate() {
		try {
			if(wechatMsgTemplateType != null) {
				wechatMsgTemplateTypeService.delete(wechatMsgTemplateType);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "数据错误！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public WechatMsgTemplateTypeService getWechatMsgTemplateTypeService() {
		return wechatMsgTemplateTypeService;
	}

	@Resource
	public void setWechatMsgTemplateTypeService(WechatMsgTemplateTypeService wechatMsgTemplateTypeService) {
		this.wechatMsgTemplateTypeService = wechatMsgTemplateTypeService;
	}

	public WechatMsgTemplateType getWechatMsgTemplateType() {
		return wechatMsgTemplateType;
	}

	public void setWechatMsgTemplateType(WechatMsgTemplateType wechatMsgTemplateType) {
		this.wechatMsgTemplateType = wechatMsgTemplateType;
	}

	public WechatMsgTemplateTypeVo getWechatMsgTemplateTypeVo() {
		return wechatMsgTemplateTypeVo;
	}

	public void setWechatMsgTemplateTypeVo(WechatMsgTemplateTypeVo wechatMsgTemplateTypeVo) {
		this.wechatMsgTemplateTypeVo = wechatMsgTemplateTypeVo;
	}

	public List<WechatMsgTemplateType> getWechatMsgTemplateTypeList() {
		return wechatMsgTemplateTypeList;
	}

	public void setWechatMsgTemplateTypeList(List<WechatMsgTemplateType> wechatMsgTemplateTypeList) {
		this.wechatMsgTemplateTypeList = wechatMsgTemplateTypeList;
	}

}
