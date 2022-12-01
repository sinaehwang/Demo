package com.hsn.exam.demo.vo;

import java.util.HashMap;
import java.util.Map;

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
	private int catergoryId;
	
	private int goodReactionPoint;
	private int badReactionPoint;
	
	private boolean extra__actorCanDelete;
	private boolean extra__actorCanModify;
	
	private String extra__writerName;
	private String extra__thumbImg;
	
	private Map<String, Object> extra;
	private Catergory catergory;

	public Map<String, Object> getExtraNotNull() {
		if ( extra == null ) {
			extra = new HashMap<String, Object>();
		}

		return extra;
	}
	
	
	public String getBodyForPrint() {
	        String bodyForPrint = body.replaceAll("\r\n", "<br>");
	        bodyForPrint = bodyForPrint.replaceAll("\r", "<br>");
	        bodyForPrint = bodyForPrint.replaceAll("\n", "<br>");

	        return bodyForPrint;
	}
	
	//작성자 프로필파일불러오기
	public String getWriterProfileImgUri() {
        return "/common/genFile/file/member/" + memberId + "/extra/profileImg/1";
    }

    public String getWriterProfileFallbackImgUri() {
        return "https://user-images.githubusercontent.com/109134688/204195353-6757dfa2-7b32-4625-bef6-cb229f5c8428.png";
    }
    
    public String getWriterProfileFallbackImgOnErrorHtmlAttr() {
        return "this.src = '" + getWriterProfileFallbackImgUri() + "'";
    }
	

}
