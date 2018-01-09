package com.tcode.open.wechat.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.member.model.Member;
import com.tcode.business.shop.service.SettingService;
import com.tcode.business.wechat.sos.model.WechatSOSLocation;
import com.tcode.business.wechat.sos.service.WechatSOSLocationService;
import com.tcode.common.action.BaseAction;
import com.tcode.system.model.SysDept;
import com.tcode.system.service.SysDeptService;

@Scope("prototype")
@Component("WechatSOSAction")
public class WechatSOSAction extends BaseAction {
	
	private SysDeptService sysDeptService;
	private SettingService settingService;
	private WechatSOSLocationService wechatSOSLocationService;
	private List<SysDept> deptList;
	private Member member;
	private WechatSOSLocation wechatSOSLocation;
	
	
	public String initSOS() {
		return SUCCESS;
	}
	
	/**
	 * 根据会员所在门店对应公司信息获取旗下所有门店信息
	 * @return
	 */
	public String getSOSDeptInfoByMember() {
		try {
			if(member != null) {
				deptList = sysDeptService.getByCompanyId(member.getCompanyId());
				this.setResult(true, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setResult(false, "查询出错！");
		}
		return SUCCESS;
	}
	
	/**
	 * 将紧急救援位置存储
	 * @return
	 */
	public String sendSOSLocation() {
		try {
			if(wechatSOSLocation != null) {
				Member member = (Member) getRequest().getSession().getAttribute("member");
				wechatSOSLocation.setCompanyId(member.getCompanyId());
				wechatSOSLocation.setVipNo(member.getVipNo());
				wechatSOSLocation.setAppid(member.getAppId());
				wechatSOSLocation.setOpenid(member.getWechatNo());
				wechatSOSLocation.setCreateBy("sys");
				wechatSOSLocation.setUpdateBy("sys");
				wechatSOSLocationService.insert(wechatSOSLocation);
				this.setResult(true, "发送位置成功！");
			}else {
				this.setResult(false, "发送位置出错！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setResult(false, "发送位置出错！");
		}
		return SUCCESS;
	}

	public SysDeptService getSysDeptService() {
		return sysDeptService;
	}
	
	@Resource
	public void setSysDeptService(SysDeptService sysDeptService) {
		this.sysDeptService = sysDeptService;
	}

	public List<SysDept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<SysDept> deptList) {
		this.deptList = deptList;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public SettingService getSettingService() {
		return settingService;
	}

	@Resource
	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}

	public WechatSOSLocation getWechatSOSLocation() {
		return wechatSOSLocation;
	}

	public void setWechatSOSLocation(WechatSOSLocation wechatSOSLocation) {
		this.wechatSOSLocation = wechatSOSLocation;
	}

	public WechatSOSLocationService getWechatSOSLocationService() {
		return wechatSOSLocationService;
	}

	@Resource
	public void setWechatSOSLocationService(WechatSOSLocationService wechatSOSLocationService) {
		this.wechatSOSLocationService = wechatSOSLocationService;
	}

}
