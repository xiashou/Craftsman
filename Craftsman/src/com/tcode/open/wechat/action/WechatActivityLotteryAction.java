package com.tcode.open.wechat.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.weixin4j.OAuth2User;

import com.tcode.business.member.model.Member;
import com.tcode.business.wechat.act.model.WechatActivityLottery;
import com.tcode.business.wechat.act.model.WechatActivityLotteryItem;
import com.tcode.business.wechat.act.model.WechatActivityLotteryPart;
import com.tcode.business.wechat.act.service.WechatActivityLotteryItemService;
import com.tcode.business.wechat.act.service.WechatActivityLotteryPartService;
import com.tcode.business.wechat.act.service.WechatActivityLotteryService;
import com.tcode.business.wechat.act.utils.WechatActivityLotteryUtil;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryItemVo;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryVo;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.open.wechat.bean.VerificationCode;
import com.tcode.open.wechat.util.AuthorizationUtil;
import com.tcode.system.model.SysDept;
import com.tcode.system.model.SysUser;

/**
 * 抽奖活动-主
 * @author supeng
 *
 */
@Scope("prototype")
@Component("OpenWechatActivityLotteryAction")
public class WechatActivityLotteryAction extends BaseAction {

	private static Logger log = Logger.getLogger("SLog");
	private WechatActivityLotteryService wechatActivityLotteryService;
	private WechatActivityLotteryItemService wechatActivityLotteryItemService;
	private WechatActivityLottery wechatActivityLottery;
	private WechatActivityLotteryItem wechatActivityLotteryItem;
	private WechatActivityLotteryVo wechatActivityLotteryVo;
	private WechatActivityLotteryItemVo wechatActivityLotteryItemVo;
	private List<WechatActivityLottery> wechatActivityLotteryList;
	private List<WechatActivityLotteryItem> wechatActivityLotteryItemList;
	private int prizeLevel;
	private WechatActivityLotteryPartService wechatActivityLotteryPartService;
	private List<WechatActivityLotteryPart> wechatActivityLotteryPartList;
	
	/**
	 * 初始化抽奖活动
	 * @return
	 */
	public String initWechatActivityLottery() {
		try {
			Member member = getMember();
			OAuth2User oAuth2User = getOAuth2User();
			
			//根据当前访问会员所在公司获取有效的活动信息，如存在有效的活动信息则进入对应的活动页面，没有则进行相应提示
			String companyId = member.getCompanyId();
			List<WechatActivityLottery> wechatActivityLotteryList = wechatActivityLotteryService.getByCompanyId(companyId);
			if(wechatActivityLotteryList.size() == 1) {//存在有且唯一有效活动
				wechatActivityLottery = wechatActivityLotteryList.get(0);
				
				//获取当前会员能抽奖的次数
				int num = WechatActivityLotteryUtil.isLotteryNum(member, oAuth2User, wechatActivityLottery);
				wechatActivityLottery.setFreeNum(num);
				
				int activityType = wechatActivityLottery.getActivityType();	//活动类型 0-砸金蛋,1-大转盘
				if(activityType == 0)//跳转至砸金蛋活动页面
					return "success0";
				else if(activityType == 1)//跳转至大转盘活动页面
					return "success1";
			}else {
				return "-1";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询参与活动次数，判断用户能否参与活动
	 * @return
	 */
	public String validateWechatActivityLotteryNum() {
		
		//获取当前会员能抽奖的次数
		int num = 0;
		Member member = getMember();
		OAuth2User oAuth2User = getOAuth2User();
		try {
			num = WechatActivityLotteryUtil.isLotteryNum(member, oAuth2User, wechatActivityLottery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		wechatActivityLottery = new WechatActivityLottery();
		wechatActivityLottery.setFreeNum(num);
		return SUCCESS;
	}
	
	/**
	 * 抽奖
	 * @return
	 */
	public String lWechatActivityLottery() {
		try {
			//判断距离上次抽奖时间是否已经有5秒钟，没有5秒不允许再次
			HttpServletRequest request = getRequest();
			HttpSession session = request.getSession();
			VerificationCode verificationCode =session.getAttribute("verificationCode") == null ? null : (VerificationCode) session.getAttribute("verificationCode");
			if(verificationCode != null) {
				String createTime = verificationCode.getCreateTime();
				String currentTime = Utils.getSysTime();
				int seconds = Utils.secondBetween(currentTime, createTime, "yyyy/MM/dd HH:mm:ss");
				
				if(seconds < 6) {//缓冲5秒
					prizeLevel = -3;//抽奖频繁
					return SUCCESS;
				}
			}else verificationCode = new VerificationCode();
			
			Member member = getMember();
			OAuth2User oAuth2User = getOAuth2User();
			prizeLevel = wechatActivityLotteryService.executeWechatActivityLottery(member, oAuth2User, wechatActivityLottery);
			if(prizeLevel >=0) prizeLevel += 1;
			
			
			//将验证码放入session
			verificationCode.setCreateTime(Utils.getSysTime());
			session.setAttribute("verificationCode", verificationCode);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
//	/**
//	 * 查询会员中奖信息
//	 * @return
//	 */
//	public String queryMemberWechatActivityLotteryPart() {
//		try {
//			Member member = getMember();
//			OAuth2User oAuth2User = getOAuth2User();
//			String activityCode = wechatActivityLottery.getActivityCode();
//			String openId = oAuth2User.getOpenid();
//			String appId = oAuth2User.getAppId();
//			wechatActivityLotteryPartList = wechatActivityLotteryPartService.getByActivityCode(activityCode, openId, appId);
//			for(WechatActivityLotteryPart wechatActivityLotteryPart : wechatActivityLotteryPartList) {
//				if(wechatActivityLotteryPart.getRedeemTime() != null)wechatActivityLotteryPart.setRedeemTime(Utils.changeDateFormat(wechatActivityLotteryPart.getRedeemTime(), "yyyy/MM/dd HH:mm:ss", "yy年MM月dd日"));
//				wechatActivityLotteryPart.setLotteryTime(Utils.changeDateFormat(wechatActivityLotteryPart.getLotteryTime(), "yyyy/MM/dd HH:mm:ss", "yy年MM月dd日"));
//			}
//		} catch(Exception e) {
//			log.error(Utils.getErrorMessage(e));
//		}
//		return SUCCESS;
//	}
	
	public WechatActivityLottery getWechatActivityLottery() {
		return wechatActivityLottery;
	}

	public void setWechatActivityLottery(WechatActivityLottery wechatActivityLottery) {
		this.wechatActivityLottery = wechatActivityLottery;
	}

	public WechatActivityLotteryVo getWechatActivityLotteryVo() {
		return wechatActivityLotteryVo;
	}

	public void setWechatActivityLotteryVo(WechatActivityLotteryVo wechatActivityLotteryVo) {
		this.wechatActivityLotteryVo = wechatActivityLotteryVo;
	}

	public List<WechatActivityLottery> getWechatActivityLotteryList() {
		return wechatActivityLotteryList;
	}

	public void setWechatActivityLotteryList(List<WechatActivityLottery> wechatActivityLotteryList) {
		this.wechatActivityLotteryList = wechatActivityLotteryList;
	}

	public WechatActivityLotteryService getWechatActivityLotteryService() {
		return wechatActivityLotteryService;
	}

	@Resource
	public void setWechatActivityLotteryService(WechatActivityLotteryService wechatActivityLotteryService) {
		this.wechatActivityLotteryService = wechatActivityLotteryService;
	}

	public WechatActivityLotteryItemService getWechatActivityLotteryItemService() {
		return wechatActivityLotteryItemService;
	}

	@Resource
	public void setWechatActivityLotteryItemService(WechatActivityLotteryItemService wechatActivityLotteryItemService) {
		this.wechatActivityLotteryItemService = wechatActivityLotteryItemService;
	}

	public List<WechatActivityLotteryItem> getWechatActivityLotteryItemList() {
		return wechatActivityLotteryItemList;
	}

	public void setWechatActivityLotteryItemList(List<WechatActivityLotteryItem> wechatActivityLotteryItemList) {
		this.wechatActivityLotteryItemList = wechatActivityLotteryItemList;
	}

	public WechatActivityLotteryItem getWechatActivityLotteryItem() {
		return wechatActivityLotteryItem;
	}

	public void setWechatActivityLotteryItem(WechatActivityLotteryItem wechatActivityLotteryItem) {
		this.wechatActivityLotteryItem = wechatActivityLotteryItem;
	}

	public WechatActivityLotteryItemVo getWechatActivityLotteryItemVo() {
		return wechatActivityLotteryItemVo;
	}

	public void setWechatActivityLotteryItemVo(WechatActivityLotteryItemVo wechatActivityLotteryItemVo) {
		this.wechatActivityLotteryItemVo = wechatActivityLotteryItemVo;
	}

	public int getPrizeLevel() {
		return prizeLevel;
	}

	public void setPrizeLevel(int prizeLevel) {
		this.prizeLevel = prizeLevel;
	}

	public WechatActivityLotteryPartService getWechatActivityLotteryPartService() {
		return wechatActivityLotteryPartService;
	}

	@Resource
	public void setWechatActivityLotteryPartService(WechatActivityLotteryPartService wechatActivityLotteryPartService) {
		this.wechatActivityLotteryPartService = wechatActivityLotteryPartService;
	}

	public List<WechatActivityLotteryPart> getWechatActivityLotteryPartList() {
		return wechatActivityLotteryPartList;
	}

	public void setWechatActivityLotteryPartList(List<WechatActivityLotteryPart> wechatActivityLotteryPartList) {
		this.wechatActivityLotteryPartList = wechatActivityLotteryPartList;
	}
	
}
