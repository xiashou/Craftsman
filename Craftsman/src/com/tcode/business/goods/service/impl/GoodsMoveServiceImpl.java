package com.tcode.business.goods.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tcode.business.goods.dao.GoodsMoveHeadDao;
import com.tcode.business.goods.dao.GoodsMoveItemDao;
import com.tcode.business.goods.dao.GoodsStockDao;
import com.tcode.business.goods.model.GoodsMoveHead;
import com.tcode.business.goods.model.GoodsMoveItem;
import com.tcode.business.goods.service.GoodsMoveService;
import com.tcode.core.util.Utils;
import com.tcode.system.dao.SysDeptDao;
import com.tcode.system.model.SysDept;

@Component("goodsMoveService")
public class GoodsMoveServiceImpl implements GoodsMoveService {
	
	private GoodsMoveHeadDao goodsMoveHeadDao;
	private GoodsMoveItemDao goodsMoveItemDao;
	private GoodsStockDao goodsStockDao;
	private SysDeptDao sysDeptDao;

	@Override
	public List<GoodsMoveHead> getByMoveIn(String moveIn) throws Exception {
		List<GoodsMoveHead> headList = goodsMoveHeadDao.loadByMoveIn(moveIn);
		if(!Utils.isEmpty(headList)){
			for(GoodsMoveHead moveHead : headList){
				moveHead.setDeptInName(sysDeptDao.loadByDeptCode(moveHead.getDeptIn()).getDeptName());
				SysDept dept = sysDeptDao.loadByDeptCode(moveHead.getDeptOut());
				moveHead.setDeptOutName(Utils.isEmpty(dept) ? "" : dept.getDeptName());
			}
		}
		return headList;
	}

	@Override
	public List<GoodsMoveHead> getByMoveOut(String moveOut) throws Exception {
		return goodsMoveHeadDao.loadByMoveOut(moveOut);
	}
	
	@Override
	public List<GoodsMoveItem> getItemListByMoveId(Integer id) throws Exception {
		return goodsMoveItemDao.loadByMoveId(id);
	}

	@Override
	public List<GoodsMoveHead> getByDept(String dept) throws Exception {
		return null;
	} 

	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void insertGoodsMove(GoodsMoveHead moveHead, List<GoodsMoveItem> moveItemList) throws Exception {
		if(!Utils.isEmpty(moveHead)){
			goodsMoveHeadDao.save(moveHead);
			if(!Utils.isEmpty(moveItemList)){
				int itemNo = 1;
				for(GoodsMoveItem moveItem : moveItemList){
					moveItem.setMoveId(moveHead.getId());
					moveItem.setItemNo(itemNo);
					goodsMoveItemDao.save(moveItem);
					goodsStockDao.editByGoodsId(moveHead.getDeptOut(), moveItem.getGoodsId(), moveItem.getNumber());
					itemNo++;
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor={Exception.class, RuntimeException.class})
	public void updateReceiptGoodsMove(Integer moveId, String userName) throws Exception {
		GoodsMoveHead moveHead = goodsMoveHeadDao.loadById(moveId);
		if(!Utils.isEmpty(moveHead)){
			List<GoodsMoveItem> itemList = goodsMoveItemDao.loadByMoveId(moveId);
			if(!Utils.isEmpty(itemList)){
				for(GoodsMoveItem moveItem : itemList){
					goodsStockDao.editByGoodsId(moveHead.getDeptIn(), moveItem.getGoodsId(), moveItem.getNumber() * -1);
				}
				moveHead.setStatus(1);
				moveHead.setUpdator(userName);
				moveHead.setUpdateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				goodsMoveHeadDao.edit(moveHead);
			}
		} else
			System.out.println(moveHead.getId() + "找不到相关数据！");
	}
	
	@Override
	public List<GoodsMoveHead> getListByPage(GoodsMoveHead moveHead, int start, int limit) throws Exception {
		List<GoodsMoveHead> headList = goodsMoveHeadDao.loadListByPage(moveHead, start, limit);
		for(GoodsMoveHead head : headList){
			head.setDeptInName(sysDeptDao.loadByDeptCode(head.getDeptIn()).getDeptName());
			SysDept dept = sysDeptDao.loadByDeptCode(head.getDeptOut());
			head.setDeptOutName(Utils.isEmpty(dept) ? "" : dept.getDeptName());
//			head.setDeptOutName(sysDeptDao.loadByDeptCode(head.getDeptOut()).getDeptName());
		}
		return headList;
	}
	
	@Override
	public Integer getListByCount(GoodsMoveHead moveHead) throws Exception {
		return goodsMoveHeadDao.loadListByCount(moveHead);
	}
	
	
	public GoodsMoveHeadDao getGoodsMoveHeadDao() {
		return goodsMoveHeadDao;
	}
	@Resource
	public void setGoodsMoveHeadDao(GoodsMoveHeadDao goodsMoveHeadDao) {
		this.goodsMoveHeadDao = goodsMoveHeadDao;
	}

	public GoodsMoveItemDao getGoodsMoveItemDao() {
		return goodsMoveItemDao;
	}
	@Resource
	public void setGoodsMoveItemDao(GoodsMoveItemDao goodsMoveItemDao) {
		this.goodsMoveItemDao = goodsMoveItemDao;
	}
	public GoodsStockDao getGoodsStockDao() {
		return goodsStockDao;
	}
	@Resource
	public void setGoodsStockDao(GoodsStockDao goodsStockDao) {
		this.goodsStockDao = goodsStockDao;
	}
	public SysDeptDao getSysDeptDao() {
		return sysDeptDao;
	}
	@Resource
	public void setSysDeptDao(SysDeptDao sysDeptDao) {
		this.sysDeptDao = sysDeptDao;
	}

}
