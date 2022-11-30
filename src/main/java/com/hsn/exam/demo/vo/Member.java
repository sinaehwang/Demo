package com.hsn.exam.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {

	private int id;
	private String regDate;
	private String updateDate;
	private String loginId;
	private String loginPw;
	private String authLevel;
	private String name;
	private String nickname;
	private String cellphoneNo;
	private String email;
	private String delStatus;
	private String delDate;

	public String getAuthLevelName() {
		return "일반회원";
	}

	// 회원 프로필파일불러오기
	public String getProfileImgUri() {
		return "/common/genFile/file/member/" + id + "/extra/profileImg/1";
	}

	public String getProfileFallbackImgUri() {
		return "https://user-images.githubusercontent.com/109134688/204195353-6757dfa2-7b32-4625-bef6-cb229f5c8428.png";
	}
	
	public String getProfileFallbackImgOnErrorHtmlAttr() {
        return "this.src = '" + getProfileFallbackImgUri() + "'";
    }

}
