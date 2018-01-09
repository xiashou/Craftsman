package com.tcode.business.wechat.act.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.weixin4j.OAuth2User;

import com.tcode.business.member.model.Member;
import com.tcode.business.wechat.act.model.PrizeModel;
import com.tcode.business.wechat.act.model.WechatActivityLottery;
import com.tcode.business.wechat.act.model.WechatActivityLotteryItem;
import com.tcode.business.wechat.act.model.WechatActivityLotteryPart;
import com.tcode.business.wechat.act.model.WechatActivityNum;
import com.tcode.business.wechat.act.service.WechatActivityLotteryPartService;
import com.tcode.business.wechat.act.service.WechatActivityLotteryService;
import com.tcode.business.wechat.act.service.WechatActivityNumService;
import com.tcode.core.util.Utils;

@Component
public class WechatActivityLotteryUtil {
	
	private static WechatActivityLotteryPartService wechatActivityLotteryPartService;
	private static WechatActivityLotteryService wechatActivityLotteryService;
	private static WechatActivityNumService wechatActivityNumService;
	
	/**
	 * 根据openid、活动编码获取用户能够参与活动的次数
	 * @param activityCode
	 * @param openId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public static int isLotteryNum(Member member, OAuth2User oAuth2User, WechatActivityLottery wechatActivityLottery) throws Exception {
		int num = 0;
//		List<WechatActivityLotteryPart> wechatActivityLotteryPartList = wechatActivityLotteryPartService.getByActivityCode(activityCode, openId, appId);
//		//现未加入参与次数模块，目前每个活动只允许用户参与一次
//		if(wechatActivityLotteryPartList.size() <= 0) num = 1;
		WechatActivityNum wechatActivityNum = null;
		String activityCode = wechatActivityLottery.getActivityCode();
		String openId = oAuth2User.getOpenid();
		String appId = oAuth2User.getAppId();
		List<WechatActivityNum> wechatActivityNums = wechatActivityNumService.getByActivityCode(activityCode, openId, appId);
		if(wechatActivityNums.size() > 0) {
			wechatActivityNum =  wechatActivityNums.get(0);
			System.out.println(wechatActivityNum);
			num = wechatActivityNum.getFreeNum();
		}else {
			String currentTime = Utils.getSysTime();
			wechatActivityNum = new WechatActivityNum();
			wechatActivityNum.setActivityCode(activityCode);
			wechatActivityNum.setActivityType(wechatActivityLottery.getActivityType());
			wechatActivityNum.setOpenId(openId);
			wechatActivityNum.setAppId(appId);
			wechatActivityNum.setFreeNum(0);
			wechatActivityNum.setCompanyId(member.getCompanyId());
//			wechatActivityNum.setCompanyName(companyName);
			wechatActivityNum.setDeptCode(member.getDeptCode());
//			wechatActivityNum.setDeptName(deptName);
			wechatActivityNum.setVipNo(member.getVipNo());
			wechatActivityNum.setCreateBy("sys");
			wechatActivityNum.setUpdateBy("sys");
			wechatActivityNum.setCreateTime(currentTime);
			wechatActivityNum.setUpdateTime(currentTime);
			wechatActivityNumService.insert(wechatActivityNum);
		}
		return num;
	}
	
	/**
	 * 抽奖
	 * @param activityCode
	 * @param openId
	 * @param appId
	 * @throws Exception 
	 */
	public static List<PrizeModel> lotteryGame(WechatActivityLottery wechatActivityLottery) throws Exception {
		List<PrizeModel> list = new ArrayList<PrizeModel>();
		List<WechatActivityLotteryItem> wechatActivityLotteryItemList = wechatActivityLottery.getWechatActivityLotteryItemList();
		String activityCode = wechatActivityLottery.getActivityCode();
		for(WechatActivityLotteryItem wechatActivityLotteryItem : wechatActivityLotteryItemList) {
			int prizeNum = wechatActivityLotteryItem.getPrizeNum();
			int prizeLevel = wechatActivityLotteryItem.getPrizeLevel();
			int prizeRate = wechatActivityLotteryItem.getPrizeRate();
			int lotteryPrizeNum = wechatActivityLotteryItem.getLotteryPrizeNum();//已抽出奖品数量
			setModel(list, prizeNum,lotteryPrizeNum, prizeLevel, prizeRate, activityCode);
		}
		return list;
	}
	
	/**
	 * 抽奖算法
	 * 
	 * @param list
	 * @return
	 */
	public static int lotteryAlgorithm(List<PrizeModel> list) {

		// 中奖等级：未中奖
		int winningLevel = -1;

		if (list == null || list.size() <= 0) {
			return winningLevel;
		}

		// 中奖随机号
		int randomWinningNo = 0;
		int args[] = new int[list.size() * 2];
		int temp = (int) Math.round(Math.random() * 1000000000) % 1000000;
		int j = 0;

		for (int i = 0; i < list.size(); i++) {

			double tmpWinningPro = list.get(i).getPrizeRate();

			if (j == 0) {
				args[j] = randomWinningNo;
			} else {
				args[j] = args[j - 1] + 1;
			}
			args[j + 1] = args[j] + (int) Math.round(tmpWinningPro * 10000) - 1;

			if (temp >= args[j] && temp <= args[j + 1]) {
				if (list != null && list.size() > i) {
					winningLevel = list.get(i).getPrizeLevel();
					return winningLevel;
				}
			}
			j += 2;
		}
		return winningLevel;
	}
	
	/**
	 * 设置可抽奖奖品列表信息
	 * @param list 奖品列表
	 * @param prizeNum 奖品数量
	 * @param lotteryPrizeNum 已抽出奖品数量
	 * @param prizeLevel 等级
	 * @param prizeRate 概率
	 * @param activityCode 抽奖活动ID
	 */
	public static void setModel(List<PrizeModel> list, int prizeNum, int lotteryPrizeNum, int prizeLevel, double prizeRate, String activityCode){
		//已抽出奖品数量
		if(prizeNum > lotteryPrizeNum){
			PrizeModel prizeModel = new PrizeModel();
			prizeModel.setId(prizeLevel);
			prizeModel.setPrizeLevel(prizeLevel);
			prizeModel.setPrizeRate(prizeRate);
			list.add(prizeModel);
		}
	}

	public WechatActivityLotteryPartService getWechatActivityLotteryPartService() {
		return wechatActivityLotteryPartService;
	}

	@Resource
	public void setWechatActivityLotteryPartService(WechatActivityLotteryPartService wechatActivityLotteryPartService) {
		this.wechatActivityLotteryPartService = wechatActivityLotteryPartService;
	}

	public WechatActivityLotteryService getWechatActivityLotteryService() {
		return wechatActivityLotteryService;
	}

	@Resource
	public void setWechatActivityLotteryService(WechatActivityLotteryService wechatActivityLotteryService) {
		WechatActivityLotteryUtil.wechatActivityLotteryService = wechatActivityLotteryService;
	}


	public WechatActivityNumService getWechatActivityNumService() {
		return wechatActivityNumService;
	}

	@Resource
	public void setWechatActivityNumService(WechatActivityNumService wechatActivityNumService) {
		WechatActivityLotteryUtil.wechatActivityNumService = wechatActivityNumService;
	}

}
