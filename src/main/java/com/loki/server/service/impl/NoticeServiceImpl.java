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
import com.loki.server.utils.BeanMapper;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.NoticeVO;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.UserNoticeVO;

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
	public ServiceResult<Void> addUserNotice(List<UserNoticeVO> userNoticeVOList) {
		ServiceResult<Void> returnValue=new ServiceResult<Void>();
		if(userNoticeVOList!=null && userNoticeVOList.size()>0) {
			for(UserNoticeVO userNoticeVO:userNoticeVOList) {
				UserNotice userNotice=BeanMapper.map(userNoticeVO, UserNotice.class);
				userNoticeDao.insert(userNotice);
			}
			returnValue.setResultCode(ResultCodeEnums.SUCCESS);
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<NoticeVO> getNotice(int noticeId) {
		ServiceResult<NoticeVO> returnValue=new ServiceResult<NoticeVO>();
		if(noticeId>0) {
			Notice notice=noticeDao.findById(noticeId);
			if(notice!=null) {
				NoticeVO noticeVO=BeanMapper.map(notice, NoticeVO.class);
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(noticeVO);
			}else {
				returnValue.setResultCode(ResultCodeEnums.NOTICE_NOT_EXIST);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<PagedResult<UserNoticeVO>> getUserNoticeList(int noticeId,Integer pageNo,Integer pageSize) {
		ServiceResult<PagedResult<UserNoticeVO>> returnValue=new ServiceResult<PagedResult<UserNoticeVO>>();
		if(noticeId>0) {
			HashMap<String, Object> map=new HashMap<>();
			map.put("noticeId", noticeId);
			pageNo = pageNo == null? 1:pageNo;  
		    pageSize = pageSize == null? 10:pageSize;
		    PageHelper.startPage(pageNo,pageSize);
		    PagedResult<UserNoticeVO> pageResult=BeanUtil.toPagedResult(BeanMapper.mapList(userNoticeDao.findListByParam(map), UserNoticeVO.class));
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
	public ServiceResult<PagedResult<NoticeVO>> getNoticeList(Map<String, Object> map,Integer pageNo,Integer pageSize) {
		ServiceResult<PagedResult<NoticeVO>> returnValue=new ServiceResult<PagedResult<NoticeVO>>();
		if(map!=null) {
			pageNo = pageNo == null? 1:pageNo;  
		    pageSize = pageSize == null? 10:pageSize; 
		    PageHelper.startPage(pageNo,pageSize);
			PagedResult<NoticeVO> pageResult=BeanUtil.toPagedResult(BeanMapper.mapList(noticeDao.findByParam(map), NoticeVO.class));
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
	public ServiceResult<PagedResult<NoticeVO>> getNoticeVOList(int userId,Integer pageNo,Integer pageSize) {
		ServiceResult<PagedResult<NoticeVO>> returnValue=new ServiceResult<PagedResult<NoticeVO>>();
		if(userId>0) {
			HashMap<String,Object> map=new HashMap<>();
			map.put("userId", userId);
			pageNo = pageNo == null? 1:pageNo;  
		    pageSize = pageSize == null? 10:pageSize; 
		    PageHelper.startPage(pageNo,pageSize);
		    PagedResult<NoticeVO> pageResult=BeanUtil.toPagedResult(BeanMapper.mapList(noticeComplexDao.findByParam(map), NoticeVO.class));
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
	public ServiceResult<NoticeVO> getNoticeVO(int noticeId,int userId) {
		ServiceResult<NoticeVO> returnValue=new ServiceResult<NoticeVO>();
		if(noticeId>0 && userId>0) {
			NoticeComplex noticeComplex=noticeComplexDao.findById(noticeId);
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
				NoticeVO noticeVO=BeanMapper.map(noticeComplex, NoticeVO.class);
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(noticeVO);
			}else {
				returnValue.setResultCode(ResultCodeEnums.NOTICE_NOT_EXIST);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}
}
