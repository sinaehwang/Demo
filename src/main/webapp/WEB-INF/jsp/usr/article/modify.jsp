<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name } 수정페이지" />
<c:set var="fileInputMaxCount" value="2" />

<%@ include file="../common/head.jspf" %>

<!-- 수정확인 스크립트 -->
<script>
ArticleModify__fileInputMaxCount = parseInt("${fileInputMaxCount}");
</script>

<script>
function ArticleModify__checkAndSubmit(form) {
	if ( ArticleModify__submited ) {
		alert('처리중입니다.');
		return;
	}
	
	form.title.value = form.title.value.trim();
	if ( form.title.value.length == 0 ) {
		alert('제목을 입력해주세요.');
		form.title.focus();
		return false;
	}
	form.body.value = form.body.value.trim();
	if ( form.body.value.length == 0 ) {
		alert('내용을 입력해주세요.');
		form.body.focus();
		return false;
	}
	
	ArticleModify__submited = true;
}

</script>


<body>
<div class="section section-article-list">
  <div class="container mx-auto">
    <form onsubmit="ArticleModify__checkAndSubmit(this); return false;" action="../article/doModify" method="POST" enctype="multipart/form-data">
     <input type="hidden" name="id" value="${article.id}" />
      <div class="card bordered shadow-lg item-bt-1-not-last-child">
            <div class="card-title">
                <a href="javascript:history.back();" class="cursor-pointer">
                    <i class="fas fa-chevron-left"></i>
                </a>
                <span>게시물 수정페이지</span>
            </div>
            <div>
                <h1 class="title-bar-type-2 px-4">상세내용</h1>
                <div class="px-4 py-8">
                    <div class="flex">
                        <span>
                            <span>Comments:</span>
                            <span class="text-gray-400 text-light">30</span>
                        </span>
                        <span class="ml-3">
                            <span>Views:</span>
                            <span class="text-gray-400 text-light">60k</span>
                        </span>
                        <div class="flex-grow"></div>
                        <span>
                            <span>Likes:</span>
                            <span class="text-gray-400 text-light">120k</span>
                        </span>
                    </div>

                    <div class="form-control">
                      <label class="label">
                        <span class="label-text">제목</span>
                      </label>
                      <input value="${article.title}" type="text" placeholder="제목을 입력해주세요" name="title" class="input input-bordered">
                    </div>


                    <div class="form-control">
                      <label class="label">
                        <span class="label-text">본문</span>
                      </label>
                      <textarea placeholder="내용을 입력해주세요" name="body" class="h-80 textarea textarea-bordered">${article.body}</textarea>
                    </div>
                    
                </div>
            </div>
        
        <c:forEach begin="1" end="${fileInputMaxCount}" var="inputNo">
        <c:set var="fileNo" value="${String.valueOf(inputNo)}" />  
        <c:set var="file" value="${article.extra.file__common__attachment[fileNo]}" />
        <div class="form-control">
            <div class="lg:flex lg:items-center lg:w-28">
              <span>첨부파일 ${inputNo}</span>
            </div>
            <div class="lg:flex-grow">
                <input type="file" name="file__article__0__common__attachment__${inputNo}"
                class="form-row-input w-full rounded-sm" />
                
                <c:if test="${file != null}">
                    <div>
                      ${file.fileName}
                    </div>
                    <div>
                      <label>
                        <input type="checkbox" name="deleteFile__article__${article.id}__common__attachment__${fileNo}" value="Y" />
                        <span>삭제</span>
                      </label>
                    </div>
                    <c:if test="${file.fileExtTypeCode == 'img'}">
                                    <div class="img-box img-box-auto">
                                        <img src="${file.forPrintUrl}">
                                    </div>
                    </c:if>
              </c:if>
              

                        
              

            </div>
        </div>
       </c:forEach>

        <div class="mt-4 btns btn-wrap gap-1">
                <button type="submit" href="../article/detail?id=${article.id }" class="btn btn-primary btn-sm mb-1">
                    <span><i class="fas fa-save"></i></span>
                    &nbsp;
                    <span>수정</span>
                </button>
                
                <button onclick="history.back();" class="btn btn-primary btn-sm mb-1">
                    <span><i class="fa-solid fa-rectangle-xmark"></i></span>
                    &nbsp;
                    <span>취소</span>
                </button>    

          </div>
          
        </div>
     </form>  
  </div>
</div>
</body>
<%@ include file="../common/foot.jspf" %>