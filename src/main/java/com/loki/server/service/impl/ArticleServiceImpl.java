package com.loki.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.loki.server.dao.ArticleDao;
import com.loki.server.dto.ArticleDTO;
import com.loki.server.dto.convertor.ArticleConvertor;
import com.loki.server.entity.Article;
import com.loki.server.entity.PagedResult;
import com.loki.server.service.ArticleService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;

@Service
@Transactional
public class ArticleServiceImpl extends BaseServiceImpl implements ArticleService {
	@Resource
	ArticleDao articleDao;

	@Override
	public boolean addArticle(ArticleDTO articleDTO) throws ServiceException {
		if (articleDTO != null) {
			Article article = new Article();
			article.setAdminCreatorId(articleDTO.getAdminCreatorId());
			article.setTitle(articleDTO.getTitle());
			article.setContent(articleDTO.getContent());
			article.setSort(articleDTO.getSort());
			article.setStatus(articleDTO.getStatus());
			article.setReadCount(0);
			articleDao.insert(article);
			if (article.getId() > 0) {
				return true;
			} else {
				throw new ServiceException(ResultCodeEnums.SAVE_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public PagedResult<ArticleDTO> getArticleList(Map<String, Object> map) throws ServiceException {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<Article> articleList = articleDao.findByParam(map);
			List<ArticleDTO> articleDTOList = new ArrayList<>();
			for (Article article : articleList) {
				ArticleDTO articleDTO = ArticleConvertor.convertArticle2ArticleDTO(article);
				articleDTO.setAdminCreatorName(getAdminName(articleDTO.getAdminCreatorId()));
				articleDTO.setAdminUpdaterName(getAdminName(articleDTO.getAdminUpdaterId()));
				articleDTO.setStatusName(getDictionariesValue("article_status", articleDTO.getStatus()));
				articleDTOList.add(articleDTO);
			}
			PagedResult<ArticleDTO> pageResult = BeanUtil.toPagedResult(articleDTOList);
			if (pageResult != null) {
				return pageResult;
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public ArticleDTO getArticle(int articleId) throws ServiceException {
		if (articleId > 0) {
			Article article = articleDao.findById(articleId);
			if (article != null) {
				ArticleDTO articleDTO = ArticleConvertor.convertArticle2ArticleDTO(article);
				if (articleDTO != null) {
					articleDTO.setAdminCreatorName(getAdminName(articleDTO.getAdminCreatorId()));
					articleDTO.setAdminUpdaterName(getAdminName(articleDTO.getAdminUpdaterId()));
					articleDTO.setStatusName(getDictionariesValue("article_status", articleDTO.getStatus()));
					return articleDTO;
				} else {
					throw new ServiceException(ResultCodeEnums.DATA_CONVERT_FAIL);
				}
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public boolean editArticle(ArticleDTO articleDTO) throws ServiceException {
		if (articleDTO != null && articleDTO.getId()>0) {
			Article article = articleDao.findById(articleDTO.getId());
			article.setAdminUpdaterId(articleDTO.getAdminUpdaterId());
			article.setTitle(articleDTO.getTitle());
			article.setContent(articleDTO.getContent());
			article.setSort(articleDTO.getSort());
			article.setStatus(articleDTO.getStatus());
			if(articleDao.update(article)) {
				return true;
			}else {
				throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

}
