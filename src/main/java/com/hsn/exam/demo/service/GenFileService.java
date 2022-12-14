package com.hsn.exam.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.common.base.Joiner;
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

	/*
	 * public int saveMeta(String relTypeCode, int relId, String typeCode, String
	 * type2Code, int fileNo, String originFileName, String fileExtTypeCode, String
	 * fileExtType2Code, String fileExt, int fileSize, String fileDir) {
	 * 
	 * Map<String, Object> param = Ut.mapOf("relTypeCode", relTypeCode, "relId",
	 * relId, "typeCode", typeCode, "type2Code", type2Code, "fileNo", fileNo,
	 * "originFileName", originFileName, "fileExtTypeCode", fileExtTypeCode,
	 * "fileExtType2Code", fileExtType2Code, "fileExt", fileExt, "fileSize",
	 * fileSize, "fileDir", fileDir); genFileRepository.saveMeta(param);
	 * 
	 * int id = Ut.getAsInt(param.get("id"), 0);
	 * 
	 * return id;
	 * 
	 * }
	 */

	public ResultData saveMeta(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo,
			String originFileName, String fileExtTypeCode, String fileExtType2Code, String fileExt, int fileSize,
			String fileDir) {

		Map<String, Object> param = Ut.mapOf("relTypeCode", relTypeCode, "relId", relId, "typeCode", typeCode,
				"type2Code", type2Code, "fileNo", fileNo, "originFileName", originFileName, "fileExtTypeCode",
				fileExtTypeCode, "fileExtType2Code", fileExtType2Code, "fileExt", fileExt, "fileSize", fileSize,
				"fileDir", fileDir);

		genFileRepository.saveMeta(param);

		int id = Ut.getAsInt(param.get("id"), 0);

		return ResultData.from("S-1", "?????????????????????.", "id", id);

	}

	public ResultData save(MultipartFile multipartFile) {

		String fileInputName = multipartFile.getName();
		String[] fileInputNameBits = fileInputName.split("__");

		String relTypeCode = fileInputNameBits[1];
		int relId = Integer.parseInt(fileInputNameBits[2]);
		String typeCode = fileInputNameBits[3];
		String type2Code = fileInputNameBits[4];
		int fileNo = Integer.parseInt(fileInputNameBits[5]);

		return save(multipartFile, relTypeCode, relId, typeCode, type2Code, fileNo);

	}

	public ResultData save(MultipartFile multipartFile, int relId) {

		String fileInputName = multipartFile.getName();// file__member__0__extra__profileImg__1

		String[] fileInputNameBits = fileInputName.split("__");

		String relTypeCode = fileInputNameBits[1];
		String typeCode = fileInputNameBits[3];
		String type2Code = fileInputNameBits[4];
		int fileNo = Integer.parseInt(fileInputNameBits[5]);

		return save(multipartFile, relTypeCode, relId, typeCode, type2Code, fileNo);

	}

	public ResultData save(MultipartFile multipartFile, String relTypeCode, int relId, String typeCode,
			String type2Code, int fileNo) {

		String fileInputName = multipartFile.getName();

		String[] fileInputNameBits = fileInputName.split("__");

		if (fileInputNameBits[0].equals("file") == false) {

			return ResultData.from("F-1", "?????????????????? ???????????? ????????????.");
		}

		int fileSize = (int) multipartFile.getSize();

		if (fileSize <= 0) {

			return ResultData.from("F-2", "?????? ???????????? ???????????? ????????????.");
		}

		String originFileName = multipartFile.getOriginalFilename();
		String fileExtTypeCode = Ut.getFileExtTypeCodeFromFileName(multipartFile.getOriginalFilename());
		String fileExtType2Code = Ut.getFileExtType2CodeFromFileName(multipartFile.getOriginalFilename());
		String fileExt = Ut.getFileExtFromFileName(multipartFile.getOriginalFilename()).toLowerCase();

		if (fileExt.equals("jpeg")) {

			fileExt = "jpg";

		}

		else if (fileExt.equals("htm")) {

			fileExt = "html";

		}

		String fileDir = Ut.getNowYearMonthDateStr();

		if (relId > 0) {
			GenFile oldGenFile = getGenFile(relTypeCode, relId, typeCode, type2Code, fileNo);
			// ????????? ?????? ?????????????????? ?????????????????????,????????????????????? ??????
			if (oldGenFile != null) {
				deleteFile(oldGenFile);
			}
		}

		// ??????????????????????????????
		ResultData saveMetaRd = saveMeta(relTypeCode, relId, typeCode, type2Code, fileNo, originFileName,
				fileExtTypeCode, fileExtType2Code, fileExt, fileSize, fileDir);

		int newGenFileId = (int) saveMetaRd.getData1();

		/*
		 * int newGenFileId = saveMeta(relTypeCode, relId, typeCode, type2Code, fileNo,
		 * originFileName, fileExtTypeCode, fileExtType2Code, fileExt, fileSize,
		 * fileDir);
		 */
		// ??? ????????? ????????? ??????(io??????) ?????? ??????

		String targetDirPath = genFileDirPath + "/" + relTypeCode + "/" + fileDir;
		java.io.File targetDir = new java.io.File(targetDirPath);

		// ??? ????????? ????????? ????????? ???????????? ???????????? ??????

		if (targetDir.exists() == false) {
			targetDir.mkdirs();
		}

		String targetFileName = newGenFileId + "." + fileExt;
		String targetFilePath = targetDirPath + "/" + targetFileName;

		// ?????? ??????(???????????? ????????? ????????? ????????? ??????)
		try {
			multipartFile.transferTo(new File(targetFilePath));
		} catch (IllegalStateException |

				IOException e) {
			return ResultData.from("F-3", "??????????????? ?????????????????????.");
		}

		// return ResultData.from("S-1", "????????? ?????????????????????.");
		return new ResultData("S-1", "????????? ?????????????????????.", "id", newGenFileId, "fileRealPath", targetFilePath, "fileName",
				targetFileName, "fileInputName", fileInputName);

	}

	public void deleteFiles(String relTypeCode, int relId) {

		List<GenFile> genFiles = genFileRepository.getGenFilesByRelTypeCodeAndRelId(relTypeCode, relId);

		for (GenFile genFile : genFiles) {
			deleteFile(genFile);
		}

	}

	private void deleteFile(GenFile genFile) {

		String filePath = genFile.getFilePath(genFileDirPath);
		Ut.delteFile(filePath); // ??????????????? ???????????? ??????

		genFileRepository.deleteFile(genFile.getId()); // db?????? ??????
	}

	public List<GenFile> getGenFiles(String relTypeCode, int relId, String typeCode, String type2Code) {
		return genFileRepository.getGenFiles(relTypeCode, relId, typeCode, type2Code);
	}

	public GenFile getGenFile(int id) {

		return genFileRepository.getGenFileById(id);
	}

	public GenFile getGenFile(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo) {

		return genFileRepository.getGenFile(relTypeCode, relId, typeCode, type2Code, fileNo);
	}

	public ResultData saveFiles(Map<String, Object> param, MultipartRequest multipartRequest) {
		// ????????? ??????
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		Map<String, ResultData> filesResultData = new HashMap<>();
		List<Integer> genFileIds = new ArrayList<>();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				ResultData fileResultData = save(multipartFile);
				int genFileId = (int) fileResultData.getData1();
				genFileIds.add(genFileId);

				filesResultData.put(fileInputName, fileResultData);
			}
		}

		String genFileIdsStr = Joiner.on(",").join(genFileIds);

		// ?????? ??????
		int deleteCount = 0;

		for (String inputName : param.keySet()) {
			String[] inputNameBits = inputName.split("__");

			if (inputNameBits[0].equals("deleteFile")) {
				String relTypeCode = inputNameBits[1];
				int relId = Integer.parseInt(inputNameBits[2]);
				String typeCode = inputNameBits[3];
				String type2Code = inputNameBits[4];
				int fileNo = Integer.parseInt(inputNameBits[5]);

				GenFile oldGenFile = getGenFile(relTypeCode, relId, typeCode, type2Code, fileNo);

				if (oldGenFile != null) {
					deleteFile(oldGenFile);
					deleteCount++;
				}
			}
		}

		return new ResultData("S-1", "????????? ????????????????????????.", "filesResultData", filesResultData, "genFileIdsStr",
				genFileIdsStr, "deleteCount", deleteCount);
	}

	public void changeRelId(int id, int relId) {
		genFileRepository.changeRelId(id, relId);
	}

	public Map<Integer, Map<String, GenFile>> getFilesMapKeyRelIdAndFileNo(String relTypeCode, List<Integer> relIds,
			String typeCode, String type2Code) {

		List<GenFile> genFiles = genFileRepository.getGenFilesRelTypeCodeAndRelIdsAndTypeCodeAndType2Code(relTypeCode,
				relIds, typeCode, type2Code);

		Map<String, GenFile> map = new HashMap<>();

		Map<Integer, Map<String, GenFile>> rs = new LinkedHashMap<>();

		for (GenFile genFile : genFiles) {
			if (rs.containsKey(genFile.getRelId()) == false) {
				rs.put(genFile.getRelId(), new LinkedHashMap<>());
			}

			rs.get(genFile.getRelId()).put(genFile.getFileNo() + "", genFile);
		}

		return rs;
	}

	public void changeInputFileRelIds(Map<String, Object> param, int id) {
		String genFileIdsStr = Ut.ifEmpty((String) param.get("genFileIdsStr"), null);

		if (genFileIdsStr != null) {
			List<Integer> genFileIds = Ut.getListDividedBy(genFileIdsStr, ",");

			// ????????? ?????? ????????? ??????, ?????? ???????????? ???????????? ????????????, file??? relId??? ?????? 0?????? ????????????.
			// ????????? ??????????????? ????????? ????????? ??????.
			for (int genFileId : genFileIds) {
				changeRelId(genFileId, id);
			}
		}
	}

	public void deleteGenFile(String relTypeCode, int relId, String typeCode, String type2Code, int fileNo) {

		GenFile genFile = genFileRepository.getGenFile(relTypeCode, relId, typeCode, type2Code, fileNo);

		deleteGenFile(genFile);

	}

	private void deleteGenFile(GenFile genFile) {
		
		String filePath = genFile.getFilePath(genFileDirPath);
		
		Ut.deleteFile(filePath);
		
		genFileRepository.deleteFile(genFile.getId());

	}

}
