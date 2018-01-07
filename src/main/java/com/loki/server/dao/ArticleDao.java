package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.Article;

public interface ArticleDao {
	void insert(Article article);
	boolean update(Article article);
	Article findById(int id);
	List<Article> findAll();
	boolean delete(int id);
}
