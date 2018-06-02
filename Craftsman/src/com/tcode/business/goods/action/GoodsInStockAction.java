package com.tcode.business.goods.action;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsInStock;
import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.goods.model.GoodsStock;
import com.tcode.business.goods.service.GoodsHourTypeService;
import com.tcode.business.goods.service.GoodsInStockService;
import com.tcode.business.goods.service.GoodsMaterialService;
import com.tcode.business.goods.service.GoodsStockService;
import com.tcode.business.shop.model.Setting;
import com.tcode.business.shop.model.Supplier;
import com.tcode.business.shop.service.SettingService;
import com.tcode.business.shop.service.SupplierService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("goodsInStockAction")
public class GoodsInStockAction extends BaseAction {

	private static final long serialVersionUID = -7882329500237030L;
	private static Logger log = Logger.getLogger("SLog");
	
	private GoodsInStockService goodsInStockService;
	private GoodsStockService goodsStockService;
	private GoodsMaterialService goodsMaterialService;
	private GoodsHourTypeService goodsHourTypeService;
	private SupplierService supplierService;
	private SettingService settingService;
	
	private List<GoodsInStock> inStockList;
	private GoodsInStock inStock;
	private Setting setting;
	private String inNumber;
	
	private String jsonStr;
	private String uploadFileName;
	private File upload;
	
	/**
	 * 批量入库第一步 
	 * 解析导入Excel 放入list返回给前台grid展示
	 * 不涉及持久化操作
	 * @return
	 */
	public String analysisGoodsInStock() {
		try {
			Workbook workbook = null;
			if (this.uploadFileName.toLowerCase().endsWith("xls"))
				workbook = new HSSFWorkbook(new FileInputStream(this.upload));
			if (this.uploadFileName.toLowerCase().endsWith("xlsx"))
				workbook = new XSSFWorkbook(new FileInputStream(this.upload));
			Sheet sheet = workbook.getSheetAt(0);
			this.inStockList = new ArrayList<GoodsInStock>();
			GoodsInStock inStock = null;
			GoodsMaterial existGoods = null;
			Supplier supplier = null;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				existGoods = new GoodsMaterial();
				inStock = new GoodsInStock();
				if (row.getCell(0) != null) {
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					existGoods = goodsMaterialService.getGoodsMaterialByCode(this.getDept().getCompanyId(), row.getCell(0).getStringCellValue().trim());
					if(!Utils.isEmpty(existGoods)){
						inStock.setGoodsId(existGoods.getId());
						inStock.setGoodsName(existGoods.getName());
					} else {
						this.setResult(false, row.getCell(0).getStringCellValue() + " 编码不存在！");
						return SUCCESS;
					}
				}
				if (row.getCell(1) != null) {
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					inStock.setNumber(Double.parseDouble(row.getCell(1).getStringCellValue() + ""));
				}
				if (row.getCell(2) != null) {
					row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					inStock.setInPrice(Double.parseDouble(row.getCell(2).getStringCellValue()));
				}
				if (row.getCell(3) != null) {
					row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					supplier = supplierService.getByName(this.getDept().getDeptCode(), row.getCell(3).getStringCellValue());
					if(!Utils.isEmpty(supplier))
						inStock.setSupplier(supplier.getId());
					else {
						this.setResult(false, row.getCell(3).getStringCellValue() + " 供应商不存在！");
						return SUCCESS;
					}
				} else {
					this.setResult(false, "供应商不能为空！");
					return SUCCESS;
				}
//				if (row.getCell(4) != null) {
//					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
//					inStock.setSettlement(Integer.parseInt(row.getCell(4).getStringCellValue()));
//				}
//				if (row.getCell(5) != null) {
//					row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
//					inStock.setSettdate(row.getCell(5).getStringCellValue());
//				}
//				if (row.getCell(6) != null) {
//					row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
//					inStock.setPurchdate(row.getCell(6).getStringCellValue());
//				}
				this.inStockList.add(inStock);
			}
			this.setResult(true, "成功添加 ");
		} catch (OldExcelFormatException e) {
			this.setResult(false, "添加失败！Excel版本太旧，请使用 97/2003 版本！");
		} catch (Exception e) {
			this.setResult(false, "添加失败！" + Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 批量入库
	 * 把传入的json字符串转化成list
	 * 入库持久化，库存改变
	 * @return
	 */
	public String saveGoodsInStock() {
		try {
			if(!Utils.isEmpty(jsonStr)){
				JSONArray array = JSONArray.fromObject(jsonStr);
				this.inStockList = new ArrayList<GoodsInStock>();
				List<GoodsStock> stockList = new ArrayList<GoodsStock>();
				GoodsInStock inStock = null;
				GoodsStock stock = null;
				GoodsMaterial existGoods = null;
				inNumber = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				for (Object object : array) {
					existGoods = new GoodsMaterial();
					JSONObject json = (JSONObject) object;
					inStock = (GoodsInStock) JSONObject.toBean(json, GoodsInStock.class);
					if(!Utils.isEmpty(inStock)){
						
						existGoods = goodsMaterialService.getGoodsMaterialByIdCode(this.getDept().getCompanyId(), inStock.getGoodsId());
						if(!Utils.isEmpty(existGoods)){
							inStock.setGoodsId(existGoods.getId());
							inStock.setGoodsName(existGoods.getName());
						} else {
							this.setResult(false, inStock.getGoodsId() + " 商品不存在！");
							return SUCCESS;
						}
						
						inStock.setDeptCode(this.getDept().getDeptCode());
						inStock.setCreator(this.getUser().getUserId());
						inStock.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
						inStock.setInNumber(inNumber);
						this.inStockList.add(inStock);
						
						stock = new GoodsStock();
						stock.setDeptCode(this.getDept().getDeptCode());
						stock.setGoodsId(inStock.getGoodsId());
						stock.setName(inStock.getGoodsName());
						stock.setNumber(inStock.getNumber());
						stockList.add(stock);
					}
				}
				int count = goodsInStockService.insertMoreGoodsInStock(inStockList);
				if(count == inStockList.size())
					goodsStockService.insertMoreGoodsStock(stockList);
				this.setResult(true, "成功保存 " + inStockList.size() + " 条记录！");
			}
		} catch(Exception e) {
			this.setResult(false, Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询入库记录
	 * @return
	 */
	public String queryGoodsInStockListPage() {
		try {
			if(Utils.isEmpty(inStock))
				inStock = new GoodsInStock();
			inStock.setDeptCode(this.getDept().getDeptCode());
			inStock.setNumber(1.0);
			this.setTotalCount(goodsInStockService.getListCount(inStock));
			inStockList = goodsInStockService.getListPage(inStock, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询退货记录
	 * @return
	 */
	public String queryReturnGoodsInStockListPage() {
		try {
			if(Utils.isEmpty(inStock))
				inStock = new GoodsInStock();
			inStock.setDeptCode(this.getDept().getDeptCode());
			inStock.setNumber(-1.0);
			this.setTotalCount(goodsInStockService.getListCount(inStock));
			inStockList = goodsInStockService.getListPage(inStock, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 分页查询入库退货记录
	 * @return
	 */
	public String queryAllGoodsInStockListPage() {
		try {
			if(Utils.isEmpty(inStock))
				inStock = new GoodsInStock();
			inStock.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(goodsInStockService.getListCount(inStock));
			inStockList = goodsInStockService.getListPage(inStock, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 商品退货
	 * @return
	 */
	public String returnGoodsInStock() {
		try {
			if(!Utils.isEmpty(jsonStr)){
				JSONArray array = JSONArray.fromObject(jsonStr);
				this.inStockList = new ArrayList<GoodsInStock>();
				List<GoodsStock> stockList = new ArrayList<GoodsStock>();
				GoodsInStock inStock = null;
				GoodsStock stock = null;
				for (Object object : array) {
					JSONObject json = (JSONObject) object;
					inStock = (GoodsInStock) JSONObject.toBean(json, GoodsInStock.class);
					if(!Utils.isEmpty(inStock)){
						
						inStock.setDeptCode(this.getDept().getDeptCode());
						inStock.setNumber(inStock.getNumber() * -1);
						inStock.setCreator(this.getUser().getUserId());
						inStock.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
						this.inStockList.add(inStock);
						
						stock = new GoodsStock();
						stock.setDeptCode(this.getDept().getDeptCode());
						stock.setGoodsId(inStock.getGoodsId());
						stock.setName(inStock.getGoodsName());
						stock.setNumber(inStock.getNumber());
						stockList.add(stock);
					}
				}
				int count = goodsInStockService.insertReturnGoodsInStock(inStockList);
				if(count == inStockList.size())
					goodsStockService.insertMoreGoodsStock(stockList);
				this.setResult(true, "退货成功！");
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 打印入库单
	 * @return
	 */
	public String InStockPrint() {
		try {
			if(!Utils.isEmpty(inNumber))
				this.inStockList = goodsInStockService.getListByInNumber(inNumber);
			setting = settingService.getById(this.getDept().getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 打印退货单
	 * @return
	 */
	public String OutStockPrint() {
		try {
			if(!Utils.isEmpty(jsonStr)){
				JSONArray array = JSONArray.fromObject(jsonStr);
				this.inStockList = new ArrayList<GoodsInStock>();
				GoodsInStock inStock = null;
				inNumber = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				for (Object object : array) {
					JSONObject json = (JSONObject) object;
					inStock = (GoodsInStock) JSONObject.toBean(json, GoodsInStock.class);
					if(!Utils.isEmpty(inStock)){
						if(!Utils.isEmpty(inStock.getSupplier())){
							Supplier supplier = supplierService.getById(inStock.getSupplier());
							inStock.setSupplierName(supplier.getName());
						}
						inStock.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
						this.inStockList.add(inStock);
					}
				}
				setting = settingService.getById(this.getDept().getDeptCode());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String formatDouble(double s){
    	DecimalFormat fmt = new DecimalFormat("##0.00");
    	return fmt.format(s);
	}
	
	
	public GoodsInStockService getGoodsInStockService() {
		return goodsInStockService;
	}
	@Resource
	public void setGoodsInStockService(GoodsInStockService goodsInStockService) {
		this.goodsInStockService = goodsInStockService;
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
	public SettingService getSettingService() {
		return settingService;
	}
	@Resource
	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}

	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public List<GoodsInStock> getInStockList() {
		return inStockList;
	}
	public void setInStockList(List<GoodsInStock> inStockList) {
		this.inStockList = inStockList;
	}
	public GoodsInStock getInStock() {
		return inStock;
	}
	public void setInStock(GoodsInStock inStock) {
		this.inStock = inStock;
	}
	public String getInNumber() {
		return inNumber;
	}
	public void setInNumber(String inNumber) {
		this.inNumber = inNumber;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}
}
