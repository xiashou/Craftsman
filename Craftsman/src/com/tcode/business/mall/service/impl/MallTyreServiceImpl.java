package com.tcode.business.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallTyreDao;
import com.tcode.business.mall.model.MallTyre;
import com.tcode.business.mall.service.MallTyreService;
import com.tcode.core.util.Utils;

@Component("mallTyreService")
public class MallTyreServiceImpl implements MallTyreService {
	
	private MallTyreDao mallTyreDao;

	@Override
	public List<MallTyre> getListByModel(String appId, Integer modelId) throws Exception {
		return mallTyreDao.loadListByModel(appId, modelId);
	}
	
	@Override
	public List<MallTyre> getListBySeries(String appId, Integer seriesId) throws Exception {
		return mallTyreDao.loadListBySeries(appId, seriesId);
	}
	
	@Override
	public MallTyre getById(Integer id) throws Exception {
		return mallTyreDao.loadById(id);
	}
	
	@Override
	public Integer saveMoreMallTyre(String appId, Integer[] modelIds, String[] goodsIds) throws Exception {
		int count = 0;
		if(!Utils.isEmpty(modelIds) && !Utils.isEmpty(goodsIds)){
			mallTyreDao.removeByOnly(appId, modelIds);
			for(Integer modelId : modelIds){
				for(String goodsId : goodsIds){
					MallTyre tyre = new MallTyre();
					tyre.setAppId(appId);
					tyre.setGoodsId(goodsId);
					tyre.setModelId(modelId);
					mallTyreDao.save(tyre);
					count++;
				}
			}
		}
		return count;
	}
	
	@Override
	public void insert(MallTyre mallTyre) throws Exception {
		mallTyreDao.save(mallTyre);
	}

	@Override
	public void update(MallTyre mallTyre) throws Exception {
		mallTyreDao.edit(mallTyre);
	}

	@Override
	public void delete(MallTyre mallTyre) throws Exception {
		mallTyreDao.remove(mallTyre);
	}

	public MallTyreDao getMallTyreDao() {
		return mallTyreDao;
	}
	@Resource
	public void setMallTyreDao(MallTyreDao mallTyreDao) {
		this.mallTyreDao = mallTyreDao;
	}


}
