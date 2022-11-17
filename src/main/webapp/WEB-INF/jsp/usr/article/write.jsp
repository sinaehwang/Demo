<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name } 글쓰기" />
<%@ include file="../common/head.jspf" %>
<body>

<script>
let ArticleWrite__submitFormDone = false;
function ArticleWrite__submitForm(form) {
    if ( ArticleWrite__submitFormDone ) {
        return;
    }
    form.title.value = form.title.value.trim();
    if ( form.title.value.length == 0 ) {
        alert('제목을 입력해주세요.');
        form.title.focus();
        return;
    }
    form.body.value = form.body.value.trim();
    if ( form.body.value.length == 0 ) {
        alert('내용을 입력해주세요.');
        form.body.focus();
        return;
    }
    form.submit();
    ArticleWrite__submitFormDone = true;
}
</script>


  <div class="section-article-write">
  <div class="container mx-auto">
  <form method="POST" action="../article/doWrite" enctype="multipart/form-data" onsubmit="ArticleWrite__submitForm(this); return false;">
  <input type="hidden" name="boardId" value="${board.id}" />
    <div class="card bordered shadow-lg item-bt-1-not-last-child">
      <div class="card-title">
        <a href="javascript:history.back();" class="cursor-pointer">
          <i class="fas fa-chevron-left"></i>
        </a>
        <span>게시물 작성</span>
      </div>
      <div class="px-4 py-8">
        <form class="grid form-type-1">
          <div class="form-control">
            <label class="cursor-pointer label">
              작성자
            </label>
            <div class="plain-text">
              홍길동
            </div>
          </div>

          <div class="form-control">
            <label class="label">
              <span class="label-text">제목</span>
            </label>
            <input type="text" placeholder="제목을 입력해주세요" name="title" class="input input-bordered">
          </div>

          <div class="form-control">
            <label class="label">
              <span class="label-text">본문</span>
            </label>
            <textarea placeholder="내용을 입력해주세요" name="body" class="h-80 textarea textarea-bordered"></textarea>
          </div>

    		<div class="form-control">
    				<div class="lg:flex lg:items-center lg:w-28">
    					<span>첨부파일 1</span>
    				</div>
    				<div class="lg:flex-grow">
    					<input type="file" name="file__article__0__common__attachment__1"
    						class="form-row-input w-full rounded-sm" />
    				</div>
    		</div>
        
    		<div class="form-control">
    				<div class="lg:flex lg:items-center lg:w-28">
    					<span>첨부파일 2</span>
    				</div>
    				<div class="lg:flex-grow">
    					<input type="file" name="file__article__0__common__attachment__2"
    						class="form-row-input w-full rounded-sm" />
    				</div>
    		</div>
        
          <div class="mt-4 btn-wrap gap-1">
                <button type="submit"  class="btn btn-primary btn-sm mb-1">
                    <span><i class="fas fa-save"></i></span>
                    &nbsp;
                    <span>작성</span>
                </button>
            
            <a onclick="if ( !confirm('삭제하시겠습니까?') ) return false;" href="#"  class="btn btn-error btn-sm mb-1">
              <span><i class="fas fa-trash"></i></span>
              &nbsp;
              <span>삭제</span>
            </a>
            <a href="#" class="btn btn-sm mb-1" title="자세히 보기">
              <span><i class="fas fa-list"></i></span>
              &nbsp;
              <span>리스트</span>
            </a>
            <a href="#" class="btn btn-sm mb-1" title="자세히 보기">
              <span><i class="fas fa-list"></i></span>
              &nbsp;
              <span>상세페이지</span>
            </a>
          </div>
        </form>
      </div>
    </div>
    </form>
  </div>
</div>


</body>
<%@ include file="../common/foot.jspf" %>