package com.loki.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.loki.server.dao.EnterpriseCertificationDao;
import com.loki.server.dao.IdentityCertificationDao;
import com.loki.server.dao.IntentionDao;
import com.loki.server.dao.TradeDao;
import com.loki.server.dao.TradeDockingDao;
import com.loki.server.dto.TradeDockingDTO;
import com.loki.server.dto.convertor.TradeDockingConvertor;
import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.entity.Intention;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.Trade;
import com.loki.server.entity.TradeDocking;
import com.loki.server.service.TradeDockingService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.BillConst;
import com.loki.server.utils.OrderNoGenerator;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Service
public class TradeDockingServiceImpl extends BaseService implements TradeDockingService {
	@Resource
	TradeDockingDao tradeDockingDao;
	@Resource
	TradeDao tradeDao;
	@Resource
	IntentionDao intentionDao;
	@Resource 
	IdentityCertificationDao identityCertificationDao;
	@Resource 
	EnterpriseCertificationDao enterpriseCertificationDao;

	@Override
	public ServiceResult<Integer> addTradeDocking(TradeDockingDTO tradeDockingDTO) {
		ServiceResult<Integer> returnValue = new ServiceResult<>();
		try {
			if (tradeDockingDTO != null) {
				int counts=tradeDockingDao.countByTradeAndUserId(tradeDockingDTO.getTradeId(), tradeDockingDTO.getUserId());
				if(counts==0) {
					Trade trade = tradeDao.findById(tradeDockingDTO.getTradeId());
					if (trade != null) {
						if (trade.getStatus().equals("trade_tendering")) {
							String logTradeType = trade.getType().equals("trade_demand") ? "需求" : "供应";
							Intention intention = intentionDao.findByUserId(tradeDockingDTO.getUserId());
							if (intention == null) {
								// 意向金账户为空，返回错误信息
								returnValue.setResultCode(ResultCodeEnums.INTENTION_NOT_EXIST);
								return returnValue;
							}
							IdentityCertification identityCertification=identityCertificationDao.findByUserId(tradeDockingDTO.getUserId());
							EnterpriseCertification enterpriseCertification=enterpriseCertificationDao.findCurrentByUserId(tradeDockingDTO.getUserId());
							if(identityCertification!=null && enterpriseCertification!=null) {
								TradeDocking tradeDocking = new TradeDocking();
								tradeDocking.setCreatorId(tradeDockingDTO.getUserId());
								tradeDocking.setUserId(tradeDockingDTO.getUserId());
								tradeDocking.setTradeId(tradeDockingDTO.getTradeId());
								tradeDocking.setIdentityId(identityCertification.getId());
								tradeDocking.setEnterpriseId(enterpriseCertification.getId());
								tradeDocking.setOffer(tradeDockingDTO.getOffer());
								tradeDocking.setIntention(tradeDockingDTO.getIntention());
								tradeDocking.setMessage(tradeDockingDTO.getMessage());
								tradeDocking.setType(tradeDockingDTO.getType());

								tradeDockingDao.insert(tradeDocking);
								if (tradeDocking.getId() > 0) {
									BigDecimal freezeIntention = tradeDockingDTO.getIntention();
									if (freezeIntention.compareTo(BigDecimal.ZERO) == 1) {
										if (intention.getAvailable().compareTo(freezeIntention) == -1) {
											// 意向金账户可用余额不足，返回错误信息
											returnValue.setResultCode(ResultCodeEnums.INTENTION_AVAILABLE_NOT_ENOUGH);
											return returnValue;
										}

										// 意向金冻结
										intention.setAvailable(
												intention.getAvailable().add(tradeDockingDTO.getIntention().negate()));
										intention.setFreeze(intention.getFreeze().add(tradeDockingDTO.getIntention()));
										intentionDao.update(intention);
										// 创建意向金日志
										addIntentionJournal("03",intention.getId(),intention.getUserId(),OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.FREEZE.getKey()),tradeDockingDTO.getIntention().negate(),logTradeType + " 对接成功,冻结意向金 " + tradeDockingDTO.getIntention());
									}

									trade.setDockingCount(trade.getDockingCount() + 1);
									tradeDao.update(trade);

									returnValue.setResultCode(ResultCodeEnums.SUCCESS);
									returnValue.setResultObj(trade.getId());
								} else {
									returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
								}
							}else {
								returnValue.setResultCode(ResultCodeEnums.CERTIFICATION_NOT_EXIST);
							}
						} else {
							returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
						}
					} else {
						returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
					}
				}else {
					returnValue.setResultCode(ResultCodeEnums.DUPLICATE_SUBMIT);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> delTradeDocking(Integer tradeId,Integer tradeDockingId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		if (tradeId!=null && tradeId>0 && tradeDockingId != null && tradeDockingId > 0) {
			Trade trade=tradeDao.findById(tradeId);
			TradeDocking tradeDocking=tradeDockingDao.findById(tradeDockingId);
			if(trade!=null && tradeDocking!=null) {
				String logTradeType = trade.getType().equals("trade_demand") ? "需求" : "供应";
				Intention intention = intentionDao.findByUserId(tradeDocking.getUserId());
				if (intention == null) {
					// 意向金账户为空，返回错误信息
					returnValue.setResultCode(ResultCodeEnums.INTENTION_NOT_EXIST);
					return returnValue;
				}
				BigDecimal unfreezeIntention = tradeDocking.getIntention();
				if (unfreezeIntention.compareTo(BigDecimal.ZERO) == 1) {
					// 意向金解冻
					intention.setAvailable(
							intention.getAvailable().add(tradeDocking.getIntention()));
					intention.setFreeze(intention.getFreeze().add(tradeDocking.getIntention().negate()));
					intentionDao.update(intention);
					// 创建意向金日志
					addIntentionJournal("04",intention.getId(),intention.getUserId(),OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.UNFREEZE.getKey()),tradeDocking.getIntention(),logTradeType + " 对接申请已取消，解冻意向金 " + tradeDocking.getIntention());
				}
				
				boolean result = intentionDao.delete(tradeDockingId);
				if (result) {
					trade.setDockingCount(trade.getDockingCount()-1);
					tradeDao.update(trade);
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				} else {
					returnValue.setResultCode(ResultCodeEnums.DELETE_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Integer> chooseDocking(Integer tradeId, Integer tradeDockingId) {
		ServiceResult<Integer> returnValue = new ServiceResult<>();
		if (tradeId != null && tradeId > 0 && tradeDockingId != null && tradeDockingId > 0) {
			Trade trade = tradeDao.findById(tradeId);
			TradeDocking tradeDockingData=tradeDockingDao.findById(tradeDockingId);
			if (trade != null && tradeDockingData!=null) {
				String logTradeType = trade.getType().equals("trade_demand") ? "需求" : "供应";
				trade.setDockingId(tradeDockingId);
				trade.setStatus("trade_docking");
				boolean result = tradeDao.update(trade);
				if (result) {
					List<TradeDocking> tradeDockingList=tradeDockingDao.findByTradeId(tradeId);
					int dockerId=0;
					for(TradeDocking tradeDocking:tradeDockingList) {
						if(tradeDocking.getId()==tradeDockingId) {
							dockerId=tradeDocking.getUserId();
						}else {
							Intention intention = intentionDao.findByUserId(tradeDocking.getUserId());
							BigDecimal unfreezeIntention = tradeDocking.getIntention();
							if (unfreezeIntention.compareTo(BigDecimal.ZERO) == 1) {
								// 意向金解冻
								intention.setAvailable(
										intention.getAvailable().add(tradeDocking.getIntention()));
								intention.setFreeze(intention.getFreeze().add(tradeDocking.getIntention().negate()));
								intentionDao.update(intention);
								// 创建意向金日志
								addIntentionJournal("04",intention.getId(),intention.getUserId(),OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.UNFREEZE.getKey()),tradeDocking.getIntention(),logTradeType + " 对接申请已作废，解冻意向金 " + tradeDocking.getIntention());
							}
						}
					}
					String tradeLogContent = "您的 " + logTradeType + " 已成功对接，对接方为 "
							+ getUserNickName(dockerId);
					// 创建贸易日志
					addTradeLog(trade.getId(), "user", trade.getUserId(), trade.getStatus(), tradeLogContent);
					
					//生成今日头条数据
					if(getSettingValue("tradeDockingNews").equals("on")) {
						String CompanyNamePublisher=trade.getEnterpriseId()>0? getEnterpriseName(trade.getEnterpriseId()):getUserNickName(trade.getUserId());
						String CompanyNameDocker=tradeDockingData.getEnterpriseId()>0? getEnterpriseName(tradeDockingData.getEnterpriseId()):getUserNickName(dockerId);
						String newsTitle="";
						newsTitle=CompanyNamePublisher+" 的【"+trade.getTitle()+"】的订单通过本平台与 "+CompanyNameDocker+" 成功对接，订单金额";
						if(trade.getType().equals("trade_demand")) {
							newsTitle+=trade.getBudget().divide(new BigDecimal(10000))+" 万";
						}else if(trade.getType().equals("trade_supply")) {
							newsTitle+=tradeDockingData.getOffer().divide(new BigDecimal(10000))+" 万";
						}else {
							newsTitle+="???";
						}
						addTopLineNews(newsTitle, "tln_trade", trade.getId());
					}
					
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(trade.getId());
				}else {
					returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
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
	public ServiceResult<PagedResult<TradeDockingDTO>> getTradeDockingList(Map<String,Object> map) {
		ServiceResult<PagedResult<TradeDockingDTO>> returnValue=new ServiceResult<>();
		try {
			if(map!=null) {
				int tradeId=(int) map.get("tradeId");
				Trade trade=tradeDao.findById(tradeId);
				if(trade!=null) {
					int userId=(int) map.get("userId");
					if(trade.getUserId()==userId) {
						map.remove("userId");
					}
					if(trade.getStatus().equals("trade_docking")) {
						int dockingTradeId=trade.getDockingId();
						map.put("id", dockingTradeId);
					}
					int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
					int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
					PageHelper.startPage(pageNo, pageSize);
					List<TradeDocking> tradeDockingList=tradeDockingDao.findByParam(map);
					List<TradeDockingDTO> tradeDockingDTOList=new ArrayList<>();
					for(TradeDocking tradeDocking:tradeDockingList) {
						TradeDockingDTO tradeDockingDTO=TradeDockingConvertor.convertTradeDocking2TradeDockingDTO(tradeDocking);
						tradeDockingDTO.setUserNickName(getUserNickName(tradeDockingDTO.getUserId()));
						tradeDockingDTO.setCreatorName(getUserName(tradeDockingDTO.getCreatorId()));
						tradeDockingDTO.setUpdaterName(getUserName(tradeDockingDTO.getUpdaterId()));
						tradeDockingDTO.setIdentityName(getIdentityName(tradeDockingDTO.getIdentityId()));
						tradeDockingDTO.setEnterpriseName(getEnterpriseName(tradeDockingDTO.getEnterpriseId()));
						tradeDockingDTO.setDockerAvatar(fetchUserAvatar(tradeDockingDTO.getUserId()));
						tradeDockingDTO.setIsChoose(trade.getDockingId()==tradeDocking.getId() ? 1:0);
						tradeDockingDTOList.add(tradeDockingDTO);
					}
					Page data=(Page) tradeDockingList;
					PagedResult<TradeDockingDTO> pagedList=BeanUtil.toPagedResult(tradeDockingDTOList,data.getPageNum(),data.getPageSize(),data.getTotal(),data.getPages());
					if(pagedList!=null) {
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						returnValue.setResultObj(pagedList);
					}else {
						returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
					}
				}else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}
}
