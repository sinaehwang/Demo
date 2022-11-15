package com.hsn.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.ArticleRepository;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.ResultData;

@Service
public class ArticleService {

	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {

		this.articleRepository = articleRepository;

	}

	public ResultData getArticle(int id) {

		Article article = articleRepository.getArticle(id);
		
		if(article==null) {
			return ResultData.from("F-1", Ut.f("%d번게시글은 존재하지않습니다.", id));
		}


		return ResultData.from("S-1", "성공", "article", article);
	}

	public ResultData writeArticle(String title, String body, int loginedMemberId) {

		articleRepository.writeArticle(title, body ,loginedMemberId);

		int id =  articleRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번째 게시글생성완료", id),"id", id);

	}

	public List<Article> getArticles() {

		return articleRepository.getArticles();
	}

	public void doDelete(int id) {

		articleRepository.doDelete(id);
	}

	public ResultData doModify(int id, String title, String body) {

		articleRepository.doModify(id, title, body);
		
		return getArticle(id);//수정후의 data1을 넘겨줌
		
	}

}
