package com.tcode.business.mall.dao.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallTyreDao;
import com.tcode.business.mall.model.MallTyre;
import com.tcode.common.dao.BaseDao;

@Component("mallTyreDao")
public class MallTyreDaoImpl extends BaseDao<MallTyre, Serializable> implements MallTyreDao {

	@Override
	public MallTyre loadById(Integer id) throws Exception {
		return super.loadById(MallTyre.class, id);
	}

	@Override
	public List<MallTyre> loadListByModel(String appId, Integer modelId) throws Exception {
		return super.loadList("from MallTyre t where t.appId = ? and t.modelId = ?", appId, modelId);
	}

	@Override
	public List<MallTyre> loadByOnly(String appId, Integer modelId, String goodsId) throws Exception {
		return super.loadList("from MallTyre t where t.appId = ? and t.modelId = ? and t.goodsId = ?", appId, modelId, goodsId);
	}
	
	@Override
	public List<MallTyre> loadListBySeries(String appId, Integer seriesId) throws Exception {
		return super.loadList("from MallTyre t where t.appId = ? and t.modelId in (from BaseCarSeries s where s.id = ?)", appId, seriesId);
	}

	@Override
	public void removeByOnly(String appId, Integer[] modelIds) throws Exception {
		super.executeHql("delete from MallTyre t where t.appId = ? and t.modelId in (" + Arrays.toString(modelIds).substring(1, Arrays.toString(modelIds).length()  - 1) + ")", appId);
	}

}
