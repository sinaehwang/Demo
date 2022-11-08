package com.hsn.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsn.exam.demo.service.ArticleService;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.ResultData;

@Controller
public class UsrArticleController {
	
	//전역변수를 만들어줌

	@Autowired
	private ArticleService articleService;//Service랑 연결됨
	
	
	@RequestMapping("/usr/article/doAdd")//브라우저요청으로 글을 추가하는경우
	@ResponseBody()
	public ResultData doAdd(String title,String body) {
		
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "title을 입력해주세요");
		}
		
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "body을 입력해주세요");
		}
		
		ResultData writeArticlerd = articleService.writeArticle(title,body);//data1에 id를 저장해서 리턴해준상태
		
		
		//Article article = articleService.getArticle((int)writeArticlerd.getData1());//data1으로 새로운 data를 찾은상태

		ResultData article =  articleService.getArticle((int)writeArticlerd.getData1());
		
		if(article.isFail()) {
			return ResultData.from(article.getResultCode(), article.getMsg());
		}
		
		return ResultData.newData(writeArticlerd,article.getData1());//기존ResultData 코드와 메세지유지하고 data1만 변경해서 사용하는 newData 
		
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody()
	public ResultData getArticles() {
		
		List<Article> articles = articleService.getArticles();
		
		if(articles ==null) {
			return ResultData.from("F-1", "게시글리스트가 없습니다.");
		}
		
		return ResultData.from("S-1", "게시글리스트입니다.", articles);
		
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody()
	
	public ResultData getArticles(int id) {
		
		ResultData article  = articleService.getArticle(id);
		
		if(article.isFail()) {
			
			return ResultData.from(article.getResultCode(),article.getMsg());
		}
		
		return ResultData.from(article.getResultCode(), article.getMsg(), article.getData1()); 
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody()
	public ResultData doDelete(int id) {
		
		ResultData Foundarticle  = articleService.getArticle(id);
		
		if(Foundarticle.isFail()) {
			
			return ResultData.from(Foundarticle.getResultCode(),Foundarticle.getMsg());
		}
		
		articleService.doDelete(id);
		
		return ResultData.from("S-1", Ut.f("%d번게시글 삭제완료.", id));
		
	}
	
	@RequestMapping("/usr/article/doModify") //에러발생
	@ResponseBody()
	public ResultData doModify(int id,String title, String body) {//return타입을 String과 Article 둘다사용하기위해 Object로변경해줌
		
		ResultData ModifyArticle = articleService.getArticle(id);
		
		if(ModifyArticle.isFail()) {
			return ResultData.from(ModifyArticle.getResultCode(), ModifyArticle.getMsg());
		}
		
		ResultData ModifyArticlerd = articleService.doModify(id,title,body);
		
		return ResultData.from(ModifyArticlerd.getResultCode(),ModifyArticlerd.getMsg(),ModifyArticlerd.getData1());
		
	}
	

}
