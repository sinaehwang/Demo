package com.hsn.exam.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hsn.exam.demo.repository.GenFileRepository;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.GenFile;
import com.hsn.exam.demo.vo.ResultData;

@Service
public class GenFileService {
	
	@Value("${custom.genFileDirPath}")
	private String genFileDirPath;
	

	@Autowired
	private GenFileRepository genFileRepository;

	public int saveMeta(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo,
			String originFileName, String fileExtTypeCode, String fileExtType2Code, String fileExt, int fileSize,
			String fileDir) {

		Map<String, Object> param = Ut.mapOf("relTypeCode", relTypeCode, "relId", relId, "typeCode", typeCode,
				"type2Code", type2Code, "fileNo", fileNo, "originFileName", originFileName, "fileExtTypeCode",
				fileExtTypeCode, "fileExtType2Code", fileExtType2Code, "fileExt", fileExt, "fileSize", fileSize,
				"fileDir", fileDir);
		genFileRepository.saveMeta(param);

		int id = Ut.getAsInt(param.get("id"), 0);

		return id;

	}

	public ResultData save(MultipartFile multipartFile, int relId) {

		String fileInputName = multipartFile.getName();

		String[] fileInputNameBits = fileInputName.split("__");

		if (fileInputNameBits[0].equals("file") == false) {

			return ResultData.from("F-1", "파라미터값이 올바르지 않습니다.");
		}

		int fileSize = (int) multipartFile.getSize();

		if (fileSize <= 0) {

			return ResultData.from("F-2", "파일 크기값이 올바르지 않습니다.");
		}

		String relTypeCode = fileInputNameBits[1];
		String typeCode = fileInputNameBits[3];
		String type2Code = fileInputNameBits[4];
		int fileNo = Integer.parseInt(fileInputNameBits[5]);
		String originFileName = multipartFile.getOriginalFilename();
		String fileExtTypeCode = Ut.getFileExtTypeCodeFromFileName(multipartFile.getOriginalFilename());
		String fileExtType2Code = Ut.getFileExtType2CodeFromFileName(multipartFile.getOriginalFilename());
		String fileExt = Ut.getFileExtFromFileName(multipartFile.getOriginalFilename()).toLowerCase();

		if (fileExt.equals("jpeg")) {
			fileExt = "jpg";
		} else if (fileExt.equals("htm")) {
			fileExt = "html";
		}

		String fileDir = Ut.getNowYearMonthDateStr();

		int newGenFileId = saveMeta(relTypeCode, relId, typeCode, type2Code, fileNo, originFileName, fileExtTypeCode,
				fileExtType2Code, fileExt, fileSize, fileDir);

		// 새 파일이 저장될 폴더(io파일) 객체 생성
		String targetDirPath = genFileDirPath + "/" + relTypeCode + "/" + fileDir;
		java.io.File targetDir = new java.io.File(targetDirPath);

		// 새 파일이 저장될 폴더가 존재하지 않는다면 생성
		if (targetDir.exists() == false) {
			targetDir.mkdirs();
		}

		String targetFileName = newGenFileId + "." + fileExt;
		String targetFilePath = targetDirPath + "/" + targetFileName;

		// 파일 생성(업로드된 파일을 지정된 경로롤 옮김)
		try {
			multipartFile.transferTo(new File(targetFilePath));
		} catch (IllegalStateException | IOException e) {
			return ResultData.from("F-3", "파일저장에 실패하였습니다.");
		}

		return ResultData.from("S-1", "파일이 생성되었습니다.");
	}

	public void deleteFiles(String relTypeCode, int relId) {
		
		List<GenFile> genFiles = genFileRepository.getGenFiles(relTypeCode, relId);

		for ( GenFile genFile : genFiles ) {
			deleteFile(genFile);
		}
		
	}

	private void deleteFile(GenFile genFile) {

		String filePath = genFile.getFilePath(genFileDirPath);
		Ut.delteFile(filePath); //파일경로를 찾아가서 지움

		genFileRepository.deleteFile(genFile.getId()); //db에서 지움
	}

	public List<GenFile> getGenFiles(String relTypeCode, int relId, String typeCode, String type2Code) {
		return genFileRepository.getGenFiles(relTypeCode, relId, typeCode, type2Code);
	}


	public GenFile getGenFile(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo) {
		
		return genFileRepository.getGenFile(relTypeCode, relId, typeCode, type2Code, fileNo);
	}
	

}
