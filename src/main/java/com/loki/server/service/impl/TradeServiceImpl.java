package com.loki.server.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.TradeAttachmentDao;
import com.loki.server.dao.TradeComplexDao;
import com.loki.server.dao.TradeDao;
import com.loki.server.dao.TradeIndustryDao;
import com.loki.server.dao.TradeInvoiceDao;
import com.loki.server.dao.TradePaycodeDao;
import com.loki.server.dao.UserDao;
import com.loki.server.entity.Intention;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.Trade;
import com.loki.server.entity.TradeAttachment;
import com.loki.server.entity.TradeComplex;
import com.loki.server.entity.TradeIndustry;
import com.loki.server.entity.TradeInvoice;
import com.loki.server.entity.TradePaycode;
import com.loki.server.service.TradeService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeVO;

@Service
@Transactional
public class TradeServiceImpl extends BaseServiceImpl implements TradeService {
	@Resource TradeDao tradeDao;
	@Resource TradeAttachmentDao tradeAttachmentDao;
	@Resource TradeIndustryDao tradeIndustryDao;
	@Resource TradeInvoiceDao tradeInvoiceDao;
	@Resource TradePaycodeDao tradePaycodeDao;
	@Resource UserDao userDao;
	@Resource TradeComplexDao tradeComplexDao;
	@Resource IntentionDao intentionDao;
	
	@Override
	public ServiceResult<Integer> publishTrade(TradeVO tradeVO) {
		ServiceResult<Integer> returnValue=new ServiceResult<Integer>();
		if(tradeVO.getTrade()!=null) {
			//校验意向金账户
			Intention intention=intentionDao.findByUserId(tradeVO.getTrade().getUserId());
			if(intention==null) {
				//意向金账户为空，返回错误信息
				returnValue.setResultCode(ResultCodeEnums.INTENTION_NOT_EXIST);
				return returnValue;
			}
			
			Trade trade=tradeVO.getTrade();
			BigDecimal freezeIntention=tradeVO.getFreezeIntention();
			if(freezeIntention.compareTo(BigDecimal.ZERO)==1) {				
				if(intention.getAvailable().compareTo(freezeIntention)==-1) {
					//意向金账户可用余额不足，返回错误信息
					returnValue.setResultCode(ResultCodeEnums.INTENTION_AVAILABLE_NOT_ENOUGH);
					return returnValue;
				}else {
					//意向金大于0，免审核
					trade.setIntention(tradeVO.getFreezeIntention());
					trade.setStatus("trade_tendering");
				}
			}else {
				//意向金为0，需审核
				trade.setIntention(BigDecimal.ZERO);
				trade.setStatus("trade_verify");
			}
			String logTradeType="供应";
			if(trade.getType().equals("trade_demand")) {
				trade.setDeliveryTime(CommonUtil.getInstance().convert(tradeVO.getTradeDeliveryTimeStr()));
				logTradeType="需求";
			}
			List<TradeAttachment> tradeAttachmentList=tradeVO.getTradeAttachmentList();
			List<TradeIndustry> tradeIndustryList=tradeVO.getTradeIndustryList();
			List<TradeInvoice> tradeInvoiceList=tradeVO.getTradeInvoiceList();
			List<TradePaycode> tradePaycodeList=tradeVO.getTradePaycodeList();
			trade.setSn(CommonUtil.getInstance().getTradeSN(trade.getType()));
			tradeDao.insert(trade);
			if(trade.getId()>0) {
				if(tradeAttachmentList!=null) {
					for(TradeAttachment tradeAttachment:tradeAttachmentList) {
						tradeAttachment.setTradeId(trade.getId());
						tradeAttachmentDao.insert(tradeAttachment);
					}
				}
				if(tradeIndustryList!=null) {
					for(TradeIndustry tradeIndustry:tradeIndustryList) {
						tradeIndustry.setTradeId(trade.getId());
						tradeIndustry.setValue(getDictionariesValue("industry", tradeIndustry.getCode()));
						tradeIndustryDao.insert(tradeIndustry);
					}
				}
				if(tradeInvoiceList!=null) {
					for(TradeInvoice tradeInvoice:tradeInvoiceList) {
						tradeInvoice.setTradeId(trade.getId());
						tradeInvoice.setValue(getDictionariesValue("invoice", tradeInvoice.getCode()));
						tradeInvoiceDao.insert(tradeInvoice);
					}
				}
				if(tradePaycodeList!=null) {
					for(TradePaycode tradePaycode:tradePaycodeList) {
						tradePaycode.setTradeId(trade.getId());
						tradePaycode.setValue(getDictionariesValue("paycode", tradePaycode.getCode()));
						tradePaycodeDao.insert(tradePaycode);
					}
				}
				
				String tradeLogContent="您发布了 "+logTradeType;
				
				if(trade.getStatus().equals("trade_tendering")) {
					//意向金冻结
					intention.setAvailable(intention.getAvailable().add(tradeVO.getFreezeIntention().negate()));
					intention.setFreeze(tradeVO.getFreezeIntention());
					intentionDao.update(intention);
					//创建意向金日志
					addIntentionLog(intention.getId(), intention.getAvailable(), tradeVO.getFreezeIntention().negate(), "trade_freeze", trade.getId(), "user", trade.getUserId(), logTradeType+" 发布成功,冻结意向金 "+tradeVO.getFreezeIntention());
					tradeLogContent+="，冻结意向金 "+tradeVO.getFreezeIntention();
				}else {
					tradeLogContent+="，请耐心等待管理员审核";
				}
				
				//创建贸易日志
				addTradeLog(trade.getId(), "user", trade.getUserId(), trade.getStatus(), tradeLogContent);
				
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(trade.getId());
			}else {
				returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
			}
		}
		else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<PagedResult<TradeComplex>> getTradeListMobile(Map<String, Object> map,Integer pageNo,Integer pageSize) {
		ServiceResult<PagedResult<TradeComplex>> returnValue=new ServiceResult<>();
		if(map!=null) {
			pageNo = pageNo == null? 1:pageNo;  
		    pageSize = pageSize == null? 10:pageSize;
		    PageHelper.startPage(pageNo,pageSize);
			PagedResult<TradeComplex> tradeComplexList=BeanUtil.toPagedResult(tradeComplexDao.findByParam(map));
			if(tradeComplexList!=null) {
				//字段code处理
				for(int i=0;i<tradeComplexList.getRows().size();i++) {
					tradeComplexList.getRows().get(i).setStatusName(getDictionariesValue("trade_status", tradeComplexList.getRows().get(i).getStatus()));
					tradeComplexList.getRows().get(i).setTypeName(getDictionariesValue("trade_type", tradeComplexList.getRows().get(i).getType()));
				}
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(tradeComplexList);
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<TradeComplex> getTrade_mobile(int tradeId) {
		ServiceResult<TradeComplex> returnValue=new ServiceResult<>();
		if(tradeId>0) {
			TradeComplex tradeComplex=tradeComplexDao.findById(tradeId);
			if(tradeComplex!=null) {
				//字段code处理
				tradeComplex.setStatusName(getDictionariesValue("trade_status", tradeComplex.getStatus()));
				tradeComplex.setTypeName(getDictionariesValue("trade_type", tradeComplex.getType()));
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(tradeComplex);
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public void tradeVerify(int tradeId, String verifyResult, String refuseReason,int adminId) throws ServiceException {
		if(tradeId>0 && verifyResult!=null && verifyResult!="") {
			Trade trade=tradeDao.findById(tradeId);
			if(trade!=null) {
				if(trade.getStatus().equals("trade_verify")) {
					String tradeTypeName=getDictionariesValue("trade_type", trade.getType());
					String tradeLogContent=tradeTypeName;
					if(verifyResult.equals("verify_pass")) {
						tradeLogContent+=" 审核通过";
						trade.setStatus("trade_tendering");
					}else {
						tradeLogContent+=" 已被管理员下架";
						//解冻意向金
						BigDecimal tradeIntention=trade.getIntention();
						if(tradeIntention.compareTo(BigDecimal.ZERO)==1) {
							tradeLogContent+="，解冻意向金 "+tradeIntention;
							trade.setIntention(BigDecimal.ZERO);
							Intention intention=intentionDao.findByUserId(trade.getUserId());
							intention.setAvailable(intention.getAvailable().add(tradeIntention));
							intention.setFreeze(intention.getFreeze().add(tradeIntention.negate()));
							intentionDao.update(intention);
							String intentionLogContent=tradeTypeName+" 已被下架，解冻意向金 "+tradeIntention;
							addIntentionLog(intention.getId(), intention.getAvailable(), tradeIntention, "trade_unfreeze", trade.getId(), "admin", adminId, intentionLogContent);
						}
						tradeLogContent+="，原因："+refuseReason;
						trade.setStatus("trade_under_carriage");
					}
					if(tradeDao.update(trade)) {
						addTradeLog(trade.getId(), "admin", adminId, trade.getStatus(), tradeLogContent);
					}else {
						throw new ServiceException(ResultCodeEnums.UPDATE_FAIL);
					}
				}else {
					throw new ServiceException(ResultCodeEnums.DATA_INVALID);
				}
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public ServiceResult<Void> setTradeOnShelves(int tradeId,int userId,BigDecimal tradeIntention) {
		ServiceResult<Void> returnValue=new ServiceResult<>();
		if(tradeId>0 && userId>0) {
			Trade trade=tradeDao.findByIdAndUserId(tradeId, userId);
			if(trade!=null) {
				if(trade.getStatus().equals("trade_under_carriage")) {
					//校验意向金账户
					Intention intention=intentionDao.findByUserId(userId);
					if(intention==null) {
						//意向金账户为空，返回错误信息
						returnValue.setResultCode(ResultCodeEnums.INTENTION_NOT_EXIST);
						return returnValue;
					}
					String tradeTypeName=getDictionariesValue("trade_type", trade.getType());
					String tradeLogContent="您上架了 "+tradeTypeName;
					if(tradeIntention.compareTo(BigDecimal.ZERO)==1) {				
						if(intention.getAvailable().compareTo(tradeIntention)==-1) {
							//意向金账户可用余额不足，返回错误信息
							returnValue.setResultCode(ResultCodeEnums.INTENTION_AVAILABLE_NOT_ENOUGH);
							return returnValue;
						}else {
							//意向金大于0，免审核
							trade.setIntention(tradeIntention);
							trade.setStatus("trade_tendering");
							tradeLogContent+="，冻结意向金 "+tradeIntention;
						}
					}else {
						//意向金为0，需审核
						tradeLogContent+="，请耐心等待管理员审核";
						trade.setIntention(BigDecimal.ZERO);
						trade.setStatus("trade_verify");
					}
					
					if(tradeDao.update(trade)) {
						if(tradeIntention.compareTo(BigDecimal.ZERO)==1) {
							//意向金冻结
							intention.setAvailable(intention.getAvailable().add(tradeIntention.negate()));
							intention.setFreeze(tradeIntention);
							intentionDao.update(intention);
							//创建日志
							addIntentionLog(intention.getId(), intention.getAvailable(), tradeIntention.negate(), "trade_freeze", trade.getId(), "user", trade.getUserId(), tradeTypeName+" 上架成功，冻结意向金 "+tradeIntention);
						}
						addTradeLog(trade.getId(), "user", trade.getUserId(), trade.getStatus(), tradeLogContent);
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					}else {
						returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
					}
				}else {
					returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> editTrade(TradeVO tradeVO) {
		ServiceResult<Void> returnValue=new ServiceResult<>();
		if(tradeVO.getTrade()!=null && tradeVO.getTrade().getId()>0 && tradeVO.getTrade().getUserId()>0) {
			Trade trade=tradeDao.findByIdAndUserId(tradeVO.getTrade().getId(), tradeVO.getTrade().getUserId());
			if(trade!=null) {
				if(trade.getStatus().equals("trade_under_carriage")) {
					trade.setTitle(tradeVO.getTrade().getTitle());
					trade.setContent(tradeVO.getTrade().getContent());
					trade.setProvinceName(tradeVO.getTrade().getProvinceName());
					trade.setCityName(tradeVO.getTrade().getCityName());
					trade.setTownName(tradeVO.getTrade().getTownName());
					if(trade.getType().equals("trade_demand")) {
						trade.setDeliveryTime(CommonUtil.getInstance().convert(tradeVO.getTradeDeliveryTimeStr()));
						trade.setQuantity(tradeVO.getTrade().getQuantity());
						trade.setBudget(tradeVO.getTrade().getBudget());
					}else {
						trade.setResourceName(tradeVO.getTrade().getResourceName());
						trade.setCapacity(tradeVO.getTrade().getCapacity());
					}
					List<TradeAttachment> tradeAttachmentList=tradeVO.getTradeAttachmentList();
					List<TradeIndustry> tradeIndustryList=tradeVO.getTradeIndustryList();
					List<TradeInvoice> tradeInvoiceList=tradeVO.getTradeInvoiceList();
					List<TradePaycode> tradePaycodeList=tradeVO.getTradePaycodeList();
					if(tradeDao.update(trade)) {
						tradeAttachmentDao.deleteByTradeId(trade.getId());
						if(tradeAttachmentList!=null) {
							for(TradeAttachment tradeAttachment:tradeAttachmentList) {
								tradeAttachment.setTradeId(trade.getId());
								tradeAttachmentDao.insert(tradeAttachment);
							}
						}
						tradeIndustryDao.deleteByTradeId(trade.getId());
						if(tradeIndustryList!=null) {
							for(TradeIndustry tradeIndustry:tradeIndustryList) {
								tradeIndustry.setTradeId(trade.getId());
								tradeIndustry.setValue(getDictionariesValue("industry", tradeIndustry.getCode()));
								tradeIndustryDao.insert(tradeIndustry);
							}
						}
						tradeInvoiceDao.deleteByTradeId(trade.getId());
						if(tradeInvoiceList!=null) {
							for(TradeInvoice tradeInvoice:tradeInvoiceList) {
								tradeInvoice.setTradeId(trade.getId());
								tradeInvoice.setValue(getDictionariesValue("invoice", tradeInvoice.getCode()));
								tradeInvoiceDao.insert(tradeInvoice);
							}
						}
						tradePaycodeDao.deleteByTradeId(trade.getId());
						if(tradePaycodeList!=null) {
							for(TradePaycode tradePaycode:tradePaycodeList) {
								tradePaycode.setTradeId(trade.getId());
								tradePaycode.setValue(getDictionariesValue("paycode", tradePaycode.getCode()));
								tradePaycodeDao.insert(tradePaycode);
							}
						}
						
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					}else {
						returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
					}
				}else {
					returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}
		else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Integer> addTrade(TradeVO tradeVO) {
		ServiceResult<Integer> returnValue=new ServiceResult<>();
		if(tradeVO.getTrade()!=null) {
			Trade trade=tradeVO.getTrade();
			String logTradeType="供应";
			if(trade.getType().equals("trade_demand")) {
				trade.setDeliveryTime(CommonUtil.getInstance().convert(tradeVO.getTradeDeliveryTimeStr()));
				logTradeType="需求";
			}
			trade.setStatus("trade_under_carriage");
			trade.setIntention(BigDecimal.ZERO);
			List<TradeAttachment> tradeAttachmentList=tradeVO.getTradeAttachmentList();
			List<TradeIndustry> tradeIndustryList=tradeVO.getTradeIndustryList();
			List<TradeInvoice> tradeInvoiceList=tradeVO.getTradeInvoiceList();
			List<TradePaycode> tradePaycodeList=tradeVO.getTradePaycodeList();
			trade.setSn(CommonUtil.getInstance().getTradeSN(trade.getType()));
			tradeDao.insert(trade);
			if(trade.getId()>0) {
				if(tradeAttachmentList!=null) {
					for(TradeAttachment tradeAttachment:tradeAttachmentList) {
						tradeAttachment.setTradeId(trade.getId());
						tradeAttachmentDao.insert(tradeAttachment);
					}
				}
				if(tradeIndustryList!=null) {
					for(TradeIndustry tradeIndustry:tradeIndustryList) {
						tradeIndustry.setTradeId(trade.getId());
						tradeIndustry.setValue(getDictionariesValue("industry", tradeIndustry.getCode()));
						tradeIndustryDao.insert(tradeIndustry);
					}
				}
				if(tradeInvoiceList!=null) {
					for(TradeInvoice tradeInvoice:tradeInvoiceList) {
						tradeInvoice.setTradeId(trade.getId());
						tradeInvoice.setValue(getDictionariesValue("invoice", tradeInvoice.getCode()));
						tradeInvoiceDao.insert(tradeInvoice);
					}
				}
				if(tradePaycodeList!=null) {
					for(TradePaycode tradePaycode:tradePaycodeList) {
						tradePaycode.setTradeId(trade.getId());
						tradePaycode.setValue(getDictionariesValue("paycode", tradePaycode.getCode()));
						tradePaycodeDao.insert(tradePaycode);
					}
				}
				String tradeLogContent="您创建了 "+logTradeType+",尽快将它上架吧~";
				addTradeLog(trade.getId(), "user", trade.getUserId(), "trade_under_carriage", tradeLogContent);
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
			}else {
				returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
			}
		}
		else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> setTradeUnderCarriage(int tradeId, int userId) {
		ServiceResult<Void> returnValue=new ServiceResult<>();
		if(tradeId>0 && userId>0) {
			Trade trade=tradeDao.findByIdAndUserId(tradeId, userId);
			if(trade!=null) {
				if(trade.getStatus().equals("trade_tendering")) {
					String tradeTypeName=getDictionariesValue("trade_type", trade.getType());
					trade.setStatus("trade_under_carriage");
					BigDecimal tradeIntention=trade.getIntention();
					trade.setIntention(BigDecimal.ZERO);
					if(tradeDao.update(trade)) {
						String tradeLogContent="您下架了 "+tradeTypeName;
						if(tradeIntention.compareTo(BigDecimal.ZERO)==1) {
							//解冻意向金
							Intention intention=intentionDao.findByUserId(trade.getUserId());
							intention.setAvailable(intention.getAvailable().add(tradeIntention));
							intention.setFreeze(intention.getFreeze().add(tradeIntention.negate()));
							intentionDao.update(intention);
							String intentionLogContent=tradeTypeName+" 已被下架，解冻意向金 "+tradeIntention;
							addIntentionLog(intention.getId(), intention.getAvailable(), tradeIntention, "trade_unfreeze", trade.getId(), "user", userId, intentionLogContent);
							tradeLogContent+="，解冻意向金 "+tradeIntention;
						}
						addTradeLog(trade.getId(), "user", userId, "trade_under_carriage", tradeLogContent);
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					}else {
						returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
					}
				}else {
					returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}
	
	@Override
	public PagedResult<TradeComplex> getTradeList(Map<String, Object> map) throws ServiceException{
		if(map!=null) {
			int pageNo = map.get("pageNo") == null? 1:(int) map.get("pageNo");  
		    int pageSize = map.get("pageSize") == null? 10:(int) map.get("pageSize");
		    PageHelper.startPage(pageNo,pageSize);
		    PagedResult<TradeComplex> tradeComplexList=BeanUtil.toPagedResult(tradeComplexDao.findByParam(map));
			if(tradeComplexList!=null) {
				return tradeComplexList;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}
	
	@Override
	public TradeComplex getTrade(int tradeId) throws ServiceException{
		if(tradeId>0) {
			TradeComplex tradeComplex=tradeComplexDao.findById(tradeId);
			if(tradeComplex!=null) {
				//字段code处理
				tradeComplex.setStatusName(getDictionariesValue("trade_status", tradeComplex.getStatus()));
				tradeComplex.setTypeName(getDictionariesValue("trade_type", tradeComplex.getType()));
				return tradeComplex;
			}else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		}else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}
}
