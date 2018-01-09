package com.tcode.business.goods.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsMoveHead;
import com.tcode.business.goods.model.GoodsMoveItem;
import com.tcode.business.goods.service.GoodsMoveService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("goodsMoveAction")
public class GoodsMoveAction extends BaseAction {

	private static final long serialVersionUID = -7806180381242759905L;
	private static Logger log = Logger.getLogger("SLog");
	
	private GoodsMoveService goodsMoveService;
	
	private List<GoodsMoveHead> moveHeadList;
	private List<GoodsMoveItem> moveItemList;
	private GoodsMoveHead moveHead;
	private Integer moveId;
	private String goodsString;
	private String deptIn;
	private Double total;
	
	/**
	 * 调拨出库
	 * @return
	 */
	public String submitGoodsMoveOut(){
		try {
			if(!Utils.isEmpty(goodsString) && !Utils.isEmpty(deptIn)){
				if(this.getDept().getDeptType() == 3){
					List<GoodsMoveItem> itemList = new ArrayList<GoodsMoveItem>();
					JSONArray goodsArray = JSONArray.fromObject(goodsString);
					//商品列表
					for (Object object : goodsArray) {
						JSONObject json = (JSONObject) object;
						GoodsMoveItem moveItem = (GoodsMoveItem) JSONObject.toBean(json, GoodsMoveItem.class);
						itemList.add(moveItem);
					}
					GoodsMoveHead moveHead = new GoodsMoveHead();
					moveHead.setDeptOut(this.getDept().getDeptCode());
					moveHead.setDeptIn(deptIn);
					moveHead.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
					moveHead.setCreator(this.getUser().getRealName());
					moveHead.setStatus(0);
					moveHead.setTotal(total);
					
					goodsMoveService.insertGoodsMove(moveHead, itemList);
					this.setResult(true, "调拨成功！");
				} else
					this.setResult(false, "请使用门店账号进行调拨！");
			} else
				this.setResult(false, "缺少参数，调拨失败！");
		} catch(Exception e) {
			this.setResult(false, "系统调试中，请联系管理员！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 调入查询
	 * @return
	 */
	public String queryGoodsMoveInList(){
		try {
			moveHeadList = goodsMoveService.getByMoveIn(this.getDept().getDeptCode());
			this.setResult(true, "");
		} catch(Exception e) {
			this.setResult(false, "系统调试中，请联系管理员！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据ID查询调入商品详细信息
	 * @return
	 */
	public String queryGoodsMoveItemById(){
		try {
			if(!Utils.isEmpty(moveId))
				moveItemList = goodsMoveService.getItemListByMoveId(moveId);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 调拨入库
	 * @return
	 */
	public String submitGoodsMoveIn(){
		try {
			if(!Utils.isEmpty(moveId)){
				goodsMoveService.updateReceiptGoodsMove(moveId, this.getUser().getUserName());
				this.setResult(true, "");
			} else
				this.setResult(false, "缺少参数，收货失败！");
		} catch(Exception e) {
			this.setResult(false, "系统调试中，请联系管理员！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	/**
	 * 调拨查询
	 * @return
	 */
	public String queryGoodsMovePage(){
		try {
			if(Utils.isEmpty(moveHead))
				moveHead = new GoodsMoveHead();
			moveHead.setDept(this.getDept().getDeptCode());
			this.setTotalCount(goodsMoveService.getListByCount(moveHead));
			moveHeadList = goodsMoveService.getListByPage(moveHead, this.getStart(), this.getLimit());
			this.setResult(true, ""); 
		} catch(Exception e) {
			this.setResult(false, "系统调试中，请联系管理员！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	

	public GoodsMoveService getGoodsMoveService() {
		return goodsMoveService;
	}
	@Resource
	public void setGoodsMoveService(GoodsMoveService goodsMoveService) {
		this.goodsMoveService = goodsMoveService;
	}
	public String getGoodsString() {
		return goodsString;
	}
	public void setGoodsString(String goodsString) {
		this.goodsString = goodsString;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public List<GoodsMoveItem> getMoveItemList() {
		return moveItemList;
	}
	public void setMoveItemList(List<GoodsMoveItem> moveItemList) {
		this.moveItemList = moveItemList;
	}
	public String getDeptIn() {
		return deptIn;
	}
	public void setDeptIn(String deptIn) {
		this.deptIn = deptIn;
	}
	public List<GoodsMoveHead> getMoveHeadList() {
		return moveHeadList;
	}
	public void setMoveHeadList(List<GoodsMoveHead> moveHeadList) {
		this.moveHeadList = moveHeadList;
	}
	public Integer getMoveId() {
		return moveId;
	}
	public void setMoveId(Integer moveId) {
		this.moveId = moveId;
	}
	public GoodsMoveHead getMoveHead() {
		return moveHead;
	}
	public void setMoveHead(GoodsMoveHead moveHead) {
		this.moveHead = moveHead;
	}
	
}
