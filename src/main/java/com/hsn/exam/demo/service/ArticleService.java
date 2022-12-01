package com.hsn.exam.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsn.exam.demo.repository.ArticleRepository;
import com.hsn.exam.demo.util.Ut;
import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.Board;
import com.hsn.exam.demo.vo.Member;
import com.hsn.exam.demo.vo.ResultData;

@Service
public class ArticleService {

	private ArticleRepository articleRepository;
	
	private GenFileService genFileService;

	public ArticleService(ArticleRepository articleRepository,GenFileService genFileService) {

		this.articleRepository = articleRepository;
		
		this.genFileService = genFileService;
		
		

	}

	public ResultData getArticle(int id) {

		Article article = articleRepository.getArticle(id);
		
		if(article==null) {
			return ResultData.from("F-1", Ut.f("%d번게시글은 존재하지않습니다.", id));
		}


		return ResultData.from("S-1", "성공", "article", article);
	}

	public ResultData writeArticle(Map<String, Object> param) {

		articleRepository.writeArticle(param);

		int id =  articleRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번째 게시글생성완료", id),"id", id);

	}

	public List<Article> getArticles(int boardId, int itemsCountInAPage, int page, String searchKeyword, String searchKeywordType, int catergoryId) {
		
		if(searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}
		
		int limitFrom = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;
		

		return articleRepository.getArticles(boardId,limitFrom,limitTake,searchKeyword,searchKeywordType,catergoryId);
	}

	public void doDelete(int id) {

		articleRepository.doDelete(id);
		
		genFileService.deleteFiles("article", id);
	}

	public Article doModify(int id, String title, String body) {

		articleRepository.doModify(id, title, body);
		
		return getArticleById(id);//수정후의 data1을 넘겨줌
		
	}

	public Board getBoardbyId(int boardId) {
		
		return articleRepository.getBoardbyId(boardId);
	}

	public int getArticlesTotalCount(int boardId, String searchKeyword, String searchKeywordType, int catergoryId) {
		
		if(searchKeyword != null && searchKeyword.length() == 0) {
			searchKeyword = null;
		}

		return articleRepository.getArticlesTotalCount(boardId,searchKeyword,searchKeywordType,catergoryId);
	}

	public Article getForPrintArticle(Integer id) {

		return articleRepository.getForPrintArticle(id);
	}

	public Article getArticleById(int relId) {
		
		return articleRepository.getArticle(relId);
	}

	public ResultData getActorCanDeleteRd(Article article, int actorId) {
		if (article.getMemberId() == actorId) {
			return new ResultData("S-1", "가능합니다.");
		}

		return ResultData.from("F-1", "삭제권한이 없습니다.");
	}

	public ResultData getActorCanModifyRd(Article article, int actorId) {
		if (article.getMemberId() == actorId) {
			return new ResultData("S-1", "가능합니다.");
		}

		return ResultData.from("F-1", "수정권한이 없습니다.");
	}

	public void increaseGoodReaction(int actorId, int relId) {
		
		 articleRepository.increaseGoodReaction(actorId,relId);
	}

	public void decreaseGoodReaction(int actorId, int relId) {

		articleRepository.decreaseGoodReaction(actorId,relId);
	}

	public void increaseBadReaction(int actorId, int relId) {

		articleRepository.increaseBadReaction(actorId,relId);
	}

	public void decreaseBadReaction(int actorId, int relId) {

		articleRepository.decreaseBadReaction(actorId,relId);
	}

}
