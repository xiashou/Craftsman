package com.tcode.business.basic.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.basic.dao.BaseImageDao;
import com.tcode.business.basic.model.BaseImage;
import com.tcode.common.dao.BaseDao;

@Component("baseImageDao")
public class BaseImageDaoImpl extends BaseDao<BaseImage, Serializable> implements BaseImageDao {

	@Override
	public BaseImage loadById(Integer id) throws Exception {
		return super.loadById(BaseImage.class, id);
	}

	@Override
	public List<BaseImage> loadByIds(String ids) throws Exception {
		return super.loadList("from BaseImage i where i.imageId in (" + ids.substring(0, ids.length() - 1) + ")");
	}

}
