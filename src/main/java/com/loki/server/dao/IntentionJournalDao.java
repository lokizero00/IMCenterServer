package com.loki.server.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.IntentionJournal;

public interface IntentionJournalDao {
	void insert(IntentionJournal intentionJournal);
	boolean update(IntentionJournal intentionJournal);
	boolean delete(int id);
	List<IntentionJournal> findAll();
	IntentionJournal findById(int id);
	IntentionJournal findByTradeNo(@Param("innerBusiNo")String innerBusiNo,@Param("typeList")List<String> typeList,@Param("stateList")List<String> stateList);
	List<IntentionJournal> findByParam(Map<String,Object> map);
}
