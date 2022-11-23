<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"  value="MY PAGE" />  

<%@ include file="../common/head.jspf" %>


<body>

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
                    
                      <div class = "imgBackground" >                      
                        <a href="#" class="row-span-2 order-1 ">
                            <img class="rounded-full" src="https://i.pravatar.cc/100?img=37" alt="">
                        </a>
                        </div>
    
                        <a href="#" class="order-2 hover:underline">
                            <span class="badge badge-primary">번호</span>
                            <span>${rq.loginedMember.id}</span>
                        </a>
    
                        <a href="#" class="cursor-pointer order-3 hover:underline">
                            <span class="badge badge-accent">회원타입</span>
                            <span>${rq.loginedMember.authLevelName}</span>
                        </a>
    
                        <a href="#" class="order-4 hover:underline">
                            <span class="badge">등록날짜</span>
                            <span class="text-gray-600 text-light">${rq.loginedMember.regDate}</span>
                        </a>
    
                        <a href="#" class="order-5 hover:underline">
                            <span class="badge">수정날짜</span>
                            <span class="text-gray-600 text-light">${rq.loginedMember.updateDate}</span>
                        </a>
    
                        <a href="#" class="order-6 hover:underline">
                            <span class="badge">로그인아이디</span>
                            <span class="text-gray-600">${rq.loginedMember.loginId}</span>
                        </a>
    
                        <a href="#" class="order-7 hover:underline">
                            <span class="badge">이름</span>
                            <span class="text-gray-600">${rq.loginedMember.name}</span>
                        </a>
    
                        <a href="#" class="order-8 sm:order-4 md:order-8 hover:underline">
                            <span class="badge">별명</span>
                            <span class="text-gray-600">${rq.loginedMember.nickname}</span>
                        </a>
                    </div>
      
                  <div class="plain-link-wrap gap-3 mt-4">
                    <a href="#" class="plain-link">
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
 
</body>
<%@ include file="../common/foot.jspf" %>