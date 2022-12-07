<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle"  value="로그인페이지" />  

<%@ include file="../common/head.jspf" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<body>
<script>
let MemberLogin__submitFormDone = false;
function MemberLogin__submitForm(form) {
    if ( MemberLogin__submitFormDone ) {
        return;
    }
    form.loginId.value = form.loginId.value.trim();
    if ( form.loginId.value.length == 0 ) {
        alert('로그인아이디를 입력해주세요.');
        form.loginId.focus();
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
    MemberLogin__submitFormDone = true;
}
</script>


  <div class="container mx-auto loginForm-out">
  <div class ="loginForm-in card">
      <form method="POST" action="doLogin" onsubmit="MemberLogin__submitForm(this); return false;">
          <input type="hidden" name="redirectUri" value="${param.afterLoginUri}" />
          <input type="hidden" name="loginPw" />
          
          <!-- 로그인컨트롤구역 -->
          <div class="section">
		<div class="container ">
    
      <a href="/" class="logo" target="_blank">
        <img src="https://user-images.githubusercontent.com/109134688/203586580-6a7ac039-be14-4579-b85d-08c0a885b11b.png" alt="">
      </a>
			<div class="row full-height justify-content-center">
				<div class="col-12 text-center align-self-center py-5">
					<div class="section pb-5 pt-5 pt-sm-2 text-center">
			          	<label for="reg-log"></label>
						<div class="card-3d-wrap mx-auto">
							<div class="card-3d-wrapper">
								<div class="card-front">
									<div class="center-wrap">
										<div class="section text-center">
											<h4 class="mb-4 pb-3">Log In</h4>
											<div class="form-group">
												<input type="text" name="loginId" class="form-style" placeholder="Your Login Id" id="loginId" autocomplete="off">
												<i class="input-icon uil uil-at"></i>
											</div>	
											<div class="form-group mt-2">
												<input type="password" name="loginPwInput" class="form-style" placeholder="Your Login Password" id="loginPwInput" autocomplete="off">
												<i class="input-icon uil uil-lock-alt"></i>
											</div>
                                            
                                            <div class ="flex justify-center">
											<button type="submit"  class="btn-1 mt-4">
                                                <span><i class="fa-solid fa-user-check"></i></span>
                                                &nbsp;
                                                <span>submit</span>
                                            </button>
                                            &nbsp;
                                            <a href="https://kauth.kakao.com/oauth/authorize?client_id=b6d266ed8e0ac2b0c97c846dc1bcc5bc&redirect_uri=http://localhost:8082/auth/kakao/calback&response_type=code" ><img class = "h-11 mt-4" src="/img/kakao_login_btn.png" alt="" /></a>
                                            </div>

                            				<p class="mb-0 mt-4 text-center"><a href="../member/findLoginId"  type="submit" class="link">Forgot your id?</a></p>
                                            <p class="mb-0 mt-4 text-center"><a href="../member/findLoginPw" type="submit" class="link">Forgot your password?</a></p>
				      					</div>
			      					</div>
			      				</div>
								
			      			</div>
			      		</div>
			      	</div>
		      	</div>
	      	</div>
	    </div>
	</div>
            <!-- 로그인컨트롤구역 -->
            
      </form>
      </div>
  </div>


</body>
<%@ include file="../common/foot.jspf" %>