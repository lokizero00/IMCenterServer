package com.loki.server.service;

import java.util.Map;

import com.loki.server.dto.ArticleDTO;
import com.loki.server.entity.PagedResult;
import com.loki.server.utils.ServiceException;

public interface ArticleService {
	boolean addArticle(ArticleDTO articleDTO) throws ServiceException;
	PagedResult<ArticleDTO> getArticleList(Map<String,Object> map) throws ServiceException;
	ArticleDTO getArticle(int articleId) throws ServiceException;
	boolean editArticle(ArticleDTO articleDTO) throws ServiceException;
}
