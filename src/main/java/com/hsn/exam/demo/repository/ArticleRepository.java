package com.hsn.exam.demo.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hsn.exam.demo.vo.Article;
import com.hsn.exam.demo.vo.Board;

@Mapper
public interface ArticleRepository {
	
	public Article getArticle(int id);

	public void writeArticle(Map<String, Object> param);

	public int getLastInsertId();

	public List<Article> getArticles(int boardId, int limitFrom, int limitTake, String searchKeyword, String searchKeywordType, int catergoryId);

	public void doDelete(int id);

	public void doModify(int id, String title, String body);

	public Board getBoardbyId(int boardId);

	public int getArticlesTotalCount(int boardId, String searchKeyword, String searchKeywordType, int catergoryId);

	public Article getForPrintArticle(Integer id);

	public void increaseGoodReaction(int actorId, int relId);

	public void decreaseGoodReaction(int actorId, int relId);

	public void increaseBadReaction(int actorId, int relId);

	public void decreaseBadReaction(int actorId, int relId);

	public int increaseHitCount(int id);

	public int getArticleHitCount(int id);


}
