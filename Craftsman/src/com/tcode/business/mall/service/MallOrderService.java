package com.tcode.business.mall.service;

import java.util.List;

import com.tcode.business.mall.model.MallCart;
import com.tcode.business.mall.model.MallOrderHead;
import com.tcode.business.mall.model.MallOrderItem;
import com.tcode.business.member.model.Member;

public interface MallOrderService {
	
	public MallOrderHead getOrderHeadById(String orderId) throws Exception;

	public MallOrderHead insertMallOrder(String appId, Member member, Integer orderType, List<MallCart> cartList) throws Exception;
	
	public void updateHead(MallOrderHead orderHead) throws Exception;
	
	public void deleteHead(MallOrderHead orderHead) throws Exception;
	
	public List<MallOrderHead> getOrderListPage(MallOrderHead orderHead, int start, int limit) throws Exception;
	
	public Integer getOrderListCount(MallOrderHead orderHead) throws Exception;
	
	public List<MallOrderHead> getOrderDetailListPage(MallOrderHead orderHead, int start, int limit) throws Exception;
	
	public List<MallOrderItem> getOrderItemList(String orderId) throws Exception;
}
