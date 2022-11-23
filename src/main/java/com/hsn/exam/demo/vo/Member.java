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
	
	

}
