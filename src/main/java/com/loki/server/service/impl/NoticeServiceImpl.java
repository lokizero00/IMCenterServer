package com.loki.server.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.loki.server.dao.NoticeComplexDao;
import com.loki.server.dao.NoticeDao;
import com.loki.server.dao.UserNoticeDao;
import com.loki.server.entity.Notice;
import com.loki.server.entity.NoticeComplex;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.UserNotice;
import com.loki.server.service.NoticeService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {
	@Resource NoticeDao noticeDao;
	@Resource UserNoticeDao userNoticeDao;
	@Resource NoticeComplexDao noticeComplexDao;

	@Override
	public ServiceResult<Void> addNotice(Notice notice) {
		ServiceResult<Void> returnValue=new ServiceResult<Void>();
		if(notice!=null) {
			notice.setId(0);
			noticeDao.insert(notice);
			if(notice.getId()>0) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
			}else {
				returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	//TODO 对于for循环中，部分保存失败的情况暂时未处理
	@Override
	public ServiceResult<Void> addUserNotice(List<UserNotice> userNoticeList) {
		ServiceResult<Void> returnValue=new ServiceResult<Void>();
		if(userNoticeList!=null && userNoticeList.size()>0) {
			for(UserNotice userNotice:userNoticeList) {
				userNoticeDao.insert(userNotice);
			}
			returnValue.setResultCode(ResultCodeEnums.SUCCESS);
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<NoticeComplex> getNotice(int noticeId) {
		ServiceResult<NoticeComplex> returnValue=new ServiceResult<>();
		if(noticeId>0) {
			NoticeComplex noticeComplex=noticeComplexDao.findById(noticeId);
			if(noticeComplex!=null) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(noticeComplex);
			}else {
				returnValue.setResultCode(ResultCodeEnums.NOTICE_NOT_EXIST);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<PagedResult<UserNotice>> getUserNoticeList(int noticeId,Integer pageNo,Integer pageSize) {
		ServiceResult<PagedResult<UserNotice>> returnValue=new ServiceResult<PagedResult<UserNotice>>();
		if(noticeId>0) {
			HashMap<String, Object> map=new HashMap<>();
			map.put("noticeId", noticeId);
			pageNo = pageNo == null? 1:pageNo;  
		    pageSize = pageSize == null? 10:pageSize;
		    PageHelper.startPage(pageNo,pageSize);
		    PagedResult<UserNotice> pageResult=BeanUtil.toPagedResult(userNoticeDao.findListByParam(map));
			if(pageResult!=null) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(pageResult);
			}else {
				returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public PagedResult<NoticeComplex> getNoticeList(Map<String, Object> map,Integer pageNo,Integer pageSize) throws ServiceException{
		if(map!=null) {
			pageNo = pageNo == null? 1:pageNo;  
		    pageSize = pageSize == null? 10:pageSize; 
		    PageHelper.startPage(pageNo,pageSize);
			PagedResult<NoticeComplex> pageResult=BeanUtil.toPagedResult(noticeComplexDao.findByParam(map));
			if(pageResult!=null) {
				return pageResult;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public ServiceResult<PagedResult<NoticeComplex>> getNoticeList_mobile(int userId,Integer pageNo,Integer pageSize) {
		ServiceResult<PagedResult<NoticeComplex>> returnValue=new ServiceResult<PagedResult<NoticeComplex>>();
		if(userId>0) {
			HashMap<String,Object> map=new HashMap<>();
			map.put("userId", userId);
			pageNo = pageNo == null? 1:pageNo;  
		    pageSize = pageSize == null? 10:pageSize; 
		    PageHelper.startPage(pageNo,pageSize);
		    PagedResult<NoticeComplex> pageResult=BeanUtil.toPagedResult(noticeComplexDao.findByParamMobile(map));
		    if(pageResult!=null) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(pageResult);
			}else {
				returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<NoticeComplex> getNotice_mobile(int noticeId,int userId) {
		ServiceResult<NoticeComplex> returnValue=new ServiceResult<NoticeComplex>();
		if(noticeId>0 && userId>0) {
			NoticeComplex noticeComplex=noticeComplexDao.findByIdMobile(noticeId);
			if(noticeComplex!=null) {
				if(!noticeComplex.isRead()) {
					//标记消息已读
					HashMap<String,Object> map=new HashMap<>();
					map.put("userId", userId);
					map.put("noticeId",noticeId);
					UserNotice userNotice=userNoticeDao.findByParam(map);
					if(userNotice!=null) {
						userNotice.setRead(true);
						userNoticeDao.update(userNotice);
					}
					noticeComplex.setRead(true);
				}
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(noticeComplex);
			}else {
				returnValue.setResultCode(ResultCodeEnums.NOTICE_NOT_EXIST);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}
}
