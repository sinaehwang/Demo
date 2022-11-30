package com.hsn.exam.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.hsn.exam.demo.exception.GenFileNotFoundException;
import com.hsn.exam.demo.service.GenFileService;
import com.hsn.exam.demo.vo.GenFile;

@Controller
public class UsrFileController {
	
	@Value("${custom.genFileDirPath}")
    private String genFileDirPath;
	
	@Autowired
	GenFileService genFileService;
	
	//파일다운로드맵핑
	@GetMapping("/common/genFile/doDownload")
    public ResponseEntity<Resource> downloadFile(int id, HttpServletRequest request) throws IOException {
		
        GenFile genFile = genFileService.getGenFile(id);

        if (genFile == null) {
            throw new GenFileNotFoundException();
        }

        String filePath = genFile.getFilePath(genFileDirPath);

        Resource resource = new InputStreamResource(new FileInputStream(filePath));

        // Try to determine file's content type
        String contentType = request.getServletContext().getMimeType(new File(filePath).getAbsolutePath());

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + genFile.getOriginFileName() + "\"")
                .contentType(MediaType.parseMediaType(contentType)).body(resource);
    }

}
