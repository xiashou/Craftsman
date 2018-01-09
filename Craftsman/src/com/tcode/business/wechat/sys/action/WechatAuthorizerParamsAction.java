package com.tcode.business.wechat.sys.action;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.service.WechatAuthorizerParamsService;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.business.wechat.sys.vo.WechatAuthorizerParamsVo;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysUser;

/**
 * 授权方（公众号）参数管理
 * @author supeng
 *
 */
@Scope("prototype")
@Component("wechatAuthorizerParamsAction")
public class WechatAuthorizerParamsAction extends BaseAction{
	
	private static Logger log = Logger.getLogger("SLog");
	private WechatAuthorizerParamsService wechatAuthorizerParamsService;
	private WechatAuthorizerParamsVo wechatAuthorizerParamsVo;
	private WechatAuthorizerParams wechatAuthorizerParams;
	private List<WechatAuthorizerParams> wechatAuthorizerParamsList;
	
	private Integer[] ids;
	
	/**
	 * 查询所有授权方（公众号）参数信息
	 * @return
	 */
	public String queryAllWechatAuthorizerParams() {
		try {
			this.setTotalCount(wechatAuthorizerParamsService.getListCount(wechatAuthorizerParamsVo));
			wechatAuthorizerParamsList = wechatAuthorizerParamsService.getListPage(wechatAuthorizerParamsVo, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 新增授权方（公众号）参数信息
	 * @return
	 */
	public String insertWechatAuthorizerParamsUUID() {
		try {
			if(wechatAuthorizerParams != null) {
				//根据门店编码查询是否已经存在该门店的数据，单个门店目前只能允许一个公众号
				String deptCode = wechatAuthorizerParams.getDeptCode();
				List<WechatAuthorizerParams> wechatAuthorizerParamList = wechatAuthorizerParamsService.getByDeptCode(deptCode);
				
				if(wechatAuthorizerParamList.size() < 1) { 
					wechatAuthorizerParams.setSid(UUID.randomUUID().toString());
					SysUser user = getUser();
					wechatAuthorizerParams.setDeptCode(wechatAuthorizerParams.getDeptCode().toUpperCase());
					wechatAuthorizerParams.setCreateBy(user.getUserName());
					wechatAuthorizerParams.setUpdateBy(user.getUserName());
					wechatAuthorizerParamsService.insert(wechatAuthorizerParams);
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
	 * 修改授权方（公众号）参数信息
	 * @return
	 */
	public String updateWechatAuthorizerParams() {
		try {
			if(wechatAuthorizerParams != null) {
				SysUser user = getUser();
				wechatAuthorizerParams.setDeptCode(wechatAuthorizerParams.getDeptCode().toUpperCase());
				wechatAuthorizerParams.setUpdateBy(user.getUserName());
				wechatAuthorizerParamsService.update(wechatAuthorizerParams);
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
	 * 删除授权方（公众号）参数信息
	 * @return
	 */
	public String deleteWechatAuthorizerParams() {
		try {
			if(wechatAuthorizerParams != null) {
				wechatAuthorizerParamsService.delete(wechatAuthorizerParams);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "数据错误！");
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 刷新公众菜单
	 * @return
	 */
	public String refreshMenuWechatAuthorizerParams() {
		try {
			if(!Utils.isEmpty(ids)){
				HttpServletRequest request = getRequest();
				int count = 0;
				for(int id : ids){
					WechatAuthorizerParams wechatAuthor = wechatAuthorizerParamsService.getById(id);
					if(!Utils.isEmpty(wechatAuthor)){
						WechatAuthorizerParamsUtil.initMenu(request, wechatAuthor.getSid(), wechatAuthor.getAuthorizerAppId());
						count++;
					}
				}
				this.setResult(true, "成功刷新" + count + "个菜单！");
			} else
				this.setResult(false, "刷新菜单失败！");
		} catch(Exception e) {
			this.setResult(false, "刷新菜单失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public WechatAuthorizerParams getWechatAuthorizerParams() {
		return wechatAuthorizerParams;
	}

	public void setWechatAuthorizerParams(WechatAuthorizerParams wechatAuthorizerParams) {
		this.wechatAuthorizerParams = wechatAuthorizerParams;
	}

	public List<WechatAuthorizerParams> getWechatAuthorizerParamsList() {
		return wechatAuthorizerParamsList;
	}

	public void setWechatAuthorizerParamsList(List<WechatAuthorizerParams> wechatAuthorizerParamsList) {
		this.wechatAuthorizerParamsList = wechatAuthorizerParamsList;
	}
	
	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
	
	
	

	public WechatAuthorizerParamsService getWechatAuthorizerParamsService() {
		return wechatAuthorizerParamsService;
	}

	@Resource
	public void setWechatAuthorizerParamsService(WechatAuthorizerParamsService wechatAuthorizerParamsService) {
		this.wechatAuthorizerParamsService = wechatAuthorizerParamsService;
	}
}
