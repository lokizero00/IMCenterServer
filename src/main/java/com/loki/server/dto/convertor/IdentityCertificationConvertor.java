package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.IdentityCertificationDTO;
import com.loki.server.entity.IdentityCertification;


public class IdentityCertificationConvertor {
	public static IdentityCertificationDTO convertIdentityCertification2IdentityCertificationDTO(IdentityCertification identityCertification) {
		if(identityCertification==null) {
			return null;
		}
		IdentityCertificationDTO identityCertificationDTO=new IdentityCertificationDTO();
		identityCertificationDTO.setId(identityCertification.getId());
		identityCertificationDTO.setCreateTime(identityCertification.getCreateTime());
		identityCertificationDTO.setUpdateTime(identityCertification.getUpdateTime());
		identityCertificationDTO.setUserId(identityCertification.getUserId());
		identityCertificationDTO.setTrueName(identityCertification.getTrueName());
		identityCertificationDTO.setIdentityNumber(identityCertification.getIdentityNumber());
		identityCertificationDTO.setIdentityFront(identityCertification.getIdentityFront());
		identityCertificationDTO.setIdentityBack(identityCertification.getIdentityBack());
		identityCertificationDTO.setVerifyTime(identityCertification.getVerifyTime());
		identityCertificationDTO.setAdminVerifierId(identityCertification.getAdminVerifierId());
		identityCertificationDTO.setStatus(identityCertification.getStatus());
		identityCertificationDTO.setRefuseReason(identityCertification.getRefuseReason());
		return identityCertificationDTO;
	}
	
	public static List<IdentityCertificationDTO> convertIdentityCertifications2IdentityCertificationDTOs(List<IdentityCertification> identityCertifications){
		List<IdentityCertificationDTO> identityCertificationDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(identityCertifications)) {
			for(IdentityCertification identityCertification:identityCertifications) {
				IdentityCertificationDTO identityCertificationDTO=new IdentityCertificationDTO();
				identityCertificationDTO.setId(identityCertification.getId());
				identityCertificationDTO.setCreateTime(identityCertification.getCreateTime());
				identityCertificationDTO.setUpdateTime(identityCertification.getUpdateTime());
				identityCertificationDTO.setUserId(identityCertification.getUserId());
				identityCertificationDTO.setTrueName(identityCertification.getTrueName());
				identityCertificationDTO.setIdentityNumber(identityCertification.getIdentityNumber());
				identityCertificationDTO.setIdentityFront(identityCertification.getIdentityFront());
				identityCertificationDTO.setIdentityBack(identityCertification.getIdentityBack());
				identityCertificationDTO.setVerifyTime(identityCertification.getVerifyTime());
				identityCertificationDTO.setAdminVerifierId(identityCertification.getAdminVerifierId());
				identityCertificationDTO.setStatus(identityCertification.getStatus());
				identityCertificationDTO.setRefuseReason(identityCertification.getRefuseReason());
				identityCertificationDTOs.add(identityCertificationDTO);
			}
		}
		return identityCertificationDTOs;
	}
}
