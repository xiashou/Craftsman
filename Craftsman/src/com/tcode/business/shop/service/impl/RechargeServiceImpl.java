package com.tcode.business.shop.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.goods.dao.GoodsHourDao;
import com.tcode.business.goods.dao.GoodsMaterialDao;
import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.member.dao.MemberDao;
import com.tcode.business.member.dao.MemberStockDao;
import com.tcode.business.member.model.MemberStock;
import com.tcode.business.order.model.OrderHead;
import com.tcode.business.report.dao.ReptMemberStockDao;
import com.tcode.business.report.dao.ReptRechargeDao;
import com.tcode.business.shop.dao.RechargeDao;
import com.tcode.business.shop.dao.RechargeDetailDao;
import com.tcode.business.shop.model.Recharge;
import com.tcode.business.shop.model.RechargeDetail;
import com.tcode.business.shop.service.RechargeService;
import com.tcode.core.util.Utils;

@Component("rechargeService")
public class RechargeServiceImpl implements RechargeService {
	
	private RechargeDao rechargeDao;
	private RechargeDetailDao rechargeDetailDao;
	private ReptRechargeDao reptRechargeDao;
	private MemberStockDao memberStockDao;
	private MemberDao memberDao;
	private GoodsHourDao goodsHourDao;
	private GoodsMaterialDao goodsMaterialDao;
	private ReptMemberStockDao reptMemberStockDao;

	@Override
	public Recharge getById(Integer id) throws Exception {
		return rechargeDao.loadById(id);
	}

	@Override
	public List<Recharge> getRechargeListByDept(String deptCode) throws Exception {
		return rechargeDao.loadByDept(deptCode);
	}
	
	@Override
	public List<RechargeDetail> getDetailListByRecharge(Integer rechargeId) throws Exception {
		return rechargeDetailDao.loadByRechargeId(rechargeId);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void updateMemberStockByRecharge(OrderHead orderHead, Integer rechargeId, String detailItem) throws Exception {
		List<RechargeDetail> dList = rechargeDetailDao.loadByRechargeId(rechargeId);
		reptRechargeDao.addRecord(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getOrderId(), "B", "充值", orderHead.getAprice());
		for(RechargeDetail detail : dList) {
			if(detailItem.indexOf(detail.getItemNo() + "") >= 0){
				if(detail.getType() == 1) {	//金额
					memberDao.editMemberBalance(orderHead.getMemId(), Double.parseDouble(detail.getValue()));
					reptRechargeDao.addRecord(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getOrderId(), "M", "余额", Double.parseDouble(detail.getValue()));
				} else if(detail.getType() >= 2 && detail.getType() <= 4) {	//2:服务 3:商品  4:卡券
					String endDate = "9999/12/30";
					if(!Utils.isEmpty(detail.getDays())){
						Calendar now = Calendar.getInstance();  
				        now.setTime(new Date());
				        now.set(Calendar.DATE, now.get(Calendar.DATE) + detail.getDays());
				        endDate = new SimpleDateFormat("yyyy/MM/dd").format(now.getTime());
					}
					//根据value查询商品类型，卡券类类型为R(recharge)
					String typeId = "";
					if(detail.getType() == 2){
						GoodsHour hour = goodsHourDao.loadById(detail.getValue());
						if(!Utils.isEmpty(hour))
							typeId = hour.getTypeId() + "";
					} else if(detail.getType() == 3) {
						GoodsMaterial material = goodsMaterialDao.loadById(detail.getValue());
						if(!Utils.isEmpty(material))
							typeId = material.getTypeId() + "";
					} else {
						typeId = "O";
					}
					MemberStock exist = memberStockDao.loadById(orderHead.getMemId(), detail.getValue(), endDate, "");
					if(!Utils.isEmpty(exist))
						memberStockDao.editStockNumber(orderHead.getMemId(), detail.getValue(), endDate, detail.getNumber(), "");
					else {
						MemberStock memberStock = new MemberStock();
						memberStock.setMemId(orderHead.getMemId());
						memberStock.setGoodsId(detail.getValue());
						memberStock.setGoodsType(detail.getType() == 2 ? 3 : detail.getType() == 3 ? 4 : 5);
						memberStock.setGoodsName(detail.getDname());
						memberStock.setTypeId(typeId);
						memberStock.setEndDate(endDate);
						memberStock.setNumber(detail.getNumber());
						memberStock.setSource("");
						memberStockDao.save(memberStock);
					}
					//记录一笔会员充值流水
					reptRechargeDao.addRecord(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getOrderId(), detail.getValue(), detail.getDname(), detail.getNumber());
					//记录一笔会员库存流水
					reptMemberStockDao.addRecord(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getOrderId(), detail.getValue(), detail.getDname(), detail.getNumber());
				} else if(detail.getType() == 5) {	//积分
					memberDao.editMemberPoint(orderHead.getMemId(), Integer.parseInt(detail.getValue()));
					reptRechargeDao.addRecord(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getOrderId(), "P", "积分", Double.parseDouble(detail.getValue()));
				} else if(detail.getType() == 6) {	//等级
					memberDao.editMemberGrade(orderHead.getMemId(), Integer.parseInt(detail.getValue()));
					reptRechargeDao.addRecord(orderHead.getDeptCode(), orderHead.getMemId(), orderHead.getOrderId(), "G", "等级", Double.parseDouble(detail.getValue()));
				}
			}
		}
	}
	
	
	
	

	@Override
	public void insert(Recharge recharge) throws Exception {
		rechargeDao.save(recharge);
	}

	@Override
	public void update(Recharge recharge) throws Exception {
		rechargeDao.edit(recharge);
	}

	@Override
	public void delete(Recharge recharge) throws Exception {
		rechargeDao.remove(recharge);
	}
	
	@Override
	public void deleteRechargeDetail(Integer rechargeId) throws Exception {
		rechargeDetailDao.removeByRechargeId(rechargeId);
	}
	
	@Override
	public void insertDetail(RechargeDetail rechargeDetail) throws Exception {
		rechargeDetailDao.save(rechargeDetail);
	}
	@Override
	public void updateDetail(RechargeDetail rechargeDetail) throws Exception {
		rechargeDetailDao.edit(rechargeDetail);
	}
	@Override
	public void deleteDetail(RechargeDetail rechargeDetail) throws Exception {
		rechargeDetailDao.remove(rechargeDetail);
	}
	
	
	
	
	
	
	
	
	public RechargeDao getRechargeDao() {
		return rechargeDao;
	}
	@Resource
	public void setRechargeDao(RechargeDao rechargeDao) {
		this.rechargeDao = rechargeDao;
	}
	public RechargeDetailDao getRechargeDetailDao() {
		return rechargeDetailDao;
	}
	@Resource
	public void setRechargeDetailDao(RechargeDetailDao rechargeDetailDao) {
		this.rechargeDetailDao = rechargeDetailDao;
	}
	public MemberStockDao getMemberStockDao() {
		return memberStockDao;
	}
	@Resource
	public void setMemberStockDao(MemberStockDao memberStockDao) {
		this.memberStockDao = memberStockDao;
	}
	public MemberDao getMemberDao() {
		return memberDao;
	}
	@Resource
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	public GoodsHourDao getGoodsHourDao() {
		return goodsHourDao;
	}
	@Resource
	public void setGoodsHourDao(GoodsHourDao goodsHourDao) {
		this.goodsHourDao = goodsHourDao;
	}
	public GoodsMaterialDao getGoodsMaterialDao() {
		return goodsMaterialDao;
	}
	@Resource
	public void setGoodsMaterialDao(GoodsMaterialDao goodsMaterialDao) {
		this.goodsMaterialDao = goodsMaterialDao;
	}
	public ReptRechargeDao getReptRechargeDao() {
		return reptRechargeDao;
	}
	@Resource
	public void setReptRechargeDao(ReptRechargeDao reptRechargeDao) {
		this.reptRechargeDao = reptRechargeDao;
	}
	public ReptMemberStockDao getReptMemberStockDao() {
		return reptMemberStockDao;
	}
	@Resource
	public void setReptMemberStockDao(ReptMemberStockDao reptMemberStockDao) {
		this.reptMemberStockDao = reptMemberStockDao;
	}


}
