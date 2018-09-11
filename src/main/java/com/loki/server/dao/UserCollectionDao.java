package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.UserCollection;

public interface UserCollectionDao {
	void insert(UserCollection userCollection);
	boolean update(UserCollection userCollection);
	UserCollection findById(int id);
	List<UserCollection> findAll();
	boolean delete(int id);
	List<UserCollection> findByParam(Map<String,Object> map);
	int checkExist(@Param("tradeId") int tradeId,@Param("userId") int userId);
	boolean deleteByTradeId(int tradeId);
}
