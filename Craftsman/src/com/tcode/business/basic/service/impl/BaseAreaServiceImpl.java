package com.tcode.business.basic.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseAreaDao;
import com.tcode.business.basic.model.BaseArea;
import com.tcode.business.basic.service.BaseAreaService;
import com.tcode.core.util.Utils;

@Component("baseAreaService")
public class BaseAreaServiceImpl implements BaseAreaService {
	
	private BaseAreaDao baseAreaDao;
	
	
	@Override
	public List<BaseArea> getAll() throws Exception {
		return baseAreaDao.loadAll();
	}

	@Override
	public BaseArea getAreaById(String areaId) throws Exception {
		return baseAreaDao.loadById(areaId);
	}
	
	@Override
	public List<BaseArea> getAllProvince() throws Exception {
		return baseAreaDao.loadAllProvince();
	}
	
	@Override
	public BaseArea getAreaByMemId(Integer memId) throws Exception {
		return baseAreaDao.loadByMemId(memId);
	}
	
	@Override
	public List<BaseArea> getBaseAreaTree() throws Exception {
		List<BaseArea> treeList = baseAreaDao.loadAllProvince();
		for(BaseArea area : treeList){
			List<BaseArea> childList = transferBaseAreaTree(baseAreaDao.loadCityById(area.getAreaId()));
			if(Utils.isEmpty(childList))
				area.setLeaf(true);
			else
				area.setLeaf(false);
			area.setChildren(childList);
			area.setId(area.getAreaId());
			area.setText(area.getAreaName());
			area.setExpanded(false);
		}
		return treeList;
	}
	
	@Override
	public List<BaseArea> getBaseAreaTreeByAgent(String areaId) throws Exception {
		List<BaseArea> treeList = new ArrayList<BaseArea>();
		treeList.add(baseAreaDao.loadById(areaId));
		for(BaseArea area : treeList){
			List<BaseArea> childList = transferBaseAreaTree(baseAreaDao.loadCityById(area.getAreaId()));
			if(Utils.isEmpty(childList))
				area.setLeaf(true);
			else
				area.setLeaf(false);
			area.setChildren(childList);
			area.setId(area.getAreaId());
			area.setText(area.getAreaName());
			area.setExpanded(false);
		}
		return treeList;
	}
	
	public List<BaseArea> transferBaseAreaTree(List<BaseArea> areaList) {
		for(BaseArea area : areaList){
			area.setText(area.getAreaName());
			area.setId(area.getAreaId());
			area.setLeaf(true);
			area.setExpanded(false);
		}
		return areaList;
	}

	@Override
	public void insert(BaseArea area) throws Exception {
		baseAreaDao.save(area);
	}

	@Override
	public void update(BaseArea area) throws Exception {
		baseAreaDao.edit(area);
	}

	@Override
	public void delete(BaseArea area) throws Exception {
		baseAreaDao.remove(area);
	}

	@Override
	public List<BaseArea> getListPage(BaseArea area, int start, int limit) throws Exception {
		return baseAreaDao.loadListPage(area, start, limit);
	}

	@Override
	public Integer getListCount(BaseArea area) throws Exception {
		return baseAreaDao.loadListCount(area);
	}

	public BaseAreaDao getBaseAreaDao() {
		return baseAreaDao;
	}
	@Resource
	public void setBaseAreaDao(BaseAreaDao baseAreaDao) {
		this.baseAreaDao = baseAreaDao;
	}

}
