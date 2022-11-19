package com.hsn.exam.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	@RequestMapping("/usr/member/join")
	public String join() {
		
		return "usr/member/join";
		
	}
	
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {
		
		if(Ut.empty(loginId)) {
			return ResultData.from("F-1", "loginId를 입력해주세요");
		}
		
		if(Ut.allNumberString(loginId)) {
			return ResultData.from("F-2", "로그인아이디는 숫자만으로 구성될수 없습니다.");
		}
		
		if(Ut.startWithNumberString(loginId)) {
			return ResultData.from("F-3", "로그인아이디는 숫자로 시작될수 없습니다.");
		}
		
		if(loginId.length()<5) {
			return ResultData.from("F-4", "로그인아이디를 5글자 이상 입력해주세요");
		}
		
		if(loginId.length()>20) {
			return ResultData.from("F-5", "로그인아이디를 20글자 이하로 입력해주세요");
		}
		
		if(Ut.isStandardLoginIdString(loginId) == false) {
			return ResultData.from("F-6", "로그인아이디는 영문과 숫자의 조합으로 구성되어야 합니다.");
		}
		
		
		Member existMember = memberService.getMemberByLoginId(loginId);
		
		if(existMember != null) {
			return ResultData.from("F-7", Ut.f("%s는 이미 사용중인 아이디입니다.",loginId ));
		}
		
		return ResultData.from("S-1", Ut.f("%s는 사용가능한 아이디입니다.",loginId ),"loginId",loginId);
	}
	
	
	
	@RequestMapping("/usr/member/doJoin")//브라우저요청으로 글을 추가하는경우
	public String dojoin(HttpServletRequest req,String loginId,String loginPw,String name,String nickname,String cellphoneNo,String email) {
		
		Member oldMember = memberService.getMemberByLoginId(loginId);

        if (oldMember != null) {
            return Ut.msgAndBack(req, loginId + "(은)는 이미 사용중인 로그인아이디 입니다.");
        }

        oldMember = memberService.getMemberByNameAndEmail(name, email);

        if (oldMember != null) {
            return Ut.msgAndBack(req, String.format("%s님은 이미 %s 메일주소로 %s 에 가입하셨습니다.", name, email, oldMember.getRegDate()));
        }

        ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

        if (joinRd.isFail()) {
            return Ut.msgAndBack(req, joinRd.getMsg());
        }

        int newMemberId = (int) joinRd.getData1();
		

        return Ut.msgAndReplace(req, joinRd.getMsg(), "/");
		
	}
	
	@RequestMapping("/usr/member/login")
    public String showLogin(HttpServletRequest req) {
        
		return "usr/member/login";
    }

    @RequestMapping("/usr/member/doLogin")
    public String doLogin(HttpServletRequest req, HttpSession session, String loginId, String loginPw, String redirectUrl) {
        
    	Member member = memberService.getMemberByLoginId(loginId);

        if (member == null) {
            return Ut.msgAndBack(req, loginId + "(은)는 존재하지 않는 로그인아이디 입니다.");
        }

        if (member.getLoginPw().equals(loginPw) == false) {
            
        	return Ut.msgAndBack(req, "비밀번호가 일치하지 않습니다.");
        }

        session.setAttribute("loginedMemberId", member.getId());
        
        return Ut.msgAndReplace(req, Ut.f("%s님 로그인되었습니다.", member.getLoginId()), redirectUrl);
    }

    
    @RequestMapping("/usr/member/doLogout")
    public String doLogout(HttpServletRequest req, HttpSession session) {
        
    	session.removeAttribute("loginedMemberId");

        String msg = "로그아웃 되었습니다.";
        return Ut.msgAndReplace(req, msg, "/");
    }
	
		
	

	
	
	


}
