package com.loki.server.dao;

import java.util.List;

import com.loki.server.model.UserCollection;

public interface UserCollectionDao {
	void insert(UserCollection userCollection);
	boolean update(UserCollection userCollection);
	UserCollection findById(int id);
	List<UserCollection> findAll();
	boolean delete(int id);
}
