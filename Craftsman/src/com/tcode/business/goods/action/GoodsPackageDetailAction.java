package com.tcode.business.goods.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsHour;
import com.tcode.business.goods.model.GoodsPackageDetail;
import com.tcode.business.goods.service.GoodsPackageDetailService;
import com.tcode.common.action.BaseAction;
import com.tcode.core.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Scope("prototype")
@Component("goodsPackageDetailAction")
public class GoodsPackageDetailAction extends BaseAction {

	private static final long serialVersionUID = -7806180381242759905L;
	private static Logger log = Logger.getLogger("SLog");
	
	private GoodsPackageDetailService packageDetailService;
	
	private List<GoodsPackageDetail> packageDetailList;
	private GoodsPackageDetail packageDetail;
	private String packageId;
	
	private String jsonStr;
	
	private String keyword;
	private List<GoodsHour> goodsList;
	
	/**
	 * 根据门店查询所有套餐商品
	 * @return
	 */
	public String queryPackageDetailById() {
		try {
			packageDetailList = packageDetailService.getGoodsPackageDetailById(packageId);
		} catch(Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 保存套餐详情
	 * @return
	 */
	public String savePackageDetail(){
		try {
			if(!Utils.isEmpty(packageId)){
				GoodsPackageDetail delGp = new GoodsPackageDetail();
				delGp.setGpId(packageId);
				packageDetailService.deleteById(delGp);
			}
			if(!Utils.isEmpty(jsonStr)){
				JSONArray array = JSONArray.fromObject(jsonStr);
				int count = 10;
				for (Object object : array) {
					JSONObject json = (JSONObject) object;
					GoodsPackageDetail packageDetail = (GoodsPackageDetail) JSONObject.toBean(json, GoodsPackageDetail.class);
					if(!Utils.isEmpty(packageDetail) && !Utils.isEmpty(packageDetail.getGpId()) && packageDetail.getNumber() > 0){
						GoodsPackageDetail exist = packageDetailService.getPackageDetailById(packageDetail.getGpId(), packageDetail.getItemNo());
						if(!Utils.isEmpty(exist)){
							packageDetailService.update(packageDetail);
						} else {
							GoodsPackageDetail itemNo = new GoodsPackageDetail();
							itemNo.setGpId(packageDetail.getGpId());
							if(!Utils.isEmpty(packageDetail.getGoodsId())){
								if("1".equals(packageDetail.getGoodsId().substring(0, 1)))
									packageDetail.setGoodsType(1);
								else if("3".equals(packageDetail.getGoodsId().substring(0, 1)))
									packageDetail.setGoodsType(2);
							}
							packageDetail.setItemNo(count);
							packageDetailService.insert(packageDetail);
							count += 10;
						}
					}
				}
				this.setResult(true, "保存成功！");
			}
		} catch (Exception e) {
			this.setResult(false, "保存失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	/**
	 * 修改工时类商品
	 * @return
	 */
	public String updatePackageGoodsDetail() {
		try {
			if(!Utils.isEmpty(packageDetail) && !Utils.isEmpty(packageDetail.getGpId()) && !Utils.isEmpty(packageDetail.getItemNo())){
				packageDetailService.update(packageDetail);
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
	public String deletePackageGoodsDetail() {
		try {
			if(!Utils.isEmpty(packageDetail) && !Utils.isEmpty(packageDetail.getGpId()) && !Utils.isEmpty(packageDetail.getItemNo())){
				packageDetailService.delete(packageDetail);
				this.setResult(true, "删除成功！");
			}
		} catch(Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	/**
	 * 根据关键字查询商品
	 * @return
	 */
	public String queryGoodsByKeyword(){
		try {
			if(!Utils.isEmpty(keyword)){
				goodsList = packageDetailService.getGoodsByKeyword(this.getDept().getDeptCode(), this.getDept().getCompanyId(), keyword);
			}
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}
	
	public GoodsPackageDetailService getPackageDetailService() {
		return packageDetailService;
	}
	@Resource
	public void setPackageDetailService(GoodsPackageDetailService packageDetailService) {
		this.packageDetailService = packageDetailService;
	}

	public List<GoodsPackageDetail> getPackageDetailList() {
		return packageDetailList;
	}

	public void setPackageDetailList(List<GoodsPackageDetail> packageDetailList) {
		this.packageDetailList = packageDetailList;
	}

	public GoodsPackageDetail getPackageDetail() {
		return packageDetail;
	}

	public void setPackageDetail(GoodsPackageDetail packageDetail) {
		this.packageDetail = packageDetail;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<GoodsHour> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<GoodsHour> goodsList) {
		this.goodsList = goodsList;
	}
}
