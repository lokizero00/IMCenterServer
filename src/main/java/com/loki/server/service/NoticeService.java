package com.loki.server.service;

import java.util.List;
import java.util.Map;

import com.loki.server.entity.Notice;
import com.loki.server.entity.PagedResult;
import com.loki.server.vo.NoticeVO;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.UserNoticeVO;

public interface NoticeService {
	//admin
	ServiceResult<Void> addNotice(Notice notice);
	ServiceResult<Void> addUserNotice(List<UserNoticeVO> userNoticeVOList);
	ServiceResult<NoticeVO> getNotice(int noticeId);
	ServiceResult<PagedResult<UserNoticeVO>> getUserNoticeList(int noticeId,Integer pageNo,Integer pageSize);
	ServiceResult<PagedResult<NoticeVO>> getNoticeList(Map<String, Object> map,Integer pageNo,Integer pageSize) ;
	//mobile
	ServiceResult<PagedResult<NoticeVO>> getNoticeVOList(int userId,Integer pageNo,Integer pageSize);
	ServiceResult<NoticeVO> getNoticeVO(int noticeId,int userId);
}
