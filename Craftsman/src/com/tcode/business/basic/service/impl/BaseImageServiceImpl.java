package com.tcode.business.basic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseImageDao;
import com.tcode.business.basic.model.BaseImage;
import com.tcode.business.basic.service.BaseImageService;

@Component("baseImageService")
public class BaseImageServiceImpl implements BaseImageService {
	
	private BaseImageDao baseImageDao;

	@Override
	public BaseImage getById(Integer id) throws Exception {
		return baseImageDao.loadById(id);
	}

	@Override
	public List<BaseImage> getByIds(String ids) throws Exception {
		return baseImageDao.loadByIds(ids);
	}

	@Override
	public void insert(BaseImage image) throws Exception {
		baseImageDao.save(image);
	}

	@Override
	public void update(BaseImage image) throws Exception {
		baseImageDao.edit(image);
	}

	@Override
	public void delete(BaseImage image) throws Exception {
		baseImageDao.remove(image);
	}

	public BaseImageDao getBaseImageDao() {
		return baseImageDao;
	}
	@Resource
	public void setBaseImageDao(BaseImageDao baseImageDao) {
		this.baseImageDao = baseImageDao;
	}

}
