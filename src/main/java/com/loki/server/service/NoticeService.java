package com.loki.server.service;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.NoticeComplex;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.UserNotice;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.NoticeVO;
import com.loki.server.vo.ServiceResult;

public interface NoticeService {
	//admin
	boolean addNotice(NoticeVO noticeVO) throws ServiceException;
	ServiceResult<Void> addUserNotice(List<UserNotice> userNoticeList);
	NoticeComplex getNotice(int noticeId) throws ServiceException;
	PagedResult<UserNotice> getUserNoticeList(Map<String, Object> map) throws ServiceException;
	PagedResult<NoticeComplex> getNoticeList(Map<String, Object> map) throws ServiceException;
	boolean sendOmittedNotice(int noticeId) throws ServiceException;
	//mobile
	ServiceResult<PagedResult<NoticeComplex>> getNoticeList_mobile(int userId,Integer pageNo,Integer pageSize);
	ServiceResult<NoticeComplex> getNotice_mobile(int noticeId,int userId);
	ServiceResult<Integer> getUnreadCount_mobile(int userId);
	ServiceResult<Void> clearUnreadCount_mobile(int userId);
}
