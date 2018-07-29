package com.loki.server.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.loki.server.dao.AdminDao;
import com.loki.server.dao.AdminLogDao;
import com.loki.server.dao.ResourcesDao;
import com.loki.server.dao.RoleAdminDao;
import com.loki.server.dao.RoleDao;
import com.loki.server.dao.RoleResourcesDao;
import com.loki.server.dao.SettingDao;
import com.loki.server.dto.AdminDTO;
import com.loki.server.dto.AdminLoginDTO;
import com.loki.server.dto.convertor.AdminConvertor;
import com.loki.server.entity.Admin;
import com.loki.server.entity.AdminLog;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.Resources;
import com.loki.server.entity.Role;
import com.loki.server.entity.RoleAdmin;
import com.loki.server.entity.RoleResources;
import com.loki.server.service.AdminService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.MD5;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.AdminVO;

@Service
@Transactional
public class AdminServiceImpl extends BaseService implements AdminService {
	@Resource
	AdminDao adminDao;
	@Resource
	AdminLogDao adminLogDao;
	@Resource
	RoleDao roleDao;
	@Resource
	ResourcesDao resourcesDao;
	@Resource
	RoleAdminDao roleAdminDao;
	@Resource
	RoleResourcesDao roleResourcesDao;
	@Resource
	SettingDao settingDao;

	@Override
	public boolean add(AdminVO adminVO) throws ServiceException {
		if (adminVO != null) {
			Admin admin = new Admin();
			admin.setCreateTime(new Timestamp(System.currentTimeMillis()));
			admin.setAdminCreatorId(adminVO.getAdminCreatorId());
			admin.setUserName(adminVO.getUserName());
			admin.setPassword(MD5.getMD5Str(adminVO.getPassword()));
			admin.setLoginCount(0);
			admin.setSuperAdmin(adminVO.isSuperAdmin());
			admin.setStatus(adminVO.getStatus());
			adminDao.insert(admin);
			if (admin.getId() > 0) {
				setRole(admin.getId(), adminVO.getRoleId());
				return true;
			} else {
				throw new ServiceException(ResultCodeEnums.ADMIN_INSERT_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public boolean edit(AdminVO adminVO) throws ServiceException {
		if (adminVO != null && adminVO.getId() > 0) {
			Admin admin =adminDao.findById(adminVO.getId());
			if(admin!=null) {
				admin.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				admin.setAdminUpdaterId(adminVO.getAdminCreatorId());
				admin.setUserName(adminVO.getUserName());
				admin.setSuperAdmin(adminVO.isSuperAdmin());
				admin.setStatus(adminVO.getStatus());
				if (adminDao.update(admin)) {
					setRole(adminVO.getId(), adminVO.getRoleId());
					return true;
				} else {
					throw new ServiceException(ResultCodeEnums.ADMIN_UPDATE_FAIL);
				}
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public boolean delete(int id) throws ServiceException {
		if (id > 0) {
			return adminDao.delete(id);
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public AdminDTO getAdmin(int id) throws ServiceException {
		if (id > 0) {
			Admin admin = adminDao.findById(id);
			if (admin != null) {
				AdminDTO adminDTO = AdminConvertor.convertAdmin2AdminDTO(admin);
				adminDTO.setAdminCreatorName(getAdminName(admin.getAdminCreatorId()));
				adminDTO.setAdminUpdaterName(getAdminName(admin.getAdminUpdaterId()));
				RoleAdmin roleAdmin = roleAdminDao.findByAdminId(id);
				if (roleAdmin != null) {
					Role role = roleDao.findById(roleAdmin.getRoleId());
					if (role != null) {
						adminDTO.setRoleId(role.getId());
						adminDTO.setRoleName(role.getName());
					}
				}
				return adminDTO;
			} else {
				throw new ServiceException(ResultCodeEnums.ADMIN_NOT_EXIST);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public PagedResult<AdminDTO> getAdminList(Map<String, Object> map) throws ServiceException {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			List<Admin> adminList = adminDao.findByParam(map);
			if (adminList != null) {
				List<AdminDTO> adminDTOList = new ArrayList<>();
				for (Admin admin : adminList) {
					AdminDTO adminDTO = AdminConvertor.convertAdmin2AdminDTO(admin);
					adminDTO.setAdminCreatorName(getAdminName(admin.getAdminCreatorId()));
					adminDTO.setAdminUpdaterName(getAdminName(admin.getAdminUpdaterId()));
					RoleAdmin roleAdmin = roleAdminDao.findByAdminId(admin.getId());
					if (roleAdmin != null) {
						Role role = roleDao.findById(roleAdmin.getRoleId());
						if (role != null) {
							adminDTO.setRoleId(role.getId());
							adminDTO.setRoleName(role.getName());
						}
					}
					adminDTOList.add(adminDTO);
				}
				Page data = (Page) adminList;
				PagedResult<AdminDTO> pagedList = BeanUtil.toPagedResult(adminDTOList, data.getPageNum(),
						data.getPageSize(), data.getTotal(), data.getPages());
				if (pagedList != null) {
					return pagedList;
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

	// TODO 这里有问题，第一行的loginCheck执行后，在RedisCache中的put方法中，value是null。很奇怪！
	@Override
	public AdminLoginDTO login(String userName, String password, String clientIP, String contextPath)
			throws ServiceException {
		if (userName != null && userName != "" && password != null && password != "") {
			Admin admin = adminDao.loginCheck(userName, password);
			if (admin != null) {
				admin.setLoginTime(new Timestamp(System.currentTimeMillis()));
				admin.setLoginCount(admin.getLoginCount() + 1);
				if (adminDao.update(admin)) {
					// 登录日志
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					AdminLog adminLog = new AdminLog();
					adminLog.setAdminId(admin.getId());
					adminLog.setIp(clientIP);
					adminLog.setContent("管理员 " + admin.getUserName() + " 在" + dateFormat.format(new Date()) + "登录了系统");
					adminLogDao.insert(adminLog);

					// 获取关联角色
					RoleAdmin roleAdmin = roleAdminDao.findByAdminId(admin.getId());
					Role role = roleDao.findById(roleAdmin.getRoleId());
					List<RoleResources> roleResourcesList = roleResourcesDao.findByRoleId(role.getId());

					List<Resources> menuList = new ArrayList<Resources>();
					List<String> permissionList = new ArrayList<String>();
					// 获取菜单/权限资源
					for (RoleResources roleResource : roleResourcesList) {
						Resources resources = resourcesDao.findById(roleResource.getResourcesId());
						if (resources.getType().equals("menu")) {
							menuList.add(resources);
						} else if (resources.getType().equals("action") || resources.getType().equals("page")) {
							String p_url = contextPath + "/" + resources.getUrl();
							permissionList.add(p_url);
						} else {
						}
					}
					AdminDTO adminDTO = AdminConvertor.convertAdmin2AdminDTO(admin);
					adminDTO.setRoleId(role.getId());
					adminDTO.setRoleName(role.getName());
					AdminLoginDTO adminLoginDTO = new AdminLoginDTO();
					adminLoginDTO.setAdminDTO(adminDTO);
					adminLoginDTO.setRole(role);
					adminLoginDTO.setMenuList(menuList);
					adminLoginDTO.setPermissionList(permissionList);
					return adminLoginDTO;
				} else {
					throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
				}
			} else {
				throw new ServiceException(ResultCodeEnums.LOGIN_DATA_INVALID);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	private boolean setRole(int adminId, int roleId) throws ServiceException {
		if (adminId > 0 && roleId > 0) {
			Admin admin = adminDao.findById(adminId);
			if (admin == null) {
				throw new ServiceException(ResultCodeEnums.ADMIN_NOT_EXIST);
			}
			roleAdminDao.deleteByAdminId(adminId);
			if(roleId==0) {
				return true;
			}
			Role role = roleDao.findById(roleId);
			if (role == null) {
				throw new ServiceException(ResultCodeEnums.ROLE_NOT_EXIST);
			}
			RoleAdmin roleAdmin = new RoleAdmin();
			roleAdmin.setAdminId(admin.getId());
			roleAdmin.setRoleId(role.getId());
			roleAdminDao.insert(roleAdmin);
			if (roleAdmin.getId() > 0) {
				return true;
			} else {
				throw new ServiceException(ResultCodeEnums.ADMIN_INSERT_ROLE_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public boolean changePassword(int adminId, int adminUpdaterId, String password) throws ServiceException {
		if (adminId > 0 && password != null && password != "") {
			return adminDao.changePassword(adminId, adminUpdaterId, MD5.getMD5Str(password));
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

}
