package com.loki.server.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.entity.PagedResult;
import com.loki.server.entity.TradeComplex;
import com.loki.server.entity.TradeLog;
import com.loki.server.service.DictionariesService;
import com.loki.server.service.TradeLogService;
import com.loki.server.service.TradeService;

@Controller
@RequestMapping("/s/trade")
public class TradeController extends BaseController{
	@Autowired TradeService tradeService;
	@Autowired DictionariesService dictionariesService;
	@Autowired TradeLogService tradeLogService;
	
//	private static final Logger logger = Logger.getLogger(TradeController.class);
	
	/**
     * 显示首页
     * @return
     */
	@RequestMapping("/tradeList")  
	public String tradeList(){
		return "trade/tradeList";
	}
	
	/**
     * 分页查询
     * @return
     */
    @RequestMapping(value="/tradeList.do", method= RequestMethod.GET)
    @ResponseBody
    public String getTradeList(Integer userId,String sn,String title,String type,String provinceName,String cityName,String townName,String status,String invoiceCode,String industryCode,String payCode,Integer pageSize,Integer pageNo,String sortName,String sortOrder) {
		try {
			HashMap<String,Object> map=new HashMap<>();
			map.put("userId", userId);
			map.put("sn", sn);
			map.put("title", title);
			map.put("type", type);
			map.put("provinceName", provinceName);
			map.put("cityName", cityName);
			map.put("townName", townName);
			map.put("status", status);
			map.put("invoiceCode", invoiceCode);
			map.put("industryCode", industryCode);
			map.put("payCode", payCode);
			map.put("sortName", sortName);
			map.put("sortOrder", sortOrder);
			map.put("pageNo",pageNo);
			map.put("pageSize",pageSize);
			PagedResult<TradeComplex> list=tradeService.getTradeList(map);
    	    return responseSuccess(list);
    	} catch (Exception e) {
			return responseFail(e.getMessage());
		}
    }
    
    /**
     * 显示详情页
     * @return
     */
	@RequestMapping("/tradeDetail")  
	public String tradeDetail(int id){
		return "trade/tradeDetail.jsp?id="+id;
	}
	
	/**
     * 获取单个贸易
     * @return
     */
	@RequestMapping(value="/tradeDetail.do",method=RequestMethod.GET)
	@ResponseBody
	public String getTrade(HttpServletRequest request, int id) {
		try {
			TradeComplex tradeComplex=tradeService.getTrade(id);
			return responseSuccess(tradeComplex);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 获取贸易日志
     * @return
     */
	@RequestMapping(value="/tradeLog.do",method=RequestMethod.GET)
	@ResponseBody
	public String getTradeLog(HttpServletRequest request, int tradeId) {
		try {
			List<TradeLog> tradeLogList=tradeLogService.getTradeLog(tradeId);
			return responseArraySuccess(tradeLogList);
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
	
	/**
     * 显示审核页
     * @return
     */
	@RequestMapping("/tradeVerify")  
	public String tradeVerify(int id){
		return "trade/tradeVerify.jsp?id="+id;
	}
	
	/**
     * 贸易审核
     * @return
     */
	@RequestMapping(value="/tradeVerify.do",method=RequestMethod.POST)
	@ResponseBody
	public String tradeVerify(HttpServletRequest request,Integer tradeId, String verifyResult, String refuseReason) {
		try {
			int adminId=(int) request.getSession().getAttribute("adminId");
			tradeService.tradeVerify(tradeId, verifyResult, refuseReason, adminId);
			return responseSuccess();
		}catch(Exception e) {
			return responseFail(e.getMessage());
		}
	}
}
