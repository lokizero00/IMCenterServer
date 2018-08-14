package com.loki.server.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loki.server.dao.AdminDao;
import com.loki.server.dao.AdminLogDao;
import com.loki.server.dao.DictionariesDao;
import com.loki.server.dao.EnterpriseCertificationDao;
import com.loki.server.dao.IdentityCertificationDao;
import com.loki.server.dao.IntentionJournalDao;
import com.loki.server.dao.IntentionLogDao;
import com.loki.server.dao.SettingDao;
import com.loki.server.dao.TopLineNewsDao;
import com.loki.server.dao.TradeLogDao;
import com.loki.server.dao.UserDao;
import com.loki.server.entity.AdminLog;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.entity.IntentionJournal;
import com.loki.server.entity.IntentionLog;
import com.loki.server.entity.TopLineNews;
import com.loki.server.entity.TradeLog;
import com.loki.server.entity.User;
import com.loki.server.utils.PropertyUtil;
import com.loki.server.utils.SessionContext;

@Service
@Transactional
public class BaseService {
	@Resource
	TradeLogDao tradeLogDao;
//	@Resource
//	IntentionLogDao intentionLogDao;
	@Resource
	IntentionJournalDao intentionJournalDao;
	@Resource
	DictionariesDao dictionariesDao;
	@Resource
	UserDao userDao;
	@Resource
	AdminDao adminDao;
	@Resource
	SettingDao settingDao;
	@Resource
	AdminLogDao adminLogDao;
	@Resource
	EnterpriseCertificationDao enterpriseCertificationDao;
	@Resource
	IdentityCertificationDao identityCertificationDao;
	@Resource 
	TopLineNewsDao topLineNewsDao;
	

	protected void addTradeLog(int tradeId, String logRole, int logOperatorId, String logState, String logContent) {
		TradeLog tradeLog = new TradeLog();
		tradeLog.setTradeId(tradeId);
		tradeLog.setLogRole(logRole);
		tradeLog.setLogOperatorId(logOperatorId);
		tradeLog.setLogState(logState);
		tradeLog.setContent(logContent);
		tradeLogDao.insert(tradeLog);
	}

	protected void addIntentionJournal(String type,int intentionId,int userId,String innerBusiNo, BigDecimal amount, String logContent) {
		IntentionJournal intentionJournal = new IntentionJournal();
		intentionJournal.setType(type);
		intentionJournal.setIntentionId(intentionId);
		intentionJournal.setUserId(userId);
		intentionJournal.setInnerBusiNo(innerBusiNo);
		intentionJournal.setAmount(amount);
		intentionJournal.setState("01");
		intentionJournal.setCheckState("00");
		intentionJournal.setOpTime(new Timestamp(System.currentTimeMillis()));
		intentionJournal.setNeedThirdConfirm(0);
		intentionJournal.setMemo(logContent);
		intentionJournalDao.insert(intentionJournal);
	}

	protected String getDictionariesValue(String type, String code) {
		if (type != null && !(type.equals("")) && code != null && !(code.equals(""))) {
			return dictionariesDao.findValueByParam(type, code);
		} else {
			return null;
		}
	}

	protected String getUserNickName(int userId) {
		if (userId > 0) {
			return userDao.findNickNameById(userId);
		} else {
			return null;
		}
	}
	
	protected String getUserName(int userId) {
		if (userId > 0) {
			return userDao.findUserNameById(userId);
		} else {
			return null;
		}
	}

	protected String getAdminName(int adminId) {
		if (adminId > 0) {
			return adminDao.findAdminNameById(adminId);
		} else {
			return null;
		}
	}
	
	protected String getEnterpriseName(Integer enterpriseCertificationId) {
		if (enterpriseCertificationId!=null && enterpriseCertificationId > 0) {
			return enterpriseCertificationDao.findEnterpriseNameById(enterpriseCertificationId);
		} else {
			return null;
		}
	}
	
	protected String getIdentityName(Integer identityCertificationId) {
		if (identityCertificationId!=null && identityCertificationId > 0) {
			return identityCertificationDao.findIdentityNameById(identityCertificationId);
		} else {
			return null;
		}
	}
	
	protected String getIdentityNameByUserId(Integer userId) {
		if (userId!=null && userId > 0) {
			String trueName=null;
			IdentityCertification ic=identityCertificationDao.findByUserId(userId);
			if(ic!=null) {
				trueName=ic.getTrueName();
			}
			return trueName;
		} else {
			return null;
		}
	}
	
	protected String getUserPhoneByUserId(Integer userId) {
		if (userId!=null && userId > 0) {
			return userDao.findPhoneById(userId);
		} else {
			return null;
		}
	}

	protected String getSettingValue(String settingName) {
		if (settingName != null && !(settingName.equals(""))) {
			return settingDao.findByName(settingName);
		} else {
			return null;
		}
	}

	protected String getImageRequestUrl(HttpServletRequest request,String name) {
		if (request != null) {
			String path=getSettingValue("transferProtocol") + "://" + request.getHeader("host") + request.getContextPath()
					+ "/" + PropertyUtil.getInstance().getPropertyValue("common", "imageRequestParam");
			return path + "?name=" + name;
		} else {
			return null;
		}
	}
	
	protected String getImageRequestPath(HttpServletRequest request) {
		if (request != null) {
			return getSettingValue("transferProtocol") + "://" + request.getHeader("host") + request.getContextPath()
					+ "/" + PropertyUtil.getInstance().getPropertyValue("common", "imageRequestParam");
		} else {
			return null;
		}
	}

	protected void addAdminLog(String logContent,int adminId,String loginIp) {
		AdminLog adminLog = new AdminLog();
		adminLog.setAdminId(adminId);
		adminLog.setIp(loginIp);
		adminLog.setContent(logContent);
		adminLogDao.insert(adminLog);
	}
	
	protected String fetchUserAvatar(int userId) {
		String avatar="";
		if(userId>0) {
			User user=userDao.findById(userId);
			if(user!=null) {
				avatar= user.getAvatar();
			}
		}
		return avatar;
	}
	
	protected void addTopLineNews(String newsTitle,String newsType, int relationId) {
		if(relationId>0) {
			TopLineNews topLineNews=new TopLineNews();
			topLineNews.setNewsTitle(newsTitle);
			topLineNews.setNewsType(newsType);
			topLineNews.setRelationId(relationId);
			topLineNewsDao.insert(topLineNews);
		}
	}
}
