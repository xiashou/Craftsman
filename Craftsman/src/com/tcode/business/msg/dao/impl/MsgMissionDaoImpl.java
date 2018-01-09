package com.tcode.business.msg.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.tcode.business.msg.dao.MsgMissionDao;
import com.tcode.business.msg.model.MsgMission;
import com.tcode.common.dao.BaseDao;

@Component("msgMissionDao")
public class MsgMissionDaoImpl extends BaseDao<MsgMission, Serializable> implements MsgMissionDao {


}
