package com.hsn.exam.demo.vo;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.hsn.exam.demo.util.Ut;


@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	private String currentUrl;
    private String currentUri;
    private Member loginedMember;
    private Map<String, String> paramMap;

    public Rq(Member loginedMember, String currentUri, Map<String, String> paramMap) {
        this.loginedMember = loginedMember;
        this.currentUrl = currentUri.split("\\?")[0];
        this.currentUri = currentUri;
        this.paramMap = paramMap;
    }

    public boolean isLogined() {
        return loginedMember != null;
    }

    public boolean isNotLogined() {
        return isLogined() == false;
    }

    public int getLoginedMemberId() {
        if (isNotLogined()) return 0;

        return loginedMember.getId();
    }

    public Member getLoginedMember() {
        return loginedMember;
    }

    public String getLoginedMemberNickname() {
        if (isNotLogined()) return "";

        return loginedMember.getNickname();
    }
    
    public String getLoginedMemberName() {
        if (isNotLogined()) return "";

        return loginedMember.getName();
    }

    public String getEncodedCurrentUri() {
        return Ut.getUriEncoded(getCurrentUri());
    }

    public String getCurrentUri() {
        return currentUri;
    }

    public String getLoginPageUri() {
    	
        String afterLoginUri;

        if (isLoginPage()) {
            afterLoginUri = Ut.getUriEncoded(paramMap.get("afterLoginUri"));
        } else {
            afterLoginUri = getEncodedCurrentUri();
        }

        return "../member/login?afterLoginUri=" + afterLoginUri;
    }

    private boolean isLoginPage() {
		return currentUrl.equals("/usr/member/login");
	}

}
