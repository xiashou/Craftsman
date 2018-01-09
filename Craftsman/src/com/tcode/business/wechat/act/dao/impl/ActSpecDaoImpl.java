package com.tcode.business.wechat.act.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.ActSpecDao;
import com.tcode.business.wechat.act.model.ActSpec;
import com.tcode.common.dao.BaseDao;

@Component("actSpecDao")
public class ActSpecDaoImpl extends BaseDao<ActSpec, Serializable> implements ActSpecDao {

	@Override
	public ActSpec loadById(Integer id) throws Exception {
		return super.loadById(ActSpec.class, id);
	}

	@Override
	public List<ActSpec> loadByActId(Integer actId) throws Exception {
		return super.loadList("from ActSpec s where s.actId = ?", actId);
	}

}
