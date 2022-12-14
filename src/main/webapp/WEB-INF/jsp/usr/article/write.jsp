<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="${board.name } 글쓰기" />
<%@ include file="../common/toastUiEditorLib.jspf"%>
<%@ include file="../common/head.jspf" %>



<script>
let ArticleWrite__submitFormDone = false;
function ArticleWrite__submitForm(form) {
    if ( ArticleWrite__submitFormDone ) {
      alert('처리진행중입니다.');
      return;
    }
    
    form.title.value = form.title.value.trim();
    if ( form.title.value.length == 0 ) {
        alert('제목을 입력해주세요.');
        form.title.focus();
        return;
    }
    
    const editor = $(form).find('.toast-ui-editor').data(
		'data-toast-editor');
    const markdown = editor.getMarkdown().trim();
    
    if (markdown.length == 0) {
    	alert('내용을 입력해주세요');
    	editor.focus();
    	return;
    }
    
    form.body.value = markdown;
    
    ArticleWrite__submitFormDone = true;
    
    form.submit();
    
}
</script>


  <div class="section-article-write">
  <div class="container mx-auto">
  <form method="POST" action="../article/doWrite" enctype="multipart/form-data" onsubmit="ArticleWrite__submitForm(this); return false;">
  <input type="hidden" name="boardId" value="${board.id}" />
  
  <input type="hidden" name="body" />
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
              <input type="text" name="LoginedMemberName" value="${rq.loginedMember.name}" />
            </div>
          </div>
          
          <div class="form-control">
            <label class="cursor-pointer label">
              음식 카테고리
            </label>
            <div class="plain-text">
                <select class="select select-bordered" name="catergoryId">
                <option disabled>카테고리를 선택해주세요</option>
                <option value="1">한식</option>
                <option value="2">중식</option>
                <option value="3">양식</option>
                </select>
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
            
            <div class="toast-ui-editor">
				<script type="text/x-template"></script>
			</div>
            
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
            
            <input onclick="history.back();" type="button" class="btn btn-primary btn-sm mb-1" value="취소"> 
            
            <a href="../article/list" class="btn btn-sm mb-1" title="자세히 보기">
              <span><i class="fas fa-list"></i></span>
              &nbsp;
              <span>리스트</span>
            </a>

          </div>
        </form>
      </div>
    </div>
    </form>
  </div>
</div>



<%@ include file="../common/foot.jspf" %>