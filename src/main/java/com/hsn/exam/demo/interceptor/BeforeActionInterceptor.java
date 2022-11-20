package com.hsn.exam.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.vo.Member;
import com.hsn.exam.demo.vo.Req;

@Component

public class BeforeActionInterceptor implements HandlerInterceptor {

	@Autowired
	private MemberService memberService;
	
	@Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        HttpSession session = req.getSession();

        Member loginedMember = null;
        int loginedMemberId = 0;

        if (session.getAttribute("loginedMemberId") != null) {
            loginedMemberId = (int) session.getAttribute("loginedMemberId");
        }

        if (loginedMemberId != 0) {
            loginedMember = memberService.getMemberById(loginedMemberId);
        }

        req.setAttribute("req", new Req(loginedMember)); //req에 새 로그인된 회원정보를 담아둠

        return HandlerInterceptor.super.preHandle(req, resp, handler);
    }
	
	

}
