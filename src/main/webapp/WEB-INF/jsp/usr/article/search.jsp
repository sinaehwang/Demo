<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="문서검색" />
<%@ include file="../common/head.jspf"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<body>

<div class="section-article-list">
<div class="container mx-auto">

    <div class="search-form-box mt-2">
     
    검색어:  
    <input id="bookName" class="input input-bordered" placeholder="검색어를 입력해주세요." maxlength="10" type="text">
    <button id="search" class="btn-1">검색</button>
    <br />
    <br />
    
    </div>
    
    <hr />
    <hr />
    
    <p></p>
    
    <script src="https://code.jquery.com/jquery-3.4.1.js"
        integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU=" crossorigin="anonymous"></script>
    <script>
        $(document).ready(function () {
          	var pageNum = 1;
            $("#search").click(function () {
              
              	$("p").html("");
                $.ajax({
                    method: "GET",
                    url: "https://dapi.kakao.com/v3/search/book?target=title",
                    data: { query: $("#bookName").val() },
                    headers: { Authorization: "KakaoAK b6d266ed8e0ac2b0c97c846dc1bcc5bc" }
                })
                
                    .done(function (msg) {
                        console.log(msg);
                        for(var i = 0; i<10; i++){
                        $("p").append("<strong>저자:</strong>" + msg.documents[i].title +"<br>" );
                        
                        $("p").append("<strong>출판사:</strong> " + msg.documents[i].publisher + "<br>");
                        
                        $("p").append("<strong>도서상세URL:</strong>" + "<a href='"+ msg.documents[i].url+"'>바로가기</a>" + "<br>");
                        
                        $("p").append("<strong>줄거리:</strong> " + msg.documents[i].contents + "<br>");
                        
                        $("p").append("<img 책표지 src='" + msg.documents[i].thumbnail + "'/>");
                        $("p").append("<hr /><br>");
                        }
                    });
            });
            
            //10개씩 반복문으로 무한스크롤 생성
            $(window).scroll(function(){
              
              if(Math.ceil($(window).scrollTop())+ $(window).height() >= $(document).height() ){
                pageNum++;
                
                $.ajax({
                  method: "GET",
                  url: "https://dapi.kakao.com/v3/search/book?target=title",
                  data: { query: $("#bookName").val(), page:pageNum },
                  headers: { Authorization: "KakaoAK b6d266ed8e0ac2b0c97c846dc1bcc5bc" }
              })
              .done(function (msg) {
                console.log(msg);
                for(var i = 0; i<10; i++){
                $("p").append("<strong>저자:</strong>" + msg.documents[i].title +"<br>" );
                
                $("p").append("<strong>출판사:</strong> " + msg.documents[i].publisher + "<br>");
                
                $("p").append("<strong>도서상세URL:</strong>" + "<a href='"+ msg.documents[i].url+"'>바로가기</a>" + "<br>");
                
                $("p").append("<strong>줄거리:</strong> " + msg.documents[i].contents + "<br>");
                
                $("p").append("<img 책표지 src='" + msg.documents[i].thumbnail + "'/>");
                $("p").append("<hr /><br>");
                }
            });
          }
       });
 });
    </script>

</div>
</div>
</body>

<%@ include file="../common/foot.jspf"%>