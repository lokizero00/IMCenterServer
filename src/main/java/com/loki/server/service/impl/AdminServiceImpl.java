package com.loki.server.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.AdminDao;
import com.loki.server.dao.AdminLogDao;
import com.loki.server.dao.ResourcesDao;
import com.loki.server.dao.RoleAdminDao;
import com.loki.server.dao.RoleDao;
import com.loki.server.dao.RoleResourcesDao;
import com.loki.server.entity.Admin;
import com.loki.server.entity.AdminLog;
import com.loki.server.entity.Resources;
import com.loki.server.entity.Role;
import com.loki.server.entity.RoleAdmin;
import com.loki.server.entity.RoleResources;
import com.loki.server.service.AdminService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.AdminVO;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	@Resource private AdminDao adminDao;
	@Resource private AdminLogDao adminLogDao;
	@Resource private RoleDao roleDao;
	@Resource private ResourcesDao resourcesDao;
	@Resource private RoleAdminDao roleAdminDao;
	@Resource private RoleResourcesDao roleResourcesDao;

	@Override
	public void insert(Admin admin) {
		adminDao.insert(admin);
	}

	@Override
	public boolean update(Admin admin) {
		return adminDao.update(admin);
	}

	@Override
	public boolean delete(int id) {
		return adminDao.delete(id);
	}

	@Override
	public Admin findById(int id) {
		return adminDao.findById(id);
	}

	@Override
	public List<Admin> findAll() {
		return adminDao.findAll();
	}

	//TODO 这里有问题，第一行的loginCheck执行后，在RedisCache中的put方法中，value是null。很奇怪！
	@Override
	public AdminVO login(String userName, String password,String clientIP) throws ServiceException{
		if(userName!=null && userName!="" && password!=null && password!="") {
			Admin admin=adminDao.loginCheck(userName, password);
			if(admin!=null) {
				admin.setLoginTime(new Timestamp(System.currentTimeMillis()));
				admin.setLoginCount(admin.getLoginCount()+1);
				if(adminDao.update(admin)) {
					//登录日志
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					AdminLog adminLog=new AdminLog();
					adminLog.setAdminId(admin.getId());
					adminLog.setIp(clientIP);
					adminLog.setContent("管理员 "+admin.getUserName()+" 在"+dateFormat.format(new Date())+"登录了系统");
					adminLogDao.insert(adminLog);
					
					//获取关联角色
					RoleAdmin roleAdmin=roleAdminDao.findByAdminId(admin.getId());
					Role role=roleDao.findById(roleAdmin.getRoleId());
					List<RoleResources> roleResourcesList=roleResourcesDao.findByRoleId(role.getId());
					
					List<Resources> menuList=new ArrayList<Resources>();
					List<Resources> permissionList=new ArrayList<Resources>();
					//获取菜单/权限资源
					for(RoleResources roleResource:roleResourcesList) {
						Resources resources=resourcesDao.findById(roleResource.getResourcesId());
						if(resources.getType().equals("menu")) {
							menuList.add(resources);
						}else if(resources.getType().equals("action")) {
							permissionList.add(resources);
						}
						else {}
					}
					admin.setRoleId(role.getId());
					admin.setRoleName(role.getName());
					AdminVO adminVO=new AdminVO();
					adminVO.setAdmin(admin);
					adminVO.setRole(role);
					adminVO.setMenuList(menuList);
					adminVO.setPermissionList(permissionList);
					return adminVO;
				}else {
					throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
				}
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

}
