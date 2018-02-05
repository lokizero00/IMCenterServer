package com.loki.server.controller;

import java.sql.Timestamp;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.entity.NoticeComplex;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.NoticeService;

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
    @RequestMapping(value="/list.do", method= RequestMethod.GET)
    @ResponseBody
    public String list(Timestamp createTimeStart,Timestamp createTimeEnd,Timestamp updateTimeStart,Timestamp updateTimeEnd,Integer adminCreatorId,Integer adminUpdaterId,String title,String content,String relationType,Integer relationId,Integer pageNo,Integer pageSize ) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("createTimeStart", createTimeStart);
			map.put("createTimeEnd", createTimeEnd);
			map.put("updateTimeStart", updateTimeStart);
			map.put("updateTimeEnd", updateTimeEnd);
			map.put("adminCreatorId", adminCreatorId);
			map.put("adminUpdaterId", adminUpdaterId);
			map.put("title", title);
			map.put("content", content);
			map.put("relationType", relationType);
			map.put("relationId", relationId);
			PagedResult<NoticeComplex> pageResult=noticeService.getNoticeList(map, pageNo, pageSize);
    	    return responseSuccess(pageResult);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
}
