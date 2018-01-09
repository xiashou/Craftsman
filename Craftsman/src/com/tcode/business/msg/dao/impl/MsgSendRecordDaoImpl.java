package com.tcode.business.msg.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.tcode.business.msg.dao.MsgSendRecordDao;
import com.tcode.business.msg.model.MsgSendRecord;
import com.tcode.business.msg.vo.MsgSendRecordVo;
import com.tcode.common.dao.BaseDao;
import com.tcode.core.util.Utils;

@Component("msgSendRecordDao")
public class MsgSendRecordDaoImpl extends BaseDao<MsgSendRecord, Serializable> implements MsgSendRecordDao {

	//根据任务ID查询短信记录,排序规则不可改变（供通知类短信频率计算）
	@Override
	public List<MsgSendRecord> loadByMissionID(int missionID) throws Exception {
		return super.loadList("from MsgSendRecord m where m.missionID = ? order by m.updateTime DESC", missionID);
	}
	
	@Override
	public List<MsgSendRecord> loadListPage(MsgSendRecordVo msgSendRecordVo, int start, int limit) throws Exception {
		List<MsgSendRecord> list = null;
		DetachedCriteria criteria = connectionCriteria(msgSendRecordVo);
		criteria.addOrder(Order.desc("updateTime"));
		list = (List<MsgSendRecord>) super.loadListForPage(criteria, start, limit);
		return list;
	}

	@Override
	public Integer loadListCount(MsgSendRecordVo msgSendRecordVo) throws Exception {
		int totalCount = 0;
		DetachedCriteria criteria = connectionCriteria(msgSendRecordVo);
		totalCount = loadListCount(criteria);
		return totalCount;
	}
	
	public DetachedCriteria connectionCriteria(MsgSendRecordVo msgSendRecordVo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MsgSendRecord.class);
		if (msgSendRecordVo != null) {
			if (msgSendRecordVo.getId() != 0)
				criteria.add(Restrictions.eq("id", msgSendRecordVo.getId()));
			if (!Utils.isEmpty(msgSendRecordVo.getDeptCode()))
				criteria.add(Restrictions.eq("deptCode", msgSendRecordVo.getDeptCode()));
			if (!Utils.isEmpty(msgSendRecordVo.getVipNo()))
				criteria.add(Restrictions.eq("vipNo", msgSendRecordVo.getVipNo()));
			if (!Utils.isEmpty(msgSendRecordVo.getMobile()))
				criteria.add(Restrictions.eq("mobile", msgSendRecordVo.getMobile()));
		}
		return criteria;
	}
}
