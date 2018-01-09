package com.tcode.business.wechat.act.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.weixin4j.OAuth2User;

import com.tcode.business.mall.model.MallSetting;
import com.tcode.business.mall.service.MallSettingService;
import com.tcode.business.wechat.act.model.ActBanner;
import com.tcode.business.wechat.act.model.ActJoiner;
import com.tcode.business.wechat.act.model.ActSignup;
import com.tcode.business.wechat.act.model.ActSpec;
import com.tcode.business.wechat.act.model.ActStatement;
import com.tcode.business.wechat.act.service.ActBannerService;
import com.tcode.business.wechat.act.service.ActJoinerService;
import com.tcode.business.wechat.act.service.ActSignupService;
import com.tcode.business.wechat.act.service.ActSpecService;
import com.tcode.business.wechat.act.service.ActStatementService;
import com.tcode.business.wechat.sys.model.WechatAuthorizerParams;
import com.tcode.business.wechat.sys.util.WechatAuthorizerParamsUtil;
import com.tcode.common.action.BaseAction;
import com.tcode.common.pay.wechat.util.WechatPay;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("actSignupWebAction")
public class ActSignupWebAction extends BaseAction {

	private static final long serialVersionUID = -8262697923304066864L;
	private static Logger log = Logger.getLogger("SLog");
	
	private ActSignupService actSignupService;
	private ActSpecService actSpecService;
	private MallSettingService mallSettingService;
	private ActStatementService actStatementService;
	private ActJoinerService actJoinerService;
	private ActBannerService actBannerService;
	
	private List<ActSignup> signupList;
	private List<ActSignup> joinList;
	private List<ActSpec> specList;
	private List<ActBanner> bannerList;
	
	private ActSignup signup;
	private MallSetting setting;
	private ActStatement statement;
	private ActJoiner joiner;
	
	private String sid;
	private Integer actId;
	private Integer number;
	private Double price;
	private String specIds;
	private String payStr;
	private String orderNo;
	
	
	public String initActSignupList() {
		try {
			if(!Utils.isEmpty(sid)){
				WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
				OAuth2User auth2User = (OAuth2User) this.getRequest().getSession().getAttribute("auth2User");
				signupList = actSignupService.getListByAppId(wechatAuthorizerParams.getAuthorizerAppId());
				joinList = actSignupService.getListByOpenId(auth2User.getOpenid());
				bannerList = actBannerService.getListByAppId(wechatAuthorizerParams.getAuthorizerAppId());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String initActSignupDetail() {
		try {
			if(!Utils.isEmpty(actId)){
				OAuth2User auth2User = (OAuth2User) this.getRequest().getSession().getAttribute("auth2User");
				signup = actSignupService.getById(actId);
				specList = actSpecService.getListByActId(actId);
				joiner = actJoinerService.getByJoin(auth2User.getOpenid(), actId);
				actSignupService.updateReadNumber(actId, 1);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String initActJoin() {
		try {
			OAuth2User auth2User = (OAuth2User) this.getRequest().getSession().getAttribute("auth2User");
			if(!Utils.isEmpty(auth2User) && !Utils.isEmpty(actId)){
				WechatAuthorizerParams wechatAuthorizerParams = WechatAuthorizerParamsUtil.getWechatAuthorizerParamsBySid(sid);
				if(!Utils.isEmpty(wechatAuthorizerParams)) {
					setting = mallSettingService.getById(wechatAuthorizerParams.getAuthorizerAppId());
					statement = actStatementService.getById(wechatAuthorizerParams.getAuthorizerAppId());
					signup = actSignupService.getById(actId);
					specList = actSpecService.getListByActId(actId);
					orderNo = "A" + signup.getId() + new SimpleDateFormat("yyyyMMddHHmmSSS").format(new Date());
					String clientIp = this.getRequest().getRemoteAddr();
					payStr = WechatPay.getActPayString(auth2User, clientIp, setting, signup.getName(), Utils.isEmpty(price) ? 0.0 : price, orderNo);
				}
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


	public ActSignupService getActSignupService() {
		return actSignupService;
	}
	@Resource
	public void setActSignupService(ActSignupService actSignupService) {
		this.actSignupService = actSignupService;
	}
	public ActSpecService getActSpecService() {
		return actSpecService;
	}
	@Resource
	public void setActSpecService(ActSpecService actSpecService) {
		this.actSpecService = actSpecService;
	}
	public List<ActSpec> getSpecList() {
		return specList;
	}
	public MallSettingService getMallSettingService() {
		return mallSettingService;
	}
	@Resource
	public void setMallSettingService(MallSettingService mallSettingService) {
		this.mallSettingService = mallSettingService;
	}
	public ActStatementService getActStatementService() {
		return actStatementService;
	}
	@Resource
	public void setActStatementService(ActStatementService actStatementService) {
		this.actStatementService = actStatementService;
	}
	public ActJoinerService getActJoinerService() {
		return actJoinerService;
	}
	@Resource
	public void setActJoinerService(ActJoinerService actJoinerService) {
		this.actJoinerService = actJoinerService;
	}
	public ActBannerService getActBannerService() {
		return actBannerService;
	}
	@Resource
	public void setActBannerService(ActBannerService actBannerService) {
		this.actBannerService = actBannerService;
	}
	public List<ActBanner> getBannerList() {
		return bannerList;
	}
	public void setBannerList(List<ActBanner> bannerList) {
		this.bannerList = bannerList;
	}
	public MallSetting getSetting() {
		return setting;
	}
	public List<ActSignup> getJoinList() {
		return joinList;
	}
	public void setJoinList(List<ActSignup> joinList) {
		this.joinList = joinList;
	}
	public void setSetting(MallSetting setting) {
		this.setting = setting;
	}

	public void setSpecList(List<ActSpec> specList) {
		this.specList = specList;
	}
	public List<ActSignup> getSignupList() {
		return signupList;
	}
	public void setSignupList(List<ActSignup> signupList) {
		this.signupList = signupList;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public ActSignup getSignup() {
		return signup;
	}
	public void setSignup(ActSignup signup) {
		this.signup = signup;
	}
	public Integer getActId() {
		return actId;
	}
	public void setActId(Integer actId) {
		this.actId = actId;
	}
	public String getSpecIds() {
		return specIds;
	}
	public void setSpecIds(String specIds) {
		this.specIds = specIds;
	}
	public String getPayStr() {
		return payStr;
	}
	public void setPayStr(String payStr) {
		this.payStr = payStr;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public ActStatement getStatement() {
		return statement;
	}
	public void setStatement(ActStatement statement) {
		this.statement = statement;
	}
	public ActJoiner getJoiner() {
		return joiner;
	}
	public void setJoiner(ActJoiner joiner) {
		this.joiner = joiner;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}

}
