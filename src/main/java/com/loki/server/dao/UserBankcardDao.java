package com.loki.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.loki.server.entity.UserBankcard;

public interface UserBankcardDao {
	void insert(UserBankcard userBankcard);
	boolean update(UserBankcard userBankcard);
	UserBankcard findById(int id);
	List<UserBankcard> findAll();
	boolean delete(int id);
	List<UserBankcard> findByParam(@Param("userId") int userId,@Param("bankCode") String bankCode,@Param("bankName") String bankName,@Param("bankCardType") String bankCardType,@Param("bankCardNumber") String bankCardNumber);
}
