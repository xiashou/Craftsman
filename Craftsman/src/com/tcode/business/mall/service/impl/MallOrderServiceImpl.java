package com.tcode.business.mall.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.mall.dao.MallCartDao;
import com.tcode.business.mall.dao.MallGoodsDao;
import com.tcode.business.mall.dao.MallOrderHeadDao;
import com.tcode.business.mall.dao.MallOrderItemDao;
import com.tcode.business.mall.dao.MallSettingDao;
import com.tcode.business.mall.model.MallCart;
import com.tcode.business.mall.model.MallGoods;
import com.tcode.business.mall.model.MallOrderHead;
import com.tcode.business.mall.model.MallOrderItem;
import com.tcode.business.mall.model.MallSetting;
import com.tcode.business.mall.service.MallOrderService;
import com.tcode.business.member.model.Member;
import com.tcode.core.util.Utils;

@Component("mallOrderService")
public class MallOrderServiceImpl implements MallOrderService {
	
	private MallOrderHeadDao mallOrderHeadDao;
	private MallOrderItemDao mallOrderItemDao;
	private MallSettingDao mallSettingDao;
	private MallGoodsDao mallGoodsDao;
	private MallCartDao mallCartDao;
	
	
	@Override
	public MallOrderHead getOrderHeadById(String orderId) throws Exception {
		return mallOrderHeadDao.loadById(orderId);
	}
	
	@Override
	public void updateHead(MallOrderHead orderHead) throws Exception {
		mallOrderHeadDao.edit(orderHead);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void deleteHead(MallOrderHead orderHead) throws Exception {
		mallOrderItemDao.removeByOrderId(orderHead.getOrderId());
		mallOrderHeadDao.remove(orderHead);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public MallOrderHead insertMallOrder(String appId, Member member, Integer orderType, List<MallCart> cartList) throws Exception {
		MallOrderHead orderHead = new MallOrderHead();
		orderHead.setOrderId(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		orderHead.setAppId(appId);
		orderHead.setMemId(member.getMemId());
		orderHead.setOpenId(member.getWechatNo());
		orderHead.setNickName(member.getWechatNick());
		orderHead.setOrderType(orderType);
		orderHead.setSaleDate(new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));
		orderHead.setStatus(1);		//待付款
		
		Double oprice = 0.0;
		Double aprice = 0.0;
		for(MallCart cartGoods : cartList){
			
			MallGoods goods = cartGoods.getGoods();
			if(Utils.isEmpty(goods))
				goods = mallGoodsDao.loadById(cartGoods.getGoodsId());
			
			MallOrderItem orderItem = new MallOrderItem();
			orderItem.setOrderId(orderHead.getOrderId());
			orderItem.setOprice(goods.getOprice());
			orderItem.setAprice(goods.getAprice());
			orderItem.setGoodsId(goods.getGoodsId());
			orderItem.setGoodsName(goods.getGoodsName());
			orderItem.setNumber(cartGoods.getNumber().intValue());
			orderItem.setSendMode(cartGoods.getSendMode());
			mallOrderItemDao.save(orderItem);
			
			oprice += goods.getOprice() * cartGoods.getNumber();
			aprice += goods.getAprice() * cartGoods.getNumber();
			
			mallCartDao.remove(cartGoods);
		}
		orderHead.setOprice(oprice);
		orderHead.setAprice(aprice);
		
		MallSetting setting = mallSettingDao.loadById(appId);
		if(!Utils.isEmpty(setting) && setting.getPointRule() != 0){
			orderHead.setPoint(aprice.intValue() / setting.getPointRule());
		}
		mallOrderHeadDao.save(orderHead);
		
		return orderHead;
	}
	
	@Override
	public List<MallOrderHead> getOrderListPage(MallOrderHead orderHead, int start, int limit) throws Exception {
		return mallOrderHeadDao.loadListPage(orderHead, start, limit);
	}
	
	@Override
	public Integer getOrderListCount(MallOrderHead orderHead) throws Exception {
		return mallOrderHeadDao.loadListCount(orderHead);
	}
	
	@Override
	public List<MallOrderHead> getOrderDetailListPage(MallOrderHead orderHead, int start, int limit) throws Exception {
		List<MallOrderHead> orderList = mallOrderHeadDao.loadListPage(orderHead, start, limit);
		for(MallOrderHead head : orderList) {
			head.setItemList(mallOrderItemDao.loadListByOrderId(head.getOrderId()));
		}
		return orderList;
	}
	
	@Override
	public List<MallOrderItem> getOrderItemList(String orderId) throws Exception {
		return mallOrderItemDao.loadListByOrderId(orderId);
	}

	public MallOrderHeadDao getMallOrderHeadDao() {
		return mallOrderHeadDao;
	}
	@Resource
	public void setMallOrderHeadDao(MallOrderHeadDao mallOrderHeadDao) {
		this.mallOrderHeadDao = mallOrderHeadDao;
	}

	public MallOrderItemDao getMallOrderItemDao() {
		return mallOrderItemDao;
	}
	@Resource
	public void setMallOrderItemDao(MallOrderItemDao mallOrderItemDao) {
		this.mallOrderItemDao = mallOrderItemDao;
	}

	public MallGoodsDao getMallGoodsDao() {
		return mallGoodsDao;
	}
	@Resource
	public void setMallGoodsDao(MallGoodsDao mallGoodsDao) {
		this.mallGoodsDao = mallGoodsDao;
	}

	public MallSettingDao getMallSettingDao() {
		return mallSettingDao;
	}
	@Resource
	public void setMallSettingDao(MallSettingDao mallSettingDao) {
		this.mallSettingDao = mallSettingDao;
	}

	public MallCartDao getMallCartDao() {
		return mallCartDao;
	}
	@Resource
	public void setMallCartDao(MallCartDao mallCartDao) {
		this.mallCartDao = mallCartDao;
	}

	
	
}
