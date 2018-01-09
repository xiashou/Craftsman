package com.tcode.business.goods.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsCheckRecord;
import com.tcode.business.goods.model.GoodsStock;
import com.tcode.business.goods.service.GoodsCheckRecordService;
import com.tcode.business.goods.service.GoodsHourTypeService;
import com.tcode.business.goods.service.GoodsMaterialService;
import com.tcode.business.goods.service.GoodsStockService;
import com.tcode.business.shop.service.SupplierService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("goodsStockAction")
public class GoodsStockAction extends BaseAction {

	private static final long serialVersionUID = -1083306736409482163L;
	private static Logger log = Logger.getLogger("SLog");
	
	private GoodsStockService goodsStockService;
	private GoodsMaterialService goodsMaterialService;
	private GoodsHourTypeService goodsHourTypeService;
	private GoodsCheckRecordService goodsCheckRecordService;
	private SupplierService supplierService;
	
	private GoodsStock stock;
	private List<GoodsStock> stockList;
	
	private String keyword;
	
	/**
	 * 根据条件分页查询门店库存
	 * @return
	 */
	public String queryGoodsStockByDeptPage() {
		try {
			if(Utils.isEmpty(stock))
				stock = new GoodsStock();
			stock.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(goodsStockService.getCountByDeptPage(stock));
			stockList = goodsStockService.getListByDeptPage(stock, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据关键字查询库存
	 * @return
	 */
	public String queryGoodsStockByKeyword() {
		try {
			if(!Utils.isEmpty(keyword)){
				stockList = goodsStockService.getListByKeyword(this.getDept().getDeptCode(), keyword);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据部门查询库存
	 * @return
	 */
	public String queryGoodsStockByDept() {
		try {
			stockList = goodsStockService.getListByDept(this.getDept().getDeptCode());
			this.setResult(true, "");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "查询库存出错！");
		}
		return SUCCESS;
	}
	
	
	/**
	 * 调整库存
	 * @return
	 */
	public String updateGoodsStock() {
		try {
			if(!Utils.isEmpty(stock)){
				if(!Utils.isEmpty(stock.getGoodsId())){
					GoodsStock goodsStock = goodsStockService.getGoodsStockById(stock.getGoodsId(), this.getDept().getDeptCode());
					if(!Utils.isEmpty(goodsStock)){
						GoodsCheckRecord checkRecord = new GoodsCheckRecord(this.getDept().getDeptCode(), stock.getGoodsId(), Double.parseDouble(goodsStock.getNumber() + ""), 
								Double.parseDouble(stock.getNumber() + ""), this.getUser().getUserName(), new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
						goodsCheckRecordService.insert(checkRecord);
						stock.setDeptCode(this.getDept().getDeptCode());
						goodsStockService.update(stock);
						this.setResult(true, "调整成功！");
					} else
						this.setResult(false, "调整失败，找不到库存记录！");
				} else
					this.setResult(false, "调整失败，ID为空！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "调整库存出错！");
		}
		return SUCCESS;
	}
	

	public GoodsStockService getGoodsStockService() {
		return goodsStockService;
	}
	@Resource
	public void setGoodsStockService(GoodsStockService goodsStockService) {
		this.goodsStockService = goodsStockService;
	}
	public GoodsMaterialService getGoodsMaterialService() {
		return goodsMaterialService;
	}
	@Resource
	public void setGoodsMaterialService(GoodsMaterialService goodsMaterialService) {
		this.goodsMaterialService = goodsMaterialService;
	}
	public GoodsHourTypeService getGoodsHourTypeService() {
		return goodsHourTypeService;
	}
	@Resource
	public void setGoodsHourTypeService(GoodsHourTypeService goodsHourTypeService) {
		this.goodsHourTypeService = goodsHourTypeService;
	}
	public SupplierService getSupplierService() {
		return supplierService;
	}
	@Resource
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public GoodsCheckRecordService getGoodsCheckRecordService() {
		return goodsCheckRecordService;
	}
	@Resource
	public void setGoodsCheckRecordService(GoodsCheckRecordService goodsCheckRecordService) {
		this.goodsCheckRecordService = goodsCheckRecordService;
	}

	public GoodsStock getStock() {
		return stock;
	}
	public void setStock(GoodsStock stock) {
		this.stock = stock;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<GoodsStock> getStockList() {
		return stockList;
	}
	public void setStockList(List<GoodsStock> stockList) {
		this.stockList = stockList;
	}
}
