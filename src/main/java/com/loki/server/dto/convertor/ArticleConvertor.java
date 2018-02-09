package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.ArticleDTO;
import com.loki.server.entity.Article;

public class ArticleConvertor {
	public static ArticleDTO convertArticle2ArticleDTO(Article article) {
		if(article==null) {
			return null;
		}
		ArticleDTO articleDTO=new ArticleDTO();
		articleDTO.setId(article.getId());
		articleDTO.setCreateTime(article.getCreateTime());
		articleDTO.setUpdateTime(article.getUpdateTime());
		articleDTO.setAdminCreatorId(article.getAdminCreatorId());
		articleDTO.setAdminUpdaterId(article.getAdminUpdaterId());
		articleDTO.setTitle(article.getTitle());
		articleDTO.setContent(article.getContent());
		articleDTO.setSort(article.getSort());
		articleDTO.setStatus(article.getStatus());
		articleDTO.setReadCount(article.getReadCount());
		return articleDTO;
	}
	
	public static List<ArticleDTO> convertArticles2ArticleDTOs(List<Article> articles){
		List<ArticleDTO> articleDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(articles)) {
			for(Article article:articles) {
				ArticleDTO articleDTO=new ArticleDTO();
				articleDTO.setId(article.getId());
				articleDTO.setCreateTime(article.getCreateTime());
				articleDTO.setUpdateTime(article.getUpdateTime());
				articleDTO.setAdminCreatorId(article.getAdminCreatorId());
				articleDTO.setAdminUpdaterId(article.getAdminUpdaterId());
				articleDTO.setTitle(article.getTitle());
				articleDTO.setContent(article.getContent());
				articleDTO.setSort(article.getSort());
				articleDTO.setStatus(article.getStatus());
				articleDTO.setReadCount(article.getReadCount());
				articleDTOs.add(articleDTO);
			}
		}
		return articleDTOs;
	}
}
