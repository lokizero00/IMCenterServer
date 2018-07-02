package com.loki.server.service;

import java.util.List;

import com.loki.server.entity.Carousel;
import com.loki.server.vo.ServiceResult;

public interface CarouselService {
	ServiceResult<List<Carousel>> getCarouselList(Integer size);
}
