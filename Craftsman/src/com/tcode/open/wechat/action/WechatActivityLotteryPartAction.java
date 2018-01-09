package com.tcode.open.wechat.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.tcode.open.wechat.util.AuthorizationUtil;
import com.tcode.system.model.SysDept;
import com.tcode.system.model.SysUser;

/**
 * 抽奖活动-主
 * @author supeng
 *
 */
@Scope("prototype")
@Component("OpenWechatActivityLotteryPartAction")
public class WechatActivityLotteryPartAction extends BaseAction {

	private static Logger log = Logger.getLogger("SLog");
	private WechatActivityLottery wechatActivityLottery;
	private WechatActivityLotteryPartService wechatActivityLotteryPartService;
	private List<WechatActivityLotteryPart> wechatActivityLotteryPartList;
	private WechatActivityLotteryPart wechatActivityLotteryPart;
	
	
	/**
	 * 查询会员中奖信息
	 * @return
	 */
	public String queryMemberWechatActivityPartLottery() {
		try {
			Member member = getMember();
			OAuth2User oAuth2User = getOAuth2User();
			String activityCode = wechatActivityLottery.getActivityCode();
			String openId = oAuth2User.getOpenid();
			String appId = oAuth2User.getAppId();
			wechatActivityLotteryPartList = wechatActivityLotteryPartService.getByActivityCode(activityCode, openId, appId);
			for(WechatActivityLotteryPart wechatActivityLotteryPart : wechatActivityLotteryPartList) {
				if(wechatActivityLotteryPart.getRedeemTime() != null)wechatActivityLotteryPart.setRedeemTime(Utils.changeDateFormat(wechatActivityLotteryPart.getRedeemTime(), "yyyy/MM/dd", "yy年MM月dd日"));
				wechatActivityLotteryPart.setLotteryTime(Utils.changeDateFormat(wechatActivityLotteryPart.getLotteryTime(), "yyyy/MM/dd", "yy年MM月dd日"));
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 兑奖
	 * @return
	 */
	public String redeemMemberWechatActivityPartLottery() {
		try {
			Member member = getMember();
			int id = wechatActivityLotteryPart.getId();
			wechatActivityLotteryPart = wechatActivityLotteryPartService.getById(id);
			wechatActivityLotteryPart.setRedeemStatus(1);//兑奖状态 0-未兑奖，1-已兑奖
			wechatActivityLotteryPart.setRedeemTime(Utils.getSysTime());//兑奖时间
			wechatActivityLotteryPart.setUpdateBy(member.getVipNo());
			wechatActivityLotteryPartService.update(wechatActivityLotteryPart);
			setMsg(SUCCESS);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public WechatActivityLottery getWechatActivityLottery() {
		return wechatActivityLottery;
	}

	public void setWechatActivityLottery(WechatActivityLottery wechatActivityLottery) {
		this.wechatActivityLottery = wechatActivityLottery;
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

	public WechatActivityLotteryPart getWechatActivityLotteryPart() {
		return wechatActivityLotteryPart;
	}

	public void setWechatActivityLotteryPart(WechatActivityLotteryPart wechatActivityLotteryPart) {
		this.wechatActivityLotteryPart = wechatActivityLotteryPart;
	}
	
}
