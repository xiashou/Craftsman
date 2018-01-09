package com.tcode.business.wechat.act.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.weixin4j.OAuth2User;

import com.tcode.business.msg.model.MsgCharging;
import com.tcode.business.msg.service.MsgChargingService;
import com.tcode.business.wechat.act.model.ActJoiner;
import com.tcode.business.wechat.act.service.ActJoinerService;
import com.tcode.business.wechat.act.service.ActSignupService;
import com.tcode.common.action.BaseAction;
import com.tcode.common.message.HttpSender;
import com.tcode.common.message.MsgUtil;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("actJoinerAction")
public class ActJoinerAction extends BaseAction {

	private static final long serialVersionUID = 8522617701483384593L;
	private static Logger log = Logger.getLogger("SLog");
	
	private ActJoinerService actJoinerService;
	private ActSignupService actSignupService;
	private MsgChargingService msgChargingService;
	
	private List<ActJoiner> joinerList;
	
	private ActJoiner joiner;
	private String[] mobileStr;
	private String message;
	
	public String queryActJoinerPage(){
		try {
			this.setTotalCount(actJoinerService.getListCount(joiner));
			joinerList = actJoinerService.getListPage(joiner, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String insertActJoiner() {
		try {
			OAuth2User auth2User = (OAuth2User) this.getRequest().getSession().getAttribute("auth2User");
			if(!Utils.isEmpty(joiner)){
				if(!Utils.isEmpty(auth2User)){
					joiner.setHeadImg(auth2User.getHeadimgurl());
					joiner.setNickName(auth2User.getNickname());
					joiner.setOpenId(auth2User.getOpenid());
				}
				joiner.setStatus(1);
			}
			actJoinerService.insert(joiner);
			actSignupService.updateSignNumber(joiner.getActId(), 1);
			if(!Utils.isEmpty(auth2User))
				MsgUtil.sendActivityMsg(this.getRequest(), "OPENTM202665053", joiner);
			this.setResult(true, "成功");
		} catch(Exception e) {
			this.setResult(false, "失败");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public String cancelActJoiner() {
		try {
			OAuth2User auth2User = (OAuth2User) this.getRequest().getSession().getAttribute("auth2User");
			if(!Utils.isEmpty(auth2User) && !Utils.isEmpty(joiner) && !Utils.isEmpty(joiner.getActId())){
				joiner = actJoinerService.getByJoin(auth2User.getOpenid(), joiner.getActId());
				joiner.setStatus(2);
				actJoinerService.update(joiner);
				actSignupService.updateSignNumber(joiner.getActId(), -1);
				this.setResult(true, "成功");
			}
		} catch(Exception e) {
			this.setResult(false, "失败");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 发送短信
	 * @return
	 */
	public String sendActJoinerSMS() {
		try {
			List<MsgCharging> msgList = msgChargingService.getByDeptCode(this.getDept().getDeptCode());
			MsgCharging msgCharging = null;
			if(msgList.size() == 1) {
				msgCharging = msgList.get(0);
				if(!Utils.isEmpty(msgCharging)){
					if(msgCharging.getRemainingNum() > 0) {
						int result = HttpSender.batchSend2(StringUtils.join(mobileStr, ","), message);
						if(result == 0) {
							msgCharging.setRemainingNum(msgCharging.getRemainingNum() - result);
							msgCharging.setSendNum(msgCharging.getSendNum() + result);
							msgCharging.setUpdateBy("sys");
							msgChargingService.update(msgCharging);
							this.setResult(true, "成功发送" + result + "条信息!");
						} else
							this.setResult(false, "发送失败！");
					} else
						this.setResult(false, "短信条数不足，请联系管理员充值！");
				} else
					this.setResult(false, "没有开通短信服务，请联系管理员！");
			} else
				this.setResult(false, "没有开通短信服务，请联系管理员！");
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
			this.setResult(false, Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public ActJoinerService getActJoinerService() {
		return actJoinerService;
	}
	@Resource
	public void setActJoinerService(ActJoinerService actJoinerService) {
		this.actJoinerService = actJoinerService;
	}
	public ActSignupService getActSignupService() {
		return actSignupService;
	}
	@Resource
	public void setActSignupService(ActSignupService actSignupService) {
		this.actSignupService = actSignupService;
	}

	public MsgChargingService getMsgChargingService() {
		return msgChargingService;
	}
	@Resource
	public void setMsgChargingService(MsgChargingService msgChargingService) {
		this.msgChargingService = msgChargingService;
	}

	public List<ActJoiner> getJoinerList() {
		return joinerList;
	}
	public void setJoinerList(List<ActJoiner> joinerList) {
		this.joinerList = joinerList;
	}
	public ActJoiner getJoiner() {
		return joiner;
	}
	public void setJoiner(ActJoiner joiner) {
		this.joiner = joiner;
	}
	public String[] getMobileStr() {
		return mobileStr;
	}
	public void setMobileStr(String[] mobileStr) {
		this.mobileStr = mobileStr;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
