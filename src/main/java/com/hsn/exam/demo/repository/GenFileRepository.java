package com.hsn.exam.demo.repository;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenFileRepository {

	void saveMeta(Map<String, Object> param);

}
