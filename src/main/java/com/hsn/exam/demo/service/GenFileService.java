package com.hsn.exam.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.GenFileRepository;
import com.hsn.exam.demo.util.Ut;

@Service
public class GenFileService {
	
	@Autowired
	private GenFileRepository genFileRepository;

	public void  saveMeta(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo,
			String originFileName, String fileExtTypeCode, String fileExtType2Code, String fileExt, int fileSize,
			String fileDir) {
		
		Map<String, Object> param = Ut.mapOf("relTypeCode", relTypeCode, "relId", relId, "typeCode", typeCode,
				"type2Code", type2Code, "fileNo", fileNo, "originFileName", originFileName, "fileExtTypeCode",
				fileExtTypeCode, "fileExtType2Code", fileExtType2Code, "fileExt", fileExt, "fileSize", fileSize,
				"fileDir", fileDir);
		genFileRepository.saveMeta(param);

		int id = Ut.getAsInt(param.get("id"), 0);
		
		
		
	}

}
