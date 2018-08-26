package com.loki.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.loki.server.dao.TradeDockingDao;
import com.loki.server.dao.TradeIndustryDao;
import com.loki.server.dao.TradeInvoiceDao;
import com.loki.server.dao.TradePaycodeDao;
import com.loki.server.dao.UserCollectionDao;
import com.loki.server.dao.UserDao;
import com.loki.server.entity.EnterpriseCertification;
import com.loki.server.entity.IdentityCertification;
import com.loki.server.entity.Intention;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.Trade;
import com.loki.server.entity.TradeAttachment;
import com.loki.server.entity.TradeComplex;
import com.loki.server.entity.TradeDocking;
import com.loki.server.entity.TradeIndustry;
import com.loki.server.entity.TradeInvoice;
import com.loki.server.entity.TradePaycode;
import com.loki.server.entity.UserCollection;
import com.loki.server.service.TradeService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.BillConst;
import com.loki.server.utils.CommonUtil;
import com.loki.server.utils.OrderNoGenerator;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.utils.ServiceException;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeVO;

@Service
@Transactional
public class TradeServiceImpl extends BaseService implements TradeService {
	@Resource
	TradeDao tradeDao;
	@Resource
	TradeAttachmentDao tradeAttachmentDao;
	@Resource
	TradeIndustryDao tradeIndustryDao;
	@Resource
	TradeInvoiceDao tradeInvoiceDao;
	@Resource
	TradePaycodeDao tradePaycodeDao;
	@Resource
	UserDao userDao;
	@Resource
	TradeComplexDao tradeComplexDao;
	@Resource
	IntentionDao intentionDao;
	@Resource
	UserCollectionDao userCollectionDao;
	@Resource
	TradeDockingDao tradeDockingDao;

	
	//TODO 发布权限校验
//	→供应资源的发布，需要企业实名；供应资源的对接申请，只需要个人实名。@文彬
//	→采购需求的发布，只需要个人实名；采购需求的对接申请，需要个人实名。@文彬
	@Override
	public ServiceResult<Integer> publishTrade(TradeVO tradeVO) {
		ServiceResult<Integer> returnValue = new ServiceResult<Integer>();
		try {
			if (tradeVO.getTrade() != null) {
				// 校验意向金账户
				Intention intention = intentionDao.findByUserId(tradeVO.getTrade().getUserId());
				if (intention == null) {
					// 意向金账户为空，返回错误信息
					returnValue.setResultCode(ResultCodeEnums.INTENTION_NOT_EXIST);
					return returnValue;
				}

				Trade trade = tradeVO.getTrade();
				BigDecimal freezeIntention = tradeVO.getFreezeIntention();
				if (freezeIntention.compareTo(BigDecimal.ZERO) == 1) {
					if (intention.getAvailable().compareTo(freezeIntention) == -1) {
						// 意向金账户可用余额不足，返回错误信息
						returnValue.setResultCode(ResultCodeEnums.INTENTION_AVAILABLE_NOT_ENOUGH);
						return returnValue;
					} else {
						// 意向金大于0，免审核
						trade.setIntention(tradeVO.getFreezeIntention());
						trade.setStatus("trade_tendering");
					}
				} else {
					// 意向金为0，需审核
					trade.setIntention(BigDecimal.ZERO);
					// trade.setStatus("trade_verify");
					// TODO 测试使用，暂时不需要审核
					trade.setStatus("trade_tendering");
				}
				String logTradeType = "供应";
				if (trade.getType().equals("trade_demand")) {
					trade.setDeliveryTime(CommonUtil.getInstance().convert(tradeVO.getTradeDeliveryTimeStr()));
					logTradeType = "需求";
				}
				List<TradeAttachment> tradeAttachmentList = tradeVO.getTradeAttachmentList();
				List<TradeIndustry> tradeIndustryList = tradeVO.getTradeIndustryList();
				List<TradeInvoice> tradeInvoiceList = tradeVO.getTradeInvoiceList();
				List<TradePaycode> tradePaycodeList = tradeVO.getTradePaycodeList();
				trade.setSn(CommonUtil.getInstance().getTradeSN(trade.getType()));
				
				//添加实名认证和企业认证参数
				IdentityCertification ic=identityCertificationDao.findByUserId(trade.getUserId());
				if(ic!=null) {
					trade.setIdentityId(ic.getId());
				}
				EnterpriseCertification ec=enterpriseCertificationDao.findCurrentByUserId(trade.getUserId());
				if(ec!=null) {
					trade.setEnterpriseId(ec.getId());
				}
				
				tradeDao.insert(trade);
				if (trade.getId() > 0) {
					if (tradeAttachmentList != null) {
						for (TradeAttachment tradeAttachment : tradeAttachmentList) {
							tradeAttachment.setTradeId(trade.getId());
							tradeAttachmentDao.insert(tradeAttachment);
						}
					}
					if (tradeIndustryList != null) {
						for (TradeIndustry tradeIndustry : tradeIndustryList) {
							tradeIndustry.setTradeId(trade.getId());
							tradeIndustry.setValue(getDictionariesValue("industry", tradeIndustry.getCode()));
							tradeIndustryDao.insert(tradeIndustry);
						}
					}
					if (tradeInvoiceList != null) {
						for (TradeInvoice tradeInvoice : tradeInvoiceList) {
							tradeInvoice.setTradeId(trade.getId());
							tradeInvoice.setValue(getDictionariesValue("invoice", tradeInvoice.getCode()));
							tradeInvoiceDao.insert(tradeInvoice);
						}
					}
					if (tradePaycodeList != null) {
						for (TradePaycode tradePaycode : tradePaycodeList) {
							tradePaycode.setTradeId(trade.getId());
							tradePaycode.setValue(getDictionariesValue("paycode", tradePaycode.getCode()));
							tradePaycodeDao.insert(tradePaycode);
						}
					}

					String tradeLogContent = "您发布了 " + logTradeType;

					if (trade.getStatus().equals("trade_tendering")) {
						// 意向金冻结
						intention.setAvailable(intention.getAvailable().add(tradeVO.getFreezeIntention().negate()));
						intention.setFreeze(intention.getFreeze().add(tradeVO.getFreezeIntention()));
						intentionDao.update(intention);
						// 创建意向金日志
						addIntentionJournal("03",intention.getId(),intention.getUserId(),OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.FREEZE.getKey()),null,tradeVO.getFreezeIntention().negate(),logTradeType + " 发布成功,冻结意向金 " + tradeVO.getFreezeIntention());
						tradeLogContent += "，冻结意向金 " + tradeVO.getFreezeIntention();
					} else {
						tradeLogContent += "，请耐心等待管理员审核";
					}

					// 创建贸易日志
					addTradeLog(trade.getId(), "user", trade.getUserId(), trade.getStatus(), tradeLogContent);

					// 生成今日头条数据
					if (getSettingValue("tradePublishNews").equals("on")) {
						String CompanyName = trade.getEnterpriseId() > 0 ? getEnterpriseName(trade.getEnterpriseId())
								: getUserNickName(trade.getUserId());
						String newsTitle = "";
						newsTitle = CompanyName + " 发布了 【" + trade.getTitle() + "】的订单";
						if (trade.getType().equals("trade_demand")) {
							newsTitle += "，订单金额" + trade.getBudget().divide(new BigDecimal(10000))+" 万";
						}
						addTopLineNews(newsTitle, "tln_trade", trade.getId());
					}

					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(trade.getId());
				} else {
					returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<PagedResult<TradeComplex>> getTradeList_mobile(Map<String, Object> map) {
		ServiceResult<PagedResult<TradeComplex>> returnValue = new ServiceResult<>();
		try {
			if (map != null) {
				int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
				int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
				PageHelper.startPage(pageNo, pageSize);
				PagedResult<TradeComplex> tradeComplexList = BeanUtil.toPagedResult(tradeComplexDao.findByParam(map));
				if (tradeComplexList != null) {
					// 字段code处理
					for (int i = 0; i < tradeComplexList.getRows().size(); i++) {
						tradeComplexList.getRows().get(i).setStatusName(
								getDictionariesValue("trade_status", tradeComplexList.getRows().get(i).getStatus()));
						tradeComplexList.getRows().get(i).setTypeName(
								getDictionariesValue("trade_type", tradeComplexList.getRows().get(i).getType()));
						tradeComplexList.getRows().get(i).setTradeAttachmentList(
								tradeAttachmentDao.findByTradeId(tradeComplexList.getRows().get(i).getId()));
						tradeComplexList.getRows().get(i).setIdentityName(getIdentityName(tradeComplexList.getRows().get(i).getIdentityId()));
						tradeComplexList.getRows().get(i).setEnterpriseName(getEnterpriseName(tradeComplexList.getRows().get(i).getEnterpriseId()));
						if (tradeComplexList.getRows().get(i).getStatus().equals("trade_success")
								&& tradeComplexList.getRows().get(i).getDockingId() > 0) {
							TradeDocking tradeDocking = tradeDockingDao
									.findById(tradeComplexList.getRows().get(i).getDockingId());
							if (tradeDocking != null) {
								tradeComplexList.getRows().get(i)
								.setDockIdentityName(getIdentityName(tradeDocking.getUserId()));
								tradeComplexList.getRows().get(i)
										.setDockEnterpriseName(getEnterpriseName(tradeDocking.getEnterpriseId()));
								tradeComplexList.getRows().get(i).setDockOffer(tradeDocking.getOffer());
							}
						}
					}
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(tradeComplexList);
				} else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<TradeComplex> getTrade_mobile(Integer tradeId, Integer userId) {
		ServiceResult<TradeComplex> returnValue = new ServiceResult<>();
		try {
			if (tradeId != null && tradeId > 0) {
				TradeComplex tradeComplex = tradeComplexDao.findById(tradeId);
				if (tradeComplex != null) {
					// 字段code处理
					tradeComplex.setStatusName(getDictionariesValue("trade_status", tradeComplex.getStatus()));
					tradeComplex.setTypeName(getDictionariesValue("trade_type", tradeComplex.getType()));
					tradeComplex.setIdentityName(getIdentityName(tradeComplex.getIdentityId()));
					tradeComplex.setEnterpriseName(getEnterpriseName(tradeComplex.getEnterpriseId()));
					tradeComplex.setTradeAttachmentList(tradeAttachmentDao.findByTradeId(tradeComplex.getId()));
					tradeComplex.setTradeIndustryList(tradeIndustryDao.findByTradeId(tradeComplex.getId()));
					tradeComplex.setTradeInvoiceList(tradeInvoiceDao.findByTradeId(tradeComplex.getId()));
					tradeComplex.setTradePaycodeList(tradePaycodeDao.findByTradeId(tradeComplex.getId()));
					userId = userId != null ? userId : 0;
					tradeComplex.setCollectionId(0);
					
					if(userId >0) {
						if (tradeComplex.getUserId() == userId) {
							tradeComplex.setIdentifier("publisher");
						}else if(tradeComplex.getDockingId()>0) {
							TradeDocking td=tradeDockingDao.findByIdAndUserId(tradeComplex.getDockingId(), userId);
							if(td!=null) {
								tradeComplex.setIdentifier("docker");
							}else {
								tradeComplex.setIdentifier("other");
							}
						}else {
							int dockFlag = tradeDockingDao.countByTradeAndUserId(tradeId, userId);
							if(dockFlag>0) {
								tradeComplex.setIdentifier("docker");
							}else {
								tradeComplex.setIdentifier("other");
							}
						}
						Map<String, Object> map = new HashMap<>();
						map.put("tradeId", tradeId);
						map.put("userId", userId);
						List<UserCollection> ucList = userCollectionDao.findByParam(map);
						if (ucList.size() > 0) {
							tradeComplex.setCollectionId(ucList.get(0).getId());
						}
					} else {
						tradeComplex.setIdentifier("visitor");
					}
					
					tradeComplex.setPublishEaseId(userDao.findEaseIdById(tradeComplex.getUserId()));
					if(tradeComplex.getDockingId()>0) {
						TradeDocking tradeDocking=tradeDockingDao.findById(tradeComplex.getDockingId());
						if(tradeDocking!=null) {
							tradeComplex.setDockEaseId(userDao.findEaseIdById(tradeDocking.getUserId()));
						}
					}
					//阅读量+1
					tradeDao.updateReadCountAdd(tradeId);
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(tradeComplex);
				} else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public void tradeVerify(int tradeId, String verifyResult, String refuseReason, int adminId)
			throws ServiceException {
		if (tradeId > 0 && verifyResult != null && verifyResult != "") {
			Trade trade = tradeDao.findById(tradeId);
			if (trade != null) {
				if (trade.getStatus().equals("trade_verify")) {
					String tradeTypeName = getDictionariesValue("trade_type", trade.getType());
					String tradeLogContent = tradeTypeName;
					if (verifyResult.equals("verify_pass")) {
						tradeLogContent += " 审核通过";
						trade.setStatus("trade_tendering");
					} else {
						tradeLogContent += " 已被管理员下架";
						// 解冻意向金
						BigDecimal tradeIntention = trade.getIntention();
						if (tradeIntention.compareTo(BigDecimal.ZERO) == 1) {
							tradeLogContent += "，解冻意向金 " + tradeIntention;
							trade.setIntention(BigDecimal.ZERO);
							Intention intention = intentionDao.findByUserId(trade.getUserId());
							intention.setAvailable(intention.getAvailable().add(tradeIntention));
							intention.setFreeze(intention.getFreeze().add(tradeIntention.negate()));
							intentionDao.update(intention);
							String intentionLogContent = tradeTypeName + " 已被下架，解冻意向金 " + tradeIntention;
							
							addIntentionJournal("04",intention.getId(),intention.getUserId(),OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.UNFREEZE.getKey()),null,tradeIntention,intentionLogContent);
						}
						tradeLogContent += "，原因：" + refuseReason;
						trade.setStatus("trade_under_carriage");
					}
					if (tradeDao.update(trade)) {
						addTradeLog(trade.getId(), "admin", adminId, trade.getStatus(), tradeLogContent);
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

	@Override
	public ServiceResult<Void> setTradeOnShelves(int tradeId, int userId, BigDecimal tradeIntention) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			if (tradeId > 0 && userId > 0) {
				Trade trade = tradeDao.findByIdAndUserId(tradeId, userId);
				if (trade != null) {
					if (trade.getStatus().equals("trade_under_carriage")) {
						// 校验意向金账户
						Intention intention = intentionDao.findByUserId(userId);
						if (intention == null) {
							// 意向金账户为空，返回错误信息
							returnValue.setResultCode(ResultCodeEnums.INTENTION_NOT_EXIST);
							return returnValue;
						}
						String tradeTypeName = getDictionariesValue("trade_type", trade.getType());
						String tradeLogContent = "您上架了 " + tradeTypeName;
						if (tradeIntention.compareTo(BigDecimal.ZERO) == 1) {
							if (intention.getAvailable().compareTo(tradeIntention) == -1) {
								// 意向金账户可用余额不足，返回错误信息
								returnValue.setResultCode(ResultCodeEnums.INTENTION_AVAILABLE_NOT_ENOUGH);
								return returnValue;
							} else {
								// 意向金大于0，免审核
								trade.setIntention(tradeIntention);
								trade.setStatus("trade_tendering");
								tradeLogContent += "，冻结意向金 " + tradeIntention;
							}
						} else {
							// 意向金为0，需审核
							tradeLogContent += "，请耐心等待管理员审核";
							trade.setIntention(BigDecimal.ZERO);
							// trade.setStatus("trade_verify");
							// TODO 测试使用，暂时不需要审核
							trade.setStatus("trade_tendering");
						}

						if (tradeDao.update(trade)) {
							if (tradeIntention.compareTo(BigDecimal.ZERO) == 1) {
								// 意向金冻结
								intention.setAvailable(intention.getAvailable().add(tradeIntention.negate()));
								intention.setFreeze(intention.getFreeze().add(tradeIntention));
								intentionDao.update(intention);
								// 创建日志
								addIntentionJournal("03",intention.getId(),intention.getUserId(),OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.FREEZE.getKey()),null,tradeIntention.negate(),tradeTypeName + " 上架成功，冻结意向金 " + tradeIntention);
							}
							addTradeLog(trade.getId(), "user", trade.getUserId(), trade.getStatus(), tradeLogContent);
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
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> editTrade(TradeVO tradeVO) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			if (tradeVO.getTrade() != null && tradeVO.getTrade().getId() > 0 && tradeVO.getTrade().getUserId() > 0) {
				Trade trade = tradeDao.findByIdAndUserId(tradeVO.getTrade().getId(), tradeVO.getTrade().getUserId());
				if (trade != null) {
					if (trade.getStatus().equals("trade_under_carriage")) {
						trade.setTitle(tradeVO.getTrade().getTitle());
						trade.setContent(tradeVO.getTrade().getContent());
						trade.setProvinceName(tradeVO.getTrade().getProvinceName());
						trade.setCityName(tradeVO.getTrade().getCityName());
						trade.setTownName(tradeVO.getTrade().getTownName());
						if (trade.getType().equals("trade_demand")) {
							trade.setDeliveryTime(CommonUtil.getInstance().convert(tradeVO.getTradeDeliveryTimeStr()));
							trade.setQuantity(tradeVO.getTrade().getQuantity());
							trade.setBudget(tradeVO.getTrade().getBudget());
						} else {
							trade.setResourceName(tradeVO.getTrade().getResourceName());
							trade.setCapacity(tradeVO.getTrade().getCapacity());
						}
						List<TradeAttachment> tradeAttachmentList = tradeVO.getTradeAttachmentList();
						List<TradeIndustry> tradeIndustryList = tradeVO.getTradeIndustryList();
						List<TradeInvoice> tradeInvoiceList = tradeVO.getTradeInvoiceList();
						List<TradePaycode> tradePaycodeList = tradeVO.getTradePaycodeList();
						if (tradeDao.update(trade)) {
							tradeAttachmentDao.deleteByTradeId(trade.getId());
							if (tradeAttachmentList != null) {
								for (TradeAttachment tradeAttachment : tradeAttachmentList) {
									tradeAttachment.setTradeId(trade.getId());
									tradeAttachmentDao.insert(tradeAttachment);
								}
							}
							tradeIndustryDao.deleteByTradeId(trade.getId());
							if (tradeIndustryList != null) {
								for (TradeIndustry tradeIndustry : tradeIndustryList) {
									tradeIndustry.setTradeId(trade.getId());
									tradeIndustry.setValue(getDictionariesValue("industry", tradeIndustry.getCode()));
									tradeIndustryDao.insert(tradeIndustry);
								}
							}
							tradeInvoiceDao.deleteByTradeId(trade.getId());
							if (tradeInvoiceList != null) {
								for (TradeInvoice tradeInvoice : tradeInvoiceList) {
									tradeInvoice.setTradeId(trade.getId());
									tradeInvoice.setValue(getDictionariesValue("invoice", tradeInvoice.getCode()));
									tradeInvoiceDao.insert(tradeInvoice);
								}
							}
							tradePaycodeDao.deleteByTradeId(trade.getId());
							if (tradePaycodeList != null) {
								for (TradePaycode tradePaycode : tradePaycodeList) {
									tradePaycode.setTradeId(trade.getId());
									tradePaycode.setValue(getDictionariesValue("paycode", tradePaycode.getCode()));
									tradePaycodeDao.insert(tradePaycode);
								}
							}

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
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	//TODO 发布权限校验
	//	→供应资源的发布，需要企业实名；供应资源的对接申请，只需要个人实名。
	//	→采购需求的发布，只需要个人实名；采购需求的对接申请，需要个人实名。
	@Override
	public ServiceResult<Integer> addTrade(TradeVO tradeVO) {
		ServiceResult<Integer> returnValue = new ServiceResult<>();
		try {
			if (tradeVO.getTrade() != null) {
				Trade trade = tradeVO.getTrade();
				String logTradeType = "供应";
				if (trade.getType().equals("trade_demand")) {
					trade.setDeliveryTime(CommonUtil.getInstance().convert(tradeVO.getTradeDeliveryTimeStr()));
					logTradeType = "需求";
				}
				trade.setStatus("trade_under_carriage");
				trade.setIntention(BigDecimal.ZERO);
				List<TradeAttachment> tradeAttachmentList = tradeVO.getTradeAttachmentList();
				List<TradeIndustry> tradeIndustryList = tradeVO.getTradeIndustryList();
				List<TradeInvoice> tradeInvoiceList = tradeVO.getTradeInvoiceList();
				List<TradePaycode> tradePaycodeList = tradeVO.getTradePaycodeList();
				trade.setSn(CommonUtil.getInstance().getTradeSN(trade.getType()));
				//添加实名认证和企业认证参数
				IdentityCertification ic=identityCertificationDao.findByUserId(trade.getUserId());
				if(ic!=null) {
					trade.setIdentityId(ic.getId());
				}
				EnterpriseCertification ec=enterpriseCertificationDao.findCurrentByUserId(trade.getUserId());
				if(ec!=null) {
					trade.setEnterpriseId(ec.getId());
				}
				tradeDao.insert(trade);
				if (trade.getId() > 0) {
					if (tradeAttachmentList != null) {
						for (TradeAttachment tradeAttachment : tradeAttachmentList) {
							tradeAttachment.setTradeId(trade.getId());
							tradeAttachmentDao.insert(tradeAttachment);
						}
					}
					if (tradeIndustryList != null) {
						for (TradeIndustry tradeIndustry : tradeIndustryList) {
							tradeIndustry.setTradeId(trade.getId());
							tradeIndustry.setValue(getDictionariesValue("industry", tradeIndustry.getCode()));
							tradeIndustryDao.insert(tradeIndustry);
						}
					}
					if (tradeInvoiceList != null) {
						for (TradeInvoice tradeInvoice : tradeInvoiceList) {
							tradeInvoice.setTradeId(trade.getId());
							tradeInvoice.setValue(getDictionariesValue("invoice", tradeInvoice.getCode()));
							tradeInvoiceDao.insert(tradeInvoice);
						}
					}
					if (tradePaycodeList != null) {
						for (TradePaycode tradePaycode : tradePaycodeList) {
							tradePaycode.setTradeId(trade.getId());
							tradePaycode.setValue(getDictionariesValue("paycode", tradePaycode.getCode()));
							tradePaycodeDao.insert(tradePaycode);
						}
					}
					String tradeLogContent = "您创建了 " + logTradeType + ",尽快将它上架吧~";
					addTradeLog(trade.getId(), "user", trade.getUserId(), "trade_under_carriage", tradeLogContent);
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				} else {
					returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> setTradeUnderCarriage(int tradeId, int userId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			if (tradeId > 0 && userId > 0) {
				Trade trade = tradeDao.findByIdAndUserId(tradeId, userId);
				if (trade != null) {
					if (trade.getStatus().equals("trade_tendering")) {
						String tradeTypeName = getDictionariesValue("trade_type", trade.getType());
						trade.setStatus("trade_under_carriage");
						BigDecimal tradeIntention = trade.getIntention();
						trade.setIntention(BigDecimal.ZERO);
						if (tradeDao.update(trade)) {
							String tradeLogContent = "您下架了 " + tradeTypeName;
							if (tradeIntention.compareTo(BigDecimal.ZERO) == 1) {
								// 解冻意向金
								Intention intention = intentionDao.findByUserId(trade.getUserId());
								intention.setAvailable(intention.getAvailable().add(tradeIntention));
								intention.setFreeze(intention.getFreeze().add(tradeIntention.negate()));
								intentionDao.update(intention);
								String intentionLogContent = tradeTypeName + " 已被下架，解冻意向金 " + tradeIntention;
								
								addIntentionJournal("04",intention.getId(),intention.getUserId(),OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.UNFREEZE.getKey()),null,tradeIntention,intentionLogContent);
								tradeLogContent += "，解冻意向金 " + tradeIntention;
							}
							addTradeLog(trade.getId(), "user", userId, "trade_under_carriage", tradeLogContent);
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
		} catch (Exception e) {
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public PagedResult<TradeComplex> getTradeList(Map<String, Object> map) throws ServiceException {
		if (map != null) {
			int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
			int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
			PageHelper.startPage(pageNo, pageSize);
			PagedResult<TradeComplex> tradeComplexList = BeanUtil.toPagedResult(tradeComplexDao.findByParam(map));
			if (tradeComplexList != null) {
				for (int i = 0; i < tradeComplexList.getRows().size(); i++) {
					// 字段code处理
					tradeComplexList.getRows().get(i).setStatusName(
							getDictionariesValue("trade_status", tradeComplexList.getRows().get(i).getStatus()));
					tradeComplexList.getRows().get(i).setTypeName(
							getDictionariesValue("trade_type", tradeComplexList.getRows().get(i).getType()));
				}
				return tradeComplexList;
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public TradeComplex getTrade(int tradeId) throws ServiceException {
		if (tradeId > 0) {
			TradeComplex tradeComplex = tradeComplexDao.findById(tradeId);
			if (tradeComplex != null) {
				// 字段code处理
				tradeComplex.setStatusName(getDictionariesValue("trade_status", tradeComplex.getStatus()));
				tradeComplex.setTypeName(getDictionariesValue("trade_type", tradeComplex.getType()));
				tradeComplex.setIdentityName(getIdentityName(tradeComplex.getIdentityId()));
				tradeComplex.setEnterpriseName(getEnterpriseName(tradeComplex.getEnterpriseId()));
				tradeComplex.setTradeAttachmentList(tradeAttachmentDao.findByTradeId(tradeComplex.getId()));
				tradeComplex.setTradeIndustryList(tradeIndustryDao.findByTradeId(tradeComplex.getId()));
				tradeComplex.setTradeInvoiceList(tradeInvoiceDao.findByTradeId(tradeComplex.getId()));
				tradeComplex.setTradePaycodeList(tradePaycodeDao.findByTradeId(tradeComplex.getId()));
				
				return tradeComplex;
			} else {
				throw new ServiceException(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} else {
			throw new ServiceException(ResultCodeEnums.PARAM_ERROR);
		}
	}

	@Override
	public ServiceResult<PagedResult<TradeComplex>> getCollectionTrade(Map<String, Object> map) {
		ServiceResult<PagedResult<TradeComplex>> returnValue = new ServiceResult<>();
		try {
			if (map != null) {
				int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
				int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
				PageHelper.startPage(pageNo, pageSize);
				List<UserCollection> ucList = userCollectionDao.findByParam(map);
				List<TradeComplex> tradeList = new ArrayList<TradeComplex>();
				for (UserCollection uc : ucList) {
					TradeComplex trade = tradeComplexDao.findById(uc.getTradeId());
					if (trade != null) {
						trade.setStatusName(getDictionariesValue("trade_status", trade.getStatus()));
						trade.setTypeName(getDictionariesValue("trade_type", trade.getType()));
						tradeList.add(trade);
					}
				}
				PagedResult<TradeComplex> tradePagedList = BeanUtil.toPagedResult(tradeList);
				if (tradePagedList != null) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(tradePagedList);
				} else {
					returnValue.setResultCode(ResultCodeEnums.DATA_CONVERT_FAIL);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> tradeOwnCancel(Integer userId, Integer tradeId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			if (userId != null && userId > 0 && tradeId != null && tradeId > 0) {
				Trade trade = tradeDao.findByIdAndUserId(tradeId, userId);
				if (trade != null) {
					if (trade.getStatus().equals("trade_docking")) {
						String logTradeType = trade.getType().equals("trade_demand") ? "需求" : "供应";
						trade.setStatus("trade_own_cancel");
						boolean result = tradeDao.update(trade);
						if (result) {
							String tradeLogContent = logTradeType + " 取消申请已提交，请等待对接方确认";
							addTradeLog(trade.getId(), "user", userId, "trade_own_cancel", tradeLogContent);
							returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						} else {
							returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
						}
					} else {
						returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
					}
				} else {
					returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> trade3rdCancel(Integer userId, Integer tradeId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			if (userId != null && userId > 0 && tradeId != null && tradeId > 0) {
				Trade trade = tradeDao.findById(tradeId);
				if (trade != null) {
					TradeDocking tradeDocking = tradeDockingDao.findById(trade.getDockingId());
					if (tradeDocking != null && tradeDocking.getUserId() == userId) {
						if (trade.getStatus().equals("trade_docking")) {
							String logTradeType = trade.getType().equals("trade_demand") ? "需求" : "供应";
							trade.setStatus("trade_3rd_cancel");
							boolean result = tradeDao.update(trade);
							if (result) {
								String tradeLogContent = logTradeType + " 取消申请已提交，请等待发布方确认";
								addTradeLog(trade.getId(), "user", userId, "trade_3rd_cancel", tradeLogContent);
								returnValue.setResultCode(ResultCodeEnums.SUCCESS);
							} else {
								returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
							}
						} else {
							returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
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
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> tradeCancelConfirm(Integer userId, Integer tradeId, Integer tradeDockingId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			if (userId != null && userId > 0 && tradeId != null && tradeId > 0) {
				Trade trade = tradeDao.findByIdAndUserId(tradeId, userId);
				TradeDocking tradeDocking = tradeDockingDao.findByIdAndUserId(tradeDockingId, userId);
				if (trade == null) {
					trade = tradeDao.findById(tradeDocking.getTradeId());
				}
				if (tradeDocking == null) {
					tradeDocking = tradeDockingDao.findById(trade.getDockingId());
				}
				// int pubUserId = trade.getUserId();
				int dockerId = tradeDocking.getUserId();
				if (trade != null && tradeDocking != null) {
					if (trade.getStatus().equals("trade_3rd_cancel") || trade.getStatus().equals("trade_own_cancel")) {
						tradeDockingDao.delete(tradeDockingId);
						trade.setStatus("trade_tendering");
						trade.setDockingId(0);
						boolean result = tradeDao.update(trade);
						if (result) {
							String logTradeType = trade.getType().equals("trade_demand") ? "需求" : "供应";
							String tradeLogContent = logTradeType + " 的对接已取消，重新等待对接";
							addTradeLog(trade.getId(), "user", userId, "trade_tendering", tradeLogContent);

							// // 解冻发布方意向金
							// Intention intention = intentionDao.findByUserId(pubUserId);
							// BigDecimal unfreezeIntention = trade.getIntention();
							// if (unfreezeIntention.compareTo(BigDecimal.ZERO) == 1) {
							// // 意向金解冻
							// intention.setAvailable(intention.getAvailable().add(trade.getIntention()));
							// intention.setFreeze(intention.getFreeze().add(trade.getIntention().negate()));
							// intentionDao.update(intention);
							// // 创建意向金日志
							// addIntentionLog(intention.getId(), intention.getAvailable(),
							// trade.getIntention(),
							// "trade_cancel_unfreeze", trade.getId(), "user", pubUserId,
							// logTradeType + " 已取消，解冻意向金 " + trade.getIntention());
							// }

							// 解冻对接方意向金
							Intention intention_docker = intentionDao.findByUserId(dockerId);
							BigDecimal unfreezeIntention_docker = tradeDocking.getIntention();
							if (unfreezeIntention_docker.compareTo(BigDecimal.ZERO) == 1) {
								// 意向金解冻
								intention_docker
										.setAvailable(intention_docker.getAvailable().add(tradeDocking.getIntention()));
								intention_docker.setFreeze(
										intention_docker.getFreeze().add(tradeDocking.getIntention().negate()));
								intentionDao.update(intention_docker);
								// 创建意向金日志
								
								addIntentionJournal("04",intention_docker.getId(),intention_docker.getUserId(),OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.UNFREEZE.getKey()),null,tradeDocking.getIntention(),logTradeType + " 已取消，解冻意向金 " + tradeDocking.getIntention());
							}

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
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> tradeOwnSuccess(Integer userId, Integer tradeId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			if (userId != null && userId > 0 && tradeId != null && tradeId > 0) {
				Trade trade = tradeDao.findByIdAndUserId(tradeId, userId);
				if (trade != null) {
					String logTradeType = trade.getType().equals("trade_demand") ? "需求" : "供应";
					if (trade.getStatus().equals("trade_docking")) {
						trade.setStatus("trade_own_success");
						boolean result = tradeDao.update(trade);
						if (result) {
							String tradeLogContent = logTradeType + " 发布方已确认成功，请等待对接方确认";
							addTradeLog(trade.getId(), "user", userId, "trade_own_success", tradeLogContent);
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
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> trade3rdSuccess(Integer userId, Integer tradeId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			if (userId != null && userId > 0 && tradeId != null && tradeId > 0) {
				Trade trade = tradeDao.findById(tradeId);
				if (trade != null) {
					TradeDocking tradeDocking = tradeDockingDao.findById(trade.getDockingId());
					if (tradeDocking != null && tradeDocking.getUserId() == userId) {
						if (trade.getStatus().equals("trade_docking")) {
							String logTradeType = trade.getType().equals("trade_demand") ? "需求" : "供应";
							trade.setStatus("trade_3rd_success");
							boolean result = tradeDao.update(trade);
							if (result) {
								String tradeLogContent = logTradeType + " 对接方已确认成功，请等待发布方确认";
								addTradeLog(trade.getId(), "user", userId, "trade_3rd_success", tradeLogContent);
								returnValue.setResultCode(ResultCodeEnums.SUCCESS);
							} else {
								returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
							}
						} else {
							returnValue.setResultCode(ResultCodeEnums.DATA_INVALID);
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
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<Void> tradeSuccessConfirm(Integer userId, Integer tradeId, Integer tradeDockingId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			if (userId != null && userId > 0 && tradeId != null && tradeId > 0) {
				Trade trade = tradeDao.findByIdAndUserId(tradeId, userId);
				TradeDocking tradeDocking = tradeDockingDao.findByIdAndUserId(tradeDockingId, userId);
				if (trade == null) {
					trade = tradeDao.findById(tradeDocking.getTradeId());
				}
				if (tradeDocking == null) {
					tradeDocking = tradeDockingDao.findById(trade.getDockingId());
				}
				int pubUserId = trade.getUserId();
				int dockerId = tradeDocking.getUserId();
				if (trade != null && tradeDocking != null) {
					if (trade.getStatus().equals("trade_3rd_success")
							|| trade.getStatus().equals("trade_own_success")) {
						trade.setStatus("trade_success");
						boolean result = tradeDao.update(trade);
						if (result) {
							String logTradeType = trade.getType().equals("trade_demand") ? "需求" : "供应";
							String tradeLogContent = logTradeType + " 已成功";
							addTradeLog(trade.getId(), "user", userId, "trade_success", tradeLogContent);

							// 解冻发布方意向金
							Intention intention = intentionDao.findByUserId(pubUserId);
							BigDecimal unfreezeIntention = trade.getIntention();
							if (unfreezeIntention.compareTo(BigDecimal.ZERO) == 1) {
								// 意向金解冻
								intention.setAvailable(intention.getAvailable().add(trade.getIntention()));
								intention.setFreeze(intention.getFreeze().add(trade.getIntention().negate()));
								intentionDao.update(intention);
								// 创建意向金日志
								addIntentionJournal("04",intention.getId(),intention.getUserId(),OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.UNFREEZE.getKey()),null,trade.getIntention(),logTradeType + " 已成功，解冻意向金 " + trade.getIntention());
							}

							// 解冻对接方意向金
							Intention intention_docker = intentionDao.findByUserId(dockerId);
							BigDecimal unfreezeIntention_docker = tradeDocking.getIntention();
							if (unfreezeIntention_docker.compareTo(BigDecimal.ZERO) == 1) {
								// 意向金解冻
								intention_docker
										.setAvailable(intention_docker.getAvailable().add(tradeDocking.getIntention()));
								intention_docker.setFreeze(
										intention_docker.getFreeze().add(tradeDocking.getIntention().negate()));
								intentionDao.update(intention_docker);
								// 创建意向金日志
								addIntentionJournal("04",intention_docker.getId(),intention_docker.getUserId(),OrderNoGenerator.getPayOrderNo(BillConst.BillOrder.UNFREEZE.getKey()),null,tradeDocking.getIntention(),logTradeType + " 已成功，解冻意向金 " + tradeDocking.getIntention());
							}

							// 生成今日头条数据
							if (getSettingValue("tradeSuccessNews").equals("on")) {
								String CompanyNamePublisher = trade.getEnterpriseId() > 0
										? getEnterpriseName(trade.getEnterpriseId())
										: getUserNickName(pubUserId);
								String CompanyNameDocker = tradeDocking.getEnterpriseId() > 0
										? getEnterpriseName(tradeDocking.getEnterpriseId())
										: getUserNickName(dockerId);
								String newsTitle = "";
								newsTitle = CompanyNamePublisher + " 的【" + trade.getTitle() + "】的订单通过本平台与 "
										+ CompanyNameDocker + " 完成对接，订单金额";
								if (trade.getType().equals("trade_demand")) {
									newsTitle += trade.getBudget().divide(new BigDecimal(10000))+" 万";
								} else if (trade.getType().equals("trade_supply")) {
									newsTitle += tradeDocking.getOffer().divide(new BigDecimal(10000))+" 万";
								} else {
									newsTitle += "???";
								}
								addTopLineNews(newsTitle, "tln_trade", trade.getId());
							}

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
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<List<TradeComplex>> getLastest10Trade() {
		ServiceResult<List<TradeComplex>> returnValue = new ServiceResult<>();
		try {
			List<TradeComplex> tradeComplexList = tradeComplexDao.findLastest10Trade();
			if (tradeComplexList != null) {
				// 字段code处理
				for (int i = 0; i < tradeComplexList.size(); i++) {
					tradeComplexList.get(i)
							.setStatusName(getDictionariesValue("trade_status", tradeComplexList.get(i).getStatus()));
					tradeComplexList.get(i)
							.setTypeName(getDictionariesValue("trade_type", tradeComplexList.get(i).getType()));
				}
				returnValue.setResultCode(ResultCodeEnums.SUCCESS);
				returnValue.setResultObj(tradeComplexList);
			} else {
				returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<PagedResult<TradeComplex>> getRecommendedList(Map<String, Object> map) {
		ServiceResult<PagedResult<TradeComplex>> returnValue = new ServiceResult<>();
		try {
			if (map != null) {
				int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
				int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
				PageHelper.startPage(pageNo, pageSize);
				PagedResult<TradeComplex> tradeComplexList = BeanUtil
						.toPagedResult(tradeComplexDao.findRecommendedList(map));
				if (tradeComplexList != null) {
					// 字段code处理
					for (int i = 0; i < tradeComplexList.getRows().size(); i++) {
						tradeComplexList.getRows().get(i).setStatusName(
								getDictionariesValue("trade_status", tradeComplexList.getRows().get(i).getStatus()));
						tradeComplexList.getRows().get(i).setTypeName(
								getDictionariesValue("trade_type", tradeComplexList.getRows().get(i).getType()));
						tradeComplexList.getRows().get(i).setTradeAttachmentList(
								tradeAttachmentDao.findByTradeId(tradeComplexList.getRows().get(i).getId()));
					}
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(tradeComplexList);
				} else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<PagedResult<TradeComplex>> getDockingTradeList_mobile(Map<String, Object> map) {
		ServiceResult<PagedResult<TradeComplex>> returnValue = new ServiceResult<>();
		try {
			if (map != null) {
				int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
				int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
				PageHelper.startPage(pageNo, pageSize);
				PagedResult<TradeComplex> tradeComplexList = BeanUtil
						.toPagedResult(tradeComplexDao.findDockingTrade(map));
				if (tradeComplexList != null) {
					// 字段code处理
					for (int i = 0; i < tradeComplexList.getRows().size(); i++) {
						tradeComplexList.getRows().get(i).setStatusName(
								getDictionariesValue("trade_status", tradeComplexList.getRows().get(i).getStatus()));
						tradeComplexList.getRows().get(i).setTypeName(
								getDictionariesValue("trade_type", tradeComplexList.getRows().get(i).getType()));
					}
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(tradeComplexList);
				} else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<PagedResult<TradeComplex>> getOwnPublishTradeList_mobile(Map<String, Object> map) {
		ServiceResult<PagedResult<TradeComplex>> returnValue = new ServiceResult<>();
		try {
			if (map != null) {
				int pageNo = map.get("pageNo") == null ? 1 : (int) map.get("pageNo");
				int pageSize = map.get("pageSize") == null ? 10 : (int) map.get("pageSize");
				PageHelper.startPage(pageNo, pageSize);
				PagedResult<TradeComplex> tradeComplexList = BeanUtil.toPagedResult(tradeComplexDao.findByOwnPublish(map));
				if (tradeComplexList != null) {
					// 字段code处理
					for (int i = 0; i < tradeComplexList.getRows().size(); i++) {
						tradeComplexList.getRows().get(i).setStatusName(
								getDictionariesValue("trade_status", tradeComplexList.getRows().get(i).getStatus()));
						tradeComplexList.getRows().get(i).setTypeName(
								getDictionariesValue("trade_type", tradeComplexList.getRows().get(i).getType()));
						tradeComplexList.getRows().get(i).setTradeAttachmentList(
								tradeAttachmentDao.findByTradeId(tradeComplexList.getRows().get(i).getId()));
						tradeComplexList.getRows().get(i).setIdentityName(getIdentityName(tradeComplexList.getRows().get(i).getIdentityId()));
						tradeComplexList.getRows().get(i).setEnterpriseName(getEnterpriseName(tradeComplexList.getRows().get(i).getEnterpriseId()));
						if (tradeComplexList.getRows().get(i).getStatus().equals("trade_success")
								&& tradeComplexList.getRows().get(i).getDockingId() > 0) {
							TradeDocking tradeDocking = tradeDockingDao
									.findById(tradeComplexList.getRows().get(i).getDockingId());
							if (tradeDocking != null) {
								tradeComplexList.getRows().get(i)
								.setIdentityName(getIdentityName(tradeDocking.getUserId()));
								tradeComplexList.getRows().get(i)
										.setDockEnterpriseName(getEnterpriseName(tradeDocking.getEnterpriseId()));
								tradeComplexList.getRows().get(i).setDockOffer(tradeDocking.getOffer());
							}
						}
					}
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(tradeComplexList);
				} else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			} else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}
}
