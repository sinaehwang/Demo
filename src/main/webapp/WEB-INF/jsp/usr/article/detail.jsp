<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name } 상세페이지" />
<%@ include file="../common/toastUiEditorLib.jspf"%>
<%@ include file="../common/head.jspf" %>

<body>
<div class="section section-article-detail">
  <div class="container mx-auto">
      <div class="card bordered shadow-lg item-bt-1-not-last-child">
            <div class="card-title">
                <a href="javascript:history.back();" class="cursor-pointer">
                    <i class="fas fa-chevron-left"></i>
                </a>
                <span>게시물 상세페이지</span>
            </div>
            <div>
                <h1 class="title-bar-type-2 px-4">상세내용</h1>
                <div class="px-4 py-8">
                    <div class="flex">
                        <span>
                            <span>댓글:</span>
                            <span class="text-gray-400 text-light">30</span>
                            &nbsp;
                        </span>
                       
                        <span>
                            <span>좋아요:</span>
                            <span class="text-gray-400 text-light">120</span>
                        </span>
                        
                        <div class="flex-grow"></div>
                        <span class="ml-3">
                            <span>조회수:</span>
                            <span class="text-gray-400 text-light">60</span>
                        </span>
                    </div>

                    <div class="mt-4">
                        <span class="badge badge-outline">제목</span>
                        <div>
                            ${article.title}
                        </div>
                    </div>

                    <div class="mt-3 grid sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-3">
                        <div>
                            <span class="badge badge-primary">번호</span>
                            <span>${article.id}</span>
                        </div>

                        <div>
                            <span class="badge badge-accent">작성자</span>
                            <span>${article.extra__writerName}</span>
                        </div>

                        <div>
                            <span class="badge">등록날짜</span>
                            <span class="text-gray-600 text-light">${article.regDate}</span>
                        </div>

                        <div>
                            <span class="badge">수정날짜</span>
                            <span class="text-gray-600 text-light">${article.updateDate}</span>
                        </div>
                    </div>

                    <div class="mt-6">
                        <span class="badge badge-outline">본문</span>
                        <!-- 본문 이미지넣는공간 -->
                        <div class="mt-3">
                            <div class="toast-ui-viewer">
                                <script type="text/x-template">${article.bodyForPrint}</script>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div>
                <h1 class="title-bar-type-2 px-4">댓글</h1>
                <c:if test="${rq.notLogined}">
                    <div>
                        댓글 작성은 <a class = "plain-link" href=" ${rq.loginPageUri} ">로그인</a> 후 이용할 수 있습니다.
                    </div>
                </c:if>
                
                <c:if test="${rq.logined}">
                <div class="px-4 py-8">
                    <!-- 댓글 입력 시작 -->
                    <form method="POST" action="../reply/doWrite" class="relative flex py-4 text-gray-600 focus-within:text-gray-400">
                        <input type="hidden" name="relTypeCode" value="article" />
                        <input type="hidden" name="relId" value="${article.id}" />
                        <input type="hidden" name="redirectUri" value="${rq.currentUri}" />
                        <img class="w-10 h-10 object-cover rounded-full shadow mr-2 cursor-pointer" alt="User avatar" src="https://play-lh.googleusercontent.com/38AGKCqmbjZ9OuWx4YjssAz3Y0DTWbiM5HB0ove1pNBq_o9mtWfGszjZNxZdwt_vgHo=w240-h480-rw?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=200&amp;q=200">
                        
                        <!-- 프로필이미지 -->

                        <span class="absolute inset-y-0 right-0 flex items-center pr-6">
                            <button type="submit" class="p-1 focus:outline-none focus:shadow-none hover:text-blue-500">
                                <svg class="w-6 h-6 transition ease-out duration-300 hover:text-blue-500 text-gray-400" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14.828 14.828a4 4 0 01-5.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                                </svg>
                            </button>
                        </span>

                        <input name="body" type="text" class="w-full py-2 pl-4 pr-10 text-sm bg-gray-100 border border-transparent appearance-none rounded-tg placeholder-gray-400 focus:bg-white focus:outline-none focus:border-blue-500 focus:text-gray-900 focus:shadow-outline-blue" style="border-radius: 25px" placeholder="댓글을 입력해주세요." autocomplete="off">
                    </form>
                    <!-- 댓글 입력 끝 -->
                </div>
             </c:if>
             
             <!-- 댓글리스트 -->
             <div>
                    <c:forEach items="${replies}" var="reply">
                        <div class="py-5 px-4">
                            <div class="flex">
                            <!-- 아바타 이미지 -->
                            <div class="flex-shrink-0">
                                <img class="w-10 h-10 object-cover rounded-full shadow mr-2 cursor-pointer" alt="User avatar" src="https://images.unsplash.com/photo-1477118476589-bff2c5c4cfbb?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=200&amp;q=200">
                            </div>

                            <div class="flex-grow px-1">
                                <div class="flex text-gray-400 text-light text-sm">
                                    <span>${reply.extra__writerName}</span>
                                    <span class="mx-1">·</span>
                                    <span>${reply.updateDate}</span>
                                </div>
                                <div class="break-all">
                                    ${reply.bodyForPrint}
                                </div>
                                <div class="mt-1">
                                    <span class="text-gray-400 cursor-pointer">
                                        <i class="fa-solid fa-heart"></i>
                                        <span>좋아요수:</span>
                                    </span>
                                    <span class="ml-1 text-gray-400 cursor-pointer">
                                        <i class="fa-regular fa-heart"></i>
                                        <span>싫어요수:</span>
                                    </span>
                                </div>
                            </div>
                            
                            
                            
                           </div> 
                           <div class="plain-link-wrap gap-3 mt-3">
                                <c:if test="${reply.memberId == rq.loginedMemberId}">
                                    <a onclick="if ( !confirm('정말 삭제하시겠습니까?') ) return false;" href="../reply/doDelete?id=${reply.id}&redirectUri=${rq.encodedCurrentUri}" class="plain-link">
                                        <span><i class="fas fa-trash-alt"></i></span>
                                        <span>댓글 삭제</span>
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
             <!-- 댓글리스트 -->
             
             
            </div>
        </div>
  </div>
</div>
</body>
<%@ include file="../common/foot.jspf" %>