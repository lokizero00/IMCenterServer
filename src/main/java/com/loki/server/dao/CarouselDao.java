package com.loki.server.dao;

import java.util.List;

import com.loki.server.entity.Carousel;

public interface CarouselDao {
	void insert(Carousel carousel);
	boolean update(Carousel carousel);
	boolean delete(int id);
	List<Carousel> findAll();
	Carousel findById(int id);
	List<Carousel> findBySize(int size);
}
