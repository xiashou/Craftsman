package com.tcode.business.mall.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.mall.model.MallAddress;
import com.tcode.business.mall.model.MallCart;
import com.tcode.business.mall.model.MallGoods;
import com.tcode.business.mall.model.MallOrderHead;
import com.tcode.business.mall.model.MallOrderItem;
import com.tcode.business.mall.model.MallSendMode;
import com.tcode.business.mall.model.MallSetting;
import com.tcode.business.mall.service.MallAddressService;
import com.tcode.business.mall.service.MallCartService;
import com.tcode.business.mall.service.MallGoodsService;
import com.tcode.business.mall.service.MallOrderService;
import com.tcode.business.mall.service.MallSendModeService;
import com.tcode.business.mall.service.MallSettingService;
import com.tcode.business.member.model.Member;
import com.tcode.business.member.model.MemberCar;
import com.tcode.business.member.service.MemberCarService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.common.action.BaseAction;
import com.tcode.common.pay.wechat.util.WechatPay;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("mallCartAction")
public class MallCartAction extends BaseAction {

	private static final long serialVersionUID = 7296709118571351172L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MallCartService mallCartService;
	private MallGoodsService mallGoodsService;
	private MallAddressService mallAddressService;
	private MallSendModeService mallSendModeService;
	private MallOrderService mallOrderService;
	private MallSettingService mallSettingService;
	private MemberCarService memberCarService;
	
	private List<MallCart> cartList;
	private List<MallSendMode> modeList;
	private List<MallAddress> addressList;
	private MallOrderHead mallOrder;
	private String payStr;
	private MallCart cart;
	private Integer memId;
	private String sid;
	private String cid;
	private String buy;
	private String orderId;
	private String modelName;
	private String numbers;
	private Integer modelId;
	private Integer type;
	
	
	/**
	 * 查询用户购物车
	 * @return
	 */
	public String queryMallCartByMemId() {
		try {
			if(!Utils.isEmpty(memId))
				cartList = mallCartService.getListByMemId(memId);
			else {
				Member member = (Member) this.getRequest().getSession().getAttribute("member");
				if(!Utils.isEmpty(member)){
					cartList = mallCartService.getListByMemId(member.getMemId());
					for(MallCart cart : cartList) {
						cart.setGoods(mallGoodsService.getById(cart.getGoodsId()));
						if(!Utils.isEmpty(cart.getSendMode()))
							cart.setModeName(mallSendModeService.getById(cart.getSendMode()).getModeName());
					}
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	
	/**
	 * 订单确认页面
	 * @return
	 */
	public String initPay() {
		try {
			Member member = (Member) this.getRequest().getSession().getAttribute("member");
			if(!Utils.isEmpty(member) && !Utils.isEmpty(buy)){
				WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
				if(buy.indexOf(",") > 0){
					String[] info = buy.split(",");
					cartList = new ArrayList<MallCart>();
					for(String infoStr : info){
						if(infoStr.indexOf("|") > 0){
							String[] carts = infoStr.split("\\|");
							MallCart cart = new MallCart();
							cart.setGoodsId(carts[0]);
							cart.setNumber(Double.parseDouble(carts[1]));
							if(carts.length >=3 && !Utils.isEmpty(carts[2]))
								cart.setSendMode(Integer.parseInt(carts[2]));
							if(carts.length >=4 && !Utils.isEmpty(carts[3]))
								cart.setModeName(carts[3]);
							cart.setGoods(mallGoodsService.getById(carts[0]));
							cartList.add(cart);
						}
					}
				}
				
				if(!Utils.isEmpty(cartList)){
					addressList = mallAddressService.getListByMemId(member.getMemId());
					mallOrder = mallOrderService.insertMallOrder(wechatAuthorizerParams.getAuthorizerAppId(), member, Utils.isEmpty(type)?1:type, cartList);
					MallSetting setting = mallSettingService.getById(wechatAuthorizerParams.getAuthorizerAppId());
					payStr = WechatPay.getPayString(member, this.getRequest().getRemoteAddr(), mallOrder, setting);
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 支付订单
	 * @return
	 */
	public String initOrderPay() {
		try {
			Member member = (Member) this.getRequest().getSession().getAttribute("member");
			if(!Utils.isEmpty(member) && !Utils.isEmpty(orderId)){
				WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
				
				List<MallOrderItem> itemList = mallOrderService.getOrderItemList(orderId);
				cartList = new ArrayList<MallCart>();
				if(!Utils.isEmpty(itemList)){
					for(MallOrderItem item : itemList){
						MallCart cart = new MallCart();
						cart.setGoodsId(item.getGoodsId());
						cart.setNumber(Double.parseDouble(item.getNumber() + ""));
						if(!Utils.isEmpty(item.getSendMode())){
							cart.setSendMode(item.getSendMode());
							MallSendMode send = mallSendModeService.getById(item.getSendMode());
							if(!Utils.isEmpty(send))
								cart.setModeName(send.getModeName());
						}
						cart.setGoods(mallGoodsService.getById(item.getGoodsId()));
						cartList.add(cart);
					}
				}
				
				if(!Utils.isEmpty(cartList)){
					addressList = mallAddressService.getListByMemId(member.getMemId());
					mallOrder = mallOrderService.getOrderHeadById(orderId);
					MallSetting setting = mallSettingService.getById(wechatAuthorizerParams.getAuthorizerAppId());
					payStr = WechatPay.getPayString(member, this.getRequest().getRemoteAddr(), mallOrder, setting);
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 轮胎保养
	 * @return
	 */
	public String initSelectBuy() {
		try {
			if(!Utils.isEmpty(modelId) && !Utils.isEmpty(sid) && !Utils.isEmpty(cid)){
				Member member = (Member) this.getRequest().getSession().getAttribute("member");
				if(!Utils.isEmpty(member)){
					WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
					List<MallGoods> goodsList = new ArrayList<MallGoods>();
					if(type == 2)
						goodsList = mallGoodsService.getListByTyre(wechatAuthorizerParams.getAuthorizerAppId(), modelId);
					else if(type == 3)
						goodsList = mallGoodsService.getListByMaintain(wechatAuthorizerParams.getAuthorizerAppId(), modelId);
					cartList = new ArrayList<MallCart>();
					for(MallGoods goods : goodsList){
						MallCart cart = new MallCart();
						cart.setMemId(member.getMemId());
						cart.setNumber(1.0);
						cart.setGoodsId(goods.getGoodsId());
						cart.setGoods(goods);
						cart.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
//						mallCartService.insert(cart);
						cartList.add(cart);
					}
					
					MemberCar car = memberCarService.getMemberCarById(Integer.parseInt(cid));
					if(!Utils.isEmpty(car)){
						car.setModel(modelId);
						car.setCarModel(modelName);
						memberCarService.update(car);
						this.getRequest().getSession().setAttribute("car", car);
					}
					
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 直接购买
	 * @return
	 */
	public String initDirectBuy() {
		try {
			if(!Utils.isEmpty(cart) && !Utils.isEmpty(cart.getGoodsId())){
				Member member = (Member) this.getRequest().getSession().getAttribute("member");
				if(!Utils.isEmpty(member)){
					cartList = new ArrayList<MallCart>();
					cart.setMemId(member.getMemId());
					cart.setGoods(mallGoodsService.getById(cart.getGoodsId()));
					cart.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
					mallCartService.insert(cart);
					cartList.add(cart);
				}
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加到购物车
	 * @return
	 */
	public String insertMallCart() {
		try {
			if(!Utils.isEmpty(cart) && !Utils.isEmpty(cart.getGoodsId())){
				cart.setCreateTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
				mallCartService.insert(cart);
				this.setResult(true, "添加成功！");
			} else
				this.setResult(false, "缺少参数！");
		} catch(Exception e) {
			this.setResult(false, "发生错误！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改购物车商品
	 * @return
	 */
	public String updateMallCart() {
		try {
			if(!Utils.isEmpty(cart) && !Utils.isEmpty(cart.getGoodsId()) && !Utils.isEmpty(cart.getMemId())){
				MallCart updateCart = mallCartService.getById(cart.getGoodsId(), cart.getMemId());
				updateCart.setNumber(cart.getNumber());
				mallCartService.update(updateCart);
				this.setResult(true, "修改成功！");
			} else
				this.setResult(false, "缺少参数！");
		} catch(Exception e) {
			this.setResult(false, "发生错误！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除购物车商品
	 * @return
	 */
	public String deleteMallCart() {
		try {
			if(!Utils.isEmpty(cart) && !Utils.isEmpty(cart.getGoodsId()) && !Utils.isEmpty(cart.getMemId())){
				mallCartService.delete(cart);
				this.setResult(true, "删除成功！");
			} else
				this.setResult(false, "缺少参数！");
		} catch(Exception e) {
			this.setResult(false, "发生错误！");
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
	
	
	public MallCartService getMallCartService() {
		return mallCartService;
	}
	@Resource
	public void setMallCartService(MallCartService mallCartService) {
		this.mallCartService = mallCartService;
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
	public MallAddressService getMallAddressService() {
		return mallAddressService;
	}
	@Resource
	public void setMallAddressService(MallAddressService mallAddressService) {
		this.mallAddressService = mallAddressService;
	}
	public MallOrderService getMallOrderService() {
		return mallOrderService;
	}
	@Resource
	public void setMallOrderService(MallOrderService mallOrderService) {
		this.mallOrderService = mallOrderService;
	}
	public MallSettingService getMallSettingService() {
		return mallSettingService;
	}
	@Resource
	public void setMallSettingService(MallSettingService mallSettingService) {
		this.mallSettingService = mallSettingService;
	}
	public MemberCarService getMemberCarService() {
		return memberCarService;
	}
	@Resource
	public void setMemberCarService(MemberCarService memberCarService) {
		this.memberCarService = memberCarService;
	}


	
	
	public List<MallCart> getCartList() {
		return cartList;
	}
	public void setCartList(List<MallCart> cartList) {
		this.cartList = cartList;
	}
	public MallCart getCart() {
		return cart;
	}
	public void setCart(MallCart cart) {
		this.cart = cart;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public List<MallSendMode> getModeList() {
		return modeList;
	}
	public void setModeList(List<MallSendMode> modeList) {
		this.modeList = modeList;
	}
	public String getBuy() {
		return buy;
	}
	public void setBuy(String buy) {
		this.buy = buy;
	}
	public String getNumbers() {
		return numbers;
	}
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	public List<MallAddress> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<MallAddress> addressList) {
		this.addressList = addressList;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPayStr() {
		return payStr;
	}
	public void setPayStr(String payStr) {
		this.payStr = payStr;
	}
	public MallOrderHead getMallOrder() {
		return mallOrder;
	}
	public void setMallOrder(MallOrderHead mallOrder) {
		this.mallOrder = mallOrder;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
