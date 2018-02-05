package com.loki.server.service;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.Notice;
import com.loki.server.entity.NoticeComplex;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.UserNotice;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;

public interface NoticeService {
	//admin
	ServiceResult<Void> addNotice(Notice notice);
	ServiceResult<Void> addUserNotice(List<UserNotice> userNoticeList);
	NoticeComplex getNotice(int noticeId) throws ServiceException;
	ServiceResult<PagedResult<UserNotice>> getUserNoticeList(int noticeId,Integer pageNo,Integer pageSize);
	PagedResult<NoticeComplex> getNoticeList(Map<String, Object> map) throws ServiceException;
	//mobile
	ServiceResult<PagedResult<NoticeComplex>> getNoticeList_mobile(int userId,Integer pageNo,Integer pageSize);
	ServiceResult<NoticeComplex> getNotice_mobile(int noticeId,int userId);
}
