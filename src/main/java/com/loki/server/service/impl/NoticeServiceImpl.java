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
import com.loki.server.dao.UserDao;
import com.loki.server.dao.UserNoticeDao;
import com.loki.server.entity.Notice;
import com.loki.server.entity.NoticeComplex;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.UserNotice;
import com.loki.server.service.NoticeService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.NoticeVO;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class NoticeServiceImpl extends BaseService implements NoticeService {
	@Resource NoticeDao noticeDao;
	@Resource UserNoticeDao userNoticeDao;
	@Resource NoticeComplexDao noticeComplexDao;
	@Resource UserDao userDao;

	@Override
	public boolean addNotice(NoticeVO noticeVO) throws ServiceException{
		if(noticeVO!=null) {
			Notice notice=new Notice();
			notice.setAdminCreatorId(noticeVO.getAdminCreatorId());
			notice.setTitle(noticeVO.getTitle());
			notice.setContent(noticeVO.getContent());
			notice.setRelationType(noticeVO.getRelationType());
			notice.setRelationId(noticeVO.getRelationId());
			noticeDao.insert(notice);
			if(notice.getId()>0) {
				if(noticeVO.isSend()) {
					//创建userNotice记录
					List<Integer> userIdList=userDao.findIdList("us_on");
					for(int userId:userIdList) {
						UserNotice userNotice=new UserNotice();
						userNotice.setUserId(userId);
						userNotice.setNoticeId(notice.getId());
						userNotice.setRead(false);
						userNoticeDao.insert(userNotice);
					}
					//TODO 发送客户端notification
				}
				return true;
			}else {
				throw new ServiceException(ResultCodeEnums.SAVE_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
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
	public NoticeComplex getNotice(int noticeId) throws ServiceException{
		if(noticeId>0) {
			NoticeComplex noticeComplex=noticeComplexDao.findById(noticeId);
			if(noticeComplex!=null) {
				return noticeComplex;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public PagedResult<UserNotice> getUserNoticeList(Map<String, Object> map) throws ServiceException{
		if(map!=null) {
			int pageNo = map.get("pageNo") == null? 1:(int) map.get("pageNo");  
		    int pageSize = map.get("pageSize") == null? 10:(int) map.get("pageSize");
		    PageHelper.startPage(pageNo,pageSize);
		    PagedResult<UserNotice> pageResult=BeanUtil.toPagedResult(userNoticeDao.findListByParam(map));
			if(pageResult!=null) {
				for(int i=0;i<pageResult.getRows().size();i++) {
					pageResult.getRows().get(i).setUserNickName(getUserNickName(pageResult.getRows().get(i).getUserId()));
				}
				return pageResult;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public PagedResult<NoticeComplex> getNoticeList(Map<String, Object> map) throws ServiceException{
		if(map!=null) {
			int pageNo = map.get("pageNo") == null? 1:(int) map.get("pageNo");  
		    int pageSize = map.get("pageSize") == null? 10:(int) map.get("pageSize");
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

	@Override
	public boolean sendOmittedNotice(int noticeId) throws ServiceException {
		if(noticeId>0) {
			List<Integer> omittedUserIdList=userNoticeDao.findOmittedUserId(noticeId);
			for(int userId:omittedUserIdList) {
				UserNotice userNotice=new UserNotice();
				userNotice.setNoticeId(noticeId);
				userNotice.setUserId(userId);
				userNotice.setRead(false);
				userNoticeDao.insert(userNotice);
			}
			//TODO 发送客户端notification
			return true;
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public ServiceResult<Integer> getUnreadCount_mobile(int userId) {
		ServiceResult<Integer> returnValue=new ServiceResult<>();
		if(userId>0) {
			int unreadCount=userNoticeDao.findUnreadCount(userId);
			returnValue.setResultCode(ResultCodeEnums.SUCCESS);
			returnValue.setResultObj(unreadCount);
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> clearUnreadCount_mobile(int userId) {
		ServiceResult<Void> returnValue=new ServiceResult<>();
		if(userId>0) {
			if(userNoticeDao.clearUnreadCount(userId)) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
			}else {
				returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}
}
