package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.UserDTO;
import com.loki.server.entity.User;

public class UserConvertor {
	public static UserDTO convertUser2UserDTO(User user) {
		if(user==null) {
			return null;
		}
		UserDTO userDTO=new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUserName(user.getUserName());
		userDTO.setPassword(user.getPassword());
		userDTO.setNickName(user.getNickName());
		userDTO.setPhone(user.getPhone());
		userDTO.setPhoneBind(user.isPhoneBind());
		userDTO.setEmail(user.getEmail());
		userDTO.setEmailBind(user.isEmailBind());
		userDTO.setAvatar(user.getAvatar());
		userDTO.setPayPwd(user.getPayPwd());
		userDTO.setRegistTime(user.getRegistTime());
		userDTO.setRegistIp(user.getRegistIp());
		userDTO.setIdentityId(user.getIdentityId());
		userDTO.setEnterpriseId(user.getEnterpriseId());
		userDTO.setEaseId(user.getEaseId());
		userDTO.setEasePwd(user.getEasePwd());
		userDTO.setStatus(user.getStatus());
		return userDTO;
	}
	
	public static List<UserDTO> convertUsers2UserDTOs(List<User> users){
		List<UserDTO> userDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(users)) {
			for(User user:users) {
				UserDTO userDTO=new UserDTO();
				userDTO.setId(user.getId());
				userDTO.setUserName(user.getUserName());
				userDTO.setPassword(user.getPassword());
				userDTO.setNickName(user.getNickName());
				userDTO.setPhone(user.getPhone());
				userDTO.setPhoneBind(user.isPhoneBind());
				userDTO.setEmail(user.getEmail());
				userDTO.setEmailBind(user.isEmailBind());
				userDTO.setAvatar(user.getAvatar());
				userDTO.setPayPwd(user.getPayPwd());
				userDTO.setRegistTime(user.getRegistTime());
				userDTO.setRegistIp(user.getRegistIp());
				userDTO.setIdentityId(user.getIdentityId());
				userDTO.setEnterpriseId(user.getEnterpriseId());
				userDTO.setEaseId(user.getEaseId());
				userDTO.setEasePwd(user.getEasePwd());
				userDTO.setStatus(user.getStatus());
				userDTOs.add(userDTO);
			}
		}
		return userDTOs;
	}
}
