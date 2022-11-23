<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"  value="비밀번호확인" />  

<%@ include file="../common/head.jspf" %>
<body>

<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<script>
let MemberCheckPassword__submitFormDone = false;
function MemberCheckPassword__submitForm(form) {
    if ( MemberCheckPassword__submitFormDone ) {
        return;
    }
    form.loginPwInput.value = form.loginPwInput.value.trim();
    if ( form.loginPwInput.value.length == 0 ) {
        alert('로그인비밀번호을 입력해주세요.');
        form.loginPwInput.focus();
        return;
    }
    form.loginPw.value = sha256(form.loginPwInput.value);
    form.loginPwInput.value = '';
    form.submit();
    MemberCheckPassword__submitFormDone = true;
}
</script>

<div class="section section-login px-2">
	<div class="container mx-auto">
	    <form class = "card-3d-wrap mx-auto" method="POST" action="doCheckPassword" onsubmit="MemberCheckPassword__submitForm(this); return false;">
	      <input type="hidden" name="redirectUri" value="${param.afterUri}" />
          <input type="hidden" name="loginPw" />  
          
          <div class="card-3d-wrapper ">
            <div class="card-front">
                  <div class="center-wrap">
                    <div class="section text-center">
                        <h4 class="mb-4 pb-3">비밀번호 확인</h4>
                        <div class="form-group">
                          <input type="password" name="loginPwInput" class="form-style" placeholder="Your Login Pw" id="loginId" autocomplete="off">
                          <i class="input-icon uil uil-at"></i>
                        </div>  

                               <button type="submit"  class="btn-1 mt-4"">
                               <span><i class="fa-solid fa-user-check"></i></span>
                                &nbsp;
                               <span>submit</span>
                               </button>
                               <p class="mb-0 mt-4 text-center"><a href="/"  type="submit" class="link">메인페이지로 이동하시겠습니까?</a></p>
                          </div>
                      
                    </div>  
                 </div>
            </div>
         </div> 
	    </form>
	</div>
</div>

</body>
<%@ include file="../common/foot.jspf" %>