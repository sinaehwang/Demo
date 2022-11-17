package com.hsn.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.GenFileRepository;

@Service
public class GenFileService {
	
	@Autowired
	private GenFileRepository genFileRepository;

	public void saveMeta(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo,
			String originFileName, String fileExtTypeCode, String fileExtType2Code, String fileExt, int fileSize,
			String fileDir) {
		// TODO Auto-generated method stub
		
	}

}
