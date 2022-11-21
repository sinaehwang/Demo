package com.hsn.exam.demo.vo;

import com.hsn.exam.demo.util.Ut;

public class Rq {
	private String currentUrl;
    private String currentUri;
    private Member loginedMember;

    public Rq(Member loginedMember, String currentUri) {
        this.loginedMember = loginedMember;
        this.currentUrl = currentUri.split("\\?")[0];
        this.currentUri = currentUri;
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

    public String getEncodedCurrentUri() {
        return Ut.getUriEncoded(getCurrentUri());
    }

    private String getCurrentUri() {
        return currentUri;
    }

    public String getLoginPageUri() {
    	
        String afterLoginUri;

        if (isLoginPage()) {
            afterLoginUri = "";
        } else {
            afterLoginUri = getEncodedCurrentUri();
        }

        return "../member/login?afterLoginUri=" + afterLoginUri;
    }

    private boolean isLoginPage() {
		return currentUrl.equals("/usr/member/login");
	}

}
