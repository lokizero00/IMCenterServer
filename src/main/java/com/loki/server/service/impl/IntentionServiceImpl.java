package com.loki.server.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.IntentionLogDao;
import com.loki.server.dto.PagedResult;
import com.loki.server.entity.Intention;
import com.loki.server.entity.IntentionLog;
import com.loki.server.service.IntentionService;
import com.loki.server.utils.BeanUtil;

@Service
@Transactional
public class IntentionServiceImpl implements IntentionService {
	@Resource IntentionDao intentionDao;
	@Resource IntentionLogDao intentionLogDao;
	
	@Override
	public HashMap<String, Object> getIntention(int userId) {
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		if(userId>0) {
			Intention intention=intentionDao.findByUserId(userId);
			if(null!=intention) {
				returnValue.put("resultCode",1);
				returnValue.put("resultObj",intention);
			}else {
				returnValue.put("resultCode",16);
				returnValue.put("msg","意向金账户不存在");
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

	@Override
	public HashMap<String, Object> getIntentionLog(int userId,int intentionId,int adminId,String type,Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null? 1:pageNo;  
	    pageSize = pageSize == null? 10:pageSize; 
		HashMap<String,Object> returnValue=new HashMap<String,Object>();
		
		if(userId>0) {
			PageHelper.startPage(pageNo,pageSize);
			PagedResult<IntentionLog> pageResult=BeanUtil.toPagedResult(intentionLogDao.findByParam(userId, intentionId, adminId, type));
			if(null!=pageResult) {
				returnValue.put("resultCode",1);
				returnValue.put("resultObj",pageResult);
			}else {
				returnValue.put("resultCode",16);
				returnValue.put("msg","意向金账户不存在");
			}
		}else {
			returnValue.put("resultCode",3);
			returnValue.put("msg","参数错误");
		}
		return returnValue;
	}

}
