package com.tcode.business.goods.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.service.GoodsHourService;
import com.tcode.common.action.BaseAction;
import com.tcode.common.idgenerator.IDHelper;
import com.tcode.core.util.Pinyin;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("goodsHourAction")
public class GoodsHourAction extends BaseAction {

	private static final long serialVersionUID = -7806180381242759905L;
	private static Logger log = Logger.getLogger("SLog");
	
	private GoodsHourService goodsHourService;
	
	private List<GoodsHour> hourList;
	private Integer typeId;
	private GoodsHour hour;
	
	/**
	 * 根据类型查询工时类商品
	 * @return
	 */
	public String queryGoodsHourByType() {
		try {
			if(!Utils.isEmpty(typeId)){
				hourList = goodsHourService.getGoodsHourByType(typeId);
			}
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 所有工时商品
	 * @return
	 */
	public String queryAllGoodsHour() {
		try {
			hourList = goodsHourService.getGoodsHourByDeptCode(this.getDept().getDeptCode());
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	/**
	 * 添加工时类商品
	 * @return
	 */
	public String insertGoodsHour() {
		try {
			if(!Utils.isEmpty(hour)){
				GoodsHour search = new GoodsHour();
				search.setDeptCode(hour.getDeptCode());
				search.setName(hour.getName());
				search.setTypeId(hour.getTypeId());
				int count = goodsHourService.getListCount(search);
				if(count==0){
					hour.setId(IDHelper.getHourID());
					hour.setDeptCode(this.getDept().getDeptCode());
					hour.setShorthand(Pinyin.getPinYinHeadChar(hour.getName()));
					goodsHourService.insert(hour);
					this.setResult(true, "添加成功！");
				}else{
					this.setResult(false, "商品名称已存在！");
				}
			}
		} catch(Exception e) {
			this.setResult(false, "添加失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改工时类商品
	 * @return
	 */
	public String updateGoodsHour() {
		try {
			if(!Utils.isEmpty(hour) && !Utils.isEmpty(hour.getId())){
				hour.setDeptCode(this.getDept().getDeptCode());
				hour.setShorthand(Pinyin.getPinYinHeadChar(hour.getName()));
				goodsHourService.update(hour);
				this.setResult(true, "修改成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "修改失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 删除工时类商品
	 * @return
	 */
	public String deleteGoodsHour() {
		try {
			if(!Utils.isEmpty(hour) && !Utils.isEmpty(hour.getId())){
				goodsHourService.delete(hour);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public GoodsHourService getGoodsHourService() {
		return goodsHourService;
	}
	@Resource
	public void setGoodsHourService(GoodsHourService goodsHourService) {
		this.goodsHourService = goodsHourService;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public List<GoodsHour> getHourList() {
		return hourList;
	}
	public void setHourList(List<GoodsHour> hourList) {
		this.hourList = hourList;
	}
	public GoodsHour getHour() {
		return hour;
	}
	public void setHour(GoodsHour hour) {
		this.hour = hour;
	}

}
