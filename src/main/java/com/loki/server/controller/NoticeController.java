package com.loki.server.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.entity.NoticeComplex;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.UserNotice;
import com.loki.server.service.NoticeService;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.NoticeVO;

@Controller
@RequestMapping("/s/notice")
public class NoticeController extends BaseController{
	@Autowired NoticeService noticeService;
	
	/**
     * 显示首页
     * @return
     */
	@RequestMapping("/noticeList")  
	public String noticeList(){
		return "notice/noticeList";
	}
	
	/**
     * 分页查询
     * @return
     */
    @RequestMapping(value="/noticeList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getNoticeList(String createTimeStart_str,String createTimeEnd_str,String updateTimeStart_str,String updateTimeEnd_str,Integer adminCreatorId,Integer adminUpdaterId,String title,String content,String relationType,Integer relationId,Integer pageNo,Integer pageSize,String sortName,String sortOrder ) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("createTimeStart", CommonUtil.getInstance().getDate(createTimeStart_str, "start"));
			map.put("createTimeEnd", CommonUtil.getInstance().getDate(createTimeEnd_str, "end"));
			map.put("updateTimeStart", null);
			map.put("updateTimeEnd", null);
			map.put("adminCreatorId", adminCreatorId);
			map.put("adminUpdaterId", adminUpdaterId);
			map.put("title", title);
			map.put("content", content);
			map.put("relationType", relationType);
			map.put("relationId", relationId);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<NoticeComplex> pageResult=noticeService.getNoticeList(map);
    	    return responseSuccess(pageResult);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
    
    /**
     * 显示详情页
     * @return
     */
	@RequestMapping("/noticeDetail")  
	public String noticeDetail(int id){
		return "notice/noticeDetail.jsp?id="+id;
	}
	
	/**
     * 获取通知
     * @return
     */
	@RequestMapping(value="/noticeDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String getNotice(HttpServletRequest request, int id) {
		try {
			NoticeComplex noticeComplex=noticeService.getNotice(id);
			return responseSuccess(noticeComplex);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 获取通知用户列表
     * @return
     */
	@RequestMapping(value="/userNotice.do",method=RequestMethod.GET)
	@ResponseBody
	public String getUserNoticeList(HttpServletRequest request, int noticeId,Integer pageNo,Integer pageSize,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("noticeId", noticeId);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<UserNotice> pageResult=noticeService.getUserNoticeList(map);
			return responseSuccess(pageResult);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 显示添加页面
     * @return
     */
	@RequestMapping("/addNoticePage")  
	public String addNoticePage(){
		return "notice/addNotice";
	}
	
	/**
     * 保存新建的通知
     * @return
     */
	@RequestMapping(value="/addNotice.do",method=RequestMethod.POST)
	@ResponseBody
	public String addNotice(HttpServletRequest request, NoticeVO noticeVO) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			noticeVO.setAdminCreatorId(adminId);
			boolean result=noticeService.addNotice(noticeVO);
			if(result) {
				return responseSuccess();
			}else {
				return responseFail(ResultCodeEnums.SAVE_FAIL.getMessage());
			}
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 遗漏补发
     * @return
     */
	@RequestMapping(value="/sendOmittedNotice.do",method=RequestMethod.POST)
	@ResponseBody
	public String sendOmittedNotice(HttpServletRequest request, int noticeId) {
		try {
			boolean result=noticeService.sendOmittedNotice(noticeId);
			if(result) {
				return responseSuccess();
			}else {
				return responseFail(ResultCodeEnums.SAVE_FAIL.getMessage());
			}
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
}
