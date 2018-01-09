package com.tcode.business.mall.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.model.GoodsPackage;
import com.tcode.business.goods.model.GoodsStock;
import com.tcode.business.mall.model.MallGoods;
import com.tcode.business.mall.service.MallGoodsService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("mallGoodsAction")
public class MallGoodsAction extends BaseAction {

	private static final long serialVersionUID = -3437444364698652107L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MallGoodsService mallGoodsService;
	
	private List<MallGoods> goodsList;
	private MallGoods goods;
	
	private Integer typeId;
	private Integer status;
	private String jsonStr;
	private File upload;
	private String uploadFileName;
	
	/**
	 * 分页查询商品
	 * @return
	 */
	public String queryMallGoodsByPage() {
		try {
			if(Utils.isEmpty(goods))
				goods = new MallGoods();
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(!Utils.isEmpty(wechatApp)) {
				goods.setAppId(wechatApp.getAuthorizerAppId());
				this.setTotalCount(mallGoodsService.getListCount(goods));
				goodsList = mallGoodsService.getListPage(goods, this.getStart(), this.getLimit());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String queryMallGoodsList() {
		try {
			if(Utils.isEmpty(goods))
				goods = new MallGoods();
			WechatAuthorizerParams wechatApp = this.getWechatApp();
			if(!Utils.isEmpty(wechatApp)) {
				goods.setAppId(wechatApp.getAuthorizerAppId());
				goodsList = mallGoodsService.getListPage(goods, 0, 100);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加选中的库存商品
	 * @return
	 */
	public String insertMoreStockMallGoods(){
		try {
			if(!Utils.isEmpty(jsonStr) && !Utils.isEmpty(typeId)){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					JSONArray array = JSONArray.fromObject(jsonStr);
					this.goodsList = new ArrayList<MallGoods>();
					GoodsStock stock = null;
					MallGoods mallGoods = null;
					for (Object object : array) {
						JSONObject json = (JSONObject) object;
						stock = (GoodsStock) JSONObject.toBean(json, GoodsStock.class);
						if(!Utils.isEmpty(stock)){
							mallGoods = new MallGoods();
							mallGoods.setAppId(wechatApp.getAuthorizerAppId());
							mallGoods.setGoodsId(stock.getGoodsId());
							mallGoods.setGoodsName(stock.getName());
							mallGoods.setTypeId(typeId);
							mallGoods.setOprice(stock.getPrice());
							mallGoods.setAprice(stock.getPrice());
							mallGoods.setNumber(stock.getNumber());
							mallGoods.setIsHot(0);
							mallGoods.setIsPoint(1);
							mallGoods.setStatus(0);
							mallGoods.setCreator(this.getUser().getUserName());
							mallGoods.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
							goodsList.add(mallGoods);
						}
					}
					int count = mallGoodsService.insertMore(goodsList);
					this.setResult(true, "成功添加" + count + "个商品！");
				} else
					this.setResult(false, "参数缺失！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加选中的工时商品
	 * @return
	 */
	public String insertMoreHourMallGoods(){
		try {
			if(!Utils.isEmpty(jsonStr) && !Utils.isEmpty(typeId)){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					JSONArray array = JSONArray.fromObject(jsonStr);
					this.goodsList = new ArrayList<MallGoods>();
					GoodsHour hour = null;
					MallGoods mallGoods = null;
					for (Object object : array) {
						JSONObject json = (JSONObject) object;
						hour = (GoodsHour) JSONObject.toBean(json, GoodsHour.class);
						if(!Utils.isEmpty(hour)){
							mallGoods = new MallGoods();
							mallGoods.setAppId(wechatApp.getAuthorizerAppId());
							mallGoods.setGoodsId(hour.getId());
							mallGoods.setGoodsName(hour.getName());
							mallGoods.setTypeId(typeId);
							mallGoods.setOprice(hour.getPrice());
							mallGoods.setAprice(hour.getPrice());
							mallGoods.setNumber(1.0);
							mallGoods.setIsHot(0);
							mallGoods.setIsPoint(1);
							mallGoods.setStatus(0);
							mallGoods.setCreator(this.getUser().getUserName());
							mallGoods.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
							goodsList.add(mallGoods);
						}
					}
					int count = mallGoodsService.insertMore(goodsList);
					this.setResult(true, "成功添加" + count + "个商品！");
				} else
					this.setResult(false, "参数缺失！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加选中的套餐商品
	 * @return
	 */
	public String insertMorePackageMallGoods(){
		try {
			if(!Utils.isEmpty(jsonStr) && !Utils.isEmpty(typeId)){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					JSONArray array = JSONArray.fromObject(jsonStr);
					this.goodsList = new ArrayList<MallGoods>();
					GoodsPackage pack = null;
					MallGoods mallGoods = null;
					for (Object object : array) {
						JSONObject json = (JSONObject) object;
						pack = (GoodsPackage) JSONObject.toBean(json, GoodsPackage.class);
						if(!Utils.isEmpty(pack)){
							mallGoods = new MallGoods();
							mallGoods.setAppId(wechatApp.getAuthorizerAppId());
							mallGoods.setGoodsId(pack.getId());
							mallGoods.setGoodsName(pack.getName());
							mallGoods.setTypeId(typeId);
							mallGoods.setOprice(pack.getPrice());
							mallGoods.setAprice(pack.getPrice());
							mallGoods.setNumber(1.0);
							mallGoods.setIsHot(0);
							mallGoods.setIsPoint(1);
							mallGoods.setStatus(0);
							mallGoods.setCreator(this.getUser().getUserName());
							mallGoods.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
							goodsList.add(mallGoods);
						}
					}
					int count = mallGoodsService.insertMore(goodsList);
					this.setResult(true, "成功添加" + count + "个商品！");
				} else
					this.setResult(false, "参数缺失！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 编辑商品
	 * @return
	 */
	public String updateMallGoods() {
		try {
			if(!Utils.isEmpty(goods) && !Utils.isEmpty(goods.getGoodsId())){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					goods.setAppId(wechatApp.getAuthorizerAppId());
					mallGoodsService.update(goods);
					this.setResult(true, "编辑成功！");
				} else
					this.setResult(false, "参数缺失！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			this.setResult(false, "编辑失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 上传商品图片
	 * @return
	 */
	public String uploadMallGoodsPictures() {
		try {
			String realpath = ServletActionContext.getServletContext().getRealPath("/upload/mall/goods");
	        if (!Utils.isEmpty(upload)) {
	        	String suffix;
	        	if(uploadFileName != null && !"".equals(uploadFileName) && uploadFileName.indexOf(".") > 0)
					suffix = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1).toLowerCase();
				else
					suffix = "";
	        	if("jpeg".equals(suffix) || "jpg".equals(suffix) || "png".equals(suffix) || "gif".equals(suffix) || "bmp".equals(suffix)){
	        		uploadFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
	        		File savefile = new File(new File(realpath), uploadFileName);
	        		if (!savefile.getParentFile().exists())
	        			savefile.getParentFile().mkdirs();
	        		FileUtils.copyFile(upload, savefile);
	        		this.setResult(true, "上传成功！");
	        	} else
	        		this.setResult(false, "请上传正确的图片格式！");
	        }
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, "上传失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 添加选中的工时商品
	 * @return
	 */
	public String handleMoreMallGoods(){
		try {
			if(!Utils.isEmpty(jsonStr) && !Utils.isEmpty(status)){
				WechatAuthorizerParams wechatApp = this.getWechatApp();
				if(!Utils.isEmpty(wechatApp)) {
					int count = 0;
					JSONArray array = JSONArray.fromObject(jsonStr);
					for (Object object : array) {
						JSONObject json = (JSONObject) object;
						MallGoods goods = (MallGoods) JSONObject.toBean(json, MallGoods.class);
						if(!Utils.isEmpty(goods))
							goods.setStatus(status);
						goods.setAppId(wechatApp.getAuthorizerAppId());
						mallGoodsService.update(goods);
						count++;
					}
					this.setResult(true, "成功" + (status == 1 ? "上架" : "下架") + count + "个商品！");
				} else
					this.setResult(false, "参数缺失！");
			} else
				this.setResult(false, "参数缺失！");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	public MallGoodsService getMallGoodsService() {
		return mallGoodsService;
	}
	@Resource
	public void setMallGoodsService(MallGoodsService mallGoodsService) {
		this.mallGoodsService = mallGoodsService;
	}
	public List<MallGoods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<MallGoods> goodsList) {
		this.goodsList = goodsList;
	}
	public MallGoods getGoods() {
		return goods;
	}
	public void setGoods(MallGoods goods) {
		this.goods = goods;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getJsonStr() {
		return jsonStr;
	}
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
