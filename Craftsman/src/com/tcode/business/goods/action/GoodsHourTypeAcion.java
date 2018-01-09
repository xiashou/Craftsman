package com.tcode.business.goods.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.model.GoodsHourType;
import com.tcode.business.goods.service.GoodsHourService;
import com.tcode.business.goods.service.GoodsHourTypeService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;
import com.tcode.system.model.SysDept;

@Scope("prototype")
@Component("goodsHourTypeAction")
public class GoodsHourTypeAcion extends BaseAction implements SessionAware {

	private static final long serialVersionUID = 6582854975822171645L;
	private static Logger log = Logger.getLogger("SLog");
	
	private Map<String, Object> session;
	private GoodsHourTypeService goodsHourTypeService;
	private GoodsHourService goodsHourService;
	
	private List<GoodsHourType> hourTypeList;
	private GoodsHourType hourType;
	
	public String queryHourTypeByDeptCode() {
		try {
			hourTypeList = goodsHourTypeService.getTypeByDeptCode(((SysDept)session.get(SESSION_DEPT)).getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有工时类商品类型
	 * @return
	 */
	public String queryAllHourType() {
		try {
			this.setTotalCount(goodsHourTypeService.getListCount(hourType));
			hourTypeList = goodsHourTypeService.getTypeByDeptCode(this.getDept().getDeptCode());
		} catch(Exception e){
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String insertHourType() {
		try {
			if(!Utils.isEmpty(hourType)){
				GoodsHourType search = new GoodsHourType();
				search.setDeptCode(this.getDept().getDeptCode());
				search.setTypeName(hourType.getTypeName());
				int count = goodsHourTypeService.getListCount(search);
				if(count==0){
					hourType.setDeptCode(this.getDept().getDeptCode());
					goodsHourTypeService.insert(hourType);
					this.setResult(true, "添加成功！");
				}else{
					this.setResult(false, "类型名称已存在！");
				}
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String updateHourType() {
		try {
			if(!Utils.isEmpty(hourType) && !Utils.isEmpty(hourType.getId())){
				hourType.setDeptCode(this.getDept().getDeptCode());
				goodsHourTypeService.update(hourType);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteHourType() {
		try {
			if(!Utils.isEmpty(hourType) && !Utils.isEmpty(hourType.getId())){
				goodsHourTypeService.delete(hourType);
				GoodsHour goodsHour = new GoodsHour();
				goodsHour.setTypeId(hourType.getId());
				goodsHourService.deleteByType(goodsHour);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public GoodsHourTypeService getGoodsHourTypeService() {
		return goodsHourTypeService;
	}
	@Resource
	public void setGoodsHourTypeService(GoodsHourTypeService goodsHourTypeService) {
		this.goodsHourTypeService = goodsHourTypeService;
	}
	
	public GoodsHourService getGoodsHourService() {
		return goodsHourService;
	}
	@Resource
	public void setGoodsHourService(GoodsHourService goodsHourService) {
		this.goodsHourService = goodsHourService;
	}

	public List<GoodsHourType> getHourTypeList() {
		return hourTypeList;
	}
	public void setHourTypeList(List<GoodsHourType> hourTypeList) {
		this.hourTypeList = hourTypeList;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session; 
	}
	public Map<String, Object> getSession() {
		return session;
	}

	public GoodsHourType getHourType() {
		return hourType;
	}

	public void setHourType(GoodsHourType hourType) {
		this.hourType = hourType;
	}
	
}
