<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="회원가입 페이지" />

<%@ include file="../common/head.jspf" %>
<%@ include file="../common/toastUiEditorLib.jspf"%>

<!-- 브라우저에서 비밀번호 암호화 실행하는 스크립트라이브러리 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script> 

<!-- 아이디 중복체크 ajax로직 -->
<script>

const MemberJoin__submitFormDone = false;

let JoinForm__validLoginId = "";//중복체크 여부 판단하기 위해서

let validPw = "";

function JoinForm__checkLoginIdDup(obj) {
  const form = $(obj).closest('form').get(0); //가장가까운 form을 찾아간다
  
  form.loginId.value = form.loginId.value.trim();
  
  if(form.loginId.value.length ==0 ){
    alert('아이디를 입력해주세요');
    form.loginId.focus();
    return;
  }
  
  var url = 'usr/member/getLoginIdDup?loginId=' + form.loginId.value;
  
  $.get(
    'getLoginIdDup',
    {
      loginId:form.loginId.value
    },
    function(data){
      
      let Color = 'text-blue-500';
      if ( data.fail ) {
        Color = 'text-red-500';
      }
      
      $('.loginIdInputMsg').html("<span class='" + Color + "'>" + data.msg + "</span>");
    
      //alert(data.msg);
    
      if(data.fail) {
        form.loginId.focus();
        JoinForm__validLoginId = '';
      }
      else {
        JoinForm__validLoginId = data.data1;
        form.loginPw.focus();
      }
    },
    'json'
  );
  
}

//비밀번호확인체크
function check_pw(){
  
  var pw = document.getElementById('pw').value;
  var SC = ["!","@","#","$","%"];
  var check_SC = 0;

  if(pw.length < 6 || pw.length>16){
      window.alert('비밀번호는 6글자 이상, 16글자 이하만 이용 가능합니다.');
      document.getElementById('pw').value='';
  }
  for(var i=0;i<SC.length;i++){
      if(pw.indexOf(SC[i]) != -1){
          check_SC = 1;
      }
  }
  
  if(check_SC == 0){
      window.alert('!,@,#,$,% 의 특수문자가 들어가 있지 않습니다.')
      document.getElementById('pw').value='';
      
  }
  if(document.getElementById('pw').value !='' && document.getElementById('pw2').value!=''){
      if(document.getElementById('pw').value==document.getElementById('pw2').value){
          document.getElementById('check').innerHTML='비밀번호가 일치합니다.'
          document.getElementById('check').style.color='blue';
      }
      else{
          document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
          document.getElementById('check').style.color='red';
      }
  }
}

//비밀번호확인체크

function MemberJoin__submitForm(form) {
    if ( MemberJoin__submitFormDone ) {
        return;
    }
    form.loginId.value = form.loginId.value.trim();
    if (form.loginId.value.length == 0) {
      alert('아이디를 입력해주세요.')
      return;
    }
    
	if ( form.loginId.value != JoinForm__validLoginId ) { //실제입력한 id와 중복체크로 전송된 id가 다르다면 다시 중복체크 과정을 거치도록
		alert('로그인아이디 중복체크를해주세요.');
		return;
	}

	form.loginPwInput.value = form.loginPwInput.value.trim();
	
	form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

	form.name.value = form.name.value.trim();
    if ( form.name.value.length == 0 ) {
        alert('이름을 입력해주세요.');
        form.name.focus();
        return;
    }
    form.nickname.value = form.nickname.value.trim();
    if ( form.nickname.value.length == 0 ) {
        alert('별명을 입력해주세요.');
        form.nickname.focus();
        return;
    }
    form.cellphoneNo.value = form.cellphoneNo.value.trim();
    if ( form.cellphoneNo.value.length == 0 ) {
        alert('휴대전화번호를 입력해주세요.');
        form.cellphoneNo.focus();
        return;
    }
    form.email.value = form.email.value.trim();
    if ( form.email.value.length == 0 ) {
        alert('이메일을 입력해주세요.');
        form.email.focus();
        return;
    }
    
    form.loginPw.value = sha256(form.loginPwInput.value);
    form.loginPwInput.value = '';
    form.loginPwConfirm.value = '';

    MemberJoin__submitFormDone = true;
    form.submit();
}

</script>

<div class="section section-article-list px-2">

	<div class="container mx-auto">
  
  <hr />
  <hr />
	    <form  class="card-3d-wrap mx-auto" method="POST" action="../member/doJoin" onsubmit="MemberJoin__submitForm(this); return false;">
             <div class="card-3d-wrapper">
             <input type="hidden" name="loginPw">
	        <div class="form-control">
                <label class="label">
                    아이디
                </label>
            <span >    
            <input class="input input-bordered w-full" text = "text" name="loginId" placeholder="로그인아이디를 입력해주세요." />
            <input onclick="JoinForm__checkLoginIdDup(this);" class="btn btn-outline btn-accent mt-2" type = "button" value="중복체크"/>
             </span> 
             
             <div class = "loginIdInputMsg mt-2"></div>
            </div>

            <div class="form-control">
                <label class="label">
                    비밀번호
                </label>
                <input class="input input-bordered w-full" type="password" maxlength="30" name="loginPwInput" id="pw" onchange="check_pw()" placeholder="로그인비밀번호를 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    비밀번호 확인
                </label>
                <input class="input input-bordered w-full" type="password" maxlength="30" name="loginPwConfirm" id="pw2" onchange="check_pw()" placeholder="로그인비밀번호 확인을 입력해주세요." />
                <span id="check"></span>
            </div>

            <div class="form-control">
                <label class="label">
                    이름
                </label>
                <input class="input input-bordered w-full" type="text" maxlength="30" name="name" placeholder="이름을 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    닉네임
                </label>
                <input class="input input-bordered w-full" type="text" maxlength="30" name="nickname" placeholder="별명을 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    휴대전화번호
                </label>
                <input class="input input-bordered w-full" type="tel" maxlength="30" name="cellphoneNo" placeholder="휴대전화번호를 입력해주세요." />
            </div>

            <div class="form-control">
                <label class="label">
                    이메일
                </label>
                <input class="input input-bordered w-full" type="email" maxlength="50" name="email" placeholder="이메일을 입력해주세요." />
            </div>

            <div class="mt-4 btn-wrap gap-1">
                <button type="submit"  class="btn btn-accent btn-sm mb-1">
                    <span><i class="fas fa-user-plus"></i></span>
                    &nbsp;
                    <span>가입</span>
                </button>

                <a href="/" class="btn btn-sm mb-1">
                    <span><i class="fas fa-home"></i></span>
                    &nbsp;
                    <span>홈</span>
                </a>
            </div>
            
            </div>
	    </form>
	</div>
</div>

<%@ include file="../common/foot.jspf" %>