package com.hsn.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reply {
    private int id;
    private String regDate;
    private String updateDate;
    private String relTypeCode;
    private int relId;
    private int memberId;
    private int parentId;
    private String body;
    private boolean blindStatus;
    private String blindDate;
    private boolean delStatus;
    private String delDate;
    private int likeCount;
    private int dislikeCount;

    private String extra__writerName;

    public String getBodyForPrint() {
        String bodyForPrint = body.replaceAll("\r\n", "<br>");
        bodyForPrint = bodyForPrint.replaceAll("\r", "<br>");
        bodyForPrint = bodyForPrint.replaceAll("\n", "<br>");

        return bodyForPrint;
    }
    
    
	//작성자 프로필파일불러오기
	public String getReplyWriterProfileImgUri() {
        return "/common/genFile/file/member/" + memberId + "/extra/profileImg/1";
    }

    public String getReplyWriterProfileFallbackImgUri() {
        return "https://user-images.githubusercontent.com/109134688/204195353-6757dfa2-7b32-4625-bef6-cb229f5c8428.png";
    }
    
    public String getReplyWriterProfileFallbackImgOnErrorHtmlAttr() {
        return "this.src = '" + getReplyWriterProfileFallbackImgUri() + "'";
    }
    
    
}
