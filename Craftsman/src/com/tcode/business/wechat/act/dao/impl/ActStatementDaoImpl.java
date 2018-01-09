package com.tcode.business.wechat.act.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tcode.business.wechat.act.dao.ActStatementDao;
import com.tcode.business.wechat.act.model.ActStatement;
import com.tcode.common.dao.BaseDao;

@Component("actStatementDao")
public class ActStatementDaoImpl extends BaseDao<ActStatement, Serializable> implements ActStatementDao {

	@Override
	public ActStatement loadById(String appId) throws Exception {
		return (ActStatement)super.loadEntity("from ActStatement s where s.appId = ?", appId);
	}

}
