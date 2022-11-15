<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="게시판리스트" />
<%@ include file="../common/head.jspf" %>

<div class="section-article-list">
  <div class="container mx-auto">
  <!-- 게시글갯수 -->
  <!-- 전체페이지수 -->
  <!-- 현재페이지수 -->
  <!-- 글작성 -->
  
  
    <div class="card bordered shadow-lg item-bt-1-not-last-child">
      <div class="card-title">
        <a href="javascript:history.back();" class="cursor-pointer">
          <i class="fas fa-chevron-left"></i>
        </a>
        <span>게시물 리스트</span>
      </div>
      <div class="item-bt-1-not-last-child">
        <c:forEach var="article" items="${articles }">
          <!-- 게시물 아이템, first -->
          <div class="px-4 py-8">
            <a class="hover:underline cursor-pointer">
              <span class="badge badge-outline">제목</span>
              <div class="line-clamp-3">${article.title}</div>
            </a>
            
            <div class="mt-3 grid sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-3">
              <a href="#" class="row-span-7">
                <img class="rounded" src="https://i.pravatar.cc/250?img=37" alt=""> <!-- 본문의 이미지불러오기 -->
              </a>

              <a href="#" class="hover:underline">
                <span class="badge badge-primary">번호</span>
                <span>${article.id}</span>
              </a>

              <a href="#" class="cursor-pointer hover:underline">
                <span class="badge badge-accent">작성자</span>
                <span>홍길동</span>
              </a>

              <a href="#" class="hover:underline">
                <span class="badge">등록날짜</span>
                <span class="text-gray-600 text-light">${article.regDate}</span>
              </a>

              <a href="#" class="hover:underline">
                <span class="badge">수정날짜</span>
                <span class="text-gray-600 text-light">${article.updateDate}</span>
              </a>

              <a href="#" class="mt-3 hover:underline cursor-pointer col-span-1 sm:col-span-2 xl:col-span-3">
                  <span class="badge badge-outline">본문</span>
                  <div class="line-clamp-3">
                    ${article.body}
                  </div>
              </a>
            </div>

            <div class="plain-link-wrap gap-3 mt-4">
              <a href="#" class="plain-link" title="자세히 보기">
                <span>
                  <i class="fas fa-info"></i>
                </span>
                <span>자세히 보기</span>
              </a>
              <a href="#" class="plain-link">
                <span>
                  <i class="fas fa-edit"></i>
                </span>
                <span>수정</span>
              </a>
              <a onclick="if ( !confirm('삭제하시겠습니까?') ) return false;"
                href="#" class="plain-link"
              >
                <span>
                  <i class="fas fa-trash"></i>
                  <span>삭제</span>
                </span>
              </a>
            </div>
          </div>
        </c:forEach>
      </div>
      
    </div>
  </div>
</div>
<%@ include file="../common/foot.jspf" %>