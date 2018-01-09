package com.tcode.business.wechat.act.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.model.WechatActivityLottery;
import com.tcode.business.wechat.act.model.WechatActivityLotteryItem;
import com.tcode.business.wechat.act.service.WechatActivityLotteryItemService;
import com.tcode.business.wechat.act.service.WechatActivityLotteryService;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryItemVo;
import com.tcode.business.wechat.act.vo.WechatActivityLotteryVo;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysDept;
import com.tcode.system.model.SysUser;

/**
 * 抽奖活动-主
 * @author supeng
 *
 */
@Scope("prototype")
@Component("WechatActivityLotteryAction")
public class WechatActivityLotteryAction extends BaseAction {

	private static Logger log = Logger.getLogger("SLog");
	private WechatActivityLotteryService wechatActivityLotteryService;
	private WechatActivityLotteryItemService wechatActivityLotteryItemService;
	private WechatActivityLottery wechatActivityLottery;
	private WechatActivityLotteryItem wechatActivityLotteryItem;
	private WechatActivityLotteryVo wechatActivityLotteryVo;
	private WechatActivityLotteryItemVo wechatActivityLotteryItemVo;
	private List<WechatActivityLottery> wechatActivityLotteryList;
	private List<WechatActivityLotteryItem> wechatActivityLotteryItemList;
	
	/**
	 * 查询抽奖活动信息
	 * @return
	 */
	public String queryAllWechatActivityLottery() {
		try {
			if(Utils.isEmpty(wechatActivityLotteryVo))
				wechatActivityLotteryVo = new WechatActivityLotteryVo();
			wechatActivityLotteryVo.setDeptCode(this.getDept().getDeptCode());
			this.setTotalCount(wechatActivityLotteryService.getListCount(wechatActivityLotteryVo));
			wechatActivityLotteryList = wechatActivityLotteryService.getListPage(wechatActivityLotteryVo, this.getStart(), this.getLimit());
			for(WechatActivityLottery wechatActivityLottery : wechatActivityLotteryList) {
				wechatActivityLottery.setbDate(Utils.changeDateFormat(wechatActivityLottery.getbDate(), "yyyy/MM/dd", "yy年MM月dd日"));
				wechatActivityLottery.seteDate(Utils.changeDateFormat(wechatActivityLottery.geteDate(), "yyyy/MM/dd", "yy年MM月dd日"));
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 根据表头活动编码查询明细
	 * @return
	 */
	public String queryWechatActivityLotteryDetailByActiveCode() {
		try {
			if(!Utils.isEmpty(wechatActivityLottery) && !Utils.isEmpty(wechatActivityLottery.getActivityCode())){
				wechatActivityLotteryItemList = wechatActivityLotteryItemService.getByActivityCode(wechatActivityLottery.getActivityCode());
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 新增抽奖活动信息
	 * @return
	 */
	public String insertWechatActivityLottery() {
		if(wechatActivityLottery != null) {
			try {
				SysUser user = getUser();
				SysDept dept = getDept();
				wechatActivityLottery.setbDate(Utils.changeDateFormat(wechatActivityLottery.getbDate(), "yy年MM月dd日", "yyyy/MM/dd"));
				wechatActivityLottery.seteDate(Utils.changeDateFormat(wechatActivityLottery.geteDate(), "yy年MM月dd日", "yyyy/MM/dd"));
				wechatActivityLottery.setCompanyId(dept.getCompanyId());
				wechatActivityLottery.setDeptCode(dept.getDeptCode());
				wechatActivityLottery.setDeptName(dept.getDeptName());
				wechatActivityLottery.setCreateBy(user.getUserName());
				wechatActivityLottery.setUpdateBy(user.getUserName());
				wechatActivityLotteryService.insert(wechatActivityLottery);
				this.setResult(true, "新增成功！");
			} catch (Exception e) {
				e.printStackTrace();
				this.setResult(false, "新增失败！");
			}
		} else
			this.setResult(false, "数据错误！");
		return SUCCESS;
	}
	
	/**
	 * 修改抽奖活动信息
	 * @return
	 */
	public String updateWechatActivityLottery() {
		if(wechatActivityLottery != null) {
			try {
				SysUser user = getUser();
				wechatActivityLottery.setbDate(Utils.changeDateFormat(wechatActivityLottery.getbDate(), "yy年MM月dd日", "yyyy/MM/dd"));
				wechatActivityLottery.seteDate(Utils.changeDateFormat(wechatActivityLottery.geteDate(), "yy年MM月dd日", "yyyy/MM/dd"));
				wechatActivityLottery.setUpdateBy(user.getUserName());
				wechatActivityLotteryService.update(wechatActivityLottery);
				this.setResult(true, "修改成功！");
			} catch (Exception e) {
				e.printStackTrace();
				this.setResult(false, "修改失败！");
			}
		} else
			this.setResult(false, "修改错误！");
		return SUCCESS;
	}
	
	/**
	 * 删除抽奖活动信息
	 * @return
	 */
	public String deleteWechatActivityLottery() {
		try {
			if(!Utils.isEmpty(wechatActivityLottery) && !Utils.isEmpty(wechatActivityLottery.getId())){
				wechatActivityLotteryService.delete(wechatActivityLottery);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 新增奖品信息
	 * @return
	 */
	public String insertWechatActivityLotteryItem() {
		if(wechatActivityLotteryItem != null) {
			try {
				SysUser user = getUser();
				wechatActivityLotteryItem.setCreateBy(user.getUserName());
				wechatActivityLotteryItem.setUpdateBy(user.getUserName());
				wechatActivityLotteryItemService.insert(wechatActivityLotteryItem);
				this.setResult(true, "新增成功！");
			} catch (Exception e) {
				e.printStackTrace();
				this.setResult(false, "新增失败！");
			}
		} else
			this.setResult(false, "数据错误！");
		return SUCCESS;
	}
	
	/**
	 * 修改奖品信息
	 * @return
	 */
	public String updateWechatActivityLotteryItem() {
		if(wechatActivityLotteryItem != null) {
			try {
				SysUser user = getUser();
				wechatActivityLotteryItem.setUpdateBy(user.getUserName());
				wechatActivityLotteryItemService.update(wechatActivityLotteryItem);
				this.setResult(true, "修改成功！");
			} catch (Exception e) {
				e.printStackTrace();
				this.setResult(false, "修改失败！");
			}
		} else
			this.setResult(false, "修改错误！");
		return SUCCESS;
	}
	
	/**
	 * 删除奖品信息
	 * @return
	 */
	public String deleteWechatActivityLotteryItem() {
		try {
			if(!Utils.isEmpty(wechatActivityLotteryItem) && !Utils.isEmpty(wechatActivityLotteryItem.getId())){
				wechatActivityLotteryItemService.delete(wechatActivityLotteryItem);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public WechatActivityLottery getWechatActivityLottery() {
		return wechatActivityLottery;
	}

	public void setWechatActivityLottery(WechatActivityLottery wechatActivityLottery) {
		this.wechatActivityLottery = wechatActivityLottery;
	}

	public WechatActivityLotteryVo getWechatActivityLotteryVo() {
		return wechatActivityLotteryVo;
	}

	public void setWechatActivityLotteryVo(WechatActivityLotteryVo wechatActivityLotteryVo) {
		this.wechatActivityLotteryVo = wechatActivityLotteryVo;
	}

	public List<WechatActivityLottery> getWechatActivityLotteryList() {
		return wechatActivityLotteryList;
	}

	public void setWechatActivityLotteryList(List<WechatActivityLottery> wechatActivityLotteryList) {
		this.wechatActivityLotteryList = wechatActivityLotteryList;
	}

	public WechatActivityLotteryService getWechatActivityLotteryService() {
		return wechatActivityLotteryService;
	}

	@Resource
	public void setWechatActivityLotteryService(WechatActivityLotteryService wechatActivityLotteryService) {
		this.wechatActivityLotteryService = wechatActivityLotteryService;
	}

	public WechatActivityLotteryItemService getWechatActivityLotteryItemService() {
		return wechatActivityLotteryItemService;
	}

	@Resource
	public void setWechatActivityLotteryItemService(WechatActivityLotteryItemService wechatActivityLotteryItemService) {
		this.wechatActivityLotteryItemService = wechatActivityLotteryItemService;
	}

	public List<WechatActivityLotteryItem> getWechatActivityLotteryItemList() {
		return wechatActivityLotteryItemList;
	}

	public void setWechatActivityLotteryItemList(List<WechatActivityLotteryItem> wechatActivityLotteryItemList) {
		this.wechatActivityLotteryItemList = wechatActivityLotteryItemList;
	}

	public WechatActivityLotteryItem getWechatActivityLotteryItem() {
		return wechatActivityLotteryItem;
	}

	public void setWechatActivityLotteryItem(WechatActivityLotteryItem wechatActivityLotteryItem) {
		this.wechatActivityLotteryItem = wechatActivityLotteryItem;
	}

	public WechatActivityLotteryItemVo getWechatActivityLotteryItemVo() {
		return wechatActivityLotteryItemVo;
	}

	public void setWechatActivityLotteryItemVo(WechatActivityLotteryItemVo wechatActivityLotteryItemVo) {
		this.wechatActivityLotteryItemVo = wechatActivityLotteryItemVo;
	}
	
}
