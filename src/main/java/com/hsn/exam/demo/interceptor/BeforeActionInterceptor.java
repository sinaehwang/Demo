package com.hsn.exam.demo.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.hsn.exam.demo.service.MemberService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Member;
import com.hsn.exam.demo.vo.Rq;

@Component

public class BeforeActionInterceptor implements HandlerInterceptor {

	@Autowired
	private MemberService memberService;
	
	@Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        
		Map<String, String> paramMap = Ut.getParamMap(req);
		
		HttpSession session = req.getSession();

        Member loginedMember = null;
        int loginedMemberId = 0;

        if (session.getAttribute("loginedMemberId") != null) {
            loginedMemberId = (int) session.getAttribute("loginedMemberId");
        }

        if (loginedMemberId != 0) {
            loginedMember = memberService.getMemberById(loginedMemberId);
        }
        
        String currentUrl = req.getRequestURI();
        String queryString = req.getQueryString();

        if (queryString != null && queryString.length() > 0) {
            currentUrl += "?" + queryString;
        }

        req.setAttribute("rq", new Rq(loginedMember, currentUrl,paramMap));
        


        return HandlerInterceptor.super.preHandle(req, resp, handler);
    }
	
	

}
