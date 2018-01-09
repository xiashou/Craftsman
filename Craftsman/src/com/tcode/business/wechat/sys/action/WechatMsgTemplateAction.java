package com.tcode.business.wechat.sys.action;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.model.WechatMsgTemplate;
import com.tcode.business.wechat.sys.service.WechatMsgTemplateService;
import com.tcode.business.wechat.sys.util.WechatMsgTemplateUtil;
import com.tcode.business.wechat.sys.vo.WechatMsgTemplateVo;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysDept;
import com.tcode.system.model.SysUser;

/**
 * 消息模板管理
 * @author supeng
 *
 */
@Scope("prototype")
@Component("WechatMsgTemplateAction")
public class WechatMsgTemplateAction extends BaseAction{
	
	private static Logger log = Logger.getLogger("SLog");
	private WechatMsgTemplateService wechatMsgTemplateService;
	private WechatMsgTemplate wechatMsgTemplate;
	private WechatMsgTemplateVo wechatMsgTemplateVo;
	private List<WechatMsgTemplate> wechatMsgTemplateList;
	
	/**
	 * 查询微信模板信息
	 * @return
	 */
	public String queryAllWechatMsgTemplate() {
		try {
			SysUser user = getUser();
			if(wechatMsgTemplateVo == null) wechatMsgTemplateVo = new WechatMsgTemplateVo();
			if(user.getRoleId() > 10) {
				//根据微信模板消息类型初始化对应店铺微信模板信息
				SysDept sysDept = getDept();
				WechatMsgTemplateUtil.initWechatMsgTemplate(sysDept);
				
				wechatMsgTemplateVo.setDeptCode(sysDept.getDeptCode());
			}
			this.setTotalCount(wechatMsgTemplateService.getListCount(wechatMsgTemplateVo));
			wechatMsgTemplateList = wechatMsgTemplateService.getListPage(wechatMsgTemplateVo, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 新增微信模板信息
	 * @return
	 */
	public String insertWechatMsgTemplate() {
		try {
			if(wechatMsgTemplate != null) {
				//根据门店编码查询是否已经存在该门店的数据，单个门店目前只能允许一个公众号
				String deptCode = wechatMsgTemplate.getDeptCode();
//				List<WechatMsgTemplate> wechatAuthorizerParamList = wechatMsgTemplateService.getByDeptCode(deptCode);
//				
//				if(wechatAuthorizerParamList.size() < 1) { 
					SysUser user = getUser();
					wechatMsgTemplate.setDeptCode(wechatMsgTemplate.getDeptCode().toUpperCase());
					wechatMsgTemplate.setCreateBy(user.getUserName());
					wechatMsgTemplate.setUpdateBy(user.getUserName());
					wechatMsgTemplateService.insert(wechatMsgTemplate);
					this.setResult(true, "新增成功！");
//				} else
//					this.setResult(false, "请勿重复添加！");
			} else
				this.setResult(false, "数据错误！");
		} catch(Exception e) {
			this.setResult(false, "新增失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改微信模板信息
	 * @return
	 */
	public String updateWechatMsgTemplate() {
		try {
			if(wechatMsgTemplate != null) {
				SysUser user = getUser();
				wechatMsgTemplate.setDeptCode(wechatMsgTemplate.getDeptCode().toUpperCase());
				wechatMsgTemplate.setUpdateBy(user.getUserName());
				wechatMsgTemplateService.update(wechatMsgTemplate);
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
	 * 删除微信模板信息
	 * @return
	 */
	public String deleteWechatMsgTemplate() {
		try {
			if(wechatMsgTemplate != null) {
				wechatMsgTemplateService.delete(wechatMsgTemplate);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "数据错误！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public WechatMsgTemplateService getWechatMsgTemplateService() {
		return wechatMsgTemplateService;
	}

	@Resource
	public void setWechatMsgTemplateService(WechatMsgTemplateService wechatMsgTemplateService) {
		this.wechatMsgTemplateService = wechatMsgTemplateService;
	}

	public WechatMsgTemplate getWechatMsgTemplate() {
		return wechatMsgTemplate;
	}

	public void setWechatMsgTemplate(WechatMsgTemplate wechatMsgTemplate) {
		this.wechatMsgTemplate = wechatMsgTemplate;
	}

	public WechatMsgTemplateVo getWechatMsgTemplateVo() {
		return wechatMsgTemplateVo;
	}

	public void setWechatMsgTemplateVo(WechatMsgTemplateVo wechatMsgTemplateVo) {
		this.wechatMsgTemplateVo = wechatMsgTemplateVo;
	}

	public List<WechatMsgTemplate> getWechatMsgTemplateList() {
		return wechatMsgTemplateList;
	}

	public void setWechatMsgTemplateList(List<WechatMsgTemplate> wechatMsgTemplateList) {
		this.wechatMsgTemplateList = wechatMsgTemplateList;
	}

}
