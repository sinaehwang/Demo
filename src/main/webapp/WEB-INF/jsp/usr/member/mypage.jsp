<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"  value="MY PAGE" />  

<%@ include file="../common/head.jspf" %>

<%@ page import="com.hsn.exam.demo.util.Ut" %>


<div class="section-article-list">
  <div class="container mx-auto">
  
     <div class="mt-5 card bordered shadow-lg item-bt-1-not-last-child">
          <div class="card-title">
            <a href="javascript:history.back();" class="cursor-pointer">
              <i class="fas fa-chevron-left"></i>
            </a>
            <span>MY PAGE 정보</span>
          </div>
          
          <div class="item-bt-1-not-last-child">
                    
                    <span class="badge badge-outline">프로필</span>
                    <a href="${detailUrl}" class="hover:underline cursor-pointer"></a>

                    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">
                    
                      <div class = "imgBackground flex justify-center" >                      
                        <a href="#" class="row-span-2 order-1 ">
                            <img class="w-20 h-20 object-cover rounded-full" onerror="${rq.loginedMember.profileFallbackImgOnErrorHtmlAttr}" src="${rq.loginedMember.profileImgUri}" alt="">
                        </a>
                        </div>
    
                        <div class="order-2 ">
                            <span class="badge badge-primary ">번호</span>
                            <span class = "hover:underline">${rq.loginedMember.id}</span>
                        </div>
    
                        <div class="order-3 ">
                            <span class="badge badge-accent">회원타입</span>
                            <span class = "hover:underline">${rq.loginedMember.authLevelName}</span>
                        </div>
    
                        <div class="order-4 ">
                            <span class="badge">등록날짜</span>
                            <span class=" hover:underline text-gray-600 text-light">${rq.loginedMember.regDate}</span>
                        </div>
    
                        <div class="order-5 ">
                            <span class="badge">수정날짜</span>
                            <span class="hover:underline text-gray-600 text-light">${rq.loginedMember.updateDate}</span>
                        </div>
    
                        <div class="order-6 ">
                            <span class="badge">로그인아이디</span>
                            <span class=" hover:underline text-gray-600">${rq.loginedMember.loginId}</span>
                        </div>
    
                        <div  class="order-7">
                            <span class="badge">이름</span>
                            <span class="hover:underline text-gray-600">${rq.loginedMember.name}</span>
                        </div>
    
                        <div class="order-8 sm:order-4 md:order-8 ">
                            <span class="badge">별명</span>
                            <span class=" hover:underline text-gray-600">${rq.loginedMember.nickname}</span>
                        </div>
                    </div>
      
                  <div class="plain-link-wrap gap-3 mt-4">
                    <a href="../member/checkPassword?afterUri=${Ut.getUriEncoded('../member/modify')}" class="plain-link">
                      <span>
                        <i class="fas fa-edit"></i>
                      </span>
                      <span>수정</span>
                    </a>
                    <a onclick="if ( !confirm('탈퇴하시겠습니까?') ) return false;"
                      href="#" class="ml-2 text-blue-500 hover:underline">
                      <span>
                        <i class="fas fa-trash"></i>
                        <span>탈퇴</span>
                      </span>
                    </a>
                  </div>
                </div>
                
          </div>
    </div>
</div>
 
<%@ include file="../common/foot.jspf" %>