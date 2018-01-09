package com.tcode.business.msg.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.msg.model.MsgCharging;
import com.tcode.business.msg.service.MsgChargingService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

/**
 * 短信充值
 * @author TSM
 *
 */
@Scope("prototype")
@Component("MsgChargingAction")
public class MsgChargingAction extends BaseAction {

	private static final long serialVersionUID = -417221412964019011L;
	private static Logger log = Logger.getLogger("SLog");
	
	private MsgChargingService msgChargingService;
	
	private MsgCharging msgCharging;
	
	/**
	 * 查询店铺短信充值信息
	 * @return
	 */
	public String queryMsgChargingByDept(){
		try {
			List<MsgCharging> cList = msgChargingService.getByDeptCode(this.getDept().getDeptCode());
			if(!Utils.isEmpty(cList))
				msgCharging = cList.get(0);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public MsgChargingService getMsgChargingService() {
		return msgChargingService;
	}
	@Resource
	public void setMsgChargingService(MsgChargingService msgChargingService) {
		this.msgChargingService = msgChargingService;
	}

	public MsgCharging getMsgCharging() {
		return msgCharging;
	}

	public void setMsgCharging(MsgCharging msgCharging) {
		this.msgCharging = msgCharging;
	}
	

}
