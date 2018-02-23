package com.loki.server.dto.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.loki.server.dto.EnterpriseCertificationDTO;
import com.loki.server.entity.EnterpriseCertification;

public class EnterpriseCertificationConvertor {
	public static EnterpriseCertificationDTO convertEnterpriseCertification2EnterpriseCertificationDTO(EnterpriseCertification enterpriseCertification) {
		if(enterpriseCertification==null) {
			return null;
		}
		EnterpriseCertificationDTO enterpriseCertificationDTO=new EnterpriseCertificationDTO();
		enterpriseCertificationDTO.setId(enterpriseCertification.getId());
		enterpriseCertificationDTO.setCreateTime(enterpriseCertification.getCreateTime());
		enterpriseCertificationDTO.setUpdateTime(enterpriseCertification.getUpdateTime());
		enterpriseCertificationDTO.setUserId(enterpriseCertification.getUserId());
		enterpriseCertificationDTO.setPosition(enterpriseCertification.getPosition());
		enterpriseCertificationDTO.setEnterpriseName(enterpriseCertification.getEnterpriseName());
		enterpriseCertificationDTO.setLicensePic(enterpriseCertification.getLicensePic());
		enterpriseCertificationDTO.setVerifyTime(enterpriseCertification.getVerifyTime());
		enterpriseCertificationDTO.setAdminVerifierId(enterpriseCertification.getAdminVerifierId());
		enterpriseCertificationDTO.setStatus(enterpriseCertification.getStatus());
		enterpriseCertificationDTO.setRefuseReason(enterpriseCertification.getRefuseReason());
		enterpriseCertificationDTO.setDeleted(enterpriseCertification.isDeleted());
		return enterpriseCertificationDTO;
	}
	
	public static List<EnterpriseCertificationDTO> convertEnterpriseCertifications2EnterpriseCertificationDTOs(List<EnterpriseCertification> enterpriseCertifications){
		List<EnterpriseCertificationDTO> enterpriseCertificationDTOs=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(enterpriseCertifications)) {
			for(EnterpriseCertification enterpriseCertification:enterpriseCertifications) {
				EnterpriseCertificationDTO enterpriseCertificationDTO=new EnterpriseCertificationDTO();
				enterpriseCertificationDTO.setId(enterpriseCertification.getId());
				enterpriseCertificationDTO.setCreateTime(enterpriseCertification.getCreateTime());
				enterpriseCertificationDTO.setUpdateTime(enterpriseCertification.getUpdateTime());
				enterpriseCertificationDTO.setUserId(enterpriseCertification.getUserId());
				enterpriseCertificationDTO.setPosition(enterpriseCertification.getPosition());
				enterpriseCertificationDTO.setEnterpriseName(enterpriseCertification.getEnterpriseName());
				enterpriseCertificationDTO.setLicensePic(enterpriseCertification.getLicensePic());
				enterpriseCertificationDTO.setVerifyTime(enterpriseCertification.getVerifyTime());
				enterpriseCertificationDTO.setAdminVerifierId(enterpriseCertification.getAdminVerifierId());
				enterpriseCertificationDTO.setStatus(enterpriseCertification.getStatus());
				enterpriseCertificationDTO.setRefuseReason(enterpriseCertification.getRefuseReason());
				enterpriseCertificationDTO.setDeleted(enterpriseCertification.isDeleted());
				enterpriseCertificationDTOs.add(enterpriseCertificationDTO);
			}
		}
		return enterpriseCertificationDTOs;
	}
}
