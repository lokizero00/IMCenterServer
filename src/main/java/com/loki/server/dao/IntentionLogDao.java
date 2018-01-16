package com.loki.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.IntentionLog;

public interface IntentionLogDao {
	void insert(IntentionLog intentionLog);
	boolean update(IntentionLog intentionLog);
	IntentionLog findById(int id);
	List<IntentionLog> findAll();
	List<IntentionLog> findByParam(@Param("userId") int userId,@Param("intentionId") int intentionId,@Param("adminId") int adminId,@Param("type") String type);
	boolean delete(int id);
}
