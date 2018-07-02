package com.loki.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.loki.server.dao.CarouselDao;
import com.loki.server.entity.Carousel;
import com.loki.server.service.CarouselService;
import com.loki.server.utils.ResultCodeEnums;
import com.loki.server.vo.ServiceResult;

@Service
public class CarouselServiceImpl implements CarouselService {
	@Resource
	CarouselDao carouselDao;

	@Override
	public ServiceResult<List<Carousel>> getCarouselList(Integer size) {
		ServiceResult<List<Carousel>> returnValue = new ServiceResult<>();
		try {
			if (size != null) {
				size = size > 0 ? size : 3;
				List<Carousel> carouselList = carouselDao.findBySize(size);
				if (carouselList != null) {
					returnValue.setResultCode(ResultCodeEnums.SUCCESS);
					returnValue.setResultObj(carouselList);
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

}
