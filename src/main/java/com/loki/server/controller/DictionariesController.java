package com.loki.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.entity.Dictionaries;
import com.loki.server.service.DictionariesService;

@Controller
@RequestMapping("/s/dictionaries")
public class DictionariesController extends BaseController {
	@Autowired
	DictionariesService dictionariesService;

	// 获取系统字典
	@RequestMapping(value = "/getDictionariesList", method = RequestMethod.GET)
	@ResponseBody
	public String getDictionariesList(HttpServletRequest request, String type) {
		try {
			List<Dictionaries> list = dictionariesService.getDictionariesList(type);
			return responseArraySuccess(list);
		} catch (Exception e) {
			return responseFail(e.getMessage());
		}
	}
}
