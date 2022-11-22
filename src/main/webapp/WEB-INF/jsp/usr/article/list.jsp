<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name }" />
<%@ include file="../common/head.jspf" %>


<div class="section-article-list">
  <div class="container mx-auto">
      <div class="total-items" >
          <span>전체 게시글수 : </span>
          <span>${totalItemsCount}</span>
      </div>
      
      <div class="total-pages">
          <span>전체 페이지수 : </span>
          <span>${totalPage}</span>
      </div>

      <div class="page">
            <span>현재페이지 : </span>
            <span>${page}</span>
      </div>

    <hr />
    <hr />
<!-- 검색어박스자리 -->   
      <div class="search-form-box mt-2 px-4">
        <form action="" class="grid gap-2">
                <input type="hidden" name="boardId" value="${board.id}" />
                
                <div class="form-control">
                      <label class="label">
                        <span class="label-text">옵션</span>
                      </label>
                      <select class="select select-bordered" name="searchKeywordType">
                        <option value="titleAndBody">제목+내용</option>
                        <option value="title">제목</option>
                        <option value="body">내용</option>
                      </select>
                      
                      <script>
                        const param__searchKeywordType = '${param.searchKeywordType}';
                        if ( param__searchKeywordType.length > 0 ) {
                          $('.search-form-box form [name="searchKeywordType"]')
                          .val('${param.searchKeywordType}');
                        }
                      </script>
                </div>
    
            <div class="form-control">
                  <label class="label">
                    <span class="label-text">검색어</span>
                  </label>
                  <input value="${param.searchKeyword}" class="input input-bordered"
                    name="searchKeyword" type="text" placeholder="검색어를 입력해주세요." maxlength="10" />
            </div>
    
            <div class="form-control">
               <p class="mb-0 mt-4 text-center"><input type="submit" class="btn-1" value="검색" /></p>
            </div>
        </form>
      </div>
<!-- 검색어박스자리 -->          
<!-- 리스트자리 -->  
     <div class="mt-5 card bordered shadow-lg item-bt-1-not-last-child">
          <div class="card-title">
            <a href="javascript:history.back();" class="cursor-pointer">
              <i class="fas fa-chevron-left"></i>
            </a>
            <span>게시물 리스트</span>
          </div>
          
          <div class="item-bt-1-not-last-child">

              <div class="plain-link-wrap gap-3 flex justify-end mt-10 mr-10">
                <a href="../article/write?boardId=${board.id}" class=" btn-primary bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded">
                <span><i class="fa-regular fa-pen-to-square"></i></span>
                <span>게시글 작성</span>
                </a>
              </div>

              <c:if test="${articles == null || articles.size() == 0}" >
                    <div class = "flex Stretch grid justify-items-center">
                        <div class = "h-10">검색결과가 존재하지 않습니다.</div>
                    </div>
               </c:if>

              <c:forEach var="article" items="${articles }">
              <c:set var="detailUrl" value="detail?id=${article.id}" />
                <!-- 게시물 아이템, first -->
                <div class="px-4 py-8">
                    
                    <div>
                      <span class="badge badge-primary">번호</span>
                      <a href="${detailUrl}" class="hover:underline cursor-pointer" >${article.id}</a>
                    </div>  
                    
                    
                    <span class="badge badge-outline">제목</span>
                    <a href="${detailUrl}" class="hover:underline cursor-pointer">${article.title}</a>

                  <div class="mt-3 grid sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-3">
                      <c:if test="${article.extra__thumbImg != null}">
                          <img class ="max-w-xs" src="${article.extra__thumbImg}" alt="" /> <!-- 본문의 이미지불러오기 -->
                      </c:if>
                      <c:if test="${article.extra__thumbImg == null}">
                          <img class ="max-w-xs" src="https://3.bp.blogspot.com/-ZKBbW7TmQD4/U6P_DTbE2MI/AAAAAAAADjg/wdhBRyLv5e8/s1600/noimg.gif" alt="" /> <!-- 본문의 이미지불러오기 -->
                      </c:if>
      
                   <a  href="${detailUrl}" class="mt-3  hover:underline cursor-pointer col-span-1 sm:col-span-2 xl:col-span-3">
                        <span class="badge badge-outline">본문</span>
                        <div class="line-clamp-3 ">
                          ${article.body}
                        </div>
                    </a>
      
                    <a href="${detailUrl}">
                      <span class="badge">수정날짜</span>
                      <span class="text-gray-600 text-light hover:underline">${article.updateDate}</span>
                    </a>

                    <a href="#" >
                      <span class="badge badge-accent">작성자</span>
                      <span class = "hover:underline">${article.extra__writerName}</span>
                    </a>
      
                    <a href="${detailUrl}" >
                      <span class="badge">등록날짜</span>
                      <span class="text-gray-600 text-light hover:underline"">${article.regDate}</span>
                    </a>
                    
                    
                  </div>
      
                  <div class="plain-link-wrap gap-3 mt-4">
                    <a href="${detailUrl}" class="plain-link" title="자세히 보기">
                      <span>
                        <i class="fas fa-info"></i>
                      </span>
                      <span>자세히 보기</span>
                    </a>
                    <a href="../article/modify?id=${article.id}" class="plain-link">
                      <span>
                        <i class="fas fa-edit"></i>
                      </span>
                      <span>수정</span>
                    </a>
                    <a onclick="if ( !confirm('삭제하시겠습니까?') ) return false;"
                      href="../article/doDelete?id=${article.id}" class="ml-2 text-blue-500 hover:underline">
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
<!-- 리스트자리 -->
<!-- 페이징부 -->
    <div class="pages mt-4 mb-4 text-center">
        <c:set var="pageMenuArmSize" value="3" />
        <c:set var="startPage"
          value="${page - pageMenuArmSize >= 1  ? page - pageMenuArmSize : 1}" />
        <c:set var="endPage"
          value="${page + pageMenuArmSize <= totalPage ? page + pageMenuArmSize : totalPage}" />
        <c:set var="uriBase" value="?boardId=${board.id}" />
        <c:set var="uriBase"
          value="${uriBase}&searchKeywordType=${param.searchKeywordType}" />
        <c:set var="uriBase"
          value="${uriBase}&searchKeyword=${param.searchKeyword}" />
          
        <c:set var="aClassStr"
        value="px-2 inline-block border border-gray-200 rounded text-lg hover:bg-gray-200" />  
        
        <c:if test="${startPage > 1}">
            <a class="${aClassStr}" href="${uriBase}&page=1">첫페이지</a>
            <a class="text-xl" href="${uriBase}&page=${startPage - 1}">◀</a>
        </c:if>  
          
          
        <c:forEach var="i" begin="${startPage}" end="${endPage}">
            <a class="text-xl ${page == i ? 'text-red-500 underline' : ''}" href="${uriBase}&page=${i}">${i}</a>
        </c:forEach>
        
        <c:if test="${endPage < totalPage}">
            <a class="text-xl" href="${uriBase}&page=${endPage + 1}">▶</a>
    
            <a class="${aClassStr}" href="${uriBase}&page=${totalPage}">마지막페이지</a>
        </c:if>
    </div>
<!-- 페이징부 -->  
  </div>
</div>
<%@ include file="../common/foot.jspf" %>