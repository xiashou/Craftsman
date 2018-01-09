package com.tcode.business.mall.action;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.basic.model.BaseCarBrand;
import com.tcode.business.basic.model.BaseCarModel;
import com.tcode.business.basic.model.BaseCarSeries;
import com.tcode.business.basic.service.BaseCarBrandService;
import com.tcode.business.basic.service.BaseCarModelService;
import com.tcode.business.basic.service.BaseCarSeriesService;
import com.tcode.business.mall.model.MallBanner;
import com.tcode.business.mall.model.MallGoods;
import com.tcode.business.mall.model.MallGoodsType;
import com.tcode.business.mall.model.MallOrderHead;
import com.tcode.business.mall.model.MallSendMode;
import com.tcode.business.mall.model.MallSetting;
import com.tcode.business.mall.service.MallBannerService;
import com.tcode.business.mall.service.MallGoodsService;
import com.tcode.business.mall.service.MallGoodsTypeService;
import com.tcode.business.mall.service.MallOrderService;
import com.tcode.business.mall.service.MallSendModeService;
import com.tcode.business.mall.service.MallSettingService;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("mallWebAction")
public class MallWebAction extends BaseAction {

	private static final long serialVersionUID = 365268305210393533L;
	private static Logger log = Logger.getLogger("SLog");

	private BaseCarBrandService carBrandService;
	private BaseCarSeriesService carSeriesService;
	private BaseCarModelService carModelService;
	private MallGoodsService mallGoodsService;
	private MallSendModeService mallSendModeService;
	private MallGoodsTypeService mallGoodsTypeService;
	private MallBannerService mallBannerService;
	private MemberCarService memberCarService;
	private MallSettingService mallSettingService;
	private MallOrderService mallOrderService;
	
	private List<MallGoodsType> goodsTypeList;
	private List<BaseCarBrand> brandList;
	private List<BaseCarSeries> seriesList;
	private List<BaseCarModel> modelList;
	private List<MallGoods> goodsList;
	private List<MallSendMode> modeList;
	private List<MallBanner> bannerList;
	private List<MemberCar> carList;
	private List<MallOrderHead> orderList;
	private MemberCar car;
	private MallGoods goods;
	private BaseCarBrand brand;
	
	private Integer carId;
	private Integer brandId;
	private Integer seriesId;
	private Integer status1;
	private Integer status3;
	private String goodsId;
	private String orderId;
	private String sid;
	
	
	/**
	 * 微信商城首页
	 * @return
	 */
	public String initMallIndex() {
		try {
			if(!Utils.isEmpty(sid)){
				WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
				goodsList = mallGoodsService.getHotList(wechatAuthorizerParams.getAuthorizerAppId(), 8);
				bannerList = mallBannerService.getListByAppId(wechatAuthorizerParams.getAuthorizerAppId());
				MallSetting setting = mallSettingService.getById(wechatAuthorizerParams.getAuthorizerAppId());
				if(!Utils.isEmpty(setting))
					this.getRequest().getSession().setAttribute("title", setting.getTitle());
				Member member = (Member) this.getRequest().getSession().getAttribute("member");
				if(!Utils.isEmpty(member)){
					carList = memberCarService.getMemberCarByMemberId(member.getMemId());
					
					if(!Utils.isEmpty(carId))
						car = memberCarService.getMemberCarById(carId);
					else if(!Utils.isEmpty(this.getRequest().getSession().getAttribute("car")))
						car = (MemberCar) this.getRequest().getSession().getAttribute("car");
					else
						car = carList.get(0);
					if(!Utils.isEmpty(car.getCarBrand()))
						brand = carBrandService.getById(car.getCarBrand());
					this.getRequest().getSession().setAttribute("car", car);
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 个人中心
	 * @return
	 */
	public String initMallCenter() {
		try {
			if(!Utils.isEmpty(sid)){
				WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
				MallOrderHead orderHead = new MallOrderHead();
				orderHead.setAppId(wechatAuthorizerParams.getAuthorizerAppId());
				Member member = this.getMember();
				if(!Utils.isEmpty(member))
					orderHead.setMemId(member.getMemId());
				orderHead.setStatus(1);
				status1 = mallOrderService.getOrderListCount(orderHead);
				orderHead.setStatus(3);
				status3 = mallOrderService.getOrderListCount(orderHead);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 会员订单
	 * @return
	 */
	public String initMallOrders() {
		try {
			if(!Utils.isEmpty(sid)){
				WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
				MallOrderHead orderHead = new MallOrderHead();
				orderHead.setAppId(wechatAuthorizerParams.getAuthorizerAppId());
				Member member = this.getMember();
				if(!Utils.isEmpty(member))
					orderHead.setMemId(member.getMemId());
				if(!Utils.isEmpty(status1))
					orderHead.setStatus(1);
				if(!Utils.isEmpty(status3))
					orderHead.setStatus(3);
				orderList = mallOrderService.getOrderDetailListPage(orderHead, 0, 30);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 订单取消
	 * @return
	 */
	public String cancelOrder(){
		try {
			if(!Utils.isEmpty(orderId)){
				MallOrderHead orderHead = new MallOrderHead();
				orderHead = mallOrderService.getOrderHeadById(orderId);
				orderHead.setStatus(0);
				mallOrderService.updateHead(orderHead);
				this.setResult(true, "成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 订单取消
	 * @return
	 */
	public String confirmOrder(){
		try {
			if(!Utils.isEmpty(orderId)){
				MallOrderHead orderHead = new MallOrderHead();
				orderHead = mallOrderService.getOrderHeadById(orderId);
				orderHead.setStatus(4);
				mallOrderService.updateHead(orderHead);
				this.setResult(true, "成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 商品详情
	 * @return
	 */
	public String initMallGoodsDetail() {
		try {
			if(!Utils.isEmpty(goodsId) && !Utils.isEmpty(sid)){
				WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
				goods = mallGoodsService.getById(goodsId);
				modeList = mallSendModeService.getListByAppId(wechatAuthorizerParams.getAuthorizerAppId());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 商品分类
	 * @return
	 */
	public String initMallCategory() {
		try {
			if(!Utils.isEmpty(sid)){
				WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
				goodsTypeList = mallGoodsTypeService.getByAppId(wechatAuthorizerParams.getAuthorizerAppId());
				goodsList = mallGoodsService.getAllListOrderType(wechatAuthorizerParams.getAuthorizerAppId());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 品牌选择
	 * @return
	 */
	public String initBrandSelect() {
		try {
			brandList = carBrandService.getAll();
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 系列选择
	 * @return
	 */
	public String initSeriesSelect() {
		try {
			if(!Utils.isEmpty(brandId))
				seriesList = carSeriesService.getByBrandId(brandId);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 型号选择
	 * @return
	 */
	public String initModelSelect() {
		try {
			if(!Utils.isEmpty(seriesId))
				modelList = carModelService.getBySeriesId(seriesId);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String formatDouble(double s){
    	DecimalFormat fmt = new DecimalFormat("##0.00");
    	return fmt.format(s);
	}
	
	public String formatInt(double s){
    	DecimalFormat fmt = new DecimalFormat("##0");
    	return fmt.format(s);
	}
	
	public String imgString(String img){
		if(!Utils.isEmpty(img)){
			if(img.indexOf(",") > 0){
				String images[] = img.split(",");
				return images[0];
			} else
				return img;
		} else
			return "";
	}
	
	public BaseCarBrandService getCarBrandService() {
		return carBrandService;
	}
	@Resource
	public void setCarBrandService(BaseCarBrandService carBrandService) {
		this.carBrandService = carBrandService;
	}
	public BaseCarSeriesService getCarSeriesService() {
		return carSeriesService;
	}
	@Resource
	public void setCarSeriesService(BaseCarSeriesService carSeriesService) {
		this.carSeriesService = carSeriesService;
	}
	public BaseCarModelService getCarModelService() {
		return carModelService;
	}
	@Resource
	public void setCarModelService(BaseCarModelService carModelService) {
		this.carModelService = carModelService;
	}
	public MallGoodsService getMallGoodsService() {
		return mallGoodsService;
	}
	@Resource
	public void setMallGoodsService(MallGoodsService mallGoodsService) {
		this.mallGoodsService = mallGoodsService;
	}
	public MallSendModeService getMallSendModeService() {
		return mallSendModeService;
	}
	@Resource
	public void setMallSendModeService(MallSendModeService mallSendModeService) {
		this.mallSendModeService = mallSendModeService;
	}
	public MallGoodsTypeService getMallGoodsTypeService() {
		return mallGoodsTypeService;
	}
	@Resource
	public void setMallGoodsTypeService(MallGoodsTypeService mallGoodsTypeService) {
		this.mallGoodsTypeService = mallGoodsTypeService;
	}
	public MallBannerService getMallBannerService() {
		return mallBannerService;
	}
	@Resource
	public void setMallBannerService(MallBannerService mallBannerService) {
		this.mallBannerService = mallBannerService;
	}
	public MemberCarService getMemberCarService() {
		return memberCarService;
	}
	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		this.memberCarService = memberCarService;
	}
	public MallSettingService getMallSettingService() {
		return mallSettingService;
	}
	@Resource
	public void setMallSettingService(MallSettingService mallSettingService) {
		this.mallSettingService = mallSettingService;
	}
	public MallOrderService getMallOrderService() {
		return mallOrderService;
	}
	@Resource
	public void setMallOrderService(MallOrderService mallOrderService) {
		this.mallOrderService = mallOrderService;
	}
	
	
	

	public List<MallGoodsType> getGoodsTypeList() {
		return goodsTypeList;
	}
	public void setGoodsTypeList(List<MallGoodsType> goodsTypeList) {
		this.goodsTypeList = goodsTypeList;
	}
	public List<BaseCarBrand> getBrandList() {
		return brandList;
	}
	public void setBrandList(List<BaseCarBrand> brandList) {
		this.brandList = brandList;
	}
	public List<BaseCarSeries> getSeriesList() {
		return seriesList;
	}
	public void setSeriesList(List<BaseCarSeries> seriesList) {
		this.seriesList = seriesList;
	}
	public List<BaseCarModel> getModelList() {
		return modelList;
	}
	public void setModelList(List<BaseCarModel> modelList) {
		this.modelList = modelList;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Integer getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}
	public List<MallGoods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<MallGoods> goodsList) {
		this.goodsList = goodsList;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public MallGoods getGoods() {
		return goods;
	}
	public void setGoods(MallGoods goods) {
		this.goods = goods;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public List<MallSendMode> getModeList() {
		return modeList;
	}
	public void setModeList(List<MallSendMode> modeList) {
		this.modeList = modeList;
	}
	public List<MallBanner> getBannerList() {
		return bannerList;
	}
	public void setBannerList(List<MallBanner> bannerList) {
		this.bannerList = bannerList;
	}
	public MemberCar getCar() {
		return car;
	}
	public void setCar(MemberCar car) {
		this.car = car;
	}
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public BaseCarBrand getBrand() {
		return brand;
	}
	public void setBrand(BaseCarBrand brand) {
		this.brand = brand;
	}
	public List<MemberCar> getCarList() {
		return carList;
	}
	public void setCarList(List<MemberCar> carList) {
		this.carList = carList;
	}
	public Integer getStatus1() {
		return status1;
	}
	public void setStatus1(Integer status1) {
		this.status1 = status1;
	}
	public Integer getStatus3() {
		return status3;
	}
	public void setStatus3(Integer status3) {
		this.status3 = status3;
	}
	public List<MallOrderHead> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<MallOrderHead> orderList) {
		this.orderList = orderList;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
