package com.tcode.business.goods.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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

import com.tcode.business.goods.model.GoodsMaterial;
import com.tcode.business.goods.service.GoodsMaterialService;
import com.tcode.common.action.BaseAction;
import com.tcode.common.idgenerator.IDHelper;
import com.tcode.core.util.Pinyin;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("goodsMaterialAction")
public class GoodsMaterialAction extends BaseAction {

	private static final long serialVersionUID = -7806180381242759905L;
	private static Logger log = Logger.getLogger("SLog");
	
	private GoodsMaterialService goodsMaterialService;
	
	private List<GoodsMaterial> materialList;
	private Integer typeId;
	private String keyword;
	private GoodsMaterial material;
	
	private String uploadFileName;
	private File upload;
	
	private String jsonStr;
	
	/**
	 * 根据类型查询实物类商品
	 * @return
	 */
	public String queryGoodsMaterialByType() {
		try {
			if(!Utils.isEmpty(typeId)){
				materialList = goodsMaterialService.getGoodsMaterialByTypeKeyword(this.getDept().getCompanyId(), typeId, keyword);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据类型查询实物类商品(带最后一次进货价)
	 * @return
	 */
	public String queryGoodsMaterialPriceByType() {
		try {
			if(!Utils.isEmpty(typeId)){
				materialList = goodsMaterialService.getGoodsMaterialByType(typeId);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据门店查询实物类商品
	 * @return
	 */
	public String queryGoodsMaterialByDept() {
		try {
			materialList = goodsMaterialService.getGoodsMaterialByDept(this.getDept().getCompanyId());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据关键字查询实物类商品
	 * @return
	 */
	public String queryGoodsMaterialByKeyword() {
		try {
			if(!Utils.isEmpty(typeId) || !Utils.isEmpty(keyword)){
				materialList = goodsMaterialService.getGoodsMaterialByTypeKeyword(this.getDept().getCompanyId(), typeId, keyword);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据关键字查询实物类商品(带库存)
	 * @return
	 */
	public String queryGoodsMaterialStockByKeyword() {
		try {
			if(!Utils.isEmpty(typeId) || !Utils.isEmpty(keyword))
				materialList = goodsMaterialService.getGoodsMaterialStockByTypeKeyword(this.getDept().getCompanyId(), this.getDept().getDeptCode(), typeId, keyword);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据id查找商品
	 * @return
	 */
	public String getGoodsMaterialById() {
		try {
			if(!Utils.isEmpty(material) && !Utils.isEmpty(material.getId())){
				material = goodsMaterialService.getGoodsMaterialById(this.getDept().getCompanyId(), material.getId());
				materialList = new ArrayList<GoodsMaterial>();
				materialList.add(material);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加实物类商品
	 * @return
	 */
	public String insertGoodsMaterial() {
		try {
			if(!Utils.isEmpty(material)){
				GoodsMaterial search = new GoodsMaterial();
				search.setDeptCode(this.getDept().getCompanyId());
				search.setName(material.getName());
				search.setCode(material.getCode());
				search.setTypeId(material.getTypeId());
				int count = goodsMaterialService.getListCount(search);
				if(count==0){
					material.setId(IDHelper.getMaterialID());
					material.setDeptCode(this.getDept().getCompanyId());
					material.setShorthand(Pinyin.getPinYinHeadChar(material.getName()));
					goodsMaterialService.insert(material);
					this.setResult(true, "添加成功！");
				}else{
					this.setResult(false, "商品名称和编码不能重复！");
				}
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改实物类商品
	 * @return
	 */
	public String updateGoodsMaterial() {
		try {
			if(!Utils.isEmpty(material) && !Utils.isEmpty(material.getId())){
				material.setDeptCode(this.getDept().getCompanyId());
				material.setShorthand(Pinyin.getPinYinHeadChar(material.getName()));
				goodsMaterialService.update(material);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除实物类商品
	 * @return
	 */
	public String deleteGoodsMaterial() {
		try {
			if(!Utils.isEmpty(material) && !Utils.isEmpty(material.getId())){
				goodsMaterialService.delete(material);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 保存商品零售价
	 * @return
	 */
	public String saveGoodsMaterialPrice() {
		try {
			if(!Utils.isEmpty(jsonStr)){
				JSONArray array = JSONArray.fromObject(jsonStr);
				for (Object object : array) {
					JSONObject json = (JSONObject) object;
					GoodsMaterial goods = (GoodsMaterial) JSONObject.toBean(json, GoodsMaterial.class);
					if(!Utils.isEmpty(goods.getId()) && !Utils.isEmpty(goods.getPrice())){
						GoodsMaterial exist = goodsMaterialService.getById(goods.getId());
						if(!Utils.isEmpty(exist)){
							exist.setPrice(goods.getPrice());
							goodsMaterialService.update(exist);
						}
					}
				}
				this.setResult(true, "保存成功！");
			}
		} catch(Exception e) {
			this.setResult(false, Utils.getErrorMessage(e));
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 批量导入实物商品档案
	 * @return
	 */
	public String importGoodsMaterial() {
		try {
			Workbook workbook = null;
			if (this.uploadFileName.toLowerCase().endsWith("xls"))
				workbook = new HSSFWorkbook(new FileInputStream(this.upload));
			if (this.uploadFileName.toLowerCase().endsWith("xlsx"))
				workbook = new XSSFWorkbook(new FileInputStream(this.upload));
			Sheet sheet = workbook.getSheetAt(0);
			this.materialList = new ArrayList<GoodsMaterial>();
			GoodsMaterial goods = null;
			GoodsMaterial existGoods = null;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				existGoods = new GoodsMaterial();
				goods = new GoodsMaterial();
				if (row.getCell(0) != null) {
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					goods.setName(row.getCell(0).getStringCellValue().trim().replaceAll("	", ""));
					existGoods = goodsMaterialService.getGoodsMaterialByName(this.getDept().getCompanyId(), goods.getName());
					if(!Utils.isEmpty(existGoods)){
						
						if (!Utils.isEmpty(row.getCell(1))) {
							row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
							existGoods = goodsMaterialService.getGoodsMaterialByCode(this.getDept().getCompanyId(), row.getCell(1).getStringCellValue().trim().replaceAll("	", ""));
							if(!Utils.isEmpty(existGoods)){
								this.setResult(false, goods.getCode().trim() + " 编码已存在！");
								return SUCCESS;
							}
						} else {
							this.setResult(false, goods.getName().trim() + " 编码不能为空！");
							return SUCCESS;
						}
					}
				}
				if (row.getCell(1) != null) {
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					goods.setCode(row.getCell(1).getStringCellValue().trim().replaceAll("	", ""));
					existGoods = goodsMaterialService.getGoodsMaterialByCode(this.getDept().getCompanyId(), goods.getCode());
					if(!Utils.isEmpty(existGoods)){
						this.setResult(false, goods.getCode().trim() + " 编码已存在！");
						return SUCCESS;
					}
				}
				if (row.getCell(2) != null) {
					row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					goods.setPrice(Double.parseDouble(row.getCell(2).getStringCellValue()));
				}
				if (row.getCell(3) != null) {
					row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					goods.setSpec(row.getCell(3).getStringCellValue());
				}
				if (row.getCell(4) != null){
					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					goods.setColor(row.getCell(4).getStringCellValue());
				}
				if (row.getCell(5) != null) {
					row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
					goods.setSize(row.getCell(5).getStringCellValue());
				}
				if (row.getCell(6) != null){
					row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
					goods.setUnit(row.getCell(6).getStringCellValue());
				}
				if (row.getCell(7) != null){
					row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
					goods.setSuitModel(row.getCell(7).getStringCellValue());
				}
				if (row.getCell(8) != null) {
					row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
					goods.setRemark(row.getCell(8).getStringCellValue());
				}
				goods.setDeptCode(this.getDept().getCompanyId());
				goods.setShorthand(Pinyin.getPinYinHeadChar(goods.getName()));
				goods.setTypeId(typeId);
				goods.setId(IDHelper.getMaterialID());
				
				this.materialList.add(goods);
			}
			int count = this.goodsMaterialService.insertMoreGoodsMaterial(materialList);
			this.setResult(true, "成功添加 " + count + " 条记录！");
		} catch (OldExcelFormatException e) {
			this.setResult(false, "添加失败！Excel版本太旧，请使用 97/2003 版本！");
		} catch (Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public GoodsMaterialService getGoodsMaterialService() {
		return goodsMaterialService;
	}
	@Resource
	public void setGoodsMaterialService(GoodsMaterialService goodsMaterialService) {
		this.goodsMaterialService = goodsMaterialService;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public List<GoodsMaterial> getMaterialList() {
		return materialList;
	}
	public void setMaterialList(List<GoodsMaterial> materialList) {
		this.materialList = materialList;
	}
	public GoodsMaterial getMaterial() {
		return material;
	}
	public void setMaterial(GoodsMaterial material) {
		this.material = material;
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
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
}
