package com.loki.server.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.loki.server.dao.IdentityCertificationDao;
import com.loki.server.dao.UserDao;
import com.loki.server.dto.IdentityCertificationDTO;
import com.loki.server.dto.convertor.IdentityCertificationConvertor;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.User;
import com.loki.server.service.IdentityCertificationService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.utils.SessionContext;
import com.loki.server.vo.IdentityCertificationVO;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class IdentityCertificationServiceImpl extends BaseService implements IdentityCertificationService {
	@Resource
	IdentityCertificationDao identityCertificationDao;
	@Resource
	UserDao userDao;

	@Override
	public ServiceResult<IdentityCertification> getIdentityCertification_mobile(int userId) {
		ServiceResult<IdentityCertification> returnValue = new ServiceResult<IdentityCertification>();
		if (userId > 0) {
			User user = userDao.findById(userId);
			if (null == user) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			} else {
				IdentityCertification identityCertification = identityCertificationDao.findById(user.getIdentityId());
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(identityCertification);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<String> getIdentityCertificationStatus_mobile(int userId) {
		ServiceResult<String> returnValue = new ServiceResult<String>();
		if (userId > 0) {
			User user = userDao.findById(userId);
			if (user == null) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			} else {
				IdentityCertification identityCertification = identityCertificationDao.findById(user.getIdentityId());
				if (null == identityCertification) {
					returnValue.setResultCode(ResultCodeEnums.IDENTITY_CERTIFICATION_NOT_EXIST);
				} else {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(identityCertification.getStatus());
				}
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Integer> addIdentityCertification_mobile(IdentityCertificationVO identityCertificationVO) {
		ServiceResult<Integer> returnValue = new ServiceResult<>();
		if (identityCertificationVO != null && identityCertificationVO.getUserId() > 0) {
			IdentityCertification identityCertification = new IdentityCertification();
			identityCertification.setUserId(identityCertificationVO.getUserId());
			identityCertification.setTrueName(identityCertificationVO.getTrueName());
			identityCertification.setIdentityNumber(identityCertificationVO.getIdentityNumber());
			identityCertification.setIdentityFront(identityCertificationVO.getIdentityFront());
			identityCertification.setIdentityBack(identityCertificationVO.getIdentityBack());
			identityCertification.setStatus("ic_verify");
			identityCertificationDao.insert(identityCertification);
			if (identityCertification.getId() > 0) {
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(identityCertification.getId());
			} else {
				returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> editIdentityCertification_mobile(IdentityCertificationVO identityCertificationVO) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		if (identityCertificationVO != null && identityCertificationVO.getId() > 0
				&& identityCertificationVO.getUserId() > 0) {
			IdentityCertification identityCertification = identityCertificationDao
					.findByIdAndUserId(identityCertificationVO.getId(), identityCertificationVO.getUserId());
			if (identityCertification != null) {
				if (identityCertification.getStatus().equals("ic_refuse")) {
					identityCertification.setTrueName(identityCertificationVO.getTrueName());
					identityCertification.setIdentityNumber(identityCertificationVO.getIdentityNumber());
					identityCertification.setIdentityFront(identityCertificationVO.getIdentityFront());
					identityCertification.setIdentityBack(identityCertificationVO.getIdentityBack());
					identityCertification.setVerifyTime(null);
					identityCertification.setAdminVerifierId(0);
					identityCertification.setStatus("ic_verify");
					identityCertification.setRefuseReason("");
					if (identityCertificationDao.update(identityCertification)) {
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					} else {
						returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
					}
				} else {
					returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public PagedResult<IdentityCertificationDTO> getIdentityCertificationList(Map<String, Object> map)
			throws ServiceException {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<IdentityCertification> identityCertificationList = identityCertificationDao.findByParam(map);
			List<IdentityCertificationDTO> identityCertificationDTOList = new ArrayList<>();
			for (IdentityCertification identityCertification : identityCertificationList) {
				IdentityCertificationDTO identityCertificationDTO = IdentityCertificationConvertor
						.convertIdentityCertification2IdentityCertificationDTO(identityCertification);
				identityCertificationDTO.setUserNickName(getUserNickName(identityCertificationDTO.getUserId()));
				identityCertificationDTO
						.setAdminVerifierName(getAdminName(identityCertificationDTO.getAdminVerifierId()));
				identityCertificationDTO.setStatusName(
						getDictionariesValue("identity_certification_status", identityCertificationDTO.getStatus()));
				identityCertificationDTOList.add(identityCertificationDTO);
			}
			PagedResult<IdentityCertificationDTO> pageResult = BeanUtil.toPagedResult(identityCertificationDTOList);
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
	public IdentityCertificationDTO getIdentityCertification(HttpServletRequest request, int id)
			throws ServiceException {
		if (id > 0) {
			IdentityCertification identityCertification = identityCertificationDao.findById(id);
			if (identityCertification != null) {
				IdentityCertificationDTO identityCertificationDTO = IdentityCertificationConvertor
						.convertIdentityCertification2IdentityCertificationDTO(identityCertification);
				if (identityCertificationDTO != null) {
					identityCertificationDTO.setUserNickName(getUserNickName(identityCertificationDTO.getUserId()));
					identityCertificationDTO
							.setAdminVerifierName(getAdminName(identityCertificationDTO.getAdminVerifierId()));
					identityCertificationDTO.setStatusName(getDictionariesValue("identity_certification_status",
							identityCertificationDTO.getStatus()));
					if (identityCertificationDTO.getIdentityFront() != null
							&& !(identityCertificationDTO.getIdentityFront().equals(""))) {
						String requestPath = getImageRequestPath(request);
						String identityFrontUrl = requestPath + "?name=" + identityCertificationDTO.getIdentityFront();
						identityCertificationDTO.setIdentityFrontUrl(identityFrontUrl);
					}
					if (identityCertificationDTO.getIdentityBack() != null
							&& !(identityCertificationDTO.getIdentityBack().equals(""))) {
						String requestPath = getImageRequestPath(request);
						String identityBackUrl = requestPath + "?name=" + identityCertificationDTO.getIdentityBack();
						identityCertificationDTO.setIdentityBackUrl(identityBackUrl);
					}
					return identityCertificationDTO;
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
	public boolean verifyIdentityCertification(HttpServletRequest request, int id, String verify,
			String refuseReason) throws ServiceException {
		if (id > 0 && verify != null && !(verify.equals(""))) {
			IdentityCertification identityCertification = identityCertificationDao.findById(id);
			if (identityCertification != null) {
				if(identityCertification.getStatus().equals("ic_verify")) {
					int adminId = (int) SessionContext.getInstance().getSessionAttribute("adminId");
					identityCertification.setAdminVerifierId(adminId);
					identityCertification.setVerifyTime(new Timestamp(System.currentTimeMillis()));
					if (verify.equals("verify_pass")) {
						identityCertification.setStatus("ic_pass");
					} else {
						identityCertification.setStatus("ic_refuse");
						identityCertification.setRefuseReason(refuseReason);
					}
					if (identityCertificationDao.update(identityCertification)) {
						return true;
					} else {
						throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
					}
				}else {
					throw new ServiceException(ResultCodeEnums.DATA_INVALID);
				}
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

}
