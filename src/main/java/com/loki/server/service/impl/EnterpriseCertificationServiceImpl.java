package com.loki.server.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.loki.server.dao.EnterpriseCertificationDao;
import com.loki.server.dao.UserDao;
import com.loki.server.dto.ArticleDTO;
import com.loki.server.dto.EnterpriseCertificationDTO;
import com.loki.server.dto.convertor.EnterpriseCertificationConvertor;
import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.User;
import com.loki.server.service.EnterpriseCertificationService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.utils.SessionContext;
import com.loki.server.vo.EnterpriseCertificationVO;
import com.loki.server.vo.ServiceResult;

@Service
@Transactional
public class EnterpriseCertificationServiceImpl extends BaseService implements EnterpriseCertificationService {
	@Resource
	EnterpriseCertificationDao enterpriseCertificationDao;
	@Resource
	UserDao userDao;

	@Override
	public ServiceResult<EnterpriseCertification> getEnterpriseCertification_mobile(int userId) {
		ServiceResult<EnterpriseCertification> returnValue = new ServiceResult<EnterpriseCertification>();
		if (userId > 0) {
			User user = userDao.findById(userId);
			if (null == user) {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			} else {
				EnterpriseCertification enterpriseCertification = enterpriseCertificationDao
						.findById(user.getEnterpriseId());
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(enterpriseCertification);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Integer> addEnterpriseCertification_mobile(
			EnterpriseCertificationVO enterpriseCertificationVO) {
		ServiceResult<Integer> returnValue = new ServiceResult<>();
		if (enterpriseCertificationVO != null && enterpriseCertificationVO.getUserId() > 0) {
			User user = userDao.findById(enterpriseCertificationVO.getUserId());
			if (user != null) {
				EnterpriseCertification lastCertification = enterpriseCertificationDao
						.findCurrentByUserId(enterpriseCertificationVO.getUserId());
				if (lastCertification == null) {
					EnterpriseCertification newCertification = new EnterpriseCertification();
					newCertification.setUserId(enterpriseCertificationVO.getUserId());
					newCertification.setPosition(enterpriseCertificationVO.getPosition());
					newCertification.setEnterpriseName(enterpriseCertificationVO.getEnterpriseName());
					newCertification.setLicensePic(enterpriseCertificationVO.getLicensePic());
					newCertification.setStatus("ec_verify");
					enterpriseCertificationDao.insert(newCertification);
					if (newCertification.getId() > 0) {
						// 关联到用户表
						user.setEnterpriseId(newCertification.getId());
						userDao.update(user);

						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						returnValue.setResultObj(newCertification.getId());
					} else {
						returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
					}
				} else {
					returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> editEnterpriseCertification_mobile(EnterpriseCertificationVO enterpriseCertificationVO) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		if (enterpriseCertificationVO != null && enterpriseCertificationVO.getId() > 0
				&& enterpriseCertificationVO.getUserId() > 0) {
			User user = userDao.findById(enterpriseCertificationVO.getUserId());
			if (user != null) {
				EnterpriseCertification enterpriseCertification = enterpriseCertificationDao
						.findCurrentByUserId(enterpriseCertificationVO.getUserId());
				if (enterpriseCertification != null
						&& enterpriseCertificationVO.getId() == enterpriseCertification.getId()) {
					if (enterpriseCertification.getStatus().equals("ec_pass")) {
						if (!enterpriseCertificationDao.nullifyByUserId(enterpriseCertificationVO.getUserId())) {
							returnValue.setResultCode(ResultCodeEnums.DELETE_FAIL);
							return returnValue;
						}
						EnterpriseCertification newCertification = new EnterpriseCertification();
						newCertification.setUserId(enterpriseCertificationVO.getUserId());
						newCertification.setPosition(enterpriseCertificationVO.getPosition());
						newCertification.setEnterpriseName(enterpriseCertificationVO.getEnterpriseName());
						newCertification.setLicensePic(enterpriseCertificationVO.getLicensePic());
						newCertification.setStatus("ec_verify");
						enterpriseCertificationDao.insert(newCertification);
						if (newCertification.getId() > 0) {
							// 关联到用户表
							user.setEnterpriseId(newCertification.getId());
							userDao.update(user);

							returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						} else {
							returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
						}
					} else if (enterpriseCertification.getStatus().equals("ec_refuse")) {
						enterpriseCertification.setPosition(enterpriseCertificationVO.getPosition());
						enterpriseCertification.setEnterpriseName(enterpriseCertificationVO.getEnterpriseName());
						enterpriseCertification.setLicensePic(enterpriseCertificationVO.getLicensePic());
						enterpriseCertification.setVerifyTime(null);
						enterpriseCertification.setAdminVerifierId(0);
						enterpriseCertification.setStatus("ec_verify");
						enterpriseCertification.setRefuseReason("");
						if (enterpriseCertificationDao.update(enterpriseCertification)) {
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
				returnValue.setResultCode(ResultCodeEnums.USER_NOT_EXIST);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public PagedResult<EnterpriseCertificationDTO> getEnterpriseCertificationList(Map<String, Object> map)
			throws ServiceException {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<EnterpriseCertification> enterpriseCertificationList = enterpriseCertificationDao.findByParam(map);
			List<EnterpriseCertificationDTO> enterpriseCertificationDTOList = new ArrayList<>();
			for (EnterpriseCertification enterpriseCertification : enterpriseCertificationList) {
				EnterpriseCertificationDTO enterpriseCertificationDTO = EnterpriseCertificationConvertor
						.convertEnterpriseCertification2EnterpriseCertificationDTO(enterpriseCertification);
				enterpriseCertificationDTO = setDTOExtendFields(enterpriseCertificationDTO, null);
				enterpriseCertificationDTOList.add(enterpriseCertificationDTO);
			}
			Page data = (Page) enterpriseCertificationList;
			PagedResult<EnterpriseCertificationDTO> pageResult = BeanUtil.toPagedResult(enterpriseCertificationDTOList,
					data.getPageNum(), data.getPageSize(), data.getTotal(), data.getPages());
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
	public EnterpriseCertificationDTO getEnterpriseCertification(HttpServletRequest request, int id)
			throws ServiceException {
		if (id > 0) {
			EnterpriseCertification enterpriseCertification = enterpriseCertificationDao.findById(id);
			if (enterpriseCertification != null) {
				EnterpriseCertificationDTO enterpriseCertificationDTO = EnterpriseCertificationConvertor
						.convertEnterpriseCertification2EnterpriseCertificationDTO(enterpriseCertification);
				if (enterpriseCertificationDTO != null) {
					enterpriseCertificationDTO = setDTOExtendFields(enterpriseCertificationDTO, request);
					return enterpriseCertificationDTO;
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
	public boolean verifyEnterpriseCertification(HttpServletRequest request, int id, String verify, String refuseReason)
			throws ServiceException {
		if (id > 0 && verify != null && !(verify.equals(""))) {
			EnterpriseCertification enterpriseCertification = enterpriseCertificationDao.findById(id);
			if (enterpriseCertification != null) {
				if (enterpriseCertification.getStatus().equals("ec_verify")) {
					int adminId=(int) request.getSession().getAttribute("adminId");
					String loginIp=(String) request.getSession().getAttribute("loginIp");
					enterpriseCertification.setAdminVerifierId(adminId);
					enterpriseCertification.setVerifyTime(new Timestamp(System.currentTimeMillis()));
					String adminLogContent = "管理员 " + getAdminName(adminId) + " 审核";
					if (verify.equals("verify_pass")) {
						adminLogContent += "通过了 用户 " + getUserName(enterpriseCertification.getUserId()) + " 的企业认证（"
								+ enterpriseCertification.getEnterpriseName() + "）";
						enterpriseCertification.setStatus("ec_pass");
					} else {
						adminLogContent += "拒绝了 用户 " + getUserName(enterpriseCertification.getUserId()) + " 的企业认证（"
								+ enterpriseCertification.getEnterpriseName() + "），原因：" + refuseReason;
						enterpriseCertification.setStatus("ec_refuse");
						enterpriseCertification.setRefuseReason(refuseReason);
					}
					if (enterpriseCertificationDao.update(enterpriseCertification)) {
						// 管理员日志
						addAdminLog(adminLogContent,adminId,loginIp);
						return true;
					} else {
						throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
					}
				} else {
					throw new ServiceException(ResultCodeEnums.DATA_INVALID);
				}
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	protected EnterpriseCertificationDTO setDTOExtendFields(EnterpriseCertificationDTO enterpriseCertificationDTO,
			HttpServletRequest request) {
		if (enterpriseCertificationDTO != null) {
			enterpriseCertificationDTO.setUserNickName(getUserNickName(enterpriseCertificationDTO.getUserId()));
			enterpriseCertificationDTO
					.setAdminVerifierName(getAdminName(enterpriseCertificationDTO.getAdminVerifierId()));
			enterpriseCertificationDTO.setStatusName(
					getDictionariesValue("enterprise_certification_status", enterpriseCertificationDTO.getStatus()));
			if (request != null && enterpriseCertificationDTO.getLicensePic() != null
					&& !(enterpriseCertificationDTO.getLicensePic().equals(""))) {
				enterpriseCertificationDTO
						.setLicensePicUrl(getImageRequestUrl(request, enterpriseCertificationDTO.getLicensePic()));
			}
		}
		return enterpriseCertificationDTO;
	}
}
