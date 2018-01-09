package com.tcode.business.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tcode.business.mall.dao.MallCartDao;
import com.tcode.business.mall.model.MallCart;
import com.tcode.business.mall.service.MallCartService;
import com.tcode.core.util.Utils;

@Component("mallCartService")
public class MallCartServiceImpl implements MallCartService {
	
	private MallCartDao mallCartDao;

	@Override
	public MallCart getById(String goodsId, Integer memId) throws Exception {
		return mallCartDao.loadById(goodsId, memId);
	}

	@Override
	public List<MallCart> getListByMemId(Integer memId) throws Exception {
		return mallCartDao.loadByMemId(memId);
	}

	@Override
	public void insert(MallCart cart) throws Exception {
		MallCart exist = mallCartDao.loadById(cart.getGoodsId(), cart.getMemId());
		if(!Utils.isEmpty(exist)){
			exist.setNumber(exist.getNumber() + cart.getNumber());
			mallCartDao.edit(exist);
		} else
			mallCartDao.save(cart);
	}

	@Override
	public void update(MallCart cart) throws Exception {
		mallCartDao.edit(cart);
	}

	@Override
	public void delete(MallCart cart) throws Exception {
		mallCartDao.remove(cart);
	}
	
	
	public MallCartDao getMallCartDao() {
		return mallCartDao;
	}
	@Resource
	public void setMallCartDao(MallCartDao mallCartDao) {
		this.mallCartDao = mallCartDao;
	}

}
