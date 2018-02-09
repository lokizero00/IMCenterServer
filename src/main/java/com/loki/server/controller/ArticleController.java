package com.loki.server.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.dto.ArticleDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.ArticleService;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.ResultCodeEnums;

@Controller
@RequestMapping("/s/article")
public class ArticleController extends BaseController{
	@Autowired ArticleService articleService;
	
	/**
     * 显示首页
     * @return
     */
	@RequestMapping("/articleListPage")  
	public String articleListPage(){
		return "article/articleList";
	}
	
	/**
     * 分页查询
     * @return
     */
    @RequestMapping(value="/articleList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getArticleList(String createTimeStart_str,String createTimeEnd_str,String updateTimeStart_str,String updateTimeEnd_str,Integer adminCreatorId,Integer adminUpdaterId,String title,String content,String status,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
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
			map.put("status", status);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<ArticleDTO> list=articleService.getArticleList(map);
    	    return responseSuccess(list);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
    
    /**
     * 显示添加页面
     * @return
     */
	@RequestMapping("/articleAddPage")  
	public String articleAddPage(){
		return "article/articleAdd";
	}
	
	/**
     * 保存新建的文章
     * @return
     */
	@RequestMapping(value="/articleAdd.do",method=RequestMethod.POST)
	@ResponseBody
	public String articleAdd(HttpServletRequest request, ArticleDTO articleDTO) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			articleDTO.setAdminCreatorId(adminId);
			boolean result=articleService.addArticle(articleDTO);
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
     * 显示详情页
     * @return
     */
	@RequestMapping("/articleDetailPage")  
	public String articleDetailPage(int id){
		return "article/articleDetail.jsp?id="+id;
	}
	
	/**
     * 获取文章
     * @return
     */
	@RequestMapping(value="/articleDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String getArticle(HttpServletRequest request, int id) {
		try {
			ArticleDTO articleDTO=articleService.getArticle(id);
			return responseSuccess(articleDTO);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 显示编辑页
     * @return
     */
	@RequestMapping("/articleEditPage")  
	public String articleEditPage(int id){
		return "article/articleEdit.jsp?id="+id;
	}
	
	/**
     * 编辑文章
     * @return
     */
	@RequestMapping(value="/articleEdit.do",method=RequestMethod.POST)
	@ResponseBody
	public String articleEdit(HttpServletRequest request, ArticleDTO articleDTO) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			articleDTO.setAdminUpdaterId(adminId);
			boolean result=articleService.editArticle(articleDTO);
			if(result) {
				return responseSuccess();
			}else {
				return responseFail(ResultCodeEnums.UPDATE_FAIL.getMessage());
			}
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
}
