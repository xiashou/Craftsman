package com.tcode.business.order.util;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.order.model.OrderHead;
import com.tcode.business.order.model.OrderItem;
import com.tcode.business.shop.model.Param;
import com.tcode.business.shop.service.ParamService;

@Component
public class OrderUtil {
	
	private static ParamService paramService;

	/**
	 * 得到当前订单支付方式
	 * @return
	 */
	public static String initPayType(OrderHead orderHead) {
		StringBuffer payType = new StringBuffer();
		Integer poffset = orderHead.getPoffset();//抵现积分
		Double pdeposit = orderHead.getPdeposit();//支付定金
		Double pbalance = orderHead.getPbalance();//支付余额
		Double pcash = orderHead.getPcash();//支付现金
		Double pcard = orderHead.getPcard();//支付刷卡
		Double ptransfer = orderHead.getPtransfer();//支付转账
		Double pwechat = orderHead.getPwechat();//支付微信
		Double palipay = orderHead.getPalipay();//支付其他
		Double pbill = orderHead.getPbill();//支付挂账
		
		if(poffset != null && poffset > 0) payType.append("积分");
		if(pdeposit != null && pdeposit > 0) {
			if(payType.length() > 0) payType.append("，");
			payType.append("定金");
		}
		if(pbalance != null && pbalance > 0) {
			if(payType.length() > 0) payType.append("，");
			payType.append("余额");
		}
		if(pcash != null && pcash > 0) {
			if(payType.length() > 0) payType.append("，");
			payType.append("现金");
		}
		if(pcard != null && pcard > 0) {
			if(payType.length() > 0) payType.append("，");
			payType.append("刷卡");
		}
		if(ptransfer != null && ptransfer > 0) {
			if(payType.length() > 0) payType.append("，");
			payType.append("转账");
		}
		if(pwechat != null && pwechat > 0) {
			if(payType.length() > 0) payType.append("，");
			payType.append("微信");
		}
		if(palipay != null && palipay > 0) {
			if(payType.length() > 0) payType.append("，");
			payType.append("支付宝");
		}
		if(pbill != null && pbill > 0) {
			if(payType.length() > 0) payType.append("，");
			payType.append("挂账");
		}
		return payType.toString();
	}
	
	/**
	 * 得到消费项目
	 * @param orderItemList
	 * @return
	 */
	public static String initConsumerItem(List<OrderItem> orderItemList) {
		OrderItem orderItem = null;
		StringBuffer consumerItem = new StringBuffer();
		int size = orderItemList.size();
		for(int i=0; i<size; i++) {
			if(i == 3) {//只显示前三个项目
				consumerItem.append("等");
				break;
			}
			orderItem = orderItemList.get(i);
			consumerItem.append(orderItem.getGoodsName());
			if(i < 2 && i<size -1) consumerItem.append("，");
			
		}
		return consumerItem.toString();
	}
	
	/**
	 * 初始化积分消费情况
	 * @return
	 * @throws Exception 
	 */
	public static String initPointInfo(OrderHead orderHead) throws Exception {
		StringBuffer pointInfo = new StringBuffer();
		Integer poffset = orderHead.getPoffset();//抵现积分
		String deptCode = orderHead.getDeptCode();
		if(poffset != null && poffset > 0) {
			DecimalFormat decimalFormat = new DecimalFormat("###.##");//格式化设置 
			Param param = paramService.getById(deptCode);
			String money = decimalFormat.format(poffset/Double.parseDouble(param.getParam2()));
			pointInfo.append("扣减积分：").append(poffset)
			.append("，抵除金额：").append(money).append("元。");
		}
		return pointInfo.toString();
	}
	

	public ParamService getParamService() {
		return paramService;
	}

	@Resource
	public void setParamService(ParamService paramService) {
		OrderUtil.paramService = paramService;
	}
	
}
