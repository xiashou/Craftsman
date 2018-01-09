package com.tcode.business.goods.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tcode.business.goods.model.GoodsPackage;
import com.tcode.business.goods.service.GoodsPackageService;
import com.tcode.common.action.BaseAction;
import com.tcode.common.idgenerator.IDHelper;
import com.tcode.core.util.Utils;

@Scope("prototype")
@Component("goodsPackageAction")
public class GoodsPackageAction extends BaseAction {

	private static final long serialVersionUID = -7806180381242759905L;
	private static Logger log = Logger.getLogger("SLog");

	private GoodsPackageService goodsPackageService;

	private List<GoodsPackage> packageList;
	private GoodsPackage goodsPackage;
	private int id;

	/**
	 * 根据门店查询所有套餐商品
	 * 
	 * @return
	 */
	public String queryAllPackageGoods() {
		try {
			packageList = goodsPackageService.getGoodsPackageByDeptCode(this.getDept().getDeptCode(), this.getDept().getCompanyId());
		} catch (Exception e) {
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	/**
	 * 保存套餐
	 * @return
	 */
	public String savePackageGoods() {
		try {
			if (!Utils.isEmpty(goodsPackage)) {
				// 判断ID是否为空，为空则新增，不为空则修改
				if (Utils.isEmpty(goodsPackage.getId())) {
					//先根据套餐名称判断是否已存在
					GoodsPackage search = new GoodsPackage();
					search.setName(goodsPackage.getName());
					search.setDeptCode(this.getDept().getDeptCode());
					int count = goodsPackageService.getListCount(search);
					if (count == 0) {
						goodsPackage.setId(IDHelper.getPackageId());
						goodsPackage.setDeptCode(this.getDept().getDeptCode());
						goodsPackage.setCreateTime(Utils.getSysTime());
						goodsPackageService.insert(goodsPackage);
					}
				} else {
					goodsPackage.setDeptCode(this.getDept().getDeptCode());
					goodsPackageService.update(goodsPackage);
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
	 * 删除套餐商品
	 * @return
	 */
	public String deletePackageGoods() {
		try {
			if (!Utils.isEmpty(goodsPackage) && !Utils.isEmpty(goodsPackage.getId())) {
				goodsPackageService.delete(goodsPackage);
				this.setResult(true, "删除成功！");
			}
		} catch (Exception e) {
			this.setResult(false, "删除失败！");
			log.error(Utils.getErrorMessage(e));
		}
		return SUCCESS;
	}

	public GoodsPackageService getGoodsPackageService() {
		return goodsPackageService;
	}

	@Resource
	public void setGoodsPackageService(GoodsPackageService goodsPackageService) {
		this.goodsPackageService = goodsPackageService;
	}
	public List<GoodsPackage> getPackageList() {
		return packageList;
	}
	public void setPackageList(List<GoodsPackage> packageList) {
		this.packageList = packageList;
	}
	public GoodsPackage getGoodsPackage() {
		return goodsPackage;
	}
	public void setGoodsPackage(GoodsPackage goodsPackage) {
		this.goodsPackage = goodsPackage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
