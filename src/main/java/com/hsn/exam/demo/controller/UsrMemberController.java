package com.hsn.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Member;
import com.hsn.exam.demo.vo.ResultData;

@Controller
public class UsrMemberController {
	
	//전역변수를 만들어줌

	@Autowired
	private MemberService memberService;
	
	
	@RequestMapping("/usr/member/join")//브라우저요청으로 글을 추가하는경우
	@ResponseBody()
	public ResultData join(String loginId,String loginPw,String name,String nickname,String cellphoneNo,String email) {
		
		if(Ut.empty(loginId)) {//로그인아이디 null값이거나 공백인경우를 체크하는 함수
			return ResultData.from("F-1", "loginId값을 입력해주세요");
		}
		
		if(Ut.empty(loginPw)) {
			return ResultData.from("F-2", "loginPw값을 입력해주세요");
		}
		
		if(Ut.empty(name)) {
			return ResultData.from("F-3","name값을 입력해주세요");
		}
		
		if(Ut.empty(nickname)) {
			return ResultData.from("F-4","nickname값을 입력해주세요");
		}
		
		if(Ut.empty(cellphoneNo)) {
			return ResultData.from("F-5","cellphoneNo값을 입력해주세요");
		} 
		
		if(Ut.empty(email)) {
			return ResultData.from("F-6","email값을 입력해주세요");
		}
		
		int id = memberService.join(loginId,loginPw,name,nickname,cellphoneNo,email);
		
		if(id==0) {
			return ResultData.from("F-7",Ut.f("이미 사용중인 아이디(%s)입니다.",loginId));
		}
		
		
		if(id == -1) {
			return ResultData.from("F-8",Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다.",name,email));
		}
		
		Member member = memberService.getMember(id);
		
		return ResultData.from("S-1",Ut.f("%s님 회원가입완료되었습니다.", nickname),member);
		
		
	}
	
	


}
