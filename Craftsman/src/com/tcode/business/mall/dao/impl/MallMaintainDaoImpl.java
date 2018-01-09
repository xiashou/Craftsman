package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallMaintainDao;
import com.tcode.business.mall.model.MallMaintain;
import com.tcode.common.dao.BaseDao;

@Component("mallMaintainDao")
public class MallMaintainDaoImpl extends BaseDao<MallMaintain, Serializable> implements MallMaintainDao {

	@Override
	public MallMaintain loadById(Integer id) throws Exception {
		return super.loadById(MallMaintain.class, id);
	}

	@Override
	public List<MallMaintain> loadListByModel(String appId, Integer modelId) throws Exception {
		return super.loadList("from MallMaintain m where m.appId = ? and m.modelId = ?", appId, modelId);
	}
	
	@Override
	public List<MallMaintain> loadByOnly(String appId, Integer modelId, String goodsId) throws Exception {
		return super.loadList("from MallMaintain m where m.appId = ? and m.modelId = ? and m.goodsId = ?", appId, modelId, goodsId);
	}
	
	@Override
	public void removeByOnly(String appId, Integer[] modelIds) throws Exception {
		super.executeHql("delete from MallMaintain m where m.appId = ? and m.modelId in (" + Arrays.toString(modelIds).substring(1, Arrays.toString(modelIds).length()  - 1) + ")", appId);
	}

}
