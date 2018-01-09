package com.tcode.business.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallMaintainDao;
import com.tcode.business.mall.model.MallMaintain;
import com.tcode.business.mall.service.MallMaintainService;
import com.tcode.core.util.Utils;

@Component("mallMaintainService")
public class MallMaintainServiceImpl implements MallMaintainService {
	
	private MallMaintainDao mallMaintainDao;

	@Override
	public List<MallMaintain> getListByModel(String appId, Integer modelId) throws Exception {
		return mallMaintainDao.loadListByModel(appId, modelId);
	}
	
	@Override
	public Integer saveMoreMallMaintain(String appId, Integer[] modelIds, String[] goodsIds) throws Exception {
		int count = 0;
		if(!Utils.isEmpty(modelIds) && !Utils.isEmpty(goodsIds)){
			mallMaintainDao.removeByOnly(appId, modelIds);
			for(Integer modelId : modelIds){
				for(String goodsId : goodsIds){
					MallMaintain maintain = new MallMaintain();
					maintain.setAppId(appId);
					maintain.setGoodsId(goodsId);
					maintain.setModelId(modelId);
					mallMaintainDao.save(maintain);
					count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * 查询关联是否存在
	 * @param appId
	 * @param modelId
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public MallMaintain getByOnly(String appId, Integer modelId, String goodsId) throws Exception {
		List<MallMaintain> mainList = mallMaintainDao.loadByOnly(appId, modelId, goodsId);
		if(!Utils.isEmpty(mainList) && mainList.size() > 0)
			return mainList.get(0);
		else
			return null;
	}

	@Override
	public MallMaintain getById(Integer id) throws Exception {
		return mallMaintainDao.loadById(id);
	}

	@Override
	public void insert(MallMaintain maintain) throws Exception {
		mallMaintainDao.save(maintain);
	}

	@Override
	public void update(MallMaintain maintain) throws Exception {
		mallMaintainDao.edit(maintain);
	}

	@Override
	public void delete(MallMaintain maintain) throws Exception {
		mallMaintainDao.remove(maintain);
	}

	public MallMaintainDao getMallMaintainDao() {
		return mallMaintainDao;
	}
	@Resource
	public void setMallMaintainDao(MallMaintainDao mallMaintainDao) {
		this.mallMaintainDao = mallMaintainDao;
	}




}
