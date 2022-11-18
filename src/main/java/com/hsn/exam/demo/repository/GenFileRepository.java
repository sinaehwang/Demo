package com.hsn.exam.demo.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hsn.exam.demo.vo.GenFile;

@Mapper
public interface GenFileRepository {

	void saveMeta(Map<String, Object> param);

	GenFile getGenFile(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo);

	List<GenFile> getGenFiles(String relTypeCode, int relId);
	
	void deleteFiles(String relTypeCode,int relId);

	void deleteFile(int id);


}
