package com.loki.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.loki.server.entity.PagedResult;
import com.loki.server.service.NoticeService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.NoticeVO;
import com.loki.server.vo.ServiceResult;

@Controller
@RequestMapping("/s/api/notice")
public class NoticeMobileController {
	@Autowired NoticeService noticeService;
	
	//获取消息列表
	@RequestMapping(value="/getNoticeList",method=RequestMethod.GET)
	public String getNoticeList(HttpServletRequest request,int userId,Integer pageNo, Integer pageSize,ModelMap mm) {
		ServiceResult<PagedResult<NoticeVO>> returnValue=noticeService.getNoticeVOList(userId, pageNo, pageSize);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
	
	//获取消息内容,获取后将标记为已读
	@RequestMapping(value="/getNotice",method=RequestMethod.GET)
	public String getNotice(HttpServletRequest request,int noticeId,int userId,ModelMap mm) {
		ServiceResult<NoticeVO> returnValue=noticeService.getNoticeVO(noticeId,userId);
		if (returnValue!=null) {
			mm.addAttribute("resultCode", returnValue.getResultCode().getCode());
			mm.addAttribute("msg", returnValue.getResultCode().getMessage());
			mm.addAttribute("resultObj", returnValue.getResultObj());
		}else {
			mm.addAttribute("resultCode", ResultCodeEnums.UNKNOW_ERROR.getCode());
			mm.addAttribute("msg", ResultCodeEnums.UNKNOW_ERROR.getMessage());
		}
		return "mobileResultJson";
	}
}
