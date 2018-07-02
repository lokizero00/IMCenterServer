package com.loki.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.loki.server.dao.TradeDao;
import com.loki.server.dao.TradeReportAttachmentDao;
import com.loki.server.dao.TradeReportDao;
import com.loki.server.dao.TradeReportInformationDao;
import com.loki.server.dto.TradeReportDTO;
import com.loki.server.dto.TradeReportInformationDTO;
import com.loki.server.dto.convertor.TradeReportConvertor;
import com.loki.server.dto.convertor.TradeReportInformationConvertor;
import com.loki.server.entity.PagedResult;
import com.loki.server.entity.Trade;
import com.loki.server.entity.TradeReport;
import com.loki.server.entity.TradeReportAttachment;
import com.loki.server.entity.TradeReportInformation;
import com.loki.server.service.TradeReportService;
import com.loki.server.utils.BeanUtil;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;
import com.loki.server.vo.TradeReportInformationVO;
import com.loki.server.vo.TradeReportVO;

@Service
public class TradeReportServiceImpl extends BaseService implements TradeReportService {
	@Resource
	TradeDao tradeDao;
	@Resource
	TradeReportDao tradeReportDao;
	@Resource
	TradeReportInformationDao tradeReportInformationDao;
	@Resource
	TradeReportAttachmentDao tradeReportAttachmentDao;

	/**
	 * 新增举报
	 * 
	 * @author lokizero00
	 * @param tradeReportVO
	 * @return tradeReportId
	 */
	@Override
	public ServiceResult<Integer> addTradeReport(TradeReportVO tradeReportVO) {
		ServiceResult<Integer> returnValue = new ServiceResult<>();
		try {
			if (tradeReportVO != null && tradeReportVO.getTradeReport() != null
					&& tradeReportVO.getTradeReportInformation() != null
					&& tradeReportVO.getTradeReport().getTradeId() > 0) {
				int tradeId = tradeReportVO.getTradeReport().getTradeId();
				Trade trade = tradeDao.findById(tradeId);
				if (trade != null) {
					int reportCount = tradeReportDao.getCountByTradeId(tradeId);
					if (trade.getStatus().equals("trade_docking") && reportCount <= 0) {
						trade.setStatus("trade_report_verify");
						if(tradeDao.update(trade)) {
							TradeReport tradeReport = tradeReportVO.getTradeReport();
							tradeReport.setCreatorId(tradeReport.getInformerId());
							tradeReport.setStatus("tr_report_verify");
							tradeReportDao.insert(tradeReport);
							if (tradeReport.getId() > 0) {
								TradeReportInformation tradeReportInformation = tradeReportVO.getTradeReportInformation();
								tradeReportInformation.setCreatorId(tradeReport.getInformerId());
								tradeReportInformation.setTradeReportId(tradeReport.getId());
								tradeReportInformation.setTradeId(tradeReport.getTradeId());
								tradeReportInformation.setType("trit_report");
								tradeReportInformationDao.insert(tradeReportInformation);

								if (tradeReportInformation.getId() > 0) {
									List<TradeReportAttachment> tradeReportAttachmentList = tradeReportVO
											.getTradeReportAttachmentList();
									for (TradeReportAttachment tradeReportAttachment : tradeReportAttachmentList) {
										tradeReportAttachment.setTradeReportInformationId(tradeReportInformation.getId());
										tradeReportAttachmentDao.insert(tradeReportAttachment);
									}
									
									String logContent=getUserNickName(tradeReport.getInformerId())+" 举报了本次的对接，管理员正在审核";
									addTradeLog(tradeId, "user", tradeReport.getInformerId(), trade.getStatus(), logContent);
									
									// TODO 发送举报通知

									returnValue.setResultCode(ResultCodeEnums.SUCCESS);
									returnValue.setResultObj(tradeReport.getId());
								} else {
									returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
								}
							} else {
								returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
							}
						}else {
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

	/**
	 * 新增举报/申诉信息
	 * 
	 * @author lokizero00
	 * @param tradeReportInformation
	 * @return tradeReportId
	 */
	@Override
	public ServiceResult<Integer> addTradeReportInformation(TradeReportInformationVO tradeReportInformationVO) {
		ServiceResult<Integer> returnValue = new ServiceResult<>();
		try {
			if (tradeReportInformationVO != null && tradeReportInformationVO.getTradeReportInformation()!=null && tradeReportInformationVO.getTradeReportAttachmentList()!=null) {
				TradeReport tradeReport=tradeReportDao.findById(tradeReportInformationVO.getTradeReportInformation().getTradeReportId());
				TradeReportInformation tradeReportInformation=tradeReportInformationVO.getTradeReportInformation();
				if(tradeReport!=null) {
					tradeReportInformationDao.insert(tradeReportInformation);
					if (tradeReportInformation.getId() > 0) {
						List<TradeReportAttachment> tradeReportAttachmentList = tradeReportInformationVO
								.getTradeReportAttachmentList();
						for (TradeReportAttachment tradeReportAttachment : tradeReportAttachmentList) {
							tradeReportAttachment.setTradeReportInformationId(tradeReportInformation.getId());
							tradeReportAttachmentDao.insert(tradeReportAttachment);
						}
						
						if(tradeReportInformation.getType().equals("trit_report")) {
							tradeReport.setStatus("tr_report_verify");
						}else {
							tradeReport.setStatus("tr_appeal_verify");
						}
						tradeReportDao.update(tradeReport);
						returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
						returnValue.setResultObj(tradeReport.getId());
					} else {
						returnValue.setResultCode(ResultCodeEnums.SAVE_FAIL);
					}
				}else {
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

	/**
	 * 取消举报
	 * 
	 * @author lokizero00
	 * @param tradeReportId
	 * @param informerId
	 * @return null
	 */
	@Override
	public ServiceResult<Void> cancelTradeReport(int tradeReportId, int informerId) {
		ServiceResult<Void> returnValue = new ServiceResult<>();
		try {
			if (tradeReportId > 0) {
				TradeReport tradeReport = tradeReportDao.findByIdAndInformerId(tradeReportId, informerId);
				if (tradeReport != null) {
					// TODO 取消举报，状态预判
					tradeReport.setStatus("tr_report_cancel");
					if (tradeReportDao.update(tradeReport)) {
						Trade trade = tradeDao.findById(tradeReport.getTradeId());
						trade.setStatus("trade_docking");
						if (tradeDao.update(trade)) {
							String logContent=getUserNickName(informerId)+" 取消了举报";
							addTradeLog(tradeReport.getTradeId(), "user", informerId, trade.getStatus(), logContent);
							returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						}else {
							returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
						}
					} else {
						returnValue.setResultCode(ResultCodeEnums.UPDATE_FAIL);
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

	/**
	 * 获取举报列表
	 * 
	 * @author lokizero00
	 * @param tradeReportId
	 * @param informerId
	 * @return null
	 */
	@Override
	public ServiceResult<PagedResult<TradeReportDTO>> getTradeReportList(int tradeId,Integer pageNo,Integer pageSize) {
		ServiceResult<PagedResult<TradeReportDTO>> returnValue=new ServiceResult<>();
		try {
			if(tradeId>0) {
				pageNo = pageNo == null ? 1 : pageNo;
				pageSize = pageSize == null ? 10 : pageSize;
				PageHelper.startPage(pageNo, pageSize);
				List<TradeReport> tradeReportList=tradeReportDao.findByTradeId(tradeId);
				List<TradeReportDTO> tradeReportDTOList=new ArrayList<>();
				for(TradeReport tradeReport:tradeReportList) {
					TradeReportDTO tradeReportDTO=TradeReportConvertor.convertTradeReport2TradeReportDTO(tradeReport);
					tradeReportDTO.setInformerName(getUserNickName(tradeReportDTO.getInformerId()));
					tradeReportDTO.setDelinquentName(getUserNickName(tradeReportDTO.getDelinquentId()));
					tradeReportDTO.setStatusName(getDictionariesValue("trade_report_status", tradeReportDTO.getStatus()));
					tradeReportDTO.setAdminVerifierName(getAdminName(tradeReportDTO.getAdminVerifierId()));
					tradeReportDTOList.add(tradeReportDTO);
				}
				Page data=(Page) tradeReportList;
				PagedResult<TradeReportDTO> pagedList=BeanUtil.toPagedResult(tradeReportDTOList,data.getPageNum(),data.getPageSize(),data.getTotal(),data.getPages());
				if(pagedList!=null) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(pagedList);
				}else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		}catch (Exception e) {
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	/**
	 * 获取举报/申诉信息列表
	 * 
	 * @author lokizero00
	 * @param tradeReportId
	 * @param informerId
	 * @return null
	 */
	@Override
	public ServiceResult<PagedResult<TradeReportInformationDTO>> getTradeReportInformationList(int tradeReportId,Integer pageNo,Integer pageSize) {
		ServiceResult<PagedResult<TradeReportInformationDTO>> returnValue=new ServiceResult<>();
		try {
			if(tradeReportId>0) {
				pageNo = pageNo == null ? 1 : pageNo;
				pageSize = pageSize == null ? 10 : pageSize;
				PageHelper.startPage(pageNo, pageSize);
				List<TradeReportInformation> tradeReportInformationList=tradeReportInformationDao.findByTradeReportId(tradeReportId);
				List<TradeReportInformationDTO> tradeReportInformationDTOList=new ArrayList<>();
				for(TradeReportInformation tradeReportInformation:tradeReportInformationList) {
					TradeReportInformationDTO tradeReportInformationDTO=TradeReportInformationConvertor.convertTradeReportInformation2TradeReportInformationDTO(tradeReportInformation);
					tradeReportInformationDTO.setCreatorName(getUserNickName(tradeReportInformation.getCreatorId()));
					tradeReportInformationDTO.setUpdaterName(getUserNickName(tradeReportInformation.getUpdaterId()));
					tradeReportInformationDTO.setTypeName(getDictionariesValue("trade_report_information_type", tradeReportInformation.getType()));
					List<TradeReportAttachment> tradeReportAttachmentList=tradeReportAttachmentDao.findByInformationId(tradeReportInformationDTO.getId());
					tradeReportInformationDTO.setAttachmentList(tradeReportAttachmentList);
					tradeReportInformationDTOList.add(tradeReportInformationDTO);
				}
				Page data=(Page) tradeReportInformationList;
				PagedResult<TradeReportInformationDTO> pagedList=BeanUtil.toPagedResult(tradeReportInformationDTOList,data.getPageNum(),data.getPageSize(),data.getTotal(),data.getPages());
				if(pagedList!=null) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(pagedList);
				}else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		}catch (Exception e) {
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

	@Override
	public ServiceResult<TradeReportDTO> getTradeReport(int tradeReportId) {
		ServiceResult<TradeReportDTO> returnValue = new ServiceResult<>();
		try {
			if(tradeReportId>0) {
				TradeReport tradeReport=tradeReportDao.findById(tradeReportId);
				if(tradeReport!=null) {
					TradeReportDTO tradeReportDTO=TradeReportConvertor.convertTradeReport2TradeReportDTO(tradeReport);
					if(tradeReportDTO!=null) {
						tradeReportDTO.setInformerName(getUserNickName(tradeReportDTO.getInformerId()));
						tradeReportDTO.setDelinquentName(getUserNickName(tradeReportDTO.getDelinquentId()));
						tradeReportDTO.setStatusName(getDictionariesValue("trade_report_status", tradeReportDTO.getStatus()));
						tradeReportDTO.setAdminVerifierName(getAdminName(tradeReportDTO.getAdminVerifierId()));
						
						returnValue.setResultCode(ResultCodeEnums.SUCCESS);
						returnValue.setResultObj(tradeReportDTO);
					}else {
						returnValue.setResultCode(ResultCodeEnums.DATA_CONVERT_FAIL);
					}
				}else {
					returnValue.setResultCode(ResultCodeEnums.DATA_QUERY_FAIL);
				}
			}else {
				returnValue.setResultCode(ResultCodeEnums.PARAM_ERROR);
			}
		}catch (Exception e) {
			returnValue.setResultCode(ResultCodeEnums.UNKNOW_ERROR);
		}
		return returnValue;
	}

}
