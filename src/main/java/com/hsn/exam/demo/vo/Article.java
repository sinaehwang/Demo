package com.hsn.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	
	private int id;
	private String regDate;
	private String updateDate;
	private String title;
	private String body;
	private int memberId;
	private int boardId;
	private boolean delStatus;
	
	private String extra__writerName;
	
	public String getBodyForPrint() {
	        String bodyForPrint = body.replaceAll("\r\n", "<br>");
	        bodyForPrint = bodyForPrint.replaceAll("\r", "<br>");
	        bodyForPrint = bodyForPrint.replaceAll("\n", "<br>");

	        return bodyForPrint;
	}

	

}
