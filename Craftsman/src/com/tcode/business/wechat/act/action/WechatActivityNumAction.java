package com.tcode.business.wechat.act.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.model.WechatActivityLottery;
import com.tcode.business.wechat.act.model.WechatActivityLotteryItem;
import com.tcode.business.wechat.act.model.WechatActivityNum;
import com.tcode.business.wechat.act.service.WechatActivityLotteryItemService;
import com.tcode.business.wechat.act.service.WechatActivityLotteryService;
import com.tcode.business.wechat.act.service.WechatActivityNumService;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryItemVo;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryVo;
import com.tcode.business.wechat.act.vo.WechatActivityNumVo;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysDept;
import com.tcode.system.model.SysUser;

/**
 * 活动参与次数
 * @author supeng
 *
 */
@Scope("prototype")
@Component("WechatActivityNumAction")
public class WechatActivityNumAction extends BaseAction {

	private static Logger log = Logger.getLogger("SLog");
	private WechatActivityNumService wechatActivityNumService;
	private WechatActivityNum wechatActivityNum;
	private WechatActivityNumVo wechatActivityNumVo;
	private List<WechatActivityNum> wechatActivityNumList;
	
	/**
	 * 查询参与次数信息
	 * @return
	 */
	public String queryAllWechatActivityNum() {
		try {
			SysDept dept = getDept();
			if(wechatActivityNumVo == null) wechatActivityNumVo = new WechatActivityNumVo();
			wechatActivityNumVo.setDeptCode(dept.getDeptCode());
			this.setTotalCount(wechatActivityNumService.getListCount(wechatActivityNumVo));
			wechatActivityNumList = wechatActivityNumService.getListPage(wechatActivityNumVo, this.getStart(), this.getLimit());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	
//	/**
//	 * 新增抽奖活动信息
//	 * @return
//	 */
//	public String insertWechatActivityNum() {
//		if(wechatActivityLottery != null) {
//			try {
//				SysUser user = getUser();
//				SysDept dept = getDept();
//				wechatActivityLottery.setbDate(Utils.changeDateFormat(wechatActivityLottery.getbDate(), "yy年MM月dd日", "yyyy/MM/dd"));
//				wechatActivityLottery.seteDate(Utils.changeDateFormat(wechatActivityLottery.geteDate(), "yy年MM月dd日", "yyyy/MM/dd"));
//				wechatActivityLottery.setCompanyId(dept.getCompanyId());
//				wechatActivityLottery.setDeptCode(dept.getDeptCode());
//				wechatActivityLottery.setDeptName(dept.getDeptName());
//				wechatActivityLottery.setCreateBy(user.getUserName());
//				wechatActivityLottery.setUpdateBy(user.getUserName());
//				wechatActivityLotteryService.insert(wechatActivityLottery);
//				this.setResult(true, "新增成功！");
//			} catch (Exception e) {
//				e.printStackTrace();
//				this.setResult(false, "新增失败！");
//			}
//		} else
//			this.setResult(false, "数据错误！");
//		return SUCCESS;
//	}
//	
	/**
	 * 修改活动次数信息
	 * @return
	 */
	public String updateWechatActivityNum() {
		if(wechatActivityNum != null) {
			try {
				SysUser user = getUser();
				wechatActivityNum.setUpdateBy(user.getUserName());
				wechatActivityNumService.update(wechatActivityNum);
				this.setResult(true, "修改成功！");
			} catch (Exception e) {
				e.printStackTrace();
				this.setResult(false, "修改失败！");
			}
		} else
			this.setResult(false, "修改错误！");
		return SUCCESS;
	}
//	
//	/**
//	 * 删除抽奖活动信息
//	 * @return
//	 */
//	public String deleteWechatActivityLottery() {
//		try {
//			if(!Utils.isEmpty(wechatActivityLottery) && !Utils.isEmpty(wechatActivityLottery.getId())){
//				wechatActivityLotteryService.delete(wechatActivityLottery);
//				this.setResult(true, "删除成功！");
//			}
//		} catch(Exception e) {
//			this.setResult(false, "删除失败！");
//			log.error(Utils.getErrorMessage(e));
//		}
//		return SUCCESS;
//	}
	
	public WechatActivityNumService getWechatActivityNumService() {
		return wechatActivityNumService;
	}

	@Resource
	public void setWechatActivityNumService(WechatActivityNumService wechatActivityNumService) {
		this.wechatActivityNumService = wechatActivityNumService;
	}

	public WechatActivityNum getWechatActivityNum() {
		return wechatActivityNum;
	}

	public void setWechatActivityNum(WechatActivityNum wechatActivityNum) {
		this.wechatActivityNum = wechatActivityNum;
	}

	public WechatActivityNumVo getWechatActivityNumVo() {
		return wechatActivityNumVo;
	}

	public void setWechatActivityNumVo(WechatActivityNumVo wechatActivityNumVo) {
		this.wechatActivityNumVo = wechatActivityNumVo;
	}

	public List<WechatActivityNum> getWechatActivityNumList() {
		return wechatActivityNumList;
	}

	public void setWechatActivityNumList(List<WechatActivityNum> wechatActivityNumList) {
		this.wechatActivityNumList = wechatActivityNumList;
	}
}
