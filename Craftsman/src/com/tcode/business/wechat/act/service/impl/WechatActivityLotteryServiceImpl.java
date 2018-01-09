package com.tcode.business.wechat.act.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.weixin4j.OAuth2User;

import com.alibaba.fastjson.JSONArray;
import com.tcode.business.member.model.Member;
import com.tcode.business.wechat.act.dao.WechatActivityLotteryDao;
import com.tcode.business.wechat.act.dao.WechatActivityLotteryItemDao;
import com.tcode.business.wechat.act.dao.WechatActivityLotteryPartDao;
import com.tcode.business.wechat.act.dao.WechatActivityNumDao;
import com.tcode.business.wechat.act.model.PrizeModel;
import com.tcode.business.wechat.act.model.WechatActivityLottery;
import com.tcode.business.wechat.act.model.WechatActivityLotteryItem;
import com.tcode.business.wechat.act.model.WechatActivityLotteryPart;
import com.tcode.business.wechat.act.model.WechatActivityNum;
import com.tcode.business.wechat.act.service.WechatActivityLotteryService;
import com.tcode.business.wechat.act.utils.WechatActivityLotteryUtil;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryVo;
import com.tcode.common.idgenerator.IDHelper;
import com.tcode.core.util.UID;
import com.tcode.core.util.Utils;

@Component("wechatActivityLotteryService")
public class WechatActivityLotteryServiceImpl implements WechatActivityLotteryService {
	
	private WechatActivityLotteryDao wechatActivityLotteryDao;
	private WechatActivityLotteryItemDao wechatActivityLotteryItemDao;
	private WechatActivityLotteryPartDao wechatActivityLotteryPartDao;
	private WechatActivityNumDao wechatActivityNumDao;

	@Override
	public void insert(WechatActivityLottery wechatActivityLottery) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatActivityLottery.setCreateTime(currentTime);
		wechatActivityLottery.setUpdateTime(currentTime);
		wechatActivityLottery.setActivityCode("L" + IDHelper.getLotteryActivityCode()); 
		wechatActivityLotteryDao.save(wechatActivityLottery);
	}

	@Override
	public void update(WechatActivityLottery wechatActivityLottery) throws Exception {
		String currentTime = Utils.getSysTime();
		wechatActivityLottery.setUpdateTime(currentTime);
		wechatActivityLotteryDao.edit(wechatActivityLottery);
	}

	@Override
	public void delete(WechatActivityLottery wechatActivityLottery) throws Exception {
		wechatActivityLotteryDao.remove(wechatActivityLottery);
	}

	@Override
	public WechatActivityLottery getById(Integer id) throws Exception {
		return wechatActivityLotteryDao.loadById(id);
	}

	@Override
	public List<WechatActivityLottery> getAll() throws Exception {
		return wechatActivityLotteryDao.loadAll();
	}

	@Override
	public List<WechatActivityLottery> getListPage(WechatActivityLotteryVo wechatActivityLotteryVo, int start,
			int limit) throws Exception {
		return wechatActivityLotteryDao.loadListPage(wechatActivityLotteryVo, start, limit);
	}

	@Override
	public Integer getListCount(WechatActivityLotteryVo wechatActivityLotteryVo) throws Exception {
		return wechatActivityLotteryDao.loadListCount(wechatActivityLotteryVo);
	}

	@Override
	public List<WechatActivityLottery> getByActivityCode(String activityCode) throws Exception {
		List<WechatActivityLottery> wechatActivityLotterieList = wechatActivityLotteryDao.loadByActivityCode(activityCode);
		//封装子表数据
		if(wechatActivityLotterieList.size() > 0) {
			List<WechatActivityLotteryItem> wechatActivityLotteryItemList = wechatActivityLotteryItemDao.loadByActivityCode(activityCode);
			wechatActivityLotterieList.get(0).setWechatActivityLotteryItemList(wechatActivityLotteryItemList);
		}
		return wechatActivityLotterieList;
	}
	
	@Override
	public List<WechatActivityLottery> getByCompanyId(String companyId) throws Exception {
		List<WechatActivityLottery> wechatActivityLotterieList = wechatActivityLotteryDao.loadByCompanyId(companyId);
		//封装子表数据
		if(wechatActivityLotterieList.size() > 0) {
			String activityCode = wechatActivityLotterieList.get(0).getActivityCode();
			List<WechatActivityLotteryItem> wechatActivityLotteryItemList = wechatActivityLotteryItemDao.loadByActivityCode(activityCode);
			
			//封装奖品信息
			int size = wechatActivityLotteryItemList.size();
			String[] levelNames = new String[size];
			for(int i=0; i<size; i++) {
				levelNames[i] = wechatActivityLotteryItemList.get(i).getLevelName();
			}
			WechatActivityLottery wechatActivityLottery = wechatActivityLotterieList.get(0);
			wechatActivityLottery.setLevelNames(JSONArray.toJSONString(levelNames));
			wechatActivityLottery.setWechatActivityLotteryItemList(wechatActivityLotteryItemList);
		}
		return wechatActivityLotterieList;
	}
	
	@Override
	public int executeWechatActivityLottery(Member member, OAuth2User oAuth2User, WechatActivityLottery wechatActivityLottery) throws Exception {
		int prizeLevel = -1;
		String currentTime = Utils.getSysTime();
		
		//获取参与次数信息
		String activityCode = wechatActivityLottery.getActivityCode();
		String openId = oAuth2User.getOpenid();
		String appId = oAuth2User.getAppId();
		List<WechatActivityNum> wechatActivityNums = wechatActivityNumDao.loadByActivityCode(activityCode, openId, appId);
		WechatActivityNum wechatActivityNum = null;
		if(wechatActivityNums.size() > 0) {//存在则分析其是否可以参加活动,不存在则初始化一条参与次数信息
			wechatActivityNum = wechatActivityNums.get(0);
			int freeNum = wechatActivityNum.getFreeNum();//参与活动次数
			if(freeNum > 0) {//剩余次数大于0，则进行抽奖操作，否则不进行操作，返回-1
				//初始化抽奖模型
				List<WechatActivityLottery> wechatActivityLotterieList = wechatActivityLotteryDao.loadByActivityCode(activityCode);
				//封装子表数据
				if(wechatActivityLotterieList.size() > 0) {
					wechatActivityLottery = wechatActivityLotterieList.get(0);
					List<WechatActivityLotteryItem> wechatActivityLotteryItemList = wechatActivityLotteryItemDao.loadByActivityCode(activityCode);
					wechatActivityLottery.setWechatActivityLotteryItemList(wechatActivityLotteryItemList);
				}
				List<PrizeModel> list = WechatActivityLotteryUtil.lotteryGame(wechatActivityLottery);
				prizeLevel = WechatActivityLotteryUtil.lotteryAlgorithm(list);//大于-1则中奖
				
				//修改对应奖品中奖数量
				List<WechatActivityLotteryItem> wechatActivityLotteryItems = wechatActivityLotteryItemDao.loadByActivityCode(activityCode, prizeLevel);
				String levelName = "";
				String prizeDesc = "";
				int prizeWin = 0;
				if(wechatActivityLotteryItems.size() > 0) {
					WechatActivityLotteryItem wechatActivityLotteryItem = wechatActivityLotteryItems.get(0);
					levelName = wechatActivityLotteryItem.getLevelName();
					prizeDesc = wechatActivityLotteryItem.getPrizeDesc();
					prizeWin = wechatActivityLotteryItem.getPrizeWin();
					
					wechatActivityLotteryItem.setLotteryPrizeNum(wechatActivityLotteryItem.getLotteryPrizeNum() + prizeWin);
					wechatActivityLotteryItemDao.edit(wechatActivityLotteryItem);
					
				}
				
				
				//插入抽奖信息
				WechatActivityLotteryPart wechatActivityLotteryPart = new WechatActivityLotteryPart();
				wechatActivityLotteryPart.setActivityCode(activityCode);
				wechatActivityLotteryPart.setActivityTitle(wechatActivityLottery.getActivityTitle());
				wechatActivityLotteryPart.setRedeemCode("S" + UID.next());//兑奖码
				wechatActivityLotteryPart.setRedeemStatus(0);//兑奖状态 0-未兑奖，1-已兑奖
				wechatActivityLotteryPart.setPrizeLevel(prizeLevel);
				wechatActivityLotteryPart.setLevelName(levelName);
				wechatActivityLotteryPart.setPrizeDesc(prizeDesc);
				wechatActivityLotteryPart.setPrizeWin(prizeWin);
				wechatActivityLotteryPart.setLotteryTime(currentTime);//抽奖时间
//				wechatActivityLotteryPart.setRedeemTime(redeemTime);//兑奖时间
				wechatActivityLotteryPart.setOpenId(openId);
				wechatActivityLotteryPart.setAppId(appId);
				wechatActivityLotteryPart.setCompanyId(member.getCompanyId());
//				wechatActivityLotteryPart.setCompanyName(companyName);
				wechatActivityLotteryPart.setDeptCode(member.getDeptCode());
//				wechatActivityLotteryPart.setDeptName(deptName);
				wechatActivityLotteryPart.setCreateBy("sys");
				wechatActivityLotteryPart.setUpdateBy("sys");
				wechatActivityLotteryPart.setCreateTime(currentTime);
				wechatActivityLotteryPart.setUpdateTime(currentTime);
				wechatActivityLotteryPartDao.save(wechatActivityLotteryPart);
				
				//修改参与次数
				wechatActivityNum.setFreeNum(freeNum - 1);
				wechatActivityNum.setUpdateTime(currentTime);
				wechatActivityNumDao.save(wechatActivityNum);
				
//				System.out.println("prizeLevel:::" + prizeLevel);
			} else prizeLevel = -2;
		}else {//不存在则初始化一条参与次数信息
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
			wechatActivityNum.setCreateBy("sys");
			wechatActivityNum.setUpdateBy("sys");
			wechatActivityNum.setCreateTime(currentTime);
			wechatActivityNum.setUpdateTime(currentTime);
			wechatActivityNumDao.save(wechatActivityNum);
			prizeLevel = -2;
		}
		// TODO Auto-generated method stub
		
		return prizeLevel;
	}
	
	public WechatActivityLotteryDao getWechatActivityLotteryDao() {
		return wechatActivityLotteryDao;
	}

	@Resource
	public void setWechatActivityLotteryDao(WechatActivityLotteryDao wechatActivityLotteryDao) {
		this.wechatActivityLotteryDao = wechatActivityLotteryDao;
	}

	public WechatActivityLotteryItemDao getWechatActivityLotteryItemDao() {
		return wechatActivityLotteryItemDao;
	}

	@Resource
	public void setWechatActivityLotteryItemDao(WechatActivityLotteryItemDao wechatActivityLotteryItemDao) {
		this.wechatActivityLotteryItemDao = wechatActivityLotteryItemDao;
	}
	
	public WechatActivityNumDao getWechatActivityNumDao() {
		return wechatActivityNumDao;
	}

	@Resource
	public void setWechatActivityNumDao(WechatActivityNumDao wechatActivityNumDao) {
		this.wechatActivityNumDao = wechatActivityNumDao;
	}
	
	public WechatActivityLotteryPartDao getWechatActivityLotteryPartDao() {
		return wechatActivityLotteryPartDao;
	}

	@Resource
	public void setWechatActivityLotteryPartDao(WechatActivityLotteryPartDao wechatActivityLotteryPartDao) {
		this.wechatActivityLotteryPartDao = wechatActivityLotteryPartDao;
	}
}
